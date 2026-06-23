package org.socymet.liquidacion

import org.grails.web.json.JSONArray
import org.socymet.anticipos.AnticipoContraEntrega
import org.socymet.anticipos.AnticipoContraFuturaEntrega
import org.socymet.anticipos.AnticipoDetalle
import org.socymet.anticipos.EstadoDeCuenta
import org.socymet.cotizaciones.TablaPreciosOro
import org.socymet.org.socymet.reportes.Reimpresion
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.recepcion.RecepcionDeOro
import org.socymet.utilidades.NumeroALiteral

import java.text.DecimalFormat

class LiquidacionDeOro extends Liquidacion {
    Integer numeroLiquidacionOro //e.g.: GENERADO: 1, PARA MOSTRAR: 0001
    String conjuntoOro="-"
    //informacion de recepcion (duplicando para facilitar generacion de reportes)
    RecepcionDeOro recepcionDeOro

    String lote
    String nombreCliente
    String nombreEmpresa
    String fechaDeRecepcion
    String cantidadDeSacos
    String estadoDelLote

    BigDecimal pesoBruto
    BigDecimal cotizacionDiariaDeOro
    BigDecimal cotizacionQuincenalDeOro
    BigDecimal alicuotaDeOro
    BigDecimal tipoDeCambioOficial
    BigDecimal tipoDeCambioComercial
    //informacion de analisis de laboratorio y puntos a pagar
    BigDecimal porcentajeOroPromexbol
    BigDecimal porcentajeHumedadPromexbol
    BigDecimal porcentajeMermaPromexbol

    BigDecimal porcentajeOroCliente
    BigDecimal porcentajeHumedadCliente
    BigDecimal porcentajeMermaCliente

    BigDecimal porcentajeOroFinal
    BigDecimal porcentajeHumedadFinal
    BigDecimal porcentajeMermaFinal

    TablaPreciosOro tablaPreciosOro
    BigDecimal porcentajeBonificacion
    //pesos
    BigDecimal kilosFinosOro
    BigDecimal onzasTroyDeOro
    //valores brutos
    BigDecimal valorOficialBrutoDeOro
    BigDecimal valorOficialBrutoDeOroEnBolivianos
    //retenciones
    String retenciones

    //adelanto por liquidacion provisional del lote en cuestion
    BigDecimal adelantoPorLiquidacionProvisional

    BigDecimal bonoTransporteKilosNetosSecosTotal=0
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
        numeroLiquidacionOro(nullable: true)
        conjuntoOro(blank: true)

        recepcionDeOro(unique: true)
        deposito nullable: false

        empresa nullable: false
        lote(blank: false)
        nombreDeposito nullable: false
        nombreCliente(blank: false)
        nombreEmpresa(blank: false)
        fechaDeRecepcion(blank: false)
        cantidadDeSacos(blank: false)
        estadoDelLote(validator: {
            return !it.equals("NO LIQUIDADO")||!it.equals("Provisional")
        })
        pesoBruto()
        cotizacionDiariaDeOro()
        cotizacionQuincenalDeOro()
        alicuotaDeOro()
        tipoDeCambioOficial()
        tipoDeCambioComercial()

        fechaDeLiquidacion(blank: false, nullable: true)
        //informacion de analisis de laboratorio
        kilosNetosHumedos()
        kilosNetosSecos()

        porcentajeOroPromexbol nullable: false, min: 0.0, max: 100.0
        porcentajeHumedadPromexbol nullable: false, min: 0.0, max: 100.0
        porcentajeMermaPromexbol nullable: false, min: 0.0, max: 100.0

        porcentajeOroCliente nullable: false, min: 0.0, max: 100.0
        porcentajeHumedadCliente nullable: false, min: 0.0, max: 100.0
        porcentajeMermaCliente nullable: false, min: 0.0, max: 100.0

        porcentajeOroFinal nullable: false, min: 0.0, max: 100.0
        porcentajeHumedadFinal nullable: false, min: 0.0, max: 100.0
        porcentajeMermaFinal nullable: false, min: 0.0, max: 100.0

        tablaPreciosOro()

        kilosFinosOro()
        onzasTroyDeOro()
        valorOficialBrutoDeOro()
        valorOficialBrutoDeOroEnBolivianos()
        valorOficialBruto()

