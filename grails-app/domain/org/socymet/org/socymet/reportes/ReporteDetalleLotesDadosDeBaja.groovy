package org.socymet.org.socymet.reportes

class ReporteDetalleLotesDadosDeBaja {
    String elemento
    Date fechaInicial
    Date fechaFinal

    static constraints = {
        elemento inList: ["Complejo","Plomo-Plata","Zinc-Plata"]
        fechaInicial()
        fechaFinal()
    }
}
