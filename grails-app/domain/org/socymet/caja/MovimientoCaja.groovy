package org.socymet.caja

import org.socymet.seguridad.SecUser

class MovimientoCaja {
    Integer numeroMovimiento
    Date fechaMovimiento

    Ingreso ingreso
    Egreso egreso

    String ci
    String nombre

    String concepto
    BigDecimal debe //ingreso
    BigDecimal haber //egreso
    BigDecimal saldo

    String consolidado
    SecUser usuario

    Date dateCreated
    Date lastUpdated

    static constraints = {
        numeroMovimiento()
        fechaMovimiento()
        ingreso nullable: true
        egreso nullable: true
        ci()
        nombre()
        concepto()

        debe()
        haber()
        saldo()

        consolidado()
        usuario()
    }

    def beforeInsert = {
        def c = MovimientoCaja.createCriteria()
        def results = c {
            projections {
                max('numeroMovimiento')
            }}
        def maxNumeroMovimiento = results.get(0)?: 0
        this.numeroMovimiento = maxNumeroMovimiento + 1
    }
}
