package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class LiquidacionGrupalDeCobrePlataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [liquidacionGrupalDeCobrePlataInstanceList: LiquidacionGrupalDeCobrePlata.list(params), liquidacionGrupalDeCobrePlataInstanceTotal: LiquidacionGrupalDeCobrePlata.count()]
    }

    def create() {
        [liquidacionGrupalDeCobrePlataInstance: new LiquidacionGrupalDeCobrePlata(params)]
    }

    def save() {
        def liquidacionGrupalDeCobrePlataInstance = new LiquidacionGrupalDeCobrePlata(params)
        if (!liquidacionGrupalDeCobrePlataInstance.save(flush: true)) {
            render(view: "create", model: [liquidacionGrupalDeCobrePlataInstance: liquidacionGrupalDeCobrePlataInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionGrupalDeCobrePlata.label', default: 'LiquidacionGrupalDeCobrePlata'), liquidacionGrupalDeCobrePlataInstance.id])
        redirect(action: "show", id: liquidacionGrupalDeCobrePlataInstance.id)
    }

    def show(Long id) {
        def liquidacionGrupalDeCobrePlataInstance = LiquidacionGrupalDeCobrePlata.get(id)
        if (!liquidacionGrupalDeCobrePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDeCobrePlata.label', default: 'LiquidacionGrupalDeCobrePlata'), id])
            redirect(action: "list")
            return
        }

        [liquidacionGrupalDeCobrePlataInstance: liquidacionGrupalDeCobrePlataInstance]
    }

    def edit(Long id) {
        def liquidacionGrupalDeCobrePlataInstance = LiquidacionGrupalDeCobrePlata.get(id)
        if (!liquidacionGrupalDeCobrePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDeCobrePlata.label', default: 'LiquidacionGrupalDeCobrePlata'), id])
            redirect(action: "list")
            return
        }

        [liquidacionGrupalDeCobrePlataInstance: liquidacionGrupalDeCobrePlataInstance]
    }

    def update(Long id, Long version) {
        def liquidacionGrupalDeCobrePlataInstance = LiquidacionGrupalDeCobrePlata.get(id)
        if (!liquidacionGrupalDeCobrePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDeCobrePlata.label', default: 'LiquidacionGrupalDeCobrePlata'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (liquidacionGrupalDeCobrePlataInstance.version > version) {
                liquidacionGrupalDeCobrePlataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'liquidacionGrupalDeCobrePlata.label', default: 'LiquidacionGrupalDeCobrePlata')] as Object[],
                        "Another user has updated this LiquidacionGrupalDeCobrePlata while you were editing")
                render(view: "edit", model: [liquidacionGrupalDeCobrePlataInstance: liquidacionGrupalDeCobrePlataInstance])
                return
            }
        }

        liquidacionGrupalDeCobrePlataInstance.properties = params

        if (!liquidacionGrupalDeCobrePlataInstance.save(flush: true)) {
            render(view: "edit", model: [liquidacionGrupalDeCobrePlataInstance: liquidacionGrupalDeCobrePlataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'liquidacionGrupalDeCobrePlata.label', default: 'LiquidacionGrupalDeCobrePlata'), liquidacionGrupalDeCobrePlataInstance.id])
        redirect(action: "show", id: liquidacionGrupalDeCobrePlataInstance.id)
    }

    def delete(Long id) {
        def liquidacionGrupalDeCobrePlataInstance = LiquidacionGrupalDeCobrePlata.get(id)
        if (!liquidacionGrupalDeCobrePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDeCobrePlata.label', default: 'LiquidacionGrupalDeCobrePlata'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionGrupalDeCobrePlataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'liquidacionGrupalDeCobrePlata.label', default: 'LiquidacionGrupalDeCobrePlata'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionGrupalDeCobrePlata.label', default: 'LiquidacionGrupalDeCobrePlata'), id])
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
