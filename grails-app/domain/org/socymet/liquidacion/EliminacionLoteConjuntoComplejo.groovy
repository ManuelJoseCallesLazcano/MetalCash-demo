package org.socymet.liquidacion

import org.socymet.org.socymet.reportes.Reimpresion

class EliminacionLoteConjuntoComplejo {
    String lote

    Integer liquidacionId
    String nombreCliente
    String nombreEmpresa
    String fechaDeRecepcion
    String fechaDeLiquidacion
    BigDecimal kilosNetosSecos
    BigDecimal porcentajeZinc
    BigDecimal porcentajePlomo
    BigDecimal porcentajePlata
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
        porcentajeZinc min: 0.0
        porcentajePlomo min: 0.0
        porcentajePlata min: 0.0
        fechaDeAsignacion nullable: true, display: false
        conjuntoOriginal blank: false
        motivo blank: false
    }

    def beforeInsert = {
        fechaDeAsignacion = new java.util.Date()
    }

    def afterInsert = {
        def liquidacionDeComplejo = LiquidacionDeComplejo.get(liquidacionId)
        liquidacionDeComplejo.conjuntoComplejo="-"
        liquidacionDeComplejo.save(failOnError: true)

        def reimpresion = new Reimpresion(fecha: new java.util.Date(),
                nombreReporte: "ELIMINACION DE LOTE DE CONJUNTO DE COMPLEJO",
                identificadorDocumento: "N/A",
                lote: "CM${lote}",
                motivo: motivo)
        reimpresion.save(failOnError: true)
    }

    def afterUpdate = {
        def liquidacionDeComplejo = LiquidacionDeComplejo.get(liquidacionId)
        liquidacionDeComplejo.conjuntoComplejo="-"
        liquidacionDeComplejo.save(failOnError: true)

        def reimpresion = new Reimpresion(fecha: new java.util.Date(),
                nombreReporte: "ELIMINACION DE LOTE DE CONJUNTO DE COMPLEJO",
                identificadorDocumento: "N/A",
                lote: "CM${lote}",
                motivo: motivo)
        reimpresion.save(failOnError: true)
    }
}
