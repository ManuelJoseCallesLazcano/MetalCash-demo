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

@Transactional
class ReporteReliquidacionesController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reporteReliquidacionesInstanceList: ReporteReliquidaciones.list(params), reporteReliquidacionesInstanceTotal: ReporteReliquidaciones.count()]
    }

    def create() {
        [reporteReliquidacionesInstance: new ReporteReliquidaciones(params)]
    }

    def save() {
        def reporteReliquidacionesInstance = new ReporteReliquidaciones(params)
        if (!reporteReliquidacionesInstance.save(flush: true)) {
            render(view: "create", model: [reporteReliquidacionesInstance: reporteReliquidacionesInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reporteReliquidaciones.label', default: 'ReporteReliquidaciones'), reporteReliquidacionesInstance.id])
        redirect(action: "show", id: reporteReliquidacionesInstance.id)
    }

    def show(Long id) {
        def reporteReliquidacionesInstance = ReporteReliquidaciones.get(id)
        if (!reporteReliquidacionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteReliquidaciones.label', default: 'ReporteReliquidaciones'), id])
            redirect(action: "list")
            return
        }

        [reporteReliquidacionesInstance: reporteReliquidacionesInstance]
    }

    def edit(Long id) {
        def reporteReliquidacionesInstance = ReporteReliquidaciones.get(id)
        if (!reporteReliquidacionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteReliquidaciones.label', default: 'ReporteReliquidaciones'), id])
            redirect(action: "list")
            return
        }

        [reporteReliquidacionesInstance: reporteReliquidacionesInstance]
    }

    def update(Long id, Long version) {
        def reporteReliquidacionesInstance = ReporteReliquidaciones.get(id)
        if (!reporteReliquidacionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteReliquidaciones.label', default: 'ReporteReliquidaciones'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reporteReliquidacionesInstance.version > version) {
                reporteReliquidacionesInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reporteReliquidaciones.label', default: 'ReporteReliquidaciones')] as Object[],
                        "Another user has updated this ReporteReliquidaciones while you were editing")
                render(view: "edit", model: [reporteReliquidacionesInstance: reporteReliquidacionesInstance])
                return
            }
        }

        reporteReliquidacionesInstance.properties = params

        if (!reporteReliquidacionesInstance.save(flush: true)) {
            render(view: "edit", model: [reporteReliquidacionesInstance: reporteReliquidacionesInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reporteReliquidaciones.label', default: 'ReporteReliquidaciones'), reporteReliquidacionesInstance.id])
        redirect(action: "show", id: reporteReliquidacionesInstance.id)
    }

    def delete(Long id) {
        def reporteReliquidacionesInstance = ReporteReliquidaciones.get(id)
        if (!reporteReliquidacionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteReliquidaciones.label', default: 'ReporteReliquidaciones'), id])
            redirect(action: "list")
            return
        }

        try {
            reporteReliquidacionesInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reporteReliquidaciones.label', default: 'ReporteReliquidaciones'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reporteReliquidaciones.label', default: 'ReporteReliquidaciones'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporteEstano = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Reliquidaciones de Estaño", 0)
        sheet1.setColumnView(0,20)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(1,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(2,20)
        sheet1.setColumnView(3,70)

        SheetSettings settings = sheet1.getSettings()
        settings.setScaleFactor(70)
        settings.setPaperSize(PaperSize.LETTER)
        settings.setOrientation(PageOrientation.PORTRAIT)
        settings.setTopMargin(0.2)
        settings.setBottomMargin(0.4)
        settings.setLeftMargin(0.6)
        settings.setRightMargin(0.4)
        settings.setHeaderMargin(0)
        settings.setFooterMargin(0)

        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
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

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="reliquidaciones_estano.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(0,0, "RELIQUIDACIONES DE ESTAÑO",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def tipoReporte = ""+params.tipoReporte
        def empresa=null
        def fechaInicial=null
        def fechaFinal=null
        def loteInicial=""
        def loteFinal=""

        def reliquidacionesEstano = null

        if (tipoReporte.equals("fechas")){
            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(0,3, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            reliquidacionesEstano=Reimpresion.findAllByFechaBetweenAndNombreReporteAndMotivoNotEqual(fechaInicial,fechaFinal,"RELIQUIDACION DE ESTANO","-")
        }
        if (tipoReporte.equals("fechasEmpresa")){
            empresa = Empresa.get(Integer.parseInt(""+params.empresa.id))

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)

            sheet1.addCell(new Label(0,4, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(1,4, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(0,3, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            reliquidacionesEstano=new ArrayList<Reimpresion>()
            def reliquidacionesEstanoAux=Reimpresion.findAllByFechaBetweenAndNombreReporteAndMotivoNotEqual(fechaInicial,fechaFinal,"RELIQUIDACION DE ESTANO","-")
            def liquidacionAux=null
            reliquidacionesEstanoAux.each {
                liquidacionAux=LiquidacionDeEstano.findByLote(it.lote)
                if (liquidacionAux.recepcionDeEstano.empresa==empresa){
                    reliquidacionesEstano.add(it)
                }
            }
        }
        if (tipoReporte.equals("lotes")){
            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            sheet1.addCell(new Label(0,3, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))

            reliquidacionesEstano=new ArrayList<Reimpresion>()
            def reliquidacionesEstanoAux=Reimpresion.findAllByNombreReporteAndMotivoNotEqual("RELIQUIDACION DE ESTANO","-")
            def liquidacionAux=null
            def loteAux=0
            reliquidacionesEstanoAux.each {
                liquidacionAux=LiquidacionDeEstano.findByLote(it.lote)
                loteAux=Integer.parseInt(liquidacionAux.lote)
                //if (liquidacionAux.recepcionDeEstano.empresa==empresa){
                if (loteAux>=loteInicial&&loteAux<=loteFinal){
                    reliquidacionesEstano.add(it)
                }
            }
        }
        if (tipoReporte.equals("lotesEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            sheet1.addCell(new Label(0,4, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(1,4, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(0,3, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))

            reliquidacionesEstano=new ArrayList<Reimpresion>()
            def reliquidacionesEstanoAux=Reimpresion.findAllByNombreReporteAndMotivoNotEqual("RELIQUIDACION DE ESTANO","-")
            def liquidacionAux=null
            def loteAux=0
            reliquidacionesEstanoAux.each {
                liquidacionAux=LiquidacionDeEstano.findByLote(it.lote)
                loteAux=Integer.parseInt(liquidacionAux.lote)
                //if (liquidacionAux.recepcionDeEstano.empresa==empresa){
                if (loteAux>=loteInicial&&loteAux<=loteFinal&&liquidacionAux.recepcionDeEstano.empresa==empresa){
                    reliquidacionesEstano.add(it)
                }
            }
        }
        //reliquidacionesEstano = LiquidacionDeEstano.list()
        sheet1.addCell(new Label(0,6, "FECHA",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "No. LIQUIDACION",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "MOTIVO",formatoEncabezado))
        /*AGREGAR ESTE CONTROL PARA TODOS LOS ELEMENTOS, ES PARA CUANDO NO SE GENEREN RESULTADOS, AL PARECER CUANDO EL list
        * NO ENCUENTRA RESULTADOS DEVUELVE UN LIST null. ADICIONAR EL CODIGO EL EL GSP PARA QUE APAREZCA LA NOTIFICACION.*/
        if (!reliquidacionesEstano) {
            flash.error = "NO SE PUDO OBTENER RESULTADOS!"
            System.out.println("*** SE ESTA PRODUCIENDO RESULTADOS NULL!!!")
            redirect(action: "create")
            return
        }

        if (reliquidacionesEstano.size()==0){
            if (reliquidacionesEstano.size()==0)
                sheet1.addCell(new Label(0,7, "SIN RESULTADOS",formatoInfoReporte))
        }else{
            def fila = 7
            reliquidacionesEstano.each {
                sheet1.addCell(new Label(0,fila, new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(it.fecha),formatoDatos))
                sheet1.addCell(new Label(1,fila, it.lote,formatoDatos))
                sheet1.addCell(new Label(2,fila, it.identificadorDocumento,formatoFecha))
                sheet1.addCell(new Label(3,fila, it.motivo,formatoDatos))

                fila++
            }

        }
        workbook.write();
        workbook.close();
    }

    def crearReportePlata = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Reliquidaciones de Plata", 0)
        sheet1.setColumnView(0,20)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(1,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(2,20)
        sheet1.setColumnView(3,70)

        SheetSettings settings = sheet1.getSettings()
        settings.setScaleFactor(70)
        settings.setPaperSize(PaperSize.LETTER)
        settings.setOrientation(PageOrientation.PORTRAIT)
        settings.setTopMargin(0.2)
        settings.setBottomMargin(0.4)
        settings.setLeftMargin(0.6)
        settings.setRightMargin(0.4)
        settings.setHeaderMargin(0)
        settings.setFooterMargin(0)

        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
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

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="reliquidaciones_plata.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(0,0, "RELIQUIDACIONES DE ESTAÑO",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def tipoReporte = ""+params.tipoReporte
        def empresa=null
        def fechaInicial=null
        def fechaFinal=null
        def loteInicial=""
        def loteFinal=""

        def reliquidacionesPlata = null

        if (tipoReporte.equals("fechas")){
            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(0,3, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            reliquidacionesPlata=Reimpresion.findAllByFechaBetweenAndNombreReporteAndMotivoNotEqual(fechaInicial,fechaFinal,"RELIQUIDACION DE PLATA","-")
        }
        if (tipoReporte.equals("fechasEmpresa")){
            empresa = Empresa.get(Integer.parseInt(""+params.empresa.id))

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)

            sheet1.addCell(new Label(0,4, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(1,4, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(0,3, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            reliquidacionesPlata=new ArrayList<Reimpresion>()
            def reliquidacionesPlataAux=Reimpresion.findAllByFechaBetweenAndNombreReporteAndMotivoNotEqual(fechaInicial,fechaFinal,"RELIQUIDACION DE PLATA","-")
            def liquidacionAux=null
            reliquidacionesPlataAux.each {
                liquidacionAux=LiquidacionDePlata.findByLote(it.lote)
                if (liquidacionAux.recepcionDePlata.empresa==empresa){
                    reliquidacionesPlata.add(it)
                }
            }
        }
        if (tipoReporte.equals("lotes")){
            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            sheet1.addCell(new Label(0,3, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))

            reliquidacionesPlata=new ArrayList<Reimpresion>()
            def reliquidacionesPlataAux=Reimpresion.findAllByNombreReporteAndMotivoNotEqual("RELIQUIDACION DE PLATA","-")
            def liquidacionAux=null
            def loteAux=0
            reliquidacionesPlataAux.each {
                liquidacionAux=LiquidacionDePlata.findByLote(it.lote)
                loteAux=Integer.parseInt(liquidacionAux.lote)
                //if (liquidacionAux.recepcionDePlata.empresa==empresa){
                if (loteAux>=loteInicial&&loteAux<=loteFinal){
                    reliquidacionesPlata.add(it)
                }
            }
        }
        if (tipoReporte.equals("lotesEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            sheet1.addCell(new Label(0,4, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(1,4, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(0,3, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))

            reliquidacionesPlata=new ArrayList<Reimpresion>()
            def reliquidacionesPlataAux=Reimpresion.findAllByNombreReporteAndMotivoNotEqual("RELIQUIDACION DE PLATA","-")
            def liquidacionAux=null
            def loteAux=0
            reliquidacionesPlataAux.each {
                liquidacionAux=LiquidacionDePlata.findByLote(it.lote)
                loteAux=Integer.parseInt(liquidacionAux.lote)
                //if (liquidacionAux.recepcionDePlata.empresa==empresa){
                if (loteAux>=loteInicial&&loteAux<=loteFinal&&liquidacionAux.recepcionDePlata.empresa==empresa){
                    reliquidacionesPlata.add(it)
                }
            }
        }
        //reliquidacionesPlata = LiquidacionDePlata.list()
        sheet1.addCell(new Label(0,6, "FECHA",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "No. LIQUIDACION",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "MOTIVO",formatoEncabezado))
        /*AGREGAR ESTE CONTROL PARA TODOS LOS ELEMENTOS, ES PARA CUANDO NO SE GENEREN RESULTADOS, AL PARECER CUANDO EL list
        * NO ENCUENTRA RESULTADOS DEVUELVE UN LIST null. ADICIONAR EL CODIGO EL EL GSP PARA QUE APAREZCA LA NOTIFICACION.*/
        if (!reliquidacionesPlata) {
            flash.error = "NO SE PUDO OBTENER RESULTADOS!"
            System.out.println("*** SE ESTA PRODUCIENDO RESULTADOS NULL!!!")
            redirect(action: "create")
            return
        }

        if (reliquidacionesPlata.size()==0){
            if (reliquidacionesPlata.size()==0)
                sheet1.addCell(new Label(0,7, "SIN RESULTADOS",formatoInfoReporte))
        }else{
            def fila = 7
            reliquidacionesPlata.each {
                sheet1.addCell(new Label(0,fila, new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(it.fecha),formatoDatos))
                sheet1.addCell(new Label(1,fila, it.lote,formatoDatos))
                sheet1.addCell(new Label(2,fila, it.identificadorDocumento,formatoFecha))
                sheet1.addCell(new Label(3,fila, it.motivo,formatoDatos))

                fila++
            }

        }
        workbook.write();
        workbook.close();
    }

    def crearReporteWolfran = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Reliquidaciones de Wolfran", 0)
        sheet1.setColumnView(0,20)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(1,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(2,20)
        sheet1.setColumnView(3,70)

        SheetSettings settings = sheet1.getSettings()
        settings.setScaleFactor(70)
        settings.setPaperSize(PaperSize.LETTER)
        settings.setOrientation(PageOrientation.PORTRAIT)
        settings.setTopMargin(0.2)
        settings.setBottomMargin(0.4)
        settings.setLeftMargin(0.6)
        settings.setRightMargin(0.4)
        settings.setHeaderMargin(0)
        settings.setFooterMargin(0)

        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
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

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="reliquidaciones_wolfran.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(0,0, "RELIQUIDACIONES DE ESTAÑO",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def tipoReporte = ""+params.tipoReporte
        def empresa=null
        def fechaInicial=null
        def fechaFinal=null
        def loteInicial=""
        def loteFinal=""

        def reliquidacionesWolfran = null

        if (tipoReporte.equals("fechas")){
            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(0,3, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            reliquidacionesWolfran=Reimpresion.findAllByFechaBetweenAndNombreReporteAndMotivoNotEqual(fechaInicial,fechaFinal,"RELIQUIDACION DE WOLFRAN","-")
        }
        if (tipoReporte.equals("fechasEmpresa")){
            empresa = Empresa.get(Integer.parseInt(""+params.empresa.id))

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)

            sheet1.addCell(new Label(0,4, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(1,4, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(0,3, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            reliquidacionesWolfran=new ArrayList<Reimpresion>()
            def reliquidacionesWolfranAux=Reimpresion.findAllByFechaBetweenAndNombreReporteAndMotivoNotEqual(fechaInicial,fechaFinal,"RELIQUIDACION DE WOLFRAN","-")
            def liquidacionAux=null
            reliquidacionesWolfranAux.each {
                liquidacionAux=LiquidacionDeWolfran.findByLote(it.lote)
                if (liquidacionAux.recepcionDeWolfran.empresa==empresa){
                    reliquidacionesWolfran.add(it)
                }
            }
        }
        if (tipoReporte.equals("lotes")){
            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            sheet1.addCell(new Label(0,3, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))

            reliquidacionesWolfran=new ArrayList<Reimpresion>()
            def reliquidacionesWolfranAux=Reimpresion.findAllByNombreReporteAndMotivoNotEqual("RELIQUIDACION DE WOLFRAN","-")
            def liquidacionAux=null
            def loteAux=0
            reliquidacionesWolfranAux.each {
                liquidacionAux=LiquidacionDeWolfran.findByLote(it.lote)
                loteAux=Integer.parseInt(liquidacionAux.lote)
                //if (liquidacionAux.recepcionDeWolfran.empresa==empresa){
                if (loteAux>=loteInicial&&loteAux<=loteFinal){
                    reliquidacionesWolfran.add(it)
                }
            }
        }
        if (tipoReporte.equals("lotesEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            sheet1.addCell(new Label(0,4, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(1,4, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(0,3, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))

            reliquidacionesWolfran=new ArrayList<Reimpresion>()
            def reliquidacionesWolfranAux=Reimpresion.findAllByNombreReporteAndMotivoNotEqual("RELIQUIDACION DE WOLFRAN","-")
            def liquidacionAux=null
            def loteAux=0
            reliquidacionesWolfranAux.each {
                liquidacionAux=LiquidacionDeWolfran.findByLote(it.lote)
                loteAux=Integer.parseInt(liquidacionAux.lote)
                //if (liquidacionAux.recepcionDeWolfran.empresa==empresa){
                if (loteAux>=loteInicial&&loteAux<=loteFinal&&liquidacionAux.recepcionDeWolfran.empresa==empresa){
                    reliquidacionesWolfran.add(it)
                }
            }
        }
        //reliquidacionesWolfran = LiquidacionDeWolfran.list()
        sheet1.addCell(new Label(0,6, "FECHA",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "No. LIQUIDACION",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "MOTIVO",formatoEncabezado))
        /*AGREGAR ESTE CONTROL PARA TODOS LOS ELEMENTOS, ES PARA CUANDO NO SE GENEREN RESULTADOS, AL PARECER CUANDO EL list
        * NO ENCUENTRA RESULTADOS DEVUELVE UN LIST null. ADICIONAR EL CODIGO EL EL GSP PARA QUE APAREZCA LA NOTIFICACION.*/
        if (!reliquidacionesWolfran) {
            flash.error = "NO SE PUDO OBTENER RESULTADOS!"
            System.out.println("*** SE ESTA PRODUCIENDO RESULTADOS NULL!!!")
            redirect(action: "create")
            return
        }

        if (reliquidacionesWolfran.size()==0){
            if (reliquidacionesWolfran.size()==0)
                sheet1.addCell(new Label(0,7, "SIN RESULTADOS",formatoInfoReporte))
        }else{
            def fila = 7
            reliquidacionesWolfran.each {
                sheet1.addCell(new Label(0,fila, new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(it.fecha),formatoDatos))
                sheet1.addCell(new Label(1,fila, it.lote,formatoDatos))
                sheet1.addCell(new Label(2,fila, it.identificadorDocumento,formatoFecha))
                sheet1.addCell(new Label(3,fila, it.motivo,formatoDatos))

                fila++
            }

        }
        workbook.write();
        workbook.close();
    }

    def crearReporteAntimonio = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Reliquidaciones de Antimonio", 0)
        sheet1.setColumnView(0,20)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(1,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(2,20)
        sheet1.setColumnView(3,70)

        SheetSettings settings = sheet1.getSettings()
        settings.setScaleFactor(70)
        settings.setPaperSize(PaperSize.LETTER)
        settings.setOrientation(PageOrientation.PORTRAIT)
        settings.setTopMargin(0.2)
        settings.setBottomMargin(0.4)
        settings.setLeftMargin(0.6)
        settings.setRightMargin(0.4)
        settings.setHeaderMargin(0)
        settings.setFooterMargin(0)

        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
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

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="reliquidaciones_antimonio.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(0,0, "RELIQUIDACIONES DE ESTAÑO",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def tipoReporte = ""+params.tipoReporte
        def empresa=null
        def fechaInicial=null
        def fechaFinal=null
        def loteInicial=""
        def loteFinal=""

        def reliquidacionesAntimonio = null

        if (tipoReporte.equals("fechas")){
            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(0,3, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            reliquidacionesAntimonio=Reimpresion.findAllByFechaBetweenAndNombreReporteAndMotivoNotEqual(fechaInicial,fechaFinal,"RELIQUIDACION DE ANTIMONIO","-")
        }
        if (tipoReporte.equals("fechasEmpresa")){
            empresa = Empresa.get(Integer.parseInt(""+params.empresa.id))

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)

            sheet1.addCell(new Label(0,4, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(1,4, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(0,3, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            reliquidacionesAntimonio=new ArrayList<Reimpresion>()
            def reliquidacionesAntimonioAux=Reimpresion.findAllByFechaBetweenAndNombreReporteAndMotivoNotEqual(fechaInicial,fechaFinal,"RELIQUIDACION DE ANTIMONIO","-")
            def liquidacionAux=null
            reliquidacionesAntimonioAux.each {
                liquidacionAux=LiquidacionDeAntimonio.findByLote(it.lote)
                if (liquidacionAux.recepcionDeAntimonio.empresa==empresa){
                    reliquidacionesAntimonio.add(it)
                }
            }
        }
        if (tipoReporte.equals("lotes")){
            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            sheet1.addCell(new Label(0,3, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))

            reliquidacionesAntimonio=new ArrayList<Reimpresion>()
            def reliquidacionesAntimonioAux=Reimpresion.findAllByNombreReporteAndMotivoNotEqual("RELIQUIDACION DE ANTIMONIO","-")
            def liquidacionAux=null
            def loteAux=0
            reliquidacionesAntimonioAux.each {
                liquidacionAux=LiquidacionDeAntimonio.findByLote(it.lote)
                loteAux=Integer.parseInt(liquidacionAux.lote)
                //if (liquidacionAux.recepcionDeAntimonio.empresa==empresa){
                if (loteAux>=loteInicial&&loteAux<=loteFinal){
                    reliquidacionesAntimonio.add(it)
                }
            }
        }
        if (tipoReporte.equals("lotesEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            sheet1.addCell(new Label(0,4, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(1,4, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(0,3, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))

            reliquidacionesAntimonio=new ArrayList<Reimpresion>()
            def reliquidacionesAntimonioAux=Reimpresion.findAllByNombreReporteAndMotivoNotEqual("RELIQUIDACION DE ANTIMONIO","-")
            def liquidacionAux=null
            def loteAux=0
            reliquidacionesAntimonioAux.each {
                liquidacionAux=LiquidacionDeAntimonio.findByLote(it.lote)
                loteAux=Integer.parseInt(liquidacionAux.lote)
                //if (liquidacionAux.recepcionDeAntimonio.empresa==empresa){
                if (loteAux>=loteInicial&&loteAux<=loteFinal&&liquidacionAux.recepcionDeAntimonio.empresa==empresa){
                    reliquidacionesAntimonio.add(it)
                }
            }
        }
        //reliquidacionesAntimonio = LiquidacionDeAntimonio.list()
        sheet1.addCell(new Label(0,6, "FECHA",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "No. LIQUIDACION",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "MOTIVO",formatoEncabezado))
        /*AGREGAR ESTE CONTROL PARA TODOS LOS ELEMENTOS, ES PARA CUANDO NO SE GENEREN RESULTADOS, AL PARECER CUANDO EL list
        * NO ENCUENTRA RESULTADOS DEVUELVE UN LIST null. ADICIONAR EL CODIGO EL EL GSP PARA QUE APAREZCA LA NOTIFICACION.*/
        if (!reliquidacionesAntimonio) {
            flash.error = "NO SE PUDO OBTENER RESULTADOS!"
            System.out.println("*** SE ESTA PRODUCIENDO RESULTADOS NULL!!!")
            redirect(action: "create")
            return
        }

        if (reliquidacionesAntimonio.size()==0){
            if (reliquidacionesAntimonio.size()==0)
                sheet1.addCell(new Label(0,7, "SIN RESULTADOS",formatoInfoReporte))
        }else{
            def fila = 7
            reliquidacionesAntimonio.each {
                sheet1.addCell(new Label(0,fila, new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(it.fecha),formatoDatos))
                sheet1.addCell(new Label(1,fila, it.lote,formatoDatos))
                sheet1.addCell(new Label(2,fila, it.identificadorDocumento,formatoFecha))
                sheet1.addCell(new Label(3,fila, it.motivo,formatoDatos))

                fila++
            }

        }
        workbook.write();
        workbook.close();
    }

    def crearReporteComplejo = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Reliquidaciones de Complejo", 0)
        sheet1.setColumnView(0,20)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(1,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(2,20)
        sheet1.setColumnView(3,70)

        SheetSettings settings = sheet1.getSettings()
        settings.setScaleFactor(70)
        settings.setPaperSize(PaperSize.LETTER)
        settings.setOrientation(PageOrientation.PORTRAIT)
        settings.setTopMargin(0.2)
        settings.setBottomMargin(0.4)
        settings.setLeftMargin(0.6)
        settings.setRightMargin(0.4)
        settings.setHeaderMargin(0)
        settings.setFooterMargin(0)

        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
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

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="reliquidaciones_complejo.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(0,0, "RELIQUIDACIONES DE ESTAÑO",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def tipoReporte = ""+params.tipoReporte
        def empresa=null
        def fechaInicial=null
        def fechaFinal=null
        def loteInicial=""
        def loteFinal=""

        def reliquidacionesComplejo = null

        if (tipoReporte.equals("fechas")){
            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(0,3, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            reliquidacionesComplejo=Reimpresion.findAllByFechaBetweenAndNombreReporteAndMotivoNotEqual(fechaInicial,fechaFinal,"RELIQUIDACION DE COMPLEJO","-")
        }
        if (tipoReporte.equals("fechasEmpresa")){
            empresa = Empresa.get(Integer.parseInt(""+params.empresa.id))

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)

            sheet1.addCell(new Label(0,4, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(1,4, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(0,3, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            reliquidacionesComplejo=new ArrayList<Reimpresion>()
            def reliquidacionesComplejoAux=Reimpresion.findAllByFechaBetweenAndNombreReporteAndMotivoNotEqual(fechaInicial,fechaFinal,"RELIQUIDACION DE COMPLEJO","-")
            def liquidacionAux=null
            reliquidacionesComplejoAux.each {
                liquidacionAux=LiquidacionDeComplejo.findByLote(it.lote)
                if (liquidacionAux.recepcionDeComplejo.empresa==empresa){
                    reliquidacionesComplejo.add(it)
                }
            }
        }
        if (tipoReporte.equals("lotes")){
            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            sheet1.addCell(new Label(0,3, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))

            reliquidacionesComplejo=new ArrayList<Reimpresion>()
            def reliquidacionesComplejoAux=Reimpresion.findAllByNombreReporteAndMotivoNotEqual("RELIQUIDACION DE COMPLEJO","-")
            def liquidacionAux=null
            def loteAux=0
            reliquidacionesComplejoAux.each {
                liquidacionAux=LiquidacionDeComplejo.findByLote(it.lote)
                loteAux=Integer.parseInt(liquidacionAux.lote)
                //if (liquidacionAux.recepcionDeComplejo.empresa==empresa){
                if (loteAux>=loteInicial&&loteAux<=loteFinal){
                    reliquidacionesComplejo.add(it)
                }
            }
        }
        if (tipoReporte.equals("lotesEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            sheet1.addCell(new Label(0,4, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(1,4, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(0,3, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))

            reliquidacionesComplejo=new ArrayList<Reimpresion>()
            def reliquidacionesComplejoAux=Reimpresion.findAllByNombreReporteAndMotivoNotEqual("RELIQUIDACION DE COMPLEJO","-")
            def liquidacionAux=null
            def loteAux=0
            reliquidacionesComplejoAux.each {
                liquidacionAux=LiquidacionDeComplejo.findByLote(it.lote)
                loteAux=Integer.parseInt(liquidacionAux.lote)
                //if (liquidacionAux.recepcionDeComplejo.empresa==empresa){
                if (loteAux>=loteInicial&&loteAux<=loteFinal&&liquidacionAux.recepcionDeComplejo.empresa==empresa){
                    reliquidacionesComplejo.add(it)
                }
            }
        }
        //reliquidacionesComplejo = LiquidacionDeComplejo.list()
        sheet1.addCell(new Label(0,6, "FECHA",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "No. LIQUIDACION",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "MOTIVO",formatoEncabezado))
        /*AGREGAR ESTE CONTROL PARA TODOS LOS ELEMENTOS, ES PARA CUANDO NO SE GENEREN RESULTADOS, AL PARECER CUANDO EL list
        * NO ENCUENTRA RESULTADOS DEVUELVE UN LIST null. ADICIONAR EL CODIGO EL EL GSP PARA QUE APAREZCA LA NOTIFICACION.*/
        if (!reliquidacionesComplejo) {
            flash.error = "NO SE PUDO OBTENER RESULTADOS!"
            System.out.println("*** SE ESTA PRODUCIENDO RESULTADOS NULL!!!")
            redirect(action: "create")
            return
        }

        if (reliquidacionesComplejo.size()==0){
            if (reliquidacionesComplejo.size()==0)
                sheet1.addCell(new Label(0,7, "SIN RESULTADOS",formatoInfoReporte))
        }else{
            def fila = 7
            reliquidacionesComplejo.each {
                sheet1.addCell(new Label(0,fila, new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(it.fecha),formatoDatos))
                sheet1.addCell(new Label(1,fila, it.lote,formatoDatos))
                sheet1.addCell(new Label(2,fila, it.identificadorDocumento,formatoFecha))
                sheet1.addCell(new Label(3,fila, it.motivo,formatoDatos))

                fila++
            }

        }
        workbook.write();
        workbook.close();
    }
}
