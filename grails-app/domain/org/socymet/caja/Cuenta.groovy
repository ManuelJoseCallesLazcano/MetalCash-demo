package org.socymet.caja

class Cuenta {
    String codigoCuenta
    String descripcion

    static constraints = {
        codigoCuenta(unique: true)
        descripcion()
    }

    String toString(){
        "$codigoCuenta: $descripcion"
    }
}
