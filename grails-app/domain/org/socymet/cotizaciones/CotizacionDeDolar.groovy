package org.socymet.cotizaciones

class CotizacionDeDolar {
    static auditable = true

    Date fecha
    BigDecimal tipoDeCambioOficial
    BigDecimal tipoDeCambioComercial
    Integer activo

    static constraints = {
        fecha(blank: false)
        tipoDeCambioOficial(min: 0.0, blank: false)
        tipoDeCambioComercial(min: 0.0, blank: false)
        activo(nullable: true, display: false)
    }

    def beforeInsert = {
        def cotizacion = CotizacionDeDolar.findByActivo(1)
        if(cotizacion){
            cotizacion.activo = 0
            cotizacion.save()
        }
        this.activo = 1
    }
}
