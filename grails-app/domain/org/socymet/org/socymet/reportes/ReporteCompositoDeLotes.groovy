package org.socymet.org.socymet.reportes

import org.grails.web.json.JSONArray
import org.smart.compositos.Comprador
import org.smart.compositos.Ingenio
import org.socymet.liquidacion.LiquidacionDeComplejo
import org.socymet.liquidacion.LiquidacionDePlomoPlata
import org.socymet.liquidacion.LiquidacionDeZincPlata
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.seguridad.SecUser

class ReporteCompositoDeLotes {
    static auditable = true

    Deposito deposito

    Integer numeroComposito
    String sigla
    String destino
    String nombreDestino="-"
    Comprador comprador
    Ingenio ingenio
    String elaboradoPor
    Date fechaDeElaboracion
    String estadoDelComposito //definitivo, provisional

    Empresa empresa

    String ordenarElemento

    Date fechaInicial
    Date fechaFinal

    BigDecimal leyMinimaZinc=0.0
    BigDecimal leyMaximaZinc=100.0
    BigDecimal leyMinimaPlomo=0.0
    BigDecimal leyMaximaPlomo=100.0
    BigDecimal leyMinimaPlata=0.0
    BigDecimal leyMaximaPlata=10000.0

    String lotes
    String lotesComposito

    BigDecimal totalKilosBrutos
    BigDecimal totalKilosNetosSecos
    BigDecimal leyPromedioZinc
    BigDecimal leyPromedioPlomo
    BigDecimal leyPromedioPlata
    BigDecimal totalKilosFinosZinc
    BigDecimal totalKilosFinosPlomo
    BigDecimal totalKilosFinosPlata
    BigDecimal totalValorNeto
    BigDecimal totalValorDeCompra

    String participacion

    String observaciones="-"

    String estadoDeAprobacion //aprobado, pendiente
    String aprobadoPor = "?"
    SecUser aprobador

    SecUser usuario

    transient springSecurityService

    static constraints = {
        numeroComposito nullable: true
        sigla blank: false, unique: true
        destino inList: ["VENTA","EXPORTACION","INGENIO"], blank: false
        nombreDestino()
        comprador(nullable: true)
        ingenio(nullable: true)
        elaboradoPor blank: false
        fechaDeElaboracion nullable: false
        estadoDelComposito inList: ["PROVISIONAL","DEFINITIVO"], blank: false
        empresa nullable: true
        ordenarElemento(inList: ["ZINC","PLOMO","PLATA"])
        fechaInicial()
        fechaFinal()
        leyMinimaZinc min: 0.0, max: 100.0, nullable: false
        leyMaximaZinc min: 0.0, max: 100.0, nullable: false
        leyMinimaPlomo min: 0.0, max: 100.0, nullable: false
        leyMaximaPlomo min: 0.0, max: 100.0, nullable: false
        leyMinimaPlata min: 0.0, max: 10000.0, nullable: false
        leyMaximaPlata min: 0.0, max: 10000.0, nullable: false
        lotes()
        lotesComposito()
        totalKilosBrutos min: 0.0, nullable: false
        totalKilosNetosSecos min: 0.0, nullable: false
        leyPromedioZinc min: 0.0, max: 100.0, nullable: false
        leyPromedioPlomo min: 0.0, max: 100.0, nullable: false
        leyPromedioPlata min: 0.0, max: 10000.0, nullable: false
        totalKilosFinosZinc min: 0.0, nullable: false
        totalKilosFinosPlomo min: 0.0, nullable: false
        totalKilosFinosPlata min: 0.0, nullable: false
        totalValorNeto min: 0.0, nullable: false
        totalValorDeCompra min: 0.0, nullable: false
        participacion()
        observaciones blank: true
        estadoDeAprobacion inList: ["PENDIENTE","APROBADO"], blank: false
        aprobadoPor blank: false
        aprobador display: false, nullable: true

        usuario display: false, nullable: true
    }

    static mapping = {
        lotes type: 'text'
        lotesComposito type: 'text'
        participacion type: 'text'
    }

    def beforeInsert = {
        def c = ReporteCompositoDeLotes.createCriteria()
        def results = c {
            projections {
                max('numeroComposito')
            }}
        def maxNumeroComprobante = results.get(0)?: 0
        this.numeroComposito = maxNumeroComprobante + 1
        this.nombreDestino = this.destino.equals("INGENIO") ? this.ingenio.nombreIngenio : this.comprador.nombreComprador
        this.usuario = springSecurityService.getCurrentUser()
        this.lotes = "[]"
    }

