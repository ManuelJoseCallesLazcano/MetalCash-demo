package org.socymet.cancelacion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.proveedor.Empresa
import org.socymet.seguridad.SecUser
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_RECEPCION','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class PagoDeRetencionesController {
    def springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        params.max = Math.min(max ?: 10, 100)
//        [pagoDeRetencionesInstanceList: PagoDeRetenciones.list(params), pagoDeRetencionesInstanceTotal: PagoDeRetenciones.count()]
        [pagoDeRetencionesInstanceList: PagoDeRetenciones.findAllByDeposito(usuarioActual.deposito,params), pagoDeRetencionesInstanceTotal: PagoDeRetenciones.count()]
    }

    def create() {
        [pagoDeRetencionesInstance: new PagoDeRetenciones(params)]
    }

    def save() {
        def pagoDeRetencionesInstance = new PagoDeRetenciones(params)
        if (!pagoDeRetencionesInstance.save(flush: true)) {
            render(view: "create", model: [pagoDeRetencionesInstance: pagoDeRetencionesInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'pagoDeRetenciones.label', default: 'PagoDeRetenciones'), pagoDeRetencionesInstance.id])
        redirect(action: "show", id: pagoDeRetencionesInstance.id)
    }

    def show(Long id) {
        def pagoDeRetencionesInstance = PagoDeRetenciones.get(id)
        if (!pagoDeRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoDeRetenciones.label', default: 'PagoDeRetenciones'), id])
            redirect(action: "list")
            return
        }

        [pagoDeRetencionesInstance: pagoDeRetencionesInstance]
    }

    def edit(Long id) {
        def pagoDeRetencionesInstance = PagoDeRetenciones.get(id)
        if (!pagoDeRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoDeRetenciones.label', default: 'PagoDeRetenciones'), id])
            redirect(action: "list")
            return
        }

        [pagoDeRetencionesInstance: pagoDeRetencionesInstance]
    }

    def update(Long id, Long version) {
        def pagoDeRetencionesInstance = PagoDeRetenciones.get(id)
        if (!pagoDeRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoDeRetenciones.label', default: 'PagoDeRetenciones'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (pagoDeRetencionesInstance.version > version) {
                pagoDeRetencionesInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'pagoDeRetenciones.label', default: 'PagoDeRetenciones')] as Object[],
                        "Another user has updated this PagoDeRetenciones while you were editing")
                render(view: "edit", model: [pagoDeRetencionesInstance: pagoDeRetencionesInstance])
                return
            }
        }

        pagoDeRetencionesInstance.properties = params

        if (!pagoDeRetencionesInstance.save(flush: true)) {
            render(view: "edit", model: [pagoDeRetencionesInstance: pagoDeRetencionesInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'pagoDeRetenciones.label', default: 'PagoDeRetenciones'), pagoDeRetencionesInstance.id])
        redirect(action: "show", id: pagoDeRetencionesInstance.id)
    }

    def delete(Long id) {
        def pagoDeRetencionesInstance = PagoDeRetenciones.get(id)
        if (!pagoDeRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoDeRetenciones.label', default: 'PagoDeRetenciones'), id])
            redirect(action: "list")
            return
        }

        try {
            pagoDeRetencionesInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'pagoDeRetenciones.label', default: 'PagoDeRetenciones'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'pagoDeRetenciones.label', default: 'PagoDeRetenciones'), id])
            redirect(action: "show", id: id)
        }
    }

    def getEmpresasSegunUsuario() {
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        def empresas = Empresa.findAllByDeposito(usuarioActual.deposito)
//        if (empresas)
//            empresas.add(0,"-TODAS LAS EMPRESAS-")
//        render text: g.select(id: 'accountNumberSelect', name: 'accountNumber', from: Account.findAllByNumber(number), optionKey: "id", optionValue: "someProperty")
        render text: g.select(id: 'empresa', name: 'empresa.id', from: empresas, optionKey: "id", noSelection: ['null': '-TODAS LAS EMPRESAS-'])
    }

    def nombreCobradorJSON() {
        def pagoDeRetenciones = PagoDeRetenciones.findAllByCiLike("${params.term}%")
        pagoDeRetenciones=PagoDeRetenciones.withCriteria {
            projections {
                distinct "ci"
                property("nombreCobrador")
                property("beneficiario")
            }
            like("ci","${params.term}%")
        }.sort()

        def pagoDeRetencionesList = []
        pagoDeRetenciones.each {
            log.error("${it[0]} - ${it[1]} - ${it[2]}")
            def mapaClientes = [:]
            //parametros en JSON para JQuery UI Autocomplete
            mapaClientes.put("id",it[0])
            mapaClientes.put("label","${it[0]} - ${it[1]}") //son las cadenas que se muestran en la lista
            mapaClientes.put("value",it[0]) //es la cadena que se establece en el input despues de ser seleccionado
            //otros parametros
            mapaClientes.put("nombreCobrador",it[1])
            mapaClientes.put("beneficiario",it[2])
            pagoDeRetencionesList.add(mapaClientes)
        }
        render pagoDeRetencionesList as JSON
    }

    def createReport = {
        def factura = PagoDeRetenciones.get(params.id)
        def realPath = servletContext.getRealPath("/reports/images/")
        params.realPath=realPath+"/"
        chain(controller:'jasper',action:'index',model:[data:factura],params:params)
    }
}
