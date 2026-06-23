package org.socymet.org.socymet.reportes

class ReporteResumenRetenciones {
    String elemento
    Date fechaInicial
    Date fechaFinal

    static constraints = {
        elemento inList: ["Complejo","Plomo Plata","Zinc Plata","TODOS"]
        fechaInicial(nullable: false)
        fechaFinal(nullable: false)
    }


}
