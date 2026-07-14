package org.socymet.org.socymet.reportes

import grails.gorm.transactions.Transactional
import org.socymet.anticipos.AnticipoPorTransporte
import org.socymet.cancelacion.EstadoCuentaTransporte
import org.socymet.cancelacion.PagoTransporte
import org.socymet.proveedor.Automovil
import org.springframework.security.access.annotation.Secured

/**
 * Reporte "Estado de Cuenta de Transporte": ledger (ingreso/egreso/saldo disponible)
 * de un Automóvil en un rango de fechas, con vista previa y exportación a XLSX.
 * Espejo de ReporteEstadoCuentaClienteController (cliente) → sobre EstadoCuentaTransporte.
 */
@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA','ROLE_REPORTES'])
class ReporteEstadoCuentaTransporteController {

    def estadoCuentaTransporteExcelService   // genera el XLSX con Apache POI

    def index() {
        redirect(action: "create", params: params)
    }

    def create() {
        def automovil = params.automovilId ? Automovil.get(params.long('automovilId')) : null
        // El datepickerUI envía nombre_day / nombre_month / nombre_year
        Date fi = null, ff = null
        if (params.fechaInicial_year) {
            fi = new java.text.SimpleDateFormat('yyyy-M-d').parse("${params.fechaInicial_year}-${params.fechaInicial_month}-${params.fechaInicial_day}")
        }
        if (params.fechaFinal_year) {
            ff = new java.text.SimpleDateFormat('yyyy-M-d').parse("${params.fechaFinal_year}-${params.fechaFinal_month}-${params.fechaFinal_day}")
        }
        def movimientos = null
        def comprobante = [:]
        if (automovil && fi && ff) {
            def ffFin = new java.text.SimpleDateFormat('yyyy-M-d HH:mm:ss').parse("${params.fechaFinal_year}-${params.fechaFinal_month}-${params.fechaFinal_day} 23:59:59")
            movimientos = EstadoCuentaTransporte.findAllByAutomovilAndFechaBetween(automovil, fi, ffFin, [sort: 'id', order: 'asc'])
            comprobante = construirComprobantes(movimientos)
        }
        [automovil: automovil, fechaInicial: fi ?: new Date(), fechaFinal: ff ?: new Date(),
         movimientos: movimientos, comprobante: comprobante]
    }

    /** Mapa asiento.id → comprobante del documento origen ("numero/año" vía toString del origen). */
    private Map construirComprobantes(List movimientos) {
        def comprobante = [:]
        def esAnticipo = { it in ['ANTICIPO_TRANSPORTE', 'REVERSA_ANTICIPO_TRANSPORTE'] }
        def esPago = { it in ['PAGO_TRANSPORTE', 'REVERSA_PAGO_TRANSPORTE'] }

        def antIds = movimientos.findAll { esAnticipo(it.tipoMovimiento) && it.origenId }*.origenId.unique()
        def pagoIds = movimientos.findAll { esPago(it.tipoMovimiento) && it.origenId }*.origenId.unique()
        def antMap = antIds ? AnticipoPorTransporte.getAll(antIds).findAll { it }.collectEntries { [(it.id): it] } : [:]
        def pagoMap = pagoIds ? PagoTransporte.getAll(pagoIds).findAll { it }.collectEntries { [(it.id): it] } : [:]

        movimientos.each { ec ->
            if (esAnticipo(ec.tipoMovimiento)) {
                def a = antMap[ec.origenId]; if (a) comprobante[ec.id] = a.toString()
            } else if (esPago(ec.tipoMovimiento)) {
                def p = pagoMap[ec.origenId]; if (p) comprobante[ec.id] = p.toString()
            }
        }
        comprobante
    }

    /** Exporta el estado de cuenta del automóvil a XLSX. Recibe automovilId y rango fi/ff (yyyy-MM-dd). */
    def exportarExcel() {
        def automovil = params.automovilId ? Automovil.get(params.long('automovilId')) : null
        Date fi = params.fi ? new java.text.SimpleDateFormat('yyyy-MM-dd').parse(params.fi) : null
        Date ff = params.ff ? new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss').parse(params.ff + ' 23:59:59') : null
        if (!automovil || !fi || !ff) {
            flash.message = "Seleccione un automóvil y un rango de fechas antes de exportar."
            redirect(action: "create"); return
        }
        def movimientos = EstadoCuentaTransporte.findAllByAutomovilAndFechaBetween(automovil, fi, ff, [sort: 'id', order: 'asc'])
        byte[] xlsx = estadoCuentaTransporteExcelService.generar(automovil, fi, ff, movimientos, construirComprobantes(movimientos))

        def nombre = "estado_cuenta_transporte_${(automovil.placa ?: 'automovil').trim().replaceAll('\\s+', '_')}.xlsx"
        response.setContentType('application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')
        response.setHeader('Content-Disposition', "attachment; filename=\"${nombre}\"")
        response.outputStream << xlsx
        response.outputStream.flush()
    }
}
