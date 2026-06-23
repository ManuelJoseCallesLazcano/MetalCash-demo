package org.socymet.recepcion

import org.socymet.cotizaciones.CotizacionDeDolar
import org.socymet.seguridad.SecUser

import java.text.DecimalFormat

class RecepcionDeEstano extends Recepcion {
    static searchable = true

    Integer loteEstano //e.g.: GENERADO: 1, PARA MOSTRAR: SB000001
    Integer codigoDepositoEstano = 0

    String estadoDelLote

    //costos de laboratorio
    String detalleLaboratorio1='-'
    BigDecimal costoLaboratorio1=0
    String detalleLaboratorio2='-'
    BigDecimal costoLaboratorio2=0
    String detalleLaboratorio3='-'
    BigDecimal costoLaboratorio3=0
    String detalleLaboratorio4='-'
    BigDecimal costoLaboratorio4=0
    BigDecimal totalCostoLaboratorio=0

    transient springSecurityService

    static constraints = {
        loteEstano(nullable: true)
        codigoDepositoEstano()
        fechaDeRecepcion(blank: false)
        cliente(blank: false)
        empresa(blank: false)
        chofer(blank: false)
        automovil(blank: false)
        //informacion de cantidades y otros
        tipoDeMaterial inList: ["CONCENTRADO","BROZA"]
        cantidadDeSacos(blank: false)
        pesoBruto(blank: false)
        pesoTara(blank: false)
        costoDeTransporte(blank: false)
        estadoDelLote(inList: ["NO LIQUIDADO","LIQUIDADO","Quemado","Provisional","Baja"],blank: false,nullable: false)
        estadoAnticipo blank:  true, nullable: true
        observaciones()
        //referencias que no tienen que ser mostradas en vistas
        transportePagado(blank: true, nullable: true)
        manipuleoPagado(blank: true, nullable: true)
        usuario(nullable: true)

        cotizacionDeDolar(nullable: true)
        /*alicuota(nullable: true)
        cotizacionDiariaDeMinerales(nullable: true)
        cotizacionQuincenalDeMinerales(nullable: true)*/
        alicuota(nullable: false)
        cotizacionDiariaDeMinerales(nullable: false)
        cotizacionQuincenalDeMinerales(nullable: false)

        //costos de laboratorio
        detalleLaboratorio1(blank:false, nullable: false)
        costoLaboratorio1(blank:false, min: 0.0, nullable: false)
        detalleLaboratorio2(blank:true, nullable: true)
        costoLaboratorio2(blank:true, min: 0.0, nullable: true)
        detalleLaboratorio3(blank:true, nullable: true)
        costoLaboratorio3(blank:true, min: 0.0, nullable: true)
        detalleLaboratorio4(blank:true, nullable: true)
        costoLaboratorio4(blank:true, min: 0.0, nullable: true)
        totalCostoLaboratorio(blank:false, min: 0.0, nullable: false)
    }

    def beforeInsert = {
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser

        System.out.println("*********** Recepcion: DEPOSITO antes de deteccion:${deposito.toString()}")
        this.deposito = usuarioActual.deposito
        System.out.println("*********** Recepcion: DEPOSITO despues de deteccion:${deposito.toString()}")

        def c = RecepcionDeEstano.createCriteria()
        def results = c {
            projections {
                max('loteEstano')
            }}
        def maxLote = results.get(0)?: 0
        this.loteEstano = maxLote + 1

        this.codigoDepositoEstano = RecepcionDeComplejo.countByDeposito(this.deposito) + 1

        this.estadoAnticipo = "SIN ANTICIPO"
        this.transportePagado = "NO"
        this.manipuleoPagado = "NO"

        this.usuario = usuarioActual
        this.cotizacionDeDolar = CotizacionDeDolar.findByActivo(1)
    }

    def afterInsert = {
    }

    String toString(){
        def decimalFormat = new DecimalFormat("000000")
        //"${decimalFormat.format(loteComplejo)}"
        def s = ""
        if (loteEstano)
            return "${deposito?.codigoDeposito}-SN${decimalFormat.format(loteEstano)}"

        return s
    }
}
