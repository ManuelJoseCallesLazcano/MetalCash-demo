package org.socymet.liquidacion

import org.grails.web.json.JSONArray
import org.socymet.anticipos.AnticipoContraEntrega
import org.socymet.anticipos.AnticipoContraFuturaEntrega
import org.socymet.anticipos.AnticipoDetalle
import org.socymet.anticipos.EstadoDeCuenta
import org.socymet.cotizaciones.AjustePrecioEstano
import org.socymet.cotizaciones.TablaCotizacionEstano
import org.socymet.org.socymet.reportes.Reimpresion
import org.socymet.recepcion.RecepcionDeEstano
import org.socymet.utilidades.NumeroALiteral

import java.text.DecimalFormat

class LiquidacionDeEstano extends Liquidacion {
    static searchable = true

    Integer numeroLiquidacionEstano //e.g.: GENERADO: 1, PARA MOSTRAR: 0001
    String conjuntoEstano = "-"

    RecepcionDeEstano recepcionDeEstano

    String lote
    String nombreCliente
    String nombreEmpresa
    String fechaDeRecepcion
    BigDecimal cantidadDeSacos
    BigDecimal tara
    String estadoDelLote

    BigDecimal pesoBruto
    BigDecimal cotizacionDiariaDeEstano
    BigDecimal cotizacionQuincenalDeEstano
    BigDecimal alicuotaDeEstano
    BigDecimal tipoDeCambioOficial
    BigDecimal tipoDeCambioComercial
    //informacion de analisis de laboratorio y puntos a pagar
    BigDecimal porcentajeEstanoPromexbol
    BigDecimal porcentajeHumedadPromexbol
    BigDecimal porcentajeMermaPromexbol = 1

    BigDecimal porcentajeEstanoCliente
    BigDecimal porcentajeHumedadCliente
    BigDecimal porcentajeMermaCliente = 1

    BigDecimal porcentajeEstanoFinal
    BigDecimal porcentajeHumedadFinal
    BigDecimal porcentajeMermaFinal = 1
    //pesos
    BigDecimal kilosFinosEstano
    BigDecimal librasFinasDeEstano

    //variables de proteccion
    BigDecimal valorPorToneladaManual
    BigDecimal puntoDeBajada

    //referencia a tabla de costos
    AjustePrecioEstano ajustePrecioEstano

    //retenciones
    String retenciones

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
        numeroLiquidacionEstano(nullable: true)
        conjuntoEstano(blank: true)

        recepcionDeEstano(unique: true)
        deposito nullable: false
        lote(blank: false)
        nombreCliente(blank: false)
        nombreEmpresa(blank: false)
        fechaDeRecepcion(blank: false)
        cantidadDeSacos(blank: false)
        tara nullable: true
        estadoDelLote(validator: {
            return !it.equals("NO LIQUIDADO")||!it.equals("Provisional")
        })
        pesoBruto()
        cotizacionDiariaDeEstano()
        cotizacionQuincenalDeEstano()
        alicuotaDeEstano()
        tipoDeCambioOficial()
        tipoDeCambioComercial()

        fechaDeLiquidacion(blank: false, nullable: true)
        //informacion de analisis de laboratorio
        porcentajeEstanoPromexbol nullable: false, min: 0.0, max: 100.0
        porcentajeHumedadPromexbol nullable: false, min: 0.0, max: 100.0
        porcentajeMermaPromexbol nullable: false, min: 0.0, max: 100.0

        porcentajeEstanoCliente nullable: false, min: 0.0, max: 100.0
        porcentajeHumedadCliente nullable: false, min: 0.0, max: 100.0
        porcentajeMermaCliente nullable: false, min: 0.0, max: 100.0

        porcentajeEstanoFinal nullable: false, min: 0.0, max: 100.0
        porcentajeHumedadFinal nullable: false, min: 0.0, max: 100.0
        porcentajeMermaFinal nullable: false, min: 0.0, max: 100.0

        kilosNetosHumedos()
        kilosNetosSecos()
        kilosFinosEstano()
        librasFinasDeEstano()
        valorOficialBruto()

        valorPorTonelada()
        //VARIABLES DE PROTECCION
        valorPorToneladaManual nullable: true
        puntoDeBajada nullable: true
        //TABLA COTIZACION
        ajustePrecioEstano nullable: false

        valorNetoMineral()
        valorNetoMineralEnBolivianos()
        bonoCalidad()
        bonoIncentivo()
        valorDeCompra()

