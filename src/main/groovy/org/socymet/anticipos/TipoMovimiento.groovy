package org.socymet.anticipos

/**
 * Origen de un movimiento del libro mayor del cliente ({@link EstadoDeCuenta}).
 * Junto con {@code origenId} forma una referencia polimórfica al documento que
 * generó el movimiento, para el control de deudas del cliente.
 *
 * Por ahora cubre los tres orígenes acordados; puede crecer a los demás tipos
 * de Liquidacion* cuando se generalice el ledger.
 */
enum TipoMovimiento {
    ANTICIPO_FUTURA_ENTREGA,
    AMORTIZACION,
    LIQUIDACION_COMPLEJO
}
