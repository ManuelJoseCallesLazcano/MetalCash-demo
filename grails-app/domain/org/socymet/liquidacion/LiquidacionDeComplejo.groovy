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

        recepcionDeComplejo(unique: true)
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
        //registrar las retenciones realizadas a esta liquidacion
        def formatter=new DecimalFormat("###")
//        def cantidadDeSacos = formatter.format(kilosNetosSecos/50.0).toInteger()
        this.recepcionDeComplejo.estadoDelLote = "LIQUIDADO"
        this.recepcionDeComplejo.pesoNeto = kilosNetosSecos
//        this.recepcionDeComplejo.cantidadDeSacos = cantidadDeSacos
//        if(this.recepcionDeComplejo.tipoDeMaterial.equals("CONCENTRADO"))
//            this.recepcionDeComplejo.costoDeTransporte = cantidadDeSacos*this.empresa.costoTransporteComplejos
//        else
//            this.recepcionDeComplejo.costoDeTransporte = cantidadDeSacos*this.empresa.costoTransporteConcentrados
        this.recepcionDeComplejo.save()

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
        // Descuento del anticipo: si el anticipo cubre un solo lote se salda completo (prefill);
        // si cubre varios lotes, se descuenta la fracción ingresada del total por pagar.
        def anticipoDetalle = AnticipoDetalle.findByRecepcionId(this.recepcionDeComplejo.id)
        if(anticipoDetalle && (this.totalAnticiposContraEntrega ?: 0) > 0){
            def anticipo = anticipoDetalle.anticipo
            def recepcion = RecepcionDeComplejo.get(anticipoDetalle.recepcionId)
            anticipo.totalPagado = (anticipo.totalPagado ?: 0) + this.totalAnticiposContraEntrega
            anticipo.totalPorPagar = (anticipo.totalPorPagar ?: 0) - this.totalAnticiposContraEntrega
            anticipo.save(failOnError: true)

            def saldado = (anticipo.totalPorPagar ?: 0) <= 0
            anticipoDetalle.anticipoPagable = this.totalAnticiposContraEntrega
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
        // La anulación (anulado=true) se procesa en el controller; no disparar la lógica de reliquidación.
        if (this.anulado) return
        //this.fechaDeLiquidacion = new java.util.Date()
        def conversor = new NumeroALiteral()
        this.totalLiquidoPagableLiteral = conversor.Convertir(this.totalLiquidoPagable.toString(),true)
        // RETORNANDO EL ANTICIPO CONTRA FUTURA ENTREGA, SI ES QUE HUBIERA
        withNewTransaction {
            if (this.totalAnticiposContraFuturaEntrega > 0){
                def ultimoAnticipo = EstadoDeCuenta.findByLiquidacionId(this.id)
                def ultimoEstadoDeCuenta = EstadoDeCuenta.findAllByCliente(this.recepcionDeComplejo.cliente, [sort: "id", order: "desc"])
                def ultimoSaldo = (ultimoEstadoDeCuenta.size()>0)?ultimoEstadoDeCuenta.get(0).saldo:0
//                def saldo = this.totalAnticiposContraFuturaEntrega + ultimoSaldo
                def saldo = ultimoAnticipo.haber + ultimoSaldo
                def estadoDeCuenta = new EstadoDeCuenta(
                        cliente: this.recepcionDeComplejo.cliente,
                        empresa: this.empresa,
                        ci: this.recepcionDeComplejo.cliente.ci,
                        nombre: this.recepcionDeComplejo.cliente.nombre,
                        nombreEmpresa: this.empresa.nombreDeEmpresa,
                        fecha: this.fechaDeLiquidacion,
                        numeroComprobante: 0,
                        detalle: "REVERSION DE ANTICIPO CONTRA FUTURA ENTREGA POR RELIQUIDACION DEL LOTE "+this.lote,
//                        debe: this.totalAnticiposContraFuturaEntrega,
                        debe: ultimoAnticipo.haber,
                        haber: 0.0,
                        saldo: saldo,
                        liquidacionId: this.id,
                        tipoMovimiento: TipoMovimiento.LIQUIDACION_COMPLEJO,
                        origenId: this.id
                )
                estadoDeCuenta.save(failOnError: true)
            }
        }
    }

    def afterUpdate = {
        //registrar las retenciones realizadas a esta liquidacion
        withNewTransaction {
            def retencionesAnteriores = LiquidacionDeComplejoRetenciones.findAllByLiquidacionDeComplejo(this)
            retencionesAnteriores.each {
                log.error("**** RETENCION LIQUIDACION: ELIMINANDO: ${it.descripcion} MONTO: ${it.monto}")
                it.delete(flush: true)
            }
        }

        withNewTransaction {
            def retencionesPorPagarAnteriores = RetencionPorPagarComplejo.findAllByLiquidacionId(this.id)
            retencionesPorPagarAnteriores.each {
                log.error("**** RETENCION POR PAGAR: ELIMINANDO: ${it.descripcion} MONTO: ${it.monto}")
                it.delete(flush: true)
            }
        }

        LiquidacionDeComplejo.withNewTransaction {
            def liquidacionDeComplejo = LiquidacionDeComplejo.get(this.id)

            def retencionesJSON = new JSONArray(retenciones)
            retencionesJSON.each {
                def codigo = it.getAt("CODIGO")
                def cantidad = it.getAt("CANTIDAD")
                def tipo = it.getAt("TIPO")
                def unidad = it.getAt("UNIDAD")
                def descripcion = it.getAt("DESCRIPCION")
                def monto = it.getAt("MONTO")
                def asignacion = it.getAt("ASIGNACION")
                log.error("**** RETENCION: ${codigo} - ${cantidad} - ${tipo} - ${descripcion} - ${asignacion} - ${monto}")
                if(!codigo.equals("-")){
                    def liquidacionDeComplejoRetenciones = new LiquidacionDeComplejoRetenciones(
                            liquidacionDeComplejo: liquidacionDeComplejo,
                            codigo: codigo,
                            cantidadDescuento: cantidad,
                            unidadDeDescuento: unidad,
                            tipoDeRetencion: tipo,
                            descripcion: descripcion,
                            asignacionDelDescuento: asignacion,
                            monto: monto)
                    liquidacionDeComplejoRetenciones.save(failOnError: true)

                    def retencionPorPagarComplejo = new RetencionPorPagarComplejo(
                            liquidacionId: liquidacionDeComplejo.id,
                            codigo: codigo,
                            cantidadDescuento: cantidad,
                            unidadDeDescuento: unidad,
                            tipoDeRetencion: tipo,
                            descripcion: descripcion,
                            asignacionDelDescuento: asignacion,
                            monto: monto,
                            lote: liquidacionDeComplejo.recepcionDeComplejo.toString(),
                            kilosNetosSecos: liquidacionDeComplejo.kilosNetosSecos,
                            valorOficialNeto: liquidacionDeComplejo.valorNetoMineralEnBolivianos,
                            recepcionDeComplejo: liquidacionDeComplejo.recepcionDeComplejo,
                            tipoDeMineral: liquidacionDeComplejo.recepcionDeComplejo.tipoDeMineral,
                            empresa: liquidacionDeComplejo.empresa,
//                            fechaDeRegistro: liquidacionDeComplejo.fechaDeLiquidacion,
                            fechaDeRegistro: liquidacionDeComplejo.recepcionDeComplejo.fechaDeRecepcion,
                            pagado: "NO"
                    )
                    retencionPorPagarComplejo.save(failOnError: true)
                }
            }
        }
        /*** BLOQUEANDO ESTE CODIGO PORQUE CUANDO SE REALIZA LA CANCELACION DEL LOTE LIQUIDADO SE INVOCA
         * AL CLOSURE afterUpdate GENERANDO INCONSISTENCIA EN LA ENTIDAD estadoDeCuenta***/
        //procesar los anticipos contra entrega
        //este bloque busca un lote recepcionado de entre los que fueron asignados al anticipo
        //el AnticipoDetalle y la Recepcion asociada ya estan pagados.
        //debe actualizarse los montos por pagar, etc. segun la diferencia entre el liquido pagable
        //anterior y el actual
        def anticipoDetalle = AnticipoDetalle.findByRecepcionId(this.recepcionDeComplejo.id)
        if(anticipoDetalle){
            def anticipo = anticipoDetalle.anticipo
            def recepcion = RecepcionDeComplejo.get(anticipoDetalle.recepcionId)
            def nuevoAnticipoPorPagar = anticipo.totalPorPagar + anticipoDetalle.anticipoPagable - this.totalAnticiposContraEntrega
            anticipo.totalPagado = anticipo.totalPagado - anticipoDetalle.anticipoPagable + this.totalAnticiposContraEntrega
            anticipo.totalPorPagar = nuevoAnticipoPorPagar
            anticipo.save(failOnError: true)

            anticipoDetalle.anticipoPagable=this.totalAnticiposContraEntrega
            anticipoDetalle.estadoAnticipo="PAGADO"
            anticipoDetalle.save(failOnError: true)

            recepcion.estadoAnticipo = "PAGADO"
            recepcion.save(failOnError: true)
        }

        withNewTransaction {
            if (this.totalAnticiposContraFuturaEntrega > 0){
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
                        detalle: "PAGO DE ANTICIPO POR RELIQUIDACION DEL LOTE ${this.lote}",
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
