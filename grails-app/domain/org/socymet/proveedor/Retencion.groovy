package org.socymet.proveedor

class Retencion {
    static auditable = true

    String descripcion
    String tipoDeRetencion
    BigDecimal cantidadDescuento
    String unidadDeDescuento
    String asignacionDelDescuento

    static constraints = {
        descripcion(blank: false)
        tipoDeRetencion(inList: ["DE LEY","OTRA"])
        cantidadDescuento(min: 0.0, blank: false)
        unidadDeDescuento(inList: ["%","Bs"])
        asignacionDelDescuento(inList: ["VNV","SACO","FIJO","VBV"])
    }

    String toString(){
        "${descripcion}"
    }

    def beforeInsert = {
        descripcion = descripcion.replace('/','_')
    }
}
