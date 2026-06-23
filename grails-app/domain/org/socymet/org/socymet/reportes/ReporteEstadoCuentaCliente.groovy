package org.socymet.org.socymet.reportes

import org.socymet.proveedor.Cliente

class ReporteEstadoCuentaCliente {
    static auditable = true

    Cliente cliente
    Date fechaInicial = new java.util.Date(84,5,14)
    Date fechaFinal = new java.util.Date(1084,5,14)

    static constraints = {
        cliente nullable: true
        fechaInicial(nullable: true)
        fechaFinal(nullable: true)
    }
}
