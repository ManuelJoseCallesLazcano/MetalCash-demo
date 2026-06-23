package org.socymet.org.socymet.reportes

import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa

class ReporteLotesRecepcionados {
    static auditable = true

    Deposito deposito
    String elemento
    Empresa empresa
    Date fechaInicial
    Date fechaFinal
    String loteInicial
    String loteFinal
    String estado

    static constraints = {
        deposito nullable: false
        elemento inList: ["Complejo","Plomo Plata","Zinc Plata","Cobre Plata"]
        empresa nullable: true
        fechaInicial()
        fechaFinal()
        loteInicial blank: true
        loteFinal blank: true
//        estado inList: ["NO LIQUIDADO","LIQUIDADO","Baja","Todos"]
        estado inList: ["NO LIQUIDADO","LIQUIDADO","Todos"]
    }
}
