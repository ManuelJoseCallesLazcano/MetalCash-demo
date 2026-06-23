package org.socymet.org.socymet.reportes

import org.socymet.proveedor.Empresa

class ReporteRetenciones {
    static auditable = true

    String elemento

    Empresa empresa
    String tipoRetencion
    Date fechaInicial
    Date fechaFinal
    String loteInicial
    String loteFinal
    String ignorarLotes

    static constraints = {
        elemento inList: ["Complejo","Plomo Plata","Zinc Plata","Cobre Plata"]

        empresa nullable: true
        tipoRetencion inList: ["DE LEY","OTRA"]
        fechaInicial(nullable: true)
        fechaFinal(nullable: true)
        loteInicial blank: true
        loteFinal blank: true
        ignorarLotes nullable: true, blank: true
    }
}
