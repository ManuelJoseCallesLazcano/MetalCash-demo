package org.socymet.proveedor

class Automovil {
    static auditable = true

    static searchable = true

    String placa
    String modelo="-"
    String color="-"

    static constraints = {
        placa(blank: false, unique: true)
        modelo(blank: false)
        color(blank: false)
    }

    String toString(){
        "${placa}"
    }
}
