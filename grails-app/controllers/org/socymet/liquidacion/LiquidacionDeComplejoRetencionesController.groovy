package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION'])
@Transactional
class LiquidacionDeComplejoRetencionesController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [liquidacionDeComplejoRetencionesInstanceList: LiquidacionDeComplejoRetenciones.list(params), liquidacionDeComplejoRetencionesInstanceTotal: LiquidacionDeComplejoRetenciones.count()]
    }

    def create() {
        [liquidacionDeComplejoRetencionesInstance: new LiquidacionDeComplejoRetenciones(params)]
    }

    def save() {
        def liquidacionDeComplejoRetencionesInstance = new LiquidacionDeComplejoRetenciones(params)
        if (!liquidacionDeComplejoRetencionesInstance.save(flush: true)) {
            render(view: "create", model: [liquidacionDeComplejoRetencionesInstance: liquidacionDeComplejoRetencionesInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionDeComplejoRetenciones.label', default: 'LiquidacionDeComplejoRetenciones'), liquidacionDeComplejoRetencionesInstance.id])
        redirect(action: "show", id: liquidacionDeComplejoRetencionesInstance.id)
    }

    def show(Long id) {
        def liquidacionDeComplejoRetencionesInstance = LiquidacionDeComplejoRetenciones.get(id)
        if (!liquidacionDeComplejoRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeComplejoRetenciones.label', default: 'LiquidacionDeComplejoRetenciones'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDeComplejoRetencionesInstance: liquidacionDeComplejoRetencionesInstance]
    }

    def edit(Long id) {
        def liquidacionDeComplejoRetencionesInstance = LiquidacionDeComplejoRetenciones.get(id)
        if (!liquidacionDeComplejoRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeComplejoRetenciones.label', default: 'LiquidacionDeComplejoRetenciones'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDeComplejoRetencionesInstance: liquidacionDeComplejoRetencionesInstance]
    }

    def update(Long id, Long version) {
        def liquidacionDeComplejoRetencionesInstance = LiquidacionDeComplejoRetenciones.get(id)
        if (!liquidacionDeComplejoRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeComplejoRetenciones.label', default: 'LiquidacionDeComplejoRetenciones'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (liquidacionDeComplejoRetencionesInstance.version > version) {
                liquidacionDeComplejoRetencionesInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'liquidacionDeComplejoRetenciones.label', default: 'LiquidacionDeComplejoRetenciones')] as Object[],
                        "Another user has updated this LiquidacionDeComplejoRetenciones while you were editing")
                render(view: "edit", model: [liquidacionDeComplejoRetencionesInstance: liquidacionDeComplejoRetencionesInstance])
                return
            }
        }

        liquidacionDeComplejoRetencionesInstance.properties = params

        if (!liquidacionDeComplejoRetencionesInstance.save(flush: true)) {
            render(view: "edit", model: [liquidacionDeComplejoRetencionesInstance: liquidacionDeComplejoRetencionesInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'liquidacionDeComplejoRetenciones.label', default: 'LiquidacionDeComplejoRetenciones'), liquidacionDeComplejoRetencionesInstance.id])
        redirect(action: "show", id: liquidacionDeComplejoRetencionesInstance.id)
    }

    def delete(Long id) {
        def liquidacionDeComplejoRetencionesInstance = LiquidacionDeComplejoRetenciones.get(id)
        if (!liquidacionDeComplejoRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeComplejoRetenciones.label', default: 'LiquidacionDeComplejoRetenciones'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionDeComplejoRetencionesInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'liquidacionDeComplejoRetenciones.label', default: 'LiquidacionDeComplejoRetenciones'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDeComplejoRetenciones.label', default: 'LiquidacionDeComplejoRetenciones'), id])
            redirect(action: "show", id: id)
        }
    }
}
