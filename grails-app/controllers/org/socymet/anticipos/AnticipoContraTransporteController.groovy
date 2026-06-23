package org.socymet.anticipos
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.recepcion.*
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class AnticipoContraTransporteController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [anticipoContraTransporteInstanceList: AnticipoContraTransporte.list(params), anticipoContraTransporteInstanceTotal: AnticipoContraTransporte.count()]
    }

    def create() {
        [anticipoContraTransporteInstance: new AnticipoContraTransporte(params)]
    }

    def save() {
        def anticipoContraTransporteInstance = new AnticipoContraTransporte(params)
        if (!anticipoContraTransporteInstance.save(flush: true)) {
            render(view: "create", model: [anticipoContraTransporteInstance: anticipoContraTransporteInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'anticipoContraTransporte.label', default: 'AnticipoContraTransporte'), anticipoContraTransporteInstance.id])
        redirect(action: "show", id: anticipoContraTransporteInstance.id)
    }

    def show(Long id) {
        def anticipoContraTransporteInstance = AnticipoContraTransporte.get(id)
        if (!anticipoContraTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipoContraTransporte.label', default: 'AnticipoContraTransporte'), id])
            redirect(action: "list")
            return
        }

        [anticipoContraTransporteInstance: anticipoContraTransporteInstance]
    }

    def edit(Long id) {
        def anticipoContraTransporteInstance = AnticipoContraTransporte.get(id)
        if (!anticipoContraTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipoContraTransporte.label', default: 'AnticipoContraTransporte'), id])
            redirect(action: "list")
            return
        }

        [anticipoContraTransporteInstance: anticipoContraTransporteInstance]
    }

    def update(Long id, Long version) {
        def anticipoContraTransporteInstance = AnticipoContraTransporte.get(id)
        if (!anticipoContraTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipoContraTransporte.label', default: 'AnticipoContraTransporte'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (anticipoContraTransporteInstance.version > version) {
                anticipoContraTransporteInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'anticipoContraTransporte.label', default: 'AnticipoContraTransporte')] as Object[],
                        "Another user has updated this AnticipoContraTransporte while you were editing")
                render(view: "edit", model: [anticipoContraTransporteInstance: anticipoContraTransporteInstance])
                return
            }
        }

        anticipoContraTransporteInstance.properties = params

        if (!anticipoContraTransporteInstance.save(flush: true)) {
            render(view: "edit", model: [anticipoContraTransporteInstance: anticipoContraTransporteInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'anticipoContraTransporte.label', default: 'AnticipoContraTransporte'), anticipoContraTransporteInstance.id])
        redirect(action: "show", id: anticipoContraTransporteInstance.id)
    }

    def delete(Long id) {
        def anticipoContraTransporteInstance = AnticipoContraTransporte.get(id)
        if (!anticipoContraTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipoContraTransporte.label', default: 'AnticipoContraTransporte'), id])
            redirect(action: "list")
            return
        }

        try {
            anticipoContraTransporteInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'anticipoContraTransporte.label', default: 'AnticipoContraTransporte'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'anticipoContraTransporte.label', default: 'AnticipoContraTransporte'), id])
            redirect(action: "show", id: id)
        }
    }

    def recepcionesJSON(params) {
        def lote = Integer.parseInt(params.term.toString())
        /*def recepcionesAntimonio = RecepcionDeAntimonio.findAllByLoteAntimonioAndEstadoDelLote(lote,"NO LIQUIDADO")
        def recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejoAndEstadoDelLote(lote,"NO LIQUIDADO")
        def recepcionesEstano = RecepcionDeEstano.findAllByLoteEstanoAndEstadoDelLote(lote,"NO LIQUIDADO")
        def recepcionesPlata = RecepcionDePlata.findAllByLotePlataAndEstadoDelLote(lote,"NO LIQUIDADO")
        def recepcionesPlomoPlata = RecepcionDePlomoPlata.findAllByLotePlomoPlataAndEstadoDelLote(lote,"NO LIQUIDADO")
        def recepcionesWolfran = RecepcionDeWolfran.findAllByLoteWolfranAndEstadoDelLote(lote,"NO LIQUIDADO")
        def recepcionesZincPlata = RecepcionDeZincPlata.findAllByLoteZincPlataAndEstadoDelLote(lote,"NO LIQUIDADO")*/
        def recepcionesAntimonio = RecepcionDeAntimonio.findAllByLoteAntimonio(lote)
        def recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejo(lote)
        def recepcionesEstano = RecepcionDeEstano.findAllByLoteEstano(lote)
        def recepcionesPlata = RecepcionDePlata.findAllByLotePlata(lote)
        def recepcionesPlomoPlata = RecepcionDePlomoPlata.findAllByLotePlomoPlata(lote)
        def recepcionesWolfran = RecepcionDeWolfran.findAllByLoteWolfran(lote)
        def recepcionesZincPlata = RecepcionDeZincPlata.findAllByLoteZincPlata(lote)
        def recepcionesList = []
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
            mapaRecepcion.put("costoDeTransporte", recepcion.costoDeTransporte)
            recepcionesList.add(mapaRecepcion)
        }
    }

    def crearReporte = {
        def anticipoContraTransporte = AnticipoContraTransporte.get(params.id)
        chain(controller:'jasper',action:'index',model:[data:anticipoContraTransporte],params:params)
    }
}
