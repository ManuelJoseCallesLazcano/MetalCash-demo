package org.socymet.proveedor
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_RECEPCION','ROLE_CONTROL_CALIDAD','ROLE_LIQUIDACION'])
@Transactional
class AutomovilController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def q = params.q?.trim()
        if (q) {
            def pattern = "%${q}%"
            [automovilInstanceList : Automovil.findAllByPlacaIlike(pattern, params),
             automovilInstanceTotal: Automovil.countByPlacaIlike(pattern)]
        } else {
            [automovilInstanceList: Automovil.list(params), automovilInstanceTotal: Automovil.count()]
        }
    }

    def create() {
        [automovilInstance: new Automovil(params)]
    }

    def save() {
        def automovilInstance = new Automovil(params)
        if (!automovilInstance.save(flush: true)) {
            render(view: "create", model: [automovilInstance: automovilInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'automovil.label', default: 'Automovil'), automovilInstance.toString()])
        redirect(action: "show", id: automovilInstance.id)
    }

    def show(Long id) {
        def automovilInstance = Automovil.get(id)
        if (!automovilInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'automovil.label', default: 'Automovil'), id])
            redirect(action: "list")
            return
        }

        [automovilInstance: automovilInstance]
    }

    def edit(Long id) {
        def automovilInstance = Automovil.get(id)
        if (!automovilInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'automovil.label', default: 'Automovil'), id])
            redirect(action: "list")
            return
        }

        [automovilInstance: automovilInstance]
    }

    def update(Long id, Long version) {
        def automovilInstance = Automovil.get(id)
        if (!automovilInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'automovil.label', default: 'Automovil'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (automovilInstance.version > version) {
                automovilInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'automovil.label', default: 'Automovil')] as Object[],
                        "Another user has updated this Automovil while you were editing")
                render(view: "edit", model: [automovilInstance: automovilInstance])
                return
            }
        }

        automovilInstance.properties = params

        if (!automovilInstance.save(flush: true)) {
            render(view: "edit", model: [automovilInstance: automovilInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'automovil.label', default: 'Automovil'), automovilInstance.toString()])
        redirect(action: "show", id: automovilInstance.id)
    }

    def delete(Long id) {
        def automovilInstance = Automovil.get(id)
        if (!automovilInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'automovil.label', default: 'Automovil'), id])
            redirect(action: "list")
            return
        }

        try {
            automovilInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'automovil.label', default: 'Automovil'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'automovil.label', default: 'Automovil'), id])
            redirect(action: "show", id: id)
        }
    }

    def automovilBusquedaJSON() {
        def term = params.q ?: ''
        def automoviles = Automovil.findAllByPlacaLike("%${term}%")
        def results = automoviles.collect { a ->
            [
                id    : a.id,
                text  : (a.modelo && a.modelo != '-') ? "${a.placa} — ${a.modelo}" : a.placa,
                placa : a.placa,
                modelo: a.modelo,
                color : a.color
            ]
        }
        render([results: results] as JSON)
    }

    def automovilesJSON() {
        def automoviles = Automovil.findAllByPlacaLike("%${params.term}%")
        def automovilesList = []
        automoviles.each {
            def mapaAutomoviles = [:]
            //parametros en JSON para JQuery UI Autocomplete
            mapaAutomoviles.put("id",it.id)
            mapaAutomoviles.put("label",it.placa) //son las cadenas que se muestran en la lista
            mapaAutomoviles.put("value",it.placa) //es la cadena que se establece en el input despues de ser seleccionado
            //otros parametros
            mapaAutomoviles.put("automovilId",it.id)
            mapaAutomoviles.put("modelo",it.modelo)
            mapaAutomoviles.put("color",it.color)
            automovilesList.add(mapaAutomoviles)
        }
        render automovilesList as JSON
    }
}
