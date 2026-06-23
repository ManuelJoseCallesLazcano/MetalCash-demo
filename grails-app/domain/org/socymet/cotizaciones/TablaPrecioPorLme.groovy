package org.socymet.cotizaciones

import org.socymet.proveedor.Empresa

class TablaPrecioPorLme {
    static auditable = true

    String nombreTabla
    Empresa empresa
    String naturalezaMineral

    CotizacionDiariaDeMinerales cotizacionDiariaDeMinerales
    BigDecimal leyPlata
    BigDecimal porcentajeLme
    BigDecimal valorPorTonelada

    String tablaDePrecios

    static constraints = {
        nombreTabla blank: false
        empresa nullable: true
        naturalezaMineral inList: ["SULFURO","OXIDO"], nullable: false, blank: false

        cotizacionDiariaDeMinerales nullable: false
        leyPlata nullable: false
        porcentajeLme nullable: false
        valorPorTonelada nullable: false
        tablaDePrecios()
    }

    static mapping = {
        tablaDePrecios type: 'text'
    }

    String toString(){
        "$nombreTabla - $empresa [$naturalezaMineral]"
    }
}
