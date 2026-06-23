package org.socymet.reportesAcopiadoras

import jxl.SheetSettings
import jxl.Workbook
import jxl.format.Alignment
import jxl.format.PageOrientation
import jxl.format.PaperSize
import jxl.write.DateFormat
import jxl.write.DateTime
import jxl.write.Formula
import jxl.write.Label
import jxl.write.Number
import jxl.write.NumberFormat
import jxl.write.WritableCellFormat
import jxl.write.WritableFont
import jxl.write.WritableSheet
import jxl.write.WritableWorkbook
import org.socymet.liquidacion.LiquidacionDeComplejo
import org.socymet.liquidacion.LiquidacionDeComplejoRetenciones
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.seguridad.SecUser
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_RECEPCION','ROLE_LIQUIDACION'])
class LotesLiquidadosController {
    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond LotesLiquidados.list(params), model:[lotesLiquidadosInstanceCount: LotesLiquidados.count()]
    }

    def show(LotesLiquidados lotesLiquidadosInstance) {
        respond lotesLiquidadosInstance
    }

    def create() {
        respond new LotesLiquidados(params)
    }

    @Transactional
    def save(LotesLiquidados lotesLiquidadosInstance) {
        if (lotesLiquidadosInstance == null) {
            notFound()
            return
        }

        if (lotesLiquidadosInstance.hasErrors()) {
            respond lotesLiquidadosInstance.errors, view:'create'
            return
        }

        lotesLiquidadosInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'lotesLiquidados.label', default: 'LotesLiquidados'), lotesLiquidadosInstance.id])
                redirect lotesLiquidadosInstance
            }
            '*' { respond lotesLiquidadosInstance, [status: CREATED] }
        }
    }

    def edit(LotesLiquidados lotesLiquidadosInstance) {
        respond lotesLiquidadosInstance
    }

    @Transactional
    def update(LotesLiquidados lotesLiquidadosInstance) {
        if (lotesLiquidadosInstance == null) {
            notFound()
            return
        }

        if (lotesLiquidadosInstance.hasErrors()) {
            respond lotesLiquidadosInstance.errors, view:'edit'
            return
        }

        lotesLiquidadosInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'LotesLiquidados.label', default: 'LotesLiquidados'), lotesLiquidadosInstance.id])
                redirect lotesLiquidadosInstance
            }
            '*'{ respond lotesLiquidadosInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(LotesLiquidados lotesLiquidadosInstance) {

        if (lotesLiquidadosInstance == null) {
            notFound()
            return
        }

        lotesLiquidadosInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'LotesLiquidados.label', default: 'LotesLiquidados'), lotesLiquidadosInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'lotesLiquidados.label', default: 'LotesLiquidados'), params.id])
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

        WritableSheet sheet1 = workbook.createSheet("Reporte de Lotes Liquidados", 0)
        sheet1.setRowView(6,500)
        for(i in 0..100)
            sheet1.setColumnView(i,8)