        valorPorTonelada()
        margen nullable: false, max: 100.0
        porcentajeRegalia(blank:true)
        regaliaMinera()

        retenciones(blank: false)

        valorNetoMineral()
        porcentajeBonificacion()
        bonoCalidad()
        bonoIncentivo()
        valorDeCompra()
        totalRetenciones()
        totalPagado()
        anticipoPorPagar nullable: false
        totalAnticiposContraEntrega()
        totalAnticiposContraFuturaEntrega()
        adelantoPorLiquidacionProvisional()
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
//        conjuntoOro = "-"
//        porcentajeRegalia = "0"
//        observaciones = "-"
//    }

    def beforeInsert = {
        def c = LiquidacionDeOro.createCriteria()
        def results = c {
            projections {
                max('numeroLiquidacionOro')
            }}
        def maxNumeroLiquidacion = results.get(0)?: 0
        this.numeroLiquidacionOro = maxNumeroLiquidacion + 1
        this.conjuntoOro = "-"

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
        this.recepcionDeOro.estadoDelLote = "LIQUIDADO"
//        if(this.recepcionDeOro.tipoDeMaterial.equals("CONCENTRADO"))
//            this.recepcionDeOro.costoDeTransporte = cantidadDeSacos*this.empresa.costoTransporteOros
//        else
//            this.recepcionDeOro.costoDeTransporte = cantidadDeSacos*this.empresa.costoTransporteConcentrados
        this.recepcionDeOro.save()

        LiquidacionDeOro.withNewTransaction {
            //actualizando el estado del lote
            def liquidacionDeOro = LiquidacionDeOro.get(this.id)
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
                    def liquidacionDeOroRetenciones = new LiquidacionDeOroRetenciones(
                            liquidacionDeOro: liquidacionDeOro,
                            codigo: codigo,
                            cantidadDescuento: cantidad,
                            unidadDeDescuento: unidad,
                            tipoDeRetencion: tipo,
                            descripcion: descripcion,
                            asignacionDelDescuento: asignacion,
                            monto: monto)
                    liquidacionDeOroRetenciones.save(failOnError: true)

                    def retencionPorPagarOro = new RetencionPorPagarComplejo(
                            liquidacionId: liquidacionDeOro.id,
                            codigo: codigo,
                            cantidadDescuento: cantidad,
                            unidadDeDescuento: unidad,
                            tipoDeRetencion: tipo,
                            descripcion: descripcion,
                            asignacionDelDescuento: asignacion,
                            monto: monto,
                            lote: liquidacionDeOro.recepcionDeOro.toString(),
                            kilosNetosSecos: liquidacionDeOro.kilosNetosSecos,
                            valorOficialNeto: liquidacionDeOro.valorNetoMineralEnBolivianos,
                            recepcionDeOro: liquidacionDeOro.recepcionDeOro,
                            tipoDeMineral: "ORO",
                            empresa: liquidacionDeOro.empresa,
//                            fechaDeRegistro: liquidacionDeOro.fechaDeLiquidacion,
                            fechaDeRegistro: liquidacionDeOro.recepcionDeOro.fechaDeRecepcion,
                            pagado: "NO"
                    )
                    retencionPorPagarOro.save(failOnError: true)
                }
            }
        }

        //procesar los anticipos contra entrega
        //este bloque busca un lote recepcionado de entre los que fueron asignados al anticipo
        def anticipoDetalle = AnticipoDetalle.findByRecepcionId(this.recepcionDeOro.id)
        if(anticipoDetalle){
            def anticipo = anticipoDetalle.anticipo
            def recepcion = RecepcionDeOro.get(anticipoDetalle.recepcionId)
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
                    cliente: recepcionDeOro.cliente,
                    empresa: recepcionDeOro.empresa,
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
            def ultimoEstadoDeCuenta = EstadoDeCuenta.findAllByCliente(this.recepcionDeOro.cliente, [sort: "id", order: "desc"])
            def ultimoSaldo = (ultimoEstadoDeCuenta.size()>0)?ultimoEstadoDeCuenta.get(0).saldo:0
            def saldo = ultimoSaldo-this.totalAnticiposContraFuturaEntrega
            def estadoDeCuenta = new EstadoDeCuenta(cliente: this.recepcionDeOro.cliente,
                    empresa: this.recepcionDeOro.empresa,
                    ci: this.recepcionDeOro.cliente.ci,
                    nombre: this.recepcionDeOro.cliente.nombre,
                    nombreEmpresa: this.recepcionDeOro.empresa.nombreDeEmpresa,
                    fecha: this.fechaDeLiquidacion,
                    numeroComprobante: this.id,
                    detalle: "PAGO POR ENTREGA POR ANTICIPO CONTRA ENTREGA EN LOTE ${this.lote}",
                    debe: 0.0,
                    haber: this.totalAnticiposContraFuturaEntrega,
                    saldo: saldo)
            estadoDeCuenta.save(failOnError: true)
        }
    }

    def beforeUpdate = {
        //this.fechaDeLiquidacion = new java.util.Date()
        def conversor = new NumeroALiteral()
        this.totalLiquidoPagableLiteral = conversor.Convertir(this.totalLiquidoPagable.toString(),true)
    }

    def afterUpdate = {
        //registrar las retenciones realizadas a esta liquidacion
        //OJO: por alguna razon la funcion delete() no funciona dentro un withNewTransaction
        def retencionesAnteriores = LiquidacionDeOroRetenciones.findAllByLiquidacionDeOro(this)
        retencionesAnteriores.each {
            log.error("**** RETENCION LIQUIDACION: ELIMINANDO: ${it.descripcion} MONTO: ${it.monto}")
            it.delete()
        }

        def retencionesPorPagarAnteriores = RetencionPorPagarComplejo.findAllByLiquidacionId(this.id)
        retencionesPorPagarAnteriores.each {
            log.error("**** RETENCION POR PAGAR: ELIMINANDO: ${it.descripcion} MONTO: ${it.monto}")
            it.delete()
        }

        LiquidacionDeOro.withNewTransaction {
            //actualizando el estado del lote
            this.recepcionDeOro.estadoDelLote = "LIQUIDADO"
            this.recepcionDeOro.save()

            def liquidacionDeOro = LiquidacionDeOro.get(this.id)
            //eliminar las retenciones para despues volverlas a registrar


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
                    def liquidacionDeOroRetenciones = new LiquidacionDeOroRetenciones(
                            liquidacionDeOro: liquidacionDeOro,
                            codigo: codigo,
                            cantidadDescuento: cantidad,
                            unidadDeDescuento: unidad,
                            tipoDeRetencion: tipo,
                            descripcion: descripcion,
                            asignacionDelDescuento: asignacion,
                            monto: monto)
                    liquidacionDeOroRetenciones.save(failOnError: true)

                    def retencionPorPagarOro = new RetencionPorPagarComplejo(
                            liquidacionId: liquidacionDeOro.id,
                            codigo: codigo,
                            cantidadDescuento: cantidad,
                            unidadDeDescuento: unidad,
                            tipoDeRetencion: tipo,
                            descripcion: descripcion,
                            asignacionDelDescuento: asignacion,
                            monto: monto,
                            lote: liquidacionDeOro.recepcionDeOro.toString(),
                            kilosNetosSecos: liquidacionDeOro.kilosNetosSecos,
                            valorOficialNeto: liquidacionDeOro.valorNetoMineralEnBolivianos,
                            recepcionDeOro: liquidacionDeOro.recepcionDeOro,
                            tipoDeMineral: "ORO",
                            empresa: liquidacionDeOro.empresa,
//                            fechaDeRegistro: liquidacionDeOro.fechaDeLiquidacion,
                            fechaDeRegistro: liquidacionDeOro.recepcionDeOro.fechaDeRecepcion,
                            pagado: "NO"
                    )
                    retencionPorPagarOro.save(failOnError: true)
                }
            }
        }
        /*** BLOQUEANDO ESTE CODIGO PORQUE CUANDO SE REALIZA LA CANCELACION DEL LOTE LIQUIDADO SE INVOCA
         * AL CLOSURE afterUpdate GENERANDO INCONSISTENCIA EN LA ENTIDAD estadoDeCuenta***/
        /*
        //eliminar el ultimo registro insertado en el EstadoDeCuenta asumiendo hubo una modificacion en el campo totalAnticiposContraFuturaEntrega
        //primero se obtiene un listado ordenado descendentemente por el id, asi se tiene en la primera posicion al ultimo registro
        def ultimoEstadoDeCuentaAEliminar = EstadoDeCuenta.findAllByCliente(this.recepcionDeOro.cliente, [sort: "id", order: "desc"])
        //esta verificacion es solo para ver si existe por lo menos un registro
        if (ultimoEstadoDeCuentaAEliminar.size()>0)
        //eliminar el ultimo registro realizado
            ultimoEstadoDeCuentaAEliminar.get(0).delete()
        //actualizar el estado de cuenta para el caso de anticipos contra futura entrega
        def ultimoEstadoDeCuenta = EstadoDeCuenta.findAllByCliente(this.recepcionDeOro.cliente, [sort: "id", order: "desc"])
        def ultimoSaldo = (ultimoEstadoDeCuenta.size()>0)?ultimoEstadoDeCuenta.get(0).saldo:0
        def saldo = ultimoSaldo-this.totalAnticiposContraFuturaEntrega
        def estadoDeCuenta = new EstadoDeCuenta(cliente: this.recepcionDeOro.cliente,
                empresa: this.recepcionDeOro.empresa,
                ci: this.recepcionDeOro.cliente.ci,
                nombre: this.recepcionDeOro.cliente.nombre,
                nombreEmpresa: this.recepcionDeOro.empresa.nombreDeEmpresa,
                fecha: this.fechaDeLiquidacion,
                numeroComprobante: this.id,
                detalle: "PAGO POR ENTREGA POR ANTICIPO CONTRA ENTREGA",
                debe: 0.0,
                haber: this.totalAnticiposContraFuturaEntrega,
                saldo: saldo)
        estadoDeCuenta.save(failOnError: true)
        */

        //procesar los anticipos contra entrega
        //este bloque busca un lote recepcionado de entre los que fueron asignados al anticipo
        //el AnticipoDetalle y la Recepcion asociada ya estan pagados.
        //debe actualizarse los montos por pagar, etc. segun la diferencia entre el liquido pagable
        //anterior y el actual
        def anticipoDetalle = AnticipoDetalle.findByRecepcionId(this.recepcionDeOro.id)
        if(anticipoDetalle){
            def anticipo = anticipoDetalle.anticipo
            def recepcion = RecepcionDeOro.get(anticipoDetalle.recepcionId)
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

        //REGISTRO DEL MOTIVO DE RELIQUIDACION
        def reimpresion = new Reimpresion(
                fecha: new java.util.Date(),
                nombreReporte: "RELIQUIDACION DE COMPLEJO",
                identificadorDocumento: numeroLiquidacionOro,
                lote: lote,
                motivo: motivoDeModificacion
        )
        reimpresion.save(failOnError: true)
    }

    def beforeDelete = {
        LiquidacionDeOro.withNewTransaction {
            def recepcion = RecepcionDeOro.get(this.recepcionDeOro.id)
            recepcion.estadoDelLote = "NO LIQUIDADO"
            recepcion.save(failOnError: true, flush: true)
        }

        LiquidacionDeOro.withNewTransaction {
            def listaRetenciones = LiquidacionDeOroRetenciones.findAllByLiquidacionDeOro(this)
            listaRetenciones.each {
                it.delete(flush: true)
            }
        }

        LiquidacionDeOro.withNewTransaction {
            def retencionesPorPagarAnteriores = RetencionPorPagarComplejo.findAllByLiquidacionId(this.id)
            if (retencionesPorPagarAnteriores || retencionesPorPagarAnteriores.size()>0)
                retencionesPorPagarAnteriores.each {
                    it.delete()
                }
        }

        LiquidacionDeOro.withNewTransaction {
            def recepcion = RecepcionDeOro.get(this.recepcionDeOro.id)
            def anticipoDetalle = AnticipoDetalle.findByRecepcionId(recepcion.id)
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
                recepcion.save(failOnError: true, flush: true)
            }
        }
    }

    String toString(){
        "${recepcionDeOro.toString()}"
    }
}
