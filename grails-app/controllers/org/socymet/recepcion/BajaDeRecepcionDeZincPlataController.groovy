package org.socymet.recepcion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.anticipos.AnticipoContraEntrega
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class BajaDeRecepcionDeZincPlataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [bajaDeRecepcionDeZincPlataInstanceList: BajaDeRecepcionDeZincPlata.list(params), bajaDeRecepcionDeZincPlataInstanceTotal: BajaDeRecepcionDeZincPlata.count()]
    }

    def create() {
        [bajaDeRecepcionDeZincPlataInstance: new BajaDeRecepcionDeZincPlata(params)]
    }

    def save() {
        def bajaDeRecepcionDeZincPlataInstance = new BajaDeRecepcionDeZincPlata(params)
        if (!bajaDeRecepcionDeZincPlataInstance.save(flush: true)) {
            render(view: "create", model: [bajaDeRecepcionDeZincPlataInstance: bajaDeRecepcionDeZincPlataInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'bajaDeRecepcionDeZincPlata.label', default: 'BajaDeRecepcionDeZincPlata'), bajaDeRecepcionDeZincPlataInstance.id])
        redirect(action: "show", id: bajaDeRecepcionDeZincPlataInstance.id)
    }

    def show(Long id) {
        def bajaDeRecepcionDeZincPlataInstance = BajaDeRecepcionDeZincPlata.get(id)
        if (!bajaDeRecepcionDeZincPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDeZincPlata.label', default: 'BajaDeRecepcionDeZincPlata'), id])
            redirect(action: "list")
            return
        }

        [bajaDeRecepcionDeZincPlataInstance: bajaDeRecepcionDeZincPlataInstance]
    }

    def edit(Long id) {
        def bajaDeRecepcionDeZincPlataInstance = BajaDeRecepcionDeZincPlata.get(id)
        if (!bajaDeRecepcionDeZincPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDeZincPlata.label', default: 'BajaDeRecepcionDeZincPlata'), id])
            redirect(action: "list")
            return
        }

        [bajaDeRecepcionDeZincPlataInstance: bajaDeRecepcionDeZincPlataInstance]
    }

    def update(Long id, Long version) {
        def bajaDeRecepcionDeZincPlataInstance = BajaDeRecepcionDeZincPlata.get(id)
        if (!bajaDeRecepcionDeZincPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDeZincPlata.label', default: 'BajaDeRecepcionDeZincPlata'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (bajaDeRecepcionDeZincPlataInstance.version > version) {
                bajaDeRecepcionDeZincPlataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'bajaDeRecepcionDeZincPlata.label', default: 'BajaDeRecepcionDeZincPlata')] as Object[],
                        "Another user has updated this BajaDeRecepcionDeZincPlata while you were editing")
                render(view: "edit", model: [bajaDeRecepcionDeZincPlataInstance: bajaDeRecepcionDeZincPlataInstance])
                return
            }
        }

        bajaDeRecepcionDeZincPlataInstance.properties = params

        if (!bajaDeRecepcionDeZincPlataInstance.save(flush: true)) {
            render(view: "edit", model: [bajaDeRecepcionDeZincPlataInstance: bajaDeRecepcionDeZincPlataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'bajaDeRecepcionDeZincPlata.label', default: 'BajaDeRecepcionDeZincPlata'), bajaDeRecepcionDeZincPlataInstance.id])
        redirect(action: "show", id: bajaDeRecepcionDeZincPlataInstance.id)
    }

    def delete(Long id) {
        def bajaDeRecepcionDeZincPlataInstance = BajaDeRecepcionDeZincPlata.get(id)
        if (!bajaDeRecepcionDeZincPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDeZincPlata.label', default: 'BajaDeRecepcionDeZincPlata'), id])
            redirect(action: "list")
            return
        }

        try {
            bajaDeRecepcionDeZincPlataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'bajaDeRecepcionDeZincPlata.label', default: 'BajaDeRecepcionDeZincPlata'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'bajaDeRecepcionDeZincPlata.label', default: 'BajaDeRecepcionDeZincPlata'), id])
            redirect(action: "show", id: id)
        }
    }

    def recepcionesJSON(params) {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesZincPlata = RecepcionDeZincPlata.findAllByLoteZincPlata(lote)
        def recepcionesList = []
        insertToList(recepcionesList, recepcionesZincPlata)
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
        def bajaDeRecepcionDeZincPlata = BajaDeRecepcionDeZincPlata.get(params.id)
        chain(controller:'jasper',action:'index',model:[data:bajaDeRecepcionDeZincPlata],params:params)
    }
}
