package org.socymet.proveedor.bonos
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class BonoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [bonoInstanceList: Bono.list(params), bonoInstanceTotal: Bono.count()]
    }

    def create() {
        [bonoInstance: new Bono(params)]
    }

    def save() {
        def bonoInstance = new Bono(params)
        if (!bonoInstance.save(flush: true)) {
            render(view: "create", model: [bonoInstance: bonoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'bono.label', default: 'Bono'), bonoInstance.id])
        redirect(action: "show", id: bonoInstance.id)
    }

    def show(Long id) {
        def bonoInstance = Bono.get(id)
        if (!bonoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bono.label', default: 'Bono'), id])
            redirect(action: "list")
            return
        }

        [bonoInstance: bonoInstance]
    }

    def edit(Long id) {
        def bonoInstance = Bono.get(id)
        if (!bonoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bono.label', default: 'Bono'), id])
            redirect(action: "list")
            return
        }

        [bonoInstance: bonoInstance]
    }

    def update(Long id, Long version) {
        def bonoInstance = Bono.get(id)
        if (!bonoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bono.label', default: 'Bono'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (bonoInstance.version > version) {
                bonoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'bono.label', default: 'Bono')] as Object[],
                          "Another user has updated this Bono while you were editing")
                render(view: "edit", model: [bonoInstance: bonoInstance])
                return
            }
        }

        bonoInstance.properties = params

        if (!bonoInstance.save(flush: true)) {
            render(view: "edit", model: [bonoInstance: bonoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'bono.label', default: 'Bono'), bonoInstance.id])
        redirect(action: "show", id: bonoInstance.id)
    }

    def delete(Long id) {
        def bonoInstance = Bono.get(id)
        if (!bonoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bono.label', default: 'Bono'), id])
            redirect(action: "list")
            return
        }

        try {
            bonoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'bono.label', default: 'Bono'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'bono.label', default: 'Bono'), id])
            redirect(action: "show", id: id)
        }
    }
}
