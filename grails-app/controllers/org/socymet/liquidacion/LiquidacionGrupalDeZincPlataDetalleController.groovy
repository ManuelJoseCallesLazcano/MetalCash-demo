package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class LiquidacionGrupalDeZincPlataDetalleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [liquidacionGrupalDeZincPlataDetalleInstanceList: LiquidacionGrupalDeZincPlataDetalle.list(params), liquidacionGrupalDeZincPlataDetalleInstanceTotal: LiquidacionGrupalDeZincPlataDetalle.count()]
    }

    def create() {
        [liquidacionGrupalDeZincPlataDetalleInstance: new LiquidacionGrupalDeZincPlataDetalle(params)]
    }

    def save() {
        def liquidacionGrupalDeZincPlataDetalleInstance = new LiquidacionGrupalDeZincPlataDetalle(params)
        if (!liquidacionGrupalDeZincPlataDetalleInstance.save(flush: true)) {
            render(view: "create", model: [liquidacionGrupalDeZincPlataDetalleInstance: liquidacionGrupalDeZincPlataDetalleInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionGrupalDeZincPlataDetalle.label', default: 'LiquidacionGrupalDeZincPlataDetalle'), liquidacionGrupalDeZincPlataDetalleInstance.id])
        redirect(action: "show", id: liquidacionGrupalDeZincPlataDetalleInstance.id)
    }

    def show(Long id) {
        def liquidacionGrupalDeZincPlataDetalleInstance = LiquidacionGrupalDeZincPlataDetalle.get(id)
        if (!liquidacionGrupalDeZincPlataDetalleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDeZincPlataDetalle.label', default: 'LiquidacionGrupalDeZincPlataDetalle'), id])
            redirect(action: "list")
            return
        }

        [liquidacionGrupalDeZincPlataDetalleInstance: liquidacionGrupalDeZincPlataDetalleInstance]
    }

    def edit(Long id) {
        def liquidacionGrupalDeZincPlataDetalleInstance = LiquidacionGrupalDeZincPlataDetalle.get(id)
        if (!liquidacionGrupalDeZincPlataDetalleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDeZincPlataDetalle.label', default: 'LiquidacionGrupalDeZincPlataDetalle'), id])
            redirect(action: "list")
            return
        }

        [liquidacionGrupalDeZincPlataDetalleInstance: liquidacionGrupalDeZincPlataDetalleInstance]
    }

    def update(Long id, Long version) {
        def liquidacionGrupalDeZincPlataDetalleInstance = LiquidacionGrupalDeZincPlataDetalle.get(id)
        if (!liquidacionGrupalDeZincPlataDetalleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDeZincPlataDetalle.label', default: 'LiquidacionGrupalDeZincPlataDetalle'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (liquidacionGrupalDeZincPlataDetalleInstance.version > version) {
                liquidacionGrupalDeZincPlataDetalleInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'liquidacionGrupalDeZincPlataDetalle.label', default: 'LiquidacionGrupalDeZincPlataDetalle')] as Object[],
                        "Another user has updated this LiquidacionGrupalDeZincPlataDetalle while you were editing")
                render(view: "edit", model: [liquidacionGrupalDeZincPlataDetalleInstance: liquidacionGrupalDeZincPlataDetalleInstance])
                return
            }
        }

        liquidacionGrupalDeZincPlataDetalleInstance.properties = params

        if (!liquidacionGrupalDeZincPlataDetalleInstance.save(flush: true)) {
            render(view: "edit", model: [liquidacionGrupalDeZincPlataDetalleInstance: liquidacionGrupalDeZincPlataDetalleInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'liquidacionGrupalDeZincPlataDetalle.label', default: 'LiquidacionGrupalDeZincPlataDetalle'), liquidacionGrupalDeZincPlataDetalleInstance.id])
        redirect(action: "show", id: liquidacionGrupalDeZincPlataDetalleInstance.id)
    }

    def delete(Long id) {
        def liquidacionGrupalDeZincPlataDetalleInstance = LiquidacionGrupalDeZincPlataDetalle.get(id)
        if (!liquidacionGrupalDeZincPlataDetalleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDeZincPlataDetalle.label', default: 'LiquidacionGrupalDeZincPlataDetalle'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionGrupalDeZincPlataDetalleInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'liquidacionGrupalDeZincPlataDetalle.label', default: 'LiquidacionGrupalDeZincPlataDetalle'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionGrupalDeZincPlataDetalle.label', default: 'LiquidacionGrupalDeZincPlataDetalle'), id])
            redirect(action: "show", id: id)
        }
    }
}
