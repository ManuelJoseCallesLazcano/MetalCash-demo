package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import jxl.Cell
import jxl.SheetSettings
import jxl.Workbook
import jxl.format.PageOrientation
import jxl.format.PaperSize
import jxl.format.VerticalAlignment
import jxl.write.*
import org.socymet.liquidacion.*
import org.socymet.proveedor.Empresa
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA','ROLE_REPORTES'])
@Transactional
class LibroRegaliasMinerasController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [libroRegaliasMinerasInstanceList: LibroRegaliasMineras.list(params), libroRegaliasMinerasInstanceTotal: LibroRegaliasMineras.count()]
    }

    def create() {
        [libroRegaliasMinerasInstance: new LibroRegaliasMineras(params)]
    }

    def save() {
        def libroRegaliasMinerasInstance = new LibroRegaliasMineras(params)
        if (!libroRegaliasMinerasInstance.save(flush: true)) {
            render(view: "create", model: [libroRegaliasMinerasInstance: libroRegaliasMinerasInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'libroRegaliasMineras.label', default: 'LibroRegaliasMineras'), libroRegaliasMinerasInstance.id])
        redirect(action: "show", id: libroRegaliasMinerasInstance.id)
    }

    def show(Long id) {
        def libroRegaliasMinerasInstance = LibroRegaliasMineras.get(id)
        if (!libroRegaliasMinerasInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'libroRegaliasMineras.label', default: 'LibroRegaliasMineras'), id])
            redirect(action: "list")
            return
        }

        [libroRegaliasMinerasInstance: libroRegaliasMinerasInstance]
    }

    def edit(Long id) {
        def libroRegaliasMinerasInstance = LibroRegaliasMineras.get(id)
        if (!libroRegaliasMinerasInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'libroRegaliasMineras.label', default: 'LibroRegaliasMineras'), id])
            redirect(action: "list")
            return
        }

        [libroRegaliasMinerasInstance: libroRegaliasMinerasInstance]
    }

    def update(Long id, Long version) {
        def libroRegaliasMinerasInstance = LibroRegaliasMineras.get(id)
        if (!libroRegaliasMinerasInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'libroRegaliasMineras.label', default: 'LibroRegaliasMineras'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (libroRegaliasMinerasInstance.version > version) {
                libroRegaliasMinerasInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'libroRegaliasMineras.label', default: 'LibroRegaliasMineras')] as Object[],
                        "Another user has updated this LibroRegaliasMineras while you were editing")
                render(view: "edit", model: [libroRegaliasMinerasInstance: libroRegaliasMinerasInstance])
                return
            }
        }

        libroRegaliasMinerasInstance.properties = params

        if (!libroRegaliasMinerasInstance.save(flush: true)) {
            render(view: "edit", model: [libroRegaliasMinerasInstance: libroRegaliasMinerasInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'libroRegaliasMineras.label', default: 'LibroRegaliasMineras'), libroRegaliasMinerasInstance.id])
        redirect(action: "show", id: libroRegaliasMinerasInstance.id)
    }

    def delete(Long id) {
        def libroRegaliasMinerasInstance = LibroRegaliasMineras.get(id)
        if (!libroRegaliasMinerasInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'libroRegaliasMineras.label', default: 'LibroRegaliasMineras'), id])
            redirect(action: "list")
            return
        }

        try {
            libroRegaliasMinerasInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'libroRegaliasMineras.label', default: 'LibroRegaliasMineras'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'libroRegaliasMineras.label', default: 'LibroRegaliasMineras'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporteEstano = {
        /*FALTA PONER TITULO DEL ELEMENTO Y DEL PERIODO DEL REPORTE*/

        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Libro de Regalias Mineras", 0)
        for(i in 0..100)
            sheet1.setColumnView(i,10)
        sheet1.setColumnView(2,15)
        sheet1.setColumnView(5,50)

        SheetSettings settings = sheet1.getSettings()
//        settings.setScaleFactor(70)
//        settings.setPaperSize(PaperSize.LEGAL)
//        settings.setOrientation(PageOrientation.LANDSCAPE)
        settings.setTopMargin(0.2)
        settings.setBottomMargin(0.4)
        settings.setLeftMargin(0.6)
        settings.setRightMargin(0.4)
        settings.setHeaderMargin(0)
        settings.setFooterMargin(0)

        WritableFont arial10BoldFont = new WritableFont(WritableFont.TAHOMA, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.TAHOMA, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.TAHOMA, 12, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.TAHOMA, 18, WritableFont.BOLD);
        WritableCellFormat formatoEncabezado = new WritableCellFormat (arial10BoldFont);
        formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)
        formatoEncabezado.setWrap(true)
        formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
        //formatoEncabezado.setAlignment(Alignment.CENTRE)
        WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoDatos.setFont(courier8PlainFont)
        formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)
        WritableCellFormat formatoInfoReporte = new WritableCellFormat (arial14BoldFont);
        WritableCellFormat formatoTitulo = new WritableCellFormat (arial16BoldFont);
        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        WritableCellFormat formatoTotales = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoTotales.setFont(arial10BoldFont)
        formatoTotales.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="libro_regalias_mineras_estano.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(0,0, "LIBRO DE REGALIAS MINERAS",formatoTitulo))
        sheet1.addCell(new Label(0,1, "COMPRAS - CONTROL R. M.",formatoTitulo))

        def fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
        def fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

        sheet1.addCell(new Label(0,3, "PERIODO:"))
        sheet1.addCell(new Label(1,3, "${new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)} AL ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)}"))

        sheet1.addCell(new Label(0,6, "Fecha",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "Numero Lote/liq",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "Municipio",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "Codigo",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "No. de N.I.M.",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "Razon Social Vendedor",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "Mineral y/o metal",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "Ley Mineral %",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "Peso Bruto",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "Peso Neto",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "Peso Fino Kg",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "Cotizacion Oficial LF",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "Valor Bruto Bs",formatoEncabezado))
        sheet1.addCell(new Label(13,6, "Alicuota %",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "Total R. M.",formatoEncabezado))
        sheet1.addCell(new Label(15,6, "No Acreditable",formatoEncabezado))
        sheet1.addCell(new Label(16,6, "Acreditable",formatoEncabezado))

        sheet1.mergeCells(2,5,3,5)
        sheet1.addCell(new Label(2,5, "Origen del Mineral",formatoEncabezado))
        sheet1.mergeCells(15,5,16,5)
        sheet1.addCell(new Label(15,5, "Consolidacion IUE",formatoEncabezado))

        def empresas = Empresa.list()

        def fila = 7
        //variables acumuladoras
        def totalPesoBruto=0
        def totalKilosNetosHumedos=0
        def totalKilosNetosSecos=0
        def totalPorcentajeEstano=0
        def totalKilosFinosEstano=0
        def totalValorOficialBruto=0
        def totalRegaliaMinera=0
        def totalTotalRetenciones=0

        empresas.each { e ->
            System.err.println("***** ITERANDO SOBRE EMPRESAS: ${e.toString()}")
            def liquidaciones1 = LiquidacionDeEstano.findAllByFechaDeLiquidacionGreaterThanEqualsAndFechaDeLiquidacionLessThanEquals(fechaInicial,fechaFinal)

            def liquidaciones = new ArrayList<LiquidacionDeEstano>()
            liquidaciones1.each {
                if(it.recepcionDeEstano.empresa.id==e.id){
                    liquidaciones.add(it)
                }
            }

            if(liquidaciones.size()>0){
                totalPesoBruto=0
                totalKilosNetosHumedos=0
                totalKilosNetosSecos=0
                totalPorcentajeEstano=0
                totalKilosFinosEstano=0
                totalValorOficialBruto=0
                totalRegaliaMinera=0
                totalTotalRetenciones=0

                Cell cellAux=null

                liquidaciones.each {
                    sheet1.addCell(new DateTime(0,fila, it.fechaDeLiquidacion,formatoFecha))
                    sheet1.addCell(new Label(1,fila, "",formatoDatos))
                    sheet1.addCell(new Label(2,fila, it.recepcionDeEstano.empresa.municipio,formatoDatos))
                    sheet1.addCell(new Label(3,fila, "",formatoDatos))
                    sheet1.addCell(new Label(4,fila, it.recepcionDeEstano.empresa.nim,formatoDatos))
                    sheet1.addCell(new Label(5,fila, it.recepcionDeEstano.empresa.toString(),formatoDatos))
                    sheet1.addCell(new Label(6,fila, "Estaño",formatoDatos))

                    cellAux = sheet1.getCell(7,fila)
                    totalPorcentajeEstano = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.porcentajeEstano
                    sheet1.addCell(new Number(7,fila, totalPorcentajeEstano,formatoDatos))

                    cellAux = sheet1.getCell(8,fila)
                    totalPesoBruto = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.pesoBruto
                    sheet1.addCell(new Number(8,fila, totalPesoBruto,formatoDatos))

                    cellAux = sheet1.getCell(9,fila)
                    totalKilosNetosSecos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosNetosSecos
                    sheet1.addCell(new Number(9,fila, totalKilosNetosSecos,formatoDatos))

                    cellAux = sheet1.getCell(10,fila)
                    totalKilosFinosEstano = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosFinosEstano
                    sheet1.addCell(new Number(10,fila, totalKilosFinosEstano,formatoDatos))

                    sheet1.addCell(new Number(11,fila, it.cotizacionQuincenalDeEstano,formatoDatos))

                    cellAux = sheet1.getCell(12,fila)
                    totalValorOficialBruto = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.valorOficialBruto
                    sheet1.addCell(new Number(12,fila, totalValorOficialBruto,formatoDatos))

                    sheet1.addCell(new Number(13,fila, it.alicuotaDeEstano,formatoDatos))

                    cellAux = sheet1.getCell(14,fila)
                    totalRegaliaMinera = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.regaliaMinera
                    sheet1.addCell(new Number(14,fila, totalRegaliaMinera,formatoDatos))

                    sheet1.addCell(new Label(15,fila, "",formatoDatos))

                    sheet1.addCell(new Label(16,fila, "",formatoDatos))
                }

                //ley
                cellAux = sheet1.getCell(9,fila)
                def _totalKilosNetosSecos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

                cellAux = sheet1.getCell(10,fila)
                def _totalKilosFinosEstano = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

                sheet1.addCell(new Number(7,fila, _totalKilosFinosEstano/_totalKilosNetosSecos*100,formatoDatos))

                fila++
            }
        }
        workbook.write();
        workbook.close();
    }

    def crearReporteComplejo = {
        /*FALTA PONER TITULO DEL ELEMENTO Y DEL PERIODO DEL REPORTE*/

        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Libro de Regalias Mineras", 0)
        for(i in 0..100)
            sheet1.setColumnView(i,12)
        sheet1.setColumnView(2,15)
        sheet1.setColumnView(5,40)
        sheet1.setColumnView(6,40)

        SheetSettings settings = sheet1.getSettings()
//        settings.setScaleFactor(70)
//        settings.setPaperSize(PaperSize.LEGAL)
//        settings.setOrientation(PageOrientation.LANDSCAPE)
        settings.setTopMargin(0.2)
        settings.setBottomMargin(0.4)
        settings.setLeftMargin(0.6)
        settings.setRightMargin(0.4)
        settings.setHeaderMargin(0)
        settings.setFooterMargin(0)

        WritableFont arial10BoldFont = new WritableFont(WritableFont.TAHOMA, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.TAHOMA, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.TAHOMA, 12, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.TAHOMA, 18, WritableFont.BOLD);
        WritableCellFormat formatoEncabezado = new WritableCellFormat (arial10BoldFont);
        formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)
        formatoEncabezado.setWrap(true)
        formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
        //formatoEncabezado.setAlignment(Alignment.CENTRE)
        WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoDatos.setFont(courier8PlainFont)
        formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)
        formatoDatos.setVerticalAlignment(VerticalAlignment.CENTRE)
        WritableCellFormat formatoInfoReporte = new WritableCellFormat (arial14BoldFont);
        WritableCellFormat formatoTitulo = new WritableCellFormat (arial16BoldFont);
        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)
        formatoFecha.setVerticalAlignment(VerticalAlignment.CENTRE)

        WritableCellFormat formatoTotales = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoTotales.setFont(arial10BoldFont)
        formatoTotales.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="libro_regalias_mineras_complejo.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(0,0, "LIBRO DE REGALIAS MINERAS",formatoTitulo))
        sheet1.addCell(new Label(0,1, "COMPRAS - CONTROL R. M.",formatoTitulo))

        def fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
        def fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

        sheet1.addCell(new Label(0,3, "PERIODO:"))
        sheet1.addCell(new Label(1,3, "${new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)} AL ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)}"))

