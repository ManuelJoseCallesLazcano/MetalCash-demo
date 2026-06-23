package org.socymet.recepcion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.anticipos.AnticipoContraEntrega
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class BajaDeRecepcionDePlataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [bajaDeRecepcionDePlataInstanceList: BajaDeRecepcionDePlata.list(params), bajaDeRecepcionDePlataInstanceTotal: BajaDeRecepcionDePlata.count()]
    }

    def create() {
        [bajaDeRecepcionDePlataInstance: new BajaDeRecepcionDePlata(params)]
    }

    def save() {
        def bajaDeRecepcionDePlataInstance = new BajaDeRecepcionDePlata(params)
        if (!bajaDeRecepcionDePlataInstance.save(flush: true)) {
            render(view: "create", model: [bajaDeRecepcionDePlataInstance: bajaDeRecepcionDePlataInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'bajaDeRecepcionDePlata.label', default: 'BajaDeRecepcionDePlata'), bajaDeRecepcionDePlataInstance.id])
        redirect(action: "show", id: bajaDeRecepcionDePlataInstance.id)
    }

    def show(Long id) {
        def bajaDeRecepcionDePlataInstance = BajaDeRecepcionDePlata.get(id)
        if (!bajaDeRecepcionDePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDePlata.label', default: 'BajaDeRecepcionDePlata'), id])
            redirect(action: "list")
            return
        }

        [bajaDeRecepcionDePlataInstance: bajaDeRecepcionDePlataInstance]
    }

    def edit(Long id) {
        def bajaDeRecepcionDePlataInstance = BajaDeRecepcionDePlata.get(id)
        if (!bajaDeRecepcionDePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDePlata.label', default: 'BajaDeRecepcionDePlata'), id])
            redirect(action: "list")
            return
        }

        [bajaDeRecepcionDePlataInstance: bajaDeRecepcionDePlataInstance]
    }

    def update(Long id, Long version) {
        def bajaDeRecepcionDePlataInstance = BajaDeRecepcionDePlata.get(id)
        if (!bajaDeRecepcionDePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDePlata.label', default: 'BajaDeRecepcionDePlata'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (bajaDeRecepcionDePlataInstance.version > version) {
                bajaDeRecepcionDePlataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'bajaDeRecepcionDePlata.label', default: 'BajaDeRecepcionDePlata')] as Object[],
                        "Another user has updated this BajaDeRecepcionDePlata while you were editing")
                render(view: "edit", model: [bajaDeRecepcionDePlataInstance: bajaDeRecepcionDePlataInstance])
                return
            }
        }

        bajaDeRecepcionDePlataInstance.properties = params

        if (!bajaDeRecepcionDePlataInstance.save(flush: true)) {
            render(view: "edit", model: [bajaDeRecepcionDePlataInstance: bajaDeRecepcionDePlataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'bajaDeRecepcionDePlata.label', default: 'BajaDeRecepcionDePlata'), bajaDeRecepcionDePlataInstance.id])
        redirect(action: "show", id: bajaDeRecepcionDePlataInstance.id)
    }

    def delete(Long id) {
        def bajaDeRecepcionDePlataInstance = BajaDeRecepcionDePlata.get(id)
        if (!bajaDeRecepcionDePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDePlata.label', default: 'BajaDeRecepcionDePlata'), id])
            redirect(action: "list")
            return
        }

        try {
            bajaDeRecepcionDePlataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'bajaDeRecepcionDePlata.label', default: 'BajaDeRecepcionDePlata'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'bajaDeRecepcionDePlata.label', default: 'BajaDeRecepcionDePlata'), id])
            redirect(action: "show", id: id)
        }
    }

    def recepcionesJSON(params) {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesPlata = RecepcionDePlata.findAllByLotePlata(lote)
        def recepcionesList = []
        insertToList(recepcionesList, recepcionesPlata)
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
        def bajaDeRecepcionDePlata = BajaDeRecepcionDePlata.get(params.id)
        chain(controller:'jasper',action:'index',model:[data:bajaDeRecepcionDePlata],params:params)
    }
}
