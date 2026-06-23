package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import jxl.SheetSettings
import jxl.Workbook
import jxl.format.Alignment
import jxl.format.PageOrientation
import jxl.format.PaperSize
import jxl.write.*
import org.socymet.liquidacion.*
import org.socymet.proveedor.Empresa
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class ReporteHistorialEmpresaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reporteHistorialEmpresaInstanceList: ReporteHistorialEmpresa.list(params), reporteHistorialEmpresaInstanceTotal: ReporteHistorialEmpresa.count()]
    }

    def create() {
        [reporteHistorialEmpresaInstance: new ReporteHistorialEmpresa(params)]
    }

    def save() {
        def reporteHistorialEmpresaInstance = new ReporteHistorialEmpresa(params)
        if (!reporteHistorialEmpresaInstance.save(flush: true)) {
            render(view: "create", model: [reporteHistorialEmpresaInstance: reporteHistorialEmpresaInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reporteHistorialEmpresa.label', default: 'ReporteHistorialEmpresa'), reporteHistorialEmpresaInstance.id])
        redirect(action: "show", id: reporteHistorialEmpresaInstance.id)
    }

    def show(Long id) {
        def reporteHistorialEmpresaInstance = ReporteHistorialEmpresa.get(id)
        if (!reporteHistorialEmpresaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteHistorialEmpresa.label', default: 'ReporteHistorialEmpresa'), id])
            redirect(action: "list")
            return
        }

        [reporteHistorialEmpresaInstance: reporteHistorialEmpresaInstance]
    }

    def edit(Long id) {
        def reporteHistorialEmpresaInstance = ReporteHistorialEmpresa.get(id)
        if (!reporteHistorialEmpresaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteHistorialEmpresa.label', default: 'ReporteHistorialEmpresa'), id])
            redirect(action: "list")
            return
        }

        [reporteHistorialEmpresaInstance: reporteHistorialEmpresaInstance]
    }

    def update(Long id, Long version) {
        def reporteHistorialEmpresaInstance = ReporteHistorialEmpresa.get(id)
        if (!reporteHistorialEmpresaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteHistorialEmpresa.label', default: 'ReporteHistorialEmpresa'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reporteHistorialEmpresaInstance.version > version) {
                reporteHistorialEmpresaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reporteHistorialEmpresa.label', default: 'ReporteHistorialEmpresa')] as Object[],
                        "Another user has updated this ReporteHistorialEmpresa while you were editing")
                render(view: "edit", model: [reporteHistorialEmpresaInstance: reporteHistorialEmpresaInstance])
                return
            }
        }

        reporteHistorialEmpresaInstance.properties = params

        if (!reporteHistorialEmpresaInstance.save(flush: true)) {
            render(view: "edit", model: [reporteHistorialEmpresaInstance: reporteHistorialEmpresaInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reporteHistorialEmpresa.label', default: 'ReporteHistorialEmpresa'), reporteHistorialEmpresaInstance.id])
        redirect(action: "show", id: reporteHistorialEmpresaInstance.id)
    }

    def delete(Long id) {
        def reporteHistorialEmpresaInstance = ReporteHistorialEmpresa.get(id)
        if (!reporteHistorialEmpresaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteHistorialEmpresa.label', default: 'ReporteHistorialEmpresa'), id])
            redirect(action: "list")
            return
        }

        try {
            reporteHistorialEmpresaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reporteHistorialEmpresa.label', default: 'ReporteHistorialEmpresa'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reporteHistorialEmpresa.label', default: 'ReporteHistorialEmpresa'), id])
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

        WritableSheet sheet1 = workbook.createSheet("Reporte de Compra Diaria", 0)
        sheet1.setRowView(6,500)
        for(i in 0..100)
            sheet1.setColumnView(i,11)
//        sheet1.setColumnView(0,11)//ajustar ancho de columnas (columna,ancho)
//        sheet1.setColumnView(1,11)//ajustar ancho de columnas (columna,ancho)
//        sheet1.setColumnView(2,11)
        sheet1.setColumnView(8,40)
        sheet1.setColumnView(9,40)

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
        response.setHeader('Content-Disposition', 'Attachment;Filename="reporte_historial_empresa.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(8,0, "HISTORIAL DE EMPRESA",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def tipoReporte = ""+params.tipoReporte
        def empresa=null
        def fechaInicial=null
        def fechaFinal=null

        def liquidacionesEstano=null
        def liquidacionesPlata=null
        def liquidacionesCobrePlata=null
        def liquidacionesWolfran=null
        def liquidacionesComplejo=null
        def liquidacionesPlomoPlata=null
        def liquidacionesZincPlata=null

        def retencionesDeLey=new ArrayList<String>()
        def retencionesOtras=new ArrayList<String>()

        if (tipoReporte.equals("fechas")){
            //empresa = Empresa.get(Integer.parseInt(""+params.empresa.id))
            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(3,2, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            liquidacionesEstano = LiquidacionDeEstano.findAllByFechaDeLiquidacionBetweenAndNombreEmpresaLike(fechaInicial,fechaFinal,"%%",[sort: 'nombreEmpresa'])
            liquidacionesPlata = LiquidacionDePlata.findAllByFechaDeLiquidacionBetweenAndNombreEmpresaLike(fechaInicial,fechaFinal,"%%",[sort: 'nombreEmpresa'])
            liquidacionesCobrePlata = LiquidacionDeCobrePlata.findAllByFechaDeLiquidacionBetweenAndNombreEmpresaLike(fechaInicial,fechaFinal,"%%",[sort: 'nombreEmpresa'])
            liquidacionesWolfran = LiquidacionDeWolfran.findAllByFechaDeLiquidacionBetweenAndNombreEmpresaLike(fechaInicial,fechaFinal,"%%",[sort: 'nombreEmpresa'])
            liquidacionesComplejo = LiquidacionDeComplejo.findAllByFechaDeLiquidacionBetweenAndNombreEmpresaLike(fechaInicial,fechaFinal,"%%",[sort: 'nombreEmpresa'])
            liquidacionesPlomoPlata = LiquidacionDePlomoPlata.findAllByFechaDeLiquidacionBetweenAndNombreEmpresaLike(fechaInicial,fechaFinal,"%%",[sort: 'nombreEmpresa'])
            liquidacionesZincPlata = LiquidacionDeZincPlata.findAllByFechaDeLiquidacionBetweenAndNombreEmpresaLike(fechaInicial,fechaFinal,"%%",[sort: 'nombreEmpresa'])

            System.out.println("*** RESULTADOS DE ESTANO: ${liquidacionesEstano.size()}")
            System.out.println("*** RESULTADOS DE PLATA: ${liquidacionesPlata.size()}")
            System.out.println("*** RESULTADOS DE COBRE PLATA: ${liquidacionesCobrePlata.size()}")
            System.out.println("*** RESULTADOS DE WOLFRAN: ${liquidacionesWolfran.size()}")
            System.out.println("*** RESULTADOS DE COMPLEJO: ${liquidacionesComplejo.size()}")
            System.out.println("*** RESULTADOS DE PlomoPlata: ${liquidacionesPlomoPlata.size()}")
            System.out.println("*** RESULTADOS DE ZincPlata: ${liquidacionesZincPlata.size()}")
        }
        if (tipoReporte.equals("fechasEmpresa")){
            empresa = Empresa.get(Integer.parseInt(""+params.empresa.id))

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)
            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(8,3, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(9,3, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(8,2, "ENTRE FECHAS:",formatoInfoReporte))
            sheet1.addCell(new Label(9,2, "${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            def liquidacionesSn = LiquidacionDeEstano.findAllByFechaDeLiquidacionBetween(fechaInicial,fechaFinal,[sort: 'nombreEmpresa'])
            liquidacionesEstano=new ArrayList<LiquidacionDeEstano>()
            liquidacionesSn.each {
                if(it.recepcionDeEstano.empresa.id==empresa.id)
                    liquidacionesEstano.add(it)
            }
            def liquidacionesAg = LiquidacionDePlata.findAllByFechaDeLiquidacionBetween(fechaInicial,fechaFinal,[sort: 'nombreEmpresa'])
            liquidacionesPlata=new ArrayList<LiquidacionDePlata>()
            liquidacionesAg.each {
                if(it.recepcionDePlata.empresa.id==empresa.id)
                    liquidacionesPlata.add(it)
            }
            def liquidacionesCu = LiquidacionDeCobrePlata.findAllByFechaDeLiquidacionBetween(fechaInicial,fechaFinal,[sort: 'nombreEmpresa'])
            liquidacionesCobrePlata=new ArrayList<LiquidacionDeCobrePlata>()
            liquidacionesCu.each {
                if(it.recepcionDeComplejo.empresa.id==empresa.id)
                    liquidacionesCobrePlata.add(it)
            }
            def liquidacionesWO3 = LiquidacionDeWolfran.findAllByFechaDeLiquidacionBetween(fechaInicial,fechaFinal,[sort: 'nombreEmpresa'])
            liquidacionesWolfran=new ArrayList<LiquidacionDeWolfran>()
            liquidacionesWO3.each {
                if(it.recepcionDeWolfran.empresa.id==empresa.id)
                    liquidacionesWolfran.add(it)
            }
            def liquidacionesCm = LiquidacionDeComplejo.findAllByFechaDeLiquidacionBetween(fechaInicial,fechaFinal,[sort: 'nombreEmpresa'])
            liquidacionesComplejo=new ArrayList<LiquidacionDeComplejo>()
            liquidacionesCm.each {
                if(it.recepcionDeComplejo.empresa.id==empresa.id)
                    liquidacionesComplejo.add(it)
            }
            def liquidacionesPbAg = LiquidacionDePlomoPlata.findAllByFechaDeLiquidacionBetween(fechaInicial,fechaFinal,[sort: 'nombreEmpresa'])
            liquidacionesPlomoPlata=new ArrayList<LiquidacionDePlomoPlata>()
            liquidacionesPbAg.each {
                if(it.recepcionDeComplejo.empresa.id==empresa.id)
                    liquidacionesPlomoPlata.add(it)
            }
            def liquidacionesZnAg = LiquidacionDeZincPlata.findAllByFechaDeLiquidacionBetween(fechaInicial,fechaFinal,[sort: 'nombreEmpresa'])
            liquidacionesZincPlata=new ArrayList<LiquidacionDeZincPlata>()
            liquidacionesZnAg.each {
                if(it.recepcionDeComplejo.empresa.id==empresa.id)
                    liquidacionesZincPlata.add(it)
            }
            System.out.println("*** RESULTADOS DE ESTANO: ${liquidacionesEstano.size()}")
            System.out.println("*** RESULTADOS DE PLATA: ${liquidacionesPlata.size()}")
            System.out.println("*** RESULTADOS DE COBRE PLATA: ${liquidacionesCobrePlata.size()}")
            System.out.println("*** RESULTADOS DE WOLFRAN: ${liquidacionesWolfran.size()}")
            System.out.println("*** RESULTADOS DE COMPLEJO: ${liquidacionesComplejo.size()}")
            System.out.println("*** RESULTADOS DE PlomoPlata: ${liquidacionesPlomoPlata.size()}")
            System.out.println("*** RESULTADOS DE ZincPlata: ${liquidacionesZincPlata.size()}")
        }

        /*CONTROLAR SI SON null LAS LISTAS OBTENIDAS DE LIQUIDACIONES*/

        /*GENERANDO LISTA GENERAL DE RETENCIONES DE LEY*/
        if(liquidacionesEstano){
            def listaRetencionesDeLeyEstano = retencionesEstanoJSON liquidacionesEstano,"DE LEY"
            listaRetencionesDeLeyEstano.each {
                if(!retencionesDeLey.contains(it.toString()))
                    retencionesDeLey.add(it)
            }
        }
        if(liquidacionesPlata){
            def listaRetencionesDeLeyPlata = retencionesPlataJSON liquidacionesPlata,"DE LEY"
            listaRetencionesDeLeyPlata.each {
                if(!retencionesDeLey.contains(it.toString()))
                    retencionesDeLey.add(it)
            }
        }
        if(liquidacionesWolfran){
            def listaRetencionesDeLeyWolfran = retencionesWolfranJSON liquidacionesWolfran,"DE LEY"
            listaRetencionesDeLeyWolfran.each {
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
        /*FIN - GENERANDO LISTA GENERAL DE RETENCIONES DE LEY*/

        /*GENERANDO LISTA GENERAL DE OTRAS RETENCIONES*/
        if(liquidacionesEstano){
            def listaRetencionesDeLeyEstano = retencionesEstanoJSON liquidacionesEstano,"OTRA"
            listaRetencionesDeLeyEstano.each {
                if(!retencionesOtras.contains(it.toString()))
                    retencionesOtras.add(it)
            }
        }
        if(liquidacionesPlata){
            def listaRetencionesDeLeyPlata = retencionesPlataJSON liquidacionesPlata,"OTRA"
            listaRetencionesDeLeyPlata.each {
                if(!retencionesOtras.contains(it.toString()))
                    retencionesOtras.add(it)
            }
        }
        if(liquidacionesWolfran){
            def listaRetencionesDeLeyWolfran = retencionesWolfranJSON liquidacionesWolfran,"OTRA"
            listaRetencionesDeLeyWolfran.each {
                if(!retencionesOtras.contains(it.toString()))
                    retencionesOtras.add(it)
            }
        }
        if(liquidacionesCobrePlata){
            def listaRetencionesDeLeyCobrePlata = retencionesCobrePlataJSON liquidacionesCobrePlata,"OTRA"
            listaRetencionesDeLeyCobrePlata.each {
                if(!retencionesOtras.contains(it.toString()))
                    retencionesOtras.add(it)
            }
        }
        if(liquidacionesComplejo){
            def listaRetencionesDeLeyComplejo = retencionesComplejoJSON liquidacionesComplejo,"OTRA"
            listaRetencionesDeLeyComplejo.each {
                if(!retencionesOtras.contains(it.toString()))
                    retencionesOtras.add(it)
            }
        }
        if(liquidacionesPlomoPlata){
            def listaRetencionesDeLeyPlomoPlata = retencionesPlomoPlataJSON liquidacionesPlomoPlata,"OTRA"
            listaRetencionesDeLeyPlomoPlata.each {
                if(!retencionesOtras.contains(it.toString()))
                    retencionesOtras.add(it)
            }
        }
        if(liquidacionesZincPlata){
            def listaRetencionesDeLeyZincPlata = retencionesZincPlataJSON liquidacionesZincPlata,"OTRA"
            listaRetencionesDeLeyZincPlata.each {
                if(!retencionesOtras.contains(it.toString()))
                    retencionesOtras.add(it)
            }
        }
        /*FIN - GENERANDO LISTA GENERAL DE OTRAS RETENCIONES*/

        sheet1.addCell(new Label(0,6, "FEC. REP.",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "COT. DIA Sn",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "COT. DIA Cu",formatoEncabezado))
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
        sheet1.addCell(new Label(19,6, "LEY %Cu",formatoEncabezado))
        sheet1.addCell(new Label(20,6, "LEY %WO3",formatoEncabezado))
        sheet1.addCell(new Label(21,6, "LEY %Zn",formatoEncabezado))
        sheet1.addCell(new Label(22,6, "LEY %Pb",formatoEncabezado))
        sheet1.addCell(new Label(23,6, "LEY DM Ag",formatoEncabezado))
        sheet1.addCell(new Label(24,6, "LEY %As",formatoEncabezado))
        sheet1.addCell(new Label(25,6, "K. F. Sn",formatoEncabezado))
        sheet1.addCell(new Label(26,6, "K. F. Cu",formatoEncabezado))
        sheet1.addCell(new Label(27,6, "K. F. WO3",formatoEncabezado))
        sheet1.addCell(new Label(28,6, "K. F. Zn",formatoEncabezado))
        sheet1.addCell(new Label(29,6, "K. F. Pb",formatoEncabezado))
        sheet1.addCell(new Label(30,6, "K. F. Ag",formatoEncabezado))
        sheet1.addCell(new Label(31,6, "K. F. As",formatoEncabezado))
        sheet1.addCell(new Label(32,6, "COT. OF. Sn",formatoEncabezado))
        sheet1.addCell(new Label(33,6, "COT. OF. Cu",formatoEncabezado))
        sheet1.addCell(new Label(34,6, "COT. OF. WO3",formatoEncabezado))
        sheet1.addCell(new Label(35,6, "COT. OF. Zn",formatoEncabezado))
        sheet1.addCell(new Label(36,6, "COT. OF. Pb",formatoEncabezado))
        sheet1.addCell(new Label(37,6, "COT. OF. Ag",formatoEncabezado))
        sheet1.addCell(new Label(38,6, "VALOR OF. BRUTO",formatoEncabezado))
        sheet1.addCell(new Label(39,6, "ALIC. Sn %",formatoEncabezado))
        sheet1.addCell(new Label(40,6, "ALIC. Cu %",formatoEncabezado))
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

        def colsNot=[16,18,19,20,21,22,23,24,32,33,34,35,36,37,39,40,41,42,43,44]
        def filasNot=new ArrayList()
        def inicioCiclo=0
        def finCiclo=0
        def filaTotal=0
        def totalLiquidacionesEstano = 0
        def totalLiquidacionesPlata = 0
        def totalLiquidacionesWolfran = 0
        def totalLiquidacionesCobrePlata = 0
        def totalLiquidacionesComplejo = 0
        def totalLiquidacionesPlomoPlata = 0
        def totalLiquidacionesZincPlata = 0
        def columnaFinalRetenciones = 60+retencionesDeLey.size()+retencionesOtras.size()
        def fila = 7

        if(liquidacionesEstano){
            def totalKilosNetosHumedosEstano=0
            def totalKilosNetosSecosEstano=0
            def totalKilosFinosEstanoEstano=0

            liquidacionesEstano.each {
                totalKilosNetosHumedosEstano+=it.kilosNetosHumedos
                totalKilosNetosSecosEstano+=it.kilosNetosSecos
                totalKilosFinosEstanoEstano+=it.kilosFinosEstano

                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new Number(1,fila, it.recepcionDeEstano.cotizacionDiariaDeMinerales.estano,formatoDatos)) //SN
                sheet1.addCell(new Number(2,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(3,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(4,fila, 0,formatoDatos)) //ZN
                sheet1.addCell(new Number(5,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(6,fila, 0,formatoDatos)) //AG
                sheet1.addCell(new DateTime(7,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(8,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(9,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(10,fila, "Sn",formatoDatos))
                sheet1.addCell(new Label(11,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(12,fila, it.cantidadDeSacos,formatoDatos))
                sheet1.addCell(new Number(13,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(14,fila, it.tara*it.cantidadDeSacos,formatoDatos))
                sheet1.addCell(new Number(15,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(16,fila, it.humedad,formatoDatos))
                sheet1.addCell(new Number(17,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(18,fila, it.porcentajeEstano,formatoDatos)) //SN
                sheet1.addCell(new Number(19,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(20,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(21,fila, 0,formatoDatos)) //ZN
                sheet1.addCell(new Number(22,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(23,fila, 0,formatoDatos)) //AG
                sheet1.addCell(new Number(24,fila, 0,formatoDatos)) //AS
                sheet1.addCell(new Number(25,fila, it.kilosFinosEstano,formatoDatos)) //SN
                sheet1.addCell(new Number(26,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(27,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(28,fila, 0,formatoDatos)) //ZN
                sheet1.addCell(new Number(29,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(30,fila, 0,formatoDatos)) //AG
                sheet1.addCell(new Number(31,fila, 0,formatoDatos)) //AS
                sheet1.addCell(new Number(32,fila, it.recepcionDeEstano.cotizacionQuincenalDeMinerales.estano,formatoDatos)) //SN
                sheet1.addCell(new Number(33,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(34,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(35,fila, 0,formatoDatos)) //ZN
                sheet1.addCell(new Number(36,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(37,fila, 0,formatoDatos)) //AG
                sheet1.addCell(new Number(38,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(39,fila, it.recepcionDeEstano.alicuota.estano,formatoDatos)) //SN
                sheet1.addCell(new Number(40,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(41,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(42,fila, 0,formatoDatos)) //ZN
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
                def retencionesDeLeyLiquidacion = LiquidacionDeEstanoRetenciones.findAllByLiquidacionDeEstanoAndTipoDeRetencion(it,"DE LEY")
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
                def retencionesOtrasLiquidacion = LiquidacionDeEstanoRetenciones.findAllByLiquidacionDeEstanoAndTipoDeRetencion(it,"OTRA")
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
                sheet1.addCell(new Number(columna+5,fila, it.recepcionDeEstano.costoDeTransporte,formatoDatos))
                sheet1.addCell(new Number(columna+6,fila, it.totalCostoLaboratorio,formatoDatos))
                sheet1.addCell(new Number(columna+6,fila, it.totalCostoLaboratorio,formatoDatos))

                fila++
            }
            //hacer un espacio para detallar el siguiente elemento
            fila++
            //SUBTOTALES ESTANO
            totalLiquidacionesEstano = liquidacionesEstano.size()
            filaTotal=7+totalLiquidacionesEstano
            inicioCiclo=7
            finCiclo=7+totalLiquidacionesEstano
            filasNot.add(filaTotal)

            sheet1.addCell(new Label(9,filaTotal, "SUBTOTALES",formatoEncabezado))
            for (int col=12;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =inicioCiclo;fil<finCiclo;fil++){
                    def contenido=(sheet1.getWritableCell(col,fil).contents.equals(""))?"0":sheet1.getWritableCell(col,fil).contents
                    def valor = Double.parseDouble(contenido)
                    tret+=(valor<0)?0:valor
                }
                if (!colsNot.contains(col))
                    sheet1.addCell(new Number(col,filaTotal, tret,formatoTotales))
            }

            //humedad
            sheet1.addCell(new Number(16,filaTotal, (totalKilosNetosSecosEstano/totalKilosNetosHumedosEstano*100-100)*-1,formatoTotales))
            //ley
            sheet1.addCell(new Number(18,filaTotal, totalKilosFinosEstanoEstano/totalKilosNetosSecosEstano*100,formatoTotales))

            fila++
        }

        if(liquidacionesPlata){
            def totalKilosNetosHumedosPlata=0
            def totalKilosNetosSecosPlata=0
            def totalKilosFinosPlataPlata=0

            liquidacionesPlata.each {
                totalKilosNetosHumedosPlata+=it.kilosNetosHumedos
                totalKilosNetosSecosPlata+=it.kilosNetosSecos
                totalKilosFinosPlataPlata+=it.kilosFinosPlata

                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new Number(1,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(2,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(3,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(4,fila, 0,formatoDatos)) //ZN
                sheet1.addCell(new Number(5,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(6,fila, it.recepcionDePlata.cotizacionDiariaDeMinerales.plata,formatoDatos)) //AG
                sheet1.addCell(new DateTime(7,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(8,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(9,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(10,fila, "Ag",formatoDatos))
                sheet1.addCell(new Label(11,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(12,fila, it.cantidadDeSacos,formatoDatos))
                sheet1.addCell(new Number(13,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(14,fila, it.tara*it.cantidadDeSacos,formatoDatos))
                sheet1.addCell(new Number(15,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(16,fila, it.humedad,formatoDatos))
                sheet1.addCell(new Number(17,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(18,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(19,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(20,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(21,fila, 0,formatoDatos)) //ZN
                sheet1.addCell(new Number(22,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(23,fila, it.porcentajePlata,formatoDatos)) //AG
                sheet1.addCell(new Number(24,fila, 0,formatoDatos)) //AS
                sheet1.addCell(new Number(25,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(26,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(27,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(28,fila, 0,formatoDatos)) //ZN
                sheet1.addCell(new Number(29,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(30,fila, it.kilosFinosPlata,formatoDatos)) //AG
                sheet1.addCell(new Number(31,fila, 0,formatoDatos)) //AS
                sheet1.addCell(new Number(32,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(33,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(34,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(35,fila, 0,formatoDatos)) //ZN
                sheet1.addCell(new Number(36,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(37,fila, it.recepcionDePlata.cotizacionQuincenalDeMinerales.plata,formatoDatos)) //AG
                sheet1.addCell(new Number(38,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(39,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(40,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(41,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(42,fila, 0,formatoDatos)) //ZN
                sheet1.addCell(new Number(43,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(44,fila, it.recepcionDePlata.alicuota.plata,formatoDatos)) //AG
                sheet1.addCell(new Number(45,fila, it.valorNetoMineral,formatoDatos))
                sheet1.addCell(new Number(46,fila, it.valorNetoMineralEnBolivianos,formatoDatos))
                sheet1.addCell(new Number(47,fila, it.bonoCalidad,formatoDatos))
                sheet1.addCell(new Number(48,fila, it.bonoIncentivo,formatoDatos))
                sheet1.addCell(new Number(49,fila, it.valorDeCompra,formatoDatos))
                sheet1.addCell(new Number(50,fila, it.regaliaMinera,formatoDatos))

                columna=51
                //DESPLIEGUE DE RETENCIONES DE LEY
                def retencionesDeLeyLiquidacion = LiquidacionDePlataRetenciones.findAllByLiquidacionDePlataAndTipoDeRetencion(it,"DE LEY")
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
                def retencionesOtrasLiquidacion = LiquidacionDePlataRetenciones.findAllByLiquidacionDePlataAndTipoDeRetencion(it,"OTRA")
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
                sheet1.addCell(new Number(columna+5,fila, it.recepcionDePlata.costoDeTransporte,formatoDatos))
                sheet1.addCell(new Number(columna+6,fila, it.totalCostoLaboratorio,formatoDatos))

                fila++
            }
            //hacer un espacio para detallar el siguiente elemento
            fila++
            //SUBTOTALES PLATA
            totalLiquidacionesPlata = liquidacionesPlata.size()
            //inicioCiclo=7+totalLiquidacionesEstano+2
            inicioCiclo=7+((liquidacionesEstano)?totalLiquidacionesEstano+2:0)
            finCiclo=inicioCiclo+totalLiquidacionesPlata
            filaTotal=finCiclo
            filasNot.add(filaTotal)

            sheet1.addCell(new Label(9,filaTotal, "SUBTOTALES",formatoEncabezado))
            for (int col=12;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =inicioCiclo;fil<finCiclo;fil++){
                    def contenido=(sheet1.getWritableCell(col,fil).contents.equals(""))?"0":sheet1.getWritableCell(col,fil).contents
                    def valor = Double.parseDouble(contenido)
                    tret+=(valor<0)?0:valor
                }
                if (!colsNot.contains(col))
                    sheet1.addCell(new Number(col,filaTotal, tret,formatoTotales))
            }

            //humedad
            sheet1.addCell(new Number(16,filaTotal, (totalKilosNetosSecosPlata/totalKilosNetosHumedosPlata*100-100)*-1,formatoTotales))
            //ley
            sheet1.addCell(new Number(23,filaTotal, 10000*totalKilosFinosPlataPlata/totalKilosNetosSecosPlata,formatoTotales))

            fila++
        }

        if(liquidacionesWolfran){
            def totalKilosNetosHumedosWolfran=0
            def totalKilosNetosSecosWolfran=0
            def totalKilosFinosWolfranWolfran=0

            liquidacionesWolfran.each {
                totalKilosNetosHumedosWolfran+=it.kilosNetosHumedos
                totalKilosNetosSecosWolfran+=it.kilosNetosSecos
                totalKilosFinosWolfranWolfran+=it.kilosFinosWolfran

                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new Number(1,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(2,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(3,fila, it.recepcionDeWolfran.cotizacionDiariaDeMinerales.wolfran,formatoDatos)) //WO3
                sheet1.addCell(new Number(4,fila, 0,formatoDatos)) //ZN
                sheet1.addCell(new Number(5,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(6,fila, 0,formatoDatos)) //AG
                sheet1.addCell(new DateTime(7,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(8,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(9,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(10,fila, "WO3",formatoDatos))
                sheet1.addCell(new Label(11,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(12,fila, it.cantidadDeSacos,formatoDatos))
                sheet1.addCell(new Number(13,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(14,fila, it.tara*it.cantidadDeSacos,formatoDatos))
                sheet1.addCell(new Number(15,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(16,fila, it.humedad,formatoDatos))
                sheet1.addCell(new Number(17,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(18,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(19,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(20,fila, it.porcentajeWolfran,formatoDatos)) //WO3
                sheet1.addCell(new Number(21,fila, 0,formatoDatos)) //ZN
                sheet1.addCell(new Number(22,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(23,fila, 0,formatoDatos)) //AG
                sheet1.addCell(new Number(24,fila, 0,formatoDatos)) //AS
                sheet1.addCell(new Number(25,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(26,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(27,fila, it.kilosFinosWolfran,formatoDatos)) //WO3
                sheet1.addCell(new Number(28,fila, 0,formatoDatos)) //ZN
                sheet1.addCell(new Number(29,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(30,fila, 0,formatoDatos)) //AG
                sheet1.addCell(new Number(31,fila, 0,formatoDatos)) //AG
                sheet1.addCell(new Number(32,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(33,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(34,fila, it.recepcionDeWolfran.cotizacionQuincenalDeMinerales.wolfran,formatoDatos)) //WO3
                sheet1.addCell(new Number(35,fila, 0,formatoDatos)) //ZN
                sheet1.addCell(new Number(36,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(37,fila, 0,formatoDatos)) //AG
                sheet1.addCell(new Number(38,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(39,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(40,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(41,fila, it.recepcionDeWolfran.alicuota.wolfran,formatoDatos)) //WO3
                sheet1.addCell(new Number(42,fila, 0,formatoDatos)) //ZN
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
                def retencionesDeLeyLiquidacion = LiquidacionDeWolfranRetenciones.findAllByLiquidacionDeWolfranAndTipoDeRetencion(it,"DE LEY")
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
                def retencionesOtrasLiquidacion = LiquidacionDeWolfranRetenciones.findAllByLiquidacionDeWolfranAndTipoDeRetencion(it,"OTRA")
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
                sheet1.addCell(new Number(columna+5,fila, it.recepcionDeWolfran.costoDeTransporte,formatoDatos))
                sheet1.addCell(new Number(columna+6,fila, it.totalCostoLaboratorio,formatoDatos))

                fila++
            }
            //hacer un espacio para detallar el siguiente elemento
            fila++
            //SUBTOTALES WOLFRAN
            totalLiquidacionesWolfran = liquidacionesWolfran.size()
            inicioCiclo=7+((liquidacionesEstano)?totalLiquidacionesEstano+2:0)+((liquidacionesPlata)?totalLiquidacionesPlata+2:0)
            finCiclo=inicioCiclo+totalLiquidacionesWolfran
            filaTotal=finCiclo
            filasNot.add(filaTotal)

            sheet1.addCell(new Label(9,filaTotal, "SUBTOTALES",formatoEncabezado))
            for (int col=12;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =inicioCiclo;fil<finCiclo;fil++){
                    def contenido=(sheet1.getWritableCell(col,fil).contents.equals(""))?"0":sheet1.getWritableCell(col,fil).contents
                    def valor = Double.parseDouble(contenido)
                    tret+=(valor<0)?0:valor
                }
                if (!colsNot.contains(col))
                    sheet1.addCell(new Number(col,filaTotal, tret,formatoTotales))
            }

            //humedad
            sheet1.addCell(new Number(16,filaTotal, (totalKilosNetosSecosWolfran/totalKilosNetosHumedosWolfran*100-100)*-1,formatoTotales))
            //ley
            sheet1.addCell(new Number(20,filaTotal, totalKilosFinosWolfranWolfran/totalKilosNetosSecosWolfran*100,formatoTotales))

            fila++
        }

        if(liquidacionesCobrePlata){
            def totalKilosNetosHumedosCobrePlata=0
            def totalKilosNetosSecosCobrePlata=0
            def totalKilosFinosCobreCobrePlata=0
            def totalKilosFinosPlataCobrePlata=0

            liquidacionesCobrePlata.each {
                totalKilosNetosHumedosCobrePlata+=it.kilosNetosHumedos
                totalKilosNetosSecosCobrePlata+=it.kilosNetosSecos
                totalKilosFinosCobreCobrePlata+=it.kilosFinosCobre
                totalKilosFinosPlataCobrePlata+=it.kilosFinosPlata

                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new Number(1,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(2,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.cobre,formatoDatos)) //SB
                sheet1.addCell(new Number(3,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(4,fila, 0,formatoDatos)) //ZN
                sheet1.addCell(new Number(5,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(6,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.cobre,formatoDatos)) //AG
                sheet1.addCell(new DateTime(7,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(8,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(9,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(10,fila, "CuAg",formatoDatos))
                sheet1.addCell(new Label(11,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(12,fila, it.cantidadDeSacos.toDouble(),formatoDatos))
                sheet1.addCell(new Number(13,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(14,fila, it.cantidadDeSacos.toDouble(),formatoDatos))
                sheet1.addCell(new Number(15,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(16,fila, it.humedad,formatoDatos))
                sheet1.addCell(new Number(17,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(18,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(19,fila, it.porcentajeCobreFinal,formatoDatos)) //SB
                sheet1.addCell(new Number(20,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(21,fila, 0,formatoDatos)) //ZN
                sheet1.addCell(new Number(22,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(23,fila, it.porcentajePlataFinal,formatoDatos)) //AG
                sheet1.addCell(new Number(24,fila, 0,formatoDatos)) //AS
                sheet1.addCell(new Number(25,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(26,fila, it.kilosFinosCobre,formatoDatos)) //SB
                sheet1.addCell(new Number(27,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(28,fila, 0,formatoDatos)) //ZN
                sheet1.addCell(new Number(29,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(30,fila, it.kilosFinosPlata,formatoDatos)) //AG
                sheet1.addCell(new Number(31,fila, 0,formatoDatos)) //AS
                sheet1.addCell(new Number(32,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(33,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.cobre,formatoDatos)) //SB
                sheet1.addCell(new Number(34,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(35,fila, 0,formatoDatos)) //ZN
                sheet1.addCell(new Number(36,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(37,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plata,formatoDatos)) //AG
                sheet1.addCell(new Number(38,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(39,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(40,fila, it.recepcionDeComplejo.alicuota.cobre,formatoDatos)) //SB
                sheet1.addCell(new Number(41,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(42,fila, 0,formatoDatos)) //ZN
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
                def retencionesOtrasLiquidacion = LiquidacionDeCobrePlataRetenciones.findAllByLiquidacionDeCobrePlataAndTipoDeRetencion(it,"OTRA")
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
                sheet1.addCell(new Number(columna+5,fila, it.recepcionDeComplejo.costoDeTransporte,formatoDatos))
                sheet1.addCell(new Number(columna+6,fila, it.totalCostoLaboratorio,formatoDatos))

                fila++
            }
            //hacer un espacio para detallar el siguiente elemento
            fila++
            //SUBTOTALES COBRE PLATA
            totalLiquidacionesCobrePlata = liquidacionesCobrePlata.size()
            inicioCiclo=7+((liquidacionesEstano)?totalLiquidacionesEstano+2:0)+((liquidacionesPlata)?totalLiquidacionesPlata+2:0)+((liquidacionesWolfran)?totalLiquidacionesWolfran+2:0)
            finCiclo=inicioCiclo+totalLiquidacionesCobrePlata
            filaTotal=finCiclo
            filasNot.add(filaTotal)

            sheet1.addCell(new Label(9,filaTotal, "SUBTOTALES",formatoEncabezado))
            for (int col=12;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =inicioCiclo;fil<finCiclo;fil++){
                    def contenido=(sheet1.getWritableCell(col,fil).contents.equals(""))?"0":sheet1.getWritableCell(col,fil).contents
                    def valor = Double.parseDouble(contenido)
                    tret+=(valor<0)?0:valor
                }
                if (!colsNot.contains(col))
                    sheet1.addCell(new Number(col,filaTotal, tret,formatoTotales))
            }

            //humedad
            sheet1.addCell(new Number(16,filaTotal, (totalKilosNetosSecosCobrePlata/totalKilosNetosHumedosCobrePlata*100-100)*-1,formatoTotales))
            //ley
            sheet1.addCell(new Number(19,filaTotal, totalKilosFinosCobreCobrePlata/totalKilosNetosSecosCobrePlata*100,formatoTotales))
            //sheet1.addCell(new Number(19,filaTotal, totalKilosFinosCobreCobrePlata/totalKilosNetosSecosCobrePlata*100000,formatoTotales))

            fila++
        }

        if(liquidacionesPlomoPlata){
            def totalKilosNetosHumedosPlomoPlata=0
            def totalKilosNetosSecosPlomoPlata=0
            def totalKilosFinosZincPlomoPlata=0
            def totalKilosFinosPlomoPlomoPlata=0
            def totalKilosFinosPlataPlomoPlata=0

            liquidacionesPlomoPlata.each {
                totalKilosNetosHumedosPlomoPlata+=it.kilosNetosHumedos
                totalKilosNetosSecosPlomoPlata+=it.kilosNetosSecos
                totalKilosFinosZincPlomoPlata+=0
                totalKilosFinosPlomoPlomoPlata+=it.kilosFinosPlomo
                totalKilosFinosPlataPlomoPlata+=it.kilosFinosPlata

                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new Number(1,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(2,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(3,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(4,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.zinc,formatoDatos)) //ZN
                sheet1.addCell(new Number(5,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plomo,formatoDatos)) //PB
                sheet1.addCell(new Number(6,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plata,formatoDatos)) //AG
                sheet1.addCell(new DateTime(7,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(8,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(9,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(10,fila, "Pb Ag",formatoDatos))
                sheet1.addCell(new Label(11,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(12,fila, Float.parseFloat(it.cantidadDeSacos),formatoDatos))
                sheet1.addCell(new Number(13,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(14,fila, it.merma,formatoDatos))
                sheet1.addCell(new Number(15,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(16,fila, it.humedad,formatoDatos))
                sheet1.addCell(new Number(17,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(18,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(19,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(20,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(21,fila, 0,formatoDatos)) //ZN
                sheet1.addCell(new Number(22,fila, it.porcentajePlomo,formatoDatos)) //PB
                sheet1.addCell(new Number(23,fila, it.porcentajePlata,formatoDatos)) //AG
                sheet1.addCell(new Number(24,fila, 0,formatoDatos)) //AS
                sheet1.addCell(new Number(25,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(26,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(27,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(28,fila, 0,formatoDatos)) //ZN
                sheet1.addCell(new Number(29,fila, it.kilosFinosPlomo,formatoDatos)) //PB
                sheet1.addCell(new Number(30,fila, it.kilosFinosPlata,formatoDatos)) //AG
                sheet1.addCell(new Number(31,fila, 0,formatoDatos)) //AS
                sheet1.addCell(new Number(32,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(33,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(34,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(35,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.zinc,formatoDatos)) //ZN
                sheet1.addCell(new Number(36,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plomo,formatoDatos)) //PB
                sheet1.addCell(new Number(37,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plata,formatoDatos)) //AG
                sheet1.addCell(new Number(38,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(39,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(40,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(41,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(42,fila, it.recepcionDeComplejo.alicuota.zinc,formatoDatos)) //ZN
                sheet1.addCell(new Number(43,fila, it.recepcionDeComplejo.alicuota.plomo,formatoDatos)) //PB
                sheet1.addCell(new Number(44,fila, it.recepcionDeComplejo.alicuota.plata,formatoDatos)) //AG
                sheet1.addCell(new Number(45,fila, it.valorNetoMineral,formatoDatos))
                sheet1.addCell(new Number(46,fila, it.valorNetoMineralEnBolivianos,formatoDatos))
                sheet1.addCell(new Number(47,fila, it.bonoCalidad,formatoDatos))
                sheet1.addCell(new Number(48,fila, it.bonoIncentivo,formatoDatos))
                sheet1.addCell(new Number(49,fila, it.valorDeCompra,formatoDatos))
                sheet1.addCell(new Number(50,fila, it.regaliaMinera,formatoDatos))

                columna=51
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
                def retencionesOtrasLiquidacion = LiquidacionDePlomoPlataRetenciones.findAllByLiquidacionDePlomoPlataAndTipoDeRetencion(it,"OTRA")
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
                sheet1.addCell(new Number(columna+5,fila, it.recepcionDeComplejo.costoDeTransporte,formatoDatos))
                sheet1.addCell(new Number(columna+6,fila, it.totalCostoLaboratorio,formatoDatos))

                fila++
            }
            //hacer un espacio para detallar el siguiente elemento
            fila++
            //SUBTOTALES COMPLEJO
            totalLiquidacionesPlomoPlata = liquidacionesPlomoPlata.size()
            inicioCiclo=7+((liquidacionesEstano)?totalLiquidacionesEstano+2:0)+((liquidacionesPlata)?totalLiquidacionesPlata+2:0)+((liquidacionesWolfran)?totalLiquidacionesWolfran+2:0)+((liquidacionesCobrePlata)?totalLiquidacionesCobrePlata+2:0)
            finCiclo=inicioCiclo+totalLiquidacionesPlomoPlata
            filaTotal=finCiclo
            filasNot.add(filaTotal)

            sheet1.addCell(new Label(9,filaTotal, "SUBTOTALES",formatoEncabezado))
            for (int col=12;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =inicioCiclo;fil<finCiclo;fil++){
                    def contenido=(sheet1.getWritableCell(col,fil).contents.equals(""))?"0":sheet1.getWritableCell(col,fil).contents
                    def valor = Double.parseDouble(contenido)
                    tret+=(valor<0)?0:valor
                }
                if (!colsNot.contains(col))
                    sheet1.addCell(new Number(col,filaTotal, tret,formatoTotales))
            }

            //humedad
            sheet1.addCell(new Number(16,filaTotal, (totalKilosNetosSecosPlomoPlata/totalKilosNetosHumedosPlomoPlata*100-100)*-1,formatoTotales))
            //ley
            sheet1.addCell(new Number(21,filaTotal, totalKilosFinosZincPlomoPlata/totalKilosNetosSecosPlomoPlata*100,formatoTotales))
            sheet1.addCell(new Number(22,filaTotal, totalKilosFinosPlomoPlomoPlata/totalKilosNetosSecosPlomoPlata*100,formatoTotales))
            sheet1.addCell(new Number(23,filaTotal, 10000*totalKilosFinosPlataPlomoPlata/totalKilosNetosSecosPlomoPlata,formatoTotales))

            fila++
        }

        if(liquidacionesZincPlata){
            def totalKilosNetosHumedosZincPlata=0
            def totalKilosNetosSecosZincPlata=0
            def totalKilosFinosZincZincPlata=0
            def totalKilosFinosPlomoZincPlata=0
            def totalKilosFinosPlataZincPlata=0

            liquidacionesZincPlata.each {
                totalKilosNetosHumedosZincPlata+=it.kilosNetosHumedos
                totalKilosNetosSecosZincPlata+=it.kilosNetosSecos
                totalKilosFinosZincZincPlata+=it.kilosFinosZinc
                totalKilosFinosPlomoZincPlata+=0
                totalKilosFinosPlataZincPlata+=it.kilosFinosPlata

                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new Number(1,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(2,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(3,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(4,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.zinc,formatoDatos)) //ZN
                sheet1.addCell(new Number(5,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plomo,formatoDatos)) //PB
                sheet1.addCell(new Number(6,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plata,formatoDatos)) //AG
                sheet1.addCell(new DateTime(7,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(8,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(9,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(10,fila, "Zn Ag",formatoDatos))
                sheet1.addCell(new Label(11,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(12,fila, Float.parseFloat(it.cantidadDeSacos),formatoDatos))
                sheet1.addCell(new Number(13,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(14,fila, it.merma,formatoDatos))
                sheet1.addCell(new Number(15,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(16,fila, it.humedad,formatoDatos))
                sheet1.addCell(new Number(17,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(18,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(19,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(20,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(21,fila, it.porcentajeZinc,formatoDatos)) //ZN
                sheet1.addCell(new Number(22,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(23,fila, it.porcentajePlata,formatoDatos)) //AG
                sheet1.addCell(new Number(24,fila, 0,formatoDatos)) //AS
                sheet1.addCell(new Number(25,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(26,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(27,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(28,fila, it.kilosFinosZinc,formatoDatos)) //ZN
                sheet1.addCell(new Number(29,fila, 0,formatoDatos)) //PB
                sheet1.addCell(new Number(30,fila, it.kilosFinosPlata,formatoDatos)) //AG
                sheet1.addCell(new Number(31,fila, 0,formatoDatos)) //AS
                sheet1.addCell(new Number(32,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(33,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(34,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(35,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.zinc,formatoDatos)) //ZN
                sheet1.addCell(new Number(36,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plomo,formatoDatos)) //PB
                sheet1.addCell(new Number(37,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plata,formatoDatos)) //AG
                sheet1.addCell(new Number(38,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(39,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(40,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(41,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(42,fila, it.recepcionDeComplejo.alicuota.zinc,formatoDatos)) //ZN
                sheet1.addCell(new Number(43,fila, it.recepcionDeComplejo.alicuota.plomo,formatoDatos)) //PB
                sheet1.addCell(new Number(44,fila, it.recepcionDeComplejo.alicuota.plata,formatoDatos)) //AG
                sheet1.addCell(new Number(45,fila, it.valorNetoMineral,formatoDatos))
                sheet1.addCell(new Number(46,fila, it.valorNetoMineralEnBolivianos,formatoDatos))
                sheet1.addCell(new Number(47,fila, it.bonoCalidad,formatoDatos))
                sheet1.addCell(new Number(48,fila, it.bonoIncentivo,formatoDatos))
                sheet1.addCell(new Number(49,fila, it.valorDeCompra,formatoDatos))
                sheet1.addCell(new Number(50,fila, it.regaliaMinera,formatoDatos))

                columna=51
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
                def retencionesOtrasLiquidacion = LiquidacionDeZincPlataRetenciones.findAllByLiquidacionDeZincPlataAndTipoDeRetencion(it,"OTRA")
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
                sheet1.addCell(new Number(columna+5,fila, it.recepcionDeComplejo.costoDeTransporte,formatoDatos))
                sheet1.addCell(new Number(columna+6,fila, it.totalCostoLaboratorio,formatoDatos))

                fila++
            }
            //hacer un espacio para detallar el siguiente elemento
            fila++
            //SUBTOTALES COMPLEJO
            totalLiquidacionesZincPlata = liquidacionesZincPlata.size()
            inicioCiclo=7+((liquidacionesEstano)?totalLiquidacionesEstano+2:0)+((liquidacionesPlata)?totalLiquidacionesPlata+2:0)+((liquidacionesWolfran)?totalLiquidacionesWolfran+2:0)+((liquidacionesCobrePlata)?totalLiquidacionesCobrePlata+2:0)+((liquidacionesPlomoPlata)?totalLiquidacionesPlomoPlata+2:0)
            finCiclo=inicioCiclo+totalLiquidacionesZincPlata
            filaTotal=finCiclo
            filasNot.add(filaTotal)

            sheet1.addCell(new Label(9,filaTotal, "SUBTOTALES",formatoEncabezado))
            for (int col=12;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =inicioCiclo;fil<finCiclo;fil++){
                    def contenido=(sheet1.getWritableCell(col,fil).contents.equals(""))?"0":sheet1.getWritableCell(col,fil).contents
                    def valor = Double.parseDouble(contenido)
                    tret+=(valor<0)?0:valor
                }
                if (!colsNot.contains(col))
                    sheet1.addCell(new Number(col,filaTotal, tret,formatoTotales))
            }

            //humedad
            sheet1.addCell(new Number(16,filaTotal, (totalKilosNetosSecosZincPlata/totalKilosNetosHumedosZincPlata*100-100)*-1,formatoTotales))
            //ley
            sheet1.addCell(new Number(21,filaTotal, totalKilosFinosZincZincPlata/totalKilosNetosSecosZincPlata*100,formatoTotales))
            sheet1.addCell(new Number(22,filaTotal, totalKilosFinosPlomoZincPlata/totalKilosNetosSecosZincPlata*100,formatoTotales))
            sheet1.addCell(new Number(23,filaTotal, 10000*totalKilosFinosPlataZincPlata/totalKilosNetosSecosZincPlata,formatoTotales))

            fila++
        }

        if(liquidacionesComplejo){
            def totalKilosNetosHumedosComplejo=0
            def totalKilosNetosSecosComplejo=0
            def totalKilosFinosZincComplejo=0
            def totalKilosFinosPlomoComplejo=0
            def totalKilosFinosPlataComplejo=0

            liquidacionesComplejo.each {
                totalKilosNetosHumedosComplejo+=it.kilosNetosHumedos
                totalKilosNetosSecosComplejo+=it.kilosNetosSecos
                totalKilosFinosZincComplejo+=it.kilosFinosZinc
                totalKilosFinosPlomoComplejo+=it.kilosFinosPlomo
                totalKilosFinosPlataComplejo+=it.kilosFinosPlata

                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new Number(1,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(2,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(3,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(4,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.zinc,formatoDatos)) //ZN
                sheet1.addCell(new Number(5,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plomo,formatoDatos)) //PB
                sheet1.addCell(new Number(6,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plata,formatoDatos)) //AG
                sheet1.addCell(new DateTime(7,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(8,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(9,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(10,fila, "Zn Pb Ag",formatoDatos))
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
                sheet1.addCell(new Number(21,fila, it.porcentajeZincFinal,formatoDatos)) //ZN
                sheet1.addCell(new Number(22,fila, it.porcentajePlomoFinal,formatoDatos)) //PB
                sheet1.addCell(new Number(23,fila, it.porcentajePlataFinal,formatoDatos)) //AG
                sheet1.addCell(new Number(24,fila, 0,formatoDatos)) //AS
                sheet1.addCell(new Number(25,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(26,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(27,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(28,fila, it.kilosFinosZinc,formatoDatos)) //ZN
                sheet1.addCell(new Number(29,fila, it.kilosFinosPlomo,formatoDatos)) //PB
                sheet1.addCell(new Number(30,fila, it.kilosFinosPlata,formatoDatos)) //AG
                sheet1.addCell(new Number(31,fila, 0,formatoDatos)) //AS
                sheet1.addCell(new Number(32,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(33,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(34,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(35,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.zinc,formatoDatos)) //ZN
                sheet1.addCell(new Number(36,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plomo,formatoDatos)) //PB
                sheet1.addCell(new Number(37,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plata,formatoDatos)) //AG
                sheet1.addCell(new Number(38,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(39,fila, 0,formatoDatos)) //SN
                sheet1.addCell(new Number(40,fila, 0,formatoDatos)) //SB
                sheet1.addCell(new Number(41,fila, 0,formatoDatos)) //WO3
                sheet1.addCell(new Number(42,fila, it.recepcionDeComplejo.alicuota.zinc,formatoDatos)) //ZN
                sheet1.addCell(new Number(43,fila, it.recepcionDeComplejo.alicuota.plomo,formatoDatos)) //PB
                sheet1.addCell(new Number(44,fila, it.recepcionDeComplejo.alicuota.plata,formatoDatos)) //AG
                sheet1.addCell(new Number(45,fila, it.valorNetoMineral,formatoDatos))
                sheet1.addCell(new Number(46,fila, it.valorNetoMineralEnBolivianos,formatoDatos))
                sheet1.addCell(new Number(47,fila, it.bonoCalidad,formatoDatos))
                sheet1.addCell(new Number(48,fila, it.bonoIncentivo,formatoDatos))
                sheet1.addCell(new Number(49,fila, it.valorDeCompra,formatoDatos))
                sheet1.addCell(new Number(50,fila, it.regaliaMinera,formatoDatos))

                columna=51
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
                def retencionesOtrasLiquidacion = LiquidacionDeComplejoRetenciones.findAllByLiquidacionDeComplejoAndTipoDeRetencion(it,"OTRA")
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
                sheet1.addCell(new Number(columna+5,fila, it.recepcionDeComplejo.costoDeTransporte,formatoDatos))
                sheet1.addCell(new Number(columna+6,fila, it.totalCostoLaboratorio,formatoDatos))

                fila++
            }
            //hacer un espacio para detallar el siguiente elemento
            fila++
            //SUBTOTALES COMPLEJO
            totalLiquidacionesComplejo = liquidacionesComplejo.size()
            inicioCiclo=7+((liquidacionesEstano)?totalLiquidacionesEstano+2:0)+((liquidacionesPlata)?totalLiquidacionesPlata+2:0)+((liquidacionesWolfran)?totalLiquidacionesWolfran+2:0)+((liquidacionesCobrePlata)?totalLiquidacionesCobrePlata+2:0)+((liquidacionesPlomoPlata)?totalLiquidacionesPlomoPlata+2:0)+((liquidacionesZincPlata)?totalLiquidacionesZincPlata+2:0)
            finCiclo=inicioCiclo+totalLiquidacionesComplejo
            filaTotal=finCiclo
            filasNot.add(filaTotal)

            sheet1.addCell(new Label(9,filaTotal, "TOTALES",formatoEncabezado))
            for (int col=12;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =inicioCiclo;fil<finCiclo;fil++){
                    def contenido=(sheet1.getWritableCell(col,fil).contents.equals(""))?"0":sheet1.getWritableCell(col,fil).contents
                    def valor = Double.parseDouble(contenido)
                    tret+=(valor<0)?0:valor
                }
                if (!colsNot.contains(col))
                    sheet1.addCell(new Number(col,filaTotal, tret,formatoTotales))
            }

            //humedad
            sheet1.addCell(new Number(16,filaTotal, (totalKilosNetosSecosComplejo/totalKilosNetosHumedosComplejo*100-100)*-1,formatoTotales))
            //ley
            sheet1.addCell(new Number(21,filaTotal, totalKilosFinosZincComplejo/totalKilosNetosSecosComplejo*100,formatoTotales))
            sheet1.addCell(new Number(22,filaTotal, totalKilosFinosPlomoComplejo/totalKilosNetosSecosComplejo*100,formatoTotales))
            sheet1.addCell(new Number(23,filaTotal, 10000*totalKilosFinosPlataComplejo/totalKilosNetosSecosComplejo,formatoTotales))

            fila++
        }
        //def colsNot=[16,18,19,20,21,22,23,30,31,32,33,34,35,37,38,39,40,41,42]
        columnaFinalRetenciones = 60+retencionesDeLey.size()+retencionesOtras.size()
        def totalLiquidaciones = fila+1
//        sheet1.addCell(new Label(9,totalLiquidaciones-1, "TOTALES",formatoEncabezado))
//        for (int col=12;col<columnaFinalRetenciones;col++){
//            def tret=0
//            for (int fil =7;fil<totalLiquidaciones+7;fil++){
//                def contenido=(sheet1.getWritableCell(col,fil).contents.equals(""))?"0":sheet1.getWritableCell(col,fil).contents
//                def valor = Double.parseDouble(contenido)
//                if (!filasNot.contains(fil))
//                    tret+=(valor<0)?0:valor
//            }
//            if (!colsNot.contains(col))
//                sheet1.addCell(new Number(col,totalLiquidaciones-1, tret,formatoTotales))
//        }

        sheet1.removeColumn(columna+6)

        def columnasOcultas = [39,39,39,32,32,32,31,25,25,25,24,18,18,18,1,1,1]
        columnasOcultas.each {
            sheet1.removeColumn(it)
        }

        workbook.write();
        workbook.close();
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
