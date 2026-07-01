package org.socymet.liquidacion

import org.grails.web.json.JSONArray
import org.socymet.anticipos.AnticipoContraEntrega
import org.socymet.anticipos.AnticipoContraFuturaEntrega
import org.socymet.anticipos.AnticipoDetalle
import org.socymet.anticipos.EstadoDeCuenta
import org.socymet.anticipos.TipoMovimiento
import org.socymet.cotizaciones.TablaOrigenCotizacionesComplejo
import org.socymet.cotizaciones.TablaPrecioPorLme
import org.socymet.cotizaciones.TerminosDeContrato
import org.socymet.org.socymet.reportes.Reimpresion
import org.socymet.proveedor.Cliente
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.utilidades.NumeroALiteral

import java.text.DecimalFormat

class LiquidacionDeComplejo extends Liquidacion{
    static auditable = true

    static searchable = true

    // Retenciones como tabla hija (reemplaza el blob String `retenciones`, que se conserva para migración)
    static hasMany = [detalleRetenciones: LiquidacionDeComplejoRetenciones]
    static mappedBy = [detalleRetenciones: 'liquidacionDeComplejo']

    Integer numeroLiquidacionComplejo //e.g.: GENERADO: 1, PARA MOSTRAR: 0001
    String conjuntoComplejo="-"
    //informacion de recepcion (duplicando para facilitar generacion de reportes)
    RecepcionDeComplejo recepcionDeComplejo
    Cliente cliente                 // referencia directa al cliente (para reportes)

    String lote
    String tipoDeMineral
    String nombreCliente
    String nombreEmpresa
    String fechaDeRecepcion
    Date fechaRecepcion             // fecha de recepción como Date (para reportes)
    String cantidadDeSacos
    Integer cantidadSacos           // cantidad de sacos (numérico, recuperado de la recepción; para deducciones por SACO)
    String estadoDelLote
    String naturalezaMineral

    BigDecimal pesoBruto
    BigDecimal cotizacionDiariaDeZinc
    BigDecimal cotizacionQuincenalDeZinc
    BigDecimal alicuotaDeZinc
    BigDecimal cotizacionDiariaDePlomo
    BigDecimal cotizacionQuincenalDePlomo
    BigDecimal alicuotaDePlomo
    BigDecimal cotizacionDiariaDePlata
    BigDecimal cotizacionQuincenalDePlata
    BigDecimal alicuotaDePlata
    BigDecimal tipoDeCambioOficial
    BigDecimal tipoDeCambioComercial
    //informacion de analisis de laboratorio y puntos a pagar
    BigDecimal dolarPuntoZinc
    BigDecimal dolarPuntoPlomo
    BigDecimal dolarPuntoPlata

    BigDecimal porcentajeZincPromexbol
    BigDecimal porcentajePlomoPromexbol
    BigDecimal porcentajePlataPromexbol
    BigDecimal porcentajeHumedadPromexbol
    BigDecimal porcentajeMermaPromexbol

    BigDecimal porcentajeZincCliente
    BigDecimal porcentajePlomoCliente
    BigDecimal porcentajePlataCliente
    BigDecimal porcentajeHumedadCliente
    BigDecimal porcentajeMermaCliente

    BigDecimal porcentajeZincFinal
    BigDecimal porcentajePlomoFinal
    BigDecimal porcentajePlataFinal
    BigDecimal porcentajeHumedadFinal
    BigDecimal porcentajeMermaFinal

    String modoValoracion
    TablaOrigenCotizacionesComplejo tablaComplejo
    TablaPrecioPorLme tablaPrecioPorLme
    TerminosDeContrato terminosDeContrato
     //pesos
    BigDecimal kilosFinosZinc
    BigDecimal kilosFinosPlata
    BigDecimal kilosFinosPlomo
    BigDecimal librasFinasDeZinc
    BigDecimal librasFinasDePlomo
    BigDecimal onzasTroyDePlata
    //valores brutos
    BigDecimal valorOficialBrutoDeZinc
    BigDecimal valorOficialBrutoDePlata
    BigDecimal valorOficialBrutoDePlomo
    BigDecimal valorOficialBrutoDeZincEnBolivianos
    BigDecimal valorOficialBrutoDePlataEnBolivianos
    BigDecimal valorOficialBrutoDePlomoEnBolivianos
    //retenciones
    String retenciones

