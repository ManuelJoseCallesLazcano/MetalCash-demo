package org.socymet.proveedor

class Deposito {
    static auditable = true

    static searchable = true

    String nombreDeposito
    String codigoDeposito
    String direccion

    static constraints = {
        nombreDeposito nullable: false
        codigoDeposito nullable: false, size: 0..5, unique: true
        direccion nullable: false
    }

    String toString(){
        "$codigoDeposito - $nombreDeposito"
    }
}
