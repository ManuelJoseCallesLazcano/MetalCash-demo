package org.socymet.proveedor
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class CuadrillaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [cuadrillaInstanceList: Cuadrilla.list(params), cuadrillaInstanceTotal: Cuadrilla.count()]
    }

    def create() {
        [cuadrillaInstance: new Cuadrilla(params)]
    }

    def save() {
        def cuadrillaInstance = new Cuadrilla(params)
        if (!cuadrillaInstance.save(flush: true)) {
            render(view: "create", model: [cuadrillaInstance: cuadrillaInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'cuadrilla.label', default: 'Cuadrilla'), cuadrillaInstance.id])
        redirect(action: "show", id: cuadrillaInstance.id)
    }

    def show(Long id) {
        def cuadrillaInstance = Cuadrilla.get(id)
        if (!cuadrillaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cuadrilla.label', default: 'Cuadrilla'), id])
            redirect(action: "list")
            return
        }

        [cuadrillaInstance: cuadrillaInstance]
    }

    def edit(Long id) {
        def cuadrillaInstance = Cuadrilla.get(id)
        if (!cuadrillaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cuadrilla.label', default: 'Cuadrilla'), id])
            redirect(action: "list")
            return
        }

        [cuadrillaInstance: cuadrillaInstance]
    }

    def update(Long id, Long version) {
        def cuadrillaInstance = Cuadrilla.get(id)
        if (!cuadrillaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cuadrilla.label', default: 'Cuadrilla'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (cuadrillaInstance.version > version) {
                cuadrillaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'cuadrilla.label', default: 'Cuadrilla')] as Object[],
                        "Another user has updated this Cuadrilla while you were editing")
                render(view: "edit", model: [cuadrillaInstance: cuadrillaInstance])
                return
            }
        }

        cuadrillaInstance.properties = params

        if (!cuadrillaInstance.save(flush: true)) {
            render(view: "edit", model: [cuadrillaInstance: cuadrillaInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'cuadrilla.label', default: 'Cuadrilla'), cuadrillaInstance.id])
        redirect(action: "show", id: cuadrillaInstance.id)
    }

    def delete(Long id) {
        def cuadrillaInstance = Cuadrilla.get(id)
        if (!cuadrillaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cuadrilla.label', default: 'Cuadrilla'), id])
            redirect(action: "list")
            return
        }

        try {
            cuadrillaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'cuadrilla.label', default: 'Cuadrilla'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'cuadrilla.label', default: 'Cuadrilla'), id])
            redirect(action: "show", id: id)
        }
    }
}
