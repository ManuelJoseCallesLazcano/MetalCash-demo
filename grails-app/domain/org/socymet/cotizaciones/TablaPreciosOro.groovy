package org.socymet.cotizaciones

class TablaPreciosOro {
    String nombreTabla
    String tablaPrecios

    static constraints = {
        nombreTabla()
        tablaPrecios()
    }

    static mapping = {
        tablaPrecios type: 'text'
    }

    String toString(){
        nombreTabla
    }
}
