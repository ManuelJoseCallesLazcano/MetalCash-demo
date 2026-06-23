package org.socymet.cancelacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class LoteBonoCalidadController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [loteBonoCalidadInstanceList: LoteBonoCalidad.list(params), loteBonoCalidadInstanceTotal: LoteBonoCalidad.count()]
    }

    def create() {
        [loteBonoCalidadInstance: new LoteBonoCalidad(params)]
    }

    def save() {
        def loteBonoCalidadInstance = new LoteBonoCalidad(params)
        if (!loteBonoCalidadInstance.save(flush: true)) {
            render(view: "create", model: [loteBonoCalidadInstance: loteBonoCalidadInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'loteBonoCalidad.label', default: 'LoteBonoCalidad'), loteBonoCalidadInstance.id])
        redirect(action: "show", id: loteBonoCalidadInstance.id)
    }

    def show(Long id) {
        def loteBonoCalidadInstance = LoteBonoCalidad.get(id)
        if (!loteBonoCalidadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'loteBonoCalidad.label', default: 'LoteBonoCalidad'), id])
            redirect(action: "list")
            return
        }

        [loteBonoCalidadInstance: loteBonoCalidadInstance]
    }

    def edit(Long id) {
        def loteBonoCalidadInstance = LoteBonoCalidad.get(id)
        if (!loteBonoCalidadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'loteBonoCalidad.label', default: 'LoteBonoCalidad'), id])
            redirect(action: "list")
            return
        }

        [loteBonoCalidadInstance: loteBonoCalidadInstance]
    }

    def update(Long id, Long version) {
        def loteBonoCalidadInstance = LoteBonoCalidad.get(id)
        if (!loteBonoCalidadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'loteBonoCalidad.label', default: 'LoteBonoCalidad'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (loteBonoCalidadInstance.version > version) {
                loteBonoCalidadInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'loteBonoCalidad.label', default: 'LoteBonoCalidad')] as Object[],
                        "Another user has updated this LoteBonoCalidad while you were editing")
                render(view: "edit", model: [loteBonoCalidadInstance: loteBonoCalidadInstance])
                return
            }
        }

        loteBonoCalidadInstance.properties = params

        if (!loteBonoCalidadInstance.save(flush: true)) {
            render(view: "edit", model: [loteBonoCalidadInstance: loteBonoCalidadInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'loteBonoCalidad.label', default: 'LoteBonoCalidad'), loteBonoCalidadInstance.id])
        redirect(action: "show", id: loteBonoCalidadInstance.id)
    }

    def delete(Long id) {
        def loteBonoCalidadInstance = LoteBonoCalidad.get(id)
        if (!loteBonoCalidadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'loteBonoCalidad.label', default: 'LoteBonoCalidad'), id])
            redirect(action: "list")
            return
        }

        try {
            loteBonoCalidadInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'loteBonoCalidad.label', default: 'LoteBonoCalidad'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'loteBonoCalidad.label', default: 'LoteBonoCalidad'), id])
            redirect(action: "show", id: id)
        }
    }
}
