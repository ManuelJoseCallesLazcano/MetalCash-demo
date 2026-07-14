package org.socymet.anticipos
import grails.gorm.transactions.Transactional

import org.grails.web.json.JSONArray
import org.socymet.cancelacion.PagoTransporte
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.recepcion.RecepcionDeOro
import org.socymet.seguridad.SecUser
import org.socymet.utilidades.NumeroALiteral
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_RECEPCION','ROLE_CAJA'])
@Transactional
class AnticipoController {
    def springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST", agregarCuota: "POST", eliminarCuota: "POST"]

    /** Este módulo no admite edición (se gestiona por cuotas / Anular). Se intercepta la ruta
     *  /edit/{id} para que no caiga en 404: redirige al show con aviso. */
    def edit(Long id) {
        flash.message = "No se permite editar un anticipo. Gestione los anticipos desde el detalle o use Anular."
        flash.swalIcon = 'warning'
        flash.swalTitle = 'Operación no disponible'
        redirect(action: "show", id: id)
    }

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        params.max = Math.min(max ?: 10, 100)
        params.sort = params.sort ?: "id"
        params.order = params.order ?: "desc"

        // Buscador: nombre de cliente, nombre de empresa, nº de comprobante (cuotas) o nº de lote (detalle)
        def q = params.q?.trim()
        def deposito = usuarioActual.deposito

        // IDs de anticipos que matchean por nº de comprobante o por nº de lote (subconsultas)
        def idsAsociados = []
        if (q) {
            // El comprobante puede teclearse como "5" o "5/26" → tomar los dígitos iniciales
            def m = q =~ /^(\d+)/
            if (m) {
                idsAsociados += AnticipoCuota.executeQuery(
                        "select distinct c.anticipo.id from AnticipoCuota c where c.numeroComprobante = :n",
                        [n: m[0][1] as Integer])
            }
            idsAsociados += AnticipoDetalle.executeQuery(
                    "select distinct d.anticipo.id from AnticipoDetalle d where lower(d.lote) like :l",
                    [l: "%${q.toLowerCase()}%".toString()])
            idsAsociados = idsAsociados.unique()
        }

        def results = Anticipo.createCriteria().list(
                max: params.max, offset: params.offset ?: 0,
                sort: params.sort, order: params.order) {
            eq('deposito', deposito)
            if (q) {
                or {
                    ilike('nombreCliente', "%${q}%")
                    ilike('nombreEmpresa', "%${q}%")
                    if (idsAsociados) inList('id', idsAsociados)
                }
            }
        }

