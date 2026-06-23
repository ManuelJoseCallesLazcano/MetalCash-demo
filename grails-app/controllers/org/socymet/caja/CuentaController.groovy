package org.socymet.caja

import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
class CuentaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Cuenta.list(params), model:[cuentaInstanceCount: Cuenta.count()]
    }

    def show(Cuenta cuentaInstance) {
        respond cuentaInstance
    }

    def create() {
        respond new Cuenta(params)
    }

    @Transactional
    def save(Cuenta cuentaInstance) {
        if (cuentaInstance == null) {
            notFound()
            return
        }

        if (cuentaInstance.hasErrors()) {
            respond cuentaInstance.errors, view:'create'
            return
        }

        cuentaInstance.save flush:true

        request.withFormat {
            form multipartForm {
//                flash.message = message(code: 'default.created.message', args: [message(code: 'cuenta.label', default: 'Cuenta'), cuentaInstance.id])
                flash.message = message(code: 'default.created.message', args: [message(code: 'cuenta.label', default: 'Cuenta'), cuentaInstance.toString()])
                redirect cuentaInstance
            }
            '*' { respond cuentaInstance, [status: CREATED] }
        }
    }

    def edit(Cuenta cuentaInstance) {
        respond cuentaInstance
    }

    @Transactional
    def update(Cuenta cuentaInstance) {
        if (cuentaInstance == null) {
            notFound()
            return
        }

        if (cuentaInstance.hasErrors()) {
            respond cuentaInstance.errors, view:'edit'
            return
        }

        cuentaInstance.save flush:true

        request.withFormat {
            form multipartForm {
//                flash.message = message(code: 'default.updated.message', args: [message(code: 'Cuenta.label', default: 'Cuenta'), cuentaInstance.id])
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Cuenta.label', default: 'Cuenta'), cuentaInstance.id])
                redirect cuentaInstance
            }
            '*'{ respond cuentaInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Cuenta cuentaInstance) {

        if (cuentaInstance == null) {
            notFound()
            return
        }

        cuentaInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Cuenta.label', default: 'Cuenta'), cuentaInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'cuenta.label', default: 'Cuenta'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