//        sheet1.addCell(new Label(0,6, "Fecha",formatoEncabezado))
        sheet1.addCell(new Label(0,6, "Fecha Recep.",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "Numero Lote/liq",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "Municipio",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "Codigo",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "No. de N.I.M.",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "Razon Social Vendedor",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "Vendedor",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "Mineral y/o metal",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "Ley Mineral %",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "Peso Bruto",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "Peso Neto",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "Peso Fino Kg",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "Cotizacion Oficial LF",formatoEncabezado))
        sheet1.addCell(new Label(13,6, "Valor Bruto Bs",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "Total Valor Bruto Bs",formatoEncabezado))
        sheet1.addCell(new Label(15,6, "Alicuota %",formatoEncabezado))
        sheet1.addCell(new Label(16,6, "R. M.",formatoEncabezado))
        sheet1.addCell(new Label(17,6, "Total R. M.",formatoEncabezado))
        sheet1.addCell(new Label(18,6, "No Acreditable",formatoEncabezado))
        sheet1.addCell(new Label(19,6, "Acreditable",formatoEncabezado))

        sheet1.mergeCells(2,5,3,5)
        sheet1.addCell(new Label(2,5, "Origen del Mineral",formatoEncabezado))
        sheet1.mergeCells(18,5,19,5)
        sheet1.addCell(new Label(18,5, "Consolidacion IUE",formatoEncabezado))

        def fila = 7
        //variables acumuladoras
        def totalPesoBruto=0
        def totalKilosNetosHumedos=0
        def totalKilosNetosSecos=0
        def totalPorcentajeZinc=0
        def totalPorcentajePlomo = 0
        def totalPorcentajePlata = 0
        def totalKilosFinosZinc=0
        def totalKilosFinosPlomo=0
        def totalKilosFinosPlata=0
        def totalValorOficialBrutoZinc=0
        def totalValorOficialBrutoPlomo=0
        def totalValorOficialBrutoPlata=0
        def totalValorOficialBruto=0
        def totalRegaliaMinera=0
        def totalTotalRetenciones=0

