package org.socymet.org.socymet.reportes


class GeneralLiquidacion {
    Date fechaInicial
    Date fechaFinal

    static constraints = {
        fechaInicial()
        fechaFinal()
    }
}
