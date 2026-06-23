package org.socymet.calidad
import grails.converters.JSON
import grails.gorm.transactions.Transactional

import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.seguridad.SecUser
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_CONTROL_CALIDAD'])
@Transactional
class ControlCalidadComplejoController {
    def springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.sort = params.sort ?: "id"
        params.order = params.order ?: "desc"

        // Buscador por lote, cliente, empresa, N° análisis o laboratorio
        def q = params.q?.trim()

        def results = ControlCalidadComplejo.createCriteria().list(
                max: params.max, offset: params.offset ?: 0,
                sort: params.sort, order: params.order) {
            if (q) {
                or {
                    ilike('lote', "%${q}%")
                    ilike('nombreCliente', "%${q}%")
                    ilike('nombreEmpresa', "%${q}%")
                    ilike('numeroAnalisis', "%${q}%")
                    ilike('nombreLaboratorio', "%${q}%")
                }
            }
        }

        [controlCalidadComplejoInstanceList: results,
         controlCalidadComplejoInstanceTotal: results.totalCount,
         q: q]
    }

    def create() {
        [controlCalidadComplejoInstance: new ControlCalidadComplejo(params)]
    }

    def save() {
        def controlCalidadComplejoInstance = new ControlCalidadComplejo(params)
        if (!controlCalidadComplejoInstance.save(flush: true)) {
            render(view: "create", model: [controlCalidadComplejoInstance: controlCalidadComplejoInstance])
            return
        }

        // El lote queda con análisis. Se actualiza en la MISMA transacción (con flush
        // explícito) para persistir el cambio sin el lock wait timeout que producía
        // hacerlo en un afterInsert con withNewTransaction.
        def recepcion = controlCalidadComplejoInstance.recepcionDeComplejo
        recepcion.estadoAnalisis = "CON ANALISIS"
        recepcion.save(flush: true, failOnError: true)

//        flash.message = message(code: 'default.created.message', args: [message(code: 'controlCalidadComplejo.label', default: 'ControlCalidadComplejo'), controlCalidadComplejoInstance.id])
        flash.message = message(code: 'default.created.message', args: [message(code: 'controlCalidadComplejo.label', default: 'ControlCalidadComplejo'), controlCalidadComplejoInstance.lote])
        redirect(action: "show", id: controlCalidadComplejoInstance.id)
    }

    def show(Long id) {
        def controlCalidadComplejoInstance = ControlCalidadComplejo.get(id)
        if (!controlCalidadComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'controlCalidadComplejo.label', default: 'ControlCalidadComplejo'), id])
            redirect(action: "list")
            return
        }

        [controlCalidadComplejoInstance: controlCalidadComplejoInstance]
    }

    def edit(Long id) {
        def controlCalidadComplejoInstance = ControlCalidadComplejo.get(id)
        if (!controlCalidadComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'controlCalidadComplejo.label', default: 'ControlCalidadComplejo'), id])
            redirect(action: "list")
            return
        }

        [controlCalidadComplejoInstance: controlCalidadComplejoInstance]
    }

    def update(Long id, Long version) {
        def controlCalidadComplejoInstance = ControlCalidadComplejo.get(id)
        if (!controlCalidadComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'controlCalidadComplejo.label', default: 'ControlCalidadComplejo'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (controlCalidadComplejoInstance.version > version) {
                controlCalidadComplejoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'controlCalidadComplejo.label', default: 'ControlCalidadComplejo')] as Object[],
                          "Another user has updated this ControlCalidadComplejo while you were editing")
                render(view: "edit", model: [controlCalidadComplejoInstance: controlCalidadComplejoInstance])
                return
            }
        }

        controlCalidadComplejoInstance.properties = params

        if (!controlCalidadComplejoInstance.save(flush: true)) {
            render(view: "edit", model: [controlCalidadComplejoInstance: controlCalidadComplejoInstance])
            return
        }

//        flash.message = message(code: 'default.updated.message', args: [message(code: 'controlCalidadComplejo.label', default: 'ControlCalidadComplejo'), controlCalidadComplejoInstance.id])
        flash.message = message(code: 'default.updated.message', args: [message(code: 'controlCalidadComplejo.label', default: 'ControlCalidadComplejo'), controlCalidadComplejoInstance.recepcionDeComplejo.toString()])
        redirect(action: "show", id: controlCalidadComplejoInstance.id)
    }

    def delete(Long id) {
        def controlCalidadComplejoInstance = ControlCalidadComplejo.get(id)
        if (!controlCalidadComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'controlCalidadComplejo.label', default: 'ControlCalidadComplejo'), id])
            redirect(action: "list")
            return
        }

        try {
            def recepcion = controlCalidadComplejoInstance.recepcionDeComplejo
            controlCalidadComplejoInstance.delete(flush: true)

            // Al quitar el análisis, el lote vuelve a quedar SIN ANALISIS
            if (recepcion) {
                recepcion.estadoAnalisis = "SIN ANALISIS"
                recepcion.save(flush: true, failOnError: true)
            }

//            flash.message = message(code: 'default.deleted.message', args: [message(code: 'controlCalidadComplejo.label', default: 'ControlCalidadComplejo'), id])
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'controlCalidadComplejo.label', default: 'ControlCalidadComplejo'), recepcion.toString()])
            flash.swalIcon = 'success'
            flash.swalTitle = 'Eliminación realizada'
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'controlCalidadComplejo.label', default: 'ControlCalidadComplejo'), id])
            redirect(action: "show", id: id)
        }
    }

    def recepcionCalidadComplejoJSON() {
        def recepcion = RecepcionDeComplejo.get(params.recepcionId)
        render([
            recepcionId: recepcion.id,
            nombreCliente: recepcion.cliente.nombre,
            empresaId: recepcion.empresa.id,
            nombreEmpresa: recepcion.empresa.toString(),
            fechaDeRecepcion: new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion),
            cantidadDeSacos: recepcion.cantidadDeSacos,
            pesoBruto: recepcion.pesoBruto,
            estadoDelLote: recepcion.estadoDelLote,
            condicionDeEntrega: recepcion.condicionDeEntrega
        ] as JSON)
    }

    def leyes = {
        try {
            def recepcion = RecepcionDeComplejo.get(params.recepcionDeComplejoId.toLong())
            if (recepcion){
                def controlCalidad = ControlCalidadComplejo.findByRecepcionDeComplejo(recepcion)
                if (controlCalidad){
                    render([
                        porcentajeMermaPromexbol: controlCalidad.porcentajeMermaPromexbol,
                        porcentajeHumedadPromexbol: controlCalidad.porcentajeHumedadPromexbol,
                        porcentajeZincPromexbol: controlCalidad.porcentajeZincPromexbol,
                        porcentajePlomoPromexbol: controlCalidad.porcentajePlomoPromexbol,
                        porcentajePlataPromexbol: controlCalidad.porcentajePlataPromexbol
                    ] as JSON)
                }else{
                    render([
                        porcentajeMermaPromexbol: 0,
                        porcentajeHumedadPromexbol: 0,
                        porcentajeZincPromexbol: 0,
                        porcentajePlomoPromexbol: 0,
                        porcentajePlataPromexbol: 0
                    ] as JSON)
                }
            }
        }catch (Exception e){
            render([
                porcentajeMermaPromexbol: -1,
                porcentajeHumedadPromexbol: -1,
                porcentajeZincPromexbol: -1,
                porcentajePlomoPromexbol: -1,
                porcentajePlataPromexbol: -1
            ] as JSON)
        }        
    }
}
