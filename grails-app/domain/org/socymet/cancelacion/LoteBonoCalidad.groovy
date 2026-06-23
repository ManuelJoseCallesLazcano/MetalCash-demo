package org.socymet.cancelacion

class LoteBonoCalidad {
    PagoBonoCalidad pagoBonoCalidad
    String lote
    Date fechaDeLiquidacion
    String nombreEmpresa
    String nombreCliente
    BigDecimal kilosNetosSecos
    BigDecimal porcentajePlataFinal
    BigDecimal totalLiquidoPagable

    static constraints = {
        pagoBonoCalidad nullable: false
        lote blank: false
        fechaDeLiquidacion nullable: false
        nombreEmpresa blank: false, nullable: false
        nombreCliente blank: false, nullable: false
        kilosNetosSecos nullable: false
        porcentajePlataFinal nullable: false
        totalLiquidoPagable nullable: false
    }
}
