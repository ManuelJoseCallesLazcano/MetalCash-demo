package org.socymet.org.socymet.reportes

import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa

class ReporteLotesLiquidados {
    static auditable = true

    Deposito deposito
    Empresa empresa
    String elemento
    Date fechaInicial
    Date fechaFinal

    static constraints = {
        empresa nullable: true
        deposito nullable: false
        elemento inList: ["Complejo","Plomo Plata","Zinc Plata","Cobre Plata"], nullable: false
        fechaInicial(nullable: true)
        fechaFinal(nullable: true)
    }
}
