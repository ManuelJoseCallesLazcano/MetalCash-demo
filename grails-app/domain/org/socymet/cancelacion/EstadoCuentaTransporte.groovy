package org.socymet.cancelacion

import org.socymet.proveedor.Automovil
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo

class EstadoCuentaTransporte {
    RecepcionDeComplejo recepcionDeComplejo
    String solicitante
    Empresa empresa
    Automovil automovil
    String ci
    String nombreResponsable
    Date fecha
    String descripcion
    BigDecimal ingreso
    BigDecimal egreso
    BigDecimal saldo // "anticipo disponible por consumir" corriente del automovil

    // Trazabilidad polimorfica al documento origen (estilo ledger del cliente)
    String tipoMovimiento // ANTICIPO_TRANSPORTE | PAGO_TRANSPORTE | REVERSA_ANTICIPO_TRANSPORTE | REVERSA_PAGO_TRANSPORTE
    Long origenId         // id del AnticipoPorTransporte o PagoTransporte de origen

    static constraints = {
        recepcionDeComplejo(nullable: true)
        solicitante blank: false
        empresa nullable: true
        automovil nullable: true
        ci blank: false
        nombreResponsable blank: false
        fecha nullable: false
        descripcion blank: false
        ingreso nullable: false
        egreso nullable: false
        saldo nullable: false
        tipoMovimiento nullable: true // nullable para no romper filas historicas
        origenId nullable: true
    }

    /**
     * Saldo disponible (anticipo por consumir) corriente del automovil:
     * el saldo del ultimo asiento registrado, o 0 si no hay movimientos.
     * Fuente unica de verdad del disponible; reemplaza el ultimoSaldo pasado a mano.
     */
    static BigDecimal saldoDisponible(Automovil automovil) {
        if (!automovil) return 0.0G
        def ultimo = EstadoCuentaTransporte.findByAutomovil(automovil, [sort: 'id', order: 'desc'])
        return ultimo?.saldo ?: 0.0G
    }
}
