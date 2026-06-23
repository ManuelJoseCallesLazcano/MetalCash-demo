package org.socymet.org.socymet.reportes

import org.socymet.proveedor.Empresa

class ReporteLotesAnalisis {
    Empresa empresa
    Date fechaInicial
    Date fechaFinal

    static constraints = {
        empresa(nullable: true)
        fechaInicial()
        fechaFinal()
    }
}
