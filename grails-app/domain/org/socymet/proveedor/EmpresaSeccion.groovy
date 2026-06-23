package org.socymet.proveedor

class EmpresaSeccion {
    static auditable = true

    Empresa empresa
    String nombreSeccion

    static constraints = {
        empresa()
        nombreSeccion()
    }

    String toString(){
        "${empresa.toString()} - ${nombreSeccion}"
    }
}
