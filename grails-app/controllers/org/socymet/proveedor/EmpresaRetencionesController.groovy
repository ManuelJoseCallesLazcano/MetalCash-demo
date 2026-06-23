package org.socymet.proveedor
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN'])
@Transactional
class EmpresaRetencionesController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [empresaRetencionesInstanceList: EmpresaRetenciones.list(params), empresaRetencionesInstanceTotal: EmpresaRetenciones.count()]
    }

    def create() {
        [empresaRetencionesInstance: new EmpresaRetenciones(params)]
    }

    def save() {
        def empresaRetencionesInstance = new EmpresaRetenciones(params)
        if (!empresaRetencionesInstance.save(flush: true)) {
            render(view: "create", model: [empresaRetencionesInstance: empresaRetencionesInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'empresaRetenciones.label', default: 'EmpresaRetenciones'), empresaRetencionesInstance.id])
        redirect(action: "show", id: empresaRetencionesInstance.id)
    }

    def show(Long id) {
        def empresaRetencionesInstance = EmpresaRetenciones.get(id)
        if (!empresaRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresaRetenciones.label', default: 'EmpresaRetenciones'), id])
            redirect(action: "list")
            return
        }

        [empresaRetencionesInstance: empresaRetencionesInstance]
    }

    def edit(Long id) {
        def empresaRetencionesInstance = EmpresaRetenciones.get(id)
        if (!empresaRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresaRetenciones.label', default: 'EmpresaRetenciones'), id])
            redirect(action: "list")
            return
        }

        [empresaRetencionesInstance: empresaRetencionesInstance]
    }

    def update(Long id, Long version) {
        def empresaRetencionesInstance = EmpresaRetenciones.get(id)
        if (!empresaRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresaRetenciones.label', default: 'EmpresaRetenciones'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (empresaRetencionesInstance.version > version) {
                empresaRetencionesInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'empresaRetenciones.label', default: 'EmpresaRetenciones')] as Object[],
                        "Another user has updated this EmpresaRetenciones while you were editing")
                render(view: "edit", model: [empresaRetencionesInstance: empresaRetencionesInstance])
                return
            }
        }

        empresaRetencionesInstance.properties = params

        if (!empresaRetencionesInstance.save(flush: true)) {
            render(view: "edit", model: [empresaRetencionesInstance: empresaRetencionesInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'empresaRetenciones.label', default: 'EmpresaRetenciones'), empresaRetencionesInstance.id])
        redirect(action: "show", id: empresaRetencionesInstance.id)
    }

    def delete(Long id) {
        def empresaRetencionesInstance = EmpresaRetenciones.get(id)
        if (!empresaRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresaRetenciones.label', default: 'EmpresaRetenciones'), id])
            redirect(action: "list")
            return
        }

        try {
            empresaRetencionesInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'empresaRetenciones.label', default: 'EmpresaRetenciones'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'empresaRetenciones.label', default: 'EmpresaRetenciones'), id])
            redirect(action: "show", id: id)
        }
    }
}
