package org.socymet.proveedor

import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_RECEPCION'])
class EmpresaSeccionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond EmpresaSeccion.list(params), model:[empresaSeccionInstanceCount: EmpresaSeccion.count()]
    }

    def show(EmpresaSeccion empresaSeccionInstance) {
        respond empresaSeccionInstance
    }

    def create() {
        respond new EmpresaSeccion(params)
    }

    @Transactional
    def save(EmpresaSeccion empresaSeccionInstance) {
        if (empresaSeccionInstance == null) {
            notFound()
            return
        }

        if (empresaSeccionInstance.hasErrors()) {
            respond empresaSeccionInstance.errors, view:'create'
            return
        }

        empresaSeccionInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'empresaSeccion.label', default: 'EmpresaSeccion'), empresaSeccionInstance.id])
                redirect empresaSeccionInstance
            }
            '*' { respond empresaSeccionInstance, [status: CREATED] }
        }
    }

    def edit(EmpresaSeccion empresaSeccionInstance) {
        respond empresaSeccionInstance
    }

    @Transactional
    def update(EmpresaSeccion empresaSeccionInstance) {
        if (empresaSeccionInstance == null) {
            notFound()
            return
        }

        if (empresaSeccionInstance.hasErrors()) {
            respond empresaSeccionInstance.errors, view:'edit'
            return
        }

        empresaSeccionInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'EmpresaSeccion.label', default: 'EmpresaSeccion'), empresaSeccionInstance.id])
                redirect empresaSeccionInstance
            }
            '*'{ respond empresaSeccionInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(EmpresaSeccion empresaSeccionInstance) {

        if (empresaSeccionInstance == null) {
            notFound()
            return
        }

        empresaSeccionInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'EmpresaSeccion.label', default: 'EmpresaSeccion'), empresaSeccionInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresaSeccion.label', default: 'EmpresaSeccion'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
