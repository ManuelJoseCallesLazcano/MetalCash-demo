package org.socymet.org.socymet.reportes

import jxl.SheetSettings
import jxl.Workbook
import jxl.format.Alignment
import jxl.format.PageOrientation
import jxl.format.PaperSize
import jxl.write.DateFormat
import jxl.write.DateTime
import jxl.write.Label
import jxl.write.Number
import jxl.write.NumberFormat
import jxl.write.WritableCellFormat
import jxl.write.WritableFont
import jxl.write.WritableSheet
import jxl.write.WritableWorkbook
import org.socymet.liquidacion.LiquidacionDeOro
import org.socymet.liquidacion.LiquidacionDeOroRetenciones
import org.socymet.proveedor.Empresa
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
class PlanillaDeLiquidacionOroController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond PlanillaDeLiquidacionOro.list(params), model:[planillaDeLiquidacionOroInstanceCount: PlanillaDeLiquidacionOro.count()]
    }

    def show(PlanillaDeLiquidacionOro planillaDeLiquidacionOroInstance) {
        respond planillaDeLiquidacionOroInstance
    }

    def create() {
        respond new PlanillaDeLiquidacionOro(params)
    }

    @Transactional
    def save(PlanillaDeLiquidacionOro planillaDeLiquidacionOroInstance) {
        if (planillaDeLiquidacionOroInstance == null) {
            notFound()
            return
        }

        if (planillaDeLiquidacionOroInstance.hasErrors()) {
            respond planillaDeLiquidacionOroInstance.errors, view:'create'
            return
        }

        planillaDeLiquidacionOroInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'planillaDeLiquidacionOro.label', default: 'PlanillaDeLiquidacionOro'), planillaDeLiquidacionOroInstance.id])
                redirect planillaDeLiquidacionOroInstance
            }
            '*' { respond planillaDeLiquidacionOroInstance, [status: CREATED] }
        }
    }

    def edit(PlanillaDeLiquidacionOro planillaDeLiquidacionOroInstance) {
        respond planillaDeLiquidacionOroInstance
    }

    @Transactional
    def update(PlanillaDeLiquidacionOro planillaDeLiquidacionOroInstance) {
        if (planillaDeLiquidacionOroInstance == null) {
            notFound()
            return
        }

        if (planillaDeLiquidacionOroInstance.hasErrors()) {
            respond planillaDeLiquidacionOroInstance.errors, view:'edit'
            return
        }

        planillaDeLiquidacionOroInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'PlanillaDeLiquidacionOro.label', default: 'PlanillaDeLiquidacionOro'), planillaDeLiquidacionOroInstance.id])
                redirect planillaDeLiquidacionOroInstance
            }
            '*'{ respond planillaDeLiquidacionOroInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(PlanillaDeLiquidacionOro planillaDeLiquidacionOroInstance) {

        if (planillaDeLiquidacionOroInstance == null) {
            notFound()
            return
        }

        planillaDeLiquidacionOroInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'PlanillaDeLiquidacionOro.label', default: 'PlanillaDeLiquidacionOro'), planillaDeLiquidacionOroInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'planillaDeLiquidacionOro.label', default: 'PlanillaDeLiquidacionOro'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def crearReporte = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.ARIAL, 7, WritableFont.NO_BOLD);
        WritableFont courier6PlainFont = new WritableFont(WritableFont.ARIAL, 7, WritableFont.NO_BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.ARIAL, 7, WritableFont.NO_BOLD);
        WritableFont courier8BoldFont = new WritableFont(WritableFont.ARIAL, 7, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);

        WritableCellFormat formatoEncabezado = new WritableCellFormat (arial10BoldFont);
        formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)
        formatoEncabezado.setWrap(true)
        //formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
        formatoEncabezado.setAlignment(Alignment.CENTRE)

        WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoDatos.setFont(courier8PlainFont)
        formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        WritableCellFormat formatoDatosHumedad = new WritableCellFormat (new NumberFormat("###,##0.000000"));
        formatoDatosHumedad.setFont(courier8PlainFont)
        formatoDatosHumedad.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        WritableCellFormat formatoTotales = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoTotales.setFont(courier8BoldFont)
        formatoTotales.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)

        WritableCellFormat formatoTotalesHumedad = new WritableCellFormat (new NumberFormat("###,##0.000000"));
        formatoTotalesHumedad.setFont(courier8BoldFont)
        formatoTotalesHumedad.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)

        WritableCellFormat formatoInfoReporte = new WritableCellFormat (arial14BoldFont);
        formatoInfoReporte.setAlignment(Alignment.CENTRE)

        WritableCellFormat formatoTitulo = new WritableCellFormat (arial16BoldFont);
        formatoTitulo.setAlignment(Alignment.CENTRE)

        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        WritableSheet sheet1 = workbook.createSheet("Planilla Liq Oro", 0)

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

        def empresa = Empresa.get(Integer.parseInt(""+params.empresa.id))

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="planilla_liquidacion_lamas_'+empresa.nombreDeEmpresa.toLowerCase().replace(' ','_')+'.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(3,0, "HISTORIAL DE COMPRAS",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def tipoReporte = ""+params.tipoReporte

        def liquidacionesOro=null

        def retencionesDeLey=new ArrayList<String>()
        def retencionesOtras=new ArrayList<String>()

        if (tipoReporte.equals("fechasEmpresa")){
            def fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            def fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)
            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            def identificador = ""
            if(!empresa.nim.equals("")&&!empresa.nim.equals("0"))
                identificador = "NIM: ${empresa.nim}"
            else{
                if(!empresa.nit.equals("")&&!empresa.nit.equals("0"))
                    identificador = "NIT: ${empresa.nit}"
                else
                    identificador = "-"
            }

            sheet1.addCell(new Label(21,1, "PLANILLA DE LIQUIDACION DE LAMAS ${empresa.toString()}",formatoTitulo))
            sheet1.addCell(new Label(21,3, "CORRESPONDIENTE A LAS FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))
            sheet1.addCell(new Label(21,4, identificador,formatoInfoReporte))

            sheet1.addCell(new Label(29,4, "${empresa.concesion}",formatoInfoReporte))
            sheet1.addCell(new Label(51,4, "${empresa.municipio}",formatoInfoReporte))
            sheet1.addCell(new Label(52,4, "${empresa.codigoMunicipio}",formatoInfoReporte))

            //def liquidacionesCm = LiquidacionDeOro.findAllByFechaDeLiquidacionBetween(fechaInicial,fechaFinal,[sort: 'lote'])
            def liquidacionesCm = LiquidacionDeOro.list([sort: 'lote'])
            liquidacionesOro=new ArrayList<LiquidacionDeOro>()
            liquidacionesCm.each {
                if(it.recepcionDeOro.fechaDeRecepcion>=fechaInicial && it.recepcionDeOro.fechaDeRecepcion<=fechaFinal && it.recepcionDeOro.empresa.id==empresa.id)
                    liquidacionesOro.add(it)
            }
            System.out.println("*** RESULTADOS DE LAMAS: ${liquidacionesOro.size()}")
        }

        /*CONTROLAR SI SON null LAS LISTAS OBTENIDAS DE LIQUIDACIONES*/

        /*GENERANDO LISTA GENERAL DE RETENCIONES DE LEY*/
        if(liquidacionesOro){
            def listaRetencionesDeLeyOro = retencionesOroJSON liquidacionesOro,"DE LEY"
            listaRetencionesDeLeyOro.each {
                if(!retencionesDeLey.contains(it.toString()))
                    retencionesDeLey.add(it)
            }
        }
        /*FIN - GENERANDO LISTA GENERAL DE RETENCIONES DE LEY*/

        /*GENERANDO LISTA GENERAL DE OTRAS RETENCIONES*/
        if(liquidacionesOro){
            def listaRetencionesDeLeyOro = retencionesOroJSON liquidacionesOro,"OTRA"
            listaRetencionesDeLeyOro.each {
                if(!retencionesOtras.contains(it.toString()))
                    retencionesOtras.add(it)
            }
        }
        /*FIN - GENERANDO LISTA GENERAL DE OTRAS RETENCIONES*/

        sheet1.addCell(new Label(0,6, "FEC. REP.",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "COT. DIA Sn",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "COT. DIA Sb",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "COT. DIA WO3",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "COT. DIA Zn",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "COT. DIA Pb",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "COT. DIA Ag",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "FEC. LIQ.",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "NOMBRE",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "ELEMENTO",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(13,6, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "TOTAL TARA",formatoEncabezado))
        sheet1.addCell(new Label(15,6, "K. N. H.",formatoEncabezado))
        sheet1.addCell(new Label(16,6, "% H2O",formatoEncabezado))
        sheet1.addCell(new Label(17,6, "K. N. S.",formatoEncabezado))
        sheet1.addCell(new Label(18,6, "LEY %Sn",formatoEncabezado))
        sheet1.addCell(new Label(19,6, "LEY %Sb",formatoEncabezado))
        sheet1.addCell(new Label(20,6, "LEY %WO3",formatoEncabezado))
        sheet1.addCell(new Label(21,6, "LEY gr/TM",formatoEncabezado))
        sheet1.addCell(new Label(22,6, "LEY %Pb",formatoEncabezado))
        sheet1.addCell(new Label(23,6, "LEY DM Ag",formatoEncabezado))
        sheet1.addCell(new Label(24,6, "LEY %As",formatoEncabezado))
        sheet1.addCell(new Label(25,6, "K. F. Sn",formatoEncabezado))
        sheet1.addCell(new Label(26,6, "K. F. Sb",formatoEncabezado))
        sheet1.addCell(new Label(27,6, "K. F. WO3",formatoEncabezado))
        sheet1.addCell(new Label(28,6, "K. F.",formatoEncabezado))
        sheet1.addCell(new Label(29,6, "K. F. Pb",formatoEncabezado))
        sheet1.addCell(new Label(30,6, "K. F. Ag",formatoEncabezado))
        sheet1.addCell(new Label(31,6, "K. F. As",formatoEncabezado))
        sheet1.addCell(new Label(32,6, "COT. OF. Sn",formatoEncabezado))
        sheet1.addCell(new Label(33,6, "COT. OF. Sb",formatoEncabezado))
        sheet1.addCell(new Label(34,6, "COT. OF. WO3",formatoEncabezado))
        sheet1.addCell(new Label(35,6, "COT. OF. Zn",formatoEncabezado))
        sheet1.addCell(new Label(36,6, "COT. OF. Pb",formatoEncabezado))
        sheet1.addCell(new Label(37,6, "COT. OF. Ag",formatoEncabezado))
        sheet1.addCell(new Label(38,6, "VALOR OF. BRUTO",formatoEncabezado))
        sheet1.addCell(new Label(39,6, "ALIC. Sn %",formatoEncabezado))
        sheet1.addCell(new Label(40,6, "ALIC. Sb %",formatoEncabezado))
        sheet1.addCell(new Label(41,6, "ALIC. WO3 %",formatoEncabezado))
        sheet1.addCell(new Label(42,6, "ALIC. Zn %",formatoEncabezado))
        sheet1.addCell(new Label(43,6, "ALIC. Pb %",formatoEncabezado))
        sheet1.addCell(new Label(44,6, "ALIC. Ag %",formatoEncabezado))
        sheet1.addCell(new Label(45,6, "VALOR NETO \$us",formatoEncabezado))
        sheet1.addCell(new Label(46,6, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(47,6, "BONO CALIDAD",formatoEncabezado))
        sheet1.addCell(new Label(48,6, "BONO INCENTIVO",formatoEncabezado))
        sheet1.addCell(new Label(49,6, "VALOR DE COMPRA",formatoEncabezado))
        sheet1.addCell(new Label(50,6, "RM",formatoEncabezado))
        /*INSERTANDO CABECERA PARA LAS RETENCIONES DE LEY*/
        def columna = 51
        retencionesDeLey.each {
            sheet1.addCell(new Label(columna,6,it,formatoEncabezado))
            columna++
        }
        sheet1.addCell(new Label(columna,6, "TOTAL RET. DE LEY",formatoEncabezado))
        columna++
        /*FIN - INSERTANDO CABECERA PARA LAS RETENCIONES DE LEY*/

        /*INSERTANDO CABECERA PARA LAS RETENCIONES DE LEY*/
        retencionesOtras.each {
            sheet1.addCell(new Label(columna,6,it,formatoEncabezado))
            columna++
        }
        sheet1.addCell(new Label(columna,6, "TOTAL OTRAS RET.",formatoEncabezado))
        columna++
        /*FIN - INSERTANDO CABECERA PARA LAS RETENCIONES DE LEY*/

        sheet1.addCell(new Label(columna,6, "TOTAL RET.",formatoEncabezado))
        sheet1.addCell(new Label(columna+1,6, "TOTAL PAGADO",formatoEncabezado))
        sheet1.addCell(new Label(columna+2,6, "ANTICIPO/ENTREGA",formatoEncabezado))
        sheet1.addCell(new Label(columna+3,6, "ANTICIPO/F. ENTREGA",formatoEncabezado))
        sheet1.addCell(new Label(columna+4,6, "LIQUIDO PAGABLE",formatoEncabezado))
        sheet1.addCell(new Label(columna+5,6, "CANC. TRANSPORTE",formatoEncabezado))
        sheet1.addCell(new Label(columna+6,6, "CANC. LABORAT.",formatoEncabezado))

        //def colsNot=[16,18,19,20,21,22,23,24,32,33,34,35,36,37,39,40,41,42,43,44]
        def colsNot=[]
        def filasNot=new ArrayList()
        def inicioCiclo=0
        def finCiclo=0
        def filaTotal=0
        def totalLiquidacionesEstano = 0
        def totalLiquidacionesPlata = 0
        def totalLiquidacionesWolfran = 0
        def totalLiquidacionesAntimonio = 0
        def totalLiquidacionesOro = 0
        def totalLiquidacionesPlomoPlata = 0
        def totalLiquidacionesOroPlata = 0
        def columnaFinalRetenciones = 60+retencionesDeLey.size()+retencionesOtras.size()
        def fila = 7

        if(liquidacionesOro){
            def totalKilosNetosHumedosOro=0
            def totalKilosNetosSecosOro=0
            def totalKilosFinosOroOro=0
            def totalKilosFinosPlomoOro=0
            def totalKilosFinosPlataOro=0

            liquidacionesOro.each {
                totalKilosNetosHumedosOro+=it.kilosNetosHumedos
                totalKilosNetosSecosOro+=it.kilosNetosSecos
                totalKilosFinosOroOro+=it.kilosFinosOro
                totalKilosFinosPlomoOro+=0
                totalKilosFinosPlataOro+=0

                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new Number(1,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(2,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(3,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(4,fila, it.recepcionDeOro.cotizacionDiariaDeMinerales.zinc,formatoDatos)) //ZN
                sheet1.addCell(new Number(5,fila, it.recepcionDeOro.cotizacionDiariaDeMinerales.plomo,formatoDatos)) //PB
                sheet1.addCell(new Number(6,fila, it.recepcionDeOro.cotizacionDiariaDeMinerales.plata,formatoDatos)) //AG
                sheet1.addCell(new DateTime(7,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(8,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(9,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(10,fila, "Oro",formatoDatos))
                sheet1.addCell(new Label(11,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(12,fila, Float.parseFloat(it.cantidadDeSacos),formatoDatos))
                sheet1.addCell(new Number(13,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(14,fila, it.porcentajeMermaFinal,formatoDatos))
                sheet1.addCell(new Number(15,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(16,fila, it.porcentajeHumedadFinal,formatoDatos))
                sheet1.addCell(new Number(17,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(18,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(19,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(20,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(21,fila, it.porcentajeOroFinal,formatoDatos)) //ZN
                sheet1.addCell(new Number(22,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(23,fila, 0,formatoDatos)) //AG
                sheet1.addCell(new Number(24,fila, 0,formatoDatos)) //AS
                sheet1.addCell(new Number(25,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(26,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(27,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(28,fila, it.kilosFinosOro,formatoDatos)) //ZN
                sheet1.addCell(new Number(29,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(30,fila, 0,formatoDatos)) //AG
                sheet1.addCell(new Number(31,fila, 0,formatoDatos)) //AS
                sheet1.addCell(new Number(32,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(33,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(34,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(35,fila, it.recepcionDeOro.cotizacionQuincenalDeMinerales.zinc,formatoDatos)) //ZN
                sheet1.addCell(new Number(36,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(37,fila, 0,formatoDatos)) //AG
                sheet1.addCell(new Number(38,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(39,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(40,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(41,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(42,fila, it.recepcionDeOro.alicuota.zinc,formatoDatos)) //ZN
                sheet1.addCell(new Number(43,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(44,fila, 0,formatoDatos)) //AG
                sheet1.addCell(new Number(45,fila, it.valorNetoMineral,formatoDatos))
                sheet1.addCell(new Number(46,fila, it.valorNetoMineralEnBolivianos,formatoDatos))
                sheet1.addCell(new Number(47,fila, it.bonoCalidad,formatoDatos))
                sheet1.addCell(new Number(48,fila, it.bonoIncentivo,formatoDatos))
                sheet1.addCell(new Number(49,fila, it.valorDeCompra,formatoDatos))
                sheet1.addCell(new Number(50,fila, it.regaliaMinera,formatoDatos))

                columna=51
                //DESPLIEGUE DE RETENCIONES DE LEY
                def retencionesDeLeyLiquidacion = LiquidacionDeOroRetenciones.findAllByLiquidacionDeOroAndTipoDeRetencion(it,"DE LEY")
                def subtotalRetencionesDeLey=it.regaliaMinera.doubleValue()

                for(int i=0;i<retencionesDeLey.size();i++){
                    def vr = valorRetencion(retencionesDeLey.get(i), retencionesDeLeyLiquidacion,retencionesDeLeyLiquidacion.size())
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesDeLey+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesDeLey,formatoDatos))
                columna++

                //DESPLIEGUE DE RETENCIONES DE LEY
                def retencionesOtrasLiquidacion = LiquidacionDeOroRetenciones.findAllByLiquidacionDeOroAndTipoDeRetencion(it,"OTRA")
                def subtotalRetencionesOtras=0

                for(int i=0;i<retencionesOtras.size();i++){
                    def vr = valorRetencion(retencionesOtras.get(i), retencionesOtrasLiquidacion,retencionesOtrasLiquidacion.size())
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesOtras+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesOtras,formatoDatos))
                columna++

                sheet1.addCell(new Number(columna,fila, it.totalRetenciones,formatoDatos))
                sheet1.addCell(new Number(columna+1,fila, it.totalPagado,formatoDatos))
                sheet1.addCell(new Number(columna+2,fila, it.totalAnticiposContraEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+3,fila, it.totalAnticiposContraFuturaEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+4,fila, it.totalLiquidoPagable,formatoDatos))
                sheet1.addCell(new Number(columna+5,fila, it.recepcionDeOro.costoDeTransporte,formatoDatos))
                sheet1.addCell(new Number(columna+6,fila, it.totalCostoLaboratorio,formatoDatos))

                fila++
            }
            //hacer un espacio para detallar el siguiente elemento
//            fila++
//            //SUBTOTALES COMPLEJO
//            totalLiquidacionesOro = liquidacionesOro.size()
//            inicioCiclo=7+((liquidacionesEstano)?totalLiquidacionesEstano+2:0)+((liquidacionesPlata)?totalLiquidacionesPlata+2:0)+((liquidacionesWolfran)?totalLiquidacionesWolfran+2:0)+((liquidacionesAntimonio)?totalLiquidacionesAntimonio+2:0)+((liquidacionesPlomoPlata)?totalLiquidacionesPlomoPlata+2:0)+((liquidacionesOroPlata)?totalLiquidacionesOroPlata+2:0)
//            finCiclo=inicioCiclo+totalLiquidacionesOro
//            filaTotal=finCiclo
//            filasNot.add(filaTotal)
//
//            sheet1.addCell(new Label(9,filaTotal, "SUBTOTALES",formatoEncabezado))
//            for (int col=12;col<columnaFinalRetenciones;col++){
//                def tret=0
//                for (int fil =inicioCiclo;fil<finCiclo;fil++){
//                    def contenido=(sheet1.getWritableCell(col,fil).contents.equals(""))?"0":sheet1.getWritableCell(col,fil).contents
//                    def valor = Double.parseDouble(contenido)
//                    tret+=(valor<0)?0:valor
//                }
//                if (!colsNot.contains(col))
//                    sheet1.addCell(new Number(col,filaTotal, tret,formatoTotales))
//            }
//
//            //humedad
//            sheet1.addCell(new Number(16,filaTotal, (totalKilosNetosSecosOro/totalKilosNetosHumedosOro*100-100)*-1,formatoTotales))
//            //ley
//            sheet1.addCell(new Number(21,filaTotal, totalKilosFinosOroOro/totalKilosNetosSecosOro*100,formatoTotales))
//            sheet1.addCell(new Number(22,filaTotal, totalKilosFinosPlomoOro/totalKilosNetosSecosOro*100,formatoTotales))
//            sheet1.addCell(new Number(23,filaTotal, 10000*totalKilosFinosPlataOro/totalKilosNetosSecosOro,formatoTotales))

//            fila++
        }
        //def colsNot=[16,18,19,20,21,22,23,30,31,32,33,34,35,37,38,39,40,41,42]
        columnaFinalRetenciones = 60+retencionesDeLey.size()+retencionesOtras.size()
        def totalLiquidaciones = fila+1
        sheet1.addCell(new Label(9,totalLiquidaciones-1, "TOTALES",formatoEncabezado))
        for (int col=12;col<columnaFinalRetenciones;col++){
            def tret=0
            for (int fil =7;fil<totalLiquidaciones+7;fil++){
                def contenido=(sheet1.getWritableCell(col,fil).contents.equals(""))?"0":sheet1.getWritableCell(col,fil).contents
                def valor = Double.parseDouble(contenido)
                if (!filasNot.contains(fil))
                    tret+=(valor<0)?0:valor
            }
            if (!colsNot.contains(col))
                sheet1.addCell(new Number(col,totalLiquidaciones-1, tret,formatoTotales))
        }

        def f = totalLiquidaciones-1
        def totalKilosNetosHumedos=Double.parseDouble((sheet1.getWritableCell(15,f).contents.equals(""))?"0":sheet1.getWritableCell(15,f).contents)
        def totalKilosNetosSecos=Double.parseDouble((sheet1.getWritableCell(17,f).contents.equals(""))?"0":sheet1.getWritableCell(17,f).contents)
        def totalKilosFinosOro=Double.parseDouble((sheet1.getWritableCell(28,f).contents.equals(""))?"0":sheet1.getWritableCell(28,f).contents)
        def totalKilosFinosPlomo=Double.parseDouble((sheet1.getWritableCell(29,f).contents.equals(""))?"0":sheet1.getWritableCell(29,f).contents)
        def totalKilosFinosPlata=Double.parseDouble((sheet1.getWritableCell(30,f).contents.equals(""))?"0":sheet1.getWritableCell(30,f).contents)

        System.out.println("f=${f}")
        System.out.println("totalKilosNetosHumedos=${totalKilosNetosHumedos}")
        System.out.println("totalKilosNetosSecos=${totalKilosNetosSecos}")
        System.out.println("totalKilosFinosOro=${totalKilosFinosOro}")
        System.out.println("totalKilosFinosPlomo=${totalKilosFinosPlomo}")
        System.out.println("totalKilosFinosPlata=${totalKilosFinosPlata}")

        //humedad
        //sheet1.addCell(new Number(16,f, 100-totalKilosNetosSecos/totalKilosNetosHumedos*100,formatoTotalesHumedad))
        //humedad + merma
//        sheet1.addCell(new Number(16,f, 100-totalKilosNetosSecos/totalKilosNetosHumedos*100,formatoTotalesHumedad))
        sheet1.addCell(new Number(16,f, 100-totalKilosNetosSecos/totalKilosNetosHumedos*100,formatoTotales))
        //ley
//        sheet1.addCell(new Number(21,f, totalKilosFinosOro/totalKilosNetosSecos*100,formatoTotales))
//        sheet1.addCell(new Number(22,f, totalKilosFinosPlomo/totalKilosNetosSecos*100,formatoTotales))
//        sheet1.addCell(new Number(23,f, 10000*totalKilosFinosPlata/totalKilosNetosSecos,formatoTotales))
        sheet1.addCell(new Number(21,f, 100*totalKilosFinosOro/totalKilosNetosSecos,formatoTotales))

        //mejorar la facha del reporte
        //def columnasOcultas = [columnaFinalRetenciones-1,columnaFinalRetenciones-2,columnaFinalRetenciones-4,columnaFinalRetenciones-5,columnaFinalRetenciones-6,columnaFinalRetenciones-8,columnaFinalRetenciones-retencionesOtras.size()-9,49,48,47,45,44,43,42,41,40,39,38,37,36,35,34,33,32,31,27,26,25,24,20,19,18,15,14,12,10,8,7,6,5,4,3,2,1]
//        def columnasOcultas = [columnaFinalRetenciones-1,columnaFinalRetenciones-2,columnaFinalRetenciones-4,columnaFinalRetenciones-6,columnaFinalRetenciones-8,columnaFinalRetenciones-retencionesOtras.size()-9,49,48,47,45,44,43,42,41,40,39,38,37,36,35,34,33,32,31,27,26,25,24,20,19,18,15,14,12,10,8,7,6,5,4,3,2,1]
        def columnasOcultas = [columnaFinalRetenciones-1,columnaFinalRetenciones-2,columnaFinalRetenciones-6,columnaFinalRetenciones-8,columnaFinalRetenciones-retencionesOtras.size()-9,49,48,47,45,44,43,42,41,40,39,38,37,36,35,34,33,32,31,30,29,27,26,25,24,23,22,20,19,18,15,14,12,10,8,7,6,5,4,3,2,1]

        for(i in columnasOcultas){
            //System.out.println("Eliminando columna ${i}")
            sheet1.removeColumn(i)
        }
        sheet1.setRowView(6,500)
        for(i in 0..100)
            sheet1.setColumnView(i,9)
        sheet1.setColumnView(1,25)
        sheet1.setColumnView(2,12)

        workbook.write()
        workbook.close()
    }

    def retencionesOroJSON = { liquidacionesOro,tipo ->
        List retencionesOro = new ArrayList()
        if (liquidacionesOro.size()>0){
            liquidacionesOro.each {
                def liquidacionOroRetenciones = LiquidacionDeOroRetenciones.findAllByLiquidacionDeOroAndTipoDeRetencion(it,tipo)
                liquidacionOroRetenciones.each {
                    if (!retencionesOro.contains(it.descripcion))
                        retencionesOro.add(it.descripcion)
                }
            }
        }
        return retencionesOro
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
}
