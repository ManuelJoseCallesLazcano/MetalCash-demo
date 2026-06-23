package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import jxl.SheetSettings
import jxl.Workbook
import jxl.format.Alignment
import jxl.format.PageOrientation
import jxl.format.PaperSize
import jxl.write.*
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.*
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class ReportePagoAnalisisController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reportePagoAnalisisInstanceList: ReportePagoAnalisis.list(params), reportePagoAnalisisInstanceTotal: ReportePagoAnalisis.count()]
    }

    def create() {
        [reportePagoAnalisisInstance: new ReportePagoAnalisis(params)]
    }

    def save() {
        def reportePagoAnalisisInstance = new ReportePagoAnalisis(params)
        if (!reportePagoAnalisisInstance.save(flush: true)) {
            render(view: "create", model: [reportePagoAnalisisInstance: reportePagoAnalisisInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reportePagoAnalisis.label', default: 'ReportePagoAnalisis'), reportePagoAnalisisInstance.id])
        redirect(action: "show", id: reportePagoAnalisisInstance.id)
    }

    def show(Long id) {
        def reportePagoAnalisisInstance = ReportePagoAnalisis.get(id)
        if (!reportePagoAnalisisInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reportePagoAnalisis.label', default: 'ReportePagoAnalisis'), id])
            redirect(action: "list")
            return
        }

        [reportePagoAnalisisInstance: reportePagoAnalisisInstance]
    }

    def edit(Long id) {
        def reportePagoAnalisisInstance = ReportePagoAnalisis.get(id)
        if (!reportePagoAnalisisInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reportePagoAnalisis.label', default: 'ReportePagoAnalisis'), id])
            redirect(action: "list")
            return
        }

        [reportePagoAnalisisInstance: reportePagoAnalisisInstance]
    }

    def update(Long id, Long version) {
        def reportePagoAnalisisInstance = ReportePagoAnalisis.get(id)
        if (!reportePagoAnalisisInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reportePagoAnalisis.label', default: 'ReportePagoAnalisis'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reportePagoAnalisisInstance.version > version) {
                reportePagoAnalisisInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reportePagoAnalisis.label', default: 'ReportePagoAnalisis')] as Object[],
                        "Another user has updated this ReportePagoAnalisis while you were editing")
                render(view: "edit", model: [reportePagoAnalisisInstance: reportePagoAnalisisInstance])
                return
            }
        }

        reportePagoAnalisisInstance.properties = params

        if (!reportePagoAnalisisInstance.save(flush: true)) {
            render(view: "edit", model: [reportePagoAnalisisInstance: reportePagoAnalisisInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reportePagoAnalisis.label', default: 'ReportePagoAnalisis'), reportePagoAnalisisInstance.id])
        redirect(action: "show", id: reportePagoAnalisisInstance.id)
    }

    def delete(Long id) {
        def reportePagoAnalisisInstance = ReportePagoAnalisis.get(id)
        if (!reportePagoAnalisisInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reportePagoAnalisis.label', default: 'ReportePagoAnalisis'), id])
            redirect(action: "list")
            return
        }

        try {
            reportePagoAnalisisInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reportePagoAnalisis.label', default: 'ReportePagoAnalisis'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reportePagoAnalisis.label', default: 'ReportePagoAnalisis'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporte = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 10, WritableFont.NO_BOLD);
        WritableFont courier6PlainFont = new WritableFont(WritableFont.COURIER, 10, WritableFont.NO_BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 10, WritableFont.NO_BOLD);
        WritableFont courier8BoldFont = new WritableFont(WritableFont.COURIER, 10, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.NO_BOLD);

        WritableCellFormat formatoEncabezado = new WritableCellFormat (arial10BoldFont);
        formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)
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
        //sheet1.setRowView(6,500)
        for(i in 0..100)
            sheet1.setColumnView(i,14)
        sheet1.setColumnView(2,6)

        SheetSettings settings = sheet1.getSettings()
        settings.setScaleFactor(70)
        settings.setPaperSize(PaperSize.LETTER)
        settings.setOrientation(PageOrientation.PORTRAIT)
        settings.setTopMargin(0.6)
        settings.setBottomMargin(0.6)
        settings.setLeftMargin(0.6)
        settings.setRightMargin(0.6)
        settings.setHeaderMargin(0)
        settings.setFooterMargin(0)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="reporte_pago_analisis_laboratorio.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(2,0, "REPORTE DE PAGO DE ANALISIS DE LABORATORIO",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def tipoReporte = ""+params.tipoReporte
        def nombreDeLaboratorio = ""+params.nombreDeLaboratorio
        def empresa=null
        def fechaInicial=null
        def fechaFinal=null

        def recepcionesEstano=null
        def recepcionesPlata=null
        def recepcionesAntimonio=null
        def recepcionesWolfran=null
        def recepcionesComplejo=null

        if (tipoReporte.equals("fechas")){
            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(3,2, "LABORATORIO: ${nombreDeLaboratorio}",formatoInfoReporte))
            sheet1.addCell(new Label(3,3, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            recepcionesEstano = RecepcionDeEstano.findAllByFechaDeRecepcionBetween(fechaInicial,fechaFinal)
            recepcionesPlata = RecepcionDePlata.findAllByFechaDeRecepcionBetween(fechaInicial,fechaFinal)
            recepcionesAntimonio = RecepcionDeAntimonio.findAllByFechaDeRecepcionBetween(fechaInicial,fechaFinal)
            recepcionesWolfran = RecepcionDeWolfran.findAllByFechaDeRecepcionBetween(fechaInicial,fechaFinal)
            recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetween(fechaInicial,fechaFinal)
            
            System.out.println("*** RESULTADOS DE ESTANO: ${recepcionesEstano.size()}")
            System.out.println("*** RESULTADOS DE PLATA: ${recepcionesPlata.size()}")
            System.out.println("*** RESULTADOS DE ANTIMONIO: ${recepcionesAntimonio.size()}")
            System.out.println("*** RESULTADOS DE WOLFRAN: ${recepcionesWolfran.size()}")
            System.out.println("*** RESULTADOS DE COMPLEJO: ${recepcionesComplejo.size()}")
        }
        if (tipoReporte.equals("fechasEmpresa")){
            empresa = Empresa.get(Integer.parseInt(""+params.empresa.id))

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)
            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(3,2, "LABORATORIO: ${nombreDeLaboratorio}",formatoInfoReporte))
            sheet1.addCell(new Label(3,3, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))
            sheet1.addCell(new Label(3,4, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(4,4, "${empresa.toString()}",formatoInfoReporte))

            def recepcionesSn = RecepcionDeEstano.findAllByFechaDeRecepcionBetween(fechaInicial,fechaFinal)
            recepcionesEstano=new ArrayList<RecepcionDeEstano>()
            recepcionesSn.each {
                if(it.empresa.id==empresa.id)
                    recepcionesEstano.add(it)
            }
            def recepcionesAg = RecepcionDePlata.findAllByFechaDeRecepcionBetween(fechaInicial,fechaFinal)
            recepcionesPlata=new ArrayList<RecepcionDePlata>()
            recepcionesAg.each {
                if(it.empresa.id==empresa.id)
                    recepcionesPlata.add(it)
            }
            def recepcionesSb = RecepcionDeAntimonio.findAllByFechaDeRecepcionBetween(fechaInicial,fechaFinal)
            recepcionesAntimonio=new ArrayList<RecepcionDeAntimonio>()
            recepcionesSb.each {
                if(it.empresa.id==empresa.id)
                    recepcionesAntimonio.add(it)
            }
            def recepcionesWO3 = RecepcionDeWolfran.findAllByFechaDeRecepcionBetween(fechaInicial,fechaFinal)
            recepcionesWolfran=new ArrayList<RecepcionDeWolfran>()
            recepcionesWO3.each {
                if(it.empresa.id==empresa.id)
                    recepcionesWolfran.add(it)
            }
            def recepcionesCm = RecepcionDeComplejo.findAllByFechaDeRecepcionBetween(fechaInicial,fechaFinal)
            recepcionesComplejo=new ArrayList<RecepcionDeComplejo>()
            recepcionesCm.each {
                if(it.empresa.id==empresa.id)
                    recepcionesComplejo.add(it)
            }
            System.out.println("*** RESULTADOS DE ESTANO: ${recepcionesEstano.size()}")
            System.out.println("*** RESULTADOS DE PLATA: ${recepcionesPlata.size()}")
            System.out.println("*** RESULTADOS DE ANTIMONIO: ${recepcionesAntimonio.size()}")
            System.out.println("*** RESULTADOS DE WOLFRAN: ${recepcionesWolfran.size()}")
            System.out.println("*** RESULTADOS DE COMPLEJO: ${recepcionesComplejo.size()}")
        }

        /*CONTROLAR SI SON null LAS LISTAS OBTENIDAS DE LIQUIDACIONES*/
        sheet1.addCell(new Label(2,6, "No.",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "FECHA RECEPCION",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "ELEMENTO",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "COSTO",formatoEncabezado))
        if (nombreDeLaboratorio.equals(""))
            sheet1.addCell(new Label(7,6, "LABORATORIO",formatoDatos))

        def contador=1
        def fila = 7
        def subtotalEstano=0
        def subtotalPlata=0
        def subtotalWolfran=0
        def subtotalAntimonio=0
        def subtotalComplejo=0

        if(recepcionesEstano){
            recepcionesEstano.each {
                if(it.costoLaboratorio1&&it.detalleLaboratorio1&&it.detalleLaboratorio1.contains(nombreDeLaboratorio)){
                    sheet1.addCell(new Label(2,fila,""+contador,formatoDatos)) //SB
                    sheet1.addCell(new DateTime(3,fila, it.fechaDeRecepcion,formatoFecha))
                    sheet1.addCell(new Label(4,fila, "Sn",formatoDatos))
                    sheet1.addCell(new Label(5,fila, it.toString(),formatoDatos))
                    sheet1.addCell(new Number(6,fila, it.costoLaboratorio1,formatoDatos))
                    if (nombreDeLaboratorio.equals(""))
                        sheet1.addCell(new Label(7,fila, it.detalleLaboratorio1,formatoDatos))
                    subtotalEstano+=it.costoLaboratorio1
                    fila++
                    contador++
                }
                if(it.costoLaboratorio2&&it.detalleLaboratorio2&&it.detalleLaboratorio2.contains(nombreDeLaboratorio)){
                    sheet1.addCell(new Label(2,fila,""+contador,formatoDatos)) //SB
                    sheet1.addCell(new DateTime(3,fila, it.fechaDeRecepcion,formatoFecha))
                    sheet1.addCell(new Label(4,fila, "Sn",formatoDatos))
                    sheet1.addCell(new Label(5,fila, it.toString(),formatoDatos))
                    sheet1.addCell(new Number(6,fila, it.costoLaboratorio2,formatoDatos))
                    if (nombreDeLaboratorio.equals(""))
                        sheet1.addCell(new Label(7,fila, it.detalleLaboratorio2,formatoDatos))
                    subtotalEstano+=it.costoLaboratorio2
                    fila++
                    contador++
                }
                if(it.costoLaboratorio3&&it.detalleLaboratorio3&&it.detalleLaboratorio3.contains(nombreDeLaboratorio)){
                    sheet1.addCell(new Label(2,fila,""+contador,formatoDatos)) //SB
                    sheet1.addCell(new DateTime(3,fila, it.fechaDeRecepcion,formatoFecha))
                    sheet1.addCell(new Label(4,fila, "Sn",formatoDatos))
                    sheet1.addCell(new Label(5,fila, it.toString(),formatoDatos))
                    sheet1.addCell(new Number(6,fila, it.costoLaboratorio3,formatoDatos))
                    if (nombreDeLaboratorio.equals(""))
                        sheet1.addCell(new Label(7,fila, it.detalleLaboratorio3,formatoDatos))
                    subtotalEstano+=it.costoLaboratorio3
                    fila++
                    contador++
                }
                if(it.costoLaboratorio4&&it.detalleLaboratorio4&&it.detalleLaboratorio4.contains(nombreDeLaboratorio)){
                    sheet1.addCell(new Label(2,fila,""+contador,formatoDatos)) //SB
                    sheet1.addCell(new DateTime(3,fila, it.fechaDeRecepcion,formatoFecha))
                    sheet1.addCell(new Label(4,fila, "Sn",formatoDatos))
                    sheet1.addCell(new Label(5,fila, it.toString(),formatoDatos))
                    sheet1.addCell(new Number(6,fila, it.costoLaboratorio4,formatoDatos))
                    if (nombreDeLaboratorio.equals(""))
                        sheet1.addCell(new Label(7,fila, it.detalleLaboratorio4,formatoDatos))
                    subtotalEstano+=it.costoLaboratorio4
                    fila++
                    contador++
                }
            }
            if (subtotalEstano!=0){
                sheet1.addCell(new Label(5,fila, "SUBTOTAL",formatoDatos))
                sheet1.addCell(new Number(6,fila, subtotalEstano,formatoDatos))
            }else{
                fila--
            }
        }

        fila++

        if(recepcionesPlata){
            recepcionesPlata.each {
                if(it.costoLaboratorio1&&it.detalleLaboratorio1&&it.detalleLaboratorio1.contains(nombreDeLaboratorio)){
                    sheet1.addCell(new Label(2,fila,""+contador,formatoDatos)) //SB
                    sheet1.addCell(new DateTime(3,fila, it.fechaDeRecepcion,formatoFecha))
                    sheet1.addCell(new Label(4,fila, "Ag",formatoDatos))
                    sheet1.addCell(new Label(5,fila, it.toString(),formatoDatos))
                    sheet1.addCell(new Number(6,fila, it.costoLaboratorio1,formatoDatos))
                    if (nombreDeLaboratorio.equals(""))
                        sheet1.addCell(new Label(7,fila, it.detalleLaboratorio1,formatoDatos))
                    subtotalPlata+=it.costoLaboratorio1
                    fila++
                    contador++
                }
                if(it.costoLaboratorio2&&it.detalleLaboratorio2&&it.detalleLaboratorio2.contains(nombreDeLaboratorio)){
                    sheet1.addCell(new Label(2,fila,""+contador,formatoDatos)) //SB
                    sheet1.addCell(new DateTime(3,fila, it.fechaDeRecepcion,formatoFecha))
                    sheet1.addCell(new Label(4,fila, "Ag",formatoDatos))
                    sheet1.addCell(new Label(5,fila, it.toString(),formatoDatos))
                    sheet1.addCell(new Number(6,fila, it.costoLaboratorio2,formatoDatos))
                    if (nombreDeLaboratorio.equals(""))
                        sheet1.addCell(new Label(7,fila, it.detalleLaboratorio2,formatoDatos))
                    subtotalPlata+=it.costoLaboratorio2
                    fila++
                    contador++
                }
                if(it.costoLaboratorio3&&it.detalleLaboratorio3&&it.detalleLaboratorio3.contains(nombreDeLaboratorio)){
                    sheet1.addCell(new Label(2,fila,""+contador,formatoDatos)) //SB
                    sheet1.addCell(new DateTime(3,fila, it.fechaDeRecepcion,formatoFecha))
                    sheet1.addCell(new Label(4,fila, "Ag",formatoDatos))
                    sheet1.addCell(new Label(5,fila, it.toString(),formatoDatos))
                    sheet1.addCell(new Number(6,fila, it.costoLaboratorio3,formatoDatos))
                    if (nombreDeLaboratorio.equals(""))
                        sheet1.addCell(new Label(7,fila, it.detalleLaboratorio3,formatoDatos))
                    subtotalPlata+=it.costoLaboratorio3
                    fila++
                    contador++
                }
                if(it.costoLaboratorio4&&it.detalleLaboratorio4&&it.detalleLaboratorio4.contains(nombreDeLaboratorio)){
                    sheet1.addCell(new Label(2,fila,""+contador,formatoDatos)) //SB
                    sheet1.addCell(new DateTime(3,fila, it.fechaDeRecepcion,formatoFecha))
                    sheet1.addCell(new Label(4,fila, "Ag",formatoDatos))
                    sheet1.addCell(new Label(5,fila, it.toString(),formatoDatos))
                    sheet1.addCell(new Number(6,fila, it.costoLaboratorio4,formatoDatos))
                    if (nombreDeLaboratorio.equals(""))
                        sheet1.addCell(new Label(7,fila, it.detalleLaboratorio4,formatoDatos))
                    subtotalPlata+=it.costoLaboratorio4
                    fila++
                    contador++
                }
            }
            if (subtotalPlata!=0){
                sheet1.addCell(new Label(5,fila, "SUBTOTAL",formatoDatos))
                sheet1.addCell(new Number(6,fila, subtotalPlata,formatoDatos))
            }else{
                fila--
            }
        }

        fila++

        if(recepcionesWolfran){
            recepcionesWolfran.each {
                if(it.costoLaboratorio1&&it.detalleLaboratorio1&&it.detalleLaboratorio1.contains(nombreDeLaboratorio)){
                    sheet1.addCell(new Label(2,fila,""+contador,formatoDatos)) //SB
                    sheet1.addCell(new DateTime(3,fila, it.fechaDeRecepcion,formatoFecha))
                    sheet1.addCell(new Label(4,fila, "W03",formatoDatos))
                    sheet1.addCell(new Label(5,fila, it.toString(),formatoDatos))
                    sheet1.addCell(new Number(6,fila, it.costoLaboratorio1,formatoDatos))
                    if (nombreDeLaboratorio.equals(""))
                        sheet1.addCell(new Label(7,fila, it.detalleLaboratorio1,formatoDatos))
                    subtotalWolfran+=it.costoLaboratorio1
                    fila++
                    contador++
                }
                if(it.costoLaboratorio2&&it.detalleLaboratorio2&&it.detalleLaboratorio2.contains(nombreDeLaboratorio)){
                    sheet1.addCell(new Label(2,fila,""+contador,formatoDatos)) //SB
                    sheet1.addCell(new DateTime(3,fila, it.fechaDeRecepcion,formatoFecha))
                    sheet1.addCell(new Label(4,fila, "W03",formatoDatos))
                    sheet1.addCell(new Label(5,fila, it.toString(),formatoDatos))
                    sheet1.addCell(new Number(6,fila, it.costoLaboratorio2,formatoDatos))
                    if (nombreDeLaboratorio.equals(""))
                        sheet1.addCell(new Label(7,fila, it.detalleLaboratorio2,formatoDatos))
                    subtotalWolfran+=it.costoLaboratorio2
                    fila++
                    contador++
                }
                if(it.costoLaboratorio3&&it.detalleLaboratorio3&&it.detalleLaboratorio3.contains(nombreDeLaboratorio)){
                    sheet1.addCell(new Label(2,fila,""+contador,formatoDatos)) //SB
                    sheet1.addCell(new DateTime(3,fila, it.fechaDeRecepcion,formatoFecha))
                    sheet1.addCell(new Label(4,fila, "W03",formatoDatos))
                    sheet1.addCell(new Label(5,fila, it.toString(),formatoDatos))
                    sheet1.addCell(new Number(6,fila, it.costoLaboratorio3,formatoDatos))
                    if (nombreDeLaboratorio.equals(""))
                        sheet1.addCell(new Label(7,fila, it.detalleLaboratorio3,formatoDatos))
                    subtotalWolfran+=it.costoLaboratorio3
                    fila++
                    contador++
                }
                if(it.costoLaboratorio4&&it.detalleLaboratorio4&&it.detalleLaboratorio4.contains(nombreDeLaboratorio)){
                    sheet1.addCell(new Label(2,fila,""+contador,formatoDatos)) //SB
                    sheet1.addCell(new DateTime(3,fila, it.fechaDeRecepcion,formatoFecha))
                    sheet1.addCell(new Label(4,fila, "W03",formatoDatos))
                    sheet1.addCell(new Label(5,fila, it.toString(),formatoDatos))
                    sheet1.addCell(new Number(6,fila, it.costoLaboratorio4,formatoDatos))
                    if (nombreDeLaboratorio.equals(""))
                        sheet1.addCell(new Label(7,fila, it.detalleLaboratorio4,formatoDatos))
                    subtotalWolfran+=it.costoLaboratorio4
                    fila++
                    contador++
                }
            }
            if (subtotalWolfran!=0){
                sheet1.addCell(new Label(5,fila, "SUBTOTAL",formatoDatos))
                sheet1.addCell(new Number(6,fila, subtotalWolfran,formatoDatos))
            }else{
                fila--
            }
        }

        fila++

        if(recepcionesAntimonio){
            recepcionesAntimonio.each {
                if(it.costoLaboratorio1&&it.detalleLaboratorio1&&it.detalleLaboratorio1.contains(nombreDeLaboratorio)){
                    sheet1.addCell(new Label(2,fila,""+contador,formatoDatos)) //SB
                    sheet1.addCell(new DateTime(3,fila, it.fechaDeRecepcion,formatoFecha))
                    sheet1.addCell(new Label(4,fila, "Sb",formatoDatos))
                    sheet1.addCell(new Label(5,fila, it.toString(),formatoDatos))
                    sheet1.addCell(new Number(6,fila, it.costoLaboratorio1,formatoDatos))
                    if (nombreDeLaboratorio.equals(""))
                        sheet1.addCell(new Label(7,fila, it.detalleLaboratorio1,formatoDatos))
                    subtotalAntimonio+=it.costoLaboratorio1
                    fila++
                    contador++
                }
                if(it.costoLaboratorio2&&it.detalleLaboratorio2&&it.detalleLaboratorio2.contains(nombreDeLaboratorio)){
                    sheet1.addCell(new Label(2,fila,""+contador,formatoDatos)) //SB
                    sheet1.addCell(new DateTime(3,fila, it.fechaDeRecepcion,formatoFecha))
                    sheet1.addCell(new Label(4,fila, "Sb",formatoDatos))
                    sheet1.addCell(new Label(5,fila, it.toString(),formatoDatos))
                    sheet1.addCell(new Number(6,fila, it.costoLaboratorio2,formatoDatos))
                    if (nombreDeLaboratorio.equals(""))
                        sheet1.addCell(new Label(7,fila, it.detalleLaboratorio2,formatoDatos))
                    subtotalAntimonio+=it.costoLaboratorio2
                    fila++
                    contador++
                }
                if(it.costoLaboratorio3&&it.detalleLaboratorio3&&it.detalleLaboratorio3.contains(nombreDeLaboratorio)){
                    sheet1.addCell(new Label(2,fila,""+contador,formatoDatos)) //SB
                    sheet1.addCell(new DateTime(3,fila, it.fechaDeRecepcion,formatoFecha))
                    sheet1.addCell(new Label(4,fila, "Sb",formatoDatos))
                    sheet1.addCell(new Label(5,fila, it.toString(),formatoDatos))
                    sheet1.addCell(new Number(6,fila, it.costoLaboratorio3,formatoDatos))
                    if (nombreDeLaboratorio.equals(""))
                        sheet1.addCell(new Label(7,fila, it.detalleLaboratorio3,formatoDatos))
                    subtotalAntimonio+=it.costoLaboratorio3
                    fila++
                    contador++
                }
                if(it.costoLaboratorio4&&it.detalleLaboratorio4&&it.detalleLaboratorio4.contains(nombreDeLaboratorio)){
                    sheet1.addCell(new Label(2,fila,""+contador,formatoDatos)) //SB
                    sheet1.addCell(new DateTime(3,fila, it.fechaDeRecepcion,formatoFecha))
                    sheet1.addCell(new Label(4,fila, "Sb",formatoDatos))
                    sheet1.addCell(new Label(5,fila, it.toString(),formatoDatos))
                    sheet1.addCell(new Number(6,fila, it.costoLaboratorio4,formatoDatos))
                    if (nombreDeLaboratorio.equals(""))
                        sheet1.addCell(new Label(7,fila, it.detalleLaboratorio4,formatoDatos))
                    subtotalAntimonio+=it.costoLaboratorio4
                    fila++
                    contador++
                }
            }
            if (subtotalAntimonio!=0){
                sheet1.addCell(new Label(5,fila, "SUBTOTAL",formatoDatos))
                sheet1.addCell(new Number(6,fila, subtotalAntimonio,formatoDatos))
            }else{
                fila--
            }
        }

        fila++

        if(recepcionesComplejo){
            recepcionesComplejo.each {
                if(it.costoLaboratorio1&&it.detalleLaboratorio1&&it.detalleLaboratorio1.contains(nombreDeLaboratorio)){
                    sheet1.addCell(new Label(2,fila,""+contador,formatoDatos)) //SB
                    sheet1.addCell(new DateTime(3,fila, it.fechaDeRecepcion,formatoFecha))
                    sheet1.addCell(new Label(4,fila, "Zn Pb Ag",formatoDatos))
                    sheet1.addCell(new Label(5,fila, it.toString(),formatoDatos))
                    sheet1.addCell(new Number(6,fila, it.costoLaboratorio1,formatoDatos))
                    if (nombreDeLaboratorio.equals(""))
                        sheet1.addCell(new Label(7,fila, it.detalleLaboratorio1,formatoDatos))
                    subtotalComplejo+=it.costoLaboratorio1
                    fila++
                    contador++
                }
                if(it.costoLaboratorio2&&it.detalleLaboratorio2&&it.detalleLaboratorio2.contains(nombreDeLaboratorio)){
                    sheet1.addCell(new Label(2,fila,""+contador,formatoDatos)) //SB
                    sheet1.addCell(new DateTime(3,fila, it.fechaDeRecepcion,formatoFecha))
                    sheet1.addCell(new Label(4,fila, "Zn Pb Ag",formatoDatos))
                    sheet1.addCell(new Label(5,fila, it.toString(),formatoDatos))
                    sheet1.addCell(new Number(6,fila, it.costoLaboratorio2,formatoDatos))
                    if (nombreDeLaboratorio.equals(""))
                        sheet1.addCell(new Label(7,fila, it.detalleLaboratorio2,formatoDatos))
                    subtotalComplejo+=it.costoLaboratorio2
                    fila++
                    contador++
                }
                if(it.costoLaboratorio3&&it.detalleLaboratorio3&&it.detalleLaboratorio3.contains(nombreDeLaboratorio)){
                    sheet1.addCell(new Label(2,fila,""+contador,formatoDatos)) //SB
                    sheet1.addCell(new DateTime(3,fila, it.fechaDeRecepcion,formatoFecha))
                    sheet1.addCell(new Label(4,fila, "Zn Pb Ag",formatoDatos))
                    sheet1.addCell(new Label(5,fila, it.toString(),formatoDatos))
                    sheet1.addCell(new Number(6,fila, it.costoLaboratorio3,formatoDatos))
                    if (nombreDeLaboratorio.equals(""))
                        sheet1.addCell(new Label(7,fila, it.detalleLaboratorio3,formatoDatos))
                    subtotalComplejo+=it.costoLaboratorio3
                    fila++
                    contador++
                }
                if(it.costoLaboratorio4&&it.detalleLaboratorio4&&it.detalleLaboratorio4.contains(nombreDeLaboratorio)){
                    sheet1.addCell(new Label(2,fila,""+contador,formatoDatos)) //SB
                    sheet1.addCell(new DateTime(3,fila, it.fechaDeRecepcion,formatoFecha))
                    sheet1.addCell(new Label(4,fila, "Zn Pb Ag",formatoDatos))
                    sheet1.addCell(new Label(5,fila, it.toString(),formatoDatos))
                    sheet1.addCell(new Number(6,fila, it.costoLaboratorio4,formatoDatos))
                    if (nombreDeLaboratorio.equals(""))
                        sheet1.addCell(new Label(7,fila, it.detalleLaboratorio4,formatoDatos))
                    subtotalComplejo+=it.costoLaboratorio4
                    fila++
                    contador++
                }
            }
            if (subtotalComplejo!=0){
                sheet1.addCell(new Label(5,fila, "SUBTOTAL",formatoDatos))
                sheet1.addCell(new Number(6,fila, subtotalComplejo,formatoDatos))
            }else{
                fila--
            }
        }

        fila++
        sheet1.addCell(new Label(5,fila, "TOTAL",formatoDatos))
        sheet1.addCell(new Number(6,fila, subtotalEstano+subtotalPlata+subtotalWolfran+subtotalAntimonio+subtotalComplejo,formatoDatos))

        workbook.write();
        workbook.close();
    }
}
