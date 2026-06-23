package org.socymet.cotizaciones
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION'])
@Transactional
class CotizacionDeDolarController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [cotizacionDeDolarInstanceList: CotizacionDeDolar.list(params), cotizacionDeDolarInstanceTotal: CotizacionDeDolar.count()]
    }

    def create() {
        [cotizacionDeDolarInstance: new CotizacionDeDolar(params)]
    }

    def save() {
        def cotizacionDeDolarInstance = new CotizacionDeDolar(params)
        if (!cotizacionDeDolarInstance.save(flush: true)) {
            render(view: "create", model: [cotizacionDeDolarInstance: cotizacionDeDolarInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'cotizacionDeDolar.label', default: 'CotizacionDeDolar'), cotizacionDeDolarInstance.id])
        redirect(action: "show", id: cotizacionDeDolarInstance.id)
    }

    def show(Long id) {
        def cotizacionDeDolarInstance = CotizacionDeDolar.get(id)
        if (!cotizacionDeDolarInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cotizacionDeDolar.label', default: 'CotizacionDeDolar'), id])
            redirect(action: "list")
            return
        }

        [cotizacionDeDolarInstance: cotizacionDeDolarInstance]
    }

    def edit(Long id) {
        def cotizacionDeDolarInstance = CotizacionDeDolar.get(id)
        if (!cotizacionDeDolarInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cotizacionDeDolar.label', default: 'CotizacionDeDolar'), id])
            redirect(action: "list")
            return
        }

        [cotizacionDeDolarInstance: cotizacionDeDolarInstance]
    }

    def update(Long id, Long version) {
        def cotizacionDeDolarInstance = CotizacionDeDolar.get(id)
        if (!cotizacionDeDolarInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cotizacionDeDolar.label', default: 'CotizacionDeDolar'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (cotizacionDeDolarInstance.version > version) {
                cotizacionDeDolarInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'cotizacionDeDolar.label', default: 'CotizacionDeDolar')] as Object[],
                          "Another user has updated this CotizacionDeDolar while you were editing")
                render(view: "edit", model: [cotizacionDeDolarInstance: cotizacionDeDolarInstance])
                return
            }
        }

        cotizacionDeDolarInstance.properties = params

        if (!cotizacionDeDolarInstance.save(flush: true)) {
            render(view: "edit", model: [cotizacionDeDolarInstance: cotizacionDeDolarInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'cotizacionDeDolar.label', default: 'CotizacionDeDolar'), cotizacionDeDolarInstance.id])
        redirect(action: "show", id: cotizacionDeDolarInstance.id)
    }

    def delete(Long id) {
        def cotizacionDeDolarInstance = CotizacionDeDolar.get(id)
        if (!cotizacionDeDolarInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cotizacionDeDolar.label', default: 'CotizacionDeDolar'), id])
            redirect(action: "list")
            return
        }

        try {
            cotizacionDeDolarInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'cotizacionDeDolar.label', default: 'CotizacionDeDolar'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'cotizacionDeDolar.label', default: 'CotizacionDeDolar'), id])
            redirect(action: "show", id: id)
        }
    }
}
