package org.socymet.proforma

import org.socymet.proveedor.Empresa
import org.socymet.seguridad.SecUser

class ProformaLiquidacion {
    Integer numeroProformaLiquidacion=0
    Date fechaProformaLiquidacion
    String nombreProforma
    //pesos
    BigDecimal toneladasMetricasHumedas
    BigDecimal humedadPromedio
    BigDecimal toneladasMetricasSecas
    BigDecimal merma
    BigDecimal toneladasMetricasSecasFinales
    //leyes
    BigDecimal leyPlomo
    BigDecimal leyPlata
    //cotizaciones
    BigDecimal cotizacionPlomo
    BigDecimal cotizacionPlata
    //leyes minerales pagables
    BigDecimal leyPlomoMineralesPagables
    BigDecimal leyPlataMineralesPagables
    //deducciones unitarias
    BigDecimal deduccionUnitariaPlomo
    BigDecimal deduccionUnitariaPlata
    //porcentaje pagable LME
    BigDecimal porcentajePagableLMEPlomo
    BigDecimal porcentajePagableLMEPlata
    //cotizaciones
    BigDecimal cotizacionPlomoMineralesPagables
    BigDecimal cotizacionPlataMineralesPagables
    //valor pagable
    BigDecimal valorPagablePlomo
    BigDecimal valorPagablePlata
    BigDecimal valorPagableTotal
    //maquila
    BigDecimal maquila
    BigDecimal maquilaFinal
    //gastos realizacion
    BigDecimal base
    BigDecimal cotizacionPlomoActual
    BigDecimal escaladorPlomoPlata
    BigDecimal gastoRealizacionTotal
    //refinamiento
    BigDecimal leyPlataOnzaTroy
    BigDecimal costoRefinacion
    BigDecimal costoRefinacionTotal

    //penalidades
    BigDecimal porcentajeArsenico
    BigDecimal arsenicoLibre
    BigDecimal costoUnitarioArsenico
    BigDecimal porcentajeUnitarioArsenico
    BigDecimal penalizacionArsenico

    BigDecimal porcentajeAntimonio
    BigDecimal antimonioLibre
    BigDecimal costoUnitarioAntimonio
    BigDecimal porcentajeUnitarioAntimonio
    BigDecimal penalizacionAntimonio

    BigDecimal porcentajeBismuto
    BigDecimal bismutoLibre
    BigDecimal costoUnitarioBismuto
    BigDecimal porcentajeUnitarioBismuto
    BigDecimal penalizacionBismuto

    BigDecimal porcentajeEstano
    BigDecimal estanoLibre
    BigDecimal costoUnitarioEstano
    BigDecimal porcentajeUnitarioEstano
    BigDecimal penalizacionEstano

    BigDecimal porcentajeHierro
    BigDecimal hierroLibre
    BigDecimal costoUnitarioHierro
    BigDecimal porcentajeUnitarioHierro
    BigDecimal penalizacionHierro

    BigDecimal porcentajeSilice
    BigDecimal siliceLibre
    BigDecimal costoUnitarioSilice
    BigDecimal porcentajeUnitarioSilice
    BigDecimal penalizacionSilice

    BigDecimal porcentajeZinc
    BigDecimal zincLibre
    BigDecimal costoUnitarioZinc
    BigDecimal porcentajeUnitarioZinc
    BigDecimal penalizacionZinc
    // valor unitario y total
    BigDecimal precioUnitario
    BigDecimal valorNetoTotal
    //DESCUENTOS POR OPERACION
    BigDecimal costoFleteTonelada
    BigDecimal costoFleteToneladaTotal
    BigDecimal costoPortuarioTonelada
    BigDecimal costoPortuarioToneladaTotal

    BigDecimal costoOperacionTonelada
    BigDecimal costoOperacionToneladaTotal
    BigDecimal regaliaDiferenciaPlomo=0
    BigDecimal regaliaDiferenciaPlomoTotal=0
    BigDecimal regaliaDiferenciaPlata=0
    BigDecimal regaliaDiferenciaPlataTotal=0
    BigDecimal descuentoOperacionesTotal=0
    
    BigDecimal valorNetoTotalFinal
    
    static constraints = {
        numeroProformaLiquidacion()
        fechaProformaLiquidacion()
        nombreProforma()
        toneladasMetricasHumedas()
        humedadPromedio()
        toneladasMetricasSecas()
        merma()
        toneladasMetricasSecasFinales()
        leyPlomo()
        leyPlata()
        cotizacionPlomo()
        cotizacionPlata()
        leyPlomoMineralesPagables()
        leyPlataMineralesPagables()
        deduccionUnitariaPlomo()
        deduccionUnitariaPlata()
        porcentajePagableLMEPlomo()
        porcentajePagableLMEPlata()
        cotizacionPlomoMineralesPagables()
        cotizacionPlataMineralesPagables()
        valorPagablePlomo()
        valorPagablePlata()
        valorPagableTotal()
        maquila()
        maquilaFinal()
        base()
        cotizacionPlomoActual()
        escaladorPlomoPlata()
        gastoRealizacionTotal()
        leyPlataOnzaTroy()
        costoRefinacion()
        costoRefinacionTotal()
        porcentajeArsenico()
        arsenicoLibre()
        costoUnitarioArsenico()
        porcentajeUnitarioArsenico()
        porcentajeAntimonio()
        antimonioLibre()
        costoUnitarioAntimonio()
        porcentajeUnitarioAntimonio()
        porcentajeBismuto()
        bismutoLibre()
        costoUnitarioBismuto()
        porcentajeUnitarioBismuto()
        porcentajeEstano()
        estanoLibre()
        costoUnitarioEstano()
        porcentajeUnitarioEstano()
        porcentajeHierro()
        hierroLibre()
        costoUnitarioHierro()
        porcentajeUnitarioHierro()
        porcentajeSilice()
        siliceLibre()
        costoUnitarioSilice()
        porcentajeUnitarioSilice()
        porcentajeZinc()
        zincLibre()
        costoUnitarioZinc()
        porcentajeUnitarioZinc()
        precioUnitario()
        valorNetoTotal()
        costoFleteTonelada()
        costoFleteToneladaTotal()
        costoPortuarioTonelada()
        costoPortuarioToneladaTotal()
        costoOperacionTonelada()
        costoOperacionToneladaTotal()
        regaliaDiferenciaPlomo()
        regaliaDiferenciaPlomoTotal()
        regaliaDiferenciaPlata()
        regaliaDiferenciaPlataTotal()
        descuentoOperacionesTotal()
        valorNetoTotalFinal()
    }

    def beforeInsert = {
        def c = ProformaLiquidacion.createCriteria()
        def results = c {
            projections {
                max('numeroProformaLiquidacion')
            }}
        def maxNumeroProformaLiquidacion = results.get(0)?: 0
        this.numeroProformaLiquidacion = maxNumeroProformaLiquidacion + 1
        this.fechaProformaLiquidacion=new java.util.Date()
    }

    String toString(){
        "$numeroProformaLiquidacion: $nombreProforma"
    }
}
