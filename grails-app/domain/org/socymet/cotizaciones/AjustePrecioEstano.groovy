package org.socymet.cotizaciones

import org.socymet.seguridad.SecUser

class AjustePrecioEstano {
    Date fecha
    CotizacionDiariaDeMinerales cotizacionDiariaDeMinerales
    TablaCotizacionEstano tablaCotizacionEstano
    BigDecimal margen
    String filaOriginal
    String filaAjustada

    SecUser usuario

    transient springSecurityService

    static constraints = {
        fecha nullable: false
        cotizacionDiariaDeMinerales nullable: false, unique: true
        tablaCotizacionEstano nullable: false
        margen nullable: false
        filaOriginal blank: false, nullable: false, size: 0..2000
        filaAjustada blank: false, nullable: false, size: 0..2000
        usuario nullable: false
    }

    def beforeInsert = {
        usuario = springSecurityService.getCurrentUser()
    }

    String toString(){
        if (fecha)
            "| ${new java.text.SimpleDateFormat('dd/MM/yyyy').format(fecha)} | ${tablaCotizacionEstano} | Margen: ${margen} |"

//        "| ${fecha} | ${tablaCotizacionEstano} | Margen: ${margen} |"
    }
}
