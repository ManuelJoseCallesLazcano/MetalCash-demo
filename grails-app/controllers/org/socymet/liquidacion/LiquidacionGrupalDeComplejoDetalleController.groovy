package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class LiquidacionGrupalDeComplejoDetalleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [liquidacionGrupalDeComplejoDetalleInstanceList: LiquidacionGrupalDeComplejoDetalle.list(params), liquidacionGrupalDeComplejoDetalleInstanceTotal: LiquidacionGrupalDeComplejoDetalle.count()]
    }

    def create() {
        [liquidacionGrupalDeComplejoDetalleInstance: new LiquidacionGrupalDeComplejoDetalle(params)]
    }

    def save() {
        def liquidacionGrupalDeComplejoDetalleInstance = new LiquidacionGrupalDeComplejoDetalle(params)
        if (!liquidacionGrupalDeComplejoDetalleInstance.save(flush: true)) {
            render(view: "create", model: [liquidacionGrupalDeComplejoDetalleInstance: liquidacionGrupalDeComplejoDetalleInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionGrupalDeComplejoDetalle.label', default: 'LiquidacionGrupalDeComplejoDetalle'), liquidacionGrupalDeComplejoDetalleInstance.id])
        redirect(action: "show", id: liquidacionGrupalDeComplejoDetalleInstance.id)
    }

    def show(Long id) {
        def liquidacionGrupalDeComplejoDetalleInstance = LiquidacionGrupalDeComplejoDetalle.get(id)
        if (!liquidacionGrupalDeComplejoDetalleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDeComplejoDetalle.label', default: 'LiquidacionGrupalDeComplejoDetalle'), id])
            redirect(action: "list")
            return
        }

        [liquidacionGrupalDeComplejoDetalleInstance: liquidacionGrupalDeComplejoDetalleInstance]
    }

    def edit(Long id) {
        def liquidacionGrupalDeComplejoDetalleInstance = LiquidacionGrupalDeComplejoDetalle.get(id)
        if (!liquidacionGrupalDeComplejoDetalleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDeComplejoDetalle.label', default: 'LiquidacionGrupalDeComplejoDetalle'), id])
            redirect(action: "list")
            return
        }

        [liquidacionGrupalDeComplejoDetalleInstance: liquidacionGrupalDeComplejoDetalleInstance]
    }

    def update(Long id, Long version) {
        def liquidacionGrupalDeComplejoDetalleInstance = LiquidacionGrupalDeComplejoDetalle.get(id)
        if (!liquidacionGrupalDeComplejoDetalleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDeComplejoDetalle.label', default: 'LiquidacionGrupalDeComplejoDetalle'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (liquidacionGrupalDeComplejoDetalleInstance.version > version) {
                liquidacionGrupalDeComplejoDetalleInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'liquidacionGrupalDeComplejoDetalle.label', default: 'LiquidacionGrupalDeComplejoDetalle')] as Object[],
                        "Another user has updated this LiquidacionGrupalDeComplejoDetalle while you were editing")
                render(view: "edit", model: [liquidacionGrupalDeComplejoDetalleInstance: liquidacionGrupalDeComplejoDetalleInstance])
                return
            }
        }

        liquidacionGrupalDeComplejoDetalleInstance.properties = params

        if (!liquidacionGrupalDeComplejoDetalleInstance.save(flush: true)) {
            render(view: "edit", model: [liquidacionGrupalDeComplejoDetalleInstance: liquidacionGrupalDeComplejoDetalleInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'liquidacionGrupalDeComplejoDetalle.label', default: 'LiquidacionGrupalDeComplejoDetalle'), liquidacionGrupalDeComplejoDetalleInstance.id])
        redirect(action: "show", id: liquidacionGrupalDeComplejoDetalleInstance.id)
    }

    def delete(Long id) {
        def liquidacionGrupalDeComplejoDetalleInstance = LiquidacionGrupalDeComplejoDetalle.get(id)
        if (!liquidacionGrupalDeComplejoDetalleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDeComplejoDetalle.label', default: 'LiquidacionGrupalDeComplejoDetalle'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionGrupalDeComplejoDetalleInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'liquidacionGrupalDeComplejoDetalle.label', default: 'LiquidacionGrupalDeComplejoDetalle'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionGrupalDeComplejoDetalle.label', default: 'LiquidacionGrupalDeComplejoDetalle'), id])
            redirect(action: "show", id: id)
        }
    }
}
