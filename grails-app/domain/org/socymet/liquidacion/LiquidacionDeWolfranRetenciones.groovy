package org.socymet.liquidacion

class LiquidacionDeWolfranRetenciones {
    LiquidacionDeWolfran liquidacionDeWolfran
    Integer codigo
    BigDecimal cantidadDescuento
    String unidadDeDescuento
    String tipoDeRetencion
    String descripcion
    String asignacionDelDescuento
    BigDecimal monto

    static constraints = {
        liquidacionDeWolfran(blank: false, nullable: false)
        codigo(blank: false, nullable: false)
        cantidadDescuento(min: 0.0, blank: false)
        unidadDeDescuento(inList: ["%","Bs"])
        tipoDeRetencion(inList: ["DE LEY","OTRA"])
        descripcion(blank: false)
        asignacionDelDescuento(inList: ["VBV","VNV","SACO","FIJO"])
        monto(min: 0.0, blank: false)
    }
}
