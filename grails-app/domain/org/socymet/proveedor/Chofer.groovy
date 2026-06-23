package org.socymet.proveedor

class Chofer {
    static auditable = true

    static searchable = true

    String ci
    String nombre
    String telefono
    String celular

    static constraints = {
        ci(blank: false, unique: false)
        nombre(blank: false)
        telefono(nullable: true)
        celular(nullable: true)
    }

    String toString(){
        "${nombre} | ${ci}"
    }

}
