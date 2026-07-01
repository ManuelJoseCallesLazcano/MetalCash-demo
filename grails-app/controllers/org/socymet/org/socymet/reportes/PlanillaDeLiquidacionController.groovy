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

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA','ROLE_REPORTES'])
@Transactional
class PlanillaDeLiquidacionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def reporteXlsxBuilderService   // genera el XLSX (Apache POI)

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [planillaDeLiquidacionInstanceList: PlanillaDeLiquidacion.list(params), planillaDeLiquidacionInstanceTotal: PlanillaDeLiquidacion.count()]
    }

    // ── Reporte XLSX (Apache POI): filtros + vista previa + exportación ────────

    def create() {
        // La planilla es SIEMPRE por empresa específica (obligatoria). Filtra por FECHA DE RECEPCIÓN.
        def empresa = params.empresaId ? Empresa.get(params.long('empresaId')) : null
        Date fi = fechaDe('fechaInicial')
        Date ff = fechaDe('fechaFinal', true)
        def columnas = null, filas = null
        def tot = [:].withDefault { 0.0G }
        def prom = [:]
        if (empresa && fi && ff) {
            def liqs = consultarLiquidaciones(empresa, fi, ff)
            def retDescs = descripcionesEnRango(liqs)
            columnas = construirColumnas(retDescs)
            filas = liqs.collect { filaPlanilla(it, retDescs, tot) }
            prom = promediosPonderados(tot)
        }
        [empresa: empresa, fechaInicial: fi ?: new Date(), fechaFinal: ff ?: new Date(),
         columnas: columnas, filas: filas, tot: tot, prom: prom]
    }

    def exportarExcel() {
        def empresa = params.empresaId ? Empresa.get(params.long('empresaId')) : null
        Date fi = params.fi ? new java.text.SimpleDateFormat('yyyy-MM-dd').parse(params.fi) : null
        Date ff = params.ff ? new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss').parse(params.ff + ' 23:59:59') : null
        if (!empresa) { flash.message = "Seleccione una empresa."; redirect(action: "create"); return }
        if (!fi || !ff) { flash.message = "Seleccione un rango de fechas antes de exportar."; redirect(action: "create"); return }

        def fmt = new java.text.SimpleDateFormat('dd/MM/yyyy')
        def tot = [:].withDefault { 0.0G }
        def liqs = consultarLiquidaciones(empresa, fi, ff)
        def retDescs = descripcionesEnRango(liqs)
        def columnas = construirColumnas(retDescs)
        def filasMapa = liqs.collect { filaPlanilla(it, retDescs, tot) }
        def filas = filasMapa.collect { m -> columnas.collect { m[it.clave] } }
        def prom = promediosPonderados(tot)

        byte[] xlsx = reporteXlsxBuilderService.construir([
            nombreHoja: 'Planilla de Liquidación',
            titulo: "PLANILLA DE LIQUIDACIÓN ${empresa.nombreDeEmpresa}".toString(),
            subtitulos: [
                "EMPRESA: ${empresa.nombreDeEmpresa}",
                "DEPARTAMENTO: ${empresa.departamento ?: ''}",
                "PROVINCIA: ${empresa.provincia ?: ''}",
                "MUNICIPIO: ${empresa.codigoMunicipio ?: ''} ${empresa.municipio ?: ''}",
                "PERIODO: ${fmt.format(fi)} AL ${fmt.format(ff)}"
            ],
            columnas: columnas, filas: filas,
            // Promedios ponderados: %H2O (col 4), %Zn (6), %Pb (7), DM Ag (8) — índices fijos previos a las retenciones
            filasResumen: [[ etiqueta: 'PROMEDIOS PONDERADOS', etiquetaHasta: 3,
                             valores: [4: prom.hum, 6: prom.zn, 7: prom.pb, 8: prom.ag] ]]
        ])
        response.setContentType('application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')
        response.setHeader('Content-Disposition', 'attachment; filename="planilla_liquidacion.xlsx"')
        response.outputStream << xlsx
        response.outputStream.flush()
    }

    /** Columnas según planilla_liquidacion_modelo.xls. Las retenciones son columnas DINÁMICAS
     *  (una por descripción retenida en el rango) entre Regalía y Total Deducciones. clave = campo del mapa. */
    private List construirColumnas(List retDescs) {
        def cols = [
            [titulo: 'Fecha Rec.',   ancho: 12, tipo: 'fecha',  clave: 'fecha'],
            [titulo: 'Nombre',      ancho: 26, tipo: 'texto',  clave: 'nombre'],
            [titulo: 'Lote',        ancho: 15, tipo: 'texto',  clave: 'lote'],
            [titulo: 'P. Bruto Kg', ancho: 12, tipo: 'numero', total: 'suma', clave: 'pesoBruto'],
            [titulo: '% H2O',       ancho: 8,  tipo: 'numero', clave: 'h2o'],
            [titulo: 'K. N. S.',    ancho: 12, tipo: 'numero', total: 'suma', clave: 'kns'],
            [titulo: 'Ley %Zn',     ancho: 9,  tipo: 'numero', clave: 'leyZn'],
            [titulo: 'Ley %Pb',     ancho: 9,  tipo: 'numero', clave: 'leyPb'],
            [titulo: 'Ley DM Ag',   ancho: 9,  tipo: 'numero', clave: 'leyAg'],
            [titulo: 'K. F. Zn',    ancho: 10, tipo: 'numero', total: 'suma', clave: 'kfZn'],
            [titulo: 'K. F. Pb',    ancho: 10, tipo: 'numero', total: 'suma', clave: 'kfPb'],
            [titulo: 'K. F. Ag',    ancho: 10, tipo: 'numero', total: 'suma', clave: 'kfAg'],
            [titulo: 'Valor Bruto de Venta Bs', ancho: 15, tipo: 'numero', total: 'suma', clave: 'valorBrutoBs'],
            [titulo: 'Valor Neto de Venta Bs',  ancho: 15, tipo: 'numero', total: 'suma', clave: 'valorNetoBs'],
            [titulo: 'Regalía Minera Bs', ancho: 14, tipo: 'numero', total: 'suma', clave: 'regalia'],
        ]
        retDescs.eachWithIndex { d, i -> cols << [titulo: d, ancho: 13, tipo: 'numero', total: 'suma', clave: "ret$i".toString()] }
        cols << [titulo: 'Total Deducciones Bs', ancho: 15, tipo: 'numero', total: 'suma', clave: 'totalDeducciones']
        cols += [
            [titulo: 'Valor Pagable del Mineral Bs', ancho: 16, tipo: 'numero', total: 'suma', clave: 'valorPagable'],
            [titulo: 'Bono Calidad Bs',    ancho: 12, tipo: 'numero', total: 'suma', clave: 'bonoCalidad'],
            [titulo: 'Bono Transporte Bs', ancho: 13, tipo: 'numero', total: 'suma', clave: 'bonoTransporte'],
            [titulo: 'Bono Lealtad Bs',    ancho: 12, tipo: 'numero', total: 'suma', clave: 'bonoLealtad'],
            [titulo: 'Total Bonos Bs',     ancho: 12, tipo: 'numero', total: 'suma', clave: 'totalBonos'],
            [titulo: 'Anticipo Contra Entrega Bs',   ancho: 16, tipo: 'numero', total: 'suma', clave: 'antEntrega'],
            [titulo: 'Anticipo C/Futura Entrega Bs', ancho: 17, tipo: 'numero', total: 'suma', clave: 'antFutura'],
            [titulo: 'Total Anticipos Bs', ancho: 14, tipo: 'numero', total: 'suma', clave: 'totalAnticipos'],
            [titulo: 'Líquido Pagable Bs', ancho: 15, tipo: 'numero', total: 'sumaPositivos', clave: 'liquido'],
        ]
        cols
    }

    /** Descripciones distintas de retención en el rango (DE LEY primero, luego OTRAS). Regalía va aparte. */
    private List descripcionesEnRango(List liqs) {
        def ley = new TreeSet(), otra = new TreeSet()
        liqs.each { liq ->
            LiquidacionDeComplejoRetenciones.findAllByLiquidacionDeComplejo(liq).each { r ->
                if (!r.descripcion) return
                if (r.tipoDeRetencion == 'DE LEY') ley << r.descripcion else otra << r.descripcion
            }
        }
        (ley as List) + (otra as List)
    }

    /** Liquidaciones de complejo (no anuladas) de la empresa, por FECHA DE RECEPCIÓN en el rango. */
    private List consultarLiquidaciones(empresa, Date fi, Date ff) {
        LiquidacionDeComplejo.createCriteria().list {
            eq('empresa', empresa)
            recepcionDeComplejo { between('fechaDeRecepcion', fi, ff) }
        }.findAll { !it.anulado }.sort { it.recepcionDeComplejo?.fechaDeRecepcion }
    }

    /** Mapa de una fila + acumula totales. Cada retención va a su columna (0 si se quitó en esa liquidación). */
    private Map filaPlanilla(liq, List retDescs, Map tot) {
        def rec = liq.recepcionDeComplejo
        def montoMap = [:]
        LiquidacionDeComplejoRetenciones.findAllByLiquidacionDeComplejo(liq).each { r ->
            if (r.descripcion) montoMap[r.descripcion] = (montoMap[r.descripcion] ?: 0.0G) + (r.monto ?: 0.0G)
        }
        def m = [
            fecha: rec?.fechaDeRecepcion, nombre: liq.nombreCliente, lote: liq.lote,
            pesoBruto: (liq.pesoBruto ?: 0.0G), h2o: (liq.porcentajeHumedadFinal ?: 0.0G), kns: (liq.kilosNetosSecos ?: 0.0G),
            leyZn: (liq.porcentajeZincFinal ?: 0.0G), leyPb: (liq.porcentajePlomoFinal ?: 0.0G), leyAg: (liq.porcentajePlataFinal ?: 0.0G),
            kfZn: (liq.kilosFinosZinc ?: 0.0G), kfPb: (liq.kilosFinosPlomo ?: 0.0G), kfAg: (liq.kilosFinosPlata ?: 0.0G),
            valorBrutoBs: (liq.valorOficialBrutoEnBolivianos ?: 0.0G), valorNetoBs: (liq.valorNetoMineralEnBolivianos ?: 0.0G),
            regalia: (liq.regaliaMinera ?: 0.0G), valorPagable: (liq.valorPagableMineral ?: 0.0G),
            bonoCalidad: (liq.bonoCalidad ?: 0.0G), bonoTransporte: (liq.bonoTransporte ?: 0.0G), bonoLealtad: (liq.bonoLealtad ?: 0.0G),
            totalBonos: (liq.totalBonos ?: 0.0G), antEntrega: (liq.totalAnticiposContraEntrega ?: 0.0G),
            antFutura: (liq.totalAnticiposContraFuturaEntrega ?: 0.0G), totalAnticipos: (liq.totalAnticipos ?: 0.0G),
            liquido: (liq.totalLiquidoPagable ?: 0.0G)
        ]
        def sumaRet = 0.0G
        retDescs.eachWithIndex { d, i -> def v = (montoMap[d] ?: 0.0G); m["ret$i".toString()] = v; sumaRet += v }
        m.totalDeducciones = (liq.regaliaMinera ?: 0.0G) + sumaRet

        // ΣKNH y ΣKNS (internos) para los promedios ponderados de humedad/leyes
        tot.knh = (tot.knh ?: 0.0G) + (liq.kilosNetosHumedos ?: 0.0G)
        def sumaKeys = ['pesoBruto','kns','kfZn','kfPb','kfAg','valorBrutoBs','valorNetoBs','regalia','totalDeducciones',
                        'valorPagable','bonoCalidad','bonoTransporte','bonoLealtad','totalBonos','antEntrega','antFutura','totalAnticipos']
        sumaKeys += (0..<retDescs.size()).collect { "ret$it".toString() }
        sumaKeys.each { tot[it] = (tot[it] ?: 0.0G) + (m[it] ?: 0.0G) }
        // Líquido pagable: el total NO considera liquidaciones con líquido < 0
        if ((m.liquido ?: 0.0G) >= 0) tot.liquido = (tot.liquido ?: 0.0G) + m.liquido
        m
    }

    /** Promedios ponderados: %Hum, %Zn, %Pb (×100) y DM Ag (×10000). */
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
        def planillaDeLiquidacionInstance = new PlanillaDeLiquidacion(params)
        if (!planillaDeLiquidacionInstance.save(flush: true)) {
            render(view: "create", model: [planillaDeLiquidacionInstance: planillaDeLiquidacionInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'planillaDeLiquidacion.label', default: 'PlanillaDeLiquidacion'), planillaDeLiquidacionInstance.id])
        redirect(action: "show", id: planillaDeLiquidacionInstance.id)
    }

    def show(Long id) {
        def planillaDeLiquidacionInstance = PlanillaDeLiquidacion.get(id)
        if (!planillaDeLiquidacionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'planillaDeLiquidacion.label', default: 'PlanillaDeLiquidacion'), id])
            redirect(action: "list")
            return
        }

        [planillaDeLiquidacionInstance: planillaDeLiquidacionInstance]
    }

    def edit(Long id) {
        def planillaDeLiquidacionInstance = PlanillaDeLiquidacion.get(id)
        if (!planillaDeLiquidacionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'planillaDeLiquidacion.label', default: 'PlanillaDeLiquidacion'), id])
            redirect(action: "list")
            return
        }

        [planillaDeLiquidacionInstance: planillaDeLiquidacionInstance]
    }

    def update(Long id, Long version) {
        def planillaDeLiquidacionInstance = PlanillaDeLiquidacion.get(id)
        if (!planillaDeLiquidacionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'planillaDeLiquidacion.label', default: 'PlanillaDeLiquidacion'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (planillaDeLiquidacionInstance.version > version) {
                planillaDeLiquidacionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'planillaDeLiquidacion.label', default: 'PlanillaDeLiquidacion')] as Object[],
                        "Another user has updated this PlanillaDeLiquidacion while you were editing")
                render(view: "edit", model: [planillaDeLiquidacionInstance: planillaDeLiquidacionInstance])
                return
            }
        }

        planillaDeLiquidacionInstance.properties = params

        if (!planillaDeLiquidacionInstance.save(flush: true)) {
            render(view: "edit", model: [planillaDeLiquidacionInstance: planillaDeLiquidacionInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'planillaDeLiquidacion.label', default: 'PlanillaDeLiquidacion'), planillaDeLiquidacionInstance.id])
        redirect(action: "show", id: planillaDeLiquidacionInstance.id)
    }

    def delete(Long id) {
        def planillaDeLiquidacionInstance = PlanillaDeLiquidacion.get(id)
        if (!planillaDeLiquidacionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'planillaDeLiquidacion.label', default: 'PlanillaDeLiquidacion'), id])
            redirect(action: "list")
            return
        }

        try {
            planillaDeLiquidacionInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'planillaDeLiquidacion.label', default: 'PlanillaDeLiquidacion'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'planillaDeLiquidacion.label', default: 'PlanillaDeLiquidacion'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporte = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.TAHOMA, 7, WritableFont.NO_BOLD);
        WritableFont courier6PlainFont = new WritableFont(WritableFont.TAHOMA, 7, WritableFont.NO_BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.TAHOMA, 7, WritableFont.NO_BOLD);
        WritableFont courier8BoldFont = new WritableFont(WritableFont.TAHOMA, 7, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.TAHOMA, 12, WritableFont.BOLD);

        WritableCellFormat formatoEncabezado = new WritableCellFormat (arial10BoldFont);
        formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)
        formatoEncabezado.setWrap(true)
        //formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
        formatoEncabezado.setAlignment(Alignment.CENTRE)

        WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoDatos.setFont(courier8PlainFont)
        formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        WritableCellFormat formatoDatosHumedad = new WritableCellFormat (new NumberFormat("###,##0.000000"));
        formatoDatosHumedad.setFont(courier8PlainFont)
        formatoDatosHumedad.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        WritableCellFormat formatoTotales = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoTotales.setFont(courier8BoldFont)
        formatoTotales.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)

        WritableCellFormat formatoTotalesHumedad = new WritableCellFormat (new NumberFormat("###,##0.000000"));
        formatoTotalesHumedad.setFont(courier8BoldFont)
        formatoTotalesHumedad.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)

        WritableCellFormat formatoInfoReporte = new WritableCellFormat (arial14BoldFont);
        formatoInfoReporte.setAlignment(Alignment.CENTRE)

        WritableCellFormat formatoTitulo = new WritableCellFormat (arial16BoldFont);
        formatoTitulo.setAlignment(Alignment.CENTRE)

        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        WritableSheet sheet1 = workbook.createSheet("Planilla de liquidacion", 0)

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

        def empresa = Empresa.get(Integer.parseInt(""+params.empresa.id))

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="planilla_liquidacion_'+empresa.nombreDeEmpresa.toLowerCase().replace(' ','_')+'.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(3,0, "HISTORIAL DE COMPRAS",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def tipoReporte = ""+params.tipoReporte

        def liquidacionesEstano=null
        def liquidacionesPlata=null
        def liquidacionesAntimonio=null
        def liquidacionesWolfran=null
        def liquidacionesComplejo=null
        def liquidacionesPlomoPlata=null
        def liquidacionesZincPlata=null

        def retencionesDeLey=new ArrayList<String>()
        def retencionesOtras=new ArrayList<String>()

        if (tipoReporte.equals("fechasEmpresa")){
            def fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            def fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)
            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            def identificador = ""
            if(!empresa.nim.equals("")&&!empresa.nim.equals("0"))
                identificador = "NIM: ${empresa.nim}"
            else{
                if(!empresa.nit.equals("")&&!empresa.nit.equals("0"))
                    identificador = "NIT: ${empresa.nit}"
                else
                    identificador = "-"
            }

            sheet1.addCell(new Label(23,1, "PLANILLA DE LIQUIDACION ${empresa.toString()}",formatoTitulo))
            sheet1.addCell(new Label(23,3, "CORRESPONDIENTE A LAS FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))
            sheet1.addCell(new Label(23,4, identificador,formatoInfoReporte))

//            sheet1.addCell(new Label(29,4, "${empresa.concesion}",formatoInfoReporte))
            sheet1.addCell(new Label(51,4, "${empresa.municipio}",formatoInfoReporte))
            sheet1.addCell(new Label(52,4, "${empresa.codigoMunicipio}",formatoInfoReporte))

            //def liquidacionesCm = LiquidacionDeComplejo.findAllByFechaDeLiquidacionBetween(fechaInicial,fechaFinal,[sort: 'lote'])
            def liquidacionesCm = LiquidacionDeComplejo.list([sort: 'lote'])
            liquidacionesComplejo=new ArrayList<LiquidacionDeComplejo>()
            liquidacionesCm.each {
                if(it.recepcionDeComplejo.fechaDeRecepcion>=fechaInicial && it.recepcionDeComplejo.fechaDeRecepcion<=fechaFinal && it.recepcionDeComplejo.empresa.id==empresa.id)
                    liquidacionesComplejo.add(it)
            }
            //def liquidacionesPbAg = LiquidacionDePlomoPlata.findAllByFechaDeLiquidacionBetween(fechaInicial,fechaFinal,[sort: 'lote'])
            def liquidacionesPbAg = LiquidacionDePlomoPlata.list([sort: 'lote'])
            liquidacionesPlomoPlata=new ArrayList<LiquidacionDePlomoPlata>()
            liquidacionesPbAg.each {
                if(it.recepcionDeComplejo.fechaDeRecepcion>=fechaInicial && it.recepcionDeComplejo.fechaDeRecepcion<=fechaFinal && it.recepcionDeComplejo.empresa.id==empresa.id)
                    liquidacionesPlomoPlata.add(it)
            }
            //def liquidacionesZnAg = LiquidacionDeZincPlata.findAllByFechaDeLiquidacionBetween(fechaInicial,fechaFinal,[sort: 'lote'])
            def liquidacionesZnAg = LiquidacionDeZincPlata.list([sort: 'lote'])
            liquidacionesZincPlata=new ArrayList<LiquidacionDeZincPlata>()
            liquidacionesZnAg.each {
                if(it.recepcionDeComplejo.fechaDeRecepcion>=fechaInicial && it.recepcionDeComplejo.fechaDeRecepcion<=fechaFinal && it.recepcionDeComplejo.empresa.id==empresa.id)
                    liquidacionesZincPlata.add(it)
            }
            System.out.println("*** RESULTADOS DE COMPLEJO: ${liquidacionesComplejo.size()}")
            System.out.println("*** RESULTADOS DE PlomoPlata: ${liquidacionesPlomoPlata.size()}")
            System.out.println("*** RESULTADOS DE ZincPlata: ${liquidacionesZincPlata.size()}")
        }

        /*CONTROLAR SI SON null LAS LISTAS OBTENIDAS DE LIQUIDACIONES*/

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
        /*FIN - GENERANDO LISTA GENERAL DE RETENCIONES DE LEY*/

        /*GENERANDO LISTA GENERAL DE OTRAS RETENCIONES*/
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
        sheet1.addCell(new Label(2,6, "COT. DIA Sb",formatoEncabezado))
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
        sheet1.addCell(new Label(19,6, "LEY %Sb",formatoEncabezado))
        sheet1.addCell(new Label(20,6, "LEY %WO3",formatoEncabezado))
        sheet1.addCell(new Label(21,6, "LEY %Zn",formatoEncabezado))
        sheet1.addCell(new Label(22,6, "LEY %Pb",formatoEncabezado))
        sheet1.addCell(new Label(23,6, "LEY DM Ag",formatoEncabezado))
        sheet1.addCell(new Label(24,6, "LEY %As",formatoEncabezado))
        sheet1.addCell(new Label(25,6, "K. F. Sn",formatoEncabezado))
        sheet1.addCell(new Label(26,6, "K. F. Sb",formatoEncabezado))
        sheet1.addCell(new Label(27,6, "K. F. WO3",formatoEncabezado))
        sheet1.addCell(new Label(28,6, "K. F. Zn",formatoEncabezado))
        sheet1.addCell(new Label(29,6, "K. F. Pb",formatoEncabezado))
        sheet1.addCell(new Label(30,6, "K. F. Ag",formatoEncabezado))
        sheet1.addCell(new Label(31,6, "K. F. As",formatoEncabezado))
        sheet1.addCell(new Label(32,6, "COT. OF. Sn",formatoEncabezado))
        sheet1.addCell(new Label(33,6, "COT. OF. Sb",formatoEncabezado))
        sheet1.addCell(new Label(34,6, "COT. OF. WO3",formatoEncabezado))
        sheet1.addCell(new Label(35,6, "COT. OF. Zn",formatoEncabezado))
        sheet1.addCell(new Label(36,6, "COT. OF. Pb",formatoEncabezado))
        sheet1.addCell(new Label(37,6, "COT. OF. Ag",formatoEncabezado))
        sheet1.addCell(new Label(38,6, "VALOR OF. BRUTO",formatoEncabezado))
        sheet1.addCell(new Label(39,6, "ALIC. Sn %",formatoEncabezado))
        sheet1.addCell(new Label(40,6, "ALIC. Sb %",formatoEncabezado))
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

        //def colsNot=[16,18,19,20,21,22,23,24,32,33,34,35,36,37,39,40,41,42,43,44]
        def colsNot=[]
        def filasNot=new ArrayList()
        def inicioCiclo=0
        def finCiclo=0
        def filaTotal=0
        def totalLiquidacionesEstano = 0
        def totalLiquidacionesPlata = 0
        def totalLiquidacionesWolfran = 0
        def totalLiquidacionesAntimonio = 0
        def totalLiquidacionesComplejo = 0
        def totalLiquidacionesPlomoPlata = 0
        def totalLiquidacionesZincPlata = 0
        def columnaFinalRetenciones = 60+retencionesDeLey.size()+retencionesOtras.size()
        def fila = 7

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
//            fila++
//            //SUBTOTALES COMPLEJO
//            totalLiquidacionesPlomoPlata = liquidacionesPlomoPlata.size()
//            inicioCiclo=7+((liquidacionesEstano)?totalLiquidacionesEstano+2:0)+((liquidacionesPlata)?totalLiquidacionesPlata+2:0)+((liquidacionesWolfran)?totalLiquidacionesWolfran+2:0)+((liquidacionesAntimonio)?totalLiquidacionesAntimonio+2:0)
//            finCiclo=inicioCiclo+totalLiquidacionesPlomoPlata
//            filaTotal=finCiclo
//            filasNot.add(filaTotal)
//
//            sheet1.addCell(new Label(9,filaTotal, "SUBTOTALES",formatoEncabezado))
//            for (int col=12;col<columnaFinalRetenciones;col++){
//                def tret=0
//                for (int fil =inicioCiclo;fil<finCiclo;fil++){
//                    def contenido=(sheet1.getWritableCell(col,fil).contents.equals(""))?"0":sheet1.getWritableCell(col,fil).contents
//                    def valor = Double.parseDouble(contenido)
//                    tret+=(valor<0)?0:valor
//                }
//                if (!colsNot.contains(col))
//                    sheet1.addCell(new Number(col,filaTotal, tret,formatoTotales))
//            }
//
//            //humedad
//            sheet1.addCell(new Number(16,filaTotal, (totalKilosNetosSecosPlomoPlata/totalKilosNetosHumedosPlomoPlata*100-100)*-1,formatoTotales))
//            //ley
//            sheet1.addCell(new Number(21,filaTotal, totalKilosFinosZincPlomoPlata/totalKilosNetosSecosPlomoPlata*100,formatoTotales))
//            sheet1.addCell(new Number(22,filaTotal, totalKilosFinosPlomoPlomoPlata/totalKilosNetosSecosPlomoPlata*100,formatoTotales))
//            sheet1.addCell(new Number(23,filaTotal, 10000*totalKilosFinosPlataPlomoPlata/totalKilosNetosSecosPlomoPlata,formatoTotales))

//            fila++
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
//            fila++
//            //SUBTOTALES COMPLEJO
//            totalLiquidacionesZincPlata = liquidacionesZincPlata.size()
//            inicioCiclo=7+((liquidacionesEstano)?totalLiquidacionesEstano+2:0)+((liquidacionesPlata)?totalLiquidacionesPlata+2:0)+((liquidacionesWolfran)?totalLiquidacionesWolfran+2:0)+((liquidacionesAntimonio)?totalLiquidacionesAntimonio+2:0)+((liquidacionesPlomoPlata)?totalLiquidacionesPlomoPlata+2:0)
//            finCiclo=inicioCiclo+totalLiquidacionesZincPlata
//            filaTotal=finCiclo
//            filasNot.add(filaTotal)
//
//            sheet1.addCell(new Label(9,filaTotal, "SUBTOTALES",formatoEncabezado))
//            for (int col=12;col<columnaFinalRetenciones;col++){
//                def tret=0
//                for (int fil =inicioCiclo;fil<finCiclo;fil++){
//                    def contenido=(sheet1.getWritableCell(col,fil).contents.equals(""))?"0":sheet1.getWritableCell(col,fil).contents
//                    def valor = Double.parseDouble(contenido)
//                    tret+=(valor<0)?0:valor
//                }
//                if (!colsNot.contains(col))
//                    sheet1.addCell(new Number(col,filaTotal, tret,formatoTotales))
//            }
//
//            //humedad
//            sheet1.addCell(new Number(16,filaTotal, (totalKilosNetosSecosZincPlata/totalKilosNetosHumedosZincPlata*100-100)*-1,formatoTotales))
//            //ley
//            sheet1.addCell(new Number(21,filaTotal, totalKilosFinosZincZincPlata/totalKilosNetosSecosZincPlata*100,formatoTotales))
//            sheet1.addCell(new Number(22,filaTotal, totalKilosFinosPlomoZincPlata/totalKilosNetosSecosZincPlata*100,formatoTotales))
//            sheet1.addCell(new Number(23,filaTotal, 10000*totalKilosFinosPlataZincPlata/totalKilosNetosSecosZincPlata,formatoTotales))

//            fila++
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
//            fila++
//            //SUBTOTALES COMPLEJO
//            totalLiquidacionesComplejo = liquidacionesComplejo.size()
//            inicioCiclo=7+((liquidacionesEstano)?totalLiquidacionesEstano+2:0)+((liquidacionesPlata)?totalLiquidacionesPlata+2:0)+((liquidacionesWolfran)?totalLiquidacionesWolfran+2:0)+((liquidacionesAntimonio)?totalLiquidacionesAntimonio+2:0)+((liquidacionesPlomoPlata)?totalLiquidacionesPlomoPlata+2:0)+((liquidacionesZincPlata)?totalLiquidacionesZincPlata+2:0)
//            finCiclo=inicioCiclo+totalLiquidacionesComplejo
//            filaTotal=finCiclo
//            filasNot.add(filaTotal)
//
//            sheet1.addCell(new Label(9,filaTotal, "SUBTOTALES",formatoEncabezado))
//            for (int col=12;col<columnaFinalRetenciones;col++){
//                def tret=0
//                for (int fil =inicioCiclo;fil<finCiclo;fil++){
//                    def contenido=(sheet1.getWritableCell(col,fil).contents.equals(""))?"0":sheet1.getWritableCell(col,fil).contents
//                    def valor = Double.parseDouble(contenido)
//                    tret+=(valor<0)?0:valor
//                }
//                if (!colsNot.contains(col))
//                    sheet1.addCell(new Number(col,filaTotal, tret,formatoTotales))
//            }
//
//            //humedad
//            sheet1.addCell(new Number(16,filaTotal, (totalKilosNetosSecosComplejo/totalKilosNetosHumedosComplejo*100-100)*-1,formatoTotales))
//            //ley
//            sheet1.addCell(new Number(21,filaTotal, totalKilosFinosZincComplejo/totalKilosNetosSecosComplejo*100,formatoTotales))
//            sheet1.addCell(new Number(22,filaTotal, totalKilosFinosPlomoComplejo/totalKilosNetosSecosComplejo*100,formatoTotales))
//            sheet1.addCell(new Number(23,filaTotal, 10000*totalKilosFinosPlataComplejo/totalKilosNetosSecosComplejo,formatoTotales))

//            fila++
        }
        //def colsNot=[16,18,19,20,21,22,23,30,31,32,33,34,35,37,38,39,40,41,42]
        columnaFinalRetenciones = 60+retencionesDeLey.size()+retencionesOtras.size()
        def totalLiquidaciones = fila+1
        sheet1.addCell(new Label(9,totalLiquidaciones-1, "TOTALES",formatoEncabezado))
        for (int col=12;col<columnaFinalRetenciones;col++){
            def tret=0
            for (int fil =7;fil<totalLiquidaciones+7;fil++){
                def contenido=(sheet1.getWritableCell(col,fil).contents.equals(""))?"0":sheet1.getWritableCell(col,fil).contents
                def valor = Double.parseDouble(contenido)
                if (!filasNot.contains(fil))
                    tret+=(valor<0)?0:valor
            }
            if (!colsNot.contains(col))
                sheet1.addCell(new Number(col,totalLiquidaciones-1, tret,formatoTotales))
        }

        def f = totalLiquidaciones-1
        def totalKilosNetosHumedos=Double.parseDouble((sheet1.getWritableCell(15,f).contents.equals(""))?"0":sheet1.getWritableCell(15,f).contents)
        def totalKilosNetosSecos=Double.parseDouble((sheet1.getWritableCell(17,f).contents.equals(""))?"0":sheet1.getWritableCell(17,f).contents)
        def totalKilosFinosZinc=Double.parseDouble((sheet1.getWritableCell(28,f).contents.equals(""))?"0":sheet1.getWritableCell(28,f).contents)
        def totalKilosFinosPlomo=Double.parseDouble((sheet1.getWritableCell(29,f).contents.equals(""))?"0":sheet1.getWritableCell(29,f).contents)
        def totalKilosFinosPlata=Double.parseDouble((sheet1.getWritableCell(30,f).contents.equals(""))?"0":sheet1.getWritableCell(30,f).contents)

        System.out.println("f=${f}")
        System.out.println("totalKilosNetosHumedos=${totalKilosNetosHumedos}")
        System.out.println("totalKilosNetosSecos=${totalKilosNetosSecos}")
        System.out.println("totalKilosFinosZinc=${totalKilosFinosZinc}")
        System.out.println("totalKilosFinosPlomo=${totalKilosFinosPlomo}")
        System.out.println("totalKilosFinosPlata=${totalKilosFinosPlata}")

        //humedad
        //sheet1.addCell(new Number(16,f, 100-totalKilosNetosSecos/totalKilosNetosHumedos*100,formatoTotalesHumedad))
        //humedad + merma
