package org.socymet.org.socymet.reportes

import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
class CompositoLotesParticipacionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond CompositoLotesParticipacion.list(params), model:[compositoLotesParticipacionInstanceCount: CompositoLotesParticipacion.count()]
    }

    def show(CompositoLotesParticipacion compositoLotesParticipacionInstance) {
        respond compositoLotesParticipacionInstance
    }

    def create() {
        respond new CompositoLotesParticipacion(params)
    }

    @Transactional
    def save(CompositoLotesParticipacion compositoLotesParticipacionInstance) {
        if (compositoLotesParticipacionInstance == null) {
            notFound()
            return
        }

        if (compositoLotesParticipacionInstance.hasErrors()) {
            respond compositoLotesParticipacionInstance.errors, view:'create'
            return
        }

        compositoLotesParticipacionInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'compositoLotesParticipacion.label', default: 'CompositoLotesParticipacion'), compositoLotesParticipacionInstance.id])
                redirect compositoLotesParticipacionInstance
            }
            '*' { respond compositoLotesParticipacionInstance, [status: CREATED] }
        }
    }

    def edit(CompositoLotesParticipacion compositoLotesParticipacionInstance) {
        respond compositoLotesParticipacionInstance
    }

    @Transactional
    def update(CompositoLotesParticipacion compositoLotesParticipacionInstance) {
        if (compositoLotesParticipacionInstance == null) {
            notFound()
            return
        }

        if (compositoLotesParticipacionInstance.hasErrors()) {
            respond compositoLotesParticipacionInstance.errors, view:'edit'
            return
        }

        compositoLotesParticipacionInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'CompositoLotesParticipacion.label', default: 'CompositoLotesParticipacion'), compositoLotesParticipacionInstance.id])
                redirect compositoLotesParticipacionInstance
            }
            '*'{ respond compositoLotesParticipacionInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(CompositoLotesParticipacion compositoLotesParticipacionInstance) {

        if (compositoLotesParticipacionInstance == null) {
            notFound()
            return
        }

        compositoLotesParticipacionInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'CompositoLotesParticipacion.label', default: 'CompositoLotesParticipacion'), compositoLotesParticipacionInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'compositoLotesParticipacion.label', default: 'CompositoLotesParticipacion'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
