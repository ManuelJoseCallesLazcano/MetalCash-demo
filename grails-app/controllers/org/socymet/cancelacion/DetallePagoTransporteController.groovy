package org.socymet.cancelacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class DetallePagoTransporteController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [detallePagoTransporteInstanceList: DetallePagoTransporte.list(params), detallePagoTransporteInstanceTotal: DetallePagoTransporte.count()]
    }

    def create() {
        [detallePagoTransporteInstance: new DetallePagoTransporte(params)]
    }

    def save() {
        def detallePagoTransporteInstance = new DetallePagoTransporte(params)
        if (!detallePagoTransporteInstance.save(flush: true)) {
            render(view: "create", model: [detallePagoTransporteInstance: detallePagoTransporteInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'detallePagoTransporte.label', default: 'DetallePagoTransporte'), detallePagoTransporteInstance.id])
        redirect(action: "show", id: detallePagoTransporteInstance.id)
    }

    def show(Long id) {
        def detallePagoTransporteInstance = DetallePagoTransporte.get(id)
        if (!detallePagoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'detallePagoTransporte.label', default: 'DetallePagoTransporte'), id])
            redirect(action: "list")
            return
        }

        [detallePagoTransporteInstance: detallePagoTransporteInstance]
    }

    def edit(Long id) {
        def detallePagoTransporteInstance = DetallePagoTransporte.get(id)
        if (!detallePagoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'detallePagoTransporte.label', default: 'DetallePagoTransporte'), id])
            redirect(action: "list")
            return
        }

        [detallePagoTransporteInstance: detallePagoTransporteInstance]
    }

    def update(Long id, Long version) {
        def detallePagoTransporteInstance = DetallePagoTransporte.get(id)
        if (!detallePagoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'detallePagoTransporte.label', default: 'DetallePagoTransporte'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (detallePagoTransporteInstance.version > version) {
                detallePagoTransporteInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'detallePagoTransporte.label', default: 'DetallePagoTransporte')] as Object[],
                        "Another user has updated this DetallePagoTransporte while you were editing")
                render(view: "edit", model: [detallePagoTransporteInstance: detallePagoTransporteInstance])
                return
            }
        }

        detallePagoTransporteInstance.properties = params

        if (!detallePagoTransporteInstance.save(flush: true)) {
            render(view: "edit", model: [detallePagoTransporteInstance: detallePagoTransporteInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'detallePagoTransporte.label', default: 'DetallePagoTransporte'), detallePagoTransporteInstance.id])
        redirect(action: "show", id: detallePagoTransporteInstance.id)
    }

    def delete(Long id) {
        def detallePagoTransporteInstance = DetallePagoTransporte.get(id)
        if (!detallePagoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'detallePagoTransporte.label', default: 'DetallePagoTransporte'), id])
            redirect(action: "list")
            return
        }

        try {
            detallePagoTransporteInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'detallePagoTransporte.label', default: 'DetallePagoTransporte'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'detallePagoTransporte.label', default: 'DetallePagoTransporte'), id])
            redirect(action: "show", id: id)
        }
    }
}