    //adelanto por liquidacion provisional del lote en cuestion
    BigDecimal adelantoPorLiquidacionProvisional

    BigDecimal bonoTransporteKilosNetosSecosTotal=0

    String aplicarCostoTratamiento
    BigDecimal costoTratamiento
    BigDecimal pesoBrozaInicial
    BigDecimal costoTratamientoTotal

    BigDecimal totalLiquidoPagableFinal=0

    //costos de laboratorio
    String detalleLaboratorio1
    BigDecimal costoLaboratorio1
    String detalleLaboratorio2
    BigDecimal costoLaboratorio2
    String detalleLaboratorio3
    BigDecimal costoLaboratorio3
    String detalleLaboratorio4
    BigDecimal costoLaboratorio4
    BigDecimal totalCostoLaboratorio

    // ── Rediseño liquidación (Fase 2) ──────────────────────────────────────
    // Numeración por gestión minera (reinicia por gestión) + anulación
    Date gestionMinera
    Boolean anulado = false

    // Regalía Minera por mineral ($us y Bs). El total en Bs va en base.regaliaMinera.
    BigDecimal regaliaMineraDeZinc
    BigDecimal regaliaMineraDePlomo
    BigDecimal regaliaMineraDePlata
    BigDecimal regaliaMineraDeZincEnBolivianos
    BigDecimal regaliaMineraDePlomoEnBolivianos
    BigDecimal regaliaMineraDePlataEnBolivianos
    BigDecimal totalRegaliaMineraDolares

    // VBV total: $us en base.valorOficialBruto; total en Bs:
    BigDecimal valorOficialBrutoEnBolivianos

    // Valor Pagable del Mineral (VNV Bs − total deducciones)
    BigDecimal valorPagableMineral

    // Bonos (manuales por ahora): bonoCalidad/bonoIncentivo en base; agregar transporte, lealtad y total
    BigDecimal bonoTransporte
    BigDecimal bonoLealtad
    BigDecimal totalBonos

    // Anticipos y otros saldos
    BigDecimal saldoAnterior
    BigDecimal totalAnticipos

    // Precio calculado ($us/TM)
    BigDecimal precioCalculado

    transient springSecurityService

