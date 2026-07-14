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
    // Nota (D4): estas columnas *Final ahora cargan la ley PROMEXBOL de ControlCalidadComplejo
    // (se reusa el nombre de columna para no romper el esquema; la fuente cambia en F5).
    BigDecimal porcentajeZincFinal
    BigDecimal porcentajePlomoFinal
    BigDecimal porcentajePlataFinal
    BigDecimal kilosFinosZinc
    BigDecimal kilosFinosPlomo
    BigDecimal kilosFinosPlata
    BigDecimal precioTonelada
    BigDecimal valorOficialBruto
    BigDecimal valorNetoMineralEnBolivianos
    BigDecimal liquidoPagable       // líquido pagable del lote (solo si liquidado; ver D3). Poblado en F5.
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
        liquidoPagable nullable: true
        costoUnitarioTransporte()
        costoDeTransporte nullable: false
        costoManipuleo nullable: false
        bonos nullable: false
        valorDeCompra nullable: false
    }
}
