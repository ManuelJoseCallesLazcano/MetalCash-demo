package org.socymet.cancelacion

import org.grails.web.json.JSONArray
import org.socymet.proveedor.Automovil
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.seguridad.SecUser

class PagoTransporte {
    static auditable = true

    static searchable = true

    Deposito deposito

    Integer numeroComprobante
    Date gestionMinera // la numeración reinicia en cada gestión minera
    String ci
    String nombreCobrador

    Date fechaDePago

    String solicitante
    Empresa empresa
    Automovil automovil

    String lotes

    Integer recepcionId
    BigDecimal pesoBruto=0
    BigDecimal precioTonelada=0
    String descripcion="POR EL LOTE: "
    BigDecimal total=0
    BigDecimal totalAnticipos=0
    BigDecimal totalPagable=0
    String totalPagableLiteral

    String observaciones

    Boolean anulado=false

    SecUser usuario

    transient springSecurityService

    static constraints = {
        deposito nullable: false
        anulado nullable: true
        // El correlativo se repite entre gestiones, pero es único dentro de una misma gestión
        numeroComprobante unique: 'gestionMinera', nullable: true
        // nullable:true para que dbCreate:update pueda AGREGAR la columna a la tabla legada
        // (MySQL no permite añadir NOT NULL con filas existentes). El beforeValidate la fija
        // siempre en las altas, así que la numeración por gestión igual funciona.
        gestionMinera nullable: true
        ci blank: false
        nombreCobrador blank: false

        fechaDePago nullable: false

        solicitante inList: ["Empresa","Particular"], nullable: false
        empresa nullable: true
        automovil nullable: true

        lotes blank: false, nullable: false

        descripcion blank: false, nullable: false

        total nullable: false, min: 0.0
        totalAnticipos nullable: false, min: 0.0
        totalPagable nullable: false, min: 0.0
        totalPagableLiteral blank: false
        observaciones blank: true, nullable: true

        usuario display: false, nullable: true
    }

    static mapping = {
        lotes type: 'text'
        descripcion type: 'text'
    }

    def beforeValidate = {
        // Backend autoritativo: deriva titular/total/anticipoAplicado desde los lotes (solo en el alta).
        if (this.id != null) return              // en updates no se recalcula el disponible ya consumido

        // Numeración por gestión minera (reinicia el correlativo en cada gestión; estilo Amortizacion)
        if (this.numeroComprobante == null) {
            this.gestionMinera = RecepcionDeComplejo.gestionMineraActiva()
            def maxNumeroComprobante = PagoTransporte.createCriteria().get {
                eq 'gestionMinera', this.gestionMinera
                projections { max 'numeroComprobante' }
            }
            this.numeroComprobante = (maxNumeroComprobante ?: 0) + 1
        }

        if (!this.lotes) return
        def lotesJSON = new JSONArray(this.lotes)
        if (lotesJSON.length() == 0) return

        def recepciones = []
        lotesJSON.each {
            def rec = RecepcionDeComplejo.get(it.getAt("recepcionId").toString().toLong())
            if (rec != null) recepciones << rec
        }
        if (!recepciones) return

        // Regla: un pago = un automovil = un ledger. Todos los lotes deben ser del mismo automovil.
        def automoviles = recepciones.collect { it.automovil?.id }.unique()
        if (automoviles.size() > 1) {
            errors.rejectValue('lotes', 'pagoTransporte.lotes.multipleAutomoviles',
                    'Todos los lotes de un pago deben pertenecer al mismo automóvil.')
            return
        }

        this.automovil = recepciones[0].automovil
        this.empresa = recepciones[0].empresa
        this.recepcionId = recepciones[0].id?.toInteger()
        if (!this.solicitante) this.solicitante = this.empresa ? "Empresa" : "Particular"

        // Peso bruto del pago = Σ peso bruto de los lotes cubiertos.
        this.pesoBruto = recepciones.sum { it.pesoBruto ?: 0.0G } ?: 0.0G

        // Total = Σ costoDeTransporte por lote (default editable): respeta el override > 0 del cajero.
        def sumaCosto = recepciones.sum { it.costoDeTransporte ?: 0.0G } ?: 0.0G
        if (this.total == null || this.total <= 0.0G) this.total = sumaCosto

        // Anticipo aplicado EDITABLE (cobrar fracción): respeta el valor enviado por el cajero
        // pero lo clampea a [0, min(total, disponible)] — el backend es autoritativo sobre el tope.
        def disponible = EstadoCuentaTransporte.saldoDisponible(this.automovil)
        def tope = (this.total < disponible) ? this.total : disponible
        def aplicado = this.totalAnticipos ?: 0.0G
        if (aplicado < 0.0G) aplicado = 0.0G
        if (aplicado > tope) aplicado = tope
        this.totalAnticipos = aplicado
        this.totalPagable = this.total - aplicado
    }

