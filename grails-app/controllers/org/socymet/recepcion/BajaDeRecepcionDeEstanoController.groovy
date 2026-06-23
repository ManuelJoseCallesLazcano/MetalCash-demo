package org.socymet.recepcion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.anticipos.AnticipoContraEntrega
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class BajaDeRecepcionDeEstanoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [bajaDeRecepcionDeEstanoInstanceList: BajaDeRecepcionDeEstano.list(params), bajaDeRecepcionDeEstanoInstanceTotal: BajaDeRecepcionDeEstano.count()]
    }

    def create() {
        [bajaDeRecepcionDeEstanoInstance: new BajaDeRecepcionDeEstano(params)]
    }

    def save() {
        def bajaDeRecepcionDeEstanoInstance = new BajaDeRecepcionDeEstano(params)
        if (!bajaDeRecepcionDeEstanoInstance.save(flush: true)) {
            render(view: "create", model: [bajaDeRecepcionDeEstanoInstance: bajaDeRecepcionDeEstanoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'bajaDeRecepcionDeEstano.label', default: 'BajaDeRecepcionDeEstano'), bajaDeRecepcionDeEstanoInstance.id])
        redirect(action: "show", id: bajaDeRecepcionDeEstanoInstance.id)
    }

    def show(Long id) {
        def bajaDeRecepcionDeEstanoInstance = BajaDeRecepcionDeEstano.get(id)
        if (!bajaDeRecepcionDeEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDeEstano.label', default: 'BajaDeRecepcionDeEstano'), id])
            redirect(action: "list")
            return
        }

        [bajaDeRecepcionDeEstanoInstance: bajaDeRecepcionDeEstanoInstance]
    }

    def edit(Long id) {
        def bajaDeRecepcionDeEstanoInstance = BajaDeRecepcionDeEstano.get(id)
        if (!bajaDeRecepcionDeEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDeEstano.label', default: 'BajaDeRecepcionDeEstano'), id])
            redirect(action: "list")
            return
        }

        [bajaDeRecepcionDeEstanoInstance: bajaDeRecepcionDeEstanoInstance]
    }

    def update(Long id, Long version) {
        def bajaDeRecepcionDeEstanoInstance = BajaDeRecepcionDeEstano.get(id)
        if (!bajaDeRecepcionDeEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDeEstano.label', default: 'BajaDeRecepcionDeEstano'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (bajaDeRecepcionDeEstanoInstance.version > version) {
                bajaDeRecepcionDeEstanoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'bajaDeRecepcionDeEstano.label', default: 'BajaDeRecepcionDeEstano')] as Object[],
                        "Another user has updated this BajaDeRecepcionDeEstano while you were editing")
                render(view: "edit", model: [bajaDeRecepcionDeEstanoInstance: bajaDeRecepcionDeEstanoInstance])
                return
            }
        }

        bajaDeRecepcionDeEstanoInstance.properties = params

        if (!bajaDeRecepcionDeEstanoInstance.save(flush: true)) {
            render(view: "edit", model: [bajaDeRecepcionDeEstanoInstance: bajaDeRecepcionDeEstanoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'bajaDeRecepcionDeEstano.label', default: 'BajaDeRecepcionDeEstano'), bajaDeRecepcionDeEstanoInstance.id])
        redirect(action: "show", id: bajaDeRecepcionDeEstanoInstance.id)
    }

    def delete(Long id) {
        def bajaDeRecepcionDeEstanoInstance = BajaDeRecepcionDeEstano.get(id)
        if (!bajaDeRecepcionDeEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bajaDeRecepcionDeEstano.label', default: 'BajaDeRecepcionDeEstano'), id])
            redirect(action: "list")
            return
        }

        try {
            bajaDeRecepcionDeEstanoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'bajaDeRecepcionDeEstano.label', default: 'BajaDeRecepcionDeEstano'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'bajaDeRecepcionDeEstano.label', default: 'BajaDeRecepcionDeEstano'), id])
            redirect(action: "show", id: id)
        }
    }

    def recepcionesJSON(params) {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesEstano = RecepcionDeEstano.findAllByLoteEstano(lote)
        def recepcionesList = []
        insertToList(recepcionesList, recepcionesEstano)
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
        def bajaDeRecepcionDeEstano = BajaDeRecepcionDeEstano.get(params.id)
        chain(controller:'jasper',action:'index',model:[data:bajaDeRecepcionDeEstano],params:params)
    }
}
