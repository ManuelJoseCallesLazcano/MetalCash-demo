package org.smart.compositos
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class IngenioController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def q = params.q?.trim()
        if (q) {
            def pattern = "%${q}%"
            [ingenioInstanceList : Ingenio.findAllByNombreIngenioIlikeOrTelefonoIlike(pattern, pattern, params),
             ingenioInstanceTotal: Ingenio.countByNombreIngenioIlikeOrTelefonoIlike(pattern, pattern)]
        } else {
            [ingenioInstanceList: Ingenio.list(params), ingenioInstanceTotal: Ingenio.count()]
        }
    }

    def create() {
        [ingenioInstance: new Ingenio(params)]
    }

    def save() {
        def ingenioInstance = new Ingenio(params)
        if (!ingenioInstance.save(flush: true)) {
            render(view: "create", model: [ingenioInstance: ingenioInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'ingenio.label', default: 'Ingenio'), ingenioInstance.toString()])
        redirect(action: "show", id: ingenioInstance.id)
    }

    def show(Long id) {
        def ingenioInstance = Ingenio.get(id)
        if (!ingenioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ingenio.label', default: 'Ingenio'), id])
            redirect(action: "index")
            return
        }

        [ingenioInstance: ingenioInstance]
    }

    def edit(Long id) {
        def ingenioInstance = Ingenio.get(id)
        if (!ingenioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ingenio.label', default: 'Ingenio'), id])
            redirect(action: "index")
            return
        }

        [ingenioInstance: ingenioInstance]
    }

    def update(Long id, Long version) {
        def ingenioInstance = Ingenio.get(id)
        if (!ingenioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ingenio.label', default: 'Ingenio'), id])
            redirect(action: "index")
            return
        }

        if (version != null) {
            if (ingenioInstance.version > version) {
                ingenioInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'ingenio.label', default: 'Ingenio')] as Object[],
                        "Another user has updated this Ingenio while you were editing")
                render(view: "edit", model: [ingenioInstance: ingenioInstance])
                return
            }
        }

        ingenioInstance.properties = params

        if (!ingenioInstance.save(flush: true)) {
            render(view: "edit", model: [ingenioInstance: ingenioInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'ingenio.label', default: 'Ingenio'), ingenioInstance.toString()])
        redirect(action: "show", id: ingenioInstance.id)
    }

    def delete(Long id) {
        def ingenioInstance = Ingenio.get(id)
        if (!ingenioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ingenio.label', default: 'Ingenio'), id])
            redirect(action: "index")
            return
        }

        try {
            ingenioInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'ingenio.label', default: 'Ingenio'), id])
            redirect(action: "index")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'ingenio.label', default: 'Ingenio'), id])
            redirect(action: "show", id: id)
        }
    }
}
