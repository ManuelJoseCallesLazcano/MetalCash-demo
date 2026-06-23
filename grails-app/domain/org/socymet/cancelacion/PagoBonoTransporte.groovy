package org.socymet.cancelacion

import org.grails.web.json.JSONArray
import org.socymet.proveedor.Automovil
import org.socymet.seguridad.SecUser

class PagoBonoTransporte {
    static searchable = true

    Integer numeroComprobante
    String ci
    String nombreCobrador

    Automovil automovil

    Integer numeroMesesPagables //parametro general

    Date fechaDePago

    Date fechaInicial
    Date fechaFinal

    String acumulacionPorMes
    String lotesBono

    Integer numeroMesesAcumulados
    BigDecimal totalKilosBrutos
    BigDecimal tipoDeCambio
    BigDecimal totalPagable=0
    String totalPagableLiteral

    String observaciones

    SecUser usuario

    transient springSecurityService

    static constraints = {
        numeroComprobante nullable: true
        ci blank: false
        nombreCobrador blank: false
        automovil nullable: true
        numeroMesesPagables nullable: false
        fechaDePago nullable: false
        fechaInicial nullable: false
        fechaFinal nullable: false
        acumulacionPorMes()
        lotesBono()
        numeroMesesAcumulados nullable: false, validator: { val, obj, errors ->
            if (val<obj.numeroMesesPagables) errors.rejectValue('numeroMesesAcumulados', 'insuficiente')
        }
        totalKilosBrutos min: 0.0, nullable: false
        tipoDeCambio min: 0.0, nullable: false
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
        def c = PagoBonoTransporte.createCriteria()
        def results = c {
            projections {
                max('numeroComprobante')
            }}
        def maxNumeroComprobante = results.get(0)?: 0
        this.numeroComprobante = maxNumeroComprobante + 1
        this.usuario = springSecurityService.getCurrentUser()
    }

    def afterInsert = {
        def lotesJSON = new JSONArray(acumulacionPorMes)
        def lotesBonoJSON = new JSONArray(lotesBono)
        def fecha
        def cantidadAcumulada

        PagoBonoProduccion.withNewTransaction {
            lotesJSON.each {
                fecha = new Date().parse("MM/yyyy",it.getAt("fecha").toString())
                cantidadAcumulada = it.getAt("cantidadAcumulada").toString().toBigDecimal()

                log.error("**** ACUMULACION: ${fecha} - ${cantidadAcumulada}")
                if(cantidadAcumulada!=0){
                    def acumulacionBonoTransporte = new AcumulacionBonoTransporte(
                            pagoBonoTransporte: this,
                            fecha: fecha,
                            automovil: this.automovil,
                            cantidadAcumulada: cantidadAcumulada
                    )
                    acumulacionBonoTransporte.save(failOnError: true)
                }
            }

            lotesBonoJSON.each {
                fecha = new Date().parse("dd/MM/yyyy",it.getAt("fechaDeRecepcion").toString())
                def loteBonoTransporte = new LoteBonoTransporte(
                        pagoBonoTransporte: this,
                        lote: it.getAt("lote"),
                        fechaDeRecepcion: fecha,
                        nombreEmpresa: it.getAt("nombreEmpresa"),
                        nombreCliente: it.getAt("nombreCliente"),
                        kilosBrutos: it.getAt("kilosBrutos").toString().toBigDecimal(),
                        leyPlomo: it.getAt("leyPlomo").toString().toBigDecimal(),
                        leyZinc: it.getAt("leyZinc").toString().toBigDecimal(),
                        bono: it.getAt("bono").toString().toBigDecimal()
                )
                loteBonoTransporte.save(failOnError: true)
            }
        }
    }

    def afterUpdate = {
    }

    def beforeDelete = {
        PagoBonoProduccion.withNewTransaction {
            def acumulacionBonoTransportes = AcumulacionBonoTransporte.findAllByPagoBonoTransporte(this)
            acumulacionBonoTransportes.each {
                it.delete(flush: true)
            }
        }
    }

    String toString(){
        "Cobrador: ${nombreCobrador} [${ci}] Fecha de Pago: ${new java.text.SimpleDateFormat('dd/MM/yyyy').format(fechaDePago)}"
    }
}
