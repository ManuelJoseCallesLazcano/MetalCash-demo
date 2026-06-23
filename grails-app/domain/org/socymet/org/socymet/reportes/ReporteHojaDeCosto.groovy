package org.socymet.org.socymet.reportes

import org.socymet.proveedor.Empresa

class ReporteHojaDeCosto {
    String elemento

    String nombreDelConjunto
    String destinoDelConjunto
    String asignarConjuntoALotes
    String ignorarLotes

    Empresa empresa
    Date fechaInicial
    Date fechaFinal
    String loteInicial
    String loteFinal
    BigDecimal leyMinimaEstano
    BigDecimal leyMaximaEstano
    BigDecimal leyMinimaPlata
    BigDecimal leyMaximaPlata
    BigDecimal leyMinimaWolfran
    BigDecimal leyMaximaWolfran
    BigDecimal leyMinimaAntimonio
    BigDecimal leyMaximaAntimonio
    BigDecimal leyMinimaZincComplejo
    BigDecimal leyMaximaZincComplejo
    BigDecimal leyMinimaPlomoComplejo
    BigDecimal leyMaximaPlomoComplejo
    BigDecimal leyMinimaPlataComplejo
    BigDecimal leyMaximaPlataComplejo
    BigDecimal leyMinimaPlomoPlomoPlata
    BigDecimal leyMaximaPlomoPlomoPlata
    BigDecimal leyMinimaPlataPlomoPlata
    BigDecimal leyMaximaPlataPlomoPlata
    BigDecimal leyMinimaZincZincPlata
    BigDecimal leyMaximaZincZincPlata
    BigDecimal leyMinimaPlataZincPlata
    BigDecimal leyMaximaPlataZincPlata
    

    static constraints = {
        elemento inList: ["Complejo","Plomo Plata","Zinc Plata"]

        nombreDelConjunto unique: true, nullable: true, blank: true
        destinoDelConjunto nullable: true, blank: true
        asignarConjuntoALotes inList: ["NO","SI"], nullable: true, blank: true
        ignorarLotes nullable: true, blank: true

        empresa nullable: true
        fechaInicial(nullable: true)
        fechaFinal(nullable: true)
        loteInicial nullable: true, blank: true
        loteFinal nullable: true, blank: true
        leyMinimaEstano nullable: true 
        leyMaximaEstano nullable: true
        leyMinimaPlata nullable: true
        leyMaximaPlata nullable: true
        leyMinimaWolfran nullable: true
        leyMaximaWolfran nullable: true
        leyMinimaAntimonio nullable: true
        leyMaximaAntimonio nullable: true
        leyMinimaZincComplejo  nullable: true
        leyMaximaZincComplejo  nullable: true
        leyMinimaPlomoComplejo  nullable: true
        leyMaximaPlomoComplejo  nullable: true
        leyMinimaPlataComplejo  nullable: true
        leyMaximaPlataComplejo  nullable: true
        leyMinimaPlomoPlomoPlata  nullable: true
        leyMaximaPlomoPlomoPlata  nullable: true
        leyMinimaPlataPlomoPlata  nullable: true
        leyMaximaPlataPlomoPlata  nullable: true
        leyMinimaZincZincPlata  nullable: true
        leyMaximaZincZincPlata  nullable: true
        leyMinimaPlataZincPlata  nullable: true
        leyMaximaPlataZincPlata  nullable: true
    }
}
