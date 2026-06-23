package org.socymet.liquidacion

import org.socymet.proveedor.Cliente
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo

class LiquidacionProvisionalDePlomoPlata {
    Cliente cliente
    Empresa empresa
    Deposito deposito
    Date fechaDeLiquidacionProvisional
    Integer numeroLiquidacionProvisionalPlomoPlata = 0

    RecepcionDeComplejo recepcionDeComplejo
    BigDecimal cotizacionDiariaDeZinc
    BigDecimal cotizacionQuincenalDeZinc
    BigDecimal alicuotaDeZinc
    BigDecimal alicuotaDeZincParaExportacion
    BigDecimal cotizacionDiariaDePlomo
    BigDecimal cotizacionQuincenalDePlomo
    BigDecimal alicuotaDePlomo
    BigDecimal alicuotaDePlomoParaExportacion
    BigDecimal cotizacionDiariaDePlata
    BigDecimal cotizacionQuincenalDePlata
    BigDecimal alicuotaDePlata
    BigDecimal alicuotaDePlataParaExportacion
    BigDecimal tipoDeCambioOficial
    BigDecimal tipoDeCambioComercial

    String lote
    String direccion

    String tipoDeMineral
    String nombreCliente
    String nombreEmpresa
    String fechaDeRecepcion
    String cantidadDeSacos
    String naturalezaMineral

    BigDecimal toneladasMetricasHumedas // wet metric tonnes
    BigDecimal humedadPromedio //moisture average
    BigDecimal toneladasMetricasSecas
    BigDecimal merma = 1
    BigDecimal toneladasMetricasSecasFinales
    String partidaArancelaria
    String condicionesDeEntrega
    String origen
    /**
     * VARIABLES PARA EL CALCULO MEDIANTE TERMINOS DE CONTRATO*/
    BigDecimal porcentajePlomo
    BigDecimal deduccionUnitariaPlomo
    BigDecimal leyPagablePlomo
    BigDecimal porcentajePagableLMEPlomo
    BigDecimal leyFinalPagablePlomo
    BigDecimal cotizacionPlomo
    BigDecimal valorBrutoPlomo

    BigDecimal porcentajePlata
    BigDecimal deduccionUnitariaPlata
    BigDecimal leyPagablePlata
    BigDecimal porcentajePagableLMEPlata
    BigDecimal leyFinalPagablePlata
    BigDecimal cotizacionPlata
    BigDecimal valorBrutoPlata
    BigDecimal valorBruto

    BigDecimal maquilaPlomoPlata
    BigDecimal basePlomoPlata
    BigDecimal cotizacionBasadaPlomo
    BigDecimal escaladorPlomoPlata
    BigDecimal cotizacionEscaladaPlomo
    BigDecimal deduccionMaquilaFinalPlomo

    BigDecimal deduccionRefinacionOnzaPlomoPlata
    BigDecimal deduccionRefinacionOnzaPlomoPlataFinal
    //penalidades
    BigDecimal porcentajeArsenico
    BigDecimal arsenicoLibre
    BigDecimal porcentajeUnitarioArsenico
    BigDecimal costoUnitarioArsenico
    BigDecimal penalidadCastigableArsenicoFinal
    BigDecimal porcentajeAntimonio
    BigDecimal antimonioLibre
    BigDecimal porcentajeUnitarioAntimonio
    BigDecimal costoUnitarioAntimonio
    BigDecimal penalidadCastigableAntimonioFinal
    BigDecimal porcentajeBismuto
    BigDecimal bismutoLibre
    BigDecimal porcentajeUnitarioBismuto
    BigDecimal costoUnitarioBismuto
    BigDecimal penalidadCastigableBismutoFinal
    BigDecimal porcentajeEstano
    BigDecimal estanoLibre
    BigDecimal porcentajeUnitarioEstano
    BigDecimal costoUnitarioEstano
    BigDecimal penalidadCastigableEstanoFinal
    BigDecimal porcentajeHierro
    BigDecimal hierroLibre
    BigDecimal porcentajeUnitarioHierro
    BigDecimal costoUnitarioHierro
    BigDecimal penalidadCastigableHierroFinal
    BigDecimal porcentajeSilice
    BigDecimal siliceLibre
    BigDecimal porcentajeUnitarioSilice
    BigDecimal costoUnitarioSilice
    BigDecimal penalidadCastigableSiliceFinal
    BigDecimal porcentajeZinc
    BigDecimal zincLibre
    BigDecimal porcentajeUnitarioZinc
    BigDecimal costoUnitarioZinc
    BigDecimal penalidadCastigableZincFinal
    BigDecimal penalidadCastigableFinal
    /** FIN - VARIABLES DE CALCULO */
    BigDecimal valorNeto
    BigDecimal porcentajeDePagoProvisional = 80
    BigDecimal pagoProvisional
    /** RETENCIONES DE LEY Y OTRAS*/
    BigDecimal librasFinasPlomo
    BigDecimal valorOficialBrutoPlomo
    BigDecimal regaliaPlomo
    BigDecimal onzasTroyPlata
    BigDecimal valorOficialBrutoPlata
    BigDecimal regaliaPlata
    // <-- otras retenciones despues de las regalias
    String retenciones /** CADENA PARA FORMAR TABLA DE RETENCIONES*/
    BigDecimal analisisDeLaboratorio
    BigDecimal inspeccionDeProducto
    BigDecimal costoDeMolienda
    BigDecimal anticipoContraContrato
    BigDecimal transporteAPuerto
    BigDecimal totalTransporteAPuerto
    BigDecimal rollBack
    BigDecimal totalRollBack
    BigDecimal totalRetenciones
    BigDecimal balanceProvisionalPagable
    
