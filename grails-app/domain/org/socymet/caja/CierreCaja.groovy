package org.socymet.caja

import org.grails.web.json.JSONArray
import org.socymet.seguridad.SecUser

class CierreCaja {
    Integer numeroCierreCaja=0
    Date fechaCierreCaja
    SecUser usuario

    String detalle
    //saldos finales
    BigDecimal debeTotal //ingreso
    BigDecimal haberTotal //egreso
    BigDecimal saldoTotal

    String observaciones="-"

    Date dateCreated
    Date lastUpdated

    transient springSecurityService

    static constraints = {
        numeroCierreCaja()
        fechaCierreCaja()
        usuario()
//        detalle()
        detalle(validator: { val, obj ->
            if(val.equals("[]")) return 'validation.informacionInsuficiente'
        })
        debeTotal()
        haberTotal()
        saldoTotal()
        observaciones nullable: true
    }

    static mapping = {
        detalle type: 'text'
    }

    def beforeInsert = {
        def cierreCajaCriteria = CierreCaja.createCriteria()
        def results = cierreCajaCriteria.list {
            projections {
                max('numeroCierreCaja')
            }}
        def maximum = results.get(0)?: 0
        this.numeroCierreCaja = maximum + 1
        this.usuario = springSecurityService.getCurrentUser()
    }

    def afterInsert = {
        def detalleJSON = new JSONArray(detalle)

        CierreCaja.withNewTransaction {
            detalleJSON.each {
                def movimientoCaja = MovimientoCaja.get(it.getAt("movimientoCajaId").toString().toInteger())
                def numeroMovimiento = it.getAt("numeroMovimiento").toString().toInteger()
                def fechaMovimiento = it.getAt("fechaMovimiento").toString()
                def ingreso = it.getAt("ingresoId").toString().toInteger()==0?null:Ingreso.get(it.getAt("ingresoId").toString().toInteger())
                def egreso = it.getAt("egresoId").toString().toInteger()==0?null:Egreso.get(it.getAt("egresoId").toString().toInteger())
                def ci = it.getAt("ci").toString()
                def nombre = it.getAt("nombre").toString()
                def concepto = it.getAt("concepto").toString()
                def debe = it.getAt("debe").toString().toBigDecimal()
                def haber = it.getAt("haber").toString().toBigDecimal()
                def saldo = it.getAt("saldo").toString().toBigDecimal()
                def usuario = SecUser.get(it.getAt("usuarioId").toString().toInteger())

                def cierreCajaDetalle = new CierreCajaDetalle(
                        cierreCaja: this,
                        movimientoCaja: movimientoCaja,
                        numeroMovimiento: numeroMovimiento,
                        fechaMovimiento: fechaMovimiento,
                        ingreso: ingreso,
                        egreso: egreso,
                        ci: ci,
                        nombre: nombre,
                        concepto: concepto,
                        debe: debe,
                        haber: haber,
                        saldo: saldo,
                        usuario: usuario
                )
                cierreCajaDetalle.save(failOnError: true)
            }
        }

        CierreCaja.withNewTransaction {
            detalleJSON.each {
                if(it.getAt("ingresoId").toString().toInteger()!=0){
                    def ingreso=Ingreso.get(it.getAt("ingresoId").toString().toInteger())
                    ingreso.consolidado="SI"
                    ingreso.save(failOnError: true, flush: true)
                }
                if(it.getAt("egresoId").toString().toInteger()!=0){
                    def egreso=Egreso.get(it.getAt("egresoId").toString().toInteger())
                    egreso.consolidado="SI"
                    egreso.save(failOnError: true, flush: true)
                }
            }
        }

        CierreCaja.withNewTransaction {
            detalleJSON.each {
                def movimientoCaja = MovimientoCaja.get(it.getAt("movimientoCajaId").toString().toInteger())
                movimientoCaja.consolidado="SI"
                movimientoCaja.save(failOnError: true, flush: true)
            }
        }
    }
}
