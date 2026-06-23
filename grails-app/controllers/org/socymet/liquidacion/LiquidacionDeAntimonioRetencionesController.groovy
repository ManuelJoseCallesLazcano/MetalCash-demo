package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class LiquidacionDeAntimonioRetencionesController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [liquidacionDeAntimonioRetencionesInstanceList: LiquidacionDeAntimonioRetenciones.list(params), liquidacionDeAntimonioRetencionesInstanceTotal: LiquidacionDeAntimonioRetenciones.count()]
    }

    def create() {
        [liquidacionDeAntimonioRetencionesInstance: new LiquidacionDeAntimonioRetenciones(params)]
    }

    def save() {
        def liquidacionDeAntimonioRetencionesInstance = new LiquidacionDeAntimonioRetenciones(params)
        if (!liquidacionDeAntimonioRetencionesInstance.save(flush: true)) {
            render(view: "create", model: [liquidacionDeAntimonioRetencionesInstance: liquidacionDeAntimonioRetencionesInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionDeAntimonioRetenciones.label', default: 'LiquidacionDeAntimonioRetenciones'), liquidacionDeAntimonioRetencionesInstance.id])
        redirect(action: "show", id: liquidacionDeAntimonioRetencionesInstance.id)
    }

    def show(Long id) {
        def liquidacionDeAntimonioRetencionesInstance = LiquidacionDeAntimonioRetenciones.get(id)
        if (!liquidacionDeAntimonioRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeAntimonioRetenciones.label', default: 'LiquidacionDeAntimonioRetenciones'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDeAntimonioRetencionesInstance: liquidacionDeAntimonioRetencionesInstance]
    }

    def edit(Long id) {
        def liquidacionDeAntimonioRetencionesInstance = LiquidacionDeAntimonioRetenciones.get(id)
        if (!liquidacionDeAntimonioRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeAntimonioRetenciones.label', default: 'LiquidacionDeAntimonioRetenciones'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDeAntimonioRetencionesInstance: liquidacionDeAntimonioRetencionesInstance]
    }

    def update(Long id, Long version) {
        def liquidacionDeAntimonioRetencionesInstance = LiquidacionDeAntimonioRetenciones.get(id)
        if (!liquidacionDeAntimonioRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeAntimonioRetenciones.label', default: 'LiquidacionDeAntimonioRetenciones'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (liquidacionDeAntimonioRetencionesInstance.version > version) {
                liquidacionDeAntimonioRetencionesInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'liquidacionDeAntimonioRetenciones.label', default: 'LiquidacionDeAntimonioRetenciones')] as Object[],
                        "Another user has updated this LiquidacionDeAntimonioRetenciones while you were editing")
                render(view: "edit", model: [liquidacionDeAntimonioRetencionesInstance: liquidacionDeAntimonioRetencionesInstance])
                return
            }
        }

        liquidacionDeAntimonioRetencionesInstance.properties = params

        if (!liquidacionDeAntimonioRetencionesInstance.save(flush: true)) {
            render(view: "edit", model: [liquidacionDeAntimonioRetencionesInstance: liquidacionDeAntimonioRetencionesInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'liquidacionDeAntimonioRetenciones.label', default: 'LiquidacionDeAntimonioRetenciones'), liquidacionDeAntimonioRetencionesInstance.id])
        redirect(action: "show", id: liquidacionDeAntimonioRetencionesInstance.id)
    }

    def delete(Long id) {
        def liquidacionDeAntimonioRetencionesInstance = LiquidacionDeAntimonioRetenciones.get(id)
        if (!liquidacionDeAntimonioRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeAntimonioRetenciones.label', default: 'LiquidacionDeAntimonioRetenciones'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionDeAntimonioRetencionesInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'liquidacionDeAntimonioRetenciones.label', default: 'LiquidacionDeAntimonioRetenciones'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDeAntimonioRetenciones.label', default: 'LiquidacionDeAntimonioRetenciones'), id])
            redirect(action: "show", id: id)
        }
    }
}
