package org.socymet.org.socymet.reportes

import org.socymet.proveedor.Empresa

class ReporteLotesCompositos {

    Date fechaInicial
    Date fechaFinal
    String estado
    Empresa empresa // para excluir empresas del reporte

    static constraints = {
        fechaInicial()
        fechaFinal()
        estado(inList: ['EN COMPOSITO', 'SIN COMPOSITO'])
        empresa()
    }
}
