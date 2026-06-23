package org.socymet.caja

import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_CAJA'])
class CierreCajaDetalleController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond CierreCajaDetalle.list(params), model: [cierreCajaDetalleInstanceCount: CierreCajaDetalle.count()]
    }

    def show(CierreCajaDetalle cierreCajaDetalleInstance) {
        respond cierreCajaDetalleInstance
    }

    def create() {
        respond new CierreCajaDetalle(params)
    }

    @Transactional
    def save(CierreCajaDetalle cierreCajaDetalleInstance) {
        if (cierreCajaDetalleInstance == null) {
            notFound()
            return
        }

        if (cierreCajaDetalleInstance.hasErrors()) {
            respond cierreCajaDetalleInstance.errors, view: 'create'
            return
        }

        cierreCajaDetalleInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'cierreCajaDetalle.label', default: 'CierreCajaDetalle'), cierreCajaDetalleInstance.id])
                redirect cierreCajaDetalleInstance
            }
            '*' { respond cierreCajaDetalleInstance, [status: CREATED] }
        }
    }

    def edit(CierreCajaDetalle cierreCajaDetalleInstance) {
        respond cierreCajaDetalleInstance
    }

    @Transactional
    def update(CierreCajaDetalle cierreCajaDetalleInstance) {
        if (cierreCajaDetalleInstance == null) {
            notFound()
            return
        }

        if (cierreCajaDetalleInstance.hasErrors()) {
            respond cierreCajaDetalleInstance.errors, view: 'edit'
            return
        }

        cierreCajaDetalleInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'CierreCajaDetalle.label', default: 'CierreCajaDetalle'), cierreCajaDetalleInstance.id])
                redirect cierreCajaDetalleInstance
            }
            '*' { respond cierreCajaDetalleInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(CierreCajaDetalle cierreCajaDetalleInstance) {

        if (cierreCajaDetalleInstance == null) {
            notFound()
            return
        }

        cierreCajaDetalleInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'CierreCajaDetalle.label', default: 'CierreCajaDetalle'), cierreCajaDetalleInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'cierreCajaDetalle.label', default: 'CierreCajaDetalle'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
