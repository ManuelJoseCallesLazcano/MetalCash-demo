package org.socymet.cancelacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class LoteBonoTransporteController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [loteBonoTransporteInstanceList: LoteBonoTransporte.list(params), loteBonoTransporteInstanceTotal: LoteBonoTransporte.count()]
    }

    def create() {
        [loteBonoTransporteInstance: new LoteBonoTransporte(params)]
    }

    def save() {
        def loteBonoTransporteInstance = new LoteBonoTransporte(params)
        if (!loteBonoTransporteInstance.save(flush: true)) {
            render(view: "create", model: [loteBonoTransporteInstance: loteBonoTransporteInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'loteBonoTransporte.label', default: 'LoteBonoTransporte'), loteBonoTransporteInstance.id])
        redirect(action: "show", id: loteBonoTransporteInstance.id)
    }

    def show(Long id) {
        def loteBonoTransporteInstance = LoteBonoTransporte.get(id)
        if (!loteBonoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'loteBonoTransporte.label', default: 'LoteBonoTransporte'), id])
            redirect(action: "list")
            return
        }

        [loteBonoTransporteInstance: loteBonoTransporteInstance]
    }

    def edit(Long id) {
        def loteBonoTransporteInstance = LoteBonoTransporte.get(id)
        if (!loteBonoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'loteBonoTransporte.label', default: 'LoteBonoTransporte'), id])
            redirect(action: "list")
            return
        }

        [loteBonoTransporteInstance: loteBonoTransporteInstance]
    }

    def update(Long id, Long version) {
        def loteBonoTransporteInstance = LoteBonoTransporte.get(id)
        if (!loteBonoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'loteBonoTransporte.label', default: 'LoteBonoTransporte'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (loteBonoTransporteInstance.version > version) {
                loteBonoTransporteInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'loteBonoTransporte.label', default: 'LoteBonoTransporte')] as Object[],
                        "Another user has updated this LoteBonoTransporte while you were editing")
                render(view: "edit", model: [loteBonoTransporteInstance: loteBonoTransporteInstance])
                return
            }
        }

        loteBonoTransporteInstance.properties = params

        if (!loteBonoTransporteInstance.save(flush: true)) {
            render(view: "edit", model: [loteBonoTransporteInstance: loteBonoTransporteInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'loteBonoTransporte.label', default: 'LoteBonoTransporte'), loteBonoTransporteInstance.id])
        redirect(action: "show", id: loteBonoTransporteInstance.id)
    }

    def delete(Long id) {
        def loteBonoTransporteInstance = LoteBonoTransporte.get(id)
        if (!loteBonoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'loteBonoTransporte.label', default: 'LoteBonoTransporte'), id])
            redirect(action: "list")
            return
        }

        try {
            loteBonoTransporteInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'loteBonoTransporte.label', default: 'LoteBonoTransporte'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'loteBonoTransporte.label', default: 'LoteBonoTransporte'), id])
            redirect(action: "show", id: id)
        }
    }
}