        porcentajeRegalia(blank: true)
        regaliaMinera()
        retenciones(blank: false)//cadena JSON
        totalRetenciones()
        totalPagado()
        totalAnticiposContraEntrega()
        totalAnticiposContraFuturaEntrega()
        totalLiquidoPagable()
        observaciones blank: true, nullable: true
        motivoDeModificacion blank: false
        fechaUltimaModificacion display: false, nullable: true // desde superclase
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

    def beforeInsert = {
        def c = LiquidacionDeEstano.createCriteria()
        def results = c {
            projections {
                max('numeroLiquidacionEstano')
            }}
        def maxNumeroLiquidacion = results.get(0)?: 0
        this.numeroLiquidacionEstano = maxNumeroLiquidacion + 1
        this.conjuntoEstano = "-"

        this.porcentajeRegalia="0"
        this.liquidado=1
        this.fechaUltimaModificacion=new java.util.Date()
        this.usuario = springSecurityService.getCurrentUser()

        def conversor = new NumeroALiteral()
        this.totalLiquidoPagableLiteral = conversor.Convertir(this.totalLiquidoPagable.toString(),true)

        this.fechaDeCancelacion = new java.util.Date(84,5,14)

        this.costoLaboratorio1 = (this.costoLaboratorio1)?this.costoLaboratorio1:0
        this.costoLaboratorio2 = (this.costoLaboratorio2)?this.costoLaboratorio2:0
        this.costoLaboratorio3 = (this.costoLaboratorio3)?this.costoLaboratorio3:0
        this.costoLaboratorio4 = (this.costoLaboratorio4)?this.costoLaboratorio4:0
    }

    def afterInsert = {
        //actualizando el estado del lote
        this.recepcionDeEstano.estadoDelLote = "LIQUIDADO"
        this.recepcionDeEstano.save()

        //registrar las retenciones realizadas a esta liquidacion
        LiquidacionDeEstano.withNewTransaction {
            def liquidacionDeEstano = LiquidacionDeEstano.get(this.id)
            def retencionesJSON = new JSONArray(retenciones)
            retencionesJSON.each {
                def codigo = it.getAt("CODIGO")
                def cantidad = it.getAt("CANTIDAD")
                def tipo = it.getAt("TIPO")
                def unidad = it.getAt("UNIDAD")
                def descripcion = it.getAt("DESCRIPCION")
                def monto = it.getAt("MONTO")
                def asignacion = it.getAt("ASIGNACION")
//                def variable = it.getAt("VARIABLE")
//                def operador = it.getAt("OPERADOR")
//                def referencia = it.getAt("REFERENCIA")
                log.error("**** RETENCION: ${codigo} - ${cantidad} - ${tipo} - ${descripcion} - ${asignacion} - ${monto}")
                if(!codigo.equals("-")){
                    def liquidacionDeEstanoRetenciones = new LiquidacionDeEstanoRetenciones(
                            liquidacionDeEstano: liquidacionDeEstano,
                            codigo: codigo,
                            cantidadDescuento: cantidad,
                            unidadDeDescuento: unidad,
                            tipoDeRetencion: tipo,
                            descripcion: descripcion,
                            asignacionDelDescuento: asignacion,
                            monto: monto
//                            ,
//                            variable: variable,
//                            operador: operador,
//                            referencia: referencia
                    )
                    liquidacionDeEstanoRetenciones.save(failOnError: true)

                    def retencionPorPagarComplejo = new RetencionPorPagarComplejo(
                            liquidacionId: liquidacionDeEstano.id,
                            codigo: codigo,
                            cantidadDescuento: cantidad,
                            unidadDeDescuento: unidad,
                            tipoDeRetencion: tipo,
                            descripcion: descripcion,
                            asignacionDelDescuento: asignacion,
                            monto: monto,
                            lote: liquidacionDeEstano.recepcionDeEstano.toString(),
                            kilosNetosSecos: liquidacionDeEstano.kilosNetosSecos,
                            valorOficialNeto: liquidacionDeEstano.valorNetoMineralEnBolivianos,
//                            recepcionDeComplejo: liquidacionDeEstano.recepcionDeEstano,
                            recepcionDeEstano: liquidacionDeEstano.recepcionDeEstano,
                            tipoDeMineral: "ESTANO",
                            empresa: liquidacionDeEstano.empresa,
//                            fechaDeRegistro: liquidacionDeEstano.fechaDeLiquidacion,
                            fechaDeRegistro: liquidacionDeEstano.recepcionDeEstano.fechaDeRecepcion,
                            pagado: "NO"
                    )
                    retencionPorPagarComplejo.save(failOnError: true)
                }
            }
        }

        //procesar los anticipos contra entrega
        def anticipoDetalle = AnticipoDetalle.findByRecepcionId(this.recepcionDeEstano.id)
        if(anticipoDetalle){
            def anticipo = anticipoDetalle.anticipo
            def recepcion = RecepcionDeEstano.get(anticipoDetalle.recepcionId)
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
                    cliente: recepcionDeEstano.cliente,
                    empresa: recepcionDeEstano.empresa,
                    fechaDeAnticipo: new java.util.Date(),
                    compromiso: "REALIZAR EL PAGO EN LA SIGUIENTE ENTREGA",
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
            def ultimoEstadoDeCuenta = EstadoDeCuenta.findAllByCliente(this.recepcionDeEstano.cliente, [sort: "id", order: "desc"])
            def ultimoSaldo = (ultimoEstadoDeCuenta.size()>0)?ultimoEstadoDeCuenta.get(0).saldo:0
            def saldo = ultimoSaldo-this.totalAnticiposContraFuturaEntrega
            def estadoDeCuenta = new EstadoDeCuenta(cliente: this.recepcionDeEstano.cliente,
                    empresa: this.recepcionDeEstano.empresa,
                    ci: this.recepcionDeEstano.cliente.ci,
                    nombre: this.recepcionDeEstano.cliente.nombre,
                    nombreEmpresa: this.recepcionDeEstano.empresa.nombreDeEmpresa,
                    fecha: this.fechaDeLiquidacion,
                    numeroComprobante: this.id,
                    detalle: "PAGO POR ENTREGA POR ANTICIPO CONTRA ENTREGA",
                    debe: 0.0,
                    haber: this.totalAnticiposContraFuturaEntrega,
                    saldo: saldo)
            estadoDeCuenta.save(failOnError: true)
        }
    }

