package org.socymet.recepcion

import org.socymet.cotizaciones.CotizacionDiariaDeMinerales
import org.socymet.cotizaciones.CotizacionQuincenalDeMinerales

class ActualizacionCotizacionRecepcion {
    String tipoCotizacion
    CotizacionDiariaDeMinerales cotizacionDiariaDeMinerales
    CotizacionQuincenalDeMinerales cotizacionQuincenalDeMinerales
    Date fechaInicial
    Date fechaFinal
    String detalleLotes

    static constraints = {
        tipoCotizacion(inList: ["COTIZACION DIARIA","COTIZACION QUINCENAL"])
        cotizacionDiariaDeMinerales()
        cotizacionQuincenalDeMinerales()
        fechaInicial()
        fechaFinal()
        detalleLotes()
    }

    static mapping = {
        detalleLotes type: 'text'
    }
}
