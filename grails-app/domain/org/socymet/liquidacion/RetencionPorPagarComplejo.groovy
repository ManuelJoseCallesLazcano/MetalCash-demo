package org.socymet.liquidacion

import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.recepcion.RecepcionDeEstano

class RetencionPorPagarComplejo {
    Long liquidacionId
    Integer codigo
    BigDecimal cantidadDescuento
    String unidadDeDescuento
    String tipoDeRetencion
    String descripcion
    String asignacionDelDescuento
    BigDecimal monto

    String lote
    BigDecimal kilosNetosSecos
    BigDecimal valorOficialNeto

    RecepcionDeComplejo recepcionDeComplejo
    RecepcionDeEstano recepcionDeEstano
    String tipoDeMineral
    Empresa empresa

    Date fechaDeRegistro
    String pagado

    static constraints = {
        liquidacionId nullable: false
        codigo(blank: false, nullable: false)
        cantidadDescuento(min: 0.0, blank: false)
        unidadDeDescuento(blank: false)
        tipoDeRetencion(blank: false)
        descripcion(blank: false)
        asignacionDelDescuento(blank: false)
        monto(min: 0.0, blank: false)
        lote blank: false
        recepcionDeComplejo nullable: true
        recepcionDeEstano nullable: true
        tipoDeMineral blank: false
        empresa(blank: false, nullable: false)
        fechaDeRegistro(blank: false, nullable: false)
        pagado(blank: false)
    }
}
