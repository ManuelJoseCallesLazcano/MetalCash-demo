package org.socymet.liquidacion

import org.grails.web.json.JSONArray
import org.socymet.anticipos.AnticipoContraEntrega
import org.socymet.anticipos.AnticipoContraFuturaEntrega
import org.socymet.anticipos.AnticipoDetalle
import org.socymet.anticipos.EstadoDeCuenta
import org.socymet.cotizaciones.TablaPreciosCobre
import org.socymet.cotizaciones.TerminosDeContrato
import org.socymet.org.socymet.reportes.Reimpresion
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.utilidades.NumeroALiteral

class LiquidacionDeCobrePlata extends Liquidacion{
    static searchable = true

    Integer numeroLiquidacionCobrePlata //e.g.: GENERADO: 1, PARA MOSTRAR: 0001
    String conjuntoCobrePlata
    //informacion de recepcion (duplicando para facilitar generacion de reportes)
    RecepcionDeComplejo recepcionDeComplejo

    String lote
    String tipoDeMineral
    String nombreCliente
    String nombreEmpresa
    String fechaDeRecepcion
    String cantidadDeSacos
    String estadoDelLote

    BigDecimal pesoBruto
    BigDecimal cotizacionDiariaDeCobre
    BigDecimal cotizacionQuincenalDeCobre
    BigDecimal alicuotaDeCobre
    BigDecimal cotizacionDiariaDePlata
    BigDecimal cotizacionQuincenalDePlata
    BigDecimal alicuotaDePlata
    BigDecimal tipoDeCambioOficial
    BigDecimal tipoDeCambioComercial
    //informacion de analisis de laboratorio y puntos a pagar
    BigDecimal porcentajeCobrePromexbol
    BigDecimal porcentajePlataPromexbol
    BigDecimal porcentajeHumedadPromexbol
    BigDecimal porcentajeMermaPromexbol=1

    BigDecimal porcentajeCobreCliente
    BigDecimal porcentajePlataCliente
    BigDecimal porcentajeHumedadCliente
    BigDecimal porcentajeMermaCliente=1

    BigDecimal porcentajeCobreFinal
    BigDecimal porcentajePlataFinal
    BigDecimal porcentajeHumedadFinal
    BigDecimal porcentajeMermaFinal=1

    BigDecimal merma
    BigDecimal humedad
    BigDecimal porcentajeCobre
    BigDecimal dolarPuntoCobre
    BigDecimal porcentajePlata
    BigDecimal dolarPuntoPlata

    String modoValoracion
    TablaPreciosCobre tablaCobre
    TerminosDeContrato terminosDeContrato
    //pesos
    BigDecimal kilosFinosPlata
    BigDecimal kilosFinosCobre
    BigDecimal librasFinasDeCobre
    BigDecimal onzasTroyDePlata
    //valores brutos
    BigDecimal valorOficialBrutoDePlata
    BigDecimal valorOficialBrutoDeCobre
    BigDecimal valorOficialBrutoDePlataEnBolivianos
    BigDecimal valorOficialBrutoDeCobreEnBolivianos
    //retenciones
    String retenciones

    //adelanto por liquidacion provisional del lote en cuestion
    BigDecimal adelantoPorLiquidacionProvisional

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
        numeroLiquidacionCobrePlata(nullable: true)
        conjuntoCobrePlata(blank: true)

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
        pesoBruto()
        cotizacionDiariaDeCobre()
        cotizacionQuincenalDeCobre()
        alicuotaDeCobre()
        cotizacionDiariaDePlata()
        cotizacionQuincenalDePlata()
        alicuotaDePlata()
        tipoDeCambioOficial()
        tipoDeCambioComercial()

        fechaDeLiquidacion(blank: false, nullable: true)

        //informacion de analisis de laboratorio
        kilosNetosHumedos()
        merma()
        humedad()
        kilosNetosSecos()
        porcentajeCobre()
        dolarPuntoCobre()
        porcentajePlata()
        dolarPuntoPlata()

        porcentajeCobrePromexbol nullable: false, min: 0.0, max: 100.0
        porcentajePlataPromexbol nullable: false, min: 0.0, max: 10000.0
        porcentajeHumedadPromexbol nullable: false, min: 0.0, max: 100.0
        porcentajeMermaPromexbol nullable: false, min: 0.0, max: 100.0

