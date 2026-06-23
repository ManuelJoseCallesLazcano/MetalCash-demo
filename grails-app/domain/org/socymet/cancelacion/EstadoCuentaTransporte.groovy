package org.socymet.cancelacion

import org.socymet.proveedor.Automovil
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo

class EstadoCuentaTransporte {
    RecepcionDeComplejo recepcionDeComplejo
    String solicitante
    Empresa empresa
    Automovil automovil
    String ci
    String nombreResponsable
    Date fecha
    String descripcion
    BigDecimal ingreso
    BigDecimal egreso
    BigDecimal saldo

    static constraints = {
        recepcionDeComplejo(nullable: true)
        solicitante blank: false
        empresa nullable: true
        automovil nullable: true
        ci blank: false
        nombreResponsable blank: false
        fecha nullable: false
        descripcion blank: false
        ingreso nullable: false
        egreso nullable: false
        saldo nullable: false
    }
}
