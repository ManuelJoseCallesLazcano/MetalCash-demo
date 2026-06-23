package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import groovy.sql.Sql
import org.socymet.liquidacion.*
import org.socymet.proveedor.Empresa
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class ReporteGraficoCantidadController {
    def dataSource

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reporteGraficoCantidadInstanceList: ReporteGraficoCantidad.list(params), reporteGraficoCantidadInstanceTotal: ReporteGraficoCantidad.count()]
    }

    def create() {
        [reporteGraficoCantidadInstance: new ReporteGraficoCantidad(params)]
    }

    def save() {
        def reporteGraficoCantidadInstance = new ReporteGraficoCantidad(params)
        if (!reporteGraficoCantidadInstance.save(flush: true)) {
            render(view: "create", model: [reporteGraficoCantidadInstance: reporteGraficoCantidadInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reporteGraficoCantidad.label', default: 'ReporteGraficoCantidad'), reporteGraficoCantidadInstance.id])
        redirect(action: "show", id: reporteGraficoCantidadInstance.id)
    }

    def show(Long id) {
        def reporteGraficoCantidadInstance = ReporteGraficoCantidad.get(id)
        if (!reporteGraficoCantidadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteGraficoCantidad.label', default: 'ReporteGraficoCantidad'), id])
            redirect(action: "list")
            return
        }

        [reporteGraficoCantidadInstance: reporteGraficoCantidadInstance]
    }

    def edit(Long id) {
        def reporteGraficoCantidadInstance = ReporteGraficoCantidad.get(id)
        if (!reporteGraficoCantidadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteGraficoCantidad.label', default: 'ReporteGraficoCantidad'), id])
            redirect(action: "list")
            return
        }

        [reporteGraficoCantidadInstance: reporteGraficoCantidadInstance]
    }

    def update(Long id, Long version) {
        def reporteGraficoCantidadInstance = ReporteGraficoCantidad.get(id)
        if (!reporteGraficoCantidadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteGraficoCantidad.label', default: 'ReporteGraficoCantidad'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reporteGraficoCantidadInstance.version > version) {
                reporteGraficoCantidadInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reporteGraficoCantidad.label', default: 'ReporteGraficoCantidad')] as Object[],
                        "Another user has updated this ReporteGraficoCantidad while you were editing")
                render(view: "edit", model: [reporteGraficoCantidadInstance: reporteGraficoCantidadInstance])
                return
            }
        }

        reporteGraficoCantidadInstance.properties = params

        if (!reporteGraficoCantidadInstance.save(flush: true)) {
            render(view: "edit", model: [reporteGraficoCantidadInstance: reporteGraficoCantidadInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reporteGraficoCantidad.label', default: 'ReporteGraficoCantidad'), reporteGraficoCantidadInstance.id])
        redirect(action: "show", id: reporteGraficoCantidadInstance.id)
    }

    def delete(Long id) {
        def reporteGraficoCantidadInstance = ReporteGraficoCantidad.get(id)
        if (!reporteGraficoCantidadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteGraficoCantidad.label', default: 'ReporteGraficoCantidad'), id])
            redirect(action: "list")
            return
        }

        try {
            reporteGraficoCantidadInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reporteGraficoCantidad.label', default: 'ReporteGraficoCantidad'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reporteGraficoCantidad.label', default: 'ReporteGraficoCantidad'), id])
            redirect(action: "show", id: id)
        }
    }

    def listaPesosComplejo() {
        def consulta = "SELECT e.nombre_de_empresa,\n" +
                "sum(liq.valor_neto_mineral_en_bolivianos) as valor_neto,\n" +
                "sum(liq.kilos_netos_secos) as kilos_netos,\n" +
                "sum(liq.kilos_finos_zinc)/sum(liq.kilos_netos_secos)*100 as promedio_zinc,\n" +
                "sum(liq.kilos_finos_plomo)/sum(liq.kilos_netos_secos)*100 as promedio_plomo,\n" +
                "1000*sum(liq.kilos_finos_plata)/sum(liq.kilos_netos_secos) as promedio_plata\n" +
                "FROM liquidacion_demo.liquidacion liq\n" +
                "inner join liquidacion_demo.empresa e on e.id=liq.empresa_id\n" +
                "group by liq.empresa_id\n" +
                "order by valor_neto desc"
        def sql = new Sql(dataSource)
        def rows = sql.rows(consulta)

        def empresasList=[]
        def valorNetoList=[]
        def pesoNetoList=[]
        def leyZincList=[]
        def leyPlomoList=[]
        def leyPlataList=[]

        rows.each { row ->
            empresasList.add("${row[0]}<br>Zn: ${row[3].toString().toDouble().round(2)} % | Pb: ${row[4].toString().toDouble().round(2)} % | Ag: ${row[5].toString().toDouble().round(2)} DM")
            valorNetoList.add(row[1])
            pesoNetoList.add(row[2])
            leyZincList.add(row[3].toString().toDouble().round(2))
            leyPlomoList.add(row[4].toString().toDouble().round(2))
            leyPlataList.add(row[5].toString().toDouble().round(2))
        }
        sql.close()

        def empresasString = (empresasList as JSON).toString().replace('\"','')
        def valoresNetosString = (valorNetoList as JSON).toString()
        def pesosNetosString = (pesoNetoList as JSON).toString()
        def leyesZincString = (leyZincList as JSON).toString()
        def leyesPlomoString = (leyPlomoList as JSON).toString()
        def leyesPlataString = (leyPlataList as JSON).toString()
        
        render([
            empresas: empresasString.substring(1, empresasString.length() - 1),
            valoresNetos: valoresNetosString.substring(1, valoresNetosString.length() - 1),
            pesosNetos: pesosNetosString.substring(1, pesosNetosString.length() - 1),
            leyesZinc: leyesZincString.substring(1, leyesZincString.length() - 1),
            leyesPlomo: leyesPlomoString.substring(1, leyesPlomoString.length() - 1),
            leyesPlata: leyesPlataString.substring(1, leyesPlataString.length() - 1)
        ] as JSON)
    }
}
