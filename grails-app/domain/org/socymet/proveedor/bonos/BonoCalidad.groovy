package org.socymet.proveedor.bonos

class BonoCalidad extends Bono{
    BigDecimal leyMinima
    BigDecimal leyMaxima

    static constraints = {
        empresa(blank: false)
        elemento(blank: false)
        simboloElemento(blank: false)
        leyMinima(blank: false, min: 0.0)
        leyMaxima(blank: false, min: 0.0)
        bono(blank: false, min: 0.0)
    }

    String toString(){
        "${empresa.toString()} - ${simboloElemento} - LEY MINIMA: ${leyMinima} - LEY MAXIMA: ${leyMaxima}"
    }
}
