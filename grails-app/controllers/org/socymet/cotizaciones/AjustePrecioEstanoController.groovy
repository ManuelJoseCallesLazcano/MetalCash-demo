package org.socymet.cotizaciones

import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN'])
class AjustePrecioEstanoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond AjustePrecioEstano.list(params), model: [ajustePrecioEstanoInstanceCount: AjustePrecioEstano.count()]
    }

    def show(AjustePrecioEstano ajustePrecioEstanoInstance) {
        respond ajustePrecioEstanoInstance
    }

    def create() {
        respond new AjustePrecioEstano(params)
    }

    @Transactional
    def save(AjustePrecioEstano ajustePrecioEstanoInstance) {
        if (ajustePrecioEstanoInstance == null) {
            notFound()
            return
        }

        if (ajustePrecioEstanoInstance.hasErrors()) {
            respond ajustePrecioEstanoInstance.errors, view: 'create'
            return
        }

        ajustePrecioEstanoInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'ajustePrecioEstano.label', default: 'AjustePrecioEstano'), ajustePrecioEstanoInstance.id])
                redirect ajustePrecioEstanoInstance
            }
            '*' { respond ajustePrecioEstanoInstance, [status: CREATED] }
        }
    }

    def edit(AjustePrecioEstano ajustePrecioEstanoInstance) {
        respond ajustePrecioEstanoInstance
    }

    @Transactional
    def update(AjustePrecioEstano ajustePrecioEstanoInstance) {
        if (ajustePrecioEstanoInstance == null) {
            notFound()
            return
        }

        if (ajustePrecioEstanoInstance.hasErrors()) {
            respond ajustePrecioEstanoInstance.errors, view: 'edit'
            return
        }

        ajustePrecioEstanoInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'AjustePrecioEstano.label', default: 'AjustePrecioEstano'), ajustePrecioEstanoInstance.id])
                redirect ajustePrecioEstanoInstance
            }
            '*' { respond ajustePrecioEstanoInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(AjustePrecioEstano ajustePrecioEstanoInstance) {

        if (ajustePrecioEstanoInstance == null) {
            notFound()
            return
        }

        ajustePrecioEstanoInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'AjustePrecioEstano.label', default: 'AjustePrecioEstano'), ajustePrecioEstanoInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'ajustePrecioEstano.label', default: 'AjustePrecioEstano'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
