package org.socymet.cotizaciones

class Alicuota {
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
        activo(nullable: true, display: false)
    }

    def beforeInsert = {
        def alic = Alicuota.findByActivo(1)
        if(alic){
            alic.activo = 0
            alic.save()
        }
        this.activo = 1
    }

    String toString(){
        if(fecha)
            "${new java.text.SimpleDateFormat('dd/MM/yyyy').format(fecha)} - Zn:${zinc} Pb:${plomo} Ag:${plata}"
//            "${new java.text.SimpleDateFormat('dd/MM/yyyy').format(fecha)} - Ag:${plata} Pb:${plomo} Zn:${zinc}"
    }
}