    def afterInsert = {
        def lotesJSON = new JSONArray(lotesComposito)
        def participacionJSON = new JSONArray(participacion)

        ReporteCompositoDeLotes.withNewTransaction {
            lotesJSON.each {
                def recepcionId = it.getAt("recepcionId").toString().toLong()
                def liquidacionId = 0
                def lote = it.getAt("lote")
                def nombreEmpresa = it.getAt("nombreEmpresa")
                def departamento = it.getAt("departamento")
                def municipio = it.getAt("municipio")
                def proveedor = it.getAt("proveedor")
                def fechaDeRecepcion = new Date().parse("dd/MM/yyyy",it.getAt("fechaDeRecepcion").toString())
                def pesoBruto = it.getAt("pesoBruto").toString().toBigDecimal()
                def porcentajeHumedad = it.getAt("porcentajeHumedad").toString().toBigDecimal()
                def kilosNetosSecos = it.getAt("kilosNetosSecos").toString().toBigDecimal()
                def porcentajeZincFinal = it.getAt("porcentajeZincFinal").toString().toBigDecimal()
                def porcentajePlomoFinal = it.getAt("porcentajePlomoFinal").toString().toBigDecimal()
                def porcentajePlataFinal = it.getAt("porcentajePlataFinal").toString().toBigDecimal()
                def kilosFinosZinc = it.getAt("kilosFinosZinc").toString().toBigDecimal()
                def kilosFinosPlomo = it.getAt("kilosFinosPlomo").toString().toBigDecimal()
                def kilosFinosPlata = it.getAt("kilosFinosPlata").toString().toBigDecimal()
                def precioTonelada = it.getAt("precioTonelada").toString().toBigDecimal()
                def valorOficialBruto = it.getAt("valorOficialBruto").toString().toBigDecimal()
                def valorNetoMineralEnBolivianos = it.getAt("valorNetoMineralEnBolivianos").toString().toBigDecimal()
                def costoUnitarioTransporte = it.getAt("costoUnitarioTransporte").toString().toBigDecimal()
                def costoDeTransporte = it.getAt("costoDeTransporte").toString().toBigDecimal()
                def costoManipuleo = 0
                def bonos = 0
                def valorDeCompra = 0

                def compositoDeLotesDetalle = new CompositoDeLotesDetalle(
                        reporteCompositoDeLotes: this,
                        recepcionId: recepcionId,
                        liquidacionId: liquidacionId,
                        fechaDeRecepcion: fechaDeRecepcion,
                        lote: lote,
                        nombreEmpresa: nombreEmpresa,
                        departamento: departamento,
                        municipio: municipio,
                        proveedor: proveedor,
                        pesoBruto: pesoBruto,
                        porcentajeHumedad: porcentajeHumedad,
                        kilosNetosSecos: kilosNetosSecos,
                        porcentajeZincFinal: porcentajeZincFinal,
                        porcentajePlomoFinal: porcentajePlomoFinal,
                        porcentajePlataFinal: porcentajePlataFinal,
                        kilosFinosZinc: kilosFinosZinc,
                        kilosFinosPlomo: kilosFinosPlomo,
                        kilosFinosPlata: kilosFinosPlata,
                        precioTonelada: precioTonelada,
                        valorOficialBruto: valorOficialBruto,
                        valorNetoMineralEnBolivianos: valorNetoMineralEnBolivianos,
                        costoUnitarioTransporte: costoUnitarioTransporte,
                        costoDeTransporte: costoDeTransporte,
                        costoManipuleo: costoManipuleo,
                        bonos: bonos,
                        valorDeCompra: valorDeCompra
                )
                compositoDeLotesDetalle.save(failOnError: true)
            }
        }

        ReporteCompositoDeLotes.withNewTransaction {
            participacionJSON.each {
//                def nombreEmpresa = it.getAt("nombreEmpresa").toString()
                def nombreEmpresa = "-"
                def departamento = it.getAt("departamento").toString()
                def municipio = it.getAt("municipio").toString()
                def kilosNetosSecos = it.getAt("kilosNetosSecos").toString().toBigDecimal()
                def porcentajeParticipacion = it.getAt("porcentajeParticipacion").toString().toBigDecimal()

                def compositoLotesParticipacion = new CompositoLotesParticipacion(
                        reporteCompositoDeLotes: this,
                        nombreEmpresa: nombreEmpresa,
                        departamento: departamento,
                        municipio: municipio,
                        kilosNetosSecos: kilosNetosSecos,
                        porcentajeParticipacion: porcentajeParticipacion
                )
                compositoLotesParticipacion.save(failOnError: true)
            }
        }

        if(estadoDelComposito.equals("DEFINITIVO")){
            ReporteCompositoDeLotes.withNewTransaction {
                lotesJSON.each {
                    def recepcionId = it.getAt("recepcionId").toString().toLong()
                    def recepcionDeComplejo = RecepcionDeComplejo.get(recepcionId)
                    if(recepcionDeComplejo){
                        recepcionDeComplejo.nombreComposito = this.sigla
                        recepcionDeComplejo.save(failOnError: true, flush: true)
                    }
                }
            }
        }
    }

