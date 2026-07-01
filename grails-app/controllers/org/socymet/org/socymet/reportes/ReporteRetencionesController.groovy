package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional
import grails.converters.JSON

import jxl.SheetSettings
import jxl.Workbook
import jxl.format.Alignment
import jxl.format.PageOrientation
import jxl.format.PaperSize
import jxl.write.*
import org.socymet.liquidacion.*
import org.socymet.proveedor.Empresa
import org.socymet.seguridad.SecUser
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA','ROLE_REPORTES'])
@Transactional
class ReporteRetencionesController {

    def springSecurityService
    def reporteXlsxBuilderService   // genera el XLSX (Apache POI)

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reporteRetencionesInstanceList: ReporteRetenciones.list(params), reporteRetencionesInstanceTotal: ReporteRetenciones.count()]
    }

    // ── Reporte XLSX (Apache POI): filtros + vista previa + exportación ────────

    def create() {
        def empresa = params.empresaId ? Empresa.get(params.long('empresaId')) : null
        def tipo = params.tipoRetencion ?: 'Todas'
        def retencion = params.retencion ?: 'Todas'
        Date fi = fechaDe('fechaInicial')
        Date ff = fechaDe('fechaFinal', true)
        def filas = null
        def tot = [:].withDefault { 0.0G }
        if (fi && ff) filas = consultarRetenciones(empresa, fi, ff, tipo, retencion, tot)
        [empresa: empresa, tipoRetencion: tipo, retencion: retencion,
         fechaInicial: fi ?: new Date(), fechaFinal: ff ?: new Date(), filas: filas, tot: tot]
    }

    /** Descripciones de retención disponibles según tipo y rango de fechas (y empresa).
     *  Itera las liquidaciones igual que el reporte → los valores coinciden con lo que luego se filtra.
     *  Dinámico: refleja lo realmente retenido (pueden quitarse/modificarse en la liquidación). */
    def retencionesDisponiblesJSON() {
        def tipo = params.tipoRetencion ?: 'Todas'
        def empresa = params.empresaId ? Empresa.get(params.long('empresaId')) : null
        Date fi = fechaDe('fechaInicial')
        Date ff = fechaDe('fechaFinal', true)
        def descripciones = new TreeSet()
        if (fi && ff) {
            def liqs = LiquidacionDeComplejo.createCriteria().list {
                between('fechaDeLiquidacion', fi, ff)
                if (empresa) eq('empresa', empresa)
            }.findAll { !it.anulado }
            liqs.each { liq ->
                if ((tipo == 'Todas' || tipo == 'DE LEY') && (liq.regaliaMinera ?: 0.0G) > 0) descripciones.add('REGALIA MINERA')
                LiquidacionDeComplejoRetenciones.findAllByLiquidacionDeComplejo(liq).each { r ->
                    if (tipo != 'Todas' && r.tipoDeRetencion != tipo) return
                    if (r.descripcion) descripciones.add(r.descripcion)
                }
            }
        }
        render([results: descripciones as List] as JSON)
    }

    def exportarExcel() {
        def empresa = params.empresaId ? Empresa.get(params.long('empresaId')) : null
        def tipo = params.tipoRetencion ?: 'Todas'
        def retencion = params.retencion ?: 'Todas'
        Date fi = params.fi ? new java.text.SimpleDateFormat('yyyy-MM-dd').parse(params.fi) : null
        Date ff = params.ff ? new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss').parse(params.ff + ' 23:59:59') : null
        if (!fi || !ff) { flash.message = "Seleccione un rango de fechas antes de exportar."; redirect(action: "create"); return }

        def fmt = new java.text.SimpleDateFormat('dd/MM/yyyy')
        def tot = [:].withDefault { 0.0G }
        def filasMapa = consultarRetenciones(empresa, fi, ff, tipo, retencion, tot)

        def columnas = [
            [titulo: 'Fec. Liq.',   ancho: 12, tipo: 'fecha'],
            [titulo: 'N° Liq.',     ancho: 10, tipo: 'texto'],
            [titulo: 'Empresa',     ancho: 26, tipo: 'texto'],
            [titulo: 'Cliente',     ancho: 24, tipo: 'texto'],
            [titulo: 'Lote',        ancho: 16, tipo: 'texto'],
            [titulo: 'Tipo',        ancho: 10, tipo: 'texto'],
            [titulo: 'Descripción', ancho: 32, tipo: 'texto'],
            [titulo: 'Cantidad',    ancho: 11, tipo: 'numero'],
            [titulo: 'Unidad',      ancho: 9,  tipo: 'texto'],
            [titulo: 'Monto [Bs]',  ancho: 14, tipo: 'numero', total: 'suma'],
        ]
        def claves = ['fecha','numero','empresa','cliente','lote','tipo','descripcion','cantidad','unidad','monto']
        def filas = filasMapa.collect { m -> claves.collect { m[it] } }

        byte[] xlsx = reporteXlsxBuilderService.construir([
            nombreHoja: 'Retenciones',
            titulo: 'REPORTE DE RETENCIONES',
            subtitulos: [(empresa ? "Empresa: ${empresa}" : "Empresa: Todas"),
                         "Tipo: ${tipo}   Retención: ${retencion}",
                         "Periodo: ${fmt.format(fi)} al ${fmt.format(ff)}"],
            columnas: columnas, filas: filas
        ])
        response.setContentType('application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')
        response.setHeader('Content-Disposition', 'attachment; filename="reporte_retenciones.xlsx"')
        response.outputStream << xlsx
        response.outputStream.flush()
    }

    /** Una fila por retención de las liquidaciones (no anuladas) en el rango. La regalía minera se
     *  incluye como retención 'DE LEY / REGALIA MINERA'. Filtra por empresa/cliente/tipo. Acumula monto. */
    private List consultarRetenciones(empresa, Date fi, Date ff, String tipo, String retencion, Map tot) {
        def filtraDesc = (retencion && retencion != 'Todas')
        def liqs = LiquidacionDeComplejo.createCriteria().list(sort: 'fechaDeLiquidacion', order: 'asc') {
            between('fechaDeLiquidacion', fi, ff)
            if (empresa) eq('empresa', empresa)
        }.findAll { !it.anulado }
        def yy = new java.text.SimpleDateFormat('yy')
        def filas = []
        liqs.each { liq ->
            def numero = "${liq.numeroLiquidacionComplejo}/${liq.gestionMinera ? yy.format(liq.gestionMinera) : ''}".toString()
            def base = [fecha: liq.fechaDeLiquidacion, numero: numero, empresa: liq.nombreEmpresa,
                        cliente: liq.nombreCliente, lote: liq.lote]
            if ((tipo == 'Todas' || tipo == 'DE LEY') && (liq.regaliaMinera ?: 0.0G) > 0 &&
                (!filtraDesc || retencion == 'REGALIA MINERA')) {
                filas << (base + [tipo: 'DE LEY', descripcion: 'REGALIA MINERA', cantidad: 0.0G, unidad: '', monto: liq.regaliaMinera])
                tot.monto = (tot.monto ?: 0.0G) + (liq.regaliaMinera ?: 0.0G)
            }
            LiquidacionDeComplejoRetenciones.findAllByLiquidacionDeComplejo(liq).each { r ->
                if (tipo != 'Todas' && r.tipoDeRetencion != tipo) return
                if (filtraDesc && r.descripcion != retencion) return
                filas << (base + [tipo: r.tipoDeRetencion, descripcion: r.descripcion,
                                  cantidad: (r.cantidadDescuento ?: 0.0G), unidad: (r.unidadDeDescuento ?: ''), monto: (r.monto ?: 0.0G)])
                tot.monto = (tot.monto ?: 0.0G) + (r.monto ?: 0.0G)
            }
        }
        filas
    }

    /** Parsea las partes _day/_month/_year del datepickerUI a Date (fin=true → 23:59:59). */
    private Date fechaDe(String campo, boolean fin = false) {
        if (!params["${campo}_year"]) return null
        def base = "${params[campo + '_year']}-${params[campo + '_month']}-${params[campo + '_day']}"
        fin ? new java.text.SimpleDateFormat('yyyy-M-d HH:mm:ss').parse("$base 23:59:59")
            : new java.text.SimpleDateFormat('yyyy-M-d').parse(base)
    }

    def save() {
        def reporteRetencionesInstance = new ReporteRetenciones(params)
        if (!reporteRetencionesInstance.save(flush: true)) {
            render(view: "create", model: [reporteRetencionesInstance: reporteRetencionesInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reporteRetenciones.label', default: 'ReporteRetenciones'), reporteRetencionesInstance.id])
        redirect(action: "show", id: reporteRetencionesInstance.id)
    }

    def show(Long id) {
        def reporteRetencionesInstance = ReporteRetenciones.get(id)
        if (!reporteRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteRetenciones.label', default: 'ReporteRetenciones'), id])
            redirect(action: "list")
            return
        }

        [reporteRetencionesInstance: reporteRetencionesInstance]
    }

    def edit(Long id) {
        def reporteRetencionesInstance = ReporteRetenciones.get(id)
        if (!reporteRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteRetenciones.label', default: 'ReporteRetenciones'), id])
            redirect(action: "list")
            return
        }

        [reporteRetencionesInstance: reporteRetencionesInstance]
    }

    def update(Long id, Long version) {
        def reporteRetencionesInstance = ReporteRetenciones.get(id)
        if (!reporteRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteRetenciones.label', default: 'ReporteRetenciones'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reporteRetencionesInstance.version > version) {
                reporteRetencionesInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reporteRetenciones.label', default: 'ReporteRetenciones')] as Object[],
                        "Another user has updated this ReporteRetenciones while you were editing")
                render(view: "edit", model: [reporteRetencionesInstance: reporteRetencionesInstance])
                return
            }
        }

        reporteRetencionesInstance.properties = params

        if (!reporteRetencionesInstance.save(flush: true)) {
            render(view: "edit", model: [reporteRetencionesInstance: reporteRetencionesInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reporteRetenciones.label', default: 'ReporteRetenciones'), reporteRetencionesInstance.id])
        redirect(action: "show", id: reporteRetencionesInstance.id)
    }

    def delete(Long id) {
        def reporteRetencionesInstance = ReporteRetenciones.get(id)
        if (!reporteRetencionesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteRetenciones.label', default: 'ReporteRetenciones'), id])
            redirect(action: "list")
            return
        }

        try {
            reporteRetencionesInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reporteRetenciones.label', default: 'ReporteRetenciones'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reporteRetenciones.label', default: 'ReporteRetenciones'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporteComplejo = {
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.TAHOMA, 8, WritableFont.BOLD);
        WritableFont courier6PlainFont = new WritableFont(WritableFont.TAHOMA, 8, WritableFont.NO_BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.TAHOMA, 8, WritableFont.NO_BOLD);
        WritableFont courier8BoldFont = new WritableFont(WritableFont.TAHOMA, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.NO_BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.TAHOMA, 16, WritableFont.BOLD);

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

        DateFormat customDateFormat2 = new DateFormat ("dd/MM/yyyy hh:mm:ss");
        WritableCellFormat formatoFecha2 = new WritableCellFormat (customDateFormat2);
        formatoFecha2.setFont(courier6PlainFont)
        formatoFecha2.setAlignment(Alignment.RIGHT)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="reporte_retenciones_complejo.xls"')

        //VERIFICACION DEL TIPO DE REPORTE
        def tipoReporte = ""+params.tipoReporte
        def tipoRetencion = ""+params.tipoRetencion
        def ignorarLotes = ""+params.ignorarLotes
        def lotesIgnorados = ignorarLotes.tokenize(',')
        def liquidacionesComplejo = null

        def empresa
        def nombreEmpresa=""
        def fechaInicial=""
        def fechaFinal=""
        def fechaInicialFormateada=""
        def fechaFinalFormateada=""
        def loteInicial=""
        def loteFinal=""
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser

        if (tipoRetencion.equals("")) {
            flash.error = "No ha especificado el Tipo de Retencion"
            redirect(action: "create")
            return
        }

        if (params.empresa.id=="null") {
            flash.error = "No ha especificado la Empresa"
            redirect(action: "create")
            return
        }

        if (tipoReporte.equals("fechasEmpresa")){
            empresa = Empresa.get(params.empresa.id)
            nombreEmpresa = empresa.toString()
            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)
            fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)

            //def liquidaciones = LiquidacionDeComplejo.findAllByFechaDeLiquidacionBetweenAndNombreEmpresaLike(fechaInicial,fechaFinal,"%${empresa.nombreDeEmpresa}%")
            //def liquidaciones = LiquidacionDeComplejo.findAllByFechaDeLiquidacionBetween(fechaInicial,fechaFinal)
//            liquidacionesComplejo = LiquidacionDeComplejo.findAllByFechaDeLiquidacionBetweenAndEmpresaAndDeposito(fechaInicial,fechaFinal,empresa,usuarioActual.deposito,[sort:"lote"])
//            liquidacionesComplejo = LiquidacionDeComplejo.findAllByFechaDeLiquidacionBetweenAndEmpresa(fechaInicial,fechaFinal,empresa,[sort:"lote"])
            liquidacionesComplejo = LiquidacionDeComplejo.findAllByFechaDeLiquidacionBetweenAndEmpresa(fechaInicial,fechaFinal,empresa,[sort:"fechaDeLiquidacion"])
            System.out.println("LIQUIDACIONES RECUPERADAS: ${liquidacionesComplejo.size()}")

//            liquidacionesComplejo=new ArrayList<LiquidacionDeComplejo>()
//            liquidaciones.each {
//                if(!existeLote(it,lotesIgnorados) && it.recepcionDeComplejo.empresa.id==empresa.id)
//                    liquidacionesComplejo.add(it)
//            }
        }
        if (tipoReporte.equals("lotesEmpresa")){
            empresa = Empresa.get(params.empresa.id)
            nombreEmpresa = empresa.toString()
            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)
            //def liquidaciones1 = LiquidacionDeComplejo.findAllByNombreEmpresaLike("%${empresa.nombreDeEmpresa}%")
            def liquidaciones1 = LiquidacionDeComplejo.list()
            def liquidaciones2=new ArrayList<LiquidacionDeComplejo>()
            liquidaciones1.each {
                def lote = Integer.parseInt(it.lote)
                if (lote>=loteInicial&&lote<=loteFinal)
                    liquidaciones2.add(it)
            }
            liquidacionesComplejo=new ArrayList<LiquidacionDeComplejo>()
            liquidaciones2.each {
                if(!existeLote(it,lotesIgnorados) && it.recepcionDeComplejo.empresa.id==empresa.id)
                    liquidacionesComplejo.add(it)
            }
        }

        //liquidacionesComplejo = LiquidacionDeComplejo.list()
        if (!liquidacionesComplejo || liquidacionesComplejo.size()==0){
            flash.error = "No se obtuvo ningun resultado."
            redirect(action: "create")
            return
        }

        def listaRetenciones = retencionesComplejoJSON liquidacionesComplejo,tipoRetencion

        if (!listaRetenciones || listaRetenciones.size()==0){
            flash.error = "No se obtuvo ningun resultado."
            redirect(action: "create")
            return
        }

        listaRetenciones.reverseEach {
            System.out.println("*** GENERANDO HOJA: ${it}")

            crearHojaComplejo(liquidacionesComplejo,
                    tipoRetencion,
                    it.toString().replace('/','_'),
                    false,
                    nombreEmpresa,
                    fechaInicialFormateada,
                    fechaFinalFormateada,
                    loteInicial,
                    loteFinal,
                    workbook,
                    formatoTitulo,
                    formatoEncabezado,
                    formatoInfoReporte,
                    formatoDatos,
                    formatoTotales,
                    formatoFecha,
                    formatoFecha2)
        }

        if (tipoRetencion.equals("DE LEY"))
            crearHojaComplejo(liquidacionesComplejo,
                    tipoRetencion,
                    "REGALIA MINERA",
                    true,
                    nombreEmpresa,
                    fechaInicialFormateada,
                    fechaFinalFormateada,
                    loteInicial,
                    loteFinal,
                    workbook,
                    formatoTitulo,
                    formatoEncabezado,
                    formatoInfoReporte,
                    formatoDatos,
                    formatoTotales,
                    formatoFecha,
                    formatoFecha2)

        workbook.write();
        workbook.close();
    }

    def crearHojaComplejo(liquidacionesComplejo,tipo,retencion,esRegalia,nombreEmpresa,fechaInicialFormateada,fechaFinalFormateada,loteInicial,loteFinal,workbook,formatoTitulo,formatoEncabezado,formatoInfoReporte,formatoDatos,formatoTotales,formatoFecha,formatoFecha2){
        //INSTANCIACION DE HOJAS Y FORMATOS        
        WritableSheet sheet = workbook.createSheet("${retencion}", 0)
        SheetSettings settings = sheet.getSettings()
//        settings.setPassword("5727041")
//        settings.setProtected(true)
        settings.setOrientation(PageOrientation.LANDSCAPE)
        settings.setScaleFactor(69)
        settings.setPaperSize(PaperSize.LETTER)
        settings.setTopMargin(0.2)
        settings.setBottomMargin(0.4)
        settings.setLeftMargin(0.6)
        settings.setRightMargin(0.4)
        settings.setHeaderMargin(0)
        settings.setFooterMargin(0)
        for(i in 4..100)
            sheet.setColumnView(i,10)
        sheet.setColumnView(0,12)//ajustar ancho de columnas (columna,ancho)
        sheet.setColumnView(1,12)//ajustar ancho de columnas (columna,ancho)
        sheet.setColumnView(2,25)
        sheet.setColumnView(3,12)
        sheet.setColumnView(4,12)
        sheet.setColumnView(6,12)
        sheet.setColumnView(13,14)
        sheet.setColumnView(14,14)
        sheet.setColumnView(15,14)
//        sheet.setRowView(5,0)
//        sheet.setRowView(6,0)
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

//        if (tipo.equals("DE LEY"))
//            sheet.addCell(new Label(4,0, "RETENCION DE LEY",formatoTitulo))
//        else//Otra
//            sheet.addCell(new Label(4,0, "OTRAS RETENCION",formatoTitulo))
        sheet.addCell(new Label(6,0, "RETENCION ${retencion}",formatoTitulo))

        sheet.mergeCells(16,0,17,0)
        sheet.addCell(new DateTime(16,0, new Date(),formatoFecha2))

        sheet.addCell(new Label(0,2, "INSTITUCIÓN: ",formatoInfoReporte))
        sheet.addCell(new Label(2,2, "${nombreEmpresa}",formatoInfoReporte))
        sheet.addCell(new Label(0,3, "CUENTA: ",formatoInfoReporte))
        sheet.addCell(new Label(2,3, "${retencion}",formatoInfoReporte))

        if (fechaInicialFormateada.equals("")){
            sheet.addCell(new Label(0,4, "LOTES: ",formatoInfoReporte))
            sheet.addCell(new Label(2,4, "${loteInicial} AL ${loteFinal}",formatoInfoReporte))
        }else{
            sheet.addCell(new Label(0,4, "PERIODO: ",formatoInfoReporte))
            sheet.addCell(new Label(2,4, "${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))
        }

        sheet.addCell(new Label(0,6, "FECHA LIQ.",formatoEncabezado))
        sheet.addCell(new Label(1,6, "LOTE",formatoEncabezado))
        sheet.addCell(new Label(2,6, "NOMBRE",formatoEncabezado))
        sheet.addCell(new Label(3,6, "P. BRUTO Kg",formatoEncabezado))
        sheet.addCell(new Label(4,6, "K. N. H.",formatoEncabezado))
        sheet.addCell(new Label(5,6, "% H2O",formatoEncabezado))
        sheet.addCell(new Label(6,6, "K. N. S.",formatoEncabezado))
        sheet.addCell(new Label(7,6, "LEY %Zn",formatoEncabezado))
        sheet.addCell(new Label(8,6, "LEY %Pb",formatoEncabezado))
        sheet.addCell(new Label(9,6, "LEY DM Ag",formatoEncabezado))
        sheet.addCell(new Label(10,6, "K. F. Zn",formatoEncabezado))
        sheet.addCell(new Label(11,6, "K. F. Pb",formatoEncabezado))
        sheet.addCell(new Label(12,6, "K. F. Ag",formatoEncabezado))
        sheet.addCell(new Label(13,6, "VALOR OF. BRUTO",formatoEncabezado))
        sheet.addCell(new Label(14,6, "VALOR NETO Bs",formatoEncabezado))
        sheet.addCell(new Label(15,6, "${retencion} Bs",formatoEncabezado))

        //DESPLIEGUE DE DATOS DE LIQUIDACIONES        
        def fila = 7
        //variables acumuladoras
        def numeroRegistros=0
        def totalCantidadSacos=0
        def totalPesoBruto=0
        def totalKilosNetosHumedos=0
        def totalHumedad=0
        def totalKilosNetosSecos=0
        def totalPorcentajeZinc=0
        def totalPorcentajePlomo=0
        def totalPorcentajePlata=0
        def totalKilosFinosZinc=0
        def totalKilosFinosPlomo=0
        def totalKilosFinosPlata=0
        def totalValorOficialBruto=0
        def totalValorNeto=0
        def totalValorNetoBolivianos=0
        def totalRetencion=0

        liquidacionesComplejo.each {
            // obtener primero el valor de la retencion y si es mayor a cero, se continua el proceso
            def ret = 0
            if (esRegalia)
                ret = it.regaliaMinera
            else{
                def retencionLiquidacion = LiquidacionDeComplejoRetenciones.findByLiquidacionDeComplejoAndDescripcion(it,retencion)
                ret = (retencionLiquidacion)?retencionLiquidacion.monto:0
            }

            if (ret == 0) {
                return
            }

            numeroRegistros++
            totalCantidadSacos+=Float.parseFloat(it.cantidadDeSacos.toString())
            totalPesoBruto+=it.pesoBruto
            //totalKilosNetosHumedos=totalKilosNetosHumedos+(it.pesoBruto-it.pesoBruto*it.porcentajeMermaFinal?:0/100)
            totalKilosNetosHumedos=totalKilosNetosHumedos+(it.pesoBruto-it.pesoBruto*it.porcentajeMermaFinal/100)
            //it.pesoBruto-it.pesoBruto*it.porcentajeMermaFinal/100
            totalHumedad+=it.porcentajeHumedadFinal
            totalKilosNetosSecos+=it.kilosNetosSecos
            totalPorcentajeZinc+=it.porcentajeZincFinal
            totalPorcentajePlomo+=it.porcentajePlomoFinal
            totalPorcentajePlata+=it.porcentajePlataFinal
            totalKilosFinosZinc+=it.kilosFinosZinc
            totalKilosFinosPlomo+=it.kilosFinosPlomo
            totalKilosFinosPlata+=it.kilosFinosPlata
            totalValorOficialBruto+=it.valorOficialBruto
            totalValorNeto+=it.valorNetoMineral
            totalValorNetoBolivianos+=it.valorNetoMineralEnBolivianos

            sheet.addCell(new DateTime(0,fila, it.fechaDeLiquidacion,formatoFecha))
            sheet.addCell(new Label(1,fila, it.lote,formatoDatos))
            sheet.addCell(new Label(2,fila, it.nombreCliente,formatoDatos))
            sheet.addCell(new Number(3,fila, it.pesoBruto,formatoDatos))
            sheet.addCell(new Number(4,fila, it.pesoBruto-it.pesoBruto*it.porcentajeMermaFinal/100,formatoDatos))
            sheet.addCell(new Number(5,fila, it.porcentajeHumedadFinal,formatoDatos))
            sheet.addCell(new Number(6,fila, it.kilosNetosSecos,formatoDatos))
            sheet.addCell(new Number(7,fila, it.porcentajeZincFinal,formatoDatos))
            sheet.addCell(new Number(8,fila, it.porcentajePlomoFinal,formatoDatos))
            sheet.addCell(new Number(9,fila, it.porcentajePlataFinal,formatoDatos))
            sheet.addCell(new Number(10,fila, it.kilosFinosZinc,formatoDatos))
            sheet.addCell(new Number(11,fila, it.kilosFinosPlomo,formatoDatos))
            sheet.addCell(new Number(12,fila, it.kilosFinosPlata,formatoDatos))
            sheet.addCell(new Number(13,fila, it.valorOficialBruto,formatoDatos))
            sheet.addCell(new Number(14,fila, it.valorNetoMineralEnBolivianos,formatoDatos))

            totalRetencion+=ret
            sheet.addCell(new Number(15,fila, ret,formatoDatos))
            fila++
        }
        //IMPRESION DE TOTALES
        sheet.addCell(new Label(2,fila, "TOTALES",formatoEncabezado))
        sheet.addCell(new Number(3,fila, totalPesoBruto,formatoTotales))
        sheet.addCell(new Number(4,fila, totalKilosNetosHumedos,formatoTotales))
        sheet.addCell(new Number(5,fila, (totalKilosNetosSecos/totalKilosNetosHumedos*100-100)*-1,formatoTotales))
        sheet.addCell(new Number(6,fila, totalKilosNetosSecos,formatoTotales))
        sheet.addCell(new Number(7,fila, totalKilosFinosZinc/totalKilosNetosSecos*100,formatoTotales))
        sheet.addCell(new Number(8,fila, totalKilosFinosPlomo/totalKilosNetosSecos*100,formatoTotales))
        sheet.addCell(new Number(9,fila, totalKilosFinosPlata/totalKilosNetosSecos*10000,formatoTotales))
        sheet.addCell(new Number(10,fila, totalKilosFinosZinc,formatoTotales))
        sheet.addCell(new Number(11,fila, totalKilosFinosPlomo,formatoTotales))
        sheet.addCell(new Number(12,fila, totalKilosFinosPlata,formatoTotales))
        sheet.addCell(new Number(13,fila, totalValorOficialBruto,formatoTotales))
        sheet.addCell(new Number(14,fila, totalValorNetoBolivianos,formatoTotales))
        sheet.addCell(new Number(15,fila, totalRetencion,formatoTotales))
    }

    def crearHojaPlomoPlata(liquidacionesPlomoPlata,tipo,retencion,esRegalia,nombreEmpresa,fechaInicialFormateada,fechaFinalFormateada,loteInicial,loteFinal,workbook,formatoTitulo,formatoEncabezado,formatoInfoReporte,formatoDatos,formatoTotales,formatoFecha,formatoFecha2){
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableSheet sheet = workbook.createSheet("${retencion}", 0)
        SheetSettings settings = sheet.getSettings()
        settings.setPassword("5727041")
        settings.setProtected(true)
        settings.setOrientation(PageOrientation.LANDSCAPE)
        settings.setScaleFactor(69)
        settings.setPaperSize(PaperSize.LETTER)
        settings.setTopMargin(0.2)
        settings.setBottomMargin(0.4)
        settings.setLeftMargin(0.6)
        settings.setRightMargin(0.4)
        settings.setHeaderMargin(0)
        settings.setFooterMargin(0)
        sheet.setColumnView(0,9)//ajustar ancho de columnas (columna,ancho)
        sheet.setColumnView(1,7)//ajustar ancho de columnas (columna,ancho)
        sheet.setColumnView(2,25)
        sheet.setColumnView(3,6)
        sheet.setRowView(5,0)
        sheet.setRowView(6,0)
        for(i in 4..100)
            sheet.setColumnView(i,10)
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

//        if (tipo.equals("DE LEY"))
//            sheet.addCell(new Label(4,0, "RETENCION DE LEY",formatoTitulo))
//        else//Otra
//            sheet.addCell(new Label(4,0, "OTRAS RETENCION",formatoTitulo))
        sheet.addCell(new Label(6,0, "RETENCION ${retencion}",formatoTitulo))

        sheet.mergeCells(16,0,17,0)
        sheet.addCell(new DateTime(16,0, new Date(),formatoFecha2))

        sheet.addCell(new Label(0,2, "INSTITUCIÓN: ",formatoInfoReporte))
        sheet.addCell(new Label(2,2, "${nombreEmpresa}",formatoInfoReporte))
        sheet.addCell(new Label(0,3, "CUENTA: ",formatoInfoReporte))
        sheet.addCell(new Label(2,3, "${retencion}",formatoInfoReporte))

        if (fechaInicialFormateada.equals("")){
            sheet.addCell(new Label(0,4, "LOTES: ",formatoInfoReporte))
            sheet.addCell(new Label(2,4, "${loteInicial} AL ${loteFinal}",formatoInfoReporte))
        }else{
            sheet.addCell(new Label(0,4, "PERIODO: ",formatoInfoReporte))
            sheet.addCell(new Label(2,4, "${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))
        }

        sheet.addCell(new Label(0,8, "FECHA LIQ.",formatoEncabezado))
        sheet.addCell(new Label(1,8, "LOTE",formatoEncabezado))
        sheet.addCell(new Label(2,8, "NOMBRE",formatoEncabezado))
        sheet.addCell(new Label(3,8, "SAC",formatoEncabezado))
        sheet.addCell(new Label(4,8, "P. BRUTO Kg",formatoEncabezado))
        sheet.addCell(new Label(5,8, "K. N. H.",formatoEncabezado))
        sheet.addCell(new Label(6,8, "% H2O",formatoEncabezado))
        sheet.addCell(new Label(7,8, "K. N. S.",formatoEncabezado))
        sheet.addCell(new Label(8,8, "LEY %Zn",formatoEncabezado))
        sheet.addCell(new Label(9,8, "LEY %Pb",formatoEncabezado))
        sheet.addCell(new Label(10,8, "LEY DM Ag",formatoEncabezado))
        sheet.addCell(new Label(11,8, "K. F. Zn",formatoEncabezado))
        sheet.addCell(new Label(12,8, "K. F. Pb",formatoEncabezado))
        sheet.addCell(new Label(13,8, "K. F. Ag",formatoEncabezado))
        sheet.addCell(new Label(14,8, "VALOR OF. BRUTO",formatoEncabezado))
        sheet.addCell(new Label(15,8, "VALOR NETO \$us",formatoEncabezado))
        sheet.addCell(new Label(16,8, "VALOR NETO Bs",formatoEncabezado))
        sheet.addCell(new Label(17,8, "${retencion}",formatoEncabezado))

        //DESPLIEGUE DE DATOS DE LIQUIDACIONES
        def fila = 9
        //variables acumuladoras
        def numeroRegistros=0
        def totalCantidadSacos=0
        def totalPesoBruto=0
        def totalKilosNetosHumedos=0
        def totalHumedad=0
        def totalKilosNetosSecos=0
        def totalPorcentajeZinc=0
        def totalPorcentajePlomo=0
        def totalPorcentajePlata=0
        def totalKilosFinosZinc=0
        def totalKilosFinosPlomo=0
        def totalKilosFinosPlata=0
        def totalValorOficialBruto=0
        def totalValorNeto=0
        def totalValorNetoBolivianos=0
        def totalRetencion=0

        liquidacionesPlomoPlata.each {
            numeroRegistros++
            totalCantidadSacos+=Float.parseFloat(it.cantidadDeSacos.toString())
            totalPesoBruto+=it.pesoBruto
            totalKilosNetosHumedos+=it.kilosNetosHumedos
            totalHumedad+=it.humedad
            totalKilosNetosSecos+=it.kilosNetosSecos
            //totalPorcentajeZinc+=it.porcentajeZinc
            totalPorcentajePlomo+=it.porcentajePlomo
            totalPorcentajePlata+=it.porcentajePlata
            //totalKilosFinosZinc+=it.kilosFinosZinc
            totalKilosFinosPlomo+=it.kilosFinosPlomo
            totalKilosFinosPlata+=it.kilosFinosPlata
            totalValorOficialBruto+=it.valorOficialBruto
            totalValorNeto+=it.valorNetoMineral
            totalValorNetoBolivianos+=it.valorNetoMineralEnBolivianos

            sheet.addCell(new DateTime(0,fila, it.fechaDeLiquidacion,formatoFecha))
            sheet.addCell(new Label(1,fila, it.lote,formatoDatos))
            sheet.addCell(new Label(2,fila, it.nombreCliente,formatoDatos))
            sheet.addCell(new Number(3,fila, Float.parseFloat(it.cantidadDeSacos.toString()),formatoDatos))
            sheet.addCell(new Number(4,fila, it.pesoBruto,formatoDatos))
            sheet.addCell(new Number(5,fila, it.kilosNetosHumedos,formatoDatos))
            sheet.addCell(new Number(6,fila, it.humedad,formatoDatos))
            sheet.addCell(new Number(7,fila, it.kilosNetosSecos,formatoDatos))
            sheet.addCell(new Number(8,fila, 0,formatoDatos))
            sheet.addCell(new Number(9,fila, it.porcentajePlomo,formatoDatos))
            sheet.addCell(new Number(10,fila, it.porcentajePlata,formatoDatos))
            sheet.addCell(new Number(11,fila, 0,formatoDatos))
            sheet.addCell(new Number(12,fila, it.kilosFinosPlomo,formatoDatos))
            sheet.addCell(new Number(13,fila, it.kilosFinosPlata,formatoDatos))
            sheet.addCell(new Number(14,fila, it.valorOficialBruto,formatoDatos))
            sheet.addCell(new Number(15,fila, it.valorNetoMineral,formatoDatos))
            sheet.addCell(new Number(16,fila, it.valorNetoMineralEnBolivianos,formatoDatos))

            def ret = 0
            if (esRegalia)
                ret = it.regaliaMinera
            else{
                def retencionLiquidacion = LiquidacionDePlomoPlataRetenciones.findByLiquidacionDePlomoPlataAndDescripcion(it,retencion)
                ret = (retencionLiquidacion)?retencionLiquidacion.monto:0
            }
            totalRetencion+=ret
            sheet.addCell(new Number(17,fila, ret,formatoDatos))
            fila++
        }
        //IMPRESION DE TOTALES
        sheet.addCell(new Label(2,fila, "TOTALES",formatoEncabezado))
        sheet.addCell(new Number(3,fila, totalCantidadSacos,formatoTotales))
        sheet.addCell(new Number(4,fila, totalPesoBruto,formatoTotales))
        sheet.addCell(new Number(5,fila, totalKilosNetosHumedos,formatoTotales))
        sheet.addCell(new Number(6,fila, (totalKilosNetosSecos/totalKilosNetosHumedos*100-100)*-1,formatoTotales))
        sheet.addCell(new Number(7,fila, totalKilosNetosSecos,formatoTotales))
        sheet.addCell(new Number(8,fila, totalKilosFinosZinc/totalKilosNetosSecos*100,formatoTotales))
        sheet.addCell(new Number(9,fila, totalKilosFinosPlomo/totalKilosNetosSecos*100,formatoTotales))
        sheet.addCell(new Number(10,fila, totalKilosFinosPlata/totalKilosNetosSecos*10000,formatoTotales))
        sheet.addCell(new Number(11,fila, totalKilosFinosZinc,formatoTotales))
        sheet.addCell(new Number(12,fila, totalKilosFinosPlomo,formatoTotales))
        sheet.addCell(new Number(13,fila, totalKilosFinosPlata,formatoTotales))
        sheet.addCell(new Number(14,fila, totalValorOficialBruto,formatoTotales))
        sheet.addCell(new Number(15,fila, totalValorNeto,formatoTotales))
        sheet.addCell(new Number(16,fila, totalValorNetoBolivianos,formatoTotales))
        sheet.addCell(new Number(17,fila, totalRetencion,formatoTotales))

        sheet.removeColumn(11)
        sheet.removeColumn(8)
    }

    def crearHojaZincPlata(liquidacionesZincPlata,tipo,retencion,esRegalia,nombreEmpresa,fechaInicialFormateada,fechaFinalFormateada,loteInicial,loteFinal,workbook,formatoTitulo,formatoEncabezado,formatoInfoReporte,formatoDatos,formatoTotales,formatoFecha,formatoFecha2){
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableSheet sheet = workbook.createSheet("${retencion}", 0)
        SheetSettings settings = sheet.getSettings()
        settings.setPassword("5727041")
        settings.setProtected(true)
        settings.setOrientation(PageOrientation.LANDSCAPE)
        settings.setScaleFactor(69)
        settings.setPaperSize(PaperSize.LETTER)
        settings.setTopMargin(0.2)
        settings.setBottomMargin(0.4)
        settings.setLeftMargin(0.6)
        settings.setRightMargin(0.4)
        settings.setHeaderMargin(0)
        settings.setFooterMargin(0)
        sheet.setColumnView(0,9)//ajustar ancho de columnas (columna,ancho)
        sheet.setColumnView(1,7)//ajustar ancho de columnas (columna,ancho)
        sheet.setColumnView(2,25)
        sheet.setColumnView(3,6)
        sheet.setRowView(5,0)
        sheet.setRowView(6,0)
        for(i in 4..100)
            sheet.setColumnView(i,10)
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

//        if (tipo.equals("DE LEY"))
//            sheet.addCell(new Label(4,0, "RETENCION DE LEY",formatoTitulo))
//        else//Otra
//            sheet.addCell(new Label(4,0, "OTRAS RETENCION",formatoTitulo))
        sheet.addCell(new Label(6,0, "RETENCION ${retencion}",formatoTitulo))

        sheet.mergeCells(16,0,17,0)
        sheet.addCell(new DateTime(16,0, new Date(),formatoFecha2))

        sheet.addCell(new Label(0,2, "INSTITUCIÓN: ",formatoInfoReporte))
        sheet.addCell(new Label(2,2, "${nombreEmpresa}",formatoInfoReporte))
        sheet.addCell(new Label(0,3, "CUENTA: ",formatoInfoReporte))
        sheet.addCell(new Label(2,3, "${retencion}",formatoInfoReporte))

        if (fechaInicialFormateada.equals("")){
            sheet.addCell(new Label(0,4, "LOTES: ",formatoInfoReporte))
            sheet.addCell(new Label(2,4, "${loteInicial} AL ${loteFinal}",formatoInfoReporte))
        }else{
            sheet.addCell(new Label(0,4, "PERIODO: ",formatoInfoReporte))
            sheet.addCell(new Label(2,4, "${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))
        }

        sheet.addCell(new Label(0,8, "FECHA LIQ.",formatoEncabezado))
        sheet.addCell(new Label(1,8, "LOTE",formatoEncabezado))
        sheet.addCell(new Label(2,8, "NOMBRE",formatoEncabezado))
        sheet.addCell(new Label(3,8, "SAC",formatoEncabezado))
        sheet.addCell(new Label(4,8, "P. BRUTO Kg",formatoEncabezado))
        sheet.addCell(new Label(5,8, "K. N. H.",formatoEncabezado))
        sheet.addCell(new Label(6,8, "% H2O",formatoEncabezado))
        sheet.addCell(new Label(7,8, "K. N. S.",formatoEncabezado))
        sheet.addCell(new Label(8,8, "LEY %Zn",formatoEncabezado))
        sheet.addCell(new Label(9,8, "LEY %Pb",formatoEncabezado))
        sheet.addCell(new Label(10,8, "LEY DM Ag",formatoEncabezado))
        sheet.addCell(new Label(11,8, "K. F. Zn",formatoEncabezado))
        sheet.addCell(new Label(12,8, "K. F. Pb",formatoEncabezado))
        sheet.addCell(new Label(13,8, "K. F. Ag",formatoEncabezado))
        sheet.addCell(new Label(14,8, "VALOR OF. BRUTO",formatoEncabezado))
        sheet.addCell(new Label(15,8, "VALOR NETO \$us",formatoEncabezado))
        sheet.addCell(new Label(16,8, "VALOR NETO Bs",formatoEncabezado))
        sheet.addCell(new Label(17,8, "${retencion}",formatoEncabezado))

        //DESPLIEGUE DE DATOS DE LIQUIDACIONES
        def fila = 9
        //variables acumuladoras
        def numeroRegistros=0
        def totalCantidadSacos=0
        def totalPesoBruto=0
        def totalKilosNetosHumedos=0
        def totalHumedad=0
        def totalKilosNetosSecos=0
        def totalPorcentajeZinc=0
        def totalPorcentajePlomo=0
        def totalPorcentajePlata=0
        def totalKilosFinosZinc=0
        def totalKilosFinosPlomo=0
        def totalKilosFinosPlata=0
        def totalValorOficialBruto=0
        def totalValorNeto=0
        def totalValorNetoBolivianos=0
        def totalRetencion=0

        liquidacionesZincPlata.each {
            numeroRegistros++
            totalCantidadSacos+=Float.parseFloat(it.cantidadDeSacos.toString())
            totalPesoBruto+=it.pesoBruto
            totalKilosNetosHumedos+=it.kilosNetosHumedos
            totalHumedad+=it.humedad
            totalKilosNetosSecos+=it.kilosNetosSecos
            totalPorcentajeZinc+=it.porcentajeZinc
            //totalPorcentajePlomo+=it.porcentajePlomo
            totalPorcentajePlata+=it.porcentajePlata
            totalKilosFinosZinc+=it.kilosFinosZinc
            //totalKilosFinosPlomo+=it.kilosFinosPlomo
            totalKilosFinosPlata+=it.kilosFinosPlata
            totalValorOficialBruto+=it.valorOficialBruto
            totalValorNeto+=it.valorNetoMineral
            totalValorNetoBolivianos+=it.valorNetoMineralEnBolivianos

            sheet.addCell(new DateTime(0,fila, it.fechaDeLiquidacion,formatoFecha))
            sheet.addCell(new Label(1,fila, it.lote,formatoDatos))
            sheet.addCell(new Label(2,fila, it.nombreCliente,formatoDatos))
            sheet.addCell(new Number(3,fila, Float.parseFloat(it.cantidadDeSacos.toString()),formatoDatos))
            sheet.addCell(new Number(4,fila, it.pesoBruto,formatoDatos))
            sheet.addCell(new Number(5,fila, it.kilosNetosHumedos,formatoDatos))
            sheet.addCell(new Number(6,fila, it.humedad,formatoDatos))
            sheet.addCell(new Number(7,fila, it.kilosNetosSecos,formatoDatos))
            sheet.addCell(new Number(8,fila, it.porcentajeZinc,formatoDatos))
            sheet.addCell(new Number(9,fila, 0,formatoDatos))
            sheet.addCell(new Number(10,fila, it.porcentajePlata,formatoDatos))
            sheet.addCell(new Number(11,fila, it.kilosFinosZinc,formatoDatos))
            sheet.addCell(new Number(12,fila, 0,formatoDatos))
            sheet.addCell(new Number(13,fila, it.kilosFinosPlata,formatoDatos))
            sheet.addCell(new Number(14,fila, it.valorOficialBruto,formatoDatos))
            sheet.addCell(new Number(15,fila, it.valorNetoMineral,formatoDatos))
            sheet.addCell(new Number(16,fila, it.valorNetoMineralEnBolivianos,formatoDatos))

            def ret = 0
            if (esRegalia)
                ret = it.regaliaMinera
            else{
                def retencionLiquidacion = LiquidacionDeZincPlataRetenciones.findByLiquidacionDeZincPlataAndDescripcion(it,retencion)
                ret = (retencionLiquidacion)?retencionLiquidacion.monto:0
            }
            totalRetencion+=ret
            sheet.addCell(new Number(17,fila, ret,formatoDatos))
            fila++
        }
        //IMPRESION DE TOTALES
        sheet.addCell(new Label(2,fila, "TOTALES",formatoEncabezado))
        sheet.addCell(new Number(3,fila, totalCantidadSacos,formatoTotales))
        sheet.addCell(new Number(4,fila, totalPesoBruto,formatoTotales))
        sheet.addCell(new Number(5,fila, totalKilosNetosHumedos,formatoTotales))
        sheet.addCell(new Number(6,fila, (totalKilosNetosSecos/totalKilosNetosHumedos*100-100)*-1,formatoTotales))
        sheet.addCell(new Number(7,fila, totalKilosNetosSecos,formatoTotales))
        sheet.addCell(new Number(8,fila, totalKilosFinosZinc/totalKilosNetosSecos*100,formatoTotales))
        sheet.addCell(new Number(9,fila, totalKilosFinosPlomo/totalKilosNetosSecos*100,formatoTotales))
        sheet.addCell(new Number(10,fila, totalKilosFinosPlata/totalKilosNetosSecos*10000,formatoTotales))
        sheet.addCell(new Number(11,fila, totalKilosFinosZinc,formatoTotales))
        sheet.addCell(new Number(12,fila, totalKilosFinosPlomo,formatoTotales))
        sheet.addCell(new Number(13,fila, totalKilosFinosPlata,formatoTotales))
        sheet.addCell(new Number(14,fila, totalValorOficialBruto,formatoTotales))
        sheet.addCell(new Number(15,fila, totalValorNeto,formatoTotales))
        sheet.addCell(new Number(16,fila, totalValorNetoBolivianos,formatoTotales))
        sheet.addCell(new Number(17,fila, totalRetencion,formatoTotales))

        sheet.removeColumn(12)
        sheet.removeColumn(9)
    }

    def crearHojaCobrePlata(liquidacionesCobrePlata,tipo,retencion,esRegalia,nombreEmpresa,fechaInicialFormateada,fechaFinalFormateada,loteInicial,loteFinal,workbook,formatoTitulo,formatoEncabezado,formatoInfoReporte,formatoDatos,formatoTotales,formatoFecha,formatoFecha2){
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableSheet sheet = workbook.createSheet("${retencion}", 0)
        SheetSettings settings = sheet.getSettings()
        settings.setPassword("5727041")
        settings.setProtected(true)
        settings.setOrientation(PageOrientation.LANDSCAPE)
        settings.setScaleFactor(69)
        settings.setPaperSize(PaperSize.LETTER)
        settings.setTopMargin(0.2)
        settings.setBottomMargin(0.4)
        settings.setLeftMargin(0.6)
        settings.setRightMargin(0.4)
        settings.setHeaderMargin(0)
        settings.setFooterMargin(0)
        sheet.setColumnView(0,9)//ajustar ancho de columnas (columna,ancho)
        sheet.setColumnView(1,7)//ajustar ancho de columnas (columna,ancho)
        sheet.setColumnView(2,25)
        sheet.setColumnView(3,6)
        sheet.setRowView(5,0)
        sheet.setRowView(6,0)
        for(i in 4..100)
            sheet.setColumnView(i,10)
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

//        if (tipo.equals("DE LEY"))
//            sheet.addCell(new Label(4,0, "RETENCION DE LEY",formatoTitulo))
//        else//Otra
//            sheet.addCell(new Label(4,0, "OTRAS RETENCION",formatoTitulo))
        sheet.addCell(new Label(6,0, "RETENCION ${retencion}",formatoTitulo))

        sheet.mergeCells(16,0,17,0)
        sheet.addCell(new DateTime(16,0, new Date(),formatoFecha2))

        sheet.addCell(new Label(0,2, "INSTITUCIÓN: ",formatoInfoReporte))
        sheet.addCell(new Label(2,2, "${nombreEmpresa}",formatoInfoReporte))
        sheet.addCell(new Label(0,3, "CUENTA: ",formatoInfoReporte))
        sheet.addCell(new Label(2,3, "${retencion}",formatoInfoReporte))

        if (fechaInicialFormateada.equals("")){
            sheet.addCell(new Label(0,4, "LOTES: ",formatoInfoReporte))
            sheet.addCell(new Label(2,4, "${loteInicial} AL ${loteFinal}",formatoInfoReporte))
        }else{
            sheet.addCell(new Label(0,4, "PERIODO: ",formatoInfoReporte))
            sheet.addCell(new Label(2,4, "${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))
        }

        sheet.addCell(new Label(0,8, "FECHA LIQ.",formatoEncabezado))
        sheet.addCell(new Label(1,8, "LOTE",formatoEncabezado))
        sheet.addCell(new Label(2,8, "NOMBRE",formatoEncabezado))
        sheet.addCell(new Label(3,8, "SAC",formatoEncabezado))
        sheet.addCell(new Label(4,8, "P. BRUTO Kg",formatoEncabezado))
        sheet.addCell(new Label(5,8, "K. N. H.",formatoEncabezado))
        sheet.addCell(new Label(6,8, "% H2O",formatoEncabezado))
        sheet.addCell(new Label(7,8, "K. N. S.",formatoEncabezado))
        sheet.addCell(new Label(8,8, "LEY %Zn",formatoEncabezado))
        sheet.addCell(new Label(9,8, "LEY %Pb",formatoEncabezado))
        sheet.addCell(new Label(10,8, "LEY DM Ag",formatoEncabezado))
        sheet.addCell(new Label(11,8, "K. F. Zn",formatoEncabezado))
        sheet.addCell(new Label(12,8, "K. F. Pb",formatoEncabezado))
        sheet.addCell(new Label(13,8, "K. F. Ag",formatoEncabezado))
        sheet.addCell(new Label(14,8, "VALOR OF. BRUTO",formatoEncabezado))
        sheet.addCell(new Label(15,8, "VALOR NETO \$us",formatoEncabezado))
        sheet.addCell(new Label(16,8, "VALOR NETO Bs",formatoEncabezado))
        sheet.addCell(new Label(17,8, "${retencion}",formatoEncabezado))

        //DESPLIEGUE DE DATOS DE LIQUIDACIONES
        def fila = 9
        //variables acumuladoras
        def numeroRegistros=0
        def totalCantidadSacos=0
        def totalPesoBruto=0
        def totalKilosNetosHumedos=0
        def totalHumedad=0
        def totalKilosNetosSecos=0
        def totalPorcentajeCobre=0
        def totalPorcentajePlomo=0
        def totalPorcentajePlata=0
        def totalKilosFinosCobre=0
        def totalKilosFinosPlomo=0
        def totalKilosFinosPlata=0
        def totalValorOficialBruto=0
        def totalValorNeto=0
        def totalValorNetoBolivianos=0
        def totalRetencion=0

        liquidacionesCobrePlata.each {
            numeroRegistros++
            totalCantidadSacos+=Float.parseFloat(it.cantidadDeSacos.toString())
            totalPesoBruto+=it.pesoBruto
            totalKilosNetosHumedos+=it.kilosNetosHumedos
            totalHumedad+=it.humedad
            totalKilosNetosSecos+=it.kilosNetosSecos
            totalPorcentajeCobre+=it.porcentajeCobre
            //totalPorcentajePlomo+=it.porcentajePlomo
            totalPorcentajePlata+=it.porcentajePlata
            totalKilosFinosCobre+=it.kilosFinosCobre
            //totalKilosFinosPlomo+=it.kilosFinosPlomo
            totalKilosFinosPlata+=it.kilosFinosPlata
            totalValorOficialBruto+=it.valorOficialBruto
            totalValorNeto+=it.valorNetoMineral
            totalValorNetoBolivianos+=it.valorNetoMineralEnBolivianos

            sheet.addCell(new DateTime(0,fila, it.fechaDeLiquidacion,formatoFecha))
            sheet.addCell(new Label(1,fila, it.lote,formatoDatos))
            sheet.addCell(new Label(2,fila, it.nombreCliente,formatoDatos))
            sheet.addCell(new Number(3,fila, Float.parseFloat(it.cantidadDeSacos.toString()),formatoDatos))
            sheet.addCell(new Number(4,fila, it.pesoBruto,formatoDatos))
            sheet.addCell(new Number(5,fila, it.kilosNetosHumedos,formatoDatos))
            sheet.addCell(new Number(6,fila, it.humedad,formatoDatos))
            sheet.addCell(new Number(7,fila, it.kilosNetosSecos,formatoDatos))
            sheet.addCell(new Number(8,fila, it.porcentajeCobre,formatoDatos))
            sheet.addCell(new Number(9,fila, 0,formatoDatos))
            sheet.addCell(new Number(10,fila, it.porcentajePlata,formatoDatos))
            sheet.addCell(new Number(11,fila, it.kilosFinosCobre,formatoDatos))
            sheet.addCell(new Number(12,fila, 0,formatoDatos))
            sheet.addCell(new Number(13,fila, it.kilosFinosPlata,formatoDatos))
            sheet.addCell(new Number(14,fila, it.valorOficialBruto,formatoDatos))
            sheet.addCell(new Number(15,fila, it.valorNetoMineral,formatoDatos))
            sheet.addCell(new Number(16,fila, it.valorNetoMineralEnBolivianos,formatoDatos))

            def ret = 0
            if (esRegalia)
                ret = it.regaliaMinera
            else{
                def retencionLiquidacion = LiquidacionDeCobrePlataRetenciones.findByLiquidacionDeCobrePlataAndDescripcion(it,retencion)
                ret = (retencionLiquidacion)?retencionLiquidacion.monto:0
            }
            totalRetencion+=ret
            sheet.addCell(new Number(17,fila, ret,formatoDatos))
            fila++
        }
        //IMPRESION DE TOTALES
        sheet.addCell(new Label(2,fila, "TOTALES",formatoEncabezado))
        sheet.addCell(new Number(3,fila, totalCantidadSacos,formatoTotales))
        sheet.addCell(new Number(4,fila, totalPesoBruto,formatoTotales))
        sheet.addCell(new Number(5,fila, totalKilosNetosHumedos,formatoTotales))
        sheet.addCell(new Number(6,fila, (totalKilosNetosSecos/totalKilosNetosHumedos*100-100)*-1,formatoTotales))
        sheet.addCell(new Number(7,fila, totalKilosNetosSecos,formatoTotales))
        sheet.addCell(new Number(8,fila, totalKilosFinosCobre/totalKilosNetosSecos*100,formatoTotales))
        sheet.addCell(new Number(9,fila, totalKilosFinosPlomo/totalKilosNetosSecos*100,formatoTotales))
        sheet.addCell(new Number(10,fila, totalKilosFinosPlata/totalKilosNetosSecos*10000,formatoTotales))
        sheet.addCell(new Number(11,fila, totalKilosFinosCobre,formatoTotales))
        sheet.addCell(new Number(12,fila, totalKilosFinosPlomo,formatoTotales))
        sheet.addCell(new Number(13,fila, totalKilosFinosPlata,formatoTotales))
        sheet.addCell(new Number(14,fila, totalValorOficialBruto,formatoTotales))
        sheet.addCell(new Number(15,fila, totalValorNeto,formatoTotales))
        sheet.addCell(new Number(16,fila, totalValorNetoBolivianos,formatoTotales))
        sheet.addCell(new Number(17,fila, totalRetencion,formatoTotales))

        sheet.removeColumn(12)
        sheet.removeColumn(9)
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
