package org.socymet.org.socymet.reportes

class DetalleFormularioM02 {
    Date fechaInicial
    Date fechaFinal

    static constraints = {
        fechaInicial(nullable: false)
        fechaFinal(nullable: false)
    }
}
