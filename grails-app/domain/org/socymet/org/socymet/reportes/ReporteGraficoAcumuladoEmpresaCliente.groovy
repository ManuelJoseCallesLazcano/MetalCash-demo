package org.socymet.org.socymet.reportes

import org.socymet.proveedor.Empresa

class ReporteGraficoAcumuladoEmpresaCliente {
    Empresa empresa

    static constraints = {
        empresa()
    }
}
