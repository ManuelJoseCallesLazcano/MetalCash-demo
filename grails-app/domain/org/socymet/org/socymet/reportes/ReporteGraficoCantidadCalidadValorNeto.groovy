package org.socymet.org.socymet.reportes

class ReporteGraficoCantidadCalidadValorNeto {
    String elemento
    Date fechaInicial
    Date fechaFinal

    static constraints = {
        elemento inList: ["Complejo","Plomo-Plata","Zinc-Plata"], nullable: false
        fechaInicial()
        fechaFinal()
    }
}
