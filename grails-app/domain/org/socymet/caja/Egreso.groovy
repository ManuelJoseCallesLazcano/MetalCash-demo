package org.socymet.caja

import org.socymet.seguridad.SecUser

import java.text.DecimalFormat

class Egreso {
    Integer numeroEgreso=0
    Date fechaEgreso
    //datos de la persona que hace el ingreso

    String ci
    String nombre
    //descripcion
    Cuenta cuenta
    Subcuenta subcuenta

    String operacion
    String identificador

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
        numeroEgreso()
        fechaEgreso()
        ci()
        nombre()
        cuenta()
        subcuenta()
        operacion(inList: ["LIQUIDACION","ANTICIPO","PAGO DE TRANSPORTE","ANTICIPO POR TRANSPORTE","OTROS PAGOS"])
        identificador()
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
        def c = Egreso.createCriteria()
        def results = c {
            projections {
                max('numeroEgreso')
            }}
        def maxNumeroEgreso = results.get(0)?: 0
        this.numeroEgreso = maxNumeroEgreso + 1

        this.fechaEgreso = new java.util.Date()

        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        this.usuario = usuarioActual
    }

    def afterInsert = {
        Egreso.withNewTransaction {
            /*Integer numeroMovimiento
    Date fechaMovimiento

    Ingreso ingreso
    Egreso egreso

    String ci
    String nombre

    String concepto
    BigDecimal debe //ingreso
    BigDecimal haber //egreso
    BigDecimal saldo

    String consolidado
    SecUser usuario*/
            def movimientoCaja = new MovimientoCaja(
                    numeroMovimiento: 0,
                    fechaMovimiento: new java.util.Date(),
                    ingreso: null,
                    egreso: this,
                    ci: this.ci,
                    nombre: this.nombre,
                    concepto: this.concepto,
                    debe: 0.0,
                    haber: this.importe,
                    saldo: 0.0,
                    consolidado: "NO",
                    usuario: this.usuario
            )
            movimientoCaja.save(failOnError: true)
        }
    }

    def beforeUpdate = {
        this.fechaEgreso = new java.util.Date()
    }

    def afterUpdate = {
//        Egreso.withNewTransaction {
//            def movimientoCaja = MovimientoCaja.findByEgreso(this)
//            movimientoCaja.fechaMovimiento = new java.util.Date()
//            movimientoCaja.ingreso = this
//            movimientoCaja.egreso = null
//            movimientoCaja.ci = this.ci
//            movimientoCaja.nombre = this.nombre
//            movimientoCaja.concepto = this.concepto
//            movimientoCaja.debe = this.importe
//            movimientoCaja.haber = 0.0
//            movimientoCaja.saldo = 0.0
//            movimientoCaja.consolidado = "NO"
//            movimientoCaja.caja = this.caja
//            movimientoCaja.usuario = this.usuario
//            movimientoCaja.save(flush:true, failOnError: true)
//        }
    }

    def beforeDelete = {
        Egreso.withNewTransaction {
            def movimientoCaja = MovimientoCaja.findByEgreso(this)
            movimientoCaja.delete(flush:true)
        }
    }

    String toString(){
        def decimalFormat = new DecimalFormat("000000")
        return numeroEgreso?decimalFormat.format(numeroEgreso):""
    }
}
