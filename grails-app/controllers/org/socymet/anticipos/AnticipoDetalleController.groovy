package org.socymet.anticipos
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class AnticipoDetalleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [anticipoDetalleInstanceList: AnticipoDetalle.list(params), anticipoDetalleInstanceTotal: AnticipoDetalle.count()]
    }

    def create() {
        [anticipoDetalleInstance: new AnticipoDetalle(params)]
    }

    def save() {
        def anticipoDetalleInstance = new AnticipoDetalle(params)
        if (!anticipoDetalleInstance.save(flush: true)) {
            render(view: "create", model: [anticipoDetalleInstance: anticipoDetalleInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'anticipoDetalle.label', default: 'AnticipoDetalle'), anticipoDetalleInstance.id])
        redirect(action: "show", id: anticipoDetalleInstance.id)
    }

    def show(Long id) {
        def anticipoDetalleInstance = AnticipoDetalle.get(id)
        if (!anticipoDetalleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipoDetalle.label', default: 'AnticipoDetalle'), id])
            redirect(action: "list")
            return
        }

        [anticipoDetalleInstance: anticipoDetalleInstance]
    }

    def edit(Long id) {
        def anticipoDetalleInstance = AnticipoDetalle.get(id)
        if (!anticipoDetalleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipoDetalle.label', default: 'AnticipoDetalle'), id])
            redirect(action: "list")
            return
        }

        [anticipoDetalleInstance: anticipoDetalleInstance]
    }

    def update(Long id, Long version) {
        def anticipoDetalleInstance = AnticipoDetalle.get(id)
        if (!anticipoDetalleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipoDetalle.label', default: 'AnticipoDetalle'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (anticipoDetalleInstance.version > version) {
                anticipoDetalleInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'anticipoDetalle.label', default: 'AnticipoDetalle')] as Object[],
                        "Another user has updated this AnticipoDetalle while you were editing")
                render(view: "edit", model: [anticipoDetalleInstance: anticipoDetalleInstance])
                return
            }
        }

        anticipoDetalleInstance.properties = params

        if (!anticipoDetalleInstance.save(flush: true)) {
            render(view: "edit", model: [anticipoDetalleInstance: anticipoDetalleInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'anticipoDetalle.label', default: 'AnticipoDetalle'), anticipoDetalleInstance.id])
        redirect(action: "show", id: anticipoDetalleInstance.id)
    }

    def delete(Long id) {
        def anticipoDetalleInstance = AnticipoDetalle.get(id)
        if (!anticipoDetalleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipoDetalle.label', default: 'AnticipoDetalle'), id])
            redirect(action: "list")
            return
        }

        try {
            anticipoDetalleInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'anticipoDetalle.label', default: 'AnticipoDetalle'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'anticipoDetalle.label', default: 'AnticipoDetalle'), id])
            redirect(action: "show", id: id)
        }
    }
}
