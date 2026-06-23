package org.smart.compositos

import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
class CompradorController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Comprador.list(params), model:[compradorInstanceCount: Comprador.count()]
    }

    def show(Comprador compradorInstance) {
        respond compradorInstance
    }

    def create() {
        respond new Comprador(params)
    }

    @Transactional
    def save(Comprador compradorInstance) {
        if (compradorInstance == null) {
            notFound()
            return
        }

        if (compradorInstance.hasErrors()) {
            respond compradorInstance.errors, view:'create'
            return
        }

        compradorInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'comprador.label', default: 'Comprador'), compradorInstance.toString()])
                redirect compradorInstance
            }
            '*' { respond compradorInstance, [status: CREATED] }
        }
    }

    def edit(Comprador compradorInstance) {
        respond compradorInstance
    }

    @Transactional
    def update(Comprador compradorInstance) {
        if (compradorInstance == null) {
            notFound()
            return
        }

        if (compradorInstance.hasErrors()) {
            respond compradorInstance.errors, view:'edit'
            return
        }

        compradorInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Comprador.label', default: 'Comprador'), compradorInstance.toString()])
                redirect compradorInstance
            }
            '*'{ respond compradorInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Comprador compradorInstance) {

        if (compradorInstance == null) {
            notFound()
            return
        }

        compradorInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Comprador.label', default: 'Comprador'), compradorInstance.toString()])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'comprador.label', default: 'Comprador'), params.toString()])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
