package org.socymet.liquidacion

import org.socymet.cancelacion.PagoDeRetenciones
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo

/*ALMACENAR TODA LA INFORMACION PARALELA DEL PAGO DE RETENCIONES EN RetencionPagada y TotalRetencionPagada*/

class RetencionPagada {
    PagoDeRetenciones pagoDeRetenciones

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
    String tipoDeMineral
    Empresa empresa

    Date fechaDeRegistro

    static constraints = {
        pagoDeRetenciones nullable: false
        codigo(blank: false, nullable: false)
        cantidadDescuento(min: 0.0, blank: false)
        unidadDeDescuento(blank: false)
        tipoDeRetencion(blank: false)
        descripcion(blank: false)
        asignacionDelDescuento(blank: false)
        monto(min: 0.0, blank: false)
        lote blank: false
        kilosNetosSecos nullable: false
        valorOficialNeto nullable: false
        recepcionDeComplejo(blank: false, nullable: false)
        tipoDeMineral blank: false
        empresa(blank: false, nullable: false)
        fechaDeRegistro(blank: false, nullable: false)
    }
}
