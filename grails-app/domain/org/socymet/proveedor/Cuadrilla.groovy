package org.socymet.proveedor

class Cuadrilla {
    static auditable = true

    Empresa empresa
    String nombreCuadrilla

    static constraints = {
        empresa nullable: false
        nombreCuadrilla blank: false
    }

    String toString(){
        nombreCuadrilla
    }
}
