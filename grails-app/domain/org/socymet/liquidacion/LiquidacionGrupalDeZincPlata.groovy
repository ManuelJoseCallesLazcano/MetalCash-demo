package org.socymet.liquidacion

import org.socymet.proveedor.Deposito

class LiquidacionGrupalDeZincPlata {
    Deposito deposito
    Integer loteInicial
    Integer loteFinal

    BigDecimal millis
    String lotes
    String lotesLiquidados
    BigDecimal total = 0

    static constraints = {
        deposito nullable: false
        loteInicial nullable: false
        loteFinal nullable: false

        millis nullable: true
        lotes()
        lotesLiquidados()
        total nullable: false
    }

    static mapping = {
        lotes type: 'text'
        lotesLiquidados type: 'text'
    }

    def afterInsert = {

    }
}
