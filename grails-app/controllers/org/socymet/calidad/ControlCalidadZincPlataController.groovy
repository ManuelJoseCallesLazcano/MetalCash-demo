package org.socymet.calidad
import grails.converters.JSON
import grails.gorm.transactions.Transactional

import org.socymet.recepcion.RecepcionDeComplejo
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_CONTROL_CALIDAD'])
@Transactional
class ControlCalidadZincPlataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [controlCalidadZincPlataInstanceList: ControlCalidadZincPlata.list(params), controlCalidadZincPlataInstanceTotal: ControlCalidadZincPlata.count()]
    }

    def create() {
        [controlCalidadZincPlataInstance: new ControlCalidadZincPlata(params)]
    }

    def save() {
        def controlCalidadZincPlataInstance = new ControlCalidadZincPlata(params)
        if (!controlCalidadZincPlataInstance.save(flush: true)) {
            render(view: "create", model: [controlCalidadZincPlataInstance: controlCalidadZincPlataInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'controlCalidadZincPlata.label', default: 'ControlCalidadZincPlata'), controlCalidadZincPlataInstance.id])
        redirect(action: "show", id: controlCalidadZincPlataInstance.id)
    }

    def show(Long id) {
        def controlCalidadZincPlataInstance = ControlCalidadZincPlata.get(id)
        if (!controlCalidadZincPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'controlCalidadZincPlata.label', default: 'ControlCalidadZincPlata'), id])
            redirect(action: "list")
            return
        }

        [controlCalidadZincPlataInstance: controlCalidadZincPlataInstance]
    }

    def edit(Long id) {
        def controlCalidadZincPlataInstance = ControlCalidadZincPlata.get(id)
        if (!controlCalidadZincPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'controlCalidadZincPlata.label', default: 'ControlCalidadZincPlata'), id])
            redirect(action: "list")
            return
        }

        [controlCalidadZincPlataInstance: controlCalidadZincPlataInstance]
    }

    def update(Long id, Long version) {
        def controlCalidadZincPlataInstance = ControlCalidadZincPlata.get(id)
        if (!controlCalidadZincPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'controlCalidadZincPlata.label', default: 'ControlCalidadZincPlata'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (controlCalidadZincPlataInstance.version > version) {
                controlCalidadZincPlataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'controlCalidadZincPlata.label', default: 'ControlCalidadZincPlata')] as Object[],
                        "Another user has updated this ControlCalidadZincPlata while you were editing")
                render(view: "edit", model: [controlCalidadZincPlataInstance: controlCalidadZincPlataInstance])
                return
            }
        }

        controlCalidadZincPlataInstance.properties = params

        if (!controlCalidadZincPlataInstance.save(flush: true)) {
            render(view: "edit", model: [controlCalidadZincPlataInstance: controlCalidadZincPlataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'controlCalidadZincPlata.label', default: 'ControlCalidadZincPlata'), controlCalidadZincPlataInstance.id])
        redirect(action: "show", id: controlCalidadZincPlataInstance.id)
    }

    def delete(Long id) {
        def controlCalidadZincPlataInstance = ControlCalidadZincPlata.get(id)
        if (!controlCalidadZincPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'controlCalidadZincPlata.label', default: 'ControlCalidadZincPlata'), id])
            redirect(action: "list")
            return
        }

        try {
            controlCalidadZincPlataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'controlCalidadZincPlata.label', default: 'ControlCalidadZincPlata'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'controlCalidadZincPlata.label', default: 'ControlCalidadZincPlata'), id])
            redirect(action: "show", id: id)
        }
    }

    def leyes = {
        try{
            def recepcion = RecepcionDeComplejo.get(params.recepcionDeComplejoId.toLong())
            if (recepcion){
                def controlCalidad = ControlCalidadZincPlata.findByRecepcionDeComplejo(recepcion)
                if (controlCalidad){
                    render([
                        porcentajeMermaPromexbol: controlCalidad.porcentajeMermaPromexbol,
                        porcentajeHumedadPromexbol: controlCalidad.porcentajeHumedadPromexbol,
                        porcentajeZincPromexbol: controlCalidad.porcentajeZincPromexbol,
                        porcentajePlataPromexbol: controlCalidad.porcentajePlataPromexbol
                    ] as JSON)
                }else{
                    render([
                        porcentajeMermaPromexbol: 0,
                        porcentajeHumedadPromexbol: 0,
                        porcentajeZincPromexbol: 0,
                        porcentajePlataPromexbol: 0
                    ] as JSON)
                }
            }
        }catch (Exception e){
            render([
                porcentajeMermaPromexbol: -1,
                porcentajeHumedadPromexbol: -1,
                porcentajeZincPromexbol: -1,
                porcentajePlataPromexbol: -1
            ] as JSON)
        }
    }
}
