package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class ReporteDetalleAnticiposContraEntregaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reporteDetalleAnticiposContraEntregaInstanceList: ReporteDetalleAnticiposContraEntrega.list(params), reporteDetalleAnticiposContraEntregaInstanceTotal: ReporteDetalleAnticiposContraEntrega.count()]
    }

    def create() {
        [reporteDetalleAnticiposContraEntregaInstance: new ReporteDetalleAnticiposContraEntrega(params)]
    }

    def save() {
        def reporteDetalleAnticiposContraEntregaInstance = new ReporteDetalleAnticiposContraEntrega(params)
        if (!reporteDetalleAnticiposContraEntregaInstance.save(flush: true)) {
            render(view: "create", model: [reporteDetalleAnticiposContraEntregaInstance: reporteDetalleAnticiposContraEntregaInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reporteDetalleAnticiposContraEntrega.label', default: 'ReporteDetalleAnticiposContraEntrega'), reporteDetalleAnticiposContraEntregaInstance.id])
        redirect(action: "show", id: reporteDetalleAnticiposContraEntregaInstance.id)
    }

    def show(Long id) {
        def reporteDetalleAnticiposContraEntregaInstance = ReporteDetalleAnticiposContraEntrega.get(id)
        if (!reporteDetalleAnticiposContraEntregaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteDetalleAnticiposContraEntrega.label', default: 'ReporteDetalleAnticiposContraEntrega'), id])
            redirect(action: "list")
            return
        }

        [reporteDetalleAnticiposContraEntregaInstance: reporteDetalleAnticiposContraEntregaInstance]
    }

    def edit(Long id) {
        def reporteDetalleAnticiposContraEntregaInstance = ReporteDetalleAnticiposContraEntrega.get(id)
        if (!reporteDetalleAnticiposContraEntregaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteDetalleAnticiposContraEntrega.label', default: 'ReporteDetalleAnticiposContraEntrega'), id])
            redirect(action: "list")
            return
        }

        [reporteDetalleAnticiposContraEntregaInstance: reporteDetalleAnticiposContraEntregaInstance]
    }

    def update(Long id, Long version) {
        def reporteDetalleAnticiposContraEntregaInstance = ReporteDetalleAnticiposContraEntrega.get(id)
        if (!reporteDetalleAnticiposContraEntregaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteDetalleAnticiposContraEntrega.label', default: 'ReporteDetalleAnticiposContraEntrega'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reporteDetalleAnticiposContraEntregaInstance.version > version) {
                reporteDetalleAnticiposContraEntregaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reporteDetalleAnticiposContraEntrega.label', default: 'ReporteDetalleAnticiposContraEntrega')] as Object[],
                        "Another user has updated this ReporteDetalleAnticiposContraEntrega while you were editing")
                render(view: "edit", model: [reporteDetalleAnticiposContraEntregaInstance: reporteDetalleAnticiposContraEntregaInstance])
                return
            }
        }

        reporteDetalleAnticiposContraEntregaInstance.properties = params

        if (!reporteDetalleAnticiposContraEntregaInstance.save(flush: true)) {
            render(view: "edit", model: [reporteDetalleAnticiposContraEntregaInstance: reporteDetalleAnticiposContraEntregaInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reporteDetalleAnticiposContraEntrega.label', default: 'ReporteDetalleAnticiposContraEntrega'), reporteDetalleAnticiposContraEntregaInstance.id])
        redirect(action: "show", id: reporteDetalleAnticiposContraEntregaInstance.id)
    }

    def delete(Long id) {
        def reporteDetalleAnticiposContraEntregaInstance = ReporteDetalleAnticiposContraEntrega.get(id)
        if (!reporteDetalleAnticiposContraEntregaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteDetalleAnticiposContraEntrega.label', default: 'ReporteDetalleAnticiposContraEntrega'), id])
            redirect(action: "list")
            return
        }

        try {
            reporteDetalleAnticiposContraEntregaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reporteDetalleAnticiposContraEntrega.label', default: 'ReporteDetalleAnticiposContraEntrega'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reporteDetalleAnticiposContraEntrega.label', default: 'ReporteDetalleAnticiposContraEntrega'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporte = {
        chain(controller:'jasper',action:'index',params:params)
    }
}
