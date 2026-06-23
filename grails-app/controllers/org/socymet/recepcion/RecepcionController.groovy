package org.socymet.recepcion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class RecepcionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [recepcionInstanceList: Recepcion.list(params), recepcionInstanceTotal: Recepcion.count()]
    }

    def create() {
        [recepcionInstance: new Recepcion(params)]
    }

    def save() {
        def recepcionInstance = new Recepcion(params)
        if (!recepcionInstance.save(flush: true)) {
            render(view: "create", model: [recepcionInstance: recepcionInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'recepcion.label', default: 'Recepcion'), recepcionInstance.id])
        redirect(action: "show", id: recepcionInstance.id)
    }

    def show(Long id) {
        def recepcionInstance = Recepcion.get(id)
        if (!recepcionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcion.label', default: 'Recepcion'), id])
            redirect(action: "list")
            return
        }

        [recepcionInstance: recepcionInstance]
    }

    def edit(Long id) {
        def recepcionInstance = Recepcion.get(id)
        if (!recepcionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcion.label', default: 'Recepcion'), id])
            redirect(action: "list")
            return
        }

        [recepcionInstance: recepcionInstance]
    }

    def update(Long id, Long version) {
        def recepcionInstance = Recepcion.get(id)
        if (!recepcionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcion.label', default: 'Recepcion'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (recepcionInstance.version > version) {
                recepcionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'recepcion.label', default: 'Recepcion')] as Object[],
                        "Another user has updated this Recepcion while you were editing")
                render(view: "edit", model: [recepcionInstance: recepcionInstance])
                return
            }
        }

        recepcionInstance.properties = params

        if (!recepcionInstance.save(flush: true)) {
            render(view: "edit", model: [recepcionInstance: recepcionInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'recepcion.label', default: 'Recepcion'), recepcionInstance.id])
        redirect(action: "show", id: recepcionInstance.id)
    }

    def delete(Long id) {
        def recepcionInstance = Recepcion.get(id)
        if (!recepcionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcion.label', default: 'Recepcion'), id])
            redirect(action: "list")
            return
        }

        try {
            recepcionInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'recepcion.label', default: 'Recepcion'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'recepcion.label', default: 'Recepcion'), id])
            redirect(action: "show", id: id)
        }
    }
}
