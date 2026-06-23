package org.socymet.liquidacion

class LiquidacionGrupalDePlomoPlataDetalle {
    BigDecimal millis
    LiquidacionDePlomoPlata liquidacionDePlomoPlata

    static constraints = {
        millis nullable: false
        liquidacionDePlomoPlata nullable: false
    }
}
