package org.socymet.proveedor
import grails.converters.JSON
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION'])
@Transactional
class RetencionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def q = params.q?.trim()
        if (q) {
            def pattern = "%${q}%"
            [retencionInstanceList : Retencion.findAllByDescripcionIlike(pattern, params),
             retencionInstanceTotal: Retencion.countByDescripcionIlike(pattern)]
        } else {
            [retencionInstanceList: Retencion.list(params), retencionInstanceTotal: Retencion.count()]
        }
    }

    def create() {
        [retencionInstance: new Retencion(params)]
    }

    def save() {
        def retencionInstance = new Retencion(params)
        if (!retencionInstance.save(flush: true)) {
            render(view: "create", model: [retencionInstance: retencionInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'retencion.label', default: 'Retencion'), retencionInstance.toString()])
        redirect(action: "show", id: retencionInstance.id)
    }

    def show(Long id) {
        def retencionInstance = Retencion.get(id)
        if (!retencionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'retencion.label', default: 'Retencion'), id])
            redirect(action: "list")
            return
        }

        [retencionInstance: retencionInstance]
    }

    def edit(Long id) {
        def retencionInstance = Retencion.get(id)
        if (!retencionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'retencion.label', default: 'Retencion'), id])
            redirect(action: "list")
            return
        }

        [retencionInstance: retencionInstance]
    }

    def update(Long id, Long version) {
        def retencionInstance = Retencion.get(id)
        if (!retencionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'retencion.label', default: 'Retencion'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (retencionInstance.version > version) {
                retencionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'retencion.label', default: 'Retencion')] as Object[],
                        "Another user has updated this Retencion while you were editing")
                render(view: "edit", model: [retencionInstance: retencionInstance])
                return
            }
        }

        retencionInstance.properties = params

        if (!retencionInstance.save(flush: true)) {
            render(view: "edit", model: [retencionInstance: retencionInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'retencion.label', default: 'Retencion'), retencionInstance.toString()])
        redirect(action: "show", id: retencionInstance.id)
    }

    def delete(Long id) {
        def retencionInstance = Retencion.get(id)
        if (!retencionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'retencion.label', default: 'Retencion'), id])
            redirect(action: "list")
            return
        }

        try {
            retencionInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'retencion.label', default: 'Retencion'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'retencion.label', default: 'Retencion'), id])
            redirect(action: "show", id: id)
        }
    }

    def mostrar() {
        def retencion = Retencion.get(params.retencionId)
        log.info("*********** RETENCION ID: ${params.retencionId} ***************")
        render([
            descripcion: retencion.descripcion,
            tipoDeRetencion: retencion.tipoDeRetencion,
            cantidadDescuento: retencion.cantidadDescuento,
            unidadDeDescuento: retencion.unidadDeDescuento,
            asignacionDelDescuento: retencion.asignacionDelDescuento
        ] as JSON)
    }
}
