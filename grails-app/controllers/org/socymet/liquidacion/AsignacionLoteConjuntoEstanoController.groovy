package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class AsignacionLoteConjuntoEstanoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [asignacionLoteConjuntoEstanoInstanceList: AsignacionLoteConjuntoEstano.list(params), asignacionLoteConjuntoEstanoInstanceTotal: AsignacionLoteConjuntoEstano.count()]
    }

    def create() {
        [asignacionLoteConjuntoEstanoInstance: new AsignacionLoteConjuntoEstano(params)]
    }

    def save() {
        def asignacionLoteConjuntoEstanoInstance = new AsignacionLoteConjuntoEstano(params)
        if (!asignacionLoteConjuntoEstanoInstance.save(flush: true)) {
            render(view: "create", model: [asignacionLoteConjuntoEstanoInstance: asignacionLoteConjuntoEstanoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'asignacionLoteConjuntoEstano.label', default: 'AsignacionLoteConjuntoEstano'), asignacionLoteConjuntoEstanoInstance.id])
        redirect(action: "show", id: asignacionLoteConjuntoEstanoInstance.id)
    }

    def show(Long id) {
        def asignacionLoteConjuntoEstanoInstance = AsignacionLoteConjuntoEstano.get(id)
        if (!asignacionLoteConjuntoEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'asignacionLoteConjuntoEstano.label', default: 'AsignacionLoteConjuntoEstano'), id])
            redirect(action: "list")
            return
        }

        [asignacionLoteConjuntoEstanoInstance: asignacionLoteConjuntoEstanoInstance]
    }

    def edit(Long id) {
        def asignacionLoteConjuntoEstanoInstance = AsignacionLoteConjuntoEstano.get(id)
        if (!asignacionLoteConjuntoEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'asignacionLoteConjuntoEstano.label', default: 'AsignacionLoteConjuntoEstano'), id])
            redirect(action: "list")
            return
        }

        [asignacionLoteConjuntoEstanoInstance: asignacionLoteConjuntoEstanoInstance]
    }

    def update(Long id, Long version) {
        def asignacionLoteConjuntoEstanoInstance = AsignacionLoteConjuntoEstano.get(id)
        if (!asignacionLoteConjuntoEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'asignacionLoteConjuntoEstano.label', default: 'AsignacionLoteConjuntoEstano'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (asignacionLoteConjuntoEstanoInstance.version > version) {
                asignacionLoteConjuntoEstanoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'asignacionLoteConjuntoEstano.label', default: 'AsignacionLoteConjuntoEstano')] as Object[],
                        "Another user has updated this AsignacionLoteConjuntoEstano while you were editing")
                render(view: "edit", model: [asignacionLoteConjuntoEstanoInstance: asignacionLoteConjuntoEstanoInstance])
                return
            }
        }

        asignacionLoteConjuntoEstanoInstance.properties = params

        if (!asignacionLoteConjuntoEstanoInstance.save(flush: true)) {
            render(view: "edit", model: [asignacionLoteConjuntoEstanoInstance: asignacionLoteConjuntoEstanoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'asignacionLoteConjuntoEstano.label', default: 'AsignacionLoteConjuntoEstano'), asignacionLoteConjuntoEstanoInstance.id])
        redirect(action: "show", id: asignacionLoteConjuntoEstanoInstance.id)
    }

    def delete(Long id) {
        def asignacionLoteConjuntoEstanoInstance = AsignacionLoteConjuntoEstano.get(id)
        if (!asignacionLoteConjuntoEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'asignacionLoteConjuntoEstano.label', default: 'AsignacionLoteConjuntoEstano'), id])
            redirect(action: "list")
            return
        }

        try {
            asignacionLoteConjuntoEstanoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'asignacionLoteConjuntoEstano.label', default: 'AsignacionLoteConjuntoEstano'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'asignacionLoteConjuntoEstano.label', default: 'AsignacionLoteConjuntoEstano'), id])
            redirect(action: "show", id: id)
        }
    }
}
