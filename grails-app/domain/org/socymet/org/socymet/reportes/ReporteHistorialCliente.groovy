package org.socymet.org.socymet.reportes

import org.socymet.proveedor.Cliente

class ReporteHistorialCliente {
    Cliente cliente
    Date fechaInicial
    Date fechaFinal

    static constraints = {
        cliente nullable: true
        fechaInicial(nullable: true)
        fechaFinal(nullable: true)
    }
}
