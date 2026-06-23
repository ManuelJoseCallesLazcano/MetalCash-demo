package org.socymet.proveedor

class Municipio {
    static auditable = true

    String departamento
    String municipio

    static constraints = {
        departamento(inList: ["ORURO","LA PAZ","POTOSI","COCHABAMBA","CHUQUISACA","TARIJA","PANDO","BENI","SANTA CRUZ"], blank: false)
        municipio()
    }

    String toString(){
        municipio
    }
}
