package org.socymet.liquidacion

import org.socymet.recepcion.RecepcionDeEstano

class CostoTransporteLaboratorioEstano {
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
        def recepcionDeEstano = RecepcionDeEstano.get(recepcionId)
        def liquidacionDeEstano = LiquidacionDeEstano.findByRecepcionDeEstano(recepcionDeEstano)
        //actualizando informacion en recepcion
        if (costoDeTransporteNuevo&&recepcionDeEstano){
            recepcionDeEstano.costoDeTransporte=costoDeTransporteNuevo
        }
        
        if (costoLaboratorio1Nuevo&&recepcionDeEstano){
            recepcionDeEstano.detalleLaboratorio1=detalleLaboratorio1Nuevo
            recepcionDeEstano.costoLaboratorio1=costoLaboratorio1Nuevo
        }

        if (costoLaboratorio2Nuevo&&recepcionDeEstano){
            recepcionDeEstano.detalleLaboratorio2=detalleLaboratorio2Nuevo
            recepcionDeEstano.costoLaboratorio2=costoLaboratorio2Nuevo
        }

        if (costoLaboratorio3Nuevo&&recepcionDeEstano){
            recepcionDeEstano.detalleLaboratorio3=detalleLaboratorio3Nuevo
            recepcionDeEstano.costoLaboratorio3=costoLaboratorio3Nuevo
        }

        if (costoLaboratorio4Nuevo&&recepcionDeEstano){
            recepcionDeEstano.detalleLaboratorio4=detalleLaboratorio4Nuevo
            recepcionDeEstano.costoLaboratorio4=costoLaboratorio4Nuevo
        }

        if (totalCostoLaboratorioNuevo&&recepcionDeEstano){
            recepcionDeEstano.totalCostoLaboratorio=totalCostoLaboratorioNuevo
        }
        //actualizando informacion en liquidacion
        if (costoLaboratorio1Nuevo&&liquidacionDeEstano){
            liquidacionDeEstano.detalleLaboratorio1=detalleLaboratorio1Nuevo
            liquidacionDeEstano.costoLaboratorio1=costoLaboratorio1Nuevo
        }

        if (costoLaboratorio2Nuevo&&liquidacionDeEstano){
            liquidacionDeEstano.detalleLaboratorio2=detalleLaboratorio2Nuevo
            liquidacionDeEstano.costoLaboratorio2=costoLaboratorio2Nuevo
        }

        if (costoLaboratorio3Nuevo&&liquidacionDeEstano){
            liquidacionDeEstano.detalleLaboratorio3=detalleLaboratorio3Nuevo
            liquidacionDeEstano.costoLaboratorio3=costoLaboratorio3Nuevo
        }

        if (costoLaboratorio4Nuevo&&liquidacionDeEstano){
            liquidacionDeEstano.detalleLaboratorio4=detalleLaboratorio4Nuevo
            liquidacionDeEstano.costoLaboratorio4=costoLaboratorio4Nuevo
        }

        if (totalCostoLaboratorioNuevo&&liquidacionDeEstano){
            liquidacionDeEstano.totalCostoLaboratorio=totalCostoLaboratorioNuevo
        }
        
        if (recepcionDeEstano)
            recepcionDeEstano.save()
        if (liquidacionDeEstano)
            liquidacionDeEstano.save()
    }
}
