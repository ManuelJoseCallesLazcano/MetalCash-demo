package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import jxl.SheetSettings
import jxl.Workbook
import jxl.format.Alignment
import jxl.format.PageOrientation
import jxl.format.PaperSize
import jxl.format.VerticalAlignment
import jxl.write.*
import org.socymet.cotizaciones.CotizacionDeDolar
import org.socymet.cotizaciones.TablaCotizacionEstanoController
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class ReporteEscalaPreciosEstanoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reporteEscalaPreciosEstanoInstanceList: ReporteEscalaPreciosEstano.list(params), reporteEscalaPreciosEstanoInstanceTotal: ReporteEscalaPreciosEstano.count()]
    }

    def create() {
        [reporteEscalaPreciosEstanoInstance: new ReporteEscalaPreciosEstano(params)]
    }

    def save() {
        def reporteEscalaPreciosEstanoInstance = new ReporteEscalaPreciosEstano(params)
        if (!reporteEscalaPreciosEstanoInstance.save(flush: true)) {
            render(view: "create", model: [reporteEscalaPreciosEstanoInstance: reporteEscalaPreciosEstanoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reporteEscalaPreciosEstano.label', default: 'ReporteEscalaPreciosEstano'), reporteEscalaPreciosEstanoInstance.id])
        redirect(action: "show", id: reporteEscalaPreciosEstanoInstance.id)
    }

    def show(Long id) {
        def reporteEscalaPreciosEstanoInstance = ReporteEscalaPreciosEstano.get(id)
        if (!reporteEscalaPreciosEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteEscalaPreciosEstano.label', default: 'ReporteEscalaPreciosEstano'), id])
            redirect(action: "list")
            return
        }

        [reporteEscalaPreciosEstanoInstance: reporteEscalaPreciosEstanoInstance]
    }

    def edit(Long id) {
        def reporteEscalaPreciosEstanoInstance = ReporteEscalaPreciosEstano.get(id)
        if (!reporteEscalaPreciosEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteEscalaPreciosEstano.label', default: 'ReporteEscalaPreciosEstano'), id])
            redirect(action: "list")
            return
        }

        [reporteEscalaPreciosEstanoInstance: reporteEscalaPreciosEstanoInstance]
    }

    def update(Long id, Long version) {
        def reporteEscalaPreciosEstanoInstance = ReporteEscalaPreciosEstano.get(id)
        if (!reporteEscalaPreciosEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteEscalaPreciosEstano.label', default: 'ReporteEscalaPreciosEstano'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reporteEscalaPreciosEstanoInstance.version > version) {
                reporteEscalaPreciosEstanoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reporteEscalaPreciosEstano.label', default: 'ReporteEscalaPreciosEstano')] as Object[],
                        "Another user has updated this ReporteEscalaPreciosEstano while you were editing")
                render(view: "edit", model: [reporteEscalaPreciosEstanoInstance: reporteEscalaPreciosEstanoInstance])
                return
            }
        }

        reporteEscalaPreciosEstanoInstance.properties = params

        if (!reporteEscalaPreciosEstanoInstance.save(flush: true)) {
            render(view: "edit", model: [reporteEscalaPreciosEstanoInstance: reporteEscalaPreciosEstanoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reporteEscalaPreciosEstano.label', default: 'ReporteEscalaPreciosEstano'), reporteEscalaPreciosEstanoInstance.id])
        redirect(action: "show", id: reporteEscalaPreciosEstanoInstance.id)
    }

    def delete(Long id) {
        def reporteEscalaPreciosEstanoInstance = ReporteEscalaPreciosEstano.get(id)
        if (!reporteEscalaPreciosEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteEscalaPreciosEstano.label', default: 'ReporteEscalaPreciosEstano'), id])
            redirect(action: "list")
            return
        }

        try {
            reporteEscalaPreciosEstanoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reporteEscalaPreciosEstano.label', default: 'ReporteEscalaPreciosEstano'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reporteEscalaPreciosEstano.label', default: 'ReporteEscalaPreciosEstano'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporteEscalaPreciosEstano = {
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 10, WritableFont.NO_BOLD);

        WritableCellFormat formatoEncabezado = new WritableCellFormat (courier8PlainFont);
        formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)
        formatoEncabezado.setWrap(true)
        formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
        formatoEncabezado.setAlignment(Alignment.CENTRE)

        WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoDatos.setFont(courier8PlainFont)
        formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="escala_precios_estano.xls"')

        //VERIFICACION DEL TIPO DE REPORTE
        def fechaCotizacion = new Date().parse("yyyy-MM-dd",""+params.fechaCotizacion_year+"-"+params.fechaCotizacion_month+"-"+params.fechaCotizacion_day)
        def fechaCotizacionFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaCotizacion)
        def cotizacionEstano = ""+params.cotizacionEstano
        def tablaCotizacionEstano = ""+params.tablaCotizacionEstano.id

        if (cotizacionEstano.equals("")) {
            flash.error = "No ha especificado la Cotizacion Diaria de Estano"
            redirect(action: "create")
            return
        }

        if (params.tablaCotizacionEstano.id=="null") {
            flash.error = "No ha seleccionado la Tabla de Cotizacion de Estano"
            redirect(action: "create")
            return
        }

        WritableSheet sheet = workbook.createSheet("Escala de Precios de Estaño", 0)
        SheetSettings settings = sheet.getSettings()
        //settings.setScaleFactor(70)
        settings.setPaperSize(PaperSize.LETTER)
        settings.setOrientation(PageOrientation.LANDSCAPE)
        settings.setTopMargin(0.2)
        settings.setBottomMargin(0.4)
        settings.setLeftMargin(0.6)
        settings.setRightMargin(0.4)
        settings.setHeaderMargin(0)
        settings.setFooterMargin(0)
        settings.setPassword("5727041")
        settings.setProtected(true)
        //DEFINIENDO ANCHO DE COLUMNAS
        for(i in 0..50)
            sheet.setColumnView(i,14)
        sheet.setColumnView(0,8)
        sheet.setColumnView(4,8)
        sheet.setColumnView(8,8)
        sheet.setColumnView(3,5)
        sheet.setColumnView(7,5)
        sheet.setRowView(3,550)
        sheet.setRowView(20,550)
        //DEFINIENDO LAS CELDAS A FUSIONAR
        sheet.mergeCells(0,1,2,1)
        sheet.mergeCells(4,1,6,1)
        sheet.mergeCells(8,1,10,1)
        sheet.mergeCells(0,2,2,2)
        sheet.mergeCells(4,2,6,2)
        sheet.mergeCells(8,2,10,2)
        sheet.mergeCells(0,14,2,14)
        sheet.mergeCells(4,14,6,14)
        sheet.mergeCells(8,14,10,14)
        
        sheet.mergeCells(0,18,2,18)
        sheet.mergeCells(4,18,6,18)
        sheet.mergeCells(8,18,10,18)
        sheet.mergeCells(0,19,2,19)
        sheet.mergeCells(4,19,6,19)
        sheet.mergeCells(8,19,10,19)
        sheet.mergeCells(0,31,2,31)
        sheet.mergeCells(4,31,6,31)
        sheet.mergeCells(8,31,10,31)

        sheet.addCell(new Label(0,1, "ESCALA DE PRECIOS DE ESTAÑO",formatoEncabezado))
        sheet.addCell(new Label(4,1, "ESCALA DE PRECIOS DE ESTAÑO",formatoEncabezado))
        sheet.addCell(new Label(8,1, "ESCALA DE PRECIOS DE ESTAÑO",formatoEncabezado))
        sheet.addCell(new Label(0,18, "ESCALA DE PRECIOS DE ESTAÑO",formatoEncabezado))
        sheet.addCell(new Label(4,18, "ESCALA DE PRECIOS DE ESTAÑO",formatoEncabezado))
        sheet.addCell(new Label(8,18, "ESCALA DE PRECIOS DE ESTAÑO",formatoEncabezado))

        sheet.addCell(new Label(0,14, "FECHA: ${fechaCotizacionFormateada}",formatoEncabezado))
        sheet.addCell(new Label(4,14, "FECHA: ${fechaCotizacionFormateada}",formatoEncabezado))
        sheet.addCell(new Label(8,14, "FECHA: ${fechaCotizacionFormateada}",formatoEncabezado))
        sheet.addCell(new Label(0,31, "FECHA: ${fechaCotizacionFormateada}",formatoEncabezado))
        sheet.addCell(new Label(4,31, "FECHA: ${fechaCotizacionFormateada}",formatoEncabezado))
        sheet.addCell(new Label(8,31, "FECHA: ${fechaCotizacionFormateada}",formatoEncabezado))
        
        sheet.addCell(new Label(0,2, "COTIZACION: ${cotizacionEstano}".replace('.',','),formatoEncabezado))
        sheet.addCell(new Label(4,2, "COTIZACION: ${cotizacionEstano}".replace('.',','),formatoEncabezado))
        sheet.addCell(new Label(8,2, "COTIZACION: ${cotizacionEstano}".replace('.',','),formatoEncabezado))
        sheet.addCell(new Label(0,19, "COTIZACION: ${cotizacionEstano}".replace('.',','),formatoEncabezado))
        sheet.addCell(new Label(4,19, "COTIZACION: ${cotizacionEstano}".replace('.',','),formatoEncabezado))
        sheet.addCell(new Label(8,19, "COTIZACION: ${cotizacionEstano}".replace('.',','),formatoEncabezado))

        sheet.addCell(new Label(0,3, "Sn%",formatoEncabezado))
        sheet.addCell(new Label(1,3, "PRECIO \$us/TON",formatoEncabezado))
        sheet.addCell(new Label(2,3, "PRECIO Bs/qq 46 Kg",formatoEncabezado))

        sheet.addCell(new Label(4,3, "Sn%",formatoEncabezado))
        sheet.addCell(new Label(5,3, "PRECIO \$us/TON",formatoEncabezado))
        sheet.addCell(new Label(6,3, "PRECIO Bs/qq 46 Kg",formatoEncabezado))

        sheet.addCell(new Label(8,3, "Sn%",formatoEncabezado))
        sheet.addCell(new Label(9,3, "PRECIO \$us/TON",formatoEncabezado))
        sheet.addCell(new Label(10,3, "PRECIO Bs/qq 46 Kg",formatoEncabezado))

        sheet.addCell(new Label(0,20, "Sn%",formatoEncabezado))
        sheet.addCell(new Label(1,20, "PRECIO \$us/TON",formatoEncabezado))
        sheet.addCell(new Label(2,20, "PRECIO Bs/qq 46 Kg",formatoEncabezado))

        sheet.addCell(new Label(4,20, "Sn%",formatoEncabezado))
        sheet.addCell(new Label(5,20, "PRECIO \$us/TON",formatoEncabezado))
        sheet.addCell(new Label(6,20, "PRECIO Bs/qq 46 Kg",formatoEncabezado))

        sheet.addCell(new Label(8,20, "Sn%",formatoEncabezado))
        sheet.addCell(new Label(9,20, "PRECIO \$us/TON",formatoEncabezado))
        sheet.addCell(new Label(10,20, "PRECIO Bs/qq 46 Kg",formatoEncabezado))

        def cotizacionDeDolar = CotizacionDeDolar.findByActivo(1)
        def tablaCotizacionEstanoController = new TablaCotizacionEstanoController()
        def leyes = [10,15,20,25,30,35,40,50,60,70]
        def fila = 0

        tablaCotizacionEstano = Integer.parseInt(tablaCotizacionEstano)
        cotizacionEstano = Float.parseFloat(cotizacionEstano)

        leyes.each {
            def vpt = tablaCotizacionEstanoController.getValorPorToneladaPresupuesto(tablaCotizacionEstano,cotizacionEstano,it)
            //System.out.println("*** CALCULANDO PARA ${it} = ${vpt}")
            sheet.addCell(new Number(0,fila+4, it,formatoDatos))
            sheet.addCell(new Number(4,fila+4, it,formatoDatos))
            sheet.addCell(new Number(8,fila+4, it,formatoDatos))

            sheet.addCell(new Number(1,fila+4, vpt,formatoDatos))
            sheet.addCell(new Number(5,fila+4, vpt,formatoDatos))
            sheet.addCell(new Number(9,fila+4, vpt,formatoDatos))

            sheet.addCell(new Number(2,fila+4, vpt*0.046*cotizacionDeDolar.tipoDeCambioComercial,formatoDatos))
            sheet.addCell(new Number(6,fila+4, vpt*0.046*cotizacionDeDolar.tipoDeCambioComercial,formatoDatos))
            sheet.addCell(new Number(10,fila+4, vpt*0.046*cotizacionDeDolar.tipoDeCambioComercial,formatoDatos))

            sheet.addCell(new Number(0,fila+21, it,formatoDatos))
            sheet.addCell(new Number(4,fila+21, it,formatoDatos))
            sheet.addCell(new Number(8,fila+21, it,formatoDatos))

            sheet.addCell(new Number(1,fila+21, vpt,formatoDatos))
            sheet.addCell(new Number(5,fila+21, vpt,formatoDatos))
            sheet.addCell(new Number(9,fila+21, vpt,formatoDatos))

            sheet.addCell(new Number(2,fila+21, vpt*0.046*cotizacionDeDolar.tipoDeCambioComercial,formatoDatos))
            sheet.addCell(new Number(6,fila+21, vpt*0.046*cotizacionDeDolar.tipoDeCambioComercial,formatoDatos))
            sheet.addCell(new Number(10,fila+21, vpt*0.046*cotizacionDeDolar.tipoDeCambioComercial,formatoDatos))

            fila++
        }
        
        workbook.write();
        workbook.close();
    }
}
