package org.socymet.org.socymet.reportes

import org.socymet.proveedor.Cliente
import org.socymet.proveedor.Empresa

class ReporteAnticipos {
    static auditable = true

    Empresa empresa
    Cliente cliente
    Date fechaInicial
    Date fechaFinal

    static constraints = {
        empresa nullable: true
        cliente nullable: true
        fechaInicial(nullable: true)
        fechaFinal(nullable: true)
    }
}
