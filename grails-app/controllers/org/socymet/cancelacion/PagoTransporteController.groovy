package org.socymet.cancelacion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.seguridad.SecUser
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_RECEPCION','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class PagoTransporteController {
    def springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST", anular: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        params.max = Math.min(max ?: 10, 100)
        params.sort = params.sort ?: "id"
        params.order = params.order ?: "desc"
        def q = params.q?.trim()
        def filtro = {
            eq('deposito', usuarioActual.deposito)
            if (q) {
                or {
                    if (q.isInteger()) eq('numeroComprobante', q.toInteger())
                    ilike('ci', "%${q}%")
                    ilike('nombreCobrador', "%${q}%")
                }
            }
        }
        def instancias = PagoTransporte.createCriteria().list(params, filtro)
        def total = PagoTransporte.createCriteria().count(filtro)
        [pagoTransporteInstanceList: instancias, pagoTransporteInstanceTotal: total, q: q]
    }

    def create() {
        [pagoTransporteInstance: new PagoTransporte(params)]
    }

    def save() {
        def pagoTransporteInstance = new PagoTransporte(params)
        if (!pagoTransporteInstance.save(flush: true)) {
            render(view: "create", model: [pagoTransporteInstance: pagoTransporteInstance])
            return
        }

        // Post-registro (con el pago YA persistido): detalle por lote, marcar transporte pagado
        // en las recepciones, y asiento en el ledger del automóvil. Se hace aquí y NO en afterInsert
        // porque el detalle referencia el pago como FK: dentro de afterInsert el pago aún es
        // transitorio (TransientPropertyValueException) y withNewTransaction cruzaba proxies de sesión.
        def lotesJSON = new org.grails.web.json.JSONArray(pagoTransporteInstance.lotes)
        lotesJSON.each {
            def recepcionId = it.getAt("recepcionId").toString().toLong()
            new DetallePagoTransporte(
                    pagoTransporte: pagoTransporteInstance,
                    lote: it.getAt("lote"),
                    recepcionId: recepcionId,
                    nombreChofer: it.getAt("nombreChofer"),
                    placaAutomovil: it.getAt("placaAutomovil"),
                    fechaDeRecepcion: it.getAt("fechaDeRecepcion"),
                    pesoBruto: it.getAt("pesoBruto").toString().toBigDecimal(),
                    tipoDeMaterial: it.getAt("tipoDeMaterial"),
                    costoDeTransporte: it.getAt("costoDeTransporte").toString().toBigDecimal()
            ).save(failOnError: true)

            def recepcion = RecepcionDeComplejo.get(recepcionId)
            recepcion.transportePagado = "SI"
            recepcion.save(flush: true, failOnError: true)
        }

        // Un solo asiento en el ledger del automóvil: el pago CONSUME disponible.
        def disponible = EstadoCuentaTransporte.saldoDisponible(pagoTransporteInstance.automovil)
        new EstadoCuentaTransporte(
                solicitante: pagoTransporteInstance.solicitante,
                empresa: pagoTransporteInstance.empresa,
                automovil: pagoTransporteInstance.automovil,
                ci: pagoTransporteInstance.ci,
                nombreResponsable: pagoTransporteInstance.nombreCobrador,
                fecha: pagoTransporteInstance.fechaDePago,
                descripcion: "PAGO DE TRANSPORTE SEGUN COMPROBANTE No. ${pagoTransporteInstance.toString()}",
                ingreso: 0,
                egreso: pagoTransporteInstance.totalAnticipos,
                saldo: disponible - pagoTransporteInstance.totalAnticipos,
                tipoMovimiento: "PAGO_TRANSPORTE",
                origenId: pagoTransporteInstance.id
        ).save(failOnError: true)

        flash.message = message(code: 'default.created.message', args: [message(code: 'pagoTransporte.label', default: 'PagoTransporte'), pagoTransporteInstance.toString()])
        redirect(action: "show", id: pagoTransporteInstance.id)
    }

    def show(Long id) {
        def pagoTransporteInstance = PagoTransporte.get(id)
        if (!pagoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoTransporte.label', default: 'PagoTransporte'), id])
            redirect(action: "list")
            return
        }

        [pagoTransporteInstance: pagoTransporteInstance,
         detalles: DetallePagoTransporte.findAllByPagoTransporte(pagoTransporteInstance, [sort: 'id', order: 'asc'])]
    }

    // Este módulo NO admite edición (se revierte con Anular). Se bloquean edit y update: /edit/{id}
    // no debe caer en 404 y no se puede forzar el guardado por POST directo.
    def edit(Long id) { bloquearEdicion(id) }

    def update(Long id, Long version) { bloquearEdicion(id) }

    private void bloquearEdicion(Long id) {
        flash.message = "No se permite editar un pago de transporte. Use Anular si necesita revertirlo."
        flash.swalIcon = 'warning'
        flash.swalTitle = 'Operación no disponible'
        redirect(action: "show", id: id)
    }

    def anular(Long id) {
        // Anulacion por REVERSA: no se borra el documento; se devuelve el disponible consumido
        // y se libera el transporte de las recepciones.
        def pagoTransporteInstance = PagoTransporte.get(id)
        if (!pagoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoTransporte.label', default: 'PagoTransporte'), id])
            redirect(action: "list")
            return
        }

        if (pagoTransporteInstance.anulado) {
            flash.swalIcon = "warning"
            flash.swalTitle = "Sin cambios"
            flash.message = "El pago No. ${pagoTransporteInstance.toString()} ya estaba anulado."
            redirect(action: "show", id: id)
            return
        }

        // El pago habia CONSUMIDO disponible (−anticipoAplicado); la reversa lo DEVUELVE (+anticipoAplicado).
        def disponible = EstadoCuentaTransporte.saldoDisponible(pagoTransporteInstance.automovil)
        new EstadoCuentaTransporte(
                solicitante: pagoTransporteInstance.solicitante,
                empresa: pagoTransporteInstance.empresa,
                automovil: pagoTransporteInstance.automovil,
                ci: pagoTransporteInstance.ci,
                nombreResponsable: pagoTransporteInstance.nombreCobrador,
                fecha: new Date(),
                descripcion: "REVERSA PAGO DE TRANSPORTE No. ${pagoTransporteInstance.toString()}",
                ingreso: pagoTransporteInstance.totalAnticipos,
                egreso: 0,
                saldo: disponible + pagoTransporteInstance.totalAnticipos,
                tipoMovimiento: "REVERSA_PAGO_TRANSPORTE",
                origenId: pagoTransporteInstance.id
        ).save(flush: true, failOnError: true)

        // Liberar el transporte: las recepciones vuelven a quedar pendientes de pago.
        DetallePagoTransporte.findAllByPagoTransporte(pagoTransporteInstance).each { d ->
            def rec = RecepcionDeComplejo.get(d.recepcionId)
            if (rec != null) {
                rec.transportePagado = "NO"
                rec.save(flush: true, failOnError: true)
            }
        }

        pagoTransporteInstance.anulado = true
        pagoTransporteInstance.save(flush: true, failOnError: true)

        flash.swalIcon = "success"
        flash.swalTitle = "Pago anulado"
        flash.message = "Pago No. ${pagoTransporteInstance.toString()} anulado (reversa registrada, lotes liberados)."
        redirect(action: "show", id: id)
    }

    def getEmpresasSegunUsuario() {
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
//        render text: g.select(id: 'accountNumberSelect', name: 'accountNumber', from: Account.findAllByNumber(number), optionKey: "id", optionValue: "someProperty")
        render text: g.select(id: 'empresa', name: 'empresa.id', from: Empresa.findAllByDeposito(usuarioActual.deposito), optionKey: "id")
    }

    def nombreCobradorJSON() {
//        def pagoTransportes = PagoTransporte.findAllByCiLike("${params.term}%")
//        def pagoTransportesList = []
//        pagoTransportes.each {
//            def mapaClientes = [:]
//            //parametros en JSON para JQuery UI Autocomplete
//            mapaClientes.put("id",it.id)
//            mapaClientes.put("label","${it.ci} - ${it.nombreCobrador}") //son las cadenas que se muestran en la lista
//            mapaClientes.put("value",it.ci) //es la cadena que se establece en el input despues de ser seleccionado
//            //otros parametros
//            mapaClientes.put("nombreCobrador",it.nombreCobrador)
//            pagoTransportesList.add(mapaClientes)
//        }

        def pagoTransportes=PagoTransporte.withCriteria {
            projections {
                distinct "ci"
                property("nombreCobrador")
            }
            like("ci","${params.term}%")
        }.sort()
        def pagoTransportesList = []
        pagoTransportes.each {
            def mapaClientes = [:]
            //parametros en JSON para JQuery UI Autocomplete
            mapaClientes.put("id",it[0])
            mapaClientes.put("label","${it[0]}: ${it[1]}") //son las cadenas que se muestran en la lista
            mapaClientes.put("value",it[0]) //es la cadena que se establece en el input despues de ser seleccionado
            //otros parametros
            mapaClientes.put("nombreCobrador",it[1])
            pagoTransportesList.add(mapaClientes)
        }
        render pagoTransportesList as JSON
    }

    def createReport = {
        def factura = PagoTransporte.get(params.id)
        def realPath = servletContext.getRealPath("/reports/images/")
        params.realPath=realPath+"/"
        chain(controller:'jasper',action:'index',model:[data:factura],params:params)
    }
}
