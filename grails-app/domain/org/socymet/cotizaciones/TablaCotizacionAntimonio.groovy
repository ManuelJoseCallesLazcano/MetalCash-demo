package org.socymet.cotizaciones

class TablaCotizacionAntimonio {
    String nombreDeTabla

    String tablaDeCotizaciones

    static constraints = {
        nombreDeTabla blank: false
        tablaDeCotizaciones blank: false, nullable: false
    }

    static mapping = {
        tablaDeCotizaciones type: 'text'
    }

    String toString(){
        "${nombreDeTabla}"
    }
}
