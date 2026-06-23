package org.socymet.proforma

import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
class ProformaGeneralLiquidacionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ProformaGeneralLiquidacion.list(params), model:[proformaGeneralLiquidacionInstanceCount: ProformaGeneralLiquidacion.count()]
    }

    def show(ProformaGeneralLiquidacion proformaGeneralLiquidacionInstance) {
        respond proformaGeneralLiquidacionInstance
    }

    def create() {
        respond new ProformaGeneralLiquidacion(params)
    }

    @Transactional
    def save(ProformaGeneralLiquidacion proformaGeneralLiquidacionInstance) {
        if (proformaGeneralLiquidacionInstance == null) {
            notFound()
            return
        }

        if (proformaGeneralLiquidacionInstance.hasErrors()) {
            respond proformaGeneralLiquidacionInstance.errors, view:'create'
            return
        }

        proformaGeneralLiquidacionInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'proformaGeneralLiquidacion.label', default: 'ProformaGeneralLiquidacion'), proformaGeneralLiquidacionInstance.id])
                redirect proformaGeneralLiquidacionInstance
            }
            '*' { respond proformaGeneralLiquidacionInstance, [status: CREATED] }
        }
    }

    def edit(ProformaGeneralLiquidacion proformaGeneralLiquidacionInstance) {
        respond proformaGeneralLiquidacionInstance
    }

    @Transactional
    def update(ProformaGeneralLiquidacion proformaGeneralLiquidacionInstance) {
        if (proformaGeneralLiquidacionInstance == null) {
            notFound()
            return
        }

        if (proformaGeneralLiquidacionInstance.hasErrors()) {
            respond proformaGeneralLiquidacionInstance.errors, view:'edit'
            return
        }

        proformaGeneralLiquidacionInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ProformaGeneralLiquidacion.label', default: 'ProformaGeneralLiquidacion'), proformaGeneralLiquidacionInstance.id])
                redirect proformaGeneralLiquidacionInstance
            }
            '*'{ respond proformaGeneralLiquidacionInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(ProformaGeneralLiquidacion proformaGeneralLiquidacionInstance) {

        if (proformaGeneralLiquidacionInstance == null) {
            notFound()
            return
        }

        proformaGeneralLiquidacionInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ProformaGeneralLiquidacion.label', default: 'ProformaGeneralLiquidacion'), proformaGeneralLiquidacionInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'proformaGeneralLiquidacion.label', default: 'ProformaGeneralLiquidacion'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
