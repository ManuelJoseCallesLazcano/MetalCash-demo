package org.socymet.proforma

class ProformaGeneralLiquidacion {
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
    BigDecimal regaliaDiferenciaPlomo
    BigDecimal regaliaDiferenciaPlomoTotal
    BigDecimal regaliaDiferenciaPlata
    BigDecimal regaliaDiferenciaPlataTotal
    BigDecimal pagosProvisionales
    BigDecimal pagosProvisionalesTotal
    String descuento1="-"
    BigDecimal costoDescuento1=0
    BigDecimal costoDescuento1Total=0
    String descuento2="-"
    BigDecimal costoDescuento2=0
    BigDecimal costoDescuento2Total=0
    String descuento3="-"
    BigDecimal costoDescuento3=0
    BigDecimal costoDescuento3Total=0
    String descuento4="-"
    BigDecimal costoDescuento4=0
    BigDecimal costoDescuento4Total=0
    String descuento5="-"
    BigDecimal costoDescuento5=0
    BigDecimal costoDescuento5Total=0
    BigDecimal descuentoOperacionesTotal=0
    
    BigDecimal valorNetoTotalFinal
    BigDecimal valorCompraMineral
    BigDecimal valorCompraMineralTotal
    BigDecimal utilidadEstimada

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
        pagosProvisionales()
        pagosProvisionalesTotal()
        descuento1()
        costoDescuento1()
        costoDescuento1Total()
        descuento2()
        costoDescuento2()
        costoDescuento2Total()
        descuento3()
        costoDescuento3()
        costoDescuento3Total()
        descuento4()
        costoDescuento4()
        costoDescuento4Total()
        descuento5()
        costoDescuento5()
        costoDescuento5Total()
        descuentoOperacionesTotal()
        valorNetoTotalFinal()
        valorCompraMineral()
        valorCompraMineralTotal()
        utilidadEstimada()
    }

    def beforeInsert = {
        def c = ProformaGeneralLiquidacion.createCriteria()
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
