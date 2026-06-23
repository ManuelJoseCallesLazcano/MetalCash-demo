package org.socymet.org.socymet.reportes

import groovy.sql.Sql
import jxl.Workbook
import jxl.format.Alignment
import jxl.write.DateFormat
import jxl.write.DateTime
import jxl.write.Label
import jxl.write.Number
import jxl.write.NumberFormat
import jxl.write.WritableCellFormat
import jxl.write.WritableFont
import jxl.write.WritableSheet
import jxl.write.WritableWorkbook
import org.socymet.anticipos.AnticipoContraEntrega
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA','ROLE_REPORTES'])
class ReporteLotesCompositosController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def dataSource

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ReporteLotesCompositos.list(params), model:[reporteLotesCompositosInstanceCount: ReporteLotesCompositos.count()]
    }

    def show(ReporteLotesCompositos reporteLotesCompositosInstance) {
        respond reporteLotesCompositosInstance
    }

    def create() {
        respond new ReporteLotesCompositos(params)
    }

    @Transactional
    def save(ReporteLotesCompositos reporteLotesCompositosInstance) {
        if (reporteLotesCompositosInstance == null) {
            notFound()
            return
        }

        if (reporteLotesCompositosInstance.hasErrors()) {
            respond reporteLotesCompositosInstance.errors, view:'create'
            return
        }

        reporteLotesCompositosInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'reporteLotesCompositos.label', default: 'ReporteLotesCompositos'), reporteLotesCompositosInstance.id])
                redirect reporteLotesCompositosInstance
            }
            '*' { respond reporteLotesCompositosInstance, [status: CREATED] }
        }
    }

    def edit(ReporteLotesCompositos reporteLotesCompositosInstance) {
        respond reporteLotesCompositosInstance
    }

    @Transactional
    def update(ReporteLotesCompositos reporteLotesCompositosInstance) {
        if (reporteLotesCompositosInstance == null) {
            notFound()
            return
        }

        if (reporteLotesCompositosInstance.hasErrors()) {
            respond reporteLotesCompositosInstance.errors, view:'edit'
            return
        }

        reporteLotesCompositosInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ReporteLotesCompositos.label', default: 'ReporteLotesCompositos'), reporteLotesCompositosInstance.id])
                redirect reporteLotesCompositosInstance
            }
            '*'{ respond reporteLotesCompositosInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(ReporteLotesCompositos reporteLotesCompositosInstance) {

        if (reporteLotesCompositosInstance == null) {
            notFound()
            return
        }

        reporteLotesCompositosInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ReporteLotesCompositos.label', default: 'ReporteLotesCompositos'), reporteLotesCompositosInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteLotesCompositos.label', default: 'ReporteLotesCompositos'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def crearReporteComplejo = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.TAHOMA, 7, WritableFont.NO_BOLD);
        WritableFont courier6PlainFont = new WritableFont(WritableFont.TAHOMA, 7, WritableFont.NO_BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.TAHOMA, 7, WritableFont.NO_BOLD);
        WritableFont courier8BoldFont = new WritableFont(WritableFont.TAHOMA, 7, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.NO_BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.TAHOMA, 16, WritableFont.NO_BOLD);

        WritableCellFormat formatoEncabezado = new WritableCellFormat (arial10BoldFont);
        formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)
        formatoEncabezado.setWrap(true)
        //formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
        formatoEncabezado.setAlignment(Alignment.CENTRE)

        WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoDatos.setFont(courier8PlainFont)
        formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        WritableCellFormat formatoTotales = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoTotales.setFont(courier8BoldFont)
        formatoTotales.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)

        WritableCellFormat formatoInfoReporte = new WritableCellFormat (arial14BoldFont);
        WritableCellFormat formatoTitulo = new WritableCellFormat (arial16BoldFont);

        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        WritableSheet sheet1 = workbook.createSheet("Lotes Compositos", 0)

        for(i in 0..100)
            sheet1.setColumnView(i,12)
        sheet1.setColumnView(2,30)
        sheet1.setColumnView(3,30)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="reporte_lotes_compositos.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(0,0, "REPORTE DE LOTES Y COMPOSITOS",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def estado = params.estado.toString()
        def empresa=null

        def recepcionesComplejo=null

