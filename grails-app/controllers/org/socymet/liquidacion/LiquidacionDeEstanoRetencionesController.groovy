package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class LiquidacionDeEstanoRetencionesController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [liquidacionDeEstanoRetencionesInstanceList: LiquidacionDeEstanoRetenciones.list(params), liquidacionDeEstanoRetencionesInstanceTotal: LiquidacionDeEstanoRetenciones.count()]
    }

    def create() {
        [liquidacionDeEstanoRetencionesInstance: new LiquidacionDeEstanoRetenciones(params)]
    }

    def save() {
        def liquidacionDeEstanoRetencionesInstance = new LiquidacionDeEstanoRetenciones(params)
        if (!liquidacionDeEstanoRetencionesInstance.save(flush: true)) {
            render(view: "create", model: [liquidacionDeEstanoRetencionesInstance: liquidacionDeEstanoRetencionesInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionDeEstanoRetenciones.label', default: 'LiquidacionDeEstanoRetenciones'), liquidacionDeEstanoRetencionesInstance.id])
        redirect(action: "show", id: liquidacionDeEstanoRetencionesInstance.id)
    }

    def show(Long id) {
        def liquidacionDeEstanoRetencionesInstance = LiquidacionDeEstanoRetenciones.get(id)
        if (!liquidacionDeEstanoRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeEstanoRetenciones.label', default: 'LiquidacionDeEstanoRetenciones'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDeEstanoRetencionesInstance: liquidacionDeEstanoRetencionesInstance]
    }

    def edit(Long id) {
        def liquidacionDeEstanoRetencionesInstance = LiquidacionDeEstanoRetenciones.get(id)
        if (!liquidacionDeEstanoRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeEstanoRetenciones.label', default: 'LiquidacionDeEstanoRetenciones'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDeEstanoRetencionesInstance: liquidacionDeEstanoRetencionesInstance]
    }

    def update(Long id, Long version) {
        def liquidacionDeEstanoRetencionesInstance = LiquidacionDeEstanoRetenciones.get(id)
        if (!liquidacionDeEstanoRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeEstanoRetenciones.label', default: 'LiquidacionDeEstanoRetenciones'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (liquidacionDeEstanoRetencionesInstance.version > version) {
                liquidacionDeEstanoRetencionesInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'liquidacionDeEstanoRetenciones.label', default: 'LiquidacionDeEstanoRetenciones')] as Object[],
                        "Another user has updated this LiquidacionDeEstanoRetenciones while you were editing")
                render(view: "edit", model: [liquidacionDeEstanoRetencionesInstance: liquidacionDeEstanoRetencionesInstance])
                return
            }
        }

        liquidacionDeEstanoRetencionesInstance.properties = params

        if (!liquidacionDeEstanoRetencionesInstance.save(flush: true)) {
            render(view: "edit", model: [liquidacionDeEstanoRetencionesInstance: liquidacionDeEstanoRetencionesInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'liquidacionDeEstanoRetenciones.label', default: 'LiquidacionDeEstanoRetenciones'), liquidacionDeEstanoRetencionesInstance.id])
        redirect(action: "show", id: liquidacionDeEstanoRetencionesInstance.id)
    }

    def delete(Long id) {
        def liquidacionDeEstanoRetencionesInstance = LiquidacionDeEstanoRetenciones.get(id)
        if (!liquidacionDeEstanoRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeEstanoRetenciones.label', default: 'LiquidacionDeEstanoRetenciones'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionDeEstanoRetencionesInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'liquidacionDeEstanoRetenciones.label', default: 'LiquidacionDeEstanoRetenciones'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDeEstanoRetenciones.label', default: 'LiquidacionDeEstanoRetenciones'), id])
            redirect(action: "show", id: id)
        }
    }
}
