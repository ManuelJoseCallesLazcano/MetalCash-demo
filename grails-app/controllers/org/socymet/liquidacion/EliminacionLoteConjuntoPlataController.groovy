package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class EliminacionLoteConjuntoPlataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [eliminacionLoteConjuntoPlataInstanceList: EliminacionLoteConjuntoPlata.list(params), eliminacionLoteConjuntoPlataInstanceTotal: EliminacionLoteConjuntoPlata.count()]
    }

    def create() {
        [eliminacionLoteConjuntoPlataInstance: new EliminacionLoteConjuntoPlata(params)]
    }

    def save() {
        def eliminacionLoteConjuntoPlataInstance = new EliminacionLoteConjuntoPlata(params)
        if (!eliminacionLoteConjuntoPlataInstance.save(flush: true)) {
            render(view: "create", model: [eliminacionLoteConjuntoPlataInstance: eliminacionLoteConjuntoPlataInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'eliminacionLoteConjuntoPlata.label', default: 'EliminacionLoteConjuntoPlata'), eliminacionLoteConjuntoPlataInstance.id])
        redirect(action: "show", id: eliminacionLoteConjuntoPlataInstance.id)
    }

    def show(Long id) {
        def eliminacionLoteConjuntoPlataInstance = EliminacionLoteConjuntoPlata.get(id)
        if (!eliminacionLoteConjuntoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'eliminacionLoteConjuntoPlata.label', default: 'EliminacionLoteConjuntoPlata'), id])
            redirect(action: "list")
            return
        }

        [eliminacionLoteConjuntoPlataInstance: eliminacionLoteConjuntoPlataInstance]
    }

    def edit(Long id) {
        def eliminacionLoteConjuntoPlataInstance = EliminacionLoteConjuntoPlata.get(id)
        if (!eliminacionLoteConjuntoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'eliminacionLoteConjuntoPlata.label', default: 'EliminacionLoteConjuntoPlata'), id])
            redirect(action: "list")
            return
        }

        [eliminacionLoteConjuntoPlataInstance: eliminacionLoteConjuntoPlataInstance]
    }

    def update(Long id, Long version) {
        def eliminacionLoteConjuntoPlataInstance = EliminacionLoteConjuntoPlata.get(id)
        if (!eliminacionLoteConjuntoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'eliminacionLoteConjuntoPlata.label', default: 'EliminacionLoteConjuntoPlata'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (eliminacionLoteConjuntoPlataInstance.version > version) {
                eliminacionLoteConjuntoPlataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'eliminacionLoteConjuntoPlata.label', default: 'EliminacionLoteConjuntoPlata')] as Object[],
                        "Another user has updated this EliminacionLoteConjuntoPlata while you were editing")
                render(view: "edit", model: [eliminacionLoteConjuntoPlataInstance: eliminacionLoteConjuntoPlataInstance])
                return
            }
        }

        eliminacionLoteConjuntoPlataInstance.properties = params

        if (!eliminacionLoteConjuntoPlataInstance.save(flush: true)) {
            render(view: "edit", model: [eliminacionLoteConjuntoPlataInstance: eliminacionLoteConjuntoPlataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'eliminacionLoteConjuntoPlata.label', default: 'EliminacionLoteConjuntoPlata'), eliminacionLoteConjuntoPlataInstance.id])
        redirect(action: "show", id: eliminacionLoteConjuntoPlataInstance.id)
    }

    def delete(Long id) {
        def eliminacionLoteConjuntoPlataInstance = EliminacionLoteConjuntoPlata.get(id)
        if (!eliminacionLoteConjuntoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'eliminacionLoteConjuntoPlata.label', default: 'EliminacionLoteConjuntoPlata'), id])
            redirect(action: "list")
            return
        }

        try {
            eliminacionLoteConjuntoPlataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'eliminacionLoteConjuntoPlata.label', default: 'EliminacionLoteConjuntoPlata'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'eliminacionLoteConjuntoPlata.label', default: 'EliminacionLoteConjuntoPlata'), id])
            redirect(action: "show", id: id)
        }
    }
}
