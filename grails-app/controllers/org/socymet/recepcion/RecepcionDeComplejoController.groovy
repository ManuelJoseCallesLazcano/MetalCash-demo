package org.socymet.recepcion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject
import org.smart.parametros.GestionMinera
import org.smart.parametros.ParametrosGenerales
import org.socymet.anticipos.AnticipoContraEntrega
import org.socymet.anticipos.AnticipoDetalle
import org.socymet.anticipos.AnticipoPorTransporte
import org.socymet.anticipos.EstadoDeCuenta
import org.socymet.caja.Cuenta
import org.socymet.caja.Subcuenta
import org.socymet.calidad.ControlCalidadCobrePlata
import org.socymet.calidad.ControlCalidadComplejo
import org.socymet.calidad.ControlCalidadPlomoPlata
import org.socymet.calidad.ControlCalidadZincPlata
import org.socymet.cotizaciones.*
import org.socymet.proveedor.Automovil
import org.socymet.proveedor.Cliente
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.proveedor.EmpresaSeccion
import org.socymet.proveedor.bonos.BonoCalidadController
import org.socymet.proveedor.bonos.BonoIncentivoController
import org.socymet.seguridad.SecUser
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

import java.text.DecimalFormat

@Secured(['ROLE_ADMIN','ROLE_RECEPCION','ROLE_CAJA'])
@Transactional
class RecepcionDeComplejoController {
    def springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.sort = params.sort ?: "id"
        params.order = params.order ?: "desc"

        // Buscador por lote (codigoLote), nombre de cliente, empresa o composito
        def q = params.q?.trim()

        def results = RecepcionDeComplejo.createCriteria().list(
                max: params.max, offset: params.offset ?: 0,
                sort: params.sort, order: params.order) {
            if (q) {
                createAlias('cliente', 'c')
                createAlias('empresa', 'e')
                or {
                    ilike('codigoLote', "%${q}%")
                    ilike('nombreComposito', "%${q}%")
                    ilike('c.nombre', "%${q}%")
                    ilike('e.nombreDeEmpresa', "%${q}%")
                }
            }
        }

        [recepcionDeComplejoInstanceList: results,
         recepcionDeComplejoInstanceTotal: results.totalCount,
         q: q]
    }

    def create() {
        [recepcionDeComplejoInstance: new RecepcionDeComplejo(params)]
    }

    def save() {
        def recepcionDeComplejoInstance = new RecepcionDeComplejo(params)
        if (!recepcionDeComplejoInstance.save(flush: true)) {
            render(view: "create", model: [recepcionDeComplejoInstance: recepcionDeComplejoInstance])
            return
        }

//        flash.message = message(code: 'default.created.message', args: [message(code: 'recepcionDeComplejo.label', default: 'RecepcionDeComplejo'), recepcionDeComplejoInstance.toString()])
        flash.message = message(code: 'default.created.message', args: [message(code: 'recepcionDeComplejo.label', default: 'RecepcionDeComplejo'), recepcionDeComplejoInstance.toString()])
        redirect(action: "show", id: recepcionDeComplejoInstance.id)
    }

    def show(Long id) {
        def recepcionDeComplejoInstance = RecepcionDeComplejo.get(id)
        if (!recepcionDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDeComplejo.label', default: 'RecepcionDeComplejo'), id])
            redirect(action: "list")
            return
        }

        [recepcionDeComplejoInstance: recepcionDeComplejoInstance]
    }

    def edit(Long id) {
        def recepcionDeComplejoInstance = RecepcionDeComplejo.get(id)
        if (!recepcionDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDeComplejo.label', default: 'RecepcionDeComplejo'), id])
            redirect(action: "list")
            return
        }

        if (bloquear(recepcionDeComplejoInstance, 'editar')) return

        [recepcionDeComplejoInstance: recepcionDeComplejoInstance]
    }

    /**
     * Si el lote no puede editarse/eliminarse, fija el flash con la causa y redirige al show.
     * Devuelve true si estaba bloqueado (el llamador debe hacer `return`).
     */
    private boolean bloquear(RecepcionDeComplejo instance, String accion) {
        def motivos = instance.motivosBloqueo()
        if (!motivos) return false
        // Materializar el nombre del lote AQUÍ (sesión abierta): toString() accede a deposito/empresa
        // lazy. Si se dejara la entidad dentro de un GString en flash, se evaluaría al renderizar el
        // show (otra request) con la instancia desligada → LazyInitializationException.
        String lote = instance.toString()
        flash.message = "No se puede ${accion} el lote ${lote}: ${motivos.join('; ')}.".toString()
        flash.swalIcon = 'warning'
        flash.swalTitle = 'Acción no permitida'
        redirect(action: "show", id: instance.id)
        true
    }

    def update(Long id, Long version) {
        def recepcionDeComplejoInstance = RecepcionDeComplejo.get(id)
        if (!recepcionDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDeComplejo.label', default: 'RecepcionDeComplejo'), id])
            redirect(action: "list")
            return
        }

        if (bloquear(recepcionDeComplejoInstance, 'editar')) return

        if (version != null) {
            if (recepcionDeComplejoInstance.version > version) {
                recepcionDeComplejoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'recepcionDeComplejo.label', default: 'RecepcionDeComplejo')] as Object[],
                        "Another user has updated this RecepcionDeComplejo while you were editing")
                render(view: "edit", model: [recepcionDeComplejoInstance: recepcionDeComplejoInstance])
                return
            }
        }

        recepcionDeComplejoInstance.properties = params

        if (!recepcionDeComplejoInstance.save(flush: true)) {
            render(view: "edit", model: [recepcionDeComplejoInstance: recepcionDeComplejoInstance])
            return
        }

