package org.socymet.cotizaciones

import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN'])
class AlicuotaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.sort = "fecha"
        params.order = "desc"
        def list = Alicuota.list(params)
        [alicuotaInstanceList: list, alicuotaInstanceTotal: Alicuota.count()]
    }

    def show(Alicuota alicuotaInstance) {
        [alicuotaInstance: alicuotaInstance]
    }

    def create() {
        [alicuotaInstance: new Alicuota(params)]
    }

    @Transactional
    def save(Alicuota alicuotaInstance) {
        if (alicuotaInstance == null) {
            notFound()
            return
        }

        if (alicuotaInstance.hasErrors()) {
            render view: 'create', model: [alicuotaInstance: alicuotaInstance]
            return
        }

        alicuotaInstance.save flush: true

        flash.message = message(code: 'default.created.message', args: [message(code: 'alicuota.label', default: 'Alicuota'), alicuotaInstance.toString()])
        redirect action: 'show', id: alicuotaInstance.id
    }

    def edit(Alicuota alicuotaInstance) {
        [alicuotaInstance: alicuotaInstance]
    }

    @Transactional
    def update(Alicuota alicuotaInstance) {
        if (alicuotaInstance == null) {
            notFound()
            return
        }

        if (alicuotaInstance.hasErrors()) {
            render view: 'edit', model: [alicuotaInstance: alicuotaInstance]
            return
        }

        alicuotaInstance.save flush: true

        flash.message = message(code: 'default.updated.message', args: [message(code: 'alicuota.label', default: 'Alicuota'), alicuotaInstance.toString()])
        redirect action: 'show', id: alicuotaInstance.id
    }

    @Transactional
    def delete(Alicuota alicuotaInstance) {
        if (alicuotaInstance == null) {
            notFound()
            return
        }

        alicuotaInstance.delete flush: true

        flash.message = message(code: 'default.deleted.message', args: [message(code: 'alicuota.label', default: 'Alicuota'), alicuotaInstance.toString()])
        redirect action: 'index'
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'alicuota.label', default: 'Alicuota'), params.toString()])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
