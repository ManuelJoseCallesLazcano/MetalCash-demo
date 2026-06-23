package org.socymet.liquidacion

class LiquidacionDeEstanoRetenciones {
    LiquidacionDeEstano liquidacionDeEstano
    Integer codigo
    BigDecimal cantidadDescuento
    String unidadDeDescuento
    String tipoDeRetencion
    String descripcion
    String asignacionDelDescuento
    BigDecimal monto

    static constraints = {
        liquidacionDeEstano(blank: false, nullable: false)
        codigo(blank: false, nullable: false)
        cantidadDescuento(min: 0.0, blank: false)
        unidadDeDescuento(inList: ["%","Bs"])
        tipoDeRetencion(inList: ["DE LEY","OTRA"])
        descripcion(blank: false)
        asignacionDelDescuento(inList: ["VBV","VNV","SACO","FIJO"])
        monto(min: 0.0, blank: false)
    }
}
