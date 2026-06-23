package org.smart.compositos

class Comprador {
    static auditable = true

    String nombreComprador
    String nombreContacto
    String telefono
    String email
    static constraints = {
        nombreComprador()
        nombreContacto()
        telefono(nullable: true)
        email(nullable: true)
    }

    String toString(){
        nombreComprador
    }
}
