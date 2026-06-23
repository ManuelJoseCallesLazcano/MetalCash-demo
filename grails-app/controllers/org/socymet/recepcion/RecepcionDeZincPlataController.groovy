package org.socymet.recepcion
import grails.converters.JSON
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_RECEPCION'])
@Transactional
class RecepcionDeZincPlataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [recepcionDeZincPlataInstanceList: RecepcionDeZincPlata.list(params), recepcionDeZincPlataInstanceTotal: RecepcionDeZincPlata.count()]
    }

    def create() {
        [recepcionDeZincPlataInstance: new RecepcionDeZincPlata(params)]
    }

    def save() {
        def recepcionDeZincPlataInstance = new RecepcionDeZincPlata(params)
        if (!recepcionDeZincPlataInstance.save(flush: true)) {
            render(view: "create", model: [recepcionDeZincPlataInstance: recepcionDeZincPlataInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'recepcionDeZincPlata.label', default: 'RecepcionDeZincPlata'), recepcionDeZincPlataInstance.id])
        redirect(action: "show", id: recepcionDeZincPlataInstance.id)
    }

    def show(Long id) {
        def recepcionDeZincPlataInstance = RecepcionDeZincPlata.get(id)
        if (!recepcionDeZincPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDeZincPlata.label', default: 'RecepcionDeZincPlata'), id])
            redirect(action: "list")
            return
        }

        [recepcionDeZincPlataInstance: recepcionDeZincPlataInstance]
    }

    def edit(Long id) {
        def recepcionDeZincPlataInstance = RecepcionDeZincPlata.get(id)
        if (!recepcionDeZincPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDeZincPlata.label', default: 'RecepcionDeZincPlata'), id])
            redirect(action: "list")
            return
        }

        [recepcionDeZincPlataInstance: recepcionDeZincPlataInstance]
    }

    def update(Long id, Long version) {
        def recepcionDeZincPlataInstance = RecepcionDeZincPlata.get(id)
        if (!recepcionDeZincPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDeZincPlata.label', default: 'RecepcionDeZincPlata'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (recepcionDeZincPlataInstance.version > version) {
                recepcionDeZincPlataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'recepcionDeZincPlata.label', default: 'RecepcionDeZincPlata')] as Object[],
                        "Another user has updated this RecepcionDeZincPlata while you were editing")
                render(view: "edit", model: [recepcionDeZincPlataInstance: recepcionDeZincPlataInstance])
                return
            }
        }

        recepcionDeZincPlataInstance.properties = params

        if (!recepcionDeZincPlataInstance.save(flush: true)) {
            render(view: "edit", model: [recepcionDeZincPlataInstance: recepcionDeZincPlataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'recepcionDeZincPlata.label', default: 'RecepcionDeZincPlata'), recepcionDeZincPlataInstance.id])
        redirect(action: "show", id: recepcionDeZincPlataInstance.id)
    }

    def delete(Long id) {
        def recepcionDeZincPlataInstance = RecepcionDeZincPlata.get(id)
        if (!recepcionDeZincPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDeZincPlata.label', default: 'RecepcionDeZincPlata'), id])
            redirect(action: "list")
            return
        }

        try {
            recepcionDeZincPlataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'recepcionDeZincPlata.label', default: 'RecepcionDeZincPlata'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'recepcionDeZincPlata.label', default: 'RecepcionDeZincPlata'), id])
            redirect(action: "show", id: id)
        }
    }

    def datosTransporteUltimaRecepcionJSON() {
        def c = RecepcionDeZincPlata.createCriteria()
        def results = c {
            projections {
                max('id')
            }}
        def maxId = results.get(0)?: 0

        if (maxId!=0){
            def ultimaRecepcion = RecepcionDeZincPlata.get(maxId)
            render([
                empresaId: ultimaRecepcion.empresa.id.toString(),
                nombreEmpresa: ultimaRecepcion.empresa.nombreDeEmpresa,
                choferId: ultimaRecepcion.chofer.id.toString(),
                ciChofer: ultimaRecepcion.chofer.ci,
                nombreChofer: ultimaRecepcion.chofer.nombre,
                automovilId: ultimaRecepcion.automovil.id.toString(),
                placaAutomovil: ultimaRecepcion.automovil.placa,
                modeloAutomovil: ultimaRecepcion.automovil.modelo,
                colorAutomovil: ultimaRecepcion.automovil.color
            ] as JSON)
        }
    }
}
