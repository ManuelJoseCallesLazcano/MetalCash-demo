package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class ReporteAnticiposContraEntregaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reporteAnticiposContraEntregaInstanceList: ReporteAnticiposContraEntrega.list(params), reporteAnticiposContraEntregaInstanceTotal: ReporteAnticiposContraEntrega.count()]
    }

    def create() {
        [reporteAnticiposContraEntregaInstance: new ReporteAnticiposContraEntrega(params)]
    }

    def save() {
        def reporteAnticiposContraEntregaInstance = new ReporteAnticiposContraEntrega(params)
        if (!reporteAnticiposContraEntregaInstance.save(flush: true)) {
            render(view: "create", model: [reporteAnticiposContraEntregaInstance: reporteAnticiposContraEntregaInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reporteAnticiposContraEntrega.label', default: 'ReporteAnticiposContraEntrega'), reporteAnticiposContraEntregaInstance.id])
        redirect(action: "show", id: reporteAnticiposContraEntregaInstance.id)
    }

    def show(Long id) {
        def reporteAnticiposContraEntregaInstance = ReporteAnticiposContraEntrega.get(id)
        if (!reporteAnticiposContraEntregaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteAnticiposContraEntrega.label', default: 'ReporteAnticiposContraEntrega'), id])
            redirect(action: "list")
            return
        }

        [reporteAnticiposContraEntregaInstance: reporteAnticiposContraEntregaInstance]
    }

    def edit(Long id) {
        def reporteAnticiposContraEntregaInstance = ReporteAnticiposContraEntrega.get(id)
        if (!reporteAnticiposContraEntregaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteAnticiposContraEntrega.label', default: 'ReporteAnticiposContraEntrega'), id])
            redirect(action: "list")
            return
        }

        [reporteAnticiposContraEntregaInstance: reporteAnticiposContraEntregaInstance]
    }

    def update(Long id, Long version) {
        def reporteAnticiposContraEntregaInstance = ReporteAnticiposContraEntrega.get(id)
        if (!reporteAnticiposContraEntregaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteAnticiposContraEntrega.label', default: 'ReporteAnticiposContraEntrega'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reporteAnticiposContraEntregaInstance.version > version) {
                reporteAnticiposContraEntregaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reporteAnticiposContraEntrega.label', default: 'ReporteAnticiposContraEntrega')] as Object[],
                        "Another user has updated this ReporteAnticiposContraEntrega while you were editing")
                render(view: "edit", model: [reporteAnticiposContraEntregaInstance: reporteAnticiposContraEntregaInstance])
                return
            }
        }

        reporteAnticiposContraEntregaInstance.properties = params

        if (!reporteAnticiposContraEntregaInstance.save(flush: true)) {
            render(view: "edit", model: [reporteAnticiposContraEntregaInstance: reporteAnticiposContraEntregaInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reporteAnticiposContraEntrega.label', default: 'ReporteAnticiposContraEntrega'), reporteAnticiposContraEntregaInstance.id])
        redirect(action: "show", id: reporteAnticiposContraEntregaInstance.id)
    }

    def delete(Long id) {
        def reporteAnticiposContraEntregaInstance = ReporteAnticiposContraEntrega.get(id)
        if (!reporteAnticiposContraEntregaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteAnticiposContraEntrega.label', default: 'ReporteAnticiposContraEntrega'), id])
            redirect(action: "list")
            return
        }

        try {
            reporteAnticiposContraEntregaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reporteAnticiposContraEntrega.label', default: 'ReporteAnticiposContraEntrega'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reporteAnticiposContraEntrega.label', default: 'ReporteAnticiposContraEntrega'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporte = {
        chain(controller:'jasper',action:'index',params:params)
    }
}
