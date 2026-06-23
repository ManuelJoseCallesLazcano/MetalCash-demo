package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class AsignacionLoteConjuntoAntimonioController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [asignacionLoteConjuntoAntimonioInstanceList: AsignacionLoteConjuntoAntimonio.list(params), asignacionLoteConjuntoAntimonioInstanceTotal: AsignacionLoteConjuntoAntimonio.count()]
    }

    def create() {
        [asignacionLoteConjuntoAntimonioInstance: new AsignacionLoteConjuntoAntimonio(params)]
    }

    def save() {
        def asignacionLoteConjuntoAntimonioInstance = new AsignacionLoteConjuntoAntimonio(params)
        if (!asignacionLoteConjuntoAntimonioInstance.save(flush: true)) {
            render(view: "create", model: [asignacionLoteConjuntoAntimonioInstance: asignacionLoteConjuntoAntimonioInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'asignacionLoteConjuntoAntimonio.label', default: 'AsignacionLoteConjuntoAntimonio'), asignacionLoteConjuntoAntimonioInstance.id])
        redirect(action: "show", id: asignacionLoteConjuntoAntimonioInstance.id)
    }

    def show(Long id) {
        def asignacionLoteConjuntoAntimonioInstance = AsignacionLoteConjuntoAntimonio.get(id)
        if (!asignacionLoteConjuntoAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'asignacionLoteConjuntoAntimonio.label', default: 'AsignacionLoteConjuntoAntimonio'), id])
            redirect(action: "list")
            return
        }

        [asignacionLoteConjuntoAntimonioInstance: asignacionLoteConjuntoAntimonioInstance]
    }

    def edit(Long id) {
        def asignacionLoteConjuntoAntimonioInstance = AsignacionLoteConjuntoAntimonio.get(id)
        if (!asignacionLoteConjuntoAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'asignacionLoteConjuntoAntimonio.label', default: 'AsignacionLoteConjuntoAntimonio'), id])
            redirect(action: "list")
            return
        }

        [asignacionLoteConjuntoAntimonioInstance: asignacionLoteConjuntoAntimonioInstance]
    }

    def update(Long id, Long version) {
        def asignacionLoteConjuntoAntimonioInstance = AsignacionLoteConjuntoAntimonio.get(id)
        if (!asignacionLoteConjuntoAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'asignacionLoteConjuntoAntimonio.label', default: 'AsignacionLoteConjuntoAntimonio'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (asignacionLoteConjuntoAntimonioInstance.version > version) {
                asignacionLoteConjuntoAntimonioInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'asignacionLoteConjuntoAntimonio.label', default: 'AsignacionLoteConjuntoAntimonio')] as Object[],
                        "Another user has updated this AsignacionLoteConjuntoAntimonio while you were editing")
                render(view: "edit", model: [asignacionLoteConjuntoAntimonioInstance: asignacionLoteConjuntoAntimonioInstance])
                return
            }
        }

        asignacionLoteConjuntoAntimonioInstance.properties = params

        if (!asignacionLoteConjuntoAntimonioInstance.save(flush: true)) {
            render(view: "edit", model: [asignacionLoteConjuntoAntimonioInstance: asignacionLoteConjuntoAntimonioInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'asignacionLoteConjuntoAntimonio.label', default: 'AsignacionLoteConjuntoAntimonio'), asignacionLoteConjuntoAntimonioInstance.id])
        redirect(action: "show", id: asignacionLoteConjuntoAntimonioInstance.id)
    }

    def delete(Long id) {
        def asignacionLoteConjuntoAntimonioInstance = AsignacionLoteConjuntoAntimonio.get(id)
        if (!asignacionLoteConjuntoAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'asignacionLoteConjuntoAntimonio.label', default: 'AsignacionLoteConjuntoAntimonio'), id])
            redirect(action: "list")
            return
        }

        try {
            asignacionLoteConjuntoAntimonioInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'asignacionLoteConjuntoAntimonio.label', default: 'AsignacionLoteConjuntoAntimonio'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'asignacionLoteConjuntoAntimonio.label', default: 'AsignacionLoteConjuntoAntimonio'), id])
            redirect(action: "show", id: id)
        }
    }
}
