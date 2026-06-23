package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class LiquidacionGrupalDeZincPlataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [liquidacionGrupalDeZincPlataInstanceList: LiquidacionGrupalDeZincPlata.list(params), liquidacionGrupalDeZincPlataInstanceTotal: LiquidacionGrupalDeZincPlata.count()]
    }

    def create() {
        [liquidacionGrupalDeZincPlataInstance: new LiquidacionGrupalDeZincPlata(params)]
    }

    def save() {
        def liquidacionGrupalDeZincPlataInstance = new LiquidacionGrupalDeZincPlata(params)
        if (!liquidacionGrupalDeZincPlataInstance.save(flush: true)) {
            render(view: "create", model: [liquidacionGrupalDeZincPlataInstance: liquidacionGrupalDeZincPlataInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionGrupalDeZincPlata.label', default: 'LiquidacionGrupalDeZincPlata'), liquidacionGrupalDeZincPlataInstance.id])
        redirect(action: "show", id: liquidacionGrupalDeZincPlataInstance.id)
    }

    def show(Long id) {
        def liquidacionGrupalDeZincPlataInstance = LiquidacionGrupalDeZincPlata.get(id)
        if (!liquidacionGrupalDeZincPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDeZincPlata.label', default: 'LiquidacionGrupalDeZincPlata'), id])
            redirect(action: "list")
            return
        }

        [liquidacionGrupalDeZincPlataInstance: liquidacionGrupalDeZincPlataInstance]
    }

    def edit(Long id) {
        def liquidacionGrupalDeZincPlataInstance = LiquidacionGrupalDeZincPlata.get(id)
        if (!liquidacionGrupalDeZincPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDeZincPlata.label', default: 'LiquidacionGrupalDeZincPlata'), id])
            redirect(action: "list")
            return
        }

        [liquidacionGrupalDeZincPlataInstance: liquidacionGrupalDeZincPlataInstance]
    }

    def update(Long id, Long version) {
        def liquidacionGrupalDeZincPlataInstance = LiquidacionGrupalDeZincPlata.get(id)
        if (!liquidacionGrupalDeZincPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDeZincPlata.label', default: 'LiquidacionGrupalDeZincPlata'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (liquidacionGrupalDeZincPlataInstance.version > version) {
                liquidacionGrupalDeZincPlataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'liquidacionGrupalDeZincPlata.label', default: 'LiquidacionGrupalDeZincPlata')] as Object[],
                        "Another user has updated this LiquidacionGrupalDeZincPlata while you were editing")
                render(view: "edit", model: [liquidacionGrupalDeZincPlataInstance: liquidacionGrupalDeZincPlataInstance])
                return
            }
        }

        liquidacionGrupalDeZincPlataInstance.properties = params

        if (!liquidacionGrupalDeZincPlataInstance.save(flush: true)) {
            render(view: "edit", model: [liquidacionGrupalDeZincPlataInstance: liquidacionGrupalDeZincPlataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'liquidacionGrupalDeZincPlata.label', default: 'LiquidacionGrupalDeZincPlata'), liquidacionGrupalDeZincPlataInstance.id])
        redirect(action: "show", id: liquidacionGrupalDeZincPlataInstance.id)
    }

    def delete(Long id) {
        def liquidacionGrupalDeZincPlataInstance = LiquidacionGrupalDeZincPlata.get(id)
        if (!liquidacionGrupalDeZincPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDeZincPlata.label', default: 'LiquidacionGrupalDeZincPlata'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionGrupalDeZincPlataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'liquidacionGrupalDeZincPlata.label', default: 'LiquidacionGrupalDeZincPlata'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionGrupalDeZincPlata.label', default: 'LiquidacionGrupalDeZincPlata'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporte = {
        def realPath = servletContext.getRealPath("/reports/images/")
        params.realPath = realPath+"/"
        params.SUBREPORT_DIR = "${servletContext.getRealPath('/reports')}/"
        chain(controller:'jasper',action:'index',params:params)
    }
}
