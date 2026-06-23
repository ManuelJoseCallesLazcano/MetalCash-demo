package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.recepcion.RecepcionDeAntimonio
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class CostoTransporteLaboratorioAntimonioController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [costoTransporteLaboratorioAntimonioInstanceList: CostoTransporteLaboratorioAntimonio.list(params), costoTransporteLaboratorioAntimonioInstanceTotal: CostoTransporteLaboratorioAntimonio.count()]
    }

    def create() {
        [costoTransporteLaboratorioAntimonioInstance: new CostoTransporteLaboratorioAntimonio(params)]
    }

    def save() {
        def costoTransporteLaboratorioAntimonioInstance = new CostoTransporteLaboratorioAntimonio(params)
        if (!costoTransporteLaboratorioAntimonioInstance.save(flush: true)) {
            render(view: "create", model: [costoTransporteLaboratorioAntimonioInstance: costoTransporteLaboratorioAntimonioInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'costoTransporteLaboratorioAntimonio.label', default: 'CostoTransporteLaboratorioAntimonio'), costoTransporteLaboratorioAntimonioInstance.id])
        redirect(action: "show", id: costoTransporteLaboratorioAntimonioInstance.id)
    }

    def show(Long id) {
        def costoTransporteLaboratorioAntimonioInstance = CostoTransporteLaboratorioAntimonio.get(id)
        if (!costoTransporteLaboratorioAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'costoTransporteLaboratorioAntimonio.label', default: 'CostoTransporteLaboratorioAntimonio'), id])
            redirect(action: "list")
            return
        }

        [costoTransporteLaboratorioAntimonioInstance: costoTransporteLaboratorioAntimonioInstance]
    }

    def edit(Long id) {
        def costoTransporteLaboratorioAntimonioInstance = CostoTransporteLaboratorioAntimonio.get(id)
        if (!costoTransporteLaboratorioAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'costoTransporteLaboratorioAntimonio.label', default: 'CostoTransporteLaboratorioAntimonio'), id])
            redirect(action: "list")
            return
        }

        [costoTransporteLaboratorioAntimonioInstance: costoTransporteLaboratorioAntimonioInstance]
    }

    def update(Long id, Long version) {
        def costoTransporteLaboratorioAntimonioInstance = CostoTransporteLaboratorioAntimonio.get(id)
        if (!costoTransporteLaboratorioAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'costoTransporteLaboratorioAntimonio.label', default: 'CostoTransporteLaboratorioAntimonio'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (costoTransporteLaboratorioAntimonioInstance.version > version) {
                costoTransporteLaboratorioAntimonioInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'costoTransporteLaboratorioAntimonio.label', default: 'CostoTransporteLaboratorioAntimonio')] as Object[],
                        "Another user has updated this CostoTransporteLaboratorioAntimonio while you were editing")
                render(view: "edit", model: [costoTransporteLaboratorioAntimonioInstance: costoTransporteLaboratorioAntimonioInstance])
                return
            }
        }

        costoTransporteLaboratorioAntimonioInstance.properties = params

        if (!costoTransporteLaboratorioAntimonioInstance.save(flush: true)) {
            render(view: "edit", model: [costoTransporteLaboratorioAntimonioInstance: costoTransporteLaboratorioAntimonioInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'costoTransporteLaboratorioAntimonio.label', default: 'CostoTransporteLaboratorioAntimonio'), costoTransporteLaboratorioAntimonioInstance.id])
        redirect(action: "show", id: costoTransporteLaboratorioAntimonioInstance.id)
    }

    def delete(Long id) {
        def costoTransporteLaboratorioAntimonioInstance = CostoTransporteLaboratorioAntimonio.get(id)
        if (!costoTransporteLaboratorioAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'costoTransporteLaboratorioAntimonio.label', default: 'CostoTransporteLaboratorioAntimonio'), id])
            redirect(action: "list")
            return
        }

        try {
            costoTransporteLaboratorioAntimonioInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'costoTransporteLaboratorioAntimonio.label', default: 'CostoTransporteLaboratorioAntimonio'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'costoTransporteLaboratorioAntimonio.label', default: 'CostoTransporteLaboratorioAntimonio'), id])
            redirect(action: "show", id: id)
        }
    }

    def recepcionesJSON(params) {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesAntimonio = RecepcionDeAntimonio.findAllByLoteAntimonio(lote)
        def recepcionesList = []
        insertToList("",recepcionesList, recepcionesAntimonio)
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
