package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import jxl.SheetSettings
import jxl.Workbook
import jxl.format.Alignment
import jxl.format.PageOrientation
import jxl.format.PaperSize
import jxl.write.*
import org.socymet.liquidacion.*
import org.socymet.proveedor.Cliente
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA','ROLE_REPORTES'])
@Transactional
class ReporteLotesLiquidadosController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def reporteXlsxBuilderService   // genera el XLSX (Apache POI)

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reporteLotesLiquidadosInstanceList: ReporteLotesLiquidados.list(params), reporteLotesLiquidadosInstanceTotal: ReporteLotesLiquidados.count()]
    }

    // ── Reporte XLSX (Apache POI): filtros + vista previa + exportación ────────

    def create() {
        def empresa = params.empresaId ? Empresa.get(params.long('empresaId')) : null
        def cliente = params.clienteId ? Cliente.get(params.long('clienteId')) : null
        Date fi = fechaDe('fechaInicial')
        Date ff = fechaDe('fechaFinal', true)
        def filas = null
        def tot = [:].withDefault { 0.0G }
        def prom = [:]
        if (fi && ff) {
            filas = consultarLiquidaciones(empresa, cliente, fi, ff).collect { liq -> filaLiquidacion(liq, tot) }
            prom = promediosPonderados(tot)
        }
        [empresa: empresa, cliente: cliente, fechaInicial: fi ?: new Date(), fechaFinal: ff ?: new Date(),
         filas: filas, tot: tot, prom: prom]
    }

    def exportarExcel() {
        def empresa = params.empresaId ? Empresa.get(params.long('empresaId')) : null
        def cliente = params.clienteId ? Cliente.get(params.long('clienteId')) : null
        Date fi = params.fi ? new java.text.SimpleDateFormat('yyyy-MM-dd').parse(params.fi) : null
        Date ff = params.ff ? new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss').parse(params.ff + ' 23:59:59') : null
        if (!fi || !ff) { flash.message = "Seleccione un rango de fechas antes de exportar."; redirect(action: "create"); return }

        def fmt = new java.text.SimpleDateFormat('dd/MM/yyyy')
        def tot = [:].withDefault { 0.0G }
        def filasMapa = consultarLiquidaciones(empresa, cliente, fi, ff).collect { liq -> filaLiquidacion(liq, tot) }

        def columnas = [
            [titulo: 'Fec. Liq.',      ancho: 12, tipo: 'fecha'],
            [titulo: 'N° Liq.',        ancho: 10, tipo: 'texto'],
            [titulo: 'Empresa',        ancho: 26, tipo: 'texto'],
            [titulo: 'Cliente',        ancho: 26, tipo: 'texto'],
            [titulo: 'Lote',           ancho: 16, tipo: 'texto'],
            [titulo: 'Sacos',          ancho: 8,  tipo: 'numero', total: 'suma'],
            [titulo: 'P. Bruto [Kg]',  ancho: 12, tipo: 'numero', total: 'suma'],
            [titulo: 'K.N.S.',         ancho: 12, tipo: 'numero', total: 'suma'],
            [titulo: 'Ley %Zn',        ancho: 9,  tipo: 'numero'],
            [titulo: 'Ley %Pb',        ancho: 9,  tipo: 'numero'],
            [titulo: 'Ley %Ag',        ancho: 9,  tipo: 'numero'],
            [titulo: 'K.F. Zn',        ancho: 10, tipo: 'numero', total: 'suma'],
            [titulo: 'K.F. Pb',        ancho: 10, tipo: 'numero', total: 'suma'],
            [titulo: 'K.F. Ag',        ancho: 10, tipo: 'numero', total: 'suma'],
            [titulo: 'V. Neto \$us',   ancho: 13, tipo: 'numero', total: 'suma'],
            [titulo: 'V. Neto Bs',     ancho: 13, tipo: 'numero', total: 'suma'],
            [titulo: 'Ret. Ley',       ancho: 12, tipo: 'numero', total: 'suma'],
            [titulo: 'Otras Ret.',     ancho: 12, tipo: 'numero', total: 'suma'],
            [titulo: 'Ant./Ent.',      ancho: 12, tipo: 'numero', total: 'suma'],
            [titulo: 'Líq. Pagable',   ancho: 14, tipo: 'numero', total: 'sumaPositivos'],
        ]
        def claves = ['fecha','numero','empresa','cliente','lote','sacos','pesoBruto','kns','leyZn','leyPb','leyAg',
                      'kfZn','kfPb','kfAg','vNetoUsd','vNetoBs','retLey','otrasRet','antEnt','liquido']
        def filas = filasMapa.collect { m -> claves.collect { m[it] } }

        def prom = promediosPonderados(tot)
        def n2 = { v -> new BigDecimal(v ?: 0).setScale(2, java.math.RoundingMode.HALF_UP) }
        byte[] xlsx = reporteXlsxBuilderService.construir([
            nombreHoja: 'Lotes Liquidados',
            titulo: 'REPORTE DE LOTES LIQUIDADOS',
            subtitulos: [(empresa ? "Empresa: ${empresa}" : "Empresa: Todas"),
                         (cliente ? "Cliente: ${cliente.nombre}" : "Cliente: Todos"),
                         "Periodo: ${fmt.format(fi)} al ${fmt.format(ff)}"],
            columnas: columnas, filas: filas,
            // Fila de promedios ponderados: Humedad en la etiqueta; %Zn (8), %Pb (9), DM Ag (10) en sus columnas
            filasResumen: [[ etiqueta: "Promedios ponderados — Humedad: ${n2(prom.hum)} %",
                             etiquetaHasta: 4,
                             valores: [8: prom.zn, 9: prom.pb, 10: prom.ag] ]]
        ])
        response.setContentType('application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')
        response.setHeader('Content-Disposition', 'attachment; filename="reporte_lotes_liquidados.xlsx"')
        response.outputStream << xlsx
        response.outputStream.flush()
    }

    /** Liquidaciones de complejo (no anuladas) por rango de fecha de liquidación y, opc., empresa y cliente. */
    private List consultarLiquidaciones(empresa, cliente, Date fi, Date ff) {
        def lista = LiquidacionDeComplejo.createCriteria().list(sort: 'nombreEmpresa', order: 'asc') {
            between('fechaDeLiquidacion', fi, ff)
            if (empresa) eq('empresa', empresa)
            if (cliente) eq('cliente', cliente)
        }
        lista.findAll { !it.anulado }
    }

    /** Mapa de una fila + acumula totales. Ret. Ley = regalía + retenciones 'DE LEY'; Otras = 'OTRA'. */
    private Map filaLiquidacion(liq, Map tot) {
        def retLey = (liq.regaliaMinera ?: 0.0G) +
            (LiquidacionDeComplejoRetenciones.findAllByLiquidacionDeComplejoAndTipoDeRetencion(liq, 'DE LEY')*.monto.sum() ?: 0.0G)
        def otrasRet = (LiquidacionDeComplejoRetenciones.findAllByLiquidacionDeComplejoAndTipoDeRetencion(liq, 'OTRA')*.monto.sum() ?: 0.0G)
        def anio = liq.gestionMinera ? new java.text.SimpleDateFormat('yy').format(liq.gestionMinera) : ''
        def m = [
            fecha: liq.fechaDeLiquidacion, numero: "${liq.numeroLiquidacionComplejo}/${anio}".toString(),
            empresa: liq.nombreEmpresa, cliente: liq.nombreCliente, lote: liq.lote,
            sacos: (liq.cantidadSacos ?: 0), pesoBruto: (liq.pesoBruto ?: 0.0G), kns: (liq.kilosNetosSecos ?: 0.0G),
            leyZn: (liq.porcentajeZincFinal ?: 0.0G), leyPb: (liq.porcentajePlomoFinal ?: 0.0G), leyAg: (liq.porcentajePlataFinal ?: 0.0G),
            kfZn: (liq.kilosFinosZinc ?: 0.0G), kfPb: (liq.kilosFinosPlomo ?: 0.0G), kfAg: (liq.kilosFinosPlata ?: 0.0G),
            vNetoUsd: (liq.valorNetoMineral ?: 0.0G), vNetoBs: (liq.valorNetoMineralEnBolivianos ?: 0.0G),
            retLey: retLey, otrasRet: otrasRet, antEnt: (liq.totalAnticiposContraEntrega ?: 0.0G), liquido: (liq.totalLiquidoPagable ?: 0.0G)
        ]
        ['sacos','pesoBruto','kns','kfZn','kfPb','kfAg','vNetoUsd','vNetoBs','retLey','otrasRet','antEnt'].each {
            tot[it] = (tot[it] ?: 0.0G) + (m[it] ?: 0.0G)
        }
        // Líquido pagable: el total NO considera liquidaciones con líquido < 0
        if ((m.liquido ?: 0.0G) >= 0) tot.liquido = (tot.liquido ?: 0.0G) + m.liquido
        // Peso Neto Húmedo = pesoBruto·(1−merma/100); se acumula (no se muestra como columna) para %Humedad promedio
        def merma = liq.porcentajeMermaFinal ?: 0.0G
        tot.knh = (tot.knh ?: 0.0G) + ((liq.pesoBruto ?: 0.0G) * (1.0G - merma / 100.0G))
        m
    }

    /**
     * Promedios PONDERADOS por peso (devuelve mapa con hum, zn, pb, ag):
     *   %Humedad = (ΣPNH−ΣPNS)/ΣPNH·100 ; %Zn = ΣKFZn/ΣPNS·100 ; %Pb = ΣKFPb/ΣPNS·100 ; DM Ag = ΣKFAg/ΣPNS·10000.
     * (Los kilos finos se guardan como pns·ley/100 (Zn,Pb) y pns·ley/10000 (Ag), de ahí los factores.)
     */
    private Map promediosPonderados(Map tot) {
        def pns = tot.kns ?: 0.0G
        def pnh = tot.knh ?: 0.0G
        [ hum: pnh ? (pnh - pns) / pnh * 100.0G : 0.0G,
          zn:  pns ? (tot.kfZn ?: 0.0G) / pns * 100.0G : 0.0G,
          pb:  pns ? (tot.kfPb ?: 0.0G) / pns * 100.0G : 0.0G,
          ag:  pns ? (tot.kfAg ?: 0.0G) / pns * 10000.0G : 0.0G ]
    }

    /** Parsea las partes _day/_month/_year del datepickerUI a Date (fin=true → 23:59:59). */
    private Date fechaDe(String campo, boolean fin = false) {
        if (!params["${campo}_year"]) return null
        def base = "${params[campo + '_year']}-${params[campo + '_month']}-${params[campo + '_day']}"
        fin ? new java.text.SimpleDateFormat('yyyy-M-d HH:mm:ss').parse("$base 23:59:59")
            : new java.text.SimpleDateFormat('yyyy-M-d').parse(base)
    }

    def save() {
        def reporteLotesLiquidadosInstance = new ReporteLotesLiquidados(params)
        if (!reporteLotesLiquidadosInstance.save(flush: true)) {
            render(view: "create", model: [reporteLotesLiquidadosInstance: reporteLotesLiquidadosInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reporteLotesLiquidados.label', default: 'ReporteLotesLiquidados'), reporteLotesLiquidadosInstance.id])
        redirect(action: "show", id: reporteLotesLiquidadosInstance.id)
    }

    def show(Long id) {
        def reporteLotesLiquidadosInstance = ReporteLotesLiquidados.get(id)
        if (!reporteLotesLiquidadosInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteLotesLiquidados.label', default: 'ReporteLotesLiquidados'), id])
            redirect(action: "list")
            return
        }

        [reporteLotesLiquidadosInstance: reporteLotesLiquidadosInstance]
    }

    def edit(Long id) {
        def reporteLotesLiquidadosInstance = ReporteLotesLiquidados.get(id)
        if (!reporteLotesLiquidadosInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteLotesLiquidados.label', default: 'ReporteLotesLiquidados'), id])
            redirect(action: "list")
            return
        }

        [reporteLotesLiquidadosInstance: reporteLotesLiquidadosInstance]
    }

    def update(Long id, Long version) {
        def reporteLotesLiquidadosInstance = ReporteLotesLiquidados.get(id)
        if (!reporteLotesLiquidadosInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteLotesLiquidados.label', default: 'ReporteLotesLiquidados'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reporteLotesLiquidadosInstance.version > version) {
                reporteLotesLiquidadosInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reporteLotesLiquidados.label', default: 'ReporteLotesLiquidados')] as Object[],
                        "Another user has updated this ReporteLotesLiquidados while you were editing")
                render(view: "edit", model: [reporteLotesLiquidadosInstance: reporteLotesLiquidadosInstance])
                return
            }
        }

        reporteLotesLiquidadosInstance.properties = params

        if (!reporteLotesLiquidadosInstance.save(flush: true)) {
            render(view: "edit", model: [reporteLotesLiquidadosInstance: reporteLotesLiquidadosInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reporteLotesLiquidados.label', default: 'ReporteLotesLiquidados'), reporteLotesLiquidadosInstance.id])
        redirect(action: "show", id: reporteLotesLiquidadosInstance.id)
    }

    def delete(Long id) {
        def reporteLotesLiquidadosInstance = ReporteLotesLiquidados.get(id)
        if (!reporteLotesLiquidadosInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteLotesLiquidados.label', default: 'ReporteLotesLiquidados'), id])
            redirect(action: "list")
            return
        }

        try {
            reporteLotesLiquidadosInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reporteLotesLiquidados.label', default: 'ReporteLotesLiquidados'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reporteLotesLiquidados.label', default: 'ReporteLotesLiquidados'), id])
            redirect(action: "show", id: id)
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
        sheet1.setRowView(5,500)
        for(i in 0..100)
            sheet1.setColumnView(i,10)
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
        response.setHeader('Content-Disposition', 'Attachment;Filename="reporte_lotes_liquidados.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(0,0, "REPORTE DE LOTES LIQUIDADOS",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def tipoReporte = ""+params.tipoReporte
        def empresa=null
        def fechaInicial=null
        def fechaFinal=null

        def liquidacionesComplejo=null

        def retencionesDeLey=new ArrayList<String>()
        def retencionesOtras=new ArrayList<String>()

        if (tipoReporte.equals("fechas")){
            //empresa = Empresa.get(Integer.parseInt(""+params.empresa.id))
            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(0,3, "ENTRE FECHAS:",formatoInfoReporte))
            sheet1.addCell(new Label(2,3, "${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))
            liquidacionesComplejo = LiquidacionDeComplejo.findAllByFechaDeLiquidacionBetween(fechaInicial,fechaFinal,[sort: 'nombreEmpresa'])
            System.out.println("*** RESULTADOS DE COMPLEJO: ${liquidacionesComplejo.size()}")
        }
        if (tipoReporte.equals("fechasEmpresa")){
            empresa = Empresa.get(Integer.parseInt(""+params.empresa.id))

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)
            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(0,2, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(2,2, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(0,3, "ENTRE FECHAS:",formatoInfoReporte))
            sheet1.addCell(new Label(2,3, "${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))
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

        sheet1.addCell(new Label(0,5, "FEC. LIQ.",formatoEncabezado))
        sheet1.addCell(new Label(1,5, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(2,5, "NOMBRE",formatoEncabezado))
        sheet1.addCell(new Label(3,5, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(4,5, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(5,5, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(6,5, "MERMA",formatoEncabezado))
        sheet1.addCell(new Label(7,5, "K. N. H.",formatoEncabezado))
        sheet1.addCell(new Label(8,5, "% H2O",formatoEncabezado))
        sheet1.addCell(new Label(9,5, "K. N. S.",formatoEncabezado))
        sheet1.addCell(new Label(10,5, "LEY %Zn",formatoEncabezado))
        sheet1.addCell(new Label(11,5, "LEY %Pb",formatoEncabezado))
        sheet1.addCell(new Label(12,5, "LEY %Ag",formatoEncabezado))
        sheet1.addCell(new Label(13,5, "K. F. Zn",formatoEncabezado))
        sheet1.addCell(new Label(14,5, "K. F. Pb",formatoEncabezado))
        sheet1.addCell(new Label(15,5, "K. F. Ag",formatoEncabezado))
        sheet1.addCell(new Label(16,5, "VALOR NETO \$us",formatoEncabezado))
        sheet1.addCell(new Label(17,5, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(18,5, "RET. LEY",formatoEncabezado))
        sheet1.addCell(new Label(19,5, "OTRAS RET.",formatoEncabezado))
        sheet1.addCell(new Label(20,5, "ANT./ENT.",formatoEncabezado))
        sheet1.addCell(new Label(21,5, "LIQ. PAGAB.",formatoEncabezado))

        def fila = 6

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
            def p0=6
            def p1=6
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
