package org.socymet.caja

import org.socymet.seguridad.SecUser

class CierreCajaDetalle {
    CierreCaja cierreCaja

    MovimientoCaja movimientoCaja
    Integer numeroMovimiento
    String fechaMovimiento
    Ingreso ingreso
    Egreso egreso
    String ci
    String nombre
    String concepto
    BigDecimal debe
    BigDecimal haber
    BigDecimal saldo
    SecUser usuario

    static constraints = {
        cierreCaja()
        movimientoCaja()
        numeroMovimiento()
        fechaMovimiento()
        ingreso(nullable: true)
        egreso(nullable: true)
        ci()
        nombre()
        concepto()
        debe()
        haber()
        saldo()
        usuario()
    }
}
