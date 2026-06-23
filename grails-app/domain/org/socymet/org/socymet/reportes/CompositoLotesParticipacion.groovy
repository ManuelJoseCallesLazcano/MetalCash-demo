package org.socymet.org.socymet.reportes

class CompositoLotesParticipacion {
    ReporteCompositoDeLotes reporteCompositoDeLotes
    String nombreEmpresa
    String departamento
    String municipio
    BigDecimal kilosNetosSecos
    BigDecimal porcentajeParticipacion

    static constraints = {
        reporteCompositoDeLotes()
        nombreEmpresa()
        departamento()
        municipio()
        kilosNetosSecos()
        porcentajeParticipacion()
    }
}
