package org.socymet.calidad
import grails.converters.JSON
import grails.gorm.transactions.Transactional

import org.socymet.recepcion.RecepcionDeComplejo
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class ControlCalidadCobrePlataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [controlCalidadCobrePlataInstanceList: ControlCalidadCobrePlata.list(params), controlCalidadCobrePlataInstanceTotal: ControlCalidadCobrePlata.count()]
    }

    def create() {
        [controlCalidadCobrePlataInstance: new ControlCalidadCobrePlata(params)]
    }

    def save() {
        def controlCalidadCobrePlataInstance = new ControlCalidadCobrePlata(params)
        if (!controlCalidadCobrePlataInstance.save(flush: true)) {
            render(view: "create", model: [controlCalidadCobrePlataInstance: controlCalidadCobrePlataInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'controlCalidadCobrePlata.label', default: 'ControlCalidadCobrePlata'), controlCalidadCobrePlataInstance.id])
        redirect(action: "show", id: controlCalidadCobrePlataInstance.id)
    }

    def show(Long id) {
        def controlCalidadCobrePlataInstance = ControlCalidadCobrePlata.get(id)
        if (!controlCalidadCobrePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'controlCalidadCobrePlata.label', default: 'ControlCalidadCobrePlata'), id])
            redirect(action: "list")
            return
        }

        [controlCalidadCobrePlataInstance: controlCalidadCobrePlataInstance]
    }

    def edit(Long id) {
        def controlCalidadCobrePlataInstance = ControlCalidadCobrePlata.get(id)
        if (!controlCalidadCobrePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'controlCalidadCobrePlata.label', default: 'ControlCalidadCobrePlata'), id])
            redirect(action: "list")
            return
        }

        [controlCalidadCobrePlataInstance: controlCalidadCobrePlataInstance]
    }

    def update(Long id, Long version) {
        def controlCalidadCobrePlataInstance = ControlCalidadCobrePlata.get(id)
        if (!controlCalidadCobrePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'controlCalidadCobrePlata.label', default: 'ControlCalidadCobrePlata'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (controlCalidadCobrePlataInstance.version > version) {
                controlCalidadCobrePlataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'controlCalidadCobrePlata.label', default: 'ControlCalidadCobrePlata')] as Object[],
                        "Another user has updated this ControlCalidadCobrePlata while you were editing")
                render(view: "edit", model: [controlCalidadCobrePlataInstance: controlCalidadCobrePlataInstance])
                return
            }
        }

        controlCalidadCobrePlataInstance.properties = params

        if (!controlCalidadCobrePlataInstance.save(flush: true)) {
            render(view: "edit", model: [controlCalidadCobrePlataInstance: controlCalidadCobrePlataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'controlCalidadCobrePlata.label', default: 'ControlCalidadCobrePlata'), controlCalidadCobrePlataInstance.id])
        redirect(action: "show", id: controlCalidadCobrePlataInstance.id)
    }

    def delete(Long id) {
        def controlCalidadCobrePlataInstance = ControlCalidadCobrePlata.get(id)
        if (!controlCalidadCobrePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'controlCalidadCobrePlata.label', default: 'ControlCalidadCobrePlata'), id])
            redirect(action: "list")
            return
        }

        try {
            controlCalidadCobrePlataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'controlCalidadCobrePlata.label', default: 'ControlCalidadCobrePlata'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'controlCalidadCobrePlata.label', default: 'ControlCalidadCobrePlata'), id])
            redirect(action: "show", id: id)
        }
    }

    def leyes = {
        try {
            def recepcion = RecepcionDeComplejo.get(params.recepcionDeComplejoId.toLong())
            if (recepcion){
                def controlCalidad = ControlCalidadCobrePlata.findByRecepcionDeComplejo(recepcion)
                if (controlCalidad){
                    render([
                        porcentajeMermaPromexbol: controlCalidad.porcentajeMermaPromexbol,
                        porcentajeHumedadPromexbol: controlCalidad.porcentajeHumedadPromexbol,
                        porcentajeCobrePromexbol: controlCalidad.porcentajeCobrePromexbol,
                        porcentajePlataPromexbol: controlCalidad.porcentajePlataPromexbol
                    ] as JSON)
                }else{
                    render([
                        porcentajeMermaPromexbol: 0,
                        porcentajeHumedadPromexbol: 0,
                        porcentajeCobrePromexbol: 0,
                        porcentajePlataPromexbol: 0
                    ] as JSON)
                }
            }
        }catch (Exception e){
            render([
                porcentajeMermaPromexbol: -1,
                porcentajeHumedadPromexbol: -1,
                porcentajeCobrePromexbol: -1,
                porcentajePlataPromexbol: -1
            ] as JSON)
        }
    }
}
