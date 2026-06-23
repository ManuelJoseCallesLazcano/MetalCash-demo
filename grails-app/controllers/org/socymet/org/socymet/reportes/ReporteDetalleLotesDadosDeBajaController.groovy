package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class ReporteDetalleLotesDadosDeBajaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reporteDetalleLotesDadosDeBajaInstanceList: ReporteDetalleLotesDadosDeBaja.list(params), reporteDetalleLotesDadosDeBajaInstanceTotal: ReporteDetalleLotesDadosDeBaja.count()]
    }

    def create() {
        [reporteDetalleLotesDadosDeBajaInstance: new ReporteDetalleLotesDadosDeBaja(params)]
    }

    def save() {
        def reporteDetalleLotesDadosDeBajaInstance = new ReporteDetalleLotesDadosDeBaja(params)
        if (!reporteDetalleLotesDadosDeBajaInstance.save(flush: true)) {
            render(view: "create", model: [reporteDetalleLotesDadosDeBajaInstance: reporteDetalleLotesDadosDeBajaInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reporteDetalleLotesDadosDeBaja.label', default: 'ReporteDetalleLotesDadosDeBaja'), reporteDetalleLotesDadosDeBajaInstance.id])
        redirect(action: "show", id: reporteDetalleLotesDadosDeBajaInstance.id)
    }

    def show(Long id) {
        def reporteDetalleLotesDadosDeBajaInstance = ReporteDetalleLotesDadosDeBaja.get(id)
        if (!reporteDetalleLotesDadosDeBajaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteDetalleLotesDadosDeBaja.label', default: 'ReporteDetalleLotesDadosDeBaja'), id])
            redirect(action: "list")
            return
        }

        [reporteDetalleLotesDadosDeBajaInstance: reporteDetalleLotesDadosDeBajaInstance]
    }

    def edit(Long id) {
        def reporteDetalleLotesDadosDeBajaInstance = ReporteDetalleLotesDadosDeBaja.get(id)
        if (!reporteDetalleLotesDadosDeBajaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteDetalleLotesDadosDeBaja.label', default: 'ReporteDetalleLotesDadosDeBaja'), id])
            redirect(action: "list")
            return
        }

        [reporteDetalleLotesDadosDeBajaInstance: reporteDetalleLotesDadosDeBajaInstance]
    }

    def update(Long id, Long version) {
        def reporteDetalleLotesDadosDeBajaInstance = ReporteDetalleLotesDadosDeBaja.get(id)
        if (!reporteDetalleLotesDadosDeBajaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteDetalleLotesDadosDeBaja.label', default: 'ReporteDetalleLotesDadosDeBaja'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reporteDetalleLotesDadosDeBajaInstance.version > version) {
                reporteDetalleLotesDadosDeBajaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reporteDetalleLotesDadosDeBaja.label', default: 'ReporteDetalleLotesDadosDeBaja')] as Object[],
                        "Another user has updated this ReporteDetalleLotesDadosDeBaja while you were editing")
                render(view: "edit", model: [reporteDetalleLotesDadosDeBajaInstance: reporteDetalleLotesDadosDeBajaInstance])
                return
            }
        }

        reporteDetalleLotesDadosDeBajaInstance.properties = params

        if (!reporteDetalleLotesDadosDeBajaInstance.save(flush: true)) {
            render(view: "edit", model: [reporteDetalleLotesDadosDeBajaInstance: reporteDetalleLotesDadosDeBajaInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reporteDetalleLotesDadosDeBaja.label', default: 'ReporteDetalleLotesDadosDeBaja'), reporteDetalleLotesDadosDeBajaInstance.id])
        redirect(action: "show", id: reporteDetalleLotesDadosDeBajaInstance.id)
    }

    def delete(Long id) {
        def reporteDetalleLotesDadosDeBajaInstance = ReporteDetalleLotesDadosDeBaja.get(id)
        if (!reporteDetalleLotesDadosDeBajaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteDetalleLotesDadosDeBaja.label', default: 'ReporteDetalleLotesDadosDeBaja'), id])
            redirect(action: "list")
            return
        }

        try {
            reporteDetalleLotesDadosDeBajaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reporteDetalleLotesDadosDeBaja.label', default: 'ReporteDetalleLotesDadosDeBaja'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reporteDetalleLotesDadosDeBaja.label', default: 'ReporteDetalleLotesDadosDeBaja'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporteEstano = {
        chain(controller:'jasper',action:'index',params:params)
    }

    def crearReportePlata = {
        chain(controller:'jasper',action:'index',params:params)
    }

    def crearReporteWolfran = {
        chain(controller:'jasper',action:'index',params:params)
    }

    def crearReporteAntimonio = {
        chain(controller:'jasper',action:'index',params:params)
    }

    def crearReporteComplejo = {
        chain(controller:'jasper',action:'index',params:params)
    }
}
