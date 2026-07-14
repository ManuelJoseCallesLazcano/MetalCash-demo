package org.socymet.anticipos

import org.socymet.cancelacion.EstadoCuentaTransporte
import org.socymet.proveedor.Automovil
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.seguridad.SecUser

class AnticipoPorTransporte {
    static auditable = true

    Integer numeroComprobante
    Date gestionMinera // la numeración reinicia en cada gestión minera
    // Anticipo puro al automovil (adelanto por transporte); ya NO atado a una recepcion/lote.
    // recepcionDeComplejo se conserva nullable solo por compatibilidad con datos historicos.
    RecepcionDeComplejo recepcionDeComplejo
    String solicitante
    Empresa empresa
    Automovil automovil // titular del ledger de transporte
    String ci
    String nombreCobrador
    Date fecha
    String descripcion="ANTICIPO POR TRANSPORTE"
    BigDecimal ultimoSaldo=0 // informativo: disponible antes de este anticipo (se deriva, no fuente de verdad)
    BigDecimal importe=0
    String importeLiteral
    String observaciones="-"
    Boolean anulado=false
    SecUser usuario

    transient springSecurityService

    static constraints = {
        // El correlativo se repite entre gestiones, pero es único dentro de una misma gestión
        numeroComprobante unique: 'gestionMinera', nullable: true
        // nullable:true para que dbCreate:update pueda AGREGAR la columna a la tabla legada
        // (MySQL no permite añadir NOT NULL con filas existentes). El beforeValidate la fija
        // siempre en las altas, así que la numeración por gestión igual funciona.
        gestionMinera nullable: true
        recepcionDeComplejo(nullable: true)
        anulado nullable: true
        solicitante inList: ["Empresa","Particular"], nullable: false
        empresa nullable: true
        automovil nullable: false
        ci blank: false
        nombreCobrador blank: false
        fecha()
        descripcion blank: false
        ultimoSaldo nullable: false
        importe min: 0.0, nullable: false
        importeLiteral blank: false
        observaciones blank: true
        usuario nullable: true
    }

    // gestionMinera y numeroComprobante se calculan en backend; gestionMinera es
    // nullable:false y la validación corre antes del insert (estilo Amortizacion).
    def beforeValidate = {
        if (this.numeroComprobante != null) return   // solo en el alta

        this.gestionMinera = RecepcionDeComplejo.gestionMineraActiva()

        // Siguiente correlativo dentro de la gestión activa (reinicia por gestión)
        def maxNumeroComprobante = AnticipoPorTransporte.createCriteria().get {
            eq 'gestionMinera', this.gestionMinera
            projections { max 'numeroComprobante' }
        }
        this.numeroComprobante = (maxNumeroComprobante ?: 0) + 1
    }

    def beforeInsert = {
        // Disponible actual del automovil (fuente de verdad = ultimo asiento del ledger)
        this.ultimoSaldo = EstadoCuentaTransporte.saldoDisponible(this.automovil)

        this.usuario = springSecurityService.getCurrentUser()
    }

    def afterInsert = {
        // Convencion "disponible por consumir": el anticipo SUBE el disponible del automovil.
        def disponible = EstadoCuentaTransporte.saldoDisponible(this.automovil)
        def estadoCuentaTransporte = new EstadoCuentaTransporte(
                solicitante: solicitante,
                empresa: empresa,
                automovil: automovil,
                ci: ci,
                nombreResponsable: nombreCobrador,
                fecha: fecha,
                descripcion: "${descripcion}",
                ingreso: importe,
                egreso: 0,
                saldo: disponible + importe,
                tipoMovimiento: "ANTICIPO_TRANSPORTE",
                origenId: this.id
        )
        estadoCuentaTransporte.save(failOnError: true)
    }

    /**
     * Motivos por los que el anticipo por transporte NO puede editarse ni eliminarse (vacío = libre).
     * Se bloquea si está anulado (registro histórico; su reversa ya se aplicó al ledger de transporte).
     */
    List<String> motivosBloqueo() {
        def m = []
        if (anulado) m << 'el anticipo está anulado'
        m
    }

    String toString(){
        "${numeroComprobante}/${gestionMinera ? new java.text.SimpleDateFormat('yy').format(gestionMinera) : '?'}"
    }
}