//        flash.message = message(code: 'default.updated.message', args: [message(code: 'recepcionDeComplejo.label', default: 'RecepcionDeComplejo'), recepcionDeComplejoInstance.toString()])
        flash.message = message(code: 'default.updated.message', args: [message(code: 'recepcionDeComplejo.label', default: 'RecepcionDeComplejo'), recepcionDeComplejoInstance.toString()])
        redirect(action: "show", id: recepcionDeComplejoInstance.id)
    }

    def delete(Long id) {
        def recepcionDeComplejoInstance = RecepcionDeComplejo.get(id)
        if (!recepcionDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDeComplejo.label', default: 'RecepcionDeComplejo'), id])
            redirect(action: "list")
            return
        }

        if (bloquear(recepcionDeComplejoInstance, 'eliminar')) return

        try {
            def nombreLote = recepcionDeComplejoInstance.toString()
            recepcionDeComplejoInstance.delete(flush: true)
//            flash.message = message(code: 'default.deleted.message', args: [message(code: 'recepcionDeComplejo.label', default: 'RecepcionDeComplejo'), id])
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'recepcionDeComplejo.label', default: 'RecepcionDeComplejo'), nombreLote])
            flash.swalIcon = 'success'
            flash.swalTitle = 'Eliminación realizada'
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'recepcionDeComplejo.label', default: 'RecepcionDeComplejo'), id])
            redirect(action: "show", id: id)
        }
    }

    def recepcionesCalidadComplejoJSON() {
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        def deposito = usuarioActual.deposito
        def lote = Integer.parseInt(params.term.toString())
//        def recepcionesComplejo = RecepcionDeComplejo.findAllByDepositoAndLoteComplejoAndTipoDeMineralAndEstadoDelLote(deposito,lote,"COMPLEJO","NO LIQUIDADO")
        def recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejoLikeAndEstadoDelLote(lote,"NO LIQUIDADO")
        def tablaComplejo = new TablaOrigenCotizacionesComplejoController()
        def terminos = new TerminosDeContratoController()
        def recepcionesList = []
        recepcionesComplejo.each { recepcion ->
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
            mapaRecepcion.put("condicionDeEntrega", recepcion.condicionDeEntrega)
//            mapaRecepcion.put("tablasIds", tablaComplejo.getTablasIds(recepcion.empresa.id,recepcion.naturalezaMineral))
//            mapaRecepcion.put("terminosIds", terminos.getTerminosIds(recepcion.empresa.id))
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def recepcionesCalidadPlomoPlataJSON() {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesComplejo = RecepcionDeComplejo.findAllByLotePlomoPlataAndTipoDeMineralAndEstadoDelLote(lote,"PB-AG","NO LIQUIDADO")
        def tablaComplejo = new TablaOrigenCotizacionesComplejoController()
        def terminosPlomoPlata = new TerminosDeContratoController()
        def recepcionesList = []
        recepcionesComplejo.each { recepcion ->
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
            mapaRecepcion.put("condicionDeEntrega", recepcion.condicionDeEntrega)
            mapaRecepcion.put("tablasIds", tablaComplejo.getTablasIds(recepcion.empresa.id,recepcion.naturalezaMineral))
            mapaRecepcion.put("terminosIds", terminosPlomoPlata.getTerminosIds(recepcion.empresa.id))
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def recepcionesCalidadZincPlataJSON() {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesComplejo = RecepcionDeComplejo.findAllByLoteZincPlataAndTipoDeMineralAndEstadoDelLote(lote,"ZN-AG","NO LIQUIDADO")
        def tablaComplejo = new TablaOrigenCotizacionesComplejoController()
        def terminosZincPlata = new TerminosDeContratoController()
        def recepcionesList = []
        recepcionesComplejo.each { recepcion ->
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
            mapaRecepcion.put("condicionDeEntrega", recepcion.condicionDeEntrega)
            mapaRecepcion.put("tablasIds", tablaComplejo.getTablasIds(recepcion.empresa.id,recepcion.naturalezaMineral))
            mapaRecepcion.put("terminosIds", terminosZincPlata.getTerminosIds(recepcion.empresa.id))
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def recepcionesCalidadCobrePlataJSON() {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesComplejo = RecepcionDeComplejo.findAllByLoteCobrePlataAndTipoDeMineralAndEstadoDelLote(lote,"CU-AG","NO LIQUIDADO")
        def recepcionesList = []
        recepcionesComplejo.each { recepcion ->
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
            mapaRecepcion.put("condicionDeEntrega", recepcion.condicionDeEntrega)
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

//    def recepcionesComplejoJSON() {
//        def lote = Integer.parseInt(params.term.toString())
//        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
//        def recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejoAndEstadoDelLote(lote,"NO LIQUIDADO")
//        def controlCalidadComplejo = null
//        def tablaPrecios = new TablaOrigenCotizacionesComplejoController()
//        def terminosContrato = new TerminosDeContratoController()
//        def bonosPorCalidad = new BonoCalidadController()
//        def bonosPorIncentivo = new BonoIncentivoController()
//        def recepcionesList = []
//        //variables para calculos
//        def cotizacionQuincenalZinc=0
//        def cotizacionQuincenalPlomo=0
//        def cotizacionQuincenalPlata=0
//        def alicuotaZinc=0
//        def alicuotaPlomo=0
//        def alicuotaPlata=0
//        def tipoDeCambioComercial=0
//        def tipoDeCambioOficial=0
//        def pesoBruto=0
//        def porcentajeMermaPromexbol=1
//        def porcentajeHumedadPromexbol=1
//        def porcentajeZincPromexbol=1
//        def porcentajePlomoPromexbol=1
//        def porcentajePlataPromexbol=1
//        def controlCalidadId=0
//        def tipoDeMineral="COMPLEJO"
//
//        def totalAnticiposContraEntrega=0
//        def anticipoPorPagar=0
//
//        def notificacionAnticipo=""
//
//        recepcionesComplejo.each { recepcion ->
//            def mapaRecepcion = [:]
//            controlCalidadComplejo = ControlCalidadComplejo.findByRecepcionDeComplejo(recepcion)
//
//            cotizacionQuincenalZinc=recepcion.cotizacionQuincenalDeMinerales.zinc
//            cotizacionQuincenalPlomo=recepcion.cotizacionQuincenalDeMinerales.plomo
//            cotizacionQuincenalPlata=recepcion.cotizacionQuincenalDeMinerales.plata
//            alicuotaZinc=recepcion.alicuota.zinc
//            alicuotaPlomo=recepcion.alicuota.plomo
//            alicuotaPlata=recepcion.alicuota.plata
//            tipoDeCambioComercial=recepcion.cotizacionDeDolar.tipoDeCambioComercial
//            tipoDeCambioOficial=recepcion.cotizacionDeDolar.tipoDeCambioOficial
//            pesoBruto=recepcion.pesoBruto
//
//            if (controlCalidadComplejo){
//                porcentajeMermaPromexbol = controlCalidadComplejo.porcentajeMermaPromexbol
//                porcentajeHumedadPromexbol = controlCalidadComplejo.porcentajeHumedadPromexbol
//                porcentajeZincPromexbol = controlCalidadComplejo.porcentajeZincPromexbol
//                porcentajePlomoPromexbol = controlCalidadComplejo.porcentajePlomoPromexbol
//                porcentajePlataPromexbol = controlCalidadComplejo.porcentajePlataPromexbol
//                controlCalidadId = controlCalidadComplejo.id
//
//                //buscar si tiene anticipos
//                def anticipoDetalle = AnticipoDetalle.findByRecepcionId(recepcion.id)
//                if(anticipoDetalle){
//                    def anticipoDetalles = AnticipoDetalle.findAllByAnticipo(anticipoDetalle.anticipo)
//                    /*ATENCION: El campo anticipoDetallesPagados servira para controlar cuando se genere un saldo negativo
//                    * bajo la idea de que si solo queda un lote para pagar el anticipo y no lo cubre debera generarse el
//                    * saldo negativo y no el saldo 0 (liquido pagable=0) como cuando se paga un anticipo con el total del
//                    * valor del lote. Tendria que calcularse la diferencia de tamaños entre anticipoDetalles y
//                    * anticipoDetallesPagados, si la diferencia es mayor a 1 todavia hay chance de poder pagarse
//                    * el anticipo.*/
//                    def anticipoDetallesPagados = AnticipoDetalle.findAllByAnticipoAndEstadoAnticipo(anticipoDetalle.anticipo,"PAGADO")
//                    def anticipo = anticipoDetalle.anticipo
//                    def lotesAsignados = ""
//                    anticipoDetalles.each {
//                        lotesAsignados+="${it.lote} (${it.estadoAnticipo})\n"
//                    }
//
//                    anticipoPorPagar = anticipo.totalPorPagar
//
//                    notificacionAnticipo = "EL LOTE ${anticipoDetalle.lote} ESTA ASIGNADO AL ANTICIPO CON IMPORTE INICIAL DE Bs${anticipoDetalle.anticipo.totalAnticipos}\nCON UN TOTAL POR PAGAR DE Bs${anticipoDetalle.anticipo.totalPorPagar}\nFORMADO POR LOS LOTES:\n${lotesAsignados}"
//                }
//            }else
//                System.out.println("****** no existe informacion en control de calidad")
//
//            mapaRecepcion.put("recepcionId", recepcion.id)
//            mapaRecepcion.put("label", recepcion.toString())
//            mapaRecepcion.put("value", recepcion.toString())
//            mapaRecepcion.put("nombreDeposito", recepcion.deposito.toString())
//            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
//            mapaRecepcion.put("empresaId", recepcion.empresa.id)
//            mapaRecepcion.put("depositoId", recepcion.deposito.id)
//            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
//            mapaRecepcion.put("retenciones", retencionesParaLiquidacion(recepcion.empresa.retenciones))
//            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
//            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
//            mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
//            mapaRecepcion.put("tipoDeMineral", tipoDeMineral)
//            mapaRecepcion.put("estadoDelLote", recepcion.estadoDelLote)
//            mapaRecepcion.put("naturalezaMineral", recepcion.naturalezaMineral)
//            mapaRecepcion.put("documentacionCompleta", recepcion.documentacionCompleta)
//            mapaRecepcion.put("cotizacionDiariaDeZinc", recepcion.cotizacionDiariaDeMinerales.zinc)
//            mapaRecepcion.put("cotizacionQuincenalDeZinc", recepcion.cotizacionQuincenalDeMinerales.zinc)
//            mapaRecepcion.put("alicuotaDeZinc", alicuotaZinc)
//            mapaRecepcion.put("cotizacionDiariaDePlomo", recepcion.cotizacionDiariaDeMinerales.plomo)
//            mapaRecepcion.put("cotizacionQuincenalDePlomo", recepcion.cotizacionQuincenalDeMinerales.plomo)
//            mapaRecepcion.put("alicuotaDePlomo", alicuotaPlomo)
//            mapaRecepcion.put("cotizacionDiariaDePlata", recepcion.cotizacionDiariaDeMinerales.plata)
//            mapaRecepcion.put("cotizacionQuincenalDePlata", recepcion.cotizacionQuincenalDeMinerales.plata)
//            mapaRecepcion.put("alicuotaDePlata", alicuotaPlata)
//            mapaRecepcion.put("tipoDeCambioOficial", recepcion.cotizacionDeDolar.tipoDeCambioOficial)
//            mapaRecepcion.put("tipoDeCambioComercial", recepcion.cotizacionDeDolar.tipoDeCambioComercial)
//
////            mapaRecepcion.put("tablasIds", tablaPrecios.getTablasIds(recepcion.empresa.id))
////            mapaRecepcion.put("terminosIds", terminosContrato.getTerminosIds(recepcion.empresa.id))
//
//            //datos de control de calidad
//            mapaRecepcion.put("porcentajeMermaPromexbol", porcentajeMermaPromexbol)
//            mapaRecepcion.put("porcentajeHumedadPromexbol", porcentajeHumedadPromexbol)
//            mapaRecepcion.put("porcentajeZincPromexbol", porcentajeZincPromexbol)
//            mapaRecepcion.put("porcentajePlomoPromexbol", porcentajePlomoPromexbol)
//            mapaRecepcion.put("porcentajePlataPromexbol", porcentajePlataPromexbol)
//            mapaRecepcion.put("controlCalidadId", controlCalidadId)
//
//            mapaRecepcion.put("anticipoPorPagar", anticipoPorPagar)
//            mapaRecepcion.put("notificacionAnticipo", notificacionAnticipo)
//
//            mapaRecepcion.put("detalleLaboratorio1", recepcion.detalleLaboratorio1)
//            mapaRecepcion.put("costoLaboratorio1", recepcion.costoLaboratorio1)
//            mapaRecepcion.put("detalleLaboratorio2", recepcion.detalleLaboratorio2)
//            mapaRecepcion.put("costoLaboratorio2", recepcion.costoLaboratorio2)
//            mapaRecepcion.put("detalleLaboratorio3", recepcion.detalleLaboratorio3)
//            mapaRecepcion.put("costoLaboratorio3", recepcion.costoLaboratorio3)
//            mapaRecepcion.put("detalleLaboratorio4", recepcion.detalleLaboratorio4)
//            mapaRecepcion.put("costoLaboratorio4", recepcion.costoLaboratorio4)
//            mapaRecepcion.put("totalCostoLaboratorio", recepcion.totalCostoLaboratorio)
//            //mapaRecepcion.put("totalAnticiposContraEntrega", getTotalAnticiposContraEntrega(recepcion.id))
//            mapaRecepcion.put("totalAnticiposContraFuturaEntrega", getTotalAnticiposContraFuturaEntrega(recepcion.cliente.id))
//            recepcionesList.add(mapaRecepcion)
//        }
//        render recepcionesList as JSON
//    }

    def recepcionesComplejoJSON() {
        def recepcion = RecepcionDeComplejo.get(params.recepcionId)
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
//        def recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejoAndEstadoDelLote(lote,"NO LIQUIDADO")
        def controlCalidadComplejo = null
        def tablaPrecios = new TablaOrigenCotizacionesComplejoController()
        def terminosContrato = new TerminosDeContratoController()
        def bonosPorCalidad = new BonoCalidadController()
        def bonosPorIncentivo = new BonoIncentivoController()
        def recepcionesList = []
        //variables para calculos
        def cotizacionQuincenalZinc=0
        def cotizacionQuincenalPlomo=0
        def cotizacionQuincenalPlata=0
        def alicuotaZinc=0
        def alicuotaPlomo=0
        def alicuotaPlata=0

        def totalAnticiposContraEntrega=0
        def anticipoPorPagar=0

        def notificacionAnticipo=""

        controlCalidadComplejo = ControlCalidadComplejo.findByRecepcionDeComplejo(recepcion)

        if (controlCalidadComplejo){
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

        render([
            recepcionId: recepcion.id,
            label: recepcion.toString(),
            value: recepcion.toString(),
            lote: recepcion.toString(),
            nombreDeposito: recepcion.deposito.toString(),
            nombreCliente: recepcion.cliente.nombre,
            empresaId: recepcion.empresa.id,
            depositoId: recepcion.deposito.id,
            nombreEmpresa: recepcion.empresa.toString(),
            retenciones: retencionesParaLiquidacion(recepcion.empresa.retenciones, recepcion.empresaSeccion),
            cantidadDeSacos: recepcion.cantidadDeSacos,
            fechaDeRecepcion: new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion),
            pesoBruto: recepcion.pesoBruto,
            tipoDeMineral: recepcion.tipoDeMineral,
            estadoDelLote: recepcion.estadoDelLote,
            naturalezaMineral: recepcion.naturalezaMineral,
            documentacionCompleta: recepcion.documentacionCompleta,
            cotizacionDiariaDeZinc: recepcion.cotizacionDiariaDeMinerales.zinc,
            cotizacionQuincenalDeZinc: recepcion.cotizacionQuincenalDeMinerales.zinc,
            alicuotaDeZinc: recepcion.alicuota.zinc,
            cotizacionDiariaDePlomo: recepcion.cotizacionDiariaDeMinerales.plomo,
            cotizacionQuincenalDePlomo: recepcion.cotizacionQuincenalDeMinerales.plomo,
            alicuotaDePlomo: recepcion.alicuota.plomo,
            cotizacionDiariaDePlata: recepcion.cotizacionDiariaDeMinerales.plata,
            cotizacionQuincenalDePlata: recepcion.cotizacionQuincenalDeMinerales.plata,
            alicuotaDePlata: recepcion.alicuota.plata,
            tipoDeCambioOficial: recepcion.cotizacionDeDolar.tipoDeCambioOficial,
            tipoDeCambioComercial: recepcion.cotizacionDeDolar.tipoDeCambioComercial,
            porcentajeMermaPromexbol: controlCalidadComplejo.porcentajeMermaPromexbol,
            porcentajeHumedadPromexbol: controlCalidadComplejo.porcentajeHumedadPromexbol,
            porcentajeZincPromexbol: controlCalidadComplejo.porcentajeZincPromexbol,
            porcentajePlomoPromexbol: controlCalidadComplejo.porcentajePlomoPromexbol,
            porcentajePlataPromexbol: controlCalidadComplejo.porcentajePlataPromexbol,
            controlCalidadId: controlCalidadComplejo.id,
            totalAnticipoPorPagar: anticipoPorPagar,
            _notificacionAnticipo: notificacionAnticipo,
            costoTratamiento: recepcion.empresa.costoTratamiento,
            detalleLaboratorio1: recepcion.detalleLaboratorio1,
            costoLaboratorio1: recepcion.costoLaboratorio1,
            detalleLaboratorio2: recepcion.detalleLaboratorio2,
            costoLaboratorio2: recepcion.costoLaboratorio2,
            detalleLaboratorio3: recepcion.detalleLaboratorio3,
            costoLaboratorio3: recepcion.costoLaboratorio3,
            detalleLaboratorio4: recepcion.detalleLaboratorio4,
            costoLaboratorio4: recepcion.costoLaboratorio4,
            totalCostoLaboratorio: recepcion.totalCostoLaboratorio,
            totalAnticiposContraFuturaEntrega: getTotalAnticiposContraFuturaEntrega(recepcion.cliente.id)
        ] as JSON)
    }

    def recepcionesComplejoValoracionJSON() {
        //recepcionDeComplejo.id
        //def lote = Integer.parseInt(params.lote.toString())
        //def recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejoAndEstadoDelLote(lote,"NO LIQUIDADO")
        //def recepcion = RecepcionDeComplejo.findByLoteComplejo(lote)
        def recepcion = RecepcionDeComplejo.get(params.recepcionDeComplejoId.toString().toLong())
        def controlCalidadComplejo = null
        def tablaPrecios = new TablaOrigenCotizacionesComplejoController()
        def tablaPrecioPorLME = new TablaPrecioPorLmeController()
        def terminosContrato = new TerminosDeContratoController()
        def bonosPorCalidad = new BonoCalidadController()
        def bonosPorIncentivo = new BonoIncentivoController()
        def recepcionesList = []
        //variables para calculos
        def cotizacionDiariaZinc=0
        def cotizacionDiariaPlomo=0
        def cotizacionDiariaPlata=0
        def cotizacionQuincenalZinc=0
        def cotizacionQuincenalPlomo=0
        def cotizacionQuincenalPlata=0
        def alicuotaZinc=0
        def alicuotaPlomo=0
        def alicuotaPlata=0
        def tipoDeCambioComercial=0
        def tipoDeCambioOficial=0
        def pesoBruto=0
        def cantidadSacos=0
        def merma=0
        def humedad=1
        def porcentajeZinc=1
        def porcentajePlomo=1
        def porcentajePlata=1
        def tipoDeMineral="COMPLEJO"
        def pesoBrutoSinMerma=0
        def kilosNetosHumedos=0
        def kilosNetosSecos=0
        def kilosFinosZinc=0
        def kilosFinosPlomo=0
        def kilosFinosPlata=0
        def librasFinasZinc=0
        def librasFinasPlomo=0
        def onzasTroyPlata=0
        def valorOficialBrutoZinc=0
        def valorOficialBrutoPlomo=0
        def valorOficialBrutoPlata=0
        def valorOficialBrutoZincBs=0
        def valorOficialBrutoPlomoBs=0
        def valorOficialBrutoPlataBs=0

        def valorLMEZinc=0
        def valorLMEPlomo=0
        def valorLMEPlata=0
        def valorLMEZincBs=0
        def valorLMEPlomoBs=0
        def valorLMEPlataBs=0
        def valorLME=0

        def valorOficialBruto=0
        def valorToneladaZinc=0
        def valorToneladaPlomo=0
        def valorToneladaPlata=0
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
        //controlCalidadComplejo = ControlCalidadComplejo.findByRecepcionDeComplejo(recepcion)

        cotizacionDiariaZinc=recepcion.cotizacionDiariaDeMinerales.zinc
        cotizacionDiariaPlomo=recepcion.cotizacionDiariaDeMinerales.plomo
        cotizacionDiariaPlata=recepcion.cotizacionDiariaDeMinerales.plata
        cotizacionQuincenalZinc=recepcion.cotizacionQuincenalDeMinerales.zinc
        cotizacionQuincenalPlomo=recepcion.cotizacionQuincenalDeMinerales.plomo
        cotizacionQuincenalPlata=recepcion.cotizacionQuincenalDeMinerales.plata
        alicuotaZinc=recepcion.alicuota.zinc
        alicuotaPlomo=recepcion.alicuota.plomo
        alicuotaPlata=recepcion.alicuota.plata
        tipoDeCambioComercial=recepcion.cotizacionDeDolar.tipoDeCambioComercial
        tipoDeCambioOficial=recepcion.cotizacionDeDolar.tipoDeCambioOficial
        pesoBruto=recepcion.pesoBruto

        merma = params.porcentajeMermaFinal.toString().toBigDecimal()
//        merma = 0
        humedad = params.porcentajeHumedadFinal.toString().toBigDecimal()
        porcentajeZinc = params.porcentajeZincFinal.toString().toBigDecimal()
        porcentajePlomo = params.porcentajePlomoFinal.toString().toBigDecimal()
        porcentajePlata = params.porcentajePlataFinal.toString().toBigDecimal()

        margen = params.margen.toString().toBigDecimal()

        kilosNetosHumedos=pesoBruto-pesoBruto*merma/100
        kilosNetosSecos=kilosNetosHumedos-kilosNetosHumedos*humedad/100

        cantidadSacos=recepcion.cantidadDeSacos

        //remove rounding of kilos finos
//        kilosFinosZinc=(kilosNetosSecos*porcentajeZinc/100).floatValue().round(2)
//        kilosFinosPlomo=(kilosNetosSecos*porcentajePlomo/100).floatValue().round(2)
//        kilosFinosPlata=(kilosNetosSecos*porcentajePlata/10000).floatValue().round(2)
        kilosFinosZinc=kilosNetosSecos*porcentajeZinc/100
        kilosFinosPlomo=kilosNetosSecos*porcentajePlomo/100
        kilosFinosPlata=kilosNetosSecos*porcentajePlata/10000
        librasFinasZinc = (kilosFinosZinc*2.2046223).floatValue().round(2)
        librasFinasPlomo = (kilosFinosPlomo*2.2046223).floatValue().round(2)
        onzasTroyPlata = (kilosFinosPlata*32.15073).floatValue().round(2)
        
        valorOficialBrutoZinc = (librasFinasZinc*cotizacionQuincenalZinc).floatValue().round(2)
        valorOficialBrutoPlomo = (librasFinasPlomo*cotizacionQuincenalPlomo).floatValue().round(2)
        valorOficialBrutoPlata = (onzasTroyPlata*cotizacionQuincenalPlata).floatValue().round(2)
        valorOficialBrutoZincBs = (valorOficialBrutoZinc*tipoDeCambioOficial).floatValue().round(2)
        valorOficialBrutoPlomoBs = (valorOficialBrutoPlomo*tipoDeCambioOficial).floatValue().round(2)
        valorOficialBrutoPlataBs = (valorOficialBrutoPlata*tipoDeCambioOficial).floatValue().round(2)
        valorOficialBruto = (valorOficialBrutoZincBs+valorOficialBrutoPlomoBs+valorOficialBrutoPlataBs).floatValue().round(2)

        valorLMEZinc = (librasFinasZinc*cotizacionDiariaZinc).floatValue().round(2)
        valorLMEPlomo = (librasFinasPlomo*cotizacionDiariaPlomo).floatValue().round(2)
        valorLMEPlata = (onzasTroyPlata*cotizacionDiariaPlata).floatValue().round(2)
        valorLMEZincBs = (valorLMEZinc*tipoDeCambioComercial).floatValue().round(2)
        valorLMEPlomoBs = (valorLMEPlomo*tipoDeCambioComercial).floatValue().round(2)
        valorLMEPlataBs = (valorLMEPlata*tipoDeCambioComercial).floatValue().round(2)
        valorLME = (valorLMEZincBs+valorLMEPlomoBs+valorLMEPlataBs).floatValue().round(2)

        //determinacion del valor por tonelada
        //valor por tabla
        valorToneladaZinc = tablaPrecios.getValorPorToneladaZinc2(recepcion,params.tablaComplejoId.toString().toLong(),porcentajeZinc)
        valorToneladaPlomo = tablaPrecios.getValorPorToneladaPlomo2(recepcion,params.tablaComplejoId.toString().toLong(),porcentajePlomo)
        valorToneladaPlata = tablaPrecios.getValorPorToneladaPlata2(recepcion,params.tablaComplejoId.toString().toLong(),porcentajePlata)
        valorToneladaTabla = (valorToneladaZinc+valorToneladaPlomo+valorToneladaPlata).floatValue().round(2)
        //valor por precio por LME
//        valorToneladaPrecioPorLME = tablaPrecioPorLME.getValorTonelada(TablaPrecioPorLme.get(params.preciosPorLmeId.toString().toLong()),recepcion,porcentajePlata)
        valorToneladaPrecioPorLME = 0
        //valor por terminos
        valorToneladaTerminos = terminosContrato.getValorToneladaPlomoPlata(recepcion.id,TerminosDeContrato.get(params.terminosDeContratoId.toString().toLong()),porcentajeZinc,porcentajePlomo,porcentajePlata,0).floatValue().round(2)
//        valorToneladaTerminos = 0

        valorToneladaTabla = (valorToneladaTabla + margen).floatValue().round(2)
        valorToneladaPrecioPorLME = (valorToneladaPrecioPorLME + margen).floatValue().round(2)
        valorToneladaTerminos = (valorToneladaTerminos + margen).floatValue().round(2)

//        modoValoracion inList: ["TABLA","PRECIO POR LME","TERMINOS DE CONTRATO"]
        if (params.modoValoracion.toString().equals("TABLA")){
            valorTonelada = valorToneladaTabla
        }
        if (params.modoValoracion.toString().equals("PRECIO POR LME")){
            valorTonelada = valorToneladaPrecioPorLME
        }
        if (params.modoValoracion.toString().equals("TERMINOS DE CONTRATO")){
            valorTonelada = valorToneladaTerminos
        }

        regaliaMinera = (valorOficialBrutoZincBs*alicuotaZinc/100 + valorOficialBrutoPlomoBs*alicuotaPlomo/100 + valorOficialBrutoPlataBs*alicuotaPlata/100).floatValue().round(2)
        valorNetoMineral = (valorTonelada*kilosNetosSecos/1000).floatValue().round(2)
        valorNetoMineralEnBolivianos = (valorNetoMineral*tipoDeCambioComercial).floatValue().round(2)
        bonoCalidad = bonosPorCalidad.bonoCalidadComplejo(recepcion.empresa.id,porcentajePlata)
        bonoIncentivo = bonosPorIncentivo.bonoIncentivoComplejo(recepcion.empresa.id,kilosNetosSecos,porcentajePlata)
        valorDeCompra = valorNetoMineralEnBolivianos+bonoCalidad+bonoIncentivo

        //pLMEtabla = (valorToneladaTabla*kilosNetosSecos*tipoDeCambioComercial/1000)*100/valorOficialBruto
//        pLMEtabla = valorToneladaTabla*100/(porcentajePlata*100*recepcion.cotizacionDiariaDeMinerales.plata/31.1035)
        pLMEprecioLME = (valorToneladaPrecioPorLME*kilosNetosSecos*tipoDeCambioComercial/1000)*100/valorOficialBruto
        pLMEtabla = (valorToneladaTabla*kilosNetosSecos*tipoDeCambioComercial/1000)*100/valorLME
        pLMEterminos = (valorToneladaTerminos*kilosNetosSecos*tipoDeCambioComercial/1000)*100/valorLME
        notificacionValoresTonelada = "POR TABLA:\tVPT = ${formatter.format(valorToneladaTabla)} ► %LME = ${formatter.format(pLMEtabla)}%\nPOR TERMINOS:\tVPT = ${formatter.format(valorToneladaTerminos)} ► %LME = ${formatter.format(pLMEterminos)}%"
//        notificacionValoresTonelada = "POR TABLA:\tVPT = ${formatter.format(valorToneladaTabla)} ► %LME = ${formatter.format(pLMEtabla)}%"
        //buscar si tiene anticipos
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
        mapaRecepcion.put("retenciones", retencionesParaLiquidacion(recepcion.empresa.retenciones, recepcion.empresaSeccion))
        mapaRecepcion.put("cantidadDeSacos", cantidadSacos)
        mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
        mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
        mapaRecepcion.put("tipoDeMineral", tipoDeMineral)
        mapaRecepcion.put("estadoDelLote", recepcion.estadoDelLote)
        mapaRecepcion.put("naturalezaMineral", recepcion.naturalezaMineral)
        mapaRecepcion.put("cotizacionDiariaDeZinc", recepcion.cotizacionDiariaDeMinerales.zinc)
        mapaRecepcion.put("cotizacionQuincenalDeZinc", recepcion.cotizacionQuincenalDeMinerales.zinc)
        mapaRecepcion.put("alicuotaDeZinc", alicuotaZinc)
        mapaRecepcion.put("cotizacionDiariaDePlomo", recepcion.cotizacionDiariaDeMinerales.plomo)
        mapaRecepcion.put("cotizacionQuincenalDePlomo", recepcion.cotizacionQuincenalDeMinerales.plomo)
        mapaRecepcion.put("alicuotaDePlomo", alicuotaPlomo)
        mapaRecepcion.put("cotizacionDiariaDePlata", recepcion.cotizacionDiariaDeMinerales.plata)
        mapaRecepcion.put("cotizacionQuincenalDePlata", recepcion.cotizacionQuincenalDeMinerales.plata)
        mapaRecepcion.put("alicuotaDePlata", alicuotaPlata)
        mapaRecepcion.put("tipoDeCambioOficial", recepcion.cotizacionDeDolar.tipoDeCambioOficial)
        mapaRecepcion.put("tipoDeCambioComercial", recepcion.cotizacionDeDolar.tipoDeCambioComercial)

        //datos de control de calidad
        mapaRecepcion.put("kilosNetosHumedos", kilosNetosHumedos)
        mapaRecepcion.put("kilosNetosSecos", kilosNetosSecos)
        mapaRecepcion.put("kilosFinosZinc", kilosFinosZinc)
        mapaRecepcion.put("kilosFinosPlomo", kilosFinosPlomo)
        mapaRecepcion.put("kilosFinosPlata", kilosFinosPlata)
        mapaRecepcion.put("librasFinasDeZinc", librasFinasZinc)
        mapaRecepcion.put("librasFinasDePlomo", librasFinasPlomo)
        mapaRecepcion.put("onzasTroyDePlata", onzasTroyPlata)
        mapaRecepcion.put("valorOficialBrutoDeZinc", valorOficialBrutoZinc)
        mapaRecepcion.put("valorOficialBrutoDePlomo", valorOficialBrutoPlomo)
        mapaRecepcion.put("valorOficialBrutoDePlata", valorOficialBrutoPlata)
        mapaRecepcion.put("valorOficialBrutoDeZincEnBolivianos", valorOficialBrutoZincBs)
        mapaRecepcion.put("valorOficialBrutoDePlomoEnBolivianos", valorOficialBrutoPlomoBs)
        mapaRecepcion.put("valorOficialBrutoDePlataEnBolivianos", valorOficialBrutoPlataBs)
        mapaRecepcion.put("valorOficialBruto", valorOficialBruto)
        mapaRecepcion.put("valorPorTonelada", valorTonelada)

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

    def recepcionGrupalComplejoJSON() {
        def deposito = Deposito.get(params.depositoId.toLong())
        def loteInicial = Integer.parseInt(params.loteInicial.toString())
        def loteFinal = Integer.parseInt(params.loteFinal.toString())
        def recepcionesComplejo = RecepcionDeComplejo.findAllByDepositoAndLoteComplejoBetweenAndEstadoDelLote(deposito,loteInicial,loteFinal,"NO LIQUIDADO")
        def controlCalidadComplejo = null

        def recepcionesList = []
        //variables para calculos
        def cotizacionQuincenalZinc=0
        def cotizacionQuincenalPlomo=0
        def cotizacionQuincenalPlata=0
        def alicuotaZinc=0
        def alicuotaPlomo=0
        def alicuotaPlata=0
        def tipoDeCambioComercial=0
        def tipoDeCambioOficial=0
        def pesoBruto=0
        def porcentajeMermaPromexbol=1
        def porcentajeHumedadPromexbol=1
        def porcentajeZincPromexbol=1
        def porcentajePlomoPromexbol=1
        def porcentajePlataPromexbol=1
        def controlCalidadId=0
        def tipoDeMineral="COMPLEJO"

        def totalAnticiposContraEntrega=0
        def anticipoPorPagar=0

        def notificacionAnticipo=""

        recepcionesComplejo.each { recepcion ->
            def mapaRecepcion = [:]
            controlCalidadComplejo = ControlCalidadComplejo.findByRecepcionDeComplejo(recepcion)

            cotizacionQuincenalZinc=recepcion.cotizacionQuincenalDeMinerales.zinc
            cotizacionQuincenalPlomo=recepcion.cotizacionQuincenalDeMinerales.plomo
            cotizacionQuincenalPlata=recepcion.cotizacionQuincenalDeMinerales.plata
            alicuotaZinc=recepcion.alicuota.zinc
            alicuotaPlomo=recepcion.alicuota.plomo
            alicuotaPlata=recepcion.alicuota.plata
            tipoDeCambioComercial=recepcion.cotizacionDeDolar.tipoDeCambioComercial
            tipoDeCambioOficial=recepcion.cotizacionDeDolar.tipoDeCambioOficial
            pesoBruto=recepcion.pesoBruto

            if (controlCalidadComplejo){
                porcentajeMermaPromexbol = controlCalidadComplejo.porcentajeMermaPromexbol
                porcentajeHumedadPromexbol = controlCalidadComplejo.porcentajeHumedadPromexbol
                porcentajeZincPromexbol = controlCalidadComplejo.porcentajeZincPromexbol
                porcentajePlomoPromexbol = controlCalidadComplejo.porcentajePlomoPromexbol
                porcentajePlataPromexbol = controlCalidadComplejo.porcentajePlataPromexbol
                controlCalidadId = controlCalidadComplejo.id
            }else{
                porcentajeMermaPromexbol = 1
                porcentajeHumedadPromexbol = 1
                porcentajeZincPromexbol = 1
                porcentajePlomoPromexbol = 1
                porcentajePlataPromexbol = 1
                System.out.println("****** no existe informacion en control de calidad")
            }

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

            mapaRecepcion.put("recepcionId", recepcion.id)
            mapaRecepcion.put("lote", recepcion.toString())
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
            mapaRecepcion.put("cotizacionDiariaDeZinc", recepcion.cotizacionDiariaDeMinerales.zinc)
            mapaRecepcion.put("cotizacionQuincenalDeZinc", recepcion.cotizacionQuincenalDeMinerales.zinc)
            mapaRecepcion.put("alicuotaDeZinc", alicuotaZinc)
            mapaRecepcion.put("cotizacionDiariaDePlomo", recepcion.cotizacionDiariaDeMinerales.plomo)
            mapaRecepcion.put("cotizacionQuincenalDePlomo", recepcion.cotizacionQuincenalDeMinerales.plomo)
            mapaRecepcion.put("alicuotaDePlomo", alicuotaPlomo)
            mapaRecepcion.put("cotizacionDiariaDePlata", recepcion.cotizacionDiariaDeMinerales.plata)
            mapaRecepcion.put("cotizacionQuincenalDePlata", recepcion.cotizacionQuincenalDeMinerales.plata)
            mapaRecepcion.put("alicuotaDePlata", alicuotaPlata)
            mapaRecepcion.put("tipoDeCambioOficial", recepcion.cotizacionDeDolar.tipoDeCambioOficial)
            mapaRecepcion.put("tipoDeCambioComercial", recepcion.cotizacionDeDolar.tipoDeCambioComercial)

            //datos de control de calidad
            mapaRecepcion.put("porcentajeMermaPromexbol", porcentajeMermaPromexbol)
            mapaRecepcion.put("porcentajeHumedadPromexbol", porcentajeHumedadPromexbol)
            mapaRecepcion.put("porcentajeZincPromexbol", porcentajeZincPromexbol)
            mapaRecepcion.put("porcentajePlomoPromexbol", porcentajePlomoPromexbol)
            mapaRecepcion.put("porcentajePlataPromexbol", porcentajePlataPromexbol)
            mapaRecepcion.put("porcentajeMermaCliente", 0)
            mapaRecepcion.put("porcentajeHumedadCliente", 0)
            mapaRecepcion.put("porcentajeZincCliente", 0)
            mapaRecepcion.put("porcentajePlomoCliente", 0)
            mapaRecepcion.put("porcentajePlataCliente", 0)
            mapaRecepcion.put("porcentajeMermaFinal", 0)
            mapaRecepcion.put("porcentajeHumedadFinal", 0)
            mapaRecepcion.put("porcentajeZincFinal", 0)
            mapaRecepcion.put("porcentajePlomoFinal", 0)
            mapaRecepcion.put("porcentajePlataFinal", 0)
            
            mapaRecepcion.put("controlCalidadId", controlCalidadId)
            mapaRecepcion.put("preciosIds", preciosComplejoGrupalIds(recepcion.empresa.id))

            mapaRecepcion.put("modoValoracion", "TABLA")
            //tablaTermino
            mapaRecepcion.put("tablaTermino", "-")
            mapaRecepcion.put("tablaComplejoId", 0)
            mapaRecepcion.put("terminosDeContratoId", 0)
            //mapaRecepcion.put("margen", 0)
            mapaRecepcion.put("margen", -10)

            def hoy = new Date()
            mapaRecepcion.put("fechaDeLiquidacion_day", hoy.toCalendar().get(Calendar.DAY_OF_MONTH))
            mapaRecepcion.put("fechaDeLiquidacion_month", hoy.toCalendar().get(Calendar.MONTH))
            mapaRecepcion.put("fechaDeLiquidacion_year", hoy.toCalendar().get(Calendar.YEAR))

            mapaRecepcion.put("kilosNetosHumedos", 0)
            mapaRecepcion.put("kilosNetosSecos", 0)
            mapaRecepcion.put("kilosFinosZinc", 0)
            mapaRecepcion.put("kilosFinosPlomo", 0)
            mapaRecepcion.put("kilosFinosPlata", 0)
            mapaRecepcion.put("librasFinasDeZinc", 0)
            mapaRecepcion.put("librasFinasDePlomo", 0)
            mapaRecepcion.put("onzasTroyDePlata", 0)
            mapaRecepcion.put("valorOficialBrutoDeZinc", 0)
            mapaRecepcion.put("valorOficialBrutoDePlomo", 0)
            mapaRecepcion.put("valorOficialBrutoDePlata", 0)
            mapaRecepcion.put("valorOficialBrutoDeZincEnBolivianos", 0)
            mapaRecepcion.put("valorOficialBrutoDePlomoEnBolivianos", 0)
            mapaRecepcion.put("valorOficialBrutoDePlataEnBolivianos", 0)
            mapaRecepcion.put("valorOficialBruto", 0)
            mapaRecepcion.put("valorPorTonelada", 0)

            mapaRecepcion.put("valorToneladaTabla", 0)
            mapaRecepcion.put("valorToneladaTerminos", 0)

            mapaRecepcion.put("regaliaMinera", 0)
            mapaRecepcion.put("valorNetoMineral", 0)
            mapaRecepcion.put("valorNetoMineralEnBolivianos", 0)
            mapaRecepcion.put("bonoCalidad", 0)
            mapaRecepcion.put("bonoIncentivo", 0)
            mapaRecepcion.put("valorDeCompra", 0)
            mapaRecepcion.put("totalRetenciones", 0)
            mapaRecepcion.put("totalPagado", 0)
            mapaRecepcion.put("anticipoPorPagar", anticipoPorPagar)
            mapaRecepcion.put("notificacionAnticipo", notificacionAnticipo)
            mapaRecepcion.put("notificacionValoresTonelada", 0)

            mapaRecepcion.put("totalAnticiposContraEntrega", 0)
            mapaRecepcion.put("totalAnticiposContraFuturaEntrega", 0)
            mapaRecepcion.put("adelantoPorLiquidacionProvisional", 0)
            mapaRecepcion.put("totalLiquidoPagable", 0)
            mapaRecepcion.put("totalLiquidoPagableOriginal", 0)
            mapaRecepcion.put("diferenciaLiquidoPagable", 0)
            mapaRecepcion.put("observaciones", "-")
            mapaRecepcion.put("motivoDeModificacion", "-")

            mapaRecepcion.put("detalleLaboratorio1", recepcion.detalleLaboratorio1)
            mapaRecepcion.put("costoLaboratorio1", recepcion.costoLaboratorio1)
            mapaRecepcion.put("detalleLaboratorio2", recepcion.detalleLaboratorio2)
            mapaRecepcion.put("costoLaboratorio2", recepcion.costoLaboratorio2)
            mapaRecepcion.put("detalleLaboratorio3", recepcion.detalleLaboratorio3)
            mapaRecepcion.put("costoLaboratorio3", recepcion.costoLaboratorio3)
            mapaRecepcion.put("detalleLaboratorio4", recepcion.detalleLaboratorio4)
            mapaRecepcion.put("costoLaboratorio4", recepcion.costoLaboratorio4)
            mapaRecepcion.put("totalCostoLaboratorio", recepcion.totalCostoLaboratorio)
            mapaRecepcion.put("totalAnticiposContraFuturaEntrega", getTotalAnticiposContraFuturaEntrega(recepcion.cliente.id))
            recepcionesList.add(mapaRecepcion)
        }
        //render recepcionesList as JSON

        render([lotes: (recepcionesList as JSON).toString()] as JSON)
    }

    def recepcionesPlomoPlataJSON() {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesComplejo = RecepcionDeComplejo.findAllByLotePlomoPlataAndEstadoDelLote(lote,"NO LIQUIDADO")
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
            mapaRecepcion.put("cotizacionDiariaDePlomo", recepcion.cotizacionDiariaDeMinerales.plomo)
            mapaRecepcion.put("cotizacionQuincenalDePlomo", recepcion.cotizacionQuincenalDeMinerales.plomo)
            mapaRecepcion.put("alicuotaDePlomo", alicuotaPlomo)
            mapaRecepcion.put("cotizacionDiariaDePlata", recepcion.cotizacionDiariaDeMinerales.plata)
            mapaRecepcion.put("cotizacionQuincenalDePlata", recepcion.cotizacionQuincenalDeMinerales.plata)
            mapaRecepcion.put("alicuotaDePlata", alicuotaPlata)
            mapaRecepcion.put("tipoDeCambioOficial", recepcion.cotizacionDeDolar.tipoDeCambioOficial)
            mapaRecepcion.put("tipoDeCambioComercial", recepcion.cotizacionDeDolar.tipoDeCambioComercial)

//            mapaRecepcion.put("tablasIds", tablaPrecios.getTablasIds(recepcion.empresa.id))
//            mapaRecepcion.put("terminosIds", terminosContrato.getTerminosIds(recepcion.empresa.id))

            //datos de control de calidad
            mapaRecepcion.put("porcentajeMermaPromexbol", porcentajeMermaPromexbol)
            mapaRecepcion.put("porcentajeHumedadPromexbol", porcentajeHumedadPromexbol)
            mapaRecepcion.put("porcentajePlomoPromexbol", porcentajePlomoPromexbol)
            mapaRecepcion.put("porcentajePlataPromexbol", porcentajePlataPromexbol)
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

    def recepcionesPlomoPlataValoracionJSON() {
        //recepcionDeComplejo.id
        //def lote = Integer.parseInt(params.lote.toString())
        //def recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejoAndEstadoDelLote(lote,"NO LIQUIDADO")
        //def recepcion = RecepcionDeComplejo.findByLoteComplejo(lote)
        def recepcion = RecepcionDeComplejo.get(params.recepcionDeComplejoId.toString().toLong())
        def controlCalidadComplejo = null
        def tablaPrecios = new TablaOrigenCotizacionesComplejoController()
        def tablaPrecioPorLME = new TablaPrecioPorLmeController()
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
        def merma=1
        def humedad=1
        def porcentajePlomo=1
        def porcentajePlata=1
        def tipoDeMineral="COMPLEJO"
        def pesoBrutoSinMerma=0
        def kilosNetosHumedos=0
        def kilosNetosSecos=0
        def kilosFinosPlomo=0
        def kilosFinosPlata=0
        def librasFinasPlomo=0
        def onzasTroyPlata=0
        def valorOficialBrutoPlomo=0
        def valorOficialBrutoPlata=0
        def valorOficialBrutoPlomoBs=0
        def valorOficialBrutoPlataBs=0
        def valorOficialBruto=0
        def valorToneladaPlomo=0
        def valorToneladaPlata=0
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

        def notificacionAnticipo=""
        def notificacionValoresTonelada=""
        def formatter = new DecimalFormat("###.00")

        def mapaRecepcion = [:]
        //controlCalidadComplejo = ControlCalidadComplejo.findByRecepcionDeComplejo(recepcion)

        cotizacionQuincenalPlomo=recepcion.cotizacionQuincenalDeMinerales.plomo
        cotizacionQuincenalPlata=recepcion.cotizacionQuincenalDeMinerales.plata
        alicuotaPlomo=recepcion.alicuota.plomo
        alicuotaPlata=recepcion.alicuota.plata
        tipoDeCambioComercial=recepcion.cotizacionDeDolar.tipoDeCambioComercial
        tipoDeCambioOficial=recepcion.cotizacionDeDolar.tipoDeCambioOficial
        pesoBruto=recepcion.pesoBruto

        merma = params.porcentajeMermaFinal.toString().toBigDecimal()
        humedad = params.porcentajeHumedadFinal.toString().toBigDecimal()
        porcentajePlomo = params.porcentajePlomoFinal.toString().toBigDecimal()
        porcentajePlata = params.porcentajePlataFinal.toString().toBigDecimal()

        margen = params.margen.toString().toBigDecimal()

        kilosNetosHumedos=pesoBruto-pesoBruto*merma/100
        kilosNetosSecos=kilosNetosHumedos-kilosNetosHumedos*humedad/100
        kilosFinosPlomo=kilosNetosSecos*porcentajePlomo/100
        kilosFinosPlata=kilosNetosSecos*porcentajePlata/10000
        librasFinasPlomo = kilosFinosPlomo*2.2046223
        //onzasTroyPlata = kilosFinosPlata*31.1035
        onzasTroyPlata = kilosFinosPlata*32.15073
        valorOficialBrutoPlomo = librasFinasPlomo*cotizacionQuincenalPlomo
        valorOficialBrutoPlata = onzasTroyPlata*cotizacionQuincenalPlata
        //valorOficialBrutoPlomoBs = valorOficialBrutoPlomo*tipoDeCambioComercial
        valorOficialBrutoPlomoBs = valorOficialBrutoPlomo*tipoDeCambioOficial
        valorOficialBrutoPlataBs = valorOficialBrutoPlata*tipoDeCambioOficial
        valorOficialBruto = valorOficialBrutoPlomoBs+valorOficialBrutoPlataBs

        //determinacion del valor por tonelada
        //valor por tabla
        valorToneladaPlomo = tablaPrecios.getValorPorToneladaPlomo2(recepcion,params.tablaComplejoId.toString().toLong(),porcentajePlomo)
        valorToneladaPlata = tablaPrecios.getValorPorToneladaPlata2(recepcion,params.tablaComplejoId.toString().toLong(),porcentajePlata)
        valorToneladaTabla = valorToneladaPlomo+valorToneladaPlata

        valorToneladaPrecioPorLME = tablaPrecioPorLME.getValorTonelada(TablaPrecioPorLme.get(params.preciosPorLmeId.toString().toLong()),recepcion,porcentajePlata)
        //valor por terminos
        valorToneladaTerminos = terminosContrato.getValorToneladaPlomoPlata(recepcion.id,TerminosDeContrato.get(params.terminosDeContratoId.toString().toLong()),0,porcentajePlomo,porcentajePlata,0)

        //se resta 10 al valor por tonelada como proteccion segun Victor
//        if (params.modoValoracion.toString().equals("TABLA")){
//            valorTonelada = valorToneladaTabla + margen
//        }else{
//            //def getValorToneladaPlomoPlata(Long recepcionId, TerminosDeContrato terminosDeContrato, BigDecimal porcentajeZinc, BigDecimal porcentajePlomo, BigDecimal porcentajePlata, BigDecimal porcentajeCobre){
//            valorTonelada = valorToneladaTerminos + margen
//        }
        valorToneladaTabla = valorToneladaTabla + margen
        valorToneladaPrecioPorLME = valorToneladaPrecioPorLME + margen
        valorToneladaTerminos = valorToneladaTerminos + margen
        if (params.modoValoracion.toString().equals("TABLA")){
            valorTonelada = valorToneladaTabla
        }
        if (params.modoValoracion.toString().equals("PRECIO POR LME")){
            valorTonelada = valorToneladaPrecioPorLME
        }
        if (params.modoValoracion.toString().equals("TERMINOS DE CONTRATO")){
            valorTonelada = valorToneladaTerminos
        }

        regaliaMinera = (valorOficialBrutoPlomoBs*alicuotaPlomo/100 + valorOficialBrutoPlataBs*alicuotaPlata/100)
        valorNetoMineral = valorTonelada*kilosNetosSecos/1000
        valorNetoMineralEnBolivianos = valorNetoMineral*tipoDeCambioComercial
        bonoCalidad = bonosPorCalidad.bonoCalidadComplejo(recepcion.empresa.id,porcentajePlata)
        bonoIncentivo = bonosPorIncentivo.bonoIncentivoComplejo(recepcion.empresa.id,kilosNetosSecos,porcentajePlata)
        valorDeCompra = valorNetoMineralEnBolivianos+bonoCalidad+bonoIncentivo

        pLMEtabla = (valorToneladaTabla*kilosNetosSecos*tipoDeCambioComercial/1000)*100/valorOficialBruto
        pLMEprecioLME = (valorToneladaPrecioPorLME*kilosNetosSecos*tipoDeCambioComercial/1000)*100/valorOficialBruto
        pLMEterminos = (valorToneladaTerminos*kilosNetosSecos*tipoDeCambioComercial/1000)*100/valorOficialBruto
//        notificacionValoresTonelada = "POR TABLA:\tVPT = ${valorToneladaTabla}\t%LME = ${formatter.format(pLMEtabla)}%\nPOR TERMINOS:\tVPT = ${valorToneladaTerminos}\t%LME = ${formatter.format(pLMEterminos)}%"
        notificacionValoresTonelada = "POR TABLA:\tVPT = ${formatter.format(valorToneladaTabla)} ► %LME = ${formatter.format(pLMEtabla)}%\nPOR PRECIO POR LME:\tVPT = ${formatter.format(valorToneladaPrecioPorLME)} ► %LME = ${formatter.format(pLMEprecioLME)}%\nPOR TERMINOS:\tVPT = ${formatter.format(valorToneladaTerminos)} ► %LME = ${formatter.format(pLMEterminos)}%"
        //buscar si tiene anticipos
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
        mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
        mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
        mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
        mapaRecepcion.put("tipoDeMineral", tipoDeMineral)
        mapaRecepcion.put("estadoDelLote", recepcion.estadoDelLote)
        mapaRecepcion.put("naturalezaMineral", recepcion.naturalezaMineral)
        mapaRecepcion.put("cotizacionDiariaDePlomo", recepcion.cotizacionDiariaDeMinerales.plomo)
        mapaRecepcion.put("cotizacionQuincenalDePlomo", recepcion.cotizacionQuincenalDeMinerales.plomo)
        mapaRecepcion.put("alicuotaDePlomo", alicuotaPlomo)
        mapaRecepcion.put("cotizacionDiariaDePlata", recepcion.cotizacionDiariaDeMinerales.plata)
        mapaRecepcion.put("cotizacionQuincenalDePlata", recepcion.cotizacionQuincenalDeMinerales.plata)
        mapaRecepcion.put("alicuotaDePlata", alicuotaPlata)
        mapaRecepcion.put("tipoDeCambioOficial", recepcion.cotizacionDeDolar.tipoDeCambioOficial)
        mapaRecepcion.put("tipoDeCambioComercial", recepcion.cotizacionDeDolar.tipoDeCambioComercial)

        //datos de control de calidad
        mapaRecepcion.put("kilosNetosHumedos", kilosNetosHumedos)
        mapaRecepcion.put("kilosNetosSecos", kilosNetosSecos)
        mapaRecepcion.put("kilosFinosPlomo", kilosFinosPlomo)
        mapaRecepcion.put("kilosFinosPlata", kilosFinosPlata)
        mapaRecepcion.put("librasFinasDePlomo", librasFinasPlomo)
        mapaRecepcion.put("onzasTroyDePlata", onzasTroyPlata)
        mapaRecepcion.put("valorOficialBrutoDePlomo", valorOficialBrutoPlomo)
        mapaRecepcion.put("valorOficialBrutoDePlata", valorOficialBrutoPlata)
        mapaRecepcion.put("valorOficialBrutoDePlomoEnBolivianos", valorOficialBrutoPlomoBs)
        mapaRecepcion.put("valorOficialBrutoDePlataEnBolivianos", valorOficialBrutoPlataBs)
        mapaRecepcion.put("valorOficialBruto", valorOficialBruto)
        mapaRecepcion.put("valorPorTonelada", valorTonelada)

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

    def recepcionGrupalPlomoPlataJSON() {
        def deposito = Deposito.get(params.depositoId.toLong())
        def loteInicial = Integer.parseInt(params.loteInicial.toString())
        def loteFinal = Integer.parseInt(params.loteFinal.toString())
        def recepcionesComplejo = RecepcionDeComplejo.findAllByDepositoAndLotePlomoPlataBetweenAndEstadoDelLote(deposito,loteInicial,loteFinal,"NO LIQUIDADO")
        def controlCalidadPlomoPlata = null

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
        def tipoDeMineral="COMPLEJO"

        def totalAnticiposContraEntrega=0
        def anticipoPorPagar=0

        def notificacionAnticipo=""

        recepcionesComplejo.each { recepcion ->
            def mapaRecepcion = [:]
            controlCalidadPlomoPlata = ControlCalidadPlomoPlata.findByRecepcionDeComplejo(recepcion)

            cotizacionQuincenalPlomo=recepcion.cotizacionQuincenalDeMinerales.plomo
            cotizacionQuincenalPlata=recepcion.cotizacionQuincenalDeMinerales.plata
            alicuotaPlomo=recepcion.alicuota.plomo
            alicuotaPlata=recepcion.alicuota.plata
            tipoDeCambioComercial=recepcion.cotizacionDeDolar.tipoDeCambioComercial
            tipoDeCambioOficial=recepcion.cotizacionDeDolar.tipoDeCambioOficial
            pesoBruto=recepcion.pesoBruto

            if (controlCalidadPlomoPlata){
                porcentajeMermaPromexbol = controlCalidadPlomoPlata.porcentajeMermaPromexbol
                porcentajeHumedadPromexbol = controlCalidadPlomoPlata.porcentajeHumedadPromexbol
                porcentajePlomoPromexbol = controlCalidadPlomoPlata.porcentajePlomoPromexbol
                porcentajePlataPromexbol = controlCalidadPlomoPlata.porcentajePlataPromexbol
                controlCalidadId = controlCalidadPlomoPlata.id
            }else{
                porcentajeMermaPromexbol = 1
                porcentajeHumedadPromexbol = 1
                porcentajePlomoPromexbol = 1
                porcentajePlataPromexbol = 1
                System.out.println("****** no existe informacion en control de calidad")
            }

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

            mapaRecepcion.put("recepcionId", recepcion.id)
            mapaRecepcion.put("lote", recepcion.toString())
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
            mapaRecepcion.put("cotizacionDiariaDeZinc", recepcion.cotizacionDiariaDeMinerales.zinc)
            mapaRecepcion.put("cotizacionQuincenalDeZinc", recepcion.cotizacionQuincenalDeMinerales.zinc)
            mapaRecepcion.put("cotizacionDiariaDePlomo", recepcion.cotizacionDiariaDeMinerales.plomo)
            mapaRecepcion.put("cotizacionQuincenalDePlomo", recepcion.cotizacionQuincenalDeMinerales.plomo)
            mapaRecepcion.put("alicuotaDePlomo", alicuotaPlomo)
            mapaRecepcion.put("cotizacionDiariaDePlata", recepcion.cotizacionDiariaDeMinerales.plata)
            mapaRecepcion.put("cotizacionQuincenalDePlata", recepcion.cotizacionQuincenalDeMinerales.plata)
            mapaRecepcion.put("alicuotaDePlata", alicuotaPlata)
            mapaRecepcion.put("tipoDeCambioOficial", recepcion.cotizacionDeDolar.tipoDeCambioOficial)
            mapaRecepcion.put("tipoDeCambioComercial", recepcion.cotizacionDeDolar.tipoDeCambioComercial)

            //datos de control de calidad
            mapaRecepcion.put("porcentajeMermaPromexbol", porcentajeMermaPromexbol)
            mapaRecepcion.put("porcentajeHumedadPromexbol", porcentajeHumedadPromexbol)
            mapaRecepcion.put("porcentajePlomoPromexbol", porcentajePlomoPromexbol)
            mapaRecepcion.put("porcentajePlataPromexbol", porcentajePlataPromexbol)
            mapaRecepcion.put("porcentajeMermaCliente", 0)
            mapaRecepcion.put("porcentajeHumedadCliente", 0)
            mapaRecepcion.put("porcentajeZincCliente", 0)
            mapaRecepcion.put("porcentajePlomoCliente", 0)
            mapaRecepcion.put("porcentajePlataCliente", 0)
            mapaRecepcion.put("porcentajeMermaFinal", 0)
            mapaRecepcion.put("porcentajeHumedadFinal", 0)
            mapaRecepcion.put("porcentajeZincFinal", 0)
            mapaRecepcion.put("porcentajePlomoFinal", 0)
            mapaRecepcion.put("porcentajePlataFinal", 0)

            mapaRecepcion.put("controlCalidadId", controlCalidadId)
            mapaRecepcion.put("preciosIds", preciosComplejoGrupalIds(recepcion.empresa.id))

            mapaRecepcion.put("modoValoracion", "TABLA")
            //tablaTermino
            mapaRecepcion.put("tablaTermino", "-")
            mapaRecepcion.put("tablaComplejoId", 0)
            mapaRecepcion.put("terminosDeContratoId", 0)
            //mapaRecepcion.put("margen", 0)
            mapaRecepcion.put("margen", -10)

            def hoy = new Date()
            mapaRecepcion.put("fechaDeLiquidacion_day", hoy.toCalendar().get(Calendar.DAY_OF_MONTH))
            mapaRecepcion.put("fechaDeLiquidacion_month", hoy.toCalendar().get(Calendar.MONTH))
            mapaRecepcion.put("fechaDeLiquidacion_year", hoy.toCalendar().get(Calendar.YEAR))

            mapaRecepcion.put("kilosNetosHumedos", 0)
            mapaRecepcion.put("kilosNetosSecos", 0)
            mapaRecepcion.put("kilosFinosPlomo", 0)
            mapaRecepcion.put("kilosFinosPlata", 0)
            mapaRecepcion.put("librasFinasDePlomo", 0)
            mapaRecepcion.put("onzasTroyDePlata", 0)
            mapaRecepcion.put("valorOficialBrutoDePlomo", 0)
            mapaRecepcion.put("valorOficialBrutoDePlata", 0)
            mapaRecepcion.put("valorOficialBrutoDePlomoEnBolivianos", 0)
            mapaRecepcion.put("valorOficialBrutoDePlataEnBolivianos", 0)
            mapaRecepcion.put("valorOficialBruto", 0)
            mapaRecepcion.put("valorPorTonelada", 0)

            mapaRecepcion.put("valorToneladaTabla", 0)
            mapaRecepcion.put("valorToneladaTerminos", 0)

            mapaRecepcion.put("regaliaMinera", 0)
            mapaRecepcion.put("valorNetoMineral", 0)
            mapaRecepcion.put("valorNetoMineralEnBolivianos", 0)
            mapaRecepcion.put("bonoCalidad", 0)
            mapaRecepcion.put("bonoIncentivo", 0)
            mapaRecepcion.put("valorDeCompra", 0)
            mapaRecepcion.put("totalRetenciones", 0)
            mapaRecepcion.put("totalPagado", 0)
            mapaRecepcion.put("anticipoPorPagar", anticipoPorPagar)
            mapaRecepcion.put("notificacionAnticipo", notificacionAnticipo)
            mapaRecepcion.put("notificacionValoresTonelada", 0)

            mapaRecepcion.put("totalAnticiposContraEntrega", 0)
            mapaRecepcion.put("totalAnticiposContraFuturaEntrega", 0)
            mapaRecepcion.put("adelantoPorLiquidacionProvisional", 0)
            mapaRecepcion.put("totalLiquidoPagable", 0)
            mapaRecepcion.put("totalLiquidoPagableOriginal", 0)
            mapaRecepcion.put("diferenciaLiquidoPagable", 0)
            mapaRecepcion.put("observaciones", "-")
            mapaRecepcion.put("motivoDeModificacion", "-")

            mapaRecepcion.put("detalleLaboratorio1", recepcion.detalleLaboratorio1)
            mapaRecepcion.put("costoLaboratorio1", recepcion.costoLaboratorio1)
            mapaRecepcion.put("detalleLaboratorio2", recepcion.detalleLaboratorio2)
            mapaRecepcion.put("costoLaboratorio2", recepcion.costoLaboratorio2)
            mapaRecepcion.put("detalleLaboratorio3", recepcion.detalleLaboratorio3)
            mapaRecepcion.put("costoLaboratorio3", recepcion.costoLaboratorio3)
            mapaRecepcion.put("detalleLaboratorio4", recepcion.detalleLaboratorio4)
            mapaRecepcion.put("costoLaboratorio4", recepcion.costoLaboratorio4)
            mapaRecepcion.put("totalCostoLaboratorio", recepcion.totalCostoLaboratorio)
            mapaRecepcion.put("totalAnticiposContraFuturaEntrega", getTotalAnticiposContraFuturaEntrega(recepcion.cliente.id))
            recepcionesList.add(mapaRecepcion)
        }
        //render recepcionesList as JSON

        render([lotes: (recepcionesList as JSON).toString()] as JSON)
    }

    def recepcionesZincPlataJSON() {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesComplejo = RecepcionDeComplejo.findAllByLoteZincPlataAndEstadoDelLote(lote,"NO LIQUIDADO")
        def controlCalidadComplejo = null
        def tablaPrecios = new TablaOrigenCotizacionesComplejoController()
        def terminosContrato = new TerminosDeContratoController()
        def bonosPorCalidad = new BonoCalidadController()
        def bonosPorIncentivo = new BonoIncentivoController()
        def recepcionesList = []
        //variables para calculos
        def cotizacionQuincenalZinc=0
        def cotizacionQuincenalPlata=0
        def alicuotaZinc=0
        def alicuotaPlata=0
        def tipoDeCambioComercial=0
        def tipoDeCambioOficial=0
        def pesoBruto=0
        def porcentajeMermaPromexbol=1
        def porcentajeHumedadPromexbol=1
        def porcentajeZincPromexbol=1
        def porcentajePlataPromexbol=1
        def controlCalidadId=0
        def tipoDeMineral="ZN-AG"

        def totalAnticiposContraEntrega=0
        def anticipoPorPagar=0

        def notificacionAnticipo=""

        recepcionesComplejo.each { recepcion ->
            def mapaRecepcion = [:]
            controlCalidadComplejo = ControlCalidadZincPlata.findByRecepcionDeComplejo(recepcion)

            cotizacionQuincenalZinc=recepcion.cotizacionQuincenalDeMinerales.zinc
            cotizacionQuincenalPlata=recepcion.cotizacionQuincenalDeMinerales.plata
            alicuotaZinc=recepcion.alicuota.zinc
            alicuotaPlata=recepcion.alicuota.plata
            tipoDeCambioComercial=recepcion.cotizacionDeDolar.tipoDeCambioComercial
            tipoDeCambioOficial=recepcion.cotizacionDeDolar.tipoDeCambioOficial
            pesoBruto=recepcion.pesoBruto

            if (controlCalidadComplejo){
                porcentajeMermaPromexbol = controlCalidadComplejo.porcentajeMermaPromexbol
                porcentajeHumedadPromexbol = controlCalidadComplejo.porcentajeHumedadPromexbol
                porcentajeZincPromexbol = controlCalidadComplejo.porcentajeZincPromexbol
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
            mapaRecepcion.put("alicuotaDeZinc", alicuotaZinc)
            mapaRecepcion.put("cotizacionDiariaDePlata", recepcion.cotizacionDiariaDeMinerales.plata)
            mapaRecepcion.put("cotizacionQuincenalDePlata", recepcion.cotizacionQuincenalDeMinerales.plata)
            mapaRecepcion.put("alicuotaDePlata", alicuotaPlata)
            mapaRecepcion.put("tipoDeCambioOficial", recepcion.cotizacionDeDolar.tipoDeCambioOficial)
            mapaRecepcion.put("tipoDeCambioComercial", recepcion.cotizacionDeDolar.tipoDeCambioComercial)

//            mapaRecepcion.put("tablasIds", tablaPrecios.getTablasIds(recepcion.empresa.id))
//            mapaRecepcion.put("terminosIds", terminosContrato.getTerminosIds(recepcion.empresa.id))

            //datos de control de calidad
            mapaRecepcion.put("porcentajeMermaPromexbol", porcentajeMermaPromexbol)
            mapaRecepcion.put("porcentajeHumedadPromexbol", porcentajeHumedadPromexbol)
            mapaRecepcion.put("porcentajeZincPromexbol", porcentajeZincPromexbol)
            mapaRecepcion.put("porcentajePlataPromexbol", porcentajePlataPromexbol)
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

    def recepcionesZincPlataValoracionJSON() {
        //recepcionDeComplejo.id
        //def lote = Integer.parseInt(params.lote.toString())
        //def recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejoAndEstadoDelLote(lote,"NO LIQUIDADO")
        //def recepcion = RecepcionDeComplejo.findByLoteComplejo(lote)
        def recepcion = RecepcionDeComplejo.get(params.recepcionDeComplejoId.toString().toLong())
        def controlCalidadComplejo = null
        def tablaPrecios = new TablaOrigenCotizacionesComplejoController()
        def tablaPrecioPorLME = new TablaPrecioPorLmeController()
        def terminosContrato = new TerminosDeContratoController()
        def bonosPorCalidad = new BonoCalidadController()
        def bonosPorIncentivo = new BonoIncentivoController()
        def recepcionesList = []
        //variables para calculos
        def cotizacionQuincenalZinc=0
        def cotizacionQuincenalPlata=0
        def alicuotaZinc=0
        def alicuotaPlata=0
        def tipoDeCambioComercial=0
        def tipoDeCambioOficial=0
        def pesoBruto=0
        def merma=1
        def humedad=1
        def porcentajeZinc=1
        def porcentajePlata=1
        def tipoDeMineral="COMPLEJO"
        def pesoBrutoSinMerma=0
        def kilosNetosHumedos=0
        def kilosNetosSecos=0
        def kilosFinosZinc=0
        def kilosFinosPlata=0
        def librasFinasZinc=0
        def onzasTroyPlata=0
        def valorOficialBrutoZinc=0
        def valorOficialBrutoPlata=0
        def valorOficialBrutoZincBs=0
        def valorOficialBrutoPlataBs=0
        def valorOficialBruto=0
        def valorToneladaZinc=0
        def valorToneladaPlata=0
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

        def notificacionAnticipo=""
        def notificacionValoresTonelada=""
        def formatter = new DecimalFormat("###.00")

        def mapaRecepcion = [:]
        //controlCalidadComplejo = ControlCalidadComplejo.findByRecepcionDeComplejo(recepcion)

        cotizacionQuincenalZinc=recepcion.cotizacionQuincenalDeMinerales.zinc
        cotizacionQuincenalPlata=recepcion.cotizacionQuincenalDeMinerales.plata
        alicuotaZinc=recepcion.alicuota.zinc
        alicuotaPlata=recepcion.alicuota.plata
        tipoDeCambioComercial=recepcion.cotizacionDeDolar.tipoDeCambioComercial
        tipoDeCambioOficial=recepcion.cotizacionDeDolar.tipoDeCambioOficial
        pesoBruto=recepcion.pesoBruto

        merma = params.porcentajeMermaFinal.toString().toBigDecimal()
        humedad = params.porcentajeHumedadFinal.toString().toBigDecimal()
        porcentajeZinc = params.porcentajeZincFinal.toString().toBigDecimal()
        porcentajePlata = params.porcentajePlataFinal.toString().toBigDecimal()

        margen = params.margen.toString().toBigDecimal()

        kilosNetosHumedos=pesoBruto-pesoBruto*merma/100
        kilosNetosSecos=kilosNetosHumedos-kilosNetosHumedos*humedad/100
        kilosFinosZinc=kilosNetosSecos*porcentajeZinc/100
        kilosFinosPlata=kilosNetosSecos*porcentajePlata/10000
        librasFinasZinc = kilosFinosZinc*2.2046223
        onzasTroyPlata = kilosFinosPlata*32.15073
        valorOficialBrutoZinc = librasFinasZinc*cotizacionQuincenalZinc
        valorOficialBrutoPlata = onzasTroyPlata*cotizacionQuincenalPlata
        valorOficialBrutoZincBs = valorOficialBrutoZinc*tipoDeCambioOficial
        valorOficialBrutoPlataBs = valorOficialBrutoPlata*tipoDeCambioOficial
        valorOficialBruto = valorOficialBrutoZincBs+valorOficialBrutoPlataBs

        //determinacion del valor por tonelada
        //valor por tabla
        valorToneladaZinc = tablaPrecios.getValorPorToneladaZinc2(recepcion,params.tablaComplejoId.toString().toLong(),porcentajeZinc)
        valorToneladaPlata = tablaPrecios.getValorPorToneladaPlata2(recepcion,params.tablaComplejoId.toString().toLong(),porcentajePlata)
        valorToneladaTabla = valorToneladaZinc+valorToneladaPlata
        //valor por precio por LME
        valorToneladaPrecioPorLME = tablaPrecioPorLME.getValorTonelada(TablaPrecioPorLme.get(params.preciosPorLmeId.toString().toLong()),recepcion,porcentajePlata)
        //valor por terminos
        valorToneladaTerminos = terminosContrato.getValorToneladaPlomoPlata(recepcion.id,TerminosDeContrato.get(params.terminosDeContratoId.toString().toLong()),porcentajeZinc,0,porcentajePlata,0)

        //se resta 10 al valor por tonelada como proteccion segun Victor
//        if (params.modoValoracion.toString().equals("TABLA")){
//            valorTonelada = valorToneladaTabla + margen
//        }else{
//            //def getValorToneladaZincPlata(Long recepcionId, TerminosDeContrato terminosDeContrato, BigDecimal porcentajeZinc, BigDecimal porcentajeZinc, BigDecimal porcentajePlata, BigDecimal porcentajeCobre){
//            valorTonelada = valorToneladaTerminos + margen
//        }
        valorToneladaTabla = valorToneladaTabla + margen
        valorToneladaPrecioPorLME = valorToneladaPrecioPorLME + margen
        valorToneladaTerminos = valorToneladaTerminos + margen
        if (params.modoValoracion.toString().equals("TABLA")){
            valorTonelada = valorToneladaTabla
        }
        if (params.modoValoracion.toString().equals("PRECIO POR LME")){
            valorTonelada = valorToneladaPrecioPorLME
        }
        if (params.modoValoracion.toString().equals("TERMINOS DE CONTRATO")){
            valorTonelada = valorToneladaTerminos
        }

        regaliaMinera = (valorOficialBrutoZincBs*alicuotaZinc/100 + valorOficialBrutoPlataBs*alicuotaPlata/100)
        valorNetoMineral = valorTonelada*kilosNetosSecos/1000
        valorNetoMineralEnBolivianos = valorNetoMineral*tipoDeCambioComercial
        bonoCalidad = bonosPorCalidad.bonoCalidadComplejo(recepcion.empresa.id,porcentajePlata)
        bonoIncentivo = bonosPorIncentivo.bonoIncentivoComplejo(recepcion.empresa.id,kilosNetosSecos,porcentajePlata)
        valorDeCompra = valorNetoMineralEnBolivianos+bonoCalidad+bonoIncentivo

        pLMEtabla = (valorToneladaTabla*kilosNetosSecos*tipoDeCambioComercial/1000)*100/valorOficialBruto
        pLMEprecioLME = (valorToneladaPrecioPorLME*kilosNetosSecos*tipoDeCambioComercial/1000)*100/valorOficialBruto
        pLMEterminos = (valorToneladaTerminos*kilosNetosSecos*tipoDeCambioComercial/1000)*100/valorOficialBruto
//        notificacionValoresTonelada = "POR TABLA:\tVPT = ${valorToneladaTabla}\t%LME = ${formatter.format(pLMEtabla)}%\nPOR TERMINOS:\tVPT = ${valorToneladaTerminos}\t%LME = ${formatter.format(pLMEterminos)}%"
        notificacionValoresTonelada = "POR TABLA:\tVPT = ${formatter.format(valorToneladaTabla)} ► %LME = ${formatter.format(pLMEtabla)}%\nPOR PRECIO POR LME:\tVPT = ${formatter.format(valorToneladaPrecioPorLME)} ► %LME = ${formatter.format(pLMEprecioLME)}%\nPOR TERMINOS:\tVPT = ${formatter.format(valorToneladaTerminos)} ► %LME = ${formatter.format(pLMEterminos)}%"
        //buscar si tiene anticipos
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
        mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
        mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
        mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
        mapaRecepcion.put("tipoDeMineral", tipoDeMineral)
        mapaRecepcion.put("estadoDelLote", recepcion.estadoDelLote)
        mapaRecepcion.put("naturalezaMineral", recepcion.naturalezaMineral)
        mapaRecepcion.put("cotizacionDiariaDeZinc", recepcion.cotizacionDiariaDeMinerales.zinc)
        mapaRecepcion.put("cotizacionQuincenalDeZinc", recepcion.cotizacionQuincenalDeMinerales.zinc)
        mapaRecepcion.put("alicuotaDeZinc", alicuotaZinc)
        mapaRecepcion.put("cotizacionDiariaDePlata", recepcion.cotizacionDiariaDeMinerales.plata)
        mapaRecepcion.put("cotizacionQuincenalDePlata", recepcion.cotizacionQuincenalDeMinerales.plata)
        mapaRecepcion.put("alicuotaDePlata", alicuotaPlata)
        mapaRecepcion.put("tipoDeCambioOficial", recepcion.cotizacionDeDolar.tipoDeCambioOficial)
        mapaRecepcion.put("tipoDeCambioComercial", recepcion.cotizacionDeDolar.tipoDeCambioComercial)

        //datos de control de calidad
        mapaRecepcion.put("kilosNetosHumedos", kilosNetosHumedos)
        mapaRecepcion.put("kilosNetosSecos", kilosNetosSecos)
        mapaRecepcion.put("kilosFinosZinc", kilosFinosZinc)
        mapaRecepcion.put("kilosFinosPlata", kilosFinosPlata)
        mapaRecepcion.put("librasFinasDeZinc", librasFinasZinc)
        mapaRecepcion.put("onzasTroyDePlata", onzasTroyPlata)
        mapaRecepcion.put("valorOficialBrutoDeZinc", valorOficialBrutoZinc)
        mapaRecepcion.put("valorOficialBrutoDePlata", valorOficialBrutoPlata)
        mapaRecepcion.put("valorOficialBrutoDeZincEnBolivianos", valorOficialBrutoZincBs)
        mapaRecepcion.put("valorOficialBrutoDePlataEnBolivianos", valorOficialBrutoPlataBs)
        mapaRecepcion.put("valorOficialBruto", valorOficialBruto)
        mapaRecepcion.put("valorPorTonelada", valorTonelada)

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

    def retencionesParaLiquidacion(String retenciones, EmpresaSeccion empresaSeccion) {
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

        //AGREGAR TODAS LAS RETENCIONES MENOS LAS QUE APLIQUEN A SECCIONES
        retencionesJSON.each {
            def retencionesEmpresaJSON = new JSONObject()
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
            if(!it.getAt("DESCRIPCION").toString().contains("SECCION"))
                retencionesEmpresaListJSON.put(retencionesEmpresaJSON)
        }
        //AGREGAR LA RETENCION DE LA SECCION DE EMPRESASECCION SIEMPRE QUE CONTENGA ALGUN VALOR
        retencionesJSON.each {
            def retencionesEmpresaJSON = new JSONObject()
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
            if(empresaSeccion && it.getAt("DESCRIPCION").toString().equals(empresaSeccion.nombreSeccion))
                retencionesEmpresaListJSON.put(retencionesEmpresaJSON)
        }
        return retencionesEmpresaListJSON.toString()
        //return retencionesJSON.toString()
    }

    def recepcionGrupalZincPlataJSON() {
        def deposito = Deposito.get(params.depositoId.toLong())
        def loteInicial = Integer.parseInt(params.loteInicial.toString())
        def loteFinal = Integer.parseInt(params.loteFinal.toString())
        def recepcionesComplejo = RecepcionDeComplejo.findAllByDepositoAndLoteZincPlataBetweenAndEstadoDelLote(deposito,loteInicial,loteFinal,"NO LIQUIDADO")
        def controlCalidadZincPlata = null

        def recepcionesList = []
        //variables para calculos
        def cotizacionQuincenalZinc=0
        def cotizacionQuincenalPlata=0
        def alicuotaZinc=0
        def alicuotaPlata=0
        def tipoDeCambioComercial=0
        def tipoDeCambioOficial=0
        def pesoBruto=0
        def porcentajeMermaPromexbol=1
        def porcentajeHumedadPromexbol=1
        def porcentajeZincPromexbol=1
        def porcentajePlataPromexbol=1
        def controlCalidadId=0
        def tipoDeMineral="COMPLEJO"

        def totalAnticiposContraEntrega=0
        def anticipoPorPagar=0

        def notificacionAnticipo=""

        recepcionesComplejo.each { recepcion ->
            def mapaRecepcion = [:]
            controlCalidadZincPlata = ControlCalidadZincPlata.findByRecepcionDeComplejo(recepcion)

            cotizacionQuincenalZinc=recepcion.cotizacionQuincenalDeMinerales.plomo
            cotizacionQuincenalPlata=recepcion.cotizacionQuincenalDeMinerales.plata
            alicuotaZinc=recepcion.alicuota.plomo
            alicuotaPlata=recepcion.alicuota.plata
            tipoDeCambioComercial=recepcion.cotizacionDeDolar.tipoDeCambioComercial
            tipoDeCambioOficial=recepcion.cotizacionDeDolar.tipoDeCambioOficial
            pesoBruto=recepcion.pesoBruto

            if (controlCalidadZincPlata){
                porcentajeMermaPromexbol = controlCalidadZincPlata.porcentajeMermaPromexbol
                porcentajeHumedadPromexbol = controlCalidadZincPlata.porcentajeHumedadPromexbol
                porcentajeZincPromexbol = controlCalidadZincPlata.porcentajeZincPromexbol
                porcentajePlataPromexbol = controlCalidadZincPlata.porcentajePlataPromexbol
                controlCalidadId = controlCalidadZincPlata.id
            }else{
                porcentajeMermaPromexbol = 1
                porcentajeHumedadPromexbol = 1
                porcentajeZincPromexbol = 1
                porcentajePlataPromexbol = 1
                System.out.println("****** no existe informacion en control de calidad")
            }

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

            mapaRecepcion.put("recepcionId", recepcion.id)
            mapaRecepcion.put("lote", recepcion.toString())
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
            mapaRecepcion.put("cotizacionDiariaDeZinc", recepcion.cotizacionDiariaDeMinerales.zinc)
            mapaRecepcion.put("cotizacionQuincenalDeZinc", recepcion.cotizacionQuincenalDeMinerales.zinc)
            mapaRecepcion.put("alicuotaDeZinc", alicuotaZinc)
            mapaRecepcion.put("cotizacionDiariaDePlata", recepcion.cotizacionDiariaDeMinerales.plata)
            mapaRecepcion.put("cotizacionQuincenalDePlata", recepcion.cotizacionQuincenalDeMinerales.plata)
            mapaRecepcion.put("alicuotaDePlata", alicuotaPlata)
            mapaRecepcion.put("tipoDeCambioOficial", recepcion.cotizacionDeDolar.tipoDeCambioOficial)
            mapaRecepcion.put("tipoDeCambioComercial", recepcion.cotizacionDeDolar.tipoDeCambioComercial)

            //datos de control de calidad
            mapaRecepcion.put("porcentajeMermaPromexbol", porcentajeMermaPromexbol)
            mapaRecepcion.put("porcentajeHumedadPromexbol", porcentajeHumedadPromexbol)
            mapaRecepcion.put("porcentajeZincPromexbol", porcentajeZincPromexbol)
            mapaRecepcion.put("porcentajePlataPromexbol", porcentajePlataPromexbol)
            mapaRecepcion.put("porcentajeMermaCliente", 0)
            mapaRecepcion.put("porcentajeHumedadCliente", 0)
            mapaRecepcion.put("porcentajeZincCliente", 0)
            mapaRecepcion.put("porcentajeZincCliente", 0)
            mapaRecepcion.put("porcentajePlataCliente", 0)
            mapaRecepcion.put("porcentajeMermaFinal", 0)
            mapaRecepcion.put("porcentajeHumedadFinal", 0)
            mapaRecepcion.put("porcentajeZincFinal", 0)
            mapaRecepcion.put("porcentajeZincFinal", 0)
            mapaRecepcion.put("porcentajePlataFinal", 0)

            mapaRecepcion.put("controlCalidadId", controlCalidadId)
            mapaRecepcion.put("preciosIds", preciosComplejoGrupalIds(recepcion.empresa.id))

            mapaRecepcion.put("modoValoracion", "TABLA")
            //tablaTermino
            mapaRecepcion.put("tablaTermino", "-")
            mapaRecepcion.put("tablaComplejoId", 0)
            mapaRecepcion.put("terminosDeContratoId", 0)
            //mapaRecepcion.put("margen", 0)
            mapaRecepcion.put("margen", -10)

            def hoy = new Date()
            mapaRecepcion.put("fechaDeLiquidacion_day", hoy.toCalendar().get(Calendar.DAY_OF_MONTH))
            mapaRecepcion.put("fechaDeLiquidacion_month", hoy.toCalendar().get(Calendar.MONTH))
            mapaRecepcion.put("fechaDeLiquidacion_year", hoy.toCalendar().get(Calendar.YEAR))

            mapaRecepcion.put("kilosNetosHumedos", 0)
            mapaRecepcion.put("kilosNetosSecos", 0)
            mapaRecepcion.put("kilosFinosZinc", 0)
            mapaRecepcion.put("kilosFinosPlata", 0)
            mapaRecepcion.put("librasFinasDeZinc", 0)
            mapaRecepcion.put("onzasTroyDePlata", 0)
            mapaRecepcion.put("valorOficialBrutoDeZinc", 0)
            mapaRecepcion.put("valorOficialBrutoDePlata", 0)
            mapaRecepcion.put("valorOficialBrutoDeZincEnBolivianos", 0)
            mapaRecepcion.put("valorOficialBrutoDePlataEnBolivianos", 0)
            mapaRecepcion.put("valorOficialBruto", 0)
            mapaRecepcion.put("valorPorTonelada", 0)

            mapaRecepcion.put("valorToneladaTabla", 0)
            mapaRecepcion.put("valorToneladaTerminos", 0)

            mapaRecepcion.put("regaliaMinera", 0)
            mapaRecepcion.put("valorNetoMineral", 0)
            mapaRecepcion.put("valorNetoMineralEnBolivianos", 0)
            mapaRecepcion.put("bonoCalidad", 0)
            mapaRecepcion.put("bonoIncentivo", 0)
            mapaRecepcion.put("valorDeCompra", 0)
            mapaRecepcion.put("totalRetenciones", 0)
            mapaRecepcion.put("totalPagado", 0)
            mapaRecepcion.put("anticipoPorPagar", anticipoPorPagar)
            mapaRecepcion.put("notificacionAnticipo", notificacionAnticipo)
            mapaRecepcion.put("notificacionValoresTonelada", 0)

            mapaRecepcion.put("totalAnticiposContraEntrega", 0)
            mapaRecepcion.put("totalAnticiposContraFuturaEntrega", 0)
            mapaRecepcion.put("adelantoPorLiquidacionProvisional", 0)
            mapaRecepcion.put("totalLiquidoPagable", 0)
            mapaRecepcion.put("totalLiquidoPagableOriginal", 0)
            mapaRecepcion.put("diferenciaLiquidoPagable", 0)
            mapaRecepcion.put("observaciones", "-")
            mapaRecepcion.put("motivoDeModificacion", "-")

            mapaRecepcion.put("detalleLaboratorio1", recepcion.detalleLaboratorio1)
            mapaRecepcion.put("costoLaboratorio1", recepcion.costoLaboratorio1)
            mapaRecepcion.put("detalleLaboratorio2", recepcion.detalleLaboratorio2)
            mapaRecepcion.put("costoLaboratorio2", recepcion.costoLaboratorio2)
            mapaRecepcion.put("detalleLaboratorio3", recepcion.detalleLaboratorio3)
            mapaRecepcion.put("costoLaboratorio3", recepcion.costoLaboratorio3)
            mapaRecepcion.put("detalleLaboratorio4", recepcion.detalleLaboratorio4)
            mapaRecepcion.put("costoLaboratorio4", recepcion.costoLaboratorio4)
            mapaRecepcion.put("totalCostoLaboratorio", recepcion.totalCostoLaboratorio)
            mapaRecepcion.put("totalAnticiposContraFuturaEntrega", getTotalAnticiposContraFuturaEntrega(recepcion.cliente.id))
            recepcionesList.add(mapaRecepcion)
        }
        //render recepcionesList as JSON

        render([lotes: (recepcionesList as JSON).toString()] as JSON)
    }

    def recepcionesCobrePlataJSON() {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesComplejo = RecepcionDeComplejo.findAllByLoteCobrePlataAndEstadoDelLote(lote,"NO LIQUIDADO")
        def controlCalidadComplejo = null
        def tablaPrecios = new TablaOrigenCotizacionesComplejoController()
        def terminosContrato = new TerminosDeContratoController()
        def bonosPorCalidad = new BonoCalidadController()
        def bonosPorIncentivo = new BonoIncentivoController()
        def recepcionesList = []
        //variables para calculos
        def cotizacionQuincenalCobre=0
        def cotizacionQuincenalPlata=0
        def alicuotaCobre=0
        def alicuotaPlata=0
        def tipoDeCambioComercial=0
        def tipoDeCambioOficial=0
        def pesoBruto=0
        def porcentajeMermaPromexbol=1
        def porcentajeHumedadPromexbol=1
        def porcentajeCobrePromexbol=1
        def porcentajePlataPromexbol=1
        def controlCalidadId=0
        def tipoDeMineral="CU-AG"

        def totalAnticiposContraEntrega=0
        def anticipoPorPagar=0

        def notificacionAnticipo=""

        recepcionesComplejo.each { recepcion ->
            def mapaRecepcion = [:]
            controlCalidadComplejo = ControlCalidadCobrePlata.findByRecepcionDeComplejo(recepcion)

            cotizacionQuincenalCobre=recepcion.cotizacionQuincenalDeMinerales.cobre
            cotizacionQuincenalPlata=recepcion.cotizacionQuincenalDeMinerales.plata
            alicuotaCobre=recepcion.alicuota.cobre
            alicuotaPlata=recepcion.alicuota.plata
            tipoDeCambioComercial=recepcion.cotizacionDeDolar.tipoDeCambioComercial
            tipoDeCambioOficial=recepcion.cotizacionDeDolar.tipoDeCambioOficial
            pesoBruto=recepcion.pesoBruto

            if (controlCalidadComplejo){
                porcentajeMermaPromexbol = controlCalidadComplejo.porcentajeMermaPromexbol
                porcentajeHumedadPromexbol = controlCalidadComplejo.porcentajeHumedadPromexbol
                porcentajeCobrePromexbol = controlCalidadComplejo.porcentajeCobrePromexbol
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
            mapaRecepcion.put("cotizacionDiariaDeCobre", recepcion.cotizacionDiariaDeMinerales.cobre)
            mapaRecepcion.put("cotizacionQuincenalDeCobre", recepcion.cotizacionQuincenalDeMinerales.cobre)
            mapaRecepcion.put("alicuotaDeCobre", alicuotaCobre)
            mapaRecepcion.put("cotizacionDiariaDePlata", recepcion.cotizacionDiariaDeMinerales.plata)
            mapaRecepcion.put("cotizacionQuincenalDePlata", recepcion.cotizacionQuincenalDeMinerales.plata)
            mapaRecepcion.put("alicuotaDePlata", alicuotaPlata)
            mapaRecepcion.put("tipoDeCambioOficial", recepcion.cotizacionDeDolar.tipoDeCambioOficial)
            mapaRecepcion.put("tipoDeCambioComercial", recepcion.cotizacionDeDolar.tipoDeCambioComercial)

//            mapaRecepcion.put("tablasIds", tablaPrecios.getTablasIds(recepcion.empresa.id))
//            mapaRecepcion.put("terminosIds", terminosContrato.getTerminosIds(recepcion.empresa.id))

            //datos de control de calidad
            mapaRecepcion.put("porcentajeMermaPromexbol", porcentajeMermaPromexbol)
            mapaRecepcion.put("porcentajeHumedadPromexbol", porcentajeHumedadPromexbol)
            mapaRecepcion.put("porcentajeCobrePromexbol", porcentajeCobrePromexbol)
            mapaRecepcion.put("porcentajePlataPromexbol", porcentajePlataPromexbol)
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

    def recepcionesCobrePlataValoracionJSON() {
        //recepcionDeComplejo.id
        //def lote = Integer.parseInt(params.lote.toString())
        //def recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejoAndEstadoDelLote(lote,"NO LIQUIDADO")
        //def recepcion = RecepcionDeComplejo.findByLoteComplejo(lote)
        def recepcion = RecepcionDeComplejo.get(params.recepcionDeComplejoId.toString().toLong())
        def controlCalidadComplejo = null
        def tablaPrecios = new TablaPreciosCobreController()
        def terminosContrato = new TerminosDeContratoController()
        def bonosPorCalidad = new BonoCalidadController()
        def bonosPorIncentivo = new BonoIncentivoController()
        def recepcionesList = []
        //variables para calculos
        def cotizacionQuincenalCobre=0
        def cotizacionQuincenalPlata=0
        def alicuotaCobre=0
        def alicuotaPlata=0
        def tipoDeCambioComercial=0
        def tipoDeCambioOficial=0
        def pesoBruto=0
        def merma=1
        def humedad=1
        def porcentajeCobre=1
        def porcentajePlata=1
        def tipoDeMineral="CU-AG"
        def pesoBrutoSinMerma=0
        def kilosNetosHumedos=0
        def kilosNetosSecos=0
        def kilosFinosCobre=0
        def kilosFinosPlata=0
        def librasFinasCobre=0
        def onzasTroyPlata=0
        def valorOficialBrutoCobre=0
        def valorOficialBrutoPlata=0
        def valorOficialBrutoCobreBs=0
        def valorOficialBrutoPlataBs=0
        def valorOficialBruto=0
        def valorToneladaCobre=0
        def valorToneladaPlata=0
        def valorTonelada=0

        def valorToneladaTabla=0
        def valorToneladaTerminos=0
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
        def valorDeCompra=0
        def totalAnticiposContraEntrega=0
        def anticipoPorPagar=0

        def notificacionAnticipo=""
        def notificacionValoresTonelada=""
        def formatter = new DecimalFormat("###.00")

        def mapaRecepcion = [:]
        //controlCalidadComplejo = ControlCalidadComplejo.findByRecepcionDeComplejo(recepcion)

        cotizacionQuincenalCobre=recepcion.cotizacionQuincenalDeMinerales.cobre
        cotizacionQuincenalPlata=recepcion.cotizacionQuincenalDeMinerales.plata
        alicuotaCobre=recepcion.alicuota.cobre
        alicuotaPlata=recepcion.alicuota.plata
        tipoDeCambioComercial=recepcion.cotizacionDeDolar.tipoDeCambioComercial
        tipoDeCambioOficial=recepcion.cotizacionDeDolar.tipoDeCambioOficial
        pesoBruto=recepcion.pesoBruto

        merma = params.porcentajeMermaFinal.toString().toBigDecimal()
        humedad = params.porcentajeHumedadFinal.toString().toBigDecimal()
        porcentajeCobre = params.porcentajeCobreFinal.toString().toBigDecimal()
        porcentajePlata = params.porcentajePlataFinal.toString().toBigDecimal()

        margen = params.margen.toString().toBigDecimal()

        kilosNetosHumedos=pesoBruto-pesoBruto*merma/100
        kilosNetosSecos=kilosNetosHumedos-kilosNetosHumedos*humedad/100
        kilosFinosCobre=kilosNetosSecos*porcentajeCobre/100
        kilosFinosPlata=kilosNetosSecos*porcentajePlata/10000
        librasFinasCobre = kilosFinosCobre*2.2046223
        //onzasTroyPlata = kilosFinosPlata*31.1035
        onzasTroyPlata = kilosFinosPlata*32.15073
        valorOficialBrutoCobre = librasFinasCobre*cotizacionQuincenalCobre
        valorOficialBrutoPlata = onzasTroyPlata*cotizacionQuincenalPlata
        //valorOficialBrutoCobreBs = valorOficialBrutoCobre*tipoDeCambioComercial
        valorOficialBrutoCobreBs = valorOficialBrutoCobre*tipoDeCambioOficial
        valorOficialBrutoPlataBs = valorOficialBrutoPlata*tipoDeCambioOficial
        valorOficialBruto = valorOficialBrutoCobreBs+valorOficialBrutoPlataBs

        //determinacion del valor por tonelada
        //valor por tabla
        //valorToneladaCobre = tablaPrecios.getValorTonelada(params.tablaComplejoId.toString().toLong(),porcentajeCobre)
        valorToneladaCobre = tablaPrecios.getValorTonelada(TablaPreciosCobre.get(params.tablaCobreId.toString().toLong()),porcentajeCobre)
        //valor por terminos
        valorToneladaTerminos = terminosContrato.getValorToneladaPlomoPlata(recepcion.id,TerminosDeContrato.get(params.terminosDeContratoId.toString().toLong()),0,0,porcentajePlata,porcentajeCobre)

        //se resta 10 al valor por tonelada como proteccion segun Victor
//        if (params.modoValoracion.toString().equals("TABLA")){
//            valorTonelada = valorToneladaCobre + margen
//        }else{
//            //def getValorToneladaCobrePlata(Long recepcionId, TerminosDeContrato terminosDeContrato, BigDecimal porcentajeZinc, BigDecimal porcentajeCobre, BigDecimal porcentajePlata, BigDecimal porcentajeCobre){
//            valorTonelada = valorToneladaTerminos + margen
//        }
        valorToneladaTabla = valorToneladaCobre + margen
        valorToneladaTerminos = valorToneladaTerminos + margen
        if (params.modoValoracion.toString().equals("TABLA")){
            valorTonelada = valorToneladaTabla
        }else{
            valorTonelada = valorToneladaTerminos
        }

        regaliaMinera = (valorOficialBrutoCobreBs*alicuotaCobre/100 + valorOficialBrutoPlataBs*alicuotaPlata/100)
        valorNetoMineral = valorTonelada*kilosNetosSecos/1000
        valorNetoMineralEnBolivianos = valorNetoMineral*tipoDeCambioComercial
        bonoCalidad = bonosPorCalidad.bonoCalidadComplejo(recepcion.empresa.id,porcentajePlata)
        bonoIncentivo = bonosPorIncentivo.bonoIncentivoComplejo(recepcion.empresa.id,kilosNetosSecos,porcentajePlata)
        valorDeCompra = valorNetoMineralEnBolivianos+bonoCalidad+bonoIncentivo

        pLMEtabla = (valorToneladaCobre*kilosNetosSecos*tipoDeCambioComercial/1000)*100/valorOficialBruto
        pLMEterminos = (valorToneladaTerminos*kilosNetosSecos*tipoDeCambioComercial/1000)*100/valorOficialBruto
        notificacionValoresTonelada = "POR TABLA:\tVPT = ${valorToneladaCobre}\t%LME = ${formatter.format(pLMEtabla)}%\nPOR TERMINOS:\tVPT = ${valorToneladaTerminos}\t%LME = ${formatter.format(pLMEterminos)}%"
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
        mapaRecepcion.put("retenciones", retencionesParaLiquidacion(recepcion.empresa.retenciones))
        mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
        mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
        mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
        mapaRecepcion.put("tipoDeMineral", tipoDeMineral)
        mapaRecepcion.put("estadoDelLote", recepcion.estadoDelLote)
        mapaRecepcion.put("cotizacionDiariaDeCobre", recepcion.cotizacionDiariaDeMinerales.cobre)
        mapaRecepcion.put("cotizacionQuincenalDeCobre", recepcion.cotizacionQuincenalDeMinerales.cobre)
        mapaRecepcion.put("alicuotaDeCobre", alicuotaCobre)
        mapaRecepcion.put("cotizacionDiariaDePlata", recepcion.cotizacionDiariaDeMinerales.plata)
        mapaRecepcion.put("cotizacionQuincenalDePlata", recepcion.cotizacionQuincenalDeMinerales.plata)
        mapaRecepcion.put("alicuotaDePlata", alicuotaPlata)
        mapaRecepcion.put("tipoDeCambioOficial", recepcion.cotizacionDeDolar.tipoDeCambioOficial)
        mapaRecepcion.put("tipoDeCambioComercial", recepcion.cotizacionDeDolar.tipoDeCambioComercial)

        //datos de control de calidad
        mapaRecepcion.put("kilosNetosHumedos", kilosNetosHumedos)
        mapaRecepcion.put("kilosNetosSecos", kilosNetosSecos)
        mapaRecepcion.put("kilosFinosCobre", kilosFinosCobre)
        mapaRecepcion.put("kilosFinosPlata", kilosFinosPlata)
        mapaRecepcion.put("librasFinasDeCobre", librasFinasCobre)
        mapaRecepcion.put("onzasTroyDePlata", onzasTroyPlata)
        mapaRecepcion.put("valorOficialBrutoDeCobre", valorOficialBrutoCobre)
        mapaRecepcion.put("valorOficialBrutoDePlata", valorOficialBrutoPlata)
        mapaRecepcion.put("valorOficialBrutoDeCobreEnBolivianos", valorOficialBrutoCobreBs)
        mapaRecepcion.put("valorOficialBrutoDePlataEnBolivianos", valorOficialBrutoPlataBs)
        mapaRecepcion.put("valorOficialBruto", valorOficialBruto)
        mapaRecepcion.put("valorPorTonelada", valorTonelada)

        mapaRecepcion.put("valorToneladaTabla", valorToneladaCobre)
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

    def recepcionGrupalCobrePlataJSON() {
        def deposito = Deposito.get(params.depositoId.toLong())
        def loteInicial = Integer.parseInt(params.loteInicial.toString())
        def loteFinal = Integer.parseInt(params.loteFinal.toString())
        def recepcionesComplejo = RecepcionDeComplejo.findAllByDepositoAndLoteCobrePlataBetweenAndEstadoDelLote(deposito,loteInicial,loteFinal,"NO LIQUIDADO")
        def controlCalidadCobrePlata = null

        def recepcionesList = []
        //variables para calculos
        def cotizacionQuincenalCobre=0
        def cotizacionQuincenalPlata=0
        def alicuotaCobre=0
        def alicuotaPlata=0
        def tipoDeCambioComercial=0
        def tipoDeCambioOficial=0
        def pesoBruto=0
        def porcentajeMermaPromexbol=1
        def porcentajeHumedadPromexbol=1
        def porcentajeCobrePromexbol=1
        def porcentajePlataPromexbol=1
        def controlCalidadId=0
        def tipoDeMineral="COMPLEJO"

        def totalAnticiposContraEntrega=0
        def anticipoPorPagar=0

        def notificacionAnticipo=""

        recepcionesComplejo.each { recepcion ->
            def mapaRecepcion = [:]
            controlCalidadCobrePlata = ControlCalidadCobrePlata.findByRecepcionDeComplejo(recepcion)

            cotizacionQuincenalCobre=recepcion.cotizacionQuincenalDeMinerales.cobre
            cotizacionQuincenalPlata=recepcion.cotizacionQuincenalDeMinerales.plata
            alicuotaCobre=recepcion.alicuota.cobre
            alicuotaPlata=recepcion.alicuota.plata
            tipoDeCambioComercial=recepcion.cotizacionDeDolar.tipoDeCambioComercial
            tipoDeCambioOficial=recepcion.cotizacionDeDolar.tipoDeCambioOficial
            pesoBruto=recepcion.pesoBruto

            if (controlCalidadCobrePlata){
                porcentajeMermaPromexbol = controlCalidadCobrePlata.porcentajeMermaPromexbol
                porcentajeHumedadPromexbol = controlCalidadCobrePlata.porcentajeHumedadPromexbol
                porcentajeCobrePromexbol = controlCalidadCobrePlata.porcentajeCobrePromexbol
                porcentajePlataPromexbol = controlCalidadCobrePlata.porcentajePlataPromexbol
                controlCalidadId = controlCalidadCobrePlata.id
            }else{
                porcentajeMermaPromexbol = 1
                porcentajeHumedadPromexbol = 1
                porcentajeCobrePromexbol = 1
                porcentajePlataPromexbol = 1
                System.out.println("****** no existe informacion en control de calidad")
            }

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

            mapaRecepcion.put("recepcionId", recepcion.id)
            mapaRecepcion.put("lote", recepcion.toString())
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
            mapaRecepcion.put("cotizacionDiariaDeZinc", recepcion.cotizacionDiariaDeMinerales.zinc)
            mapaRecepcion.put("cotizacionQuincenalDeZinc", recepcion.cotizacionQuincenalDeMinerales.zinc)
            mapaRecepcion.put("cotizacionDiariaDeCobre", recepcion.cotizacionDiariaDeMinerales.cobre)
            mapaRecepcion.put("cotizacionQuincenalDeCobre", recepcion.cotizacionQuincenalDeMinerales.cobre)
            mapaRecepcion.put("alicuotaDeCobre", alicuotaCobre)
            mapaRecepcion.put("cotizacionDiariaDePlata", recepcion.cotizacionDiariaDeMinerales.plata)
            mapaRecepcion.put("cotizacionQuincenalDePlata", recepcion.cotizacionQuincenalDeMinerales.plata)
            mapaRecepcion.put("alicuotaDePlata", alicuotaPlata)
            mapaRecepcion.put("tipoDeCambioOficial", recepcion.cotizacionDeDolar.tipoDeCambioOficial)
            mapaRecepcion.put("tipoDeCambioComercial", recepcion.cotizacionDeDolar.tipoDeCambioComercial)

            //datos de control de calidad
            mapaRecepcion.put("porcentajeMermaPromexbol", porcentajeMermaPromexbol)
            mapaRecepcion.put("porcentajeHumedadPromexbol", porcentajeHumedadPromexbol)
            mapaRecepcion.put("porcentajeCobrePromexbol", porcentajeCobrePromexbol)
            mapaRecepcion.put("porcentajePlataPromexbol", porcentajePlataPromexbol)
            mapaRecepcion.put("porcentajeMermaCliente", 0)
            mapaRecepcion.put("porcentajeHumedadCliente", 0)
            mapaRecepcion.put("porcentajeZincCliente", 0)
            mapaRecepcion.put("porcentajeCobreCliente", 0)
            mapaRecepcion.put("porcentajePlataCliente", 0)
            mapaRecepcion.put("porcentajeMermaFinal", 0)
            mapaRecepcion.put("porcentajeHumedadFinal", 0)
            mapaRecepcion.put("porcentajeZincFinal", 0)
            mapaRecepcion.put("porcentajeCobreFinal", 0)
            mapaRecepcion.put("porcentajePlataFinal", 0)

            mapaRecepcion.put("controlCalidadId", controlCalidadId)
            mapaRecepcion.put("preciosIds", preciosCobreGrupalIds(recepcion.empresa.id))

            mapaRecepcion.put("modoValoracion", "TABLA")
            //tablaTermino
            mapaRecepcion.put("tablaTermino", "-")
            mapaRecepcion.put("tablaCobreId", 0)
            mapaRecepcion.put("terminosDeContratoId", 0)
            //mapaRecepcion.put("margen", 0)
            mapaRecepcion.put("margen", -10)

            def hoy = new Date()
            mapaRecepcion.put("fechaDeLiquidacion_day", hoy.toCalendar().get(Calendar.DAY_OF_MONTH))
            mapaRecepcion.put("fechaDeLiquidacion_month", hoy.toCalendar().get(Calendar.MONTH))
            mapaRecepcion.put("fechaDeLiquidacion_year", hoy.toCalendar().get(Calendar.YEAR))

            mapaRecepcion.put("kilosNetosHumedos", 0)
            mapaRecepcion.put("kilosNetosSecos", 0)
            mapaRecepcion.put("kilosFinosCobre", 0)
            mapaRecepcion.put("kilosFinosPlata", 0)
            mapaRecepcion.put("librasFinasDeCobre", 0)
            mapaRecepcion.put("onzasTroyDePlata", 0)
            mapaRecepcion.put("valorOficialBrutoDeCobre", 0)
            mapaRecepcion.put("valorOficialBrutoDePlata", 0)
            mapaRecepcion.put("valorOficialBrutoDeCobreEnBolivianos", 0)
            mapaRecepcion.put("valorOficialBrutoDePlataEnBolivianos", 0)
            mapaRecepcion.put("valorOficialBruto", 0)
            mapaRecepcion.put("valorPorTonelada", 0)

            mapaRecepcion.put("valorToneladaTabla", 0)
            mapaRecepcion.put("valorToneladaTerminos", 0)

            mapaRecepcion.put("regaliaMinera", 0)
            mapaRecepcion.put("valorNetoMineral", 0)
            mapaRecepcion.put("valorNetoMineralEnBolivianos", 0)
            mapaRecepcion.put("bonoCalidad", 0)
            mapaRecepcion.put("bonoIncentivo", 0)
            mapaRecepcion.put("valorDeCompra", 0)
            mapaRecepcion.put("totalRetenciones", 0)
            mapaRecepcion.put("totalPagado", 0)
            mapaRecepcion.put("anticipoPorPagar", anticipoPorPagar)
            mapaRecepcion.put("notificacionAnticipo", notificacionAnticipo)
            mapaRecepcion.put("notificacionValoresTonelada", 0)

            mapaRecepcion.put("totalAnticiposContraEntrega", 0)
            mapaRecepcion.put("totalAnticiposContraFuturaEntrega", 0)
            mapaRecepcion.put("adelantoPorLiquidacionProvisional", 0)
            mapaRecepcion.put("totalLiquidoPagable", 0)
            mapaRecepcion.put("totalLiquidoPagableOriginal", 0)
            mapaRecepcion.put("diferenciaLiquidoPagable", 0)
            mapaRecepcion.put("observaciones", "-")
            mapaRecepcion.put("motivoDeModificacion", "-")

            mapaRecepcion.put("detalleLaboratorio1", recepcion.detalleLaboratorio1)
            mapaRecepcion.put("costoLaboratorio1", recepcion.costoLaboratorio1)
            mapaRecepcion.put("detalleLaboratorio2", recepcion.detalleLaboratorio2)
            mapaRecepcion.put("costoLaboratorio2", recepcion.costoLaboratorio2)
            mapaRecepcion.put("detalleLaboratorio3", recepcion.detalleLaboratorio3)
            mapaRecepcion.put("costoLaboratorio3", recepcion.costoLaboratorio3)
            mapaRecepcion.put("detalleLaboratorio4", recepcion.detalleLaboratorio4)
            mapaRecepcion.put("costoLaboratorio4", recepcion.costoLaboratorio4)
            mapaRecepcion.put("totalCostoLaboratorio", recepcion.totalCostoLaboratorio)
            mapaRecepcion.put("totalAnticiposContraFuturaEntrega", getTotalAnticiposContraFuturaEntrega(recepcion.cliente.id))
            recepcionesList.add(mapaRecepcion)
        }
        //render recepcionesList as JSON

        render([lotes: (recepcionesList as JSON).toString()] as JSON)
    }

    def recepcionesAnticipoJSON() {
        def tipoDeMineral = params.tipoDeMineral.toString()
        def loteInicial = Integer.parseInt(params.loteInicial.toString())
        def loteFinal = Integer.parseInt(params.loteFinal.toString())
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        def deposito = usuarioActual.deposito
//        def deposito = Deposito.get(params.depositoId.toString().toLong())
        def empresa=null
        def cliente=null
        def recepciones = null
        if (!params.empresaId.toString().equals("null")&&!params.clienteId.toString().equals("null")){
            empresa = Empresa.get(params.empresaId.toString().toLong())
            cliente = Cliente.get(params.clienteId.toString().toLong())
            //recepciones = RecepcionDeComplejo.findAllByEmpresaAndClienteAndLoteComplejoBetweenAndDepositoAndEstadoAnticipoAndEstadoDelLote(empresa,cliente,loteInicial,loteFinal,deposito,"SIN ANTICIPO","NO LIQUIDADO")
            if (tipoDeMineral.equals("COMPLEJO"))
//                recepciones = RecepcionDeComplejo.findAllByEmpresaAndClienteAndLoteComplejoBetweenAndDepositoAndEstadoAnticipoAndEstadoDelLote(empresa,cliente,loteInicial,loteFinal,deposito,"SIN ANTICIPO","NO LIQUIDADO")
                recepciones = RecepcionDeComplejo.findAllByEmpresaAndClienteAndLoteComplejoBetweenAndEstadoAnticipoAndEstadoDelLote(empresa,cliente,loteInicial,loteFinal,"SIN ANTICIPO","NO LIQUIDADO")
//            if (tipoDeMineral.equals("ESTANO"))
//                recepciones = RecepcionDeEstano.findAllByEmpresaAndClienteAndLoteEstanoBetweenAndEstadoAnticipoAndEstadoDelLote(empresa,cliente,loteInicial,loteFinal,"SIN ANTICIPO","NO LIQUIDADO")
            if (tipoDeMineral.equals("ORO"))
                recepciones = RecepcionDeOro.findAllByEmpresaAndClienteAndLoteOroBetweenAndEstadoAnticipoAndEstadoDelLote(empresa,cliente,loteInicial,loteFinal,"SIN ANTICIPO","NO LIQUIDADO")
        }
        def recepcionesList = []
        if (recepciones){
            recepciones.each { recepcion ->
                def mapaRecepcion = [:]
                mapaRecepcion.put("recepcionId", recepcion.id)
                mapaRecepcion.put("lote", recepcion.toString())
                mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
                mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
                mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
                mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
                mapaRecepcion.put("tipoDeMaterial", recepcion.tipoDeMaterial)//anticipoSugerido
                mapaRecepcion.put("anticipoSugerido", recepcion.anticipoAutorizado)

                recepcionesList.add(mapaRecepcion)
            }
        }
        render([lotes: (recepcionesList as JSON).toString()] as JSON)
    }

    def recepcionesTransporteJSON() {
        def empresa=null
        def automovil=null
        def recepcionesComplejo = null
        if (params.solicitante.toString().equals("Empresa")){
            if(!params.empresaId.toString().equals("null")){
                empresa = Empresa.get(params.empresaId.toString().toLong())
                //recepcionesComplejo = RecepcionDeComplejo.findAllByEmpresaAndNumeroRecepcionBetweenAndDepositoAndTransportePagado(empresa,loteInicial,loteFinal,deposito,"NO")
//                recepcionesComplejo = RecepcionDeComplejo.findAllByEmpresaAndDepositoAndTransportePagado(empresa,deposito,"NO")
                recepcionesComplejo = RecepcionDeComplejo.findAllByEmpresaAndTransportePagado(empresa,"NO")
//                recepcionesComplejo = RecepcionDeComplejo.findAllByEmpresaAndDepositoAndTransportePagadoAndEstadoDelLote(empresa,deposito,"NO","LIQUIDADO")
            }
        }
        if (params.solicitante.toString().equals("Particular")){
            if(!params.automovilId.toString().equals("null")){
                automovil = Automovil.get(params.automovilId.toString().toLong())
                //recepcionesComplejo = RecepcionDeComplejo.findAllByAutomovilAndNumeroRecepcionBetweenAndDepositoAndTransportePagado(automovil,loteInicial,loteFinal,deposito,"NO")
//                recepcionesComplejo = RecepcionDeComplejo.findAllByAutomovilAndDepositoAndTransportePagado(automovil,deposito,"NO")
                recepcionesComplejo = RecepcionDeComplejo.findAllByAutomovilAndTransportePagado(automovil,"NO")
//                recepcionesComplejo = RecepcionDeComplejo.findAllByAutomovilAndDepositoAndTransportePagadoAndEstadoDelLote(automovil,deposito,"NO","LIQUIDADO")
            }
        }
        def recepcionesList = []
        if (recepcionesComplejo){
            recepcionesComplejo.each { recepcion ->
                def mapaRecepcion = [:]

                mapaRecepcion.put("recepcionId", recepcion.id)
                mapaRecepcion.put("lote", recepcion.toString())
                mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
                mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
                mapaRecepcion.put("nombreChofer", recepcion.chofer.nombre)
                mapaRecepcion.put("placaAutomovil", recepcion.automovil.placa)
                mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
                mapaRecepcion.put("precioTonelada", recepcion.tipoDeMaterial.equals("BROZA")?recepcion.empresa.costoTransporteComplejos:recepcion.empresa.costoTransporteConcentrados)
                mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
                mapaRecepcion.put("tipoDeMaterial", recepcion.tipoDeMaterial)
                mapaRecepcion.put("costoDeTransporte", recepcion.costoDeTransporte)
                // El anticipo ya no se aplica por lote; se consume del disponible del automovil en el pago.
                mapaRecepcion.put("anticipoTransporte", 0)

                recepcionesList.add(mapaRecepcion)
            }
        }
        render([lotes: (recepcionesList as JSON).toString()] as JSON)
    }

    def recepcionesTransporteActualizadoJSON() {
        def lotesAnteriores = params.lotes.toString()
        def lotesJSON = new JSONArray(lotesAnteriores)
        def recepcionesList = []
        lotesJSON.each {
            def recepcionId = it.getAt("recepcionId").toString().toLong()
            def recepcion = RecepcionDeComplejo.get(recepcionId)

            if(recepcion){
                def mapaRecepcion = [:]

                mapaRecepcion.put("recepcionId", recepcion.id)
                mapaRecepcion.put("lote", recepcion.toString())
                mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
                mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
                mapaRecepcion.put("nombreChofer", recepcion.chofer.nombre)
                mapaRecepcion.put("placaAutomovil", recepcion.automovil.placa)
                mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
                mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
                mapaRecepcion.put("tipoDeMaterial", recepcion.tipoDeMaterial)
                mapaRecepcion.put("costoDeTransporte", recepcion.costoDeTransporte)

                recepcionesList.add(mapaRecepcion)
            }
        }

        render([lotes: (recepcionesList as JSON).toString()] as JSON)
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
        def c = RecepcionDeComplejo.createCriteria()
        def results = c {
            projections {
                max('id')
            }}
        def maxId = results.get(0)?: 0

        if (maxId!=0){
            def ultimaRecepcion = RecepcionDeComplejo.get(maxId)
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
        def recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetweenAndEstadoDelLote(fechaInicial,fechaFinal,"NO LIQUIDADO")
        def recepcionesList = []
        recepcionesComplejo.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("lote", recepcion.loteComplejo)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            //mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            def kilosNetosHumedos = recepcion.pesoBruto-Float.parseFloat(recepcion.cantidadDeSacos)*recepcion.pesoTara
            mapaRecepcion.put("kilosNetosHumedos", kilosNetosHumedos)
            mapaRecepcion.put("cotizacionZinc", recepcion.cotizacionDiariaDeMinerales.zinc)
            mapaRecepcion.put("cotizacionPlomo", recepcion.cotizacionDiariaDeMinerales.plomo)
            mapaRecepcion.put("cotizacionPlata", recepcion.cotizacionDiariaDeMinerales.plata)
            mapaRecepcion.put("merma", 0)
            mapaRecepcion.put("humedad", 0)
            mapaRecepcion.put("porcentajeZinc", 0)
            mapaRecepcion.put("porcentajePlomo", 0)
            mapaRecepcion.put("porcentajePlata", 0)
            mapaRecepcion.put("puntoZinc", 0)
            mapaRecepcion.put("puntoPlomo", 0)
            mapaRecepcion.put("puntoPlata", 0)
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def recepcionesPresupuestoFechasEmpresaJSON() {
        def fechaInicial = new Date().parse("yyyy-MM-dd",params.fechaInicial)
        def fechaFinal = new Date().parse("yyyy-MM-dd",params.fechaFinal)
        def empresa = Empresa.get(params.empresaId)
        def recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetweenAndEstadoDelLoteAndEmpresa(fechaInicial,fechaFinal,"NO LIQUIDADO",empresa)
        def recepcionesList = []
        recepcionesComplejo.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("lote", recepcion.loteComplejo)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            //mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            def kilosNetosHumedos = recepcion.pesoBruto-Float.parseFloat(recepcion.cantidadDeSacos)*recepcion.pesoTara
            mapaRecepcion.put("kilosNetosHumedos", kilosNetosHumedos)
            mapaRecepcion.put("cotizacionZinc", recepcion.cotizacionDiariaDeMinerales.zinc)
            mapaRecepcion.put("cotizacionPlomo", recepcion.cotizacionDiariaDeMinerales.plomo)
            mapaRecepcion.put("cotizacionPlata", recepcion.cotizacionDiariaDeMinerales.plata)
            mapaRecepcion.put("merma", 0)
            mapaRecepcion.put("humedad", 0)
            mapaRecepcion.put("porcentajeZinc", 0)
            mapaRecepcion.put("porcentajePlomo", 0)
            mapaRecepcion.put("porcentajePlata", 0)
            mapaRecepcion.put("puntoZinc", 0)
            mapaRecepcion.put("puntoPlomo", 0)
            mapaRecepcion.put("puntoPlata", 0)
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def recepcionesPresupuestoLotesJSON() {
        def loteInicial = params.loteInicial
        def loteFinal = params.loteFinal
        def recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejoBetweenAndEstadoDelLote(loteInicial,loteFinal,"NO LIQUIDADO")
        def recepcionesList = []
        recepcionesComplejo.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("lote", recepcion.loteComplejo)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            //mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            def kilosNetosHumedos = recepcion.pesoBruto-recepcion.cantidadDeSacos*recepcion.pesoTara
            mapaRecepcion.put("kilosNetosHumedos", kilosNetosHumedos)
            mapaRecepcion.put("cotizacionZinc", recepcion.cotizacionDiariaDeMinerales.zinc)
            mapaRecepcion.put("cotizacionPlomo", recepcion.cotizacionDiariaDeMinerales.plomo)
            mapaRecepcion.put("cotizacionPlata", recepcion.cotizacionDiariaDeMinerales.plata)
            mapaRecepcion.put("merma", 0)
            mapaRecepcion.put("humedad", 0)
            mapaRecepcion.put("porcentajeZinc", 0)
            mapaRecepcion.put("porcentajePlomo", 0)
            mapaRecepcion.put("porcentajePlata", 0)
            mapaRecepcion.put("puntoZinc", 0)
            mapaRecepcion.put("puntoPlomo", 0)
            mapaRecepcion.put("puntoPlata", 0)
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def recepcionesPresupuestoLotesEmpresaJSON() {
        def loteInicial = params.loteInicial
        def loteFinal = params.loteFinal
        def empresa = Empresa.get(params.empresaId)
        def recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejoBetweenAndEstadoDelLoteAndEmpresa(loteInicial,loteFinal,"NO LIQUIDADO",empresa)
        def recepcionesList = []
        recepcionesComplejo.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("lote", recepcion.loteComplejo)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            //mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            def kilosNetosHumedos = recepcion.pesoBruto-recepcion.cantidadDeSacos*recepcion.pesoTara
            mapaRecepcion.put("kilosNetosHumedos", kilosNetosHumedos)
            mapaRecepcion.put("cotizacionZinc", recepcion.cotizacionDiariaDeMinerales.zinc)
            mapaRecepcion.put("cotizacionPlomo", recepcion.cotizacionDiariaDeMinerales.plomo)
            mapaRecepcion.put("cotizacionPlata", recepcion.cotizacionDiariaDeMinerales.plata)
            mapaRecepcion.put("merma", 0)
            mapaRecepcion.put("humedad", 0)
            mapaRecepcion.put("porcentajeZinc", 0)
            mapaRecepcion.put("porcentajePlomo", 0)
            mapaRecepcion.put("porcentajePlata", 0)
            mapaRecepcion.put("puntoZinc", 0)
            mapaRecepcion.put("puntoPlomo", 0)
            mapaRecepcion.put("puntoPlata", 0)
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def crearReporte = {
        def recepcionDeComplejo = RecepcionDeComplejo.get(params.id)
        def realPath = org.socymet.util.ReportesRuntime.realPath("/reports/images/")
        params.realPath = realPath+"/"
        chain(controller:'jasper',action:'index',model:[data:recepcionDeComplejo],params:params)
    }

    def numeroDeLote = {
        def empresa = Empresa.get(params.empresaId)
        def deposito = Deposito.get(params.depositoId)

        // El lote de turno solo se genera cuando hay empresa (y depósito) definidos
        if (!empresa || !deposito) {
            render([lote: "?"] as JSON)
            return
        }

        def gestionMinera = RecepcionDeComplejo.gestionMineraActiva()
        def loteComplejo = RecepcionDeComplejo.siguienteLoteComplejo(deposito, empresa, gestionMinera)

        render([lote: RecepcionDeComplejo.formatearCodigoLote(deposito, empresa, loteComplejo, gestionMinera)] as JSON)
    }

    def preciosIds = {
        def precios = [:]
        def tablas = new TablaOrigenCotizacionesComplejoController()
        def preciosLME = new TablaPrecioPorLmeController()
        def terminos = new TerminosDeContratoController()
        //naturalezaMinferal
//        precios.put("tablasIds",tablas.getTablasIds(params.empresaId,params.naturalezaMineral))
//        precios.put("preciosPorLmeIds",preciosLME.getPreciosIds(params.empresaId,params.naturalezaMineral))
//        precios.put("terminosIds",terminos.getTerminosIds(params.empresaId))
        precios.put("tablasIds",tablas.getTablasIds(params.naturalezaMineral))
        precios.put("preciosPorLmeIds",preciosLME.getPreciosIds(params.naturalezaMineral))
        precios.put("terminosIds",terminos.getTerminosIds())
        render precios as  JSON
    }

    def preciosComplejoGrupalIds = { empresaId ->
        def precios = [:]
        def tablas = new TablaOrigenCotizacionesComplejoController()
        def terminos = new TerminosDeContratoController()
        precios.put("tablasIds",tablas.getTablasIds(empresaId))
        precios.put("terminosIds",terminos.getTerminosIds(empresaId))
        return (precios as  JSON).toString()
    }

    def preciosCobreGrupalIds = { empresaId ->
        def precios = [:]
        def tablas = new TablaPreciosCobreController()
        def terminos = new TerminosDeContratoController()
        precios.put("tablasIds",tablas.getTablasIds(empresaId))
        precios.put("terminosIds",terminos.getTerminosIds(empresaId))
        return (precios as  JSON).toString()
    }

    def preciosCobreIds = {
        def precios = [:]
        def tablas = new TablaPreciosCobreController()
        def terminos = new TerminosDeContratoController()
        precios.put("tablasIds",tablas.getTablasIds(params.empresaId))
        precios.put("terminosIds",terminos.getTerminosIds(params.empresaId))
        render precios as  JSON
    }

    def recepcionesLiquidacionGrupalComplejoJSON() {
        def loteInicial = Integer.parseInt(params.loteInicial.toString())
        def loteFinal = Integer.parseInt(params.loteFinal.toString())
        def deposito = Deposito.get(params.depositoId.toString().toLong())
        def empresa=null
        def cliente=null
        def recepcionesComplejo = null
        if (!params.empresaId.toString().equals("null")&&!params.clienteId.toString().equals("null")){
            empresa = Empresa.get(params.empresaId.toString().toLong())
            cliente = Cliente.get(params.clienteId.toString().toLong())
            //recepcionesComplejo = RecepcionDeComplejo.findAllByEmpresaAndClienteAndLoteComplejoBetweenAndDepositoAndEstadoAnticipoAndEstadoDelLote(empresa,cliente,loteInicial,loteFinal,deposito,"SIN ANTICIPO","NO LIQUIDADO")
            if (tipoDeMineral.equals("COMPLEJO"))
                recepcionesComplejo = RecepcionDeComplejo.findAllByEmpresaAndClienteAndLoteComplejoBetweenAndDepositoAndEstadoAnticipoAndEstadoDelLote(empresa,cliente,loteInicial,loteFinal,deposito,"SIN ANTICIPO","NO LIQUIDADO")
            if (tipoDeMineral.equals("PB-AG"))
                recepcionesComplejo = RecepcionDeComplejo.findAllByEmpresaAndClienteAndLotePlomoPlataBetweenAndDepositoAndEstadoAnticipoAndEstadoDelLote(empresa,cliente,loteInicial,loteFinal,deposito,"SIN ANTICIPO","NO LIQUIDADO")
            if (tipoDeMineral.equals("ZN-AG"))
                recepcionesComplejo = RecepcionDeComplejo.findAllByEmpresaAndClienteAndLoteZincPlataBetweenAndDepositoAndEstadoAnticipoAndEstadoDelLote(empresa,cliente,loteInicial,loteFinal,deposito,"SIN ANTICIPO","NO LIQUIDADO")
            if (tipoDeMineral.equals("CU-AG"))
                recepcionesComplejo = RecepcionDeComplejo.findAllByEmpresaAndClienteAndLoteCobrePlataBetweenAndDepositoAndEstadoAnticipoAndEstadoDelLote(empresa,cliente,loteInicial,loteFinal,deposito,"SIN ANTICIPO","NO LIQUIDADO")
        }
        def recepcionesList = []
        if (recepcionesComplejo){
            recepcionesComplejo.each { recepcion ->
                def mapaRecepcion = [:]
                mapaRecepcion.put("recepcionId", recepcion.id)
                mapaRecepcion.put("lote", recepcion.toString())
                mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
                mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
                mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
                mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
                mapaRecepcion.put("tipoDeMaterial", recepcion.tipoDeMaterial)//anticipoSugerido
                mapaRecepcion.put("anticipoSugerido", recepcion.anticipoAutorizado)

                recepcionesList.add(mapaRecepcion)
            }
        }
        render([lotes: (recepcionesList as JSON).toString()] as JSON)
    }

    def listaTablaTermino = {
        def modoValoracion = params.modo.toInteger() // 1=tabla, 2=terminos
        def recepcionId = params.recepcionId.toLong()
        def htmlTablaTermino = "<select name=\"tablaTermino\" >"

        if (recepcionId==0)
            render htmlTablaTermino +"</select>"
        else{
            def recepcion = RecepcionDeComplejo.get(recepcionId)

            if (modoValoracion==1){
                def tablas = new TablaOrigenCotizacionesComplejoController()
                def tablasIds = tablas.getTablasIds(recepcion.empresa.id)
                def tokenizer = new StringTokenizer(tablasIds,"-")
                while (tokenizer.hasMoreTokens()){
                    def tabla = TablaOrigenCotizacionesComplejo.get(tokenizer.nextToken().toLong())
                    htmlTablaTermino = htmlTablaTermino + "<option value=\"${tabla.id}\">${tabla.nombreTabla}</option>"
                }
                htmlTablaTermino = htmlTablaTermino + "</select>"
            }

            if (modoValoracion==2){
                def terminos = new TerminosDeContratoController()
                def terminosIds = terminos.getTerminosIds(recepcion.empresa.id)
                def tokenizer = new StringTokenizer(terminosIds,"-")
                while (tokenizer.hasMoreTokens()){
                    def termino = TerminosDeContrato.get(tokenizer.nextToken().toLong())
                    htmlTablaTermino = htmlTablaTermino + "<option value=\"${termino.id}\">${termino.nombreContrato}</option>"
                }
                htmlTablaTermino = htmlTablaTermino + "</select>"
            }

            render htmlTablaTermino
        }
    }

    def listarTablaTermino = {
        def modoValoracion = params.modo.toInteger() // 1=tabla, 2=terminos
        def recepcionId = params.recepcionId.toLong()
        def htmlTablaTermino = "<option value=\"0\">-SELECCIONE-</option>"
        def recepcion = RecepcionDeComplejo.get(recepcionId)
        if (modoValoracion==1){
            def tablas = new TablaOrigenCotizacionesComplejoController()
            def tablasIds = tablas.getTablasIds(recepcion.empresa.id)
            def tokenizer = new StringTokenizer(tablasIds,"-")
            while (tokenizer.hasMoreTokens()){
                def tabla = TablaOrigenCotizacionesComplejo.get(tokenizer.nextToken().toLong())
                htmlTablaTermino = htmlTablaTermino + "<option value=\"${tabla.id}\">${tabla.nombreTabla}</option>"
            }
        }
        if (modoValoracion==2){
            def terminos = new TerminosDeContratoController()
            def terminosIds = terminos.getTerminosIds(recepcion.empresa.id)
            def tokenizer = new StringTokenizer(terminosIds,"-")
            while (tokenizer.hasMoreTokens()){
                def termino = TerminosDeContrato.get(tokenizer.nextToken().toLong())
                htmlTablaTermino = htmlTablaTermino + "<option value=\"${termino.id}\">${termino.nombreContrato}</option>"
            }
        }
        def opciones = [:]
        opciones.put("opciones",htmlTablaTermino)
        render opciones as JSON
    }

    def listarTablaTerminoCobre = {
        def modoValoracion = params.modo.toInteger() // 1=tabla, 2=terminos
        def recepcionId = params.recepcionId.toLong()
        def htmlTablaTermino = "<option value=\"0\">-SELECCIONE-</option>"
        def recepcion = RecepcionDeComplejo.get(recepcionId)
        if (modoValoracion==1){
            def tablas = new TablaPreciosCobreController()
            def tablasIds = tablas.getTablasIds(recepcion.empresa.id)
            def tokenizer = new StringTokenizer(tablasIds,"-")
            while (tokenizer.hasMoreTokens()){
                def tabla = TablaPreciosCobre.get(tokenizer.nextToken().toLong())
                htmlTablaTermino = htmlTablaTermino + "<option value=\"${tabla.id}\">${tabla.nombreTabla}</option>"
            }
        }
        if (modoValoracion==2){
            def terminos = new TerminosDeContratoController()
            def terminosIds = terminos.getTerminosIds(recepcion.empresa.id)
            def tokenizer = new StringTokenizer(terminosIds,"-")
            while (tokenizer.hasMoreTokens()){
                def termino = TerminosDeContrato.get(tokenizer.nextToken().toLong())
                htmlTablaTermino = htmlTablaTermino + "<option value=\"${termino.id}\">${termino.nombreContrato}</option>"
            }
        }
        def opciones = [:]
        opciones.put("opciones",htmlTablaTermino)
        render opciones as JSON
    }

    def ready = {
        def parametros = ParametrosGenerales.get(1)
        def limite = parametros?parametros.mesesPagablesBonoCantidad:0
        render([working: RecepcionDeComplejo.list().size()>limite?0:1] as JSON)
    }

    def filtradoLotesActualizacionCotizacionJSON() {
        def tipoCotizacion = params.tipoCotizacion.toString()
        def cotizacionDiariaDeMinerales = CotizacionDiariaDeMinerales.get(params.cotizacionDiariaDeMineralesId.toLong())
        def cotizacionQuincenalDeMinerales = CotizacionQuincenalDeMinerales.get(params.cotizacionQuincenalDeMineralesId.toLong())
//        def fechaInicial = new Date().parse("yyyy-MM-dd hh:mm:ss","${params.fechaInicial_year}-${params.fechaInicial_month}-${params.fechaInicial_day} 00:00:00")
//        def fechaFinal = new Date().parse("yyyy-MM-dd hh:mm:ss","${params.fechaInicial_year}-${params.fechaInicial_month}-${params.fechaInicial_day} 23:59:59")
        def fechaInicial = new Date().parse("yyyy-MM-dd","${params.fechaInicial_year}-${params.fechaInicial_month}-${params.fechaInicial_day}")
        def fechaFinal = new Date().parse("yyyy-MM-dd","${params.fechaFinal_year}-${params.fechaFinal_month}-${params.fechaFinal_day}")
        def recepciones = RecepcionDeComplejo.findAllByFechaDeRecepcionBetweenAndEstadoDelLote(fechaInicial,fechaFinal,"NO LIQUIDADO")
        log.error("fechaInicial: $fechaInicial")
        log.error("fechaFinal: $fechaFinal")
        log.error("recepciones: ${recepciones.size()} encontradas")
        def recepcionesList = []
        if (recepciones){
            recepciones.each { recepcion ->
                def mapaRecepcion = [:]
                mapaRecepcion.put("lote", recepcion.toString())
                mapaRecepcion.put("recepcionId", recepcion.id)
                mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
                mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
                mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
                mapaRecepcion.put("fechaDeCotizacionQuincenal", recepcion.cotizacionQuincenalDeMinerales.toString())
                mapaRecepcion.put("fechaDeCotizacionDiaria", recepcion.cotizacionDiariaDeMinerales.toString())//anticipoSugerido

                recepcionesList.add(mapaRecepcion)
            }
        }
        render([detalleLotes: (recepcionesList as JSON).toString()] as JSON)
    }

    def actualizacionCotizacionRecepcionJSON() {
        def tipoCotizacion = params.tipoCotizacion.toString()
        def cotizacionDiariaDeMinerales = CotizacionDiariaDeMinerales.get(params.cotizacionDiariaDeMineralesId.toLong())
        def cotizacionQuincenalDeMinerales = CotizacionQuincenalDeMinerales.get(params.cotizacionQuincenalDeMineralesId.toLong())
        def detalleLotes = params.detalleLotes.toString()
        log.error("detalleLotes: ${detalleLotes}")

        def detalleLotesJSON = new JSONArray(detalleLotes)

        detalleLotesJSON.each {
            def recepcionId = it.getAt("recepcionId").toString().toLong()
            def recepcion = RecepcionDeComplejo.get(recepcionId)
            if(tipoCotizacion.equals("COTIZACION DIARIA"))
                recepcion.cotizacionDiariaDeMinerales=cotizacionDiariaDeMinerales
            else
                recepcion.cotizacionQuincenalDeMinerales=cotizacionQuincenalDeMinerales
            recepcion.save(failOnError: true, flush: true)

            log.error("**** LOTE: ${recepcion.toString()}")
        }

        def recepcionesList = []
        detalleLotesJSON.each {
            def recepcionId = it.getAt("recepcionId").toString().toLong()
            def recepcion = RecepcionDeComplejo.get(recepcionId)
            def mapaRecepcion = [:]
            mapaRecepcion.put("lote", recepcion.toString())
            mapaRecepcion.put("recepcionId", recepcion.id)
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("fechaDeCotizacionQuincenal", recepcion.cotizacionQuincenalDeMinerales.toString())
            mapaRecepcion.put("fechaDeCotizacionDiaria", recepcion.cotizacionDiariaDeMinerales.toString())//anticipoSugerido

            recepcionesList.add(mapaRecepcion)
        }

        render([_detalleLotes: (recepcionesList as JSON).toString()] as JSON)
    }

    def seccionesDeEmpresa = {
        def recepcion = RecepcionDeComplejo.get(params.recepcionId)
        def empresa = Empresa.get(params.empresaId)
        def empresaSeccionList = EmpresaSeccion.findAllByEmpresa(empresa)

        log.error("recepcion: $recepcion")
        //    <g:select id= "empresaSeccion"  name="empresaSeccion.id" from="${EmpresaSeccion.list(sort: 'nombreSeccion')}" optionKey="id" value="${recepcionDeComplejoInstance?.empresaSeccion?.id}" class="many-to-one, chosen-select"/>
        render g.select(id: 'empresaSeccion', name: "empresaSeccion.id", from: empresaSeccionList, optionKey: "id", value: "${recepcion? recepcion.empresaSeccion.id: null}", class: "many-to-one, chosen-select")
//        render g.select(id: 'empresaSeccion', name: "empresaSeccion.id", from: empresaSeccionList, optionKey: "id", class: "many-to-one")
    }
}
