package org.socymet.caja

import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
class SubcuentaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Subcuenta.list(params), model: [subcuentaInstanceCount: Subcuenta.count()]
    }

    def show(Subcuenta subcuentaInstance) {
        respond subcuentaInstance
    }

    def create() {
        respond new Subcuenta(params)
    }

    @Transactional
    def save(Subcuenta subcuentaInstance) {
        if (subcuentaInstance == null) {
            notFound()
            return
        }

        if (subcuentaInstance.hasErrors()) {
            respond subcuentaInstance.errors, view: 'create'
            return
        }

        subcuentaInstance.save flush: true

        request.withFormat {
            form multipartForm {
//                flash.message = message(code: 'default.created.message', args: [message(code: 'subcuenta.label', default: 'Subcuenta'), subcuentaInstance.id])
                flash.message = message(code: 'default.created.message', args: [message(code: 'subcuenta.label', default: 'Subcuenta'), subcuentaInstance.toString()])
                redirect subcuentaInstance
            }
            '*' { respond subcuentaInstance, [status: CREATED] }
        }
    }

    def edit(Subcuenta subcuentaInstance) {
        respond subcuentaInstance
    }

    @Transactional
    def update(Subcuenta subcuentaInstance) {
        if (subcuentaInstance == null) {
            notFound()
            return
        }

        if (subcuentaInstance.hasErrors()) {
            respond subcuentaInstance.errors, view: 'edit'
            return
        }

        subcuentaInstance.save flush: true

        request.withFormat {
            form multipartForm {
//                flash.message = message(code: 'default.updated.message', args: [message(code: 'Subcuenta.label', default: 'Subcuenta'), subcuentaInstance.id])
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Subcuenta.label', default: 'Subcuenta'), subcuentaInstance.toString()])
                redirect subcuentaInstance
            }
            '*' { respond subcuentaInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Subcuenta subcuentaInstance) {

        if (subcuentaInstance == null) {
            notFound()
            return
        }

        subcuentaInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Subcuenta.label', default: 'Subcuenta'), subcuentaInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'subcuenta.label', default: 'Subcuenta'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    def subcuentasDeCuenta = {
        def cuenta = Cuenta.get(params.cuentaId)
        def subcuentas = Subcuenta.findAllByCuenta(cuenta)
        render g.select(from: subcuentas, optionKey: "id", id: 'id', name: "subcuenta")
    }
}
