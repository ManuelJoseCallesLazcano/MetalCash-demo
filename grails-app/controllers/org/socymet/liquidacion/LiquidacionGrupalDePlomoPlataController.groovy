package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class LiquidacionGrupalDePlomoPlataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [liquidacionGrupalDePlomoPlataInstanceList: LiquidacionGrupalDePlomoPlata.list(params), liquidacionGrupalDePlomoPlataInstanceTotal: LiquidacionGrupalDePlomoPlata.count()]
    }

    def create() {
        [liquidacionGrupalDePlomoPlataInstance: new LiquidacionGrupalDePlomoPlata(params)]
    }

    def save() {
        def liquidacionGrupalDePlomoPlataInstance = new LiquidacionGrupalDePlomoPlata(params)
        if (!liquidacionGrupalDePlomoPlataInstance.save(flush: true)) {
            render(view: "create", model: [liquidacionGrupalDePlomoPlataInstance: liquidacionGrupalDePlomoPlataInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionGrupalDePlomoPlata.label', default: 'LiquidacionGrupalDePlomoPlata'), liquidacionGrupalDePlomoPlataInstance.id])
        redirect(action: "show", id: liquidacionGrupalDePlomoPlataInstance.id)
    }

    def show(Long id) {
        def liquidacionGrupalDePlomoPlataInstance = LiquidacionGrupalDePlomoPlata.get(id)
        if (!liquidacionGrupalDePlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDePlomoPlata.label', default: 'LiquidacionGrupalDePlomoPlata'), id])
            redirect(action: "list")
            return
        }

        [liquidacionGrupalDePlomoPlataInstance: liquidacionGrupalDePlomoPlataInstance]
    }

    def edit(Long id) {
        def liquidacionGrupalDePlomoPlataInstance = LiquidacionGrupalDePlomoPlata.get(id)
        if (!liquidacionGrupalDePlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDePlomoPlata.label', default: 'LiquidacionGrupalDePlomoPlata'), id])
            redirect(action: "list")
            return
        }

        [liquidacionGrupalDePlomoPlataInstance: liquidacionGrupalDePlomoPlataInstance]
    }

    def update(Long id, Long version) {
        def liquidacionGrupalDePlomoPlataInstance = LiquidacionGrupalDePlomoPlata.get(id)
        if (!liquidacionGrupalDePlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDePlomoPlata.label', default: 'LiquidacionGrupalDePlomoPlata'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (liquidacionGrupalDePlomoPlataInstance.version > version) {
                liquidacionGrupalDePlomoPlataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'liquidacionGrupalDePlomoPlata.label', default: 'LiquidacionGrupalDePlomoPlata')] as Object[],
                        "Another user has updated this LiquidacionGrupalDePlomoPlata while you were editing")
                render(view: "edit", model: [liquidacionGrupalDePlomoPlataInstance: liquidacionGrupalDePlomoPlataInstance])
                return
            }
        }

        liquidacionGrupalDePlomoPlataInstance.properties = params

        if (!liquidacionGrupalDePlomoPlataInstance.save(flush: true)) {
            render(view: "edit", model: [liquidacionGrupalDePlomoPlataInstance: liquidacionGrupalDePlomoPlataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'liquidacionGrupalDePlomoPlata.label', default: 'LiquidacionGrupalDePlomoPlata'), liquidacionGrupalDePlomoPlataInstance.id])
        redirect(action: "show", id: liquidacionGrupalDePlomoPlataInstance.id)
    }

    def delete(Long id) {
        def liquidacionGrupalDePlomoPlataInstance = LiquidacionGrupalDePlomoPlata.get(id)
        if (!liquidacionGrupalDePlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDePlomoPlata.label', default: 'LiquidacionGrupalDePlomoPlata'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionGrupalDePlomoPlataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'liquidacionGrupalDePlomoPlata.label', default: 'LiquidacionGrupalDePlomoPlata'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionGrupalDePlomoPlata.label', default: 'LiquidacionGrupalDePlomoPlata'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporte = {
        def realPath = org.socymet.util.ReportesRuntime.realPath("/reports/images/")
        params.realPath = realPath+"/"
        params.SUBREPORT_DIR = "${org.socymet.util.ReportesRuntime.realPath('/reports')}/"
        chain(controller:'jasper',action:'index',params:params)
    }
}
