package org.socymet.liquidacion

import org.grails.web.json.JSONArray
import org.socymet.anticipos.AnticipoContraEntrega
import org.socymet.anticipos.AnticipoContraFuturaEntrega
import org.socymet.anticipos.EstadoDeCuenta
import org.socymet.cotizaciones.TablaCotizacionPlata
import org.socymet.org.socymet.reportes.Reimpresion
import org.socymet.recepcion.RecepcionDePlata
import org.socymet.utilidades.NumeroALiteral

import java.text.DecimalFormat

class LiquidacionDePlata extends Liquidacion{
    static searchable = true

    Integer numeroLiquidacionPlata //e.g.: GENERADO: 1, PARA MOSTRAR: 0001
    String conjuntoPlata
    
    RecepcionDePlata recepcionDePlata
    String lote
    String nombreCliente
    String nombreEmpresa
    String fechaDeRecepcion
    BigDecimal cantidadDeSacos
    BigDecimal tara
    String estadoDelLote

    BigDecimal pesoBruto
    BigDecimal cotizacionDiariaDePlata
    BigDecimal cotizacionQuincenalDePlata
    BigDecimal alicuotaDePlata
    BigDecimal tipoDeCambioOficial
    BigDecimal tipoDeCambioComercial
    //informacion de analisis de laboratorio y puntos a pagar
    BigDecimal humedad
    BigDecimal porcentajePlata
    //pesos
    BigDecimal kilosFinosPlata
    BigDecimal librasFinasDePlata

    //variables de proteccion
    BigDecimal valorPorToneladaManual
    BigDecimal puntoDeBajada

    //referencia a tabla de costos
    TablaCotizacionPlata tablaCotizacionPlata

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
        numeroLiquidacionPlata(nullable: true)
        conjuntoPlata(blank: true)
        
        recepcionDePlata(unique: true)
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
        cotizacionDiariaDePlata()
        cotizacionQuincenalDePlata()
        alicuotaDePlata()
        tipoDeCambioOficial()
        tipoDeCambioComercial()

        fechaDeLiquidacion(blank: false, nullable: true)
        //informacion de analisis de laboratorio
        kilosNetosHumedos()
        humedad()
        kilosNetosSecos()
        porcentajePlata()
        kilosFinosPlata()
        librasFinasDePlata()
        valorOficialBruto()

        valorPorTonelada()
        //VARIABLES DE PROTECCION
        valorPorToneladaManual nullable: true
        puntoDeBajada nullable: true
        //TABLA COTIZACION
        tablaCotizacionPlata nullable: true

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
        observaciones blank: true
        motivoDeModificacion blank: false
        fechaUltimaModificacion display: false, nullable: true // desde superclase
        liquidado display: false, nullable: true
        usuario display: false, nullable: true

        fechaDeCancelacion display: false, nullable: true // desde superclase

        //costos de laboratorio
        detalleLaboratorio1(blanssk:true, nullable: true)
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
        def c = LiquidacionDePlata.createCriteria()
        def results = c {
            projections {
                max('numeroLiquidacionPlata')
            }}
        def maxNumeroLiquidacion = results.get(0)?: 0
        this.numeroLiquidacionPlata = maxNumeroLiquidacion + 1
        this.conjuntoPlata = "-"
        
        this.porcentajeRegalia=""
        this.liquidado=1
        this.fechaUltimaModificacion=new java.util.Date()
        this.usuario = springSecurityService.getCurrentUser()

        this.fechaDeCancelacion = new java.util.Date(84,5,14)

