package org.socymet.liquidacion

import org.socymet.org.socymet.reportes.Reimpresion

class EliminacionLoteConjuntoWolfran {
    String lote

    Integer liquidacionId
    String nombreCliente
    String nombreEmpresa
    String fechaDeRecepcion
    String fechaDeLiquidacion
    BigDecimal kilosNetosSecos
    BigDecimal porcentajeWolfran
    Date fechaDeAsignacion
    String conjuntoOriginal
    String motivo

    static constraints = {
        lote blank: false
        liquidacionId nullable: true
        nombreCliente blank: false
        nombreEmpresa blank: false
        fechaDeRecepcion nullable: false
        fechaDeLiquidacion nullable: false
        kilosNetosSecos min: 0.0
        porcentajeWolfran min: 0.0
        fechaDeAsignacion nullable: true, display: false
        conjuntoOriginal blank: false
        motivo blank: false
    }

    def beforeInsert = {
        fechaDeAsignacion = new java.util.Date()
    }

    def afterInsert = {
        def liquidacionDeWolfran = LiquidacionDeWolfran.get(liquidacionId)
        liquidacionDeWolfran.conjuntoWolfran="-"
        liquidacionDeWolfran.save(failOnError: true)

        def reimpresion = new Reimpresion(fecha: new java.util.Date(),
                nombreReporte: "ELIMINACION DE LOTE DE CONJUNTO DE WOLFRAN",
                identificadorDocumento: "N/A",
                lote: "WO3${lote}",
                motivo: motivo)
        reimpresion.save(failOnError: true)
    }

    def afterUpdate = {
        def liquidacionDeWolfran = LiquidacionDeWolfran.get(liquidacionId)
        liquidacionDeWolfran.conjuntoWolfran="-"
        liquidacionDeWolfran.save(failOnError: true)

        def reimpresion = new Reimpresion(fecha: new java.util.Date(),
                nombreReporte: "ELIMINACION DE LOTE DE CONJUNTO DE WOLFRAN",
                identificadorDocumento: "N/A",
                lote: "WO3${lote}",
                motivo: motivo)
        reimpresion.save(failOnError: true)
    }
}
