package org.socymet.org.socymet.reportes

import org.socymet.proveedor.Deposito

class ReporteGeneralLiquidacion {
    Deposito deposito
    Date fechaInicial
    Date fechaFinal

    static constraints = {
        deposito(nullable: true)
        fechaInicial()
        fechaFinal()
    }
}
