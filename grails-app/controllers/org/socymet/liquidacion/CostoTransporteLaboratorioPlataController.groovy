package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.recepcion.RecepcionDePlata
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class CostoTransporteLaboratorioPlataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [costoTransporteLaboratorioPlataInstanceList: CostoTransporteLaboratorioPlata.list(params), costoTransporteLaboratorioPlataInstanceTotal: CostoTransporteLaboratorioPlata.count()]
    }

    def create() {
        [costoTransporteLaboratorioPlataInstance: new CostoTransporteLaboratorioPlata(params)]
    }

    def save() {
        def costoTransporteLaboratorioPlataInstance = new CostoTransporteLaboratorioPlata(params)
        if (!costoTransporteLaboratorioPlataInstance.save(flush: true)) {
            render(view: "create", model: [costoTransporteLaboratorioPlataInstance: costoTransporteLaboratorioPlataInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'costoTransporteLaboratorioPlata.label', default: 'CostoTransporteLaboratorioPlata'), costoTransporteLaboratorioPlataInstance.id])
        redirect(action: "show", id: costoTransporteLaboratorioPlataInstance.id)
    }

    def show(Long id) {
        def costoTransporteLaboratorioPlataInstance = CostoTransporteLaboratorioPlata.get(id)
        if (!costoTransporteLaboratorioPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'costoTransporteLaboratorioPlata.label', default: 'CostoTransporteLaboratorioPlata'), id])
            redirect(action: "list")
            return
        }

        [costoTransporteLaboratorioPlataInstance: costoTransporteLaboratorioPlataInstance]
    }

    def edit(Long id) {
        def costoTransporteLaboratorioPlataInstance = CostoTransporteLaboratorioPlata.get(id)
        if (!costoTransporteLaboratorioPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'costoTransporteLaboratorioPlata.label', default: 'CostoTransporteLaboratorioPlata'), id])
            redirect(action: "list")
            return
        }

        [costoTransporteLaboratorioPlataInstance: costoTransporteLaboratorioPlataInstance]
    }

    def update(Long id, Long version) {
        def costoTransporteLaboratorioPlataInstance = CostoTransporteLaboratorioPlata.get(id)
        if (!costoTransporteLaboratorioPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'costoTransporteLaboratorioPlata.label', default: 'CostoTransporteLaboratorioPlata'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (costoTransporteLaboratorioPlataInstance.version > version) {
                costoTransporteLaboratorioPlataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'costoTransporteLaboratorioPlata.label', default: 'CostoTransporteLaboratorioPlata')] as Object[],
                        "Another user has updated this CostoTransporteLaboratorioPlata while you were editing")
                render(view: "edit", model: [costoTransporteLaboratorioPlataInstance: costoTransporteLaboratorioPlataInstance])
                return
            }
        }

        costoTransporteLaboratorioPlataInstance.properties = params

        if (!costoTransporteLaboratorioPlataInstance.save(flush: true)) {
            render(view: "edit", model: [costoTransporteLaboratorioPlataInstance: costoTransporteLaboratorioPlataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'costoTransporteLaboratorioPlata.label', default: 'CostoTransporteLaboratorioPlata'), costoTransporteLaboratorioPlataInstance.id])
        redirect(action: "show", id: costoTransporteLaboratorioPlataInstance.id)
    }

    def delete(Long id) {
        def costoTransporteLaboratorioPlataInstance = CostoTransporteLaboratorioPlata.get(id)
        if (!costoTransporteLaboratorioPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'costoTransporteLaboratorioPlata.label', default: 'CostoTransporteLaboratorioPlata'), id])
            redirect(action: "list")
            return
        }

        try {
            costoTransporteLaboratorioPlataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'costoTransporteLaboratorioPlata.label', default: 'CostoTransporteLaboratorioPlata'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'costoTransporteLaboratorioPlata.label', default: 'CostoTransporteLaboratorioPlata'), id])
            redirect(action: "show", id: id)
        }
    }

    def recepcionesJSON(params) {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesPlata = RecepcionDePlata.findAllByLotePlata(lote)
        def recepcionesList = []
        insertToList("",recepcionesList, recepcionesPlata)
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
