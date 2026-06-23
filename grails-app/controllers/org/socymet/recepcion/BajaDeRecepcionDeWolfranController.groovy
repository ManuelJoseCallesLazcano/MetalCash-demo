package org.socymet.recepcion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.anticipos.AnticipoContraEntrega
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class BajaDeRecepcionDeWolfranController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [bajaDeRecepcionDeWolfranInstanceList: BajaDeRecepcionDeWolfran.list(params), bajaDeRecepcionDeWolfranInstanceTotal: BajaDeRecepcionDeWolfran.count()]
    }

    def create() {
        [bajaDeRecepcionDeWolfranInstance: new BajaDeRecepcionDeWolfran(params)]
    }

    def save() {
        def bajaDeRecepcionDeWolfranInstance = new BajaDeRecepcionDeWolfran(params)
        if (!bajaDeRecepcionDeWolfranInstance.save(flush: true)) {
            render(view: "create", model: [bajaDeRecepcionDeWolfranInstance: bajaDeRecepcionDeWolfranInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'bajaDeRecepcionDeWolfran.label', default: 'BajaDeRecepcionDeWolfran'), bajaDeRecepcionDeWolfranInstance.id])
        redirect(action: "show", id: bajaDeRecepcionDeWolfranInstance.id)
    }

    def show(Long id) {
        def bajaDeRecepcionDeWolfranInstance = BajaDeRecepcionDeWolfran.get(id)
        if (!bajaDeRecepcionDeWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDeWolfran.label', default: 'BajaDeRecepcionDeWolfran'), id])
            redirect(action: "list")
            return
        }

        [bajaDeRecepcionDeWolfranInstance: bajaDeRecepcionDeWolfranInstance]
    }

    def edit(Long id) {
        def bajaDeRecepcionDeWolfranInstance = BajaDeRecepcionDeWolfran.get(id)
        if (!bajaDeRecepcionDeWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDeWolfran.label', default: 'BajaDeRecepcionDeWolfran'), id])
            redirect(action: "list")
            return
        }

        [bajaDeRecepcionDeWolfranInstance: bajaDeRecepcionDeWolfranInstance]
    }

    def update(Long id, Long version) {
        def bajaDeRecepcionDeWolfranInstance = BajaDeRecepcionDeWolfran.get(id)
        if (!bajaDeRecepcionDeWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDeWolfran.label', default: 'BajaDeRecepcionDeWolfran'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (bajaDeRecepcionDeWolfranInstance.version > version) {
                bajaDeRecepcionDeWolfranInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'bajaDeRecepcionDeWolfran.label', default: 'BajaDeRecepcionDeWolfran')] as Object[],
                        "Another user has updated this BajaDeRecepcionDeWolfran while you were editing")
                render(view: "edit", model: [bajaDeRecepcionDeWolfranInstance: bajaDeRecepcionDeWolfranInstance])
                return
            }
        }

        bajaDeRecepcionDeWolfranInstance.properties = params

        if (!bajaDeRecepcionDeWolfranInstance.save(flush: true)) {
            render(view: "edit", model: [bajaDeRecepcionDeWolfranInstance: bajaDeRecepcionDeWolfranInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'bajaDeRecepcionDeWolfran.label', default: 'BajaDeRecepcionDeWolfran'), bajaDeRecepcionDeWolfranInstance.id])
        redirect(action: "show", id: bajaDeRecepcionDeWolfranInstance.id)
    }

    def delete(Long id) {
        def bajaDeRecepcionDeWolfranInstance = BajaDeRecepcionDeWolfran.get(id)
        if (!bajaDeRecepcionDeWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDeWolfran.label', default: 'BajaDeRecepcionDeWolfran'), id])
            redirect(action: "list")
            return
        }

        try {
            bajaDeRecepcionDeWolfranInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'bajaDeRecepcionDeWolfran.label', default: 'BajaDeRecepcionDeWolfran'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'bajaDeRecepcionDeWolfran.label', default: 'BajaDeRecepcionDeWolfran'), id])
            redirect(action: "show", id: id)
        }
    }

    def recepcionesJSON(params) {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesWolfran = RecepcionDeWolfran.findAllByLoteWolfran(lote)
        def recepcionesList = []
        insertToList(recepcionesList, recepcionesWolfran)
        render recepcionesList as JSON
    }

    def insertToList = { recepcionesList, recepcionList->
        recepcionList.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("recepcionId", recepcion.id)
            mapaRecepcion.put("label", recepcion.toString())
            mapaRecepcion.put("value", recepcion.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            mapaRecepcion.put("gastoPorAnticipo", getTotalAnticiposContraEntrega(recepcion.id))
            mapaRecepcion.put("gastoPorAnalisis", recepcion.totalCostoLaboratorio)
            recepcionesList.add(mapaRecepcion)
        }
    }

    def getTotalAnticiposContraEntrega = { recepcionId ->
        def totalAnticiposContraEntrega = 0
        def anticipoContraEntregas = AnticipoContraEntrega.findAllByRecepcionId(recepcionId)
        totalAnticiposContraEntrega = anticipoContraEntregas*.importe.sum()
        return (totalAnticiposContraEntrega)?totalAnticiposContraEntrega:0
    }

    def crearReporte = {
        def bajaDeRecepcionDeWolfran = BajaDeRecepcionDeWolfran.get(params.id)
        chain(controller:'jasper',action:'index',model:[data:bajaDeRecepcionDeWolfran],params:params)
    }
}
