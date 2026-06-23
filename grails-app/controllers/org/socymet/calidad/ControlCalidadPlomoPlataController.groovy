package org.socymet.calidad
import grails.converters.JSON
import grails.gorm.transactions.Transactional

import org.socymet.recepcion.RecepcionDeComplejo
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_CONTROL_CALIDAD'])
@Transactional
class ControlCalidadPlomoPlataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [controlCalidadPlomoPlataInstanceList: ControlCalidadPlomoPlata.list(params), controlCalidadPlomoPlataInstanceTotal: ControlCalidadPlomoPlata.count()]
    }

    def create() {
        [controlCalidadPlomoPlataInstance: new ControlCalidadPlomoPlata(params)]
    }

    def save() {
        def controlCalidadPlomoPlataInstance = new ControlCalidadPlomoPlata(params)
        if (!controlCalidadPlomoPlataInstance.save(flush: true)) {
            render(view: "create", model: [controlCalidadPlomoPlataInstance: controlCalidadPlomoPlataInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'controlCalidadPlomoPlata.label', default: 'ControlCalidadPlomoPlata'), controlCalidadPlomoPlataInstance.id])
        redirect(action: "show", id: controlCalidadPlomoPlataInstance.id)
    }

    def show(Long id) {
        def controlCalidadPlomoPlataInstance = ControlCalidadPlomoPlata.get(id)
        if (!controlCalidadPlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'controlCalidadPlomoPlata.label', default: 'ControlCalidadPlomoPlata'), id])
            redirect(action: "list")
            return
        }

        [controlCalidadPlomoPlataInstance: controlCalidadPlomoPlataInstance]
    }

    def edit(Long id) {
        def controlCalidadPlomoPlataInstance = ControlCalidadPlomoPlata.get(id)
        if (!controlCalidadPlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'controlCalidadPlomoPlata.label', default: 'ControlCalidadPlomoPlata'), id])
            redirect(action: "list")
            return
        }

        [controlCalidadPlomoPlataInstance: controlCalidadPlomoPlataInstance]
    }

    def update(Long id, Long version) {
        def controlCalidadPlomoPlataInstance = ControlCalidadPlomoPlata.get(id)
        if (!controlCalidadPlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'controlCalidadPlomoPlata.label', default: 'ControlCalidadPlomoPlata'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (controlCalidadPlomoPlataInstance.version > version) {
                controlCalidadPlomoPlataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'controlCalidadPlomoPlata.label', default: 'ControlCalidadPlomoPlata')] as Object[],
                        "Another user has updated this ControlCalidadPlomoPlata while you were editing")
                render(view: "edit", model: [controlCalidadPlomoPlataInstance: controlCalidadPlomoPlataInstance])
                return
            }
        }

        controlCalidadPlomoPlataInstance.properties = params

        if (!controlCalidadPlomoPlataInstance.save(flush: true)) {
            render(view: "edit", model: [controlCalidadPlomoPlataInstance: controlCalidadPlomoPlataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'controlCalidadPlomoPlata.label', default: 'ControlCalidadPlomoPlata'), controlCalidadPlomoPlataInstance.id])
        redirect(action: "show", id: controlCalidadPlomoPlataInstance.id)
    }

    def delete(Long id) {
        def controlCalidadPlomoPlataInstance = ControlCalidadPlomoPlata.get(id)
        if (!controlCalidadPlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'controlCalidadPlomoPlata.label', default: 'ControlCalidadPlomoPlata'), id])
            redirect(action: "list")
            return
        }

        try {
            controlCalidadPlomoPlataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'controlCalidadPlomoPlata.label', default: 'ControlCalidadPlomoPlata'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'controlCalidadPlomoPlata.label', default: 'ControlCalidadPlomoPlata'), id])
            redirect(action: "show", id: id)
        }
    }

    def leyes = {
        try{
            def recepcion = RecepcionDeComplejo.get(params.recepcionDeComplejoId.toLong())
            if (recepcion){
                def controlCalidad = ControlCalidadPlomoPlata.findByRecepcionDeComplejo(recepcion)
                if (controlCalidad){
                    render([
                        porcentajeMermaPromexbol: controlCalidad.porcentajeMermaPromexbol,
                        porcentajeHumedadPromexbol: controlCalidad.porcentajeHumedadPromexbol,
                        porcentajePlomoPromexbol: controlCalidad.porcentajePlomoPromexbol,
                        porcentajePlataPromexbol: controlCalidad.porcentajePlataPromexbol
                    ] as JSON)
                }else{
                    render([
                        porcentajeMermaPromexbol: 0,
                        porcentajeHumedadPromexbol: 0,
                        porcentajePlomoPromexbol: 0,
                        porcentajePlataPromexbol: 0
                    ] as JSON)
                }
            }
        }catch (Exception e){
            render([
                porcentajeMermaPromexbol: -1,
                porcentajeHumedadPromexbol: -1,
                porcentajePlomoPromexbol: -1,
                porcentajePlataPromexbol: -1
            ] as JSON)
        }        
    }
}
