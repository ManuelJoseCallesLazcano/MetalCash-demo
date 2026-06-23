package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class LiquidacionDeWolfranRetencionesController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [liquidacionDeWolfranRetencionesInstanceList: LiquidacionDeWolfranRetenciones.list(params), liquidacionDeWolfranRetencionesInstanceTotal: LiquidacionDeWolfranRetenciones.count()]
    }

    def create() {
        [liquidacionDeWolfranRetencionesInstance: new LiquidacionDeWolfranRetenciones(params)]
    }

    def save() {
        def liquidacionDeWolfranRetencionesInstance = new LiquidacionDeWolfranRetenciones(params)
        if (!liquidacionDeWolfranRetencionesInstance.save(flush: true)) {
            render(view: "create", model: [liquidacionDeWolfranRetencionesInstance: liquidacionDeWolfranRetencionesInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionDeWolfranRetenciones.label', default: 'LiquidacionDeWolfranRetenciones'), liquidacionDeWolfranRetencionesInstance.id])
        redirect(action: "show", id: liquidacionDeWolfranRetencionesInstance.id)
    }

    def show(Long id) {
        def liquidacionDeWolfranRetencionesInstance = LiquidacionDeWolfranRetenciones.get(id)
        if (!liquidacionDeWolfranRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeWolfranRetenciones.label', default: 'LiquidacionDeWolfranRetenciones'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDeWolfranRetencionesInstance: liquidacionDeWolfranRetencionesInstance]
    }

    def edit(Long id) {
        def liquidacionDeWolfranRetencionesInstance = LiquidacionDeWolfranRetenciones.get(id)
        if (!liquidacionDeWolfranRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeWolfranRetenciones.label', default: 'LiquidacionDeWolfranRetenciones'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDeWolfranRetencionesInstance: liquidacionDeWolfranRetencionesInstance]
    }

    def update(Long id, Long version) {
        def liquidacionDeWolfranRetencionesInstance = LiquidacionDeWolfranRetenciones.get(id)
        if (!liquidacionDeWolfranRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeWolfranRetenciones.label', default: 'LiquidacionDeWolfranRetenciones'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (liquidacionDeWolfranRetencionesInstance.version > version) {
                liquidacionDeWolfranRetencionesInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'liquidacionDeWolfranRetenciones.label', default: 'LiquidacionDeWolfranRetenciones')] as Object[],
                        "Another user has updated this LiquidacionDeWolfranRetenciones while you were editing")
                render(view: "edit", model: [liquidacionDeWolfranRetencionesInstance: liquidacionDeWolfranRetencionesInstance])
                return
            }
        }

        liquidacionDeWolfranRetencionesInstance.properties = params

        if (!liquidacionDeWolfranRetencionesInstance.save(flush: true)) {
            render(view: "edit", model: [liquidacionDeWolfranRetencionesInstance: liquidacionDeWolfranRetencionesInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'liquidacionDeWolfranRetenciones.label', default: 'LiquidacionDeWolfranRetenciones'), liquidacionDeWolfranRetencionesInstance.id])
        redirect(action: "show", id: liquidacionDeWolfranRetencionesInstance.id)
    }

    def delete(Long id) {
        def liquidacionDeWolfranRetencionesInstance = LiquidacionDeWolfranRetenciones.get(id)
        if (!liquidacionDeWolfranRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeWolfranRetenciones.label', default: 'LiquidacionDeWolfranRetenciones'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionDeWolfranRetencionesInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'liquidacionDeWolfranRetenciones.label', default: 'LiquidacionDeWolfranRetenciones'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDeWolfranRetenciones.label', default: 'LiquidacionDeWolfranRetenciones'), id])
            redirect(action: "show", id: id)
        }
    }
}
