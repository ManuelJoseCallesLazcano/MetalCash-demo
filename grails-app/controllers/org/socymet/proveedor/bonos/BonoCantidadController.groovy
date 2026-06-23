package org.socymet.proveedor.bonos
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class BonoCantidadController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [bonoCantidadInstanceList: BonoCantidad.list(params), bonoCantidadInstanceTotal: BonoCantidad.count()]
    }

    def create() {
        [bonoCantidadInstance: new BonoCantidad(params)]
    }

    def save() {
        def bonoCantidadInstance = new BonoCantidad(params)
        if (!bonoCantidadInstance.save(flush: true)) {
            render(view: "create", model: [bonoCantidadInstance: bonoCantidadInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'bonoCantidad.label', default: 'BonoCantidad'), bonoCantidadInstance.id])
        redirect(action: "show", id: bonoCantidadInstance.id)
    }

    def show(Long id) {
        def bonoCantidadInstance = BonoCantidad.get(id)
        if (!bonoCantidadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bonoCantidad.label', default: 'BonoCantidad'), id])
            redirect(action: "list")
            return
        }

        [bonoCantidadInstance: bonoCantidadInstance]
    }

    def edit(Long id) {
        def bonoCantidadInstance = BonoCantidad.get(id)
        if (!bonoCantidadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bonoCantidad.label', default: 'BonoCantidad'), id])
            redirect(action: "list")
            return
        }

        [bonoCantidadInstance: bonoCantidadInstance]
    }

    def update(Long id, Long version) {
        def bonoCantidadInstance = BonoCantidad.get(id)
        if (!bonoCantidadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bonoCantidad.label', default: 'BonoCantidad'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (bonoCantidadInstance.version > version) {
                bonoCantidadInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'bonoCantidad.label', default: 'BonoCantidad')] as Object[],
                        "Another user has updated this BonoCantidad while you were editing")
                render(view: "edit", model: [bonoCantidadInstance: bonoCantidadInstance])
                return
            }
        }

        bonoCantidadInstance.properties = params

        if (!bonoCantidadInstance.save(flush: true)) {
            render(view: "edit", model: [bonoCantidadInstance: bonoCantidadInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'bonoCantidad.label', default: 'BonoCantidad'), bonoCantidadInstance.id])
        redirect(action: "show", id: bonoCantidadInstance.id)
    }

    def delete(Long id) {
        def bonoCantidadInstance = BonoCantidad.get(id)
        if (!bonoCantidadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bonoCantidad.label', default: 'BonoCantidad'), id])
            redirect(action: "list")
            return
        }

        try {
            bonoCantidadInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'bonoCantidad.label', default: 'BonoCantidad'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'bonoCantidad.label', default: 'BonoCantidad'), id])
            redirect(action: "show", id: id)
        }
    }
}
