package org.socymet.anticipos
import grails.converters.JSON
import grails.gorm.transactions.Transactional
import grails.plugins.jasper.JasperExportFormat
import grails.plugins.jasper.JasperReportDef

import org.socymet.cancelacion.EstadoCuentaTransporte
import org.socymet.proveedor.Automovil
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_RECEPCION','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class AnticipoPorTransporteController {

    def jasperService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST", anular: "POST"]

    /**
     * Impresión oficial: genera orden_anticipo_contra_transporte.jasper (reporte SQL) a PDF
     * DIRECTAMENTE con jasperService, que provee la conexión JDBC (`$P{REPORT_CONNECTION}`). No usa
     * chain al controller 'jasper'. El id REAL va en el query param 'lid'; el segmento de ruta lleva
     * el N° del anticipo (título de la pestaña). Parámetros del reporte: id (query) y realPath (logo).
     */
    def imprimirPdf() {
        Long id = params.long('lid')
        def apt = id ? AnticipoPorTransporte.get(id) : null
        if (!apt) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipoPorTransporte.label', default: 'AnticipoPorTransporte'), id])
            redirect(action: "list"); return
        }
        Map rp = [
            id      : id.toString(),
            realPath: org.socymet.util.ReportesRuntime.realPath('/reports') + '/images/'
        ]
        def reportDef = new JasperReportDef(name: 'orden_anticipo_contra_transporte.jasper',
                fileFormat: JasperExportFormat.PDF_FORMAT, parameters: rp)
        byte[] bytes = jasperService.generateReport(reportDef).toByteArray()
        String nombre = "AnticipoTransporte-${apt.toString()}".replaceAll(/[^0-9A-Za-z._-]/, '-')
        response.contentType = 'application/pdf'
        response.setHeader('Content-Disposition', "inline; filename=\"${nombre}.pdf\"")
        response.outputStream << bytes
        response.outputStream.flush()
    }

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.sort = params.sort ?: "id"
        params.order = params.order ?: "desc"
        def q = params.q?.trim()
        def filtro = {
            if (q) {
                or {
                    if (q.isInteger()) eq('numeroComprobante', q.toInteger())
                    ilike('ci', "%${q}%")
                    ilike('nombreCobrador', "%${q}%")
                }
            }
        }
        def instancias = AnticipoPorTransporte.createCriteria().list(params, filtro)
        def total = AnticipoPorTransporte.createCriteria().count(filtro)
        [anticipoPorTransporteInstanceList: instancias, anticipoPorTransporteInstanceTotal: total, q: q]
    }

    def create() {
        [anticipoPorTransporteInstance: new AnticipoPorTransporte(params)]
    }

    def save() {
        def anticipoPorTransporteInstance = new AnticipoPorTransporte(params)
        if (!anticipoPorTransporteInstance.save(flush: true)) {
            render(view: "create", model: [anticipoPorTransporteInstance: anticipoPorTransporteInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'anticipoPorTransporte.label', default: 'AnticipoPorTransporte'), anticipoPorTransporteInstance.toString()])
        redirect(action: "show", id: anticipoPorTransporteInstance.id)
    }

    def show(Long id) {
        def anticipoPorTransporteInstance = AnticipoPorTransporte.get(id)
        if (!anticipoPorTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipoPorTransporte.label', default: 'AnticipoPorTransporte'), id])
            redirect(action: "list")
            return
        }

        [anticipoPorTransporteInstance: anticipoPorTransporteInstance]
    }

    // Este módulo NO admite edición (se revierte con Anular). Se bloquean edit y update: /edit/{id}
    // no debe caer en 404 y no se puede forzar el guardado por POST directo.
    def edit(Long id) { bloquearEdicion(id) }

    def update(Long id, Long version) { bloquearEdicion(id) }

    private void bloquearEdicion(Long id) {
        flash.message = "No se permite editar un anticipo por transporte. Use Anular si necesita revertirlo."
        flash.swalIcon = 'warning'
        flash.swalTitle = 'Operación no disponible'
        redirect(action: "show", id: id)
    }

    def anular(Long id) {
        // Anulacion por REVERSA: no se borra el documento; se registra un asiento inverso en el ledger.
        def anticipoPorTransporteInstance = AnticipoPorTransporte.get(id)
        if (!anticipoPorTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'anticipoPorTransporte.label', default: 'AnticipoPorTransporte'), id])
            redirect(action: "list")
            return
        }

        if (anticipoPorTransporteInstance.anulado) {
            flash.swalIcon = "warning"
            flash.swalTitle = "Sin cambios"
            flash.message = "El anticipo #${anticipoPorTransporteInstance.numeroComprobante} ya estaba anulado."
            redirect(action: "show", id: id)
            return
        }

        // El anticipo habia SUBIDO el disponible (+importe); la reversa lo BAJA (−importe).
        def disponible = EstadoCuentaTransporte.saldoDisponible(anticipoPorTransporteInstance.automovil)
        new EstadoCuentaTransporte(
                solicitante: anticipoPorTransporteInstance.solicitante,
                empresa: anticipoPorTransporteInstance.empresa,
                automovil: anticipoPorTransporteInstance.automovil,
                ci: anticipoPorTransporteInstance.ci,
                nombreResponsable: anticipoPorTransporteInstance.nombreCobrador,
                fecha: new Date(),
                descripcion: "REVERSA ANTICIPO POR TRANSPORTE No. ${anticipoPorTransporteInstance.toString()}",
                ingreso: 0,
                egreso: anticipoPorTransporteInstance.importe,
                saldo: disponible - anticipoPorTransporteInstance.importe,
                tipoMovimiento: "REVERSA_ANTICIPO_TRANSPORTE",
                origenId: anticipoPorTransporteInstance.id
        ).save(flush: true, failOnError: true)

        anticipoPorTransporteInstance.anulado = true
        anticipoPorTransporteInstance.save(flush: true, failOnError: true)

        flash.swalIcon = "success"
        flash.swalTitle = "Anticipo anulado"
        flash.message = "Anticipo No. ${anticipoPorTransporteInstance.toString()} anulado (reversa registrada en el estado de cuenta del Automóvil)."
        redirect(action: "show", id: id)
    }

    def recuperarDatosChoferJSON = {
        def recepcion = RecepcionDeComplejo.get(params.recepcionId)
        render([
            ciChofer: recepcion.chofer.ci,
            nombreChofer: recepcion.chofer.nombre
        ] as JSON)
    }

}
