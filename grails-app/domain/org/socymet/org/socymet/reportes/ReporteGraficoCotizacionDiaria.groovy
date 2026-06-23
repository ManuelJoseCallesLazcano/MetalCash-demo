package org.socymet.org.socymet.reportes

class ReporteGraficoCotizacionDiaria {
    String elemento

    static constraints = {
        elemento inList: ["Plata","Plomo","Zinc"], nullable: false
    }
}
