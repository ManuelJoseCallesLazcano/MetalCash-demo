package org.socymet.cotizaciones
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class TerminosContratoPlomoPlataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [terminosContratoPlomoPlataInstanceList: TerminosContratoPlomoPlata.list(params), terminosContratoPlomoPlataInstanceTotal: TerminosContratoPlomoPlata.count()]
    }

    def create() {
        [terminosContratoPlomoPlataInstance: new TerminosContratoPlomoPlata(params)]
    }

    def save() {
        def terminosContratoPlomoPlataInstance = new TerminosContratoPlomoPlata(params)
        if (!terminosContratoPlomoPlataInstance.save(flush: true)) {
            render(view: "create", model: [terminosContratoPlomoPlataInstance: terminosContratoPlomoPlataInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'terminosContratoPlomoPlata.label', default: 'TerminosContratoPlomoPlata'), terminosContratoPlomoPlataInstance.id])
        redirect(action: "show", id: terminosContratoPlomoPlataInstance.id)
    }

    def show(Long id) {
        def terminosContratoPlomoPlataInstance = TerminosContratoPlomoPlata.get(id)
        if (!terminosContratoPlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'terminosContratoPlomoPlata.label', default: 'TerminosContratoPlomoPlata'), id])
            redirect(action: "list")
            return
        }

        [terminosContratoPlomoPlataInstance: terminosContratoPlomoPlataInstance]
    }

    def edit(Long id) {
        def terminosContratoPlomoPlataInstance = TerminosContratoPlomoPlata.get(id)
        if (!terminosContratoPlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'terminosContratoPlomoPlata.label', default: 'TerminosContratoPlomoPlata'), id])
            redirect(action: "list")
            return
        }

        [terminosContratoPlomoPlataInstance: terminosContratoPlomoPlataInstance]
    }

    def update(Long id, Long version) {
        def terminosContratoPlomoPlataInstance = TerminosContratoPlomoPlata.get(id)
        if (!terminosContratoPlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'terminosContratoPlomoPlata.label', default: 'TerminosContratoPlomoPlata'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (terminosContratoPlomoPlataInstance.version > version) {
                terminosContratoPlomoPlataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'terminosContratoPlomoPlata.label', default: 'TerminosContratoPlomoPlata')] as Object[],
                        "Another user has updated this TerminosContratoPlomoPlata while you were editing")
                render(view: "edit", model: [terminosContratoPlomoPlataInstance: terminosContratoPlomoPlataInstance])
                return
            }
        }

        terminosContratoPlomoPlataInstance.properties = params

        if (!terminosContratoPlomoPlataInstance.save(flush: true)) {
            render(view: "edit", model: [terminosContratoPlomoPlataInstance: terminosContratoPlomoPlataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'terminosContratoPlomoPlata.label', default: 'TerminosContratoPlomoPlata'), terminosContratoPlomoPlataInstance.id])
        redirect(action: "show", id: terminosContratoPlomoPlataInstance.id)
    }

    def delete(Long id) {
        def terminosContratoPlomoPlataInstance = TerminosContratoPlomoPlata.get(id)
        if (!terminosContratoPlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'terminosContratoPlomoPlata.label', default: 'TerminosContratoPlomoPlata'), id])
            redirect(action: "list")
            return
        }

        try {
            terminosContratoPlomoPlataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'terminosContratoPlomoPlata.label', default: 'TerminosContratoPlomoPlata'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'terminosContratoPlomoPlata.label', default: 'TerminosContratoPlomoPlata'), id])
            redirect(action: "show", id: id)
        }
    }
}
