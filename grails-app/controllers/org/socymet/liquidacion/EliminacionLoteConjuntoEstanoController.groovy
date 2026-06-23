package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class EliminacionLoteConjuntoEstanoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [eliminacionLoteConjuntoEstanoInstanceList: EliminacionLoteConjuntoEstano.list(params), eliminacionLoteConjuntoEstanoInstanceTotal: EliminacionLoteConjuntoEstano.count()]
    }

    def create() {
        [eliminacionLoteConjuntoEstanoInstance: new EliminacionLoteConjuntoEstano(params)]
    }

    def save() {
        def eliminacionLoteConjuntoEstanoInstance = new EliminacionLoteConjuntoEstano(params)
        if (!eliminacionLoteConjuntoEstanoInstance.save(flush: true)) {
            render(view: "create", model: [eliminacionLoteConjuntoEstanoInstance: eliminacionLoteConjuntoEstanoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'eliminacionLoteConjuntoEstano.label', default: 'EliminacionLoteConjuntoEstano'), eliminacionLoteConjuntoEstanoInstance.id])
        redirect(action: "show", id: eliminacionLoteConjuntoEstanoInstance.id)
    }

    def show(Long id) {
        def eliminacionLoteConjuntoEstanoInstance = EliminacionLoteConjuntoEstano.get(id)
        if (!eliminacionLoteConjuntoEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'eliminacionLoteConjuntoEstano.label', default: 'EliminacionLoteConjuntoEstano'), id])
            redirect(action: "list")
            return
        }

        [eliminacionLoteConjuntoEstanoInstance: eliminacionLoteConjuntoEstanoInstance]
    }

    def edit(Long id) {
        def eliminacionLoteConjuntoEstanoInstance = EliminacionLoteConjuntoEstano.get(id)
        if (!eliminacionLoteConjuntoEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'eliminacionLoteConjuntoEstano.label', default: 'EliminacionLoteConjuntoEstano'), id])
            redirect(action: "list")
            return
        }

        [eliminacionLoteConjuntoEstanoInstance: eliminacionLoteConjuntoEstanoInstance]
    }

    def update(Long id, Long version) {
        def eliminacionLoteConjuntoEstanoInstance = EliminacionLoteConjuntoEstano.get(id)
        if (!eliminacionLoteConjuntoEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'eliminacionLoteConjuntoEstano.label', default: 'EliminacionLoteConjuntoEstano'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (eliminacionLoteConjuntoEstanoInstance.version > version) {
                eliminacionLoteConjuntoEstanoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'eliminacionLoteConjuntoEstano.label', default: 'EliminacionLoteConjuntoEstano')] as Object[],
                        "Another user has updated this EliminacionLoteConjuntoEstano while you were editing")
                render(view: "edit", model: [eliminacionLoteConjuntoEstanoInstance: eliminacionLoteConjuntoEstanoInstance])
                return
            }
        }

        eliminacionLoteConjuntoEstanoInstance.properties = params

        if (!eliminacionLoteConjuntoEstanoInstance.save(flush: true)) {
            render(view: "edit", model: [eliminacionLoteConjuntoEstanoInstance: eliminacionLoteConjuntoEstanoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'eliminacionLoteConjuntoEstano.label', default: 'EliminacionLoteConjuntoEstano'), eliminacionLoteConjuntoEstanoInstance.id])
        redirect(action: "show", id: eliminacionLoteConjuntoEstanoInstance.id)
    }

    def delete(Long id) {
        def eliminacionLoteConjuntoEstanoInstance = EliminacionLoteConjuntoEstano.get(id)
        if (!eliminacionLoteConjuntoEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'eliminacionLoteConjuntoEstano.label', default: 'EliminacionLoteConjuntoEstano'), id])
            redirect(action: "list")
            return
        }

        try {
            eliminacionLoteConjuntoEstanoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'eliminacionLoteConjuntoEstano.label', default: 'EliminacionLoteConjuntoEstano'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'eliminacionLoteConjuntoEstano.label', default: 'EliminacionLoteConjuntoEstano'), id])
            redirect(action: "show", id: id)
        }
    }
}
