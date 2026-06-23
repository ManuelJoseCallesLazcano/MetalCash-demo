package org.socymet.caja

class Subcuenta {
    Cuenta cuenta

    String codigoSubcuenta
    String descripcion

    static constraints = {
        cuenta()
        codigoSubcuenta(unique: true)
        descripcion()
    }

    String toString(){
        "$codigoSubcuenta: $descripcion"
    }
}
