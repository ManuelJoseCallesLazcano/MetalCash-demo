package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION'])
@Transactional
class LiquidacionDeZincPlataRetencionesController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [liquidacionDeZincPlataRetencionesInstanceList: LiquidacionDeZincPlataRetenciones.list(params), liquidacionDeZincPlataRetencionesInstanceTotal: LiquidacionDeZincPlataRetenciones.count()]
    }

    def create() {
        [liquidacionDeZincPlataRetencionesInstance: new LiquidacionDeZincPlataRetenciones(params)]
    }

    def save() {
        def liquidacionDeZincPlataRetencionesInstance = new LiquidacionDeZincPlataRetenciones(params)
        if (!liquidacionDeZincPlataRetencionesInstance.save(flush: true)) {
            render(view: "create", model: [liquidacionDeZincPlataRetencionesInstance: liquidacionDeZincPlataRetencionesInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionDeZincPlataRetenciones.label', default: 'LiquidacionDeZincPlataRetenciones'), liquidacionDeZincPlataRetencionesInstance.id])
        redirect(action: "show", id: liquidacionDeZincPlataRetencionesInstance.id)
    }

    def show(Long id) {
        def liquidacionDeZincPlataRetencionesInstance = LiquidacionDeZincPlataRetenciones.get(id)
        if (!liquidacionDeZincPlataRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeZincPlataRetenciones.label', default: 'LiquidacionDeZincPlataRetenciones'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDeZincPlataRetencionesInstance: liquidacionDeZincPlataRetencionesInstance]
    }

    def edit(Long id) {
        def liquidacionDeZincPlataRetencionesInstance = LiquidacionDeZincPlataRetenciones.get(id)
        if (!liquidacionDeZincPlataRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeZincPlataRetenciones.label', default: 'LiquidacionDeZincPlataRetenciones'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDeZincPlataRetencionesInstance: liquidacionDeZincPlataRetencionesInstance]
    }

    def update(Long id, Long version) {
        def liquidacionDeZincPlataRetencionesInstance = LiquidacionDeZincPlataRetenciones.get(id)
        if (!liquidacionDeZincPlataRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeZincPlataRetenciones.label', default: 'LiquidacionDeZincPlataRetenciones'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (liquidacionDeZincPlataRetencionesInstance.version > version) {
                liquidacionDeZincPlataRetencionesInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'liquidacionDeZincPlataRetenciones.label', default: 'LiquidacionDeZincPlataRetenciones')] as Object[],
                        "Another user has updated this LiquidacionDeZincPlataRetenciones while you were editing")
                render(view: "edit", model: [liquidacionDeZincPlataRetencionesInstance: liquidacionDeZincPlataRetencionesInstance])
                return
            }
        }

        liquidacionDeZincPlataRetencionesInstance.properties = params

        if (!liquidacionDeZincPlataRetencionesInstance.save(flush: true)) {
            render(view: "edit", model: [liquidacionDeZincPlataRetencionesInstance: liquidacionDeZincPlataRetencionesInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'liquidacionDeZincPlataRetenciones.label', default: 'LiquidacionDeZincPlataRetenciones'), liquidacionDeZincPlataRetencionesInstance.id])
        redirect(action: "show", id: liquidacionDeZincPlataRetencionesInstance.id)
    }

    def delete(Long id) {
        def liquidacionDeZincPlataRetencionesInstance = LiquidacionDeZincPlataRetenciones.get(id)
        if (!liquidacionDeZincPlataRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeZincPlataRetenciones.label', default: 'LiquidacionDeZincPlataRetenciones'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionDeZincPlataRetencionesInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'liquidacionDeZincPlataRetenciones.label', default: 'LiquidacionDeZincPlataRetenciones'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDeZincPlataRetenciones.label', default: 'LiquidacionDeZincPlataRetenciones'), id])
            redirect(action: "show", id: id)
        }
    }
}
