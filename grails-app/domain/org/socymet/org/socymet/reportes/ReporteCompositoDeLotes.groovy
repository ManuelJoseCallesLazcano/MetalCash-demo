package org.socymet.org.socymet.reportes

import org.smart.compositos.Comprador
import org.smart.compositos.Ingenio
import org.smart.parametros.GestionMinera
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.seguridad.SecUser

class ReporteCompositoDeLotes {
    static auditable = true

    Deposito deposito

    Date gestionMinera            // numeración reinicia por gestión (D9)

    Integer numeroComposito
    String sigla
    String destino
    String nombreDestino="-"
    Comprador comprador
    Ingenio ingenio
    String elaboradoPor
    Date fechaDeElaboracion
    String estadoDelComposito //definitivo, provisional
    Boolean anulado = false      // baja lógica (D7): conserva el registro, libera los lotes

    Empresa empresa

    String ordenarElemento

    Date fechaInicial
    Date fechaFinal

    BigDecimal leyMinimaZinc=0.0
    BigDecimal leyMaximaZinc=100.0
    BigDecimal leyMinimaPlomo=0.0
    BigDecimal leyMaximaPlomo=100.0
    BigDecimal leyMinimaPlata=0.0
    BigDecimal leyMaximaPlata=10000.0

    String lotes = "[]"            // DEPRECADOS (D2): reemplazados por la tabla hija; se conservan
    String lotesComposito = "[]"   // como columnas para migración hasta retirarlos.

    BigDecimal totalKilosBrutos
    BigDecimal totalKilosNetosSecos
    BigDecimal leyPromedioZinc
    BigDecimal leyPromedioPlomo
    BigDecimal leyPromedioPlata
    BigDecimal totalKilosFinosZinc
    BigDecimal totalKilosFinosPlomo
    BigDecimal totalKilosFinosPlata
    BigDecimal totalValorNeto
    BigDecimal totalLiquidoPagable   // suma del líquido pagable de lotes liquidados (D3)
    BigDecimal totalValorDeCompra

    String participacion = "[]"    // DEPRECADO (D2)

    String observaciones="-"

    SecUser usuario

    transient springSecurityService

    static constraints = {
        gestionMinera nullable: true    // asignada en beforeValidate; nullable por filas legacy
        numeroComposito nullable: true, unique: 'gestionMinera'
        sigla blank: false, unique: true
        destino inList: ["VENTA","EXPORTACION","INGENIO"], blank: false
        nombreDestino()
        // EXPORTACION se trata como comprador (D10): comprador requerido salvo destino INGENIO
        comprador(nullable: true, validator: { val, obj ->
            if (obj.destino != "INGENIO" && val == null) return 'requerido'
        })
        ingenio(nullable: true, validator: { val, obj ->
            if (obj.destino == "INGENIO" && val == null) return 'requerido'
        })
        elaboradoPor blank: false
        fechaDeElaboracion nullable: false
        estadoDelComposito inList: ["PROVISIONAL","DEFINITIVO"], blank: false
        anulado nullable: true
        empresa nullable: true
        ordenarElemento(inList: ["ZINC","PLOMO","PLATA"])
        fechaInicial(nullable: true)   // filtros opcionales usados al conformar
        fechaFinal(nullable: true)
        leyMinimaZinc min: 0.0, max: 100.0, nullable: false
        leyMaximaZinc min: 0.0, max: 100.0, nullable: false
        leyMinimaPlomo min: 0.0, max: 100.0, nullable: false
        leyMaximaPlomo min: 0.0, max: 100.0, nullable: false
        leyMinimaPlata min: 0.0, max: 10000.0, nullable: false
        leyMaximaPlata min: 0.0, max: 10000.0, nullable: false
        lotes()
        lotesComposito()
        totalKilosBrutos min: 0.0, nullable: false
        totalKilosNetosSecos min: 0.0, nullable: false
        leyPromedioZinc min: 0.0, max: 100.0, nullable: false
        leyPromedioPlomo min: 0.0, max: 100.0, nullable: false
        leyPromedioPlata min: 0.0, max: 10000.0, nullable: false
        totalKilosFinosZinc min: 0.0, nullable: false
        totalKilosFinosPlomo min: 0.0, nullable: false
        totalKilosFinosPlata min: 0.0, nullable: false
        totalValorNeto min: 0.0, nullable: false
        totalLiquidoPagable min: 0.0, nullable: true
        totalValorDeCompra min: 0.0, nullable: false
        participacion()
        observaciones blank: true

        usuario display: false, nullable: true
    }

    static mapping = {
        lotes type: 'text'
        lotesComposito type: 'text'
        participacion type: 'text'
    }

    // La validación corre antes de beforeInsert; asignamos aquí los campos calculados nullable:false.
    def beforeValidate = {
        if (this.numeroComposito == null) {
            this.gestionMinera = gestionMineraActiva()
            def maxNumero = ReporteCompositoDeLotes.createCriteria().get {
                eq('gestionMinera', this.gestionMinera)
                projections { max('numeroComposito') }
            }
            this.numeroComposito = (maxNumero ?: 0) + 1
        }
        // Fecha de elaboración = momento del registro o última modificación; elaboradoPor = usuario actual.
        this.fechaDeElaboracion = new Date()
        if (!this.elaboradoPor) this.elaboradoPor = springSecurityService.getCurrentUser()?.nombre ?: '-'
    }

    /** Gestión minera actualmente activa. */
    static Date gestionMineraActiva() {
        GestionMinera.findByEstado("ACTIVO").gestion
    }

    def beforeInsert = {
        this.nombreDestino = this.destino == "INGENIO" ? this.ingenio?.nombreIngenio : this.comprador?.nombreComprador
        this.usuario = springSecurityService.getCurrentUser()
    }

    def beforeUpdate = {
        this.nombreDestino = this.destino == "INGENIO" ? this.ingenio?.nombreIngenio : this.comprador?.nombreComprador
        this.usuario = springSecurityService.getCurrentUser()
    }

    // Los hijos (detalle/participación) y la RESERVA de lotes los arma el controller/service con la
    // selección de recepciones (contrato limpio de F4/F5). Al borrar, se liberan los lotes reservados
    // vía HQL (no .save(): re-dispararía beforeValidate de la recepción y podría hacer rollback).
    def beforeDelete = {
        def detalles = CompositoDeLotesDetalle.findAllByReporteCompositoDeLotes(this)
        def ids = detalles.collect { it.recepcionId as Long }
        if (ids) RecepcionDeComplejo.executeUpdate(
                "update RecepcionDeComplejo set nombreComposito = '-' where id in :ids and nombreComposito = :sigla",
                [ids: ids, sigla: this.sigla])
        detalles*.delete()
        CompositoLotesParticipacion.findAllByReporteCompositoDeLotes(this)*.delete()
    }

    String toString(){
        sigla ?: "Compósito ${id}"
    }
}