//        sheet1.addCell(new Number(16,f, 100-totalKilosNetosSecos/totalKilosNetosHumedos*100,formatoTotalesHumedad))
        sheet1.addCell(new Number(16,f, 100-totalKilosNetosSecos/totalKilosNetosHumedos*100,formatoTotales))
        //ley
        sheet1.addCell(new Number(21,f, totalKilosFinosZinc/totalKilosNetosSecos*100,formatoTotales))
        sheet1.addCell(new Number(22,f, totalKilosFinosPlomo/totalKilosNetosSecos*100,formatoTotales))
        sheet1.addCell(new Number(23,f, 10000*totalKilosFinosPlata/totalKilosNetosSecos,formatoTotales))
        
        //mejorar la facha del reporte
        //def columnasOcultas = [columnaFinalRetenciones-1,columnaFinalRetenciones-2,columnaFinalRetenciones-4,columnaFinalRetenciones-5,columnaFinalRetenciones-6,columnaFinalRetenciones-8,columnaFinalRetenciones-retencionesOtras.size()-9,49,48,47,45,44,43,42,41,40,39,38,37,36,35,34,33,32,31,27,26,25,24,20,19,18,15,14,12,10,8,7,6,5,4,3,2,1]
//        def columnasOcultas = [columnaFinalRetenciones-1,columnaFinalRetenciones-2,columnaFinalRetenciones-4,columnaFinalRetenciones-6,columnaFinalRetenciones-8,columnaFinalRetenciones-retencionesOtras.size()-9,49,48,47,45,44,43,42,41,40,39,38,37,36,35,34,33,32,31,27,26,25,24,20,19,18,15,14,12,10,8,7,6,5,4,3,2,1]
        def columnasOcultas = [columnaFinalRetenciones-1,columnaFinalRetenciones-2,columnaFinalRetenciones-6,columnaFinalRetenciones-8,columnaFinalRetenciones-retencionesOtras.size()-9,49,48,47,45,44,43,42,41,40,39,38,37,36,35,34,33,32,31,27,26,25,24,20,19,18,15,14,12,10,8,7,6,5,4,3,2,1]

        for(i in columnasOcultas){
            //System.out.println("Eliminando columna ${i}")
            sheet1.removeColumn(i)
        }
        sheet1.setRowView(6,500)
        for(i in 0..100)
            sheet1.setColumnView(i,9)
        sheet1.setColumnView(1,25)
        sheet1.setColumnView(2,12)

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
