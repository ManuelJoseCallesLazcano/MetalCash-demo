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

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class ReporteCompraGeneralController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reporteCompraGeneralInstanceList: ReporteCompraGeneral.list(params), reporteCompraGeneralInstanceTotal: ReporteCompraGeneral.count()]
    }

    def create() {
        [reporteCompraGeneralInstance: new ReporteCompraGeneral(params)]
    }

    def save() {
        def reporteCompraGeneralInstance = new ReporteCompraGeneral(params)
        if (!reporteCompraGeneralInstance.save(flush: true)) {
            render(view: "create", model: [reporteCompraGeneralInstance: reporteCompraGeneralInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reporteCompraGeneral.label', default: 'ReporteCompraGeneral'), reporteCompraGeneralInstance.id])
        redirect(action: "show", id: reporteCompraGeneralInstance.id)
    }

    def show(Long id) {
        def reporteCompraGeneralInstance = ReporteCompraGeneral.get(id)
        if (!reporteCompraGeneralInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteCompraGeneral.label', default: 'ReporteCompraGeneral'), id])
            redirect(action: "list")
            return
        }

        [reporteCompraGeneralInstance: reporteCompraGeneralInstance]
    }

    def edit(Long id) {
        def reporteCompraGeneralInstance = ReporteCompraGeneral.get(id)
        if (!reporteCompraGeneralInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteCompraGeneral.label', default: 'ReporteCompraGeneral'), id])
            redirect(action: "list")
            return
        }

        [reporteCompraGeneralInstance: reporteCompraGeneralInstance]
    }

    def update(Long id, Long version) {
        def reporteCompraGeneralInstance = ReporteCompraGeneral.get(id)
        if (!reporteCompraGeneralInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteCompraGeneral.label', default: 'ReporteCompraGeneral'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reporteCompraGeneralInstance.version > version) {
                reporteCompraGeneralInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reporteCompraGeneral.label', default: 'ReporteCompraGeneral')] as Object[],
                        "Another user has updated this ReporteCompraGeneral while you were editing")
                render(view: "edit", model: [reporteCompraGeneralInstance: reporteCompraGeneralInstance])
                return
            }
        }

        reporteCompraGeneralInstance.properties = params

        if (!reporteCompraGeneralInstance.save(flush: true)) {
            render(view: "edit", model: [reporteCompraGeneralInstance: reporteCompraGeneralInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reporteCompraGeneral.label', default: 'ReporteCompraGeneral'), reporteCompraGeneralInstance.id])
        redirect(action: "show", id: reporteCompraGeneralInstance.id)
    }

    def delete(Long id) {
        def reporteCompraGeneralInstance = ReporteCompraGeneral.get(id)
        if (!reporteCompraGeneralInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteCompraGeneral.label', default: 'ReporteCompraGeneral'), id])
            redirect(action: "list")
            return
        }

        try {
            reporteCompraGeneralInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reporteCompraGeneral.label', default: 'ReporteCompraGeneral'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reporteCompraGeneral.label', default: 'ReporteCompraGeneral'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporteComplejo = {
        /*FALTA PONER TITULO DEL ELEMENTO Y DEL PERIODO DEL REPORTE*/

        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Compra General de Complejo", 0)
        sheet1.setColumnView(0,50)
        sheet1.setRowView(6,500)
        for(i in 1..100)
            sheet1.setColumnView(i,12)
        SheetSettings settings = sheet1.getSettings()
        settings.setScaleFactor(70)
        settings.setPaperSize(PaperSize.LEGAL)
        settings.setOrientation(PageOrientation.LANDSCAPE)
        settings.setTopMargin(0.2)
        settings.setBottomMargin(0.4)
        settings.setLeftMargin(0.6)
        settings.setRightMargin(0.4)
        settings.setHeaderMargin(0)
        settings.setFooterMargin(0)

        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD);
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
        response.setHeader('Content-Disposition', 'Attachment;Filename="compra_minerales_empresa_complejo.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(3,0, "COMPRA DE MINERALES POR EMPRESA",formatoTitulo))

        def fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
        def fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

        sheet1.addCell(new Label(0,3, "ELEMENTO:"))
        sheet1.addCell(new Label(1,3, "Complejo"))
        sheet1.addCell(new Label(0,4, "PERIODO:"))
        sheet1.addCell(new Label(1,4, "${new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)} AL ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)}"))

        sheet1.addCell(new Label(0,6, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "K. N. H.",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "% H2O",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "K. N. S.",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "LEY %Zn",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "LEY %Pb",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "LEY DM Ag",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "K. F. Zn",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "K. F. Pb",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "K. F. Ag",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "VALOR OF. BRUTO",formatoEncabezado))
        sheet1.addCell(new Label(13,6, "VALOR NETO \$us",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(15,6, "BONO CALIDAD",formatoEncabezado))
        sheet1.addCell(new Label(16,6, "BONO INCENTIVO",formatoEncabezado))
        sheet1.addCell(new Label(17,6, "VALOR DE COMPRA",formatoEncabezado))
        sheet1.addCell(new Label(18,6, "RM",formatoEncabezado))

        /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA RETENCIONES DE LEY*/
//        def liquidacionesTodas = LiquidacionDeComplejo.findAllByFechaDeLiquidacionGreaterThanEqualsAndFechaDeLiquidacionLessThanEquals(fechaInicial,fechaFinal)
        def liquidacionesTodas = LiquidacionDeComplejo.findAllByFechaDeCancelacionGreaterThanEqualsAndFechaDeCancelacionLessThanEquals(fechaInicial,fechaFinal)
        def listaRetencionesDeLey = retencionesComplejoJSON liquidacionesTodas,"DE LEY"
        def columna = 19
        listaRetencionesDeLey.each {
            sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
            columna++
        }
        sheet1.addCell(new Label(columna,6, "TOTAL RET. DE LEY",formatoEncabezado))
        columna++

        /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA OTRAS RETENCIONES*/
        def listaRetencionesOtras = retencionesComplejoJSON liquidacionesTodas,"OTRA"
        listaRetencionesOtras.each {
            sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
            columna++
        }
        sheet1.addCell(new Label(columna,6, "TOTAL OTRAS RET.",formatoEncabezado))
        columna++

        sheet1.addCell(new Label(columna,6, "TOTAL RET.",formatoEncabezado))
        sheet1.addCell(new Label(columna+1,6, "TOTAL PAGADO",formatoEncabezado))
        sheet1.addCell(new Label(columna+2,6, "ANTICIPO/ENTREGA",formatoEncabezado))
        sheet1.addCell(new Label(columna+3,6, "ANTICIPO/F. ENTREGA",formatoEncabezado))
        sheet1.addCell(new Label(columna+4,6, "LIQUIDO PAGABLE",formatoEncabezado))

        def empresas = Empresa.list()

        def fila = 7
        //variables acumuladoras
        def totalCantidadSacos=0
        def totalPesoBruto=0
        def totalKilosNetosHumedos=0
        def totalHumedad=0
        def totalKilosNetosSecos=0
        //def totalPorcentajeComplejo=0
        def totalKilosFinosZinc=0
        def totalKilosFinosPlomo=0
        def totalKilosFinosPlata=0
        def totalValorOficialBruto=0
        def totalValorNeto=0
        def totalValorNetoBolivianos=0
        def totalBonoCalidad=0
        def totalBonoIncentivo=0
        def totalValorDeCompra=0
        def totalRegaliaMinera=0
        def totalTotalRetenciones=0
        def totalTotalPagado=0
        def totalTotalAnticiposContraEntrega=0
        def totalTotalAnticiposContraFuturaEntrega=0
        def totalTotalLiquidoPagable=0

        empresas.each { e ->
            //log.info("***** ITERANDO SOBRE: ${e.toString()}")
            System.err.println("***** ITERANDO SOBRE EMPRESAS: ${e.toString()}")
//            def liquidaciones1 = LiquidacionDeComplejo.findAllByFechaDeLiquidacionGreaterThanEqualsAndFechaDeLiquidacionLessThanEquals(fechaInicial,fechaFinal)
            def liquidaciones1 = LiquidacionDeComplejo.findAllByFechaDeCancelacionGreaterThanEqualsAndFechaDeCancelacionLessThanEquals(fechaInicial,fechaFinal)
            //def liquidaciones1 = LiquidacionDeComplejo.findAllByFechaDeLiquidacionBetween(fechaInicial,fechaFinal)

            def liquidaciones = new ArrayList<LiquidacionDeComplejo>()
            liquidaciones1.each {
                if(it.recepcionDeComplejo.empresa.id==e.id){
                    liquidaciones.add(it)
                }
            }

            if(liquidaciones.size()>0){
                totalCantidadSacos=0
                totalPesoBruto=0
                totalKilosNetosHumedos=0
                totalHumedad=0
                totalKilosNetosSecos=0
                //totalPorcentajeComplejo=0
                totalKilosFinosZinc=0
                totalKilosFinosPlomo=0
                totalKilosFinosPlata=0
                totalValorOficialBruto=0
                totalValorNeto=0
                totalValorNetoBolivianos=0
                totalBonoCalidad=0
                totalBonoIncentivo=0
                totalValorDeCompra=0
                totalRegaliaMinera=0
                totalTotalRetenciones=0
                totalTotalPagado=0
                totalTotalAnticiposContraEntrega=0
                totalTotalAnticiposContraFuturaEntrega=0
                totalTotalLiquidoPagable=0

                Cell cellAux=null

                liquidaciones.each {
                    sheet1.addCell(new Label(0,fila, it.nombreEmpresa,formatoDatos))

                    cellAux = sheet1.getCell(1,fila)
                    totalCantidadSacos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.cantidadDeSacos.toDouble()
                    sheet1.addCell(new Number(1,fila, totalCantidadSacos,formatoDatos))

                    cellAux = sheet1.getCell(2,fila)
                    totalPesoBruto = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.pesoBruto
                    sheet1.addCell(new Number(2,fila, totalPesoBruto,formatoDatos))

                    cellAux = sheet1.getCell(3,fila)
                    totalKilosNetosHumedos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosNetosHumedos
                    sheet1.addCell(new Number(3,fila, totalKilosNetosHumedos,formatoDatos))

                    cellAux = sheet1.getCell(4,fila)
                    totalHumedad = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.porcentajeHumedadFinal
                    sheet1.addCell(new Number(4,fila, totalHumedad,formatoDatos))

                    cellAux = sheet1.getCell(5,fila)
                    totalKilosNetosSecos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosNetosSecos
                    sheet1.addCell(new Number(5,fila, totalKilosNetosSecos,formatoDatos))

                    //cellAux = sheet1.getCell(6,fila)
                    //totalPorcentajeComplejo = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.porcentajeComplejo
                    //sheet1.addCell(new Number(6,fila, totalPorcentajeComplejo,formatoDatos))

                    cellAux = sheet1.getCell(9,fila)
                    totalKilosFinosZinc = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosFinosZinc
                    sheet1.addCell(new Number(9,fila, totalKilosFinosZinc,formatoDatos))

                    cellAux = sheet1.getCell(10,fila)
                    totalKilosFinosPlomo = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosFinosPlomo
                    sheet1.addCell(new Number(10,fila, totalKilosFinosPlomo,formatoDatos))

                    cellAux = sheet1.getCell(11,fila)
                    totalKilosFinosPlata = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosFinosPlata
                    sheet1.addCell(new Number(11,fila, totalKilosFinosPlata,formatoDatos))

                    cellAux = sheet1.getCell(12,fila)
                    totalValorOficialBruto = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.valorOficialBruto
                    sheet1.addCell(new Number(12,fila, totalValorOficialBruto,formatoDatos))

                    cellAux = sheet1.getCell(13,fila)
                    totalValorNeto = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.valorNetoMineral
                    sheet1.addCell(new Number(13,fila, totalValorNeto,formatoDatos))

                    cellAux = sheet1.getCell(14,fila)
                    totalValorNetoBolivianos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.valorNetoMineralEnBolivianos
                    sheet1.addCell(new Number(14,fila, totalValorNetoBolivianos,formatoDatos))

                    cellAux = sheet1.getCell(15,fila)
                    totalBonoCalidad = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.bonoCalidad
                    sheet1.addCell(new Number(15,fila, totalBonoCalidad,formatoDatos))

                    cellAux = sheet1.getCell(16,fila)
                    totalBonoIncentivo = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.bonoIncentivo
                    sheet1.addCell(new Number(16,fila, totalBonoIncentivo,formatoDatos))

                    cellAux = sheet1.getCell(17,fila)
                    totalValorDeCompra = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.valorDeCompra
                    sheet1.addCell(new Number(17,fila, totalValorDeCompra,formatoDatos))

                    cellAux = sheet1.getCell(18,fila)
                    totalRegaliaMinera = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.regaliaMinera
                    sheet1.addCell(new Number(18,fila, totalRegaliaMinera,formatoDatos))

                    columna = 19

                    /*DESPLIEGUE DE RETENCIONES DE LEY*/
                    def retencionesDeLeyLiquidacion = LiquidacionDeComplejoRetenciones.findAllByLiquidacionDeComplejoAndTipoDeRetencion(it,"DE LEY")
                    def numretDeLey = retencionesDeLeyLiquidacion.size()
                    //System.out.println("*** ITERANDO SOBRE ${numretDeLey} RETENCIONES DE LEY!")
                    def subtotalRetencionesDeLey=it.regaliaMinera.doubleValue()
                    def retencionAux=0
                    for(int i=0;i<listaRetencionesDeLey.size();i++){
                        def vr = valorRetencion(listaRetencionesDeLey.get(i), retencionesDeLeyLiquidacion,numretDeLey)

                        cellAux = sheet1.getCell(columna,fila)
                        retencionAux = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+vr
                        sheet1.addCell(new Number(columna,fila, retencionAux,formatoDatos))

                        subtotalRetencionesDeLey+=vr
                        columna++
                    }

                    cellAux = sheet1.getCell(columna,fila)
                    subtotalRetencionesDeLey = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+subtotalRetencionesDeLey
                    sheet1.addCell(new Number(columna,fila, subtotalRetencionesDeLey,formatoDatos))
                    columna++

                    /*DESPLIEGUE DE RETENCIONES DE LEY*/
                    def retencionesOtrasLiquidacion = LiquidacionDeComplejoRetenciones.findAllByLiquidacionDeComplejoAndTipoDeRetencion(it,"OTRA")
                    def numretOtras = retencionesOtrasLiquidacion.size()
                    //System.out.println("*** ITERANDO SOBRE ${numretOtras} RETENCIONES DE LEY!")
                    def subtotalRetencionesOtras=0
                    for(int i=0;i<listaRetencionesOtras.size();i++){
                        def vr = valorRetencion(listaRetencionesOtras.get(i), retencionesOtrasLiquidacion,numretOtras)

                        cellAux = sheet1.getCell(columna,fila)
                        retencionAux = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+vr
                        sheet1.addCell(new Number(columna,fila, retencionAux,formatoDatos))

                        subtotalRetencionesOtras+=vr
                        columna++
                    }

                    cellAux = sheet1.getCell(columna,fila)
                    subtotalRetencionesOtras = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+subtotalRetencionesOtras
                    sheet1.addCell(new Number(columna,fila, subtotalRetencionesOtras,formatoDatos))
                    columna++

                    cellAux = sheet1.getCell(columna,fila)
                    totalTotalRetenciones = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.totalRetenciones
                    sheet1.addCell(new Number(columna,fila, totalTotalRetenciones,formatoDatos))

                    cellAux = sheet1.getCell(columna+1,fila)
                    totalTotalPagado = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.totalPagado
                    sheet1.addCell(new Number(columna+1,fila, totalTotalPagado,formatoDatos))

                    cellAux = sheet1.getCell(columna+2,fila)
                    totalTotalAnticiposContraEntrega = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.totalAnticiposContraEntrega
                    sheet1.addCell(new Number(columna+2,fila, totalTotalAnticiposContraEntrega,formatoDatos))

                    cellAux = sheet1.getCell(columna+3,fila)
                    totalTotalAnticiposContraFuturaEntrega = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.totalAnticiposContraFuturaEntrega
                    sheet1.addCell(new Number(columna+3,fila, totalTotalAnticiposContraFuturaEntrega,formatoDatos))

                    cellAux = sheet1.getCell(columna+4,fila)
                    totalTotalLiquidoPagable = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+((it.totalLiquidoPagable<0)?0:it.totalLiquidoPagable)
                    sheet1.addCell(new Number(columna+4,fila, totalTotalLiquidoPagable,formatoDatos))
                }

                //humedad
                cellAux = sheet1.getCell(3,fila)
                def _totalKilosNetosHumedos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

                cellAux = sheet1.getCell(5,fila)
                def _totalKilosNetosSecos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

                sheet1.addCell(new Number(4,fila, (100-100*_totalKilosNetosSecos/_totalKilosNetosHumedos),formatoDatos))

                //ley
                cellAux = sheet1.getCell(9,fila)
                def _totalKilosFinosZinc = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

                sheet1.addCell(new Number(6,fila, _totalKilosFinosZinc/_totalKilosNetosSecos*100,formatoDatos))

                cellAux = sheet1.getCell(10,fila)
                def _totalKilosFinosPlomo = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

                sheet1.addCell(new Number(7,fila, _totalKilosFinosPlomo/_totalKilosNetosSecos*100,formatoDatos))

                cellAux = sheet1.getCell(11,fila)
                def _totalKilosFinosPlata = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

                sheet1.addCell(new Number(8,fila, _totalKilosFinosPlata/_totalKilosNetosSecos*10000,formatoDatos))

                fila++
            }
        }

        def columnaFinalRetenciones = 28+listaRetencionesDeLey.size()+listaRetencionesOtras.size()
        def totalLiquidaciones = fila
        for (int col=1;col<columnaFinalRetenciones;col++){
            def tret=0
            for (int fil =7;fil<totalLiquidaciones+7;fil++){
                def valor = ((sheet1.getWritableCell(col,fil).contents.isNumber())?sheet1.getWritableCell(col,fil).contents.toBigDecimal():0)
                tret+=valor
            }
            sheet1.addCell(new Number(col,totalLiquidaciones, tret,formatoTotales))
        }

        //humedad
        Cell cellAux = sheet1.getCell(3,fila)
        def __totalKilosNetosHumedos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        cellAux = sheet1.getCell(5,fila)
        def __totalKilosNetosSecos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        if(__totalKilosNetosHumedos>0)
            sheet1.addCell(new Number(4,fila, (100-100*__totalKilosNetosSecos/__totalKilosNetosHumedos),formatoTotales))

        //ley
        cellAux = sheet1.getCell(9,fila)
        def __totalKilosFinosZinc = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        sheet1.addCell(new Number(6,fila, __totalKilosFinosZinc/__totalKilosNetosSecos*100,formatoTotales))

        cellAux = sheet1.getCell(10,fila)
        def __totalKilosFinosPlomo = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        sheet1.addCell(new Number(7,fila, __totalKilosFinosPlomo/__totalKilosNetosSecos*100,formatoTotales))

        cellAux = sheet1.getCell(11,fila)
        def __totalKilosFinosPlata = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        sheet1.addCell(new Number(8,fila, __totalKilosFinosPlata/__totalKilosNetosSecos*10000,formatoTotales))
        
        workbook.write();
        workbook.close();
    }

    def crearReportePlomoPlata = {
        /*FALTA PONER TITULO DEL ELEMENTO Y DEL PERIODO DEL REPORTE*/

        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Compra General de PlomoPlata", 0)
        sheet1.setColumnView(0,50)
        sheet1.setRowView(6,500)
        for(i in 1..100)
            sheet1.setColumnView(i,12)
        SheetSettings settings = sheet1.getSettings()
        settings.setScaleFactor(70)
        settings.setPaperSize(PaperSize.LEGAL)
        settings.setOrientation(PageOrientation.LANDSCAPE)
        settings.setTopMargin(0.2)
        settings.setBottomMargin(0.4)
        settings.setLeftMargin(0.6)
        settings.setRightMargin(0.4)
        settings.setHeaderMargin(0)
        settings.setFooterMargin(0)

        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD);
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
        response.setHeader('Content-Disposition', 'Attachment;Filename="compra_minerales_empresa_complejo.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(3,0, "COMPRA DE MINERALES POR EMPRESA",formatoTitulo))

        def fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
        def fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

        sheet1.addCell(new Label(0,3, "ELEMENTO:"))
        sheet1.addCell(new Label(1,3, "PlomoPlata"))
        sheet1.addCell(new Label(0,4, "PERIODO:"))
        sheet1.addCell(new Label(1,4, "${new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)} AL ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)}"))

        sheet1.addCell(new Label(0,6, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "K. N. H.",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "% H2O",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "K. N. S.",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "LEY %Zn",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "LEY %Pb",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "LEY DM Ag",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "K. F. Zn",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "K. F. Pb",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "K. F. Ag",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "VALOR OF. BRUTO",formatoEncabezado))
        sheet1.addCell(new Label(13,6, "VALOR NETO \$us",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(15,6, "BONO CALIDAD",formatoEncabezado))
        sheet1.addCell(new Label(16,6, "BONO INCENTIVO",formatoEncabezado))
        sheet1.addCell(new Label(17,6, "VALOR DE COMPRA",formatoEncabezado))
        sheet1.addCell(new Label(18,6, "RM",formatoEncabezado))

        /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA RETENCIONES DE LEY*/
        def liquidacionesTodas = LiquidacionDePlomoPlata.findAllByFechaDeLiquidacionGreaterThanEqualsAndFechaDeLiquidacionLessThanEquals(fechaInicial,fechaFinal)
        def listaRetencionesDeLey = retencionesPlomoPlataJSON liquidacionesTodas,"DE LEY"
        def columna = 19
        listaRetencionesDeLey.each {
            sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
            columna++
        }
        sheet1.addCell(new Label(columna,6, "TOTAL RET. DE LEY",formatoEncabezado))
        columna++

        /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA OTRAS RETENCIONES*/
        def listaRetencionesOtras = retencionesPlomoPlataJSON liquidacionesTodas,"OTRA"
        listaRetencionesOtras.each {
            sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
            columna++
        }
        sheet1.addCell(new Label(columna,6, "TOTAL OTRAS RET.",formatoEncabezado))
        columna++

        sheet1.addCell(new Label(columna,6, "TOTAL RET.",formatoEncabezado))
        sheet1.addCell(new Label(columna+1,6, "TOTAL PAGADO",formatoEncabezado))
        sheet1.addCell(new Label(columna+2,6, "ANTICIPO/ENTREGA",formatoEncabezado))
        sheet1.addCell(new Label(columna+3,6, "ANTICIPO/F. ENTREGA",formatoEncabezado))
        sheet1.addCell(new Label(columna+4,6, "LIQUIDO PAGABLE",formatoEncabezado))

        def empresas = Empresa.list()

        def fila = 7
        //variables acumuladoras
        def totalCantidadSacos=0
        def totalPesoBruto=0
        def totalKilosNetosHumedos=0
        def totalHumedad=0
        def totalKilosNetosSecos=0
        //def totalPorcentajePlomoPlata=0
        def totalKilosFinosZinc=0
        def totalKilosFinosPlomo=0
        def totalKilosFinosPlata=0
        def totalValorOficialBruto=0
        def totalValorNeto=0
        def totalValorNetoBolivianos=0
        def totalBonoCalidad=0
        def totalBonoIncentivo=0
        def totalValorDeCompra=0
        def totalRegaliaMinera=0
        def totalTotalRetenciones=0
        def totalTotalPagado=0
        def totalTotalAnticiposContraEntrega=0
        def totalTotalAnticiposContraFuturaEntrega=0
        def totalTotalLiquidoPagable=0

        empresas.each { e ->
            //log.info("***** ITERANDO SOBRE: ${e.toString()}")
            System.err.println("***** ITERANDO SOBRE EMPRESAS: ${e.toString()}")
            def liquidaciones1 = LiquidacionDePlomoPlata.findAllByFechaDeLiquidacionGreaterThanEqualsAndFechaDeLiquidacionLessThanEquals(fechaInicial,fechaFinal)
            //def liquidaciones1 = LiquidacionDePlomoPlata.findAllByFechaDeLiquidacionBetween(fechaInicial,fechaFinal)

            def liquidaciones = new ArrayList<LiquidacionDePlomoPlata>()
            liquidaciones1.each {
                if(it.recepcionDeComplejo.empresa.id==e.id){
                    liquidaciones.add(it)
                }
            }

            if(liquidaciones.size()>0){
                totalCantidadSacos=0
                totalPesoBruto=0
                totalKilosNetosHumedos=0
                totalHumedad=0
                totalKilosNetosSecos=0
                //totalPorcentajePlomoPlata=0
                totalKilosFinosZinc=0
                totalKilosFinosPlomo=0
                totalKilosFinosPlata=0
                totalValorOficialBruto=0
                totalValorNeto=0
                totalValorNetoBolivianos=0
                totalBonoCalidad=0
                totalBonoIncentivo=0
                totalValorDeCompra=0
                totalRegaliaMinera=0
                totalTotalRetenciones=0
                totalTotalPagado=0
                totalTotalAnticiposContraEntrega=0
                totalTotalAnticiposContraFuturaEntrega=0
                totalTotalLiquidoPagable=0

                Cell cellAux=null

                liquidaciones.each {
                    sheet1.addCell(new Label(0,fila, it.nombreEmpresa,formatoDatos))

                    cellAux = sheet1.getCell(1,fila)
                    totalCantidadSacos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.cantidadDeSacos.toDouble()
                    sheet1.addCell(new Number(1,fila, totalCantidadSacos,formatoDatos))

                    cellAux = sheet1.getCell(2,fila)
                    totalPesoBruto = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.pesoBruto
                    sheet1.addCell(new Number(2,fila, totalPesoBruto,formatoDatos))

                    cellAux = sheet1.getCell(3,fila)
                    totalKilosNetosHumedos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosNetosHumedos
                    sheet1.addCell(new Number(3,fila, totalKilosNetosHumedos,formatoDatos))

                    cellAux = sheet1.getCell(4,fila)
                    totalHumedad = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.porcentajeHumedadFinal
                    sheet1.addCell(new Number(4,fila, totalHumedad,formatoDatos))

                    cellAux = sheet1.getCell(5,fila)
                    totalKilosNetosSecos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosNetosSecos
                    sheet1.addCell(new Number(5,fila, totalKilosNetosSecos,formatoDatos))

                    //cellAux = sheet1.getCell(6,fila)
                    //totalPorcentajePlomoPlata = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.porcentajePlomoPlata
                    //sheet1.addCell(new Number(6,fila, totalPorcentajePlomoPlata,formatoDatos))

                    cellAux = sheet1.getCell(9,fila)
                    //totalKilosFinosZinc = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosFinosZinc
                    totalKilosFinosZinc = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+0
                    sheet1.addCell(new Number(9,fila, totalKilosFinosZinc,formatoDatos))

                    cellAux = sheet1.getCell(10,fila)
                    totalKilosFinosPlomo = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosFinosPlomo
                    sheet1.addCell(new Number(10,fila, totalKilosFinosPlomo,formatoDatos))

                    cellAux = sheet1.getCell(11,fila)
                    totalKilosFinosPlata = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosFinosPlata
                    sheet1.addCell(new Number(11,fila, totalKilosFinosPlata,formatoDatos))

                    cellAux = sheet1.getCell(12,fila)
                    totalValorOficialBruto = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.valorOficialBruto
                    sheet1.addCell(new Number(12,fila, totalValorOficialBruto,formatoDatos))

                    cellAux = sheet1.getCell(13,fila)
                    totalValorNeto = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.valorNetoMineral
                    sheet1.addCell(new Number(13,fila, totalValorNeto,formatoDatos))

                    cellAux = sheet1.getCell(14,fila)
                    totalValorNetoBolivianos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.valorNetoMineralEnBolivianos
                    sheet1.addCell(new Number(14,fila, totalValorNetoBolivianos,formatoDatos))

                    cellAux = sheet1.getCell(15,fila)
                    totalBonoCalidad = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.bonoCalidad
                    sheet1.addCell(new Number(15,fila, totalBonoCalidad,formatoDatos))

                    cellAux = sheet1.getCell(16,fila)
                    totalBonoIncentivo = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.bonoIncentivo
                    sheet1.addCell(new Number(16,fila, totalBonoIncentivo,formatoDatos))

                    cellAux = sheet1.getCell(17,fila)
                    totalValorDeCompra = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.valorDeCompra
                    sheet1.addCell(new Number(17,fila, totalValorDeCompra,formatoDatos))

                    cellAux = sheet1.getCell(18,fila)
                    totalRegaliaMinera = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.regaliaMinera
                    sheet1.addCell(new Number(18,fila, totalRegaliaMinera,formatoDatos))

                    columna = 19

                    /*DESPLIEGUE DE RETENCIONES DE LEY*/
                    def retencionesDeLeyLiquidacion = LiquidacionDePlomoPlataRetenciones.findAllByLiquidacionDePlomoPlataAndTipoDeRetencion(it,"DE LEY")
                    def numretDeLey = retencionesDeLeyLiquidacion.size()
                    //System.out.println("*** ITERANDO SOBRE ${numretDeLey} RETENCIONES DE LEY!")
                    def subtotalRetencionesDeLey=it.regaliaMinera.doubleValue()
                    def retencionAux=0
                    for(int i=0;i<listaRetencionesDeLey.size();i++){
                        def vr = valorRetencion(listaRetencionesDeLey.get(i), retencionesDeLeyLiquidacion,numretDeLey)

                        cellAux = sheet1.getCell(columna,fila)
                        retencionAux = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+vr
                        sheet1.addCell(new Number(columna,fila, retencionAux,formatoDatos))

                        subtotalRetencionesDeLey+=vr
                        columna++
                    }

                    cellAux = sheet1.getCell(columna,fila)
                    subtotalRetencionesDeLey = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+subtotalRetencionesDeLey
                    sheet1.addCell(new Number(columna,fila, subtotalRetencionesDeLey,formatoDatos))
                    columna++

                    /*DESPLIEGUE DE RETENCIONES DE LEY*/
                    def retencionesOtrasLiquidacion = LiquidacionDePlomoPlataRetenciones.findAllByLiquidacionDePlomoPlataAndTipoDeRetencion(it,"OTRA")
                    def numretOtras = retencionesOtrasLiquidacion.size()
                    //System.out.println("*** ITERANDO SOBRE ${numretOtras} RETENCIONES DE LEY!")
                    def subtotalRetencionesOtras=0
                    for(int i=0;i<listaRetencionesOtras.size();i++){
                        def vr = valorRetencion(listaRetencionesOtras.get(i), retencionesOtrasLiquidacion,numretOtras)

                        cellAux = sheet1.getCell(columna,fila)
                        retencionAux = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+vr
                        sheet1.addCell(new Number(columna,fila, retencionAux,formatoDatos))

                        subtotalRetencionesOtras+=vr
                        columna++
                    }

                    cellAux = sheet1.getCell(columna,fila)
                    subtotalRetencionesOtras = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+subtotalRetencionesOtras
                    sheet1.addCell(new Number(columna,fila, subtotalRetencionesOtras,formatoDatos))
                    columna++

                    cellAux = sheet1.getCell(columna,fila)
                    totalTotalRetenciones = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.totalRetenciones
                    sheet1.addCell(new Number(columna,fila, totalTotalRetenciones,formatoDatos))

                    cellAux = sheet1.getCell(columna+1,fila)
                    totalTotalPagado = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.totalPagado
                    sheet1.addCell(new Number(columna+1,fila, totalTotalPagado,formatoDatos))

                    cellAux = sheet1.getCell(columna+2,fila)
                    totalTotalAnticiposContraEntrega = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.totalAnticiposContraEntrega
                    sheet1.addCell(new Number(columna+2,fila, totalTotalAnticiposContraEntrega,formatoDatos))

                    cellAux = sheet1.getCell(columna+3,fila)
                    totalTotalAnticiposContraFuturaEntrega = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.totalAnticiposContraFuturaEntrega
                    sheet1.addCell(new Number(columna+3,fila, totalTotalAnticiposContraFuturaEntrega,formatoDatos))

                    cellAux = sheet1.getCell(columna+4,fila)
                    totalTotalLiquidoPagable = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+((it.totalLiquidoPagable<0)?0:it.totalLiquidoPagable)
                    sheet1.addCell(new Number(columna+4,fila, totalTotalLiquidoPagable,formatoDatos))
                }

                //humedad
                cellAux = sheet1.getCell(3,fila)
                def _totalKilosNetosHumedos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

                cellAux = sheet1.getCell(5,fila)
                def _totalKilosNetosSecos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

                sheet1.addCell(new Number(4,fila, (100-100*_totalKilosNetosSecos/_totalKilosNetosHumedos),formatoDatos))

                //ley
                cellAux = sheet1.getCell(9,fila)
                def _totalKilosFinosZinc = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

                sheet1.addCell(new Number(6,fila, _totalKilosFinosZinc/_totalKilosNetosSecos*100,formatoDatos))

                cellAux = sheet1.getCell(10,fila)
                def _totalKilosFinosPlomo = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

                sheet1.addCell(new Number(7,fila, _totalKilosFinosPlomo/_totalKilosNetosSecos*100,formatoDatos))

                cellAux = sheet1.getCell(11,fila)
                def _totalKilosFinosPlata = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

                sheet1.addCell(new Number(8,fila, _totalKilosFinosPlata/_totalKilosNetosSecos*10000,formatoDatos))

                fila++
            }
        }

        def columnaFinalRetenciones = 26+listaRetencionesDeLey.size()+listaRetencionesOtras.size()
        def totalLiquidaciones = fila
        for (int col=1;col<columnaFinalRetenciones;col++){
            def tret=0
            for (int fil =7;fil<totalLiquidaciones+7;fil++){
                def valor = ((sheet1.getWritableCell(col,fil).contents.isNumber())?sheet1.getWritableCell(col,fil).contents.toBigDecimal():0)
                tret+=valor
            }
            sheet1.addCell(new Number(col,totalLiquidaciones, tret,formatoTotales))
        }

        //humedad
        Cell cellAux = sheet1.getCell(3,fila)
        def __totalKilosNetosHumedos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        cellAux = sheet1.getCell(5,fila)
        def __totalKilosNetosSecos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        if(__totalKilosNetosHumedos>0)
            sheet1.addCell(new Number(4,fila, (100-100*__totalKilosNetosSecos/__totalKilosNetosHumedos),formatoTotales))

        //ley
        cellAux = sheet1.getCell(9,fila)
        def __totalKilosFinosZinc = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        sheet1.addCell(new Number(6,fila, __totalKilosFinosZinc/__totalKilosNetosSecos*100,formatoTotales))

        cellAux = sheet1.getCell(10,fila)
        def __totalKilosFinosPlomo = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        sheet1.addCell(new Number(7,fila, __totalKilosFinosPlomo/__totalKilosNetosSecos*100,formatoTotales))

        cellAux = sheet1.getCell(11,fila)
        def __totalKilosFinosPlata = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        sheet1.addCell(new Number(8,fila, __totalKilosFinosPlata/__totalKilosNetosSecos*10000,formatoTotales))

        workbook.write();
        workbook.close();
    }

    def crearReporteZincPlata = {
        /*FALTA PONER TITULO DEL ELEMENTO Y DEL PERIODO DEL REPORTE*/

        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Compra General de ZincPlata", 0)
        sheet1.setColumnView(0,50)
        sheet1.setRowView(6,500)
        for(i in 1..100)
            sheet1.setColumnView(i,12)
        SheetSettings settings = sheet1.getSettings()
        settings.setScaleFactor(70)
        settings.setPaperSize(PaperSize.LEGAL)
        settings.setOrientation(PageOrientation.LANDSCAPE)
        settings.setTopMargin(0.2)
        settings.setBottomMargin(0.4)
        settings.setLeftMargin(0.6)
        settings.setRightMargin(0.4)
        settings.setHeaderMargin(0)
        settings.setFooterMargin(0)

        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD);
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
        response.setHeader('Content-Disposition', 'Attachment;Filename="compra_minerales_empresa_complejo.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(3,0, "COMPRA DE MINERALES POR EMPRESA",formatoTitulo))

        def fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
        def fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

        sheet1.addCell(new Label(0,3, "ELEMENTO:"))
        sheet1.addCell(new Label(1,3, "ZincPlata"))
        sheet1.addCell(new Label(0,4, "PERIODO:"))
        sheet1.addCell(new Label(1,4, "${new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)} AL ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)}"))

        sheet1.addCell(new Label(0,6, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "K. N. H.",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "% H2O",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "K. N. S.",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "LEY %Zn",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "LEY %Pb",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "LEY DM Ag",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "K. F. Zn",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "K. F. Pb",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "K. F. Ag",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "VALOR OF. BRUTO",formatoEncabezado))
        sheet1.addCell(new Label(13,6, "VALOR NETO \$us",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(15,6, "BONO CALIDAD",formatoEncabezado))
        sheet1.addCell(new Label(16,6, "BONO INCENTIVO",formatoEncabezado))
        sheet1.addCell(new Label(17,6, "VALOR DE COMPRA",formatoEncabezado))
        sheet1.addCell(new Label(18,6, "RM",formatoEncabezado))

        /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA RETENCIONES DE LEY*/
        def liquidacionesTodas = LiquidacionDeZincPlata.findAllByFechaDeLiquidacionGreaterThanEqualsAndFechaDeLiquidacionLessThanEquals(fechaInicial,fechaFinal)
        def listaRetencionesDeLey = retencionesZincPlataJSON liquidacionesTodas,"DE LEY"
        def columna = 19
        listaRetencionesDeLey.each {
            sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
            columna++
        }
        sheet1.addCell(new Label(columna,6, "TOTAL RET. DE LEY",formatoEncabezado))
        columna++

        /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA OTRAS RETENCIONES*/
        def listaRetencionesOtras = retencionesZincPlataJSON liquidacionesTodas,"OTRA"
        listaRetencionesOtras.each {
            sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
            columna++
        }
        sheet1.addCell(new Label(columna,6, "TOTAL OTRAS RET.",formatoEncabezado))
        columna++

        sheet1.addCell(new Label(columna,6, "TOTAL RET.",formatoEncabezado))
        sheet1.addCell(new Label(columna+1,6, "TOTAL PAGADO",formatoEncabezado))
        sheet1.addCell(new Label(columna+2,6, "ANTICIPO/ENTREGA",formatoEncabezado))
        sheet1.addCell(new Label(columna+3,6, "ANTICIPO/F. ENTREGA",formatoEncabezado))
        sheet1.addCell(new Label(columna+4,6, "LIQUIDO PAGABLE",formatoEncabezado))

        def empresas = Empresa.list()

        def fila = 7
        //variables acumuladoras
        def totalCantidadSacos=0
        def totalPesoBruto=0
        def totalKilosNetosHumedos=0
        def totalHumedad=0
        def totalKilosNetosSecos=0
        //def totalPorcentajeZincPlata=0
        def totalKilosFinosZinc=0
        def totalKilosFinosPlomo=0
        def totalKilosFinosPlata=0
        def totalValorOficialBruto=0
        def totalValorNeto=0
        def totalValorNetoBolivianos=0
        def totalBonoCalidad=0
        def totalBonoIncentivo=0
        def totalValorDeCompra=0
        def totalRegaliaMinera=0
        def totalTotalRetenciones=0
        def totalTotalPagado=0
        def totalTotalAnticiposContraEntrega=0
        def totalTotalAnticiposContraFuturaEntrega=0
        def totalTotalLiquidoPagable=0

        empresas.each { e ->
            //log.info("***** ITERANDO SOBRE: ${e.toString()}")
            System.err.println("***** ITERANDO SOBRE EMPRESAS: ${e.toString()}")
            def liquidaciones1 = LiquidacionDeZincPlata.findAllByFechaDeLiquidacionGreaterThanEqualsAndFechaDeLiquidacionLessThanEquals(fechaInicial,fechaFinal)
            //def liquidaciones1 = LiquidacionDeZincPlata.findAllByFechaDeLiquidacionBetween(fechaInicial,fechaFinal)

            def liquidaciones = new ArrayList<LiquidacionDeZincPlata>()
            liquidaciones1.each {
                if(it.recepcionDeComplejo.empresa.id==e.id){
                    liquidaciones.add(it)
                }
            }

            if(liquidaciones.size()>0){
                totalCantidadSacos=0
                totalPesoBruto=0
                totalKilosNetosHumedos=0
                totalHumedad=0
                totalKilosNetosSecos=0
                //totalPorcentajeZincPlata=0
                totalKilosFinosZinc=0
                totalKilosFinosPlomo=0
                totalKilosFinosPlata=0
                totalValorOficialBruto=0
                totalValorNeto=0
                totalValorNetoBolivianos=0
                totalBonoCalidad=0
                totalBonoIncentivo=0
                totalValorDeCompra=0
                totalRegaliaMinera=0
                totalTotalRetenciones=0
                totalTotalPagado=0
                totalTotalAnticiposContraEntrega=0
                totalTotalAnticiposContraFuturaEntrega=0
                totalTotalLiquidoPagable=0

                Cell cellAux=null

                liquidaciones.each {
                    sheet1.addCell(new Label(0,fila, it.nombreEmpresa,formatoDatos))

                    cellAux = sheet1.getCell(1,fila)
                    totalCantidadSacos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.cantidadDeSacos.toDouble()
                    sheet1.addCell(new Number(1,fila, totalCantidadSacos,formatoDatos))

                    cellAux = sheet1.getCell(2,fila)
                    totalPesoBruto = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.pesoBruto
                    sheet1.addCell(new Number(2,fila, totalPesoBruto,formatoDatos))

                    cellAux = sheet1.getCell(3,fila)
                    totalKilosNetosHumedos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosNetosHumedos
                    sheet1.addCell(new Number(3,fila, totalKilosNetosHumedos,formatoDatos))

                    cellAux = sheet1.getCell(4,fila)
                    totalHumedad = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.porcentajeHumedadFinal
                    sheet1.addCell(new Number(4,fila, totalHumedad,formatoDatos))

                    cellAux = sheet1.getCell(5,fila)
                    totalKilosNetosSecos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosNetosSecos
                    sheet1.addCell(new Number(5,fila, totalKilosNetosSecos,formatoDatos))

                    //cellAux = sheet1.getCell(6,fila)
                    //totalPorcentajeZincPlata = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.porcentajeZincPlata
                    //sheet1.addCell(new Number(6,fila, totalPorcentajeZincPlata,formatoDatos))

                    cellAux = sheet1.getCell(9,fila)
                    totalKilosFinosZinc = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosFinosZinc
                    sheet1.addCell(new Number(9,fila, totalKilosFinosZinc,formatoDatos))

                    cellAux = sheet1.getCell(10,fila)
                    //totalKilosFinosPlomo = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosFinosPlomo
                    totalKilosFinosPlomo = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+0
                    sheet1.addCell(new Number(10,fila, totalKilosFinosPlomo,formatoDatos))

                    cellAux = sheet1.getCell(11,fila)
                    totalKilosFinosPlata = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosFinosPlata
                    sheet1.addCell(new Number(11,fila, totalKilosFinosPlata,formatoDatos))

                    cellAux = sheet1.getCell(12,fila)
                    totalValorOficialBruto = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.valorOficialBruto
                    sheet1.addCell(new Number(12,fila, totalValorOficialBruto,formatoDatos))

                    cellAux = sheet1.getCell(13,fila)
                    totalValorNeto = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.valorNetoMineral
                    sheet1.addCell(new Number(13,fila, totalValorNeto,formatoDatos))

                    cellAux = sheet1.getCell(14,fila)
                    totalValorNetoBolivianos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.valorNetoMineralEnBolivianos
                    sheet1.addCell(new Number(14,fila, totalValorNetoBolivianos,formatoDatos))

                    cellAux = sheet1.getCell(15,fila)
                    totalBonoCalidad = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.bonoCalidad
                    sheet1.addCell(new Number(15,fila, totalBonoCalidad,formatoDatos))

                    cellAux = sheet1.getCell(16,fila)
                    totalBonoIncentivo = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.bonoIncentivo
                    sheet1.addCell(new Number(16,fila, totalBonoIncentivo,formatoDatos))

                    cellAux = sheet1.getCell(17,fila)
                    totalValorDeCompra = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.valorDeCompra
                    sheet1.addCell(new Number(17,fila, totalValorDeCompra,formatoDatos))

                    cellAux = sheet1.getCell(18,fila)
                    totalRegaliaMinera = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.regaliaMinera
                    sheet1.addCell(new Number(18,fila, totalRegaliaMinera,formatoDatos))

                    columna = 19

                    /*DESPLIEGUE DE RETENCIONES DE LEY*/
                    def retencionesDeLeyLiquidacion = LiquidacionDeZincPlataRetenciones.findAllByLiquidacionDeZincPlataAndTipoDeRetencion(it,"DE LEY")
                    def numretDeLey = retencionesDeLeyLiquidacion.size()
                    //System.out.println("*** ITERANDO SOBRE ${numretDeLey} RETENCIONES DE LEY!")
                    def subtotalRetencionesDeLey=it.regaliaMinera.doubleValue()
                    def retencionAux=0
                    for(int i=0;i<listaRetencionesDeLey.size();i++){
                        def vr = valorRetencion(listaRetencionesDeLey.get(i), retencionesDeLeyLiquidacion,numretDeLey)

                        cellAux = sheet1.getCell(columna,fila)
                        retencionAux = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+vr
                        sheet1.addCell(new Number(columna,fila, retencionAux,formatoDatos))

                        subtotalRetencionesDeLey+=vr
                        columna++
                    }

                    cellAux = sheet1.getCell(columna,fila)
                    subtotalRetencionesDeLey = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+subtotalRetencionesDeLey
                    sheet1.addCell(new Number(columna,fila, subtotalRetencionesDeLey,formatoDatos))
                    columna++

                    /*DESPLIEGUE DE RETENCIONES DE LEY*/
                    def retencionesOtrasLiquidacion = LiquidacionDeZincPlataRetenciones.findAllByLiquidacionDeZincPlataAndTipoDeRetencion(it,"OTRA")
                    def numretOtras = retencionesOtrasLiquidacion.size()
                    //System.out.println("*** ITERANDO SOBRE ${numretOtras} RETENCIONES DE LEY!")
                    def subtotalRetencionesOtras=0
                    for(int i=0;i<listaRetencionesOtras.size();i++){
                        def vr = valorRetencion(listaRetencionesOtras.get(i), retencionesOtrasLiquidacion,numretOtras)

                        cellAux = sheet1.getCell(columna,fila)
                        retencionAux = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+vr
                        sheet1.addCell(new Number(columna,fila, retencionAux,formatoDatos))

                        subtotalRetencionesOtras+=vr
                        columna++
                    }

                    cellAux = sheet1.getCell(columna,fila)
                    subtotalRetencionesOtras = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+subtotalRetencionesOtras
                    sheet1.addCell(new Number(columna,fila, subtotalRetencionesOtras,formatoDatos))
                    columna++

                    cellAux = sheet1.getCell(columna,fila)
                    totalTotalRetenciones = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.totalRetenciones
                    sheet1.addCell(new Number(columna,fila, totalTotalRetenciones,formatoDatos))

                    cellAux = sheet1.getCell(columna+1,fila)
                    totalTotalPagado = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.totalPagado
                    sheet1.addCell(new Number(columna+1,fila, totalTotalPagado,formatoDatos))

                    cellAux = sheet1.getCell(columna+2,fila)
                    totalTotalAnticiposContraEntrega = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.totalAnticiposContraEntrega
                    sheet1.addCell(new Number(columna+2,fila, totalTotalAnticiposContraEntrega,formatoDatos))

                    cellAux = sheet1.getCell(columna+3,fila)
                    totalTotalAnticiposContraFuturaEntrega = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.totalAnticiposContraFuturaEntrega
                    sheet1.addCell(new Number(columna+3,fila, totalTotalAnticiposContraFuturaEntrega,formatoDatos))

                    cellAux = sheet1.getCell(columna+4,fila)
                    totalTotalLiquidoPagable = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+((it.totalLiquidoPagable<0)?0:it.totalLiquidoPagable)
                    sheet1.addCell(new Number(columna+4,fila, totalTotalLiquidoPagable,formatoDatos))
                }

                //humedad
                cellAux = sheet1.getCell(3,fila)
                def _totalKilosNetosHumedos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

                cellAux = sheet1.getCell(5,fila)
                def _totalKilosNetosSecos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

                sheet1.addCell(new Number(4,fila, (100-100*_totalKilosNetosSecos/_totalKilosNetosHumedos),formatoDatos))

                //ley
                cellAux = sheet1.getCell(9,fila)
                def _totalKilosFinosZinc = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

                sheet1.addCell(new Number(6,fila, _totalKilosFinosZinc/_totalKilosNetosSecos*100,formatoDatos))

                cellAux = sheet1.getCell(10,fila)
                def _totalKilosFinosPlomo = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

                sheet1.addCell(new Number(7,fila, _totalKilosFinosPlomo/_totalKilosNetosSecos*100,formatoDatos))

                cellAux = sheet1.getCell(11,fila)
                def _totalKilosFinosPlata = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

                sheet1.addCell(new Number(8,fila, _totalKilosFinosPlata/_totalKilosNetosSecos*10000,formatoDatos))

                fila++
            }
        }

        def columnaFinalRetenciones = 26+listaRetencionesDeLey.size()+listaRetencionesOtras.size()
        def totalLiquidaciones = fila
        for (int col=1;col<columnaFinalRetenciones;col++){
            def tret=0
            for (int fil =7;fil<totalLiquidaciones+7;fil++){
                def valor = ((sheet1.getWritableCell(col,fil).contents.isNumber())?sheet1.getWritableCell(col,fil).contents.toBigDecimal():0)
                tret+=valor
            }
            sheet1.addCell(new Number(col,totalLiquidaciones, tret,formatoTotales))
        }

        //humedad
        Cell cellAux = sheet1.getCell(3,fila)
        def __totalKilosNetosHumedos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        cellAux = sheet1.getCell(5,fila)
        def __totalKilosNetosSecos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        if(__totalKilosNetosHumedos>0)
            sheet1.addCell(new Number(4,fila, (100-100*__totalKilosNetosSecos/__totalKilosNetosHumedos),formatoTotales))

        //ley
        cellAux = sheet1.getCell(9,fila)
        def __totalKilosFinosZinc = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        sheet1.addCell(new Number(6,fila, __totalKilosFinosZinc/__totalKilosNetosSecos*100,formatoTotales))

        cellAux = sheet1.getCell(10,fila)
        def __totalKilosFinosPlomo = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        sheet1.addCell(new Number(7,fila, __totalKilosFinosPlomo/__totalKilosNetosSecos*100,formatoTotales))

        cellAux = sheet1.getCell(11,fila)
        def __totalKilosFinosPlata = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        sheet1.addCell(new Number(8,fila, __totalKilosFinosPlata/__totalKilosNetosSecos*10000,formatoTotales))

        workbook.write();
        workbook.close();
    }

    def crearReporteCobrePlata = {
        /*FALTA PONER TITULO DEL ELEMENTO Y DEL PERIODO DEL REPORTE*/

        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Compra General de CobrePlata", 0)
        sheet1.setColumnView(0,50)
        sheet1.setRowView(6,500)
        for(i in 1..100)
            sheet1.setColumnView(i,12)
        SheetSettings settings = sheet1.getSettings()
        settings.setScaleFactor(70)
        settings.setPaperSize(PaperSize.LEGAL)
        settings.setOrientation(PageOrientation.LANDSCAPE)
        settings.setTopMargin(0.2)
        settings.setBottomMargin(0.4)
        settings.setLeftMargin(0.6)
        settings.setRightMargin(0.4)
        settings.setHeaderMargin(0)
        settings.setFooterMargin(0)

        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD);
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
        response.setHeader('Content-Disposition', 'Attachment;Filename="compra_minerales_empresa_cobre.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(3,0, "COMPRA DE MINERALES POR EMPRESA",formatoTitulo))

        def fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
        def fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

        sheet1.addCell(new Label(0,3, "ELEMENTO:"))
        sheet1.addCell(new Label(1,3, "CobrePlata"))
        sheet1.addCell(new Label(0,4, "PERIODO:"))
        sheet1.addCell(new Label(1,4, "${new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)} AL ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)}"))

        sheet1.addCell(new Label(0,6, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "K. N. H.",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "% H2O",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "K. N. S.",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "LEY %Cu",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "LEY %Pb",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "LEY DM Ag",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "K. F. Cu",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "K. F. Pb",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "K. F. Ag",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "VALOR OF. BRUTO",formatoEncabezado))
        sheet1.addCell(new Label(13,6, "VALOR NETO \$us",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(15,6, "BONO CALIDAD",formatoEncabezado))
        sheet1.addCell(new Label(16,6, "BONO INCENTIVO",formatoEncabezado))
        sheet1.addCell(new Label(17,6, "VALOR DE COMPRA",formatoEncabezado))
        sheet1.addCell(new Label(18,6, "RM",formatoEncabezado))

        /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA RETENCIONES DE LEY*/
        def liquidacionesTodas = LiquidacionDeCobrePlata.findAllByFechaDeLiquidacionGreaterThanEqualsAndFechaDeLiquidacionLessThanEquals(fechaInicial,fechaFinal)
        def listaRetencionesDeLey = retencionesCobrePlataJSON liquidacionesTodas,"DE LEY"
        def columna = 19
        listaRetencionesDeLey.each {
            sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
            columna++
        }
        sheet1.addCell(new Label(columna,6, "TOTAL RET. DE LEY",formatoEncabezado))
        columna++

        /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA OTRAS RETENCIONES*/
        def listaRetencionesOtras = retencionesCobrePlataJSON liquidacionesTodas,"OTRA"
        listaRetencionesOtras.each {
            sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
            columna++
        }
        sheet1.addCell(new Label(columna,6, "TOTAL OTRAS RET.",formatoEncabezado))
        columna++

        sheet1.addCell(new Label(columna,6, "TOTAL RET.",formatoEncabezado))
        sheet1.addCell(new Label(columna+1,6, "TOTAL PAGADO",formatoEncabezado))
        sheet1.addCell(new Label(columna+2,6, "ANTICIPO/ENTREGA",formatoEncabezado))
        sheet1.addCell(new Label(columna+3,6, "ANTICIPO/F. ENTREGA",formatoEncabezado))
        sheet1.addCell(new Label(columna+4,6, "LIQUIDO PAGABLE",formatoEncabezado))

        def empresas = Empresa.list()

        def fila = 7
        //variables acumuladoras
        def totalCantidadSacos=0
        def totalPesoBruto=0
        def totalKilosNetosHumedos=0
        def totalHumedad=0
        def totalKilosNetosSecos=0
        //def totalPorcentajeCobrePlata=0
        def totalKilosFinosCobre=0
        def totalKilosFinosPlomo=0
        def totalKilosFinosPlata=0
        def totalValorOficialBruto=0
        def totalValorNeto=0
        def totalValorNetoBolivianos=0
        def totalBonoCalidad=0
        def totalBonoIncentivo=0
        def totalValorDeCompra=0
        def totalRegaliaMinera=0
        def totalTotalRetenciones=0
        def totalTotalPagado=0
        def totalTotalAnticiposContraEntrega=0
        def totalTotalAnticiposContraFuturaEntrega=0
        def totalTotalLiquidoPagable=0

        empresas.each { e ->
            //log.info("***** ITERANDO SOBRE: ${e.toString()}")
            System.err.println("***** ITERANDO SOBRE EMPRESAS: ${e.toString()}")
            def liquidaciones1 = LiquidacionDeCobrePlata.findAllByFechaDeLiquidacionGreaterThanEqualsAndFechaDeLiquidacionLessThanEquals(fechaInicial,fechaFinal)
            //def liquidaciones1 = LiquidacionDeCobrePlata.findAllByFechaDeLiquidacionBetween(fechaInicial,fechaFinal)

            def liquidaciones = new ArrayList<LiquidacionDeCobrePlata>()
            liquidaciones1.each {
                if(it.recepcionDeComplejo.empresa.id==e.id){
                    liquidaciones.add(it)
                }
            }

            if(liquidaciones.size()>0){
                totalCantidadSacos=0
                totalPesoBruto=0
                totalKilosNetosHumedos=0
                totalHumedad=0
                totalKilosNetosSecos=0
                //totalPorcentajeCobrePlata=0
                totalKilosFinosCobre=0
                totalKilosFinosPlomo=0
                totalKilosFinosPlata=0
                totalValorOficialBruto=0
                totalValorNeto=0
                totalValorNetoBolivianos=0
                totalBonoCalidad=0
                totalBonoIncentivo=0
                totalValorDeCompra=0
                totalRegaliaMinera=0
                totalTotalRetenciones=0
                totalTotalPagado=0
                totalTotalAnticiposContraEntrega=0
                totalTotalAnticiposContraFuturaEntrega=0
                totalTotalLiquidoPagable=0

                Cell cellAux=null

                liquidaciones.each {
                    System.err.println("${it.lote} - SACOS:${it.cantidadDeSacos} - KNS:${it.kilosNetosSecos} - VOB:${it.valorOficialBruto} - VON:${it.valorNetoMineralEnBolivianos}")
                    sheet1.addCell(new Label(0,fila, it.nombreEmpresa,formatoDatos))

                    cellAux = sheet1.getCell(1,fila)
                    totalCantidadSacos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.cantidadDeSacos.toDouble()
                    sheet1.addCell(new Number(1,fila, totalCantidadSacos,formatoDatos))

                    cellAux = sheet1.getCell(2,fila)
                    totalPesoBruto = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.pesoBruto
                    sheet1.addCell(new Number(2,fila, totalPesoBruto,formatoDatos))

                    cellAux = sheet1.getCell(3,fila)
                    totalKilosNetosHumedos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosNetosHumedos
                    sheet1.addCell(new Number(3,fila, totalKilosNetosHumedos,formatoDatos))

                    cellAux = sheet1.getCell(4,fila)
                    totalHumedad = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.porcentajeHumedadFinal
                    sheet1.addCell(new Number(4,fila, totalHumedad,formatoDatos))

                    cellAux = sheet1.getCell(5,fila)
                    totalKilosNetosSecos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosNetosSecos
                    sheet1.addCell(new Number(5,fila, totalKilosNetosSecos,formatoDatos))

                    //cellAux = sheet1.getCell(6,fila)
                    //totalPorcentajeCobrePlata = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.porcentajeCobrePlata
                    //sheet1.addCell(new Number(6,fila, totalPorcentajeCobrePlata,formatoDatos))

                    cellAux = sheet1.getCell(9,fila)
                    totalKilosFinosCobre = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosFinosCobre
                    sheet1.addCell(new Number(9,fila, totalKilosFinosCobre,formatoDatos))

                    cellAux = sheet1.getCell(10,fila)
                    //totalKilosFinosPlomo = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosFinosPlomo
                    totalKilosFinosPlomo = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+0
                    sheet1.addCell(new Number(10,fila, totalKilosFinosPlomo,formatoDatos))

                    cellAux = sheet1.getCell(11,fila)
                    totalKilosFinosPlata = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.kilosFinosPlata
                    sheet1.addCell(new Number(11,fila, totalKilosFinosPlata,formatoDatos))

                    cellAux = sheet1.getCell(12,fila)
                    totalValorOficialBruto = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.valorOficialBruto
                    sheet1.addCell(new Number(12,fila, totalValorOficialBruto,formatoDatos))

                    cellAux = sheet1.getCell(13,fila)
                    totalValorNeto = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.valorNetoMineral
                    sheet1.addCell(new Number(13,fila, totalValorNeto,formatoDatos))

                    cellAux = sheet1.getCell(14,fila)
                    totalValorNetoBolivianos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.valorNetoMineralEnBolivianos
                    sheet1.addCell(new Number(14,fila, totalValorNetoBolivianos,formatoDatos))

                    cellAux = sheet1.getCell(15,fila)
                    totalBonoCalidad = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.bonoCalidad
                    sheet1.addCell(new Number(15,fila, totalBonoCalidad,formatoDatos))

                    cellAux = sheet1.getCell(16,fila)
                    totalBonoIncentivo = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.bonoIncentivo
                    sheet1.addCell(new Number(16,fila, totalBonoIncentivo,formatoDatos))

                    cellAux = sheet1.getCell(17,fila)
                    totalValorDeCompra = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.valorDeCompra
                    sheet1.addCell(new Number(17,fila, totalValorDeCompra,formatoDatos))

                    cellAux = sheet1.getCell(18,fila)
                    totalRegaliaMinera = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.regaliaMinera
                    sheet1.addCell(new Number(18,fila, totalRegaliaMinera,formatoDatos))

                    columna = 19

                    /*DESPLIEGUE DE RETENCIONES DE LEY*/
                    def retencionesDeLeyLiquidacion = LiquidacionDeCobrePlataRetenciones.findAllByLiquidacionDeCobrePlataAndTipoDeRetencion(it,"DE LEY")
                    def numretDeLey = retencionesDeLeyLiquidacion.size()
                    //System.out.println("*** ITERANDO SOBRE ${numretDeLey} RETENCIONES DE LEY!")
                    def subtotalRetencionesDeLey=it.regaliaMinera.doubleValue()
                    def retencionAux=0
                    for(int i=0;i<listaRetencionesDeLey.size();i++){
                        def vr = valorRetencion(listaRetencionesDeLey.get(i), retencionesDeLeyLiquidacion,numretDeLey)

                        cellAux = sheet1.getCell(columna,fila)
                        retencionAux = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+vr
                        sheet1.addCell(new Number(columna,fila, retencionAux,formatoDatos))

                        subtotalRetencionesDeLey+=vr
                        columna++
                    }

                    cellAux = sheet1.getCell(columna,fila)
                    subtotalRetencionesDeLey = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+subtotalRetencionesDeLey
                    sheet1.addCell(new Number(columna,fila, subtotalRetencionesDeLey,formatoDatos))
                    columna++

                    /*DESPLIEGUE DE RETENCIONES DE LEY*/
                    def retencionesOtrasLiquidacion = LiquidacionDeCobrePlataRetenciones.findAllByLiquidacionDeCobrePlataAndTipoDeRetencion(it,"OTRA")
                    def numretOtras = retencionesOtrasLiquidacion.size()
                    //System.out.println("*** ITERANDO SOBRE ${numretOtras} RETENCIONES DE LEY!")
                    def subtotalRetencionesOtras=0
                    for(int i=0;i<listaRetencionesOtras.size();i++){
                        def vr = valorRetencion(listaRetencionesOtras.get(i), retencionesOtrasLiquidacion,numretOtras)

                        cellAux = sheet1.getCell(columna,fila)
                        retencionAux = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+vr
                        sheet1.addCell(new Number(columna,fila, retencionAux,formatoDatos))

                        subtotalRetencionesOtras+=vr
                        columna++
                    }

                    cellAux = sheet1.getCell(columna,fila)
                    subtotalRetencionesOtras = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+subtotalRetencionesOtras
                    sheet1.addCell(new Number(columna,fila, subtotalRetencionesOtras,formatoDatos))
                    columna++

                    cellAux = sheet1.getCell(columna,fila)
                    totalTotalRetenciones = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.totalRetenciones
                    sheet1.addCell(new Number(columna,fila, totalTotalRetenciones,formatoDatos))

                    cellAux = sheet1.getCell(columna+1,fila)
                    totalTotalPagado = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.totalPagado
                    sheet1.addCell(new Number(columna+1,fila, totalTotalPagado,formatoDatos))

                    cellAux = sheet1.getCell(columna+2,fila)
                    totalTotalAnticiposContraEntrega = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.totalAnticiposContraEntrega
                    sheet1.addCell(new Number(columna+2,fila, totalTotalAnticiposContraEntrega,formatoDatos))

                    cellAux = sheet1.getCell(columna+3,fila)
                    totalTotalAnticiposContraFuturaEntrega = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+it.totalAnticiposContraFuturaEntrega
                    sheet1.addCell(new Number(columna+3,fila, totalTotalAnticiposContraFuturaEntrega,formatoDatos))

                    cellAux = sheet1.getCell(columna+4,fila)
                    totalTotalLiquidoPagable = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)+((it.totalLiquidoPagable<0)?0:it.totalLiquidoPagable)
                    sheet1.addCell(new Number(columna+4,fila, totalTotalLiquidoPagable,formatoDatos))

//                    fila++    // para pruebas
                }

                //humedad
                cellAux = sheet1.getCell(3,fila)
                def _totalKilosNetosHumedos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

                cellAux = sheet1.getCell(5,fila)
                def _totalKilosNetosSecos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

                sheet1.addCell(new Number(4,fila, (100-100*_totalKilosNetosSecos/_totalKilosNetosHumedos),formatoDatos))

                //ley
                cellAux = sheet1.getCell(9,fila)
                def _totalKilosFinosCobre = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

                sheet1.addCell(new Number(6,fila, _totalKilosFinosCobre/_totalKilosNetosSecos*100,formatoDatos))

                cellAux = sheet1.getCell(10,fila)
                def _totalKilosFinosPlomo = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

                sheet1.addCell(new Number(7,fila, _totalKilosFinosPlomo/_totalKilosNetosSecos*100,formatoDatos))

                cellAux = sheet1.getCell(11,fila)
                def _totalKilosFinosPlata = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

                sheet1.addCell(new Number(8,fila, _totalKilosFinosPlata/_totalKilosNetosSecos*10000,formatoDatos))

                fila++
            }
        }

        def columnaFinalRetenciones = 26+listaRetencionesDeLey.size()+listaRetencionesOtras.size()
        def totalLiquidaciones = fila
        for (int col=1;col<columnaFinalRetenciones;col++){
            def tret=0
            for (int fil =7;fil<totalLiquidaciones+7;fil++){
                def valor = ((sheet1.getWritableCell(col,fil).contents.isNumber())?sheet1.getWritableCell(col,fil).contents.toBigDecimal():0)
                tret+=valor
            }
            sheet1.addCell(new Number(col,totalLiquidaciones, tret,formatoTotales))
        }

        //humedad
        Cell cellAux = sheet1.getCell(3,fila)
        def __totalKilosNetosHumedos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        cellAux = sheet1.getCell(5,fila)
        def __totalKilosNetosSecos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        if(__totalKilosNetosHumedos>0)
            sheet1.addCell(new Number(4,fila, (100-100*__totalKilosNetosSecos/__totalKilosNetosHumedos),formatoTotales))

        //ley
        cellAux = sheet1.getCell(9,fila)
        def __totalKilosFinosCobre = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        sheet1.addCell(new Number(6,fila, __totalKilosFinosCobre/__totalKilosNetosSecos*100,formatoTotales))

        cellAux = sheet1.getCell(10,fila)
        def __totalKilosFinosPlomo = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        sheet1.addCell(new Number(7,fila, __totalKilosFinosPlomo/__totalKilosNetosSecos*100,formatoTotales))

        cellAux = sheet1.getCell(11,fila)
        def __totalKilosFinosPlata = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        sheet1.addCell(new Number(8,fila, __totalKilosFinosPlata/__totalKilosNetosSecos*10000,formatoTotales))

        workbook.write();
        workbook.close();
    }

    def crearDetalle = {
        /*FALTA PONER TITULO DEL ELEMENTO Y DEL PERIODO DEL REPORTE*/

        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Detalle de FORM M-02", 0)
        sheet1.setColumnView(0,50)
        sheet1.setRowView(6,500)
        for(i in 1..100)
            sheet1.setColumnView(i,12)
        SheetSettings settings = sheet1.getSettings()
        settings.setScaleFactor(70)
        settings.setPaperSize(PaperSize.LEGAL)
        settings.setOrientation(PageOrientation.LANDSCAPE)
        settings.setTopMargin(0.2)
        settings.setBottomMargin(0.4)
        settings.setLeftMargin(0.6)
        settings.setRightMargin(0.4)
        settings.setHeaderMargin(0)
        settings.setFooterMargin(0)

        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD);
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
        response.setHeader('Content-Disposition', 'Attachment;Filename="detalle_form_m02.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(3,0, "DETALLE DE FORM M-02",formatoTitulo))

        def fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
        def fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

        sheet1.addCell(new Label(0,3, "ELEMENTO:"))
        sheet1.addCell(new Label(1,3, "Complejo"))
        sheet1.addCell(new Label(0,4, "PERIODO:"))
        sheet1.addCell(new Label(1,4, "${new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)} AL ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)}"))

        sheet1.addCell(new Label(0,6, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "KILOS BRUTOS",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "KILOS NETOS SECOS",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "LEY %Zn",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "LEY %Pb",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "LEY DM Ag",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "LEY %Cu",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(18,6, "RM Bs",formatoEncabezado))

        /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA RETENCIONES DE LEY*/
        def liquidacionesTodas = LiquidacionDeComplejo.findAllByFechaDeLiquidacionGreaterThanEqualsAndFechaDeLiquidacionLessThanEquals(fechaInicial,fechaFinal)
        def listaRetencionesDeLey = retencionesComplejoJSON liquidacionesTodas,"DE LEY"
        def columna = 19
        listaRetencionesDeLey.each {
            sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
            columna++
        }

        def empresas = Empresa.list()

        def fila = 7
        def totalKilosBrutos = 0
        def totalKilosNetosSecos = 0
        def totalKilosFinosZinc = 0
        def totalKilosFinosPlomo = 0
        def totalKilosFinosPlata = 0
        def totalKilosFinosCobre = 0
        def leyZinc = 0
        def leyPlomo = 0
        def leyPlata = 0
        def leyCobre = 0
        def totalValorNeto = 0
        def totalRegalia = 0


        empresas.each { e ->
            System.err.println("***** ITERANDO SOBRE EMPRESAS: ${e.toString()}")
            //PlomoPlata
            def liquidacionesPlomoPlataAux = LiquidacionDePlomoPlata.findAllByEmpresa(e)
            def liquidacionesPlomoPlata = new ArrayList<LiquidacionDePlomoPlata>()
            liquidacionesPlomoPlataAux.each { liq ->
                if(liq.recepcionDeComplejo.fechaDeRecepcion>=fechaInicial&&liq.recepcionDeComplejo.fechaDeRecepcion<=fechaFinal)
                    liquidacionesPlomoPlata.add(liq)
            }
            //ZincPlata
            def liquidacionesZincPlataAux = LiquidacionDeZincPlata.findAllByEmpresa(e)
            def liquidacionesZincPlata = new ArrayList<LiquidacionDeZincPlata>()
            liquidacionesZincPlataAux.each { liq ->
                if(liq.recepcionDeComplejo.fechaDeRecepcion>=fechaInicial&&liq.recepcionDeComplejo.fechaDeRecepcion<=fechaFinal)
                    liquidacionesZincPlata.add(liq)
            }
            //Complejo
            def liquidacionesComplejoAux = LiquidacionDeComplejo.findAllByEmpresa(e)
            def liquidacionesComplejo = new ArrayList<LiquidacionDeComplejo>()
            liquidacionesComplejoAux.each { liq ->
                if(liq.recepcionDeComplejo.fechaDeRecepcion>=fechaInicial&&liq.recepcionDeComplejo.fechaDeRecepcion<=fechaFinal)
                    liquidacionesComplejo.add(liq)
            }
            //CobrePlata
            def liquidacionesCobrePlataAux = LiquidacionDeCobrePlata.findAllByEmpresa(e)
            def liquidacionesCobrePlata = new ArrayList<LiquidacionDeCobrePlata>()
            liquidacionesCobrePlataAux.each { liq ->
                if(liq.recepcionDeComplejo.fechaDeRecepcion>=fechaInicial&&liq.recepcionDeComplejo.fechaDeRecepcion<=fechaFinal)
                    liquidacionesCobrePlata.add(liq)
            }
            //acumulando totales para todos los minerales
            liquidacionesPlomoPlata.each { plomoPlata ->
                totalKilosBrutos+=plomoPlata.pesoBruto
                totalKilosNetosSecos+=plomoPlata.kilosNetosSecos
                totalKilosFinosPlomo+=plomoPlata.kilosFinosPlomo
                totalKilosFinosPlata+=plomoPlata.kilosFinosPlata
                totalValorNeto+=plomoPlata.valorNetoMineralEnBolivianos
                totalRegalia+=plomoPlata.regaliaMinera
            }
            liquidacionesZincPlata.each { zincPlata ->
                totalKilosBrutos+=zincPlata.pesoBruto
                totalKilosNetosSecos+=zincPlata.kilosNetosSecos
                totalKilosFinosZinc+=zincPlata.kilosFinosZinc
                totalKilosFinosPlata+=zincPlata.kilosFinosPlata
                totalValorNeto+=zincPlata.valorNetoMineralEnBolivianos
                totalRegalia+=zincPlata.regaliaMinera
            }
            liquidacionesComplejo.each { complejo ->
                totalKilosBrutos+=complejo.pesoBruto
                totalKilosNetosSecos+=complejo.kilosNetosSecos
                totalKilosFinosZinc+=complejo.kilosFinosZinc
                totalKilosFinosPlomo+=complejo.kilosFinosPlomo
                totalKilosFinosPlata+=complejo.kilosFinosPlata
                totalValorNeto+=complejo.valorNetoMineralEnBolivianos
                totalRegalia+=complejo.regaliaMinera
            }
            liquidacionesCobrePlata.each { cobre ->
                totalKilosBrutos+=cobre.pesoBruto
                totalKilosNetosSecos+=cobre.kilosNetosSecos
                totalKilosFinosCobre+=cobre.kilosFinosCobre
                totalKilosFinosPlata+=cobre.kilosFinosPlata
                totalValorNeto+=cobre.valorNetoMineralEnBolivianos
                totalRegalia+=cobre.regaliaMinera
            }
            
            leyZinc = 100*totalKilosFinosZinc/totalKilosNetosSecos
            leyPlomo = 100*totalKilosFinosPlomo/totalKilosNetosSecos
            leyPlata = 10000*totalKilosFinosPlata/totalKilosNetosSecos
            leyCobre = 100*totalKilosFinosCobre/totalKilosNetosSecos

            sheet1.addCell(new Label(0,fila, e.toString(),formatoDatos))
            sheet1.addCell(new Number(1,fila, totalKilosBrutos,formatoDatos))
            sheet1.addCell(new Number(2,fila, totalKilosNetosSecos,formatoDatos))
            sheet1.addCell(new Number(3,fila, leyZinc,formatoDatos))
            sheet1.addCell(new Number(4,fila, leyPlomo,formatoDatos))
            sheet1.addCell(new Number(5,fila, leyPlata,formatoDatos))
            sheet1.addCell(new Number(6,fila, leyCobre,formatoDatos))
            sheet1.addCell(new Number(7,fila, totalValorNeto,formatoDatos))
            sheet1.addCell(new Number(8,fila, totalRegalia,formatoDatos))

            /**
             *ADICIONAR SUMAS DE RETENCIONES DE LEY.
             *CREAR OTRO REPORTE PARA EL DETALLE DEL FORM M-02.
             *MOVER ESTE CLOSURE AL NUEVO REPORTE.
             */

            fila++
        }

        def columnaFinalRetenciones = 28+listaRetencionesDeLey.size()+listaRetencionesOtras.size()
        def totalLiquidaciones = fila
        for (int col=1;col<columnaFinalRetenciones;col++){
            def tret=0
            for (int fil =7;fil<totalLiquidaciones+7;fil++){
                def valor = ((sheet1.getWritableCell(col,fil).contents.isNumber())?sheet1.getWritableCell(col,fil).contents.toBigDecimal():0)
                tret+=valor
            }
            sheet1.addCell(new Number(col,totalLiquidaciones, tret,formatoTotales))
        }

        //humedad
        Cell cellAux = sheet1.getCell(3,fila)
        def __totalKilosNetosHumedos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        cellAux = sheet1.getCell(5,fila)
        def __totalKilosNetosSecos = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        if(__totalKilosNetosHumedos>0)
            sheet1.addCell(new Number(4,fila, (100-100*__totalKilosNetosSecos/__totalKilosNetosHumedos),formatoTotales))

        //ley
        cellAux = sheet1.getCell(9,fila)
        def __totalKilosFinosZinc = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        sheet1.addCell(new Number(6,fila, __totalKilosFinosZinc/__totalKilosNetosSecos*100,formatoTotales))

        cellAux = sheet1.getCell(10,fila)
        def __totalKilosFinosPlomo = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        sheet1.addCell(new Number(7,fila, __totalKilosFinosPlomo/__totalKilosNetosSecos*100,formatoTotales))

        cellAux = sheet1.getCell(11,fila)
        def __totalKilosFinosPlata = ((cellAux.contents.isNumber())?cellAux.contents.toBigDecimal():0)

        sheet1.addCell(new Number(8,fila, __totalKilosFinosPlata/__totalKilosNetosSecos*10000,formatoTotales))

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

    def retencionesEstanoJSON = { liquidacionesEstano,tipo ->
        List retencionesEstano = new ArrayList()
        if (liquidacionesEstano.size()>0){
            liquidacionesEstano.each {
                def liquidacionEstanoRetenciones = LiquidacionDeEstanoRetenciones.findAllByLiquidacionDeEstanoAndTipoDeRetencion(it,tipo)
                liquidacionEstanoRetenciones.each {
                    if (!retencionesEstano.contains(it.descripcion))
                        retencionesEstano.add(it.descripcion)
                }
            }
        }
        return retencionesEstano
    }

    def retencionesPlataJSON = { liquidacionesPlata,tipo ->
        List retencionesPlata = new ArrayList()
        if (liquidacionesPlata.size()>0){
            liquidacionesPlata.each {
                def liquidacionPlataRetenciones = LiquidacionDePlataRetenciones.findAllByLiquidacionDePlataAndTipoDeRetencion(it,tipo)
                liquidacionPlataRetenciones.each {
                    if (!retencionesPlata.contains(it.descripcion))
                        retencionesPlata.add(it.descripcion)
                }
            }
        }
        return retencionesPlata
    }

    def retencionesWolfranJSON = { liquidacionesWolfran,tipo ->
        List retencionesWolfran = new ArrayList()
        if (liquidacionesWolfran.size()>0){
            liquidacionesWolfran.each {
                def liquidacionWolfranRetenciones = LiquidacionDeWolfranRetenciones.findAllByLiquidacionDeWolfranAndTipoDeRetencion(it,tipo)
                liquidacionWolfranRetenciones.each {
                    if (!retencionesWolfran.contains(it.descripcion))
                        retencionesWolfran.add(it.descripcion)
                }
            }
        }
        return retencionesWolfran
    }

    def retencionesAntimonioJSON = { liquidacionesAntimonio,tipo ->
        List retencionesAntimonio = new ArrayList()
        if (liquidacionesAntimonio.size()>0){
            liquidacionesAntimonio.each {
                def liquidacionAntimonioRetenciones = LiquidacionDeAntimonioRetenciones.findAllByLiquidacionDeAntimonioAndTipoDeRetencion(it,tipo)
                liquidacionAntimonioRetenciones.each {
                    if (!retencionesAntimonio.contains(it.descripcion))
                        retencionesAntimonio.add(it.descripcion)
                }
            }
        }
        return retencionesAntimonio
    }

    def retencionesComplejoJSON = { liquidacionesComplejo,tipo ->
        List retencionesComplejo = new ArrayList()
        if (liquidacionesComplejo.size()>0){
            liquidacionesComplejo.each {
                def liquidacionComplejoRetenciones = LiquidacionDeComplejoRetenciones.findAllByLiquidacionDeComplejoAndTipoDeRetencion(it,tipo)
                liquidacionComplejoRetenciones.each {
                    if (!retencionesComplejo.contains(it.descripcion))
                        retencionesComplejo.add(it.descripcion)
                }
            }
        }
        return retencionesComplejo
    }

    def retencionesPlomoPlataJSON = { liquidacionesPlomoPlata,tipo ->
        List retencionesPlomoPlata = new ArrayList()
        if (liquidacionesPlomoPlata.size()>0){
            liquidacionesPlomoPlata.each {
                def liquidacionPlomoPlataRetenciones = LiquidacionDePlomoPlataRetenciones.findAllByLiquidacionDePlomoPlataAndTipoDeRetencion(it,tipo)
                liquidacionPlomoPlataRetenciones.each {
                    if (!retencionesPlomoPlata.contains(it.descripcion))
                        retencionesPlomoPlata.add(it.descripcion)
                }
            }
        }
        return retencionesPlomoPlata
    }

    def retencionesZincPlataJSON = { liquidacionesZincPlata,tipo ->
        List retencionesZincPlata = new ArrayList()
        if (liquidacionesZincPlata.size()>0){
            liquidacionesZincPlata.each {
                def liquidacionZincPlataRetenciones = LiquidacionDeZincPlataRetenciones.findAllByLiquidacionDeZincPlataAndTipoDeRetencion(it,tipo)
                liquidacionZincPlataRetenciones.each {
                    if (!retencionesZincPlata.contains(it.descripcion))
                        retencionesZincPlata.add(it.descripcion)
                }
            }
        }
        return retencionesZincPlata
    }

    def retencionesCobrePlataJSON = { liquidacionesCobrePlata,tipo ->
        List retencionesCobrePlata = new ArrayList()
        if (liquidacionesCobrePlata.size()>0){
            liquidacionesCobrePlata.each {
                def liquidacionCobrePlataRetenciones = LiquidacionDeCobrePlataRetenciones.findAllByLiquidacionDeCobrePlataAndTipoDeRetencion(it,tipo)
                liquidacionCobrePlataRetenciones.each {
                    if (!retencionesCobrePlata.contains(it.descripcion))
                        retencionesCobrePlata.add(it.descripcion)
                }
            }
        }
        return retencionesCobrePlata
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