//        sheet1.setColumnView(0,11)//ajustar ancho de columnas (columna,ancho)
//        sheet1.setColumnView(1,11)//ajustar ancho de columnas (columna,ancho)
//        sheet1.setColumnView(2,11)
        sheet1.setColumnView(1,30)
        sheet1.setColumnView(2,30)
        sheet1.setColumnView(3,15)

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
        response.setHeader('Content-Disposition', 'Attachment;Filename="reporte_lotes_liquidados.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(3,0, "REPORTE DE LOTES LIQUIDADOS",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def tipoReporte = ""+params.tipoReporte
        def empresa=null
        def fechaInicial=null
        def fechaFinal=null
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        def deposito = usuarioActual.deposito
        System.out.println("DEPOSITO: ${deposito}")
//        if (params.deposito.id!=9999)
//            deposito= Deposito.get(params.deposito.id)

        def liquidacionesComplejo=null

        def retencionesDeLey=new ArrayList<String>()
        def retencionesOtras=new ArrayList<String>()

        if (tipoReporte.equals("fechas")){
            //empresa = Empresa.get(Integer.parseInt(""+params.empresa.id))
            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(2,2, "ENTRE FECHAS:",formatoInfoReporte))
            sheet1.addCell(new Label(3,2, "${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            if (deposito)
                liquidacionesComplejo = LiquidacionDeComplejo.findAllByFechaDeLiquidacionBetweenAndDeposito(fechaInicial,fechaFinal,deposito,[sort: 'nombreEmpresa'])
            else
                liquidacionesComplejo = LiquidacionDeComplejo.findAllByFechaDeLiquidacionBetween(fechaInicial,fechaFinal,[sort: 'nombreEmpresa'])

            System.out.println("*** RESULTADOS DE COMPLEJO: ${liquidacionesComplejo.size()}")
        }
        if (tipoReporte.equals("fechasEmpresa")){
            empresa = Empresa.get(Integer.parseInt(""+params.empresa.id))

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)
            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(2,3, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(3,3, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(2,2, "ENTRE FECHAS:",formatoInfoReporte))
            sheet1.addCell(new Label(3,2, "${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            if (deposito)
                liquidacionesComplejo = LiquidacionDeComplejo.findAllByFechaDeLiquidacionBetweenAndDepositoAndEmpresa(fechaInicial,fechaFinal,deposito,empresa,[sort: 'nombreEmpresa'])
            else
                liquidacionesComplejo = LiquidacionDeComplejo.findAllByFechaDeLiquidacionBetweenAndEmpresa(fechaInicial,fechaFinal,empresa,[sort: 'nombreEmpresa'])

            System.out.println("*** RESULTADOS DE COMPLEJO: ${liquidacionesComplejo.size()}")
        }

        /*GENERANDO LISTA GENERAL DE RETENCIONES DE LEY*/
        if(liquidacionesComplejo){
            def listaRetencionesDeLeyComplejo = retencionesComplejoJSON liquidacionesComplejo,"DE LEY"
            listaRetencionesDeLeyComplejo.each {
                if(!retencionesDeLey.contains(it.toString()))
                    retencionesDeLey.add(it)
            }
        }

        if(liquidacionesComplejo){
            def listaRetencionesDeLeyComplejo = retencionesComplejoJSON liquidacionesComplejo,"OTRA"
            listaRetencionesDeLeyComplejo.each {
                if(!retencionesOtras.contains(it.toString()))
                    retencionesOtras.add(it)
            }
        }

        sheet1.addCell(new Label(0,6, "FEC. LIQ.",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "NOMBRE",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "MERMA",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "K. N. H.",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "% H2O",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "K. N. S.",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "LEY %Zn",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "LEY %Pb",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "LEY %Ag",formatoEncabezado))
        sheet1.addCell(new Label(13,6, "K. F. Zn",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "K. F. Pb",formatoEncabezado))
        sheet1.addCell(new Label(15,6, "K. F. Ag",formatoEncabezado))
        sheet1.addCell(new Label(16,6, "VALOR NETO \$us",formatoEncabezado))
        sheet1.addCell(new Label(17,6, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(18,6, "RET. LEY",formatoEncabezado))
        sheet1.addCell(new Label(19,6, "OTRAS RET.",formatoEncabezado))
        sheet1.addCell(new Label(20,6, "ANT./ENT.",formatoEncabezado))
        sheet1.addCell(new Label(21,6, "LIQ. PAGAB.",formatoEncabezado))

        def fila = 7

        def totalCantidadDeSacos=0
        def totalPesoBruto=0
        def totalTotalTara=0
        def totalKilosNetosHumedos=0
        def totalHumedad=0
        def totalKilosNetosSecos=0
        def totalPorcentajeZinc=0
        def totalPorcentajePlomo=0
        def totalPorcentajePlata=0
        def totalKilosFinosZinc=0
        def totalKilosFinosPlomo=0
        def totalKilosFinosPlata=0
        def totalValorNetoMineral=0
        def totalValorNetoMineralEnBolivianos=0
        def totalRetencionesDeLey=0
        def totalOtrasRetenciones=0
        def totalAnticiposContraEntrega=0
        def totalLiquidoPagable=0

        if(liquidacionesComplejo){
            liquidacionesComplejo.each {
                sheet1.addCell(new DateTime(0,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(1,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(2,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(3,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(4,fila, Double.parseDouble(it.cantidadDeSacos),formatoDatos))
                sheet1.addCell(new Number(5,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(6,fila, it.porcentajeMermaFinal,formatoDatos))
                //sheet1.addCell(new Number(7,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(7,fila, it.pesoBruto-it.pesoBruto*it.porcentajeMermaFinal/100,formatoDatos))
                sheet1.addCell(new Number(8,fila, it.porcentajeHumedadFinal,formatoDatos))
                sheet1.addCell(new Number(9,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(10,fila, it.porcentajeZincFinal,formatoDatos)) //SN
                sheet1.addCell(new Number(11,fila, it.porcentajePlomoFinal,formatoDatos)) //SN
                sheet1.addCell(new Number(12,fila, it.porcentajePlataFinal,formatoDatos)) //SN
                sheet1.addCell(new Number(13,fila, it.kilosFinosZinc,formatoDatos)) //SN
                sheet1.addCell(new Number(14,fila, it.kilosFinosPlomo,formatoDatos)) //SN
                sheet1.addCell(new Number(15,fila, it.kilosFinosPlata,formatoDatos)) //SN
                sheet1.addCell(new Number(16,fila, it.valorNetoMineral,formatoDatos))
                sheet1.addCell(new Number(17,fila, it.valorNetoMineralEnBolivianos,formatoDatos))

                def retencionesDeLeyLiquidacion = LiquidacionDeComplejoRetenciones.findAllByLiquidacionDeComplejoAndTipoDeRetencion(it,"DE LEY")
                def subtotalRetencionesDeLey=it.regaliaMinera.doubleValue()
                for(int i=0;i<retencionesDeLey.size();i++){
                    def vr = valorRetencion(retencionesDeLey.get(i), retencionesDeLeyLiquidacion,retencionesDeLeyLiquidacion.size())
                    subtotalRetencionesDeLey+=vr
                }
                sheet1.addCell(new Number(18,fila, subtotalRetencionesDeLey,formatoDatos))

                //DESPLIEGUE DE RETENCIONES DE LEY
                def retencionesOtrasLiquidacion = LiquidacionDeComplejoRetenciones.findAllByLiquidacionDeComplejoAndTipoDeRetencion(it,"OTRA")
                def subtotalRetencionesOtras=0
                for(int i=0;i<retencionesOtras.size();i++){
                    def vr = valorRetencion(retencionesOtras.get(i), retencionesOtrasLiquidacion,retencionesOtrasLiquidacion.size())
                    subtotalRetencionesOtras+=vr
                }
                sheet1.addCell(new Number(19,fila, subtotalRetencionesOtras,formatoDatos))

                sheet1.addCell(new Number(20,fila, it.totalAnticiposContraEntrega,formatoDatos))
                sheet1.addCell(new Number(21,fila, it.totalLiquidoPagable,formatoDatos))

                totalCantidadDeSacos+=Double.parseDouble(it.cantidadDeSacos)
                totalPesoBruto+=it.pesoBruto
                totalTotalTara+=it.porcentajeMermaFinal
                totalKilosNetosHumedos+=it.kilosNetosHumedos
                totalHumedad+=it.porcentajeHumedadFinal
                totalKilosNetosSecos+=it.kilosNetosSecos
                totalPorcentajeZinc+=it.porcentajeZincFinal
                totalPorcentajePlomo+=it.porcentajePlomoFinal
                totalPorcentajePlata+=it.porcentajePlataFinal
                totalKilosFinosZinc+=it.kilosFinosZinc
                totalKilosFinosPlomo+=it.kilosFinosPlomo
                totalKilosFinosPlata+=it.kilosFinosPlata
                totalValorNetoMineral+=it.valorNetoMineral
                totalValorNetoMineralEnBolivianos+=it.valorNetoMineralEnBolivianos
                totalRetencionesDeLey+=subtotalRetencionesDeLey
                totalOtrasRetenciones+=subtotalRetencionesOtras
                totalAnticiposContraEntrega+=it.totalAnticiposContraEntrega
                totalLiquidoPagable=totalLiquidoPagable+((it.totalLiquidoPagable<0)?0:it.totalLiquidoPagable)

                fila++
            }


            //insertar filas para subtotales
            def p0=7
            def p1=7
            def p2=p1+1
            def rango=p1..fila
            def emp1=null
            def emp2=null
            def incrFila=0
            def subtotalLiquidoPagable=0
            rango.each {
                emp1=sheet1.getWritableCell(1,p1).contents
                emp2=sheet1.getWritableCell(1,p2).contents
                if (!emp1.equals(emp2)){
                    sheet1.insertRow(p2)
                    sheet1.addCell(new Label(2,p2,"SUBTOTALES",formatoTotales))
                    sheet1.addCell(new Formula(4, p2,"SUM(E${p0+1}:E${p1+1})",formatoTotales))
                    sheet1.addCell(new Formula(5, p2,"SUM(F${p0+1}:F${p1+1})",formatoTotales))
                    sheet1.addCell(new Formula(6, p2,"SUM(G${p0+1}:G${p1+1})",formatoTotales))
                    sheet1.addCell(new Formula(7, p2,"SUM(H${p0+1}:H${p1+1})",formatoTotales))
                    sheet1.addCell(new Formula(8, p2,"SUM(I${p0+1}:I${p1+1})",formatoTotales))
                    sheet1.addCell(new Formula(9, p2,"SUM(J${p0+1}:J${p1+1})",formatoTotales))
                    sheet1.addCell(new Formula(10, p2,"SUM(K${p0+1}:K${p1+1})",formatoTotales))
                    sheet1.addCell(new Formula(11, p2,"SUM(L${p0+1}:L${p1+1})",formatoTotales))
                    sheet1.addCell(new Formula(12, p2,"SUM(M${p0+1}:M${p1+1})",formatoTotales))
                    sheet1.addCell(new Formula(13, p2,"SUM(N${p0+1}:N${p1+1})",formatoTotales))
                    sheet1.addCell(new Formula(14, p2,"SUM(O${p0+1}:O${p1+1})",formatoTotales))
                    sheet1.addCell(new Formula(15, p2,"SUM(P${p0+1}:P${p1+1})",formatoTotales))
                    sheet1.addCell(new Formula(16, p2,"SUM(Q${p0+1}:Q${p1+1})",formatoTotales))
                    sheet1.addCell(new Formula(17, p2,"SUM(R${p0+1}:R${p1+1})",formatoTotales))
                    sheet1.addCell(new Formula(18, p2,"SUM(S${p0+1}:S${p1+1})",formatoTotales))
                    sheet1.addCell(new Formula(19, p2,"SUM(T${p0+1}:T${p1+1})",formatoTotales))
                    sheet1.addCell(new Formula(20, p2,"SUM(U${p0+1}:U${p1+1})",formatoTotales))

                    def knh=0
                    def kns=0
                    def kfZn=0
                    def kfPb=0
                    def kfAg=0
                    for (def i=p0;i<=p1;i++){
                        knh+=sheet1.getWritableCell(7,i).contents.toBigDecimal()
                        kns+=sheet1.getWritableCell(9,i).contents.toBigDecimal()
                        kfZn+=sheet1.getWritableCell(13,i).contents.toBigDecimal()
                        kfPb+=sheet1.getWritableCell(14,i).contents.toBigDecimal()
                        kfAg+=sheet1.getWritableCell(15,i).contents.toBigDecimal()
                        subtotalLiquidoPagable+=(sheet1.getWritableCell(21,i).contents.toBigDecimal()<0)?0:sheet1.getWritableCell(21,i).contents.toBigDecimal()
                    }
                    sheet1.addCell(new Number(21,p2, subtotalLiquidoPagable,formatoTotales))

                    sheet1.addCell(new Number(8,p2, 100-100*kns/knh,formatoTotales))
                    sheet1.addCell(new Number(10,p2, 100*kfZn/kns,formatoTotales))
                    sheet1.addCell(new Number(11,p2, 100*kfPb/kns,formatoTotales))
                    sheet1.addCell(new Number(12,p2, 10000*kfAg/kns,formatoTotales))

                    p0=p2+1
                    p1=p2+1
                    p2=p1+1
                    incrFila++
                    subtotalLiquidoPagable=0
                }else{
                    p1++
                    p2++
                }
            }

            sheet1.addCell(new Number(4,fila+incrFila, totalCantidadDeSacos,formatoTotales))
            sheet1.addCell(new Number(5,fila+incrFila, totalPesoBruto,formatoTotales))
            sheet1.addCell(new Number(6,fila+incrFila, totalTotalTara,formatoTotales))
            sheet1.addCell(new Number(7,fila+incrFila, totalKilosNetosHumedos,formatoTotales))
            sheet1.addCell(new Number(8,fila+incrFila, 100-100*totalKilosNetosSecos/totalKilosNetosHumedos,formatoTotales))
            sheet1.addCell(new Number(9,fila+incrFila, totalKilosNetosSecos,formatoTotales))
            sheet1.addCell(new Number(10,fila+incrFila, 100*totalKilosFinosZinc/totalKilosNetosSecos,formatoTotales))
            sheet1.addCell(new Number(11,fila+incrFila, 100*totalKilosFinosPlomo/totalKilosNetosSecos,formatoTotales))
            sheet1.addCell(new Number(12,fila+incrFila, 10000*totalKilosFinosPlata/totalKilosNetosSecos,formatoTotales))
            sheet1.addCell(new Number(13,fila+incrFila, totalKilosFinosZinc,formatoTotales))
            sheet1.addCell(new Number(14,fila+incrFila, totalKilosFinosPlomo,formatoTotales))
            sheet1.addCell(new Number(15,fila+incrFila, totalKilosFinosPlata,formatoTotales))
            sheet1.addCell(new Number(16,fila+incrFila, totalValorNetoMineral,formatoTotales))
            sheet1.addCell(new Number(17,fila+incrFila, totalValorNetoMineralEnBolivianos,formatoTotales))
            sheet1.addCell(new Number(18,fila+incrFila, totalRetencionesDeLey,formatoTotales))
            sheet1.addCell(new Number(19,fila+incrFila, totalOtrasRetenciones,formatoTotales))
            sheet1.addCell(new Number(20,fila+incrFila, totalAnticiposContraEntrega,formatoTotales))
            sheet1.addCell(new Number(21,fila+incrFila, totalLiquidoPagable,formatoTotales))
        }

        workbook.write();
        workbook.close();
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
