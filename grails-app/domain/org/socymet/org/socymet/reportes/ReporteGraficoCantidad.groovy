package org.socymet.org.socymet.reportes

import org.socymet.proveedor.Empresa

class ReporteGraficoCantidad {
    Empresa empresa
    String elemento

    static constraints = {
        empresa nullable: true
        elemento inList: ["Complejo"]
    }
}
