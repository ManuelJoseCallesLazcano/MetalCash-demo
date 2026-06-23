package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class AsignacionLoteConjuntoComplejoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [asignacionLoteConjuntoComplejoInstanceList: AsignacionLoteConjuntoComplejo.list(params), asignacionLoteConjuntoComplejoInstanceTotal: AsignacionLoteConjuntoComplejo.count()]
    }

    def create() {
        [asignacionLoteConjuntoComplejoInstance: new AsignacionLoteConjuntoComplejo(params)]
    }

    def save() {
        def asignacionLoteConjuntoComplejoInstance = new AsignacionLoteConjuntoComplejo(params)
        if (!asignacionLoteConjuntoComplejoInstance.save(flush: true)) {
            render(view: "create", model: [asignacionLoteConjuntoComplejoInstance: asignacionLoteConjuntoComplejoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'asignacionLoteConjuntoComplejo.label', default: 'AsignacionLoteConjuntoComplejo'), asignacionLoteConjuntoComplejoInstance.id])
        redirect(action: "show", id: asignacionLoteConjuntoComplejoInstance.id)
    }

    def show(Long id) {
        def asignacionLoteConjuntoComplejoInstance = AsignacionLoteConjuntoComplejo.get(id)
        if (!asignacionLoteConjuntoComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'asignacionLoteConjuntoComplejo.label', default: 'AsignacionLoteConjuntoComplejo'), id])
            redirect(action: "list")
            return
        }

        [asignacionLoteConjuntoComplejoInstance: asignacionLoteConjuntoComplejoInstance]
    }

    def edit(Long id) {
        def asignacionLoteConjuntoComplejoInstance = AsignacionLoteConjuntoComplejo.get(id)
        if (!asignacionLoteConjuntoComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'asignacionLoteConjuntoComplejo.label', default: 'AsignacionLoteConjuntoComplejo'), id])
            redirect(action: "list")
            return
        }

        [asignacionLoteConjuntoComplejoInstance: asignacionLoteConjuntoComplejoInstance]
    }

    def update(Long id, Long version) {
        def asignacionLoteConjuntoComplejoInstance = AsignacionLoteConjuntoComplejo.get(id)
        if (!asignacionLoteConjuntoComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'asignacionLoteConjuntoComplejo.label', default: 'AsignacionLoteConjuntoComplejo'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (asignacionLoteConjuntoComplejoInstance.version > version) {
                asignacionLoteConjuntoComplejoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'asignacionLoteConjuntoComplejo.label', default: 'AsignacionLoteConjuntoComplejo')] as Object[],
                        "Another user has updated this AsignacionLoteConjuntoComplejo while you were editing")
                render(view: "edit", model: [asignacionLoteConjuntoComplejoInstance: asignacionLoteConjuntoComplejoInstance])
                return
            }
        }

        asignacionLoteConjuntoComplejoInstance.properties = params

        if (!asignacionLoteConjuntoComplejoInstance.save(flush: true)) {
            render(view: "edit", model: [asignacionLoteConjuntoComplejoInstance: asignacionLoteConjuntoComplejoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'asignacionLoteConjuntoComplejo.label', default: 'AsignacionLoteConjuntoComplejo'), asignacionLoteConjuntoComplejoInstance.id])
        redirect(action: "show", id: asignacionLoteConjuntoComplejoInstance.id)
    }

    def delete(Long id) {
        def asignacionLoteConjuntoComplejoInstance = AsignacionLoteConjuntoComplejo.get(id)
        if (!asignacionLoteConjuntoComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'asignacionLoteConjuntoComplejo.label', default: 'AsignacionLoteConjuntoComplejo'), id])
            redirect(action: "list")
            return
        }

        try {
            asignacionLoteConjuntoComplejoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'asignacionLoteConjuntoComplejo.label', default: 'AsignacionLoteConjuntoComplejo'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'asignacionLoteConjuntoComplejo.label', default: 'AsignacionLoteConjuntoComplejo'), id])
            redirect(action: "show", id: id)
        }
    }
}
