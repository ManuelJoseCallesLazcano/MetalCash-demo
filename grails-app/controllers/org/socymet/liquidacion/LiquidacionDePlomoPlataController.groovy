package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import grails.plugins.jasper.JasperExportFormat
import grails.plugins.jasper.JasperReportDef
import org.grails.web.json.JSONArray
import org.socymet.anticipos.AnticipoDetalle
import org.socymet.cotizaciones.TablaOrigenCotizacionesComplejo
import org.socymet.cotizaciones.TerminosDeContrato
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.utilidades.NumeroALiteral
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class LiquidacionDePlomoPlataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def jasperService

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [liquidacionDePlomoPlataInstanceList: LiquidacionDePlomoPlata.list(params), liquidacionDePlomoPlataInstanceTotal: LiquidacionDePlomoPlata.count()]
    }

    @Secured(['ROLE_ADMIN','ROLE_LIQUIDACION'])
    def create() {
        [liquidacionDePlomoPlataInstance: new LiquidacionDePlomoPlata(params)]
    }

    @Secured(['ROLE_ADMIN','ROLE_LIQUIDACION'])
    def save() {
        def liquidacionDePlomoPlataInstance = new LiquidacionDePlomoPlata(params)
        if (!liquidacionDePlomoPlataInstance.save(flush: true)) {
            render(view: "create", model: [liquidacionDePlomoPlataInstance: liquidacionDePlomoPlataInstance])
            return
        }

//        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionDePlomoPlata.label', default: 'LiquidacionDePlomoPlata'), liquidacionDePlomoPlataInstance.id])
        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionDePlomoPlata.label', default: 'LiquidacionDePlomoPlata'), liquidacionDePlomoPlataInstance.lote])
        redirect(action: "show", id: liquidacionDePlomoPlataInstance.id)
    }

    def show(Long id) {
        def liquidacionDePlomoPlataInstance = LiquidacionDePlomoPlata.get(id)
        if (!liquidacionDePlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDePlomoPlata.label', default: 'LiquidacionDePlomoPlata'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDePlomoPlataInstance: liquidacionDePlomoPlataInstance]
    }

    @Secured(['ROLE_ADMIN','ROLE_LIQUIDACION'])
    def edit(Long id) {
        def liquidacionDePlomoPlataInstance = LiquidacionDePlomoPlata.get(id)
        if (!liquidacionDePlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDePlomoPlata.label', default: 'LiquidacionDePlomoPlata'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDePlomoPlataInstance: liquidacionDePlomoPlataInstance]
    }

    def update(Long id, Long version) {
        def liquidacionDePlomoPlataInstance = LiquidacionDePlomoPlata.get(id)
        if (!liquidacionDePlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDePlomoPlata.label', default: 'LiquidacionDePlomoPlata'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (liquidacionDePlomoPlataInstance.version > version) {
                liquidacionDePlomoPlataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'liquidacionDePlomoPlata.label', default: 'LiquidacionDePlomoPlata')] as Object[],
                        "Another user has updated this LiquidacionDePlomoPlata while you were editing")
                render(view: "edit", model: [liquidacionDePlomoPlataInstance: liquidacionDePlomoPlataInstance])
                return
            }
        }

        liquidacionDePlomoPlataInstance.properties = params

        if (!liquidacionDePlomoPlataInstance.save(flush: true)) {
            render(view: "edit", model: [liquidacionDePlomoPlataInstance: liquidacionDePlomoPlataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'liquidacionDePlomoPlata.label', default: 'LiquidacionDePlomoPlata'), liquidacionDePlomoPlataInstance.id])
        redirect(action: "show", id: liquidacionDePlomoPlataInstance.id)
    }

    @Secured(['ROLE_ADMIN','ROLE_LIQUIDACION'])
    def delete(Long id) {
        def liquidacionDePlomoPlataInstance = LiquidacionDePlomoPlata.get(id)
        if (!liquidacionDePlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDePlomoPlata.label', default: 'LiquidacionDePlomoPlata'), id])
            redirect(action: "list")
            return
        }

        try {
            /* CAMBIAR EL ESTADO DEL LOTE */
            def recepcion = RecepcionDeComplejo.get(liquidacionDePlomoPlataInstance.recepcionDeComplejo.id)
            recepcion.estadoDelLote = "NO LIQUIDADO"
            recepcion.save()
            /* FIN - CAMBIAR EL ESTADO DEL LOTE*/

            /* ELIMINAR LAS RETENCIONES*/
            def listaRetenciones = LiquidacionDePlomoPlataRetenciones.findAllByLiquidacionDePlomoPlata(liquidacionDePlomoPlataInstance)
            listaRetenciones.each {
                it.delete(flush: true)
            }
            /* FIN - ELIMINAR RETENCIONES*/

            /* ELIMINAR RETENCIONES POR PAGAR*/
            def retencionesPorPagarAnteriores = RetencionPorPagarComplejo.findAllByLiquidacionId(liquidacionDePlomoPlataInstance.id)
            retencionesPorPagarAnteriores.each {
                it.delete()
            }
            /* FIN - ELIMINAR RETENCIONES*/

            /* ELIMINAR LOS ANTICIPOS CONTRA ENTREGA REALIZADOS*/
            def anticipoDetalle = AnticipoDetalle.findByRecepcionId(recepcion.id)
            if(anticipoDetalle){
                def anticipo = anticipoDetalle.anticipo
                def nuevoAnticipoPorPagar = anticipo.totalPorPagar + liquidacionDePlomoPlataInstance.totalAnticiposContraEntrega
                anticipo.totalPagado = anticipo.totalPagado - liquidacionDePlomoPlataInstance.totalAnticiposContraEntrega
                anticipo.totalPorPagar = nuevoAnticipoPorPagar
                anticipo.save(failOnError: true)

                anticipoDetalle.anticipoPagable=liquidacionDePlomoPlataInstance.totalAnticiposContraEntrega
                anticipoDetalle.estadoAnticipo="SIN PAGAR"
                anticipoDetalle.save(failOnError: true)

                recepcion.estadoAnticipo = "CON ANTICIPO"
                recepcion.save(failOnError: true)
            }
            /* FIN - ELIMINAR LOS ANTICIPOS CONTRA ENTREGA REALIZADOS*/
            
            liquidacionDePlomoPlataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'liquidacionDePlomoPlata.label', default: 'LiquidacionDePlomoPlata'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDePlomoPlata.label', default: 'LiquidacionDePlomoPlata'), id])
            redirect(action: "show", id: id)
        }
    }

    def eliminarDeConjunto(Long id) {
        def liquidacionDePlomoPlataInstance = LiquidacionDePlomoPlata.get(id)
        if (!liquidacionDePlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDePlomoPlata.label', default: 'LiquidacionDePlomoPlata'), id])
            redirect(action: "list")
            return
        }

        try {
            System.out.println("*** LOCALIZADO LOTE: ${liquidacionDePlomoPlataInstance.lote} DEL CONJUNTO: ${liquidacionDePlomoPlataInstance.conjuntoPlomoPlata}")
            liquidacionDePlomoPlataInstance.conjuntoPlomoPlata="-"
            liquidacionDePlomoPlataInstance.save(failOnError: true)
            flash.message = "El Lote ha sido eliminado del Conjunto anteriormente asignado."
            redirect(action: "show", id: id)
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDePlomoPlata.label', default: 'LiquidacionDePlomoPlata'), id])
            redirect(action: "show", id: id)
        }
    }

    def cancelacionDeLote(Long id) {
        def liquidacionDePlomoPlataInstance = LiquidacionDePlomoPlata.get(id)
        if (!liquidacionDePlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDePlomoPlata.label', default: 'LiquidacionDePlomoPlata'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionDePlomoPlataInstance.fechaDeCancelacion=new java.util.Date()
            liquidacionDePlomoPlataInstance.save(failOnError: true)
            flash.message = "Se ha establecido la Fecha de Cancelacion del Lote"
            redirect(action: "show", id: id)
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDePlomoPlata.label', default: 'LiquidacionDePlomoPlata'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporte = {
        def realPath = org.socymet.util.ReportesRuntime.realPath("/reports/images/")
        params.realPath = realPath+"/"
        params.SUBREPORT_DIR = "${org.socymet.util.ReportesRuntime.realPath('/reports')}/"
        chain(controller:'jasper',action:'index',params:params)
    }

    def liquidacionesJSON() {
        def lote = params.term.toString()
        def liquidacionDePlomoPlatas = LiquidacionDePlomoPlata.findAllByLoteLikeAndConjuntoPlomoPlata("%${lote}%","-")
        def liquidacionesList = []
        liquidacionDePlomoPlatas.each { liquidacion ->
            def liquidacionMap = [:]
            liquidacionMap.put("liquidacionId", liquidacion.id)
            liquidacionMap.put("label", liquidacion.lote)
            liquidacionMap.put("value", liquidacion.lote)
            liquidacionMap.put("nombreCliente", liquidacion.nombreCliente)
            liquidacionMap.put("nombreEmpresa", liquidacion.nombreEmpresa)
            liquidacionMap.put("fechaDeRecepcion", liquidacion.fechaDeRecepcion)
            liquidacionMap.put("fechaDeLiquidacion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(liquidacion.fechaDeLiquidacion))
            liquidacionMap.put("kilosNetosSecos", liquidacion.kilosNetosSecos)
            liquidacionMap.put("porcentajePlomo", liquidacion.porcentajePlomo)
            liquidacionMap.put("porcentajePlata", liquidacion.porcentajePlata)
            liquidacionesList.add(liquidacionMap)
        }
        render liquidacionesList as JSON
    }

    def lotesEnConjuntoJSON() {
        def lote = params.term.toString()
        def liquidacionDePlomoPlatas = LiquidacionDePlomoPlata.findAllByLoteLikeAndConjuntoPlomoPlataNotEqual("%${lote}%","-")
        def liquidacionesList = []
        liquidacionDePlomoPlatas.each { liquidacion ->
            def liquidacionMap = [:]
            liquidacionMap.put("liquidacionId", liquidacion.id)
            liquidacionMap.put("label", liquidacion.lote)
            liquidacionMap.put("value", liquidacion.lote)
            liquidacionMap.put("nombreCliente", liquidacion.nombreCliente)
            liquidacionMap.put("nombreEmpresa", liquidacion.nombreEmpresa)
            liquidacionMap.put("fechaDeRecepcion", liquidacion.fechaDeRecepcion)
            liquidacionMap.put("fechaDeLiquidacion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(liquidacion.fechaDeLiquidacion))
            liquidacionMap.put("kilosNetosSecos", liquidacion.kilosNetosSecos)
            liquidacionMap.put("porcentajePlomo", liquidacion.porcentajePlomo)
            liquidacionMap.put("porcentajePlata", liquidacion.porcentajePlata)
            liquidacionMap.put("conjuntoPlomoPlata", liquidacion.conjuntoPlomoPlata)
            liquidacionesList.add(liquidacionMap)
        }
        render liquidacionesList as JSON
    }

    def crearReporteGrupal2 = {
        Map reportParams = [:]
        def millis = params.millis.toBigDecimal()
        def realPath = org.socymet.util.ReportesRuntime.realPath("/reports/images/")
        reportParams.put("millis",millis)
        reportParams.put("realPath",realPath+"/")
        reportParams.put("SUBREPORT_DIR","${org.socymet.util.ReportesRuntime.realPath('/reports')}/")

        def reportDef = new JasperReportDef(name:'liquidacion_grupal_plomo_plata.jasper',fileFormat:JasperExportFormat.PDF_FORMAT,parameters: reportParams)
        byte[] bytes
        bytes = jasperService.generateReport(reportDef).toByteArray()
        response.addHeader("Content-Disposition", 'attachment; filename="liquidacion_grupal_plomo_plata.pdf"')
        response.contentType = 'application/pdf'
        response.outputStream << bytes
        response.outputStream.flush()

        //return null
//        render(file: bytes, fileName: "book.pdf",contentType: 'application/pdf')
//        render [:] as JSON
    }

    def liquidar = {
        def s = "[${params.lote.toString()}]"
        def millis = params.millis.toBigDecimal()
        def loteAux = new JSONArray(s)
        def lotecito = loteAux[0]
        log.error("*************** LOTE A LIQUIDAR: ${loteAux}")
        log.error("${loteAux[0].getAt("lote")}: ${loteAux[0].getAt("totalLiquidoPagable")}")
        log.error("*************** millis: ${millis}")

        def recepcionDeComplejo = RecepcionDeComplejo.get(lotecito.getAt("recepcionId").toString().toLong())
        def deposito = Deposito.get(lotecito.getAt("depositoId").toString().toLong())
        def empresa  = Empresa.get(lotecito.getAt("empresaId").toString().toLong())
        def lote = lotecito.getAt("lote")
        def tipoDeMineral = lotecito.getAt("tipoDeMineral")
        def nombreDeposito  = lotecito.getAt("nombreDeposito")
        def nombreCliente = lotecito.getAt("nombreCliente")
        def nombreEmpresa = lotecito.getAt("nombreEmpresa")
        def fechaDeRecepcion = lotecito.getAt("fechaDeRecepcion")
        def cantidadDeSacos = lotecito.getAt("cantidadDeSacos")
        def estadoDelLote = lotecito.getAt("estadoDelLote")
        def pesoBruto = lotecito.getAt("pesoBruto").toString().toBigDecimal()
        def cotizacionDiariaDePlomo = lotecito.getAt("cotizacionDiariaDePlomo").toString().toBigDecimal()
        def cotizacionQuincenalDePlomo = lotecito.getAt("cotizacionQuincenalDePlomo").toString().toBigDecimal()
        def alicuotaDePlomo = lotecito.getAt("alicuotaDePlomo").toString().toBigDecimal()
        def cotizacionDiariaDePlata = lotecito.getAt("cotizacionDiariaDePlata").toString().toBigDecimal()
        def cotizacionQuincenalDePlata = lotecito.getAt("cotizacionQuincenalDePlata").toString().toBigDecimal()
        def alicuotaDePlata = lotecito.getAt("alicuotaDePlata").toString().toBigDecimal()
        def tipoDeCambioOficial = lotecito.getAt("tipoDeCambioOficial").toString().toBigDecimal()
        def tipoDeCambioComercial = lotecito.getAt("tipoDeCambioComercial").toString().toBigDecimal()
        def fechaDeLiquidacion = new java.util.Date()
        def kilosNetosHumedos = lotecito.getAt("kilosNetosHumedos").toString().toBigDecimal()
        def kilosNetosSecos = lotecito.getAt("kilosNetosSecos").toString().toBigDecimal()
        def dolarPuntoPlomo = 0
        def dolarPuntoPlata = 0
        def porcentajePlomoPromexbol  = lotecito.getAt("porcentajePlomoPromexbol").toString().toBigDecimal()
        def porcentajePlataPromexbol  = lotecito.getAt("porcentajePlataPromexbol").toString().toBigDecimal()
        def porcentajeHumedadPromexbol  = lotecito.getAt("porcentajeHumedadPromexbol").toString().toBigDecimal()
        def porcentajeMermaPromexbol  = lotecito.getAt("porcentajeMermaPromexbol").toString().toBigDecimal()
        def porcentajePlomoCliente  = lotecito.getAt("porcentajePlomoCliente").toString().toBigDecimal()
        def porcentajePlataCliente  = lotecito.getAt("porcentajePlataCliente").toString().toBigDecimal()
        def porcentajeHumedadCliente  = lotecito.getAt("porcentajeHumedadCliente").toString().toBigDecimal()
        def porcentajeMermaCliente  = lotecito.getAt("porcentajeMermaCliente").toString().toBigDecimal()
        def porcentajePlomoFinal = lotecito.getAt("porcentajePlomoFinal").toString().toBigDecimal()
        def porcentajePlataFinal = lotecito.getAt("porcentajePlataFinal").toString().toBigDecimal()
        def porcentajeHumedadFinal  = lotecito.getAt("porcentajeHumedadFinal").toString().toBigDecimal()
        def porcentajeMermaFinal  = lotecito.getAt("porcentajeMermaFinal").toString().toBigDecimal()
        def modoValoracion = lotecito.getAt("modoValoracion")
        def tablaComplejo = TablaOrigenCotizacionesComplejo.get(lotecito.getAt("tablaComplejoId").toString().toLong())
        def terminosDeContrato = TerminosDeContrato.get(lotecito.getAt("terminosDeContratoId").toString().toLong())
        def kilosFinosPlomo = lotecito.getAt("kilosFinosPlomo").toString().toBigDecimal()
        def kilosFinosPlata = lotecito.getAt("kilosFinosPlata").toString().toBigDecimal()
        def librasFinasDePlomo = lotecito.getAt("librasFinasDePlomo").toString().toBigDecimal()
        def onzasTroyDePlata = lotecito.getAt("onzasTroyDePlata").toString().toBigDecimal()
        def valorOficialBrutoDePlomo = lotecito.getAt("valorOficialBrutoDePlomo").toString().toBigDecimal()
        def valorOficialBrutoDePlata = lotecito.getAt("valorOficialBrutoDePlata").toString().toBigDecimal()
        def valorOficialBrutoDePlomoEnBolivianos = lotecito.getAt("valorOficialBrutoDePlomoEnBolivianos").toString().toBigDecimal()
        def valorOficialBrutoDePlataEnBolivianos = lotecito.getAt("valorOficialBrutoDePlataEnBolivianos").toString().toBigDecimal()
        def valorOficialBruto = lotecito.getAt("valorOficialBruto").toString().toBigDecimal()
        def valorPorTonelada = lotecito.getAt("valorPorTonelada").toString().toBigDecimal()
        def margen  = lotecito.getAt("margen").toString().toBigDecimal()
        def regaliaMinera = lotecito.getAt("regaliaMinera").toString().toBigDecimal()
        def retenciones = lotecito.getAt("retenciones")
        def valorNetoMineral = lotecito.getAt("valorNetoMineral").toString().toBigDecimal()
        def valorNetoMineralEnBolivianos = lotecito.getAt("valorNetoMineralEnBolivianos").toString().toBigDecimal()
        def bonoCalidad = lotecito.getAt("bonoCalidad").toString().toBigDecimal()
        def bonoIncentivo = lotecito.getAt("bonoIncentivo").toString().toBigDecimal()
        def valorDeCompra = lotecito.getAt("valorDeCompra").toString().toBigDecimal()
        def totalRetenciones = lotecito.getAt("totalRetenciones").toString().toBigDecimal()
        def totalPagado = lotecito.getAt("totalPagado").toString().toBigDecimal()
        def anticipoPorPagar  = lotecito.getAt("anticipoPorPagar").toString().toBigDecimal()
        def totalAnticiposContraEntrega = lotecito.getAt("totalAnticiposContraEntrega").toString().toBigDecimal()
        def totalAnticiposContraFuturaEntrega = lotecito.getAt("totalAnticiposContraFuturaEntrega").toString().toBigDecimal()
        def adelantoPorLiquidacionProvisional = 0
        //por 1ra vez liquidado ambas variables tienen el mismo valor y la diferencia es 0
        def totalLiquidoPagable = lotecito.getAt("totalLiquidoPagable").toString().toBigDecimal()

        def conversor = new NumeroALiteral()
        def totalLiquidoPagableLiteral = conversor.Convertir(totalLiquidoPagable.toString(),true)

        def totalLiquidoPagableOriginal = lotecito.getAt("totalLiquidoPagable").toString().toBigDecimal()
        def diferenciaLiquidoPagable = 0
        def observaciones  = lotecito.getAt("observaciones")
        def motivoDeModificacion = lotecito.getAt("motivoDeModificacion")
        def detalleLaboratorio1 = lotecito.getAt("detalleLaboratorio1")
        def totalCostoLaboratorio = lotecito.getAt("totalCostoLaboratorio").toString().toBigDecimal()

        def liquidacionDePlomoPlata = new LiquidacionDePlomoPlata(
                recepcionDeComplejo:recepcionDeComplejo,
                deposito:deposito,
                empresa:empresa,
                lote:lote,
                conjuntoPlomoPlata: "-",
                tipoDeMineral:tipoDeMineral,
                nombreDeposito:nombreDeposito,
                nombreCliente:nombreCliente,
                nombreEmpresa:nombreEmpresa,
                fechaDeRecepcion: fechaDeRecepcion,
                cantidadDeSacos:cantidadDeSacos,
                estadoDelLote:estadoDelLote,
                pesoBruto:pesoBruto,
                cotizacionDiariaDePlomo:cotizacionDiariaDePlomo,
                cotizacionQuincenalDePlomo:cotizacionQuincenalDePlomo,
                alicuotaDePlomo:alicuotaDePlomo,
                cotizacionDiariaDePlata:cotizacionDiariaDePlata,
                cotizacionQuincenalDePlata:cotizacionQuincenalDePlata,
                alicuotaDePlata:alicuotaDePlata,
                tipoDeCambioOficial:tipoDeCambioOficial,
                tipoDeCambioComercial:tipoDeCambioComercial,
                fechaDeLiquidacion:fechaDeLiquidacion,
                kilosNetosHumedos:kilosNetosHumedos,
                kilosNetosSecos:kilosNetosSecos,
                dolarPuntoPlomo:dolarPuntoPlomo,
                dolarPuntoPlata:dolarPuntoPlata,
                porcentajePlomoPromexbol :porcentajePlomoPromexbol ,
                porcentajePlataPromexbol :porcentajePlataPromexbol ,
                porcentajeHumedadPromexbol :porcentajeHumedadPromexbol ,
                porcentajeMermaPromexbol :porcentajeMermaPromexbol ,
                porcentajePlomoCliente :porcentajePlomoCliente ,
                porcentajePlataCliente :porcentajePlataCliente ,
                porcentajeHumedadCliente :porcentajeHumedadCliente ,
                porcentajeMermaCliente :porcentajeMermaCliente ,
                porcentajePlomoFinal:porcentajePlomoFinal,
                porcentajePlataFinal:porcentajePlataFinal,
                porcentajeHumedadFinal :porcentajeHumedadFinal ,
                porcentajeMermaFinal :porcentajeMermaFinal ,
                merma :porcentajeMermaFinal ,
                humedad :porcentajeHumedadFinal ,
                porcentajePlomo:porcentajePlomoFinal,
                porcentajePlata:porcentajePlataFinal,
                modoValoracion:modoValoracion,
                tablaComplejo:tablaComplejo,
                terminosDeContrato:terminosDeContrato,
                kilosFinosPlomo:kilosFinosPlomo,
                kilosFinosPlata:kilosFinosPlata,
                librasFinasDePlomo:librasFinasDePlomo,
                onzasTroyDePlata:onzasTroyDePlata,
                valorOficialBrutoDePlomo:valorOficialBrutoDePlomo,
                valorOficialBrutoDePlata:valorOficialBrutoDePlata,
                valorOficialBrutoDePlomoEnBolivianos:valorOficialBrutoDePlomoEnBolivianos,
                valorOficialBrutoDePlataEnBolivianos:valorOficialBrutoDePlataEnBolivianos,
                valorOficialBruto:valorOficialBruto,
                valorPorTonelada:valorPorTonelada,
                margen :margen ,
                porcentajeRegalia: 0,
                regaliaMinera:regaliaMinera,
                retenciones:retenciones,
                valorNetoMineral:valorNetoMineral,
                valorNetoMineralEnBolivianos:valorNetoMineralEnBolivianos,
                bonoCalidad:bonoCalidad,
                bonoIncentivo:bonoIncentivo,
                valorDeCompra:valorDeCompra,
                totalRetenciones:totalRetenciones,
                totalPagado:totalPagado,
                anticipoPorPagar :anticipoPorPagar ,
                totalAnticiposContraEntrega:totalAnticiposContraEntrega,
                totalAnticiposContraFuturaEntrega:totalAnticiposContraFuturaEntrega,
                adelantoPorLiquidacionProvisional:adelantoPorLiquidacionProvisional,
                totalLiquidoPagable:totalLiquidoPagable,
                totalLiquidoPagableLiteral: totalLiquidoPagableLiteral,
                totalLiquidoPagableOriginal:totalLiquidoPagableOriginal,
                diferenciaLiquidoPagable:diferenciaLiquidoPagable,
                observaciones :observaciones ,
                motivoDeModificacion:motivoDeModificacion,
                detalleLaboratorio1:detalleLaboratorio1,
                totalCostoLaboratorio:totalCostoLaboratorio
        )
        liquidacionDePlomoPlata.save(failOnError: true)

        def liquidacionGrupalDePlomoPlataDetalle = new LiquidacionGrupalDePlomoPlataDetalle(
                millis: millis,
                liquidacionDePlomoPlata: liquidacionDePlomoPlata
        )
        liquidacionGrupalDePlomoPlataDetalle.save(failOnError: true)
        render [:] as JSON
    }
}
