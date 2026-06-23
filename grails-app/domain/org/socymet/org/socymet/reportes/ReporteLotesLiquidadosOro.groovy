package org.socymet.org.socymet.reportes

import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa

class ReporteLotesLiquidadosOro {
    Deposito deposito
    Empresa empresa
    String elemento
    Date fechaInicial
    Date fechaFinal

    static constraints = {
        empresa nullable: true
        deposito nullable: false
        elemento inList: ["Oro"]
        fechaInicial(nullable: true)
        fechaFinal(nullable: true)
    }
}
