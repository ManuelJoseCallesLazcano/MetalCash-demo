package org.socymet.recepcion

import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_RECEPCION','ROLE_CAJA'])
class ActualizacionCotizacionRecepcionController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ActualizacionCotizacionRecepcion.list(params), model:[actualizacionCotizacionRecepcionInstanceCount: ActualizacionCotizacionRecepcion.count()]
    }

    def show(ActualizacionCotizacionRecepcion actualizacionCotizacionRecepcionInstance) {
        respond actualizacionCotizacionRecepcionInstance
    }

    def create() {
        respond new ActualizacionCotizacionRecepcion(params)
    }

    @Transactional
    def save(ActualizacionCotizacionRecepcion actualizacionCotizacionRecepcionInstance) {
        if (actualizacionCotizacionRecepcionInstance == null) {
            notFound()
            return
        }

        if (actualizacionCotizacionRecepcionInstance.hasErrors()) {
            respond actualizacionCotizacionRecepcionInstance.errors, view:'create'
            return
        }

        actualizacionCotizacionRecepcionInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'actualizacionCotizacionRecepcion.label', default: 'ActualizacionCotizacionRecepcion'), actualizacionCotizacionRecepcionInstance.id])
                redirect actualizacionCotizacionRecepcionInstance
            }
            '*' { respond actualizacionCotizacionRecepcionInstance, [status: CREATED] }
        }
    }

    def edit(ActualizacionCotizacionRecepcion actualizacionCotizacionRecepcionInstance) {
        respond actualizacionCotizacionRecepcionInstance
    }

    @Transactional
    def update(ActualizacionCotizacionRecepcion actualizacionCotizacionRecepcionInstance) {
        if (actualizacionCotizacionRecepcionInstance == null) {
            notFound()
            return
        }

        if (actualizacionCotizacionRecepcionInstance.hasErrors()) {
            respond actualizacionCotizacionRecepcionInstance.errors, view:'edit'
            return
        }

        actualizacionCotizacionRecepcionInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ActualizacionCotizacionRecepcion.label', default: 'ActualizacionCotizacionRecepcion'), actualizacionCotizacionRecepcionInstance.id])
                redirect actualizacionCotizacionRecepcionInstance
            }
            '*'{ respond actualizacionCotizacionRecepcionInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(ActualizacionCotizacionRecepcion actualizacionCotizacionRecepcionInstance) {

        if (actualizacionCotizacionRecepcionInstance == null) {
            notFound()
            return
        }

        actualizacionCotizacionRecepcionInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ActualizacionCotizacionRecepcion.label', default: 'ActualizacionCotizacionRecepcion'), actualizacionCotizacionRecepcionInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'actualizacionCotizacionRecepcion.label', default: 'ActualizacionCotizacionRecepcion'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
