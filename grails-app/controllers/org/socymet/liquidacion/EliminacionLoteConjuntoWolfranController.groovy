package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class EliminacionLoteConjuntoWolfranController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [eliminacionLoteConjuntoWolfranInstanceList: EliminacionLoteConjuntoWolfran.list(params), eliminacionLoteConjuntoWolfranInstanceTotal: EliminacionLoteConjuntoWolfran.count()]
    }

    def create() {
        [eliminacionLoteConjuntoWolfranInstance: new EliminacionLoteConjuntoWolfran(params)]
    }

    def save() {
        def eliminacionLoteConjuntoWolfranInstance = new EliminacionLoteConjuntoWolfran(params)
        if (!eliminacionLoteConjuntoWolfranInstance.save(flush: true)) {
            render(view: "create", model: [eliminacionLoteConjuntoWolfranInstance: eliminacionLoteConjuntoWolfranInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'eliminacionLoteConjuntoWolfran.label', default: 'EliminacionLoteConjuntoWolfran'), eliminacionLoteConjuntoWolfranInstance.id])
        redirect(action: "show", id: eliminacionLoteConjuntoWolfranInstance.id)
    }

    def show(Long id) {
        def eliminacionLoteConjuntoWolfranInstance = EliminacionLoteConjuntoWolfran.get(id)
        if (!eliminacionLoteConjuntoWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'eliminacionLoteConjuntoWolfran.label', default: 'EliminacionLoteConjuntoWolfran'), id])
            redirect(action: "list")
            return
        }

        [eliminacionLoteConjuntoWolfranInstance: eliminacionLoteConjuntoWolfranInstance]
    }

    def edit(Long id) {
        def eliminacionLoteConjuntoWolfranInstance = EliminacionLoteConjuntoWolfran.get(id)
        if (!eliminacionLoteConjuntoWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'eliminacionLoteConjuntoWolfran.label', default: 'EliminacionLoteConjuntoWolfran'), id])
            redirect(action: "list")
            return
        }

        [eliminacionLoteConjuntoWolfranInstance: eliminacionLoteConjuntoWolfranInstance]
    }

    def update(Long id, Long version) {
        def eliminacionLoteConjuntoWolfranInstance = EliminacionLoteConjuntoWolfran.get(id)
        if (!eliminacionLoteConjuntoWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'eliminacionLoteConjuntoWolfran.label', default: 'EliminacionLoteConjuntoWolfran'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (eliminacionLoteConjuntoWolfranInstance.version > version) {
                eliminacionLoteConjuntoWolfranInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'eliminacionLoteConjuntoWolfran.label', default: 'EliminacionLoteConjuntoWolfran')] as Object[],
                        "Another user has updated this EliminacionLoteConjuntoWolfran while you were editing")
                render(view: "edit", model: [eliminacionLoteConjuntoWolfranInstance: eliminacionLoteConjuntoWolfranInstance])
                return
            }
        }

        eliminacionLoteConjuntoWolfranInstance.properties = params

        if (!eliminacionLoteConjuntoWolfranInstance.save(flush: true)) {
            render(view: "edit", model: [eliminacionLoteConjuntoWolfranInstance: eliminacionLoteConjuntoWolfranInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'eliminacionLoteConjuntoWolfran.label', default: 'EliminacionLoteConjuntoWolfran'), eliminacionLoteConjuntoWolfranInstance.id])
        redirect(action: "show", id: eliminacionLoteConjuntoWolfranInstance.id)
    }

    def delete(Long id) {
        def eliminacionLoteConjuntoWolfranInstance = EliminacionLoteConjuntoWolfran.get(id)
        if (!eliminacionLoteConjuntoWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'eliminacionLoteConjuntoWolfran.label', default: 'EliminacionLoteConjuntoWolfran'), id])
            redirect(action: "list")
            return
        }

        try {
            eliminacionLoteConjuntoWolfranInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'eliminacionLoteConjuntoWolfran.label', default: 'EliminacionLoteConjuntoWolfran'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'eliminacionLoteConjuntoWolfran.label', default: 'EliminacionLoteConjuntoWolfran'), id])
            redirect(action: "show", id: id)
        }
    }
}
