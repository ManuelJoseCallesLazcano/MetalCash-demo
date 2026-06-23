package org.socymet.cancelacion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.liquidacion.*
import org.socymet.seguridad.SecUser

//import org.socymet.liquidacion.LiquidacionDePlomoPlata
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

//import org.socymet.liquidacion.LiquidacionDeZincPlata
@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class CancelacionController {
    def springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [cancelacionInstanceList: Cancelacion.list(params), cancelacionInstanceTotal: Cancelacion.count()]
    }

    def create() {
        [cancelacionInstance: new Cancelacion(params)]
    }

    def save() {
        def cancelacionInstance = new Cancelacion(params)
        if (!cancelacionInstance.save(flush: true)) {
            render(view: "create", model: [cancelacionInstance: cancelacionInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'cancelacion.label', default: 'Cancelacion'), cancelacionInstance.id])
        redirect(action: "show", id: cancelacionInstance.id)
    }

    def show(Long id) {
        def cancelacionInstance = Cancelacion.get(id)
        if (!cancelacionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cancelacion.label', default: 'Cancelacion'), id])
            redirect(action: "list")
            return
        }

        [cancelacionInstance: cancelacionInstance]
    }

    def edit(Long id) {
        def cancelacionInstance = Cancelacion.get(id)
        if (!cancelacionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cancelacion.label', default: 'Cancelacion'), id])
            redirect(action: "list")
            return
        }

        [cancelacionInstance: cancelacionInstance]
    }

    def update(Long id, Long version) {
        def cancelacionInstance = Cancelacion.get(id)
        if (!cancelacionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cancelacion.label', default: 'Cancelacion'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (cancelacionInstance.version > version) {
                cancelacionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'cancelacion.label', default: 'Cancelacion')] as Object[],
                          "Another user has updated this Cancelacion while you were editing")
                render(view: "edit", model: [cancelacionInstance: cancelacionInstance])
                return
            }
        }

        cancelacionInstance.properties = params

        if (!cancelacionInstance.save(flush: true)) {
            render(view: "edit", model: [cancelacionInstance: cancelacionInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'cancelacion.label', default: 'Cancelacion'), cancelacionInstance.id])
        redirect(action: "show", id: cancelacionInstance.id)
    }

    def delete(Long id) {
        def cancelacionInstance = Cancelacion.get(id)
        if (!cancelacionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cancelacion.label', default: 'Cancelacion'), id])
            redirect(action: "list")
            return
        }

        try {
            def liquidacionId = cancelacionInstance.liquidacionId
            def fechaPorDefecto = new java.util.Date(84,5,14)
            def liquidacionEstano = LiquidacionDeEstano.get(liquidacionId)
            def liquidacionPlata = LiquidacionDePlata.get(liquidacionId)
            def liquidacionWolfran = LiquidacionDeWolfran.get(liquidacionId)
            def liquidacionAntimonio = LiquidacionDeAntimonio.get(liquidacionId)
            def liquidacionComplejo = LiquidacionDeComplejo.get(liquidacionId)
            def liquidacionPlomoPlata = LiquidacionDePlomoPlata.get(liquidacionId)
            def liquidacionZincPlata = LiquidacionDeZincPlata.get(liquidacionId)
            def liquidacionCobrePlata = LiquidacionDeCobrePlata.get(liquidacionId)

            if(liquidacionEstano){
                liquidacionEstano.fechaDeCancelacion=fechaPorDefecto
                liquidacionEstano.save()
            }
            if(liquidacionPlata){
                liquidacionPlata.fechaDeCancelacion=fechaPorDefecto
                liquidacionPlata.save()
            }
            if(liquidacionWolfran){
                liquidacionWolfran.fechaDeCancelacion=fechaPorDefecto
                liquidacionWolfran.save()
            }
            if(liquidacionAntimonio){
                liquidacionAntimonio.fechaDeCancelacion=fechaPorDefecto
                liquidacionAntimonio.save()
            }
            if(liquidacionComplejo){
                liquidacionComplejo.fechaDeCancelacion=fechaPorDefecto
                liquidacionComplejo.save()
            }
            if(liquidacionPlomoPlata){
                liquidacionPlomoPlata.fechaDeCancelacion=fechaPorDefecto
                liquidacionPlomoPlata.save()
            }
            if(liquidacionZincPlata){
                liquidacionZincPlata.fechaDeCancelacion=fechaPorDefecto
                liquidacionZincPlata.save()
            }
            if(liquidacionCobrePlata){
                liquidacionCobrePlata.fechaDeCancelacion=fechaPorDefecto
                liquidacionCobrePlata.save()
            }
            
            cancelacionInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'cancelacion.label', default: 'Cancelacion'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'cancelacion.label', default: 'Cancelacion'), id])
            redirect(action: "show", id: id)
        }
    }

    def recepcionesJSON(params) {
        def fechaPorDefecto = new java.util.Date(84,5,14)
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        def lote = Integer.parseInt(params.term.toString())
        def liquidacionesComplejo = LiquidacionDeComplejo.findAllByDepositoAndLoteLikeAndFechaDeCancelacion(usuarioActual.deposito,"%${lote}%",fechaPorDefecto)
        def liquidacionesList = []
        insertToList("",liquidacionesList, liquidacionesComplejo)
        render liquidacionesList as JSON
    }

    def insertToList = { elemento, liquidacionesList, liquidacionList->
        liquidacionList.each { liquidacion ->
            def mapaLiquidacion = [:]
            mapaLiquidacion.put("label", elemento+liquidacion.lote)
            mapaLiquidacion.put("value", elemento+liquidacion.lote)
            mapaLiquidacion.put("liquidacionId", liquidacion.id)
            mapaLiquidacion.put("nombreCliente", liquidacion.nombreCliente)
            mapaLiquidacion.put("nombreEmpresa", liquidacion.nombreEmpresa)
            mapaLiquidacion.put("fechaDeRecepcion", liquidacion.fechaDeRecepcion)
            mapaLiquidacion.put("fechaDeLiquidacion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(liquidacion.fechaDeLiquidacion))
            mapaLiquidacion.put("totalLiquidoPagable", liquidacion.totalLiquidoPagable)
            liquidacionesList.add(mapaLiquidacion)
        }
    }
}
