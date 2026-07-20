package org.socymet.liquidacion

import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
class LiquidacionDeOroController {

//    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond LiquidacionDeOro.list(params), model:[liquidacionDeOroInstanceCount: LiquidacionDeOro.count()]
    }

    def show(LiquidacionDeOro liquidacionDeOroInstance) {
        respond liquidacionDeOroInstance
    }

    def create() {
        respond new LiquidacionDeOro(params)
    }

    @Transactional
    def save(LiquidacionDeOro liquidacionDeOroInstance) {
        if (liquidacionDeOroInstance == null) {
            notFound()
            return
        }

        if (liquidacionDeOroInstance.hasErrors()) {
            respond liquidacionDeOroInstance.errors, view:'create'
            return
        }

        liquidacionDeOroInstance.save flush:true

        request.withFormat {
            form multipartForm {
//                flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionDeOro.label', default: 'LiquidacionDeOro'), liquidacionDeOroInstance.id])
                flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionDeOro.label', default: 'LiquidacionDeOro'), liquidacionDeOroInstance.toString()])
                redirect liquidacionDeOroInstance
            }
            '*' { respond liquidacionDeOroInstance, [status: CREATED] }
        }
    }

    def edit(LiquidacionDeOro liquidacionDeOroInstance) {
        respond liquidacionDeOroInstance
    }

    @Transactional
    def update(LiquidacionDeOro liquidacionDeOroInstance) {
        if (liquidacionDeOroInstance == null) {
            notFound()
            return
        }

        if (liquidacionDeOroInstance.hasErrors()) {
            respond liquidacionDeOroInstance.errors, view:'edit'
            return
        }

        liquidacionDeOroInstance.save flush:true

        request.withFormat {
            form multipartForm {
//                flash.message = message(code: 'default.updated.message', args: [message(code: 'LiquidacionDeOro.label', default: 'LiquidacionDeOro'), liquidacionDeOroInstance.id])
                flash.message = message(code: 'default.updated.message', args: [message(code: 'LiquidacionDeOro.label', default: 'LiquidacionDeOro'), liquidacionDeOroInstance.toString()])
                redirect liquidacionDeOroInstance
            }
            '*'{ respond liquidacionDeOroInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(LiquidacionDeOro liquidacionDeOroInstance) {

        if (liquidacionDeOroInstance == null) {
            notFound()
            return
        }

        liquidacionDeOroInstance.delete flush:true

        request.withFormat {
            form multipartForm {
//                flash.message = message(code: 'default.deleted.message', args: [message(code: 'LiquidacionDeOro.label', default: 'LiquidacionDeOro'), liquidacionDeOroInstance.id])
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'LiquidacionDeOro.label', default: 'LiquidacionDeOro'), liquidacionDeOroInstance.toString()])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeOro.label', default: 'LiquidacionDeOro'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def crearReporte = {
        def realPath = org.socymet.util.ReportesRuntime.realPath("/reports/images/")
        params.realPath = realPath+"/"
        params.SUBREPORT_DIR = "${org.socymet.util.ReportesRuntime.realPath('/reports')}/"
        chain(controller:'jasper',action:'index',params:params)
    }
}
