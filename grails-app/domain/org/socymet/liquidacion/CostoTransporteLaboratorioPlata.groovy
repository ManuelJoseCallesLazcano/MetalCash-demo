package org.socymet.liquidacion

import org.socymet.recepcion.RecepcionDePlata

class CostoTransporteLaboratorioPlata {
    String lote

    Integer recepcionId
    String nombreCliente
    String nombreEmpresa
    String fechaDeRecepcion
    BigDecimal pesoBruto
    //costo de transporte
    BigDecimal costoDeTransporteAnterior
    BigDecimal costoDeTransporteNuevo
    //costo de laboratorio
    String detalleLaboratorio1Anterior
    BigDecimal costoLaboratorio1Anterior
    String detalleLaboratorio2Anterior
    BigDecimal costoLaboratorio2Anterior
    String detalleLaboratorio3Anterior
    BigDecimal costoLaboratorio3Anterior
    String detalleLaboratorio4Anterior
    BigDecimal costoLaboratorio4Anterior
    BigDecimal totalCostoLaboratorioAnterior

    String detalleLaboratorio1Nuevo
    BigDecimal costoLaboratorio1Nuevo
    String detalleLaboratorio2Nuevo
    BigDecimal costoLaboratorio2Nuevo
    String detalleLaboratorio3Nuevo
    BigDecimal costoLaboratorio3Nuevo
    String detalleLaboratorio4Nuevo
    BigDecimal costoLaboratorio4Nuevo
    BigDecimal totalCostoLaboratorioNuevo

    String observaciones

    static constraints = {
        lote(blank: false)
        recepcionId(display: false, nullable: true)
        nombreCliente(blank: false)
        nombreEmpresa(blank: false)
        fechaDeRecepcion(blank: false)
        pesoBruto(min: 0.0, blank: false)

        costoDeTransporteAnterior(blank: false)
        costoDeTransporteNuevo(nullable: true)

        detalleLaboratorio1Anterior blank:true, nullable: true
        costoLaboratorio1Anterior blank:true, min: 0.0, nullable: true
        detalleLaboratorio2Anterior blank:true, nullable: true
        costoLaboratorio2Anterior blank:true, min: 0.0, nullable: true
        detalleLaboratorio3Anterior blank:true, nullable: true
        costoLaboratorio3Anterior blank:true, min: 0.0, nullable: true
        detalleLaboratorio4Anterior blank:true, nullable: true
        costoLaboratorio4Anterior blank:true, min: 0.0, nullable: true
        totalCostoLaboratorioAnterior blank:true, min: 0.0, nullable: true

        detalleLaboratorio1Nuevo blank:true, nullable: true
        costoLaboratorio1Nuevo blank:true, min: 0.0, nullable: true
        detalleLaboratorio2Nuevo blank:true, nullable: true
        costoLaboratorio2Nuevo blank:true, min: 0.0, nullable: true
        detalleLaboratorio3Nuevo blank:true, nullable: true
        costoLaboratorio3Nuevo blank:true, min: 0.0, nullable: true
        detalleLaboratorio4Nuevo blank:true, nullable: true
        costoLaboratorio4Nuevo blank:true, min: 0.0, nullable: true
        totalCostoLaboratorioNuevo blank:true, min: 0.0, nullable: true

        observaciones blank: true
    }

    def afterInsert = {
        def recepcionDePlata = RecepcionDePlata.get(recepcionId)
        def liquidacionDePlata = LiquidacionDePlata.findByRecepcionDePlata(recepcionDePlata)
        //actualizando informacion en recepcion
        if (costoDeTransporteNuevo&&recepcionDePlata){
            recepcionDePlata.costoDeTransporte=costoDeTransporteNuevo
        }

        if (costoLaboratorio1Nuevo&&recepcionDePlata){
            recepcionDePlata.detalleLaboratorio1=detalleLaboratorio1Nuevo
            recepcionDePlata.costoLaboratorio1=costoLaboratorio1Nuevo
        }

        if (costoLaboratorio2Nuevo&&recepcionDePlata){
            recepcionDePlata.detalleLaboratorio2=detalleLaboratorio2Nuevo
            recepcionDePlata.costoLaboratorio2=costoLaboratorio2Nuevo
        }

        if (costoLaboratorio3Nuevo&&recepcionDePlata){
            recepcionDePlata.detalleLaboratorio3=detalleLaboratorio3Nuevo
            recepcionDePlata.costoLaboratorio3=costoLaboratorio3Nuevo
        }

        if (costoLaboratorio4Nuevo&&recepcionDePlata){
            recepcionDePlata.detalleLaboratorio4=detalleLaboratorio4Nuevo
            recepcionDePlata.costoLaboratorio4=costoLaboratorio4Nuevo
        }

        if (totalCostoLaboratorioNuevo&&recepcionDePlata){
            recepcionDePlata.totalCostoLaboratorio=totalCostoLaboratorioNuevo
        }
        //actualizando informacion en liquidacion
        if (costoLaboratorio1Nuevo&&liquidacionDePlata){
            liquidacionDePlata.detalleLaboratorio1=detalleLaboratorio1Nuevo
            liquidacionDePlata.costoLaboratorio1=costoLaboratorio1Nuevo
        }

        if (costoLaboratorio2Nuevo&&liquidacionDePlata){
            liquidacionDePlata.detalleLaboratorio2=detalleLaboratorio2Nuevo
            liquidacionDePlata.costoLaboratorio2=costoLaboratorio2Nuevo
        }

        if (costoLaboratorio3Nuevo&&liquidacionDePlata){
            liquidacionDePlata.detalleLaboratorio3=detalleLaboratorio3Nuevo
            liquidacionDePlata.costoLaboratorio3=costoLaboratorio3Nuevo
        }

        if (costoLaboratorio4Nuevo&&liquidacionDePlata){
            liquidacionDePlata.detalleLaboratorio4=detalleLaboratorio4Nuevo
            liquidacionDePlata.costoLaboratorio4=costoLaboratorio4Nuevo
        }

        if (totalCostoLaboratorioNuevo&&liquidacionDePlata){
            liquidacionDePlata.totalCostoLaboratorio=totalCostoLaboratorioNuevo
        }

        if (recepcionDePlata)
            recepcionDePlata.save()
        if (liquidacionDePlata)
            liquidacionDePlata.save()
    }
}
