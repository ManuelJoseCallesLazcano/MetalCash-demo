package org.socymet.org.socymet.reportes

import org.socymet.cotizaciones.TablaCotizacionPlata

class ReporteEscalaPreciosPlata {
    Date fechaCotizacion
    BigDecimal cotizacionPlata
    TablaCotizacionPlata tablaCotizacionPlata

    static constraints = {
        fechaCotizacion()
        cotizacionPlata nullable: true, min: 0.0
        tablaCotizacionPlata nullable: true
    }
}
