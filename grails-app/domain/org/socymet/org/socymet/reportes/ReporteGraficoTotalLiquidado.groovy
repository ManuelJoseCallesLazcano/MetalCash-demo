package org.socymet.org.socymet.reportes

import org.socymet.proveedor.Empresa

class ReporteGraficoTotalLiquidado {
    Empresa empresa
    String elemento

    static constraints = {
        empresa nullable: true
        elemento inList: ["Complejo","Plomo-Plata","Zinc-Plata"], nullable: false
    }
}
