package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import grails.plugins.jasper.JasperExportFormat
import grails.plugins.jasper.JasperReportDef
import org.socymet.recepcion.*
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class ReportePagoTransporteController {
    def jasperService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reportePagoTransporteInstanceList: ReportePagoTransporte.list(params), reportePagoTransporteInstanceTotal: ReportePagoTransporte.count()]
    }

    def create() {
        [reportePagoTransporteInstance: new ReportePagoTransporte(params)]
    }

    def save() {
        def reportePagoTransporteInstance = new ReportePagoTransporte(params)
        if (!reportePagoTransporteInstance.save(flush: true)) {
            render(view: "create", model: [reportePagoTransporteInstance: reportePagoTransporteInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reportePagoTransporte.label', default: 'ReportePagoTransporte'), reportePagoTransporteInstance.id])
        redirect(action: "show", id: reportePagoTransporteInstance.id)
    }

    def show(Long id) {
        def reportePagoTransporteInstance = ReportePagoTransporte.get(id)
        if (!reportePagoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reportePagoTransporte.label', default: 'ReportePagoTransporte'), id])
            redirect(action: "list")
            return
        }

        [reportePagoTransporteInstance: reportePagoTransporteInstance]
    }

    def edit(Long id) {
        def reportePagoTransporteInstance = ReportePagoTransporte.get(id)
        if (!reportePagoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reportePagoTransporte.label', default: 'ReportePagoTransporte'), id])
            redirect(action: "list")
            return
        }

        [reportePagoTransporteInstance: reportePagoTransporteInstance]
    }

    def update(Long id, Long version) {
        def reportePagoTransporteInstance = ReportePagoTransporte.get(id)
        if (!reportePagoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reportePagoTransporte.label', default: 'ReportePagoTransporte'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reportePagoTransporteInstance.version > version) {
                reportePagoTransporteInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reportePagoTransporte.label', default: 'ReportePagoTransporte')] as Object[],
                        "Another user has updated this ReportePagoTransporte while you were editing")
                render(view: "edit", model: [reportePagoTransporteInstance: reportePagoTransporteInstance])
                return
            }
        }

        reportePagoTransporteInstance.properties = params

        if (!reportePagoTransporteInstance.save(flush: true)) {
            render(view: "edit", model: [reportePagoTransporteInstance: reportePagoTransporteInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reportePagoTransporte.label', default: 'ReportePagoTransporte'), reportePagoTransporteInstance.id])
        redirect(action: "show", id: reportePagoTransporteInstance.id)
    }

    def delete(Long id) {
        def reportePagoTransporteInstance = ReportePagoTransporte.get(id)
        if (!reportePagoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reportePagoTransporte.label', default: 'ReportePagoTransporte'), id])
            redirect(action: "list")
            return
        }

        try {
            reportePagoTransporteInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reportePagoTransporte.label', default: 'ReportePagoTransporte'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reportePagoTransporte.label', default: 'ReportePagoTransporte'), id])
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
            //parametros del reporte
            reportParams.put("ELEMENTO_1",elemento1)
            reportParams.put("ELEMENTO_CLASS_1",elementoClass1)
            reportParams.put("FECHA_INICIAL_1",fechaInicialRep1)
            reportParams.put("FECHA_FINAL_1",fechaFinalRep1)
            def reportDef = new JasperReportDef(name:'reporte_pago_transporte_fechas.jasper',fileFormat:JasperExportFormat.PDF_FORMAT,parameters: reportParams)
            bytes = jasperService.generateReport(reportDef).toByteArray()

            pagoDeTransporte elementoClass1,fechaInicial1,fechaFinal1

            System.err.println("ELEMENTO_1: ${elemento1}")
            System.err.println("ELEMENTO_CLASS_1: ${elementoClass1}")
            System.err.println("FECHA_INICIAL_1: ${fechaInicial1}")
            System.err.println("FECHA_FINAL_1: ${fechaFinal1}")
        }
        if (tipoReporte.equals("ReportePagoTransportePorFechasEmpresa")){
            def elemento2 = new String(params.ELEMENTO_2)
            def elementoClass2 = new String(params.ELEMENTO_CLASS_2)
            def empresaId2 = params.EMPRESA_ID_2
            def fechaInicialRep2 = params.FECHA_INICIAL_2
            def fechaFinalRep2 = params.FECHA_FINAL_2
            def fechaInicial2 = new Date().parse("yyyy-MM-dd",""+params.FECHA_INICIAL_2)
            def fechaFinal2 = new Date().parse("yyyy-MM-dd",""+params.FECHA_FINAL_2)
            //parametros del reporte
            reportParams.put("ELEMENTO_2",elemento2)
            reportParams.put("ELEMENTO_CLASS_2",elementoClass2)
            reportParams.put("EMPRESA_ID_2",empresaId2)
            reportParams.put("FECHA_INICIAL_2",fechaInicialRep2)
            reportParams.put("FECHA_FINAL_2",fechaFinalRep2)
            def reportDef = new JasperReportDef(name:'reporte_pago_transporte_fechas_empresa.jasper',fileFormat:JasperExportFormat.PDF_FORMAT,parameters: reportParams)
            bytes = jasperService.generateReport(reportDef).toByteArray()

            pagoDeTransporte elementoClass2,fechaInicial2,fechaFinal2

            System.err.println("ELEMENTO_2: ${elemento2}")
            System.err.println("ELEMENTO_CLASS_2: ${elementoClass2}")
            System.err.println("EMPRESA_ID_2: ${empresaId2}")
            System.err.println("FECHA_INICIAL_2: ${fechaInicial2}")
            System.err.println("FECHA_FINAL_2: ${fechaFinal2}")
        }
        if (tipoReporte.equals("ReportePagoTransportePorLotes")){
            def elemento3 = new String(params.ELEMENTO_3)
            def elementoClass3 = new String(params.ELEMENTO_CLASS_3)
            def loteInicial3 = Integer.parseInt(params.LOTE_INICIAL_3)
            def loteFinal3 = Integer.parseInt(params.LOTE_FINAL_3)
            //parametros del reporte
            reportParams.put("ELEMENTO_3",elemento3)
            reportParams.put("ELEMENTO_CLASS_3",elementoClass3)
            reportParams.put("LOTE_INICIAL_3",loteInicial3)
            reportParams.put("LOTE_FINAL_3",loteFinal3)
            def reportDef = new JasperReportDef(name:'reporte_pago_transporte_lotes.jasper',fileFormat:JasperExportFormat.PDF_FORMAT,parameters: reportParams)
            bytes = jasperService.generateReport(reportDef).toByteArray()

            pagoDeTransporteLotes elementoClass3,loteInicial3,loteFinal3

            System.err.println("ELEMENTO_3: ${elemento3}")
            System.err.println("ELEMENTO_CLASS_3: ${elementoClass3}")
            System.err.println("LOTE_INICIAL_3: ${loteInicial3}")
            System.err.println("LOTE_FINAL_3: ${loteFinal3}")
        }
        if (tipoReporte.equals("ReportePagoTransportePorLotesEmpresa")){
            def elemento4 = new String(params.ELEMENTO_4)
            def elementoClass4 = new String(params.ELEMENTO_CLASS_4)
            def empresaId4 = params.EMPRESA_ID_4
            def loteInicial4 = Integer.parseInt(params.LOTE_INICIAL_4)
            def loteFinal4 = Integer.parseInt(params.LOTE_FINAL_4)
            //parametros del reporte
            reportParams.put("ELEMENTO_4",elemento4)
            reportParams.put("ELEMENTO_CLASS_4",elementoClass4)
            reportParams.put("EMPRESA_ID_4",empresaId4)
            reportParams.put("LOTE_INICIAL_4",loteInicial4)
            reportParams.put("LOTE_FINAL_4",loteFinal4)
            def reportDef = new JasperReportDef(name:'reporte_pago_transporte_lotes_empresa.jasper',fileFormat:JasperExportFormat.PDF_FORMAT,parameters: reportParams)
            bytes = jasperService.generateReport(reportDef).toByteArray()

            pagoDeTransporteLotes elementoClass4,loteInicial4,loteFinal4

            System.err.println("ELEMENTO_4: ${elemento4}")
            System.err.println("ELEMENTO_CLASS_4: ${elementoClass4}")
            System.err.println("EMPRESA_ID_4: ${empresaId4}")
            System.err.println("LOTE_INICIAL_4: ${loteInicial4}")
            System.err.println("LOTE_FINAL_4: ${loteFinal4}")
        }

        //ENVIAR EL REPORTE PARA DESCARGA
        response.addHeader("Content-Disposition", 'attachment; filename="reporte_transporte.pdf"')
        response.contentType = 'application/pdf'
        response.outputStream << bytes
        response.outputStream.flush()
    }

    def pagoDeTransporte = { elemento,fechaInicial,fechaFinal ->
        def recepciones

        if (elemento.equals("org.socymet.recepcion.RecepcionDeAntimonio")){
            recepciones = RecepcionDeAntimonio.findAllByTransportePagadoAndFechaDeRecepcionBetween("NO",fechaInicial,fechaFinal)
        }
        if (elemento.equals("org.socymet.recepcion.RecepcionDeComplejo")){
            recepciones = RecepcionDeComplejo.findAllByTransportePagadoAndFechaDeRecepcionBetween("NO",fechaInicial,fechaFinal)
        }
        if (elemento.equals("org.socymet.recepcion.RecepcionDeEstano")){
            recepciones = RecepcionDeEstano.findAllByTransportePagadoAndFechaDeRecepcionBetween("NO",fechaInicial,fechaFinal)
        }
        if (elemento.equals("org.socymet.recepcion.RecepcionDePlata")){
            recepciones = RecepcionDePlata.findAllByTransportePagadoAndFechaDeRecepcionBetween("NO",fechaInicial,fechaFinal)
        }
        if (elemento.equals("org.socymet.recepcion.RecepcionDePlomoPlata")){
            recepciones = RecepcionDePlomoPlata.findAllByTransportePagadoAndFechaDeRecepcionBetween("NO",fechaInicial,fechaFinal)
        }
        if (elemento.equals("org.socymet.recepcion.RecepcionDeWolfran")){
            recepciones = RecepcionDeWolfran.findAllByTransportePagadoAndFechaDeRecepcionBetween("NO",fechaInicial,fechaFinal)
        }
        if (elemento.equals("org.socymet.recepcion.RecepcionDeZincPlata")){
            recepciones = RecepcionDeZincPlata.findAllByTransportePagadoAndFechaDeRecepcionBetween("NO",fechaInicial,fechaFinal)
        }

        if (recepciones.size()>0)
            pagar recepciones
    }

    def pagoDeTransporteLotes = { elemento, loteInicial, loteFinal ->
        def recepciones

        if (elemento.equals("org.socymet.recepcion.RecepcionDeAntimonio")){
            recepciones = RecepcionDeAntimonio.findAllByTransportePagadoAndLoteAntimonioBetween("NO",loteInicial,loteFinal)
        }
        if (elemento.equals("org.socymet.recepcion.RecepcionDeComplejo")){
            recepciones = RecepcionDeComplejo.findAllByTransportePagadoAndLoteComplejoBetween("NO",loteInicial,loteFinal)
        }
        if (elemento.equals("org.socymet.recepcion.RecepcionDeEstano")){
            recepciones = RecepcionDeEstano.findAllByTransportePagadoAndLoteEstanoBetween("NO",loteInicial,loteFinal)
        }
        if (elemento.equals("org.socymet.recepcion.RecepcionDePlata")){
            recepciones = RecepcionDePlata.findAllByTransportePagadoAndLotePlataBetween("NO",loteInicial,loteFinal)
        }
        if (elemento.equals("org.socymet.recepcion.RecepcionDePlomoPlata")){
            recepciones = RecepcionDePlomoPlata.findAllByTransportePagadoAndLotePlomoPlataBetween("NO",loteInicial,loteFinal)
        }
        if (elemento.equals("org.socymet.recepcion.RecepcionDeWolfran")){
            recepciones = RecepcionDeWolfran.findAllByTransportePagadoAndLoteWolfranBetween("NO",loteInicial,loteFinal)
        }
        if (elemento.equals("org.socymet.recepcion.RecepcionDeZincPlata")){
            recepciones = RecepcionDeZincPlata.findAllByTransportePagadoAndLoteZincPlataBetween("NO",loteInicial,loteFinal)
        }

        if (recepciones.size()>0)
            pagar recepciones
    }

    def pagar = { lista ->
        lista.each {
            System.err.println("PAGANDO: ${it.id}")
            it.transportePagado="SI"
            it.save(failOnError: true)
        }
    }
}