    def beforeUpdate = {
        this.nombreDestino = this.destino.equals("INGENIO") ? this.ingenio.nombreIngenio : this.comprador.nombreComprador
        this.usuario = springSecurityService.getCurrentUser()
    }

    def afterUpdate = {
        def lotesJSON = new JSONArray(lotesComposito)
        def participacionJSON = new JSONArray(participacion)

        ReporteCompositoDeLotes.withNewTransaction {
            def reporteCompositoDeLotes = ReporteCompositoDeLotes.get(this.id)
            def compositoDeLotesDetalles = CompositoDeLotesDetalle.findAllByReporteCompositoDeLotes(reporteCompositoDeLotes)
            compositoDeLotesDetalles.each { r ->
                r.delete(flush: true)
            }
        }

        ReporteCompositoDeLotes.withNewTransaction {
            def reporteCompositoDeLotes = ReporteCompositoDeLotes.get(this.id)
            def participacions = CompositoLotesParticipacion.findAllByReporteCompositoDeLotes(reporteCompositoDeLotes)
            participacions.each { p->
                p.delete(flush: true)
            }
        }
        ReporteCompositoDeLotes.withNewTransaction {
            lotesJSON.each {
                def recepcionId = it.getAt("recepcionId").toString().toLong()
                def liquidacionId = 0
                def lote = it.getAt("lote")
                def nombreEmpresa = it.getAt("nombreEmpresa")
                def departamento = it.getAt("departamento")
                def municipio = it.getAt("municipio")
                def proveedor = it.getAt("proveedor")
                def fechaDeRecepcion = new Date().parse("dd/MM/yyyy",it.getAt("fechaDeRecepcion").toString())
                def pesoBruto = it.getAt("pesoBruto").toString().toBigDecimal()
                def porcentajeHumedad = it.getAt("porcentajeHumedad").toString().toBigDecimal()
                def kilosNetosSecos = it.getAt("kilosNetosSecos").toString().toBigDecimal()
                def porcentajeZincFinal = it.getAt("porcentajeZincFinal").toString().toBigDecimal()
                def porcentajePlomoFinal = it.getAt("porcentajePlomoFinal").toString().toBigDecimal()
                def porcentajePlataFinal = it.getAt("porcentajePlataFinal").toString().toBigDecimal()
                def kilosFinosZinc = it.getAt("kilosFinosZinc").toString().toBigDecimal()
                def kilosFinosPlomo = it.getAt("kilosFinosPlomo").toString().toBigDecimal()
                def kilosFinosPlata = it.getAt("kilosFinosPlata").toString().toBigDecimal()
                def precioTonelada = it.getAt("precioTonelada").toString().toBigDecimal()
                def valorOficialBruto = it.getAt("valorOficialBruto").toString().toBigDecimal()
                def valorNetoMineralEnBolivianos = it.getAt("valorNetoMineralEnBolivianos").toString().toBigDecimal()
                def costoUnitarioTransporte = it.getAt("costoUnitarioTransporte").toString().toBigDecimal()
                def costoDeTransporte = it.getAt("costoDeTransporte").toString().toBigDecimal()
                def costoManipuleo = 0
                def bonos = 0
                def valorDeCompra = 0

                def compositoDeLotesDetalle = new CompositoDeLotesDetalle(
                        reporteCompositoDeLotes: this,
                        recepcionId: recepcionId,
                        liquidacionId: liquidacionId,
                        fechaDeRecepcion: fechaDeRecepcion,
                        lote: lote,
                        nombreEmpresa: nombreEmpresa,
                        departamento: departamento,
                        municipio: municipio,
                        proveedor: proveedor,
                        pesoBruto: pesoBruto,
                        porcentajeHumedad: porcentajeHumedad,
                        kilosNetosSecos: kilosNetosSecos,
                        porcentajeZincFinal: porcentajeZincFinal,
                        porcentajePlomoFinal: porcentajePlomoFinal,
                        porcentajePlataFinal: porcentajePlataFinal,
                        kilosFinosZinc: kilosFinosZinc,
                        kilosFinosPlomo: kilosFinosPlomo,
                        kilosFinosPlata: kilosFinosPlata,
                        precioTonelada: precioTonelada,
                        valorOficialBruto: valorOficialBruto,
                        valorNetoMineralEnBolivianos: valorNetoMineralEnBolivianos,
                        costoUnitarioTransporte: costoUnitarioTransporte,
                        costoDeTransporte: costoDeTransporte,
                        costoManipuleo: costoManipuleo,
                        bonos: bonos,
                        valorDeCompra: valorDeCompra
                )
                compositoDeLotesDetalle.save(failOnError: true)
            }
        }

        ReporteCompositoDeLotes.withNewTransaction {
            participacionJSON.each {
//                def nombreEmpresa = it.getAt("nombreEmpresa")
                def nombreEmpresa = "-"
                def departamento = it.getAt("departamento").toString()
                def municipio = it.getAt("municipio").toString()
                def kilosNetosSecos = it.getAt("kilosNetosSecos").toString().toBigDecimal()
                def porcentajeParticipacion = it.getAt("porcentajeParticipacion").toString().toBigDecimal()

                def compositoLotesParticipacion = new CompositoLotesParticipacion(
                        reporteCompositoDeLotes: this,
                        nombreEmpresa: nombreEmpresa,
                        departamento: departamento,
                        municipio: municipio,
                        kilosNetosSecos: kilosNetosSecos,
                        porcentajeParticipacion: porcentajeParticipacion
                )
                compositoLotesParticipacion.save(failOnError: true)
            }
        }

        if(estadoDelComposito.equals("DEFINITIVO")){
            ReporteCompositoDeLotes.withNewTransaction {
                lotesJSON.each {
                    def recepcionId = it.getAt("recepcionId").toString().toLong()
                    def recepcionDeComplejo = RecepcionDeComplejo.get(recepcionId)
                    if(recepcionDeComplejo){
                        recepcionDeComplejo.nombreComposito = this.sigla
                        recepcionDeComplejo.save(failOnError: true, flush: true)
                    }
                }
            }
        }
    }

