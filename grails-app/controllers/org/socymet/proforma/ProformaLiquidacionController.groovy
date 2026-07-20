package org.socymet.proforma

import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
class ProformaLiquidacionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ProformaLiquidacion.list(params), model:[proformaLiquidacionInstanceCount: ProformaLiquidacion.count()]
    }

    def show(ProformaLiquidacion proformaLiquidacionInstance) {
        respond proformaLiquidacionInstance
    }

    def create() {
        respond new ProformaLiquidacion(params)
    }

    @Transactional
    def save(ProformaLiquidacion proformaLiquidacionInstance) {
        if (proformaLiquidacionInstance == null) {
            notFound()
            return
        }

        if (proformaLiquidacionInstance.hasErrors()) {
            respond proformaLiquidacionInstance.errors, view:'create'
            return
        }

        proformaLiquidacionInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'proformaLiquidacion.label', default: 'ProformaLiquidacion'), proformaLiquidacionInstance.id])
                redirect proformaLiquidacionInstance
            }
            '*' { respond proformaLiquidacionInstance, [status: CREATED] }
        }
    }

    def edit(ProformaLiquidacion proformaLiquidacionInstance) {
        respond proformaLiquidacionInstance
    }

    @Transactional
    def update(ProformaLiquidacion proformaLiquidacionInstance) {
        if (proformaLiquidacionInstance == null) {
            notFound()
            return
        }

        if (proformaLiquidacionInstance.hasErrors()) {
            respond proformaLiquidacionInstance.errors, view:'edit'
            return
        }

        proformaLiquidacionInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ProformaLiquidacion.label', default: 'ProformaLiquidacion'), proformaLiquidacionInstance.id])
                redirect proformaLiquidacionInstance
            }
            '*'{ respond proformaLiquidacionInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(ProformaLiquidacion proformaLiquidacionInstance) {

        if (proformaLiquidacionInstance == null) {
            notFound()
            return
        }

        proformaLiquidacionInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ProformaLiquidacion.label', default: 'ProformaLiquidacion'), proformaLiquidacionInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'proformaLiquidacion.label', default: 'ProformaLiquidacion'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def createReport = {
        def proformaLiquidacion = ProformaLiquidacion.get(params.id)
        def realPath = org.socymet.util.ReportesRuntime.realPath("/reports/images/")
        params.realPath=realPath+"/"
        chain(controller:'jasper',action:'index',model:[data:proformaLiquidacion],params:params)
    }
}
