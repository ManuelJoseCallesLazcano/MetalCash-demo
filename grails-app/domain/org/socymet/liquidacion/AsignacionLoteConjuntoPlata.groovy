package org.socymet.liquidacion

import org.socymet.org.socymet.reportes.Reimpresion

class AsignacionLoteConjuntoPlata {
    /*NO TE OLVIDES FILTRAR, AUNQUE SEA REDUNDANTE, LAS LIQUIDACIONES CUYO ESTADO DE RECEPCION SEA "liquidado"*/
    String lote
    //DATOS DE LA RECEPCION (DUPLICANDO INFORMACION PARA FACILITAR LA GENERACION DE REPORTES)
    Integer liquidacionId
    String nombreCliente
    String nombreEmpresa
    String fechaDeRecepcion
    String fechaDeLiquidacion
    BigDecimal kilosNetosSecos
    BigDecimal porcentajePlata
    Date fechaDeAsignacion
    String conjuntoDestino
    String motivo

    static constraints = {
        lote blank: false
        liquidacionId nullable: true
        nombreCliente blank: false
        nombreEmpresa blank: false
        fechaDeRecepcion nullable: false
        fechaDeLiquidacion nullable: false
        kilosNetosSecos min: 0.0
        porcentajePlata min: 0.0
        fechaDeAsignacion nullable: true, display: false
        conjuntoDestino blank: false
        motivo blank: false
    }

    def beforeInsert = {
        fechaDeAsignacion = new java.util.Date()
    }

    def afterInsert = {
        def liquidacionDePlata = LiquidacionDePlata.get(liquidacionId)
        liquidacionDePlata.conjuntoPlata=conjuntoDestino
        liquidacionDePlata.save(failOnError: true)

        def reimpresion = new Reimpresion(fecha: new java.util.Date(),
                nombreReporte: "ASIGNACION DE LOTE A CONJUNTO DE PLATA",
                identificadorDocumento: "N/A",
                lote: "SN${lote}",
                motivo: motivo)
        reimpresion.save(failOnError: true)
    }

    def afterUpdate = {
        def liquidacionDePlata = LiquidacionDePlata.get(liquidacionId)
        liquidacionDePlata.conjuntoPlata=conjuntoDestino
        liquidacionDePlata.save(failOnError: true)

        def reimpresion = new Reimpresion(fecha: new java.util.Date(),
                nombreReporte: "ASIGNACION DE LOTE A CONJUNTO DE PLATA",
                identificadorDocumento: "N/A",
                lote: "AG${lote}",
                motivo: motivo)
        reimpresion.save(failOnError: true)
    }
}
