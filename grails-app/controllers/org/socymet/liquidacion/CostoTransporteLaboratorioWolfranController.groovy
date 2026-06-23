package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.recepcion.RecepcionDeWolfran
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class CostoTransporteLaboratorioWolfranController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [costoTransporteLaboratorioWolfranInstanceList: CostoTransporteLaboratorioWolfran.list(params), costoTransporteLaboratorioWolfranInstanceTotal: CostoTransporteLaboratorioWolfran.count()]
    }

    def create() {
        [costoTransporteLaboratorioWolfranInstance: new CostoTransporteLaboratorioWolfran(params)]
    }

    def save() {
        def costoTransporteLaboratorioWolfranInstance = new CostoTransporteLaboratorioWolfran(params)
        if (!costoTransporteLaboratorioWolfranInstance.save(flush: true)) {
            render(view: "create", model: [costoTransporteLaboratorioWolfranInstance: costoTransporteLaboratorioWolfranInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'costoTransporteLaboratorioWolfran.label', default: 'CostoTransporteLaboratorioWolfran'), costoTransporteLaboratorioWolfranInstance.id])
        redirect(action: "show", id: costoTransporteLaboratorioWolfranInstance.id)
    }

    def show(Long id) {
        def costoTransporteLaboratorioWolfranInstance = CostoTransporteLaboratorioWolfran.get(id)
        if (!costoTransporteLaboratorioWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'costoTransporteLaboratorioWolfran.label', default: 'CostoTransporteLaboratorioWolfran'), id])
            redirect(action: "list")
            return
        }

        [costoTransporteLaboratorioWolfranInstance: costoTransporteLaboratorioWolfranInstance]
    }

    def edit(Long id) {
        def costoTransporteLaboratorioWolfranInstance = CostoTransporteLaboratorioWolfran.get(id)
        if (!costoTransporteLaboratorioWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'costoTransporteLaboratorioWolfran.label', default: 'CostoTransporteLaboratorioWolfran'), id])
            redirect(action: "list")
            return
        }

        [costoTransporteLaboratorioWolfranInstance: costoTransporteLaboratorioWolfranInstance]
    }

    def update(Long id, Long version) {
        def costoTransporteLaboratorioWolfranInstance = CostoTransporteLaboratorioWolfran.get(id)
        if (!costoTransporteLaboratorioWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'costoTransporteLaboratorioWolfran.label', default: 'CostoTransporteLaboratorioWolfran'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (costoTransporteLaboratorioWolfranInstance.version > version) {
                costoTransporteLaboratorioWolfranInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'costoTransporteLaboratorioWolfran.label', default: 'CostoTransporteLaboratorioWolfran')] as Object[],
                        "Another user has updated this CostoTransporteLaboratorioWolfran while you were editing")
                render(view: "edit", model: [costoTransporteLaboratorioWolfranInstance: costoTransporteLaboratorioWolfranInstance])
                return
            }
        }

        costoTransporteLaboratorioWolfranInstance.properties = params

        if (!costoTransporteLaboratorioWolfranInstance.save(flush: true)) {
            render(view: "edit", model: [costoTransporteLaboratorioWolfranInstance: costoTransporteLaboratorioWolfranInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'costoTransporteLaboratorioWolfran.label', default: 'CostoTransporteLaboratorioWolfran'), costoTransporteLaboratorioWolfranInstance.id])
        redirect(action: "show", id: costoTransporteLaboratorioWolfranInstance.id)
    }

    def delete(Long id) {
        def costoTransporteLaboratorioWolfranInstance = CostoTransporteLaboratorioWolfran.get(id)
        if (!costoTransporteLaboratorioWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'costoTransporteLaboratorioWolfran.label', default: 'CostoTransporteLaboratorioWolfran'), id])
            redirect(action: "list")
            return
        }

        try {
            costoTransporteLaboratorioWolfranInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'costoTransporteLaboratorioWolfran.label', default: 'CostoTransporteLaboratorioWolfran'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'costoTransporteLaboratorioWolfran.label', default: 'CostoTransporteLaboratorioWolfran'), id])
            redirect(action: "show", id: id)
        }
    }

    def recepcionesJSON(params) {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesWolfran = RecepcionDeWolfran.findAllByLoteWolfran(lote)
        def recepcionesList = []
        insertToList("",recepcionesList, recepcionesWolfran)
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
