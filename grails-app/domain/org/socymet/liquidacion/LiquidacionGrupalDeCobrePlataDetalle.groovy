package org.socymet.liquidacion

class LiquidacionGrupalDeCobrePlataDetalle {
    BigDecimal millis
    LiquidacionDeCobrePlata liquidacionDeCobrePlata

    static constraints = {
        millis nullable: false
        liquidacionDeCobrePlata nullable: false
    }
}
