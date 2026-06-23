package org.socymet.cotizaciones

class TerminosContratoPlomoPlata {
    static auditable = true

    String nombreTerminosContrato

    BigDecimal deduccionUnitariaPlomo
    BigDecimal deduccionUnitariaPlata
    BigDecimal porcentajePagablePlomo
    BigDecimal porcentajePagablePlata
    BigDecimal maquila
    BigDecimal basePlomo
    BigDecimal escaladorPlomo
    BigDecimal gastoRefinacion
    //penalidades
    BigDecimal calidadLibreArsenico
    BigDecimal penalizacionArsenico
    BigDecimal porcentajePenalizableArsenico
    BigDecimal calidadLibreAntimonio
    BigDecimal penalizacionAntimonio
    BigDecimal porcentajePenalizableAntimonio
    BigDecimal calidadLibreBismuto
    BigDecimal penalizacionBismuto
    BigDecimal porcentajePenalizableBismuto
    BigDecimal calidadLibreEstano
    BigDecimal penalizacionEstano
    BigDecimal porcentajePenalizableEstano
    BigDecimal calidadLibreZinc
    BigDecimal penalizacionZinc
    BigDecimal porcentajePenalizableZinc

    static constraints = {
        nombreTerminosContrato blank: false

        deduccionUnitariaPlomo nullable: false, min: 0.0
        deduccionUnitariaPlata nullable: false, min: 0.0
        porcentajePagablePlomo nullable: false, min: 0.0
        porcentajePagablePlata nullable: false, min: 0.0

        maquila nullable: false, min: 0.0
        basePlomo nullable: false, min: 0.0
        escaladorPlomo nullable: false, min: 0.0
        gastoRefinacion nullable: false, min: 0.0

        calidadLibreArsenico nullable: false, min: 0.0
        penalizacionArsenico nullable: false, min: 0.0
        porcentajePenalizableArsenico nullable: false, min: 0.0
        calidadLibreAntimonio nullable: false, min: 0.0
        penalizacionAntimonio nullable: false, min: 0.0
        porcentajePenalizableAntimonio nullable: false, min: 0.0
        calidadLibreBismuto nullable: false, min: 0.0
        penalizacionBismuto nullable: false, min: 0.0
        porcentajePenalizableBismuto nullable: false, min: 0.0
        calidadLibreEstano nullable: false, min: 0.0
        penalizacionEstano nullable: false, min: 0.0
        porcentajePenalizableEstano nullable: false, min: 0.0
        calidadLibreZinc nullable: false, min: 0.0
        penalizacionZinc nullable: false, min: 0.0
        porcentajePenalizableZinc nullable: false, min: 0.0
    }
}
