package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class CompositoDeLotesDetalleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [compositoDeLotesDetalleInstanceList: CompositoDeLotesDetalle.list(params), compositoDeLotesDetalleInstanceTotal: CompositoDeLotesDetalle.count()]
    }

    def create() {
        [compositoDeLotesDetalleInstance: new CompositoDeLotesDetalle(params)]
    }

    def save() {
        def compositoDeLotesDetalleInstance = new CompositoDeLotesDetalle(params)
        if (!compositoDeLotesDetalleInstance.save(flush: true)) {
            render(view: "create", model: [compositoDeLotesDetalleInstance: compositoDeLotesDetalleInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'compositoDeLotesDetalle.label', default: 'CompositoDeLotesDetalle'), compositoDeLotesDetalleInstance.id])
        redirect(action: "show", id: compositoDeLotesDetalleInstance.id)
    }

    def show(Long id) {
        def compositoDeLotesDetalleInstance = CompositoDeLotesDetalle.get(id)
        if (!compositoDeLotesDetalleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'compositoDeLotesDetalle.label', default: 'CompositoDeLotesDetalle'), id])
            redirect(action: "list")
            return
        }

        [compositoDeLotesDetalleInstance: compositoDeLotesDetalleInstance]
    }

    def edit(Long id) {
        def compositoDeLotesDetalleInstance = CompositoDeLotesDetalle.get(id)
        if (!compositoDeLotesDetalleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'compositoDeLotesDetalle.label', default: 'CompositoDeLotesDetalle'), id])
            redirect(action: "list")
            return
        }

        [compositoDeLotesDetalleInstance: compositoDeLotesDetalleInstance]
    }

    def update(Long id, Long version) {
        def compositoDeLotesDetalleInstance = CompositoDeLotesDetalle.get(id)
        if (!compositoDeLotesDetalleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'compositoDeLotesDetalle.label', default: 'CompositoDeLotesDetalle'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (compositoDeLotesDetalleInstance.version > version) {
                compositoDeLotesDetalleInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'compositoDeLotesDetalle.label', default: 'CompositoDeLotesDetalle')] as Object[],
                        "Another user has updated this CompositoDeLotesDetalle while you were editing")
                render(view: "edit", model: [compositoDeLotesDetalleInstance: compositoDeLotesDetalleInstance])
                return
            }
        }

        compositoDeLotesDetalleInstance.properties = params

        if (!compositoDeLotesDetalleInstance.save(flush: true)) {
            render(view: "edit", model: [compositoDeLotesDetalleInstance: compositoDeLotesDetalleInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'compositoDeLotesDetalle.label', default: 'CompositoDeLotesDetalle'), compositoDeLotesDetalleInstance.id])
        redirect(action: "show", id: compositoDeLotesDetalleInstance.id)
    }

    def delete(Long id) {
        def compositoDeLotesDetalleInstance = CompositoDeLotesDetalle.get(id)
        if (!compositoDeLotesDetalleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'compositoDeLotesDetalle.label', default: 'CompositoDeLotesDetalle'), id])
            redirect(action: "list")
            return
        }

        try {
            compositoDeLotesDetalleInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'compositoDeLotesDetalle.label', default: 'CompositoDeLotesDetalle'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'compositoDeLotesDetalle.label', default: 'CompositoDeLotesDetalle'), id])
            redirect(action: "show", id: id)
        }
    }
}