    static constraints = {
        fechaDeLiquidacionProvisional nullable: false
        numeroLiquidacionProvisionalPlomoPlata nullable: false
        lote blank: false, nullable: false, unique: true
        recepcionDeComplejo nullable: false, unique: true
        deposito nullable:false
        cotizacionDiariaDeZinc nullable: false
        cotizacionQuincenalDeZinc nullable: false
        alicuotaDeZinc nullable: false
        alicuotaDeZincParaExportacion nullable: false
        cotizacionDiariaDePlomo nullable: false
        cotizacionQuincenalDePlomo nullable: false
        alicuotaDePlomo nullable: false
        alicuotaDePlomoParaExportacion nullable: false
        cotizacionDiariaDePlata nullable: false
        cotizacionQuincenalDePlata nullable: false
        alicuotaDePlata nullable: false
        alicuotaDePlataParaExportacion nullable: false
        tipoDeCambioOficial nullable: false
        tipoDeCambioComercial nullable: false

        cliente nullable:false
        empresa nullable:false
        nombreCliente blank: false, nullable: false
        nombreEmpresa blank: false, nullable: false
        direccion blank: false
        fechaDeRecepcion blank: false, nullable: false
        tipoDeMineral blank: false, nullable: false
        naturalezaMineral blank: false, nullable: false
        cantidadDeSacos blank: false, nullable: false
        toneladasMetricasHumedas nullable: false, min: 0.0
        humedadPromedio nullable: false, min: 0.0, max: 100.0
        toneladasMetricasSecas nullable: false, min: 0.0
        merma nullable: false, min: 0.0
        toneladasMetricasSecasFinales nullable: false, min: 0.0
        partidaArancelaria blank: false, nullable: false
        condicionesDeEntrega blank: false, nullable: false
        origen blank: false, nullable: false

        porcentajePlomo nullable: false, min: 0.0, max: 100.0
        deduccionUnitariaPlomo nullable: false, min: 0.0
        leyPagablePlomo nullable: false, min: 0.0
        porcentajePagableLMEPlomo nullable: false, min: 0.0
        leyFinalPagablePlomo nullable: false, min: 0.0
        cotizacionPlomo nullable: false, min: 0.0
        valorBrutoPlomo nullable: false, min: 0.0

        porcentajePlata nullable: false, min: 0.0, max: 100.0
        deduccionUnitariaPlata nullable: false, min: 0.0
        leyPagablePlata nullable: false, min: 0.0
        porcentajePagableLMEPlata nullable: false, min: 0.0
        leyFinalPagablePlata nullable: false, min: 0.0
        cotizacionPlata nullable: false, min: 0.0
        valorBrutoPlata nullable: false, min: 0.0
        valorBruto nullable: false, min: 0.0

        maquilaPlomoPlata nullable: false, min: 0.0
        basePlomoPlata nullable: false, min: 0.0
        cotizacionBasadaPlomo nullable: false, min: 0.0
        escaladorPlomoPlata nullable: false, min: 0.0
        cotizacionEscaladaPlomo nullable: false, min: 0.0
        deduccionMaquilaFinalPlomo nullable: false, min: 0.0

        deduccionRefinacionOnzaPlomoPlata nullable: false, min: 0.0
        deduccionRefinacionOnzaPlomoPlataFinal nullable: false, min: 0.0

        porcentajeArsenico nullable: false, min: 0.0, max: 100.0
        arsenicoLibre nullable: false, min: 0.0, max: 100.0
        porcentajeUnitarioArsenico nullable: false, min: 0.0, max: 100.0
        costoUnitarioArsenico nullable: false, min: 0.0
        penalidadCastigableArsenicoFinal nullable: false, min: 0.0
        porcentajeAntimonio nullable: false, min: 0.0, max: 100.0
        antimonioLibre nullable: false, min: 0.0, max: 100.0
        porcentajeUnitarioAntimonio nullable: false, min: 0.0, max: 100.0
        costoUnitarioAntimonio nullable: false, min: 0.0
        penalidadCastigableAntimonioFinal nullable: false, min: 0.0
        porcentajeBismuto nullable: false, min: 0.0, max: 100.0
        bismutoLibre nullable: false, min: 0.0, max: 100.0
        porcentajeUnitarioBismuto nullable: false, min: 0.0, max: 100.0
        costoUnitarioBismuto nullable: false, min: 0.0
        penalidadCastigableBismutoFinal nullable: false, min: 0.0
        porcentajeEstano nullable: false, min: 0.0, max: 100.0
        estanoLibre nullable: false, min: 0.0, max: 100.0
        porcentajeUnitarioEstano nullable: false, min: 0.0, max: 100.0
        costoUnitarioEstano nullable: false, min: 0.0
        penalidadCastigableEstanoFinal nullable: false, min: 0.0
        porcentajeHierro nullable: false, min: 0.0, max: 100.0
        hierroLibre nullable: false, min: 0.0, max: 100.0
        porcentajeUnitarioHierro nullable: false, min: 0.0, max: 100.0
        costoUnitarioHierro nullable: false, min: 0.0
        penalidadCastigableHierroFinal nullable: false, min: 0.0
        porcentajeSilice nullable: false, min: 0.0, max: 100.0
        siliceLibre nullable: false, min: 0.0, max: 100.0
        porcentajeUnitarioSilice nullable: false, min: 0.0, max: 100.0
        costoUnitarioSilice nullable: false, min: 0.0
        penalidadCastigableSiliceFinal nullable: false, min: 0.0
        porcentajeZinc nullable: false, min: 0.0, max: 100.0
        zincLibre nullable: false, min: 0.0, max: 100.0
        porcentajeUnitarioZinc nullable: false, min: 0.0, max: 100.0
        costoUnitarioZinc nullable: false, min: 0.0
        penalidadCastigableZincFinal nullable: false, min: 0.0
        penalidadCastigableFinal nullable: false, min: 0.0

        valorNeto nullable: false
        porcentajeDePagoProvisional nullable: false, min: 0.0, max: 100.0
        pagoProvisional nullable: false

        librasFinasPlomo nullable: false, min: 0.0
        valorOficialBrutoPlomo nullable: false, min: 0.0
        regaliaPlomo nullable: false, min: 0.0
        onzasTroyPlata nullable: false, min: 0.0
        valorOficialBrutoPlata nullable: false, min: 0.0
        regaliaPlata nullable: false, min: 0.0

        retenciones()
        analisisDeLaboratorio nullable: false, min: 0.0
        inspeccionDeProducto nullable: false, min: 0.0
        costoDeMolienda nullable: false, min: 0.0
        anticipoContraContrato nullable: false, min: 0.0
        transporteAPuerto nullable: false, min: 0.0
        totalTransporteAPuerto nullable: false, min: 0.0
        rollBack nullable: false, min: 0.0
        totalRollBack nullable: false, min: 0.0
        totalRetenciones nullable: false, min: 0.0
        balanceProvisionalPagable nullable: false
    }

    static mapping = {
        retenciones type: 'text'
    }
}
