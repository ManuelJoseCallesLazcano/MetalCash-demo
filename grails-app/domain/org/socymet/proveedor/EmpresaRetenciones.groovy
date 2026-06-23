package org.socymet.proveedor

class EmpresaRetenciones {
    static auditable = true

    Empresa empresa
    String descripcion
    String tipoDeRetencion
    BigDecimal cantidadDescuento
    String unidadDeDescuento
    String asignacionDelDescuento

    static constraints = {
        empresa()
        descripcion(blank: false)
        tipoDeRetencion(inList: ["DE LEY","OTRA"])
        cantidadDescuento(min: 0.0, blank: false)
        unidadDeDescuento(inList: ["%","Bs"])
        asignacionDelDescuento(inList: ["VBV","VNV","TON. BRUTA","SACO","FIJO"])
    }

    String toString(){
        "$empresa : $descripcion $tipoDeRetencion $cantidadDescuento $unidadDeDescuento $asignacionDelDescuento"
    }
}
