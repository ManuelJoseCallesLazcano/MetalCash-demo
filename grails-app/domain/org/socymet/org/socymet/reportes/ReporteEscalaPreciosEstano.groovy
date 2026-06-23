package org.socymet.org.socymet.reportes

import org.socymet.cotizaciones.TablaCotizacionEstano

class ReporteEscalaPreciosEstano {
    Date fechaCotizacion
    BigDecimal cotizacionEstano
    TablaCotizacionEstano tablaCotizacionEstano

    static constraints = {
        fechaCotizacion()
        cotizacionEstano nullable: true, min: 0.0
        tablaCotizacionEstano nullable: true
    }
}
