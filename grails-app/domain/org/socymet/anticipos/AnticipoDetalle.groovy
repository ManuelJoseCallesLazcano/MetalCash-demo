package org.socymet.anticipos

class AnticipoDetalle {
    static auditable = true

    static searchable = true

    Anticipo anticipo

    String lote
    Integer recepcionId
    String nombreCliente
    String nombreEmpresa
    String fechaDeRecepcion
    String tipoDeMineral
    BigDecimal pesoBruto //
    String tipoDeMaterial //
    String estadoAnticipo //pagado, sin pagar
    BigDecimal anticipoPagable

    static constraints = {
        anticipo()
        lote(blank: false)
        recepcionId(display: false, nullable: true)
        nombreCliente(blank: false)
        nombreEmpresa(blank: false)
        fechaDeRecepcion(blank: false)
        tipoDeMineral nullable: false
        pesoBruto(blank: false, min: 0.0)
        tipoDeMaterial(blank: false)
        estadoAnticipo blank: false, nullable: true
        anticipoPagable nullable: false
    }

    String toString(){
        "${nombreEmpresa} - ${nombreCliente}: LOTE= ${lote} Estado Anticipo= ${estadoAnticipo} Anticipo Pagable= ${anticipoPagable}"
    }
}
