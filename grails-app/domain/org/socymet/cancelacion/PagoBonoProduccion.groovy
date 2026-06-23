package org.socymet.cancelacion

import org.grails.web.json.JSONArray
import org.socymet.proveedor.Cliente
import org.socymet.proveedor.Cuadrilla
import org.socymet.proveedor.Empresa
import org.socymet.seguridad.SecUser

class PagoBonoProduccion {
    static searchable = true

    Integer numeroComprobante
    String ci
    String nombreCobrador

    String tipoSeleccion
    Cliente cliente
    String nombreCliente
    Empresa empresa
    String nombreEmpresa
    Cuadrilla cuadrilla

    Integer numeroMesesPagables //parametro general

    Date fechaDePago

    Date fechaInicial
    Date fechaFinal

    String acumulacionPorMes
    String lotesBono

    Integer numeroMesesAcumulados
    BigDecimal totalKilosNetosSecos
    BigDecimal tipoDeCambio
    BigDecimal bonoPorTonelada  //parametro general
    BigDecimal totalPagable=0
    String totalPagableLiteral

    String observaciones

    SecUser usuario

    transient springSecurityService

    static constraints = {
        numeroComprobante nullable: true
        ci blank: false
        nombreCobrador blank: false
        tipoSeleccion inList: ["INDIVIDUAL","CUADRILLA"], nullable: false
        cliente nullable: true
        nombreCliente blank: false, nullable: false
        empresa nullable: true
        nombreEmpresa blank: false, nullable: false
        cuadrilla nullable: true
        numeroMesesPagables nullable: false
        fechaDePago nullable: false
        fechaInicial nullable: false
        fechaFinal nullable: false
        acumulacionPorMes()
        lotesBono()
        numeroMesesAcumulados nullable: false, validator: { val, obj, errors ->
            if (val<obj.numeroMesesPagables) errors.rejectValue('numeroMesesAcumulados', 'insuficiente')
        }
        totalKilosNetosSecos min: 0.0, nullable: false
        tipoDeCambio min: 0.0, nullable: false
        bonoPorTonelada min: 0.0, nullable: false
        totalPagable nullable: false, min: 0.0
        totalPagableLiteral blank: false
        observaciones blank: true
        usuario display: false, nullable: true
    }

    static mapping = {
        acumulacionPorMes type: 'text'
        lotesBono type: 'text'
    }

    def beforeInsert = {
        def c = PagoBonoProduccion.createCriteria()
        def results = c {
            projections {
                max('numeroComprobante')
            }}
        def maxNumeroComprobante = results.get(0)?: 0
        this.numeroComprobante = maxNumeroComprobante + 1
        this.usuario = springSecurityService.getCurrentUser()
    }

    def afterInsert = {
        /*crear una clase para contener todos los bonos pagados para que luego se pueda utilizar
        * para verificar que no se pague dos veces por el mismo.
        * tambien verificar que el pago por cuadrilla este correcto con los siguientes nombres
        * JUAN PEREZ
        * EDWIN FABRICA CRUZ
        * ELEOTERIO SILVESTRE CASTILLO
        * */
        def lotesJSON = new JSONArray(acumulacionPorMes)
        def lotesBonoJSON = new JSONArray(lotesBono)
        def fecha
        def tipoSeleccion
        def cliente
        def empresa
        def cuadrilla
        def cantidadAcumulada

        PagoBonoProduccion.withNewTransaction {
            lotesJSON.each {
                fecha = new Date().parse("MM/yyyy",it.getAt("fecha").toString())
                cantidadAcumulada = it.getAt("cantidadAcumulada").toString().toBigDecimal()

                log.error("**** ACUMULACION: ${fecha} - ${cantidadAcumulada}")
                if(cantidadAcumulada!=0){
                    def acumulacionBonoProduccion = new AcumulacionBonoProduccion(
                            pagoBonoProduccion: this,
                            fecha: fecha,
                            tipoSeleccion: this.tipoSeleccion,
                            cliente: (this.tipoSeleccion.equals("INDIVIDUAL"))?this.cliente:null,
                            empresa: (this.tipoSeleccion.equals("INDIVIDUAL"))?this.cliente.empresa:this.empresa,
                            cuadrilla: (this.tipoSeleccion.equals("CUADRILLA"))?this.cuadrilla:null,
                            cantidadAcumulada: cantidadAcumulada
                    )
                    acumulacionBonoProduccion.save(failOnError: true)
                }
            }

            lotesBonoJSON.each {
                fecha = new Date().parse("dd/MM/yyyy",it.getAt("fechaDeLiquidacion").toString())
                def loteBonoProduccion = new LoteBonoProduccion(
                        pagoBonoProduccion: this,
                        lote: it.getAt("lote"),
                        fechaDeLiquidacion: fecha,
                        nombreEmpresa: it.getAt("nombreEmpresa"),
                        nombreCliente: it.getAt("nombreCliente"),
                        kilosNetosSecos: it.getAt("kilosNetosSecos").toString().toBigDecimal(),
                        totalLiquidoPagable: it.getAt("totalLiquidoPagable").toString().toBigDecimal()
                )
                loteBonoProduccion.save(failOnError: true)
            }
        }
    }

    def afterUpdate = {
    }

    def beforeDelete = {
        PagoBonoProduccion.withNewTransaction {
            def acumulacionBonoProduccion = AcumulacionBonoProduccion.findAllByPagoBonoProduccion(this)
            acumulacionBonoProduccion.each {
                it.delete(flush: true)
            }
        }
    }

    String toString(){
        "Cobrador: ${nombreCobrador} [${ci}] Fecha de Pago: ${new java.text.SimpleDateFormat('dd/MM/yyyy').format(fechaDePago)}"
    }
}
