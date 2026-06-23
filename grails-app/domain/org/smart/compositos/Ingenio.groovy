package org.smart.compositos

class Ingenio {
    static auditable = true

    String nombreIngenio
    String telefono
    
    static constraints = {
        nombreIngenio()
        telefono(nullable: true)
    }

    String toString(){
        nombreIngenio
    }
}
