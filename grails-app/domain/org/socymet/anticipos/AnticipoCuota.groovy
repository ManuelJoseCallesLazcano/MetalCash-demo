package org.socymet.anticipos

import org.smart.parametros.GestionMinera
import org.socymet.proveedor.Deposito
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.seguridad.SecUser

/**
 * Un anticipo individual emitido sobre un encabezado {@link Anticipo}.
 * Cada cuota es "un anticipo emitido": tiene su propio número de comprobante
 * y su fecha de emisión. El comprobante se reinicia con cada gestión minera
 * (correlativo único dentro de la gestión activa).
 */
class AnticipoCuota {
    static auditable = true

    static belongsTo = [anticipo: Anticipo]

    Integer numeroComprobante
    BigDecimal monto
    Date fecha
    Date gestionMinera

    SecUser usuario
    Deposito deposito

    transient springSecurityService

    static constraints = {
        // El correlativo se repite entre gestiones, pero es único dentro de una misma gestión
        numeroComprobante unique: 'gestionMinera', nullable: false
        monto min: 0.0G, nullable: false
        fecha nullable: false
        gestionMinera nullable: false
        usuario display: false, nullable: true
        deposito nullable: true
    }

    // Asignación en beforeValidate (NO beforeInsert): gestionMinera y numeroComprobante
    // son nullable:false y se calculan en backend, por lo que deben estar presentes
    // ANTES de que corra la validación.
    def beforeValidate = {
        if (this.numeroComprobante != null) return   // solo en alta de la cuota

        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        this.usuario = usuarioActual
        this.deposito = usuarioActual?.deposito
        this.gestionMinera = RecepcionDeComplejo.gestionMineraActiva()

        // Siguiente comprobante dentro de la gestión activa (reinicia por gestión)
        def maxComprobante = AnticipoCuota.createCriteria().get {
            eq 'gestionMinera', this.gestionMinera
            projections { max 'numeroComprobante' }
        }
        this.numeroComprobante = (maxComprobante ?: 0) + 1
    }

    String toString() {
        "Comprobante ${numeroComprobante} (${gestionMinera ? new java.text.SimpleDateFormat('yyyy').format(gestionMinera) : '?'}): ${monto}"
    }
}
