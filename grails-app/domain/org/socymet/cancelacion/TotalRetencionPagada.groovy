package org.socymet.cancelacion

class TotalRetencionPagada {
    PagoDeRetenciones pagoDeRetenciones

    String retencion
    BigDecimal total

    static constraints = {
        pagoDeRetenciones nullable: false
        retencion blank: false
        total nullable: false
    }
}
