package org.socymet.cancelacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class AcumulacionBonoCalidadController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [acumulacionBonoCalidadInstanceList: AcumulacionBonoCalidad.list(params), acumulacionBonoCalidadInstanceTotal: AcumulacionBonoCalidad.count()]
    }

    def create() {
        [acumulacionBonoCalidadInstance: new AcumulacionBonoCalidad(params)]
    }

    def save() {
        def acumulacionBonoCalidadInstance = new AcumulacionBonoCalidad(params)
        if (!acumulacionBonoCalidadInstance.save(flush: true)) {
            render(view: "create", model: [acumulacionBonoCalidadInstance: acumulacionBonoCalidadInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'acumulacionBonoCalidad.label', default: 'AcumulacionBonoCalidad'), acumulacionBonoCalidadInstance.id])
        redirect(action: "show", id: acumulacionBonoCalidadInstance.id)
    }

    def show(Long id) {
        def acumulacionBonoCalidadInstance = AcumulacionBonoCalidad.get(id)
        if (!acumulacionBonoCalidadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'acumulacionBonoCalidad.label', default: 'AcumulacionBonoCalidad'), id])
            redirect(action: "list")
            return
        }

        [acumulacionBonoCalidadInstance: acumulacionBonoCalidadInstance]
    }

    def edit(Long id) {
        def acumulacionBonoCalidadInstance = AcumulacionBonoCalidad.get(id)
        if (!acumulacionBonoCalidadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'acumulacionBonoCalidad.label', default: 'AcumulacionBonoCalidad'), id])
            redirect(action: "list")
            return
        }

        [acumulacionBonoCalidadInstance: acumulacionBonoCalidadInstance]
    }

    def update(Long id, Long version) {
        def acumulacionBonoCalidadInstance = AcumulacionBonoCalidad.get(id)
        if (!acumulacionBonoCalidadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'acumulacionBonoCalidad.label', default: 'AcumulacionBonoCalidad'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (acumulacionBonoCalidadInstance.version > version) {
                acumulacionBonoCalidadInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'acumulacionBonoCalidad.label', default: 'AcumulacionBonoCalidad')] as Object[],
                        "Another user has updated this AcumulacionBonoCalidad while you were editing")
                render(view: "edit", model: [acumulacionBonoCalidadInstance: acumulacionBonoCalidadInstance])
                return
            }
        }

        acumulacionBonoCalidadInstance.properties = params

        if (!acumulacionBonoCalidadInstance.save(flush: true)) {
            render(view: "edit", model: [acumulacionBonoCalidadInstance: acumulacionBonoCalidadInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'acumulacionBonoCalidad.label', default: 'AcumulacionBonoCalidad'), acumulacionBonoCalidadInstance.id])
        redirect(action: "show", id: acumulacionBonoCalidadInstance.id)
    }

    def delete(Long id) {
        def acumulacionBonoCalidadInstance = AcumulacionBonoCalidad.get(id)
        if (!acumulacionBonoCalidadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'acumulacionBonoCalidad.label', default: 'AcumulacionBonoCalidad'), id])
            redirect(action: "list")
            return
        }

        try {
            acumulacionBonoCalidadInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'acumulacionBonoCalidad.label', default: 'AcumulacionBonoCalidad'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'acumulacionBonoCalidad.label', default: 'AcumulacionBonoCalidad'), id])
            redirect(action: "show", id: id)
        }
    }
}