    static constraints = {
        numeroLiquidacionComplejo unique: 'gestionMinera', nullable: true
        gestionMinera nullable: true
        anulado nullable: true
        conjuntoComplejo(blank: true)

        // Antes: unique:true → impedía re-liquidar un lote tras anular su liquidación (la anulada seguía
        // ocupando la referencia única). Ahora un validador permite re-liquidar si TODA liquidación previa
        // de esa recepción está anulada; solo falla si existe otra liquidación ACTIVA (no anulada).
        recepcionDeComplejo(nullable: false, validator: { val, obj ->
            if (val == null) return
            def activas = LiquidacionDeComplejo.createCriteria().count {
                eq 'recepcionDeComplejo', val
                or { eq 'anulado', false; isNull 'anulado' }   // activas = no anuladas (false o null)
                if (obj.id) ne 'id', obj.id                     // excluir el propio registro al actualizar
            }
            if (activas > 0) return 'yaLiquidado'
        })
        cliente nullable: true
        deposito nullable: false

        empresa nullable: false
        lote(blank: false)
        tipoDeMineral(blank: false)
        nombreDeposito nullable: false
        nombreCliente(blank: false)
        nombreEmpresa(blank: false)
        fechaDeRecepcion(blank: false)
        fechaRecepcion(nullable: true)
        cantidadDeSacos(blank: false)
        cantidadSacos(nullable: true)
        estadoDelLote(validator: {
            return !it.equals("NO LIQUIDADO")||!it.equals("Provisional")
        })
        naturalezaMineral blank: false, nullable: false
        pesoBruto()
        cotizacionDiariaDeZinc()
        cotizacionQuincenalDeZinc()
        alicuotaDeZinc()
        cotizacionDiariaDePlomo()
        cotizacionQuincenalDePlomo()
        alicuotaDePlomo()
        cotizacionDiariaDePlata()
        cotizacionQuincenalDePlata()
        alicuotaDePlata()
        tipoDeCambioOficial()
        tipoDeCambioComercial()
        
        fechaDeLiquidacion(blank: false, nullable: true)

        //informacion de analisis de laboratorio
        kilosNetosHumedos()
        kilosNetosSecos()
        dolarPuntoZinc()
        dolarPuntoPlomo()
        dolarPuntoPlata()

        porcentajeZincPromexbol nullable: false, min: 0.0, max: 100.0
        porcentajePlomoPromexbol nullable: false, min: 0.0, max: 100.0
        porcentajePlataPromexbol nullable: false, min: 0.0, max: 10000.0
        porcentajeHumedadPromexbol nullable: false, min: 0.0, max: 100.0
        porcentajeMermaPromexbol nullable: false, min: 0.0, max: 100.0

        porcentajeZincCliente nullable: false, min: 0.0, max: 100.0
        porcentajePlomoCliente nullable: false, min: 0.0, max: 100.0
        porcentajePlataCliente nullable: false, min: 0.0, max: 10000.0
        porcentajeHumedadCliente nullable: false, min: 0.0, max: 100.0
        porcentajeMermaCliente nullable: false, min: 0.0, max: 100.0

        porcentajeZincFinal nullable: false, min: 0.0, max: 100.0
        porcentajePlomoFinal nullable: false, min: 0.0, max: 100.0
        porcentajePlataFinal nullable: false, min: 0.0, max: 10000.0
        porcentajeHumedadFinal nullable: false, min: 0.0, max: 100.0
        porcentajeMermaFinal nullable: false, min: 0.0, max: 100.0

//        modoValoracion inList: ["TABLA","PRECIO POR LME","TERMINOS DE CONTRATO"]
        modoValoracion inList: ["MANUAL","TABLA","TERMINOS DE CONTRATO"], nullable: true
        tablaComplejo nullable: true
        tablaPrecioPorLme nullable: true
        terminosDeContrato nullable: true

        kilosFinosZinc()
        kilosFinosPlomo()
        kilosFinosPlata()
        librasFinasDeZinc()
        librasFinasDePlomo()
        onzasTroyDePlata()
        valorOficialBrutoDeZinc()
        valorOficialBrutoDePlomo()
        valorOficialBrutoDePlata()
        valorOficialBrutoDeZincEnBolivianos()
        valorOficialBrutoDePlomoEnBolivianos()
        valorOficialBrutoDePlataEnBolivianos()
        valorOficialBruto()

        valorPorTonelada()
        margen nullable: false, max: 100.0
        porcentajeRegalia(blank:true)
        regaliaMinera()

        retenciones(blank: false)

        valorNetoMineral()
        bonoCalidad()
        bonoIncentivo()
        valorDeCompra()
        totalRetenciones()
        totalPagado()
        anticipoPorPagar nullable: false
        totalAnticiposContraEntrega()
        totalAnticiposContraFuturaEntrega()
        adelantoPorLiquidacionProvisional()
        aplicarCostoTratamiento(inList: ["NO", "SI"])
        costoTratamiento()
        pesoBrozaInicial(min: 0.0)
        costoTratamientoTotal(min: 0.0)
        totalLiquidoPagable()
        totalLiquidoPagableLiteral nullable: true, blank: true
        totalLiquidoPagableOriginal()
        diferenciaLiquidoPagable()
        bonoTransporteKilosNetosSecosTotal()
        totalLiquidoPagableFinal()
        observaciones blank: true
        motivoDeModificacion blank: false
        fechaUltimaModificacion display: false, nullable: true // desde superclase
        nombreComposito blank: true, nullable: true // nombre de conjunto asignado
        liquidado display: false, nullable: true
        usuario display: false, nullable: true

        fechaDeCancelacion display: false, nullable: true // desde superclase

        //costos de laboratorio
        detalleLaboratorio1(blank:true, nullable: true)
        costoLaboratorio1(blank:true, nullable: true)
        detalleLaboratorio2(blank:true, nullable: true)
        costoLaboratorio2(blank:true, nullable: true)
        detalleLaboratorio3(blank:true, nullable: true)
        costoLaboratorio3(blank:true, nullable: true)
        detalleLaboratorio4(blank:true, nullable: true)
        costoLaboratorio4(blank:true, nullable: true)
        totalCostoLaboratorio(blank:true, nullable: true)

        // Campos del rediseño (Fase 2) — todos nullable (tabla `liquidacion` compartida y poblada)
        regaliaMineraDeZinc nullable: true
        regaliaMineraDePlomo nullable: true
        regaliaMineraDePlata nullable: true
        regaliaMineraDeZincEnBolivianos nullable: true
        regaliaMineraDePlomoEnBolivianos nullable: true
        regaliaMineraDePlataEnBolivianos nullable: true
        totalRegaliaMineraDolares nullable: true
        valorOficialBrutoEnBolivianos nullable: true
        valorPagableMineral nullable: true
        bonoTransporte nullable: true
        bonoLealtad nullable: true
        totalBonos nullable: true
        saldoAnterior nullable: true
        totalAnticipos nullable: true
        precioCalculado nullable: true
    }

