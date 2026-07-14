package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import grails.plugins.jasper.JasperExportFormat
import grails.plugins.jasper.JasperReportDef
import org.grails.web.json.JSONArray
import org.socymet.anticipos.Anticipo
import org.socymet.anticipos.AnticipoContraFuturaEntrega
import org.socymet.anticipos.AnticipoDetalle
import org.socymet.anticipos.EstadoDeCuenta
import org.socymet.anticipos.TipoMovimiento
import org.socymet.calidad.ControlCalidadComplejo
import org.socymet.cotizaciones.CotizacionDeDolar
import org.socymet.cotizaciones.TablaOrigenCotizacionesComplejo
import org.socymet.cotizaciones.TerminosDeContrato
import org.socymet.proveedor.Cliente
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.proveedor.EmpresaRetenciones
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.seguridad.SecUser
import org.socymet.utilidades.NumeroALiteral
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class LiquidacionDeComplejoController {
    def springSecurityService
    def jasperService
    def liquidacionComplejoCalculoService

    static allowedMethods = [save: "POST", anular: "POST"]

    /** Este módulo no admite edición (se revierte con Anular). Se intercepta la ruta /edit/{id}
     *  para que no caiga en 404: redirige al show con aviso. */
    def edit(Long id) {
        flash.message = "No se permite editar una liquidación. Use Anular si necesita revertirla."
        flash.swalIcon = 'warning'
        flash.swalTitle = 'Operación no disponible'
        redirect(action: "show", id: id)
    }

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.sort = params.sort ?: "id"
        params.order = params.order ?: "desc"

        // Buscador: N° liquidación, lote, nombre de cliente o nombre de empresa
        def q = params.q?.trim()
        def numero = (q && q.isInteger()) ? (q as Integer) : null

        def results = LiquidacionDeComplejo.createCriteria().list(
                max: params.max, offset: params.offset ?: 0,
                sort: params.sort, order: params.order) {
            if (q) {
                or {
                    ilike('lote', "%${q}%")
                    ilike('nombreCliente', "%${q}%")
                    ilike('nombreEmpresa', "%${q}%")
                    if (numero != null) eq('numeroLiquidacionComplejo', numero)
                }
            }
        }
        [liquidacionDeComplejoInstanceList: results, liquidacionDeComplejoInstanceCount: results.totalCount, q: q]
    }

    @Secured(['ROLE_ADMIN','ROLE_LIQUIDACION'])
    def create() {
        // Lotes pendientes de liquidar (para el selector del form)
        def recepcionesDisponibles = RecepcionDeComplejo.findAllByEstadoDelLote("NO LIQUIDADO", [sort: 'id', order: 'desc'])

        def liquidacionDeComplejoInstance = new LiquidacionDeComplejo(params)
        def retencionesEmpresa = []
        def anticipoLote = null

        def rec = params.recepcionId ? RecepcionDeComplejo.get(params.recepcionId) : null
        if (rec) {
            liquidacionDeComplejoInstance = prefillDesdeRecepcion(rec)
            retencionesEmpresa = EmpresaRetenciones.findAllByEmpresa(rec.empresa)

            // Anticipo asociado al lote: si cubre un solo lote se salda completo; si varios, fracción
            def ad = AnticipoDetalle.findByRecepcionId(rec.id)
            if (ad && ad.anticipo) {
                def ant = ad.anticipo
                def nLotes = AnticipoDetalle.countByAnticipo(ant)
                // 'ultimo' = este es el único lote del anticipo aún NO liquidado (el actual incluido).
                // Si al liquidarlo no se cobra el saldo total, ese residual se trasladará a un ACFE.
                def pendientes = AnticipoDetalle.findAllByAnticipo(ant).count { d -> RecepcionDeComplejo.get(d.recepcionId)?.estadoDelLote == 'NO LIQUIDADO' }
                anticipoLote = [totalPorPagar: ant.totalPorPagar ?: 0.0G, lotes: nLotes, unico: (nLotes == 1), ultimo: (pendientes <= 1)]
                liquidacionDeComplejoInstance.totalAnticiposContraEntrega = (nLotes == 1) ? (ant.totalPorPagar ?: 0.0G) : 0.0G
            }
        }

        [liquidacionDeComplejoInstance: liquidacionDeComplejoInstance,
         recepcionesDisponibles: recepcionesDisponibles,
         retencionesEmpresa: retencionesEmpresa,
         anticipoLote: anticipoLote]
    }

    /** Construye una liquidación precargada con los datos capturados en el lote (recepción). */
    private LiquidacionDeComplejo prefillDesdeRecepcion(RecepcionDeComplejo rec) {
        def cd = rec.cotizacionDiariaDeMinerales
        def cq = rec.cotizacionQuincenalDeMinerales
        def al = rec.alicuota
        def dol = rec.cotizacionDeDolar ?: CotizacionDeDolar.findByActivo(1)
        def cc = ControlCalidadComplejo.findByRecepcionDeComplejo(rec)
        def prom = { a, b -> (b != null && b > 0) ? (((a ?: 0.0G) + b) / 2) : (a ?: 0.0G) }

        // Saldo anterior INFORMATIVO: último saldo del estado de cuenta del cliente (deuda vigente).
        // No se descuenta del líquido; el cobro de una fracción se hace con totalAnticiposContraFuturaEntrega.
        def ultimoSaldoCliente = rec.cliente ? (EstadoDeCuenta.findAllByCliente(rec.cliente, [sort: 'id', order: 'desc'])[0]?.saldo ?: 0.0G) : 0.0G

        new LiquidacionDeComplejo(
            recepcionDeComplejo: rec, cliente: rec.cliente, empresa: rec.empresa, deposito: rec.deposito,
            nombreCliente: rec.cliente?.nombre, nombreEmpresa: rec.empresa?.nombreDeEmpresa, nombreDeposito: rec.deposito?.toString(),
            lote: rec.codigoLote, tipoDeMineral: rec.tipoDeMineral,
            fechaRecepcion: rec.fechaDeRecepcion,
            fechaDeRecepcion: rec.fechaDeRecepcion ? rec.fechaDeRecepcion.format('dd/MM/yyyy') : null,
            cantidadSacos: rec.cantidadSacos,
            cantidadDeSacos: (rec.cantidadSacos != null ? rec.cantidadSacos.toString() : (rec.cantidadDeSacos ?: '0')),
            pesoBruto: rec.pesoBruto,
            cotizacionDiariaDeZinc: cd?.zinc, cotizacionDiariaDePlomo: cd?.plomo, cotizacionDiariaDePlata: cd?.plata,
            cotizacionQuincenalDeZinc: cq?.zinc, cotizacionQuincenalDePlomo: cq?.plomo, cotizacionQuincenalDePlata: cq?.plata,
            alicuotaDeZinc: al?.zinc, alicuotaDePlomo: al?.plomo, alicuotaDePlata: al?.plata,
            tipoDeCambioOficial: dol?.tipoDeCambioOficial, tipoDeCambioComercial: dol?.tipoDeCambioComercial,
            porcentajeZincPromexbol: cc?.porcentajeZincPromexbol, porcentajePlomoPromexbol: cc?.porcentajePlomoPromexbol, porcentajePlataPromexbol: cc?.porcentajePlataPromexbol,
            porcentajeHumedadPromexbol: cc?.porcentajeHumedadPromexbol, porcentajeMermaPromexbol: cc?.porcentajeMermaPromexbol ?: 1.0G,
            porcentajeZincCliente: cc?.porcentajeZincCliente, porcentajePlomoCliente: cc?.porcentajePlomoCliente, porcentajePlataCliente: cc?.porcentajePlataCliente,
            porcentajeHumedadCliente: cc?.porcentajeHumedadCliente, porcentajeMermaCliente: cc?.porcentajeMermaCliente,
            porcentajeZincFinal: prom(cc?.porcentajeZincPromexbol, cc?.porcentajeZincCliente),
            porcentajePlomoFinal: prom(cc?.porcentajePlomoPromexbol, cc?.porcentajePlomoCliente),
            porcentajePlataFinal: prom(cc?.porcentajePlataPromexbol, cc?.porcentajePlataCliente),
            porcentajeHumedadFinal: cc?.porcentajeHumedadPromexbol,
            porcentajeMermaFinal: cc?.porcentajeMermaPromexbol ?: 1.0G,
            modoValoracion: 'MANUAL', valorPorTonelada: 0.0G,
            bonoCalidad: 0.0G, bonoTransporte: 0.0G, bonoLealtad: 0.0G, bonoIncentivo: 0.0G,
            totalAnticiposContraEntrega: 0.0G, totalAnticiposContraFuturaEntrega: 0.0G, saldoAnterior: ultimoSaldoCliente
        )
    }

    /** Modelo para re-renderizar create (selector de lotes, retenciones de empresa, info de anticipo). */
    private Map modeloCreate(LiquidacionDeComplejo liq) {
        def anticipoLote = null
        if (liq.recepcionDeComplejo) {
            def ad = AnticipoDetalle.findByRecepcionId(liq.recepcionDeComplejo.id)
            if (ad?.anticipo) {
                def nLotes = AnticipoDetalle.countByAnticipo(ad.anticipo)
                def pendientes = AnticipoDetalle.findAllByAnticipo(ad.anticipo).count { d -> RecepcionDeComplejo.get(d.recepcionId)?.estadoDelLote == 'NO LIQUIDADO' }
                anticipoLote = [totalPorPagar: ad.anticipo.totalPorPagar ?: 0.0G, lotes: nLotes, unico: (nLotes == 1), ultimo: (pendientes <= 1)]
            }
        }
        [liquidacionDeComplejoInstance: liq,
         recepcionesDisponibles: RecepcionDeComplejo.findAllByEstadoDelLote("NO LIQUIDADO", [sort: 'id', order: 'desc']),
         retencionesEmpresa: liq.empresa ? EmpresaRetenciones.findAllByEmpresa(liq.empresa) : [],
         anticipoLote: anticipoLote]
    }

    @Secured(['ROLE_ADMIN','ROLE_LIQUIDACION'])
    def save() {
        def liquidacionDeComplejoInstance = new LiquidacionDeComplejo(params)

        // Retenciones (tabla del form → hijos LiquidacionDeComplejoRetenciones)
        def descs = params.list('retDescripcion'), tipos = params.list('retTipo'),
            asigs = params.list('retAsignacion'), cants = params.list('retCantidad'), units = params.list('retUnidad')
        descs.eachWithIndex { d, i ->
            if (d?.toString()?.trim()) {
                liquidacionDeComplejoInstance.addToDetalleRetenciones(new LiquidacionDeComplejoRetenciones(
                    codigo: i + 1, descripcion: d, tipoDeRetencion: tipos[i] ?: 'OTRA',
                    asignacionDelDescuento: asigs[i] ?: 'VNV',
                    cantidadDescuento: (cants[i]?.toString()?.isBigDecimal() ? cants[i].toBigDecimal() : 0.0G),
                    unidadDeDescuento: units[i] ?: '%', monto: 0.0G))
            }
        }

        // Validación: el descuento de anticipo no puede superar el total por pagar del anticipo del lote
        def adq = liquidacionDeComplejoInstance.recepcionDeComplejo ? AnticipoDetalle.findByRecepcionId(liquidacionDeComplejoInstance.recepcionDeComplejo.id) : null
        if (adq?.anticipo && (liquidacionDeComplejoInstance.totalAnticiposContraEntrega ?: 0) > (adq.anticipo.totalPorPagar ?: 0)) {
            flash.message = "El descuento de anticipo (Bs ${liquidacionDeComplejoInstance.totalAnticiposContraEntrega}) supera el total por pagar del anticipo del lote (Bs ${adq.anticipo.totalPorPagar})."
            flash.swalIcon = 'error'
            render(view: "create", model: modeloCreate(liquidacionDeComplejoInstance))
            return
        }

        // Recálculo autoritativo en backend (fuente única; no se confía en el form)
        liquidacionComplejoCalculoService.recalcular(liquidacionDeComplejoInstance)

        if (!liquidacionDeComplejoInstance.save(flush: true)) {
            render(view: "create", model: modeloCreate(liquidacionDeComplejoInstance))
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionDeComplejo.label', default: 'LiquidacionDeComplejo'), liquidacionDeComplejoInstance.lote])
        redirect(action: "show", id: liquidacionDeComplejoInstance.id)
    }

    def show(Long id) {
        def liquidacionDeComplejoInstance = LiquidacionDeComplejo.get(id)
        if (!liquidacionDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeComplejo.label', default: 'LiquidacionDeComplejo'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDeComplejoInstance: liquidacionDeComplejoInstance]
    }

    /** Comprobante imprimible (HTML) de la liquidación, con todos los campos del rediseño. */
    def imprimir(Long id) {
        def liquidacionDeComplejoInstance = LiquidacionDeComplejo.get(id)
        if (!liquidacionDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeComplejo.label', default: 'LiquidacionDeComplejo'), id])
            redirect(action: "list"); return
        }
        [liquidacionDeComplejoInstance: liquidacionDeComplejoInstance]
    }

    /**
     * Comprobante BORRADOR (no persistido) con los datos actuales del formulario, para la etapa de
     * negociación con el cliente. Reutiliza el MISMO pipeline que save() (bind + retenciones +
     * recálculo autoritativo) pero NO guarda: arma una instancia transitoria y renderiza el mismo
     * diseño de comprobante marcado como BORRADOR. No expira la sesión (ver keepAlive del form).
     */
    def imprimirBorrador() {
        def liquidacionDeComplejoInstance = new LiquidacionDeComplejo(params)

        // Retenciones (tabla del form → hijos transitorios), idéntico a save()
        def descs = params.list('retDescripcion'), tipos = params.list('retTipo'),
            asigs = params.list('retAsignacion'), cants = params.list('retCantidad'), units = params.list('retUnidad')
        descs.eachWithIndex { d, idx ->
            if (d?.toString()?.trim()) {
                liquidacionDeComplejoInstance.addToDetalleRetenciones(new LiquidacionDeComplejoRetenciones(
                    codigo: idx + 1, descripcion: d, tipoDeRetencion: tipos[idx] ?: 'OTRA',
                    asignacionDelDescuento: asigs[idx] ?: 'VNV',
                    cantidadDescuento: (cants[idx]?.toString()?.isBigDecimal() ? cants[idx].toBigDecimal() : 0.0G),
                    unidadDeDescuento: units[idx] ?: '%', monto: 0.0G))
            }
        }

        // Recálculo autoritativo (misma fuente que save), pero SIN persistir.
        liquidacionComplejoCalculoService.recalcular(liquidacionDeComplejoInstance)
        // El borrador aún no tiene fecha/número de liquidación (se asignan al guardar): muestra la fecha de hoy.
        if (liquidacionDeComplejoInstance.fechaDeLiquidacion == null) liquidacionDeComplejoInstance.fechaDeLiquidacion = new Date()

        render(view: 'imprimir', model: [liquidacionDeComplejoInstance: liquidacionDeComplejoInstance, borrador: true])
    }

    /**
     * Mantiene viva la sesión mientras el formulario de liquidación está abierto (negociación).
     * El form la invoca periódicamente; cada petición autenticada renueva el lastAccessedTime,
     * evitando que la sesión expire durante los minutos de negociación con el cliente.
     */
    def keepAlive() {
        render(text: 'ok', contentType: 'text/plain')
    }

    /**
     * Anular la liquidación. No se edita ni elimina: la anulación REVIERTE sus efectos
     * (lote→NO LIQUIDADO, retenciones por pagar, descuento de anticipo, ACFE por saldo
     * negativo y asientos del estado de cuenta) en la misma transacción y marca anulado=true.
     */
    @Secured(['ROLE_ADMIN'])
    def anular(Long id) {
        def liq = LiquidacionDeComplejo.get(id)
        if (!liq) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeComplejo.label', default: 'LiquidacionDeComplejo'), id])
            redirect(action: "list"); return
        }
        if (liq.anulado) {
            flash.message = "La liquidación ya está anulada."
            flash.swalIcon = 'error'; redirect(action: "show", id: id); return
        }

        def rec = liq.recepcionDeComplejo
        // D11: no se puede anular una liquidación cuyo lote esté en un compósito (provisional o
        // definitivo). Hay que sacar el lote del compósito primero. La marca de reserva es la fuente.
        if (rec && rec.nombreComposito && rec.nombreComposito != '-') {
            flash.message = "No se puede anular: el lote ${rec.codigoLote} está en el compósito ${rec.nombreComposito}. Quítelo del compósito primero."
            flash.swalIcon = 'error'; redirect(action: "show", id: id); return
        }

        def cliente = rec?.cliente

        // IMPORTANTE: todos los cambios de estado/flags se hacen con HQL executeUpdate (no con .save()).
        // Motivo: hacer rec.save() re-dispara el beforeValidate de RecepcionDeComplejo, que recalcula
        // pesoBruto = pesoNeto − pesoTara y aplica los validadores (taraExcedeBruto, pesoBruto min:0.01).
        // Si la recepción tiene datos límite/heredados, esa validación falla, y como 'anular' es
        // @Transactional con failOnError, lanzaba excepción y hacía ROLLBACK de TODA la anulación
        // (por eso "no pasaba nada": ni reversión, ni estado, ni anulado). executeUpdate actualiza
        // solo las columnas indicadas, sin instanciar ni validar el objeto.

        // 1. Liberar el lote (vuelve a NO LIQUIDADO) sin re-validar la recepción
        if (rec) {
            RecepcionDeComplejo.executeUpdate(
                "update RecepcionDeComplejo set estadoDelLote = :estado where id = :id",
                [estado: 'NO LIQUIDADO', id: rec.id])
        }

        // 2. Eliminar las retenciones por pagar (obligaciones pendientes); el detalle queda como histórico
        RetencionPorPagarComplejo.executeUpdate(
            "delete RetencionPorPagarComplejo where liquidacionId = :id", [id: liq.id])

        // ACFE generados por ESTA liquidación (pueden ser 2: por saldo negativo del líquido y/o por
        // traslado del saldo no cobrado del anticipo contra entrega). El de "traslado" se identifica por
        // su compromiso; su importe es el residual que pasó del anticipo a deuda del cliente y que, al
        // anular, debe devolverse al anticipo (además de la fracción cobrada).
        def acfes = AnticipoContraFuturaEntrega.findAllByLiquidacionId(liq.id).findAll { !it.anulado }
        def acfeResidual = acfes.find { it.compromiso?.startsWith('SALDO NO COBRADO DEL ANTICIPO CONTRA ENTREGA') }
        def residualAnticipo = acfeResidual?.importe ?: 0.0G

        // 3. Revertir el efecto de la liquidación sobre el anticipo contra entrega: se devuelve tanto la
        //    fracción cobrada (totalAnticiposContraEntrega) como el residual que se trasladó al ACFE.
        //    Se clampa a >= 0 por si hubiera drift de datos.
        def ad = rec ? AnticipoDetalle.findByRecepcionId(rec.id) : null
        def cobroAnticipo = liq.totalAnticiposContraEntrega ?: 0.0G
        def devolverAnticipo = cobroAnticipo + residualAnticipo
        if (ad?.anticipo && devolverAnticipo > 0) {
            def ant = ad.anticipo
            def nuevoPagado = ((ant.totalPagado ?: 0.0G) - devolverAnticipo).max(0.0G)
            def nuevoPorPagar = ((ant.totalPorPagar ?: 0.0G) + devolverAnticipo).max(0.0G)
            Anticipo.executeUpdate(
                "update Anticipo set totalPagado = :pagado, totalPorPagar = :porPagar where id = :id",
                [pagado: nuevoPagado, porPagar: nuevoPorPagar, id: ant.id])
            // El detalle del lote vuelve a quedar sin pago
            AnticipoDetalle.executeUpdate(
                "update AnticipoDetalle set anticipoPagable = 0, estadoAnticipo = :estado where id = :id",
                [estado: 'SIN PAGAR', id: ad.id])
            // La recepción vuelve a 'CON ANTICIPO' (sin re-validar pesos)
            RecepcionDeComplejo.executeUpdate(
                "update RecepcionDeComplejo set estadoAnticipo = :estado where id = :id",
                [estado: 'CON ANTICIPO', id: rec.id])
        }

        // 4. Anular TODOS los ACFE generados por esta liquidación + reversa en el ledger
        acfes.each { acfe ->
            asientoLedger(acfe.cliente, acfe.empresa, acfe.numeroAnticipo, 0.0G, acfe.importe,
                "ANULACION DE ACFE POR ANULACION DE LIQUIDACION DEL LOTE ${liq.lote}",
                TipoMovimiento.ANTICIPO_FUTURA_ENTREGA, acfe.id)
            AnticipoContraFuturaEntrega.executeUpdate(
                "update AnticipoContraFuturaEntrega set anulado = true where id = :id", [id: acfe.id])
        }

        // 5. Revertir el pago de un ACFE previo durante la liquidación
        if (cliente && (liq.totalAnticiposContraFuturaEntrega ?: 0) > 0) {
            asientoLedger(cliente, liq.empresa, 0, liq.totalAnticiposContraFuturaEntrega, 0.0G,
                "REVERSION DE PAGO DE ACFE POR ANULACION DE LIQUIDACION DEL LOTE ${liq.lote}",
                TipoMovimiento.LIQUIDACION_COMPLEJO, liq.id)
        }

        // 6. Marcar la liquidación como anulada (HQL, sin disparar beforeUpdate/afterUpdate)
        LiquidacionDeComplejo.executeUpdate(
            "update LiquidacionDeComplejo set anulado = true where id = :id", [id: liq.id])

        flash.message = "Liquidación N° ${liq.numeroLiquidacionComplejo} anulada. El lote ${liq.lote} volvió a NO LIQUIDADO."
        flash.swalIcon = 'success'; flash.swalTitle = 'Liquidación anulada'
        redirect(action: "show", id: id)
    }

    /** Crea un asiento en el estado de cuenta del cliente con el saldo recalculado. */
    private void asientoLedger(cliente, empresa, Integer numeroComprobante, BigDecimal debe, BigDecimal haber, String detalle, TipoMovimiento tipo, Long origenId) {
        def ultimo = EstadoDeCuenta.findAllByCliente(cliente, [sort: "id", order: "desc"])[0]
        def ultimoSaldo = ultimo?.saldo ?: 0.0G
        new EstadoDeCuenta(
            cliente: cliente, empresa: empresa, ci: cliente.ci, nombre: cliente.nombre,
            nombreEmpresa: empresa?.nombreDeEmpresa, fecha: new Date(),
            numeroComprobante: numeroComprobante, detalle: detalle,
            debe: debe, haber: haber, saldo: ultimoSaldo + debe - haber,
            liquidacionId: 0, tipoMovimiento: tipo, origenId: origenId
        ).save(failOnError: true)
    }

    def eliminarDeConjunto(Long id) {
        def liquidacionDeComplejoInstance = LiquidacionDeComplejo.get(id)
        if (!liquidacionDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeComplejo.label', default: 'LiquidacionDeComplejo'), id])
            redirect(action: "list")
            return
        }

        try {
            System.out.println("*** LOCALIZADO LOTE: ${liquidacionDeComplejoInstance.lote} DEL CONJUNTO: ${liquidacionDeComplejoInstance.conjuntoComplejo}")
            liquidacionDeComplejoInstance.conjuntoComplejo="-"
            liquidacionDeComplejoInstance.save(failOnError: true)
            flash.message = "El Lote ha sido eliminado del Conjunto anteriormente asignado."
            redirect(action: "show", id: id)
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDeComplejo.label', default: 'LiquidacionDeComplejo'), id])
            redirect(action: "show", id: id)
        }
    }

    def cancelacionDeLote(Long id) {
        def liquidacionDeComplejoInstance = LiquidacionDeComplejo.get(id)
        if (!liquidacionDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeComplejo.label', default: 'LiquidacionDeComplejo'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionDeComplejoInstance.fechaDeCancelacion=new java.util.Date()
            liquidacionDeComplejoInstance.save(failOnError: true)
            flash.message = "Se ha establecido la Fecha de Cancelacion del Lote"
            redirect(action: "show", id: id)
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDeComplejo.label', default: 'LiquidacionDeComplejo'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporte = {
        def realPath = servletContext.getRealPath("/reports/images/")
        params.realPath = realPath+"/"
        params.SUBREPORT_DIR = "${servletContext.getRealPath('/reports')}/"
        chain(controller:'jasper',action:'index',params:params)
    }

    def crearReporteGrupal2 = {
        Map reportParams = [:]
        def millis = params.millis.toBigDecimal()
        def realPath = servletContext.getRealPath("/reports/images/")
        reportParams.put("millis",millis)
        reportParams.put("realPath",realPath+"/")
        reportParams.put("SUBREPORT_DIR","${servletContext.getRealPath('/reports')}/")

        def reportDef = new JasperReportDef(name:'liquidacion_grupal_complejo.jasper',fileFormat:JasperExportFormat.PDF_FORMAT,parameters: reportParams)
        byte[] bytes
        bytes = jasperService.generateReport(reportDef).toByteArray()
        response.addHeader("Content-Disposition", 'attachment; filename="liquidacion_grupal_complejo.pdf"')
        response.contentType = 'application/pdf'
        response.outputStream << bytes
        response.outputStream.flush()

        //return null
//        render(file: bytes, fileName: "book.pdf",contentType: 'application/pdf')
//        render [:] as JSON
    }

    def crearReporteGrupal = {
        def realPath = servletContext.getRealPath("/reports/images/")
        params.realPath = realPath+"/"
        params.SUBREPORT_DIR = "${servletContext.getRealPath('/reports')}/"
        chain(controller:'jasper',action:'index',params:params)
    }

    def liquidacionesJSON() {
        def lote = params.term.toString()
        def liquidacionDeComplejos = LiquidacionDeComplejo.findAllByLoteLikeAndConjuntoComplejo("%${lote}%","-")
        def liquidacionesList = []
        liquidacionDeComplejos.each { liquidacion ->
            def liquidacionMap = [:]
            liquidacionMap.put("liquidacionId", liquidacion.id)
            liquidacionMap.put("label", liquidacion.lote)
            liquidacionMap.put("value", liquidacion.lote)
            liquidacionMap.put("nombreCliente", liquidacion.nombreCliente)
            liquidacionMap.put("nombreEmpresa", liquidacion.nombreEmpresa)
            liquidacionMap.put("fechaDeRecepcion", liquidacion.fechaDeRecepcion)
            liquidacionMap.put("fechaDeLiquidacion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(liquidacion.fechaDeLiquidacion))
            liquidacionMap.put("kilosNetosSecos", liquidacion.kilosNetosSecos)
            liquidacionMap.put("porcentajeZinc", liquidacion.porcentajeZinc)
            liquidacionMap.put("porcentajePlomo", liquidacion.porcentajePlomo)
            liquidacionMap.put("porcentajePlata", liquidacion.porcentajePlata)
            liquidacionesList.add(liquidacionMap)
        }
        render liquidacionesList as JSON
    }

    def lotesEnConjuntoJSON() {
        def lote = params.term.toString()
        def liquidacionDeComplejos = LiquidacionDeComplejo.findAllByLoteLikeAndConjuntoComplejoNotEqual("%${lote}%","-")
        def liquidacionesList = []
        liquidacionDeComplejos.each { liquidacion ->
            def liquidacionMap = [:]
            liquidacionMap.put("liquidacionId", liquidacion.id)
            liquidacionMap.put("label", liquidacion.lote)
            liquidacionMap.put("value", liquidacion.lote)
            liquidacionMap.put("nombreCliente", liquidacion.nombreCliente)
            liquidacionMap.put("nombreEmpresa", liquidacion.nombreEmpresa)
            liquidacionMap.put("fechaDeRecepcion", liquidacion.fechaDeRecepcion)
            liquidacionMap.put("fechaDeLiquidacion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(liquidacion.fechaDeLiquidacion))
            liquidacionMap.put("kilosNetosSecos", liquidacion.kilosNetosSecos)
            liquidacionMap.put("porcentajeZinc", liquidacion.porcentajeZinc)
            liquidacionMap.put("porcentajePlomo", liquidacion.porcentajePlomo)
            liquidacionMap.put("porcentajePlata", liquidacion.porcentajePlata)
            liquidacionMap.put("conjuntoComplejo", liquidacion.conjuntoComplejo)
            liquidacionesList.add(liquidacionMap)
        }
        render liquidacionesList as JSON
    }

    def liquidar = {
        def s = "[${params.lote.toString()}]"
        def millis = params.millis.toBigDecimal()
        def loteAux = new JSONArray(s)
        def lotecito = loteAux[0]
        log.error("*************** LOTE A LIQUIDAR: ${loteAux}")
        log.error("${loteAux[0].getAt("lote")}: ${loteAux[0].getAt("totalLiquidoPagable")}")
        log.error("*************** millis: ${millis}")

        def recepcionDeComplejo = RecepcionDeComplejo.get(lotecito.getAt("recepcionId").toString().toLong())
        def deposito = Deposito.get(lotecito.getAt("depositoId").toString().toLong())
        def empresa  = Empresa.get(lotecito.getAt("empresaId").toString().toLong())
        def lote = lotecito.getAt("lote")
        def tipoDeMineral = lotecito.getAt("tipoDeMineral")
        def nombreDeposito  = lotecito.getAt("nombreDeposito")
        def nombreCliente = lotecito.getAt("nombreCliente")
        def nombreEmpresa = lotecito.getAt("nombreEmpresa")
        def fechaDeRecepcion = lotecito.getAt("fechaDeRecepcion")
        def cantidadDeSacos = lotecito.getAt("cantidadDeSacos")
        def estadoDelLote = lotecito.getAt("estadoDelLote")
        def pesoBruto = lotecito.getAt("pesoBruto").toString().toBigDecimal()
        def cotizacionDiariaDeZinc = lotecito.getAt("cotizacionDiariaDeZinc").toString().toBigDecimal()
        def cotizacionQuincenalDeZinc = lotecito.getAt("cotizacionQuincenalDeZinc").toString().toBigDecimal()
        def alicuotaDeZinc = lotecito.getAt("alicuotaDeZinc").toString().toBigDecimal()
        def cotizacionDiariaDePlomo = lotecito.getAt("cotizacionDiariaDePlomo").toString().toBigDecimal()
        def cotizacionQuincenalDePlomo = lotecito.getAt("cotizacionQuincenalDePlomo").toString().toBigDecimal()
        def alicuotaDePlomo = lotecito.getAt("alicuotaDePlomo").toString().toBigDecimal()
        def cotizacionDiariaDePlata = lotecito.getAt("cotizacionDiariaDePlata").toString().toBigDecimal()
        def cotizacionQuincenalDePlata = lotecito.getAt("cotizacionQuincenalDePlata").toString().toBigDecimal()
        def alicuotaDePlata = lotecito.getAt("alicuotaDePlata").toString().toBigDecimal()
        def tipoDeCambioOficial = lotecito.getAt("tipoDeCambioOficial").toString().toBigDecimal()
        def tipoDeCambioComercial = lotecito.getAt("tipoDeCambioComercial").toString().toBigDecimal()
        def fechaDeLiquidacion = new java.util.Date()
        def kilosNetosHumedos = lotecito.getAt("kilosNetosHumedos").toString().toBigDecimal()
        def kilosNetosSecos = lotecito.getAt("kilosNetosSecos").toString().toBigDecimal()
        def dolarPuntoZinc = 0
        def dolarPuntoPlomo = 0
        def dolarPuntoPlata = 0
        def porcentajeZincPromexbol  = lotecito.getAt("porcentajeZincPromexbol").toString().toBigDecimal()
        def porcentajePlomoPromexbol  = lotecito.getAt("porcentajePlomoPromexbol").toString().toBigDecimal()
        def porcentajePlataPromexbol  = lotecito.getAt("porcentajePlataPromexbol").toString().toBigDecimal()
        def porcentajeHumedadPromexbol  = lotecito.getAt("porcentajeHumedadPromexbol").toString().toBigDecimal()
        def porcentajeMermaPromexbol  = lotecito.getAt("porcentajeMermaPromexbol").toString().toBigDecimal()
        def porcentajeZincCliente  = lotecito.getAt("porcentajeZincCliente").toString().toBigDecimal()
        def porcentajePlomoCliente  = lotecito.getAt("porcentajePlomoCliente").toString().toBigDecimal()
        def porcentajePlataCliente  = lotecito.getAt("porcentajePlataCliente").toString().toBigDecimal()
        def porcentajeHumedadCliente  = lotecito.getAt("porcentajeHumedadCliente").toString().toBigDecimal()
        def porcentajeMermaCliente  = lotecito.getAt("porcentajeMermaCliente").toString().toBigDecimal()
        def porcentajeZincFinal = lotecito.getAt("porcentajeZincFinal").toString().toBigDecimal()
        def porcentajePlomoFinal = lotecito.getAt("porcentajePlomoFinal").toString().toBigDecimal()
        def porcentajePlataFinal = lotecito.getAt("porcentajePlataFinal").toString().toBigDecimal()
        def porcentajeHumedadFinal  = lotecito.getAt("porcentajeHumedadFinal").toString().toBigDecimal()
        def porcentajeMermaFinal  = lotecito.getAt("porcentajeMermaFinal").toString().toBigDecimal()
        def modoValoracion = lotecito.getAt("modoValoracion")
        def tablaComplejo = TablaOrigenCotizacionesComplejo.get(lotecito.getAt("tablaComplejoId").toString().toLong())
        def terminosDeContrato = TerminosDeContrato.get(lotecito.getAt("terminosDeContratoId").toString().toLong())
        def kilosFinosZinc = lotecito.getAt("kilosFinosZinc").toString().toBigDecimal()
        def kilosFinosPlomo = lotecito.getAt("kilosFinosPlomo").toString().toBigDecimal()
        def kilosFinosPlata = lotecito.getAt("kilosFinosPlata").toString().toBigDecimal()
        def librasFinasDeZinc = lotecito.getAt("librasFinasDeZinc").toString().toBigDecimal()
        def librasFinasDePlomo = lotecito.getAt("librasFinasDePlomo").toString().toBigDecimal()
        def onzasTroyDePlata = lotecito.getAt("onzasTroyDePlata").toString().toBigDecimal()
        def valorOficialBrutoDeZinc = lotecito.getAt("valorOficialBrutoDeZinc").toString().toBigDecimal()
        def valorOficialBrutoDePlomo = lotecito.getAt("valorOficialBrutoDePlomo").toString().toBigDecimal()
        def valorOficialBrutoDePlata = lotecito.getAt("valorOficialBrutoDePlata").toString().toBigDecimal()
        def valorOficialBrutoDeZincEnBolivianos = lotecito.getAt("valorOficialBrutoDeZincEnBolivianos").toString().toBigDecimal()
        def valorOficialBrutoDePlomoEnBolivianos = lotecito.getAt("valorOficialBrutoDePlomoEnBolivianos").toString().toBigDecimal()
        def valorOficialBrutoDePlataEnBolivianos = lotecito.getAt("valorOficialBrutoDePlataEnBolivianos").toString().toBigDecimal()
        def valorOficialBruto = lotecito.getAt("valorOficialBruto").toString().toBigDecimal()
        def valorPorTonelada = lotecito.getAt("valorPorTonelada").toString().toBigDecimal()
        def margen  = lotecito.getAt("margen").toString().toBigDecimal()
        def regaliaMinera = lotecito.getAt("regaliaMinera").toString().toBigDecimal()
        def retenciones = lotecito.getAt("retenciones")
        def valorNetoMineral = lotecito.getAt("valorNetoMineral").toString().toBigDecimal()
        def valorNetoMineralEnBolivianos = lotecito.getAt("valorNetoMineralEnBolivianos").toString().toBigDecimal()
        def bonoCalidad = lotecito.getAt("bonoCalidad").toString().toBigDecimal()
        def bonoIncentivo = lotecito.getAt("bonoIncentivo").toString().toBigDecimal()
        def valorDeCompra = lotecito.getAt("valorDeCompra").toString().toBigDecimal()
        def totalRetenciones = lotecito.getAt("totalRetenciones").toString().toBigDecimal()
        def totalPagado = lotecito.getAt("totalPagado").toString().toBigDecimal()
        def anticipoPorPagar  = lotecito.getAt("anticipoPorPagar").toString().toBigDecimal()
        def totalAnticiposContraEntrega = lotecito.getAt("totalAnticiposContraEntrega").toString().toBigDecimal()
        def totalAnticiposContraFuturaEntrega = lotecito.getAt("totalAnticiposContraFuturaEntrega").toString().toBigDecimal()
        def adelantoPorLiquidacionProvisional = 0
        //por 1ra vez liquidado ambas variables tienen el mismo valor y la diferencia es 0
        def totalLiquidoPagable = lotecito.getAt("totalLiquidoPagable").toString().toBigDecimal()

        def conversor = new NumeroALiteral()
        def totalLiquidoPagableLiteral = conversor.Convertir(totalLiquidoPagable.toString(),true)

        def totalLiquidoPagableOriginal = lotecito.getAt("totalLiquidoPagable").toString().toBigDecimal()
        def diferenciaLiquidoPagable = 0
        def observaciones  = lotecito.getAt("observaciones")
        def motivoDeModificacion = lotecito.getAt("motivoDeModificacion")
        def detalleLaboratorio1 = lotecito.getAt("detalleLaboratorio1")
        def totalCostoLaboratorio = lotecito.getAt("totalCostoLaboratorio").toString().toBigDecimal()

        def liquidacionDeComplejo = new LiquidacionDeComplejo(
                recepcionDeComplejo:recepcionDeComplejo,
                deposito:deposito,
                empresa:empresa,
                lote:lote,
                conjuntoComplejo: "-",
                tipoDeMineral:tipoDeMineral,
                nombreDeposito:nombreDeposito,
                nombreCliente:nombreCliente,
                nombreEmpresa:nombreEmpresa,
                fechaDeRecepcion: fechaDeRecepcion,
                cantidadDeSacos:cantidadDeSacos,
                estadoDelLote:estadoDelLote,
                pesoBruto:pesoBruto,
                cotizacionDiariaDeZinc:cotizacionDiariaDeZinc,
                cotizacionQuincenalDeZinc:cotizacionQuincenalDeZinc,
                alicuotaDeZinc:alicuotaDeZinc,
                cotizacionDiariaDePlomo:cotizacionDiariaDePlomo,
                cotizacionQuincenalDePlomo:cotizacionQuincenalDePlomo,
                alicuotaDePlomo:alicuotaDePlomo,
                cotizacionDiariaDePlata:cotizacionDiariaDePlata,
                cotizacionQuincenalDePlata:cotizacionQuincenalDePlata,
                alicuotaDePlata:alicuotaDePlata,
                tipoDeCambioOficial:tipoDeCambioOficial,
                tipoDeCambioComercial:tipoDeCambioComercial,
                fechaDeLiquidacion:fechaDeLiquidacion,
                kilosNetosHumedos:kilosNetosHumedos,
                kilosNetosSecos:kilosNetosSecos,
                dolarPuntoZinc:dolarPuntoZinc,
                dolarPuntoPlomo:dolarPuntoPlomo,
                dolarPuntoPlata:dolarPuntoPlata,
                porcentajeZincPromexbol :porcentajeZincPromexbol ,
                porcentajePlomoPromexbol :porcentajePlomoPromexbol ,
                porcentajePlataPromexbol :porcentajePlataPromexbol ,
                porcentajeHumedadPromexbol :porcentajeHumedadPromexbol ,
                porcentajeMermaPromexbol :porcentajeMermaPromexbol ,
                porcentajeZincCliente :porcentajeZincCliente ,
                porcentajePlomoCliente :porcentajePlomoCliente ,
                porcentajePlataCliente :porcentajePlataCliente ,
                porcentajeHumedadCliente :porcentajeHumedadCliente ,
                porcentajeMermaCliente :porcentajeMermaCliente ,
                porcentajeZincFinal:porcentajeZincFinal,
                porcentajePlomoFinal:porcentajePlomoFinal,
                porcentajePlataFinal:porcentajePlataFinal,
                porcentajeHumedadFinal :porcentajeHumedadFinal ,
                porcentajeMermaFinal :porcentajeMermaFinal ,
                modoValoracion:modoValoracion,
                tablaComplejo:tablaComplejo,
                terminosDeContrato:terminosDeContrato,
                kilosFinosZinc:kilosFinosZinc,
                kilosFinosPlomo:kilosFinosPlomo,
                kilosFinosPlata:kilosFinosPlata,
                librasFinasDeZinc:librasFinasDeZinc,
                librasFinasDePlomo:librasFinasDePlomo,
                onzasTroyDePlata:onzasTroyDePlata,
                valorOficialBrutoDeZinc:valorOficialBrutoDeZinc,
                valorOficialBrutoDePlomo:valorOficialBrutoDePlomo,
                valorOficialBrutoDePlata:valorOficialBrutoDePlata,
                valorOficialBrutoDeZincEnBolivianos:valorOficialBrutoDeZincEnBolivianos,
                valorOficialBrutoDePlomoEnBolivianos:valorOficialBrutoDePlomoEnBolivianos,
                valorOficialBrutoDePlataEnBolivianos:valorOficialBrutoDePlataEnBolivianos,
                valorOficialBruto:valorOficialBruto,
                valorPorTonelada:valorPorTonelada,
                margen :margen ,
                porcentajeRegalia: 0,
                regaliaMinera:regaliaMinera,
                retenciones:retenciones,
                valorNetoMineral:valorNetoMineral,
                valorNetoMineralEnBolivianos:valorNetoMineralEnBolivianos,
                bonoCalidad:bonoCalidad,
                bonoIncentivo:bonoIncentivo,
                valorDeCompra:valorDeCompra,
                totalRetenciones:totalRetenciones,
                totalPagado:totalPagado,
                anticipoPorPagar :anticipoPorPagar ,
                totalAnticiposContraEntrega:totalAnticiposContraEntrega,
                totalAnticiposContraFuturaEntrega:totalAnticiposContraFuturaEntrega,
                adelantoPorLiquidacionProvisional:adelantoPorLiquidacionProvisional,
                totalLiquidoPagable:totalLiquidoPagable,
                totalLiquidoPagableLiteral: totalLiquidoPagableLiteral,
                totalLiquidoPagableOriginal:totalLiquidoPagableOriginal,
                diferenciaLiquidoPagable:diferenciaLiquidoPagable,
                observaciones :observaciones ,
                motivoDeModificacion:motivoDeModificacion,
                detalleLaboratorio1:detalleLaboratorio1,
                totalCostoLaboratorio:totalCostoLaboratorio
        )
        liquidacionDeComplejo.save(failOnError: true)

        def liquidacionGrupalDeComplejoDetalle = new LiquidacionGrupalDeComplejoDetalle(
                millis: millis,
                liquidacionDeComplejo: liquidacionDeComplejo
        )
        liquidacionGrupalDeComplejoDetalle.save(failOnError: true)
        render [:] as JSON
    }
}
