package org.smart.parametros
import grails.converters.JSON
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Transactional
class ParametrosGeneralesController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    @Secured(['ROLE_ADMIN'])
    def index() {
        redirect(action: "list", params: params)
    }

    @Secured(['ROLE_ADMIN'])
    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [parametrosGeneralesInstanceList: ParametrosGenerales.list(params), parametrosGeneralesInstanceTotal: ParametrosGenerales.count()]
    }

    @Secured(['ROLE_ADMIN'])
    def create() {
        [parametrosGeneralesInstance: new ParametrosGenerales(params)]
    }

    @Secured(['ROLE_ADMIN'])
    def save() {
        def parametrosGeneralesInstance = new ParametrosGenerales(params)
        if (!parametrosGeneralesInstance.save(flush: true)) {
            render(view: "create", model: [parametrosGeneralesInstance: parametrosGeneralesInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'parametrosGenerales.label', default: 'ParametrosGenerales'), parametrosGeneralesInstance.id])
        redirect(action: "show", id: parametrosGeneralesInstance.id)
    }

    @Secured(['ROLE_ADMIN'])
    def show(Long id) {
        def parametrosGeneralesInstance = ParametrosGenerales.get(id)
        if (!parametrosGeneralesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'parametrosGenerales.label', default: 'ParametrosGenerales'), id])
            redirect(action: "list")
            return
        }

        [parametrosGeneralesInstance: parametrosGeneralesInstance]
    }

    @Secured(['ROLE_ADMIN'])
    def edit(Long id) {
        def parametrosGeneralesInstance = ParametrosGenerales.get(id)
        if (!parametrosGeneralesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'parametrosGenerales.label', default: 'ParametrosGenerales'), id])
            redirect(action: "list")
            return
        }

        [parametrosGeneralesInstance: parametrosGeneralesInstance]
    }

    @Secured(['ROLE_ADMIN'])
    def update(Long id, Long version) {
        def parametrosGeneralesInstance = ParametrosGenerales.get(id)
        if (!parametrosGeneralesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'parametrosGenerales.label', default: 'ParametrosGenerales'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (parametrosGeneralesInstance.version > version) {
                parametrosGeneralesInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'parametrosGenerales.label', default: 'ParametrosGenerales')] as Object[],
                        "Another user has updated this ParametrosGenerales while you were editing")
                render(view: "edit", model: [parametrosGeneralesInstance: parametrosGeneralesInstance])
                return
            }
        }

        parametrosGeneralesInstance.properties = params

        if (!parametrosGeneralesInstance.save(flush: true)) {
            render(view: "edit", model: [parametrosGeneralesInstance: parametrosGeneralesInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'parametrosGenerales.label', default: 'ParametrosGenerales'), parametrosGeneralesInstance.id])
        redirect(action: "show", id: parametrosGeneralesInstance.id)
    }

    @Secured(['ROLE_ADMIN'])
    def delete(Long id) {
        def parametrosGeneralesInstance = ParametrosGenerales.get(id)
        if (!parametrosGeneralesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'parametrosGenerales.label', default: 'ParametrosGenerales'), id])
            redirect(action: "list")
            return
        }

        try {
            parametrosGeneralesInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'parametrosGenerales.label', default: 'ParametrosGenerales'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'parametrosGenerales.label', default: 'ParametrosGenerales'), id])
            redirect(action: "show", id: id)
        }
    }

    @Secured(['ROLE_ADMIN','ROLE_CAJA','ROLE_LIQUIDACION'])
    def mesesPagablesBonoProduccion() {
        def parametrosGenerales = ParametrosGenerales.get(1)
        render([mesesPagables: (parametrosGenerales)?parametrosGenerales.mesesPagablesBonoCantidad:0] as JSON)
    }

    @Secured(['ROLE_ADMIN','ROLE_CAJA','ROLE_LIQUIDACION'])
    def mesesPagablesBonoCalidad() {
        def parametrosGenerales = ParametrosGenerales.get(1)
        render([mesesPagables: (parametrosGenerales)?parametrosGenerales.mesesPagablesBonoCalidad:0] as JSON)
    }

    @Secured(['ROLE_ADMIN','ROLE_CAJA','ROLE_LIQUIDACION'])
    def mesesPagablesBonoTransporte() {
        def parametrosGenerales = ParametrosGenerales.get(1)
        render([mesesPagables: (parametrosGenerales)?parametrosGenerales.mesesPagablesBonoTransporte :0] as JSON)
    }

    @Secured(['ROLE_ADMIN','ROLE_CAJA','ROLE_LIQUIDACION'])
    def leyMinimaPlataBonoCalidad() {
        def parametrosGenerales = ParametrosGenerales.get(1)
        render([leyMinimaPlata: (parametrosGenerales)?parametrosGenerales.leyMinimaPlataBonoCalidad:0] as JSON)
    }

    @Secured(['ROLE_ADMIN','ROLE_CAJA','ROLE_LIQUIDACION'])
    def costosManipuleo() {
        def parametrosGenerales = ParametrosGenerales.get(1)
        render([
            pesadaVaciada: (parametrosGenerales)?parametrosGenerales.pesadaVaciada:0,
            carguioMaquina: (parametrosGenerales)?parametrosGenerales.carguioMaquina:0,
            embolsadaArrumada: (parametrosGenerales)?parametrosGenerales.embolsadaArrumada:0,
            soloComuneada: (parametrosGenerales)?parametrosGenerales.soloComuneada:0,
            soloVaciada: (parametrosGenerales)?parametrosGenerales.soloVaciada:0,
            soloPesada: (parametrosGenerales)?parametrosGenerales.soloPesada:0,
            soloEmbolsada: (parametrosGenerales)?parametrosGenerales.soloEmbolsada:0
        ] as JSON)
    }
}
