package org.socymet.org.socymet.reportes

import org.socymet.proveedor.Empresa

class ReportePagoTransporteReimpresion {
    String elemento
    Empresa empresa
    Date fechaInicial
    Date fechaFinal
    String loteInicial
    String loteFinal
    String motivoReimpresion

    static constraints = {
        elemento inList: ["Estano","Plata","Wolfran","Antimonio","Complejo","Plomo Plata","Zinc Plata"]
        empresa nullable: true
        fechaInicial()
        fechaFinal()
        loteInicial blank: true
        loteFinal blank: true
        motivoReimpresion blank: false
    }
}
