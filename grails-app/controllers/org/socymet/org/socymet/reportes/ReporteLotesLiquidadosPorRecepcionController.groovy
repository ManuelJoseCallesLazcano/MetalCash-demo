package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class ReporteLotesLiquidadosPorRecepcionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reporteLotesLiquidadosPorRecepcionInstanceList: ReporteLotesLiquidadosPorRecepcion.list(params), reporteLotesLiquidadosPorRecepcionInstanceTotal: ReporteLotesLiquidadosPorRecepcion.count()]
    }

    def create() {
        [reporteLotesLiquidadosPorRecepcionInstance: new ReporteLotesLiquidadosPorRecepcion(params)]
    }

    def save() {
        def reporteLotesLiquidadosPorRecepcionInstance = new ReporteLotesLiquidadosPorRecepcion(params)
        if (!reporteLotesLiquidadosPorRecepcionInstance.save(flush: true)) {
            render(view: "create", model: [reporteLotesLiquidadosPorRecepcionInstance: reporteLotesLiquidadosPorRecepcionInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reporteLotesLiquidadosPorRecepcion.label', default: 'ReporteLotesLiquidadosPorRecepcion'), reporteLotesLiquidadosPorRecepcionInstance.id])
        redirect(action: "show", id: reporteLotesLiquidadosPorRecepcionInstance.id)
    }

    def show(Long id) {
        def reporteLotesLiquidadosPorRecepcionInstance = ReporteLotesLiquidadosPorRecepcion.get(id)
        if (!reporteLotesLiquidadosPorRecepcionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteLotesLiquidadosPorRecepcion.label', default: 'ReporteLotesLiquidadosPorRecepcion'), id])
            redirect(action: "list")
            return
        }

        [reporteLotesLiquidadosPorRecepcionInstance: reporteLotesLiquidadosPorRecepcionInstance]
    }

    def edit(Long id) {
        def reporteLotesLiquidadosPorRecepcionInstance = ReporteLotesLiquidadosPorRecepcion.get(id)
        if (!reporteLotesLiquidadosPorRecepcionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteLotesLiquidadosPorRecepcion.label', default: 'ReporteLotesLiquidadosPorRecepcion'), id])
            redirect(action: "list")
            return
        }

        [reporteLotesLiquidadosPorRecepcionInstance: reporteLotesLiquidadosPorRecepcionInstance]
    }

    def update(Long id, Long version) {
        def reporteLotesLiquidadosPorRecepcionInstance = ReporteLotesLiquidadosPorRecepcion.get(id)
        if (!reporteLotesLiquidadosPorRecepcionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteLotesLiquidadosPorRecepcion.label', default: 'ReporteLotesLiquidadosPorRecepcion'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reporteLotesLiquidadosPorRecepcionInstance.version > version) {
                reporteLotesLiquidadosPorRecepcionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reporteLotesLiquidadosPorRecepcion.label', default: 'ReporteLotesLiquidadosPorRecepcion')] as Object[],
                        "Another user has updated this ReporteLotesLiquidadosPorRecepcion while you were editing")
                render(view: "edit", model: [reporteLotesLiquidadosPorRecepcionInstance: reporteLotesLiquidadosPorRecepcionInstance])
                return
            }
        }

        reporteLotesLiquidadosPorRecepcionInstance.properties = params

        if (!reporteLotesLiquidadosPorRecepcionInstance.save(flush: true)) {
            render(view: "edit", model: [reporteLotesLiquidadosPorRecepcionInstance: reporteLotesLiquidadosPorRecepcionInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reporteLotesLiquidadosPorRecepcion.label', default: 'ReporteLotesLiquidadosPorRecepcion'), reporteLotesLiquidadosPorRecepcionInstance.id])
        redirect(action: "show", id: reporteLotesLiquidadosPorRecepcionInstance.id)
    }

    def delete(Long id) {
        def reporteLotesLiquidadosPorRecepcionInstance = ReporteLotesLiquidadosPorRecepcion.get(id)
        if (!reporteLotesLiquidadosPorRecepcionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteLotesLiquidadosPorRecepcion.label', default: 'ReporteLotesLiquidadosPorRecepcion'), id])
            redirect(action: "list")
            return
        }

        try {
            reporteLotesLiquidadosPorRecepcionInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reporteLotesLiquidadosPorRecepcion.label', default: 'ReporteLotesLiquidadosPorRecepcion'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reporteLotesLiquidadosPorRecepcion.label', default: 'ReporteLotesLiquidadosPorRecepcion'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporte = {
        chain(controller:'jasper',action:'index',params:params)
    }
}
