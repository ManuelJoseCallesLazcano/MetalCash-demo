package org.socymet.cancelacion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.proveedor.Deposito
import org.socymet.recepcion.RecepcionDeComplejo
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class PagoManipuleoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [pagoManipuleoInstanceList: PagoManipuleo.list(params), pagoManipuleoInstanceTotal: PagoManipuleo.count()]
    }

    def create() {
        [pagoManipuleoInstance: new PagoManipuleo(params)]
    }

    def save() {
        def pagoManipuleoInstance = new PagoManipuleo(params)
        if (!pagoManipuleoInstance.save(flush: true)) {
            render(view: "create", model: [pagoManipuleoInstance: pagoManipuleoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'pagoManipuleo.label', default: 'PagoManipuleo'), pagoManipuleoInstance.id])
        redirect(action: "show", id: pagoManipuleoInstance.id)
    }

    def show(Long id) {
        def pagoManipuleoInstance = PagoManipuleo.get(id)
        if (!pagoManipuleoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoManipuleo.label', default: 'PagoManipuleo'), id])
            redirect(action: "list")
            return
        }

        [pagoManipuleoInstance: pagoManipuleoInstance]
    }

    def edit(Long id) {
        def pagoManipuleoInstance = PagoManipuleo.get(id)
        if (!pagoManipuleoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoManipuleo.label', default: 'PagoManipuleo'), id])
            redirect(action: "list")
            return
        }

        [pagoManipuleoInstance: pagoManipuleoInstance]
    }

    def update(Long id, Long version) {
        def pagoManipuleoInstance = PagoManipuleo.get(id)
        if (!pagoManipuleoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoManipuleo.label', default: 'PagoManipuleo'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (pagoManipuleoInstance.version > version) {
                pagoManipuleoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'pagoManipuleo.label', default: 'PagoManipuleo')] as Object[],
                        "Another user has updated this PagoManipuleo while you were editing")
                render(view: "edit", model: [pagoManipuleoInstance: pagoManipuleoInstance])
                return
            }
        }

        pagoManipuleoInstance.properties = params

        if (!pagoManipuleoInstance.save(flush: true)) {
            render(view: "edit", model: [pagoManipuleoInstance: pagoManipuleoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'pagoManipuleo.label', default: 'PagoManipuleo'), pagoManipuleoInstance.id])
        redirect(action: "show", id: pagoManipuleoInstance.id)
    }

    def delete(Long id) {
        def pagoManipuleoInstance = PagoManipuleo.get(id)
        if (!pagoManipuleoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoManipuleo.label', default: 'PagoManipuleo'), id])
            redirect(action: "list")
            return
        }

        try {
            pagoManipuleoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'pagoManipuleo.label', default: 'PagoManipuleo'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'pagoManipuleo.label', default: 'PagoManipuleo'), id])
            redirect(action: "show", id: id)
        }
    }

    def recepcionesJSON() {
        def deposito = Deposito.get(params.depositoId.toString().toLong())
        def recepcionesComplejo = RecepcionDeComplejo.findAllByDepositoAndManipuleoPagado(deposito,"NO")
        def recepcionesList = []
        if (recepcionesComplejo){
            recepcionesComplejo.each { recepcion ->
                def mapaRecepcion = [:]
                mapaRecepcion.put("recepcionId", recepcion.id)
                mapaRecepcion.put("lote", recepcion.toString())
                mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
                mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
                mapaRecepcion.put("tipoDeMaterial", recepcion.tipoDeMaterial)
                mapaRecepcion.put("pesadaVaciada", "NO")
                mapaRecepcion.put("carguioMaquina", "NO")
                mapaRecepcion.put("embolsadaArrumada", "NO")
                mapaRecepcion.put("soloComuneada", "NO")
                mapaRecepcion.put("soloVaciada", "NO")
                mapaRecepcion.put("soloPesada", "NO")
                mapaRecepcion.put("soloEmbolsada", "NO")
                mapaRecepcion.put("costoManipuleo", 0)
                recepcionesList.add(mapaRecepcion)
            }
        }
        render([lotes: (recepcionesList as JSON).toString()] as JSON)
    }

    def nombreCobradorJSON() {
        def pagos=PagoManipuleo.withCriteria {
            projections {
                distinct "ci"
                property("nombreCobrador")
            }
            like("ci","${params.term}%")
        }.sort()

        def pagosList = []
        pagos.each {
            def mapaClientes = [:]
            //parametros en JSON para JQuery UI Autocomplete
            mapaClientes.put("id",it[0])
            mapaClientes.put("label","${it[0]} - ${it[1]}") //son las cadenas que se muestran en la lista
            mapaClientes.put("value",it[0]) //es la cadena que se establece en el input despues de ser seleccionado
            mapaClientes.put("nombreCobrador",it[1])
            pagosList.add(mapaClientes)
        }
        render pagosList as JSON
    }

    def createReport = {
        def factura = PagoManipuleo.get(params.id)
        def realPath = org.socymet.util.ReportesRuntime.realPath("/reports/images/")
        params.realPath=realPath+"/"
        chain(controller:'jasper',action:'index',model:[data:factura],params:params)
    }
}
