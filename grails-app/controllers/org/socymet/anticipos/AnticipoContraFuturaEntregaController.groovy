package org.socymet.anticipos
import grails.gorm.transactions.Transactional

import grails.plugins.jasper.JasperExportFormat
import grails.plugins.jasper.JasperReportDef
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_RECEPCION','ROLE_CAJA'])
@Transactional
class AnticipoContraFuturaEntregaController {

    def jasperService

    static allowedMethods = [save: "POST", anular: "POST"]

    /**
     * Impresión oficial: genera anticipo_contra_futura_entrega.jasper (reporte SQL) a PDF DIRECTAMENTE
     * con jasperService, que provee la conexión JDBC (`$P{REPORT_CONNECTION}`). No usa chain al
     * controller 'jasper'. El id REAL va en el query param 'lid'; el segmento de ruta lleva el N° del
     * anticipo (título de la pestaña del navegador). Parámetros del reporte: id (query) y realPath (logo).
     */
    def imprimirPdf() {
        Long id = params.long('lid')
        def acfe = id ? AnticipoContraFuturaEntrega.get(id) : null
        if (!acfe) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipoContraFuturaEntrega.label', default: 'AnticipoContraFuturaEntrega'), id])
            redirect(action: "list"); return
        }
        Map rp = [
            id      : id.toString(),
            realPath: org.socymet.util.ReportesRuntime.realPath('/reports') + '/images/'
        ]
        def reportDef = new JasperReportDef(name: 'anticipo_contra_futura_entrega.jasper',
                fileFormat: JasperExportFormat.PDF_FORMAT, parameters: rp)
        byte[] bytes = jasperService.generateReport(reportDef).toByteArray()
        String nombre = "AnticipoFuturaEntrega-${acfe.toString()}".replaceAll(/[^0-9A-Za-z._-]/, '-')
        response.contentType = 'application/pdf'
        response.setHeader('Content-Disposition', "inline; filename=\"${nombre}.pdf\"")
        response.outputStream << bytes
        response.outputStream.flush()
    }

    /** Este módulo no admite edición (se revierte con Anular). Se intercepta la ruta /edit/{id}
     *  para que no caiga en 404: redirige al show con aviso. */
    def edit(Long id) {
        flash.message = "No se permite editar un anticipo contra futura entrega. Use Anular si necesita revertirlo."
        flash.swalIcon = 'warning'
        flash.swalTitle = 'Operación no disponible'
        redirect(action: "show", id: id)
    }

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.sort = params.sort ?: "id"
        params.order = params.order ?: "desc"

        // Buscador: nº de comprobante, nombre de cliente o nombre de empresa
        def q = params.q?.trim()
        def numComprobante = null
        if (q) { def m = q =~ /^(\d+)/; if (m) numComprobante = m[0][1] as Integer }

        def results = AnticipoContraFuturaEntrega.createCriteria().list(
                max: params.max, offset: params.offset ?: 0,
                sort: params.sort, order: params.order) {
            if (q) {
                createAlias('cliente', 'c')
                createAlias('empresa', 'e')
                or {
                    ilike('c.nombre', "%${q}%")
                    ilike('e.nombreDeEmpresa', "%${q}%")
                    if (numComprobante != null) eq('numeroAnticipo', numComprobante)
                }
            }
        }

        [anticipoContraFuturaEntregaInstanceList: results, anticipoContraFuturaEntregaInstanceTotal: results.totalCount, q: q]
    }

    def create() {
        [anticipoContraFuturaEntregaInstance: new AnticipoContraFuturaEntrega(params)]
    }

    def save() {
        def anticipoContraFuturaEntregaInstance = new AnticipoContraFuturaEntrega(params)
        if (!anticipoContraFuturaEntregaInstance.save(flush: true)) {
            render(view: "create", model: [anticipoContraFuturaEntregaInstance: anticipoContraFuturaEntregaInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'anticipoContraFuturaEntrega.label', default: 'AnticipoContraFuturaEntrega'), anticipoContraFuturaEntregaInstance.toString()])
        redirect(action: "show", id: anticipoContraFuturaEntregaInstance.id)
    }

    def show(Long id) {
        def anticipoContraFuturaEntregaInstance = AnticipoContraFuturaEntrega.get(id)
        if (!anticipoContraFuturaEntregaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipoContraFuturaEntrega.label', default: 'AnticipoContraFuturaEntrega'), id])
            redirect(action: "list")
            return
        }

        [anticipoContraFuturaEntregaInstance: anticipoContraFuturaEntregaInstance]
    }

    /**
     * Anular el anticipo. No se edita ni elimina: la anulación revierte su efecto en el
     * estado de cuenta (el anticipo sumó deuda con un DEBE; la reversa registra un HABER)
     * y marca el registro como anulado. La reversa queda ligada al mismo documento origen.
     */
    def anular(Long id) {
        def anticipoContraFuturaEntregaInstance = AnticipoContraFuturaEntrega.get(id)
        if (!anticipoContraFuturaEntregaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipoContraFuturaEntrega.label', default: 'AnticipoContraFuturaEntrega'), id])
            redirect(action: "list")
            return
        }
        if (anticipoContraFuturaEntregaInstance.anulado) {
            flash.message = "El anticipo ya está anulado."
            flash.swalIcon = 'error'
            redirect(action: "show", id: id)
            return
        }

        def cliente = anticipoContraFuturaEntregaInstance.cliente
        def ultimo = EstadoDeCuenta.findAllByCliente(cliente, [sort: "id", order: "desc"])[0]
        def ultimoSaldo = ultimo?.saldo ?: 0G
        new EstadoDeCuenta(
                cliente: cliente,
                empresa: anticipoContraFuturaEntregaInstance.empresa,
                ci: cliente.ci,
                nombre: cliente.nombre,
                nombreEmpresa: anticipoContraFuturaEntregaInstance.empresa.nombreDeEmpresa,
                fecha: new Date(),
                numeroComprobante: anticipoContraFuturaEntregaInstance.numeroAnticipo,
                detalle: "ANULACION DE ANTICIPO CONTRA FUTURA ENTREGA " + anticipoContraFuturaEntregaInstance.compromiso,
                debe: 0.0,
                haber: anticipoContraFuturaEntregaInstance.importe,
                saldo: ultimoSaldo - anticipoContraFuturaEntregaInstance.importe,
                liquidacionId: 0,
                tipoMovimiento: TipoMovimiento.ANTICIPO_FUTURA_ENTREGA,
                origenId: anticipoContraFuturaEntregaInstance.id
        ).save(flush: true, failOnError: true)

        anticipoContraFuturaEntregaInstance.anulado = true
        anticipoContraFuturaEntregaInstance.save(flush: true, failOnError: true)

        flash.message = "Anticipo N° ${anticipoContraFuturaEntregaInstance} anulado. Se revirtió su efecto en el estado de cuenta."
        flash.swalIcon = 'success'
        flash.swalTitle = 'Anticipo anulado'
        redirect(action: "show", id: id)
    }
}
