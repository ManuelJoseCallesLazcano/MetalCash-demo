package org.socymet.recepcion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject
import org.smart.parametros.ParametrosGenerales
import org.socymet.anticipos.AnticipoContraEntrega
import org.socymet.anticipos.AnticipoDetalle
import org.socymet.anticipos.EstadoDeCuenta
import org.socymet.calidad.ControlCalidadEstano
import org.socymet.cotizaciones.AjustePrecioEstano
import org.socymet.cotizaciones.TablaCotizacionEstano
import org.socymet.cotizaciones.TablaCotizacionEstanoController
import org.socymet.proveedor.Cliente
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.proveedor.bonos.BonoCalidadController
import org.socymet.proveedor.bonos.BonoCantidadController
import org.socymet.proveedor.bonos.BonoIncentivoController
import org.socymet.seguridad.SecUser
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

import java.text.DecimalFormat

@Secured(['ROLE_ADMIN','ROLE_RECEPCION','ROLE_CAJA'])
@Transactional
class RecepcionDeEstanoController {
    def springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [recepcionDeEstanoInstanceList: RecepcionDeEstano.list(params), recepcionDeEstanoInstanceTotal: RecepcionDeEstano.count()]
    }

    def create() {
        [recepcionDeEstanoInstance: new RecepcionDeEstano(params)]
    }

    def save() {
        def recepcionDeEstanoInstance = new RecepcionDeEstano(params)
        if (!recepcionDeEstanoInstance.save(flush: true)) {
            render(view: "create", model: [recepcionDeEstanoInstance: recepcionDeEstanoInstance])
            return
        }

//        flash.message = message(code: 'default.created.message', args: [message(code: 'recepcionDeEstano.label', default: 'RecepcionDeEstano'), recepcionDeEstanoInstance.id])
        flash.message = message(code: 'default.created.message', args: [message(code: 'recepcionDeEstano.label', default: 'RecepcionDeEstano'), recepcionDeEstanoInstance.toString()])
        redirect(action: "show", id: recepcionDeEstanoInstance.id)
    }

    def show(Long id) {
        def recepcionDeEstanoInstance = RecepcionDeEstano.get(id)
        if (!recepcionDeEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDeEstano.label', default: 'RecepcionDeEstano'), id])
            redirect(action: "list")
            return
        }

        [recepcionDeEstanoInstance: recepcionDeEstanoInstance]
    }

    def edit(Long id) {
        def recepcionDeEstanoInstance = RecepcionDeEstano.get(id)
        if (!recepcionDeEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDeEstano.label', default: 'RecepcionDeEstano'), id])
            redirect(action: "list")
            return
        }

        [recepcionDeEstanoInstance: recepcionDeEstanoInstance]
    }

    def update(Long id, Long version) {
        def recepcionDeEstanoInstance = RecepcionDeEstano.get(id)
        if (!recepcionDeEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDeEstano.label', default: 'RecepcionDeEstano'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (recepcionDeEstanoInstance.version > version) {
                recepcionDeEstanoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'recepcionDeEstano.label', default: 'RecepcionDeEstano')] as Object[],
                        "Another user has updated this RecepcionDeEstano while you were editing")
                render(view: "edit", model: [recepcionDeEstanoInstance: recepcionDeEstanoInstance])
                return
            }
        }

        recepcionDeEstanoInstance.properties = params

        if (!recepcionDeEstanoInstance.save(flush: true)) {
            render(view: "edit", model: [recepcionDeEstanoInstance: recepcionDeEstanoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'recepcionDeEstano.label', default: 'RecepcionDeEstano'), recepcionDeEstanoInstance.id])
        redirect(action: "show", id: recepcionDeEstanoInstance.id)
    }

    def delete(Long id) {
        def recepcionDeEstanoInstance = RecepcionDeEstano.get(id)
        if (!recepcionDeEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDeEstano.label', default: 'RecepcionDeEstano'), id])
            redirect(action: "list")
            return
        }

        try {
            recepcionDeEstanoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'recepcionDeEstano.label', default: 'RecepcionDeEstano'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'recepcionDeEstano.label', default: 'RecepcionDeEstano'), id])
            redirect(action: "show", id: id)
        }
    }

    def recepcionesEstanoJSON() {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesEstano = RecepcionDeEstano.findAllByLoteEstanoAndEstadoDelLote(lote,"NO LIQUIDADO")
        def controlCalidadEstano = null
        def parametrosGenerales = ParametrosGenerales.get(1)
        def recepcionesList = []
        //variables para calculos
        def cotizacionQuincenalEstano=0
        def alicuotaEstano=0
        def tipoDeCambioComercial=0
        def tipoDeCambioOficial=0
        def pesoBruto=0
        def porcentajeMermaPromexbol=1
        def porcentajeHumedadPromexbol=1
        def porcentajeEstanoPromexbol=1
        def controlCalidadId=0
        def tipoDeMineral="ZN-AG"

        def totalAnticiposContraEntrega=0
        def anticipoPorPagar=0

        def notificacionAnticipo=""

        recepcionesEstano.each { recepcion ->
            def mapaRecepcion = [:]
            controlCalidadEstano = ControlCalidadEstano.findByRecepcionDeEstano(recepcion)

            cotizacionQuincenalEstano=recepcion.cotizacionQuincenalDeMinerales.estano
            alicuotaEstano=recepcion.alicuota.estano
            tipoDeCambioComercial=recepcion.cotizacionDeDolar.tipoDeCambioComercial
            tipoDeCambioOficial=recepcion.cotizacionDeDolar.tipoDeCambioOficial
            pesoBruto=recepcion.pesoBruto

            if (controlCalidadEstano){
                porcentajeMermaPromexbol = controlCalidadEstano.porcentajeMermaPromexbol
                porcentajeHumedadPromexbol = controlCalidadEstano.porcentajeHumedadPromexbol
                porcentajeEstanoPromexbol = controlCalidadEstano.porcentajeEstanoPromexbol
                controlCalidadId = controlCalidadEstano.id

                //buscar si tiene anticipos
//                def anticipoDetalle = AnticipoDetalle.findByRecepcionId(recepcion.id)
                def anticipoDetalle = AnticipoDetalle.findByRecepcionIdAndTipoDeMineral(recepcion.id,"ESTANO")
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
            mapaRecepcion.put("municipio", recepcion.empresa.municipio)
//            mapaRecepcion.put("porcentajeMaximoHierro", parametrosGenerales.porcentajeMaximoHierro)
//            mapaRecepcion.put("retenciones", retencionesParaLiquidacion(recepcion.empresa.retencionesEstano,0))
            mapaRecepcion.put("retenciones", retencionesParaLiquidacion(recepcion.empresa.retenciones,0))
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            mapaRecepcion.put("pesoTara", recepcion.pesoTara)
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            mapaRecepcion.put("tipoDeMineral", tipoDeMineral)
            mapaRecepcion.put("estadoDelLote", recepcion.estadoDelLote)
            mapaRecepcion.put("documentacionCompleta", recepcion.documentacionCompleta)
            mapaRecepcion.put("cotizacionDiariaDeEstano", recepcion.cotizacionDiariaDeMinerales.estano)
            mapaRecepcion.put("cotizacionQuincenalDeEstano", recepcion.cotizacionQuincenalDeMinerales.estano)
            mapaRecepcion.put("alicuotaDeEstano", alicuotaEstano)
            mapaRecepcion.put("tipoDeCambioOficial", recepcion.cotizacionDeDolar.tipoDeCambioOficial)
            mapaRecepcion.put("tipoDeCambioComercial", recepcion.cotizacionDeDolar.tipoDeCambioComercial)
            //datos de control de calidad
            mapaRecepcion.put("porcentajeMermaPromexbol", porcentajeMermaPromexbol)
            mapaRecepcion.put("porcentajeHumedadPromexbol", porcentajeHumedadPromexbol)
            mapaRecepcion.put("porcentajeEstanoPromexbol", porcentajeEstanoPromexbol)
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

    def recepcionesEstanoValoracionJSON() {
        //recepcionDeEstano.id
        //def lote = Integer.parseInt(params.lote.toString())
        //def recepcionesEstano = RecepcionDeEstano.findAllByLoteEstanoAndEstadoDelLote(lote,"NO LIQUIDADO")
        //def recepcion = RecepcionDeEstano.findByLoteEstano(lote)
        def recepcion = RecepcionDeEstano.get(params.recepcionDeEstanoId.toString().toLong())
        def controlCalidadEstano = null
        def tablaPrecios = new TablaCotizacionEstanoController()
        def bonosPorCalidad = new BonoCalidadController()
        def bonosPorIncentivo = new BonoIncentivoController()
        def bonoPorProduccion = new BonoCantidadController()
        def recepcionesList = []
        //variables para calculos
        def cotizacionQuincenalEstano=0
        def alicuotaEstano=0
        def tipoDeCambioComercial=0
        def tipoDeCambioOficial=0
        def pesoBruto=0
        def cantidadDeSacos=0
        def tara=0
        def humedad=1
        def porcentajeEstano=1
        def porcentajeHierro=0
        def tipoDeMineral="SN"
        def pesoBrutoSinMerma=0
        def kilosNetosHumedos=0
        def kilosNetosSecos=0
        def kilosFinosEstano=0
        def librasFinasEstano=0
        def valorOficialBrutoEstano=0
        def valorOficialBrutoEstanoBs=0
        def valorOficialBruto=0
        def valorToneladaEstano=0
        def valorTonelada=0

        def valorToneladaTabla=0
        def pLMEtabla=0
        def pLMEterminos=0
        def vonTabla=0
        def vonTerminos=0
        def margen=0

        def regaliaMinera=0
        def valorNetoMineral=0
        def valorNetoMineralEnBolivianos=0
        def bonoCalidad=0
        def bonoIncentivo=0
        def bonoProduccion=0
        def valorDeCompra=0
        def totalAnticiposContraEntrega=0
        def anticipoPorPagar=0

        def notificacionAnticipo=""
        def notificacionValoresTonelada=""
        def formatter = new DecimalFormat("###.00")

        def mapaRecepcion = [:]
        //controlCalidadEstano = ControlCalidadEstano.findByRecepcionDeEstano(recepcion)

        cotizacionQuincenalEstano=recepcion.cotizacionQuincenalDeMinerales.estano
        alicuotaEstano=recepcion.alicuota.estano
        tipoDeCambioComercial=recepcion.cotizacionDeDolar.tipoDeCambioComercial
        tipoDeCambioOficial=recepcion.cotizacionDeDolar.tipoDeCambioOficial
        pesoBruto=recepcion.pesoBruto

        cantidadDeSacos = params.cantidadDeSacos.toString().toBigDecimal()
        tara = params.tara.toString().toBigDecimal()
        humedad = params.porcentajeHumedadFinal.toString().toBigDecimal()
        porcentajeEstano = params.porcentajeEstanoFinal.toString().toBigDecimal()
        porcentajeHierro = params.porcentajeHierro.toString().toBigDecimal()

        margen = params.margen.toString().toBigDecimal()

        kilosNetosHumedos=pesoBruto-cantidadDeSacos*tara

        log.error("cantidadDeSacos=$cantidadDeSacos - tara=$tara - kilosNetosHumedos=$kilosNetosHumedos")
        kilosNetosSecos=kilosNetosHumedos-kilosNetosHumedos*humedad/100
        kilosFinosEstano=kilosNetosSecos*porcentajeEstano/100
        librasFinasEstano = kilosFinosEstano*2.2046223
        valorOficialBrutoEstano = librasFinasEstano*cotizacionQuincenalEstano
        valorOficialBrutoEstanoBs = valorOficialBrutoEstano*tipoDeCambioOficial
        valorOficialBruto = valorOficialBrutoEstanoBs

        //determinacion del valor por tonelada
        //ajustePrecioEstanoId
//        def ajuste = AjustePrecioEstano.findByTablaCotizacionEstano(TablaCotizacionEstano.get(params.tablaCotizacionEstanoId.toString().toLong()),[sort: 'id',order: 'desc'])
        def ajuste = AjustePrecioEstano.get(params.ajustePrecioEstanoId)
        def cotizacionAjustada = recepcion.cotizacionDiariaDeMinerales.estano-ajuste.margen
        log.error("tablaCotizacionEstanoId: ${ajuste.tablaCotizacionEstano}")
        log.error("cotizacionDiariaDeMinerales.estano: ${recepcion.cotizacionDiariaDeMinerales.estano.toFloat()}")
        log.error("porcentajeEstano: ${porcentajeEstano}")
        log.error("cotizacionAjustada: ${cotizacionAjustada}")
        valorToneladaTabla = tablaPrecios.getValorPorTonelada(ajuste.tablaCotizacionEstano,cotizacionAjustada.toFloat(),porcentajeEstano.toFloat())
        valorToneladaTabla = valorToneladaTabla.doubleValue().round(2)
        log.error("valorToneladaTabla: $valorToneladaTabla")
//        valorTonelada = valorToneladaTabla + margen
        valorTonelada = valorToneladaTabla

        regaliaMinera = (valorOficialBrutoEstanoBs*alicuotaEstano/100).doubleValue().round(2)

        valorNetoMineral = (valorTonelada*kilosNetosSecos/1000).doubleValue().round(2)
        valorNetoMineralEnBolivianos = (valorNetoMineral*tipoDeCambioComercial).doubleValue().round(2)
//        bonoCalidad = bonosPorCalidad.bonoCalidadEstano(recepcion.empresa.id,porcentajePlata)
//        bonoIncentivo = bonosPorIncentivo.bonoIncentivoEstano(recepcion.empresa.id,kilosNetosSecos,porcentajePlata)
//        bonoProduccion = bonoPorProduccion.bonoProduccion(recepcion.empresa,kilosNetosSecos)
        bonoProduccion = 0
        valorDeCompra = valorNetoMineralEnBolivianos+bonoProduccion

        pLMEtabla = (valorToneladaTabla*kilosNetosSecos*tipoDeCambioComercial/1000)*100/valorOficialBruto
        notificacionValoresTonelada = "COTIZACION OFICIAL: ${recepcion.cotizacionDiariaDeMinerales.estano} \$us/LF \nMARGEN: ${ajuste.margen} \nCOTIZACION AJUSTADA: ${cotizacionAjustada} \$us/LF\n"
        notificacionValoresTonelada += "VALOR POR TONELADA: ${formatter.format(valorToneladaTabla)} \$us/Tn"
        //buscar si tiene anticipos
//        def anticipoDetalle = AnticipoDetalle.findByRecepcionId(recepcion.id)
        def anticipoDetalle = AnticipoDetalle.findByRecepcionIdAndTipoDeMineral(recepcion.id,"ESTANO")
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

        //DATOS PARA RETORNAR AL CLIENTE
        mapaRecepcion.put("recepcionId", recepcion.id)
        mapaRecepcion.put("label", recepcion.toString())
        mapaRecepcion.put("value", recepcion.toString())
        mapaRecepcion.put("nombreDeposito", recepcion.deposito.toString())
        mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
        mapaRecepcion.put("empresaId", recepcion.empresa.id)
        mapaRecepcion.put("depositoId", recepcion.deposito.id)
        mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
        mapaRecepcion.put("municipio", recepcion.empresa.municipio)
        mapaRecepcion.put("retenciones", retencionesParaLiquidacion(recepcion.empresa.retenciones, porcentajeHierro))
        mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
        mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
        mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
        mapaRecepcion.put("tipoDeMineral", tipoDeMineral)
        mapaRecepcion.put("estadoDelLote", recepcion.estadoDelLote)
        mapaRecepcion.put("cotizacionDiariaDeEstano", recepcion.cotizacionDiariaDeMinerales.estano)
        mapaRecepcion.put("cotizacionQuincenalDeEstano", recepcion.cotizacionQuincenalDeMinerales.estano)
        mapaRecepcion.put("alicuotaDeEstano", alicuotaEstano)
        mapaRecepcion.put("tipoDeCambioOficial", recepcion.cotizacionDeDolar.tipoDeCambioOficial)
        mapaRecepcion.put("tipoDeCambioComercial", recepcion.cotizacionDeDolar.tipoDeCambioComercial)

        //datos de control de calidad
        mapaRecepcion.put("kilosNetosHumedos", kilosNetosHumedos)
        mapaRecepcion.put("kilosNetosSecos", kilosNetosSecos)
        mapaRecepcion.put("kilosFinosEstano", kilosFinosEstano)
        mapaRecepcion.put("librasFinasDeEstano", librasFinasEstano)
        mapaRecepcion.put("valorOficialBrutoDeEstano", valorOficialBrutoEstano)
        mapaRecepcion.put("valorOficialBrutoDeEstanoEnBolivianos", valorOficialBrutoEstanoBs)
        mapaRecepcion.put("valorOficialBruto", valorOficialBruto)
        mapaRecepcion.put("valorPorTonelada", valorTonelada)

        mapaRecepcion.put("valorToneladaTabla", valorToneladaTabla)

        mapaRecepcion.put("regaliaMinera", regaliaMinera)
        mapaRecepcion.put("valorNetoMineral", valorNetoMineral)
        mapaRecepcion.put("valorNetoMineralEnBolivianos", valorNetoMineralEnBolivianos)
        mapaRecepcion.put("bonoCalidad", bonoCalidad)
        mapaRecepcion.put("bonoIncentivo", bonoProduccion)
        mapaRecepcion.put("valorDeCompra", valorDeCompra)
        mapaRecepcion.put("anticipoPorPagar", anticipoPorPagar)
        mapaRecepcion.put("notificacionAnticipo", notificacionAnticipo)
        mapaRecepcion.put("notificacionValoresTonelada", notificacionValoresTonelada)
        mapaRecepcion.put("cantidadAnticiposPorPagar", cantidadAnticiposPorPagar)

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

    def retencionesParaLiquidacion = { retenciones,porcentajeHierro ->
        def parametrosGenerales = ParametrosGenerales.get(1)
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
        regaliaMineraJSON.put("VARIABLE"," ")
        regaliaMineraJSON.put("OPERADOR"," ")
        regaliaMineraJSON.put("REFERENCIA"," ")
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
            def variable = it.getAt("VARIABLE")
            def operador = it.getAt("OPERADOR")
            def referencia = it.getAt("REFERENCIA")
            retencionesEmpresaJSON.put("CODIGO",codigo)
            retencionesEmpresaJSON.put("CANTIDAD",cantidad)
            retencionesEmpresaJSON.put("TIPO",tipo)
            retencionesEmpresaJSON.put("UNIDAD",unidad)
            retencionesEmpresaJSON.put("DESCRIPCION",descripcion)
            retencionesEmpresaJSON.put("ASIGNACION",asignacion)
            retencionesEmpresaJSON.put("MONTO","0")
            retencionesEmpresaJSON.put("VARIABLE",variable)
            retencionesEmpresaJSON.put("OPERADOR",operador)
            retencionesEmpresaJSON.put("REFERENCIA",referencia)
            retencionesEmpresaListJSON.put(retencionesEmpresaJSON)
            //it.putAt("MONTO",0)
        }

//        if(porcentajeHierro>parametrosGenerales.porcentajeMaximoHierro){
//            def retencionesEmpresaJSON = new JSONObject()
//            retencionesEmpresaJSON.put("CODIGO","0")
//            retencionesEmpresaJSON.put("CANTIDAD",parametrosGenerales.penalizacionHierroTonelada.toString())
////            log.error("CANTIDAD: ${parametrosGenerales.penalizacionHierroTonelada}")
//            retencionesEmpresaJSON.put("TIPO","OTRA")
//            retencionesEmpresaJSON.put("UNIDAD","\$us/Pt")
//            retencionesEmpresaJSON.put("DESCRIPCION","HIERRO >7%")
//            retencionesEmpresaJSON.put("ASIGNACION","")
//            retencionesEmpresaJSON.put("MONTO","0")
//            retencionesEmpresaListJSON.put(retencionesEmpresaJSON)
//        }

        return retencionesEmpresaListJSON.toString()
        //return retencionesJSON.toString()
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

    def datosTransporteUltimaRecepcionJSON() {
        def c = RecepcionDeEstano.createCriteria()
        def results = c {
            projections {
                max('id')
            }}
        def maxId = results.get(0)?: 0

        if (maxId!=0){
            def ultimaRecepcion = RecepcionDeEstano.get(maxId)
            render([
                empresaId: ultimaRecepcion.empresa.id.toString(),
                nombreEmpresa: ultimaRecepcion.empresa.nombreDeEmpresa,
                choferId: ultimaRecepcion.chofer.id.toString(),
                ciChofer: ultimaRecepcion.chofer.ci,
                nombreChofer: ultimaRecepcion.chofer.nombre,
                automovilId: ultimaRecepcion.automovil.id.toString(),
                placaAutomovil: ultimaRecepcion.automovil.placa,
                modeloAutomovil: ultimaRecepcion.automovil.modelo,
                colorAutomovil: ultimaRecepcion.automovil.color
            ] as JSON)
        }
    }

    def recepcionesPresupuestoFechasJSON() {
        def fechaInicial = new Date().parse("yyyy-MM-dd",params.fechaInicial)
        def fechaFinal = new Date().parse("yyyy-MM-dd",params.fechaFinal)
        System.err.println("********* FECHA INICIAL: $fechaInicial")
        System.err.println("********* FECHA FINAL: $fechaFinal")
        def recepcionesEstano = RecepcionDeEstano.findAllByFechaDeRecepcionBetweenAndEstadoDelLote(fechaInicial,fechaFinal,"NO LIQUIDADO")
        def recepcionesList = []
        recepcionesEstano.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("lote", recepcion.loteEstano)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            //mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            def kilosNetosHumedos = recepcion.pesoBruto-Float.parseFloat(recepcion.cantidadDeSacos)*recepcion.pesoTara
            mapaRecepcion.put("kilosNetosHumedos", kilosNetosHumedos)
            mapaRecepcion.put("cotizacionEstano", recepcion.cotizacionDiariaDeMinerales.estano)
            mapaRecepcion.put("humedad", 0)
            mapaRecepcion.put("porcentajeEstano", 0)
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def recepcionesPresupuestoFechasEmpresaJSON() {
        def fechaInicial = new Date().parse("yyyy-MM-dd",params.fechaInicial)
        def fechaFinal = new Date().parse("yyyy-MM-dd",params.fechaFinal)
        def empresa = Empresa.get(params.empresaId)
        def recepcionesEstano = RecepcionDeEstano.findAllByFechaDeRecepcionBetweenAndEstadoDelLoteAndEmpresa(fechaInicial,fechaFinal,"NO LIQUIDADO",empresa)
        def recepcionesList = []
        recepcionesEstano.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("lote", recepcion.loteEstano)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            //mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            def kilosNetosHumedos = recepcion.pesoBruto-recepcion.cantidadDeSacos*recepcion.pesoTara
            mapaRecepcion.put("kilosNetosHumedos", kilosNetosHumedos)
            mapaRecepcion.put("cotizacionEstano", recepcion.cotizacionDiariaDeMinerales.estano)
            mapaRecepcion.put("humedad", 0)
            mapaRecepcion.put("porcentajeEstano", 0)
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def recepcionesPresupuestoLotesJSON() {
        def loteInicial = params.loteInicial
        def loteFinal = params.loteFinal
        def recepcionesEstano = RecepcionDeEstano.findAllByLoteEstanoBetweenAndEstadoDelLote(loteInicial,loteFinal,"NO LIQUIDADO")
        def recepcionesList = []
        recepcionesEstano.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("lote", recepcion.loteEstano)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            //mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            def kilosNetosHumedos = recepcion.pesoBruto-recepcion.cantidadDeSacos*recepcion.pesoTara
            mapaRecepcion.put("kilosNetosHumedos", kilosNetosHumedos)
            mapaRecepcion.put("cotizacionEstano", recepcion.cotizacionDiariaDeMinerales.estano)
            mapaRecepcion.put("humedad", 0)
            mapaRecepcion.put("porcentajeEstano", 0)
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def recepcionesPresupuestoLotesEmpresaJSON() {
        def loteInicial = params.loteInicial
        def loteFinal = params.loteFinal
        def empresa = Empresa.get(params.empresaId)
        def recepcionesEstano = RecepcionDeEstano.findAllByLoteEstanoBetweenAndEstadoDelLoteAndEmpresa(loteInicial,loteFinal,"NO LIQUIDADO",empresa)
        def recepcionesList = []
        recepcionesEstano.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("lote", recepcion.loteEstano)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            //mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            def kilosNetosHumedos = recepcion.pesoBruto-recepcion.cantidadDeSacos*recepcion.pesoTara
            mapaRecepcion.put("kilosNetosHumedos", kilosNetosHumedos)
            mapaRecepcion.put("cotizacionEstano", recepcion.cotizacionDiariaDeMinerales.estano)
            mapaRecepcion.put("humedad", 0)
            mapaRecepcion.put("porcentajeEstano", 0)
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    /*def recepcionesPagoTransporteEstanoJSON(Empresa empresa, Date fechaInicial, Date fechaFinal) {
        //elemento - fecha inicial - fecha final
        def recepcionesEstano = RecepcionDeEstano.findAllByEmpresaAndFechaDeRecepcionBetweenAndTransportePagado(empresa,fechaInicial,fechaFinal,"NO")
        def recepcionesList = []
        recepcionesEstano.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("liquidacionId", recepcion.id)
            mapaRecepcion.put("clienteId", recepcion.cliente.id)
            mapaRecepcion.put("nombreCliente", recepcion.cliente.toString())
            mapaRecepcion.put("empresaId", recepcion.empresa.id)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            mapaRecepcion.put("estadoDelLote", recepcion.estadoDelLote)
            mapaRecepcion.put("costoDeTransporte", recepcion.costoDeTransporte)
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }*/

    def recepcionesCalidadEstanoJSON() {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesEstano = RecepcionDeEstano.findAllByLoteEstanoAndEstadoDelLote(lote,"NO LIQUIDADO")
        def recepcionesList = []
        recepcionesEstano.each { recepcion ->
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

    def numeroDeLote = {
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        def decimalFormat = new DecimalFormat("000000")
        def deposito = usuarioActual.deposito
        def maximum = null
        def loteEstano = 0
        //loteEstano
        def cm = RecepcionDeEstano.createCriteria()
        def results = cm.list {
            like("deposito",deposito)
            projections {
                max('loteEstano')
            }}
        maximum = results.get(0)?: 0
        loteEstano = maximum + 1

//        render(contentType: "text/json") {
//            lote = decimalFormat.format(loteEstano)
//        }
        if (loteEstano>0)
            render([
                depositoActual: deposito.toString(),
                lote: "SN"+decimalFormat.format(loteEstano)
            ] as JSON)
    }

    def crearReporte = {
        def recepcionDeEstano = RecepcionDeEstano.get(params.id)
        chain(controller:'jasper',action:'index',model:[data:recepcionDeEstano],params:params)
    }
}
