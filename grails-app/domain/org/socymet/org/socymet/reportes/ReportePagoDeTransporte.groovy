package org.socymet.org.socymet.reportes

import org.socymet.proveedor.Automovil
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa

class ReportePagoDeTransporte {
    static auditable = true

    Deposito deposito
    String elemento
    Empresa empresa
    Automovil automovil
    Date fechaInicial
    Date fechaFinal
    String estado


    static constraints = {
        deposito nullable: true
        elemento inList: ["COMPLEJO","PB-AG","ZN-AG","CU-AG"], nullable: true
        empresa nullable: false
        automovil nullable: false
        fechaInicial nullable: false
        fechaFinal nullable: false
        estado inList: ["PAGADO","SIN PAGAR"]
    }
}
