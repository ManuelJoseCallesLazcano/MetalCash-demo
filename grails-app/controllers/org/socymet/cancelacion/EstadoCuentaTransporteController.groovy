package org.socymet.cancelacion
import grails.converters.JSON
import grails.gorm.transactions.Transactional

import org.socymet.proveedor.Automovil
import org.socymet.proveedor.Empresa
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured


@Secured(['ROLE_ADMIN','ROLE_RECEPCION','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class EstadoCuentaTransporteController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [estadoCuentaTransporteInstanceList: EstadoCuentaTransporte.list(params), estadoCuentaTransporteInstanceTotal: EstadoCuentaTransporte.count()]
    }

    def create() {
        [estadoCuentaTransporteInstance: new EstadoCuentaTransporte(params)]
    }

    def save() {
        def estadoCuentaTransporteInstance = new EstadoCuentaTransporte(params)
        if (!estadoCuentaTransporteInstance.save(flush: true)) {
            render(view: "create", model: [estadoCuentaTransporteInstance: estadoCuentaTransporteInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'estadoCuentaTransporte.label', default: 'EstadoCuentaTransporte'), estadoCuentaTransporteInstance.id])
        redirect(action: "show", id: estadoCuentaTransporteInstance.id)
    }

    def show(Long id) {
        def estadoCuentaTransporteInstance = EstadoCuentaTransporte.get(id)
        if (!estadoCuentaTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'estadoCuentaTransporte.label', default: 'EstadoCuentaTransporte'), id])
            redirect(action: "list")
            return
        }

        [estadoCuentaTransporteInstance: estadoCuentaTransporteInstance]
    }

    def edit(Long id) {
        def estadoCuentaTransporteInstance = EstadoCuentaTransporte.get(id)
        if (!estadoCuentaTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'estadoCuentaTransporte.label', default: 'EstadoCuentaTransporte'), id])
            redirect(action: "list")
            return
        }

        [estadoCuentaTransporteInstance: estadoCuentaTransporteInstance]
    }

    def update(Long id, Long version) {
        def estadoCuentaTransporteInstance = EstadoCuentaTransporte.get(id)
        if (!estadoCuentaTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'estadoCuentaTransporte.label', default: 'EstadoCuentaTransporte'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (estadoCuentaTransporteInstance.version > version) {
                estadoCuentaTransporteInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'estadoCuentaTransporte.label', default: 'EstadoCuentaTransporte')] as Object[],
                        "Another user has updated this EstadoCuentaTransporte while you were editing")
                render(view: "edit", model: [estadoCuentaTransporteInstance: estadoCuentaTransporteInstance])
                return
            }
        }

        estadoCuentaTransporteInstance.properties = params

        if (!estadoCuentaTransporteInstance.save(flush: true)) {
            render(view: "edit", model: [estadoCuentaTransporteInstance: estadoCuentaTransporteInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'estadoCuentaTransporte.label', default: 'EstadoCuentaTransporte'), estadoCuentaTransporteInstance.id])
        redirect(action: "show", id: estadoCuentaTransporteInstance.id)
    }

    def delete(Long id) {
        def estadoCuentaTransporteInstance = EstadoCuentaTransporte.get(id)
        if (!estadoCuentaTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'estadoCuentaTransporte.label', default: 'EstadoCuentaTransporte'), id])
            redirect(action: "list")
            return
        }

        try {
            estadoCuentaTransporteInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'estadoCuentaTransporte.label', default: 'EstadoCuentaTransporte'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'estadoCuentaTransporte.label', default: 'EstadoCuentaTransporte'), id])
            redirect(action: "show", id: id)
        }
    }

    def obtenerSaldoJSON = {
        // Titular del ledger = Automovil. El disponible es el saldo del ultimo asiento del automovil.
        def automovil=null
        def ultimo=null
        def saldo=0
        def notificar=""
        if(params.automovilId!=null && !params.automovilId.toString().equals("null")){
            automovil = Automovil.get(params.automovilId.toString().toLong())
            ultimo = EstadoCuentaTransporte.findByAutomovil(automovil, [sort: "id", order: "desc"])
            notificar="Ultima transaccion para Transporte:\nAutomovil: ${automovil.toString()}\n"
        }
        if (ultimo){
            saldo = ultimo.saldo
            notificar=notificar+"Descripcion: ${ultimo.descripcion}\nFecha:${new java.text.SimpleDateFormat('dd/MM/yyyy').format(ultimo.fecha)}\nIngreso:${ultimo.ingreso}\nEgreso:${ultimo.egreso}\nDisponible:${ultimo.saldo}"
        }else{
            notificar=notificar+"No existen transacciones anteriores:\nDisponible: 0"
        }
        render([
            saldoCuenta: saldo,
            notificacion: notificar
        ] as JSON)
    }

    /**
     * Búsqueda asíncrona (Select2) de CI/cobrador sobre el ledger de transporte
     * (EstadoCuentaTransporte). Devuelve combinaciones CI + nombreResponsable DISTINTAS
     * (sin duplicados) que empiezan por el término, para autocompletar el nombre del cobrador
     * al elegir un CI. Fuente compartida por los formularios de Anticipo y Pago de Transporte.
     */
    def cobradorBusquedaJSON() {
        def q = params.q?.toString()?.trim()
        def resultados = []
        if (q) {
            def filas = EstadoCuentaTransporte.withCriteria {
                projections {
                    distinct 'ci'
                    property 'nombreResponsable'
                }
                ilike 'ci', "${q}%"
            }
            resultados = filas.sort { it[0] }.collect { fila ->
                [id: fila[0], text: "${fila[0]} — ${fila[1]}", ci: fila[0], nombreCobrador: fila[1]]
            }
        }
        render([results: resultados] as JSON)
    }
}