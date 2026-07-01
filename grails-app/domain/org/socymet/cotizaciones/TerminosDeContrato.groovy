package org.socymet.cotizaciones

import org.socymet.proveedor.Empresa

class TerminosDeContrato {
    static auditable = true

    String nombreContrato
    Empresa empresa
    String tipoDeMineral

    //leyes de elementos a castigar
    BigDecimal porcentajeArsenico
    BigDecimal porcentajeAntimonio
    BigDecimal porcentajeBismuto
    BigDecimal porcentajeEstano
    BigDecimal porcentajeHierro
    BigDecimal porcentajeSilice
    BigDecimal porcentajeZinc
    //deducciones unitarias
    BigDecimal deduccionUnitariaZinc
    BigDecimal deduccionUnitariaPlomo
    BigDecimal deduccionUnitariaPlata
    BigDecimal deduccionUnitariaCobre=0
    //porcentaje pagable LME
    BigDecimal porcentajePagableLMEZinc
    BigDecimal porcentajePagableLMEPlomo
    BigDecimal porcentajePagableLMEPlata
    BigDecimal porcentajePagableLMECobre=0
    //maquila, base, escalador
    BigDecimal maquilaZincPlata
    BigDecimal baseZincPlata
    BigDecimal escaladorZincPlata

    BigDecimal maquilaPlomoPlata
    BigDecimal basePlomoPlata
    BigDecimal escaladorPlomoPlata

    BigDecimal maquilaCobre=0
    BigDecimal baseCobre=0
    BigDecimal escaladorCobre=0
    //deducciones refinacion de onza pagable de plata
    BigDecimal deduccionRefinacionOnzaZincPlata
    BigDecimal deduccionRefinacionOnzaPlomoPlata
    BigDecimal deduccionRefinacionOnzaCobrePlata=0
    //deducciones refinacion de libra pagable
    BigDecimal deduccionRefinacionLibraZinc=0
    BigDecimal deduccionRefinacionLibraPlomo=0
    BigDecimal deduccionRefinacionLibraCobre=0
    //penalidades
    BigDecimal arsenicoLibre
    BigDecimal costoUnitarioArsenico
    BigDecimal porcentajeUnitarioArsenico
    BigDecimal antimonioLibre
    BigDecimal costoUnitarioAntimonio
    BigDecimal porcentajeUnitarioAntimonio
    BigDecimal bismutoLibre
    BigDecimal costoUnitarioBismuto
    BigDecimal porcentajeUnitarioBismuto
    BigDecimal estanoLibre
    BigDecimal costoUnitarioEstano
    BigDecimal porcentajeUnitarioEstano
    BigDecimal hierroLibre
    BigDecimal costoUnitarioHierro
    BigDecimal porcentajeUnitarioHierro
    BigDecimal siliceLibre
    BigDecimal costoUnitarioSilice
    BigDecimal porcentajeUnitarioSilice
    BigDecimal zincLibre
    BigDecimal costoUnitarioZinc
    BigDecimal porcentajeUnitarioZinc
    //otras deducciones
    BigDecimal transporteInterno
    BigDecimal laboratorio
    BigDecimal molienda
    BigDecimal manipuleo
    BigDecimal margenAdministrativo
    BigDecimal transporteAPuerto
    BigDecimal rollBack
    //para no complicarse podria utilizarse como un campo calculador mediante javascript en la vista

    static constraints = {
        nombreContrato blank: false
        empresa nullable: true
        tipoDeMineral inList: ["PB-AG","ZN-AG"], nullable: true
        //leyes de elementos a castigar
        porcentajeArsenico nullable: false, min: 0.0
        porcentajeAntimonio nullable: false, min: 0.0
        porcentajeBismuto nullable: false, min: 0.0
        porcentajeEstano nullable: false, min: 0.0
        porcentajeHierro nullable: false, min: 0.0
        porcentajeSilice nullable: false, min: 0.0
        porcentajeZinc nullable: false, min: 0.0
        //deducciones unitarias
        deduccionUnitariaZinc nullable: false, min: 0.0
        deduccionUnitariaPlomo nullable: false, min: 0.0
        deduccionUnitariaPlata nullable: false, min: 0.0
        deduccionUnitariaCobre nullable: false, min: 0.0
        //porcentaje pagable LME
        porcentajePagableLMEZinc nullable: false, min: 0.0
        porcentajePagableLMEPlomo nullable: false, min: 0.0
        porcentajePagableLMEPlata nullable: false, min: 0.0
        porcentajePagableLMECobre nullable: false, min: 0.0
        //maquila, base, escalador
        maquilaZincPlata nullable: false, min: 0.0
        baseZincPlata nullable: false, min: 0.0
        escaladorZincPlata nullable: false, min: 0.0
        maquilaPlomoPlata nullable: false, min: 0.0
        basePlomoPlata nullable: false, min: 0.0
        escaladorPlomoPlata nullable: false, min: 0.0
        maquilaCobre nullable: false, min: 0.0
        baseCobre nullable: false, min: 0.0
        escaladorCobre nullable: false, min: 0.0
        //deducciones refinacion de onza pagable de plata
        deduccionRefinacionOnzaZincPlata nullable: false, min: 0.0
        deduccionRefinacionOnzaPlomoPlata nullable: false, min: 0.0
        deduccionRefinacionOnzaCobrePlata nullable: false, min: 0.0
        //deducciones refinacion de libra pagable
        deduccionRefinacionLibraZinc nullable: false, min: 0.0
        deduccionRefinacionLibraPlomo nullable: false, min: 0.0
        deduccionRefinacionLibraCobre nullable: false, min: 0.0
        //penalidades
        arsenicoLibre nullable: false, min: 0.0
        costoUnitarioArsenico nullable: false, min: 0.0
        porcentajeUnitarioArsenico nullable: false, min: 0.0
        antimonioLibre nullable: false, min: 0.0
        costoUnitarioAntimonio nullable: false, min: 0.0
        porcentajeUnitarioAntimonio nullable: false, min: 0.0
        bismutoLibre nullable: false, min: 0.0
        costoUnitarioBismuto nullable: false, min: 0.0
        porcentajeUnitarioBismuto nullable: false, min: 0.0
        estanoLibre nullable: false, min: 0.0
        costoUnitarioEstano nullable: false, min: 0.0
        porcentajeUnitarioEstano nullable: false, min: 0.0
        hierroLibre nullable: false, min: 0.0
        costoUnitarioHierro nullable: false, min: 0.0
        porcentajeUnitarioHierro nullable: false, min: 0.0
        siliceLibre nullable: false, min: 0.0
        costoUnitarioSilice nullable: false, min: 0.0
        porcentajeUnitarioSilice nullable: false, min: 0.0
        zincLibre nullable: false, min: 0.0
        costoUnitarioZinc nullable: false, min: 0.0
        porcentajeUnitarioZinc nullable: false, min: 0.0
        //otras deducciones
        transporteInterno nullable: false, min: 0.0
        laboratorio nullable: false, min: 0.0
        molienda nullable: false, min: 0.0
        manipuleo nullable: false, min: 0.0
        margenAdministrativo nullable: false, min: 0.0
        transporteAPuerto nullable: false, min: 0.0
        rollBack nullable: false, min: 0.0
    }

    String toString(){
        "$nombreContrato"
    }
}
