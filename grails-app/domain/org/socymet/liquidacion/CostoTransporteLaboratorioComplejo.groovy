package org.socymet.liquidacion

import org.socymet.recepcion.RecepcionDeComplejo

class CostoTransporteLaboratorioComplejo {
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
        def recepcionDeComplejo = RecepcionDeComplejo.get(recepcionId)
        def liquidacionDeComplejo = LiquidacionDeComplejo.findByRecepcionDeComplejo(recepcionDeComplejo)
        //actualizando informacion en recepcion
        if (costoDeTransporteNuevo&&recepcionDeComplejo){
            recepcionDeComplejo.costoDeTransporte=costoDeTransporteNuevo
        }

        if (costoLaboratorio1Nuevo&&recepcionDeComplejo){
            recepcionDeComplejo.detalleLaboratorio1=detalleLaboratorio1Nuevo
            recepcionDeComplejo.costoLaboratorio1=costoLaboratorio1Nuevo
        }

        if (costoLaboratorio2Nuevo&&recepcionDeComplejo){
            recepcionDeComplejo.detalleLaboratorio2=detalleLaboratorio2Nuevo
            recepcionDeComplejo.costoLaboratorio2=costoLaboratorio2Nuevo
        }

        if (costoLaboratorio3Nuevo&&recepcionDeComplejo){
            recepcionDeComplejo.detalleLaboratorio3=detalleLaboratorio3Nuevo
            recepcionDeComplejo.costoLaboratorio3=costoLaboratorio3Nuevo
        }

        if (costoLaboratorio4Nuevo&&recepcionDeComplejo){
            recepcionDeComplejo.detalleLaboratorio4=detalleLaboratorio4Nuevo
            recepcionDeComplejo.costoLaboratorio4=costoLaboratorio4Nuevo
        }

        if (totalCostoLaboratorioNuevo&&recepcionDeComplejo){
            recepcionDeComplejo.totalCostoLaboratorio=totalCostoLaboratorioNuevo
        }
        //actualizando informacion en liquidacion
        if (costoLaboratorio1Nuevo&&liquidacionDeComplejo){
            liquidacionDeComplejo.detalleLaboratorio1=detalleLaboratorio1Nuevo
            liquidacionDeComplejo.costoLaboratorio1=costoLaboratorio1Nuevo
        }

        if (costoLaboratorio2Nuevo&&liquidacionDeComplejo){
            liquidacionDeComplejo.detalleLaboratorio2=detalleLaboratorio2Nuevo
            liquidacionDeComplejo.costoLaboratorio2=costoLaboratorio2Nuevo
        }

        if (costoLaboratorio3Nuevo&&liquidacionDeComplejo){
            liquidacionDeComplejo.detalleLaboratorio3=detalleLaboratorio3Nuevo
            liquidacionDeComplejo.costoLaboratorio3=costoLaboratorio3Nuevo
        }

        if (costoLaboratorio4Nuevo&&liquidacionDeComplejo){
            liquidacionDeComplejo.detalleLaboratorio4=detalleLaboratorio4Nuevo
            liquidacionDeComplejo.costoLaboratorio4=costoLaboratorio4Nuevo
        }

        if (totalCostoLaboratorioNuevo&&liquidacionDeComplejo){
            liquidacionDeComplejo.totalCostoLaboratorio=totalCostoLaboratorioNuevo
        }

        if (recepcionDeComplejo)
            recepcionDeComplejo.save()
        if (liquidacionDeComplejo)
            liquidacionDeComplejo.save()
    }
}
