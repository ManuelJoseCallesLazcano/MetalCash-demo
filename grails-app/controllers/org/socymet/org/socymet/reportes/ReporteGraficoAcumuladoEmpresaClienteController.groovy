package org.socymet.org.socymet.reportes

import grails.converters.JSON
import groovy.sql.Sql
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
class ReporteGraficoAcumuladoEmpresaClienteController {
    def dataSource

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ReporteGraficoAcumuladoEmpresaCliente.list(params), model:[reporteGraficoAcumuladoEmpresaClienteInstanceCount: ReporteGraficoAcumuladoEmpresaCliente.count()]
    }

    def show(ReporteGraficoAcumuladoEmpresaCliente reporteGraficoAcumuladoEmpresaClienteInstance) {
        respond reporteGraficoAcumuladoEmpresaClienteInstance
    }

    def create() {
        respond new ReporteGraficoAcumuladoEmpresaCliente(params)
    }

    @Transactional
    def save(ReporteGraficoAcumuladoEmpresaCliente reporteGraficoAcumuladoEmpresaClienteInstance) {
        if (reporteGraficoAcumuladoEmpresaClienteInstance == null) {
            notFound()
            return
        }

        if (reporteGraficoAcumuladoEmpresaClienteInstance.hasErrors()) {
            respond reporteGraficoAcumuladoEmpresaClienteInstance.errors, view:'create'
            return
        }

        reporteGraficoAcumuladoEmpresaClienteInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'reporteGraficoAcumuladoEmpresaCliente.label', default: 'ReporteGraficoAcumuladoEmpresaCliente'), reporteGraficoAcumuladoEmpresaClienteInstance.id])
                redirect reporteGraficoAcumuladoEmpresaClienteInstance
            }
            '*' { respond reporteGraficoAcumuladoEmpresaClienteInstance, [status: CREATED] }
        }
    }

    def edit(ReporteGraficoAcumuladoEmpresaCliente reporteGraficoAcumuladoEmpresaClienteInstance) {
        respond reporteGraficoAcumuladoEmpresaClienteInstance
    }

    @Transactional
    def update(ReporteGraficoAcumuladoEmpresaCliente reporteGraficoAcumuladoEmpresaClienteInstance) {
        if (reporteGraficoAcumuladoEmpresaClienteInstance == null) {
            notFound()
            return
        }

        if (reporteGraficoAcumuladoEmpresaClienteInstance.hasErrors()) {
            respond reporteGraficoAcumuladoEmpresaClienteInstance.errors, view:'edit'
            return
        }

        reporteGraficoAcumuladoEmpresaClienteInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ReporteGraficoAcumuladoEmpresaCliente.label', default: 'ReporteGraficoAcumuladoEmpresaCliente'), reporteGraficoAcumuladoEmpresaClienteInstance.id])
                redirect reporteGraficoAcumuladoEmpresaClienteInstance
            }
            '*'{ respond reporteGraficoAcumuladoEmpresaClienteInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(ReporteGraficoAcumuladoEmpresaCliente reporteGraficoAcumuladoEmpresaClienteInstance) {

        if (reporteGraficoAcumuladoEmpresaClienteInstance == null) {
            notFound()
            return
        }

        reporteGraficoAcumuladoEmpresaClienteInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ReporteGraficoAcumuladoEmpresaCliente.label', default: 'ReporteGraficoAcumuladoEmpresaCliente'), reporteGraficoAcumuladoEmpresaClienteInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteGraficoAcumuladoEmpresaCliente.label', default: 'ReporteGraficoAcumuladoEmpresaCliente'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def datosGrafico() {
        log.error("params.empresaId: ${params.empresaId}")
        def empresaId = params.empresaId
        def consulta = "SELECT liq.nombre_cliente,\n" +
                "sum(liq.valor_neto_mineral_en_bolivianos) as valor_neto,\n" +
                "sum(liq.kilos_netos_secos) as kilos_netos,\n" +
                "sum(liq.kilos_finos_zinc)/sum(liq.kilos_netos_secos)*100 as promedio_zinc,\n" +
                "sum(liq.kilos_finos_plomo)/sum(liq.kilos_netos_secos)*100 as promedio_plomo,\n" +
                "1000*sum(liq.kilos_finos_plata)/sum(liq.kilos_netos_secos) as promedio_plata\n" +
                "FROM liquidacion_demo.liquidacion liq\n" +
                "inner join liquidacion_demo.empresa e on e.id=liq.empresa_id\n" +
                "where empresa_id=${empresaId}\n" +
                "group by liq.nombre_cliente\n" +
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
