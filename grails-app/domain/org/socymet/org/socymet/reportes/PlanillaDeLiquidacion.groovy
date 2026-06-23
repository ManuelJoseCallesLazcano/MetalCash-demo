package org.socymet.org.socymet.reportes

import org.socymet.proveedor.Empresa

class PlanillaDeLiquidacion {
    static auditable = true

    Empresa empresa
    Date fechaInicial
    Date fechaFinal

    static constraints = {
        empresa nullable: true
        fechaInicial(nullable: true)
        fechaFinal(nullable: true)
    }
}
