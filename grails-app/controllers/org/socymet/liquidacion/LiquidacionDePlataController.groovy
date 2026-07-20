package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.recepcion.RecepcionDePlata
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class LiquidacionDePlataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [liquidacionDePlataInstanceList: LiquidacionDePlata.list(params), liquidacionDePlataInstanceTotal: LiquidacionDePlata.count()]
    }

    def create() {
        [liquidacionDePlataInstance: new LiquidacionDePlata(params)]
    }

    def save() {
        def liquidacionDePlataInstance = new LiquidacionDePlata(params)
        if (!liquidacionDePlataInstance.save(flush: true)) {
            render(view: "create", model: [liquidacionDePlataInstance: liquidacionDePlataInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionDePlata.label', default: 'LiquidacionDePlata'), liquidacionDePlataInstance.id])
        redirect(action: "show", id: liquidacionDePlataInstance.id)
    }

    def show(Long id) {
        def liquidacionDePlataInstance = LiquidacionDePlata.get(id)
        if (!liquidacionDePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDePlata.label', default: 'LiquidacionDePlata'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDePlataInstance: liquidacionDePlataInstance]
    }

    def edit(Long id) {
        def liquidacionDePlataInstance = LiquidacionDePlata.get(id)
        if (!liquidacionDePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDePlata.label', default: 'LiquidacionDePlata'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDePlataInstance: liquidacionDePlataInstance]
    }

    def update(Long id, Long version) {
        def liquidacionDePlataInstance = LiquidacionDePlata.get(id)
        if (!liquidacionDePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDePlata.label', default: 'LiquidacionDePlata'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (liquidacionDePlataInstance.version > version) {
                liquidacionDePlataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'liquidacionDePlata.label', default: 'LiquidacionDePlata')] as Object[],
                        "Another user has updated this LiquidacionDePlata while you were editing")
                render(view: "edit", model: [liquidacionDePlataInstance: liquidacionDePlataInstance])
                return
            }
        }

        liquidacionDePlataInstance.properties = params

        if (!liquidacionDePlataInstance.save(flush: true)) {
            render(view: "edit", model: [liquidacionDePlataInstance: liquidacionDePlataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'liquidacionDePlata.label', default: 'LiquidacionDePlata'), liquidacionDePlataInstance.id])
        redirect(action: "show", id: liquidacionDePlataInstance.id)
    }

    def delete(Long id) {
        def liquidacionDePlataInstance = LiquidacionDePlata.get(id)
        if (!liquidacionDePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDePlata.label', default: 'LiquidacionDePlata'), id])
            redirect(action: "list")
            return
        }

        try {
            /* CAMBIAR EL ESTADO DEL LOTE */
            def recepcion = RecepcionDePlata.get(liquidacionDePlataInstance.recepcionDePlata.id)
            recepcion.estadoDelLote = "NO LIQUIDADO"
            recepcion.save()
            /* FIN - CAMBIAR EL ESTADO DEL LOTE*/
            
            /* ELIMINAR LAS RETENCIONES*/
            def listaRetenciones = LiquidacionDePlataRetenciones.findAllByLiquidacionDePlata(liquidacionDePlataInstance)
            listaRetenciones.each {
                it.delete(flush: true)
            }
            /* FIN - ELIMINAR RETENCIONES*/
            
            liquidacionDePlataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'liquidacionDePlata.label', default: 'LiquidacionDePlata'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDePlata.label', default: 'LiquidacionDePlata'), id])
            redirect(action: "show", id: id)
        }
    }

    def eliminarDeConjunto(Long id) {
        def liquidacionDePlataInstance = LiquidacionDePlata.get(id)
        if (!liquidacionDePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDePlata.label', default: 'LiquidacionDePlata'), id])
            redirect(action: "list")
            return
        }

        try {
            System.out.println("*** LOCALIZADO LOTE: ${liquidacionDePlataInstance.lote} DEL CONJUNTO: ${liquidacionDePlataInstance.conjuntoPlata}")
            liquidacionDePlataInstance.conjuntoPlata="-"
            liquidacionDePlataInstance.save(failOnError: true)
            flash.message = "El Lote ha sido eliminado del Conjunto anteriormente asignado."
            redirect(action: "show", id: id)
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDePlata.label', default: 'LiquidacionDePlata'), id])
            redirect(action: "show", id: id)
        }
    }

    def cancelacionDeLote(Long id) {
        def liquidacionDePlataInstance = LiquidacionDePlata.get(id)
        if (!liquidacionDePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDePlata.label', default: 'LiquidacionDePlata'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionDePlataInstance.fechaDeCancelacion=new java.util.Date()
            liquidacionDePlataInstance.save(failOnError: true)
            flash.message = "Se ha establecido la Fecha de Cancelacion del Lote"
            redirect(action: "show", id: id)
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDePlata.label', default: 'LiquidacionDePlata'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporte = {
        def liquidacionDePlata = LiquidacionDePlata.get(params.id)
        params.SUBREPORT_DIR = "${org.socymet.util.ReportesRuntime.realPath('/reports')}/"
        chain(controller:'jasper',action:'index',model:[data:liquidacionDePlata],params:params)
    }

    def liquidacionesJSON() {
        def lote = params.term.toString()
        def liquidacionDePlatas = LiquidacionDePlata.findAllByLoteLikeAndConjuntoPlata("%${lote}%","-")
        def liquidacionesList = []
        liquidacionDePlatas.each { liquidacion ->
            def liquidacionMap = [:]
            liquidacionMap.put("liquidacionId", liquidacion.id)
            liquidacionMap.put("label", liquidacion.lote)
            liquidacionMap.put("value", liquidacion.lote)
            liquidacionMap.put("nombreCliente", liquidacion.nombreCliente)
            liquidacionMap.put("nombreEmpresa", liquidacion.nombreEmpresa)
            liquidacionMap.put("fechaDeRecepcion", liquidacion.fechaDeRecepcion)
            liquidacionMap.put("fechaDeLiquidacion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(liquidacion.fechaDeLiquidacion))
            liquidacionMap.put("kilosNetosSecos", liquidacion.kilosNetosSecos)
            liquidacionMap.put("porcentajePlata", liquidacion.porcentajePlata)
            liquidacionesList.add(liquidacionMap)
        }
        render liquidacionesList as JSON
    }

    def lotesEnConjuntoJSON() {
        def lote = params.term.toString()
        def liquidacionDePlatas = LiquidacionDePlata.findAllByLoteLikeAndConjuntoPlataNotEqual("%${lote}%","-")
        def liquidacionesList = []
        liquidacionDePlatas.each { liquidacion ->
            def liquidacionMap = [:]
            liquidacionMap.put("liquidacionId", liquidacion.id)
            liquidacionMap.put("label", liquidacion.lote)
            liquidacionMap.put("value", liquidacion.lote)
            liquidacionMap.put("nombreCliente", liquidacion.nombreCliente)
            liquidacionMap.put("nombreEmpresa", liquidacion.nombreEmpresa)
            liquidacionMap.put("fechaDeRecepcion", liquidacion.fechaDeRecepcion)
            liquidacionMap.put("fechaDeLiquidacion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(liquidacion.fechaDeLiquidacion))
            liquidacionMap.put("kilosNetosSecos", liquidacion.kilosNetosSecos)
            liquidacionMap.put("porcentajePlata", liquidacion.porcentajePlata)
            liquidacionMap.put("conjuntoPlata", liquidacion.conjuntoPlata)
            liquidacionesList.add(liquidacionMap)
        }
        render liquidacionesList as JSON
    }
}
