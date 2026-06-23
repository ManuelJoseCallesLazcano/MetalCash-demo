package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.recepcion.RecepcionDeEstano
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class CostoTransporteLaboratorioEstanoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [costoTransporteLaboratorioEstanoInstanceList: CostoTransporteLaboratorioEstano.list(params), costoTransporteLaboratorioEstanoInstanceTotal: CostoTransporteLaboratorioEstano.count()]
    }

    def create() {
        [costoTransporteLaboratorioEstanoInstance: new CostoTransporteLaboratorioEstano(params)]
    }

    def save() {
        def costoTransporteLaboratorioEstanoInstance = new CostoTransporteLaboratorioEstano(params)
        if (!costoTransporteLaboratorioEstanoInstance.save(flush: true)) {
            render(view: "create", model: [costoTransporteLaboratorioEstanoInstance: costoTransporteLaboratorioEstanoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'costoTransporteLaboratorioEstano.label', default: 'CostoTransporteLaboratorioEstano'), costoTransporteLaboratorioEstanoInstance.id])
        redirect(action: "show", id: costoTransporteLaboratorioEstanoInstance.id)
    }

    def show(Long id) {
        def costoTransporteLaboratorioEstanoInstance = CostoTransporteLaboratorioEstano.get(id)
        if (!costoTransporteLaboratorioEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'costoTransporteLaboratorioEstano.label', default: 'CostoTransporteLaboratorioEstano'), id])
            redirect(action: "list")
            return
        }

        [costoTransporteLaboratorioEstanoInstance: costoTransporteLaboratorioEstanoInstance]
    }

    def edit(Long id) {
        def costoTransporteLaboratorioEstanoInstance = CostoTransporteLaboratorioEstano.get(id)
        if (!costoTransporteLaboratorioEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'costoTransporteLaboratorioEstano.label', default: 'CostoTransporteLaboratorioEstano'), id])
            redirect(action: "list")
            return
        }

        [costoTransporteLaboratorioEstanoInstance: costoTransporteLaboratorioEstanoInstance]
    }

    def update(Long id, Long version) {
        def costoTransporteLaboratorioEstanoInstance = CostoTransporteLaboratorioEstano.get(id)
        if (!costoTransporteLaboratorioEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'costoTransporteLaboratorioEstano.label', default: 'CostoTransporteLaboratorioEstano'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (costoTransporteLaboratorioEstanoInstance.version > version) {
                costoTransporteLaboratorioEstanoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'costoTransporteLaboratorioEstano.label', default: 'CostoTransporteLaboratorioEstano')] as Object[],
                        "Another user has updated this CostoTransporteLaboratorioEstano while you were editing")
                render(view: "edit", model: [costoTransporteLaboratorioEstanoInstance: costoTransporteLaboratorioEstanoInstance])
                return
            }
        }

        costoTransporteLaboratorioEstanoInstance.properties = params

        if (!costoTransporteLaboratorioEstanoInstance.save(flush: true)) {
            render(view: "edit", model: [costoTransporteLaboratorioEstanoInstance: costoTransporteLaboratorioEstanoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'costoTransporteLaboratorioEstano.label', default: 'CostoTransporteLaboratorioEstano'), costoTransporteLaboratorioEstanoInstance.id])
        redirect(action: "show", id: costoTransporteLaboratorioEstanoInstance.id)
    }

    def delete(Long id) {
        def costoTransporteLaboratorioEstanoInstance = CostoTransporteLaboratorioEstano.get(id)
        if (!costoTransporteLaboratorioEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'costoTransporteLaboratorioEstano.label', default: 'CostoTransporteLaboratorioEstano'), id])
            redirect(action: "list")
            return
        }

        try {
            costoTransporteLaboratorioEstanoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'costoTransporteLaboratorioEstano.label', default: 'CostoTransporteLaboratorioEstano'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'costoTransporteLaboratorioEstano.label', default: 'CostoTransporteLaboratorioEstano'), id])
            redirect(action: "show", id: id)
        }
    }

    def recepcionesJSON(params) {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesEstano = RecepcionDeEstano.findAllByLoteEstano(lote)
        def recepcionesList = []
        insertToList("",recepcionesList, recepcionesEstano)
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
            mapaRecepcion.put("costoDeTransporteAnterior", recepcion.costoDeTransporte)
            mapaRecepcion.put("detalleLaboratorio1Anterior", recepcion.detalleLaboratorio1)
            mapaRecepcion.put("costoLaboratorio1Anterior", recepcion.costoLaboratorio1)
            mapaRecepcion.put("detalleLaboratorio2Anterior", recepcion.detalleLaboratorio2)
            mapaRecepcion.put("costoLaboratorio2Anterior", recepcion.costoLaboratorio2)
            mapaRecepcion.put("detalleLaboratorio3Anterior", recepcion.detalleLaboratorio3)
            mapaRecepcion.put("costoLaboratorio3Anterior", recepcion.costoLaboratorio3)
            mapaRecepcion.put("detalleLaboratorio4Anterior", recepcion.detalleLaboratorio4)
            mapaRecepcion.put("costoLaboratorio4Anterior", recepcion.costoLaboratorio4)
            mapaRecepcion.put("totalCostoLaboratorioAnterior", recepcion.totalCostoLaboratorio)
            recepcionesList.add(mapaRecepcion)
        }
    }
}
