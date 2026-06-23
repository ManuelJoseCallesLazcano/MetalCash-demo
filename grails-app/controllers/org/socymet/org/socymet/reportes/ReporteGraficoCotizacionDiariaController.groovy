package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.cotizaciones.CotizacionDiariaDeMinerales
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class ReporteGraficoCotizacionDiariaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reporteGraficoCotizacionDiariaInstanceList: ReporteGraficoCotizacionDiaria.list(params), reporteGraficoCotizacionDiariaInstanceTotal: ReporteGraficoCotizacionDiaria.count()]
    }

    def create() {
        [reporteGraficoCotizacionDiariaInstance: new ReporteGraficoCotizacionDiaria(params)]
    }

    def save() {
        def reporteGraficoCotizacionDiariaInstance = new ReporteGraficoCotizacionDiaria(params)
        if (!reporteGraficoCotizacionDiariaInstance.save(flush: true)) {
            render(view: "create", model: [reporteGraficoCotizacionDiariaInstance: reporteGraficoCotizacionDiariaInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reporteGraficoCotizacionDiaria.label', default: 'ReporteGraficoCotizacionDiaria'), reporteGraficoCotizacionDiariaInstance.id])
        redirect(action: "show", id: reporteGraficoCotizacionDiariaInstance.id)
    }

    def show(Long id) {
        def reporteGraficoCotizacionDiariaInstance = ReporteGraficoCotizacionDiaria.get(id)
        if (!reporteGraficoCotizacionDiariaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteGraficoCotizacionDiaria.label', default: 'ReporteGraficoCotizacionDiaria'), id])
            redirect(action: "list")
            return
        }

        [reporteGraficoCotizacionDiariaInstance: reporteGraficoCotizacionDiariaInstance]
    }

    def edit(Long id) {
        def reporteGraficoCotizacionDiariaInstance = ReporteGraficoCotizacionDiaria.get(id)
        if (!reporteGraficoCotizacionDiariaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteGraficoCotizacionDiaria.label', default: 'ReporteGraficoCotizacionDiaria'), id])
            redirect(action: "list")
            return
        }

        [reporteGraficoCotizacionDiariaInstance: reporteGraficoCotizacionDiariaInstance]
    }

    def update(Long id, Long version) {
        def reporteGraficoCotizacionDiariaInstance = ReporteGraficoCotizacionDiaria.get(id)
        if (!reporteGraficoCotizacionDiariaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteGraficoCotizacionDiaria.label', default: 'ReporteGraficoCotizacionDiaria'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reporteGraficoCotizacionDiariaInstance.version > version) {
                reporteGraficoCotizacionDiariaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reporteGraficoCotizacionDiaria.label', default: 'ReporteGraficoCotizacionDiaria')] as Object[],
                        "Another user has updated this ReporteGraficoCotizacionDiaria while you were editing")
                render(view: "edit", model: [reporteGraficoCotizacionDiariaInstance: reporteGraficoCotizacionDiariaInstance])
                return
            }
        }

        reporteGraficoCotizacionDiariaInstance.properties = params

        if (!reporteGraficoCotizacionDiariaInstance.save(flush: true)) {
            render(view: "edit", model: [reporteGraficoCotizacionDiariaInstance: reporteGraficoCotizacionDiariaInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reporteGraficoCotizacionDiaria.label', default: 'ReporteGraficoCotizacionDiaria'), reporteGraficoCotizacionDiariaInstance.id])
        redirect(action: "show", id: reporteGraficoCotizacionDiariaInstance.id)
    }

    def delete(Long id) {
        def reporteGraficoCotizacionDiariaInstance = ReporteGraficoCotizacionDiaria.get(id)
        if (!reporteGraficoCotizacionDiariaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteGraficoCotizacionDiaria.label', default: 'ReporteGraficoCotizacionDiaria'), id])
            redirect(action: "list")
            return
        }

        try {
            reporteGraficoCotizacionDiariaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reporteGraficoCotizacionDiaria.label', default: 'ReporteGraficoCotizacionDiaria'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reporteGraficoCotizacionDiaria.label', default: 'ReporteGraficoCotizacionDiaria'), id])
            redirect(action: "show", id: id)
        }
    }

    def listaCotizacionDiariaEstano() {
        def cotizaciones = CotizacionDiariaDeMinerales.list("from CotizacionDiariaDeMinerales as cdm order by cdm.fecha")

        def cotizacionesList = []
        cotizaciones.each { cotizacion ->
            def coordenada = []
            coordenada.add(cotizacion.fecha.time)
            coordenada.add(cotizacion.estano)
            cotizacionesList.add(coordenada)
        }

        render cotizacionesList as JSON
    }

    def listaCotizacionDiariaPlata() {
        def cotizaciones = CotizacionDiariaDeMinerales.list("from CotizacionDiariaDeMinerales as cdm order by cdm.fecha")

        def cotizacionesList = []
        cotizaciones.each { cotizacion ->
            def coordenada = []
            coordenada.add(cotizacion.fecha.time)
            coordenada.add(cotizacion.plata)
            cotizacionesList.add(coordenada)
        }

        render cotizacionesList as JSON
    }

    def listaCotizacionDiariaPlomo() {
        def cotizaciones = CotizacionDiariaDeMinerales.list("from CotizacionDiariaDeMinerales as cdm order by cdm.fecha")

        def cotizacionesList = []
        cotizaciones.each { cotizacion ->
            def coordenada = []
            coordenada.add(cotizacion.fecha.time)
            coordenada.add(cotizacion.plomo)
            cotizacionesList.add(coordenada)
        }

        render cotizacionesList as JSON
    }

    def listaCotizacionDiariaAntimonio() {
        def cotizaciones = CotizacionDiariaDeMinerales.list("from CotizacionDiariaDeMinerales as cdm order by cdm.fecha")

        def cotizacionesList = []
        cotizaciones.each { cotizacion ->
            def coordenada = []
            coordenada.add(cotizacion.fecha.time)
            coordenada.add(cotizacion.antimonio)
            cotizacionesList.add(coordenada)
        }

        render cotizacionesList as JSON
    }

    def listaCotizacionDiariaZinc() {
        def cotizaciones = CotizacionDiariaDeMinerales.list("from CotizacionDiariaDeMinerales as cdm order by cdm.fecha")

        def cotizacionesList = []
        cotizaciones.each { cotizacion ->
            def coordenada = []
            coordenada.add(cotizacion.fecha.time)
            coordenada.add(cotizacion.zinc)
            cotizacionesList.add(coordenada)
        }

        render cotizacionesList as JSON
    }

    def listaCotizacionDiariaWolfran() {
        def cotizaciones = CotizacionDiariaDeMinerales.list("from CotizacionDiariaDeMinerales as cdm order by cdm.fecha")

        def cotizacionesList = []
        cotizaciones.each { cotizacion ->
            def coordenada = []
            coordenada.add(cotizacion.fecha.time)
            coordenada.add(cotizacion.wolfran)
            cotizacionesList.add(coordenada)
        }

        render cotizacionesList as JSON
    }


}