    def beforeInsert = {
        // fechaDePago viene del formulario (datepicker, por defecto hoy); respaldo por si faltara
        if (this.fechaDePago == null) this.fechaDePago = new java.util.Date()
        this.usuario = springSecurityService.getCurrentUser() as SecUser
        this.deposito = usuario.deposito
    }

    // El post-registro (detalle por lote, marcar transportePagado, asiento en el ledger) se hace
    // en PagoTransporteController.save() con el pago YA persistido. NO en afterInsert: el detalle
    // referencia el pago como FK y dentro de afterInsert el pago aún es transitorio
    // (TransientPropertyValueException); withNewTransaction, por su parte, cruzaba proxies de sesión.

    def afterUpdate = {
//        def anticipoDetalleAnteriores = AnticipoDetalle.findAllByAnticipo(this)
//        anticipoDetalleAnteriores.each {
//            log.error("**** ELIMINANDO: ${it.lote} PESO BRUTO: ${it.pesoBruto}")
//            it.delete()
//        }
//
//        Anticipo.withNewTransaction {
//            //actualizando el estado del lote
//            //this.anticipoPagado = "SI" //este campo no ha sido declarado
//            def retencionesJSON = new JSONArray(lotes)
//            retencionesJSON.each {
//                def recepcion = RecepcionDeComplejo.get(it.getAt("recepcionId").toString().toInteger())
//                recepcion.estadoAnticipo="CON ANTICIPO"
//                recepcion.save(failOnError: true)
//
//                def lote = it.getAt("lote")
//                def recepcionId = it.getAt("recepcionId")
//                def nombreChofer = it.getAt("nombreChofer")
//                def nombreEmpresa = it.getAt("nombreEmpresa")
//                def fechaDeRecepcion = it.getAt("fechaDeRecepcion")
//                def pesoBruto = it.getAt("pesoBruto")
//
//                log.error("**** LOTE: ${lote} - ${recepcionId} - ${nombreChofer} - ${nombreEmpresa} - ${fechaDeRecepcion} - ${pesoBruto}")
//                def anticipoDetalle = new AnticipoDetalle(
//                        anticipo: this,
//                        lote: lote,
//                        recepcionId: recepcionId,
//                        nombreChofer: nombreChofer,
//                        nombreEmpresa: nombreEmpresa,
//                        fechaDeRecepcion: fechaDeRecepcion,
//                        pesoBruto: pesoBruto
//                )
//                anticipoDetalle.save(failOnError: true)
//            }
//        }
    }

//    String toString(){
//        "Cobrador: ${nombreCobrador} [${ci}] Fecha de Pago: ${fechaDePago.format('dd/MM/yyyy')} Solicitante: ${(solicitante.equals("Empresa"))?empresa.toString():automovil.toString()} Lotes: ${descripcion}"
//    }

    /**
     * Motivos por los que el pago de transporte NO puede editarse ni eliminarse (vacío = libre).
     * Se bloquea si está anulado (registro histórico; su reversa ya se aplicó al ledger de transporte).
     */
    List<String> motivosBloqueo() {
        def m = []
        if (anulado) m << 'el pago está anulado'
        m
    }

    String toString(){
        "${numeroComprobante}/${gestionMinera ? new java.text.SimpleDateFormat('yy').format(gestionMinera) : '?'}"
    }
}
