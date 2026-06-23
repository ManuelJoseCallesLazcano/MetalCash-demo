package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class LiquidacionDeCobrePlataRetencionesController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [liquidacionDeCobrePlataRetencionesInstanceList: LiquidacionDeCobrePlataRetenciones.list(params), liquidacionDeCobrePlataRetencionesInstanceTotal: LiquidacionDeCobrePlataRetenciones.count()]
    }

    def create() {
        [liquidacionDeCobrePlataRetencionesInstance: new LiquidacionDeCobrePlataRetenciones(params)]
    }

    def save() {
        def liquidacionDeCobrePlataRetencionesInstance = new LiquidacionDeCobrePlataRetenciones(params)
        if (!liquidacionDeCobrePlataRetencionesInstance.save(flush: true)) {
            render(view: "create", model: [liquidacionDeCobrePlataRetencionesInstance: liquidacionDeCobrePlataRetencionesInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionDeCobrePlataRetenciones.label', default: 'LiquidacionDeCobrePlataRetenciones'), liquidacionDeCobrePlataRetencionesInstance.id])
        redirect(action: "show", id: liquidacionDeCobrePlataRetencionesInstance.id)
    }

    def show(Long id) {
        def liquidacionDeCobrePlataRetencionesInstance = LiquidacionDeCobrePlataRetenciones.get(id)
        if (!liquidacionDeCobrePlataRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeCobrePlataRetenciones.label', default: 'LiquidacionDeCobrePlataRetenciones'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDeCobrePlataRetencionesInstance: liquidacionDeCobrePlataRetencionesInstance]
    }

    def edit(Long id) {
        def liquidacionDeCobrePlataRetencionesInstance = LiquidacionDeCobrePlataRetenciones.get(id)
        if (!liquidacionDeCobrePlataRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeCobrePlataRetenciones.label', default: 'LiquidacionDeCobrePlataRetenciones'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDeCobrePlataRetencionesInstance: liquidacionDeCobrePlataRetencionesInstance]
    }

    def update(Long id, Long version) {
        def liquidacionDeCobrePlataRetencionesInstance = LiquidacionDeCobrePlataRetenciones.get(id)
        if (!liquidacionDeCobrePlataRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeCobrePlataRetenciones.label', default: 'LiquidacionDeCobrePlataRetenciones'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (liquidacionDeCobrePlataRetencionesInstance.version > version) {
                liquidacionDeCobrePlataRetencionesInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'liquidacionDeCobrePlataRetenciones.label', default: 'LiquidacionDeCobrePlataRetenciones')] as Object[],
                        "Another user has updated this LiquidacionDeCobrePlataRetenciones while you were editing")
                render(view: "edit", model: [liquidacionDeCobrePlataRetencionesInstance: liquidacionDeCobrePlataRetencionesInstance])
                return
            }
        }

        liquidacionDeCobrePlataRetencionesInstance.properties = params

        if (!liquidacionDeCobrePlataRetencionesInstance.save(flush: true)) {
            render(view: "edit", model: [liquidacionDeCobrePlataRetencionesInstance: liquidacionDeCobrePlataRetencionesInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'liquidacionDeCobrePlataRetenciones.label', default: 'LiquidacionDeCobrePlataRetenciones'), liquidacionDeCobrePlataRetencionesInstance.id])
        redirect(action: "show", id: liquidacionDeCobrePlataRetencionesInstance.id)
    }

    def delete(Long id) {
        def liquidacionDeCobrePlataRetencionesInstance = LiquidacionDeCobrePlataRetenciones.get(id)
        if (!liquidacionDeCobrePlataRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeCobrePlataRetenciones.label', default: 'LiquidacionDeCobrePlataRetenciones'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionDeCobrePlataRetencionesInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'liquidacionDeCobrePlataRetenciones.label', default: 'LiquidacionDeCobrePlataRetenciones'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDeCobrePlataRetenciones.label', default: 'LiquidacionDeCobrePlataRetenciones'), id])
            redirect(action: "show", id: id)
        }
    }
}
