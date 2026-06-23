package org.socymet.liquidacion

class LiquidacionDeCobrePlataRetenciones {
    LiquidacionDeCobrePlata liquidacionDeCobrePlata
    Integer codigo
    BigDecimal cantidadDescuento
    String unidadDeDescuento
    String tipoDeRetencion
    String descripcion
    String asignacionDelDescuento
    BigDecimal monto

    static constraints = {
        liquidacionDeCobrePlata(blank: false, nullable: false)
        codigo(blank: false, nullable: false)
        cantidadDescuento(min: 0.0, blank: false)
        unidadDeDescuento(inList: ["%","Bs"])
        tipoDeRetencion(inList: ["DE LEY","OTRA"])
        descripcion(blank: false)
//        asignacionDelDescuento(inList: ["VBV","VNV","SACO","FIJO"])
        asignacionDelDescuento(inList: ["VBV","VNV","TON. BRUTA","SACO","FIJO"])
        monto(min: 0.0, blank: false)
    }
}