    static mapping = {
        retenciones type: 'text'
    }

    // Numeración por gestión minera: gestionMinera y numeroLiquidacionComplejo se calculan
    // en backend; el correlativo reinicia en cada gestión (unique: 'gestionMinera').
    def beforeValidate = {
        // Defaults para campos legados (nullable:false) que el flujo de liquidación de complejo NO utiliza,
        // de modo que no produzcan errores de validación al registrar. Solo se asignan si están vacíos.
        if (this.motivoDeModificacion == null) this.motivoDeModificacion = "-"
        if (this.estadoDelLote == null) this.estadoDelLote = "LIQUIDADO"
        if (this.naturalezaMineral == null) this.naturalezaMineral = this.recepcionDeComplejo?.naturalezaMineral ?: "SULFURO"
        if (this.retenciones == null) this.retenciones = "[]"
        if (this.aplicarCostoTratamiento == null) this.aplicarCostoTratamiento = "NO"
        if (this.kilosNetosHumedos == null) this.kilosNetosHumedos = 0.0G
        if (this.bonoIncentivo == null) this.bonoIncentivo = 0.0G
        if (this.valorDeCompra == null) this.valorDeCompra = 0.0G
        if (this.totalPagado == null) this.totalPagado = 0.0G
        if (this.anticipoPorPagar == null) this.anticipoPorPagar = 0.0G
        if (this.dolarPuntoZinc == null) this.dolarPuntoZinc = 0.0G
        if (this.dolarPuntoPlomo == null) this.dolarPuntoPlomo = 0.0G
        if (this.dolarPuntoPlata == null) this.dolarPuntoPlata = 0.0G
        if (this.porcentajeHumedadCliente == null) this.porcentajeHumedadCliente = 0.0G
        if (this.porcentajeMermaCliente == null) this.porcentajeMermaCliente = 0.0G
        if (this.adelantoPorLiquidacionProvisional == null) this.adelantoPorLiquidacionProvisional = 0.0G
        if (this.pesoBrozaInicial == null) this.pesoBrozaInicial = 0.0G
        if (this.costoTratamiento == null) this.costoTratamiento = 0.0G
        if (this.costoTratamientoTotal == null) this.costoTratamientoTotal = 0.0G

        if (this.numeroLiquidacionComplejo != null) return   // solo en el alta

        this.gestionMinera = RecepcionDeComplejo.gestionMineraActiva()

        def maxNumeroLiquidacion = LiquidacionDeComplejo.createCriteria().get {
            eq 'gestionMinera', this.gestionMinera
            projections { max 'numeroLiquidacionComplejo' }
        }
        this.numeroLiquidacionComplejo = (maxNumeroLiquidacion ?: 0) + 1
    }

    def beforeInsert = {
        this.conjuntoComplejo = "-"

        // Referencia directa a cliente y fecha de recepción como Date (para reportes)
        this.cliente = this.recepcionDeComplejo?.cliente
        this.fechaRecepcion = this.recepcionDeComplejo?.fechaDeRecepcion
        
        this.porcentajeRegalia=""
        this.liquidado=1
        this.fechaUltimaModificacion=new java.util.Date()
        this.usuario = springSecurityService.getCurrentUser()

        log.error("********************* this.totalLiquidoPagable = ${this.totalLiquidoPagable}")
//        if (this.totalLiquidoPagable){
//            def conversor = new NumeroALiteral()
//            this.totalLiquidoPagableLiteral = conversor.Convertir(this.totalLiquidoPagable.toString(),true)
//        }

        def conversor = new NumeroALiteral()
        this.totalLiquidoPagableLiteral = conversor.Convertir(this.totalLiquidoPagable.toString(),true)

        this.fechaDeCancelacion = new java.util.Date(84,5,14)
        
        this.costoLaboratorio1 = (this.costoLaboratorio1)?this.costoLaboratorio1:0
        this.costoLaboratorio2 = (this.costoLaboratorio2)?this.costoLaboratorio2:0
        this.costoLaboratorio3 = (this.costoLaboratorio3)?this.costoLaboratorio3:0
        this.costoLaboratorio4 = (this.costoLaboratorio4)?this.costoLaboratorio4:0
    }

