package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.recepcion.RecepcionDeAntimonio
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class LiquidacionDeAntimonioController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [liquidacionDeAntimonioInstanceList: LiquidacionDeAntimonio.list(params), liquidacionDeAntimonioInstanceTotal: LiquidacionDeAntimonio.count()]
    }

    def create() {
        [liquidacionDeAntimonioInstance: new LiquidacionDeAntimonio(params)]
    }

    def save() {
        def liquidacionDeAntimonioInstance = new LiquidacionDeAntimonio(params)
        if (!liquidacionDeAntimonioInstance.save(flush: true)) {
            render(view: "create", model: [liquidacionDeAntimonioInstance: liquidacionDeAntimonioInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionDeAntimonio.label', default: 'LiquidacionDeAntimonio'), liquidacionDeAntimonioInstance.id])
        redirect(action: "show", id: liquidacionDeAntimonioInstance.id)
    }

    def show(Long id) {
        def liquidacionDeAntimonioInstance = LiquidacionDeAntimonio.get(id)
        if (!liquidacionDeAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeAntimonio.label', default: 'LiquidacionDeAntimonio'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDeAntimonioInstance: liquidacionDeAntimonioInstance]
    }

    def edit(Long id) {
        def liquidacionDeAntimonioInstance = LiquidacionDeAntimonio.get(id)
        if (!liquidacionDeAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeAntimonio.label', default: 'LiquidacionDeAntimonio'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDeAntimonioInstance: liquidacionDeAntimonioInstance]
    }

    def update(Long id, Long version) {
        def liquidacionDeAntimonioInstance = LiquidacionDeAntimonio.get(id)
        if (!liquidacionDeAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeAntimonio.label', default: 'LiquidacionDeAntimonio'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (liquidacionDeAntimonioInstance.version > version) {
                liquidacionDeAntimonioInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'liquidacionDeAntimonio.label', default: 'LiquidacionDeAntimonio')] as Object[],
                        "Another user has updated this LiquidacionDeAntimonio while you were editing")
                render(view: "edit", model: [liquidacionDeAntimonioInstance: liquidacionDeAntimonioInstance])
                return
            }
        }

        liquidacionDeAntimonioInstance.properties = params

        if (!liquidacionDeAntimonioInstance.save(flush: true)) {
            render(view: "edit", model: [liquidacionDeAntimonioInstance: liquidacionDeAntimonioInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'liquidacionDeAntimonio.label', default: 'LiquidacionDeAntimonio'), liquidacionDeAntimonioInstance.id])
        redirect(action: "show", id: liquidacionDeAntimonioInstance.id)
    }

    def delete(Long id) {
        def liquidacionDeAntimonioInstance = LiquidacionDeAntimonio.get(id)
        if (!liquidacionDeAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeAntimonio.label', default: 'LiquidacionDeAntimonio'), id])
            redirect(action: "list")
            return
        }

        try {
            /* CAMBIAR EL ESTADO DEL LOTE */
            def recepcion = RecepcionDeAntimonio.get(liquidacionDeAntimonioInstance.recepcionDeAntimonio.id)
            recepcion.estadoDelLote = "NO LIQUIDADO"
            recepcion.save()
            /* FIN - CAMBIAR EL ESTADO DEL LOTE*/
            
            /* ELIMINAR LAS RETENCIONES*/
            def listaRetenciones = LiquidacionDeAntimonioRetenciones.findAllByLiquidacionDeAntimonio(liquidacionDeAntimonioInstance)
            listaRetenciones.each {
                it.delete(flush: true)
            }
            /* FIN - ELIMINAR RETENCIONES*/
            
            liquidacionDeAntimonioInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'liquidacionDeAntimonio.label', default: 'LiquidacionDeAntimonio'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDeAntimonio.label', default: 'LiquidacionDeAntimonio'), id])
            redirect(action: "show", id: id)
        }
    }

    def cancelacionDeLote(Long id) {
        def liquidacionDeAntimonioInstance = LiquidacionDeAntimonio.get(id)
        if (!liquidacionDeAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeAntimonio.label', default: 'LiquidacionDeAntimonio'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionDeAntimonioInstance.fechaDeCancelacion=new java.util.Date()
            liquidacionDeAntimonioInstance.save(failOnError: true)
            flash.message = "Se ha establecido la Fecha de Cancelacion del Lote"
            redirect(action: "show", id: id)
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDeAntimonio.label', default: 'LiquidacionDeAntimonio'), id])
            redirect(action: "show", id: id)
        }
    }

    def eliminarDeConjunto(Long id) {
        def liquidacionDeAntimonioInstance = LiquidacionDeAntimonio.get(id)
        if (!liquidacionDeAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeAntimonio.label', default: 'LiquidacionDeAntimonio'), id])
            redirect(action: "list")
            return
        }

        try {
            System.out.println("*** LOCALIZADO LOTE: ${liquidacionDeAntimonioInstance.lote} DEL CONJUNTO: ${liquidacionDeAntimonioInstance.conjuntoAntimonio}")
            liquidacionDeAntimonioInstance.conjuntoAntimonio="-"
            liquidacionDeAntimonioInstance.save(failOnError: true)
            flash.message = "El Lote ha sido eliminado del Conjunto anteriormente asignado."
            redirect(action: "show", id: id)
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDeAntimonio.label', default: 'LiquidacionDeAntimonio'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporte = {
        def liquidacionDeAntimonio = LiquidacionDeAntimonio.get(params.id)
        params.SUBREPORT_DIR = "${org.socymet.util.ReportesRuntime.realPath('/reports')}/"
        chain(controller:'jasper',action:'index',model:[data:liquidacionDeAntimonio],params:params)
    }

    def liquidacionesJSON() {
        def lote = params.term.toString()
        def liquidacionDeAntimonios = LiquidacionDeAntimonio.findAllByLoteLikeAndConjuntoAntimonio("%${lote}%","-")
        def liquidacionesList = []
        liquidacionDeAntimonios.each { liquidacion ->
            def liquidacionMap = [:]
            liquidacionMap.put("liquidacionId", liquidacion.id)
            liquidacionMap.put("label", liquidacion.lote)
            liquidacionMap.put("value", liquidacion.lote)
            liquidacionMap.put("nombreCliente", liquidacion.nombreCliente)
            liquidacionMap.put("nombreEmpresa", liquidacion.nombreEmpresa)
            liquidacionMap.put("fechaDeRecepcion", liquidacion.fechaDeRecepcion)
            liquidacionMap.put("fechaDeLiquidacion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(liquidacion.fechaDeLiquidacion))
            liquidacionMap.put("kilosNetosSecos", liquidacion.kilosNetosSecos)
            liquidacionMap.put("porcentajeAntimonio", liquidacion.porcentajeAntimonio)
            liquidacionesList.add(liquidacionMap)
        }
        render liquidacionesList as JSON
    }

    def lotesEnConjuntoJSON() {
        def lote = params.term.toString()
        def liquidacionDeAntimonios = LiquidacionDeAntimonio.findAllByLoteLikeAndConjuntoAntimonioNotEqual("%${lote}%","-")
        def liquidacionesList = []
        liquidacionDeAntimonios.each { liquidacion ->
            def liquidacionMap = [:]
            liquidacionMap.put("liquidacionId", liquidacion.id)
            liquidacionMap.put("label", liquidacion.lote)
            liquidacionMap.put("value", liquidacion.lote)
            liquidacionMap.put("nombreCliente", liquidacion.nombreCliente)
            liquidacionMap.put("nombreEmpresa", liquidacion.nombreEmpresa)
            liquidacionMap.put("fechaDeRecepcion", liquidacion.fechaDeRecepcion)
            liquidacionMap.put("fechaDeLiquidacion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(liquidacion.fechaDeLiquidacion))
            liquidacionMap.put("kilosNetosSecos", liquidacion.kilosNetosSecos)
            liquidacionMap.put("porcentajeAntimonio", liquidacion.porcentajeAntimonio)
            liquidacionMap.put("conjuntoAntimonio", liquidacion.conjuntoAntimonio)
            liquidacionesList.add(liquidacionMap)
        }
        render liquidacionesList as JSON
    }
}
