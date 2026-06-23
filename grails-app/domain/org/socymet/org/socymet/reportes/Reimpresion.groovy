package org.socymet.org.socymet.reportes

class Reimpresion {
    Date fecha
    String nombreReporte
    String identificadorDocumento
    String lote
    String motivo

    static constraints = {
        fecha nullable: false
        nombreReporte blank: false
        identificadorDocumento blank: false
        lote blank: true
        motivo blank: true
    }
}
