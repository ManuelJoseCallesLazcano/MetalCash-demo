package org.socymet.recepcion

import org.socymet.cotizaciones.CotizacionDeDolar

import java.text.DecimalFormat

class RecepcionDePlomoPlata extends Recepcion{
    static searchable = true

    Integer lotePlomoPlata //e.g.: GENERADO: 1, PARA MOSTRAR: SB000001

    String estadoDelLote

    //costos de laboratorio
    String detalleLaboratorio1
    BigDecimal costoLaboratorio1
    String detalleLaboratorio2
    BigDecimal costoLaboratorio2
    String detalleLaboratorio3
    BigDecimal costoLaboratorio3
    String detalleLaboratorio4
    BigDecimal costoLaboratorio4
    BigDecimal totalCostoLaboratorio

    transient springSecurityService

    static constraints = {
        deposito nullable: false
        lotePlomoPlata(nullable: true)
        fechaDeRecepcion(blank: false)
        cliente(blank: false)
        empresa(blank: false)
        chofer(blank: false)
        automovil(blank: false)
        //informacion de cantidades y otros
        cantidadDeSacos(blank: false)
        pesoBruto(blank: false)
        pesoTara(blank: false)
        costoDeTransporte(blank: false)
        estadoDelLote(inList: ["NO LIQUIDADO","LIQUIDADO","Quemado","Provisional","Baja"],blank: false,nullable: false)
        observaciones()
        //referencias que no tienen que ser mostradas en vistas
        transportePagado(blank: true, nullable: true)
        usuario(nullable: true)

        cotizacionDeDolar(nullable: true)
        /*alicuota(nullable: true)
        cotizacionDiariaDeMinerales(nullable: true)
        cotizacionQuincenalDeMinerales(nullable: true)*/
        alicuota(nullable: false)
        cotizacionDiariaDeMinerales(nullable: false)
        cotizacionQuincenalDeMinerales(nullable: false)

        //costos de laboratorio
        detalleLaboratorio1(blank:true, nullable: true)
        costoLaboratorio1(blank:true, min: 0.0, nullable: true)
        detalleLaboratorio2(blank:true, nullable: true)
        costoLaboratorio2(blank:true, min: 0.0, nullable: true)
        detalleLaboratorio3(blank:true, nullable: true)
        costoLaboratorio3(blank:true, min: 0.0, nullable: true)
        detalleLaboratorio4(blank:true, nullable: true)
        costoLaboratorio4(blank:true, min: 0.0, nullable: true)
        totalCostoLaboratorio(blank:true, min: 0.0, nullable: true)
    }

    def beforeInsert = {
        def c = RecepcionDePlomoPlata.createCriteria()
        def results = c {
            projections {
                max('lotePlomoPlata')
            }}
        def maxLote = results.get(0)?: 0
        this.lotePlomoPlata = maxLote + 1

        this.transportePagado = "NO"

        this.usuario = springSecurityService.getCurrentUser()
        //cotizaciones activas en el momento de la recepcion
        //this.alicuota = Alicuota.findByActivo(1)
        this.cotizacionDeDolar = CotizacionDeDolar.findByActivo(1)
        //this.cotizacionDiariaDeMinerales = CotizacionDiariaDeMinerales.findByActivo(1)
        //this.cotizacionQuincenalDeMinerales = CotizacionQuincenalDeMinerales.findByActivo(1)

        //solucion horrible para el problema de totalCostoLaboratorio; de alguna forma cuando se ingresa
        //por ejemplo Bs 30 se almacena Bs 3, muy probablemente por un error en la manipulacion del
        //sistema
        if (this.totalCostoLaboratorio>0&&this.totalCostoLaboratorio<10)
            this.totalCostoLaboratorio=this.totalCostoLaboratorio*10
    }

    static afterInsert = {
    }

    String toString(){
        def decimalFormat = new DecimalFormat("000000")
        "${decimalFormat.format(lotePlomoPlata)}"
    }
}
