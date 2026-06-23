package org.socymet.liquidacion

import org.socymet.recepcion.RecepcionDeEstano

class CancelacionDeEstano {
    RecepcionDeEstano recepcionDeEstano
    LiquidacionDeEstano liquidacionDeEstano

    String lote
    String nombreCliente
    String nombreEmpresa
    Integer numeroLiquidacionEstano //e.g.: GENERADO: 1, PARA MOSTRAR: 0001
    String fechaDeRecepcion
    String fechaDeLiquidacion
    BigDecimal totalLiquidoPagable

    static constraints = {
        lote(blank: false)
        nombreCliente(blank: false)
        nombreEmpresa(blank: false)
        fechaDeRecepcion(blank: false)
    }
}
