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
import org.socymet.cotizaciones.TablaCotizacionPlataController
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class ReporteEscalaPreciosPlataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reporteEscalaPreciosPlataInstanceList: ReporteEscalaPreciosPlata.list(params), reporteEscalaPreciosPlataInstanceTotal: ReporteEscalaPreciosPlata.count()]
    }

    def create() {
        [reporteEscalaPreciosPlataInstance: new ReporteEscalaPreciosPlata(params)]
    }

    def save() {
        def reporteEscalaPreciosPlataInstance = new ReporteEscalaPreciosPlata(params)
        if (!reporteEscalaPreciosPlataInstance.save(flush: true)) {
            render(view: "create", model: [reporteEscalaPreciosPlataInstance: reporteEscalaPreciosPlataInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reporteEscalaPreciosPlata.label', default: 'ReporteEscalaPreciosPlata'), reporteEscalaPreciosPlataInstance.id])
        redirect(action: "show", id: reporteEscalaPreciosPlataInstance.id)
    }

    def show(Long id) {
        def reporteEscalaPreciosPlataInstance = ReporteEscalaPreciosPlata.get(id)
        if (!reporteEscalaPreciosPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteEscalaPreciosPlata.label', default: 'ReporteEscalaPreciosPlata'), id])
            redirect(action: "list")
            return
        }

        [reporteEscalaPreciosPlataInstance: reporteEscalaPreciosPlataInstance]
    }

    def edit(Long id) {
        def reporteEscalaPreciosPlataInstance = ReporteEscalaPreciosPlata.get(id)
        if (!reporteEscalaPreciosPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteEscalaPreciosPlata.label', default: 'ReporteEscalaPreciosPlata'), id])
            redirect(action: "list")
            return
        }

        [reporteEscalaPreciosPlataInstance: reporteEscalaPreciosPlataInstance]
    }

    def update(Long id, Long version) {
        def reporteEscalaPreciosPlataInstance = ReporteEscalaPreciosPlata.get(id)
        if (!reporteEscalaPreciosPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteEscalaPreciosPlata.label', default: 'ReporteEscalaPreciosPlata'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reporteEscalaPreciosPlataInstance.version > version) {
                reporteEscalaPreciosPlataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reporteEscalaPreciosPlata.label', default: 'ReporteEscalaPreciosPlata')] as Object[],
                        "Another user has updated this ReporteEscalaPreciosPlata while you were editing")
                render(view: "edit", model: [reporteEscalaPreciosPlataInstance: reporteEscalaPreciosPlataInstance])
                return
            }
        }

        reporteEscalaPreciosPlataInstance.properties = params

        if (!reporteEscalaPreciosPlataInstance.save(flush: true)) {
            render(view: "edit", model: [reporteEscalaPreciosPlataInstance: reporteEscalaPreciosPlataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reporteEscalaPreciosPlata.label', default: 'ReporteEscalaPreciosPlata'), reporteEscalaPreciosPlataInstance.id])
        redirect(action: "show", id: reporteEscalaPreciosPlataInstance.id)
    }

    def delete(Long id) {
        def reporteEscalaPreciosPlataInstance = ReporteEscalaPreciosPlata.get(id)
        if (!reporteEscalaPreciosPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteEscalaPreciosPlata.label', default: 'ReporteEscalaPreciosPlata'), id])
            redirect(action: "list")
            return
        }

        try {
            reporteEscalaPreciosPlataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reporteEscalaPreciosPlata.label', default: 'ReporteEscalaPreciosPlata'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reporteEscalaPreciosPlata.label', default: 'ReporteEscalaPreciosPlata'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporteEscalaPreciosPlata = {
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
        response.setHeader('Content-Disposition', 'Attachment;Filename="escala_precios_plata.xls"')

        //VERIFICACION DEL TIPO DE REPORTE
        def fechaCotizacion = new Date().parse("yyyy-MM-dd",""+params.fechaCotizacion_year+"-"+params.fechaCotizacion_month+"-"+params.fechaCotizacion_day)
        def fechaCotizacionFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaCotizacion)
        def cotizacionPlata = ""+params.cotizacionPlata
        def tablaCotizacionPlata = ""+params.tablaCotizacionPlata.id

        if (cotizacionPlata.equals("")) {
            flash.error = "No ha especificado la Cotizacion Diaria de Plata"
            redirect(action: "create")
            return
        }

        if (params.tablaCotizacionPlata.id=="null") {
            flash.error = "No ha seleccionado la Tabla de Cotizacion de Plata"
            redirect(action: "create")
            return
        }

        WritableSheet sheet = workbook.createSheet("Escala de Precios de Plata", 0)
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
        sheet.mergeCells(0,15,2,15)
        sheet.mergeCells(4,15,6,15)
        sheet.mergeCells(8,15,10,15)

        sheet.mergeCells(0,18,2,18)
        sheet.mergeCells(4,18,6,18)
        sheet.mergeCells(8,18,10,18)
        sheet.mergeCells(0,19,2,19)
        sheet.mergeCells(4,19,6,19)
        sheet.mergeCells(8,19,10,19)
        sheet.mergeCells(0,32,2,32)
        sheet.mergeCells(4,32,6,32)
        sheet.mergeCells(8,32,10,32)

        sheet.addCell(new Label(0,1, "ESCALA DE PRECIOS DE PLATA",formatoEncabezado))
        sheet.addCell(new Label(4,1, "ESCALA DE PRECIOS DE PLATA",formatoEncabezado))
        sheet.addCell(new Label(8,1, "ESCALA DE PRECIOS DE PLATA",formatoEncabezado))
        sheet.addCell(new Label(0,18, "ESCALA DE PRECIOS DE PLATA",formatoEncabezado))
        sheet.addCell(new Label(4,18, "ESCALA DE PRECIOS DE PLATA",formatoEncabezado))
        sheet.addCell(new Label(8,18, "ESCALA DE PRECIOS DE PLATA",formatoEncabezado))

        sheet.addCell(new Label(0,15, "FECHA: ${fechaCotizacionFormateada}",formatoEncabezado))
        sheet.addCell(new Label(4,15, "FECHA: ${fechaCotizacionFormateada}",formatoEncabezado))
        sheet.addCell(new Label(8,15, "FECHA: ${fechaCotizacionFormateada}",formatoEncabezado))
        sheet.addCell(new Label(0,32, "FECHA: ${fechaCotizacionFormateada}",formatoEncabezado))
        sheet.addCell(new Label(4,32, "FECHA: ${fechaCotizacionFormateada}",formatoEncabezado))
        sheet.addCell(new Label(8,32, "FECHA: ${fechaCotizacionFormateada}",formatoEncabezado))

        sheet.addCell(new Label(0,2, "COTIZACION: ${cotizacionPlata}".replace('.',','),formatoEncabezado))
        sheet.addCell(new Label(4,2, "COTIZACION: ${cotizacionPlata}".replace('.',','),formatoEncabezado))
        sheet.addCell(new Label(8,2, "COTIZACION: ${cotizacionPlata}".replace('.',','),formatoEncabezado))
        sheet.addCell(new Label(0,19, "COTIZACION: ${cotizacionPlata}".replace('.',','),formatoEncabezado))
        sheet.addCell(new Label(4,19, "COTIZACION: ${cotizacionPlata}".replace('.',','),formatoEncabezado))
        sheet.addCell(new Label(8,19, "COTIZACION: ${cotizacionPlata}".replace('.',','),formatoEncabezado))

        sheet.addCell(new Label(0,3, "Ag DM",formatoEncabezado))
        sheet.addCell(new Label(1,3, "PRECIO \$us/TON",formatoEncabezado))
        sheet.addCell(new Label(2,3, "PRECIO Bs/qq 50 Kg",formatoEncabezado))

        sheet.addCell(new Label(4,3, "Ag DM",formatoEncabezado))
        sheet.addCell(new Label(5,3, "PRECIO \$us/TON",formatoEncabezado))
        sheet.addCell(new Label(6,3, "PRECIO Bs/qq 50 Kg",formatoEncabezado))

        sheet.addCell(new Label(8,3, "Ag DM",formatoEncabezado))
        sheet.addCell(new Label(9,3, "PRECIO \$us/TON",formatoEncabezado))
        sheet.addCell(new Label(10,3, "PRECIO Bs/qq 50 Kg",formatoEncabezado))

        sheet.addCell(new Label(0,20, "Ag DM",formatoEncabezado))
        sheet.addCell(new Label(1,20, "PRECIO \$us/TON",formatoEncabezado))
        sheet.addCell(new Label(2,20, "PRECIO Bs/qq 50 Kg",formatoEncabezado))

        sheet.addCell(new Label(4,20, "Ag DM",formatoEncabezado))
        sheet.addCell(new Label(5,20, "PRECIO \$us/TON",formatoEncabezado))
        sheet.addCell(new Label(6,20, "PRECIO Bs/qq 50 Kg",formatoEncabezado))

        sheet.addCell(new Label(8,20, "Ag DM",formatoEncabezado))
        sheet.addCell(new Label(9,20, "PRECIO \$us/TON",formatoEncabezado))
        sheet.addCell(new Label(10,20, "PRECIO Bs/qq 50 Kg",formatoEncabezado))

        def cotizacionDeDolar = CotizacionDeDolar.findByActivo(1)
        def tablaCotizacionPlataController = new TablaCotizacionPlataController()
        def leyes = [20,30,40,50,60,70,80,90,100,150,200]
        def fila = 0

        tablaCotizacionPlata = Integer.parseInt(tablaCotizacionPlata)
        cotizacionPlata = Float.parseFloat(cotizacionPlata)

        leyes.each {
            def vpt = tablaCotizacionPlataController.getValorPorToneladaPresupuesto(tablaCotizacionPlata,cotizacionPlata,it)
            //System.out.println("*** CALCULANDO PARA ${it} = ${vpt}")
            sheet.addCell(new Number(0,fila+4, it,formatoDatos))
            sheet.addCell(new Number(4,fila+4, it,formatoDatos))
            sheet.addCell(new Number(8,fila+4, it,formatoDatos))

            sheet.addCell(new Number(1,fila+4, vpt,formatoDatos))
            sheet.addCell(new Number(5,fila+4, vpt,formatoDatos))
            sheet.addCell(new Number(9,fila+4, vpt,formatoDatos))

            sheet.addCell(new Number(2,fila+4, vpt*0.05*cotizacionDeDolar.tipoDeCambioComercial,formatoDatos))
            sheet.addCell(new Number(6,fila+4, vpt*0.05*cotizacionDeDolar.tipoDeCambioComercial,formatoDatos))
            sheet.addCell(new Number(10,fila+4, vpt*0.05*cotizacionDeDolar.tipoDeCambioComercial,formatoDatos))

            sheet.addCell(new Number(0,fila+21, it,formatoDatos))
            sheet.addCell(new Number(4,fila+21, it,formatoDatos))
            sheet.addCell(new Number(8,fila+21, it,formatoDatos))

            sheet.addCell(new Number(1,fila+21, vpt,formatoDatos))
            sheet.addCell(new Number(5,fila+21, vpt,formatoDatos))
            sheet.addCell(new Number(9,fila+21, vpt,formatoDatos))

            sheet.addCell(new Number(2,fila+21, vpt*0.05*cotizacionDeDolar.tipoDeCambioComercial,formatoDatos))
            sheet.addCell(new Number(6,fila+21, vpt*0.05*cotizacionDeDolar.tipoDeCambioComercial,formatoDatos))
            sheet.addCell(new Number(10,fila+21, vpt*0.05*cotizacionDeDolar.tipoDeCambioComercial,formatoDatos))

            fila++
        }

        workbook.write();
        workbook.close();
    }
}
