package org.socymet.proveedor.bonos

class BonoIncentivo extends Bono{
    BigDecimal leyMinima
    BigDecimal leyMaxima
    BigDecimal cantidadMinima
    BigDecimal cantidadMaxima

    static constraints = {
        empresa(blank: false)
        elemento(blank: false)
        simboloElemento(blank: false)
        leyMinima(blank: false, min: 0.0)
        leyMaxima(blank: false, min: 0.0)
        cantidadMinima(blank: false, min: 0.0)
        cantidadMaxima(blank: false, min: 0.0)
        bono(blank: false, min: 0.0)
    }
}
