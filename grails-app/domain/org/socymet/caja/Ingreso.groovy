package org.socymet.caja

import org.socymet.seguridad.SecUser

import java.text.DecimalFormat

class Ingreso {
    Integer numeroIngreso=0
    Date fechaIngreso
    //datos de la persona que hace el ingreso

    String ci
    String nombre
    //descripcion
    Cuenta cuenta
    Subcuenta subcuenta
    String concepto
    //datos monetarios
    BigDecimal importe
    String importeLiteral

    String observaciones

    SecUser usuario

    String consolidado="NO"

    Date dateCreated
    Date lastUpdated

    def springSecurityService

    static constraints = {
        numeroIngreso()
        fechaIngreso()
        ci()
        nombre()
        cuenta()
        subcuenta()
        concepto()
        importe(min:0.0)
        importeLiteral()
        observaciones(nullable: true)
        usuario(validator: {
            if(!it) return 'validation.usuarioSinCaja'
        })
        consolidado()
//        consolidado(validator: { val, obj ->
//            if(val.equals("SI")) return 'validation.ingresoConsolidado'
//        })
    }

    def beforeInsert = {
        def c = Ingreso.createCriteria()
        def results = c {
            projections {
                max('numeroIngreso')
            }}
        def maxNumeroIngreso = results.get(0)?: 0
        this.numeroIngreso = maxNumeroIngreso + 1

        this.fechaIngreso = new java.util.Date()

        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        this.usuario = usuarioActual
    }

    def afterInsert = {
        Ingreso.withNewTransaction {
            def movimientoCaja = new MovimientoCaja(
                    numeroMovimiento: 0,
                    fechaMovimiento: new java.util.Date(),
                    ingreso: this,
                    egreso: null,
                    ci: this.ci,
                    nombre: this.nombre,
                    concepto: this.concepto,
                    debe: this.importe,
                    haber: 0.0,
                    saldo: 0.0,
                    consolidado: "NO",
                    usuario: this.usuario
            )
            movimientoCaja.save(failOnError: true)
        }
    }

    def beforeUpdate = {
        this.fechaIngreso = new java.util.Date()
    }

    def afterUpdate = {
        Ingreso.withNewTransaction {
            def movimientoCaja = MovimientoCaja.findByIngreso(this)
            movimientoCaja.fechaMovimiento = new java.util.Date()
            movimientoCaja.ingreso = this
            movimientoCaja.egreso = null
            movimientoCaja.ci = this.ci
            movimientoCaja.nombre = this.nombre
            movimientoCaja.concepto = this.concepto
            movimientoCaja.debe = this.importe
            movimientoCaja.haber = 0.0
            movimientoCaja.saldo = 0.0
            movimientoCaja.consolidado = "NO"
//            movimientoCaja.caja = this.caja
            movimientoCaja.usuario = this.usuario
            movimientoCaja.save(flush:true, failOnError: true)
        }
    }

    def beforeDelete = {
        Ingreso.withNewTransaction {
            def movimientoCaja = MovimientoCaja.findByIngreso(this)
            movimientoCaja.delete(flush:true)
        }
    }

    String toString(){
        def decimalFormat = new DecimalFormat("000000")
        return numeroIngreso?decimalFormat.format(numeroIngreso):""
    }
}

