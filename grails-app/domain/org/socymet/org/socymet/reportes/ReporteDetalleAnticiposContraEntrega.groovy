package org.socymet.org.socymet.reportes

import org.socymet.proveedor.Empresa

class ReporteDetalleAnticiposContraEntrega {
    Empresa empresa
    Date fechaInicial
    Date fechaFinal
    String numeroAnticipoInicial
    String numeroAnticipoFinal

    static constraints = {
        empresa nullable: true
        fechaInicial()
        fechaFinal()
        numeroAnticipoInicial blank: true
        numeroAnticipoFinal blank: true
    }
}
