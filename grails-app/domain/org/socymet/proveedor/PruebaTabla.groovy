package org.socymet.proveedor

class PruebaTabla {
    String nombreDeTabla
    String contenido

    static constraints = {
        nombreDeTabla()
        contenido()
    }

    static mapping = {
        contenido type: 'text'
    }
}
