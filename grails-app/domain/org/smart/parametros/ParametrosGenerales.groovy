package org.smart.parametros

class ParametrosGenerales {
    Integer mesesPagablesBonoCantidad
    Integer mesesPagablesBonoCalidad
    Integer mesesPagablesBonoTransporte

    BigDecimal leyMinimaPlataBonoCalidad

    BigDecimal leyBajaZincBonoTransporte
    BigDecimal leyBajaPlomoBonoTransporte
    BigDecimal leyAltaZincBonoTransporte
    BigDecimal leyAltaPlomoBonoTransporte
    //COSTOS POR MANIPULEO
    BigDecimal pesadaVaciada
    BigDecimal carguioMaquina
    BigDecimal embolsadaArrumada
    BigDecimal soloComuneada
    BigDecimal soloVaciada
    BigDecimal soloPesada
    BigDecimal soloEmbolsada

    static constraints = {
        mesesPagablesBonoCantidad min: 0, nullable: false
        mesesPagablesBonoCalidad min: 0, nullable: false
        mesesPagablesBonoTransporte min: 0, nullable: false

        leyMinimaPlataBonoCalidad min: 0.0, nullable: false

        leyBajaZincBonoTransporte min: 0.0, max: 100.0, nullable: false
        leyBajaPlomoBonoTransporte min: 0.0, max: 100.0, nullable: false
        leyAltaZincBonoTransporte min: 0.0, max: 100.0, nullable: false
        leyAltaPlomoBonoTransporte min: 0.0, max: 100.0, nullable: false

        pesadaVaciada min: 0.0, max: 100.0, nullable: false
        carguioMaquina min: 0.0, max: 100.0, nullable: false
        embolsadaArrumada min: 0.0, max: 100.0, nullable: false
        soloComuneada min: 0.0, max: 100.0, nullable: false
        soloVaciada min: 0.0, max: 100.0, nullable: false
        soloPesada min: 0.0, max: 100.0, nullable: false
        soloEmbolsada min: 0.0, max: 100.0, nullable: false
    }
}
