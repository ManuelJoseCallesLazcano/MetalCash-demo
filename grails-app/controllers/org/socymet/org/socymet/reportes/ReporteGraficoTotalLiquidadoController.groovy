package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.liquidacion.*
import org.socymet.proveedor.Empresa
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class ReporteGraficoTotalLiquidadoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reporteGraficoTotalLiquidadoInstanceList: ReporteGraficoTotalLiquidado.list(params), reporteGraficoTotalLiquidadoInstanceTotal: ReporteGraficoTotalLiquidado.count()]
    }

    def create() {
        [reporteGraficoTotalLiquidadoInstance: new ReporteGraficoTotalLiquidado(params)]
    }

    def save() {
        def reporteGraficoTotalLiquidadoInstance = new ReporteGraficoTotalLiquidado(params)
        if (!reporteGraficoTotalLiquidadoInstance.save(flush: true)) {
            render(view: "create", model: [reporteGraficoTotalLiquidadoInstance: reporteGraficoTotalLiquidadoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reporteGraficoTotalLiquidado.label', default: 'ReporteGraficoTotalLiquidado'), reporteGraficoTotalLiquidadoInstance.id])
        redirect(action: "show", id: reporteGraficoTotalLiquidadoInstance.id)
    }

    def show(Long id) {
        def reporteGraficoTotalLiquidadoInstance = ReporteGraficoTotalLiquidado.get(id)
        if (!reporteGraficoTotalLiquidadoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteGraficoTotalLiquidado.label', default: 'ReporteGraficoTotalLiquidado'), id])
            redirect(action: "list")
            return
        }

        [reporteGraficoTotalLiquidadoInstance: reporteGraficoTotalLiquidadoInstance]
    }

    def edit(Long id) {
        def reporteGraficoTotalLiquidadoInstance = ReporteGraficoTotalLiquidado.get(id)
        if (!reporteGraficoTotalLiquidadoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteGraficoTotalLiquidado.label', default: 'ReporteGraficoTotalLiquidado'), id])
            redirect(action: "list")
            return
        }

        [reporteGraficoTotalLiquidadoInstance: reporteGraficoTotalLiquidadoInstance]
    }

    def update(Long id, Long version) {
        def reporteGraficoTotalLiquidadoInstance = ReporteGraficoTotalLiquidado.get(id)
        if (!reporteGraficoTotalLiquidadoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteGraficoTotalLiquidado.label', default: 'ReporteGraficoTotalLiquidado'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reporteGraficoTotalLiquidadoInstance.version > version) {
                reporteGraficoTotalLiquidadoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reporteGraficoTotalLiquidado.label', default: 'ReporteGraficoTotalLiquidado')] as Object[],
                        "Another user has updated this ReporteGraficoTotalLiquidado while you were editing")
                render(view: "edit", model: [reporteGraficoTotalLiquidadoInstance: reporteGraficoTotalLiquidadoInstance])
                return
            }
        }

        reporteGraficoTotalLiquidadoInstance.properties = params

        if (!reporteGraficoTotalLiquidadoInstance.save(flush: true)) {
            render(view: "edit", model: [reporteGraficoTotalLiquidadoInstance: reporteGraficoTotalLiquidadoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reporteGraficoTotalLiquidado.label', default: 'ReporteGraficoTotalLiquidado'), reporteGraficoTotalLiquidadoInstance.id])
        redirect(action: "show", id: reporteGraficoTotalLiquidadoInstance.id)
    }

    def delete(Long id) {
        def reporteGraficoTotalLiquidadoInstance = ReporteGraficoTotalLiquidado.get(id)
        if (!reporteGraficoTotalLiquidadoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteGraficoTotalLiquidado.label', default: 'ReporteGraficoTotalLiquidado'), id])
            redirect(action: "list")
            return
        }

        try {
            reporteGraficoTotalLiquidadoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reporteGraficoTotalLiquidado.label', default: 'ReporteGraficoTotalLiquidado'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reporteGraficoTotalLiquidado.label', default: 'ReporteGraficoTotalLiquidado'), id])
            redirect(action: "show", id: id)
        }
    }

    def listaTotalLiquidadoEstano() {
        def c = LiquidacionDeEstano.createCriteria()
        def liquidacionesEstano = c.list {
            projections {
                groupProperty('fechaDeLiquidacion','fechaDeLiquidacion')
                sum('totalLiquidoPagable','totalLiquidoPagable')
            }
            order("fechaDeLiquidacion", "asc")
        }

        log.error("***** RESULTADOS: ${liquidacionesEstano.size()}")

        liquidacionesEstano.each {
            log.error("***** ${it.getAt(0)}")
        }

        def liquidacionesList = []
        liquidacionesEstano.each { liquidacion ->
            def coordenada = []
            coordenada.add(liquidacion.getAt(0).time)
            coordenada.add(liquidacion.getAt(1))
            liquidacionesList.add(coordenada)
        }

        render liquidacionesList as JSON
    }

    def listaTotalLiquidadoPlata() {
        def c = LiquidacionDePlata.createCriteria()
        def liquidacionesPlata = c.list {
            projections {
                groupProperty('fechaDeLiquidacion','fechaDeLiquidacion')
                sum('totalLiquidoPagable','totalLiquidoPagable')
            }
            order("fechaDeLiquidacion", "asc")
        }

        log.error("***** RESULTADOS: ${liquidacionesPlata.size()}")

        liquidacionesPlata.each {
            log.error("***** ${it.getAt(0)}")
        }

        def liquidacionesList = []
        liquidacionesPlata.each { liquidacion ->
            def coordenada = []
            coordenada.add(liquidacion.getAt(0).time)
            coordenada.add(liquidacion.getAt(1))
            liquidacionesList.add(coordenada)
        }

        render liquidacionesList as JSON
    }

    def listaTotalLiquidadoWolfran() {
        def c = LiquidacionDeWolfran.createCriteria()
        def liquidacionesWolfran = c.list {
            projections {
                groupProperty('fechaDeLiquidacion','fechaDeLiquidacion')
                sum('totalLiquidoPagable','totalLiquidoPagable')
            }
            order("fechaDeLiquidacion", "asc")
        }

        log.error("***** RESULTADOS: ${liquidacionesWolfran.size()}")

        liquidacionesWolfran.each {
            log.error("***** ${it.getAt(0)}")
        }

        def liquidacionesList = []
        liquidacionesWolfran.each { liquidacion ->
            def coordenada = []
            coordenada.add(liquidacion.getAt(0).time)
            coordenada.add(liquidacion.getAt(1))
            liquidacionesList.add(coordenada)
        }

        render liquidacionesList as JSON
    }

    def listaTotalLiquidadoAntimonio() {
        def c = LiquidacionDeAntimonio.createCriteria()
        def liquidacionesAntimonio = c.list {
            projections {
                groupProperty('fechaDeLiquidacion','fechaDeLiquidacion')
                sum('totalLiquidoPagable','totalLiquidoPagable')
            }
            order("fechaDeLiquidacion", "asc")
        }

        log.error("***** RESULTADOS: ${liquidacionesAntimonio.size()}")

        liquidacionesAntimonio.each {
            log.error("***** ${it.getAt(0)}")
        }

        def liquidacionesList = []
        liquidacionesAntimonio.each { liquidacion ->
            def coordenada = []
            coordenada.add(liquidacion.getAt(0).time)
            coordenada.add(liquidacion.getAt(1))
            liquidacionesList.add(coordenada)
        }

        render liquidacionesList as JSON
    }

    def listaTotalLiquidadoComplejo() {
        def c = LiquidacionDeComplejo.createCriteria()
        def liquidacionesComplejo = c.list {
            projections {
                groupProperty('fechaDeLiquidacion','fechaDeLiquidacion')
                sum('totalLiquidoPagable','totalLiquidoPagable')
            }
            order("fechaDeLiquidacion", "asc")
        }

        log.error("***** RESULTADOS: ${liquidacionesComplejo.size()}")

        liquidacionesComplejo.each {
            log.error("***** ${it.getAt(0)}")
        }

        def liquidacionesList = []
        liquidacionesComplejo.each { liquidacion ->
            def coordenada = []
            coordenada.add(liquidacion.getAt(0).time)
            coordenada.add(liquidacion.getAt(1))
            liquidacionesList.add(coordenada)
        }

        render liquidacionesList as JSON
    }

    def listaTotalLiquidadoEstanoEmpresa() {
        def empresaId = params.empresaId
        def empresa = Empresa.get(Integer.parseInt("$empresaId"))

        def c = LiquidacionDeEstano.createCriteria()
        def liquidacionesEstano = c.list {
            projections {
                groupProperty('fechaDeLiquidacion','fechaDeLiquidacion')
                sum('totalLiquidoPagable','totalLiquidoPagable')
            }
            like("nombreEmpresa",empresa.toString())
            order("fechaDeLiquidacion", "asc")
        }

        log.error("***** RESULTADOS: ${liquidacionesEstano.size()}")

        liquidacionesEstano.each {
            log.error("***** ${it.getAt(0)}")
        }

        def liquidacionesList = []
        liquidacionesEstano.each { liquidacion ->
            def coordenada = []
            coordenada.add(liquidacion.getAt(0).time)
            coordenada.add(liquidacion.getAt(1))
            liquidacionesList.add(coordenada)
        }

        render liquidacionesList as JSON
    }

    def listaTotalLiquidadoPlataEmpresa() {
        def empresaId = params.empresaId
        def empresa = Empresa.get(Integer.parseInt("$empresaId"))

        def c = LiquidacionDePlata.createCriteria()
        def liquidacionesPlata = c.list {
            projections {
                groupProperty('fechaDeLiquidacion','fechaDeLiquidacion')
                sum('totalLiquidoPagable','totalLiquidoPagable')
            }
            like("nombreEmpresa",empresa.toString())
            order("fechaDeLiquidacion", "asc")
        }

        log.error("***** RESULTADOS: ${liquidacionesPlata.size()}")

        liquidacionesPlata.each {
            log.error("***** ${it.getAt(0)}")
        }

        def liquidacionesList = []
        liquidacionesPlata.each { liquidacion ->
            def coordenada = []
            coordenada.add(liquidacion.getAt(0).time)
            coordenada.add(liquidacion.getAt(1))
            liquidacionesList.add(coordenada)
        }

        render liquidacionesList as JSON
    }

    def listaTotalLiquidadoWolfranEmpresa() {
        def empresaId = params.empresaId
        def empresa = Empresa.get(Integer.parseInt("$empresaId"))

        def c = LiquidacionDeWolfran.createCriteria()
        def liquidacionesWolfran = c.list {
            projections {
                groupProperty('fechaDeLiquidacion','fechaDeLiquidacion')
                sum('totalLiquidoPagable','totalLiquidoPagable')
            }
            like("nombreEmpresa",empresa.toString())
            order("fechaDeLiquidacion", "asc")
        }

        log.error("***** RESULTADOS: ${liquidacionesWolfran.size()}")

        liquidacionesWolfran.each {
            log.error("***** ${it.getAt(0)}")
        }

        def liquidacionesList = []
        liquidacionesWolfran.each { liquidacion ->
            def coordenada = []
            coordenada.add(liquidacion.getAt(0).time)
            coordenada.add(liquidacion.getAt(1))
            liquidacionesList.add(coordenada)
        }

        render liquidacionesList as JSON
    }

    def listaTotalLiquidadoAntimonioEmpresa() {
        def empresaId = params.empresaId
        def empresa = Empresa.get(Integer.parseInt("$empresaId"))

        def c = LiquidacionDeAntimonio.createCriteria()
        def liquidacionesAntimonio = c.list {
            projections {
                groupProperty('fechaDeLiquidacion','fechaDeLiquidacion')
                sum('totalLiquidoPagable','totalLiquidoPagable')
            }
            like("nombreEmpresa",empresa.toString())
            order("fechaDeLiquidacion", "asc")
        }

        log.error("***** RESULTADOS: ${liquidacionesAntimonio.size()}")

        liquidacionesAntimonio.each {
            log.error("***** ${it.getAt(0)}")
        }

        def liquidacionesList = []
        liquidacionesAntimonio.each { liquidacion ->
            def coordenada = []
            coordenada.add(liquidacion.getAt(0).time)
            coordenada.add(liquidacion.getAt(1))
            liquidacionesList.add(coordenada)
        }

        render liquidacionesList as JSON
    }

    def listaTotalLiquidadoComplejoEmpresa() {
        def empresaId = params.empresaId
        def empresa = Empresa.get(Integer.parseInt("$empresaId"))

        def c = LiquidacionDeComplejo.createCriteria()
        def liquidacionesComplejo = c.list {
            projections {
                groupProperty('fechaDeLiquidacion','fechaDeLiquidacion')
                sum('totalLiquidoPagable','totalLiquidoPagable')
            }
            like("nombreEmpresa",empresa.toString())
            order("fechaDeLiquidacion", "asc")
        }

        log.error("***** RESULTADOS: ${liquidacionesComplejo.size()}")

        liquidacionesComplejo.each {
            log.error("***** ${it.getAt(0)}")
        }

        def liquidacionesList = []
        liquidacionesComplejo.each { liquidacion ->
            def coordenada = []
            coordenada.add(liquidacion.getAt(0).time)
            coordenada.add(liquidacion.getAt(1))
            liquidacionesList.add(coordenada)
        }

        render liquidacionesList as JSON
    }
}