//        sheet1.addCell(new Label(0,2, "ELEMENTO: COMPLEJO",formatoInfoReporte))
//        sheet1.addCell(new Label(0,3, "DEPOSITO: ${deposito}",formatoInfoReporte))
//        empresa = Empresa.get(params.empresa.id)

        def fechaInicial = new Date().parse("yyyy-MM-dd HH:mm:ss","${params.fechaInicial_year}-${params.fechaInicial_month}-${params.fechaInicial_day} 00:00:00")
        def fechaFinal = new Date().parse("yyyy-MM-dd HH:mm:ss", "${params.fechaFinal_year}-${params.fechaFinal_month}-${params.fechaFinal_day} 23:59:00")

        def empresaIds = ""

        if (params.empresa == null)
            empresaIds = null
        else {
            if (params.empresa.id.toString().contains('[')){
                def empresaIdsArray = params.empresa.id as ArrayList
                empresaIdsArray.each { e ->
                    empresaIds+="$e,"
                }
                empresaIds=empresaIds.substring(0,empresaIds.length()-1)
            } else
                empresaIds = params.empresa.id
        }

        log.error("empresaIds: ${empresaIds}")
        log.error("estado: $estado")
        log.error("fechaInicial: $fechaInicial")
        log.error("fechaFinal: $fechaFinal")

        def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
        def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)

//        sheet1.addCell(new Label(0,2, "EMPRESA: ${empresa.toString()}",formatoInfoReporte))
        sheet1.addCell(new Label(0,2, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

//        recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetweenAndEmpresa(fechaInicial,fechaFinal,empresa)
        def consulta = "SELECT rec.fecha_de_recepcion,\n" +
                "rec.codigo_lote,\n" +
                "emp.tipo_de_empresa,\n" +
                "emp.nombre_de_empresa,\n" +
                "cli.nombre,\n" +
                "rec.peso_bruto,\n" +
                "rec.nombre_composito\n" +
                "FROM liquidacion_demo.recepcion rec\n" +
                "inner join liquidacion_demo.empresa emp on emp.id=rec.empresa_id\n" +
                "inner join liquidacion_demo.cliente cli on cli.id=rec.cliente_id\n" +
                "where rec.fecha_de_recepcion between '${new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss').format(fechaInicial)}' and '${new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss').format(fechaFinal)}'\n"

        if (empresaIds != null)
            consulta += "and rec.empresa_id not in (${empresaIds})\n"

        if (estado.equals("EN COMPOSITO"))
            consulta += "and rec.nombre_composito<>'-'"
        else
            consulta += "and rec.nombre_composito='-'"
//                "and rec.empresa_id not in (${empresaIds})\n" +
//                "and rec.nombre_composito='-'"
        def sql = new Sql(dataSource)
        def rows = sql.rows(consulta)

        sheet1.addCell(new Label(0,4, "FEC. REC.",formatoEncabezado))
        sheet1.addCell(new Label(1,4, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(2,4, "PROCEDENCIA",formatoEncabezado))
        sheet1.addCell(new Label(3,4, "PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(4, 4, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(5,4, "COMPOSITO",formatoEncabezado))

        def fila = 5

        def totalCantidadDeSacos=0
        def totalPesoBruto=0

        rows.each {
            sheet1.addCell(new DateTime(0,fila, it[0],formatoFecha))
            sheet1.addCell(new Label(1,fila, it[1],formatoDatos))
            sheet1.addCell(new Label(2,fila, "${it[2]} ${it[3]}",formatoDatos))
            sheet1.addCell(new Label(3,fila, it[4],formatoDatos))
            sheet1.addCell(new Number(4,fila, it[5],formatoDatos))
            sheet1.addCell(new Label(5,fila, it[6],formatoDatos))

//            totalCantidadDeSacos+=Double.parseDouble(it.cantidadDeSacos.toString())
            totalPesoBruto+=it[5]

            fila++
        }
        //llenado de totales
        sheet1.addCell(new Number(4,fila, totalPesoBruto,formatoTotales))

        workbook.write();
        workbook.close();
    }
}
