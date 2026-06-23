package org.socymet.cancelacion

import org.socymet.liquidacion.LiquidacionDeCobrePlata
import org.socymet.liquidacion.LiquidacionDeComplejo
import org.socymet.liquidacion.LiquidacionDePlomoPlata
import org.socymet.liquidacion.LiquidacionDeZincPlata
import org.socymet.seguridad.SecUser

class Cancelacion {
    String lote
    Integer liquidacionId
    String nombreCliente
    String nombreEmpresa
    String fechaDeRecepcion
    String fechaDeLiquidacion
    BigDecimal totalLiquidoPagable

    Date fechaDeCancelacion
    String observaciones

    SecUser usuario

    transient springSecurityService

    static constraints = {
        lote(blank: false)
        liquidacionId(display: false, nullable: true)
        nombreCliente(blank: false)
        nombreEmpresa(blank: false)
        fechaDeRecepcion(blank: false)
        fechaDeLiquidacion(blank: false)
        totalLiquidoPagable(blank: false, min: 0.0)
        fechaDeCancelacion(blank: false)
        observaciones(blank: true, nullable: true)
        usuario(display: false, nullable: true)
    }

    def beforeInsert = {
        this.usuario = springSecurityService.getCurrentUser()
    }

    def afterInsert = {
//        def liquidacionEstano = LiquidacionDeEstano.get(liquidacionId)
//        def liquidacionPlata = LiquidacionDePlata.get(liquidacionId)
//        def liquidacionWolfran = LiquidacionDeWolfran.get(liquidacionId)
//        def liquidacionAntimonio = LiquidacionDeAntimonio.get(liquidacionId)
        def liquidacionComplejo = LiquidacionDeComplejo.get(liquidacionId)
        def liquidacionDePlomoPlata = LiquidacionDePlomoPlata.get(liquidacionId)
        def liquidacionDeZincPlata = LiquidacionDeZincPlata.get(liquidacionId)
        def liquidacionDeCobrePlata = LiquidacionDeCobrePlata.get(liquidacionId)

        Cancelacion.withNewTransaction {
//            if(liquidacionEstano){
//                liquidacionEstano.fechaDeCancelacion=fechaDeCancelacion
//                liquidacionEstano.save()
//            }
//            if(liquidacionPlata){
//                liquidacionPlata.fechaDeCancelacion=fechaDeCancelacion
//                liquidacionPlata.save()
//            }
//            if(liquidacionWolfran){
//                liquidacionWolfran.fechaDeCancelacion=fechaDeCancelacion
//                liquidacionWolfran.save()
//            }
//            if(liquidacionAntimonio){
//                liquidacionAntimonio.fechaDeCancelacion=fechaDeCancelacion
//                liquidacionAntimonio.save()
//            }
            if(liquidacionComplejo){
                liquidacionComplejo.fechaDeCancelacion=fechaDeCancelacion
                liquidacionComplejo.save()
            }

            if(liquidacionDePlomoPlata){
                liquidacionDePlomoPlata.fechaDeCancelacion=fechaDeCancelacion
                liquidacionDePlomoPlata.save()
            }

            if(liquidacionDeZincPlata){
                System.err.println("******************* fechaDeCancelacion: $fechaDeCancelacion")
                liquidacionDeZincPlata.fechaDeCancelacion=fechaDeCancelacion
                liquidacionDeZincPlata.save()
            }

            if(liquidacionDeCobrePlata){
                liquidacionDeCobrePlata.fechaDeCancelacion=fechaDeCancelacion
                liquidacionDeCobrePlata.save()
            }
        }
    }

    def beforeUpdate = {
        this.usuario = springSecurityService.getCurrentUser()
    }
}
