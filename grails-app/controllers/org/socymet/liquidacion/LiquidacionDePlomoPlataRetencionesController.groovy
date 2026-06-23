package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION'])
@Transactional
class LiquidacionDePlomoPlataRetencionesController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [liquidacionDePlomoPlataRetencionesInstanceList: LiquidacionDePlomoPlataRetenciones.list(params), liquidacionDePlomoPlataRetencionesInstanceTotal: LiquidacionDePlomoPlataRetenciones.count()]
    }

    def create() {
        [liquidacionDePlomoPlataRetencionesInstance: new LiquidacionDePlomoPlataRetenciones(params)]
    }

    def save() {
        def liquidacionDePlomoPlataRetencionesInstance = new LiquidacionDePlomoPlataRetenciones(params)
        if (!liquidacionDePlomoPlataRetencionesInstance.save(flush: true)) {
            render(view: "create", model: [liquidacionDePlomoPlataRetencionesInstance: liquidacionDePlomoPlataRetencionesInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionDePlomoPlataRetenciones.label', default: 'LiquidacionDePlomoPlataRetenciones'), liquidacionDePlomoPlataRetencionesInstance.id])
        redirect(action: "show", id: liquidacionDePlomoPlataRetencionesInstance.id)
    }

    def show(Long id) {
        def liquidacionDePlomoPlataRetencionesInstance = LiquidacionDePlomoPlataRetenciones.get(id)
        if (!liquidacionDePlomoPlataRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDePlomoPlataRetenciones.label', default: 'LiquidacionDePlomoPlataRetenciones'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDePlomoPlataRetencionesInstance: liquidacionDePlomoPlataRetencionesInstance]
    }

    def edit(Long id) {
        def liquidacionDePlomoPlataRetencionesInstance = LiquidacionDePlomoPlataRetenciones.get(id)
        if (!liquidacionDePlomoPlataRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDePlomoPlataRetenciones.label', default: 'LiquidacionDePlomoPlataRetenciones'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDePlomoPlataRetencionesInstance: liquidacionDePlomoPlataRetencionesInstance]
    }

    def update(Long id, Long version) {
        def liquidacionDePlomoPlataRetencionesInstance = LiquidacionDePlomoPlataRetenciones.get(id)
        if (!liquidacionDePlomoPlataRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDePlomoPlataRetenciones.label', default: 'LiquidacionDePlomoPlataRetenciones'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (liquidacionDePlomoPlataRetencionesInstance.version > version) {
                liquidacionDePlomoPlataRetencionesInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'liquidacionDePlomoPlataRetenciones.label', default: 'LiquidacionDePlomoPlataRetenciones')] as Object[],
                        "Another user has updated this LiquidacionDePlomoPlataRetenciones while you were editing")
                render(view: "edit", model: [liquidacionDePlomoPlataRetencionesInstance: liquidacionDePlomoPlataRetencionesInstance])
                return
            }
        }

        liquidacionDePlomoPlataRetencionesInstance.properties = params

        if (!liquidacionDePlomoPlataRetencionesInstance.save(flush: true)) {
            render(view: "edit", model: [liquidacionDePlomoPlataRetencionesInstance: liquidacionDePlomoPlataRetencionesInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'liquidacionDePlomoPlataRetenciones.label', default: 'LiquidacionDePlomoPlataRetenciones'), liquidacionDePlomoPlataRetencionesInstance.id])
        redirect(action: "show", id: liquidacionDePlomoPlataRetencionesInstance.id)
    }

    def delete(Long id) {
        def liquidacionDePlomoPlataRetencionesInstance = LiquidacionDePlomoPlataRetenciones.get(id)
        if (!liquidacionDePlomoPlataRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDePlomoPlataRetenciones.label', default: 'LiquidacionDePlomoPlataRetenciones'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionDePlomoPlataRetencionesInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'liquidacionDePlomoPlataRetenciones.label', default: 'LiquidacionDePlomoPlataRetenciones'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDePlomoPlataRetenciones.label', default: 'LiquidacionDePlomoPlataRetenciones'), id])
            redirect(action: "show", id: id)
        }
    }
}
