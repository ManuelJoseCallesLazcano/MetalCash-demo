package org.socymet.recepcion

import grails.converters.JSON
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject
import org.socymet.anticipos.AnticipoContraEntrega
import org.socymet.anticipos.AnticipoDetalle
import org.socymet.anticipos.EstadoDeCuenta
import org.socymet.calidad.ControlCalidadOro
import org.socymet.cotizaciones.TablaPreciosOro
import org.socymet.cotizaciones.TablaPreciosOroController
import org.socymet.proveedor.Cliente
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.bonos.BonoCalidadController
import org.socymet.proveedor.bonos.BonoIncentivoController
import org.socymet.seguridad.SecUser
import org.springframework.security.access.annotation.Secured

import java.text.DecimalFormat

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_RECEPCION','ROLE_CAJA'])
class RecepcionDeOroController {
    
    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond RecepcionDeOro.list(params), model:[recepcionDeOroInstanceCount: RecepcionDeOro.count()]
    }

    def show(RecepcionDeOro recepcionDeOroInstance) {
        respond recepcionDeOroInstance
    }

    def create() {
        respond new RecepcionDeOro(params)
    }

    @Transactional
    def save(RecepcionDeOro recepcionDeOroInstance) {
        if (recepcionDeOroInstance == null) {
            notFound()
            return
        }

        if (recepcionDeOroInstance.hasErrors()) {
            respond recepcionDeOroInstance.errors, view:'create'
            return
        }

        recepcionDeOroInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'recepcionDeOro.label', default: 'RecepcionDeOro'), recepcionDeOroInstance.id])
                redirect recepcionDeOroInstance
            }
            '*' { respond recepcionDeOroInstance, [status: CREATED] }
        }
    }

    def edit(RecepcionDeOro recepcionDeOroInstance) {
        respond recepcionDeOroInstance
    }

    @Transactional
    def update(RecepcionDeOro recepcionDeOroInstance) {
        if (recepcionDeOroInstance == null) {
            notFound()
            return
        }

        if (recepcionDeOroInstance.hasErrors()) {
            respond recepcionDeOroInstance.errors, view:'edit'
            return
        }

        recepcionDeOroInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'RecepcionDeOro.label', default: 'RecepcionDeOro'), recepcionDeOroInstance.id])
                redirect recepcionDeOroInstance
            }
            '*'{ respond recepcionDeOroInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(RecepcionDeOro recepcionDeOroInstance) {

        if (recepcionDeOroInstance == null) {
            notFound()
            return
        }

        recepcionDeOroInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'RecepcionDeOro.label', default: 'RecepcionDeOro'), recepcionDeOroInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDeOro.label', default: 'RecepcionDeOro'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def numeroDeLote = {
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        def decimalFormat = new DecimalFormat("000000")
        def deposito = Deposito.get(params.depositoId)

        def results = null
        def maximum = null
        def loteOro = 0
        //loteOro
        def cm = RecepcionDeOro.createCriteria()
        results = cm.list {
            like("deposito",deposito)
            projections {
                max('loteOro')
            }}
        maximum = results.get(0)?: 0
        loteOro = maximum + 1

        if (loteOro>0)
            render([
                depositoActual: deposito.toString(),
                lote: "AU"+decimalFormat.format(loteOro)
            ] as JSON)
    }

    def recepcionesCalidadOroJSON() {
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        def deposito = usuarioActual.deposito
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesOro = RecepcionDeOro.findAllByLoteOroAndEstadoDelLote(lote,"NO LIQUIDADO")
        def recepcionesList = []
        recepcionesOro.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("recepcionId", recepcion.id)
            mapaRecepcion.put("label", recepcion.toString())
            mapaRecepcion.put("value", recepcion.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("empresaId", recepcion.empresa.id)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            mapaRecepcion.put("estadoDelLote", recepcion.estadoDelLote)
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def recepcionesOroJSON() {
        def lote = Integer.parseInt(params.term.toString())
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        def recepcionesOro = RecepcionDeOro.findAllByLoteOroAndEstadoDelLote(lote,"NO LIQUIDADO")
        def controlCalidadOro = null
        def recepcionesList = []
        //variables para calculos
        def cotizacionQuincenalOro=0
        def alicuotaOro=0
        def tipoDeCambioComercial=0
        def tipoDeCambioOficial=0
        def pesoBruto=0
        def porcentajeMermaPromexbol=1
        def porcentajeHumedadPromexbol=1
        def porcentajeOroPromexbol=1
        def controlCalidadId=0
        def tipoDeMineral="ORO"

        def totalAnticiposContraEntrega=0
        def anticipoPorPagar=0

        def notificacionAnticipo=""

        recepcionesOro.each { recepcion ->
            def mapaRecepcion = [:]
            controlCalidadOro = ControlCalidadOro.findByRecepcionDeOro(recepcion)

            cotizacionQuincenalOro=recepcion.cotizacionQuincenalDeMinerales.oro
            alicuotaOro=recepcion.alicuota.oro
            tipoDeCambioComercial=recepcion.cotizacionDeDolar.tipoDeCambioComercial
            tipoDeCambioOficial=recepcion.cotizacionDeDolar.tipoDeCambioOficial
            pesoBruto=recepcion.pesoBruto

            if (controlCalidadOro){
                porcentajeMermaPromexbol = controlCalidadOro.porcentajeMermaPromexbol
                porcentajeHumedadPromexbol = controlCalidadOro.porcentajeHumedadPromexbol
                porcentajeOroPromexbol = controlCalidadOro.porcentajeOroPromexbol
                controlCalidadId = controlCalidadOro.id

                //buscar si tiene anticipos
                def anticipoDetalle = AnticipoDetalle.findByRecepcionId(recepcion.id)
                if(anticipoDetalle){
                    def anticipoDetalles = AnticipoDetalle.findAllByAnticipo(anticipoDetalle.anticipo)
                    /*ATENCION: El campo anticipoDetallesPagados servira para controlar cuando se genere un saldo negativo
                    * bajo la idea de que si solo queda un lote para pagar el anticipo y no lo cubre debera generarse el
                    * saldo negativo y no el saldo 0 (liquido pagable=0) como cuando se paga un anticipo con el total del
                    * valor del lote. Tendria que calcularse la diferencia de tamaños entre anticipoDetalles y
                    * anticipoDetallesPagados, si la diferencia es mayor a 1 todavia hay chance de poder pagarse
                    * el anticipo.*/
                    def anticipoDetallesPagados = AnticipoDetalle.findAllByAnticipoAndEstadoAnticipo(anticipoDetalle.anticipo,"PAGADO")
                    def anticipo = anticipoDetalle.anticipo
                    def lotesAsignados = ""
                    anticipoDetalles.each {
                        lotesAsignados+="${it.lote} (${it.estadoAnticipo})\n"
                    }

                    anticipoPorPagar = anticipo.totalPorPagar

                    notificacionAnticipo = "EL LOTE ${anticipoDetalle.lote} ESTA ASIGNADO AL ANTICIPO CON IMPORTE INICIAL DE Bs${anticipoDetalle.anticipo.totalAnticipos}\nCON UN TOTAL POR PAGAR DE Bs${anticipoDetalle.anticipo.totalPorPagar}\nFORMADO POR LOS LOTES:\n${lotesAsignados}"
                }
            }else
                System.out.println("****** no existe informacion en control de calidad")

            mapaRecepcion.put("recepcionId", recepcion.id)
            mapaRecepcion.put("label", recepcion.toString())
            mapaRecepcion.put("value", recepcion.toString())
            mapaRecepcion.put("nombreDeposito", recepcion.deposito.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("empresaId", recepcion.empresa.id)
            mapaRecepcion.put("depositoId", recepcion.deposito.id)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("retenciones", retencionesParaLiquidacion(recepcion.empresa.retenciones))
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            mapaRecepcion.put("tipoDeMineral", tipoDeMineral)
            mapaRecepcion.put("estadoDelLote", recepcion.estadoDelLote)
            mapaRecepcion.put("documentacionCompleta", recepcion.documentacionCompleta)
            mapaRecepcion.put("cotizacionDiariaDeOro", recepcion.cotizacionDiariaDeMinerales.oro)
            mapaRecepcion.put("cotizacionQuincenalDeOro", recepcion.cotizacionQuincenalDeMinerales.oro)
            mapaRecepcion.put("alicuotaDeOro", alicuotaOro)
            mapaRecepcion.put("tipoDeCambioOficial", recepcion.cotizacionDeDolar.tipoDeCambioOficial)
            mapaRecepcion.put("tipoDeCambioComercial", recepcion.cotizacionDeDolar.tipoDeCambioComercial)
            //datos de control de calidad
            mapaRecepcion.put("porcentajeMermaPromexbol", porcentajeMermaPromexbol)
            mapaRecepcion.put("porcentajeHumedadPromexbol", porcentajeHumedadPromexbol)
            mapaRecepcion.put("porcentajeOroPromexbol", porcentajeOroPromexbol)
            mapaRecepcion.put("controlCalidadId", controlCalidadId)

            mapaRecepcion.put("anticipoPorPagar", anticipoPorPagar)
            mapaRecepcion.put("notificacionAnticipo", notificacionAnticipo)

            mapaRecepcion.put("detalleLaboratorio1", recepcion.detalleLaboratorio1)
            mapaRecepcion.put("costoLaboratorio1", recepcion.costoLaboratorio1)
            mapaRecepcion.put("detalleLaboratorio2", recepcion.detalleLaboratorio2)
            mapaRecepcion.put("costoLaboratorio2", recepcion.costoLaboratorio2)
            mapaRecepcion.put("detalleLaboratorio3", recepcion.detalleLaboratorio3)
            mapaRecepcion.put("costoLaboratorio3", recepcion.costoLaboratorio3)
            mapaRecepcion.put("detalleLaboratorio4", recepcion.detalleLaboratorio4)
            mapaRecepcion.put("costoLaboratorio4", recepcion.costoLaboratorio4)
            mapaRecepcion.put("totalCostoLaboratorio", recepcion.totalCostoLaboratorio)
            //mapaRecepcion.put("totalAnticiposContraEntrega", getTotalAnticiposContraEntrega(recepcion.id))
            mapaRecepcion.put("totalAnticiposContraFuturaEntrega", getTotalAnticiposContraFuturaEntrega(recepcion.cliente.id))
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def recepcionesOroValoracionJSON() {
        def recepcion = RecepcionDeOro.get(params.recepcionDeOroId.toString().toLong())
        def controlCalidadOro = null
        def tablaPreciosOro = new TablaPreciosOroController()
        def bonosPorCalidad = new BonoCalidadController()
        def bonosPorIncentivo = new BonoIncentivoController()
        def recepcionesList = []
        //variables para calculos
        def cotizacionDiariaOro=0
        def cotizacionQuincenalOro=0
        def alicuotaOro=0
        def tipoDeCambioComercial=0
        def tipoDeCambioOficial=0
        def pesoBruto=0
        def cantidadSacos=0
        def merma=0
        def humedad=1
        def porcentajeOro=1
        def tipoDeMineral="ORO"
        def pesoBrutoSinMerma=0
        def kilosNetosHumedos=0
        def kilosNetosSecos=0
        def kilosFinosOro=0
        def onzasTroyOro=0
        def valorOficialBrutoOro=0
        def valorOficialBrutoOroBs=0

        def valorLMEOro=0
        def valorLMEOroBs=0
        def valorLMEPlomoBs=0
        def valorLMEPlataBs=0
        def valorLME=0

        def valorOficialBruto=0
        def valorToneladaOro=0
        def valorTonelada=0

        def valorToneladaTabla=0
        def valorToneladaPrecioPorLME=0
        def valorToneladaTerminos=0
        def pLMEtabla=0
        def pLMEprecioLME=0
        def pLMEterminos=0
        def vonTabla=0
        def vonTerminos=0
        def margen=0

        def regaliaMinera=0
        def valorNetoMineral=0
        def valorNetoMineralEnBolivianos=0
        def bonoCalidad=0
        def bonoIncentivo=0
        def valorDeCompra=0
        def totalAnticiposContraEntrega=0
        def anticipoPorPagar=0
        def bonoTransporteKilosNetosSecosTotal=0

        def notificacionAnticipo=""
        def notificacionValoresTonelada=""
        def formatter = new DecimalFormat("###.00")

        def mapaRecepcion = [:]
        //controlCalidadOro = ControlCalidadOro.findByRecepcionDeOro(recepcion)

        cotizacionDiariaOro=recepcion.cotizacionDiariaDeMinerales.oro
        cotizacionQuincenalOro=recepcion.cotizacionQuincenalDeMinerales.oro
        alicuotaOro=recepcion.alicuota.oro
        tipoDeCambioComercial=recepcion.cotizacionDeDolar.tipoDeCambioComercial
        tipoDeCambioOficial=recepcion.cotizacionDeDolar.tipoDeCambioOficial
        pesoBruto=recepcion.pesoBruto

//        merma = params.porcentajeMermaFinal.toString().toBigDecimal()
        merma = 0
        humedad = params.porcentajeHumedadFinal.toString().toBigDecimal()
        porcentajeOro = params.porcentajeOroFinal.toString().toBigDecimal()
        
        kilosNetosHumedos=pesoBruto-pesoBruto*merma/100
        kilosNetosSecos=kilosNetosHumedos-kilosNetosHumedos*humedad/100

        cantidadSacos=recepcion.cantidadDeSacos

        kilosFinosOro=(kilosNetosSecos*porcentajeOro/100).floatValue().round(2)
        onzasTroyOro = (kilosFinosOro*32.15073).floatValue().round(2)

        valorOficialBrutoOro = (onzasTroyOro*cotizacionQuincenalOro).floatValue().round(2)
        valorOficialBrutoOroBs = (valorOficialBrutoOro*tipoDeCambioOficial).floatValue().round(2)
        valorOficialBruto = (valorOficialBrutoOroBs).floatValue().round(2)

        valorLMEOro = (onzasTroyOro*cotizacionDiariaOro).floatValue().round(2)
        valorLMEOroBs = (valorLMEOro*tipoDeCambioComercial).floatValue().round(2)
        valorLME = (valorLMEOroBs+valorLMEPlomoBs+valorLMEPlataBs).floatValue().round(2)

        //determinacion del valor por tonelada
        //valor por tabla
        valorToneladaOro = tablaPreciosOro.getValorPorTonelada(params.tablaOroId.toString().toLong(),porcentajeOro)
        valorToneladaTabla = (valorToneladaOro + margen).floatValue().round(2)
        valorTonelada = valorToneladaTabla
        regaliaMinera = (valorOficialBrutoOroBs*alicuotaOro/100).floatValue().round(2)
        valorNetoMineral = (valorTonelada*kilosNetosSecos/1000).floatValue().round(2)
        valorNetoMineralEnBolivianos = valorNetoMineral
        def bonoLiquidacionOro=recepcion.empresa.bonoLiquidacionOro
        bonoCalidad = (valorNetoMineral*bonoLiquidacionOro/100).floatValue().round(2)
        bonoIncentivo = 0
        valorDeCompra = (valorNetoMineralEnBolivianos+bonoCalidad+bonoIncentivo).floatValue().round(2)

        pLMEtabla = (valorToneladaTabla*kilosNetosSecos*tipoDeCambioComercial/1000)*100/valorLME
        notificacionValoresTonelada = "POR TABLA:\tVPT = ${formatter.format(valorToneladaTabla)} ► %LME = ${formatter.format(pLMEtabla)}%"
//      //buscar si tiene anticipos
        def anticipoDetalle = AnticipoDetalle.findByRecepcionId(recepcion.id)
        def cantidadAnticiposPorPagar = 0
        if(anticipoDetalle){
            def anticipoDetalles = AnticipoDetalle.findAllByAnticipo(anticipoDetalle.anticipo)
            /*ATENCION: El campo anticipoDetallesPagados servira para controlar cuando se genere un saldo negativo
            * bajo la idea de que si solo queda un lote para pagar el anticipo y no lo cubre debera generarse el
            * saldo negativo y no el saldo 0 (liquido pagable=0) como cuando se paga un anticipo con el total del
            * valor del lote. Tendria que calcularse la diferencia de tamaños entre anticipoDetalles y
            * anticipoDetallesPagados, si la diferencia es mayor a 1 todavia hay chance de poder pagarse
            * el anticipo.*/
            def anticipoDetallesPagados = AnticipoDetalle.findAllByAnticipoAndEstadoAnticipo(anticipoDetalle.anticipo,"PAGADO")
            cantidadAnticiposPorPagar = anticipoDetalles.size()-anticipoDetallesPagados.size()
            def anticipo = anticipoDetalle.anticipo
            def lotesAsignados = ""
            anticipoDetalles.each {
                lotesAsignados+="${it.lote} (${it.estadoAnticipo})\n"
            }
            if(params.vista.toString().equals("create"))
                anticipoPorPagar = anticipo.totalPorPagar
            else
                anticipoPorPagar = anticipo.totalPorPagar + anticipoDetalle.anticipoPagable

            notificacionAnticipo = "EL LOTE ${anticipoDetalle.lote} ESTA ASIGNADO AL ANTICIPO CON IMPORTE INICIAL DE Bs${anticipoDetalle.anticipo.totalAnticipos}\nCON UN TOTAL POR PAGAR DE Bs${anticipoDetalle.anticipo.totalPorPagar}\nFORMADO POR LOS LOTES:\n${lotesAsignados}"
        }

        if(recepcion.empresa.aplicarBonoTransporte.equals("SI"))
            bonoTransporteKilosNetosSecosTotal=(tipoDeCambioComercial*recepcion.empresa.bonoTransporteKilosNetosSecos*pesoBruto/1000.0).floatValue().round(2)

        //DATOS PARA RETORNAR AL CLIENTE
        mapaRecepcion.put("recepcionId", recepcion.id)
        mapaRecepcion.put("label", recepcion.toString())
        mapaRecepcion.put("value", recepcion.toString())
        mapaRecepcion.put("nombreDeposito", recepcion.deposito.toString())
        mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
        mapaRecepcion.put("empresaId", recepcion.empresa.id)
        mapaRecepcion.put("depositoId", recepcion.deposito.id)
        mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
        mapaRecepcion.put("retenciones", retencionesParaLiquidacion(recepcion.empresa.retenciones))
        mapaRecepcion.put("cantidadDeSacos", cantidadSacos)
        mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
        mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
        mapaRecepcion.put("tipoDeMineral", tipoDeMineral)
        mapaRecepcion.put("estadoDelLote", recepcion.estadoDelLote)
        mapaRecepcion.put("cotizacionDiariaDeOro", recepcion.cotizacionDiariaDeMinerales.oro)
        mapaRecepcion.put("cotizacionQuincenalDeOro", recepcion.cotizacionQuincenalDeMinerales.oro)
        mapaRecepcion.put("alicuotaDeOro", alicuotaOro)
        mapaRecepcion.put("tipoDeCambioOficial", recepcion.cotizacionDeDolar.tipoDeCambioOficial)
        mapaRecepcion.put("tipoDeCambioComercial", recepcion.cotizacionDeDolar.tipoDeCambioComercial)

        //datos de control de calidad
        mapaRecepcion.put("kilosNetosHumedos", kilosNetosHumedos)
        mapaRecepcion.put("kilosNetosSecos", kilosNetosSecos)
        mapaRecepcion.put("kilosFinosOro", kilosFinosOro)
        mapaRecepcion.put("onzasTroyDeOro", onzasTroyOro)
        mapaRecepcion.put("valorOficialBrutoDeOro", valorOficialBrutoOro)
        mapaRecepcion.put("valorOficialBrutoDeOroEnBolivianos", valorOficialBrutoOroBs)
        mapaRecepcion.put("valorOficialBruto", valorOficialBruto)
        mapaRecepcion.put("valorPorTonelada", valorTonelada)
        mapaRecepcion.put("porcentajeBonificacion", recepcion.empresa.bonoLiquidacionOro)

        mapaRecepcion.put("valorToneladaTabla", valorToneladaTabla)
        mapaRecepcion.put("valorToneladaTerminos", valorToneladaTerminos)

        mapaRecepcion.put("regaliaMinera", regaliaMinera)
        mapaRecepcion.put("valorNetoMineral", valorNetoMineral)
        mapaRecepcion.put("valorNetoMineralEnBolivianos", valorNetoMineralEnBolivianos)
        mapaRecepcion.put("bonoCalidad", bonoCalidad)
        mapaRecepcion.put("bonoIncentivo", bonoIncentivo)
        mapaRecepcion.put("valorDeCompra", valorDeCompra)
        mapaRecepcion.put("anticipoPorPagar", anticipoPorPagar)
        mapaRecepcion.put("notificacionAnticipo", notificacionAnticipo)
        mapaRecepcion.put("notificacionValoresTonelada", notificacionValoresTonelada)
        mapaRecepcion.put("cantidadAnticiposPorPagar", cantidadAnticiposPorPagar)
        mapaRecepcion.put("bonoTransporteKilosNetosSecosTotal", bonoTransporteKilosNetosSecosTotal)

        mapaRecepcion.put("detalleLaboratorio1", recepcion.detalleLaboratorio1)
        mapaRecepcion.put("costoLaboratorio1", recepcion.costoLaboratorio1)
        mapaRecepcion.put("detalleLaboratorio2", recepcion.detalleLaboratorio2)
        mapaRecepcion.put("costoLaboratorio2", recepcion.costoLaboratorio2)
        mapaRecepcion.put("detalleLaboratorio3", recepcion.detalleLaboratorio3)
        mapaRecepcion.put("costoLaboratorio3", recepcion.costoLaboratorio3)
        mapaRecepcion.put("detalleLaboratorio4", recepcion.detalleLaboratorio4)
        mapaRecepcion.put("costoLaboratorio4", recepcion.costoLaboratorio4)
        mapaRecepcion.put("totalCostoLaboratorio", recepcion.totalCostoLaboratorio)
        //mapaRecepcion.put("totalAnticiposContraEntrega", getTotalAnticiposContraEntrega(recepcion.id))
        mapaRecepcion.put("totalAnticiposContraFuturaEntrega", getTotalAnticiposContraFuturaEntrega(recepcion.cliente.id))

        render mapaRecepcion as JSON
    }

    def getTotalAnticiposContraEntrega = { recepcionId ->
        def totalAnticiposContraEntrega = 0
        def anticipoContraEntregas = AnticipoContraEntrega.findAllByRecepcionId(recepcionId)
        totalAnticiposContraEntrega = anticipoContraEntregas*.importe.sum()
        return (totalAnticiposContraEntrega)?totalAnticiposContraEntrega:0
    }

    def getTotalAnticiposContraFuturaEntrega = { clienteId ->
        def cliente = Cliente.get(clienteId)
        def ultimoEstadoDeCuenta = EstadoDeCuenta.findAllByCliente(cliente, [sort: "id", order: "desc"])
        def ultimoSaldo = (ultimoEstadoDeCuenta.size()>0)?ultimoEstadoDeCuenta.get(0).saldo:0
        return ultimoSaldo
        /*def anticipoContraFuturaEntregas = AnticipoContraFuturaEntrega.findAllByCliente(Cliente.get(clienteId))
        def totalAnticipoContraFuturaEntregas = anticipoContraFuturaEntregas*.importe.sum()
        return (totalAnticipoContraFuturaEntregas)?totalAnticipoContraFuturaEntregas:0*/
    }

    def retencionesParaLiquidacion = { retenciones ->
        def retencionesJSON = new JSONArray(retenciones)
        //def retencionesEmpresaListJSON = new JSONObject()
        def retencionesEmpresaListJSON = new JSONArray()
        //retencion estatica - regalia minera
        def regaliaMineraJSON = new JSONObject()
        regaliaMineraJSON.put("CODIGO","-")
        regaliaMineraJSON.put("CANTIDAD"," ")
        regaliaMineraJSON.put("TIPO","DE LEY")
        regaliaMineraJSON.put("UNIDAD"," ")
        regaliaMineraJSON.put("DESCRIPCION","REGALIA MINERA")
        regaliaMineraJSON.put("ASIGNACION","VBV")
        regaliaMineraJSON.put("MONTO","0")
        retencionesEmpresaListJSON.put(regaliaMineraJSON)

        retencionesJSON.each {
            def retencionesEmpresaJSON = new JSONObject()
            //def retencionesEmpresaJSON = new JSONObject()
            def codigo = it.getAt("CODIGO")
            def cantidad = it.getAt("CANTIDAD")
            def unidad = it.getAt("UNIDAD")
            def tipo = it.getAt("TIPO")
            def descripcion = it.getAt("DESCRIPCION")
            def asignacion = it.getAt("ASIGNACION")
            retencionesEmpresaJSON.put("CODIGO",codigo)
            retencionesEmpresaJSON.put("CANTIDAD",cantidad)
            retencionesEmpresaJSON.put("TIPO",tipo)
            retencionesEmpresaJSON.put("UNIDAD",unidad)
            retencionesEmpresaJSON.put("DESCRIPCION",descripcion)
            retencionesEmpresaJSON.put("ASIGNACION",asignacion)
            retencionesEmpresaJSON.put("MONTO","0")
            retencionesEmpresaListJSON.put(retencionesEmpresaJSON)
            //it.putAt("MONTO",0)
        }
        return retencionesEmpresaListJSON.toString()
        //return retencionesJSON.toString()
    }

}
