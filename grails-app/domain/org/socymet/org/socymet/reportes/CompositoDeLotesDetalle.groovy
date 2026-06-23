package org.socymet.org.socymet.reportes

class CompositoDeLotesDetalle {
    ReporteCompositoDeLotes reporteCompositoDeLotes

    BigInteger recepcionId
    BigInteger liquidacionId

    Date fechaDeRecepcion
    String lote
    String nombreEmpresa
    String departamento
    String municipio
    String proveedor
    BigDecimal pesoBruto
    BigDecimal porcentajeHumedad
    BigDecimal kilosNetosSecos
    BigDecimal porcentajeZincFinal
    BigDecimal porcentajePlomoFinal
    BigDecimal porcentajePlataFinal
    BigDecimal kilosFinosZinc
    BigDecimal kilosFinosPlomo
    BigDecimal kilosFinosPlata
    BigDecimal precioTonelada
    BigDecimal valorOficialBruto
    BigDecimal valorNetoMineralEnBolivianos
    BigDecimal costoUnitarioTransporte
    BigDecimal costoDeTransporte
    BigDecimal costoManipuleo
    BigDecimal bonos
    BigDecimal valorDeCompra
    
    static constraints = {
        reporteCompositoDeLotes nullable: false
        recepcionId nullable: false
        liquidacionId nullable: false

        fechaDeRecepcion nullable: false
        lote blank: false, nullable: false
        nombreEmpresa blank: false, nullable: false
        departamento()
        municipio()
        proveedor()
        pesoBruto nullable: false
        porcentajeHumedad()
        kilosNetosSecos nullable: false
        porcentajeZincFinal nullable: false
        porcentajePlomoFinal nullable: false
        porcentajePlataFinal nullable: false
        kilosFinosZinc nullable: false
        kilosFinosPlomo nullable: false
        kilosFinosPlata nullable: false
        precioTonelada()
        valorOficialBruto()
        valorNetoMineralEnBolivianos nullable: false
        costoUnitarioTransporte()
        costoDeTransporte nullable: false
        costoManipuleo nullable: false
        bonos nullable: false
        valorDeCompra nullable: false
    }
}
