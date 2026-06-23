package org.socymet.anticipos

import org.socymet.proveedor.Cliente
import org.socymet.proveedor.Empresa

class EstadoDeCuenta {
    static auditable = true

    Cliente cliente
    Empresa empresa
    String ci
    String nombre
    String nombreEmpresa
    Date fecha
    Integer numeroComprobante
    String detalle
    BigDecimal debe
    BigDecimal haber
    BigDecimal saldo
    Integer liquidacionId

    // Referencia polimórfica al documento que originó el movimiento (control de deudas
    // del cliente). liquidacionId se conserva por compatibilidad con los Liquidacion*
    // aún no migrados a este esquema.
    TipoMovimiento tipoMovimiento
    Long origenId

    static constraints = {
        cliente()
        empresa()
        ci(blank: false)
        nombre(blank: false)
        nombreEmpresa(blank: false)
        fecha(blank: false)
        numeroComprobante(blank: false)
        detalle(blank: false)
        debe(blank: false)
        haber(blank: false)
        saldo(blank: false)
        liquidacionId()
        tipoMovimiento nullable: true
        origenId nullable: true
    }
}
