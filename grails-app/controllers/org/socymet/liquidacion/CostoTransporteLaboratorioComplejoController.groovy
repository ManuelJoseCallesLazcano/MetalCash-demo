package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.recepcion.RecepcionDeComplejo
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class CostoTransporteLaboratorioComplejoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [costoTransporteLaboratorioComplejoInstanceList: CostoTransporteLaboratorioComplejo.list(params), costoTransporteLaboratorioComplejoInstanceTotal: CostoTransporteLaboratorioComplejo.count()]
    }

    def create() {
        [costoTransporteLaboratorioComplejoInstance: new CostoTransporteLaboratorioComplejo(params)]
    }

    def save() {
        def costoTransporteLaboratorioComplejoInstance = new CostoTransporteLaboratorioComplejo(params)
        if (!costoTransporteLaboratorioComplejoInstance.save(flush: true)) {
            render(view: "create", model: [costoTransporteLaboratorioComplejoInstance: costoTransporteLaboratorioComplejoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'costoTransporteLaboratorioComplejo.label', default: 'CostoTransporteLaboratorioComplejo'), costoTransporteLaboratorioComplejoInstance.id])
        redirect(action: "show", id: costoTransporteLaboratorioComplejoInstance.id)
    }

    def show(Long id) {
        def costoTransporteLaboratorioComplejoInstance = CostoTransporteLaboratorioComplejo.get(id)
        if (!costoTransporteLaboratorioComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'costoTransporteLaboratorioComplejo.label', default: 'CostoTransporteLaboratorioComplejo'), id])
            redirect(action: "list")
            return
        }

        [costoTransporteLaboratorioComplejoInstance: costoTransporteLaboratorioComplejoInstance]
    }

    def edit(Long id) {
        def costoTransporteLaboratorioComplejoInstance = CostoTransporteLaboratorioComplejo.get(id)
        if (!costoTransporteLaboratorioComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'costoTransporteLaboratorioComplejo.label', default: 'CostoTransporteLaboratorioComplejo'), id])
            redirect(action: "list")
            return
        }

        [costoTransporteLaboratorioComplejoInstance: costoTransporteLaboratorioComplejoInstance]
    }

    def update(Long id, Long version) {
        def costoTransporteLaboratorioComplejoInstance = CostoTransporteLaboratorioComplejo.get(id)
        if (!costoTransporteLaboratorioComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'costoTransporteLaboratorioComplejo.label', default: 'CostoTransporteLaboratorioComplejo'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (costoTransporteLaboratorioComplejoInstance.version > version) {
                costoTransporteLaboratorioComplejoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'costoTransporteLaboratorioComplejo.label', default: 'CostoTransporteLaboratorioComplejo')] as Object[],
                        "Another user has updated this CostoTransporteLaboratorioComplejo while you were editing")
                render(view: "edit", model: [costoTransporteLaboratorioComplejoInstance: costoTransporteLaboratorioComplejoInstance])
                return
            }
        }

        costoTransporteLaboratorioComplejoInstance.properties = params

        if (!costoTransporteLaboratorioComplejoInstance.save(flush: true)) {
            render(view: "edit", model: [costoTransporteLaboratorioComplejoInstance: costoTransporteLaboratorioComplejoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'costoTransporteLaboratorioComplejo.label', default: 'CostoTransporteLaboratorioComplejo'), costoTransporteLaboratorioComplejoInstance.id])
        redirect(action: "show", id: costoTransporteLaboratorioComplejoInstance.id)
    }

    def delete(Long id) {
        def costoTransporteLaboratorioComplejoInstance = CostoTransporteLaboratorioComplejo.get(id)
        if (!costoTransporteLaboratorioComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'costoTransporteLaboratorioComplejo.label', default: 'CostoTransporteLaboratorioComplejo'), id])
            redirect(action: "list")
            return
        }

        try {
            costoTransporteLaboratorioComplejoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'costoTransporteLaboratorioComplejo.label', default: 'CostoTransporteLaboratorioComplejo'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'costoTransporteLaboratorioComplejo.label', default: 'CostoTransporteLaboratorioComplejo'), id])
            redirect(action: "show", id: id)
        }
    }

    def recepcionesJSON(params) {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejo(lote)
        def recepcionesList = []
        insertToList("",recepcionesList, recepcionesComplejo)
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
