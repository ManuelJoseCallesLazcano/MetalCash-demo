package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class LiquidacionGrupalDeCobrePlataDetalleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [liquidacionGrupalDeCobrePlataDetalleInstanceList: LiquidacionGrupalDeCobrePlataDetalle.list(params), liquidacionGrupalDeCobrePlataDetalleInstanceTotal: LiquidacionGrupalDeCobrePlataDetalle.count()]
    }

    def create() {
        [liquidacionGrupalDeCobrePlataDetalleInstance: new LiquidacionGrupalDeCobrePlataDetalle(params)]
    }

    def save() {
        def liquidacionGrupalDeCobrePlataDetalleInstance = new LiquidacionGrupalDeCobrePlataDetalle(params)
        if (!liquidacionGrupalDeCobrePlataDetalleInstance.save(flush: true)) {
            render(view: "create", model: [liquidacionGrupalDeCobrePlataDetalleInstance: liquidacionGrupalDeCobrePlataDetalleInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionGrupalDeCobrePlataDetalle.label', default: 'LiquidacionGrupalDeCobrePlataDetalle'), liquidacionGrupalDeCobrePlataDetalleInstance.id])
        redirect(action: "show", id: liquidacionGrupalDeCobrePlataDetalleInstance.id)
    }

    def show(Long id) {
        def liquidacionGrupalDeCobrePlataDetalleInstance = LiquidacionGrupalDeCobrePlataDetalle.get(id)
        if (!liquidacionGrupalDeCobrePlataDetalleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDeCobrePlataDetalle.label', default: 'LiquidacionGrupalDeCobrePlataDetalle'), id])
            redirect(action: "list")
            return
        }

        [liquidacionGrupalDeCobrePlataDetalleInstance: liquidacionGrupalDeCobrePlataDetalleInstance]
    }

    def edit(Long id) {
        def liquidacionGrupalDeCobrePlataDetalleInstance = LiquidacionGrupalDeCobrePlataDetalle.get(id)
        if (!liquidacionGrupalDeCobrePlataDetalleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDeCobrePlataDetalle.label', default: 'LiquidacionGrupalDeCobrePlataDetalle'), id])
            redirect(action: "list")
            return
        }

        [liquidacionGrupalDeCobrePlataDetalleInstance: liquidacionGrupalDeCobrePlataDetalleInstance]
    }

    def update(Long id, Long version) {
        def liquidacionGrupalDeCobrePlataDetalleInstance = LiquidacionGrupalDeCobrePlataDetalle.get(id)
        if (!liquidacionGrupalDeCobrePlataDetalleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDeCobrePlataDetalle.label', default: 'LiquidacionGrupalDeCobrePlataDetalle'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (liquidacionGrupalDeCobrePlataDetalleInstance.version > version) {
                liquidacionGrupalDeCobrePlataDetalleInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'liquidacionGrupalDeCobrePlataDetalle.label', default: 'LiquidacionGrupalDeCobrePlataDetalle')] as Object[],
                        "Another user has updated this LiquidacionGrupalDeCobrePlataDetalle while you were editing")
                render(view: "edit", model: [liquidacionGrupalDeCobrePlataDetalleInstance: liquidacionGrupalDeCobrePlataDetalleInstance])
                return
            }
        }

        liquidacionGrupalDeCobrePlataDetalleInstance.properties = params

        if (!liquidacionGrupalDeCobrePlataDetalleInstance.save(flush: true)) {
            render(view: "edit", model: [liquidacionGrupalDeCobrePlataDetalleInstance: liquidacionGrupalDeCobrePlataDetalleInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'liquidacionGrupalDeCobrePlataDetalle.label', default: 'LiquidacionGrupalDeCobrePlataDetalle'), liquidacionGrupalDeCobrePlataDetalleInstance.id])
        redirect(action: "show", id: liquidacionGrupalDeCobrePlataDetalleInstance.id)
    }

    def delete(Long id) {
        def liquidacionGrupalDeCobrePlataDetalleInstance = LiquidacionGrupalDeCobrePlataDetalle.get(id)
        if (!liquidacionGrupalDeCobrePlataDetalleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDeCobrePlataDetalle.label', default: 'LiquidacionGrupalDeCobrePlataDetalle'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionGrupalDeCobrePlataDetalleInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'liquidacionGrupalDeCobrePlataDetalle.label', default: 'LiquidacionGrupalDeCobrePlataDetalle'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionGrupalDeCobrePlataDetalle.label', default: 'LiquidacionGrupalDeCobrePlataDetalle'), id])
            redirect(action: "show", id: id)
        }
    }
}
