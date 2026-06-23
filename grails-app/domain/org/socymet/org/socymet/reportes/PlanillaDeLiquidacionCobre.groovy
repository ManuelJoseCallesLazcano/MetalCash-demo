package org.socymet.org.socymet.reportes

import org.socymet.proveedor.Empresa

class PlanillaDeLiquidacionCobre {
    Empresa empresa
    Date fechaInicial
    Date fechaFinal

    static constraints = {
        empresa nullable: true
        fechaInicial(blank: false)
        fechaFinal(blank: false)
    }
}
