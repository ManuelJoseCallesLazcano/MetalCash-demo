package org.socymet.reportesAcopiadoras

import org.socymet.proveedor.Empresa

class PlanillaLiquidacion {
    Empresa empresa
    Date fechaInicial
    Date fechaFinal

    static constraints = {
        empresa nullable: false
        fechaInicial()
        fechaFinal()
    }
}
