package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class AsignacionLoteConjuntoPlataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [asignacionLoteConjuntoPlataInstanceList: AsignacionLoteConjuntoPlata.list(params), asignacionLoteConjuntoPlataInstanceTotal: AsignacionLoteConjuntoPlata.count()]
    }

    def create() {
        [asignacionLoteConjuntoPlataInstance: new AsignacionLoteConjuntoPlata(params)]
    }

    def save() {
        def asignacionLoteConjuntoPlataInstance = new AsignacionLoteConjuntoPlata(params)
        if (!asignacionLoteConjuntoPlataInstance.save(flush: true)) {
            render(view: "create", model: [asignacionLoteConjuntoPlataInstance: asignacionLoteConjuntoPlataInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'asignacionLoteConjuntoPlata.label', default: 'AsignacionLoteConjuntoPlata'), asignacionLoteConjuntoPlataInstance.id])
        redirect(action: "show", id: asignacionLoteConjuntoPlataInstance.id)
    }

    def show(Long id) {
        def asignacionLoteConjuntoPlataInstance = AsignacionLoteConjuntoPlata.get(id)
        if (!asignacionLoteConjuntoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'asignacionLoteConjuntoPlata.label', default: 'AsignacionLoteConjuntoPlata'), id])
            redirect(action: "list")
            return
        }

        [asignacionLoteConjuntoPlataInstance: asignacionLoteConjuntoPlataInstance]
    }

    def edit(Long id) {
        def asignacionLoteConjuntoPlataInstance = AsignacionLoteConjuntoPlata.get(id)
        if (!asignacionLoteConjuntoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'asignacionLoteConjuntoPlata.label', default: 'AsignacionLoteConjuntoPlata'), id])
            redirect(action: "list")
            return
        }

        [asignacionLoteConjuntoPlataInstance: asignacionLoteConjuntoPlataInstance]
    }

    def update(Long id, Long version) {
        def asignacionLoteConjuntoPlataInstance = AsignacionLoteConjuntoPlata.get(id)
        if (!asignacionLoteConjuntoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'asignacionLoteConjuntoPlata.label', default: 'AsignacionLoteConjuntoPlata'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (asignacionLoteConjuntoPlataInstance.version > version) {
                asignacionLoteConjuntoPlataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'asignacionLoteConjuntoPlata.label', default: 'AsignacionLoteConjuntoPlata')] as Object[],
                        "Another user has updated this AsignacionLoteConjuntoPlata while you were editing")
                render(view: "edit", model: [asignacionLoteConjuntoPlataInstance: asignacionLoteConjuntoPlataInstance])
                return
            }
        }

        asignacionLoteConjuntoPlataInstance.properties = params

        if (!asignacionLoteConjuntoPlataInstance.save(flush: true)) {
            render(view: "edit", model: [asignacionLoteConjuntoPlataInstance: asignacionLoteConjuntoPlataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'asignacionLoteConjuntoPlata.label', default: 'AsignacionLoteConjuntoPlata'), asignacionLoteConjuntoPlataInstance.id])
        redirect(action: "show", id: asignacionLoteConjuntoPlataInstance.id)
    }

    def delete(Long id) {
        def asignacionLoteConjuntoPlataInstance = AsignacionLoteConjuntoPlata.get(id)
        if (!asignacionLoteConjuntoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'asignacionLoteConjuntoPlata.label', default: 'AsignacionLoteConjuntoPlata'), id])
            redirect(action: "list")
            return
        }

        try {
            asignacionLoteConjuntoPlataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'asignacionLoteConjuntoPlata.label', default: 'AsignacionLoteConjuntoPlata'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'asignacionLoteConjuntoPlata.label', default: 'AsignacionLoteConjuntoPlata'), id])
            redirect(action: "show", id: id)
        }
    }
}
