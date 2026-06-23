package org.socymet.cancelacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class AcumulacionBonoProduccionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [acumulacionBonoProduccionInstanceList: AcumulacionBonoProduccion.list(params), acumulacionBonoProduccionInstanceTotal: AcumulacionBonoProduccion.count()]
    }

    def create() {
        [acumulacionBonoProduccionInstance: new AcumulacionBonoProduccion(params)]
    }

    def save() {
        def acumulacionBonoProduccionInstance = new AcumulacionBonoProduccion(params)
        if (!acumulacionBonoProduccionInstance.save(flush: true)) {
            render(view: "create", model: [acumulacionBonoProduccionInstance: acumulacionBonoProduccionInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'acumulacionBonoProduccion.label', default: 'AcumulacionBonoProduccion'), acumulacionBonoProduccionInstance.id])
        redirect(action: "show", id: acumulacionBonoProduccionInstance.id)
    }

    def show(Long id) {
        def acumulacionBonoProduccionInstance = AcumulacionBonoProduccion.get(id)
        if (!acumulacionBonoProduccionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'acumulacionBonoProduccion.label', default: 'AcumulacionBonoProduccion'), id])
            redirect(action: "list")
            return
        }

        [acumulacionBonoProduccionInstance: acumulacionBonoProduccionInstance]
    }

    def edit(Long id) {
        def acumulacionBonoProduccionInstance = AcumulacionBonoProduccion.get(id)
        if (!acumulacionBonoProduccionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'acumulacionBonoProduccion.label', default: 'AcumulacionBonoProduccion'), id])
            redirect(action: "list")
            return
        }

        [acumulacionBonoProduccionInstance: acumulacionBonoProduccionInstance]
    }

    def update(Long id, Long version) {
        def acumulacionBonoProduccionInstance = AcumulacionBonoProduccion.get(id)
        if (!acumulacionBonoProduccionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'acumulacionBonoProduccion.label', default: 'AcumulacionBonoProduccion'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (acumulacionBonoProduccionInstance.version > version) {
                acumulacionBonoProduccionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'acumulacionBonoProduccion.label', default: 'AcumulacionBonoProduccion')] as Object[],
                        "Another user has updated this AcumulacionBonoProduccion while you were editing")
                render(view: "edit", model: [acumulacionBonoProduccionInstance: acumulacionBonoProduccionInstance])
                return
            }
        }

        acumulacionBonoProduccionInstance.properties = params

        if (!acumulacionBonoProduccionInstance.save(flush: true)) {
            render(view: "edit", model: [acumulacionBonoProduccionInstance: acumulacionBonoProduccionInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'acumulacionBonoProduccion.label', default: 'AcumulacionBonoProduccion'), acumulacionBonoProduccionInstance.id])
        redirect(action: "show", id: acumulacionBonoProduccionInstance.id)
    }

    def delete(Long id) {
        def acumulacionBonoProduccionInstance = AcumulacionBonoProduccion.get(id)
        if (!acumulacionBonoProduccionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'acumulacionBonoProduccion.label', default: 'AcumulacionBonoProduccion'), id])
            redirect(action: "list")
            return
        }

        try {
            acumulacionBonoProduccionInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'acumulacionBonoProduccion.label', default: 'AcumulacionBonoProduccion'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'acumulacionBonoProduccion.label', default: 'AcumulacionBonoProduccion'), id])
            redirect(action: "show", id: id)
        }
    }
}
