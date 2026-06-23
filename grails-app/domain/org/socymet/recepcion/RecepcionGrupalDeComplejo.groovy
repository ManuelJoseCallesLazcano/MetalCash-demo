package org.socymet.recepcion

import org.socymet.proveedor.*

class RecepcionGrupalDeComplejo {
    Deposito deposito
    Cliente cliente
    Empresa empresa
    Chofer chofer
    Automovil automovil

    Date fechaDeRecepcion
    String tipoDeMineral

    String lotes

    static constraints = {
        cliente(blank: false)
        empresa(blank: false)
        chofer(blank: false)
        automovil(blank: false)
        fechaDeRecepcion(blank: false)

        deposito nullable: false
        tipoDeMineral inList: ["COMPLEJO","PB-AG","ZN-AG","CU-AG"], nullable: false
    }
}
