package org.socymet.calidad

import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_CONTROL_CALIDAD'])
class ControlCalidadOroController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ControlCalidadOro.list(params), model:[controlCalidadOroInstanceCount: ControlCalidadOro.count()]
    }

    def show(ControlCalidadOro controlCalidadOroInstance) {
        respond controlCalidadOroInstance
    }

    def create() {
        respond new ControlCalidadOro(params)
    }

    @Transactional
    def save(ControlCalidadOro controlCalidadOroInstance) {
        if (controlCalidadOroInstance == null) {
            notFound()
            return
        }

        if (controlCalidadOroInstance.hasErrors()) {
            respond controlCalidadOroInstance.errors, view:'create'
            return
        }

        controlCalidadOroInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'controlCalidadOro.label', default: 'ControlCalidadOro'), controlCalidadOroInstance.toString()])
                redirect controlCalidadOroInstance
            }
            '*' { respond controlCalidadOroInstance, [status: CREATED] }
        }
    }

    def edit(ControlCalidadOro controlCalidadOroInstance) {
        respond controlCalidadOroInstance
    }

    @Transactional
    def update(ControlCalidadOro controlCalidadOroInstance) {
        if (controlCalidadOroInstance == null) {
            notFound()
            return
        }

        if (controlCalidadOroInstance.hasErrors()) {
            respond controlCalidadOroInstance.errors, view:'edit'
            return
        }

        controlCalidadOroInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ControlCalidadOro.label', default: 'ControlCalidadOro'), controlCalidadOroInstance.toString()])
                redirect controlCalidadOroInstance
            }
            '*'{ respond controlCalidadOroInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(ControlCalidadOro controlCalidadOroInstance) {

        if (controlCalidadOroInstance == null) {
            notFound()
            return
        }

        controlCalidadOroInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ControlCalidadOro.label', default: 'ControlCalidadOro'), controlCalidadOroInstance.toString()])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'controlCalidadOro.label', default: 'ControlCalidadOro'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
