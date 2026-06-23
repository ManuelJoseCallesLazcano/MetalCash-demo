package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class ReimpresionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reimpresionInstanceList: Reimpresion.list(params), reimpresionInstanceTotal: Reimpresion.count()]
    }

    def create() {
        [reimpresionInstance: new Reimpresion(params)]
    }

    def save() {
        def reimpresionInstance = new Reimpresion(params)
        if (!reimpresionInstance.save(flush: true)) {
            render(view: "create", model: [reimpresionInstance: reimpresionInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reimpresion.label', default: 'Reimpresion'), reimpresionInstance.id])
        redirect(action: "show", id: reimpresionInstance.id)
    }

    def show(Long id) {
        def reimpresionInstance = Reimpresion.get(id)
        if (!reimpresionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reimpresion.label', default: 'Reimpresion'), id])
            redirect(action: "list")
            return
        }

        [reimpresionInstance: reimpresionInstance]
    }

    def edit(Long id) {
        def reimpresionInstance = Reimpresion.get(id)
        if (!reimpresionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reimpresion.label', default: 'Reimpresion'), id])
            redirect(action: "list")
            return
        }

        [reimpresionInstance: reimpresionInstance]
    }

    def update(Long id, Long version) {
        def reimpresionInstance = Reimpresion.get(id)
        if (!reimpresionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reimpresion.label', default: 'Reimpresion'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reimpresionInstance.version > version) {
                reimpresionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reimpresion.label', default: 'Reimpresion')] as Object[],
                        "Another user has updated this Reimpresion while you were editing")
                render(view: "edit", model: [reimpresionInstance: reimpresionInstance])
                return
            }
        }

        reimpresionInstance.properties = params

        if (!reimpresionInstance.save(flush: true)) {
            render(view: "edit", model: [reimpresionInstance: reimpresionInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reimpresion.label', default: 'Reimpresion'), reimpresionInstance.id])
        redirect(action: "show", id: reimpresionInstance.id)
    }

    def delete(Long id) {
        def reimpresionInstance = Reimpresion.get(id)
        if (!reimpresionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reimpresion.label', default: 'Reimpresion'), id])
            redirect(action: "list")
            return
        }

        try {
            reimpresionInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reimpresion.label', default: 'Reimpresion'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reimpresion.label', default: 'Reimpresion'), id])
            redirect(action: "show", id: id)
        }
    }
}
