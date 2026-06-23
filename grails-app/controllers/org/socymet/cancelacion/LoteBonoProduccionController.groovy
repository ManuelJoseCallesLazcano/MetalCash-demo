package org.socymet.cancelacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class LoteBonoProduccionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [loteBonoProduccionInstanceList: LoteBonoProduccion.list(params), loteBonoProduccionInstanceTotal: LoteBonoProduccion.count()]
    }

    def create() {
        [loteBonoProduccionInstance: new LoteBonoProduccion(params)]
    }

    def save() {
        def loteBonoProduccionInstance = new LoteBonoProduccion(params)
        if (!loteBonoProduccionInstance.save(flush: true)) {
            render(view: "create", model: [loteBonoProduccionInstance: loteBonoProduccionInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'loteBonoProduccion.label', default: 'LoteBonoProduccion'), loteBonoProduccionInstance.id])
        redirect(action: "show", id: loteBonoProduccionInstance.id)
    }

    def show(Long id) {
        def loteBonoProduccionInstance = LoteBonoProduccion.get(id)
        if (!loteBonoProduccionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'loteBonoProduccion.label', default: 'LoteBonoProduccion'), id])
            redirect(action: "list")
            return
        }

        [loteBonoProduccionInstance: loteBonoProduccionInstance]
    }

    def edit(Long id) {
        def loteBonoProduccionInstance = LoteBonoProduccion.get(id)
        if (!loteBonoProduccionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'loteBonoProduccion.label', default: 'LoteBonoProduccion'), id])
            redirect(action: "list")
            return
        }

        [loteBonoProduccionInstance: loteBonoProduccionInstance]
    }

    def update(Long id, Long version) {
        def loteBonoProduccionInstance = LoteBonoProduccion.get(id)
        if (!loteBonoProduccionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'loteBonoProduccion.label', default: 'LoteBonoProduccion'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (loteBonoProduccionInstance.version > version) {
                loteBonoProduccionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'loteBonoProduccion.label', default: 'LoteBonoProduccion')] as Object[],
                        "Another user has updated this LoteBonoProduccion while you were editing")
                render(view: "edit", model: [loteBonoProduccionInstance: loteBonoProduccionInstance])
                return
            }
        }

        loteBonoProduccionInstance.properties = params

        if (!loteBonoProduccionInstance.save(flush: true)) {
            render(view: "edit", model: [loteBonoProduccionInstance: loteBonoProduccionInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'loteBonoProduccion.label', default: 'LoteBonoProduccion'), loteBonoProduccionInstance.id])
        redirect(action: "show", id: loteBonoProduccionInstance.id)
    }

    def delete(Long id) {
        def loteBonoProduccionInstance = LoteBonoProduccion.get(id)
        if (!loteBonoProduccionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'loteBonoProduccion.label', default: 'LoteBonoProduccion'), id])
            redirect(action: "list")
            return
        }

        try {
            loteBonoProduccionInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'loteBonoProduccion.label', default: 'LoteBonoProduccion'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'loteBonoProduccion.label', default: 'LoteBonoProduccion'), id])
            redirect(action: "show", id: id)
        }
    }
}
