package org.socymet.recepcion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.anticipos.AnticipoContraEntrega
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class BajaDeRecepcionDeComplejoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [bajaDeRecepcionDeComplejoInstanceList: BajaDeRecepcionDeComplejo.list(params), bajaDeRecepcionDeComplejoInstanceTotal: BajaDeRecepcionDeComplejo.count()]
    }

    def create() {
        [bajaDeRecepcionDeComplejoInstance: new BajaDeRecepcionDeComplejo(params)]
    }

    def save() {
        def bajaDeRecepcionDeComplejoInstance = new BajaDeRecepcionDeComplejo(params)
        if (!bajaDeRecepcionDeComplejoInstance.save(flush: true)) {
            render(view: "create", model: [bajaDeRecepcionDeComplejoInstance: bajaDeRecepcionDeComplejoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'bajaDeRecepcionDeComplejo.label', default: 'BajaDeRecepcionDeComplejo'), bajaDeRecepcionDeComplejoInstance.id])
        redirect(action: "show", id: bajaDeRecepcionDeComplejoInstance.id)
    }

    def show(Long id) {
        def bajaDeRecepcionDeComplejoInstance = BajaDeRecepcionDeComplejo.get(id)
        if (!bajaDeRecepcionDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDeComplejo.label', default: 'BajaDeRecepcionDeComplejo'), id])
            redirect(action: "list")
            return
        }

        [bajaDeRecepcionDeComplejoInstance: bajaDeRecepcionDeComplejoInstance]
    }

    def edit(Long id) {
        def bajaDeRecepcionDeComplejoInstance = BajaDeRecepcionDeComplejo.get(id)
        if (!bajaDeRecepcionDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDeComplejo.label', default: 'BajaDeRecepcionDeComplejo'), id])
            redirect(action: "list")
            return
        }

        [bajaDeRecepcionDeComplejoInstance: bajaDeRecepcionDeComplejoInstance]
    }

    def update(Long id, Long version) {
        def bajaDeRecepcionDeComplejoInstance = BajaDeRecepcionDeComplejo.get(id)
        if (!bajaDeRecepcionDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDeComplejo.label', default: 'BajaDeRecepcionDeComplejo'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (bajaDeRecepcionDeComplejoInstance.version > version) {
                bajaDeRecepcionDeComplejoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'bajaDeRecepcionDeComplejo.label', default: 'BajaDeRecepcionDeComplejo')] as Object[],
                        "Another user has updated this BajaDeRecepcionDeComplejo while you were editing")
                render(view: "edit", model: [bajaDeRecepcionDeComplejoInstance: bajaDeRecepcionDeComplejoInstance])
                return
            }
        }

        bajaDeRecepcionDeComplejoInstance.properties = params

        if (!bajaDeRecepcionDeComplejoInstance.save(flush: true)) {
            render(view: "edit", model: [bajaDeRecepcionDeComplejoInstance: bajaDeRecepcionDeComplejoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'bajaDeRecepcionDeComplejo.label', default: 'BajaDeRecepcionDeComplejo'), bajaDeRecepcionDeComplejoInstance.id])
        redirect(action: "show", id: bajaDeRecepcionDeComplejoInstance.id)
    }

    def delete(Long id) {
        def bajaDeRecepcionDeComplejoInstance = BajaDeRecepcionDeComplejo.get(id)
        if (!bajaDeRecepcionDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDeComplejo.label', default: 'BajaDeRecepcionDeComplejo'), id])
            redirect(action: "list")
            return
        }

        try {
            bajaDeRecepcionDeComplejoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'bajaDeRecepcionDeComplejo.label', default: 'BajaDeRecepcionDeComplejo'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'bajaDeRecepcionDeComplejo.label', default: 'BajaDeRecepcionDeComplejo'), id])
            redirect(action: "show", id: id)
        }
    }

    def recepcionesJSON(params) {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesComplejo = RecepcionDeComplejo.findAllByLoteComplejo(lote)
        def recepcionesList = []
        insertToList(recepcionesList, recepcionesComplejo)
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
        def bajaDeRecepcionDeComplejo = BajaDeRecepcionDeComplejo.get(params.id)
        chain(controller:'jasper',action:'index',model:[data:bajaDeRecepcionDeComplejo],params:params)
    }
}
