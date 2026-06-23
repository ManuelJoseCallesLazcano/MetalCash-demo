package org.socymet.org.socymet.reportes

import org.socymet.proveedor.Empresa

class ReporteReliquidaciones {
    String elemento

    Empresa empresa
    Date fechaInicial
    Date fechaFinal
    String loteInicial
    String loteFinal

    static constraints = {
        elemento inList: ["Estano","Plata","Wolfran","Antimonio","Complejo"]

        empresa nullable: true
        fechaInicial(nullable: true)
        fechaFinal(nullable: true)
        loteInicial nullable: true, blank: true
        loteFinal nullable: true, blank: true
    }
}
