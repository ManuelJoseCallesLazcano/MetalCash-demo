package org.socymet.recepcion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException

@Transactional
class RecepcionGrupalDeComplejoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [recepcionGrupalDeComplejoInstanceList: RecepcionGrupalDeComplejo.list(params), recepcionGrupalDeComplejoInstanceTotal: RecepcionGrupalDeComplejo.count()]
    }

    def create() {
        [recepcionGrupalDeComplejoInstance: new RecepcionGrupalDeComplejo(params)]
    }

    def save() {
        def recepcionGrupalDeComplejoInstance = new RecepcionGrupalDeComplejo(params)
        if (!recepcionGrupalDeComplejoInstance.save(flush: true)) {
            render(view: "create", model: [recepcionGrupalDeComplejoInstance: recepcionGrupalDeComplejoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'recepcionGrupalDeComplejo.label', default: 'RecepcionGrupalDeComplejo'), recepcionGrupalDeComplejoInstance.id])
        redirect(action: "show", id: recepcionGrupalDeComplejoInstance.id)
    }

    def show(Long id) {
        def recepcionGrupalDeComplejoInstance = RecepcionGrupalDeComplejo.get(id)
        if (!recepcionGrupalDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionGrupalDeComplejo.label', default: 'RecepcionGrupalDeComplejo'), id])
            redirect(action: "list")
            return
        }

        [recepcionGrupalDeComplejoInstance: recepcionGrupalDeComplejoInstance]
    }

    def edit(Long id) {
        def recepcionGrupalDeComplejoInstance = RecepcionGrupalDeComplejo.get(id)
        if (!recepcionGrupalDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionGrupalDeComplejo.label', default: 'RecepcionGrupalDeComplejo'), id])
            redirect(action: "list")
            return
        }

        [recepcionGrupalDeComplejoInstance: recepcionGrupalDeComplejoInstance]
    }

    def update(Long id, Long version) {
        def recepcionGrupalDeComplejoInstance = RecepcionGrupalDeComplejo.get(id)
        if (!recepcionGrupalDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionGrupalDeComplejo.label', default: 'RecepcionGrupalDeComplejo'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (recepcionGrupalDeComplejoInstance.version > version) {
                recepcionGrupalDeComplejoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'recepcionGrupalDeComplejo.label', default: 'RecepcionGrupalDeComplejo')] as Object[],
                        "Another user has updated this RecepcionGrupalDeComplejo while you were editing")
                render(view: "edit", model: [recepcionGrupalDeComplejoInstance: recepcionGrupalDeComplejoInstance])
                return
            }
        }

        recepcionGrupalDeComplejoInstance.properties = params

        if (!recepcionGrupalDeComplejoInstance.save(flush: true)) {
            render(view: "edit", model: [recepcionGrupalDeComplejoInstance: recepcionGrupalDeComplejoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'recepcionGrupalDeComplejo.label', default: 'RecepcionGrupalDeComplejo'), recepcionGrupalDeComplejoInstance.id])
        redirect(action: "show", id: recepcionGrupalDeComplejoInstance.id)
    }

    def delete(Long id) {
        def recepcionGrupalDeComplejoInstance = RecepcionGrupalDeComplejo.get(id)
        if (!recepcionGrupalDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionGrupalDeComplejo.label', default: 'RecepcionGrupalDeComplejo'), id])
            redirect(action: "list")
            return
        }

        try {
            recepcionGrupalDeComplejoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'recepcionGrupalDeComplejo.label', default: 'RecepcionGrupalDeComplejo'), id])
            redirect(action: "list")            e
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'recepcionGrupalDeComplejo.label', default: 'RecepcionGrupalDeComplejo'), id])
            redirect(action: "show", id: id)
        }
    }

    def validarLotes = {

    }
}
