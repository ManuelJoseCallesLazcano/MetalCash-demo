package org.socymet.cancelacion

class LoteBonoTransporte {
    PagoBonoTransporte pagoBonoTransporte
    String lote
    Date fechaDeRecepcion
    String nombreEmpresa
    String nombreCliente
    BigDecimal kilosBrutos
    BigDecimal leyPlomo
    BigDecimal leyZinc
    BigDecimal bono

    static constraints = {
        pagoBonoTransporte nullable: false
        lote blank: false
        fechaDeRecepcion nullable: false
        nombreEmpresa blank: false, nullable: false
        nombreCliente blank: false, nullable: false
        kilosBrutos nullable: false
        leyPlomo nullable: false
        leyZinc nullable: false
        bono nullable: false
    }
}
