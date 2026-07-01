package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import jxl.Workbook
import jxl.format.Alignment
import jxl.write.*
import org.socymet.anticipos.AnticipoContraEntrega
import org.socymet.proveedor.Cliente
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA','ROLE_REPORTES'])
@Transactional
class ReporteLotesRecepcionadosController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def jasperService
    def reporteXlsxBuilderService   // genera el XLSX (Apache POI)

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reporteLotesRecepcionadosInstanceList: ReporteLotesRecepcionados.list(params), reporteLotesRecepcionadosInstanceTotal: ReporteLotesRecepcionados.count()]
    }

    // ── Reporte XLSX (Apache POI): filtros + vista previa + exportación ────────

    def create() {
        def empresa = params.empresaId ? Empresa.get(params.long('empresaId')) : null
        def deposito = params.depositoId ? Deposito.get(params.long('depositoId')) : null
        def cliente = params.clienteId ? Cliente.get(params.long('clienteId')) : null
        def estado = params.estado ?: 'Todos'
        Date fi = fechaDe('fechaInicial')
        Date ff = fechaDe('fechaFinal', true)   // hasta 23:59:59
        def filas = null
        def totSacos = 0, totPeso = 0.0G
        if (fi && ff) {
            filas = []
            consultarRecepciones(empresa, deposito, cliente, fi, ff, estado).each { r ->
                def nSacos = sacos(r)
                filas << [fecha: r.fechaDeRecepcion, lote: r.toString(), procedencia: r.empresa?.toString(),
                          proveedor: r.cliente?.nombre, sacos: nSacos, pesoBruto: r.pesoBruto ?: 0.0G,
                          estado: r.estadoDelLote]
                totSacos += nSacos; totPeso += (r.pesoBruto ?: 0.0G)
            }
        }
        [empresa: empresa, deposito: deposito, cliente: cliente, estado: estado,
         fechaInicial: fi ?: new Date(), fechaFinal: ff ?: new Date(),
         filas: filas, totSacos: totSacos, totPeso: totPeso]
    }

    /** Exporta a XLSX con los mismos filtros. Recibe empresaId/depositoId (opc.), estado, fi, ff (yyyy-MM-dd). */
    def exportarExcel() {
        def empresa = params.empresaId ? Empresa.get(params.long('empresaId')) : null
        def deposito = params.depositoId ? Deposito.get(params.long('depositoId')) : null
        def cliente = params.clienteId ? Cliente.get(params.long('clienteId')) : null
        def estado = params.estado ?: 'Todos'
        Date fi = params.fi ? new java.text.SimpleDateFormat('yyyy-MM-dd').parse(params.fi) : null
        Date ff = params.ff ? new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss').parse(params.ff + ' 23:59:59') : null
        if (!fi || !ff) { flash.message = "Seleccione un rango de fechas antes de exportar."; redirect(action: "create"); return }

        def recepciones = consultarRecepciones(empresa, deposito, cliente, fi, ff, estado)
        def fmt = new java.text.SimpleDateFormat('dd/MM/yyyy')

        def columnas = [
            [titulo: 'Fec. Rec.',    ancho: 12, tipo: 'fecha'],
            [titulo: 'Lote',         ancho: 16, tipo: 'texto'],
            [titulo: 'Procedencia',  ancho: 30, tipo: 'texto'],
            [titulo: 'Proveedor',    ancho: 30, tipo: 'texto'],
            [titulo: 'Sacos',        ancho: 10, tipo: 'numero', total: 'suma'],
            [titulo: 'P. Bruto [Kg]',ancho: 14, tipo: 'numero', total: 'suma'],
            [titulo: 'Estado',       ancho: 16, tipo: 'texto'],
        ]
        def filas = recepciones.collect { r ->
            [ r.fechaDeRecepcion, r.toString(), r.empresa?.toString(), r.cliente?.nombre,
              sacos(r), r.pesoBruto ?: 0, r.estadoDelLote ]
        }

        byte[] xlsx = reporteXlsxBuilderService.construir([
            nombreHoja: 'Lotes Recepcionados',
            titulo: 'REPORTE DE LOTES RECEPCIONADOS',
            subtitulos: [
                (empresa ? "Empresa: ${empresa}" : "Empresa: Todas"),
                (deposito ? "Depósito: ${deposito}" : "Depósito: Todos"),
                (cliente ? "Cliente: ${cliente.nombre}" : "Cliente: Todos"),
                "Estado: ${estado}",
                "Periodo: ${fmt.format(fi)} al ${fmt.format(ff)}"
            ],
            columnas: columnas, filas: filas
        ])

        response.setContentType('application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')
        response.setHeader('Content-Disposition', 'attachment; filename="reporte_lotes_recepcionados.xlsx"')
        response.outputStream << xlsx
        response.outputStream.flush()
    }

    /** Recepciones de complejo por rango de fechas y, opcionalmente, empresa, depósito, cliente y estado. */
    private List consultarRecepciones(empresa, deposito, cliente, Date fi, Date ff, String estado) {
        RecepcionDeComplejo.createCriteria().list(sort: 'fechaDeRecepcion', order: 'asc') {
            between('fechaDeRecepcion', fi, ff)
            if (empresa) eq('empresa', empresa)
            if (deposito) eq('deposito', deposito)
            if (cliente) eq('cliente', cliente)
            if (estado && estado != 'Todos') eq('estadoDelLote', estado)
        }
    }

    /** Cantidad de sacos numérica (campo nuevo cantidadSacos; respaldo al String legado cantidadDeSacos). */
    private static int sacos(r) {
        if (r.cantidadSacos != null) return r.cantidadSacos
        try { return (r.cantidadDeSacos ?: '0').toString().toBigDecimal().intValue() } catch (ignored) { return 0 }
    }

    /** Parsea las partes _day/_month/_year del datepickerUI a Date (fin=true → 23:59:59). */
    private Date fechaDe(String campo, boolean fin = false) {
        if (!params["${campo}_year"]) return null
        def base = "${params[campo + '_year']}-${params[campo + '_month']}-${params[campo + '_day']}"
        fin ? new java.text.SimpleDateFormat('yyyy-M-d HH:mm:ss').parse("$base 23:59:59")
            : new java.text.SimpleDateFormat('yyyy-M-d').parse(base)
    }

    def save() {
        def reporteLotesRecepcionadosInstance = new ReporteLotesRecepcionados(params)
        if (!reporteLotesRecepcionadosInstance.save(flush: true)) {
            render(view: "create", model: [reporteLotesRecepcionadosInstance: reporteLotesRecepcionadosInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reporteLotesRecepcionados.label', default: 'ReporteLotesRecepcionados'), reporteLotesRecepcionadosInstance.id])
        redirect(action: "show", id: reporteLotesRecepcionadosInstance.id)
    }

    def show(Long id) {
        def reporteLotesRecepcionadosInstance = ReporteLotesRecepcionados.get(id)
        if (!reporteLotesRecepcionadosInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteLotesRecepcionados.label', default: 'ReporteLotesRecepcionados'), id])
            redirect(action: "list")
            return
        }

        [reporteLotesRecepcionadosInstance: reporteLotesRecepcionadosInstance]
    }

    def edit(Long id) {
        def reporteLotesRecepcionadosInstance = ReporteLotesRecepcionados.get(id)
        if (!reporteLotesRecepcionadosInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteLotesRecepcionados.label', default: 'ReporteLotesRecepcionados'), id])
            redirect(action: "list")
            return
        }

        [reporteLotesRecepcionadosInstance: reporteLotesRecepcionadosInstance]
    }

    def update(Long id, Long version) {
        def reporteLotesRecepcionadosInstance = ReporteLotesRecepcionados.get(id)
        if (!reporteLotesRecepcionadosInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteLotesRecepcionados.label', default: 'ReporteLotesRecepcionados'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reporteLotesRecepcionadosInstance.version > version) {
                reporteLotesRecepcionadosInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'reporteLotesRecepcionados.label', default: 'ReporteLotesRecepcionados')] as Object[],
                          "Another user has updated this ReporteLotesRecepcionados while you were editing")
                render(view: "edit", model: [reporteLotesRecepcionadosInstance: reporteLotesRecepcionadosInstance])
                return
            }
        }

        reporteLotesRecepcionadosInstance.properties = params

        if (!reporteLotesRecepcionadosInstance.save(flush: true)) {
            render(view: "edit", model: [reporteLotesRecepcionadosInstance: reporteLotesRecepcionadosInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reporteLotesRecepcionados.label', default: 'ReporteLotesRecepcionados'), reporteLotesRecepcionadosInstance.id])
        redirect(action: "show", id: reporteLotesRecepcionadosInstance.id)
    }

    def delete(Long id) {
        def reporteLotesRecepcionadosInstance = ReporteLotesRecepcionados.get(id)
        if (!reporteLotesRecepcionadosInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteLotesRecepcionados.label', default: 'ReporteLotesRecepcionados'), id])
            redirect(action: "list")
            return
        }

        try {
            reporteLotesRecepcionadosInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reporteLotesRecepcionados.label', default: 'ReporteLotesRecepcionados'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reporteLotesRecepcionados.label', default: 'ReporteLotesRecepcionados'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporte = {
        //chain(controller:'jasper',action:'index',model:[data:null],params:params)

        chain(controller:'jasper',action:'index',params:params)
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

        WritableSheet sheet1 = workbook.createSheet("Lotes Recepcionados", 0)

        for(i in 0..100)
            sheet1.setColumnView(i,12)
        sheet1.setColumnView(2,30)
        sheet1.setColumnView(3,30)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="reporte_lotes_recepcionados.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(0,0, "REPORTE DE RECEPCION",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def deposito = Deposito.get(params.deposito.id)
        def estado = params.estado.toString()
        def tipoReporte = ""+params.tipoReporte
        def empresa=null
        def fechaInicial=null
        def fechaFinal=null
        def loteInicial=0
        def loteFinal=0

        def recepcionesComplejo=null

//        sheet1.addCell(new Label(0,2, "ELEMENTO: COMPLEJO",formatoInfoReporte))
//        sheet1.addCell(new Label(0,3, "DEPOSITO: ${deposito}",formatoInfoReporte))

        if (tipoReporte.equals("fechas")){
            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            log.error("fechaInicial: $fechaInicial")
            log.error("fechaFinal: $fechaFinal")

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(0,3, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            if (estado.equals("Todos"))
                recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetween(fechaInicial,fechaFinal)
            else
                recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetweenAndEstadoDelLote(fechaInicial,fechaFinal,estado)
        }
        if (tipoReporte.equals("fechasEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            log.error("empresa: $empresa")
            log.error("fechaInicial: $fechaInicial")
            log.error("fechaFinal: $fechaFinal")

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(0,2, "EMPRESA: ${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(0,3, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            if (estado.equals("Todos"))
                recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetweenAndEmpresa(fechaInicial,fechaFinal,empresa)
            else
                recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetweenAndEmpresaAndEstadoDelLote(fechaInicial,fechaFinal,empresa,estado)
        }

        sheet1.addCell(new Label(0,5, "FEC. REC.",formatoEncabezado))
        sheet1.addCell(new Label(1,5, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(2,5, "PROCEDENCIA",formatoEncabezado))
        sheet1.addCell(new Label(3,5, "PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(4,5, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(5,5, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(6,5, "ANTICIPO",formatoEncabezado))
        sheet1.addCell(new Label(7,5, "ESTADO",formatoEncabezado))

        def fila = 6

        def totalCantidadDeSacos=0
        def totalPesoBruto=0
        def totalAnticipo=0

        if(recepcionesComplejo){
            recepcionesComplejo.each {
                sheet1.addCell(new DateTime(0,fila, it.fechaDeRecepcion,formatoFecha))
                sheet1.addCell(new Label(1,fila, it.toString(),formatoDatos))
                sheet1.addCell(new Label(2,fila, it.empresa.toString(),formatoDatos))
                sheet1.addCell(new Label(3,fila, it.cliente.nombre,formatoDatos))
                sheet1.addCell(new Number(4,fila, Double.parseDouble(it.cantidadDeSacos),formatoDatos))
                sheet1.addCell(new Number(5,fila, it.pesoBruto,formatoDatos))
                def anticipo = AnticipoContraEntrega.findByRecepcionId(it.id)
                def montoAnticipo = (anticipo)?anticipo.importe:0
                sheet1.addCell(new Number(6,fila, montoAnticipo,formatoDatos))
                sheet1.addCell(new Label(7,fila, it.estadoDelLote,formatoDatos))

                totalCantidadDeSacos+=Double.parseDouble(it.cantidadDeSacos.toString())
                totalPesoBruto+=it.pesoBruto
                totalAnticipo+=montoAnticipo

                fila++
            }
            //llenado de totales
            sheet1.addCell(new Number(4,fila, totalCantidadDeSacos,formatoTotales))
            sheet1.addCell(new Number(5,fila, totalPesoBruto,formatoTotales))
            sheet1.addCell(new Number(6,fila, totalAnticipo,formatoTotales))
        }

        workbook.write();
        workbook.close();
    }

    def crearReportePlomoPlata = {
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

        WritableSheet sheet1 = workbook.createSheet("Reporte de Lotes Recepcionados", 0)

        for(i in 0..100)
            sheet1.setColumnView(i,12)
        sheet1.setColumnView(2,30)
        sheet1.setColumnView(3,30)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="reporte_lotes_recepcionados.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(2,0, "REPORTE DE RECEPCION",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def deposito = Deposito.get(params.deposito.id)
        def estado = params.estado.toString()
        def tipoReporte = ""+params.tipoReporte
        def empresa=null
        def fechaInicial=null
        def fechaFinal=null
        def loteInicial=0
        def loteFinal=0

        def recepcionesComplejo=null

        sheet1.addCell(new Label(0,2, "ELEMENTO: PB-AG",formatoInfoReporte))
        sheet1.addCell(new Label(0,3, "DEPOSITO: ${deposito}",formatoInfoReporte))

        if (tipoReporte.equals("fechas")){
            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(0,4, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            if (estado.equals("Todos"))
                recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetweenAndTipoDeMineralAndDeposito(fechaInicial,fechaFinal,"PB-AG",deposito)
            else
                recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetweenAndTipoDeMineralAndEstadoDelLoteAndDeposito(fechaInicial,fechaFinal,"PB-AG",estado,deposito)
        }
        if (tipoReporte.equals("fechasEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(0,5, "EMPRESA: ${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(0,4, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            if (estado.equals("Todos"))
                recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetweenAndEmpresaAndTipoDeMineralAndDeposito(fechaInicial,fechaFinal,empresa,"PB-AG",deposito)
            else
                recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetweenAndEmpresaAndTipoDeMineralAndEstadoDelLoteAndDeposito(fechaInicial,fechaFinal,empresa,"PB-AG",estado,deposito)
        }
        if (tipoReporte.equals("lotes")){
            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            if (estado.equals("Todos"))
                recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejoBetweenAndTipoDeMineralAndDeposito(loteInicial,loteFinal,"PB-AG",deposito)
            else
                recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejoBetweenAndTipoDeMineralAndEstadoDelLoteAndDeposito(loteInicial,loteFinal,"PB-AG",estado,deposito)

            sheet1.addCell(new Label(0,4, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))
        }
        if (tipoReporte.equals("lotesEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            if (estado.equals("Todos"))
                    recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejoBetweenAndEmpresaAndTipoDeMineralAndDeposito(loteInicial,loteFinal,empresa,"PB-AG",deposito)
            else
                recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejoBetweenAndEmpresaAndTipoDeMineralAndEstadoDelLoteAndDeposito(loteInicial,loteFinal,empresa,"PB-AG",estado,deposito)

            sheet1.addCell(new Label(0,5, "EMPRESA: ${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(0,4, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))
        }

        sheet1.addCell(new Label(0,7, "FEC. REC.",formatoEncabezado))
        sheet1.addCell(new Label(1,7, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(2,7, "PROCEDENCIA",formatoEncabezado))
        sheet1.addCell(new Label(3,7, "PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(4,7, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(5,7, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(6,7, "ANTICIPO",formatoEncabezado))
        sheet1.addCell(new Label(7,7, "ESTADO",formatoEncabezado))

        def fila = 8

        def totalCantidadDeSacos=0
        def totalPesoBruto=0
        def totalAnticipo=0

        if(recepcionesComplejo){
            recepcionesComplejo.each {
                sheet1.addCell(new DateTime(0,fila, it.fechaDeRecepcion,formatoFecha))
                sheet1.addCell(new Label(1,fila, it.toString(),formatoDatos))
                sheet1.addCell(new Label(2,fila, it.empresa.toString(),formatoDatos))
                sheet1.addCell(new Label(3,fila, it.cliente.nombre,formatoDatos))
                sheet1.addCell(new Number(4,fila, Double.parseDouble(it.cantidadDeSacos),formatoDatos))
                sheet1.addCell(new Number(5,fila, it.pesoBruto,formatoDatos))
                def anticipo = AnticipoContraEntrega.findByRecepcionId(it.id)
                def montoAnticipo = (anticipo)?anticipo.importe:0
                sheet1.addCell(new Number(6,fila, montoAnticipo,formatoDatos))
                sheet1.addCell(new Label(7,fila, it.estadoDelLote,formatoDatos))

                totalCantidadDeSacos+=Double.parseDouble(it.cantidadDeSacos.toString())
                totalPesoBruto+=it.pesoBruto
                totalAnticipo+=montoAnticipo

                fila++
            }
            //llenado de totales
            sheet1.addCell(new Number(4,fila, totalCantidadDeSacos,formatoTotales))
            sheet1.addCell(new Number(5,fila, totalPesoBruto,formatoTotales))
            sheet1.addCell(new Number(6,fila, totalAnticipo,formatoTotales))
        }

        workbook.write();
        workbook.close();
    }

    def crearReporteZincPlata = {
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

        WritableSheet sheet1 = workbook.createSheet("Reporte de Lotes Recepcionados", 0)
        
        for(i in 0..100)
            sheet1.setColumnView(i,12)
        sheet1.setColumnView(2,30)
        sheet1.setColumnView(3,30)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="reporte_lotes_recepcionados.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(2,0, "REPORTE DE RECEPCION",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def deposito = Deposito.get(params.deposito.id)
        def estado = params.estado.toString()
        def tipoReporte = ""+params.tipoReporte
        def empresa=null
        def fechaInicial=null
        def fechaFinal=null
        def loteInicial=0
        def loteFinal=0

        def recepcionesComplejo=null

        sheet1.addCell(new Label(0,2, "ELEMENTO: ZN-AG",formatoInfoReporte))
        sheet1.addCell(new Label(0,3, "DEPOSITO: ${deposito}",formatoInfoReporte))

        if (tipoReporte.equals("fechas")){
            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(0,4, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            if (estado.equals("Todos"))
                recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetweenAndTipoDeMineralAndDeposito(fechaInicial,fechaFinal,"ZN-AG",deposito)
            else
                recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetweenAndTipoDeMineralAndEstadoDelLoteAndDeposito(fechaInicial,fechaFinal,"ZN-AG",estado,deposito)
        }
        if (tipoReporte.equals("fechasEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(0,5, "EMPRESA: ${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(0,4, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            if (estado.equals("Todos"))
                recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetweenAndEmpresaAndTipoDeMineralAndDeposito(fechaInicial,fechaFinal,empresa,"ZN-AG",deposito)
            else
                recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetweenAndEmpresaAndTipoDeMineralAndEstadoDelLoteAndDeposito(fechaInicial,fechaFinal,empresa,"ZN-AG",estado,deposito)
        }
        if (tipoReporte.equals("lotes")){
            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            if (estado.equals("Todos"))
                recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejoBetweenAndTipoDeMineralAndDeposito(loteInicial,loteFinal,"ZN-AG",deposito)
            else
                recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejoBetweenAndTipoDeMineralAndEstadoDelLoteAndDeposito(loteInicial,loteFinal,"ZN-AG",estado,deposito)

            sheet1.addCell(new Label(0,4, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))
        }
        if (tipoReporte.equals("lotesEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            if (estado.equals("Todos"))
                recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejoBetweenAndEmpresaAndTipoDeMineralAndDeposito(loteInicial,loteFinal,empresa,"ZN-AG",deposito)
            else
                recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejoBetweenAndEmpresaAndTipoDeMineralAndEstadoDelLoteAndDeposito(loteInicial,loteFinal,empresa,"ZN-AG",estado,deposito)

            sheet1.addCell(new Label(0,5, "EMPRESA: ${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(0,4, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))
        }

        sheet1.addCell(new Label(0,7, "FEC. REC.",formatoEncabezado))
        sheet1.addCell(new Label(1,7, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(2,7, "PROCEDENCIA",formatoEncabezado))
        sheet1.addCell(new Label(3,7, "PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(4,7, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(5,7, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(6,7, "ANTICIPO",formatoEncabezado))
        sheet1.addCell(new Label(7,7, "ESTADO",formatoEncabezado))

        def fila = 8

        def totalCantidadDeSacos=0
        def totalPesoBruto=0
        def totalAnticipo=0

        if(recepcionesComplejo){
            recepcionesComplejo.each {
                sheet1.addCell(new DateTime(0,fila, it.fechaDeRecepcion,formatoFecha))
                sheet1.addCell(new Label(1,fila, it.toString(),formatoDatos))
                sheet1.addCell(new Label(2,fila, it.empresa.toString(),formatoDatos))
                sheet1.addCell(new Label(3,fila, it.cliente.nombre,formatoDatos))
                sheet1.addCell(new Number(4,fila, Double.parseDouble(it.cantidadDeSacos),formatoDatos))
                sheet1.addCell(new Number(5,fila, it.pesoBruto,formatoDatos))
                def anticipo = AnticipoContraEntrega.findByRecepcionId(it.id)
                def montoAnticipo = (anticipo)?anticipo.importe:0
                sheet1.addCell(new Number(6,fila, montoAnticipo,formatoDatos))
                sheet1.addCell(new Label(7,fila, it.estadoDelLote,formatoDatos))

                totalCantidadDeSacos+=Double.parseDouble(it.cantidadDeSacos.toString())
                totalPesoBruto+=it.pesoBruto
                totalAnticipo+=montoAnticipo

                fila++
            }
            //llenado de totales
            sheet1.addCell(new Number(4,fila, totalCantidadDeSacos,formatoTotales))
            sheet1.addCell(new Number(5,fila, totalPesoBruto,formatoTotales))
            sheet1.addCell(new Number(6,fila, totalAnticipo,formatoTotales))
        }

        workbook.write();
        workbook.close();
    }

    def crearReporteCobrePlata = {
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

        WritableSheet sheet1 = workbook.createSheet("Reporte de Lotes Recepcionados", 0)

        for(i in 0..100)
            sheet1.setColumnView(i,12)
        sheet1.setColumnView(2,30)
        sheet1.setColumnView(3,30)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="reporte_lotes_recepcionados.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(2,0, "REPORTE DE RECEPCION",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def deposito = Deposito.get(params.deposito.id)
        def estado = params.estado.toString()
        def tipoReporte = ""+params.tipoReporte
        def empresa=null
        def fechaInicial=null
        def fechaFinal=null
        def loteInicial=0
        def loteFinal=0

        def recepcionesComplejo=null

        sheet1.addCell(new Label(0,2, "ELEMENTO: CU-AG",formatoInfoReporte))
        sheet1.addCell(new Label(0,3, "DEPOSITO: ${deposito}",formatoInfoReporte))

        if (tipoReporte.equals("fechas")){
            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(0,4, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            if (estado.equals("Todos"))
                recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetweenAndTipoDeMineralAndDeposito(fechaInicial,fechaFinal,"CU-AG",deposito)
            else
                recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetweenAndTipoDeMineralAndEstadoDelLoteAndDeposito(fechaInicial,fechaFinal,"CU-AG",estado,deposito)
        }
        if (tipoReporte.equals("fechasEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(0,5, "EMPRESA: ${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(0,4, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            if (estado.equals("Todos"))
                recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetweenAndEmpresaAndTipoDeMineralAndDeposito(fechaInicial,fechaFinal,empresa,"CU-AG",deposito)
            else
                recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetweenAndEmpresaAndTipoDeMineralAndEstadoDelLoteAndDeposito(fechaInicial,fechaFinal,empresa,"CU-AG",estado,deposito)
        }
        if (tipoReporte.equals("lotes")){
            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            if (estado.equals("Todos"))
                recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejoBetweenAndTipoDeMineralAndDeposito(loteInicial,loteFinal,"CU-AG",deposito)
            else
                recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejoBetweenAndTipoDeMineralAndEstadoDelLoteAndDeposito(loteInicial,loteFinal,"CU-AG",estado,deposito)

            sheet1.addCell(new Label(0,4, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))
        }
        if (tipoReporte.equals("lotesEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            if (estado.equals("Todos"))
                recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejoBetweenAndEmpresaAndTipoDeMineralAndDeposito(loteInicial,loteFinal,empresa,"CU-AG",deposito)
            else
                recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejoBetweenAndEmpresaAndTipoDeMineralAndEstadoDelLoteAndDeposito(loteInicial,loteFinal,empresa,"CU-AG",estado,deposito)

            sheet1.addCell(new Label(0,5, "EMPRESA: ${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(0,4, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))
        }

        sheet1.addCell(new Label(0,7, "FEC. REC.",formatoEncabezado))
        sheet1.addCell(new Label(1,7, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(2,7, "PROCEDENCIA",formatoEncabezado))
        sheet1.addCell(new Label(3,7, "PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(4,7, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(5,7, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(6,7, "ANTICIPO",formatoEncabezado))
        sheet1.addCell(new Label(7,7, "ESTADO",formatoEncabezado))

        def fila = 8

        def totalCantidadDeSacos=0
        def totalPesoBruto=0
        def totalAnticipo=0

        if(recepcionesComplejo){
            recepcionesComplejo.each {
                sheet1.addCell(new DateTime(0,fila, it.fechaDeRecepcion,formatoFecha))
                sheet1.addCell(new Label(1,fila, it.toString(),formatoDatos))
                sheet1.addCell(new Label(2,fila, it.empresa.toString(),formatoDatos))
                sheet1.addCell(new Label(3,fila, it.cliente.nombre,formatoDatos))
                sheet1.addCell(new Number(4,fila, Double.parseDouble(it.cantidadDeSacos),formatoDatos))
                sheet1.addCell(new Number(5,fila, it.pesoBruto,formatoDatos))
                def anticipo = AnticipoContraEntrega.findByRecepcionId(it.id)
                def montoAnticipo = (anticipo)?anticipo.importe:0
                sheet1.addCell(new Number(6,fila, montoAnticipo,formatoDatos))
                sheet1.addCell(new Label(7,fila, it.estadoDelLote,formatoDatos))

                totalCantidadDeSacos+=Double.parseDouble(it.cantidadDeSacos.toString())
                totalPesoBruto+=it.pesoBruto
                totalAnticipo+=montoAnticipo

                fila++
            }
            //llenado de totales
            sheet1.addCell(new Number(4,fila, totalCantidadDeSacos,formatoTotales))
            sheet1.addCell(new Number(5,fila, totalPesoBruto,formatoTotales))
            sheet1.addCell(new Number(6,fila, totalAnticipo,formatoTotales))
        }

        workbook.write();
        workbook.close();
    }
}
