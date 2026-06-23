package org.socymet.caja

import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
class IngresoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Ingreso.list(params), model: [ingresoInstanceCount: Ingreso.count()]
    }

    def show(Ingreso ingresoInstance) {
        respond ingresoInstance
    }

    def create() {
        respond new Ingreso(params)
    }

    @Transactional
    def save(Ingreso ingresoInstance) {
        if (ingresoInstance == null) {
            notFound()
            return
        }

        if (ingresoInstance.hasErrors()) {
            respond ingresoInstance.errors, view: 'create'
            return
        }

        ingresoInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'ingreso.label', default: 'Ingreso'), ingresoInstance.id])
                redirect ingresoInstance
            }
            '*' { respond ingresoInstance, [status: CREATED] }
        }
    }

    def edit(Ingreso ingresoInstance) {
        respond ingresoInstance
    }

    @Transactional
    def update(Ingreso ingresoInstance) {
        if (ingresoInstance == null) {
            notFound()
            return
        }

        if (ingresoInstance.hasErrors()) {
            respond ingresoInstance.errors, view: 'edit'
            return
        }

        ingresoInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Ingreso.label', default: 'Ingreso'), ingresoInstance.id])
                redirect ingresoInstance
            }
            '*' { respond ingresoInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Ingreso ingresoInstance) {

        if (ingresoInstance == null) {
            notFound()
            return
        }

        ingresoInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Ingreso.label', default: 'Ingreso'), ingresoInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'ingreso.label', default: 'Ingreso'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    def createReport = {
        def pesajeNormal = Ingreso.get(params.id)
        def realPath = servletContext.getRealPath("/reports/images/")
        params.realPath=realPath+"/"
        params.SUBREPORT_DIR = "${servletContext.getRealPath('/reports')}/"
        chain(controller:'jasper',action:'index',model:[data:pesajeNormal],params:params)
    }
}
