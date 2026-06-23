package org.socymet.liquidacion

class LiquidacionGrupalDeComplejoDetalle {
    BigDecimal millis
    LiquidacionDeComplejo liquidacionDeComplejo

    static constraints = {
        millis nullable: false
        liquidacionDeComplejo nullable: false
    }
}
