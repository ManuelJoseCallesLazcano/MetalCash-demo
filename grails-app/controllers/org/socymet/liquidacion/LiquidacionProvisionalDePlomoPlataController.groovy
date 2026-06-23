package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject
import org.socymet.anticipos.AnticipoDetalle
import org.socymet.calidad.ControlCalidadPlomoPlata
import org.socymet.cotizaciones.TablaOrigenCotizacionesComplejoController
import org.socymet.cotizaciones.TerminosDeContratoController
import org.socymet.proveedor.bonos.BonoCalidadController
import org.socymet.proveedor.bonos.BonoIncentivoController
import org.socymet.recepcion.RecepcionDeComplejo
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class LiquidacionProvisionalDePlomoPlataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [liquidacionProvisionalDePlomoPlataInstanceList: LiquidacionProvisionalDePlomoPlata.list(params), liquidacionProvisionalDePlomoPlataInstanceTotal: LiquidacionProvisionalDePlomoPlata.count()]
    }

    def create() {
        [liquidacionProvisionalDePlomoPlataInstance: new LiquidacionProvisionalDePlomoPlata(params)]
    }

    def save() {
        def liquidacionProvisionalDePlomoPlataInstance = new LiquidacionProvisionalDePlomoPlata(params)
        if (!liquidacionProvisionalDePlomoPlataInstance.save(flush: true)) {
            render(view: "create", model: [liquidacionProvisionalDePlomoPlataInstance: liquidacionProvisionalDePlomoPlataInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionProvisionalDePlomoPlata.label', default: 'LiquidacionProvisionalDePlomoPlata'), liquidacionProvisionalDePlomoPlataInstance.id])
        redirect(action: "show", id: liquidacionProvisionalDePlomoPlataInstance.id)
    }

    def show(Long id) {
        def liquidacionProvisionalDePlomoPlataInstance = LiquidacionProvisionalDePlomoPlata.get(id)
        if (!liquidacionProvisionalDePlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionProvisionalDePlomoPlata.label', default: 'LiquidacionProvisionalDePlomoPlata'), id])
            redirect(action: "list")
            return
        }

        [liquidacionProvisionalDePlomoPlataInstance: liquidacionProvisionalDePlomoPlataInstance]
    }

    def edit(Long id) {
        def liquidacionProvisionalDePlomoPlataInstance = LiquidacionProvisionalDePlomoPlata.get(id)
        if (!liquidacionProvisionalDePlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionProvisionalDePlomoPlata.label', default: 'LiquidacionProvisionalDePlomoPlata'), id])
            redirect(action: "list")
            return
        }

        [liquidacionProvisionalDePlomoPlataInstance: liquidacionProvisionalDePlomoPlataInstance]
    }

    def update(Long id, Long version) {
        def liquidacionProvisionalDePlomoPlataInstance = LiquidacionProvisionalDePlomoPlata.get(id)
        if (!liquidacionProvisionalDePlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionProvisionalDePlomoPlata.label', default: 'LiquidacionProvisionalDePlomoPlata'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (liquidacionProvisionalDePlomoPlataInstance.version > version) {
                liquidacionProvisionalDePlomoPlataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'liquidacionProvisionalDePlomoPlata.label', default: 'LiquidacionProvisionalDePlomoPlata')] as Object[],
                        "Another user has updated this LiquidacionProvisionalDePlomoPlata while you were editing")
                render(view: "edit", model: [liquidacionProvisionalDePlomoPlataInstance: liquidacionProvisionalDePlomoPlataInstance])
                return
            }
        }

        liquidacionProvisionalDePlomoPlataInstance.properties = params

        if (!liquidacionProvisionalDePlomoPlataInstance.save(flush: true)) {
            render(view: "edit", model: [liquidacionProvisionalDePlomoPlataInstance: liquidacionProvisionalDePlomoPlataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'liquidacionProvisionalDePlomoPlata.label', default: 'LiquidacionProvisionalDePlomoPlata'), liquidacionProvisionalDePlomoPlataInstance.id])
        redirect(action: "show", id: liquidacionProvisionalDePlomoPlataInstance.id)
    }

    def delete(Long id) {
        def liquidacionProvisionalDePlomoPlataInstance = LiquidacionProvisionalDePlomoPlata.get(id)
        if (!liquidacionProvisionalDePlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionProvisionalDePlomoPlata.label', default: 'LiquidacionProvisionalDePlomoPlata'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionProvisionalDePlomoPlataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'liquidacionProvisionalDePlomoPlata.label', default: 'LiquidacionProvisionalDePlomoPlata'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionProvisionalDePlomoPlata.label', default: 'LiquidacionProvisionalDePlomoPlata'), id])
            redirect(action: "show", id: id)
        }
    }

    def recepcionesPlomoPlataJSON() {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesComplejo = RecepcionDeComplejo.findAllByLotePlomoPlataAndEstadoDelLoteAndCondicionDeEntrega(lote,"NO LIQUIDADO","TERM-CON")
        def controlCalidadComplejo = null
        def tablaPrecios = new TablaOrigenCotizacionesComplejoController()
        def terminosContrato = new TerminosDeContratoController()
        def bonosPorCalidad = new BonoCalidadController()
        def bonosPorIncentivo = new BonoIncentivoController()
        def recepcionesList = []
        //variables para calculos
        def cotizacionQuincenalPlomo=0
        def cotizacionQuincenalPlata=0
        def alicuotaPlomo=0
        def alicuotaPlata=0
        def tipoDeCambioComercial=0
        def tipoDeCambioOficial=0
        def pesoBruto=0
        def porcentajeMermaPromexbol=1
        def porcentajeHumedadPromexbol=1
        def porcentajePlomoPromexbol=1
        def porcentajePlataPromexbol=1
        def controlCalidadId=0
        def tipoDeMineral="PB-AG"

        def totalAnticiposContraEntrega=0
        def anticipoPorPagar=0

        def notificacionAnticipo=""

        recepcionesComplejo.each { recepcion ->
            def mapaRecepcion = [:]
            controlCalidadComplejo = ControlCalidadPlomoPlata.findByRecepcionDeComplejo(recepcion)

            cotizacionQuincenalPlomo=recepcion.cotizacionQuincenalDeMinerales.plomo
            cotizacionQuincenalPlata=recepcion.cotizacionQuincenalDeMinerales.plata
            alicuotaPlomo=recepcion.alicuota.plomo
            alicuotaPlata=recepcion.alicuota.plata
            tipoDeCambioComercial=recepcion.cotizacionDeDolar.tipoDeCambioComercial
            tipoDeCambioOficial=recepcion.cotizacionDeDolar.tipoDeCambioOficial
            pesoBruto=recepcion.pesoBruto

            if (controlCalidadComplejo){
                porcentajeMermaPromexbol = controlCalidadComplejo.porcentajeMermaPromexbol
                porcentajeHumedadPromexbol = controlCalidadComplejo.porcentajeHumedadPromexbol
                porcentajePlomoPromexbol = controlCalidadComplejo.porcentajePlomoPromexbol
                porcentajePlataPromexbol = controlCalidadComplejo.porcentajePlataPromexbol
                controlCalidadId = controlCalidadComplejo.id

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
            mapaRecepcion.put("direccion", recepcion.cliente.direccion)
            mapaRecepcion.put("clienteId", recepcion.cliente.id)
            mapaRecepcion.put("empresaId", recepcion.empresa.id)
            mapaRecepcion.put("depositoId", recepcion.deposito.id)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("retenciones", retencionesParaLiquidacion(recepcion.empresa.retenciones))
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            mapaRecepcion.put("tipoDeMineral", tipoDeMineral)
            mapaRecepcion.put("estadoDelLote", recepcion.estadoDelLote)
            mapaRecepcion.put("naturalezaMineral", recepcion.naturalezaMineral)
            mapaRecepcion.put("documentacionCompleta", recepcion.documentacionCompleta)
            mapaRecepcion.put("cotizacionDiariaDeZinc", recepcion.cotizacionDiariaDeMinerales.zinc)
            mapaRecepcion.put("cotizacionQuincenalDeZinc", recepcion.cotizacionQuincenalDeMinerales.zinc)
            mapaRecepcion.put("alicuotaDeZinc", recepcion.alicuota.zinc)
            mapaRecepcion.put("cotizacionDiariaDePlomo", recepcion.cotizacionDiariaDeMinerales.plomo)
            mapaRecepcion.put("cotizacionQuincenalDePlomo", recepcion.cotizacionQuincenalDeMinerales.plomo)
            mapaRecepcion.put("alicuotaDePlomo", alicuotaPlomo)
            mapaRecepcion.put("cotizacionDiariaDePlata", recepcion.cotizacionDiariaDeMinerales.plata)
            mapaRecepcion.put("cotizacionQuincenalDePlata", recepcion.cotizacionQuincenalDeMinerales.plata)
            mapaRecepcion.put("alicuotaDePlata", alicuotaPlata)
            mapaRecepcion.put("alicuotaDeZincParaExportacion", recepcion.alicuota.zincExportacion)
            mapaRecepcion.put("alicuotaDePlomoParaExportacion", recepcion.alicuota.plomoExportacion)
            mapaRecepcion.put("alicuotaDePlataParaExportacion", recepcion.alicuota.plataExportacion)
            mapaRecepcion.put("tipoDeCambioOficial", recepcion.cotizacionDeDolar.tipoDeCambioOficial)
            mapaRecepcion.put("tipoDeCambioComercial", recepcion.cotizacionDeDolar.tipoDeCambioComercial)

            //datos de control de calidad
            mapaRecepcion.put("porcentajeMermaPromexbol", porcentajeMermaPromexbol)
            mapaRecepcion.put("porcentajeHumedadPromexbol", porcentajeHumedadPromexbol)
            mapaRecepcion.put("porcentajePlomoPromexbol", porcentajePlomoPromexbol)
            mapaRecepcion.put("porcentajePlataPromexbol", porcentajePlataPromexbol)
            mapaRecepcion.put("controlCalidadId", controlCalidadId)
            mapaRecepcion.put("anticipoPorPagar", anticipoPorPagar)
            mapaRecepcion.put("notificacionAnticipo", notificacionAnticipo)

            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
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
