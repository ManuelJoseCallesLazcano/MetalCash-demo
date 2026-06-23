package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import jxl.SheetSettings
import jxl.Workbook
import jxl.format.Alignment
import jxl.format.PageOrientation
import jxl.format.PaperSize
import jxl.write.*
import org.socymet.liquidacion.LiquidacionDeCobrePlata
import org.socymet.liquidacion.LiquidacionDeComplejo
import org.socymet.liquidacion.LiquidacionDePlomoPlata
import org.socymet.liquidacion.LiquidacionDeZincPlata
import org.socymet.proveedor.Cliente
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class ReporteProduccionPorClienteController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reporteProduccionPorClienteInstanceList: ReporteProduccionPorCliente.list(params), reporteProduccionPorClienteInstanceTotal: ReporteProduccionPorCliente.count()]
    }

    def create() {
        [reporteProduccionPorClienteInstance: new ReporteProduccionPorCliente(params)]
    }

    def save() {
        def reporteProduccionPorClienteInstance = new ReporteProduccionPorCliente(params)
        if (!reporteProduccionPorClienteInstance.save(flush: true)) {
            render(view: "create", model: [reporteProduccionPorClienteInstance: reporteProduccionPorClienteInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reporteProduccionPorCliente.label', default: 'ReporteProduccionPorCliente'), reporteProduccionPorClienteInstance.id])
        redirect(action: "show", id: reporteProduccionPorClienteInstance.id)
    }

    def show(Long id) {
        def reporteProduccionPorClienteInstance = ReporteProduccionPorCliente.get(id)
        if (!reporteProduccionPorClienteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteProduccionPorCliente.label', default: 'ReporteProduccionPorCliente'), id])
            redirect(action: "list")
            return
        }

        [reporteProduccionPorClienteInstance: reporteProduccionPorClienteInstance]
    }

    def edit(Long id) {
        def reporteProduccionPorClienteInstance = ReporteProduccionPorCliente.get(id)
        if (!reporteProduccionPorClienteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteProduccionPorCliente.label', default: 'ReporteProduccionPorCliente'), id])
            redirect(action: "list")
            return
        }

        [reporteProduccionPorClienteInstance: reporteProduccionPorClienteInstance]
    }

    def update(Long id, Long version) {
        def reporteProduccionPorClienteInstance = ReporteProduccionPorCliente.get(id)
        if (!reporteProduccionPorClienteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteProduccionPorCliente.label', default: 'ReporteProduccionPorCliente'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reporteProduccionPorClienteInstance.version > version) {
                reporteProduccionPorClienteInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reporteProduccionPorCliente.label', default: 'ReporteProduccionPorCliente')] as Object[],
                        "Another user has updated this ReporteProduccionPorCliente while you were editing")
                render(view: "edit", model: [reporteProduccionPorClienteInstance: reporteProduccionPorClienteInstance])
                return
            }
        }

        reporteProduccionPorClienteInstance.properties = params

        if (!reporteProduccionPorClienteInstance.save(flush: true)) {
            render(view: "edit", model: [reporteProduccionPorClienteInstance: reporteProduccionPorClienteInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reporteProduccionPorCliente.label', default: 'ReporteProduccionPorCliente'), reporteProduccionPorClienteInstance.id])
        redirect(action: "show", id: reporteProduccionPorClienteInstance.id)
    }

    def delete(Long id) {
        def reporteProduccionPorClienteInstance = ReporteProduccionPorCliente.get(id)
        if (!reporteProduccionPorClienteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteProduccionPorCliente.label', default: 'ReporteProduccionPorCliente'), id])
            redirect(action: "list")
            return
        }

        try {
            reporteProduccionPorClienteInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reporteProduccionPorCliente.label', default: 'ReporteProduccionPorCliente'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reporteProduccionPorCliente.label', default: 'ReporteProduccionPorCliente'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporte = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.ARIAL, 7, WritableFont.NO_BOLD);
        WritableFont courier6PlainFont = new WritableFont(WritableFont.ARIAL, 7, WritableFont.NO_BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.ARIAL, 7, WritableFont.NO_BOLD);
        WritableFont courier8BoldFont = new WritableFont(WritableFont.ARIAL, 7, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);

        WritableCellFormat formatoEncabezado = new WritableCellFormat (arial10BoldFont);
        formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)
        formatoEncabezado.setWrap(true)
        //formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
        formatoEncabezado.setAlignment(Alignment.CENTRE)

        WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoDatos.setFont(courier8PlainFont)
        formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        WritableCellFormat formatoDatosHumedad = new WritableCellFormat (new NumberFormat("###,##0.000000"));
        formatoDatosHumedad.setFont(courier8PlainFont)
        formatoDatosHumedad.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        WritableCellFormat formatoTotales = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoTotales.setFont(courier8BoldFont)
        formatoTotales.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)

        WritableCellFormat formatoTotalesHumedad = new WritableCellFormat (new NumberFormat("###,##0.000000"));
        formatoTotalesHumedad.setFont(courier8BoldFont)
        formatoTotalesHumedad.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)

        WritableCellFormat formatoInfoReporte = new WritableCellFormat (arial14BoldFont);
        formatoInfoReporte.setAlignment(Alignment.CENTRE)

        WritableCellFormat formatoTitulo = new WritableCellFormat (arial16BoldFont);
        formatoTitulo.setAlignment(Alignment.CENTRE)

        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        WritableSheet sheet1 = workbook.createSheet("Reporte de Compra Diaria", 0)

        SheetSettings settings = sheet1.getSettings()
        settings.setScaleFactor(70)
        settings.setPaperSize(PaperSize.LETTER)
        settings.setOrientation(PageOrientation.LANDSCAPE)
        settings.setTopMargin(0.2)
        settings.setBottomMargin(0.4)
        settings.setLeftMargin(0.6)
        settings.setRightMargin(0.4)
        settings.setHeaderMargin(0)
        settings.setFooterMargin(0)
        for(i in 0..100)
            sheet1.setColumnView(i,11)
        sheet1.setColumnView(0,30)

        def empresa = Empresa.get(Integer.parseInt(""+params.empresa.id))
        def fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
        def fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)
        def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
        def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="produccion_clientes_'+empresa.nombreDeEmpresa.toLowerCase().replace(' ','_')+'.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(3,1, "PRODUCCION GENERAL DE CLIENTES DE ${empresa.toString().toUpperCase()}",formatoTitulo))
        sheet1.addCell(new Label(3,3, "CORRESPONDIENTE A LAS FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))
        sheet1.addCell(new Label(0,6, "NOMBRE",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "K. N. S.",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "LEY %Zn",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "LEY %Pb",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "LEY DM Ag",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "LEY %Cu",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "LIQUIDO PAGABLE",formatoEncabezado))

        def clientes = Cliente.findAllByEmpresa(empresa,[sort:'nombre'])
        def totalSacos = 0
        def totalPesoBruto = 0
        def totalKilosNetosSecos = 0
        def totalPorcentajeZinc = 0
        def totalPorcentajePlomo = 0
        def totalPorcentajePlata = 0
        def totalPorcentajeCobre = 0
        def totalKilosFinosZinc = 0
        def totalKilosFinosPlomo = 0
        def totalKilosFinosPlata = 0
        def totalKilosFinosCobre = 0
        def totalLiquidoPagable = 0

        def fila = 7

        clientes.each { cliente ->
            log.error("cliente: ${cliente}")

            def recepciones = RecepcionDeComplejo.findAllByClienteAndEstadoDelLoteAndFechaDeRecepcionBetween(cliente,"LIQUIDADO",fechaInicial,fechaFinal)

            log.error("# recepciones: ${recepciones.size()}")

            if (recepciones){
                recepciones.each { recepcion ->
                    log.error("***** lote: ${recepcion}")
                    if (recepcion.tipoDeMineral.equals("PB-AG")){
                        def liquidacion = LiquidacionDePlomoPlata.findByRecepcionDeComplejo(recepcion)
                        totalSacos+=liquidacion.cantidadDeSacos.toBigDecimal()
                        totalPesoBruto+=liquidacion.pesoBruto
                        totalKilosNetosSecos+=liquidacion.kilosNetosSecos
                        totalPorcentajePlomo+=liquidacion.porcentajePlomoFinal
                        totalPorcentajePlata+=liquidacion.porcentajePlataFinal
                        totalKilosFinosPlomo+=liquidacion.kilosFinosPlomo
                        totalKilosFinosPlata+=liquidacion.kilosFinosPlata
                        totalLiquidoPagable+=liquidacion.totalLiquidoPagable
                    }
                    if (recepcion.tipoDeMineral.equals("ZN-AG")){
                        def liquidacion = LiquidacionDeZincPlata.findByRecepcionDeComplejo(recepcion)
                        totalSacos+=liquidacion.cantidadDeSacos.toBigDecimal()
                        totalPesoBruto+=liquidacion.pesoBruto
                        totalKilosNetosSecos+=liquidacion.kilosNetosSecos
                        totalPorcentajeZinc+=liquidacion.porcentajeZincFinal
                        totalPorcentajePlata+=liquidacion.porcentajePlataFinal
                        totalKilosFinosZinc+=liquidacion.kilosFinosZinc
                        totalKilosFinosPlata+=liquidacion.kilosFinosPlata
                        totalLiquidoPagable+=liquidacion.totalLiquidoPagable
                    }
                    if (recepcion.tipoDeMineral.equals("COMPLEJO")){
                        def liquidacion = LiquidacionDeComplejo.findByRecepcionDeComplejo(recepcion)
                        totalSacos+=liquidacion.cantidadDeSacos.toBigDecimal()
                        totalPesoBruto+=liquidacion.pesoBruto
                        totalKilosNetosSecos+=liquidacion.kilosNetosSecos
                        totalPorcentajeZinc+=liquidacion.porcentajeZincFinal
                        totalPorcentajePlomo+=liquidacion.porcentajePlomoFinal
                        totalPorcentajePlata+=liquidacion.porcentajePlataFinal
                        totalKilosFinosZinc+=liquidacion.kilosFinosZinc
                        totalKilosFinosPlomo+=liquidacion.kilosFinosPlomo
                        totalKilosFinosPlata+=liquidacion.kilosFinosPlata
                        totalLiquidoPagable+=liquidacion.totalLiquidoPagable
                    }
                    if (recepcion.tipoDeMineral.equals("CU-AG")){
                        def liquidacion = LiquidacionDeCobrePlata.findByRecepcionDeComplejo(recepcion)
                        totalSacos+=liquidacion.cantidadDeSacos.toBigDecimal()
                        totalPesoBruto+=liquidacion.pesoBruto
                        totalKilosNetosSecos+=liquidacion.kilosNetosSecos
                        totalPorcentajeCobre+=liquidacion.porcentajeCobreFinal
                        totalPorcentajePlata+=liquidacion.porcentajePlataFinal
                        totalKilosFinosCobre+=liquidacion.kilosFinosCobre
                        totalKilosFinosPlata+=liquidacion.kilosFinosPlata
                        totalLiquidoPagable+=liquidacion.totalLiquidoPagable
                    }
                }

                sheet1.addCell(new Label(0,fila, cliente.nombre,formatoDatos))
                sheet1.addCell(new Number(1,fila, totalSacos,formatoDatos)) //SN
                sheet1.addCell(new Number(2,fila, totalPesoBruto,formatoDatos)) //SB
                sheet1.addCell(new Number(3,fila, totalKilosNetosSecos,formatoDatos)) //WO3
                sheet1.addCell(new Number(4,fila, 100*totalKilosFinosZinc/totalKilosNetosSecos,formatoDatos)) //WO3
                sheet1.addCell(new Number(5,fila, 100*totalKilosFinosPlomo/totalKilosNetosSecos,formatoDatos)) //WO3
                sheet1.addCell(new Number(6,fila, 10000*totalKilosFinosPlata/totalKilosNetosSecos,formatoDatos)) //WO3
                sheet1.addCell(new Number(7,fila, 100*totalPorcentajeCobre/totalKilosNetosSecos,formatoDatos)) //WO3
                sheet1.addCell(new Number(8,fila, totalLiquidoPagable,formatoDatos)) //WO3

                totalSacos = 0
                totalPesoBruto = 0
                totalKilosNetosSecos = 0
                totalPorcentajeZinc = 0
                totalPorcentajePlomo = 0
                totalPorcentajePlata = 0
                totalPorcentajeCobre = 0
                totalKilosFinosZinc = 0
                totalKilosFinosPlomo = 0
                totalKilosFinosPlata = 0
                totalKilosFinosCobre = 0
                totalLiquidoPagable = 0
                fila++
            }
        }

        workbook.write();
        workbook.close();
    }
}
