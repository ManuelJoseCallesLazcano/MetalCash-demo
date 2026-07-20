package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class LiquidacionGrupalDeComplejoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [liquidacionGrupalDeComplejoInstanceList: LiquidacionGrupalDeComplejo.list(params), liquidacionGrupalDeComplejoInstanceTotal: LiquidacionGrupalDeComplejo.count()]
    }

    def create() {
        [liquidacionGrupalDeComplejoInstance: new LiquidacionGrupalDeComplejo(params)]
    }

    def save() {
        def liquidacionGrupalDeComplejoInstance = new LiquidacionGrupalDeComplejo(params)
        if (!liquidacionGrupalDeComplejoInstance.save(flush: true)) {
            render(view: "create", model: [liquidacionGrupalDeComplejoInstance: liquidacionGrupalDeComplejoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionGrupalDeComplejo.label', default: 'LiquidacionGrupalDeComplejo'), liquidacionGrupalDeComplejoInstance.id])
        redirect(action: "show", id: liquidacionGrupalDeComplejoInstance.id)
    }

    def show(Long id) {
        def liquidacionGrupalDeComplejoInstance = LiquidacionGrupalDeComplejo.get(id)
        if (!liquidacionGrupalDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDeComplejo.label', default: 'LiquidacionGrupalDeComplejo'), id])
            redirect(action: "list")
            return
        }

        [liquidacionGrupalDeComplejoInstance: liquidacionGrupalDeComplejoInstance]
    }

    def edit(Long id) {
        def liquidacionGrupalDeComplejoInstance = LiquidacionGrupalDeComplejo.get(id)
        if (!liquidacionGrupalDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDeComplejo.label', default: 'LiquidacionGrupalDeComplejo'), id])
            redirect(action: "list")
            return
        }

        [liquidacionGrupalDeComplejoInstance: liquidacionGrupalDeComplejoInstance]
    }

    def update(Long id, Long version) {
        def liquidacionGrupalDeComplejoInstance = LiquidacionGrupalDeComplejo.get(id)
        if (!liquidacionGrupalDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDeComplejo.label', default: 'LiquidacionGrupalDeComplejo'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (liquidacionGrupalDeComplejoInstance.version > version) {
                liquidacionGrupalDeComplejoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'liquidacionGrupalDeComplejo.label', default: 'LiquidacionGrupalDeComplejo')] as Object[],
                        "Another user has updated this LiquidacionGrupalDeComplejo while you were editing")
                render(view: "edit", model: [liquidacionGrupalDeComplejoInstance: liquidacionGrupalDeComplejoInstance])
                return
            }
        }

        liquidacionGrupalDeComplejoInstance.properties = params

        if (!liquidacionGrupalDeComplejoInstance.save(flush: true)) {
            render(view: "edit", model: [liquidacionGrupalDeComplejoInstance: liquidacionGrupalDeComplejoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'liquidacionGrupalDeComplejo.label', default: 'LiquidacionGrupalDeComplejo'), liquidacionGrupalDeComplejoInstance.id])
        redirect(action: "show", id: liquidacionGrupalDeComplejoInstance.id)
    }

    def delete(Long id) {
        def liquidacionGrupalDeComplejoInstance = LiquidacionGrupalDeComplejo.get(id)
        if (!liquidacionGrupalDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionGrupalDeComplejo.label', default: 'LiquidacionGrupalDeComplejo'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionGrupalDeComplejoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'liquidacionGrupalDeComplejo.label', default: 'LiquidacionGrupalDeComplejo'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionGrupalDeComplejo.label', default: 'LiquidacionGrupalDeComplejo'), id])
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
