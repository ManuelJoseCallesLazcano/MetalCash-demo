package org.socymet.org.socymet.reportes

import org.socymet.proveedor.Empresa

class ReporteAnticiposContraEntrega {
    String elemento
    Empresa empresa
    Date fechaInicial
    Date fechaFinal
    String loteInicial
    String loteFinal

    static constraints = {
        elemento inList: ["Complejo","Plomo Plata","Zinc Plata"]
        empresa nullable: true
        fechaInicial()
        fechaFinal()
        loteInicial blank: true
        loteFinal blank: true
    }
}
