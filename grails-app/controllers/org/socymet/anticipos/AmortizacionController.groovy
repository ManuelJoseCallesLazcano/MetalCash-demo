package org.socymet.anticipos
import grails.gorm.transactions.Transactional

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_RECEPCION','ROLE_CAJA'])
@Transactional
class AmortizacionController {

    static allowedMethods = [save: "POST", anular: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.sort = "id"
        params.order = "desc"
        [amortizacionInstanceList: Amortizacion.list(params), amortizacionInstanceTotal: Amortizacion.count()]
    }

    def create() {
        [amortizacionInstance: new Amortizacion(params)]
    }

    def save() {
        def amortizacionInstance = new Amortizacion(params)

        // No se puede amortizar si el cliente no tiene deuda pendiente.
        // (Si no hay cliente, se deja que la validación del dominio reporte el error.)
        def cliente = amortizacionInstance.cliente
        if (cliente) {
            def ultimoEstado = EstadoDeCuenta.findAllByCliente(cliente, [sort: "id", order: "desc"])[0]
            def saldoActual = ultimoEstado?.saldo ?: 0G
            if (saldoActual <= 0) {
                flash.message = "No se puede amortizar: el cliente ${cliente.nombre} no tiene deuda pendiente (saldo Bs ${saldoActual})."
                flash.swalIcon = 'error'
                render(view: "create", model: [amortizacionInstance: amortizacionInstance])
                return
            }
            if (amortizacionInstance.importe != null && amortizacionInstance.importe > saldoActual) {
                flash.message = "No se puede amortizar Bs ${amortizacionInstance.importe}: supera la deuda pendiente del cliente ${cliente.nombre} (Bs ${saldoActual})."
                flash.swalIcon = 'error'
                render(view: "create", model: [amortizacionInstance: amortizacionInstance])
                return
            }
            // Saldo del cliente fijado en backend (fuente única, no depende del JS del form)
            amortizacionInstance.saldoActual = saldoActual
            amortizacionInstance.saldoPorPagar = saldoActual - (amortizacionInstance.importe ?: 0G)
        }

        if (!amortizacionInstance.save(flush: true)) {
            render(view: "create", model: [amortizacionInstance: amortizacionInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'amortizacion.label', default: 'Amortizacion'), amortizacionInstance.toString()])
        redirect(action: "show", id: amortizacionInstance.id)
    }

    def show(Long id) {
        def amortizacionInstance = Amortizacion.get(id)
        if (!amortizacionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'amortizacion.label', default: 'Amortizacion'), id])
            redirect(action: "list")
            return
        }

        [amortizacionInstance: amortizacionInstance]
    }

    /**
     * Anular la amortización. No se edita ni elimina: la anulación revierte su efecto en el
     * estado de cuenta (la amortización bajó la deuda con un HABER; la reversa registra un
     * DEBE) y marca el registro como anulado. La reversa queda ligada al documento origen.
     */
    def anular(Long id) {
        def amortizacionInstance = Amortizacion.get(id)
        if (!amortizacionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'amortizacion.label', default: 'Amortizacion'), id])
            redirect(action: "list")
            return
        }
        if (amortizacionInstance.anulado) {
            flash.message = "La amortización ya está anulada."
            flash.swalIcon = 'error'
            redirect(action: "show", id: id)
            return
        }

        def cliente = amortizacionInstance.cliente
        def ultimo = EstadoDeCuenta.findAllByCliente(cliente, [sort: "id", order: "desc"])[0]
        def ultimoSaldo = ultimo?.saldo ?: 0G
        new EstadoDeCuenta(
                cliente: cliente,
                empresa: amortizacionInstance.empresa,
                ci: amortizacionInstance.ci,
                nombre: amortizacionInstance.nombre,
                nombreEmpresa: amortizacionInstance.nombreEmpresa,
                fecha: new Date(),
                numeroComprobante: amortizacionInstance.numeroAmortizacion,
                detalle: "ANULACION DE AMORTIZACION " + amortizacionInstance.concepto,
                debe: amortizacionInstance.importe,
                haber: 0.0,
                saldo: ultimoSaldo + amortizacionInstance.importe,
                liquidacionId: 0,
                tipoMovimiento: TipoMovimiento.AMORTIZACION,
                origenId: amortizacionInstance.id
        ).save(flush: true, failOnError: true)

        amortizacionInstance.anulado = true
        amortizacionInstance.save(flush: true, failOnError: true)

        flash.message = "Amortización N° ${amortizacionInstance} anulada. Se revirtió su efecto en el estado de cuenta."
        flash.swalIcon = 'success'
        flash.swalTitle = 'Amortización anulada'
        redirect(action: "show", id: id)
    }

    def crearReporte = {
        def amortizacion = Amortizacion.get(params.id)
        def realPath = servletContext.getRealPath("/reports/images/")
        params.realPath=realPath+"/"
        chain(controller:'jasper',action:'index',model:[data:amortizacion],params:params)
    }
}
