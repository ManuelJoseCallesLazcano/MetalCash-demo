package org.smart.compositos

import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional(readOnly = true)
class IngenioController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Ingenio.list(params), model:[ingenioInstanceCount: Ingenio.count()]
    }

    def show(Ingenio ingenioInstance) {
        respond ingenioInstance
    }

    def create() {
        respond new Ingenio(params)
    }

    @Transactional
    def save(Ingenio ingenioInstance) {
        if (ingenioInstance == null) {
            notFound()
            return
        }

        if (ingenioInstance.hasErrors()) {
            respond ingenioInstance.errors, view:'create'
            return
        }

        ingenioInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'ingenio.label', default: 'Ingenio'), ingenioInstance.toString()])
                redirect ingenioInstance
            }
            '*' { respond ingenioInstance, [status: CREATED] }
        }
    }

    def edit(Ingenio ingenioInstance) {
        respond ingenioInstance
    }

    @Transactional
    def update(Ingenio ingenioInstance) {
        if (ingenioInstance == null) {
            notFound()
            return
        }

        if (ingenioInstance.hasErrors()) {
            respond ingenioInstance.errors, view:'edit'
            return
        }

        ingenioInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Ingenio.label', default: 'Ingenio'), ingenioInstance.toString()])
                redirect ingenioInstance
            }
            '*'{ respond ingenioInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Ingenio ingenioInstance) {

        if (ingenioInstance == null) {
            notFound()
            return
        }

        ingenioInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Ingenio.label', default: 'Ingenio'), ingenioInstance.toString()])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'ingenio.label', default: 'Ingenio'), params.toString()])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