        [anticipoInstanceList: results, anticipoInstanceTotal: results.totalCount, q: q]
    }

    def create() {
        [anticipoInstance: new Anticipo(params)]
    }

    def save() {
        def anticipoInstance = new Anticipo(params)

        def lotesJSON = anticipoInstance.lotes ? new JSONArray(anticipoInstance.lotes) : null
        if (!lotesJSON || lotesJSON.length() == 0) {
            flash.message = "Debe seleccionar al menos un lote."
            flash.swalIcon = 'error'
            render(view: "create", model: [anticipoInstance: anticipoInstance])
            return
        }

        // No se puede anticipar sobre un lote que ya fue liquidado
        def loteLiquidado = primerLoteLiquidado(lotesJSON, anticipoInstance.tipoDeMineral)
        if (loteLiquidado) {
            flash.message = "No se puede emitir anticipo: el lote ${loteLiquidado} ya está LIQUIDADO."
            flash.swalIcon = 'error'
            render(view: "create", model: [anticipoInstance: anticipoInstance])
            return
        }

        anticipoInstance.totalAnticipos = 0
        if (!anticipoInstance.save(flush: true)) {
            render(view: "create", model: [anticipoInstance: anticipoInstance])
            return
        }

        // Cuotas iniciales emitidas en el alta (cada una con su comprobante)
        def montos = params.list('cuotaMonto')
        def fechas = params.list('cuotaFecha')
        montos.eachWithIndex { m, i ->
            if (m?.toString()?.trim()) {
                emitirCuota(anticipoInstance, m, i < fechas.size() ? fechas[i] : null)
            }
        }
        recalcularTotal(anticipoInstance)

        // Detalle de lotes y marcado de recepciones, en la MISMA transacción
        procesarLotes(anticipoInstance, lotesJSON)

        flash.message = message(code: 'default.created.message', args: [message(code: 'anticipo.label', default: 'Anticipo'), anticipoInstance.toString()])
        flash.swalIcon = 'success'
        redirect(action: "show", id: anticipoInstance.id)
    }

    /** Emitir un nuevo anticipo (cuota) sobre un encabezado existente. */
    def agregarCuota(Long id) {
        def anticipoInstance = Anticipo.get(id)
        if (!anticipoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipo.label', default: 'Anticipo'), id])
            redirect(action: "list")
            return
        }

        def lotesJSON = anticipoInstance.lotes ? new JSONArray(anticipoInstance.lotes) : new JSONArray()
        def loteLiquidado = primerLoteLiquidado(lotesJSON, anticipoInstance.tipoDeMineral)
        if (loteLiquidado) {
            flash.message = "No se puede emitir un nuevo anticipo: el lote ${loteLiquidado} ya está LIQUIDADO."
            flash.swalIcon = 'error'
            redirect(action: "show", id: id)
            return
        }

        if (!params.monto?.toString()?.trim()) {
            flash.message = "Debe indicar el importe del anticipo."
            flash.swalIcon = 'error'
            redirect(action: "show", id: id)
            return
        }

        def cuota = emitirCuota(anticipoInstance, params.monto, params.fecha)
        recalcularTotal(anticipoInstance)

        flash.message = "Anticipo emitido. Comprobante ${cuota.numeroComprobante}/${new java.text.SimpleDateFormat('yy').format(cuota.gestionMinera)}."
        flash.swalIcon = 'success'
        flash.swalTitle = 'Anticipo emitido'
        redirect(action: "show", id: id)
    }

    /** Anular un anticipo (cuota) emitido. */
    @Secured(['ROLE_ADMIN'])
    def eliminarCuota(Long id) {
        def cuota = AnticipoCuota.get(id)
        if (!cuota) {
            redirect(action: "list")
            return
        }
        def anticipo = cuota.anticipo
        anticipo.removeFromCuotas(cuota)
        cuota.delete(flush: true)
        recalcularTotal(anticipo)

        flash.message = "Anticipo (comprobante N° ${cuota.numeroComprobante}) anulado."
        flash.swalIcon = 'success'
        redirect(action: "show", id: anticipo.id)
    }

    def show(Long id) {
        def anticipoInstance = Anticipo.get(id)
        if (!anticipoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipo.label', default: 'Anticipo'), id])
            redirect(action: "list")
            return
        }

        def detalles = AnticipoDetalle.findAllByAnticipo(anticipoInstance)

        // Cobrado por lote: el anticipoPagable de cada AnticipoDetalle es lo descontado del anticipo
        // en la liquidación de ese lote. Mapa recepcionId(String) → monto cobrado, para la tabla de lotes.
        def cobradoPorLote = [:]
        detalles.each { d ->
            if (d.recepcionId != null) cobradoPorLote[d.recepcionId.toString()] = d.anticipoPagable ?: 0.0G
        }

        // ¿Todos los lotes liquidados? (ninguna recepción del anticipo sigue NO LIQUIDADO)
        def lotesPendientes = detalles.count { d -> RecepcionDeComplejo.get(d.recepcionId)?.estadoDelLote == 'NO LIQUIDADO' }
        def todosLiquidados = (detalles.size() > 0 && lotesPendientes == 0)

        // Saldo trasladado a ACFE = total del anticipo − lo efectivamente cobrado en los lotes.
        // Si todos los lotes están liquidados y queda diferencia, ese residual se registró como ACFE.
        def totalCobrado = detalles.sum { it.anticipoPagable ?: 0.0G } ?: 0.0G
        def residualTrasladado = (anticipoInstance.totalAnticipos ?: 0.0G) - totalCobrado
        def trasladadoACFE = todosLiquidados && residualTrasladado > 0

        [anticipoInstance: anticipoInstance, cobradoPorLote: cobradoPorLote, totalCobrado: totalCobrado,
         todosLiquidados: todosLiquidados, trasladadoACFE: trasladadoACFE, residualTrasladado: residualTrasladado]
    }

    /**
     * Eliminar el registro de Anticipo. El anticipo no se edita: para cambiarlo se anula
     * y se crea uno nuevo. La eliminación libera de anticipo a TODOS los lotes relacionados
     * (sus recepciones vuelven a SIN ANTICIPO).
     *
     * Solo se permite eliminar el registro completo si:
     *  - tiene a lo sumo UN anticipo emitido (con más de uno, se anulan individualmente
     *    desde el detalle vía eliminarCuota);
     *  - no fue pagado en caja;
     *  - ningún lote relacionado fue LIQUIDADO.
     */
    def delete(Long id) {
        def anticipoInstance = Anticipo.get(id)
        if (!anticipoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipo.label', default: 'Anticipo'), id])
            redirect(action: "list")
            return
        }

        // Solo eliminable si tiene a lo sumo un anticipo emitido; con varios se anulan uno a uno.
        if ((anticipoInstance.cuotas?.size() ?: 0) > 1) {
            flash.message = "No se puede eliminar: el registro tiene más de un anticipo emitido. Anule los anticipos individualmente desde el detalle."
            flash.swalIcon = 'error'
            redirect(action: "show", id: id)
            return
        }

        // No se puede eliminar si ya fue pagado en caja. (TODO caja: cuando exista el módulo de
        // caja/Egreso, validar también contra sus pagos, no solo contra totalPagado.)
        if ((anticipoInstance.totalPagado ?: 0) > 0) {
            flash.message = "No se puede eliminar: el anticipo ya tiene pagos registrados en caja."
            flash.swalIcon = 'error'
            redirect(action: "show", id: id)
            return
        }

        def lotesJSON = anticipoInstance.lotes ? new JSONArray(anticipoInstance.lotes) : new JSONArray()
        def loteLiquidado = primerLoteLiquidado(lotesJSON, anticipoInstance.tipoDeMineral)
        if (loteLiquidado) {
            flash.message = "No se puede anular el anticipo: el lote ${loteLiquidado} ya fue LIQUIDADO."
            flash.swalIcon = 'error'
            redirect(action: "show", id: id)
            return
        }

        try {
            // Liberar de anticipo a todas las recepciones relacionadas
            lotesJSON.each { item ->
                def recepcionId = item.getAt('recepcionId')?.toString()
                if (recepcionId) {
                    def recepcion = (anticipoInstance.tipoDeMineral == 'ORO') ?
                            RecepcionDeOro.get(recepcionId.toBigInteger()) :
                            RecepcionDeComplejo.get(recepcionId.toBigInteger())
                    if (recepcion) {
                        recepcion.estadoAnticipo = "SIN ANTICIPO"
                        recepcion.save(flush: true, failOnError: true)
                    }
                }
            }

            // Borrar el detalle (sin belongsTo → no cascada) y luego el anticipo (cuotas en cascada)
            AnticipoDetalle.findAllByAnticipo(anticipoInstance)*.delete(flush: true)
            anticipoInstance.delete(flush: true)

            flash.message = "Anticipo eliminado. Los lotes relacionados quedaron liberados (SIN ANTICIPO)."
            flash.swalIcon = 'success'
            flash.swalTitle = 'Anticipo eliminado'
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'anticipo.label', default: 'Anticipo'), id])
            flash.swalIcon = 'error'
            redirect(action: "show", id: id)
        }
    }

    def createReport = {
        def factura = PagoTransporte.get(params.id)
        def realPath = servletContext.getRealPath("/reports/images/")
        params.realPath=realPath+"/"
        chain(controller:'jasper',action:'index',model:[data:factura],params:params)
    }

    // ── Helpers de anticipos ────────────────────────────────────────────────

    /** Crea y persiste una cuota (anticipo emitido); el comprobante lo asigna su beforeInsert. */
    private AnticipoCuota emitirCuota(Anticipo anticipo, def monto, def fechaStr) {
        def cuota = new AnticipoCuota(
                monto: new BigDecimal(monto.toString().replace(',', '').trim()),
                fecha: parseFecha(fechaStr))
        anticipo.addToCuotas(cuota)
        cuota.save(flush: true, failOnError: true)   // flush por cuota: el correlativo ve las previas
        cuota
    }

    /** Recalcula el total de anticipos y el saldo por pagar a partir de las cuotas. */
    private void recalcularTotal(Anticipo anticipo) {
        anticipo.totalAnticipos = anticipo.cuotas ? anticipo.cuotas*.monto.sum() : 0G
        anticipo.totalPorPagar = anticipo.totalAnticipos - (anticipo.totalPagado ?: 0G)
        // Literal server-side (fuente única). NumeroALiteral ya produce "UN MIL …" para 1000-1999.
        anticipo.literalTotalAnticipos = new NumeroALiteral().Convertir(anticipo.totalAnticipos.toString(), true)
        anticipo.save(flush: true, failOnError: true)
    }

    private Date parseFecha(def s) {
        if (!s?.toString()?.trim()) return new Date()
        try {
            new java.text.SimpleDateFormat('dd/MM/yyyy').parse(s.toString().trim())
        } catch (ignored) {
            new Date()
        }
    }

    /** Devuelve el código del primer lote LIQUIDADO del conjunto, o null si ninguno lo está. */
    private String primerLoteLiquidado(JSONArray lotesJSON, String tipoDeMineral) {
        for (item in lotesJSON) {
            def recepcionId = item.getAt('recepcionId')?.toString()
            if (!recepcionId) continue
            def recepcion = (tipoDeMineral == 'ORO') ?
                    RecepcionDeOro.get(recepcionId.toBigInteger()) :
                    RecepcionDeComplejo.get(recepcionId.toBigInteger())
            if (recepcion?.estadoDelLote == 'LIQUIDADO') return item.getAt('lote')?.toString()
        }
        null
    }

    /** Crea el detalle por lote y marca cada recepción como CON ANTICIPO (misma transacción). */
    private void procesarLotes(Anticipo anticipo, JSONArray lotesJSON) {
        lotesJSON.each { item ->
            def recepcionId = item.getAt('recepcionId')
            new AnticipoDetalle(
                    anticipo: anticipo,
                    lote: item.getAt('lote'),
                    recepcionId: recepcionId,
                    nombreCliente: item.getAt('nombreCliente'),
                    nombreEmpresa: item.getAt('nombreEmpresa'),
                    fechaDeRecepcion: item.getAt('fechaDeRecepcion'),
                    tipoDeMineral: anticipo.tipoDeMineral,
                    pesoBruto: item.getAt('pesoBruto'),
                    tipoDeMaterial: item.getAt('tipoDeMaterial'),
                    estadoAnticipo: 'SIN PAGAR',
                    anticipoPagable: 0
            ).save(flush: true, failOnError: true)

            def recepcion = (anticipo.tipoDeMineral == 'ORO') ?
                    RecepcionDeOro.get(recepcionId.toString().toBigInteger()) :
                    RecepcionDeComplejo.get(recepcionId.toString().toBigInteger())
            if (recepcion) {
                recepcion.estadoAnticipo = "CON ANTICIPO"
                recepcion.save(flush: true, failOnError: true)
            }
        }
    }
}
