package org.socymet.cotizaciones

class TablaCotizacionWolfran {
    String nombreDeTabla

    String tablaDeCotizaciones

    static constraints = {
        nombreDeTabla blank: false
        tablaDeCotizaciones()
    }

    static mapping = {
        tablaDeCotizaciones type: 'text'
    }

    String toString(){
        "${nombreDeTabla}"
    }
}
