package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import jxl.SheetSettings
import jxl.Workbook
import jxl.format.Alignment
import jxl.format.PageOrientation
import jxl.format.PaperSize
import jxl.format.VerticalAlignment
import jxl.write.*
import org.socymet.anticipos.Anticipo
import org.socymet.proveedor.Cliente
import org.socymet.proveedor.Empresa
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA','ROLE_REPORTES'])
@Transactional
class ReporteAnticiposController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def reporteXlsxBuilderService   // genera el XLSX (Apache POI)

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reporteAnticiposInstanceList: ReporteAnticipos.list(params), reporteAnticiposInstanceTotal: ReporteAnticipos.count()]
    }

    // ── Reporte XLSX (Apache POI): filtros + vista previa + exportación ────────

    def create() {
        def empresa = params.empresaId ? Empresa.get(params.long('empresaId')) : null
        def cliente = params.clienteId ? Cliente.get(params.long('clienteId')) : null
        Date fi = fechaDe('fechaInicial')
        Date ff = fechaDe('fechaFinal', true)
        def filas = null
        def tot = [:].withDefault { 0.0G }
        if (fi && ff) {
            filas = consultarAnticipos(empresa, cliente, fi, ff).collect { a -> filaAnticipo(a, tot) }
        }
        [empresa: empresa, cliente: cliente, fechaInicial: fi ?: new Date(), fechaFinal: ff ?: new Date(),
         filas: filas, tot: tot]
    }

    def exportarExcel() {
        def empresa = params.empresaId ? Empresa.get(params.long('empresaId')) : null
        def cliente = params.clienteId ? Cliente.get(params.long('clienteId')) : null
        Date fi = params.fi ? new java.text.SimpleDateFormat('yyyy-MM-dd').parse(params.fi) : null
        Date ff = params.ff ? new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss').parse(params.ff + ' 23:59:59') : null
        if (!fi || !ff) { flash.message = "Seleccione un rango de fechas antes de exportar."; redirect(action: "create"); return }

        def fmt = new java.text.SimpleDateFormat('dd/MM/yyyy')
        def tot = [:].withDefault { 0.0G }
        def filasMapa = consultarAnticipos(empresa, cliente, fi, ff).collect { a -> filaAnticipo(a, tot) }

        def columnas = [
            [titulo: 'Comprobante',      ancho: 14, tipo: 'texto'],
            [titulo: 'Empresa',          ancho: 28, tipo: 'texto'],
            [titulo: 'Cliente',          ancho: 28, tipo: 'texto'],
            [titulo: '1er Anticipo',     ancho: 13, tipo: 'fecha'],
            [titulo: 'N° Ant.',          ancho: 8,  tipo: 'numero'],
            [titulo: 'Total Anticipos',  ancho: 15, tipo: 'numero', total: 'suma'],
            [titulo: 'Total Pagado',     ancho: 15, tipo: 'numero', total: 'suma'],
            [titulo: 'Total Por Pagar',  ancho: 15, tipo: 'numero', total: 'suma'],
            [titulo: 'Lotes',            ancho: 40, tipo: 'texto'],
        ]
        def claves = ['comprobante','empresa','cliente','fecha','nAnt','totalAnticipos','totalPagado','totalPorPagar','lotes']
        def filas = filasMapa.collect { m -> claves.collect { m[it] } }

        byte[] xlsx = reporteXlsxBuilderService.construir([
            nombreHoja: 'Anticipos',
            titulo: 'REPORTE DE ANTICIPOS CONTRA ENTREGA',
            subtitulos: [(empresa ? "Empresa: ${empresa}" : "Empresa: Todas"),
                         (cliente ? "Cliente: ${cliente.nombre}" : "Cliente: Todos"),
                         "Periodo: ${fmt.format(fi)} al ${fmt.format(ff)}"],
            columnas: columnas, filas: filas
        ])
        response.setContentType('application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')
        response.setHeader('Content-Disposition', 'attachment; filename="reporte_anticipos.xlsx"')
        response.outputStream << xlsx
        response.outputStream.flush()
    }

    /** Anticipos con al menos una cuota en el rango, y opc. empresa/cliente. */
    private List consultarAnticipos(empresa, cliente, Date fi, Date ff) {
        Anticipo.createCriteria().listDistinct {
            cuotas { between('fecha', fi, ff) }
            if (empresa) eq('empresa', empresa)
            if (cliente) eq('cliente', cliente)
            order('nombreEmpresa', 'asc')
        }
    }

    /** Mapa de una fila + acumula totales. */
    private Map filaAnticipo(a, Map tot) {
        def fechas = a.cuotas*.fecha.findAll { it != null }
        // Comprobantes en formato numeroComprobante/yy (uno por cuota, ordenados por emisión)
        def yy = new java.text.SimpleDateFormat('yy')
        def comprobante = (a.cuotas?.sort { it.id ?: 0 } ?: []).collect {
            "${it.numeroComprobante}/${it.gestionMinera ? yy.format(it.gestionMinera) : '?'}"
        }.join(', ')
        def m = [
            comprobante: comprobante, empresa: a.nombreEmpresa, cliente: a.nombreCliente,
            fecha: (fechas ? fechas.min() : null), nAnt: (a.cuotas?.size() ?: 0),
            totalAnticipos: (a.totalAnticipos ?: 0.0G), totalPagado: (a.totalPagado ?: 0.0G),
            totalPorPagar: (a.totalPorPagar ?: 0.0G), lotes: (a.descripcion ?: '')
        ]
        ['totalAnticipos','totalPagado','totalPorPagar'].each { tot[it] = (tot[it] ?: 0.0G) + (m[it] ?: 0.0G) }
        m
    }

    /** Parsea las partes _day/_month/_year del datepickerUI a Date (fin=true → 23:59:59). */
    private Date fechaDe(String campo, boolean fin = false) {
        if (!params["${campo}_year"]) return null
        def base = "${params[campo + '_year']}-${params[campo + '_month']}-${params[campo + '_day']}"
        fin ? new java.text.SimpleDateFormat('yyyy-M-d HH:mm:ss').parse("$base 23:59:59")
            : new java.text.SimpleDateFormat('yyyy-M-d').parse(base)
    }

    def save() {
        def reporteAnticiposInstance = new ReporteAnticipos(params)
        if (!reporteAnticiposInstance.save(flush: true)) {
            render(view: "create", model: [reporteAnticiposInstance: reporteAnticiposInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reporteAnticipos.label', default: 'ReporteAnticipos'), reporteAnticiposInstance.id])
        redirect(action: "show", id: reporteAnticiposInstance.id)
    }

    def show(Long id) {
        def reporteAnticiposInstance = ReporteAnticipos.get(id)
        if (!reporteAnticiposInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteAnticipos.label', default: 'ReporteAnticipos'), id])
            redirect(action: "list")
            return
        }

        [reporteAnticiposInstance: reporteAnticiposInstance]
    }

    def edit(Long id) {
        def reporteAnticiposInstance = ReporteAnticipos.get(id)
        if (!reporteAnticiposInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteAnticipos.label', default: 'ReporteAnticipos'), id])
            redirect(action: "list")
            return
        }

        [reporteAnticiposInstance: reporteAnticiposInstance]
    }

    def update(Long id, Long version) {
        def reporteAnticiposInstance = ReporteAnticipos.get(id)
        if (!reporteAnticiposInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteAnticipos.label', default: 'ReporteAnticipos'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reporteAnticiposInstance.version > version) {
                reporteAnticiposInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reporteAnticipos.label', default: 'ReporteAnticipos')] as Object[],
                        "Another user has updated this ReporteAnticipos while you were editing")
                render(view: "edit", model: [reporteAnticiposInstance: reporteAnticiposInstance])
                return
            }
        }

        reporteAnticiposInstance.properties = params

        if (!reporteAnticiposInstance.save(flush: true)) {
            render(view: "edit", model: [reporteAnticiposInstance: reporteAnticiposInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reporteAnticipos.label', default: 'ReporteAnticipos'), reporteAnticiposInstance.id])
        redirect(action: "show", id: reporteAnticiposInstance.id)
    }

    def delete(Long id) {
        def reporteAnticiposInstance = ReporteAnticipos.get(id)
        if (!reporteAnticiposInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteAnticipos.label', default: 'ReporteAnticipos'), id])
            redirect(action: "list")
            return
        }

        try {
            reporteAnticiposInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reporteAnticipos.label', default: 'ReporteAnticipos'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reporteAnticipos.label', default: 'ReporteAnticipos'), id])
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
        formatoDatos.setWrap(true)
        formatoDatos.setVerticalAlignment(VerticalAlignment.CENTRE)

        WritableCellFormat formatoTotales = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoTotales.setFont(courier8BoldFont)
        formatoTotales.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)

        WritableCellFormat formatoInfoReporte = new WritableCellFormat (arial14BoldFont);
        WritableCellFormat formatoTitulo = new WritableCellFormat (arial16BoldFont);

        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        WritableSheet sheet1 = workbook.createSheet("Anticipos", 0)
        sheet1.setRowView(6,500)
        for(i in 0..100)
            sheet1.setColumnView(i,11)
        sheet1.setColumnView(1,30)
        sheet1.setColumnView(2,30)
        sheet1.setColumnView(12,40)

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
        response.setHeader('Content-Disposition', 'Attachment;Filename="reporte_anticipos.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(0,0, "REPORTE DE ANTICIPOS",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def tipoReporte = ""+params.tipoReporte
        def empresa=null
        def cliente=null
        def fechaInicial=null
        def fechaFinal=null

        def anticipos=null

        def retencionesDeLey=new ArrayList<String>()
        def retencionesOtras=new ArrayList<String>()

        if (tipoReporte.equals("fechas")){
            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(0,2, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

//            anticipos = Anticipo.list()
            anticipos = Anticipo.findAllByFechaPrimerAnticipoBetween(fechaInicial, fechaFinal)
        }
        
        if (tipoReporte.equals("fechasEmpresa")){
            empresa = Empresa.get(Integer.parseInt(""+params.empresa.id))

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)
            
            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(0,2, "EMPRESA: ${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(0,3, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            anticipos = Anticipo.findAllByEmpresaAndFechaPrimerAnticipo(empresa,fechaInicial, fechaFinal)
        }

        if (tipoReporte.equals("fechasCliente")){
            cliente = Cliente.get(Integer.parseInt(""+params.cliente.id))

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)
            
            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(0,2, "CLIENTE: ${cliente.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(0,3, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            anticipos = Anticipo.findAllByClienteAndFechaPrimerAnticipo(cliente, fechaInicial, fechaFinal)
        }

        System.out.println("*** RESULTADOS DE ANTICIPOS POR ${tipoReporte}: ${anticipos.size()}")

        sheet1.addCell(new Label(0,6, "No. COMPROBANTE",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "EMPRESA",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "NOMBRE",formatoEncabezado))
        sheet1.addCell(new Label(3,5, "1er. ANTICIPO",formatoEncabezado))
        sheet1.mergeCells(3,5,4,5)
        sheet1.addCell(new Label(3,6, "MONTO",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "FECHA",formatoEncabezado))
        sheet1.addCell(new Label(5,5, "2do. ANTICIPO",formatoEncabezado))
        sheet1.mergeCells(5,5,6,5)
        sheet1.addCell(new Label(5,6, "MONTO",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "FECHA",formatoEncabezado))
        sheet1.addCell(new Label(7,5, "3er. ANTICIPO",formatoEncabezado))
        sheet1.mergeCells(7,5,8,5)
        sheet1.addCell(new Label(7,6, "MONTO",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "FECHA",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "TOTAL ANTICIPOS",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "TOTAL PAGADO",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "TOTAL POR PAGAR",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "LOTES",formatoEncabezado))

        def totalPrimerAnticipo = 0
        def totalSegundoAnticipo = 0
        def totalTercerAnticipo = 0
        def totalTotalAnticipos = 0
        def totalTotalPagado = 0
        def totalTotalPorPagar = 0
        
        def fila = 7

        def primerAnticipo=0
        def segundoAnticipo=0
        def tercerAnticipo=0

        if(anticipos){
            anticipos.each {
                if((it.fechaPrimerAnticipo>=fechaInicial && it.fechaPrimerAnticipo<=fechaFinal)||(it.fechaSegundoAnticipo>=fechaInicial && it.fechaSegundoAnticipo<=fechaFinal)||(it.fechaTercerAnticipo>=fechaInicial && it.fechaTercerAnticipo<=fechaFinal)){
                    sheet1.addCell(new Number(0,fila, it.numeroComprobante,formatoDatos))
                    sheet1.addCell(new Label(1,fila, it.empresa.toString(),formatoDatos))
                    sheet1.addCell(new Label(2,fila, it.nombreCliente,formatoDatos))

                    sheet1.addCell(new Number(3,fila, it.primerAnticipo,formatoDatos)) //SN
                    sheet1.addCell(new DateTime(4,fila, it.fechaPrimerAnticipo,formatoFecha))
                    totalPrimerAnticipo+=it.primerAnticipo

                    sheet1.addCell(new Number(5,fila, it.segundoAnticipo,formatoDatos)) //SN
                    sheet1.addCell(new DateTime(6,fila, it.fechaSegundoAnticipo,formatoFecha))
                    totalSegundoAnticipo+=it.segundoAnticipo

                    sheet1.addCell(new Number(7,fila, it.tercerAnticipo,formatoDatos)) //SN
                    sheet1.addCell(new DateTime(8,fila, it.fechaTercerAnticipo,formatoFecha))
                    totalTercerAnticipo+=it.tercerAnticipo

                    sheet1.addCell(new Number(9,fila, it.totalAnticipos,formatoDatos))
                    sheet1.addCell(new Number(10,fila, it.totalPagado,formatoDatos))
                    sheet1.addCell(new Number(11,fila, it.totalPorPagar,formatoDatos))
                    sheet1.addCell(new Label(12,fila, it.descripcion,formatoDatos))

                    totalTotalAnticipos+=it.totalAnticipos
                    totalTotalPagado+=it.totalPagado
                    totalTotalPorPagar+=it.totalPorPagar

                    fila++
                }
            }

            sheet1.addCell(new Label(2,fila, "TOTALES",formatoTotales))
            sheet1.addCell(new Number(3,fila, totalPrimerAnticipo,formatoTotales))
            sheet1.addCell(new Number(5,fila, totalSegundoAnticipo,formatoTotales))
            sheet1.addCell(new Number(7,fila, totalTercerAnticipo,formatoTotales))
            sheet1.addCell(new Number(9,fila, totalTotalAnticipos,formatoTotales))
            sheet1.addCell(new Number(10,fila, totalTotalPagado,formatoTotales))
            sheet1.addCell(new Number(11,fila, totalTotalPorPagar,formatoTotales))
        }

        workbook.write();
        workbook.close();
    }
}
