package org.socymet.caja

import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_CAJA'])
class CierreCajaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond CierreCaja.list(params), model: [cierreCajaInstanceCount: CierreCaja.count()]
    }

    def show(CierreCaja cierreCajaInstance) {
        respond cierreCajaInstance
    }

    def create() {
        respond new CierreCaja(params)
    }

    @Transactional
    def save(CierreCaja cierreCajaInstance) {
        if (cierreCajaInstance == null) {
            notFound()
            return
        }

        if (cierreCajaInstance.hasErrors()) {
            respond cierreCajaInstance.errors, view: 'create'
            return
        }

        cierreCajaInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'cierreCaja.label', default: 'CierreCaja'), cierreCajaInstance.id])
                redirect cierreCajaInstance
            }
            '*' { respond cierreCajaInstance, [status: CREATED] }
        }
    }

    def edit(CierreCaja cierreCajaInstance) {
        respond cierreCajaInstance
    }

    @Transactional
    def update(CierreCaja cierreCajaInstance) {
        if (cierreCajaInstance == null) {
            notFound()
            return
        }

        if (cierreCajaInstance.hasErrors()) {
            respond cierreCajaInstance.errors, view: 'edit'
            return
        }

        cierreCajaInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'CierreCaja.label', default: 'CierreCaja'), cierreCajaInstance.id])
                redirect cierreCajaInstance
            }
            '*' { respond cierreCajaInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(CierreCaja cierreCajaInstance) {

        if (cierreCajaInstance == null) {
            notFound()
            return
        }

        cierreCajaInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'CierreCaja.label', default: 'CierreCaja'), cierreCajaInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'cierreCaja.label', default: 'CierreCaja'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    def createReport = {
        def pesajeNormal = CierreCaja.get(params.id)
        def realPath = servletContext.getRealPath("/reports/images/")
        params.realPath=realPath+"/"
        params.SUBREPORT_DIR = "${servletContext.getRealPath('/reports')}/"
        chain(controller:'jasper',action:'index',model:[data:pesajeNormal],params:params)
    }
}
