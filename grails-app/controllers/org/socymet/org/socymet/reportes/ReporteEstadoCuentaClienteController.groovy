package org.socymet.org.socymet.reportes

import jxl.Workbook
import jxl.format.VerticalAlignment
import jxl.write.DateFormat
import jxl.write.Label
import jxl.write.Number
import jxl.write.NumberFormat
import jxl.write.WritableCellFormat
import jxl.write.WritableFont
import jxl.write.WritableSheet
import jxl.write.WritableWorkbook
import org.socymet.anticipos.EstadoDeCuenta
import org.socymet.anticipos.TipoMovimiento
import org.socymet.liquidacion.LiquidacionDeComplejo
import org.socymet.proveedor.Cliente
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA','ROLE_REPORTES'])
class ReporteEstadoCuentaClienteController {

    def estadoCuentaExcelService   // genera el XLSX con Apache POI

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ReporteEstadoCuentaCliente.list(params), model:[reporteEstadoCuentaClienteInstanceCount: ReporteEstadoCuentaCliente.count()]
    }

    def show(ReporteEstadoCuentaCliente reporteEstadoCuentaClienteInstance) {
        respond reporteEstadoCuentaClienteInstance
    }

    def create() {
        def cliente = params.clienteId ? Cliente.get(params.long('clienteId')) : null
        // El datepickerUI envía nombre_day / nombre_month / nombre_year
        Date fi = null, ff = null
        if (params.fechaInicial_year) {
            fi = new java.text.SimpleDateFormat('yyyy-M-d').parse("${params.fechaInicial_year}-${params.fechaInicial_month}-${params.fechaInicial_day}")
        }
        if (params.fechaFinal_year) {
            ff = new java.text.SimpleDateFormat('yyyy-M-d').parse("${params.fechaFinal_year}-${params.fechaFinal_month}-${params.fechaFinal_day}")
        }
        def estadoCuentaList = null
        def comprobanteEc = [:]   // ec.id → "numeroLiquidacionComplejo/añoGestion" para asientos originados por liquidación
        if (cliente && fi && ff) {
            def ffFin = new java.text.SimpleDateFormat('yyyy-M-d HH:mm:ss').parse("${params.fechaFinal_year}-${params.fechaFinal_month}-${params.fechaFinal_day} 23:59:59")
            estadoCuentaList = EstadoDeCuenta.findAllByClienteAndFechaBetween(cliente, fi, ffFin, [sort: 'id', order: 'asc'])

            def liqIds = estadoCuentaList.findAll { it.tipoMovimiento == TipoMovimiento.LIQUIDACION_COMPLEJO && it.origenId }*.origenId.unique()
            def liqMap = liqIds ? LiquidacionDeComplejo.getAll(liqIds).findAll { it }.collectEntries { [(it.id): it] } : [:]
            def anioFmt = new java.text.SimpleDateFormat('yy')
            estadoCuentaList.each { ec ->
                def liq = (ec.tipoMovimiento == TipoMovimiento.LIQUIDACION_COMPLEJO) ? liqMap[ec.origenId] : null
                if (liq) {
                    def anio = liq.gestionMinera ? anioFmt.format(liq.gestionMinera) : ''
                    comprobanteEc[ec.id] = "${liq.numeroLiquidacionComplejo}/${anio}"
                }
            }
        }
        [cliente: cliente, fechaInicial: fi ?: new Date(), fechaFinal: ff ?: new Date(), estadoCuentaList: estadoCuentaList, comprobanteEc: comprobanteEc]
    }

    /** Mapa ec.id → comprobante a mostrar ("numeroLiquidacion/añoGestion" para asientos de liquidación). */
    private Map construirComprobantes(List movimientos) {
        def comprobanteEc = [:]
        def liqIds = movimientos.findAll { it.tipoMovimiento == TipoMovimiento.LIQUIDACION_COMPLEJO && it.origenId }*.origenId.unique()
        def liqMap = liqIds ? LiquidacionDeComplejo.getAll(liqIds).findAll { it }.collectEntries { [(it.id): it] } : [:]
        def anioFmt = new java.text.SimpleDateFormat('yy')
        movimientos.each { ec ->
            def liq = (ec.tipoMovimiento == TipoMovimiento.LIQUIDACION_COMPLEJO) ? liqMap[ec.origenId] : null
            if (liq) comprobanteEc[ec.id] = "${liq.numeroLiquidacionComplejo}/${liq.gestionMinera ? anioFmt.format(liq.gestionMinera) : ''}"
        }
        comprobanteEc
    }

    /** Exporta el estado de cuenta a XLSX (Apache POI). Recibe clienteId y rango fi/ff (yyyy-MM-dd). */
    def exportarExcel() {
        def cliente = params.clienteId ? Cliente.get(params.long('clienteId')) : null
        Date fi = params.fi ? new java.text.SimpleDateFormat('yyyy-MM-dd').parse(params.fi) : null
        Date ff = params.ff ? new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss').parse(params.ff + ' 23:59:59') : null
        if (!cliente || !fi || !ff) {
            flash.message = "Seleccione un cliente y un rango de fechas antes de exportar."
            redirect(action: "create"); return
        }
        def movimientos = EstadoDeCuenta.findAllByClienteAndFechaBetween(cliente, fi, ff, [sort: 'id', order: 'asc'])
        byte[] xlsx = estadoCuentaExcelService.generar(cliente, fi, ff, movimientos, construirComprobantes(movimientos))

        def nombre = "estado_cuenta_${(cliente.nombre ?: 'cliente').trim().replaceAll('\\s+', '_')}.xlsx"
        response.setContentType('application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')
        response.setHeader('Content-Disposition', "attachment; filename=\"${nombre}\"")
        response.outputStream << xlsx
        response.outputStream.flush()
    }

    @Transactional
    def save(ReporteEstadoCuentaCliente reporteEstadoCuentaClienteInstance) {
        if (reporteEstadoCuentaClienteInstance == null) {
            notFound()
            return
        }

        if (reporteEstadoCuentaClienteInstance.hasErrors()) {
            respond reporteEstadoCuentaClienteInstance.errors, view:'create'
            return
        }

        reporteEstadoCuentaClienteInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'reporteEstadoCuentaCliente.label', default: 'ReporteEstadoCuentaCliente'), reporteEstadoCuentaClienteInstance.id])
                redirect reporteEstadoCuentaClienteInstance
            }
            '*' { respond reporteEstadoCuentaClienteInstance, [status: CREATED] }
        }
    }

    def edit(ReporteEstadoCuentaCliente reporteEstadoCuentaClienteInstance) {
        respond reporteEstadoCuentaClienteInstance
    }

    @Transactional
    def update(ReporteEstadoCuentaCliente reporteEstadoCuentaClienteInstance) {
        if (reporteEstadoCuentaClienteInstance == null) {
            notFound()
            return
        }

        if (reporteEstadoCuentaClienteInstance.hasErrors()) {
            respond reporteEstadoCuentaClienteInstance.errors, view:'edit'
            return
        }

        reporteEstadoCuentaClienteInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ReporteEstadoCuentaCliente.label', default: 'ReporteEstadoCuentaCliente'), reporteEstadoCuentaClienteInstance.id])
                redirect reporteEstadoCuentaClienteInstance
            }
            '*'{ respond reporteEstadoCuentaClienteInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(ReporteEstadoCuentaCliente reporteEstadoCuentaClienteInstance) {

        if (reporteEstadoCuentaClienteInstance == null) {
            notFound()
            return
        }

        reporteEstadoCuentaClienteInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ReporteEstadoCuentaCliente.label', default: 'ReporteEstadoCuentaCliente'), reporteEstadoCuentaClienteInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteEstadoCuentaCliente.label', default: 'ReporteEstadoCuentaCliente'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def crearReporte = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Estado Cuenta", 0)
//        sheet1.setRowView(6,600)
        for(i in 0..100)
            sheet1.setColumnView(i,15)
        sheet1.setColumnView(1,30)
//        sheet1.setColumnView(1,30)
//        sheet1.setColumnView(4,25)
//        sheet1.setColumnView(7,40)
//        sheet1.setColumnView(8,40)
//        sheet1.setColumnView(13,40)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD);
        WritableCellFormat formatoEncabezado = new WritableCellFormat (arial10BoldFont);
        formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)
        formatoEncabezado.setWrap(true)
        formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
        //formatoEncabezado.setAlignment(Alignment.CENTRE)
        WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"))
        formatoDatos.setFont(courier8PlainFont)
        formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        WritableCellFormat formatoDatosEnteros = new WritableCellFormat (new NumberFormat("##0"))
        formatoDatosEnteros.setFont(courier8PlainFont)
        formatoDatosEnteros.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        WritableCellFormat formatoTotales = new WritableCellFormat (new NumberFormat("###,##0.00"))
        formatoTotales.setFont(arial10BoldFont)
        formatoTotales.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)
        WritableCellFormat formatoInfoReporte = new WritableCellFormat (arial14BoldFont);
        WritableCellFormat formatoTitulo = new WritableCellFormat (arial16BoldFont);
        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(0,0, "REPORTE DE ESTADO DE CUENTA DE PROVEEDOR",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def cliente = Cliente.get(params.cliente.id)
        def fechaInicial = new Date().parse("yyyy-MM-dd hh:mm:ss","${params.fechaInicial_year}-${params.fechaInicial_month}-${params.fechaInicial_day} 00:00:00")
        def fechaFinal = new Date().parse("yyyy-MM-dd hh:mm:ss","${params.fechaFinal_year}-${params.fechaFinal_month}-${params.fechaFinal_day} 23:59:59")
        def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
        def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)

        sheet1.addCell(new Label(0,2, "CLIENTE:",formatoInfoReporte))
        sheet1.addCell(new Label(1,2, cliente.toString(),formatoInfoReporte))

