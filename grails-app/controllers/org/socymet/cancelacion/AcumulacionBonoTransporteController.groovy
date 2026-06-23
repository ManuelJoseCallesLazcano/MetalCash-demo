package org.socymet.cancelacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class AcumulacionBonoTransporteController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [acumulacionBonoTransporteInstanceList: AcumulacionBonoTransporte.list(params), acumulacionBonoTransporteInstanceTotal: AcumulacionBonoTransporte.count()]
    }

    def create() {
        [acumulacionBonoTransporteInstance: new AcumulacionBonoTransporte(params)]
    }

    def save() {
        def acumulacionBonoTransporteInstance = new AcumulacionBonoTransporte(params)
        if (!acumulacionBonoTransporteInstance.save(flush: true)) {
            render(view: "create", model: [acumulacionBonoTransporteInstance: acumulacionBonoTransporteInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'acumulacionBonoTransporte.label', default: 'AcumulacionBonoTransporte'), acumulacionBonoTransporteInstance.id])
        redirect(action: "show", id: acumulacionBonoTransporteInstance.id)
    }

    def show(Long id) {
        def acumulacionBonoTransporteInstance = AcumulacionBonoTransporte.get(id)
        if (!acumulacionBonoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'acumulacionBonoTransporte.label', default: 'AcumulacionBonoTransporte'), id])
            redirect(action: "list")
            return
        }

        [acumulacionBonoTransporteInstance: acumulacionBonoTransporteInstance]
    }

    def edit(Long id) {
        def acumulacionBonoTransporteInstance = AcumulacionBonoTransporte.get(id)
        if (!acumulacionBonoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'acumulacionBonoTransporte.label', default: 'AcumulacionBonoTransporte'), id])
            redirect(action: "list")
            return
        }

        [acumulacionBonoTransporteInstance: acumulacionBonoTransporteInstance]
    }

    def update(Long id, Long version) {
        def acumulacionBonoTransporteInstance = AcumulacionBonoTransporte.get(id)
        if (!acumulacionBonoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'acumulacionBonoTransporte.label', default: 'AcumulacionBonoTransporte'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (acumulacionBonoTransporteInstance.version > version) {
                acumulacionBonoTransporteInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'acumulacionBonoTransporte.label', default: 'AcumulacionBonoTransporte')] as Object[],
                        "Another user has updated this AcumulacionBonoTransporte while you were editing")
                render(view: "edit", model: [acumulacionBonoTransporteInstance: acumulacionBonoTransporteInstance])
                return
            }
        }

        acumulacionBonoTransporteInstance.properties = params

        if (!acumulacionBonoTransporteInstance.save(flush: true)) {
            render(view: "edit", model: [acumulacionBonoTransporteInstance: acumulacionBonoTransporteInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'acumulacionBonoTransporte.label', default: 'AcumulacionBonoTransporte'), acumulacionBonoTransporteInstance.id])
        redirect(action: "show", id: acumulacionBonoTransporteInstance.id)
    }

    def delete(Long id) {
        def acumulacionBonoTransporteInstance = AcumulacionBonoTransporte.get(id)
        if (!acumulacionBonoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'acumulacionBonoTransporte.label', default: 'AcumulacionBonoTransporte'), id])
            redirect(action: "list")
            return
        }

        try {
            acumulacionBonoTransporteInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'acumulacionBonoTransporte.label', default: 'AcumulacionBonoTransporte'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'acumulacionBonoTransporte.label', default: 'AcumulacionBonoTransporte'), id])
            redirect(action: "show", id: id)
        }
    }
}