//        def liquidaciones = LiquidacionDeComplejo.findAllByFechaDeLiquidacionGreaterThanEqualsAndFechaDeLiquidacionLessThanEquals(fechaInicial,fechaFinal,[sort: 'recepcionDeComplejo'])

        def liquidacionesCm = LiquidacionDeComplejo.list([sort: 'lote'])
        def liquidaciones=new ArrayList<LiquidacionDeComplejo>()
        liquidacionesCm.each {
//                if(it.recepcionDeComplejo.fechaDeRecepcion>=fechaInicial && it.recepcionDeComplejo.fechaDeRecepcion<=fechaFinal && it.recepcionDeComplejo.empresa.id==empresa.id)
            if(it.recepcionDeComplejo.fechaDeRecepcion>=fechaInicial && it.recepcionDeComplejo.fechaDeRecepcion<=fechaFinal)
                liquidaciones.add(it)
        }

        if(liquidaciones.size()>0){
            log.error("resultados encontrados: ${liquidaciones.size()}")

            Cell cellAux=null

            liquidaciones.each {
//                sheet1.addCell(new DateTime(0,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new DateTime(0,fila, it.recepcionDeComplejo.fechaDeRecepcion,formatoFecha))
                sheet1.addCell(new Label(0,fila+1, "",formatoFecha))
                sheet1.addCell(new Label(0,fila+2, "",formatoFecha))
                sheet1.mergeCells(0,fila,0,fila+2)

                sheet1.addCell(new Label(1,fila, it.lote,formatoDatos))
                sheet1.addCell(new Label(1,fila+1, "",formatoDatos))
                sheet1.addCell(new Label(1,fila+2, "",formatoDatos))
                sheet1.mergeCells(1,fila,1,fila+2)

                sheet1.addCell(new Label(2,fila, it.recepcionDeComplejo.empresa.municipio,formatoDatos))
                sheet1.addCell(new Label(2,fila+1, "",formatoDatos))
                sheet1.addCell(new Label(2,fila+2, "",formatoDatos))
                sheet1.mergeCells(2,fila,2,fila+2)

                sheet1.addCell(new Label(3,fila, it.recepcionDeComplejo.empresa.codigoMunicipio,formatoDatos))
                sheet1.addCell(new Label(3,fila+1, "",formatoDatos))
                sheet1.addCell(new Label(3,fila+2, "",formatoDatos))
                sheet1.mergeCells(3,fila,3,fila+2)

                sheet1.addCell(new Label(4,fila, it.recepcionDeComplejo.empresa.nim,formatoDatos))
                sheet1.addCell(new Label(4,fila+1, "",formatoDatos))
                sheet1.addCell(new Label(4,fila+2, "",formatoDatos))
                sheet1.mergeCells(4,fila,4,fila+2)

                sheet1.addCell(new Label(5,fila, it.recepcionDeComplejo.empresa.toString(),formatoDatos))
                sheet1.addCell(new Label(5,fila+1, "",formatoDatos))
                sheet1.addCell(new Label(5,fila+2, "",formatoDatos))
                sheet1.mergeCells(5,fila,5,fila+2)

                sheet1.addCell(new Label(6,fila, it.recepcionDeComplejo.cliente.nombre,formatoDatos))
                sheet1.addCell(new Label(6,fila+1, "",formatoDatos))
                sheet1.addCell(new Label(6,fila+2, "",formatoDatos))
                sheet1.mergeCells(6,fila,6,fila+2)

                sheet1.addCell(new Label(7,fila, "Zinc",formatoDatos))
                sheet1.addCell(new Label(7,fila+1, "Plomo",formatoDatos))
                sheet1.addCell(new Label(7,fila+2, "Plata",formatoDatos))

                //zinc
                cellAux = sheet1.getCell(8,fila)
                totalPorcentajeZinc = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.porcentajeZincFinal
                sheet1.addCell(new Number(8,fila, totalPorcentajeZinc,formatoDatos))
                //plomo
                cellAux = sheet1.getCell(8,fila+1)
                totalPorcentajePlomo = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.porcentajePlomoFinal
                sheet1.addCell(new Number(8,fila+1, totalPorcentajePlomo,formatoDatos))
                //plata
                cellAux = sheet1.getCell(8,fila+2)
                totalPorcentajePlata = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.porcentajePlataFinal
                sheet1.addCell(new Number(8,fila+2, totalPorcentajePlata,formatoDatos))

                //zinc
                cellAux = sheet1.getCell(9,fila)
                totalPesoBruto = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.pesoBruto
                sheet1.addCell(new Number(9,fila, totalPesoBruto,formatoDatos))
                //plomo
                cellAux = sheet1.getCell(9,fila+1)
                totalPesoBruto = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.pesoBruto
                sheet1.addCell(new Number(9,fila+1, 0,formatoDatos))
                //plata
                cellAux = sheet1.getCell(9,fila+2)
                totalPesoBruto = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.pesoBruto
                sheet1.addCell(new Number(9,fila+2, 0,formatoDatos))
                sheet1.mergeCells(9,fila,9,fila+2)

                //zinc
                cellAux = sheet1.getCell(10,fila)
                totalKilosNetosSecos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosNetosSecos
                sheet1.addCell(new Number(10,fila, totalKilosNetosSecos,formatoDatos))
                //plomo
                cellAux = sheet1.getCell(10,fila+1)
                totalKilosNetosSecos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosNetosSecos
                sheet1.addCell(new Number(10,fila+1, 0,formatoDatos))
                //plata
                cellAux = sheet1.getCell(10,fila+2)
                totalKilosNetosSecos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosNetosSecos
                sheet1.addCell(new Number(10,fila+2, 0,formatoDatos))
                sheet1.mergeCells(10,fila,10,fila+2)

                //zinc
                cellAux = sheet1.getCell(11,fila)
                totalKilosFinosZinc = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosFinosZinc
                sheet1.addCell(new Number(11,fila, totalKilosFinosZinc,formatoDatos))
                //plomo
                cellAux = sheet1.getCell(11,fila+1)
                totalKilosFinosPlomo = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosFinosPlomo
                sheet1.addCell(new Number(11,fila+1, totalKilosFinosPlomo,formatoDatos))
                //plata
                cellAux = sheet1.getCell(11,fila+2)
                totalKilosFinosPlata = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosFinosPlata
                sheet1.addCell(new Number(11,fila+2, totalKilosFinosPlata,formatoDatos))

                sheet1.addCell(new Number(12,fila, it.cotizacionQuincenalDeZinc,formatoDatos))
                sheet1.addCell(new Number(12,fila+1, it.cotizacionQuincenalDePlomo,formatoDatos))
                sheet1.addCell(new Number(12,fila+2, it.cotizacionQuincenalDePlata,formatoDatos))

                //zinc
                cellAux = sheet1.getCell(13,fila)
                totalValorOficialBrutoZinc = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.valorOficialBrutoDeZincEnBolivianos
                sheet1.addCell(new Number(13,fila, totalValorOficialBrutoZinc,formatoDatos))
                //plomo
                cellAux = sheet1.getCell(13,fila+1)
                totalValorOficialBrutoPlomo = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.valorOficialBrutoDePlomoEnBolivianos
                sheet1.addCell(new Number(13,fila+1, totalValorOficialBrutoPlomo,formatoDatos))
                //plata
                cellAux = sheet1.getCell(13,fila+2)
                totalValorOficialBrutoPlata = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.valorOficialBrutoDePlataEnBolivianos
                sheet1.addCell(new Number(13,fila+2, totalValorOficialBrutoPlata,formatoDatos))

                sheet1.addCell(new Number(14,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(14,fila+1, 0,formatoDatos))
                sheet1.addCell(new Number(14,fila+2, 0,formatoDatos))
                sheet1.mergeCells(14,fila,14,fila+2)

                sheet1.addCell(new Number(15,fila, it.alicuotaDeZinc,formatoDatos))
                sheet1.addCell(new Number(15,fila+1, it.alicuotaDePlomo,formatoDatos))
                sheet1.addCell(new Number(15,fila+2, it.alicuotaDePlata,formatoDatos))

                sheet1.addCell(new Number(16,fila, it.valorOficialBrutoDeZincEnBolivianos*it.alicuotaDeZinc/100,formatoDatos))
                sheet1.addCell(new Number(16,fila+1, it.valorOficialBrutoDePlomoEnBolivianos*it.alicuotaDePlomo/100,formatoDatos))
                sheet1.addCell(new Number(16,fila+2, it.valorOficialBrutoDePlataEnBolivianos*it.alicuotaDePlata/100,formatoDatos))

                cellAux = sheet1.getCell(17,fila)
                totalRegaliaMinera = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.regaliaMinera
                sheet1.addCell(new Number(17,fila, totalRegaliaMinera,formatoDatos))
                sheet1.addCell(new Number(17,fila+1, 0,formatoDatos))
                sheet1.addCell(new Number(17,fila+2, 0,formatoDatos))
                sheet1.mergeCells(17,fila,17,fila+2)

                sheet1.addCell(new Label(18,fila, "",formatoDatos))
                sheet1.addCell(new Label(18,fila+1, "",formatoDatos))
                sheet1.addCell(new Label(18,fila+2, "",formatoDatos))
                sheet1.mergeCells(18,fila,18,fila+2)

                sheet1.addCell(new Label(19,fila, "",formatoDatos))
                sheet1.addCell(new Label(19,fila+1, "",formatoDatos))
                sheet1.addCell(new Label(19,fila+2, "",formatoDatos))
                sheet1.mergeCells(19,fila,19,fila+2)

                fila+=3
            }
        }

        workbook.write();
        workbook.close();
    }

    def valorRetencion(descripcionRetencion, retencionesLiquidacion, numeroRetenciones) {
        for(int i=0;i<numeroRetenciones;i++){
            def retencion = retencionesLiquidacion.get(i)
            if (descripcionRetencion.equals(retencion.descripcion)){
                return retencion.monto
            }
        }
        return 0
    }

    def existeLote(liquidacion,lotesIgnorados) {
        for(int i=0;i<lotesIgnorados.size();i++){
            def loteIgnoradoString = lotesIgnorados.get(i)
            def loteIgnorado=(loteIgnoradoString.isNumber())?Integer.parseInt(loteIgnoradoString):-1
            def lote=Integer.parseInt(liquidacion.lote)
            if (loteIgnorado==lote){
                return true
            }
        }
        return false
    }
}