    def beforeDelete = {
        def lotesJSON = new JSONArray(lotes)

        lotesJSON.each {
            def liquidacionId = it.getAt("liquidacionId").toString().toLong()
            def liquidacionDeComplejo = LiquidacionDeComplejo.get(liquidacionId)
            def liquidacionDePlomoPlata = LiquidacionDePlomoPlata.get(liquidacionId)
            def liquidacionDeZincPlata = LiquidacionDeZincPlata.get(liquidacionId)
            if(liquidacionDeComplejo){
                liquidacionDeComplejo.nombreComposito = ""
                liquidacionDeComplejo.save()
            }
            if(liquidacionDePlomoPlata){
                liquidacionDePlomoPlata.nombreComposito = ""
                liquidacionDePlomoPlata.save()
            }
            if(liquidacionDeZincPlata){
                liquidacionDeZincPlata.nombreComposito = ""
                liquidacionDeZincPlata.save()
            }
        }

        ReporteCompositoDeLotes.withNewTransaction {
            def reporteCompositoDeLotes = ReporteCompositoDeLotes.get(id)
            def compositoDeLotesDetalles = CompositoDeLotesDetalle.findAllByReporteCompositoDeLotes(reporteCompositoDeLotes)
            compositoDeLotesDetalles.each { r ->
                r.delete(flush: true)
            }
        }
    }

    String toString(){
//        "Cobrador: ${nombreCobrador} [${ci}] Fecha de Pago: ${fechaDePago.format('dd/MM/yyyy')} Lotes: ${descripcion}"
    }
}
