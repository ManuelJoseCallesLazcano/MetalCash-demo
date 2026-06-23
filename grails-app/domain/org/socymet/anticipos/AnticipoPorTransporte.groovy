package org.socymet.anticipos

import org.socymet.cancelacion.EstadoCuentaTransporte
import org.socymet.proveedor.Automovil
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.seguridad.SecUser

class AnticipoPorTransporte {
    static auditable = true

    Integer numeroComprobante
    RecepcionDeComplejo recepcionDeComplejo
    String solicitante
    Empresa empresa
    Automovil automovil
    String ci
    String nombreCobrador
    Date fecha
    String descripcion="ANTICIPO CONTRA TRANSPORTE"
    BigDecimal ultimoSaldo=0
    BigDecimal importe=0
    String importeLiteral
    String observaciones="-"
    SecUser usuario

    transient springSecurityService

    static constraints = {
        numeroComprobante nullable: true
        recepcionDeComplejo(nullable: true, unique: true)
        solicitante inList: ["Empresa","Particular"], nullable: false
        empresa nullable: true
        automovil nullable: true
        ci blank: false
        nombreCobrador blank: false
        fecha()
        descripcion blank: false
        ultimoSaldo nullable: false
        importe min: 0.0, nullable: false
        importeLiteral blank: false
        observaciones blank: true
        usuario nullable: true
    }

    def beforeInsert = {
        def c = AnticipoPorTransporte.createCriteria()
        def results = c {
            projections {
                max('numeroComprobante')
            }}
        def maxNumeroComprobante = results.get(0)?: 0
        this.numeroComprobante = maxNumeroComprobante + 1

        this.empresa = this.recepcionDeComplejo.empresa
        this.automovil = this.recepcionDeComplejo.automovil

        this.usuario = springSecurityService.getCurrentUser()
    }

    def afterInsert = {
        def estadoCuentaTransporte = new EstadoCuentaTransporte(
                solicitante: solicitante,
                empresa: empresa,
                automovil: automovil,
                ci: ci,
                nombreResponsable: nombreCobrador,
                fecha: fecha,
                descripcion: "REGISTRO AUTOMATICO: ${descripcion}",
                ingreso: 0,
                egreso: importe,
                saldo: ultimoSaldo+0-importe //ultimo saldo + ingreso - egreso
        )
        estadoCuentaTransporte.save(failOnError: true)
    }

    String toString(){
        numeroComprobante.toString()
    }
}
