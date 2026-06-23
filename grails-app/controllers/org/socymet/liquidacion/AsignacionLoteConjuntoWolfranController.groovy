package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class AsignacionLoteConjuntoWolfranController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [asignacionLoteConjuntoWolfranInstanceList: AsignacionLoteConjuntoWolfran.list(params), asignacionLoteConjuntoWolfranInstanceTotal: AsignacionLoteConjuntoWolfran.count()]
    }

    def create() {
        [asignacionLoteConjuntoWolfranInstance: new AsignacionLoteConjuntoWolfran(params)]
    }

    def save() {
        def asignacionLoteConjuntoWolfranInstance = new AsignacionLoteConjuntoWolfran(params)
        if (!asignacionLoteConjuntoWolfranInstance.save(flush: true)) {
            render(view: "create", model: [asignacionLoteConjuntoWolfranInstance: asignacionLoteConjuntoWolfranInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'asignacionLoteConjuntoWolfran.label', default: 'AsignacionLoteConjuntoWolfran'), asignacionLoteConjuntoWolfranInstance.id])
        redirect(action: "show", id: asignacionLoteConjuntoWolfranInstance.id)
    }

    def show(Long id) {
        def asignacionLoteConjuntoWolfranInstance = AsignacionLoteConjuntoWolfran.get(id)
        if (!asignacionLoteConjuntoWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'asignacionLoteConjuntoWolfran.label', default: 'AsignacionLoteConjuntoWolfran'), id])
            redirect(action: "list")
            return
        }

        [asignacionLoteConjuntoWolfranInstance: asignacionLoteConjuntoWolfranInstance]
    }

    def edit(Long id) {
        def asignacionLoteConjuntoWolfranInstance = AsignacionLoteConjuntoWolfran.get(id)
        if (!asignacionLoteConjuntoWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'asignacionLoteConjuntoWolfran.label', default: 'AsignacionLoteConjuntoWolfran'), id])
            redirect(action: "list")
            return
        }

        [asignacionLoteConjuntoWolfranInstance: asignacionLoteConjuntoWolfranInstance]
    }

    def update(Long id, Long version) {
        def asignacionLoteConjuntoWolfranInstance = AsignacionLoteConjuntoWolfran.get(id)
        if (!asignacionLoteConjuntoWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'asignacionLoteConjuntoWolfran.label', default: 'AsignacionLoteConjuntoWolfran'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (asignacionLoteConjuntoWolfranInstance.version > version) {
                asignacionLoteConjuntoWolfranInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'asignacionLoteConjuntoWolfran.label', default: 'AsignacionLoteConjuntoWolfran')] as Object[],
                        "Another user has updated this AsignacionLoteConjuntoWolfran while you were editing")
                render(view: "edit", model: [asignacionLoteConjuntoWolfranInstance: asignacionLoteConjuntoWolfranInstance])
                return
            }
        }

        asignacionLoteConjuntoWolfranInstance.properties = params

        if (!asignacionLoteConjuntoWolfranInstance.save(flush: true)) {
            render(view: "edit", model: [asignacionLoteConjuntoWolfranInstance: asignacionLoteConjuntoWolfranInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'asignacionLoteConjuntoWolfran.label', default: 'AsignacionLoteConjuntoWolfran'), asignacionLoteConjuntoWolfranInstance.id])
        redirect(action: "show", id: asignacionLoteConjuntoWolfranInstance.id)
    }

    def delete(Long id) {
        def asignacionLoteConjuntoWolfranInstance = AsignacionLoteConjuntoWolfran.get(id)
        if (!asignacionLoteConjuntoWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'asignacionLoteConjuntoWolfran.label', default: 'AsignacionLoteConjuntoWolfran'), id])
            redirect(action: "list")
            return
        }

        try {
            asignacionLoteConjuntoWolfranInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'asignacionLoteConjuntoWolfran.label', default: 'AsignacionLoteConjuntoWolfran'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'asignacionLoteConjuntoWolfran.label', default: 'AsignacionLoteConjuntoWolfran'), id])
            redirect(action: "show", id: id)
        }
    }
}
