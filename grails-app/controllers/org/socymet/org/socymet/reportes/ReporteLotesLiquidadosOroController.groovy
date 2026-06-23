package org.socymet.org.socymet.reportes

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
import org.socymet.liquidacion.LiquidacionDeOro
import org.socymet.liquidacion.LiquidacionDeOroRetenciones
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
class ReporteLotesLiquidadosOroController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ReporteLotesLiquidadosOro.list(params), model:[reporteLotesLiquidadosOroInstanceCount: ReporteLotesLiquidadosOro.count()]
    }

    def show(ReporteLotesLiquidadosOro reporteLotesLiquidadosOroInstance) {
        respond reporteLotesLiquidadosOroInstance
    }

    def create() {
        respond new ReporteLotesLiquidadosOro(params)
    }

    @Transactional
    def save(ReporteLotesLiquidadosOro reporteLotesLiquidadosOroInstance) {
        if (reporteLotesLiquidadosOroInstance == null) {
            notFound()
            return
        }

        if (reporteLotesLiquidadosOroInstance.hasErrors()) {
            respond reporteLotesLiquidadosOroInstance.errors, view:'create'
            return
        }

        reporteLotesLiquidadosOroInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'reporteLotesLiquidadosOro.label', default: 'ReporteLotesLiquidadosOro'), reporteLotesLiquidadosOroInstance.id])
                redirect reporteLotesLiquidadosOroInstance
            }
            '*' { respond reporteLotesLiquidadosOroInstance, [status: CREATED] }
        }
    }

    def edit(ReporteLotesLiquidadosOro reporteLotesLiquidadosOroInstance) {
        respond reporteLotesLiquidadosOroInstance
    }

    @Transactional
    def update(ReporteLotesLiquidadosOro reporteLotesLiquidadosOroInstance) {
        if (reporteLotesLiquidadosOroInstance == null) {
            notFound()
            return
        }

        if (reporteLotesLiquidadosOroInstance.hasErrors()) {
            respond reporteLotesLiquidadosOroInstance.errors, view:'edit'
            return
        }

        reporteLotesLiquidadosOroInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ReporteLotesLiquidadosOro.label', default: 'ReporteLotesLiquidadosOro'), reporteLotesLiquidadosOroInstance.id])
                redirect reporteLotesLiquidadosOroInstance
            }
            '*'{ respond reporteLotesLiquidadosOroInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(ReporteLotesLiquidadosOro reporteLotesLiquidadosOroInstance) {

        if (reporteLotesLiquidadosOroInstance == null) {
            notFound()
            return
        }

        reporteLotesLiquidadosOroInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ReporteLotesLiquidadosOro.label', default: 'ReporteLotesLiquidadosOro'), reporteLotesLiquidadosOroInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteLotesLiquidadosOro.label', default: 'ReporteLotesLiquidadosOro'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
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

        WritableSheet sheet1 = workbook.createSheet("Reporte de Lotes Liquidados", 0)
        sheet1.setRowView(6,500)
        for(i in 0..100)
            sheet1.setColumnView(i,8)
//        sheet1.setColumnView(0,11)//ajustar ancho de columnas (columna,ancho)
//        sheet1.setColumnView(1,11)//ajustar ancho de columnas (columna,ancho)
//        sheet1.setColumnView(2,11)
        sheet1.setColumnView(1,30)
        sheet1.setColumnView(2,30)

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
        response.setHeader('Content-Disposition', 'Attachment;Filename="reporte_lotes_liquidados_lamas.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(3,0, "REPORTE DE LOTES LIQUIDADOS DE LAMAS",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def tipoReporte = ""+params.tipoReporte
        def empresa=null
        def fechaInicial=null
        def fechaFinal=null
        def deposito = null
        System.out.println("DEPOSITO: ${params.deposito.id}")
        if (params.deposito.id!=9999)
            deposito= Deposito.get(params.deposito.id)

        def liquidacionesOro=null

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
                liquidacionesOro = LiquidacionDeOro.findAllByFechaDeLiquidacionBetweenAndDeposito(fechaInicial,fechaFinal,deposito,[sort: 'nombreEmpresa'])
            else
                liquidacionesOro = LiquidacionDeOro.findAllByFechaDeLiquidacionBetween(fechaInicial,fechaFinal,[sort: 'nombreEmpresa'])

            System.out.println("*** RESULTADOS DE COMPLEJO: ${liquidacionesOro.size()}")
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
                liquidacionesOro = LiquidacionDeOro.findAllByFechaDeLiquidacionBetweenAndDepositoAndEmpresa(fechaInicial,fechaFinal,deposito,empresa,[sort: 'nombreEmpresa'])
            else
                liquidacionesOro = LiquidacionDeOro.findAllByFechaDeLiquidacionBetweenAndEmpresa(fechaInicial,fechaFinal,empresa,[sort: 'nombreEmpresa'])

            System.out.println("*** RESULTADOS DE COMPLEJO: ${liquidacionesOro.size()}")
        }

        /*GENERANDO LISTA GENERAL DE RETENCIONES DE LEY*/
        if(liquidacionesOro){
            def listaRetencionesDeLeyOro = retencionesOroJSON liquidacionesOro,"DE LEY"
            listaRetencionesDeLeyOro.each {
                if(!retencionesDeLey.contains(it.toString()))
                    retencionesDeLey.add(it)
            }
        }

        if(liquidacionesOro){
            def listaRetencionesDeLeyOro = retencionesOroJSON liquidacionesOro,"OTRA"
            listaRetencionesDeLeyOro.each {
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
        sheet1.addCell(new Label(10,6, "LEY gr/TM",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "K. F.",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(13,6, "RET. LEY",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "OTRAS RET.",formatoEncabezado))
        sheet1.addCell(new Label(15,6, "ANT./ENT.",formatoEncabezado))
        sheet1.addCell(new Label(16,6, "LIQ. PAGAB.",formatoEncabezado))

        def fila = 7

        def totalCantidadDeSacos=0
        def totalPesoBruto=0
        def totalTotalTara=0
        def totalKilosNetosHumedos=0
        def totalHumedad=0
        def totalKilosNetosSecos=0
        def totalPorcentajeOro=0
        def totalKilosFinosOro=0
        def totalValorNetoMineralEnBolivianos=0
        def totalRetencionesDeLey=0
        def totalOtrasRetenciones=0
        def totalAnticiposContraEntrega=0
        def totalLiquidoPagable=0

        if(liquidacionesOro){
            liquidacionesOro.each {
                sheet1.addCell(new DateTime(0,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(1,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(2,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(3,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(4,fila, Double.parseDouble(it.cantidadDeSacos),formatoDatos))
                sheet1.addCell(new Number(5,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(6,fila, it.porcentajeMermaFinal,formatoDatos))
                sheet1.addCell(new Number(7,fila, it.pesoBruto-it.pesoBruto*it.porcentajeMermaFinal/100,formatoDatos))
                sheet1.addCell(new Number(8,fila, it.porcentajeHumedadFinal,formatoDatos))
                sheet1.addCell(new Number(9,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(10,fila, it.porcentajeOroFinal,formatoDatos)) //SN
                sheet1.addCell(new Number(11,fila, it.kilosFinosOro,formatoDatos)) //SN
                sheet1.addCell(new Number(12,fila, it.valorNetoMineralEnBolivianos,formatoDatos))

                def retencionesDeLeyLiquidacion = LiquidacionDeOroRetenciones.findAllByLiquidacionDeOroAndTipoDeRetencion(it,"DE LEY")
                def subtotalRetencionesDeLey=it.regaliaMinera.doubleValue()
                for(int i=0;i<retencionesDeLey.size();i++){
                    def vr = valorRetencion(retencionesDeLey.get(i), retencionesDeLeyLiquidacion,retencionesDeLeyLiquidacion.size())
                    subtotalRetencionesDeLey+=vr
                }
                sheet1.addCell(new Number(13,fila, subtotalRetencionesDeLey,formatoDatos))

                //DESPLIEGUE DE RETENCIONES DE LEY
                def retencionesOtrasLiquidacion = LiquidacionDeOroRetenciones.findAllByLiquidacionDeOroAndTipoDeRetencion(it,"OTRA")
                def subtotalRetencionesOtras=0
                for(int i=0;i<retencionesOtras.size();i++){
                    def vr = valorRetencion(retencionesOtras.get(i), retencionesOtrasLiquidacion,retencionesOtrasLiquidacion.size())
                    subtotalRetencionesOtras+=vr
                }
                sheet1.addCell(new Number(14,fila, subtotalRetencionesOtras,formatoDatos))

                sheet1.addCell(new Number(15,fila, it.totalAnticiposContraEntrega,formatoDatos))
                sheet1.addCell(new Number(16,fila, it.totalLiquidoPagable,formatoDatos))

                totalCantidadDeSacos+=Double.parseDouble(it.cantidadDeSacos)
                totalPesoBruto+=it.pesoBruto
                totalTotalTara+=it.porcentajeMermaFinal
                totalKilosNetosHumedos+=it.kilosNetosHumedos
                totalHumedad+=it.porcentajeHumedadFinal
                totalKilosNetosSecos+=it.kilosNetosSecos
                totalPorcentajeOro+=it.porcentajeOroFinal
                totalKilosFinosOro+=it.kilosFinosOro
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

                    def knh=0
                    def kns=0
                    def kfAu=0
                    for (def i=p0;i<=p1;i++){
                        knh+=sheet1.getWritableCell(7,i).contents.toBigDecimal()
                        kns+=sheet1.getWritableCell(9,i).contents.toBigDecimal()
                        kfAu+=sheet1.getWritableCell(13,i).contents.toBigDecimal()
                        subtotalLiquidoPagable+=(sheet1.getWritableCell(16,i).contents.toBigDecimal()<0)?0:sheet1.getWritableCell(16,i).contents.toBigDecimal()
                    }
                    sheet1.addCell(new Number(21,p2, subtotalLiquidoPagable,formatoTotales))

                    sheet1.addCell(new Number(8,p2, 100-100*kns/knh,formatoTotales))
                    sheet1.addCell(new Number(10,p2, 100*kfAu/kns,formatoTotales))

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
            sheet1.addCell(new Number(10,fila+incrFila, 100*totalKilosFinosOro/totalKilosNetosSecos,formatoTotales))
            sheet1.addCell(new Number(11,fila+incrFila, totalKilosFinosOro,formatoTotales))
            sheet1.addCell(new Number(12,fila+incrFila, totalValorNetoMineralEnBolivianos,formatoTotales))
            sheet1.addCell(new Number(13,fila+incrFila, totalRetencionesDeLey,formatoTotales))
            sheet1.addCell(new Number(14,fila+incrFila, totalOtrasRetenciones,formatoTotales))
            sheet1.addCell(new Number(15,fila+incrFila, totalAnticiposContraEntrega,formatoTotales))
            sheet1.addCell(new Number(16,fila+incrFila, totalLiquidoPagable,formatoTotales))
        }

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