//        sheet1.addCell(new Label(0,3, "RANGO DE FECHAS:",formatoInfoReporte))
//        sheet1.addCell(new Label(2,3, "${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

        def estadoCuentaList = EstadoDeCuenta.findAllByClienteAndFechaBetween(cliente,fechaInicial,fechaFinal,[sort:'id', order:'asc'])

        sheet1.addCell(new Label(0,4, "FECHA",formatoEncabezado))
        sheet1.addCell(new Label(1,4, "DETALLE",formatoEncabezado))
        sheet1.addCell(new Label(2,4, "DEBE Bs",formatoEncabezado))
        sheet1.addCell(new Label(3,4, "HABER Bs",formatoEncabezado))
        sheet1.addCell(new Label(4,4, "SALDO Bs",formatoEncabezado))

        def fila=5
        estadoCuentaList.each { estadoCuenta ->
            sheet1.addCell(new Label(0,fila, new java.text.SimpleDateFormat("dd/MM/yyyy").format(estadoCuenta.fecha),formatoDatos))
            sheet1.addCell(new Label(1,fila, estadoCuenta.detalle,formatoDatosEnteros))
            sheet1.addCell(new Number(2,fila, estadoCuenta.debe,formatoDatos))
            sheet1.addCell(new Number(3,fila, estadoCuenta.haber,formatoDatos))
            sheet1.addCell(new Number(4,fila, estadoCuenta.saldo,formatoDatos))
            fila++
        }

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="estado_cuenta_'+cliente.nombre.trim().replace(' ','_')+'.xls"')
        workbook.write()
        workbook.close()
    }
}
