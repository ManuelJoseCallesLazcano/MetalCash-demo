package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class EliminacionLoteConjuntoComplejoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [eliminacionLoteConjuntoComplejoInstanceList: EliminacionLoteConjuntoComplejo.list(params), eliminacionLoteConjuntoComplejoInstanceTotal: EliminacionLoteConjuntoComplejo.count()]
    }

    def create() {
        [eliminacionLoteConjuntoComplejoInstance: new EliminacionLoteConjuntoComplejo(params)]
    }

    def save() {
        def eliminacionLoteConjuntoComplejoInstance = new EliminacionLoteConjuntoComplejo(params)
        if (!eliminacionLoteConjuntoComplejoInstance.save(flush: true)) {
            render(view: "create", model: [eliminacionLoteConjuntoComplejoInstance: eliminacionLoteConjuntoComplejoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'eliminacionLoteConjuntoComplejo.label', default: 'EliminacionLoteConjuntoComplejo'), eliminacionLoteConjuntoComplejoInstance.id])
        redirect(action: "show", id: eliminacionLoteConjuntoComplejoInstance.id)
    }

    def show(Long id) {
        def eliminacionLoteConjuntoComplejoInstance = EliminacionLoteConjuntoComplejo.get(id)
        if (!eliminacionLoteConjuntoComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'eliminacionLoteConjuntoComplejo.label', default: 'EliminacionLoteConjuntoComplejo'), id])
            redirect(action: "list")
            return
        }

        [eliminacionLoteConjuntoComplejoInstance: eliminacionLoteConjuntoComplejoInstance]
    }

    def edit(Long id) {
        def eliminacionLoteConjuntoComplejoInstance = EliminacionLoteConjuntoComplejo.get(id)
        if (!eliminacionLoteConjuntoComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'eliminacionLoteConjuntoComplejo.label', default: 'EliminacionLoteConjuntoComplejo'), id])
            redirect(action: "list")
            return
        }

        [eliminacionLoteConjuntoComplejoInstance: eliminacionLoteConjuntoComplejoInstance]
    }

    def update(Long id, Long version) {
        def eliminacionLoteConjuntoComplejoInstance = EliminacionLoteConjuntoComplejo.get(id)
        if (!eliminacionLoteConjuntoComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'eliminacionLoteConjuntoComplejo.label', default: 'EliminacionLoteConjuntoComplejo'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (eliminacionLoteConjuntoComplejoInstance.version > version) {
                eliminacionLoteConjuntoComplejoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'eliminacionLoteConjuntoComplejo.label', default: 'EliminacionLoteConjuntoComplejo')] as Object[],
                        "Another user has updated this EliminacionLoteConjuntoComplejo while you were editing")
                render(view: "edit", model: [eliminacionLoteConjuntoComplejoInstance: eliminacionLoteConjuntoComplejoInstance])
                return
            }
        }

        eliminacionLoteConjuntoComplejoInstance.properties = params

        if (!eliminacionLoteConjuntoComplejoInstance.save(flush: true)) {
            render(view: "edit", model: [eliminacionLoteConjuntoComplejoInstance: eliminacionLoteConjuntoComplejoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'eliminacionLoteConjuntoComplejo.label', default: 'EliminacionLoteConjuntoComplejo'), eliminacionLoteConjuntoComplejoInstance.id])
        redirect(action: "show", id: eliminacionLoteConjuntoComplejoInstance.id)
    }

    def delete(Long id) {
        def eliminacionLoteConjuntoComplejoInstance = EliminacionLoteConjuntoComplejo.get(id)
        if (!eliminacionLoteConjuntoComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'eliminacionLoteConjuntoComplejo.label', default: 'EliminacionLoteConjuntoComplejo'), id])
            redirect(action: "list")
            return
        }

        try {
            eliminacionLoteConjuntoComplejoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'eliminacionLoteConjuntoComplejo.label', default: 'EliminacionLoteConjuntoComplejo'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'eliminacionLoteConjuntoComplejo.label', default: 'EliminacionLoteConjuntoComplejo'), id])
            redirect(action: "show", id: id)
        }
    }
}
