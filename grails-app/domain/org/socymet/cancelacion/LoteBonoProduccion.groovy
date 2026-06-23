package org.socymet.cancelacion

class LoteBonoProduccion {
    PagoBonoProduccion pagoBonoProduccion
    String lote
    Date fechaDeLiquidacion
    String nombreEmpresa
    String nombreCliente
    BigDecimal kilosNetosSecos
    BigDecimal totalLiquidoPagable

    static constraints = {
        pagoBonoProduccion nullable: false
        lote blank: false
        fechaDeLiquidacion nullable: false
        nombreEmpresa blank: false, nullable: false
        nombreCliente blank: false, nullable: false
        kilosNetosSecos nullable: false
        totalLiquidoPagable nullable: false
    }
}
