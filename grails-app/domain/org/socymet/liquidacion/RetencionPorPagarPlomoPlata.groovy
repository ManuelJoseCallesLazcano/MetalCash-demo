package org.socymet.liquidacion

import org.socymet.proveedor.Empresa

class RetencionPorPagarPlomoPlata {
    Integer codigo
    BigDecimal cantidadDescuento
    String unidadDeDescuento
    String tipoDeRetencion
    String descripcion
    String asignacionDelDescuento
    BigDecimal monto

    LiquidacionDePlomoPlata liquidacionDePlomoPlata
    Empresa empresa

    Date fechaDeRegistro
    Date fechaDePago
    String pagado

    static constraints = {
        codigo(blank: false, nullable: false)
        cantidadDescuento(min: 0.0, blank: false)
        unidadDeDescuento(blank: false)
        tipoDeRetencion(blank: false)
        descripcion(blank: false)
        asignacionDelDescuento(blank: false)
        monto(min: 0.0, blank: false)
        liquidacionDePlomoPlata(blank: false, nullable: false)
        empresa(blank: false, nullable: false)
        fechaDeRegistro(blank: false, nullable: false)
        fechaDePago(blank: false, nullable: false)
        pagado(blank: false)
    }
}
