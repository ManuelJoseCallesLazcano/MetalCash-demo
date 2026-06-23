package org.socymet.calidad

import grails.converters.JSON
import org.socymet.recepcion.RecepcionDeEstano
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_RECEPCION','ROLE_CAJA'])
class ControlCalidadEstanoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ControlCalidadEstano.list(params), model: [controlCalidadEstanoInstanceCount: ControlCalidadEstano.count()]
    }

    def show(ControlCalidadEstano controlCalidadEstanoInstance) {
        respond controlCalidadEstanoInstance
    }

    def create() {
        respond new ControlCalidadEstano(params)
    }

    @Transactional
    def save(ControlCalidadEstano controlCalidadEstanoInstance) {
        if (controlCalidadEstanoInstance == null) {
            notFound()
            return
        }

        if (controlCalidadEstanoInstance.hasErrors()) {
            respond controlCalidadEstanoInstance.errors, view: 'create'
            return
        }

        controlCalidadEstanoInstance.save flush: true

        request.withFormat {
            form multipartForm {
//                flash.message = message(code: 'default.created.message', args: [message(code: 'controlCalidadEstano.label', default: 'ControlCalidadEstano'), controlCalidadEstanoInstance.id])
                flash.message = message(code: 'default.created.message', args: [message(code: 'controlCalidadEstano.label', default: 'ControlCalidadEstano'), controlCalidadEstanoInstance.recepcionDeEstano.toString()])
                redirect controlCalidadEstanoInstance
            }
            '*' { respond controlCalidadEstanoInstance, [status: CREATED] }
        }
    }

    def edit(ControlCalidadEstano controlCalidadEstanoInstance) {
        respond controlCalidadEstanoInstance
    }

    @Transactional
    def update(ControlCalidadEstano controlCalidadEstanoInstance) {
        if (controlCalidadEstanoInstance == null) {
            notFound()
            return
        }

        if (controlCalidadEstanoInstance.hasErrors()) {
            respond controlCalidadEstanoInstance.errors, view: 'edit'
            return
        }

        controlCalidadEstanoInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ControlCalidadEstano.label', default: 'ControlCalidadEstano'), controlCalidadEstanoInstance.id])
                redirect controlCalidadEstanoInstance
            }
            '*' { respond controlCalidadEstanoInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(ControlCalidadEstano controlCalidadEstanoInstance) {

        if (controlCalidadEstanoInstance == null) {
            notFound()
            return
        }

        controlCalidadEstanoInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ControlCalidadEstano.label', default: 'ControlCalidadEstano'), controlCalidadEstanoInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'controlCalidadEstano.label', default: 'ControlCalidadEstano'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    def leyes = {
        try {
            def recepcion = RecepcionDeEstano.get(params.recepcionDeEstanoId.toLong())
            if (recepcion){
                def controlCalidad = ControlCalidadEstano.findByRecepcionDeEstano(recepcion)
                if (controlCalidad){
                    render([
                        porcentajeMermaPromexbol: controlCalidad.porcentajeMermaPromexbol,
                        porcentajeHumedadPromexbol: controlCalidad.porcentajeHumedadPromexbol,
                        porcentajeEstanoPromexbol: controlCalidad.porcentajeEstanoPromexbol
                    ] as JSON)
                }else{
                    render([
                        porcentajeMermaPromexbol: 0,
                        porcentajeHumedadPromexbol: 0,
                        porcentajeEstanoPromexbol: 0
                    ] as JSON)
                }
            }
        }catch (Exception e){
            render([
                porcentajeMermaPromexbol: -1,
                porcentajeHumedadPromexbol: -1,
                porcentajeEstanoPromexbol: -1
            ] as JSON)
        }
    }
}
