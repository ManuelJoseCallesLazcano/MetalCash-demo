package org.socymet.cotizaciones

class CotizacionQuincenalDeMinerales {
    static auditable = true

    Date fecha
    BigDecimal estano=0
    BigDecimal plata
    BigDecimal plomo
    BigDecimal antimonio=0
    BigDecimal zinc
    BigDecimal wolfran=0
    BigDecimal cobre=0
    BigDecimal oro=0

    BigDecimal alicuotaZinc
    BigDecimal alicuotaPlomo
    BigDecimal alicuotaPlata

    Integer activo

    static constraints = {
        fecha(blank: false, unique: true)
        estano(min: 0.0, blank: false)
        plata(min: 0.0, blank: false)
        plomo(min: 0.0, blank: false)
        antimonio(min: 0.0, blank: false)
        zinc(min: 0.0, blank: false)
        wolfran(min: 0.0, blank: false)
        cobre(min: 0.0, blank: false)
        oro(min: 0.0, blank: false)
        alicuotaZinc()
        alicuotaPlomo()
        alicuotaPlata()
        activo(nullable: true, display: false)
    }

    def beforeInsert = {
        def cotizacion = CotizacionQuincenalDeMinerales.findByActivo(1)
        if(cotizacion){
            cotizacion.activo = 0
            cotizacion.save()
        }
        this.activo = 1
    }

    def afterInsert = {
        Alicuota.withNewTransaction {
            new Alicuota(
                    fecha: this.fecha,
                    estano: 0,
                    plata: this.alicuotaPlata,
                    plomo: this.alicuotaPlomo,
                    antimonio: 0,
                    zinc: this.alicuotaZinc,
                    wolfran: 0,
                    cobre: 0,
                    oro: 0,
                    activo: 0
            ).save(failOnError: true, flush: true)
        }
    }

    String toString(){
        if(fecha)
            "${new java.text.SimpleDateFormat('dd/MM/yyyy').format(fecha)} - Zn:${zinc} Pb:${plomo} Ag:${plata}"
    }
}