    def afterInsert = {
        // Marcar el lote como LIQUIDADO con HQL executeUpdate: actualiza solo esa columna sin
        // re-validar la recepción ni re-disparar su beforeValidate (que recalcula pesoBruto y podía
        // hacer fallar el guardado). Se refleja también en memoria por si la recepción se vuelve a
        // guardar más abajo (bloque de anticipo contra entrega).
        RecepcionDeComplejo.executeUpdate(
                "update RecepcionDeComplejo set estadoDelLote = :estado where id = :id",
                [estado: 'LIQUIDADO', id: this.recepcionDeComplejo.id])
        this.recepcionDeComplejo.estadoDelLote = "LIQUIDADO"

        // Retenciones por pagar a partir del detalle (LiquidacionDeComplejoRetenciones ya
        // persistido por cascada desde el controller). La regalía minera no es una fila de
        // retención (se calcula); aquí solo las retenciones configuradas.
        this.detalleRetenciones?.each { ret ->
            new RetencionPorPagarComplejo(
                    liquidacionId: this.id,
                    codigo: ret.codigo,
                    cantidadDescuento: ret.cantidadDescuento,
                    unidadDeDescuento: ret.unidadDeDescuento,
                    tipoDeRetencion: ret.tipoDeRetencion,
                    descripcion: ret.descripcion,
                    asignacionDelDescuento: ret.asignacionDelDescuento,
                    monto: ret.monto,
                    lote: this.recepcionDeComplejo.toString(),
                    kilosNetosSecos: this.kilosNetosSecos,
                    valorOficialNeto: this.valorNetoMineralEnBolivianos,
                    recepcionDeComplejo: this.recepcionDeComplejo,
                    tipoDeMineral: this.recepcionDeComplejo.tipoDeMineral,
                    empresa: this.empresa,
                    fechaDeRegistro: this.recepcionDeComplejo.fechaDeRecepcion,
                    pagado: "NO"
            ).save(failOnError: true)
        }

        //procesar los anticipos contra entrega
        //este bloque busca un lote recepcionado de entre los que fueron asignados al anticipo
        // Descuento del anticipo: se aplica la fracción cobrada en esta liquidación. Si es el ÚLTIMO
        // lote por liquidar del anticipo y queda saldo, ese saldo se traslada a un ACFE (deuda del cliente).
        def anticipoDetalle = AnticipoDetalle.findByRecepcionId(this.recepcionDeComplejo.id)
        if (anticipoDetalle) {
            def anticipo = anticipoDetalle.anticipo
            def recepcion = RecepcionDeComplejo.get(anticipoDetalle.recepcionId)
            def cobro = this.totalAnticiposContraEntrega ?: 0.0G

            // 1) Aplicar el cobro (fracción) al anticipo
            if (cobro > 0) {
                anticipo.totalPagado = (anticipo.totalPagado ?: 0) + cobro
                anticipo.totalPorPagar = (anticipo.totalPorPagar ?: 0) - cobro
                anticipo.save(failOnError: true)
            }

            // 2) ¿Era el ÚLTIMO lote por liquidar del anticipo? El lote actual ya quedó en LIQUIDADO
            //    (HQL al inicio del afterInsert), por eso no se cuenta entre los pendientes.
            def lotesPendientes = AnticipoDetalle.findAllByAnticipo(anticipo).count { d ->
                RecepcionDeComplejo.get(d.recepcionId)?.estadoDelLote == 'NO LIQUIDADO'
            }
            def residual = anticipo.totalPorPagar ?: 0.0G
            if (lotesPendientes == 0 && residual > 0) {
                // Trasladar el saldo no cobrado a un Anticipo contra Futura Entrega (su afterInsert lo
                // registra como deuda en el EstadoDeCuenta del cliente). Así no queda saldo "colgado".
                def conv = new NumeroALiteral()
                new AnticipoContraFuturaEntrega(
                        cliente: this.recepcionDeComplejo.cliente,
                        empresa: this.recepcionDeComplejo.empresa,
                        fechaDeAnticipo: new java.util.Date(),
                        compromiso: "SALDO NO COBRADO DEL ANTICIPO CONTRA ENTREGA (LOTE ${this.lote})",
                        importe: residual,
                        importeLiteral: conv.Convertir(residual.toString(), true),
                        observaciones: "TRASLADO AUTOMATICO: EL ANTICIPO CONTRA ENTREGA QUEDO CON SALDO AL LIQUIDARSE SU ULTIMO LOTE.",
                        liquidacionId: this.id
                ).save(failOnError: true)
                // El anticipo contra entrega queda saldado (su saldo se trasladó al ACFE)
                anticipo.totalPagado = anticipo.totalAnticipos
                anticipo.totalPorPagar = 0.0G
                anticipo.save(failOnError: true)
            }

            // 3) Estados finales (tras el posible traslado del residual)
            def saldado = (anticipo.totalPorPagar ?: 0) <= 0
            anticipoDetalle.anticipoPagable = cobro
            anticipoDetalle.estadoAnticipo = saldado ? "PAGADO" : "PARCIAL"
            anticipoDetalle.save(failOnError: true)

            recepcion.estadoAnticipo = saldado ? "PAGADO" : "CON ANTICIPO"
            recepcion.save(failOnError: true)
        }


        //VERIFICAR SI EL LIQUIDO PAGABLE ES MENOR A CERO. SI ES ASI GENERAR UN ANTICIPO CONTRA FUTURA ENTREGA
        //(la numeración y la gestión del ACFE las asigna su beforeValidate; al guardarse, su
        // afterInsert registra la deuda en el EstadoDeCuenta del cliente)
        if (this.totalLiquidoPagable < 0){
            def conversor = new NumeroALiteral()
            def importeAnticipo = -1*totalLiquidoPagable
            def anticipoContraFuturaEntrega = new AnticipoContraFuturaEntrega(
                    cliente: recepcionDeComplejo.cliente,
                    empresa: recepcionDeComplejo.empresa,
                    fechaDeAnticipo: new java.util.Date(),
                    compromiso: "REALIZAR EL PAGO EN LA SIGUIENTE ENTREGA (SEGUN LOTE ${this.lote})",
                    importe: importeAnticipo,
                    importeLiteral: conversor.Convertir(importeAnticipo.toString(),true),
                    observaciones: "SE HA GENERADO EL REGISTRO PORQUE LA LIQUIDACION A PRODUCIDO SALDO NEGATIVO.",
                    liquidacionId: this.id
            )
            anticipoContraFuturaEntrega.save(failOnError: true)
        }

        //REGISTRO DE TRANSACCIONES DEL ESTADO DE CUENTA PARA EL PROCESAMIENTO DE ANTICIPOS CONTRA FUTURA ENTREGA
        //actualizar el estado de cuenta para el caso de anticipos contra futura entrega
        if (this.totalAnticiposContraFuturaEntrega!=0){
            def ultimoEstadoDeCuenta = EstadoDeCuenta.findAllByCliente(this.recepcionDeComplejo.cliente, [sort: "id", order: "desc"])
            def ultimoSaldo = (ultimoEstadoDeCuenta.size()>0)?ultimoEstadoDeCuenta.get(0).saldo:0
            def saldo = ultimoSaldo-this.totalAnticiposContraFuturaEntrega
            def estadoDeCuenta = new EstadoDeCuenta(
                    cliente: this.recepcionDeComplejo.cliente,
                    empresa: this.recepcionDeComplejo.empresa,
                    ci: this.recepcionDeComplejo.cliente.ci,
                    nombre: this.recepcionDeComplejo.cliente.nombre,
                    nombreEmpresa: this.recepcionDeComplejo.empresa.nombreDeEmpresa,
                    fecha: this.fechaDeLiquidacion,
                    numeroComprobante: this.id,
                    detalle: "PAGO POR ENTREGA POR ANTICIPO CONTRA ENTREGA EN LOTE ${this.lote}",
                    debe: 0.0,
                    haber: this.totalAnticiposContraFuturaEntrega,
                    saldo: saldo,
                    liquidacionId: this.id,
                    tipoMovimiento: TipoMovimiento.LIQUIDACION_COMPLEJO,
                    origenId: this.id
            )
            estadoDeCuenta.save(failOnError: true)
        }
    }

