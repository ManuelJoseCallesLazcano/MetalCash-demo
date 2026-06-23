package org.socymet.org.socymet.reportes

import org.socymet.proveedor.Empresa

class ReportePagoAnalisis {
    String nombreDeLaboratorio

    Empresa empresa

    Date fechaInicial
    Date fechaFinal

    static constraints = {
        nombreDeLaboratorio blank: true
        empresa nullable: true
        fechaInicial nullable: false
        fechaFinal nullable: false
    }
}
