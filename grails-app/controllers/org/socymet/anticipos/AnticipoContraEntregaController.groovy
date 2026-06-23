package org.socymet.anticipos
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.recepcion.*
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class AnticipoContraEntregaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [anticipoContraEntregaInstanceList: AnticipoContraEntrega.list(params), anticipoContraEntregaInstanceTotal: AnticipoContraEntrega.count()]
    }

    def create() {
        [anticipoContraEntregaInstance: new AnticipoContraEntrega(params)]
    }

    def save() {
        def anticipoContraEntregaInstance = new AnticipoContraEntrega(params)
        if (!anticipoContraEntregaInstance.save(flush: true)) {
            render(view: "create", model: [anticipoContraEntregaInstance: anticipoContraEntregaInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'anticipoContraEntrega.label', default: 'AnticipoContraEntrega'), anticipoContraEntregaInstance.id])
        redirect(action: "show", id: anticipoContraEntregaInstance.id)
    }

    def show(Long id) {
        def anticipoContraEntregaInstance = AnticipoContraEntrega.get(id)
        if (!anticipoContraEntregaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipoContraEntrega.label', default: 'AnticipoContraEntrega'), id])
            redirect(action: "list")
            return
        }

        [anticipoContraEntregaInstance: anticipoContraEntregaInstance]
    }

    def edit(Long id) {
        def anticipoContraEntregaInstance = AnticipoContraEntrega.get(id)
        if (!anticipoContraEntregaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipoContraEntrega.label', default: 'AnticipoContraEntrega'), id])
            redirect(action: "list")
            return
        }

        [anticipoContraEntregaInstance: anticipoContraEntregaInstance]
    }

    def update(Long id, Long version) {
        def anticipoContraEntregaInstance = AnticipoContraEntrega.get(id)
        if (!anticipoContraEntregaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipoContraEntrega.label', default: 'AnticipoContraEntrega'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (anticipoContraEntregaInstance.version > version) {
                anticipoContraEntregaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'anticipoContraEntrega.label', default: 'AnticipoContraEntrega')] as Object[],
                          "Another user has updated this AnticipoContraEntrega while you were editing")
                render(view: "edit", model: [anticipoContraEntregaInstance: anticipoContraEntregaInstance])
                return
            }
        }

        anticipoContraEntregaInstance.properties = params

        if (!anticipoContraEntregaInstance.save(flush: true)) {
            render(view: "edit", model: [anticipoContraEntregaInstance: anticipoContraEntregaInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'anticipoContraEntrega.label', default: 'AnticipoContraEntrega'), anticipoContraEntregaInstance.id])
        redirect(action: "show", id: anticipoContraEntregaInstance.id)
    }

    def delete(Long id) {
        def anticipoContraEntregaInstance = AnticipoContraEntrega.get(id)
        if (!anticipoContraEntregaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipoContraEntrega.label', default: 'AnticipoContraEntrega'), id])
            redirect(action: "list")
            return
        }

        try {
            anticipoContraEntregaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'anticipoContraEntrega.label', default: 'AnticipoContraEntrega'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'anticipoContraEntrega.label', default: 'AnticipoContraEntrega'), id])
            redirect(action: "show", id: id)
        }
    }

    def recepcionesJSON(params) {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesAntimonio = RecepcionDeAntimonio.findAllByLoteAntimonioAndEstadoDelLote(lote,"NO LIQUIDADO")
        def recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejoAndEstadoDelLote(lote,"NO LIQUIDADO")
        def recepcionesEstano = RecepcionDeEstano.findAllByLoteEstanoAndEstadoDelLote(lote,"NO LIQUIDADO")
        def recepcionesPlata = RecepcionDePlata.findAllByLotePlataAndEstadoDelLote(lote,"NO LIQUIDADO")
        def recepcionesPlomoPlata = RecepcionDePlomoPlata.findAllByLotePlomoPlataAndEstadoDelLote(lote,"NO LIQUIDADO")
        def recepcionesWolfran = RecepcionDeWolfran.findAllByLoteWolfranAndEstadoDelLote(lote,"NO LIQUIDADO")
        def recepcionesZincPlata = RecepcionDeZincPlata.findAllByLoteZincPlataAndEstadoDelLote(lote,"NO LIQUIDADO")
        def recepcionesList = []
        /*insertToList(recepcionesList, recepcionesAntimonio)
        insertToList(recepcionesList, recepcionesComplejo)
        insertToList(recepcionesList, recepcionesEstano)
        insertToList(recepcionesList, recepcionesPlata)
        insertToList(recepcionesList, recepcionesPlomoPlata)
        insertToList(recepcionesList, recepcionesWolfran)
        insertToList(recepcionesList, recepcionesZincPlata)*/
        insertToList("SB-",recepcionesList, recepcionesAntimonio)
        insertToList("CM-",recepcionesList, recepcionesComplejo)
        insertToList("SN-",recepcionesList, recepcionesEstano)
        insertToList("AG-",recepcionesList, recepcionesPlata)
        insertToList("PBAG-",recepcionesList, recepcionesPlomoPlata)
        insertToList("WO3-",recepcionesList, recepcionesWolfran)
        insertToList("ZNAG-",recepcionesList, recepcionesZincPlata)
        render recepcionesList as JSON
    }

    def insertToList = { elemento, recepcionesList, recepcionList->
        recepcionList.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("recepcionId", recepcion.id)
            mapaRecepcion.put("label", elemento+recepcion.toString())
            mapaRecepcion.put("value", elemento+recepcion.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.toString())
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            recepcionesList.add(mapaRecepcion)
        }
    }

    def crearReporte = {
        def anticipoContraEntrega = AnticipoContraEntrega.get(params.id)
        chain(controller:'jasper',action:'index',model:[data:anticipoContraEntrega],params:params)
    }
}
