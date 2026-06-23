package org.socymet.cancelacion

import org.socymet.proveedor.Cliente
import org.socymet.proveedor.Cuadrilla
import org.socymet.proveedor.Empresa

class AcumulacionBonoProduccion {
    PagoBonoProduccion pagoBonoProduccion
    Date fecha
    String tipoSeleccion
    Cliente cliente
    Empresa empresa
    Cuadrilla cuadrilla
    BigDecimal cantidadAcumulada

    static constraints = {
        pagoBonoProduccion nullable: false
        fecha nullable: false
        tipoSeleccion blank: false, nullable: false
        cliente nullable: true
        empresa nullable: true
        cuadrilla nullable: true
        cantidadAcumulada nullable: false
    }
}
