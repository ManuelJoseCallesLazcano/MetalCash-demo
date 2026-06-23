package org.socymet.cancelacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class DetallePagoManipuleoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [detallePagoManipuleoInstanceList: DetallePagoManipuleo.list(params), detallePagoManipuleoInstanceTotal: DetallePagoManipuleo.count()]
    }

    def create() {
        [detallePagoManipuleoInstance: new DetallePagoManipuleo(params)]
    }

    def save() {
        def detallePagoManipuleoInstance = new DetallePagoManipuleo(params)
        if (!detallePagoManipuleoInstance.save(flush: true)) {
            render(view: "create", model: [detallePagoManipuleoInstance: detallePagoManipuleoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'detallePagoManipuleo.label', default: 'DetallePagoManipuleo'), detallePagoManipuleoInstance.id])
        redirect(action: "show", id: detallePagoManipuleoInstance.id)
    }

    def show(Long id) {
        def detallePagoManipuleoInstance = DetallePagoManipuleo.get(id)
        if (!detallePagoManipuleoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'detallePagoManipuleo.label', default: 'DetallePagoManipuleo'), id])
            redirect(action: "list")
            return
        }

        [detallePagoManipuleoInstance: detallePagoManipuleoInstance]
    }

    def edit(Long id) {
        def detallePagoManipuleoInstance = DetallePagoManipuleo.get(id)
        if (!detallePagoManipuleoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'detallePagoManipuleo.label', default: 'DetallePagoManipuleo'), id])
            redirect(action: "list")
            return
        }

        [detallePagoManipuleoInstance: detallePagoManipuleoInstance]
    }

    def update(Long id, Long version) {
        def detallePagoManipuleoInstance = DetallePagoManipuleo.get(id)
        if (!detallePagoManipuleoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'detallePagoManipuleo.label', default: 'DetallePagoManipuleo'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (detallePagoManipuleoInstance.version > version) {
                detallePagoManipuleoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'detallePagoManipuleo.label', default: 'DetallePagoManipuleo')] as Object[],
                        "Another user has updated this DetallePagoManipuleo while you were editing")
                render(view: "edit", model: [detallePagoManipuleoInstance: detallePagoManipuleoInstance])
                return
            }
        }

        detallePagoManipuleoInstance.properties = params

        if (!detallePagoManipuleoInstance.save(flush: true)) {
            render(view: "edit", model: [detallePagoManipuleoInstance: detallePagoManipuleoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'detallePagoManipuleo.label', default: 'DetallePagoManipuleo'), detallePagoManipuleoInstance.id])
        redirect(action: "show", id: detallePagoManipuleoInstance.id)
    }

    def delete(Long id) {
        def detallePagoManipuleoInstance = DetallePagoManipuleo.get(id)
        if (!detallePagoManipuleoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'detallePagoManipuleo.label', default: 'DetallePagoManipuleo'), id])
            redirect(action: "list")
            return
        }

        try {
            detallePagoManipuleoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'detallePagoManipuleo.label', default: 'DetallePagoManipuleo'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'detallePagoManipuleo.label', default: 'DetallePagoManipuleo'), id])
            redirect(action: "show", id: id)
        }
    }
}
