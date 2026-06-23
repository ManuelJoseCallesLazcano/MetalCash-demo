package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import jxl.Workbook
import jxl.format.Alignment
import jxl.write.*
import org.socymet.liquidacion.*
import org.socymet.proveedor.Cliente
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class ReporteHistorialClienteController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reporteHistorialClienteInstanceList: ReporteHistorialCliente.list(params), reporteHistorialClienteInstanceTotal: ReporteHistorialCliente.count()]
    }

    def create() {
        [reporteHistorialClienteInstance: new ReporteHistorialCliente(params)]
    }

    def save() {
        def reporteHistorialClienteInstance = new ReporteHistorialCliente(params)
        if (!reporteHistorialClienteInstance.save(flush: true)) {
            render(view: "create", model: [reporteHistorialClienteInstance: reporteHistorialClienteInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reporteHistorialCliente.label', default: 'ReporteHistorialCliente'), reporteHistorialClienteInstance.id])
        redirect(action: "show", id: reporteHistorialClienteInstance.id)
    }

    def show(Long id) {
        def reporteHistorialClienteInstance = ReporteHistorialCliente.get(id)
        if (!reporteHistorialClienteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteHistorialCliente.label', default: 'ReporteHistorialCliente'), id])
            redirect(action: "list")
            return
        }

        [reporteHistorialClienteInstance: reporteHistorialClienteInstance]
    }

    def edit(Long id) {
        def reporteHistorialClienteInstance = ReporteHistorialCliente.get(id)
        if (!reporteHistorialClienteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteHistorialCliente.label', default: 'ReporteHistorialCliente'), id])
            redirect(action: "list")
            return
        }

        [reporteHistorialClienteInstance: reporteHistorialClienteInstance]
    }

    def update(Long id, Long version) {
        def reporteHistorialClienteInstance = ReporteHistorialCliente.get(id)
        if (!reporteHistorialClienteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteHistorialCliente.label', default: 'ReporteHistorialCliente'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reporteHistorialClienteInstance.version > version) {
                reporteHistorialClienteInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reporteHistorialCliente.label', default: 'ReporteHistorialCliente')] as Object[],
                        "Another user has updated this ReporteHistorialCliente while you were editing")
                render(view: "edit", model: [reporteHistorialClienteInstance: reporteHistorialClienteInstance])
                return
            }
        }

        reporteHistorialClienteInstance.properties = params

        if (!reporteHistorialClienteInstance.save(flush: true)) {
            render(view: "edit", model: [reporteHistorialClienteInstance: reporteHistorialClienteInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reporteHistorialCliente.label', default: 'ReporteHistorialCliente'), reporteHistorialClienteInstance.id])
        redirect(action: "show", id: reporteHistorialClienteInstance.id)
    }

    def delete(Long id) {
        def reporteHistorialClienteInstance = ReporteHistorialCliente.get(id)
        if (!reporteHistorialClienteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteHistorialCliente.label', default: 'ReporteHistorialCliente'), id])
            redirect(action: "list")
            return
        }

        try {
            reporteHistorialClienteInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reporteHistorialCliente.label', default: 'ReporteHistorialCliente'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reporteHistorialCliente.label', default: 'ReporteHistorialCliente'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporte = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 7, WritableFont.NO_BOLD);
        WritableFont courier6PlainFont = new WritableFont(WritableFont.COURIER, 7, WritableFont.NO_BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 7, WritableFont.NO_BOLD);
        WritableFont courier8BoldFont = new WritableFont(WritableFont.COURIER, 7, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.COURIER, 10, WritableFont.NO_BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.NO_BOLD);

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

        WritableSheet sheet1 = workbook.createSheet("Historial de Cliente", 0)
        sheet1.setRowView(6,500)
        for(i in 0..100)
            sheet1.setColumnView(i,9)
        sheet1.setColumnView(2,30)
        sheet1.setColumnView(3,30)
        sheet1.setColumnView(5,12)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="reporte_historial_cliente.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(3,0, "HISTORIAL DE CLIENTE",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def tipoReporte = "fechasEmpresa"
        def cliente=null
        def fechaInicial=null
        def fechaFinal=null

        def liquidacionesComplejo=null
        def liquidacionesPlomoPlata=null
        def liquidacionesZincPlata=null
        def liquidacionesCobrePlata=null
        
        def retencionesDeLey=new ArrayList<String>()
        def retencionesOtras=new ArrayList<String>()

        cliente = Cliente.get(Integer.parseInt(""+params.cliente.id))

        fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
        fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)
        
        def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
        def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
        sheet1.addCell(new Label(3,3, "CLIENTE:",formatoInfoReporte))
        sheet1.addCell(new Label(4,3, "${cliente.toString()}",formatoInfoReporte))
        sheet1.addCell(new Label(3,4, "EMPRESA:",formatoInfoReporte))
        sheet1.addCell(new Label(4,4, "${cliente.empresa.toString()}",formatoInfoReporte))
        sheet1.addCell(new Label(3,2, "ENTRE FECHAS:",formatoInfoReporte))
        sheet1.addCell(new Label(4,2, "${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

        def liquidacionesCm = LiquidacionDeComplejo.findAllByFechaDeLiquidacionBetween(fechaInicial,fechaFinal,[sort: 'fechaDeLiquidacion'])
        liquidacionesComplejo=new ArrayList<LiquidacionDeComplejo>()
        liquidacionesCm.each {
            if(it.recepcionDeComplejo.cliente.id==cliente.id)
                liquidacionesComplejo.add(it)
        }

        def liquidacionesPbAg = LiquidacionDePlomoPlata.findAllByFechaDeLiquidacionBetween(fechaInicial,fechaFinal,[sort: 'fechaDeLiquidacion'])
        liquidacionesPlomoPlata=new ArrayList<LiquidacionDeComplejo>()
        liquidacionesPbAg.each {
            if(it.recepcionDeComplejo.cliente.id==cliente.id)
                liquidacionesPlomoPlata.add(it)
        }

        def liquidacionesZnAg = LiquidacionDeZincPlata.findAllByFechaDeLiquidacionBetween(fechaInicial,fechaFinal,[sort: 'fechaDeLiquidacion'])
        liquidacionesZincPlata=new ArrayList<LiquidacionDeZincPlata>()
        liquidacionesZnAg.each {
            if(it.recepcionDeComplejo.cliente.id==cliente.id)
                liquidacionesZincPlata.add(it)
        }

        def liquidacionesCuAg = LiquidacionDeCobrePlata.findAllByFechaDeLiquidacionBetween(fechaInicial,fechaFinal,[sort: 'fechaDeLiquidacion'])
        liquidacionesCobrePlata=new ArrayList<LiquidacionDeCobrePlata>()
        liquidacionesCuAg.each {
            if(it.recepcionDeComplejo.cliente.id==cliente.id)
                liquidacionesCobrePlata.add(it)
        }
        
        /*GENERANDO LISTA GENERAL DE RETENCIONES DE LEY*/
        if(liquidacionesComplejo){
            def listaRetencionesDeLeyComplejo = retencionesComplejoJSON liquidacionesComplejo,"DE LEY"
            listaRetencionesDeLeyComplejo.each {
                if(!retencionesDeLey.contains(it.toString()))
                    retencionesDeLey.add(it)
            }
        }

        if(liquidacionesPlomoPlata){
            def listaRetencionesDeLeyPlomoPlata = retencionesPlomoPlataJSON liquidacionesPlomoPlata,"DE LEY"
            listaRetencionesDeLeyPlomoPlata.each {
                if(!retencionesDeLey.contains(it.toString()))
                    retencionesDeLey.add(it)
            }
        }

        if(liquidacionesZincPlata){
            def listaRetencionesDeLeyZincPlata = retencionesZincPlataJSON liquidacionesZincPlata,"DE LEY"
            listaRetencionesDeLeyZincPlata.each {
                if(!retencionesDeLey.contains(it.toString()))
                    retencionesDeLey.add(it)
            }
        }

        if(liquidacionesCobrePlata){
            def listaRetencionesDeLeyCobrePlata = retencionesCobrePlataJSON liquidacionesCobrePlata,"DE LEY"
            listaRetencionesDeLeyCobrePlata.each {
                if(!retencionesDeLey.contains(it.toString()))
                    retencionesDeLey.add(it)
            }
        }
        /*FIN - GENERANDO LISTA GENERAL DE RETENCIONES DE LEY*/

        /*GENERANDO LISTA GENERAL DE OTRAS RETENCIONES*/        
        if(liquidacionesComplejo){
            def listaRetencionesDeLeyComplejo = retencionesComplejoJSON liquidacionesComplejo,"Otra"
            listaRetencionesDeLeyComplejo.each {
                if(!retencionesOtras.contains(it.toString()))
                    retencionesOtras.add(it)
            }
        }
        if(liquidacionesPlomoPlata){
            def listaRetencionesDeLeyPlomoPlata = retencionesPlomoPlataJSON liquidacionesPlomoPlata,"Otra"
            listaRetencionesDeLeyPlomoPlata.each {
                if(!retencionesOtras.contains(it.toString()))
                    retencionesOtras.add(it)
            }
        }
        if(liquidacionesZincPlata){
            def listaRetencionesDeLeyZincPlata = retencionesZincPlataJSON liquidacionesZincPlata,"Otra"
            listaRetencionesDeLeyZincPlata.each {
                if(!retencionesOtras.contains(it.toString()))
                    retencionesOtras.add(it)
            }
        }
        if(liquidacionesCobrePlata){
            def listaRetencionesDeLeyCobrePlata = retencionesCobrePlataJSON liquidacionesCobrePlata,"Otra"
            listaRetencionesDeLeyCobrePlata.each {
                if(!retencionesOtras.contains(it.toString()))
                    retencionesOtras.add(it)
            }
        }
        /*FIN - GENERANDO LISTA GENERAL DE OTRAS RETENCIONES*/

        sheet1.addCell(new Label(0,6, "FEC. REP.",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "FEC. LIQ.",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "NOMBRE",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "MINERAL",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "K. N. H.",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "% H2O",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "K. N. S.",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "LEY %Zn",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "LEY %Pb",formatoEncabezado))
        sheet1.addCell(new Label(13,6, "LEY DM Ag",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "LEY %Cu",formatoEncabezado))
        sheet1.addCell(new Label(15,6, "K. F. Zn",formatoEncabezado))
        sheet1.addCell(new Label(16,6, "K. F. Pb",formatoEncabezado))
        sheet1.addCell(new Label(17,6, "K. F. Ag",formatoEncabezado))
        sheet1.addCell(new Label(18,6, "K. F. Cu",formatoEncabezado))
        sheet1.addCell(new Label(19,6, "VALOR OF. BRUTO",formatoEncabezado))
        sheet1.addCell(new Label(20,6, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(21,6, "RM",formatoEncabezado))
        /*INSERTANDO CABECERA PARA LAS RETENCIONES DE LEY*/
        def columna = 22
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

        //def colsNot=[16,18,19,20,21,22,23,30,31,32,33,34,35,37,38,39,40,41,42]
        def colsNot=[11,12,13,14]
        def filasNot=new ArrayList()
        def inicioCiclo=0
        def finCiclo=0
        def filaTotal=0
        def totalLiquidacionesComplejo = 0
        def totalLiquidacionesPlomoPlata = 0
        def totalLiquidacionesZincPlata = 0
        def totalLiquidacionesCobrePlata = 0

        def columnaFinalRetenciones = 29+retencionesDeLey.size()+retencionesOtras.size()
        def fila = 7

        if(liquidacionesComplejo){
            liquidacionesComplejo.each {
                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new DateTime(1,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(2,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(3,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(4,fila, "Zn Pb Ag",formatoDatos))
                sheet1.addCell(new Label(5,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(6,fila, Float.parseFloat(it.cantidadDeSacos),formatoDatos))
                sheet1.addCell(new Number(7,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(8,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(9,fila, it.porcentajeHumedadFinal,formatoDatos))
                sheet1.addCell(new Number(10,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(11,fila, it.porcentajeZincFinal,formatoDatos)) //ZN
                sheet1.addCell(new Number(12,fila, it.porcentajePlomoFinal,formatoDatos)) //PB
                sheet1.addCell(new Number(13,fila, it.porcentajePlataFinal,formatoDatos)) //AG
                sheet1.addCell(new Number(14,fila, 0,formatoDatos)) //CU
                sheet1.addCell(new Number(15,fila, it.kilosFinosZinc,formatoDatos)) //ZN
                sheet1.addCell(new Number(16,fila, it.kilosFinosPlomo,formatoDatos)) //PB
                sheet1.addCell(new Number(17,fila, it.kilosFinosPlata,formatoDatos)) //AG
                sheet1.addCell(new Number(18,fila, 0,formatoDatos)) //cu
                sheet1.addCell(new Number(19,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(20,fila, it.valorNetoMineralEnBolivianos,formatoDatos))
                sheet1.addCell(new Number(21,fila, it.regaliaMinera,formatoDatos))

                columna=22
                //DESPLIEGUE DE RETENCIONES DE LEY
                def retencionesDeLeyLiquidacion = LiquidacionDeComplejoRetenciones.findAllByLiquidacionDeComplejoAndTipoDeRetencion(it,"DE LEY")
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
                def retencionesOtrasLiquidacion = LiquidacionDeComplejoRetenciones.findAllByLiquidacionDeComplejoAndTipoDeRetencion(it,"Otra")
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

                fila++
            }
            //hacer un espacio para detallar el siguiente elemento
            fila++
            //SUBTOTALES COMPLEJO
            totalLiquidacionesComplejo = liquidacionesComplejo.size()
            inicioCiclo=7
            finCiclo=inicioCiclo+totalLiquidacionesComplejo
            filaTotal=finCiclo
            filasNot.add(filaTotal)

            sheet1.addCell(new Label(3,filaTotal, "SUBTOTALES",formatoEncabezado))
            for (int col=6;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =inicioCiclo;fil<finCiclo;fil++){
                    def contenido=(sheet1.getWritableCell(col,fil).contents.equals(""))?"0":sheet1.getWritableCell(col,fil).contents
                    def valor = Double.parseDouble(contenido)
                    tret+=valor
                }
                if (!colsNot.contains(col))
                    sheet1.addCell(new Number(col,filaTotal, tret,formatoTotales))
            }
            fila++
        }

        if(liquidacionesPlomoPlata){
            liquidacionesPlomoPlata.each {
                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new DateTime(1,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(2,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(3,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(4,fila, "Pb Ag",formatoDatos))
                sheet1.addCell(new Label(5,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(6,fila, Float.parseFloat(it.cantidadDeSacos),formatoDatos))
                sheet1.addCell(new Number(7,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(8,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(9,fila, it.porcentajeHumedadFinal,formatoDatos))
                sheet1.addCell(new Number(10,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(11,fila, 0,formatoDatos)) //ZN
                sheet1.addCell(new Number(12,fila, it.porcentajePlomoFinal,formatoDatos)) //PB
                sheet1.addCell(new Number(13,fila, it.porcentajePlataFinal,formatoDatos)) //AG
                sheet1.addCell(new Number(14,fila, 0,formatoDatos)) //CU
                sheet1.addCell(new Number(15,fila, 0,formatoDatos)) //ZN
                sheet1.addCell(new Number(16,fila, it.kilosFinosPlomo,formatoDatos)) //PB
                sheet1.addCell(new Number(17,fila, it.kilosFinosPlata,formatoDatos)) //AG
                sheet1.addCell(new Number(18,fila, 0,formatoDatos)) //cu
                sheet1.addCell(new Number(19,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(20,fila, it.valorNetoMineralEnBolivianos,formatoDatos))
                sheet1.addCell(new Number(21,fila, it.regaliaMinera,formatoDatos))

                columna=22
                //DESPLIEGUE DE RETENCIONES DE LEY
                def retencionesDeLeyLiquidacion = LiquidacionDePlomoPlataRetenciones.findAllByLiquidacionDePlomoPlataAndTipoDeRetencion(it,"DE LEY")
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
                def retencionesOtrasLiquidacion = LiquidacionDePlomoPlataRetenciones.findAllByLiquidacionDePlomoPlataAndTipoDeRetencion(it,"Otra")
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

                fila++
            }
            //hacer un espacio para detallar el siguiente elemento
            fila++
            //SUBTOTALES COMPLEJO
            totalLiquidacionesPlomoPlata = liquidacionesPlomoPlata.size()
            inicioCiclo=7+((liquidacionesComplejo)?totalLiquidacionesComplejo+2:0)
            finCiclo=inicioCiclo+totalLiquidacionesPlomoPlata
            filaTotal=finCiclo
            filasNot.add(filaTotal)

            sheet1.addCell(new Label(3,filaTotal, "SUBTOTALES",formatoEncabezado))
            for (int col=6;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =inicioCiclo;fil<finCiclo;fil++){
                    def contenido=(sheet1.getWritableCell(col,fil).contents.equals(""))?"0":sheet1.getWritableCell(col,fil).contents
                    def valor = Double.parseDouble(contenido)
                    tret+=valor
                }
                if (!colsNot.contains(col))
                    sheet1.addCell(new Number(col,filaTotal, tret,formatoTotales))
            }
            fila++
        }

        if(liquidacionesZincPlata){
            liquidacionesZincPlata.each {
                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new DateTime(1,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(2,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(3,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(4,fila, "Zn Ag",formatoDatos))
                sheet1.addCell(new Label(5,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(6,fila, Float.parseFloat(it.cantidadDeSacos),formatoDatos))
                sheet1.addCell(new Number(7,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(8,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(9,fila, it.porcentajeHumedadFinal,formatoDatos))
                sheet1.addCell(new Number(10,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(11,fila, it.porcentajeZincFinal,formatoDatos)) //ZN
                sheet1.addCell(new Number(12,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(13,fila, it.porcentajePlataFinal,formatoDatos)) //AG
                sheet1.addCell(new Number(14,fila, 0,formatoDatos)) //CU
                sheet1.addCell(new Number(15,fila, it.kilosFinosZinc,formatoDatos)) //ZN
                sheet1.addCell(new Number(16,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(17,fila, it.kilosFinosPlata,formatoDatos)) //AG
                sheet1.addCell(new Number(18,fila, 0,formatoDatos)) //cu
                sheet1.addCell(new Number(19,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(20,fila, it.valorNetoMineralEnBolivianos,formatoDatos))
                sheet1.addCell(new Number(21,fila, it.regaliaMinera,formatoDatos))

                columna=22
                //DESPLIEGUE DE RETENCIONES DE LEY
                def retencionesDeLeyLiquidacion = LiquidacionDeZincPlataRetenciones.findAllByLiquidacionDeZincPlataAndTipoDeRetencion(it,"DE LEY")
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
                def retencionesOtrasLiquidacion = LiquidacionDeZincPlataRetenciones.findAllByLiquidacionDeZincPlataAndTipoDeRetencion(it,"Otra")
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

                fila++
            }
            //hacer un espacio para detallar el siguiente elemento
            fila++
            //SUBTOTALES COMPLEJO
            totalLiquidacionesZincPlata = liquidacionesZincPlata.size()
            inicioCiclo=7+((liquidacionesPlomoPlata)?totalLiquidacionesPlomoPlata+2:0)+((liquidacionesComplejo)?totalLiquidacionesComplejo+2:0)
            finCiclo=inicioCiclo+totalLiquidacionesZincPlata
            filaTotal=finCiclo
            filasNot.add(filaTotal)

            sheet1.addCell(new Label(3,filaTotal, "SUBTOTALES",formatoEncabezado))
            for (int col=6;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =inicioCiclo;fil<finCiclo;fil++){
                    def contenido=(sheet1.getWritableCell(col,fil).contents.equals(""))?"0":sheet1.getWritableCell(col,fil).contents
                    def valor = Double.parseDouble(contenido)
                    tret+=valor
                }
                if (!colsNot.contains(col))
                    sheet1.addCell(new Number(col,filaTotal, tret,formatoTotales))
            }
            fila++
        }

        if(liquidacionesCobrePlata){
            liquidacionesCobrePlata.each {
                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new DateTime(1,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(2,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(3,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(4,fila, "Cu Ag",formatoDatos))
                sheet1.addCell(new Label(5,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(6,fila, Float.parseFloat(it.cantidadDeSacos),formatoDatos))
                sheet1.addCell(new Number(7,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(8,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(9,fila, it.porcentajeHumedadFinal,formatoDatos))
                sheet1.addCell(new Number(10,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(11,fila, 0,formatoDatos)) //ZN
                sheet1.addCell(new Number(12,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(13,fila, it.porcentajePlataFinal,formatoDatos)) //AG
                sheet1.addCell(new Number(14,fila, it.porcentajeCobreFinal,formatoDatos)) //CU
                sheet1.addCell(new Number(15,fila, 0,formatoDatos)) //ZN
                sheet1.addCell(new Number(16,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(17,fila, it.kilosFinosPlata,formatoDatos)) //AG
                sheet1.addCell(new Number(18,fila, it.kilosFinosCobre,formatoDatos)) //cu
                sheet1.addCell(new Number(19,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(20,fila, it.valorNetoMineralEnBolivianos,formatoDatos))
                sheet1.addCell(new Number(21,fila, it.regaliaMinera,formatoDatos))

                columna=22
                //DESPLIEGUE DE RETENCIONES DE LEY
                def retencionesDeLeyLiquidacion = LiquidacionDeCobrePlataRetenciones.findAllByLiquidacionDeCobrePlataAndTipoDeRetencion(it,"DE LEY")
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
                def retencionesOtrasLiquidacion = LiquidacionDeCobrePlataRetenciones.findAllByLiquidacionDeCobrePlataAndTipoDeRetencion(it,"Otra")
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

                fila++
            }
            //hacer un espacio para detallar el siguiente elemento
            fila++
            //SUBTOTALES COMPLEJO
            totalLiquidacionesCobrePlata = liquidacionesCobrePlata.size()
            inicioCiclo=7+((liquidacionesCobrePlata)?totalLiquidacionesCobrePlata+2:0)+((liquidacionesPlomoPlata)?totalLiquidacionesPlomoPlata+2:0)+((liquidacionesZincPlata)?totalLiquidacionesZincPlata+2:0)
            finCiclo=inicioCiclo+totalLiquidacionesCobrePlata
            filaTotal=finCiclo
            filasNot.add(filaTotal)

            sheet1.addCell(new Label(3,filaTotal, "TOTALES",formatoEncabezado))
            for (int col=6;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =inicioCiclo;fil<finCiclo;fil++){
                    def contenido=(sheet1.getWritableCell(col,fil).contents.equals(""))?"0":sheet1.getWritableCell(col,fil).contents
                    def valor = Double.parseDouble(contenido)
                    tret+=valor
                }
                if (!colsNot.contains(col))
                    sheet1.addCell(new Number(col,filaTotal, tret,formatoTotales))
            }
            fila++
        }
        
        
        //def colsNot=[16,18,19,20,21,22,23,30,31,32,33,34,35,37,38,39,40,41,42]
//        columnaFinalRetenciones = 29+retencionesDeLey.size()+retencionesOtras.size()
//        def totalLiquidaciones = fila+1
//        sheet1.addCell(new Label(3,totalLiquidaciones-1, "TOTALES",formatoEncabezado))
//        for (int col=6;col<columnaFinalRetenciones;col++){
//            def tret=0
//            for (int fil =7;fil<totalLiquidaciones+7;fil++){
//                def contenido=(sheet1.getWritableCell(col,fil).contents.equals(""))?"0":sheet1.getWritableCell(col,fil).contents
//                def valor = Double.parseDouble(contenido)
//                if (!filasNot.contains(fil))
//                    tret+=valor
//            }
//            if (!colsNot.contains(col))
//                sheet1.addCell(new Number(col,totalLiquidaciones-1, tret,formatoTotales))
//        }

//        sheet1.removeColumn(2)
        sheet1.removeColumn(2)
        sheet1.removeColumn(17)
        sheet1.removeColumn(13)
//        sheet1.removeColumn(2)

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
