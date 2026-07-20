package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import grails.plugins.jasper.JasperExportFormat
import grails.plugins.jasper.JasperReportDef
import org.grails.web.json.JSONArray
import org.socymet.anticipos.AnticipoDetalle
import org.socymet.cotizaciones.TablaPreciosCobre
import org.socymet.cotizaciones.TerminosDeContrato
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.utilidades.NumeroALiteral
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class LiquidacionDeCobrePlataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def jasperService

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [liquidacionDeCobrePlataInstanceList: LiquidacionDeCobrePlata.list(params), liquidacionDeCobrePlataInstanceTotal: LiquidacionDeCobrePlata.count()]
    }

    def create() {
        [liquidacionDeCobrePlataInstance: new LiquidacionDeCobrePlata(params)]
    }

    def save() {
        def liquidacionDeCobrePlataInstance = new LiquidacionDeCobrePlata(params)
        if (!liquidacionDeCobrePlataInstance.save(flush: true)) {
            render(view: "create", model: [liquidacionDeCobrePlataInstance: liquidacionDeCobrePlataInstance])
            return
        }

//        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionDeCobrePlata.label', default: 'LiquidacionDeCobrePlata'), liquidacionDeCobrePlataInstance.id])
        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionDeCobrePlata.label', default: 'LiquidacionDeCobrePlata'), liquidacionDeCobrePlataInstance.lote])
        redirect(action: "show", id: liquidacionDeCobrePlataInstance.id)
    }

    def show(Long id) {
        def liquidacionDeCobrePlataInstance = LiquidacionDeCobrePlata.get(id)
        if (!liquidacionDeCobrePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeCobrePlata.label', default: 'LiquidacionDeCobrePlata'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDeCobrePlataInstance: liquidacionDeCobrePlataInstance]
    }

    def edit(Long id) {
        def liquidacionDeCobrePlataInstance = LiquidacionDeCobrePlata.get(id)
        if (!liquidacionDeCobrePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeCobrePlata.label', default: 'LiquidacionDeCobrePlata'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDeCobrePlataInstance: liquidacionDeCobrePlataInstance]
    }

    def update(Long id, Long version) {
        def liquidacionDeCobrePlataInstance = LiquidacionDeCobrePlata.get(id)
        if (!liquidacionDeCobrePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeCobrePlata.label', default: 'LiquidacionDeCobrePlata'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (liquidacionDeCobrePlataInstance.version > version) {
                liquidacionDeCobrePlataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'liquidacionDeCobrePlata.label', default: 'LiquidacionDeCobrePlata')] as Object[],
                        "Another user has updated this LiquidacionDeCobrePlata while you were editing")
                render(view: "edit", model: [liquidacionDeCobrePlataInstance: liquidacionDeCobrePlataInstance])
                return
            }
        }

        liquidacionDeCobrePlataInstance.properties = params

        if (!liquidacionDeCobrePlataInstance.save(flush: true)) {
            render(view: "edit", model: [liquidacionDeCobrePlataInstance: liquidacionDeCobrePlataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'liquidacionDeCobrePlata.label', default: 'LiquidacionDeCobrePlata'), liquidacionDeCobrePlataInstance.id])
        redirect(action: "show", id: liquidacionDeCobrePlataInstance.id)
    }

    def delete(Long id) {
        def liquidacionDeCobrePlataInstance = LiquidacionDeCobrePlata.get(id)
        if (!liquidacionDeCobrePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeCobrePlata.label', default: 'LiquidacionDeCobrePlata'), id])
            redirect(action: "list")
            return
        }

        try {
            /* CAMBIAR EL ESTADO DEL LOTE */
            def recepcion = RecepcionDeComplejo.get(liquidacionDeCobrePlataInstance.recepcionDeComplejo.id)
            recepcion.estadoDelLote = "NO LIQUIDADO"
            recepcion.save()
            /* FIN - CAMBIAR EL ESTADO DEL LOTE*/

            /* ELIMINAR LAS RETENCIONES*/
            def listaRetenciones = LiquidacionDeCobrePlataRetenciones.findAllByLiquidacionDeCobrePlata(liquidacionDeCobrePlataInstance)
            listaRetenciones.each {
                it.delete(flush: true)
            }
            /* FIN - ELIMINAR RETENCIONES*/

            /* ELIMINAR RETENCIONES POR PAGAR*/
            def retencionesPorPagarAnteriores = RetencionPorPagarComplejo.findAllByLiquidacionId(liquidacionDeCobrePlataInstance.id)
            retencionesPorPagarAnteriores.each {
                it.delete()
            }
            /* FIN - ELIMINAR RETENCIONES*/

            /* ELIMINAR LOS ANTICIPOS CONTRA ENTREGA REALIZADOS*/
            def anticipoDetalle = AnticipoDetalle.findByRecepcionId(recepcion.id)
            if(anticipoDetalle){
                def anticipo = anticipoDetalle.anticipo
                def nuevoAnticipoPorPagar = anticipo.totalPorPagar + liquidacionDeCobrePlataInstance.totalAnticiposContraEntrega
                anticipo.totalPagado = anticipo.totalPagado - liquidacionDeCobrePlataInstance.totalAnticiposContraEntrega
                anticipo.totalPorPagar = nuevoAnticipoPorPagar
                anticipo.save(failOnError: true)

                anticipoDetalle.anticipoPagable=liquidacionDeCobrePlataInstance.totalAnticiposContraEntrega
                anticipoDetalle.estadoAnticipo="SIN PAGAR"
                anticipoDetalle.save(failOnError: true)

                recepcion.estadoAnticipo = "CON ANTICIPO"
                recepcion.save(failOnError: true)
            }
            /* FIN - ELIMINAR LOS ANTICIPOS CONTRA ENTREGA REALIZADOS*/
            
            liquidacionDeCobrePlataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'liquidacionDeCobrePlata.label', default: 'LiquidacionDeCobrePlata'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDeCobrePlata.label', default: 'LiquidacionDeCobrePlata'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporte = {
        def realPath = org.socymet.util.ReportesRuntime.realPath("/reports/images/")
        params.realPath = realPath+"/"
        params.SUBREPORT_DIR = "${org.socymet.util.ReportesRuntime.realPath('/reports')}/"
        chain(controller:'jasper',action:'index',params:params)
    }

    def crearReporteGrupal2 = {
        Map reportParams = [:]
        def millis = params.millis.toBigDecimal()
        def realPath = org.socymet.util.ReportesRuntime.realPath("/reports/images/")
        reportParams.put("millis",millis)
        reportParams.put("realPath",realPath+"/")
        reportParams.put("SUBREPORT_DIR","${org.socymet.util.ReportesRuntime.realPath('/reports')}/")

        def reportDef = new JasperReportDef(name:'liquidacion_grupal_cobre_plata.jasper',fileFormat:JasperExportFormat.PDF_FORMAT,parameters: reportParams)
        byte[] bytes
        bytes = jasperService.generateReport(reportDef).toByteArray()
        response.addHeader("Content-Disposition", 'attachment; filename="liquidacion_grupal_cobre_plata.pdf"')
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
        def cotizacionDiariaDeCobre = lotecito.getAt("cotizacionDiariaDeCobre").toString().toBigDecimal()
        def cotizacionQuincenalDeCobre = lotecito.getAt("cotizacionQuincenalDeCobre").toString().toBigDecimal()
        def alicuotaDeCobre = lotecito.getAt("alicuotaDeCobre").toString().toBigDecimal()
        def cotizacionDiariaDePlata = lotecito.getAt("cotizacionDiariaDePlata").toString().toBigDecimal()
        def cotizacionQuincenalDePlata = lotecito.getAt("cotizacionQuincenalDePlata").toString().toBigDecimal()
        def alicuotaDePlata = lotecito.getAt("alicuotaDePlata").toString().toBigDecimal()
        def tipoDeCambioOficial = lotecito.getAt("tipoDeCambioOficial").toString().toBigDecimal()
        def tipoDeCambioComercial = lotecito.getAt("tipoDeCambioComercial").toString().toBigDecimal()
        def fechaDeLiquidacion = new java.util.Date()
        def kilosNetosHumedos = lotecito.getAt("kilosNetosHumedos").toString().toBigDecimal()
        def kilosNetosSecos = lotecito.getAt("kilosNetosSecos").toString().toBigDecimal()
        def dolarPuntoCobre = 0
        def dolarPuntoPlata = 0
        def porcentajeCobrePromexbol  = lotecito.getAt("porcentajeCobrePromexbol").toString().toBigDecimal()
        def porcentajePlataPromexbol  = lotecito.getAt("porcentajePlataPromexbol").toString().toBigDecimal()
        def porcentajeHumedadPromexbol  = lotecito.getAt("porcentajeHumedadPromexbol").toString().toBigDecimal()
        def porcentajeMermaPromexbol  = lotecito.getAt("porcentajeMermaPromexbol").toString().toBigDecimal()
        def porcentajeCobreCliente  = lotecito.getAt("porcentajeCobreCliente").toString().toBigDecimal()
        def porcentajePlataCliente  = lotecito.getAt("porcentajePlataCliente").toString().toBigDecimal()
        def porcentajeHumedadCliente  = lotecito.getAt("porcentajeHumedadCliente").toString().toBigDecimal()
        def porcentajeMermaCliente  = lotecito.getAt("porcentajeMermaCliente").toString().toBigDecimal()
        def porcentajeCobreFinal = lotecito.getAt("porcentajeCobreFinal").toString().toBigDecimal()
        def porcentajePlataFinal = lotecito.getAt("porcentajePlataFinal").toString().toBigDecimal()
        def porcentajeHumedadFinal  = lotecito.getAt("porcentajeHumedadFinal").toString().toBigDecimal()
        def porcentajeMermaFinal  = lotecito.getAt("porcentajeMermaFinal").toString().toBigDecimal()
        def modoValoracion = lotecito.getAt("modoValoracion")
        def tablaComplejo = TablaPreciosCobre.get(lotecito.getAt("tablaCobreId").toString().toLong())
        def terminosDeContrato = TerminosDeContrato.get(lotecito.getAt("terminosDeContratoId").toString().toLong())
        def kilosFinosCobre = lotecito.getAt("kilosFinosCobre").toString().toBigDecimal()
        def kilosFinosPlata = lotecito.getAt("kilosFinosPlata").toString().toBigDecimal()
        def librasFinasDeCobre = lotecito.getAt("librasFinasDeCobre").toString().toBigDecimal()
        def onzasTroyDePlata = lotecito.getAt("onzasTroyDePlata").toString().toBigDecimal()
        def valorOficialBrutoDeCobre = lotecito.getAt("valorOficialBrutoDeCobre").toString().toBigDecimal()
        def valorOficialBrutoDePlata = lotecito.getAt("valorOficialBrutoDePlata").toString().toBigDecimal()
        def valorOficialBrutoDeCobreEnBolivianos = lotecito.getAt("valorOficialBrutoDeCobreEnBolivianos").toString().toBigDecimal()
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

        def liquidacionDeCobrePlata = new LiquidacionDeCobrePlata(
                recepcionDeComplejo:recepcionDeComplejo,
                deposito:deposito,
                empresa:empresa,
                lote:lote,
                conjuntoCobrePlata: "-",
                tipoDeMineral:tipoDeMineral,
                nombreDeposito:nombreDeposito,
                nombreCliente:nombreCliente,
                nombreEmpresa:nombreEmpresa,
                fechaDeRecepcion: fechaDeRecepcion,
                cantidadDeSacos:cantidadDeSacos,
                estadoDelLote:estadoDelLote,
                pesoBruto:pesoBruto,
                cotizacionDiariaDeCobre:cotizacionDiariaDeCobre,
                cotizacionQuincenalDeCobre:cotizacionQuincenalDeCobre,
                alicuotaDeCobre:alicuotaDeCobre,
                cotizacionDiariaDePlata:cotizacionDiariaDePlata,
                cotizacionQuincenalDePlata:cotizacionQuincenalDePlata,
                alicuotaDePlata:alicuotaDePlata,
                tipoDeCambioOficial:tipoDeCambioOficial,
                tipoDeCambioComercial:tipoDeCambioComercial,
                fechaDeLiquidacion:fechaDeLiquidacion,
                kilosNetosHumedos:kilosNetosHumedos,
                kilosNetosSecos:kilosNetosSecos,
                dolarPuntoCobre:dolarPuntoCobre,
                dolarPuntoPlata:dolarPuntoPlata,
                porcentajeCobrePromexbol :porcentajeCobrePromexbol ,
                porcentajePlataPromexbol :porcentajePlataPromexbol ,
                porcentajeHumedadPromexbol :porcentajeHumedadPromexbol ,
                porcentajeMermaPromexbol :porcentajeMermaPromexbol ,
                porcentajeCobreCliente :porcentajeCobreCliente ,
                porcentajePlataCliente :porcentajePlataCliente ,
                porcentajeHumedadCliente :porcentajeHumedadCliente ,
                porcentajeMermaCliente :porcentajeMermaCliente ,
                porcentajeCobreFinal:porcentajeCobreFinal,
                porcentajePlataFinal:porcentajePlataFinal,
                porcentajeHumedadFinal :porcentajeHumedadFinal ,
                porcentajeMermaFinal :porcentajeMermaFinal ,
                merma :porcentajeMermaFinal ,
                humedad :porcentajeHumedadFinal ,
                porcentajeCobre:porcentajeCobreFinal,
                porcentajePlata:porcentajePlataFinal,
                modoValoracion:modoValoracion,
                tablaCobre: tablaComplejo,
                terminosDeContrato:terminosDeContrato,
                kilosFinosCobre:kilosFinosCobre,
                kilosFinosPlata:kilosFinosPlata,
                librasFinasDeCobre:librasFinasDeCobre,
                onzasTroyDePlata:onzasTroyDePlata,
                valorOficialBrutoDeCobre:valorOficialBrutoDeCobre,
                valorOficialBrutoDePlata:valorOficialBrutoDePlata,
                valorOficialBrutoDeCobreEnBolivianos:valorOficialBrutoDeCobreEnBolivianos,
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
        liquidacionDeCobrePlata.save(failOnError: true)

        def liquidacionGrupalDeCobrePlataDetalle = new LiquidacionGrupalDeCobrePlataDetalle(
                millis: millis,
                liquidacionDeCobrePlata: liquidacionDeCobrePlata
        )
        liquidacionGrupalDeCobrePlataDetalle.save(failOnError: true)
        render [:] as JSON
    }
}
