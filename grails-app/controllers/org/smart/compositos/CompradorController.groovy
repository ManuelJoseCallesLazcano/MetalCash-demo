package org.smart.compositos
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class CompradorController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def q = params.q?.trim()
        if (q) {
            def pattern = "%${q}%"
            [compradorInstanceList : Comprador.findAllByNombreCompradorIlikeOrNombreContactoIlike(pattern, pattern, params),
             compradorInstanceTotal: Comprador.countByNombreCompradorIlikeOrNombreContactoIlike(pattern, pattern)]
        } else {
            [compradorInstanceList: Comprador.list(params), compradorInstanceTotal: Comprador.count()]
        }
    }

    def create() {
        [compradorInstance: new Comprador(params)]
    }

    def save() {
        def compradorInstance = new Comprador(params)
        if (!compradorInstance.save(flush: true)) {
            render(view: "create", model: [compradorInstance: compradorInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'comprador.label', default: 'Comprador'), compradorInstance.toString()])
        redirect(action: "show", id: compradorInstance.id)
    }

    def show(Long id) {
        def compradorInstance = Comprador.get(id)
        if (!compradorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'comprador.label', default: 'Comprador'), id])
            redirect(action: "index")
            return
        }

        [compradorInstance: compradorInstance]
    }

    def edit(Long id) {
        def compradorInstance = Comprador.get(id)
        if (!compradorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'comprador.label', default: 'Comprador'), id])
            redirect(action: "index")
            return
        }

        [compradorInstance: compradorInstance]
    }

    def update(Long id, Long version) {
        def compradorInstance = Comprador.get(id)
        if (!compradorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'comprador.label', default: 'Comprador'), id])
            redirect(action: "index")
            return
        }

        if (version != null) {
            if (compradorInstance.version > version) {
                compradorInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'comprador.label', default: 'Comprador')] as Object[],
                        "Another user has updated this Comprador while you were editing")
                render(view: "edit", model: [compradorInstance: compradorInstance])
                return
            }
        }

        compradorInstance.properties = params

        if (!compradorInstance.save(flush: true)) {
            render(view: "edit", model: [compradorInstance: compradorInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'comprador.label', default: 'Comprador'), compradorInstance.toString()])
        redirect(action: "show", id: compradorInstance.id)
    }

    def delete(Long id) {
        def compradorInstance = Comprador.get(id)
        if (!compradorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'comprador.label', default: 'Comprador'), id])
            redirect(action: "index")
            return
        }

        try {
            compradorInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'comprador.label', default: 'Comprador'), id])
            redirect(action: "index")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'comprador.label', default: 'Comprador'), id])
            redirect(action: "show", id: id)
        }
    }
}
