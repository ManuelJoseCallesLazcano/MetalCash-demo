package org.socymet.recepcion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.anticipos.AnticipoContraEntrega
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class BajaDeRecepcionDePlomoPlataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [bajaDeRecepcionDePlomoPlataInstanceList: BajaDeRecepcionDePlomoPlata.list(params), bajaDeRecepcionDePlomoPlataInstanceTotal: BajaDeRecepcionDePlomoPlata.count()]
    }

    def create() {
        [bajaDeRecepcionDePlomoPlataInstance: new BajaDeRecepcionDePlomoPlata(params)]
    }

    def save() {
        def bajaDeRecepcionDePlomoPlataInstance = new BajaDeRecepcionDePlomoPlata(params)
        if (!bajaDeRecepcionDePlomoPlataInstance.save(flush: true)) {
            render(view: "create", model: [bajaDeRecepcionDePlomoPlataInstance: bajaDeRecepcionDePlomoPlataInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'bajaDeRecepcionDePlomoPlata.label', default: 'BajaDeRecepcionDePlomoPlata'), bajaDeRecepcionDePlomoPlataInstance.id])
        redirect(action: "show", id: bajaDeRecepcionDePlomoPlataInstance.id)
    }

    def show(Long id) {
        def bajaDeRecepcionDePlomoPlataInstance = BajaDeRecepcionDePlomoPlata.get(id)
        if (!bajaDeRecepcionDePlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDePlomoPlata.label', default: 'BajaDeRecepcionDePlomoPlata'), id])
            redirect(action: "list")
            return
        }

        [bajaDeRecepcionDePlomoPlataInstance: bajaDeRecepcionDePlomoPlataInstance]
    }

    def edit(Long id) {
        def bajaDeRecepcionDePlomoPlataInstance = BajaDeRecepcionDePlomoPlata.get(id)
        if (!bajaDeRecepcionDePlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDePlomoPlata.label', default: 'BajaDeRecepcionDePlomoPlata'), id])
            redirect(action: "list")
            return
        }

        [bajaDeRecepcionDePlomoPlataInstance: bajaDeRecepcionDePlomoPlataInstance]
    }

    def update(Long id, Long version) {
        def bajaDeRecepcionDePlomoPlataInstance = BajaDeRecepcionDePlomoPlata.get(id)
        if (!bajaDeRecepcionDePlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDePlomoPlata.label', default: 'BajaDeRecepcionDePlomoPlata'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (bajaDeRecepcionDePlomoPlataInstance.version > version) {
                bajaDeRecepcionDePlomoPlataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'bajaDeRecepcionDePlomoPlata.label', default: 'BajaDeRecepcionDePlomoPlata')] as Object[],
                        "Another user has updated this BajaDeRecepcionDePlomoPlata while you were editing")
                render(view: "edit", model: [bajaDeRecepcionDePlomoPlataInstance: bajaDeRecepcionDePlomoPlataInstance])
                return
            }
        }

        bajaDeRecepcionDePlomoPlataInstance.properties = params

        if (!bajaDeRecepcionDePlomoPlataInstance.save(flush: true)) {
            render(view: "edit", model: [bajaDeRecepcionDePlomoPlataInstance: bajaDeRecepcionDePlomoPlataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'bajaDeRecepcionDePlomoPlata.label', default: 'BajaDeRecepcionDePlomoPlata'), bajaDeRecepcionDePlomoPlataInstance.id])
        redirect(action: "show", id: bajaDeRecepcionDePlomoPlataInstance.id)
    }

    def delete(Long id) {
        def bajaDeRecepcionDePlomoPlataInstance = BajaDeRecepcionDePlomoPlata.get(id)
        if (!bajaDeRecepcionDePlomoPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDePlomoPlata.label', default: 'BajaDeRecepcionDePlomoPlata'), id])
            redirect(action: "list")
            return
        }

        try {
            bajaDeRecepcionDePlomoPlataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'bajaDeRecepcionDePlomoPlata.label', default: 'BajaDeRecepcionDePlomoPlata'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'bajaDeRecepcionDePlomoPlata.label', default: 'BajaDeRecepcionDePlomoPlata'), id])
            redirect(action: "show", id: id)
        }
    }

    def recepcionesJSON(params) {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesPlomoPlata = RecepcionDePlomoPlata.findAllByLotePlomoPlata(lote)
        def recepcionesList = []
        insertToList(recepcionesList, recepcionesPlomoPlata)
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
        def bajaDeRecepcionDePlomoPlata = BajaDeRecepcionDePlomoPlata.get(params.id)
        chain(controller:'jasper',action:'index',model:[data:bajaDeRecepcionDePlomoPlata],params:params)
    }
}
