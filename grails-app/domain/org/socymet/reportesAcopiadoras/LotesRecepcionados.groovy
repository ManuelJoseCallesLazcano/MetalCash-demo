package org.socymet.reportesAcopiadoras

import org.socymet.proveedor.Empresa

class LotesRecepcionados {
    Empresa empresa
    Date fechaInicial
    Date fechaFinal
    String estado

    static constraints = {
        empresa nullable: true
        fechaInicial()
        fechaFinal()
        estado inList: ["NO LIQUIDADO","LIQUIDADO","Todos"]
    }
}
