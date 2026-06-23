package org.socymet.cancelacion

import org.socymet.proveedor.Automovil

class AcumulacionBonoTransporte {
    PagoBonoTransporte pagoBonoTransporte
    Date fecha
    Automovil automovil
    BigDecimal cantidadAcumulada

    static constraints = {
        pagoBonoTransporte nullable: false
        fecha nullable: false
        automovil nullable: true
        cantidadAcumulada nullable: false
    }
}