    def beforeUpdate = {
        // Este módulo NO soporta reliquidación: solo alta (save) y anulación (acción 'anular' del controller).
        // Por eso este hook se deja sin efectos. Antes generaba asientos de reversión y, como Hibernate
        // emite un UPDATE espurio tras el insert, terminaba duplicando el estado de cuenta.
    }

    def afterUpdate = {
        // Sin reliquidación: el alta (afterInsert) ya creó retenciones y asientos del estado de cuenta,
        // y la anulación se procesa en el controller (acción 'anular'). Este hook se deja vacío a propósito
        // para no duplicar asientos ni rehacer retenciones cuando Hibernate emite un UPDATE (el espurio
        // posterior al insert, o el de la anulación).
    }

    def beforeDelete = {
        /* CAMBIAR EL ESTADO DEL LOTE */
        withNewTransaction {
            def recepcion = RecepcionDeComplejo.get(this.recepcionDeComplejo.id)
            recepcion.estadoDelLote = "NO LIQUIDADO"
            recepcion.save(failOnError: true, flush: true)
        }
        /* ELIMINAR LAS RETENCIONES*/
        withNewTransaction {
            def listaRetenciones = LiquidacionDeComplejoRetenciones.findAllByLiquidacionDeComplejo(this)
            listaRetenciones.each {
                it.delete(flush: true)
            }
        }
        /* ELIMINAR RETENCIONES POR PAGAR*/
        withNewTransaction {
            def retencionesPorPagarAnteriores = RetencionPorPagarComplejo.findAllByLiquidacionId(this.id)
            retencionesPorPagarAnteriores.each {
                it.delete(flush: true)
            }
        }
        /* ELIMINAR LOS ANTICIPOS CONTRA ENTREGA REALIZADOS*/
        withNewTransaction {
            def recepcion = RecepcionDeComplejo.get(this.recepcionDeComplejo.id)
            def anticipoDetalle = AnticipoDetalle.findByRecepcionId(this.recepcionDeComplejo.id)
            if(anticipoDetalle){
                def anticipo = anticipoDetalle.anticipo
                def nuevoAnticipoPorPagar = anticipo.totalPorPagar + this.totalAnticiposContraEntrega
                anticipo.totalPagado = anticipo.totalPagado - this.totalAnticiposContraEntrega
                anticipo.totalPorPagar = nuevoAnticipoPorPagar
                anticipo.save(failOnError: true)

                anticipoDetalle.anticipoPagable=this.totalAnticiposContraEntrega
                anticipoDetalle.estadoAnticipo="SIN PAGAR"
                anticipoDetalle.save(failOnError: true)

                recepcion.estadoAnticipo = "CON ANTICIPO"
                recepcion.save(failOnError: true)
            }
        }
        // RETORNANDO EL ANTICIPO CONTRA FUTURA ENTREGA, SI ES QUE HUBIERA
        withNewTransaction {
            if (this.totalAnticiposContraFuturaEntrega > 0){
                def ultimoEstadoDeCuenta = EstadoDeCuenta.findAllByCliente(this.recepcionDeComplejo.cliente, [sort: "id", order: "desc"])
                def ultimoSaldo = (ultimoEstadoDeCuenta.size()>0)?ultimoEstadoDeCuenta.get(0).saldo:0
                def saldo = this.totalAnticiposContraFuturaEntrega + ultimoSaldo
                def estadoDeCuenta = new EstadoDeCuenta(
                        cliente: this.recepcionDeComplejo.cliente,
                        empresa: this.empresa,
                        ci: this.recepcionDeComplejo.cliente.ci,
                        nombre: this.recepcionDeComplejo.cliente.nombre,
                        nombreEmpresa: this.empresa.nombreDeEmpresa,
                        fecha: this.fechaDeLiquidacion,
                        numeroComprobante: 0,
                        detalle: "REVERSION DE ANTICIPO CONTRA FUTURA ENTREGA POR ELIMINACION DE LIQUIDACION DEL LOTE "+this.lote,
                        debe: this.totalAnticiposContraFuturaEntrega,
                        haber: 0.0,
                        saldo: saldo,
                        liquidacionId: 0,
                        tipoMovimiento: TipoMovimiento.LIQUIDACION_COMPLEJO,
                        origenId: this.id
                )
                estadoDeCuenta.save(failOnError: true)
            }
        }
    }

    String toString(){
        "${recepcionDeComplejo.toString()}"
    }
}
