package org.socymet.proveedor
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class PruebaTablaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [pruebaTablaInstanceList: PruebaTabla.list(params), pruebaTablaInstanceTotal: PruebaTabla.count()]
    }

    def create() {
        [pruebaTablaInstance: new PruebaTabla(params)]
    }

    def save() {
        def pruebaTablaInstance = new PruebaTabla(params)
        if (!pruebaTablaInstance.save(flush: true)) {
            render(view: "create", model: [pruebaTablaInstance: pruebaTablaInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'pruebaTabla.label', default: 'PruebaTabla'), pruebaTablaInstance.id])
        redirect(action: "show", id: pruebaTablaInstance.id)
    }

    def show(Long id) {
        def pruebaTablaInstance = PruebaTabla.get(id)
        if (!pruebaTablaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pruebaTabla.label', default: 'PruebaTabla'), id])
            redirect(action: "list")
            return
        }

        [pruebaTablaInstance: pruebaTablaInstance]
    }

    def edit(Long id) {
        def pruebaTablaInstance = PruebaTabla.get(id)
        if (!pruebaTablaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pruebaTabla.label', default: 'PruebaTabla'), id])
            redirect(action: "list")
            return
        }

        [pruebaTablaInstance: pruebaTablaInstance]
    }

    def update(Long id, Long version) {
        def pruebaTablaInstance = PruebaTabla.get(id)
        if (!pruebaTablaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pruebaTabla.label', default: 'PruebaTabla'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (pruebaTablaInstance.version > version) {
                pruebaTablaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'pruebaTabla.label', default: 'PruebaTabla')] as Object[],
                        "Another user has updated this PruebaTabla while you were editing")
                render(view: "edit", model: [pruebaTablaInstance: pruebaTablaInstance])
                return
            }
        }

        pruebaTablaInstance.properties = params

        if (!pruebaTablaInstance.save(flush: true)) {
            render(view: "edit", model: [pruebaTablaInstance: pruebaTablaInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'pruebaTabla.label', default: 'PruebaTabla'), pruebaTablaInstance.id])
        redirect(action: "show", id: pruebaTablaInstance.id)
    }

    def delete(Long id) {
        def pruebaTablaInstance = PruebaTabla.get(id)
        if (!pruebaTablaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pruebaTabla.label', default: 'PruebaTabla'), id])
            redirect(action: "list")
            return
        }

        try {
            pruebaTablaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'pruebaTabla.label', default: 'PruebaTabla'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'pruebaTabla.label', default: 'PruebaTabla'), id])
            redirect(action: "show", id: id)
        }
    }
}
