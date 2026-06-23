package org.socymet.proveedor
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class BonoEmpresaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [bonoInstanceList: BonoEmpresa.list(params), bonoInstanceTotal: BonoEmpresa.count()]
    }

    def create() {
        [bonoInstance: new BonoEmpresa(params)]
    }

    def save() {
        def bonoInstance = new BonoEmpresa(params)
        if (!bonoInstance.save(flush: true)) {
            render(view: "create", model: [bonoInstance: bonoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'bono.label', default: 'BonoEmpresa'), bonoInstance.id])
        redirect(action: "show", id: bonoInstance.id)
    }

    def show(Long id) {
        def bonoInstance = BonoEmpresa.get(id)
        if (!bonoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bono.label', default: 'BonoEmpresa'), id])
            redirect(action: "list")
            return
        }

        [bonoInstance: bonoInstance]
    }

    def edit(Long id) {
        def bonoInstance = BonoEmpresa.get(id)
        if (!bonoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bono.label', default: 'BonoEmpresa'), id])
            redirect(action: "list")
            return
        }

        [bonoInstance: bonoInstance]
    }

    def update(Long id, Long version) {
        def bonoInstance = BonoEmpresa.get(id)
        if (!bonoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bono.label', default: 'BonoEmpresa'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (bonoInstance.version > version) {
                bonoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'bono.label', default: 'BonoEmpresa')] as Object[],
                        "Another user has updated this BonoEmpresa while you were editing")
                render(view: "edit", model: [bonoInstance: bonoInstance])
                return
            }
        }

        bonoInstance.properties = params

        if (!bonoInstance.save(flush: true)) {
            render(view: "edit", model: [bonoInstance: bonoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'bono.label', default: 'BonoEmpresa'), bonoInstance.id])
        redirect(action: "show", id: bonoInstance.id)
    }

    def delete(Long id) {
        def bonoInstance = BonoEmpresa.get(id)
        if (!bonoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bono.label', default: 'BonoEmpresa'), id])
            redirect(action: "list")
            return
        }

        try {
            bonoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'bono.label', default: 'BonoEmpresa'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'bono.label', default: 'BonoEmpresa'), id])
            redirect(action: "show", id: id)
        }
    }

}
