package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class EliminacionLoteConjuntoAntimonioController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [eliminacionLoteConjuntoAntimonioInstanceList: EliminacionLoteConjuntoAntimonio.list(params), eliminacionLoteConjuntoAntimonioInstanceTotal: EliminacionLoteConjuntoAntimonio.count()]
    }

    def create() {
        [eliminacionLoteConjuntoAntimonioInstance: new EliminacionLoteConjuntoAntimonio(params)]
    }

    def save() {
        def eliminacionLoteConjuntoAntimonioInstance = new EliminacionLoteConjuntoAntimonio(params)
        if (!eliminacionLoteConjuntoAntimonioInstance.save(flush: true)) {
            render(view: "create", model: [eliminacionLoteConjuntoAntimonioInstance: eliminacionLoteConjuntoAntimonioInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'eliminacionLoteConjuntoAntimonio.label', default: 'EliminacionLoteConjuntoAntimonio'), eliminacionLoteConjuntoAntimonioInstance.id])
        redirect(action: "show", id: eliminacionLoteConjuntoAntimonioInstance.id)
    }

    def show(Long id) {
        def eliminacionLoteConjuntoAntimonioInstance = EliminacionLoteConjuntoAntimonio.get(id)
        if (!eliminacionLoteConjuntoAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'eliminacionLoteConjuntoAntimonio.label', default: 'EliminacionLoteConjuntoAntimonio'), id])
            redirect(action: "list")
            return
        }

        [eliminacionLoteConjuntoAntimonioInstance: eliminacionLoteConjuntoAntimonioInstance]
    }

    def edit(Long id) {
        def eliminacionLoteConjuntoAntimonioInstance = EliminacionLoteConjuntoAntimonio.get(id)
        if (!eliminacionLoteConjuntoAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'eliminacionLoteConjuntoAntimonio.label', default: 'EliminacionLoteConjuntoAntimonio'), id])
            redirect(action: "list")
            return
        }

        [eliminacionLoteConjuntoAntimonioInstance: eliminacionLoteConjuntoAntimonioInstance]
    }

    def update(Long id, Long version) {
        def eliminacionLoteConjuntoAntimonioInstance = EliminacionLoteConjuntoAntimonio.get(id)
        if (!eliminacionLoteConjuntoAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'eliminacionLoteConjuntoAntimonio.label', default: 'EliminacionLoteConjuntoAntimonio'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (eliminacionLoteConjuntoAntimonioInstance.version > version) {
                eliminacionLoteConjuntoAntimonioInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'eliminacionLoteConjuntoAntimonio.label', default: 'EliminacionLoteConjuntoAntimonio')] as Object[],
                        "Another user has updated this EliminacionLoteConjuntoAntimonio while you were editing")
                render(view: "edit", model: [eliminacionLoteConjuntoAntimonioInstance: eliminacionLoteConjuntoAntimonioInstance])
                return
            }
        }

        eliminacionLoteConjuntoAntimonioInstance.properties = params

        if (!eliminacionLoteConjuntoAntimonioInstance.save(flush: true)) {
            render(view: "edit", model: [eliminacionLoteConjuntoAntimonioInstance: eliminacionLoteConjuntoAntimonioInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'eliminacionLoteConjuntoAntimonio.label', default: 'EliminacionLoteConjuntoAntimonio'), eliminacionLoteConjuntoAntimonioInstance.id])
        redirect(action: "show", id: eliminacionLoteConjuntoAntimonioInstance.id)
    }

    def delete(Long id) {
        def eliminacionLoteConjuntoAntimonioInstance = EliminacionLoteConjuntoAntimonio.get(id)
        if (!eliminacionLoteConjuntoAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'eliminacionLoteConjuntoAntimonio.label', default: 'EliminacionLoteConjuntoAntimonio'), id])
            redirect(action: "list")
            return
        }

        try {
            eliminacionLoteConjuntoAntimonioInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'eliminacionLoteConjuntoAntimonio.label', default: 'EliminacionLoteConjuntoAntimonio'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'eliminacionLoteConjuntoAntimonio.label', default: 'EliminacionLoteConjuntoAntimonio'), id])
            redirect(action: "show", id: id)
        }
    }
}
