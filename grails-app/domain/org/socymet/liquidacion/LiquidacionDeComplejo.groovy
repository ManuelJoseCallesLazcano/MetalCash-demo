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
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.utilidades.NumeroALiteral

import java.text.DecimalFormat

class LiquidacionDeComplejo extends Liquidacion{
    static auditable = true

    static searchable = true

    Integer numeroLiquidacionComplejo //e.g.: GENERADO: 1, PARA MOSTRAR: 0001
    String conjuntoComplejo="-"
    //informacion de recepcion (duplicando para facilitar generacion de reportes)
    RecepcionDeComplejo recepcionDeComplejo

    String lote
    String tipoDeMineral
    String nombreCliente
    String nombreEmpresa
    String fechaDeRecepcion
    String cantidadDeSacos
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

    transient springSecurityService

    static constraints = {
        numeroLiquidacionComplejo(nullable: true)
        conjuntoComplejo(blank: true)

        recepcionDeComplejo(unique: true)
        deposito nullable: false

        empresa nullable: false
        lote(blank: false)
        tipoDeMineral(blank: false)
        nombreDeposito nullable: false
        nombreCliente(blank: false)
        nombreEmpresa(blank: false)
        fechaDeRecepcion(blank: false)
        cantidadDeSacos(blank: false)
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
        modoValoracion inList: ["TABLA","TERMINOS DE CONTRATO"]
        tablaComplejo nullable: false
        tablaPrecioPorLme nullable: false
        terminosDeContrato nullable: false

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
    }

    static mapping = {
        retenciones type: 'text'
    }

//    def beforeValidate = {
//        conjuntoComplejo = "-"
//        porcentajeRegalia = "0"
//        observaciones = "-"
//    }

    def beforeInsert = {
        def c = LiquidacionDeComplejo.createCriteria()
        def results = c {
            projections {
                max('numeroLiquidacionComplejo')
            }}
        def maxNumeroLiquidacion = results.get(0)?: 0
        this.numeroLiquidacionComplejo = maxNumeroLiquidacion + 1
        this.conjuntoComplejo = "-"
        
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

        LiquidacionDeComplejo.withNewTransaction {
            //actualizando el estado del lote
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

        //procesar los anticipos contra entrega
        //este bloque busca un lote recepcionado de entre los que fueron asignados al anticipo
        def anticipoDetalle = AnticipoDetalle.findByRecepcionId(this.recepcionDeComplejo.id)
        if(anticipoDetalle){
            def anticipo = anticipoDetalle.anticipo
            def recepcion = RecepcionDeComplejo.get(anticipoDetalle.recepcionId)
            def nuevoAnticipoPorPagar = anticipo.totalPorPagar - this.totalAnticiposContraEntrega
            anticipo.totalPagado = anticipo.totalPagado + this.totalAnticiposContraEntrega
            anticipo.totalPorPagar = nuevoAnticipoPorPagar
            anticipo.save(failOnError: true)

            anticipoDetalle.anticipoPagable=this.totalAnticiposContraEntrega
            anticipoDetalle.estadoAnticipo="PAGADO"
            anticipoDetalle.save(failOnError: true)

            recepcion.estadoAnticipo = "PAGADO"
            recepcion.save(failOnError: true)
        }


        //VERIFICAR SI EL LIQUIDO PAGABLE ES MENOR A CERO. SI ES ASI GENERAR UN ANTICIPO CONTRA FUTURA ENTREGA
        if (this.totalLiquidoPagable<0){
            def c = AnticipoContraEntrega.createCriteria()
            def results = c {
                projections {
                    max('numeroAnticipo')
                }}
            def maxNumeroAnticipo = results.get(0)?: 0
            def numeroAnticipo = maxNumeroAnticipo + 1
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
