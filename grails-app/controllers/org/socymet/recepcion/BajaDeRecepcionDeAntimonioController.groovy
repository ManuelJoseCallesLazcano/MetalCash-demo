package org.socymet.recepcion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.anticipos.AnticipoContraEntrega
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class BajaDeRecepcionDeAntimonioController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [bajaDeRecepcionDeAntimonioInstanceList: BajaDeRecepcionDeAntimonio.list(params), bajaDeRecepcionDeAntimonioInstanceTotal: BajaDeRecepcionDeAntimonio.count()]
    }

    def create() {
        [bajaDeRecepcionDeAntimonioInstance: new BajaDeRecepcionDeAntimonio(params)]
    }

    def save() {
        def bajaDeRecepcionDeAntimonioInstance = new BajaDeRecepcionDeAntimonio(params)
        if (!bajaDeRecepcionDeAntimonioInstance.save(flush: true)) {
            render(view: "create", model: [bajaDeRecepcionDeAntimonioInstance: bajaDeRecepcionDeAntimonioInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'bajaDeRecepcionDeAntimonio.label', default: 'BajaDeRecepcionDeAntimonio'), bajaDeRecepcionDeAntimonioInstance.id])
        redirect(action: "show", id: bajaDeRecepcionDeAntimonioInstance.id)
    }

    def show(Long id) {
        def bajaDeRecepcionDeAntimonioInstance = BajaDeRecepcionDeAntimonio.get(id)
        if (!bajaDeRecepcionDeAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDeAntimonio.label', default: 'BajaDeRecepcionDeAntimonio'), id])
            redirect(action: "list")
            return
        }

        [bajaDeRecepcionDeAntimonioInstance: bajaDeRecepcionDeAntimonioInstance]
    }

    def edit(Long id) {
        def bajaDeRecepcionDeAntimonioInstance = BajaDeRecepcionDeAntimonio.get(id)
        if (!bajaDeRecepcionDeAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDeAntimonio.label', default: 'BajaDeRecepcionDeAntimonio'), id])
            redirect(action: "list")
            return
        }

        [bajaDeRecepcionDeAntimonioInstance: bajaDeRecepcionDeAntimonioInstance]
    }

    def update(Long id, Long version) {
        def bajaDeRecepcionDeAntimonioInstance = BajaDeRecepcionDeAntimonio.get(id)
        if (!bajaDeRecepcionDeAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDeAntimonio.label', default: 'BajaDeRecepcionDeAntimonio'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (bajaDeRecepcionDeAntimonioInstance.version > version) {
                bajaDeRecepcionDeAntimonioInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'bajaDeRecepcionDeAntimonio.label', default: 'BajaDeRecepcionDeAntimonio')] as Object[],
                        "Another user has updated this BajaDeRecepcionDeAntimonio while you were editing")
                render(view: "edit", model: [bajaDeRecepcionDeAntimonioInstance: bajaDeRecepcionDeAntimonioInstance])
                return
            }
        }

        bajaDeRecepcionDeAntimonioInstance.properties = params

        if (!bajaDeRecepcionDeAntimonioInstance.save(flush: true)) {
            render(view: "edit", model: [bajaDeRecepcionDeAntimonioInstance: bajaDeRecepcionDeAntimonioInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'bajaDeRecepcionDeAntimonio.label', default: 'BajaDeRecepcionDeAntimonio'), bajaDeRecepcionDeAntimonioInstance.id])
        redirect(action: "show", id: bajaDeRecepcionDeAntimonioInstance.id)
    }

    def delete(Long id) {
        def bajaDeRecepcionDeAntimonioInstance = BajaDeRecepcionDeAntimonio.get(id)
        if (!bajaDeRecepcionDeAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDeAntimonio.label', default: 'BajaDeRecepcionDeAntimonio'), id])
            redirect(action: "list")
            return
        }

        try {
            bajaDeRecepcionDeAntimonioInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'bajaDeRecepcionDeAntimonio.label', default: 'BajaDeRecepcionDeAntimonio'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'bajaDeRecepcionDeAntimonio.label', default: 'BajaDeRecepcionDeAntimonio'), id])
            redirect(action: "show", id: id)
        }
    }

    def recepcionesJSON(params) {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesAntimonio = RecepcionDeAntimonio.findAllByLoteAntimonio(lote)
        def recepcionesList = []
        insertToList(recepcionesList, recepcionesAntimonio)
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
        def bajaDeRecepcionDeAntimonio = BajaDeRecepcionDeAntimonio.get(params.id)
        chain(controller:'jasper',action:'index',model:[data:bajaDeRecepcionDeAntimonio],params:params)
    }
}
