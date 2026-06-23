package org.socymet.org.socymet.reportes

class LibroRegaliasMineras {
    static auditable = true

    String elemento
    Date fechaInicial
    Date fechaFinal

    static constraints = {
        elemento inList: ["Complejo"]
        fechaInicial(nullable: false)
        fechaFinal(nullable: false)
    }
}
