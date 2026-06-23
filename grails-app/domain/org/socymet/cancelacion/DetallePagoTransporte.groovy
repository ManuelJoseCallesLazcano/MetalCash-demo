package org.socymet.cancelacion

class DetallePagoTransporte {
    static auditable = true

    static searchable = true

    PagoTransporte pagoTransporte

    String lote
    Integer recepcionId
    String nombreChofer
    String placaAutomovil
    String fechaDeRecepcion
    BigDecimal pesoBruto
    String tipoDeMaterial
    BigDecimal costoDeTransporte

    static constraints = {
        pagoTransporte()
        lote(blank: false)
        recepcionId(display: false, nullable: true)
        nombreChofer(blank: false)
        placaAutomovil(blank: false)
        fechaDeRecepcion(blank: false)
        pesoBruto(blank: false, min: 0.0)
        tipoDeMaterial(blank: false)
        costoDeTransporte nullable: false
    }

    String toString(){
        "Lote: ${lote} Chofer: ${nombreChofer} Automovil: ${placaAutomovil} Fecha De Recepcion: ${fechaDeRecepcion} Peso Bruto: ${pesoBruto} Tipo De Material: ${tipoDeMaterial} Costo De Transporte: ${costoDeTransporte}"
    }
}
