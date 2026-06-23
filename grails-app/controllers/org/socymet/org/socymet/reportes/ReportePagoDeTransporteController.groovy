package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import jxl.SheetSettings
import jxl.Workbook
import jxl.format.Alignment
import jxl.format.PageOrientation
import jxl.format.PaperSize
import jxl.write.*
import org.socymet.cancelacion.DetallePagoTransporte
import org.socymet.cancelacion.EstadoCuentaTransporte
import org.socymet.cancelacion.PagoTransporte
import org.socymet.proveedor.Automovil
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA','ROLE_REPORTES'])
@Transactional
class ReportePagoDeTransporteController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reportePagoDeTransporteInstanceList: ReportePagoDeTransporte.list(params), reportePagoDeTransporteInstanceTotal: ReportePagoDeTransporte.count()]
    }

    def create() {
        [reportePagoDeTransporteInstance: new ReportePagoDeTransporte(params)]
    }

    def save() {
        def reportePagoDeTransporteInstance = new ReportePagoDeTransporte(params)
        if (!reportePagoDeTransporteInstance.save(flush: true)) {
            render(view: "create", model: [reportePagoDeTransporteInstance: reportePagoDeTransporteInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reportePagoDeTransporte.label', default: 'ReportePagoDeTransporte'), reportePagoDeTransporteInstance.id])
        redirect(action: "show", id: reportePagoDeTransporteInstance.id)
    }

    def show(Long id) {
        def reportePagoDeTransporteInstance = ReportePagoDeTransporte.get(id)
        if (!reportePagoDeTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reportePagoDeTransporte.label', default: 'ReportePagoDeTransporte'), id])
            redirect(action: "list")
            return
        }

        [reportePagoDeTransporteInstance: reportePagoDeTransporteInstance]
    }

    def edit(Long id) {
        def reportePagoDeTransporteInstance = ReportePagoDeTransporte.get(id)
        if (!reportePagoDeTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reportePagoDeTransporte.label', default: 'ReportePagoDeTransporte'), id])
            redirect(action: "list")
            return
        }

        [reportePagoDeTransporteInstance: reportePagoDeTransporteInstance]
    }

    def update(Long id, Long version) {
        def reportePagoDeTransporteInstance = ReportePagoDeTransporte.get(id)
        if (!reportePagoDeTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reportePagoDeTransporte.label', default: 'ReportePagoDeTransporte'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reportePagoDeTransporteInstance.version > version) {
                reportePagoDeTransporteInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reportePagoDeTransporte.label', default: 'ReportePagoDeTransporte')] as Object[],
                        "Another user has updated this ReportePagoDeTransporte while you were editing")
                render(view: "edit", model: [reportePagoDeTransporteInstance: reportePagoDeTransporteInstance])
                return
            }
        }

        reportePagoDeTransporteInstance.properties = params

        if (!reportePagoDeTransporteInstance.save(flush: true)) {
            render(view: "edit", model: [reportePagoDeTransporteInstance: reportePagoDeTransporteInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reportePagoDeTransporte.label', default: 'ReportePagoDeTransporte'), reportePagoDeTransporteInstance.id])
        redirect(action: "show", id: reportePagoDeTransporteInstance.id)
    }

    def delete(Long id) {
        def reportePagoDeTransporteInstance = ReportePagoDeTransporte.get(id)
        if (!reportePagoDeTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reportePagoDeTransporte.label', default: 'ReportePagoDeTransporte'), id])
            redirect(action: "list")
            return
        }

        try {
            reportePagoDeTransporteInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reportePagoDeTransporte.label', default: 'ReportePagoDeTransporte'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reportePagoDeTransporte.label', default: 'ReportePagoDeTransporte'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporte = {
//        def tipoReporte = params.tipoReporte
//        def empresa = Empresa.get(params.empresa.id)
        def tipoReporte = params.tipoReporte
        def empresa = (!params.empresa.id.equals("null"))?Empresa.get(params.empresa.id.toLong()):null
        def automovil = (!params.automovil.id.equals("null"))?Automovil.get(params.automovil.id.toString().toLong()):null
        def fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
        def fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)
        def estado = (params.estado.equals("PAGADO"))?"SI":"NO"

        System.out.println("***** PARAMETROS *****")
        System.out.println("params.tipoReporte: ${params.tipoReporte}")
        System.out.println("params.deposito.id: ${params.deposito.id}")
        System.out.println("params.elemento: ${params.elemento} blank?: ${params.elemento.equals("")}")
        System.out.println("params.empresa.id: ${params.empresa.id}")
        System.out.println("params.automovil.id: ${params.automovil.id}")
        System.out.println("fechaInicial: ${fechaInicial}")
        System.out.println("fechaFinal: ${fechaFinal}")
        System.out.println("params.estado: ${params.estado} blank?: ${params.estado.equals("")}")

        def pagosDeTransporte=PagoTransporte.findAllByFechaDePagoGreaterThanEqualsAndFechaDePagoLessThanEquals(fechaInicial,fechaFinal)
        def recepcion
        def recepciones = new ArrayList<RecepcionDeComplejo>()
        pagosDeTransporte.each {
            recepcion=RecepcionDeComplejo.get(it.recepcionId)
            if(recepcion){
                if (tipoReporte.equals("fechas")){
                    recepciones.add(recepcion)
                }

                if (tipoReporte.equals("fechasEmpresa") && recepcion.empresa.equals(empresa)){
                    recepciones.add(recepcion)
                }

                if (tipoReporte.equals("fechasAutomovil") && recepcion.automovil.equals(automovil)){
                    recepciones.add(recepcion)
                }
            }
        }

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

        WritableCellFormat formatoIdentificador = new WritableCellFormat (new NumberFormat("000000"));
        formatoIdentificador.setFont(courier8PlainFont)
        formatoIdentificador.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        WritableCellFormat formatoTotales = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoTotales.setFont(courier8BoldFont)
        formatoTotales.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)

        WritableCellFormat formatoInfoReporte = new WritableCellFormat (arial14BoldFont);
        WritableCellFormat formatoTitulo = new WritableCellFormat (arial16BoldFont);

        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        WritableSheet sheet1 = workbook.createSheet("Pago de Transporte", 0)
        sheet1.setRowView(6,500)
        for(i in 0..100)
            sheet1.setColumnView(i,11)
        sheet1.setColumnView(2,30)
        sheet1.setColumnView(3,30)
        sheet1.setColumnView(4,30)

        SheetSettings settings = sheet1.getSettings()
        settings.setScaleFactor(70)
        settings.setPaperSize(PaperSize.LETTER)
        settings.setOrientation(PageOrientation.LANDSCAPE)
        settings.setTopMargin(0.2)
        settings.setBottomMargin(0.4)
        settings.setLeftMargin(0.6)
        settings.setRightMargin(0.4)
        settings.setHeaderMargin(0)
        settings.setFooterMargin(0)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="reporte_pago_transporte.xls"')

        //titulos
        sheet1.addCell(new Label(0,0, "REPORTE DE PAGO DE TRANSPORTE",formatoTitulo))
//        sheet1.addCell(new Label(0,2, "DEPOSITO: ${(deposito)?deposito:"TODOS"}",formatoInfoReporte))
//        sheet1.addCell(new Label(0,3, "ELEMENTO: ${(!elemento.equals(""))?elemento:"TODOS"}",formatoInfoReporte))
        sheet1.addCell(new Label(0,3, "MINERAL: COMPLEJO",formatoInfoReporte))
        def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
        def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
        sheet1.addCell(new Label(0,4, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

        //nombres de columnas
        sheet1.addCell(new Label(0,6, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "FEC. RECEP.",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "NOMBRE",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "CHOFER",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "AUTOMOVIL",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "MATERIAL",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "COSTO TRANSP.",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "COMPROBANTE DE PAGO No.",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "FECHA DE PAGO",formatoEncabezado))

        if (recepciones){
            def fila=7
            def sumaSacos = 0
            def sumaPesoBruto = 0
            def sumaCostoDeTransporte = 0

            recepciones.each {
                sheet1.addCell(new Label(0,fila, it.toString(),formatoDatos))
                sheet1.addCell(new DateTime(1,fila, it.fechaDeRecepcion,formatoFecha))
                sheet1.addCell(new Label(2,fila, it.empresa.toString(),formatoDatos))
                sheet1.addCell(new Label(3,fila, it.cliente.nombre,formatoDatos))
                sheet1.addCell(new Label(4,fila, it.chofer.nombre,formatoDatos))
                sheet1.addCell(new Label(5,fila, it.automovil.placa,formatoDatos))
                sheet1.addCell(new Label(6,fila, it.tipoDeMaterial,formatoDatos))
                sheet1.addCell(new Number(7,fila, Double.parseDouble(it.cantidadDeSacos),formatoDatos))
                sheet1.addCell(new Number(8,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(9,fila, it.costoDeTransporte,formatoDatos))

                def pagoTransporte = PagoTransporte.findByRecepcionId(it.id)
                sheet1.addCell(new Number(10,fila, pagoTransporte.numeroComprobante,formatoIdentificador))
                sheet1.addCell(new DateTime(11,fila, pagoTransporte.fechaDePago,formatoFecha))

                sumaSacos+=Double.parseDouble(it.cantidadDeSacos)
                sumaPesoBruto+=it.pesoBruto
                sumaCostoDeTransporte+=it.costoDeTransporte
                fila++
            }

            sheet1.addCell(new Number(7,fila, sumaSacos,formatoTotales))
            sheet1.addCell(new Number(8,fila, sumaPesoBruto,formatoTotales))
            sheet1.addCell(new Number(9,fila, sumaCostoDeTransporte,formatoTotales))
        }

        //ESTADOS DE CUENTA
        if (tipoReporte.equals("fechasEmpresa")){
            WritableSheet sheet2 = workbook.createSheet("Estado Cuenta ${empresa.toString()}", 1)
            sheet2.setRowView(6,500)
            for(i in 0..100)
                sheet1.setColumnView(i,11)
            sheet2.setColumnView(1,30)
            sheet2.setColumnView(2,30)

            def estados = EstadoCuentaTransporte.findAllByEmpresa(empresa)

            sheet2.addCell(new Label(1,0, "ESTADO DE CUENTA DE TRANSPORTE",formatoTitulo))
            sheet2.addCell(new Label(0,2, "EMPRESA: ${empresa.toString()}",formatoInfoReporte))
            fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet2.addCell(new Label(0,3, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            sheet2.addCell(new Label(0,6, "FECHA",formatoEncabezado))
            sheet2.addCell(new Label(1,6, "RESPONSABLE",formatoEncabezado))
            sheet2.addCell(new Label(2,6, "DESCRIPCION",formatoEncabezado))
            sheet2.addCell(new Label(3,6, "INGRESO",formatoEncabezado))
            sheet2.addCell(new Label(4,6, "EGRESO",formatoEncabezado))
            sheet2.addCell(new Label(5,6, "SALDO",formatoEncabezado))

            if (estados){
                def fila=7

                estados.each {
                    sheet2.addCell(new DateTime(0,fila, it.fecha,formatoFecha))
                    sheet2.addCell(new Label(1,fila,"${it.nombreResponsable} [${it.ci}]",formatoDatos))
                    sheet2.addCell(new Label(2,fila, it.descripcion.substring(20),formatoDatos))
                    sheet2.addCell(new Number(3,fila, it.ingreso,formatoDatos))
                    sheet2.addCell(new Number(4,fila, it.egreso,formatoDatos))
                    sheet2.addCell(new Number(5,fila, it.saldo,formatoDatos))

                    fila++
                }
            }
        }

        if (tipoReporte.equals("fechasAutomovil")){
            WritableSheet sheet2 = workbook.createSheet("Estado Cuenta ${automovil.toString()}", 1)
            sheet2.setRowView(6,500)
            for(i in 0..100)
                sheet1.setColumnView(i,11)
            sheet2.setColumnView(1,30)
            sheet2.setColumnView(2,30)

            def estados = EstadoCuentaTransporte.findAllByAutomovil(automovil)

            sheet2.addCell(new Label(1,0, "ESTADO DE CUENTA DE TRANSPORTE",formatoTitulo))
            sheet2.addCell(new Label(0,2, "AUTOMOVIL: ${automovil.toString()}",formatoInfoReporte))
            fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet2.addCell(new Label(0,3, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            sheet2.addCell(new Label(0,6, "FECHA",formatoEncabezado))
            sheet2.addCell(new Label(1,6, "RESPONSABLE",formatoEncabezado))
            sheet2.addCell(new Label(2,6, "DESCRIPCION",formatoEncabezado))
            sheet2.addCell(new Label(3,6, "INGRESO",formatoEncabezado))
            sheet2.addCell(new Label(4,6, "EGRESO",formatoEncabezado))
            sheet2.addCell(new Label(5,6, "SALDO",formatoEncabezado))

            if (estados){
                def fila=7

                estados.each {
                    sheet2.addCell(new DateTime(0,fila, it.fecha,formatoFecha))
                    sheet2.addCell(new Label(1,fila,"${it.nombreResponsable} [${it.ci}]",formatoDatos))
                    sheet2.addCell(new Label(2,fila, it.descripcion,formatoDatos))
                    sheet2.addCell(new Number(3,fila, it.ingreso,formatoDatos))
                    sheet2.addCell(new Number(4,fila, it.egreso,formatoDatos))
                    sheet2.addCell(new Number(5,fila, it.saldo,formatoDatos))

                    fila++
                }
            }
        }

        workbook.write();
        workbook.close();
    }
}
