package org.socymet.org.socymet.reportes

import org.socymet.cotizaciones.TablaCotizacionAntimonio

class ReporteEscalaPreciosAntimonio {
    Date fechaCotizacion
    TablaCotizacionAntimonio tablaCotizacionAntimonio

    static constraints = {
        fechaCotizacion()
        tablaCotizacionAntimonio nullable: true
    }
}
