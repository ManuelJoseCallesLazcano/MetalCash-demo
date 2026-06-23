package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import com.google.gson.JsonArray
import grails.converters.JSON
import jxl.Workbook
import jxl.format.Alignment
import jxl.write.DateFormat
import jxl.write.DateTime
import jxl.write.Label
import jxl.write.Number
import jxl.write.NumberFormat
import jxl.write.WritableCellFormat
import jxl.write.WritableFont
import jxl.write.WritableSheet
import jxl.write.WritableWorkbook
import grails.plugins.jasper.JasperExportFormat
import grails.plugins.jasper.JasperReportDef
import org.grails.web.json.JSONArray
import org.socymet.anticipos.AnticipoContraEntrega
import org.socymet.calidad.ControlCalidadComplejo
import org.socymet.cotizaciones.CotizacionDeDolar
import org.socymet.liquidacion.LiquidacionDeComplejo
import org.socymet.liquidacion.LiquidacionDePlomoPlata
import org.socymet.liquidacion.LiquidacionDeZincPlata
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.seguridad.SecUser
import org.socymet.seguridad.SecUserSecRole
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class ReporteCompositoDeLotesController {
    def jasperService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    transient springSecurityService

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        params.max = Math.min(max ?: 10, 100)
        [reporteCompositoDeLotesInstanceList: ReporteCompositoDeLotes.list(params), reporteCompositoDeLotesInstanceTotal: ReporteCompositoDeLotes.count()]
//        [reporteCompositoDeLotesInstanceList: ReporteCompositoDeLotes.findAllByDeposito(usuarioActual.deposito,params), reporteCompositoDeLotesInstanceTotal: ReporteCompositoDeLotes.findAllByDeposito(usuarioActual.deposito,params).size()]
    }

    def create() {
        [reporteCompositoDeLotesInstance: new ReporteCompositoDeLotes(params)]
    }

    def save() {
        def reporteCompositoDeLotesInstance = new ReporteCompositoDeLotes(params)
        if (!reporteCompositoDeLotesInstance.save(flush: true)) {
            render(view: "create", model: [reporteCompositoDeLotesInstance: reporteCompositoDeLotesInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reporteCompositoDeLotes.label', default: 'ReporteCompositoDeLotes'), reporteCompositoDeLotesInstance.id])
        redirect(action: "show", id: reporteCompositoDeLotesInstance.id)
    }

    def show(Long id) {
        def reporteCompositoDeLotesInstance = ReporteCompositoDeLotes.get(id)
        if (!reporteCompositoDeLotesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteCompositoDeLotes.label', default: 'ReporteCompositoDeLotes'), id])
            redirect(action: "list")
            return
        }

        [reporteCompositoDeLotesInstance: reporteCompositoDeLotesInstance]
    }

    def edit(Long id) {
        def reporteCompositoDeLotesInstance = ReporteCompositoDeLotes.get(id)
        if (!reporteCompositoDeLotesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteCompositoDeLotes.label', default: 'ReporteCompositoDeLotes'), id])
            redirect(action: "list")
            return
        }

        if(reporteCompositoDeLotesInstance.estadoDeAprobacion.equals("APROBADO")){
            flash.message = message(code: 'reporteCompositoDeLotesInstance.definitivoAprobado', args: [])
            redirect(action: "list")
//            show(reporteCompositoDeLotesInstance.id)
            return
        }

        [reporteCompositoDeLotesInstance: reporteCompositoDeLotesInstance]
    }

    def update(Long id, Long version) {
        def reporteCompositoDeLotesInstance = ReporteCompositoDeLotes.get(id)
        if (!reporteCompositoDeLotesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteCompositoDeLotes.label', default: 'ReporteCompositoDeLotes'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reporteCompositoDeLotesInstance.version > version) {
                reporteCompositoDeLotesInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reporteCompositoDeLotes.label', default: 'ReporteCompositoDeLotes')] as Object[],
                        "Another user has updated this ReporteCompositoDeLotes while you were editing")
                render(view: "edit", model: [reporteCompositoDeLotesInstance: reporteCompositoDeLotesInstance])
                return
            }
        }

        reporteCompositoDeLotesInstance.properties = params

        if (!reporteCompositoDeLotesInstance.save(flush: true)) {
            render(view: "edit", model: [reporteCompositoDeLotesInstance: reporteCompositoDeLotesInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reporteCompositoDeLotes.label', default: 'ReporteCompositoDeLotes'), reporteCompositoDeLotesInstance.id])
        redirect(action: "show", id: reporteCompositoDeLotesInstance.id)
    }

    def delete(Long id) {
        def reporteCompositoDeLotesInstance = ReporteCompositoDeLotes.get(id)
        if (!reporteCompositoDeLotesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteCompositoDeLotes.label', default: 'ReporteCompositoDeLotes'), id])
            redirect(action: "list")
            return
        }

        try {
            reporteCompositoDeLotesInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reporteCompositoDeLotes.label', default: 'ReporteCompositoDeLotes'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reporteCompositoDeLotes.label', default: 'ReporteCompositoDeLotes'), id])
            redirect(action: "show", id: id)
        }
    }

    def lotesParaCompositoJSON() {
        def empresa = !params.empresaId.equals("null")?Empresa.get(params.empresaId.toLong()):null
        def ordenarElemento = params.ordenarElemento.toString()
        def lotesCompositoJSON = params.lotesComposito.toString()==""?new JSONArray("[]"):new JSONArray(params.lotesComposito)
        log.error("lotesCompositoJSON: $lotesCompositoJSON")
        def recepcionesComplejo
        def liquidacionesList = []
        def mapaLiquidacion = [:]
        if (empresa){
            recepcionesComplejo = RecepcionDeComplejo.findAllByEmpresaAndEstadoAnalisisAndNombreComposito(empresa, "CON ANALISIS", "-")
        }else{
            recepcionesComplejo = RecepcionDeComplejo.findAllByEstadoAnalisisAndNombreComposito("CON ANALISIS", "-")
        }

        def lotesComposito = new ArrayList<LoteComposito>()
        def loteComposito
        def analisis
        def liquidacion
        recepcionesComplejo.each {recepcion ->
//            log.error("LOTE: ${recepcion.toString()}")
            if(recepcion.estadoDelLote=="LIQUIDADO"){
                liquidacion = LiquidacionDeComplejo.findByRecepcionDeComplejo(recepcion)
                loteComposito = new LoteComposito(
                        recepcionDeComplejo: recepcion,
                        fechaRecepcion: recepcion.fechaDeRecepcion,
                        codigoLote: recepcion.toString(),
                        empresa: recepcion.empresa.toString(),
                        departamento: recepcion.empresa.departamento,
                        municipio: recepcion.empresa.municipio,
                        proveedor: recepcion.cliente.nombre,
                        pesoBruto: recepcion.pesoBruto,
                        porcentajeHumedad: liquidacion.porcentajeHumedadFinal,
                        pesoNeto: liquidacion.kilosNetosSecos,
                        porcentajeZinc: liquidacion.porcentajeZincFinal,
                        porcentajePlomo: liquidacion.porcentajePlomoFinal,
                        porcentajePlata: liquidacion.porcentajePlataFinal,
                        kilosFinosZinc: liquidacion.kilosFinosZinc,
                        kilosFinosPlomo: liquidacion.kilosFinosPlomo,
                        kilosFinosPlata: liquidacion.kilosFinosPlata,
                        precioTonelada: liquidacion.valorPorTonelada,
                        valorOficialBruto: liquidacion.valorOficialBruto,
                        valorNeto: liquidacion.valorNetoMineralEnBolivianos,
                        costoUnitarioTransporte: recepcion.tipoDeMaterial=="CONCENTRADO"?recepcion.empresa.costoTransporteConcentrados:recepcion.empresa.costoTransporteComplejos,
                        costoTransporte: recepcion.costoDeTransporte
                )
                lotesComposito.add(loteComposito)
            }else{
                analisis = ControlCalidadComplejo.findByRecepcionDeComplejo(recepcion)
                if(analisis){
//                    log.error("ANALISIS LABORATORIO: ${analisis.toString()}")
                    def tipoDeCambioOficial = CotizacionDeDolar.findByActivo(1).tipoDeCambioOficial
                    def pesoBruto=recepcion.pesoBruto
                    def kilosNetosHumedos=pesoBruto-pesoBruto*analisis.porcentajeMermaPromexbol/100
                    def kilosNetosSecos=kilosNetosHumedos-kilosNetosHumedos*analisis.porcentajeHumedadPromexbol/100
                    def kilosFinosZinc=kilosNetosSecos*analisis.porcentajeZincPromexbol/100
                    def kilosFinosPlomo=kilosNetosSecos*analisis.porcentajePlomoPromexbol/100
                    def kilosFinosPlata=kilosNetosSecos*analisis.porcentajePlataPromexbol/10000
                    def librasFinasZinc = (kilosFinosZinc*2.2046223).floatValue().round(2)
                    def librasFinasPlomo = (kilosFinosPlomo*2.2046223).floatValue().round(2)
                    def onzasTroyPlata = (kilosFinosPlata*32.15073).floatValue().round(2)
                    def valorOficialBrutoZinc = (librasFinasZinc*recepcion.cotizacionQuincenalDeMinerales.zinc).floatValue().round(2)
                    def valorOficialBrutoPlomo = (librasFinasPlomo*recepcion.cotizacionQuincenalDeMinerales.plomo).floatValue().round(2)
                    def valorOficialBrutoPlata = (onzasTroyPlata*recepcion.cotizacionQuincenalDeMinerales.plata).floatValue().round(2)
                    def valorOficialBrutoZincBs = (valorOficialBrutoZinc*tipoDeCambioOficial).floatValue().round(2)
                    def valorOficialBrutoPlomoBs = (valorOficialBrutoPlomo*tipoDeCambioOficial).floatValue().round(2)
                    def valorOficialBrutoPlataBs = (valorOficialBrutoPlata*tipoDeCambioOficial).floatValue().round(2)
                    def valorOficialBruto = (valorOficialBrutoZincBs+valorOficialBrutoPlomoBs+valorOficialBrutoPlataBs).floatValue().round(2)

                    loteComposito = new LoteComposito(
                            recepcionDeComplejo: recepcion,
                            fechaRecepcion: recepcion.fechaDeRecepcion,
                            codigoLote: recepcion.toString(),
                            empresa: recepcion.empresa.toString(),
                            departamento: recepcion.empresa.departamento,
                            municipio: recepcion.empresa.municipio,
                            proveedor: recepcion.cliente.nombre,
                            pesoBruto: recepcion.pesoBruto,
                            porcentajeHumedad: analisis.porcentajeHumedadPromexbol,
                            pesoNeto: kilosNetosSecos,
                            porcentajeZinc: analisis.porcentajeZincPromexbol,
                            porcentajePlomo: analisis.porcentajePlomoPromexbol,
                            porcentajePlata: analisis.porcentajePlataPromexbol,
                            kilosFinosZinc: kilosFinosZinc,
                            kilosFinosPlomo: kilosFinosPlomo,
                            kilosFinosPlata: kilosFinosPlata,
                            precioTonelada: 0,
                            valorOficialBruto: valorOficialBruto,
                            valorNeto: 0,
                            costoUnitarioTransporte: recepcion.tipoDeMaterial=="CONCENTRADO"?recepcion.empresa.costoTransporteConcentrados:recepcion.empresa.costoTransporteComplejos,
                            costoTransporte: recepcion.costoDeTransporte
                    )
                    lotesComposito.add(loteComposito)
                }
            }
        }

        def sortBy
        switch (ordenarElemento){
            case "PLATA":
                sortBy = {ob1,ob2->ob2.porcentajePlata <=> ob1.porcentajePlata}
                break
            case "PLOMO":
                sortBy = {ob1,ob2->ob2.porcentajePlomo <=> ob1.porcentajePlomo}
                break
            case "ZINC":
                sortBy = {ob1,ob2->ob2.porcentajeZinc <=> ob1.porcentajeZinc}
                break
        }

        lotesComposito.sort(sortBy)

        lotesComposito.each { lote ->
            mapaLiquidacion = [:]
            mapaLiquidacion.put("lote", lote.recepcionDeComplejo.toString())
            mapaLiquidacion.put("recepcionId", lote.recepcionDeComplejo.id)
            mapaLiquidacion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(lote.recepcionDeComplejo.fechaDeRecepcion))
            mapaLiquidacion.put("nombreEmpresa", lote.empresa.toString())
            mapaLiquidacion.put("departamento", lote.departamento)
            mapaLiquidacion.put("proveedor", lote.proveedor)
            mapaLiquidacion.put("municipio", lote.municipio)
            mapaLiquidacion.put("pesoBruto", lote.pesoBruto)
            mapaLiquidacion.put("porcentajeHumedad", lote.porcentajeHumedad)
            mapaLiquidacion.put("kilosNetosSecos", lote.pesoNeto)
            mapaLiquidacion.put("porcentajeZincFinal", lote.porcentajeZinc)
            mapaLiquidacion.put("porcentajePlomoFinal", lote.porcentajePlomo)
            mapaLiquidacion.put("porcentajePlataFinal", lote.porcentajePlata)
            mapaLiquidacion.put("kilosFinosZinc", lote.kilosFinosZinc)
            mapaLiquidacion.put("kilosFinosPlomo", lote.kilosFinosPlomo)
            mapaLiquidacion.put("kilosFinosPlata", lote.kilosFinosPlata)
            mapaLiquidacion.put("precioTonelada", lote.precioTonelada)
            mapaLiquidacion.put("valorOficialBruto", lote.valorOficialBruto)
            mapaLiquidacion.put("valorNetoMineralEnBolivianos", lote.valorNeto)
            mapaLiquidacion.put("costoUnitarioTransporte", lote.costoUnitarioTransporte)
            mapaLiquidacion.put("costoDeTransporte", lote.costoTransporte)
            mapaLiquidacion.put("costoManipuleo", lote.recepcionDeComplejo.costoManipuleo)
            mapaLiquidacion.put("bonos", 0)
            mapaLiquidacion.put("totalLiquidoPagable", 0)
//            mapaLiquidacion.put("disponible", "SI")
            mapaLiquidacion.put("disponible", existeRecepcion(lote.recepcionDeComplejo.id, lotesCompositoJSON)?"SI":"NO")

            liquidacionesList.add(mapaLiquidacion)
        }

        render([
            lotes: (liquidacionesList as JSON).toString(),
            numberFormatException: 0,
            nullPointerException: 0
        ] as JSON)
    }

    def existeRecepcion(Long recepcionId, JSONArray jsonArray){
        def existe = true
        jsonArray.each {
//            log.error("****** existeRecepcion: comparando $recepcionId con ${it["recepcionId"]} > ${it["recepcionId"]==recepcionId}")
            if(it["recepcionId"]==recepcionId)
                existe = existe && false
        }
        return existe
    }

    def usuarioActual = {
        def cuenta = springSecurityService.currentUser as SecUser
        def rolesDeUsuario = SecUserSecRole.findAllBySecUser(cuenta)
        render([
            elaboradoPor: cuenta.nombre,
            roles: rolesDeUsuario.secRole as String
        ] as JSON)
    }

    def createReport = {
        def factura = ReporteCompositoDeLotes.get(params.id)
        def realPath = servletContext.getRealPath("/reports/images/")
        params.realPath=realPath+"/"
        chain(controller:'jasper',action:'index',model:[data:factura],params:params)
    }

    def crearReporteComposito = {
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

        WritableCellFormat formatoContador = new WritableCellFormat (new NumberFormat("##0"));
        formatoContador.setFont(courier8PlainFont)
        formatoContador.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

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

        WritableSheet sheet1 = workbook.createSheet("Composito de Lotes", 0)

        for(i in 0..100)
            sheet1.setColumnView(i,11)
        sheet1.setColumnView(0,5)
        sheet1.setColumnView(3,25)
        sheet1.setColumnView(4,25)
//        sheet1.setColumnView(3,30)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="reporte_composito_lotes.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        //VERIFICACION DEL TIPO DE REPORTE
        def composito = ReporteCompositoDeLotes.get(params.compositoId)
        def compositoDetalle = CompositoDeLotesDetalle.findAllByReporteCompositoDeLotes(composito)
        def fila = 8
        def totalKilosBrutos=0
        def totalValorBruto = 0
        def contFila = 1

        sheet1.addCell(new Label(0,0, "REPORTE DE COMPOSITO DE LOTES",formatoTitulo))
//        BigDecimal leyMinimaZinc=0.0
//        BigDecimal leyMaximaZinc
//        BigDecimal leyMinimaPlomo=0.0
//        BigDecimal leyMaximaPlomo
//        BigDecimal leyMinimaPlata=0.0
//        BigDecimal leyMaximaPlata
//        sheet1.addCell(new Label(0,2, "No. COMPOSITO:",formatoInfoReporte))
//        sheet1.addCell(new Label(2,2, "${composito.numeroComposito}",formatoInfoReporte))
        sheet1.addCell(new Label(0,2, "SIGLA:",formatoInfoReporte))
        sheet1.addCell(new Label(3,2, "${composito.sigla}",formatoInfoReporte))
        sheet1.addCell(new Label(0,3, "DESTINO:",formatoInfoReporte))
        sheet1.addCell(new Label(3,3, "${composito.destino}",formatoInfoReporte))
        sheet1.addCell(new Label(0,4, "ELABORADO POR:",formatoInfoReporte))
        sheet1.addCell(new Label(3,4, "${composito.elaboradoPor}",formatoInfoReporte))
        sheet1.addCell(new Label(0,5, "FECHA ELABORACION:",formatoInfoReporte))
        sheet1.addCell(new Label(3,5, "${new java.text.SimpleDateFormat("dd/MM/yyyy").format(composito.fechaDeElaboracion)}",formatoInfoReporte))
        sheet1.addCell(new Label(0,6, "ESTADO DEL COMPOSITO:",formatoInfoReporte))
        sheet1.addCell(new Label(3,6, "${composito.estadoDelComposito}",formatoInfoReporte))

        sheet1.addCell(new Label(0,fila, "No.",formatoEncabezado))
        sheet1.addCell(new Label(1,fila, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(2,fila, "FECHA DE RECEPCION",formatoEncabezado))
        sheet1.addCell(new Label(3,fila, "EMPRESA",formatoEncabezado))
        sheet1.addCell(new Label(4,fila, "PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(5,fila, "MUNICIPIO",formatoEncabezado))
        sheet1.addCell(new Label(6,fila, "KILOS BRUTOS",formatoEncabezado))
        sheet1.addCell(new Label(7,fila, "HUMEDAD",formatoEncabezado))
        sheet1.addCell(new Label(8,fila, "KILOS NETOS",formatoEncabezado))
        sheet1.addCell(new Label(9,fila, "LEY ZN",formatoEncabezado))
        sheet1.addCell(new Label(10,fila, "LEY PB",formatoEncabezado))
        sheet1.addCell(new Label(11,fila, "LEY AG",formatoEncabezado))
        sheet1.addCell(new Label(12,fila, "K. F. ZN",formatoEncabezado))
        sheet1.addCell(new Label(13,fila, "K. F. PB",formatoEncabezado))
        sheet1.addCell(new Label(14,fila, "K. F. AG",formatoEncabezado))
        sheet1.addCell(new Label(15,fila, "VALOR \$us/TON",formatoEncabezado))
        sheet1.addCell(new Label(16,fila, "VALOR BRUTO",formatoEncabezado))
        sheet1.addCell(new Label(17,fila, "VALOR NETO",formatoEncabezado))

        fila++

        if(compositoDetalle){
            compositoDetalle.each {
                sheet1.addCell(new Number(0,fila, contFila,formatoContador))
                sheet1.addCell(new Label(1,fila, it.lote,formatoDatos))
                sheet1.addCell(new DateTime(2,fila, it.fechaDeRecepcion,formatoFecha))
                sheet1.addCell(new Label(3,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(4,fila, it.proveedor,formatoDatos))
                sheet1.addCell(new Label(5,fila, it.municipio,formatoDatos))
                sheet1.addCell(new Number(6,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(7,fila, it.porcentajeHumedad,formatoDatos))
                sheet1.addCell(new Number(8,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(9,fila, it.porcentajeZincFinal,formatoDatos))
                sheet1.addCell(new Number(10,fila, it.porcentajePlomoFinal,formatoDatos))
                sheet1.addCell(new Number(11,fila, it.porcentajePlataFinal,formatoDatos))
                sheet1.addCell(new Number(12,fila, it.kilosFinosZinc,formatoDatos))
                sheet1.addCell(new Number(13,fila, it.kilosFinosPlomo,formatoDatos))
                sheet1.addCell(new Number(14,fila, it.kilosFinosPlata,formatoDatos))
                sheet1.addCell(new Number(15,fila, it.precioTonelada,formatoDatos))
                sheet1.addCell(new Number(16,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(17,fila, it.valorNetoMineralEnBolivianos,formatoDatos))

//                totalCostoTransporte+=it.costoDeTransporte
                totalValorBruto+=it.valorOficialBruto

                fila++
                contFila++
            }
//            //llenado de totales
            sheet1.addCell(new Number(6,fila, composito.totalKilosBrutos,formatoTotales))
            sheet1.addCell(new Number(8,fila, composito.totalKilosNetosSecos,formatoTotales))
            sheet1.addCell(new Number(9,fila, composito.leyPromedioZinc,formatoTotales))
            sheet1.addCell(new Number(10,fila, composito.leyPromedioPlomo,formatoTotales))
            sheet1.addCell(new Number(11,fila, composito.leyPromedioPlata,formatoTotales))
            sheet1.addCell(new Number(12,fila, composito.totalKilosFinosZinc,formatoTotales))
            sheet1.addCell(new Number(13,fila, composito.totalKilosFinosPlomo,formatoTotales))
            sheet1.addCell(new Number(14,fila, composito.totalKilosFinosPlata,formatoTotales))
            sheet1.addCell(new Number(16,fila, totalValorBruto,formatoTotales))
            sheet1.addCell(new Number(17,fila, composito.totalValorNeto,formatoTotales))
        }

        fila+=2
        sheet1.addCell(new Label(0,fila, "PARTICIPACION",formatoTitulo))
        fila+=2
        sheet1.addCell(new Label(3,fila, "DEPARTAMENTO",formatoEncabezado))
        sheet1.addCell(new Label(4,fila, "MUNICIPIO",formatoEncabezado))
        sheet1.addCell(new Label(5,fila, "KILOS NETOS",formatoEncabezado))
        sheet1.addCell(new Label(6,fila, "%",formatoEncabezado))
        fila++
        def participacion = CompositoLotesParticipacion.findAllByReporteCompositoDeLotes(composito)

        participacion.each {
            sheet1.addCell(new Label(3,fila, it.departamento,formatoDatos))
            sheet1.addCell(new Label(4,fila, it.municipio,formatoDatos))
            sheet1.addCell(new Number(5,fila, it.kilosNetosSecos,formatoDatos))
            sheet1.addCell(new Number(6,fila, it.porcentajeParticipacion,formatoDatos))
            fila++
        }
        sheet1.addCell(new Number(6,fila, 100,formatoTotales))

        workbook.write();
        workbook.close();
    }

    def crearReportePDF = {
        log.error("composito Id: ${params.compositoId}")

        def composito = ReporteCompositoDeLotes.get(params.compositoId.toLong())

        def realPath = servletContext.getRealPath("/reports/images/")

        //parametros del reporte
        Map reportParams = [:]
        reportParams.put("realPath",realPath+"/")
        reportParams.put("SUBREPORT_DIR","${servletContext.getRealPath('/reports')}/")
        reportParams.put("reporteCompositoDeLotesId", composito.id)

        def reportDef = new JasperReportDef(name: "reporte_composito_lotes.jasper",
                fileFormat: JasperExportFormat.PDF_FORMAT,
                parameters: reportParams)
        byte[] bytes = jasperService.generateReport(reportDef).toByteArray()
        //ENVIAR EL REPORTE PARA DESCARGA
        response.addHeader("Content-Disposition", 'attachment; filename="composito_'+composito.sigla.replace(' ','_')+'.pdf"')
        response.contentType = 'application/pdf'
        response.outputStream << bytes
        response.outputStream.flush()
    }
}

@Transactional
class LoteComposito{
    RecepcionDeComplejo recepcionDeComplejo
    Date fechaRecepcion
    String codigoLote
    String empresa
    String proveedor
    String departamento
    String municipio
    BigDecimal pesoBruto
    BigDecimal porcentajeHumedad
    BigDecimal pesoNeto
    BigDecimal porcentajeZinc
    BigDecimal porcentajePlomo
    BigDecimal porcentajePlata
    BigDecimal kilosFinosZinc
    BigDecimal kilosFinosPlomo
    BigDecimal kilosFinosPlata
    BigDecimal precioTonelada
    BigDecimal valorOficialBruto
    BigDecimal valorNeto
    BigDecimal costoUnitarioTransporte
    BigDecimal costoTransporte
}
