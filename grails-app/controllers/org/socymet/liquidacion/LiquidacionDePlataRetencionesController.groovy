package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class LiquidacionDePlataRetencionesController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [liquidacionDePlataRetencionesInstanceList: LiquidacionDePlataRetenciones.list(params), liquidacionDePlataRetencionesInstanceTotal: LiquidacionDePlataRetenciones.count()]
    }

    def create() {
        [liquidacionDePlataRetencionesInstance: new LiquidacionDePlataRetenciones(params)]
    }

    def save() {
        def liquidacionDePlataRetencionesInstance = new LiquidacionDePlataRetenciones(params)
        if (!liquidacionDePlataRetencionesInstance.save(flush: true)) {
            render(view: "create", model: [liquidacionDePlataRetencionesInstance: liquidacionDePlataRetencionesInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionDePlataRetenciones.label', default: 'LiquidacionDePlataRetenciones'), liquidacionDePlataRetencionesInstance.id])
        redirect(action: "show", id: liquidacionDePlataRetencionesInstance.id)
    }

    def show(Long id) {
        def liquidacionDePlataRetencionesInstance = LiquidacionDePlataRetenciones.get(id)
        if (!liquidacionDePlataRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDePlataRetenciones.label', default: 'LiquidacionDePlataRetenciones'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDePlataRetencionesInstance: liquidacionDePlataRetencionesInstance]
    }

    def edit(Long id) {
        def liquidacionDePlataRetencionesInstance = LiquidacionDePlataRetenciones.get(id)
        if (!liquidacionDePlataRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDePlataRetenciones.label', default: 'LiquidacionDePlataRetenciones'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDePlataRetencionesInstance: liquidacionDePlataRetencionesInstance]
    }

    def update(Long id, Long version) {
        def liquidacionDePlataRetencionesInstance = LiquidacionDePlataRetenciones.get(id)
        if (!liquidacionDePlataRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDePlataRetenciones.label', default: 'LiquidacionDePlataRetenciones'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (liquidacionDePlataRetencionesInstance.version > version) {
                liquidacionDePlataRetencionesInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'liquidacionDePlataRetenciones.label', default: 'LiquidacionDePlataRetenciones')] as Object[],
                        "Another user has updated this LiquidacionDePlataRetenciones while you were editing")
                render(view: "edit", model: [liquidacionDePlataRetencionesInstance: liquidacionDePlataRetencionesInstance])
                return
            }
        }

        liquidacionDePlataRetencionesInstance.properties = params

        if (!liquidacionDePlataRetencionesInstance.save(flush: true)) {
            render(view: "edit", model: [liquidacionDePlataRetencionesInstance: liquidacionDePlataRetencionesInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'liquidacionDePlataRetenciones.label', default: 'LiquidacionDePlataRetenciones'), liquidacionDePlataRetencionesInstance.id])
        redirect(action: "show", id: liquidacionDePlataRetencionesInstance.id)
    }

    def delete(Long id) {
        def liquidacionDePlataRetencionesInstance = LiquidacionDePlataRetenciones.get(id)
        if (!liquidacionDePlataRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDePlataRetenciones.label', default: 'LiquidacionDePlataRetenciones'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionDePlataRetencionesInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'liquidacionDePlataRetenciones.label', default: 'LiquidacionDePlataRetenciones'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDePlataRetenciones.label', default: 'LiquidacionDePlataRetenciones'), id])
            redirect(action: "show", id: id)
        }
    }
}