        porcentajeCobreCliente nullable: false, min: 0.0, max: 100.0
        porcentajePlataCliente nullable: false, min: 0.0, max: 10000.0
        porcentajeHumedadCliente nullable: false, min: 0.0, max: 100.0
        porcentajeMermaCliente nullable: false, min: 0.0, max: 100.0

        porcentajeCobreFinal nullable: false, min: 0.0, max: 100.0
        porcentajePlataFinal nullable: false, min: 0.0, max: 10000.0
        porcentajeHumedadFinal nullable: false, min: 0.0, max: 100.0
        porcentajeMermaFinal nullable: false, min: 0.0, max: 100.0

        modoValoracion inList: ["TABLA","TERMINOS DE CONTRATO"]
        tablaCobre nullable: false
        terminosDeContrato nullable: false

        kilosFinosCobre()
        kilosFinosPlata()
        librasFinasDeCobre()
        onzasTroyDePlata()
        valorOficialBrutoDeCobre()
        valorOficialBrutoDePlata()
        valorOficialBrutoDeCobreEnBolivianos()
        valorOficialBrutoDePlataEnBolivianos()
        valorOficialBruto()

        valorPorTonelada()
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
        totalLiquidoPagable()
        totalLiquidoPagableLiteral nullable: true, blank: true
        totalLiquidoPagableOriginal()
        diferenciaLiquidoPagable()
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

