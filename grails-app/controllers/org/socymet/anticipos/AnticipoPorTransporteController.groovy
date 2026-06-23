package org.socymet.anticipos
import grails.converters.JSON
import grails.gorm.transactions.Transactional

import org.socymet.cancelacion.EstadoCuentaTransporte
import org.socymet.proveedor.Automovil
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_RECEPCION','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class AnticipoPorTransporteController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.sort = "id"
        params.order = "desc"
        [anticipoPorTransporteInstanceList: AnticipoPorTransporte.list(params), anticipoPorTransporteInstanceTotal: AnticipoPorTransporte.count()]
    }

    def create() {
        [anticipoPorTransporteInstance: new AnticipoPorTransporte(params)]
    }

    def save() {
        def anticipoPorTransporteInstance = new AnticipoPorTransporte(params)
        if (!anticipoPorTransporteInstance.save(flush: true)) {
            render(view: "create", model: [anticipoPorTransporteInstance: anticipoPorTransporteInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'anticipoPorTransporte.label', default: 'AnticipoPorTransporte'), anticipoPorTransporteInstance.id])
        redirect(action: "show", id: anticipoPorTransporteInstance.id)
    }

    def show(Long id) {
        def anticipoPorTransporteInstance = AnticipoPorTransporte.get(id)
        if (!anticipoPorTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipoPorTransporte.label', default: 'AnticipoPorTransporte'), id])
            redirect(action: "list")
            return
        }

        [anticipoPorTransporteInstance: anticipoPorTransporteInstance]
    }

    def edit(Long id) {
        def anticipoPorTransporteInstance = AnticipoPorTransporte.get(id)
        if (!anticipoPorTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipoPorTransporte.label', default: 'AnticipoPorTransporte'), id])
            redirect(action: "list")
            return
        }

        [anticipoPorTransporteInstance: anticipoPorTransporteInstance]
    }

    def update(Long id, Long version) {
        def anticipoPorTransporteInstance = AnticipoPorTransporte.get(id)
        if (!anticipoPorTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipoPorTransporte.label', default: 'AnticipoPorTransporte'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (anticipoPorTransporteInstance.version > version) {
                anticipoPorTransporteInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'anticipoPorTransporte.label', default: 'AnticipoPorTransporte')] as Object[],
                        "Another user has updated this AnticipoPorTransporte while you were editing")
                render(view: "edit", model: [anticipoPorTransporteInstance: anticipoPorTransporteInstance])
                return
            }
        }

        anticipoPorTransporteInstance.properties = params

        if (!anticipoPorTransporteInstance.save(flush: true)) {
            render(view: "edit", model: [anticipoPorTransporteInstance: anticipoPorTransporteInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'anticipoPorTransporte.label', default: 'AnticipoPorTransporte'), anticipoPorTransporteInstance.id])
        redirect(action: "show", id: anticipoPorTransporteInstance.id)
    }

    def delete(Long id) {
        def anticipoPorTransporteInstance = AnticipoPorTransporte.get(id)
        if (!anticipoPorTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipoPorTransporte.label', default: 'AnticipoPorTransporte'), id])
            redirect(action: "list")
            return
        }

        try {
            anticipoPorTransporteInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'anticipoPorTransporte.label', default: 'AnticipoPorTransporte'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'anticipoPorTransporte.label', default: 'AnticipoPorTransporte'), id])
            redirect(action: "show", id: id)
        }
    }

    def createReport = {
        def factura = AnticipoPorTransporte.get(params.id)
        def realPath = servletContext.getRealPath("/reports/images/")
        params.realPath=realPath+"/"
        chain(controller:'jasper',action:'index',model:[data:factura],params:params)
    }

    def recuperarDatosChoferJSON = {
        def recepcion = RecepcionDeComplejo.get(params.recepcionId)
        render([
            ciChofer: recepcion.chofer.ci,
            nombreChofer: recepcion.chofer.nombre
        ] as JSON)
    }
}
