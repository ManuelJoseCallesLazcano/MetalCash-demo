package org.socymet.liquidacion

class LiquidacionGrupalDeZincPlataDetalle {
    BigDecimal millis
    LiquidacionDeZincPlata liquidacionDeZincPlata

    static constraints = {
        millis nullable: false
        liquidacionDeZincPlata nullable: false
    }
}