    def beforeInsert = {
        def c = LiquidacionDeCobrePlata.createCriteria()
        def results = c {
            projections {
                max('numeroLiquidacionCobrePlata')
            }}
        def maxNumeroLiquidacion = results.get(0)?: 0
        this.numeroLiquidacionCobrePlata = maxNumeroLiquidacion + 1
        this.conjuntoCobrePlata = "-"

        this.porcentajeRegalia=""
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
        this.recepcionDeComplejo.estadoDelLote = "LIQUIDADO"
        this.recepcionDeComplejo.save()
        //registrar las retenciones realizadas a esta liquidacion
        LiquidacionDeCobrePlata.withNewTransaction {
            def liquidacionDeCobrePlata = LiquidacionDeCobrePlata.get(this.id)
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
                    def liquidacionDeCobrePlataRetenciones = new LiquidacionDeCobrePlataRetenciones(
                            liquidacionDeCobrePlata: liquidacionDeCobrePlata,
                            codigo: codigo,
                            cantidadDescuento: cantidad,
                            unidadDeDescuento: unidad,
                            tipoDeRetencion: tipo,
                            descripcion: descripcion,
                            asignacionDelDescuento: asignacion,
                            monto: monto)
                    liquidacionDeCobrePlataRetenciones.save(failOnError: true)

                    def retencionPorPagarComplejo = new RetencionPorPagarComplejo(
                            liquidacionId: liquidacionDeCobrePlata.id,
                            codigo: codigo,
                            cantidadDescuento: cantidad,
                            unidadDeDescuento: unidad,
                            tipoDeRetencion: tipo,
                            descripcion: descripcion,
                            asignacionDelDescuento: asignacion,
                            monto: monto,
                            lote: liquidacionDeCobrePlata.recepcionDeComplejo.toString(),
                            kilosNetosSecos: liquidacionDeCobrePlata.kilosNetosSecos,
                            valorOficialNeto: liquidacionDeCobrePlata.valorNetoMineralEnBolivianos,
                            recepcionDeComplejo: liquidacionDeCobrePlata.recepcionDeComplejo,
                            tipoDeMineral: liquidacionDeCobrePlata.recepcionDeComplejo.tipoDeMineral,
                            empresa: liquidacionDeCobrePlata.empresa,
//                            fechaDeRegistro: liquidacionDeCobrePlata.fechaDeLiquidacion,
                            fechaDeRegistro: liquidacionDeCobrePlata.recepcionDeComplejo.fechaDeRecepcion,
                            pagado: "NO"
                    )
                    retencionPorPagarComplejo.save(failOnError: true)
                }
            }
        }

        //procesar los anticipos contra entrega
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
            def estadoDeCuenta = new EstadoDeCuenta(cliente: this.recepcionDeComplejo.cliente,
                    empresa: this.recepcionDeComplejo.empresa,
                    ci: this.recepcionDeComplejo.cliente.ci,
                    nombre: this.recepcionDeComplejo.cliente.nombre,
                    nombreEmpresa: this.recepcionDeComplejo.empresa.nombreDeEmpresa,
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
        def retencionesAnteriores = LiquidacionDeCobrePlataRetenciones.findAllByLiquidacionDeCobrePlata(this)
        retencionesAnteriores.each {
            log.error("**** ELIMINANDO: ${it.descripcion} MONTO: ${it.monto}")
            it.delete()
        }

        def retencionesPorPagarAnteriores = RetencionPorPagarComplejo.findAllByLiquidacionId(this.id)
        retencionesPorPagarAnteriores.each {
            log.error("**** RETENCION POR PAGAR: ELIMINANDO: ${it.descripcion} MONTO: ${it.monto}")
            it.delete()
        }

        LiquidacionDeCobrePlata.withNewTransaction {
            //actualizando el estado del lote
            this.recepcionDeComplejo.estadoDelLote = "LIQUIDADO"
            this.recepcionDeComplejo.save()

            def liquidacionDeCobrePlata = LiquidacionDeCobrePlata.get(this.id)
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
                    def liquidacionDeCobrePlataRetenciones = new LiquidacionDeCobrePlataRetenciones(
                            liquidacionDeCobrePlata: liquidacionDeCobrePlata,
                            codigo: codigo,
                            cantidadDescuento: cantidad,
                            unidadDeDescuento: unidad,
                            tipoDeRetencion: tipo,
                            descripcion: descripcion,
                            asignacionDelDescuento: asignacion,
                            monto: monto)
                    liquidacionDeCobrePlataRetenciones.save(failOnError: true)

                    def retencionPorPagarComplejo = new RetencionPorPagarComplejo(
                            liquidacionId: liquidacionDeCobrePlata.id,
                            codigo: codigo,
                            cantidadDescuento: cantidad,
                            unidadDeDescuento: unidad,
                            tipoDeRetencion: tipo,
                            descripcion: descripcion,
                            asignacionDelDescuento: asignacion,
                            monto: monto,
                            lote: liquidacionDeCobrePlata.recepcionDeComplejo.toString(),
                            kilosNetosSecos: liquidacionDeCobrePlata.kilosNetosSecos,
                            valorOficialNeto: liquidacionDeCobrePlata.valorNetoMineralEnBolivianos,
                            recepcionDeComplejo: liquidacionDeCobrePlata.recepcionDeComplejo,
                            tipoDeMineral: liquidacionDeCobrePlata.recepcionDeComplejo.tipoDeMineral,
                            empresa: liquidacionDeCobrePlata.empresa,
//                            fechaDeRegistro: liquidacionDeCobrePlata.fechaDeLiquidacion,
                            fechaDeRegistro: liquidacionDeCobrePlata.recepcionDeComplejo.fechaDeRecepcion,
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
        def ultimoEstadoDeCuentaAEliminar = EstadoDeCuenta.findAllByCliente(this.recepcionDeComplejo.cliente, [sort: "id", order: "desc"])
        //esta verificacion es solo para ver si existe por lo menos un registro
        if (ultimoEstadoDeCuentaAEliminar.size()>0)
        //eliminar el ultimo registro realizado
            ultimoEstadoDeCuentaAEliminar.get(0).delete()
        //actualizar el estado de cuenta para el caso de anticipos contra futura entrega
        def ultimoEstadoDeCuenta = EstadoDeCuenta.findAllByCliente(this.recepcionDeComplejo.cliente, [sort: "id", order: "desc"])
        def ultimoSaldo = (ultimoEstadoDeCuenta.size()>0)?ultimoEstadoDeCuenta.get(0).saldo:0
        def saldo = ultimoSaldo-this.totalAnticiposContraFuturaEntrega
        def estadoDeCuenta = new EstadoDeCuenta(cliente: this.recepcionDeComplejo.cliente,
                empresa: this.recepcionDeComplejo.empresa,
                ci: this.recepcionDeComplejo.cliente.ci,
                nombre: this.recepcionDeComplejo.cliente.nombre,
                nombreEmpresa: this.recepcionDeComplejo.empresa.nombreDeEmpresa,
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
        //REGISTRO DEL MOTIVO DE RELIQUIDACION
        def reimpresion = new Reimpresion(
                fecha: new java.util.Date(),
                nombreReporte: "RELIQUIDACION DE COBRE PLATA",
                identificadorDocumento: numeroLiquidacionCobrePlata,
                lote: lote,
                motivo: motivoDeModificacion
        )
        reimpresion.save(failOnError: true)
    }

    String toString(){
        "${recepcionDeComplejo}"
    }
}
