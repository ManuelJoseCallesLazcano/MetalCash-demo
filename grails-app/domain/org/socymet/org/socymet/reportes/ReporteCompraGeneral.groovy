package org.socymet.org.socymet.reportes

class ReporteCompraGeneral {
    String elemento
    Date fechaInicial
    Date fechaFinal

    static constraints = {
        elemento inList: ["Complejo","Plomo-Plata","Zinc-Plata","Cobre-Plata"]
        fechaInicial(nullable: false)
        fechaFinal(nullable: false)
    }
}
