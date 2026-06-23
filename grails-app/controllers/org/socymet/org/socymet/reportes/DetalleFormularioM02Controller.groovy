package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

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
class DetalleFormularioM02Controller {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [detalleFormularioM02InstanceList: DetalleFormularioM02.list(params), detalleFormularioM02InstanceTotal: DetalleFormularioM02.count()]
    }

    def create() {
        [detalleFormularioM02Instance: new DetalleFormularioM02(params)]
    }

    def save() {
        def detalleFormularioM02Instance = new DetalleFormularioM02(params)
        if (!detalleFormularioM02Instance.save(flush: true)) {
            render(view: "create", model: [detalleFormularioM02Instance: detalleFormularioM02Instance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'detalleFormularioM02.label', default: 'DetalleFormularioM02'), detalleFormularioM02Instance.id])
        redirect(action: "show", id: detalleFormularioM02Instance.id)
    }

    def show(Long id) {
        def detalleFormularioM02Instance = DetalleFormularioM02.get(id)
        if (!detalleFormularioM02Instance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'detalleFormularioM02.label', default: 'DetalleFormularioM02'), id])
            redirect(action: "list")
            return
        }

        [detalleFormularioM02Instance: detalleFormularioM02Instance]
    }

    def edit(Long id) {
        def detalleFormularioM02Instance = DetalleFormularioM02.get(id)
        if (!detalleFormularioM02Instance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'detalleFormularioM02.label', default: 'DetalleFormularioM02'), id])
            redirect(action: "list")
            return
        }

        [detalleFormularioM02Instance: detalleFormularioM02Instance]
    }

    def update(Long id, Long version) {
        def detalleFormularioM02Instance = DetalleFormularioM02.get(id)
        if (!detalleFormularioM02Instance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'detalleFormularioM02.label', default: 'DetalleFormularioM02'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (detalleFormularioM02Instance.version > version) {
                detalleFormularioM02Instance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'detalleFormularioM02.label', default: 'DetalleFormularioM02')] as Object[],
                        "Another user has updated this DetalleFormularioM02 while you were editing")
                render(view: "edit", model: [detalleFormularioM02Instance: detalleFormularioM02Instance])
                return
            }
        }

        detalleFormularioM02Instance.properties = params

        if (!detalleFormularioM02Instance.save(flush: true)) {
            render(view: "edit", model: [detalleFormularioM02Instance: detalleFormularioM02Instance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'detalleFormularioM02.label', default: 'DetalleFormularioM02'), detalleFormularioM02Instance.id])
        redirect(action: "show", id: detalleFormularioM02Instance.id)
    }

    def delete(Long id) {
        def detalleFormularioM02Instance = DetalleFormularioM02.get(id)
        if (!detalleFormularioM02Instance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'detalleFormularioM02.label', default: 'DetalleFormularioM02'), id])
            redirect(action: "list")
            return
        }

        try {
            detalleFormularioM02Instance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'detalleFormularioM02.label', default: 'DetalleFormularioM02'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'detalleFormularioM02.label', default: 'DetalleFormularioM02'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearDetalle = {
        /*FALTA PONER TITULO DEL ELEMENTO Y DEL PERIODO DEL REPORTE*/

        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Detalle de FORM M-02", 0)
        sheet1.setColumnView(0,50)
        sheet1.setRowView(6,500)
        for(i in 1..100)
            sheet1.setColumnView(i,14)
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

        sheet1.addCell(new Label(0,4, "PERIODO:"))
        sheet1.addCell(new Label(1,4, "${new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)} AL ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)}"))

        sheet1.addCell(new Label(0,6, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "KILOS BRUTOS",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "KILOS NETOS SECOS",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "LEY %Zn",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "LEY %Pb",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "LEY DM Ag",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "LEY %Cu",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "RM Bs",formatoEncabezado))

        /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA RETENCIONES DE LEY*/
        def retencionesDeLey=new ArrayList<String>()

        def liquidacionesPlomoPlataTodas = LiquidacionDePlomoPlata.findAllByFechaDeLiquidacionGreaterThanEqualsAndFechaDeLiquidacionLessThanEquals(fechaInicial,fechaFinal)
        def liquidacionesZincPlataTodas = LiquidacionDeZincPlata.findAllByFechaDeLiquidacionGreaterThanEqualsAndFechaDeLiquidacionLessThanEquals(fechaInicial,fechaFinal)
        def liquidacionesComplejoTodas = LiquidacionDeComplejo.findAllByFechaDeLiquidacionGreaterThanEqualsAndFechaDeLiquidacionLessThanEquals(fechaInicial,fechaFinal)
        def liquidacionesCobrePlataTodas = LiquidacionDeCobrePlata.findAllByFechaDeLiquidacionGreaterThanEqualsAndFechaDeLiquidacionLessThanEquals(fechaInicial,fechaFinal)
        
        def listaRetencionesDeLeyComplejo = retencionesComplejoJSON liquidacionesComplejoTodas,"DE LEY"
        listaRetencionesDeLeyComplejo.each {
            if(!retencionesDeLey.contains(it.toString()))
                retencionesDeLey.add(it)
        }
        def listaRetencionesDeLeyPlomoPlata = retencionesPlomoPlataJSON liquidacionesPlomoPlataTodas,"DE LEY"
        listaRetencionesDeLeyPlomoPlata.each {
            if(!retencionesDeLey.contains(it.toString()))
                retencionesDeLey.add(it)
        }
        def listaRetencionesDeLeyZincPlata = retencionesZincPlataJSON liquidacionesZincPlataTodas,"DE LEY"
        listaRetencionesDeLeyZincPlata.each {
            if(!retencionesDeLey.contains(it.toString()))
                retencionesDeLey.add(it)
        }
        def listaRetencionesDeLeyCobrePlata = retencionesCobrePlataJSON liquidacionesCobrePlataTodas,"DE LEY"
        listaRetencionesDeLeyCobrePlata.each {
            if(!retencionesDeLey.contains(it.toString()))
                retencionesDeLey.add(it)
        }
        
        def columna = 9
        retencionesDeLey.each {
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

        def supertotalKilosBrutos = 0
        def supertotalKilosNetosSecos = 0
        def supertotalValorNeto = 0
        def supertotalRegalia = 0

        def supertotalesRetenciones = new BigDecimal[retencionesDeLey.size()]
        for (int i;i<supertotalesRetenciones.size();i++)
            supertotalesRetenciones[i]=0

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

            def totalesRetenciones = new BigDecimal[retencionesDeLey.size()]
            for (int i;i<totalesRetenciones.size();i++)
                totalesRetenciones[i]=0

            //acumulando totales para todos los minerales
            liquidacionesPlomoPlata.each { plomoPlata ->
                totalKilosBrutos+=plomoPlata.pesoBruto
                totalKilosNetosSecos+=plomoPlata.kilosNetosSecos
                totalKilosFinosPlomo+=plomoPlata.kilosFinosPlomo
                totalKilosFinosPlata+=plomoPlata.kilosFinosPlata
                totalValorNeto+=plomoPlata.valorNetoMineralEnBolivianos
                totalRegalia+=plomoPlata.regaliaMinera                

                def retencionesDeLeyLiquidacion = LiquidacionDePlomoPlataRetenciones.findAllByLiquidacionDePlomoPlataAndTipoDeRetencion(plomoPlata,"DE LEY")
                for(int i=0;i<retencionesDeLey.size();i++){
                    def vr = valorRetencion(retencionesDeLey.get(i), retencionesDeLeyLiquidacion,retencionesDeLeyLiquidacion.size())
                    totalesRetenciones[i]=totalesRetenciones[i]+vr
                    supertotalesRetenciones[i]=supertotalesRetenciones[i]+vr
                }
            }
            liquidacionesZincPlata.each { zincPlata ->
                totalKilosBrutos+=zincPlata.pesoBruto
                totalKilosNetosSecos+=zincPlata.kilosNetosSecos
                totalKilosFinosZinc+=zincPlata.kilosFinosZinc
                totalKilosFinosPlata+=zincPlata.kilosFinosPlata
                totalValorNeto+=zincPlata.valorNetoMineralEnBolivianos
                totalRegalia+=zincPlata.regaliaMinera

                def retencionesDeLeyLiquidacion = LiquidacionDeZincPlataRetenciones.findAllByLiquidacionDeZincPlataAndTipoDeRetencion(zincPlata,"DE LEY")
                for(int i=0;i<retencionesDeLey.size();i++){
                    def vr = valorRetencion(retencionesDeLey.get(i), retencionesDeLeyLiquidacion,retencionesDeLeyLiquidacion.size())
                    totalesRetenciones[i]=totalesRetenciones[i]+vr
                    supertotalesRetenciones[i]=supertotalesRetenciones[i]+vr
                }
            }
            liquidacionesComplejo.each { complejo ->
                totalKilosBrutos+=complejo.pesoBruto
                totalKilosNetosSecos+=complejo.kilosNetosSecos
                totalKilosFinosZinc+=complejo.kilosFinosZinc
                totalKilosFinosPlomo+=complejo.kilosFinosPlomo
                totalKilosFinosPlata+=complejo.kilosFinosPlata
                totalValorNeto+=complejo.valorNetoMineralEnBolivianos
                totalRegalia+=complejo.regaliaMinera

                def retencionesDeLeyLiquidacion = LiquidacionDeComplejoRetenciones.findAllByLiquidacionDeComplejoAndTipoDeRetencion(complejo,"DE LEY")
                for(int i=0;i<retencionesDeLey.size();i++){
                    def vr = valorRetencion(retencionesDeLey.get(i), retencionesDeLeyLiquidacion,retencionesDeLeyLiquidacion.size())
                    totalesRetenciones[i]=totalesRetenciones[i]+vr
                    supertotalesRetenciones[i]=supertotalesRetenciones[i]+vr
                }
            }
            liquidacionesCobrePlata.each { cobre ->
                totalKilosBrutos+=cobre.pesoBruto
                totalKilosNetosSecos+=cobre.kilosNetosSecos
                totalKilosFinosCobre+=cobre.kilosFinosCobre
                totalKilosFinosPlata+=cobre.kilosFinosPlata
                totalValorNeto+=cobre.valorNetoMineralEnBolivianos
                totalRegalia+=cobre.regaliaMinera

                def retencionesDeLeyLiquidacion = LiquidacionDeCobrePlataRetenciones.findAllByLiquidacionDeCobrePlataAndTipoDeRetencion(cobre,"DE LEY")
                for(int i=0;i<retencionesDeLey.size();i++){
                    def vr = valorRetencion(retencionesDeLey.get(i), retencionesDeLeyLiquidacion,retencionesDeLeyLiquidacion.size())
                    totalesRetenciones[i]=totalesRetenciones[i]+vr
                    supertotalesRetenciones[i]=supertotalesRetenciones[i]+vr
                }
            }

            leyZinc = (totalKilosNetosSecos==0)?0:100*totalKilosFinosZinc/totalKilosNetosSecos
            leyPlomo = (totalKilosNetosSecos==0)?0:100*totalKilosFinosPlomo/totalKilosNetosSecos
            leyPlata = (totalKilosNetosSecos==0)?0:10000*totalKilosFinosPlata/totalKilosNetosSecos
            leyCobre = (totalKilosNetosSecos==0)?0:100*totalKilosFinosCobre/totalKilosNetosSecos

            log.error("leyCobre: $leyCobre totalKilosFinosCobre: $totalKilosFinosCobre totalKilosNetosSecos: $totalKilosNetosSecos")

            sheet1.addCell(new Label(0,fila, e.toString(),formatoDatos))
            sheet1.addCell(new Number(1,fila, totalKilosBrutos,formatoDatos))
            sheet1.addCell(new Number(2,fila, totalKilosNetosSecos,formatoDatos))
            sheet1.addCell(new Number(3,fila, leyZinc,formatoDatos))
            sheet1.addCell(new Number(4,fila, leyPlomo,formatoDatos))
            sheet1.addCell(new Number(5,fila, leyPlata,formatoDatos))
            sheet1.addCell(new Number(6,fila, leyCobre,formatoDatos))
            sheet1.addCell(new Number(7,fila, totalValorNeto,formatoDatos))
            sheet1.addCell(new Number(8,fila, totalRegalia,formatoDatos))

            for(int i=0;i<totalesRetenciones.size();i++){
                sheet1.addCell(new Number(9+i,fila, totalesRetenciones[i],formatoDatos))
            }
            
            supertotalKilosBrutos+=totalKilosBrutos
            supertotalKilosNetosSecos+=totalKilosNetosSecos
            supertotalValorNeto+=totalValorNeto
            supertotalRegalia+=totalRegalia
            
            totalKilosBrutos = 0
            totalKilosNetosSecos = 0
            totalKilosFinosZinc = 0
            totalKilosFinosPlomo = 0
            totalKilosFinosPlata = 0
            totalKilosFinosCobre = 0
            leyZinc = 0
            leyPlomo = 0
            leyPlata = 0
            leyCobre = 0
            totalValorNeto = 0
            totalRegalia = 0

            fila++
        }

        sheet1.addCell(new Number(1,fila, supertotalKilosBrutos,formatoTotales))
        sheet1.addCell(new Number(2,fila, supertotalKilosNetosSecos,formatoTotales))
        sheet1.addCell(new Number(7,fila, supertotalValorNeto,formatoTotales))
        sheet1.addCell(new Number(8,fila, supertotalRegalia,formatoTotales))

        for(int i=0;i<supertotalesRetenciones.size();i++){
            sheet1.addCell(new Number(9+i,fila, supertotalesRetenciones[i],formatoTotales))
        }

        sheet1.removeRow(2)
        sheet1.removeRow(1)
        
        //RESUMEN PARA DEPOSITO EN CUENTAS BANCARIAS
        //INEFICIENTE PERO...QUE FLOREJA!
        WritableSheet sheet2 = workbook.createSheet("RESUMEN PARA DEPOSITO EN CUENTAS", 1)
        sheet2.setColumnView(0,50)
        for(i in 1..100)
            sheet2.setColumnView(i,14)
        def readCell
        def newCell
        def readFormat
        def newFormat
//        def numrows = fila+10
//        def numcols=9+supertotalesRetenciones.size()
        def numcols = fila+10
        def numrows=9+supertotalesRetenciones.size()
        def rango=1..8
        fila = 5
        for (int i = 0 ; i < numrows ; i++){
            for (int j = 0 ; j < numcols ; j++){
                if(i<1||i>8){
                    readCell = sheet1.getWritableCell(i, j);
                    newCell = readCell.copyTo(i, j);
                    readFormat = readCell.getCellFormat();
                    if (readFormat){
                        newFormat = new WritableCellFormat(readFormat);
                        newCell.setCellFormat(newFormat);
                        sheet2.addCell(newCell);
                    }
                }
            }
        }

        for(i in rango){
            sheet2.removeColumn(1)
        }

        sheet2.addCell(new Label(0,0, "DETALLE DE CUENTAS PARA DEPOSITO",formatoTitulo))
        sheet2.addCell(new Label(0,2, "PERIODO:"))
        sheet2.addCell(new Label(1,2, "${new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)} AL ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)}"))
        empresas.each { e ->
            sheet2.addCell(new Label(numrows-8,fila, e.numeroCuentaComibol,formatoDatos))
            sheet2.addCell(new Label(numrows-7,fila, e.numeroCuentaCNS,formatoDatos))
            sheet2.addCell(new Label(numrows-6,fila, e.municipio,formatoDatos))
            fila++
        }
        sheet2.addCell(new Label(numrows-8,4, "NRO. CUENTA COMIBOL",formatoEncabezado))
        sheet2.addCell(new Label(numrows-7,4, "NRO. CUENTA C.N.S.",formatoEncabezado))
        sheet2.addCell(new Label(numrows-6,4, "MUNICIPIO",formatoEncabezado))

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
