package org.socymet.reportesAcopiadoras

import org.socymet.proveedor.Empresa

class LotesLiquidados {
    Empresa empresa
    Date fechaInicial
    Date fechaFinal

    static constraints = {
        empresa nullable: true
        fechaInicial()
        fechaFinal()
    }
}
