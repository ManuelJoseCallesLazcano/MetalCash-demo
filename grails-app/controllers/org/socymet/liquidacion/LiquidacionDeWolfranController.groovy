package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.recepcion.RecepcionDeWolfran
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class LiquidacionDeWolfranController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [liquidacionDeWolfranInstanceList: LiquidacionDeWolfran.list(params), liquidacionDeWolfranInstanceTotal: LiquidacionDeWolfran.count()]
    }

    def create() {
        [liquidacionDeWolfranInstance: new LiquidacionDeWolfran(params)]
    }

    def save() {
        def liquidacionDeWolfranInstance = new LiquidacionDeWolfran(params)
        if (!liquidacionDeWolfranInstance.save(flush: true)) {
            render(view: "create", model: [liquidacionDeWolfranInstance: liquidacionDeWolfranInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionDeWolfran.label', default: 'LiquidacionDeWolfran'), liquidacionDeWolfranInstance.id])
        redirect(action: "show", id: liquidacionDeWolfranInstance.id)
    }

    def show(Long id) {
        def liquidacionDeWolfranInstance = LiquidacionDeWolfran.get(id)
        if (!liquidacionDeWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeWolfran.label', default: 'LiquidacionDeWolfran'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDeWolfranInstance: liquidacionDeWolfranInstance]
    }

    def edit(Long id) {
        def liquidacionDeWolfranInstance = LiquidacionDeWolfran.get(id)
        if (!liquidacionDeWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeWolfran.label', default: 'LiquidacionDeWolfran'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDeWolfranInstance: liquidacionDeWolfranInstance]
    }

    def update(Long id, Long version) {
        def liquidacionDeWolfranInstance = LiquidacionDeWolfran.get(id)
        if (!liquidacionDeWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeWolfran.label', default: 'LiquidacionDeWolfran'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (liquidacionDeWolfranInstance.version > version) {
                liquidacionDeWolfranInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'liquidacionDeWolfran.label', default: 'LiquidacionDeWolfran')] as Object[],
                        "Another user has updated this LiquidacionDeWolfran while you were editing")
                render(view: "edit", model: [liquidacionDeWolfranInstance: liquidacionDeWolfranInstance])
                return
            }
        }

        liquidacionDeWolfranInstance.properties = params

        if (!liquidacionDeWolfranInstance.save(flush: true)) {
            render(view: "edit", model: [liquidacionDeWolfranInstance: liquidacionDeWolfranInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'liquidacionDeWolfran.label', default: 'LiquidacionDeWolfran'), liquidacionDeWolfranInstance.id])
        redirect(action: "show", id: liquidacionDeWolfranInstance.id)
    }

    def delete(Long id) {
        def liquidacionDeWolfranInstance = LiquidacionDeWolfran.get(id)
        if (!liquidacionDeWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeWolfran.label', default: 'LiquidacionDeWolfran'), id])
            redirect(action: "list")
            return
        }

        try {
            /* CAMBIAR EL ESTADO DEL LOTE */
            def recepcion = RecepcionDeWolfran.get(liquidacionDeWolfranInstance.recepcionDeWolfran.id)
            recepcion.estadoDelLote = "NO LIQUIDADO"
            recepcion.save()
            /* FIN - CAMBIAR EL ESTADO DEL LOTE*/
            
            /* ELIMINAR LAS RETENCIONES*/
            def listaRetenciones = LiquidacionDeWolfranRetenciones.findAllByLiquidacionDeWolfran(liquidacionDeWolfranInstance)
            listaRetenciones.each {
                it.delete(flush: true)
            }
            /* FIN - ELIMINAR RETENCIONES*/
            
            liquidacionDeWolfranInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'liquidacionDeWolfran.label', default: 'LiquidacionDeWolfran'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDeWolfran.label', default: 'LiquidacionDeWolfran'), id])
            redirect(action: "show", id: id)
        }
    }

    def eliminarDeConjunto(Long id) {
        def liquidacionDeWolfranInstance = LiquidacionDeWolfran.get(id)
        if (!liquidacionDeWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeWolfran.label', default: 'LiquidacionDeWolfran'), id])
            redirect(action: "list")
            return
        }

        try {
            System.out.println("*** LOCALIZADO LOTE: ${liquidacionDeWolfranInstance.lote} DEL CONJUNTO: ${liquidacionDeWolfranInstance.conjuntoWolfran}")
            liquidacionDeWolfranInstance.conjuntoWolfran="-"
            liquidacionDeWolfranInstance.save(failOnError: true)
            flash.message = "El Lote ha sido eliminado del Conjunto anteriormente asignado."
            redirect(action: "show", id: id)
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDeWolfran.label', default: 'LiquidacionDeWolfran'), id])
            redirect(action: "show", id: id)
        }
    }

    def cancelacionDeLote(Long id) {
        def liquidacionDeWolfranInstance = LiquidacionDeWolfran.get(id)
        if (!liquidacionDeWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeWolfran.label', default: 'LiquidacionDeWolfran'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionDeWolfranInstance.fechaDeCancelacion=new java.util.Date()
            liquidacionDeWolfranInstance.save(failOnError: true)
            flash.message = "Se ha establecido la Fecha de Cancelacion del Lote"
            redirect(action: "show", id: id)
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDeWolfran.label', default: 'LiquidacionDeWolfran'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporte = {
        def liquidacionDeWolfran = LiquidacionDeWolfran.get(params.id)
        params.SUBREPORT_DIR = "${servletContext.getRealPath('/reports')}/"
        chain(controller:'jasper',action:'index',model:[data:liquidacionDeWolfran],params:params)
    }

    def liquidacionesJSON() {
        def lote = params.term.toString()
        def liquidacionDeWolfrans = LiquidacionDeWolfran.findAllByLoteLikeAndConjuntoWolfran("%${lote}%","-")
        def liquidacionesList = []
        liquidacionDeWolfrans.each { liquidacion ->
            def liquidacionMap = [:]
            liquidacionMap.put("liquidacionId", liquidacion.id)
            liquidacionMap.put("label", liquidacion.lote)
            liquidacionMap.put("value", liquidacion.lote)
            liquidacionMap.put("nombreCliente", liquidacion.nombreCliente)
            liquidacionMap.put("nombreEmpresa", liquidacion.nombreEmpresa)
            liquidacionMap.put("fechaDeRecepcion", liquidacion.fechaDeRecepcion)
            liquidacionMap.put("fechaDeLiquidacion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(liquidacion.fechaDeLiquidacion))
            liquidacionMap.put("kilosNetosSecos", liquidacion.kilosNetosSecos)
            liquidacionMap.put("porcentajeWolfran", liquidacion.porcentajeWolfran)
            liquidacionesList.add(liquidacionMap)
        }
        render liquidacionesList as JSON
    }

    def lotesEnConjuntoJSON() {
        def lote = params.term.toString()
        def liquidacionDeWolfrans = LiquidacionDeWolfran.findAllByLoteLikeAndConjuntoWolfranNotEqual("%${lote}%","-")
        def liquidacionesList = []
        liquidacionDeWolfrans.each { liquidacion ->
            def liquidacionMap = [:]
            liquidacionMap.put("liquidacionId", liquidacion.id)
            liquidacionMap.put("label", liquidacion.lote)
            liquidacionMap.put("value", liquidacion.lote)
            liquidacionMap.put("nombreCliente", liquidacion.nombreCliente)
            liquidacionMap.put("nombreEmpresa", liquidacion.nombreEmpresa)
            liquidacionMap.put("fechaDeRecepcion", liquidacion.fechaDeRecepcion)
            liquidacionMap.put("fechaDeLiquidacion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(liquidacion.fechaDeLiquidacion))
            liquidacionMap.put("kilosNetosSecos", liquidacion.kilosNetosSecos)
            liquidacionMap.put("porcentajeWolfran", liquidacion.porcentajeWolfran)
            liquidacionMap.put("conjuntoWolfran", liquidacion.conjuntoWolfran)
            liquidacionesList.add(liquidacionMap)
        }
        render liquidacionesList as JSON
    }
}
