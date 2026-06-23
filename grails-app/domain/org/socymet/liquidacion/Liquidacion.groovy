package org.socymet.liquidacion

import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.seguridad.SecUser

class Liquidacion {
    Empresa empresa
    Deposito deposito
    String nombreDeposito
    Date fechaDeLiquidacion
    BigDecimal kilosNetosHumedos
    BigDecimal kilosNetosSecos
    BigDecimal valorOficialBruto
    BigDecimal valorPorTonelada
    BigDecimal margen = 0
    //llenado opcional, puede elegirse entre la regalia total (obtenida con alicuotas y cotizaciones quincenales
    //o mediante un porcentaje del total del valor del mineral
    //String porcentajeRegalia //es String para poder almacenarlo como nulo en la base de datos
    String porcentajeRegalia = "0"
    BigDecimal regaliaMinera

    BigDecimal valorNetoMineral
    BigDecimal valorNetoMineralEnBolivianos
    BigDecimal bonoCalidad
    BigDecimal bonoIncentivo
    BigDecimal valorDeCompra
    BigDecimal totalRetenciones
    BigDecimal totalPagado
    BigDecimal anticipoPorPagar
    BigDecimal totalAnticiposContraEntrega
    BigDecimal totalAnticiposContraFuturaEntrega
    BigDecimal totalLiquidoPagable
    String totalLiquidoPagableLiteral="-"
    BigDecimal totalLiquidoPagableOriginal=0
    BigDecimal diferenciaLiquidoPagable=0
    String observaciones="-"
    //fecha de cancelacion
    Date fechaDeCancelacion
    //informacion util para reliquidacion
    Date fechaUltimaModificacion
    String motivoDeModificacion

    Integer liquidado
    String nombreComposito="-"
    SecUser usuario

    static constraints = {
    }
}
