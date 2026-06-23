package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import grails.plugins.jasper.JasperExportFormat
import grails.plugins.jasper.JasperReportDef
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class ReportePagoTransporteReimpresionController {
    def jasperService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reportePagoTransporteReimpresionInstanceList: ReportePagoTransporteReimpresion.list(params), reportePagoTransporteReimpresionInstanceTotal: ReportePagoTransporteReimpresion.count()]
    }

    def create() {
        [reportePagoTransporteReimpresionInstance: new ReportePagoTransporteReimpresion(params)]
    }

    def save() {
        def reportePagoTransporteReimpresionInstance = new ReportePagoTransporteReimpresion(params)
        if (!reportePagoTransporteReimpresionInstance.save(flush: true)) {
            render(view: "create", model: [reportePagoTransporteReimpresionInstance: reportePagoTransporteReimpresionInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reportePagoTransporteReimpresion.label', default: 'ReportePagoTransporteReimpresion'), reportePagoTransporteReimpresionInstance.id])
        redirect(action: "show", id: reportePagoTransporteReimpresionInstance.id)
    }

    def show(Long id) {
        def reportePagoTransporteReimpresionInstance = ReportePagoTransporteReimpresion.get(id)
        if (!reportePagoTransporteReimpresionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reportePagoTransporteReimpresion.label', default: 'ReportePagoTransporteReimpresion'), id])
            redirect(action: "list")
            return
        }

        [reportePagoTransporteReimpresionInstance: reportePagoTransporteReimpresionInstance]
    }

    def edit(Long id) {
        def reportePagoTransporteReimpresionInstance = ReportePagoTransporteReimpresion.get(id)
        if (!reportePagoTransporteReimpresionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reportePagoTransporteReimpresion.label', default: 'ReportePagoTransporteReimpresion'), id])
            redirect(action: "list")
            return
        }

        [reportePagoTransporteReimpresionInstance: reportePagoTransporteReimpresionInstance]
    }

    def update(Long id, Long version) {
        def reportePagoTransporteReimpresionInstance = ReportePagoTransporteReimpresion.get(id)
        if (!reportePagoTransporteReimpresionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reportePagoTransporteReimpresion.label', default: 'ReportePagoTransporteReimpresion'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reportePagoTransporteReimpresionInstance.version > version) {
                reportePagoTransporteReimpresionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reportePagoTransporteReimpresion.label', default: 'ReportePagoTransporteReimpresion')] as Object[],
                        "Another user has updated this ReportePagoTransporteReimpresion while you were editing")
                render(view: "edit", model: [reportePagoTransporteReimpresionInstance: reportePagoTransporteReimpresionInstance])
                return
            }
        }

        reportePagoTransporteReimpresionInstance.properties = params

        if (!reportePagoTransporteReimpresionInstance.save(flush: true)) {
            render(view: "edit", model: [reportePagoTransporteReimpresionInstance: reportePagoTransporteReimpresionInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reportePagoTransporteReimpresion.label', default: 'ReportePagoTransporteReimpresion'), reportePagoTransporteReimpresionInstance.id])
        redirect(action: "show", id: reportePagoTransporteReimpresionInstance.id)
    }

    def delete(Long id) {
        def reportePagoTransporteReimpresionInstance = ReportePagoTransporteReimpresion.get(id)
        if (!reportePagoTransporteReimpresionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reportePagoTransporteReimpresion.label', default: 'ReportePagoTransporteReimpresion'), id])
            redirect(action: "list")
            return
        }

        try {
            reportePagoTransporteReimpresionInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reportePagoTransporteReimpresion.label', default: 'ReportePagoTransporteReimpresion'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reportePagoTransporteReimpresion.label', default: 'ReportePagoTransporteReimpresion'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporte = {
        Map reportParams = [:]
        def tipoReporte = ""+params._name
        byte[] bytes

        System.err.println("*** PARAMETROS DEL REPORTE ***")
        System.err.println("RADIO SELECCIONADO: ${tipoReporte}")

        if (tipoReporte.equals("ReportePagoTransportePorFechas")){
            def elemento1 = new String(params.ELEMENTO_1)
            def elementoClass1 = new String(params.ELEMENTO_CLASS_1)
            def fechaInicialRep1 = params.FECHA_INICIAL_1
            def fechaFinalRep1 = params.FECHA_FINAL_1
            def fechaInicial1 = new Date().parse("yyyy-MM-dd",""+params.FECHA_INICIAL_1)
            def fechaFinal1 = new Date().parse("yyyy-MM-dd",""+params.FECHA_FINAL_1)
            def motivo1 = params.MOTIVO_1
            //parametros del reporte
            reportParams.put("ELEMENTO_1",elemento1)
            reportParams.put("ELEMENTO_CLASS_1",elementoClass1)
            reportParams.put("FECHA_INICIAL_1",fechaInicialRep1)
            reportParams.put("FECHA_FINAL_1",fechaFinalRep1)
            reportParams.put("MOTIVO_1",motivo1)
            def reportDef = new JasperReportDef(name:'reporte_pago_transporte_fechas_reimpresion.jasper',fileFormat:JasperExportFormat.PDF_FORMAT,parameters: reportParams)
            bytes = jasperService.generateReport(reportDef).toByteArray()

            //pagoDeTransporte elementoClass1,fechaInicial1,fechaFinal1

            System.err.println("ELEMENTO_1: ${elemento1}")
            System.err.println("ELEMENTO_CLASS_1: ${elementoClass1}")
            System.err.println("FECHA_INICIAL_1: ${fechaInicial1}")
            System.err.println("FECHA_FINAL_1: ${fechaFinal1}")

            def reimpresion = new Reimpresion(
                    fecha: new java.util.Date(),
                    nombreReporte:"REIMPRESION DE REPORTE DE PAGO DE TRANSPORTE",
                    identificadorDocumento:"N/A",
                    lote:"N/A",
                    motivo:motivo1
            )
            reimpresion.save(failOnError: true)
        }
        if (tipoReporte.equals("ReportePagoTransportePorFechasEmpresa")){
            def elemento2 = new String(params.ELEMENTO_2)
            def elementoClass2 = new String(params.ELEMENTO_CLASS_2)
            def empresaId2 = params.EMPRESA_ID_2
            def fechaInicialRep2 = params.FECHA_INICIAL_2
            def fechaFinalRep2 = params.FECHA_FINAL_2
            def fechaInicial2 = new Date().parse("yyyy-MM-dd",""+params.FECHA_INICIAL_2)
            def fechaFinal2 = new Date().parse("yyyy-MM-dd",""+params.FECHA_FINAL_2)
            def motivo2 = params.MOTIVO_2
            //parametros del reporte
            reportParams.put("ELEMENTO_2",elemento2)
            reportParams.put("ELEMENTO_CLASS_2",elementoClass2)
            reportParams.put("EMPRESA_ID_2",empresaId2)
            reportParams.put("FECHA_INICIAL_2",fechaInicialRep2)
            reportParams.put("FECHA_FINAL_2",fechaFinalRep2)
            reportParams.put("MOTIVO_2",motivo2)
            def reportDef = new JasperReportDef(name:'reporte_pago_transporte_fechas_empresa_reimpresion.jasper',fileFormat:JasperExportFormat.PDF_FORMAT,parameters: reportParams)
            bytes = jasperService.generateReport(reportDef).toByteArray()

            //pagoDeTransporte elementoClass2,fechaInicial2,fechaFinal2

            System.err.println("ELEMENTO_2: ${elemento2}")
            System.err.println("ELEMENTO_CLASS_2: ${elementoClass2}")
            System.err.println("EMPRESA_ID_2: ${empresaId2}")
            System.err.println("FECHA_INICIAL_2: ${fechaInicial2}")
            System.err.println("FECHA_FINAL_2: ${fechaFinal2}")

            def reimpresion = new Reimpresion(
                    fecha: new java.util.Date(),
                    nombreReporte:"REIMPRESION DE REPORTE DE PAGO DE TRANSPORTE",
                    identificadorDocumento:"N/A",
                    lote:"N/A",
                    motivo:motivo2
            )
            reimpresion.save(failOnError: true)
        }
        if (tipoReporte.equals("ReportePagoTransportePorLotes")){
            def elemento3 = new String(params.ELEMENTO_3)
            def elementoClass3 = new String(params.ELEMENTO_CLASS_3)
            def loteInicial3 = Integer.parseInt(params.LOTE_INICIAL_3)
            def loteFinal3 = Integer.parseInt(params.LOTE_FINAL_3)
            def motivo3 = params.MOTIVO_3

            //parametros del reporte
            reportParams.put("ELEMENTO_3",elemento3)
            reportParams.put("ELEMENTO_CLASS_3",elementoClass3)
            reportParams.put("LOTE_INICIAL_3",loteInicial3)
            reportParams.put("LOTE_FINAL_3",loteFinal3)
            reportParams.put("MOTIVO_3",motivo3)
            def reportDef = new JasperReportDef(name:'reporte_pago_transporte_lotes_reimpresion.jasper',fileFormat:JasperExportFormat.PDF_FORMAT,parameters: reportParams)
            bytes = jasperService.generateReport(reportDef).toByteArray()

            //pagoDeTransporte elementoClass3,fechaInicial3,fechaFinal3

            System.err.println("ELEMENTO_3: ${elemento3}")
            System.err.println("ELEMENTO_CLASS_3: ${elementoClass3}")
            System.err.println("LOTE_INICIAL_3: ${loteInicial3}")
            System.err.println("LOTE_FINAL_3: ${loteFinal3}")

            def reimpresion = new Reimpresion(
                    fecha: new java.util.Date(),
                    nombreReporte:"REIMPRESION DE REPORTE DE PAGO DE TRANSPORTE",
                    identificadorDocumento:"N/A",
                    lote:"N/A",
                    motivo:motivo3
            )
            reimpresion.save(failOnError: true)
        }
        if (tipoReporte.equals("ReportePagoTransportePorLotesEmpresa")){
            def elemento4 = new String(params.ELEMENTO_4)
            def elementoClass4 = new String(params.ELEMENTO_CLASS_4)
            def empresaId4 = params.EMPRESA_ID_4
            def loteInicial4 = Integer.parseInt(params.LOTE_INICIAL_4)
            def loteFinal4 = Integer.parseInt(params.LOTE_FINAL_4)
            def motivo4 = params.MOTIVO_4
            //parametros del reporte
            reportParams.put("ELEMENTO_4",elemento4)
            reportParams.put("ELEMENTO_CLASS_4",elementoClass4)
            reportParams.put("EMPRESA_ID_4",empresaId4)
            reportParams.put("LOTE_INICIAL_4",loteInicial4)
            reportParams.put("LOTE_FINAL_4",loteFinal4)
            reportParams.put("MOTIVO_4",motivo4)
            def reportDef = new JasperReportDef(name:'reporte_pago_transporte_lotes_empresa_reimpresion.jasper',fileFormat:JasperExportFormat.PDF_FORMAT,parameters: reportParams)
            bytes = jasperService.generateReport(reportDef).toByteArray()

            //pagoDeTransporte elementoClass4,fechaInicial4,fechaFinal4

            System.err.println("ELEMENTO_4: ${elemento4}")
            System.err.println("ELEMENTO_CLASS_4: ${elementoClass4}")
            System.err.println("EMPRESA_ID_4: ${empresaId4}")
            System.err.println("LOTE_INICIAL_4: ${loteInicial4}")
            System.err.println("LOTE_FINAL_4: ${loteFinal4}")

            def reimpresion = new Reimpresion(
                    fecha: new java.util.Date(),
                    nombreReporte:"REIMPRESION DE REPORTE DE PAGO DE TRANSPORTE",
                    identificadorDocumento:"N/A",
                    lote:"N/A",
                    motivo:motivo4
            )
            reimpresion.save(failOnError: true)
        }

        //ENVIAR EL REPORTE PARA DESCARGA
        response.addHeader("Content-Disposition", 'attachment; filename="reporte_transporte.pdf"')
        response.contentType = 'application/pdf'
        response.outputStream << bytes
        response.outputStream.flush()
    }
}
