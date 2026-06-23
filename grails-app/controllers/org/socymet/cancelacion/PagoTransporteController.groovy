package org.socymet.cancelacion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.proveedor.Empresa
import org.socymet.seguridad.SecUser
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_RECEPCION','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class PagoTransporteController {
    def springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        params.max = Math.min(max ?: 10, 100)
        params.sort = "id"
        params.order = "desc"
        [pagoTransporteInstanceList: PagoTransporte.findAllByDeposito(usuarioActual.deposito,params), pagoTransporteInstanceTotal: PagoTransporte.findAllByDeposito(usuarioActual.deposito).size()]
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

        flash.message = message(code: 'default.created.message', args: [message(code: 'pagoTransporte.label', default: 'PagoTransporte'), pagoTransporteInstance.id])
        redirect(action: "show", id: pagoTransporteInstance.id)
    }

    def show(Long id) {
        def pagoTransporteInstance = PagoTransporte.get(id)
        if (!pagoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoTransporte.label', default: 'PagoTransporte'), id])
            redirect(action: "list")
            return
        }

        [pagoTransporteInstance: pagoTransporteInstance]
    }

    def edit(Long id) {
        def pagoTransporteInstance = PagoTransporte.get(id)
        if (!pagoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoTransporte.label', default: 'PagoTransporte'), id])
            redirect(action: "list")
            return
        }

        [pagoTransporteInstance: pagoTransporteInstance]
    }

    def update(Long id, Long version) {
        def pagoTransporteInstance = PagoTransporte.get(id)
        if (!pagoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoTransporte.label', default: 'PagoTransporte'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (pagoTransporteInstance.version > version) {
                pagoTransporteInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'pagoTransporte.label', default: 'PagoTransporte')] as Object[],
                        "Another user has updated this PagoTransporte while you were editing")
                render(view: "edit", model: [pagoTransporteInstance: pagoTransporteInstance])
                return
            }
        }

        pagoTransporteInstance.properties = params

        if (!pagoTransporteInstance.save(flush: true)) {
            render(view: "edit", model: [pagoTransporteInstance: pagoTransporteInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'pagoTransporte.label', default: 'PagoTransporte'), pagoTransporteInstance.id])
        redirect(action: "show", id: pagoTransporteInstance.id)
    }

    def delete(Long id) {
        def pagoTransporteInstance = PagoTransporte.get(id)
        if (!pagoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoTransporte.label', default: 'PagoTransporte'), id])
            redirect(action: "list")
            return
        }

        try {
            pagoTransporteInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'pagoTransporte.label', default: 'PagoTransporte'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'pagoTransporte.label', default: 'PagoTransporte'), id])
            redirect(action: "show", id: id)
        }
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
