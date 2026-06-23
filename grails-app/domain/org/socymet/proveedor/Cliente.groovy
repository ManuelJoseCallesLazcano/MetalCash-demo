package org.socymet.proveedor

import org.socymet.seguridad.SecUser

class Cliente {
    static auditable = true

    static searchable = true
    Deposito deposito

    String cuadrilla
    String ci
    String nombre
    String telefono
    String celular

    Empresa empresa

    transient springSecurityService

    static constraints = {
        deposito nullable: false
        empresa()
        cuadrilla blank: true, nullable: true
        ci(blank: false)
        nombre(blank: false)
        telefono(nullable: true)
        celular(nullable: true)
    }

    String toString(){
//        "${nombre} | ${ci}"
        "${nombre} | ${empresa.toString()}"
    }

    Deposito getDepositoUsuarioConectado(){
        return (springSecurityService.getCurrentUser() as SecUser).deposito
    }
}
