package org.socymet.org.socymet.reportes

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
import org.socymet.calidad.ControlCalidadComplejo
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA','ROLE_REPORTES'])
class ReporteLotesAnalisisController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ReporteLotesAnalisis.list(params), model:[reporteLotesAnalisisInstanceCount: ReporteLotesAnalisis.count()]
    }

    def show(ReporteLotesAnalisis reporteLotesAnalisisInstance) {
        respond reporteLotesAnalisisInstance
    }

    def create() {
        respond new ReporteLotesAnalisis(params)
    }

    @Transactional
    def save(ReporteLotesAnalisis reporteLotesAnalisisInstance) {
        if (reporteLotesAnalisisInstance == null) {
            notFound()
            return
        }

        if (reporteLotesAnalisisInstance.hasErrors()) {
            respond reporteLotesAnalisisInstance.errors, view:'create'
            return
        }

        reporteLotesAnalisisInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'reporteLotesAnalisis.label', default: 'ReporteLotesAnalisis'), reporteLotesAnalisisInstance.id])
                redirect reporteLotesAnalisisInstance
            }
            '*' { respond reporteLotesAnalisisInstance, [status: CREATED] }
        }
    }

    def edit(ReporteLotesAnalisis reporteLotesAnalisisInstance) {
        respond reporteLotesAnalisisInstance
    }

    @Transactional
    def update(ReporteLotesAnalisis reporteLotesAnalisisInstance) {
        if (reporteLotesAnalisisInstance == null) {
            notFound()
            return
        }

        if (reporteLotesAnalisisInstance.hasErrors()) {
            respond reporteLotesAnalisisInstance.errors, view:'edit'
            return
        }

        reporteLotesAnalisisInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ReporteLotesAnalisis.label', default: 'ReporteLotesAnalisis'), reporteLotesAnalisisInstance.id])
                redirect reporteLotesAnalisisInstance
            }
            '*'{ respond reporteLotesAnalisisInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(ReporteLotesAnalisis reporteLotesAnalisisInstance) {

        if (reporteLotesAnalisisInstance == null) {
            notFound()
            return
        }

        reporteLotesAnalisisInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ReporteLotesAnalisis.label', default: 'ReporteLotesAnalisis'), reporteLotesAnalisisInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteLotesAnalisis.label', default: 'ReporteLotesAnalisis'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def crearReporte = {
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

        WritableSheet sheet1 = workbook.createSheet("Analisis", 0)

        for(i in 0..100)
            sheet1.setColumnView(i,12)
        sheet1.setColumnView(2,30)
        sheet1.setColumnView(3,30)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="reporte_lotes_analisis.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(0,0, "REPORTE DE ANALISIS DE LOTES",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def empresa = params.empresa.id != 'null' ? Empresa.get(params.empresa.id) : null
        def fechaInicial = new Date().parse("yyyy-MM-dd HH:mm:ss","${params.fechaInicial_year}-${params.fechaInicial_month}-${params.fechaInicial_day} 00:00:00")
        def fechaFinal = new Date().parse("yyyy-MM-dd HH:mm:ss", "${params.fechaFinal_year}-${params.fechaFinal_month}-${params.fechaFinal_day} 23:59:00")

        def recepcionesComplejo=null

        if (empresa) {
            recepcionesComplejo = RecepcionDeComplejo.findAllByEmpresaAndFechaDeRecepcionBetweenAndEstadoAnalisis(empresa, fechaInicial, fechaFinal, "CON ANALISIS")
            sheet1.addCell(new Label(0,2, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(2,2, empresa.toString(),formatoInfoReporte))
        } else
            recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetweenAndEstadoAnalisis(fechaInicial, fechaFinal, "CON ANALISIS")

        def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
        def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
        sheet1.addCell(new Label(0,3, "ENTRE FECHAS:",formatoInfoReporte))
        sheet1.addCell(new Label(2,3, "${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

        sheet1.addCell(new Label(0,5, "FEC. REC.",formatoEncabezado))
        sheet1.addCell(new Label(1,5, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(2,5, "PROCEDENCIA",formatoEncabezado))
        sheet1.addCell(new Label(3,5, "PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(4,5, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(5,5, "FEC. ANALISIS",formatoEncabezado))
        sheet1.addCell(new Label(6,5, "Zn %",formatoEncabezado))
        sheet1.addCell(new Label(7,5, "Pb %",formatoEncabezado))
        sheet1.addCell(new Label(8,5, "Ag DM",formatoEncabezado))
        sheet1.addCell(new Label(9,5, "H2O %",formatoEncabezado))

        def fila = 6

        def analisis
        def totalCantidadDeSacos=0
        def totalPesoBruto=0
        def totalAnticipo=0

        if(recepcionesComplejo){
            recepcionesComplejo.each {
                analisis = ControlCalidadComplejo.findByRecepcionDeComplejo(it)

                sheet1.addCell(new DateTime(0,fila, it.fechaDeRecepcion,formatoFecha))
                sheet1.addCell(new Label(1,fila, it.toString(),formatoDatos))
                sheet1.addCell(new Label(2,fila, it.empresa.toString(),formatoDatos))
                sheet1.addCell(new Label(3,fila, it.cliente.nombre,formatoDatos))
                sheet1.addCell(new Number(4,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new DateTime(5,fila, analisis.fechaAnalisis,formatoFecha))
                sheet1.addCell(new Number(6,fila, analisis.porcentajeZincPromexbol,formatoDatos))
                sheet1.addCell(new Number(7,fila, analisis.porcentajePlomoPromexbol,formatoDatos))
                sheet1.addCell(new Number(8,fila, analisis.porcentajePlataPromexbol,formatoDatos))
                sheet1.addCell(new Number(9,fila, analisis.porcentajeHumedadPromexbol,formatoDatos))

                totalPesoBruto+=it.pesoBruto

                fila++
            }
            //llenado de totales
//            sheet1.addCell(new Number(4,fila, totalCantidadDeSacos,formatoTotales))
//            sheet1.addCell(new Number(5,fila, totalPesoBruto,formatoTotales))
//            sheet1.addCell(new Number(6,fila, totalAnticipo,formatoTotales))
        }

        workbook.write();
        workbook.close();
    }
}
