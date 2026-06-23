package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import grails.plugins.jasper.JasperExportFormat
import grails.plugins.jasper.JasperReportDef
import org.grails.web.json.JSONArray
import org.socymet.anticipos.AnticipoDetalle
import org.socymet.cotizaciones.TablaOrigenCotizacionesComplejo
import org.socymet.cotizaciones.TerminosDeContrato
import org.socymet.proveedor.Cliente
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.seguridad.SecUser
import org.socymet.utilidades.NumeroALiteral
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class LiquidacionDeComplejoController {
    def springSecurityService
    def jasperService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
//        params.max = Math.min(max ?: 10, 100)
//        [liquidacionDeComplejoInstanceList: LiquidacionDeComplejo.list(params), liquidacionDeComplejoInstanceTotal: LiquidacionDeComplejo.count()]

        params.max = Math.min(max ?: 10, 100)
        params.sort = "id"
        params.order = "desc"

        def modoBusqueda = params.modoBusqueda.toString()

        def lista
        def tamano

        if(modoBusqueda.equals("null")||modoBusqueda.equals('-TODOS-'))
            respond LiquidacionDeComplejo.list(params), model:[liquidacionDeComplejoInstanceCount: LiquidacionDeComplejo.count()]
        if(modoBusqueda.equals('CLIENTE')){
            def cliente = Cliente.get(params.clienteId)
            lista = LiquidacionDeComplejo.findAllByNombreClienteLike("%${cliente.nombre}%", params)
            tamano = LiquidacionDeComplejo.findAllByNombreClienteLike("%${cliente.nombre}%").size()
            respond lista, model:[liquidacionDeComplejoInstanceCount: tamano]
        }
        if(modoBusqueda.equals('EMPRESA')){
            def empresa = Empresa.get(params.empresaId)
            lista = LiquidacionDeComplejo.findAllByNombreEmpresaLike("%${empresa.nombreDeEmpresa}%", params)
            tamano = LiquidacionDeComplejo.findAllByNombreEmpresaLike("%${empresa.nombreDeEmpresa}%").size()
            respond lista, model:[liquidacionDeComplejoInstanceCount: tamano]
        }
    }

    @Secured(['ROLE_ADMIN','ROLE_LIQUIDACION'])
    def create() {
        [liquidacionDeComplejoInstance: new LiquidacionDeComplejo(params)]
    }

    @Secured(['ROLE_ADMIN','ROLE_LIQUIDACION'])
    def save() {
        def liquidacionDeComplejoInstance = new LiquidacionDeComplejo(params)
        if (!liquidacionDeComplejoInstance.save(flush: true)) {
            render(view: "create", model: [liquidacionDeComplejoInstance: liquidacionDeComplejoInstance])
            return
        }

//        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionDeComplejo.label', default: 'LiquidacionDeComplejo'), liquidacionDeComplejoInstance.id])
        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionDeComplejo.label', default: 'LiquidacionDeComplejo'), liquidacionDeComplejoInstance.lote])
        redirect(action: "show", id: liquidacionDeComplejoInstance.id)
    }

    def show(Long id) {
        def liquidacionDeComplejoInstance = LiquidacionDeComplejo.get(id)
        if (!liquidacionDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeComplejo.label', default: 'LiquidacionDeComplejo'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDeComplejoInstance: liquidacionDeComplejoInstance]
    }

    @Secured(['ROLE_ADMIN','ROLE_LIQUIDACION'])
    def edit(Long id) {
        def liquidacionDeComplejoInstance = LiquidacionDeComplejo.get(id)
        if (!liquidacionDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeComplejo.label', default: 'LiquidacionDeComplejo'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDeComplejoInstance: liquidacionDeComplejoInstance]
    }

    def update(Long id, Long version) {
        def liquidacionDeComplejoInstance = LiquidacionDeComplejo.get(id)
        if (!liquidacionDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeComplejo.label', default: 'LiquidacionDeComplejo'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (liquidacionDeComplejoInstance.version > version) {
                liquidacionDeComplejoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'liquidacionDeComplejo.label', default: 'LiquidacionDeComplejo')] as Object[],
                          "Another user has updated this LiquidacionDeComplejo while you were editing")
                render(view: "edit", model: [liquidacionDeComplejoInstance: liquidacionDeComplejoInstance])
                return
            }
        }

        liquidacionDeComplejoInstance.properties = params

        if (!liquidacionDeComplejoInstance.save(flush: true)) {
            render(view: "edit", model: [liquidacionDeComplejoInstance: liquidacionDeComplejoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'liquidacionDeComplejo.label', default: 'LiquidacionDeComplejo'), liquidacionDeComplejoInstance.id])
        redirect(action: "show", id: liquidacionDeComplejoInstance.id)
    }

    @Secured(['ROLE_ADMIN','ROLE_LIQUIDACION'])
    def delete(Long id) {
        def liquidacionDeComplejoInstance = LiquidacionDeComplejo.get(id)
        if (!liquidacionDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeComplejo.label', default: 'LiquidacionDeComplejo'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionDeComplejoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'liquidacionDeComplejo.label', default: 'LiquidacionDeComplejo'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDeComplejo.label', default: 'LiquidacionDeComplejo'), id])
            redirect(action: "show", id: id)
        }
    }

    def eliminarDeConjunto(Long id) {
        def liquidacionDeComplejoInstance = LiquidacionDeComplejo.get(id)
        if (!liquidacionDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeComplejo.label', default: 'LiquidacionDeComplejo'), id])
            redirect(action: "list")
            return
        }

        try {
            System.out.println("*** LOCALIZADO LOTE: ${liquidacionDeComplejoInstance.lote} DEL CONJUNTO: ${liquidacionDeComplejoInstance.conjuntoComplejo}")
            liquidacionDeComplejoInstance.conjuntoComplejo="-"
            liquidacionDeComplejoInstance.save(failOnError: true)
            flash.message = "El Lote ha sido eliminado del Conjunto anteriormente asignado."
            redirect(action: "show", id: id)
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDeComplejo.label', default: 'LiquidacionDeComplejo'), id])
            redirect(action: "show", id: id)
        }
    }

    def cancelacionDeLote(Long id) {
        def liquidacionDeComplejoInstance = LiquidacionDeComplejo.get(id)
        if (!liquidacionDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeComplejo.label', default: 'LiquidacionDeComplejo'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionDeComplejoInstance.fechaDeCancelacion=new java.util.Date()
            liquidacionDeComplejoInstance.save(failOnError: true)
            flash.message = "Se ha establecido la Fecha de Cancelacion del Lote"
            redirect(action: "show", id: id)
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDeComplejo.label', default: 'LiquidacionDeComplejo'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporte = {
        def realPath = servletContext.getRealPath("/reports/images/")
        params.realPath = realPath+"/"
        params.SUBREPORT_DIR = "${servletContext.getRealPath('/reports')}/"
        chain(controller:'jasper',action:'index',params:params)
    }

    def crearReporteGrupal2 = {
        Map reportParams = [:]
        def millis = params.millis.toBigDecimal()
        def realPath = servletContext.getRealPath("/reports/images/")
        reportParams.put("millis",millis)
        reportParams.put("realPath",realPath+"/")
        reportParams.put("SUBREPORT_DIR","${servletContext.getRealPath('/reports')}/")

        def reportDef = new JasperReportDef(name:'liquidacion_grupal_complejo.jasper',fileFormat:JasperExportFormat.PDF_FORMAT,parameters: reportParams)
        byte[] bytes
        bytes = jasperService.generateReport(reportDef).toByteArray()
        response.addHeader("Content-Disposition", 'attachment; filename="liquidacion_grupal_complejo.pdf"')
        response.contentType = 'application/pdf'
        response.outputStream << bytes
        response.outputStream.flush()

        //return null
//        render(file: bytes, fileName: "book.pdf",contentType: 'application/pdf')
//        render [:] as JSON
    }

    def crearReporteGrupal = {
        def realPath = servletContext.getRealPath("/reports/images/")
        params.realPath = realPath+"/"
        params.SUBREPORT_DIR = "${servletContext.getRealPath('/reports')}/"
        chain(controller:'jasper',action:'index',params:params)
    }

    def liquidacionesJSON() {
        def lote = params.term.toString()
        def liquidacionDeComplejos = LiquidacionDeComplejo.findAllByLoteLikeAndConjuntoComplejo("%${lote}%","-")
        def liquidacionesList = []
        liquidacionDeComplejos.each { liquidacion ->
            def liquidacionMap = [:]
            liquidacionMap.put("liquidacionId", liquidacion.id)
            liquidacionMap.put("label", liquidacion.lote)
            liquidacionMap.put("value", liquidacion.lote)
            liquidacionMap.put("nombreCliente", liquidacion.nombreCliente)
            liquidacionMap.put("nombreEmpresa", liquidacion.nombreEmpresa)
            liquidacionMap.put("fechaDeRecepcion", liquidacion.fechaDeRecepcion)
            liquidacionMap.put("fechaDeLiquidacion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(liquidacion.fechaDeLiquidacion))
            liquidacionMap.put("kilosNetosSecos", liquidacion.kilosNetosSecos)
            liquidacionMap.put("porcentajeZinc", liquidacion.porcentajeZinc)
            liquidacionMap.put("porcentajePlomo", liquidacion.porcentajePlomo)
            liquidacionMap.put("porcentajePlata", liquidacion.porcentajePlata)
            liquidacionesList.add(liquidacionMap)
        }
        render liquidacionesList as JSON
    }

    def lotesEnConjuntoJSON() {
        def lote = params.term.toString()
        def liquidacionDeComplejos = LiquidacionDeComplejo.findAllByLoteLikeAndConjuntoComplejoNotEqual("%${lote}%","-")
        def liquidacionesList = []
        liquidacionDeComplejos.each { liquidacion ->
            def liquidacionMap = [:]
            liquidacionMap.put("liquidacionId", liquidacion.id)
            liquidacionMap.put("label", liquidacion.lote)
            liquidacionMap.put("value", liquidacion.lote)
            liquidacionMap.put("nombreCliente", liquidacion.nombreCliente)
            liquidacionMap.put("nombreEmpresa", liquidacion.nombreEmpresa)
            liquidacionMap.put("fechaDeRecepcion", liquidacion.fechaDeRecepcion)
            liquidacionMap.put("fechaDeLiquidacion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(liquidacion.fechaDeLiquidacion))
            liquidacionMap.put("kilosNetosSecos", liquidacion.kilosNetosSecos)
            liquidacionMap.put("porcentajeZinc", liquidacion.porcentajeZinc)
            liquidacionMap.put("porcentajePlomo", liquidacion.porcentajePlomo)
            liquidacionMap.put("porcentajePlata", liquidacion.porcentajePlata)
            liquidacionMap.put("conjuntoComplejo", liquidacion.conjuntoComplejo)
            liquidacionesList.add(liquidacionMap)
        }
        render liquidacionesList as JSON
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
        def cotizacionDiariaDeZinc = lotecito.getAt("cotizacionDiariaDeZinc").toString().toBigDecimal()
        def cotizacionQuincenalDeZinc = lotecito.getAt("cotizacionQuincenalDeZinc").toString().toBigDecimal()
        def alicuotaDeZinc = lotecito.getAt("alicuotaDeZinc").toString().toBigDecimal()
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
        def dolarPuntoZinc = 0
        def dolarPuntoPlomo = 0
        def dolarPuntoPlata = 0
        def porcentajeZincPromexbol  = lotecito.getAt("porcentajeZincPromexbol").toString().toBigDecimal()
        def porcentajePlomoPromexbol  = lotecito.getAt("porcentajePlomoPromexbol").toString().toBigDecimal()
        def porcentajePlataPromexbol  = lotecito.getAt("porcentajePlataPromexbol").toString().toBigDecimal()
        def porcentajeHumedadPromexbol  = lotecito.getAt("porcentajeHumedadPromexbol").toString().toBigDecimal()
        def porcentajeMermaPromexbol  = lotecito.getAt("porcentajeMermaPromexbol").toString().toBigDecimal()
        def porcentajeZincCliente  = lotecito.getAt("porcentajeZincCliente").toString().toBigDecimal()
        def porcentajePlomoCliente  = lotecito.getAt("porcentajePlomoCliente").toString().toBigDecimal()
        def porcentajePlataCliente  = lotecito.getAt("porcentajePlataCliente").toString().toBigDecimal()
        def porcentajeHumedadCliente  = lotecito.getAt("porcentajeHumedadCliente").toString().toBigDecimal()
        def porcentajeMermaCliente  = lotecito.getAt("porcentajeMermaCliente").toString().toBigDecimal()
        def porcentajeZincFinal = lotecito.getAt("porcentajeZincFinal").toString().toBigDecimal()
        def porcentajePlomoFinal = lotecito.getAt("porcentajePlomoFinal").toString().toBigDecimal()
        def porcentajePlataFinal = lotecito.getAt("porcentajePlataFinal").toString().toBigDecimal()
        def porcentajeHumedadFinal  = lotecito.getAt("porcentajeHumedadFinal").toString().toBigDecimal()
        def porcentajeMermaFinal  = lotecito.getAt("porcentajeMermaFinal").toString().toBigDecimal()
        def modoValoracion = lotecito.getAt("modoValoracion")
        def tablaComplejo = TablaOrigenCotizacionesComplejo.get(lotecito.getAt("tablaComplejoId").toString().toLong())
        def terminosDeContrato = TerminosDeContrato.get(lotecito.getAt("terminosDeContratoId").toString().toLong())
        def kilosFinosZinc = lotecito.getAt("kilosFinosZinc").toString().toBigDecimal()
        def kilosFinosPlomo = lotecito.getAt("kilosFinosPlomo").toString().toBigDecimal()
        def kilosFinosPlata = lotecito.getAt("kilosFinosPlata").toString().toBigDecimal()
        def librasFinasDeZinc = lotecito.getAt("librasFinasDeZinc").toString().toBigDecimal()
        def librasFinasDePlomo = lotecito.getAt("librasFinasDePlomo").toString().toBigDecimal()
        def onzasTroyDePlata = lotecito.getAt("onzasTroyDePlata").toString().toBigDecimal()
        def valorOficialBrutoDeZinc = lotecito.getAt("valorOficialBrutoDeZinc").toString().toBigDecimal()
        def valorOficialBrutoDePlomo = lotecito.getAt("valorOficialBrutoDePlomo").toString().toBigDecimal()
        def valorOficialBrutoDePlata = lotecito.getAt("valorOficialBrutoDePlata").toString().toBigDecimal()
        def valorOficialBrutoDeZincEnBolivianos = lotecito.getAt("valorOficialBrutoDeZincEnBolivianos").toString().toBigDecimal()
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

        def liquidacionDeComplejo = new LiquidacionDeComplejo(
                recepcionDeComplejo:recepcionDeComplejo,
                deposito:deposito,
                empresa:empresa,
                lote:lote,
                conjuntoComplejo: "-",
                tipoDeMineral:tipoDeMineral,
                nombreDeposito:nombreDeposito,
                nombreCliente:nombreCliente,
                nombreEmpresa:nombreEmpresa,
                fechaDeRecepcion: fechaDeRecepcion,
                cantidadDeSacos:cantidadDeSacos,
                estadoDelLote:estadoDelLote,
                pesoBruto:pesoBruto,
                cotizacionDiariaDeZinc:cotizacionDiariaDeZinc,
                cotizacionQuincenalDeZinc:cotizacionQuincenalDeZinc,
                alicuotaDeZinc:alicuotaDeZinc,
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
                dolarPuntoZinc:dolarPuntoZinc,
                dolarPuntoPlomo:dolarPuntoPlomo,
                dolarPuntoPlata:dolarPuntoPlata,
                porcentajeZincPromexbol :porcentajeZincPromexbol ,
                porcentajePlomoPromexbol :porcentajePlomoPromexbol ,
                porcentajePlataPromexbol :porcentajePlataPromexbol ,
                porcentajeHumedadPromexbol :porcentajeHumedadPromexbol ,
                porcentajeMermaPromexbol :porcentajeMermaPromexbol ,
                porcentajeZincCliente :porcentajeZincCliente ,
                porcentajePlomoCliente :porcentajePlomoCliente ,
                porcentajePlataCliente :porcentajePlataCliente ,
                porcentajeHumedadCliente :porcentajeHumedadCliente ,
                porcentajeMermaCliente :porcentajeMermaCliente ,
                porcentajeZincFinal:porcentajeZincFinal,
                porcentajePlomoFinal:porcentajePlomoFinal,
                porcentajePlataFinal:porcentajePlataFinal,
                porcentajeHumedadFinal :porcentajeHumedadFinal ,
                porcentajeMermaFinal :porcentajeMermaFinal ,
                modoValoracion:modoValoracion,
                tablaComplejo:tablaComplejo,
                terminosDeContrato:terminosDeContrato,
                kilosFinosZinc:kilosFinosZinc,
                kilosFinosPlomo:kilosFinosPlomo,
                kilosFinosPlata:kilosFinosPlata,
                librasFinasDeZinc:librasFinasDeZinc,
                librasFinasDePlomo:librasFinasDePlomo,
                onzasTroyDePlata:onzasTroyDePlata,
                valorOficialBrutoDeZinc:valorOficialBrutoDeZinc,
                valorOficialBrutoDePlomo:valorOficialBrutoDePlomo,
                valorOficialBrutoDePlata:valorOficialBrutoDePlata,
                valorOficialBrutoDeZincEnBolivianos:valorOficialBrutoDeZincEnBolivianos,
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
        liquidacionDeComplejo.save(failOnError: true)

        def liquidacionGrupalDeComplejoDetalle = new LiquidacionGrupalDeComplejoDetalle(
                millis: millis,
                liquidacionDeComplejo: liquidacionDeComplejo
        )
        liquidacionGrupalDeComplejoDetalle.save(failOnError: true)
        render [:] as JSON
    }
}
