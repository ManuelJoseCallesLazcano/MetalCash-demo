package org.socymet.proveedor
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_RECEPCION','ROLE_CONTROL_CALIDAD','ROLE_LIQUIDACION'])
@Transactional
class ChoferController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def q = params.q?.trim()
        if (q) {
            def pattern = "%${q}%"
            [choferInstanceList : Chofer.findAllByCiIlikeOrNombreIlike(pattern, pattern, params),
             choferInstanceTotal: Chofer.countByCiIlikeOrNombreIlike(pattern, pattern)]
        } else {
            [choferInstanceList: Chofer.list(params), choferInstanceTotal: Chofer.count()]
        }
    }

    def create() {
        [choferInstance: new Chofer(params)]
    }

    def save() {
        def choferInstance = new Chofer(params)
        if (!choferInstance.save(flush: true)) {
            render(view: "create", model: [choferInstance: choferInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'chofer.label', default: 'Chofer'), choferInstance.toString()])
        redirect(action: "show", id: choferInstance.id)
    }

    def show(Long id) {
        def choferInstance = Chofer.get(id)
        if (!choferInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'chofer.label', default: 'Chofer'), id])
            redirect(action: "list")
            return
        }

        [choferInstance: choferInstance]
    }

    def edit(Long id) {
        def choferInstance = Chofer.get(id)
        if (!choferInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'chofer.label', default: 'Chofer'), id])
            redirect(action: "list")
            return
        }

        [choferInstance: choferInstance]
    }

    def update(Long id, Long version) {
        def choferInstance = Chofer.get(id)
        if (!choferInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'chofer.label', default: 'Chofer'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (choferInstance.version > version) {
                choferInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'chofer.label', default: 'Chofer')] as Object[],
                        "Another user has updated this Chofer while you were editing")
                render(view: "edit", model: [choferInstance: choferInstance])
                return
            }
        }

        choferInstance.properties = params

        if (!choferInstance.save(flush: true)) {
            render(view: "edit", model: [choferInstance: choferInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'chofer.label', default: 'Chofer'), choferInstance.toString()])
        redirect(action: "show", id: choferInstance.id)
    }

    def delete(Long id) {
        def choferInstance = Chofer.get(id)
        if (!choferInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'chofer.label', default: 'Chofer'), id])
            redirect(action: "list")
            return
        }

        try {
            choferInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'chofer.label', default: 'Chofer'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'chofer.label', default: 'Chofer'), id])
            redirect(action: "show", id: id)
        }
    }

    def choferBusquedaJSON() {
        def term = params.q ?: ''
        def choferes = Chofer.findAllByNombreLikeOrCiLike("%${term}%", "%${term}%")
        def results = choferes.collect { c ->
            [
                id          : c.id,
                text        : "${c.nombre} | ${c.ci}",
                nombreChofer: c.nombre,
                ciChofer    : c.ci
            ]
        }
        render([results: results] as JSON)
    }

    def choferesJSON() {
        def choferes = Chofer.findAllByCiLike("${params.term}%")
        def choferesList = []
        choferes.each {
            def mapaChoferes = [:]
            //parametros en JSON para JQuery UI Autocomplete
            mapaChoferes.put("id",it.id)
            //mapaChoferes.put("label",it.ci) //son las cadenas que se muestran en la lista
            mapaChoferes.put("label","${it.ci} : ${it.nombre}") //son las cadenas que se muestran en la lista
            mapaChoferes.put("value",it.ci) //es la cadena que se establece en el input despues de ser seleccionado
            //otros parametros
            mapaChoferes.put("choferId",it.id)
            mapaChoferes.put("nombreChofer",it.nombre)
            choferesList.add(mapaChoferes)
        }
        render choferesList as JSON
    }

    def choferesPorNombreJSON() {
        def choferes = Chofer.findAllByNombreLike("${params.term}%")
        def choferesList = []
        choferes.each {
            def mapaChoferes = [:]
            //parametros en JSON para JQuery UI Autocomplete
            mapaChoferes.put("id",it.id)
            mapaChoferes.put("label",it.nombre) //son las cadenas que se muestran en la lista
            mapaChoferes.put("value",it.nombre) //es la cadena que se establece en el input despues de ser seleccionado
            //otros parametros
            mapaChoferes.put("choferId",it.id)
            mapaChoferes.put("ciChofer",it.ci)
            choferesList.add(mapaChoferes)
        }
        render choferesList as JSON
    }
}
