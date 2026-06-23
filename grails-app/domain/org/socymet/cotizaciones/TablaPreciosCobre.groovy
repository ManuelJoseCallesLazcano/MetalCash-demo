package org.socymet.cotizaciones

import org.socymet.proveedor.Empresa

class TablaPreciosCobre {
    String nombreTabla
    Empresa empresa

    BigDecimal leyInicial
    BigDecimal leyFinal
    BigDecimal valorPorPunto

    String tablaDePrecios

    static constraints = {
        nombreTabla blank: false
        empresa nullable: false,unique: false

        leyInicial nullable: false
        leyFinal nullable: false
        valorPorPunto nullable: false
        tablaDePrecios()
    }

    static mapping = {
        tablaDePrecios type: 'text'
    }

    String toString(){
        "$nombreTabla - $empresa"
    }
}
