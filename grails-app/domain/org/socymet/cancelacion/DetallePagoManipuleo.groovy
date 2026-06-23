package org.socymet.cancelacion

class DetallePagoManipuleo {
    static searchable = true

    PagoManipuleo pagoManipuleo

    String lote
    Integer recepcionId
    Date fechaDeRecepcion
    BigDecimal pesoBruto
    String tipoDeMaterial
    String pesadaVaciada
    String carguioMaquina
    String embolsadaArrumada
    String soloComuneada
    String soloVaciada
    String soloPesada
    String soloEmbolsada
    BigDecimal costoDeManipuleo

    static constraints = {
        pagoManipuleo()
        lote(blank: false)
        recepcionId(display: false, nullable: true)
        fechaDeRecepcion(blank: false)
        pesoBruto(blank: false, min: 0.0)
        tipoDeMaterial(blank: false)
        pesadaVaciada(blank: false)
        carguioMaquina(blank: false)
        embolsadaArrumada(blank: false)
        soloComuneada(blank: false)
        soloVaciada(blank: false)
        soloPesada(blank: false)
        soloEmbolsada(blank: false)
        costoDeManipuleo nullable: false
    }

    String toString(){
        "Lote: ${lote} Fecha De Recepcion: ${fechaDeRecepcion} Peso Bruto: ${pesoBruto} Tipo De Material: ${tipoDeMaterial} Costo De Manipuleo: ${costoDeManipuleo}"
    }
}
