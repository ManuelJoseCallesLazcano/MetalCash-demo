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
import org.socymet.cotizaciones.CotizacionDeDolar
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeOro
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
class ReportePlanillaTransporteOroController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ReportePlanillaTransporteOro.list(params), model:[reportePlanillaTransporteOroInstanceCount: ReportePlanillaTransporteOro.count()]
    }

    def show(ReportePlanillaTransporteOro reportePlanillaTransporteOroInstance) {
        respond reportePlanillaTransporteOroInstance
    }

    def create() {
        respond new ReportePlanillaTransporteOro(params)
    }

    @Transactional
    def save(ReportePlanillaTransporteOro reportePlanillaTransporteOroInstance) {
        if (reportePlanillaTransporteOroInstance == null) {
            notFound()
            return
        }

        if (reportePlanillaTransporteOroInstance.hasErrors()) {
            respond reportePlanillaTransporteOroInstance.errors, view:'create'
            return
        }

        reportePlanillaTransporteOroInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'reportePlanillaTransporteOro.label', default: 'ReportePlanillaTransporteOro'), reportePlanillaTransporteOroInstance.id])
                redirect reportePlanillaTransporteOroInstance
            }
            '*' { respond reportePlanillaTransporteOroInstance, [status: CREATED] }
        }
    }

    def edit(ReportePlanillaTransporteOro reportePlanillaTransporteOroInstance) {
        respond reportePlanillaTransporteOroInstance
    }

    @Transactional
    def update(ReportePlanillaTransporteOro reportePlanillaTransporteOroInstance) {
        if (reportePlanillaTransporteOroInstance == null) {
            notFound()
            return
        }

        if (reportePlanillaTransporteOroInstance.hasErrors()) {
            respond reportePlanillaTransporteOroInstance.errors, view:'edit'
            return
        }

        reportePlanillaTransporteOroInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ReportePlanillaTransporteOro.label', default: 'ReportePlanillaTransporteOro'), reportePlanillaTransporteOroInstance.id])
                redirect reportePlanillaTransporteOroInstance
            }
            '*'{ respond reportePlanillaTransporteOroInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(ReportePlanillaTransporteOro reportePlanillaTransporteOroInstance) {

        if (reportePlanillaTransporteOroInstance == null) {
            notFound()
            return
        }

        reportePlanillaTransporteOroInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ReportePlanillaTransporteOro.label', default: 'ReportePlanillaTransporteOro'), reportePlanillaTransporteOroInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'reportePlanillaTransporteOro.label', default: 'ReportePlanillaTransporteOro'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def crearReporte = {
        //chain(controller:'jasper',action:'index',model:[data:null],params:params)

        chain(controller:'jasper',action:'index',params:params)
    }

    def crearReporteOro = {
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

        WritableSheet sheet1 = workbook.createSheet("Reporte de Lotes Recepcionados", 0)

        for(i in 0..100)
            sheet1.setColumnView(i,12)
        sheet1.setColumnView(2,30)
        sheet1.setColumnView(3,30)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="reporte_planilla_transporte_lamas.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(2,0, "PLANILLA DE TRANSPORTE DE LAMAS",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def deposito = Deposito.get(params.deposito.id)
        def tipoCambio = params.tipoCambio.toBigDecimal()
        def estado = params.estado.toString()
        def tipoReporte = ""+params.tipoReporte
        def empresa=null
        def fechaInicial=null
        def fechaFinal=null
        def loteInicial=0
        def loteFinal=0

        def recepcionesOro=null

        sheet1.addCell(new Label(0,2, "ELEMENTO: LAMAS",formatoInfoReporte))
        sheet1.addCell(new Label(0,3, "DEPOSITO: ${deposito}",formatoInfoReporte))

        if (tipoReporte.equals("fechas")){
            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(0,4, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            if (estado.equals("Todos"))
                recepcionesOro = RecepcionDeOro.findAllByFechaDeRecepcionBetweenAndDeposito(fechaInicial,fechaFinal,deposito)
            else
                recepcionesOro = RecepcionDeOro.findAllByFechaDeRecepcionBetweenAndEstadoDelLoteAndDeposito(fechaInicial,fechaFinal,estado,deposito)
        }
        if (tipoReporte.equals("fechasEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(0,5, "EMPRESA: ${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(0,4, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            if (estado.equals("Todos"))
                recepcionesOro = RecepcionDeOro.findAllByFechaDeRecepcionBetweenAndEmpresaAndDeposito(fechaInicial,fechaFinal,empresa,deposito)
            else
                recepcionesOro = RecepcionDeOro.findAllByFechaDeRecepcionBetweenAndEmpresaAndEstadoDelLoteAndDeposito(fechaInicial,fechaFinal,empresa,estado,deposito)
        }
        if (tipoReporte.equals("lotes")){
            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            if (estado.equals("Todos"))
                recepcionesOro = RecepcionDeOro.findAllByLoteOroBetweenAndDeposito(loteInicial,loteFinal,deposito)
            else
                recepcionesOro = RecepcionDeOro.findAllByLoteOroBetweenAndEstadoDelLoteAndDeposito(loteInicial,loteFinal,estado,deposito)

            sheet1.addCell(new Label(0,4, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))
        }
        if (tipoReporte.equals("lotesEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            if (estado.equals("Todos"))
                recepcionesOro = RecepcionDeOro.findAllByLoteOroBetweenAndEmpresaAndDeposito(loteInicial,loteFinal,empresa,deposito)
            else
                recepcionesOro = RecepcionDeOro.findAllByLoteOroBetweenAndEmpresaAndEstadoDelLoteAndDeposito(loteInicial,loteFinal,empresa,estado,deposito)

            sheet1.addCell(new Label(0,5, "EMPRESA: ${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(0,4, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))
        }

        sheet1.addCell(new Label(0,7, "FEC. REC.",formatoEncabezado))
        sheet1.addCell(new Label(1,7, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(2,7, "PROCEDENCIA",formatoEncabezado))
        sheet1.addCell(new Label(3,7, "PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(4,7, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(5,7, "COSTO TRANSPORTE",formatoEncabezado))

        def fila = 8

        def totalPesoBruto=0
        def costoTransporte=0
        def totalCostoTransporte=0
//        def tipoCambio=CotizacionDeDolar.findByActivo(1)

        if(recepcionesOro){
            recepcionesOro.each {
//                costoTransporte=tipoCambio.tipoDeCambioComercial*it.pesoBruto*it.empresa.costoTransporteOroTonelada/1000.0
                costoTransporte=tipoCambio*it.pesoBruto*it.empresa.costoTransporteOroTonelada/1000.0

                sheet1.addCell(new DateTime(0,fila, it.fechaDeRecepcion,formatoFecha))
                sheet1.addCell(new Label(1,fila, it.toString(),formatoDatos))
                sheet1.addCell(new Label(2,fila, it.empresa.toString(),formatoDatos))
                sheet1.addCell(new Label(3,fila, it.cliente.nombre,formatoDatos))
                sheet1.addCell(new Number(4,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(5,fila, costoTransporte,formatoDatos))

                totalPesoBruto+=it.pesoBruto
                totalCostoTransporte+=costoTransporte

                fila++
            }
            //llenado de totales
            sheet1.addCell(new Number(4,fila, totalPesoBruto,formatoTotales))
            sheet1.addCell(new Number(5,fila, totalCostoTransporte,formatoTotales))
        }

        workbook.write();
        workbook.close();
    }
}
