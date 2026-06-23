package org.socymet.proveedor.bonos

class BonoCantidad extends Bono{
    BigDecimal cantidadMinima
    BigDecimal cantidadMaxima

    static constraints = {
        empresa(blank: false)
        elemento(blank: false)
        simboloElemento(blank: false)
        cantidadMinima(blank: false, min: 0.0)
        cantidadMaxima(blank: false, min: 0.0)
        bono(blank: false, min: 0.0)
    }
}