    def beforeUpdate = {
        //ANULADO POR GENERAR CAMBIOS QUE PRODUCEN ERROR
        def conversor = new NumeroALiteral()
        this.totalLiquidoPagableLiteral = conversor.Convertir(this.totalLiquidoPagable.toString(),true)
    }

    def afterUpdate = {
        //registrar las retenciones realizadas a esta liquidacion
        //OJO: por alguna razon la funcion delete() no funciona dentro un withNewTransaction
        def retencionesAnteriores = LiquidacionDeEstanoRetenciones.findAllByLiquidacionDeEstano(this)
        retencionesAnteriores.each {
            log.error("**** ELIMINANDO: ${it.descripcion} MONTO: ${it.monto}")
            it.delete()
        }

        def retencionesPorPagarAnteriores = RetencionPorPagarComplejo.findAllByLiquidacionId(this.id)
        retencionesPorPagarAnteriores.each {
            log.error("**** RETENCION POR PAGAR: ELIMINANDO: ${it.descripcion} MONTO: ${it.monto}")
            it.delete()
        }

        LiquidacionDeEstano.withNewTransaction {
            //actualizando el estado del lote
            this.recepcionDeEstano.estadoDelLote = "LIQUIDADO"
            this.recepcionDeEstano.save()

            def liquidacionDeEstano = LiquidacionDeEstano.get(this.id)
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
                def variable = it.getAt("VARIABLE")
                def operador = it.getAt("OPERADOR")
                def referencia = it.getAt("REFERENCIA")
                log.error("**** RETENCION: ${codigo} - ${cantidad} - ${tipo} - ${descripcion} - ${asignacion} - ${monto}")
                if(!codigo.equals("-")){
                    def liquidacionDeEstanoRetenciones = new LiquidacionDeEstanoRetenciones(
                            liquidacionDeEstano: liquidacionDeEstano,
                            codigo: codigo,
                            cantidadDescuento: cantidad,
                            unidadDeDescuento: unidad,
                            tipoDeRetencion: tipo,
                            descripcion: descripcion,
                            asignacionDelDescuento: asignacion,
                            monto: monto,
                            variable: variable,
                            operador: operador,
                            referencia: referencia)
                    liquidacionDeEstanoRetenciones.save(failOnError: true)

                    def retencionPorPagarComplejo = new RetencionPorPagarComplejo(
                            liquidacionId: liquidacionDeEstano.id,
                            codigo: codigo,
                            cantidadDescuento: cantidad,
                            unidadDeDescuento: unidad,
                            tipoDeRetencion: tipo,
                            descripcion: descripcion,
                            asignacionDelDescuento: asignacion,
                            monto: monto,
                            lote: liquidacionDeEstano.recepcionDeEstano.toString(),
                            kilosNetosSecos: liquidacionDeEstano.kilosNetosSecos,
                            valorOficialNeto: liquidacionDeEstano.valorNetoMineralEnBolivianos,
//                            recepcionDeComplejo: liquidacionDeEstano.recepcionDeEstano,
                            recepcionDeEstano: liquidacionDeEstano.recepcionDeEstano,
                            tipoDeMineral: "ESTANO",
                            empresa: liquidacionDeEstano.empresa,
//                            fechaDeRegistro: liquidacionDeEstano.fechaDeLiquidacion,
                            fechaDeRegistro: liquidacionDeEstano.recepcionDeEstano.fechaDeRecepcion,
                            pagado: "NO"
                    )
                    retencionPorPagarComplejo.save(failOnError: true)
                }
            }
        }
        /*** BLOQUEANDO ESTE CODIGO PORQUE CUANDO SE REALIZA LA CANCELACION DEL LOTE LIQUIDADO SE INVOCA
         * AL CLOSURE afterUpdate GENERANDO INCONSISTENCIA EN LA ENTIDAD estadoDeCuenta***/
        /*
        //eliminar el ultimo registro insertado en el EstadoDeCuenta asumiendo hubo una modificacion en el campo totalAnticiposContraFuturaEntrega
        //primero se obtiene un listado ordenado descendentemente por el id, asi se tiene en la primera posicion al ultimo registro
        def ultimoEstadoDeCuentaAEliminar = EstadoDeCuenta.findAllByCliente(this.recepcionDeEstano.cliente, [sort: "id", order: "desc"])
        //esta verificacion es solo para ver si existe por lo menos un registro
        if (ultimoEstadoDeCuentaAEliminar.size()>0)
        //eliminar el ultimo registro realizado
            ultimoEstadoDeCuentaAEliminar.get(0).delete()
        //actualizar el estado de cuenta para el caso de anticipos contra futura entrega
        def ultimoEstadoDeCuenta = EstadoDeCuenta.findAllByCliente(this.recepcionDeEstano.cliente, [sort: "id", order: "desc"])
        def ultimoSaldo = (ultimoEstadoDeCuenta.size()>0)?ultimoEstadoDeCuenta.get(0).saldo:0
        def saldo = ultimoSaldo-this.totalAnticiposContraFuturaEntrega
        def estadoDeCuenta = new EstadoDeCuenta(cliente: this.recepcionDeEstano.cliente,
                empresa: this.recepcionDeEstano.empresa,
                ci: this.recepcionDeEstano.cliente.ci,
                nombre: this.recepcionDeEstano.cliente.nombre,
                nombreEmpresa: this.recepcionDeEstano.empresa.nombreDeEmpresa,
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
        def anticipoDetalle = AnticipoDetalle.findByRecepcionId(this.recepcionDeEstano.id)
        if(anticipoDetalle){
            def anticipo = anticipoDetalle.anticipo
            def recepcion = RecepcionDeEstano.get(anticipoDetalle.recepcionId)
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
                nombreReporte: "RELIQUIDACION DE ZINC PLATA",
                identificadorDocumento: numeroLiquidacionEstano,
                lote: lote,
                motivo: motivoDeModificacion
        )
        reimpresion.save(failOnError: true)
    }

    def beforeDelete(){
        def retencionesAnteriores = LiquidacionDeEstanoRetenciones.findAllByLiquidacionDeEstano(this)
        retencionesAnteriores.each {
            log.error("**** ELIMINANDO: ${it.descripcion} MONTO: ${it.monto}")
            it.delete()
        }

        def retencionesPorPagarAnteriores = RetencionPorPagarComplejo.findAllByLiquidacionId(this.id)
        retencionesPorPagarAnteriores.each {
            log.error("**** RETENCION POR PAGAR: ELIMINANDO: ${it.descripcion} MONTO: ${it.monto}")
            it.delete()
        }
    }

    String toString(){
        "${recepcionDeEstano}"
    }
}