        this.costoLaboratorio1 = (this.costoLaboratorio1)?this.costoLaboratorio1:0
        this.costoLaboratorio2 = (this.costoLaboratorio2)?this.costoLaboratorio2:0
        this.costoLaboratorio3 = (this.costoLaboratorio3)?this.costoLaboratorio3:0
        this.costoLaboratorio4 = (this.costoLaboratorio4)?this.costoLaboratorio4:0
    }

    def afterInsert = {
        //registrar las retenciones realizadas a esta liquidacion
        LiquidacionDePlata.withNewTransaction {
            //actualizando el estado del lote
            this.recepcionDePlata.estadoDelLote = "LIQUIDADO"
            this.recepcionDePlata.save()

            def liquidacionDePlata = LiquidacionDePlata.get(this.id)
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
                    def liquidacionDePlataRetenciones = new LiquidacionDePlataRetenciones(
                            liquidacionDePlata: liquidacionDePlata,
                            codigo: codigo,
                            cantidadDescuento: cantidad,
                            unidadDeDescuento: unidad,
                            tipoDeRetencion: tipo,
                            descripcion: descripcion,
                            asignacionDelDescuento: asignacion,
                            monto: monto)
                    liquidacionDePlataRetenciones.save(failOnError: true)
                }
            }
        }

        //procesar los anticipos contra entrega
        if (this.totalAnticiposContraEntrega.floatValue()>0){
            def decimalFormat = new DecimalFormat("000000")
            def lote = decimalFormat.format(Integer.parseInt(this.lote))

            def anticiposContraEntrega = AnticipoContraEntrega.findAllByLote("AG-${lote}")
            anticiposContraEntrega.each {
                it.anticipoPagado = "SI"
                it.save()
            }
        }

        if (this.totalAnticiposContraFuturaEntrega.floatValue()>0){
            //actualizar el estado de cuenta para el caso de anticipos contra futura entrega
            def ultimoEstadoDeCuenta = EstadoDeCuenta.findAllByCliente(this.recepcionDePlata.cliente, [sort: "id", order: "desc"])
            def ultimoSaldo = (ultimoEstadoDeCuenta.size()>0)?ultimoEstadoDeCuenta.get(0).saldo:0
            def saldo = ultimoSaldo-this.totalAnticiposContraFuturaEntrega
            def estadoDeCuenta = new EstadoDeCuenta(cliente: this.recepcionDePlata.cliente,
                    empresa: this.recepcionDePlata.empresa,
                    ci: this.recepcionDePlata.cliente.ci,
                    nombre: this.recepcionDePlata.cliente.nombre,
                    nombreEmpresa: this.recepcionDePlata.empresa.toString(),
                    fecha: this.fechaDeLiquidacion,
                    numeroComprobante: this.numeroLiquidacionPlata,
                    detalle: "PAGO POR ENTREGA DEL LOTE AG${lote} POR ANTICIPO CONTRA ENTREGA",
                    debe: 0.0,
                    haber: this.totalAnticiposContraFuturaEntrega,
                    saldo: saldo)
            estadoDeCuenta.save(failOnError: true)
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
                    cliente: recepcionDePlata.cliente,
                    empresa: recepcionDePlata.empresa,
                    fechaDeAnticipo: new java.util.Date(),
                    compromiso: "REALIZAR EL PAGO EN LA SIGUIENTE ENTREGA",
                    importe: importeAnticipo,
                    importeLiteral: conversor.Convertir(importeAnticipo.toString(),true),
                    observaciones: "SE HA GENERADO EL REGISTRO PORQUE LA LIQUIDACION A PRODUCIDO SALDO NEGATIVO.",
                    liquidacionId: this.id
            )
            anticipoContraFuturaEntrega.save(failOnError: true)
        }
    }

    def beforeUpdate = {
        //this.fechaDeLiquidacion = new java.util.Date()
    }

    def afterUpdate = {
        //registrar las retenciones realizadas a esta liquidacion
        //OJO: por alguna razon la funcion delete() no funciona dentro un withNewTransaction
        def retencionesAnteriores = LiquidacionDePlataRetenciones.findAllByLiquidacionDePlata(this)
        retencionesAnteriores.each {
            log.error("**** ELIMINANDO: ${it.descripcion} MONTO: ${it.monto}")
            it.delete()
        }

        LiquidacionDePlata.withNewTransaction {
            //actualizando el estado del lote
            this.recepcionDePlata.estadoDelLote = "LIQUIDADO"
            this.recepcionDePlata.save()

            def liquidacionDePlata = LiquidacionDePlata.get(this.id)
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
                    def liquidacionDePlataRetenciones = new LiquidacionDePlataRetenciones(
                            liquidacionDePlata: liquidacionDePlata,
                            codigo: codigo,
                            cantidadDescuento: cantidad,
                            unidadDeDescuento: unidad,
                            tipoDeRetencion: tipo,
                            descripcion: descripcion,
                            asignacionDelDescuento: asignacion,
                            monto: monto)
                    liquidacionDePlataRetenciones.save(failOnError: true)
                }
            }
        }

        /*** BLOQUEANDO ESTE CODIGO PORQUE CUANDO SE REALIZA LA CANCELACION DEL LOTE LIQUIDADO SE INVOCA
         * AL CLOSURE afterUpdate GENERANDO INCONSISTENCIA EN LA ENTIDAD estadoDeCuenta***/
        /*
        //eliminar el ultimo registro insertado en el EstadoDeCuenta asumiendo hubo una modificacion en el campo totalAnticiposContraFuturaEntrega
        //primero se obtiene un listado ordenado descendentemente por el id, asi se tiene en la primera posicion al ultimo registro
        def ultimoEstadoDeCuentaAEliminar = EstadoDeCuenta.findAllByCliente(this.recepcionDePlata.cliente, [sort: "id", order: "desc"])
        //esta verificacion es solo para ver si existe por lo menos un registro
        if (ultimoEstadoDeCuentaAEliminar.size()>0)
        //eliminar el ultimo registro realizado
            ultimoEstadoDeCuentaAEliminar.get(0).delete()
        //actualizar el estado de cuenta para el caso de anticipos contra futura entrega
        def ultimoEstadoDeCuenta = EstadoDeCuenta.findAllByCliente(this.recepcionDePlata.cliente, [sort: "id", order: "desc"])
        def ultimoSaldo = (ultimoEstadoDeCuenta.size()>0)?ultimoEstadoDeCuenta.get(0).saldo:0
        def saldo = ultimoSaldo-this.totalAnticiposContraFuturaEntrega
        def estadoDeCuenta = new EstadoDeCuenta(cliente: this.recepcionDePlata.cliente,
                empresa: this.recepcionDePlata.empresa,
                ci: this.recepcionDePlata.cliente.ci,
                nombre: this.recepcionDePlata.cliente.nombre,
                nombreEmpresa: this.recepcionDePlata.empresa.nombreDeEmpresa,
                fecha: this.fechaDeLiquidacion,
                numeroComprobante: this.id,
                detalle: "PAGO POR ENTREGA POR ANTICIPO CONTRA ENTREGA",
                debe: 0.0,
                haber: this.totalAnticiposContraFuturaEntrega,
                saldo: saldo)
        estadoDeCuenta.save(failOnError: true)
        */
        //REGISTRO DEL MOTIVO DE RELIQUIDACION
        def reimpresion = new Reimpresion(
                fecha: new java.util.Date(),
                nombreReporte: "RELIQUIDACION DE PLATA",
                identificadorDocumento: numeroLiquidacionPlata,
                lote: lote,
                motivo: motivoDeModificacion
        )
        reimpresion.save(failOnError: true)
    }

    String toString(){
        "${recepcionDePlata}"
    }
}
