package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class LiquidacionGrupalDePlomoPlataDetalleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [liquidacionGrupalDePlomoPlataDetalleInstanceList: LiquidacionGrupalDePlomoPlataDetalle.list(params), liquidacionGrupalDePlomoPlataDetalleInstanceTotal: LiquidacionGrupalDePlomoPlataDetalle.count()]
    }

    def create() {
        [liquidacionGrupalDePlomoPlataDetalleInstance: new LiquidacionGrupalDePlomoPlataDetalle(params)]
    }

    def save() {
        def liquidacionGrupalDePlomoPlataDetalleInstance = new LiquidacionGrupalDePlomoPlataDetalle(params)
        if (!liquidacionGrupalDePlomoPlataDetalleInstance.save(flush: true)) {
            render(view: "create", model: [liquidacionGrupalDePlomoPlataDetalleInstance: liquidacionGrupalDePlomoPlataDetalleInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionGrupalDePlomoPlataDetalle.label', default: 'LiquidacionGrupalDePlomoPlataDetalle'), liquidacionGrupalDePlomoPlataDetalleInstance.id])
        redirect(action: "show", id: liquidacionGrupalDePlomoPlataDetalleInstance.id)
    }

    def show(Long id) {
        def liquidacionGrupalDePlomoPlataDetalleInstance = LiquidacionGrupalDePlomoPlataDetalle.get(id)
        if (!liquidacionGrupalDePlomoPlataDetalleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDePlomoPlataDetalle.label', default: 'LiquidacionGrupalDePlomoPlataDetalle'), id])
            redirect(action: "list")
            return
        }

        [liquidacionGrupalDePlomoPlataDetalleInstance: liquidacionGrupalDePlomoPlataDetalleInstance]
    }

    def edit(Long id) {
        def liquidacionGrupalDePlomoPlataDetalleInstance = LiquidacionGrupalDePlomoPlataDetalle.get(id)
        if (!liquidacionGrupalDePlomoPlataDetalleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDePlomoPlataDetalle.label', default: 'LiquidacionGrupalDePlomoPlataDetalle'), id])
            redirect(action: "list")
            return
        }

        [liquidacionGrupalDePlomoPlataDetalleInstance: liquidacionGrupalDePlomoPlataDetalleInstance]
    }

    def update(Long id, Long version) {
        def liquidacionGrupalDePlomoPlataDetalleInstance = LiquidacionGrupalDePlomoPlataDetalle.get(id)
        if (!liquidacionGrupalDePlomoPlataDetalleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDePlomoPlataDetalle.label', default: 'LiquidacionGrupalDePlomoPlataDetalle'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (liquidacionGrupalDePlomoPlataDetalleInstance.version > version) {
                liquidacionGrupalDePlomoPlataDetalleInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'liquidacionGrupalDePlomoPlataDetalle.label', default: 'LiquidacionGrupalDePlomoPlataDetalle')] as Object[],
                        "Another user has updated this LiquidacionGrupalDePlomoPlataDetalle while you were editing")
                render(view: "edit", model: [liquidacionGrupalDePlomoPlataDetalleInstance: liquidacionGrupalDePlomoPlataDetalleInstance])
                return
            }
        }

        liquidacionGrupalDePlomoPlataDetalleInstance.properties = params

        if (!liquidacionGrupalDePlomoPlataDetalleInstance.save(flush: true)) {
            render(view: "edit", model: [liquidacionGrupalDePlomoPlataDetalleInstance: liquidacionGrupalDePlomoPlataDetalleInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'liquidacionGrupalDePlomoPlataDetalle.label', default: 'LiquidacionGrupalDePlomoPlataDetalle'), liquidacionGrupalDePlomoPlataDetalleInstance.id])
        redirect(action: "show", id: liquidacionGrupalDePlomoPlataDetalleInstance.id)
    }

    def delete(Long id) {
        def liquidacionGrupalDePlomoPlataDetalleInstance = LiquidacionGrupalDePlomoPlataDetalle.get(id)
        if (!liquidacionGrupalDePlomoPlataDetalleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDePlomoPlataDetalle.label', default: 'LiquidacionGrupalDePlomoPlataDetalle'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionGrupalDePlomoPlataDetalleInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'liquidacionGrupalDePlomoPlataDetalle.label', default: 'LiquidacionGrupalDePlomoPlataDetalle'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionGrupalDePlomoPlataDetalle.label', default: 'LiquidacionGrupalDePlomoPlataDetalle'), id])
            redirect(action: "show", id: id)
        }
    }
}
