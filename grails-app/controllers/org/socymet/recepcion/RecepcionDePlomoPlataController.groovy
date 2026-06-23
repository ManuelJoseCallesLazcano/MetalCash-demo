package org.socymet.recepcion
import grails.converters.JSON
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_RECEPCION'])
@Transactional
class RecepcionDePlomoPlataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [recepcionDePlomoPlataInstanceList: RecepcionDePlomoPlata.list(params), recepcionDePlomoPlataInstanceTotal: RecepcionDePlomoPlata.count()]
    }

    def create() {
        [recepcionDePlomoPlataInstance: new RecepcionDePlomoPlata(params)]
    }

    def save() {
        def recepcionDePlomoPlataInstance = new RecepcionDePlomoPlata(params)
        if (!recepcionDePlomoPlataInstance.save(flush: true)) {
            render(view: "create", model: [recepcionDePlomoPlataInstance: recepcionDePlomoPlataInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'recepcionDePlomoPlata.label', default: 'RecepcionDePlomoPlata'), recepcionDePlomoPlataInstance.id])
        redirect(action: "show", id: recepcionDePlomoPlataInstance.id)
    }

    def show(Long id) {
        def recepcionDePlomoPlataInstance = RecepcionDePlomoPlata.get(id)
        if (!recepcionDePlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDePlomoPlata.label', default: 'RecepcionDePlomoPlata'), id])
            redirect(action: "list")
            return
        }

        [recepcionDePlomoPlataInstance: recepcionDePlomoPlataInstance]
    }

    def edit(Long id) {
        def recepcionDePlomoPlataInstance = RecepcionDePlomoPlata.get(id)
        if (!recepcionDePlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDePlomoPlata.label', default: 'RecepcionDePlomoPlata'), id])
            redirect(action: "list")
            return
        }

        [recepcionDePlomoPlataInstance: recepcionDePlomoPlataInstance]
    }

    def update(Long id, Long version) {
        def recepcionDePlomoPlataInstance = RecepcionDePlomoPlata.get(id)
        if (!recepcionDePlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDePlomoPlata.label', default: 'RecepcionDePlomoPlata'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (recepcionDePlomoPlataInstance.version > version) {
                recepcionDePlomoPlataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'recepcionDePlomoPlata.label', default: 'RecepcionDePlomoPlata')] as Object[],
                        "Another user has updated this RecepcionDePlomoPlata while you were editing")
                render(view: "edit", model: [recepcionDePlomoPlataInstance: recepcionDePlomoPlataInstance])
                return
            }
        }

        recepcionDePlomoPlataInstance.properties = params

        if (!recepcionDePlomoPlataInstance.save(flush: true)) {
            render(view: "edit", model: [recepcionDePlomoPlataInstance: recepcionDePlomoPlataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'recepcionDePlomoPlata.label', default: 'RecepcionDePlomoPlata'), recepcionDePlomoPlataInstance.id])
        redirect(action: "show", id: recepcionDePlomoPlataInstance.id)
    }

    def delete(Long id) {
        def recepcionDePlomoPlataInstance = RecepcionDePlomoPlata.get(id)
        if (!recepcionDePlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDePlomoPlata.label', default: 'RecepcionDePlomoPlata'), id])
            redirect(action: "list")
            return
        }

        try {
            recepcionDePlomoPlataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'recepcionDePlomoPlata.label', default: 'RecepcionDePlomoPlata'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'recepcionDePlomoPlata.label', default: 'RecepcionDePlomoPlata'), id])
            redirect(action: "show", id: id)
        }
    }

    def datosTransporteUltimaRecepcionJSON() {
        def c = RecepcionDePlomoPlata.createCriteria()
        def results = c {
            projections {
                max('id')
            }}
        def maxId = results.get(0)?: 0

        if (maxId!=0){
            def ultimaRecepcion = RecepcionDePlomoPlata.get(maxId)
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
