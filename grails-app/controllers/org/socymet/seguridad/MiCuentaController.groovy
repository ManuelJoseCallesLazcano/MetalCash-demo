package org.socymet.seguridad
import grails.converters.JSON
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_RECEPCION','ROLE_LIQUIDACION','ROLE_ADMIN'])
@Transactional
class MiCuentaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    transient springSecurityService

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [miCuentaInstanceList: MiCuenta.list(params), miCuentaInstanceTotal: MiCuenta.count()]
    }

    def create() {
        [miCuentaInstance: new MiCuenta(params)]
    }

    def save() {
        def miCuentaInstance = new MiCuenta(params)
        if (!miCuentaInstance.save(flush: true)) {
            render(view: "create", model: [miCuentaInstance: miCuentaInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'miCuenta.label', default: 'MiCuenta'), miCuentaInstance.id])
        redirect(action: "show", id: miCuentaInstance.id)
    }

    def show(Long id) {
        def miCuentaInstance = MiCuenta.get(id)
        if (!miCuentaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'miCuenta.label', default: 'MiCuenta'), id])
            redirect(action: "list")
            return
        }

        [miCuentaInstance: miCuentaInstance]
    }

    def edit(Long id) {
        def miCuentaInstance = MiCuenta.get(id)
        if (!miCuentaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'miCuenta.label', default: 'MiCuenta'), id])
            redirect(action: "list")
            return
        }

        [miCuentaInstance: miCuentaInstance]
    }

    def update(Long id, Long version) {
        def miCuentaInstance = MiCuenta.get(id)
        if (!miCuentaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'miCuenta.label', default: 'MiCuenta'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (miCuentaInstance.version > version) {
                miCuentaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'miCuenta.label', default: 'MiCuenta')] as Object[],
                        "Another user has updated this MiCuenta while you were editing")
                render(view: "edit", model: [miCuentaInstance: miCuentaInstance])
                return
            }
        }

        miCuentaInstance.properties = params

        if (!miCuentaInstance.save(flush: true)) {
            render(view: "edit", model: [miCuentaInstance: miCuentaInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'miCuenta.label', default: 'MiCuenta'), miCuentaInstance.id])
        redirect(action: "show", id: miCuentaInstance.id)
    }

    def delete(Long id) {
        def miCuentaInstance = MiCuenta.get(id)
        if (!miCuentaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'miCuenta.label', default: 'MiCuenta'), id])
            redirect(action: "list")
            return
        }

        try {
            miCuentaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'miCuenta.label', default: 'MiCuenta'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'miCuenta.label', default: 'MiCuenta'), id])
            redirect(action: "show", id: id)
        }
    }

    def misDatos = {
        def usuario = springSecurityService.currentUser as SecUser
        render([
            nombre: usuario.nombre,
            cuenta: usuario.username
        ] as JSON)
    }

    def actualizar = {
        def usuario = springSecurityService.currentUser as SecUser
        def cuenta = params.cuenta.toString()
        def contrasena = params.contrasena.toString()
        usuario.username = cuenta
        usuario.password = contrasena
        usuario.save(failOnError: true)
        render([mensaje: ""] as JSON)
    }
}
