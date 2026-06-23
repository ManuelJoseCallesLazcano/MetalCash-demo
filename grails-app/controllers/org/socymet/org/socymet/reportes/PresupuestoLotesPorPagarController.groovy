package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import jxl.Workbook
import jxl.write.*
import org.grails.web.json.JSONArray
import org.socymet.cotizaciones.*
import org.springframework.dao.DataIntegrityViolationException

//librerias para generar documentos XLS
@Transactional
class PresupuestoLotesPorPagarController {
    def jasperService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [presupuestoLotesPorPagarInstanceList: PresupuestoLotesPorPagar.list(params), presupuestoLotesPorPagarInstanceTotal: PresupuestoLotesPorPagar.count()]
    }

    def create() {
        [presupuestoLotesPorPagarInstance: new PresupuestoLotesPorPagar(params)]
    }

    def save() {
        def presupuestoLotesPorPagarInstance = new PresupuestoLotesPorPagar(params)
        if (!presupuestoLotesPorPagarInstance.save(flush: true)) {
            render(view: "create", model: [presupuestoLotesPorPagarInstance: presupuestoLotesPorPagarInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'presupuestoLotesPorPagar.label', default: 'PresupuestoLotesPorPagar'), presupuestoLotesPorPagarInstance.id])
        redirect(action: "show", id: presupuestoLotesPorPagarInstance.id)
    }

    def show(Long id) {
        def presupuestoLotesPorPagarInstance = PresupuestoLotesPorPagar.get(id)
        if (!presupuestoLotesPorPagarInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'presupuestoLotesPorPagar.label', default: 'PresupuestoLotesPorPagar'), id])
            redirect(action: "list")
            return
        }

        [presupuestoLotesPorPagarInstance: presupuestoLotesPorPagarInstance]
    }

    def edit(Long id) {
        def presupuestoLotesPorPagarInstance = PresupuestoLotesPorPagar.get(id)
        if (!presupuestoLotesPorPagarInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'presupuestoLotesPorPagar.label', default: 'PresupuestoLotesPorPagar'), id])
            redirect(action: "list")
            return
        }

        [presupuestoLotesPorPagarInstance: presupuestoLotesPorPagarInstance]
    }

    def update(Long id, Long version) {
        def presupuestoLotesPorPagarInstance = PresupuestoLotesPorPagar.get(id)
        if (!presupuestoLotesPorPagarInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'presupuestoLotesPorPagar.label', default: 'PresupuestoLotesPorPagar'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (presupuestoLotesPorPagarInstance.version > version) {
                presupuestoLotesPorPagarInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'presupuestoLotesPorPagar.label', default: 'PresupuestoLotesPorPagar')] as Object[],
                        "Another user has updated this PresupuestoLotesPorPagar while you were editing")
                render(view: "edit", model: [presupuestoLotesPorPagarInstance: presupuestoLotesPorPagarInstance])
                return
            }
        }

        presupuestoLotesPorPagarInstance.properties = params

        if (!presupuestoLotesPorPagarInstance.save(flush: true)) {
            render(view: "edit", model: [presupuestoLotesPorPagarInstance: presupuestoLotesPorPagarInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'presupuestoLotesPorPagar.label', default: 'PresupuestoLotesPorPagar'), presupuestoLotesPorPagarInstance.id])
        redirect(action: "show", id: presupuestoLotesPorPagarInstance.id)
    }

    def delete(Long id) {
        def presupuestoLotesPorPagarInstance = PresupuestoLotesPorPagar.get(id)
        if (!presupuestoLotesPorPagarInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'presupuestoLotesPorPagar.label', default: 'PresupuestoLotesPorPagar'), id])
            redirect(action: "list")
            return
        }

        try {
            presupuestoLotesPorPagarInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'presupuestoLotesPorPagar.label', default: 'PresupuestoLotesPorPagar'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'presupuestoLotesPorPagar.label', default: 'PresupuestoLotesPorPagar'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporteEstano = {
        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="presupuesto_estano.xls"')
        def cotizacionDolar = CotizacionDeDolar.findByActivo(1)
        def resultadoEstano = ""+params.resultadoEstano
        def tablaCotizacionEstanoId = ""+params.tablaCotizacionEstanoId
        def resultadoEstanoJSON = new JSONArray(resultadoEstano)
        def pc = new TablaCotizacionEstanoController()
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Presupuesto de Estano", 0)
        sheet1.setColumnView(0,10)
        sheet1.setColumnView(2,40)
        sheet1.setColumnView(3,40)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
        WritableCellFormat arial10Boldformat = new WritableCellFormat (arial10BoldFont);
        WritableCellFormat arial14Boldformat = new WritableCellFormat (arial14BoldFont);
        sheet1.addCell(new Label(0,0, "PRESUPUESTO DE ESTAÑO",arial14Boldformat))
        sheet1.addCell(new Label(0,2, "FECHA",arial10Boldformat))
        sheet1.addCell(new Label(1,2, "LOTE",arial10Boldformat))
        sheet1.addCell(new Label(2,2, "PROCEDENCIA",arial10Boldformat))
        sheet1.addCell(new Label(3,2, "PROVEEDOR",arial10Boldformat))
        sheet1.addCell(new Label(4,2, "SACOS",arial10Boldformat))
        sheet1.addCell(new Label(5,2, "KB",arial10Boldformat))
        sheet1.addCell(new Label(6,2, "COT. DIA",arial10Boldformat))
        sheet1.addCell(new Label(7,2, "% H2O",arial10Boldformat))
        sheet1.addCell(new Label(8,2, "KNS",arial10Boldformat))
        sheet1.addCell(new Label(9,2, "% Sn",arial10Boldformat))
        sheet1.addCell(new Label(10,2, "VALOR",arial10Boldformat))
        def i=3
        def registrosValidos=0
        def sumaPesoBruto=0
        def sumaHumedad=0
        def sumaKilosNetosSecos=0
        def sumaPorcentajeEstano=0
        def sumaValor=0
        resultadoEstanoJSON.each {
            def porcentajeEstano = Float.parseFloat(it.porcentajeEstano.toString())
            if(porcentajeEstano!=0){
                registrosValidos++
                def cotizacionEstano =  Float.parseFloat(it.cotizacionEstano.toString())
                def kilosNetosHumedos = Float.parseFloat(it.kilosNetosHumedos.toString())
                def humedad = Float.parseFloat(it.humedad.toString())
                def vpt = pc.getValorPorToneladaPresupuesto(Integer.parseInt(tablaCotizacionEstanoId),cotizacionEstano,porcentajeEstano)
                def kilosNetosSecos = kilosNetosHumedos-kilosNetosHumedos*humedad/100
                def valor = cotizacionDolar.tipoDeCambioComercial*kilosNetosSecos*vpt/1000

                sumaPesoBruto+=kilosNetosHumedos
                sumaHumedad+=humedad
                sumaKilosNetosSecos+=kilosNetosSecos
                sumaPorcentajeEstano+=porcentajeEstano
                sumaValor+=valor
                //System.out.println(it.toString())
                sheet1.addCell(new Label(0,i,""+it.fechaDeRecepcion))
                sheet1.addCell(new Number(1,i,it.lote))
                sheet1.addCell(new Label(2,i,it.nombreEmpresa))
                sheet1.addCell(new Label(3,i,it.nombreCliente))
                sheet1.addCell(new Label(4,i,it.cantidadDeSacos))
                sheet1.addCell(new Number(5,i,kilosNetosHumedos))
                sheet1.addCell(new Number(6,i,it.cotizacionEstano))
                sheet1.addCell(new Number(7,i,humedad))
                sheet1.addCell(new Number(8,i,kilosNetosSecos))
                sheet1.addCell(new Number(9,i,porcentajeEstano))
                sheet1.addCell(new Number(10,i,valor))
                i++
            }
        }
        if (registrosValidos>0){
            sheet1.addCell(new Label(0,i,"TOTALES Y PROMEDIOS",arial10Boldformat))
            sheet1.addCell(new Number(5,i,sumaPesoBruto,arial10Boldformat))
            sheet1.addCell(new Number(7,i,sumaHumedad/registrosValidos,arial10Boldformat))
            sheet1.addCell(new Number(8,i,sumaKilosNetosSecos,arial10Boldformat))
            sheet1.addCell(new Number(9,i,sumaPorcentajeEstano/registrosValidos,arial10Boldformat))
            sheet1.addCell(new Number(10,i,sumaValor,arial10Boldformat))
        }

        workbook.write();
        workbook.close();
    }

    def crearReportePlata = {
        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="presupuesto_plata.xls"')
        def cotizacionDolar = CotizacionDeDolar.findByActivo(1)
        def resultadoPlata = ""+params.resultadoPlata
        def tablaCotizacionPlataId = ""+params.tablaCotizacionPlataId
        def resultadoPlataJSON = new JSONArray(resultadoPlata)
        def pc = new TablaCotizacionPlataController()
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Presupuesto de Plata", 0)
        sheet1.setColumnView(0,10)
        sheet1.setColumnView(2,40)
        sheet1.setColumnView(3,40)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
        WritableCellFormat arial10Boldformat = new WritableCellFormat (arial10BoldFont);
        WritableCellFormat arial14Boldformat = new WritableCellFormat (arial14BoldFont);
        sheet1.addCell(new Label(0,0, "PRESUPUESTO DE PLATA",arial14Boldformat))
        sheet1.addCell(new Label(0,2, "FECHA",arial10Boldformat))
        sheet1.addCell(new Label(1,2, "LOTE",arial10Boldformat))
        sheet1.addCell(new Label(2,2, "PROCEDENCIA",arial10Boldformat))
        sheet1.addCell(new Label(3,2, "PROVEEDOR",arial10Boldformat))
        sheet1.addCell(new Label(4,2, "SACOS",arial10Boldformat))
        sheet1.addCell(new Label(5,2, "KB",arial10Boldformat))
        sheet1.addCell(new Label(6,2, "COT. DIA",arial10Boldformat))
        sheet1.addCell(new Label(7,2, "% H2O",arial10Boldformat))
        sheet1.addCell(new Label(8,2, "KNS",arial10Boldformat))
        sheet1.addCell(new Label(9,2, "DM Ag",arial10Boldformat))
        sheet1.addCell(new Label(10,2, "VALOR",arial10Boldformat))
        def i=3
        def registrosValidos=0
        def sumaPesoBruto=0
        def sumaHumedad=0
        def sumaKilosNetosSecos=0
        def sumaPorcentajePlata=0
        def sumaValor=0
        resultadoPlataJSON.each {
            System.out.println(it.toString())
            def porcentajePlata = Float.parseFloat(it.porcentajePlata.toString())
            if(porcentajePlata!=0){
                registrosValidos++
                def cotizacionPlata =  Float.parseFloat(it.cotizacionPlata.toString())
                def kilosNetosHumedos = Float.parseFloat(it.kilosNetosHumedos.toString())
                def humedad = Float.parseFloat(it.humedad.toString())
                def vpt = pc.getValorPorToneladaPresupuesto(Integer.parseInt(tablaCotizacionPlataId),cotizacionPlata,porcentajePlata)
                def kilosNetosSecos = kilosNetosHumedos-kilosNetosHumedos*humedad/100
                def valor = cotizacionDolar.tipoDeCambioComercial*kilosNetosSecos*vpt/1000

                sumaPesoBruto+=kilosNetosHumedos
                sumaHumedad+=humedad
                sumaKilosNetosSecos+=kilosNetosSecos
                sumaPorcentajePlata+=porcentajePlata
                sumaValor+=valor
                //System.out.println(it.toString())
                sheet1.addCell(new Label(0,i,""+it.fechaDeRecepcion))
                sheet1.addCell(new Number(1,i,it.lote))
                sheet1.addCell(new Label(2,i,it.nombreEmpresa))
                sheet1.addCell(new Label(3,i,it.nombreCliente))
                sheet1.addCell(new Label(4,i,it.cantidadDeSacos))
                sheet1.addCell(new Number(5,i,kilosNetosHumedos))
                sheet1.addCell(new Number(6,i,it.cotizacionPlata))
                sheet1.addCell(new Number(7,i,humedad))
                sheet1.addCell(new Number(8,i,kilosNetosSecos))
                sheet1.addCell(new Number(9,i,porcentajePlata))
                sheet1.addCell(new Number(10,i,valor))
                i++
            }
        }
        if (registrosValidos>0){
            sheet1.addCell(new Label(0,i,"TOTALES Y PROMEDIOS",arial10Boldformat))
            sheet1.addCell(new Number(5,i,sumaPesoBruto,arial10Boldformat))
            sheet1.addCell(new Number(7,i,sumaHumedad/registrosValidos,arial10Boldformat))
            sheet1.addCell(new Number(8,i,sumaKilosNetosSecos,arial10Boldformat))
            sheet1.addCell(new Number(9,i,sumaPorcentajePlata/registrosValidos,arial10Boldformat))
            sheet1.addCell(new Number(10,i,sumaValor,arial10Boldformat))
        }

        workbook.write();
        workbook.close();
    }

    def crearReporteAntimonio = {
        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="presupuesto_antimonio.xls"')
        def cotizacionDolar = CotizacionDeDolar.findByActivo(1)
        def resultadoAntimonio = ""+params.resultadoAntimonio
        def tablaCotizacionAntimonioId = ""+params.tablaCotizacionAntimonioId
        def resultadoAntimonioJSON = new JSONArray(resultadoAntimonio)
        def pc = new TablaCotizacionAntimonioController()
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Presupuesto de Antimonio", 0)
        sheet1.setColumnView(0,10)
        sheet1.setColumnView(2,40)
        sheet1.setColumnView(3,40)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
        WritableCellFormat arial10Boldformat = new WritableCellFormat (arial10BoldFont);
        WritableCellFormat arial14Boldformat = new WritableCellFormat (arial14BoldFont);
        sheet1.addCell(new Label(0,0, "PRESUPUESTO DE ANTIMONIO",arial14Boldformat))
        sheet1.addCell(new Label(0,2, "FECHA",arial10Boldformat))
        sheet1.addCell(new Label(1,2, "LOTE",arial10Boldformat))
        sheet1.addCell(new Label(2,2, "PROCEDENCIA",arial10Boldformat))
        sheet1.addCell(new Label(3,2, "PROVEEDOR",arial10Boldformat))
        sheet1.addCell(new Label(4,2, "SACOS",arial10Boldformat))
        sheet1.addCell(new Label(5,2, "KB",arial10Boldformat))
        sheet1.addCell(new Label(6,2, "COT. DIA",arial10Boldformat))
        sheet1.addCell(new Label(7,2, "% H2O",arial10Boldformat))
        sheet1.addCell(new Label(8,2, "KNS",arial10Boldformat))
        sheet1.addCell(new Label(9,2, "% Sb",arial10Boldformat))
        sheet1.addCell(new Label(10,2, "VALOR",arial10Boldformat))
        def i=3
        def registrosValidos=0
        def sumaPesoBruto=0
        def sumaHumedad=0
        def sumaKilosNetosSecos=0
        def sumaPorcentajeAntimonio=0
        def sumaValor=0
        resultadoAntimonioJSON.each {
            System.out.println(it.toString())
            def porcentajeAntimonio = Float.parseFloat(it.porcentajeAntimonio.toString())
            if(porcentajeAntimonio!=0){
                registrosValidos++
                def cotizacionAntimonio =  Float.parseFloat(it.cotizacionAntimonio.toString())
                def kilosNetosHumedos = Float.parseFloat(it.kilosNetosHumedos.toString())
                def humedad = Float.parseFloat(it.humedad.toString())
                def vpt = pc.getValorPorToneladaPresupuesto(Integer.parseInt(tablaCotizacionAntimonioId),porcentajeAntimonio)
                def kilosNetosSecos = kilosNetosHumedos-kilosNetosHumedos*humedad/100
                def valor = cotizacionDolar.tipoDeCambioComercial*kilosNetosSecos*vpt/1000

                sumaPesoBruto+=kilosNetosHumedos
                sumaHumedad+=humedad
                sumaKilosNetosSecos+=kilosNetosSecos
                sumaPorcentajeAntimonio+=porcentajeAntimonio
                sumaValor+=valor
                //System.out.println(it.toString())
                sheet1.addCell(new Label(0,i,""+it.fechaDeRecepcion))
                sheet1.addCell(new Number(1,i,it.lote))
                sheet1.addCell(new Label(2,i,it.nombreEmpresa))
                sheet1.addCell(new Label(3,i,it.nombreCliente))
                sheet1.addCell(new Label(4,i,it.cantidadDeSacos))
                sheet1.addCell(new Number(5,i,kilosNetosHumedos))
                sheet1.addCell(new Number(6,i,it.cotizacionAntimonio))
                sheet1.addCell(new Number(7,i,humedad))
                sheet1.addCell(new Number(8,i,kilosNetosSecos))
                sheet1.addCell(new Number(9,i,porcentajeAntimonio))
                sheet1.addCell(new Number(10,i,valor))
                i++
            }
        }
        if (registrosValidos>0){
            sheet1.addCell(new Label(0,i,"TOTALES Y PROMEDIOS",arial10Boldformat))
            sheet1.addCell(new Number(5,i,sumaPesoBruto,arial10Boldformat))
            sheet1.addCell(new Number(7,i,sumaHumedad/registrosValidos,arial10Boldformat))
            sheet1.addCell(new Number(8,i,sumaKilosNetosSecos,arial10Boldformat))
            sheet1.addCell(new Number(9,i,sumaPorcentajeAntimonio/registrosValidos,arial10Boldformat))
            sheet1.addCell(new Number(10,i,sumaValor,arial10Boldformat))
        }

        workbook.write();
        workbook.close();
    }

    def crearReporteWolfran = {
        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="presupuesto_antimonio.xls"')
        def cotizacionDolar = CotizacionDeDolar.findByActivo(1)
        def resultadoWolfran = ""+params.resultadoWolfran
        def tablaCotizacionWolfranId = ""+params.tablaCotizacionWolfranId
        def resultadoWolfranJSON = new JSONArray(resultadoWolfran)
        def pc = new TablaCotizacionWolfranController()
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Presupuesto de Wolfran", 0)
        sheet1.setColumnView(0,10)
        sheet1.setColumnView(2,40)
        sheet1.setColumnView(3,40)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
        WritableCellFormat arial10Boldformat = new WritableCellFormat (arial10BoldFont);
        WritableCellFormat arial14Boldformat = new WritableCellFormat (arial14BoldFont);
        sheet1.addCell(new Label(0,0, "PRESUPUESTO DE ANTIMONIO",arial14Boldformat))
        sheet1.addCell(new Label(0,2, "FECHA",arial10Boldformat))
        sheet1.addCell(new Label(1,2, "LOTE",arial10Boldformat))
        sheet1.addCell(new Label(2,2, "PROCEDENCIA",arial10Boldformat))
        sheet1.addCell(new Label(3,2, "PROVEEDOR",arial10Boldformat))
        sheet1.addCell(new Label(4,2, "SACOS",arial10Boldformat))
        sheet1.addCell(new Label(5,2, "KB",arial10Boldformat))
        sheet1.addCell(new Label(6,2, "COT. DIA",arial10Boldformat))
        sheet1.addCell(new Label(7,2, "% H2O",arial10Boldformat))
        sheet1.addCell(new Label(8,2, "KNS",arial10Boldformat))
        sheet1.addCell(new Label(9,2, "% WO3",arial10Boldformat))
        sheet1.addCell(new Label(10,2, "VALOR",arial10Boldformat))
        def i=3
        def registrosValidos=0
        def sumaPesoBruto=0
        def sumaHumedad=0
        def sumaKilosNetosSecos=0
        def sumaPorcentajeWolfran=0
        def sumaValor=0
        resultadoWolfranJSON.each {
            System.out.println(it.toString())
            def porcentajeWolfran = Float.parseFloat(it.porcentajeWolfran.toString())
            if(porcentajeWolfran!=0){
                registrosValidos++
                def cotizacionWolfran =  Float.parseFloat(it.cotizacionWolfran.toString())
                def kilosNetosHumedos = Float.parseFloat(it.kilosNetosHumedos.toString())
                def humedad = Float.parseFloat(it.humedad.toString())
                def vpt = pc.getValorPorToneladaPresupuesto(Integer.parseInt(tablaCotizacionWolfranId),porcentajeWolfran)
                def kilosNetosSecos = kilosNetosHumedos-kilosNetosHumedos*humedad/100
                def valor = cotizacionDolar.tipoDeCambioComercial*kilosNetosSecos*vpt/1000

                sumaPesoBruto+=kilosNetosHumedos
                sumaHumedad+=humedad
                sumaKilosNetosSecos+=kilosNetosSecos
                sumaPorcentajeWolfran+=porcentajeWolfran
                sumaValor+=valor
                //System.out.println(it.toString())
                sheet1.addCell(new Label(0,i,""+it.fechaDeRecepcion))
                sheet1.addCell(new Number(1,i,it.lote))
                sheet1.addCell(new Label(2,i,it.nombreEmpresa))
                sheet1.addCell(new Label(3,i,it.nombreCliente))
                sheet1.addCell(new Label(4,i,it.cantidadDeSacos))
                sheet1.addCell(new Number(5,i,kilosNetosHumedos))
                sheet1.addCell(new Number(6,i,it.cotizacionWolfran))
                sheet1.addCell(new Number(7,i,humedad))
                sheet1.addCell(new Number(8,i,kilosNetosSecos))
                sheet1.addCell(new Number(9,i,porcentajeWolfran))
                sheet1.addCell(new Number(10,i,valor))
                i++
            }
        }
        if (registrosValidos>0){
            sheet1.addCell(new Label(0,i,"TOTALES Y PROMEDIOS",arial10Boldformat))
            sheet1.addCell(new Number(5,i,sumaPesoBruto,arial10Boldformat))
            sheet1.addCell(new Number(7,i,sumaHumedad/registrosValidos,arial10Boldformat))
            sheet1.addCell(new Number(8,i,sumaKilosNetosSecos,arial10Boldformat))
            sheet1.addCell(new Number(9,i,sumaPorcentajeWolfran/registrosValidos,arial10Boldformat))
            sheet1.addCell(new Number(10,i,sumaValor,arial10Boldformat))
        }

        workbook.write();
        workbook.close();
    }

    def crearReporteComplejo = {
        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="presupuesto_complejo.xls"')
        def cotizacionDolar = CotizacionDeDolar.findByActivo(1)
        def resultadoComplejo = ""+params.resultadoComplejo
        def resultadoComplejoJSON = new JSONArray(resultadoComplejo)
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Presupuesto de Complejo", 0)
        sheet1.setColumnView(0,10)
        sheet1.setColumnView(2,40)
        sheet1.setColumnView(3,40)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
        WritableCellFormat arial10Boldformat = new WritableCellFormat (arial10BoldFont);
        WritableCellFormat arial14Boldformat = new WritableCellFormat (arial14BoldFont);
        sheet1.addCell(new Label(0,0, "PRESUPUESTO DE COMPLEJO",arial14Boldformat))
        sheet1.addCell(new Label(0,2, "FECHA",arial10Boldformat))
        sheet1.addCell(new Label(1,2, "LOTE",arial10Boldformat))
        sheet1.addCell(new Label(2,2, "PROCEDENCIA",arial10Boldformat))
        sheet1.addCell(new Label(3,2, "PROVEEDOR",arial10Boldformat))
        sheet1.addCell(new Label(4,2, "SACOS",arial10Boldformat))
        sheet1.addCell(new Label(5,2, "KB",arial10Boldformat))
        sheet1.addCell(new Label(6,2, "COT. Zn",arial10Boldformat))
        sheet1.addCell(new Label(7,2, "COT. Pb",arial10Boldformat))
        sheet1.addCell(new Label(8,2, "COT. Ag",arial10Boldformat))
        sheet1.addCell(new Label(9,2, "MERMA",arial10Boldformat))
        sheet1.addCell(new Label(10,2, "% H2O",arial10Boldformat))        
        sheet1.addCell(new Label(11,2, "KNS",arial10Boldformat))
        sheet1.addCell(new Label(12,2, "% Zn",arial10Boldformat))
        sheet1.addCell(new Label(13,2, "% Pb",arial10Boldformat))
        sheet1.addCell(new Label(14,2, "% Ag",arial10Boldformat))
        sheet1.addCell(new Label(15,2, "VALOR",arial10Boldformat))
        def i=3
        def registrosValidos=0
        def sumaPesoBruto=0
        def sumaHumedad=0
        def sumaKilosNetosSecos=0
        def sumaPorcentajeZinc=0
        def sumaPorcentajePlomo=0
        def sumaPorcentajePlata=0
        def sumaValor=0
        resultadoComplejoJSON.each {
            //{"fechaDeRecepcion":"03/01/2014","lote":1,"nombreEmpresa":"Cooperativa Minera NUEVA SAN PABLO LTDA.","nombreCliente":"PRUDENCIO CONDORI","cantidadDeSacos":"10","kilosNetosHumedos":1200,
            // "cotizacionZinc":0.9,"cotizacionPlomo":0.98,"cotizacionPlata":19.5,"merma":"0","humedad":"0",
            // "porcentajeZinc":"0","porcentajePlomo":"0","porcentajePlata":"0",
            // "puntoZinc":"0","puntoPlomo":"0","puntoPlata":"0","id":"1"}

            def porcentajeZinc = Float.parseFloat(it.porcentajeZinc.toString())
            def porcentajePlomo = Float.parseFloat(it.porcentajePlomo.toString())
            def porcentajePlata = Float.parseFloat(it.porcentajePlata.toString())
            if(porcentajeZinc!=0||porcentajePlomo!=0||porcentajePlata!=0){
                registrosValidos++
                def cotizacionZinc =  Float.parseFloat(it.cotizacionZinc.toString())
                def cotizacionPlomo =  Float.parseFloat(it.cotizacionPlomo.toString())
                def cotizacionPlata =  Float.parseFloat(it.cotizacionPlata.toString())
                def kilosNetosHumedos = Float.parseFloat(it.kilosNetosHumedos.toString())
                def merma = Float.parseFloat(it.merma.toString())
                def humedad = Float.parseFloat(it.humedad.toString())
                def puntoZinc =  Float.parseFloat(it.puntoZinc.toString())
                def puntoPlomo =  Float.parseFloat(it.puntoPlomo.toString())
                def puntoPlata =  Float.parseFloat(it.puntoPlata.toString())
                //valorPorTonelada = dolarPuntoZinc*porcentajeZinc + dolarPuntoPlomo*porcentajePlomo + dolarPuntoPlata*porcentajePlata;
                def vpt = puntoZinc*porcentajeZinc + puntoPlomo*porcentajePlomo + puntoPlata*porcentajePlata;
                
                def kilosNetosSecos = kilosNetosHumedos-kilosNetosHumedos*humedad/100-kilosNetosHumedos*merma/100
                def valor = cotizacionDolar.tipoDeCambioComercial*kilosNetosSecos*vpt/1000

                sumaPesoBruto+=kilosNetosHumedos
                sumaHumedad+=humedad
                sumaKilosNetosSecos+=kilosNetosSecos
                sumaPorcentajeZinc+=porcentajeZinc
                sumaPorcentajePlomo+=porcentajePlomo
                sumaPorcentajePlata+=porcentajePlata
                sumaValor+=valor
                //System.out.println(it.toString())
                sheet1.addCell(new Label(0,i,""+it.fechaDeRecepcion))
                sheet1.addCell(new Number(1,i,it.lote))
                sheet1.addCell(new Label(2,i,it.nombreEmpresa))
                sheet1.addCell(new Label(3,i,it.nombreCliente))
                sheet1.addCell(new Label(4,i,it.cantidadDeSacos))
                sheet1.addCell(new Number(5,i,kilosNetosHumedos))
                sheet1.addCell(new Number(6,i,cotizacionZinc))
                sheet1.addCell(new Number(7,i,cotizacionPlomo))
                sheet1.addCell(new Number(8,i,cotizacionPlata))
                sheet1.addCell(new Number(9,i,merma))
                sheet1.addCell(new Number(10,i,humedad))
                sheet1.addCell(new Number(11,i,kilosNetosSecos))
                sheet1.addCell(new Number(12,i,porcentajeZinc))
                sheet1.addCell(new Number(13,i,porcentajePlomo))
                sheet1.addCell(new Number(14,i,porcentajePlata))
                sheet1.addCell(new Number(15,i,valor))
                i++
            }
        }
        if (registrosValidos>0){
            sheet1.addCell(new Label(0,i,"TOTALES Y PROMEDIOS",arial10Boldformat))
            sheet1.addCell(new Number(5,i,sumaPesoBruto,arial10Boldformat))
            sheet1.addCell(new Number(10,i,sumaHumedad/registrosValidos,arial10Boldformat))
            sheet1.addCell(new Number(11,i,sumaKilosNetosSecos,arial10Boldformat))
            sheet1.addCell(new Number(12,i,sumaPorcentajeZinc/registrosValidos,arial10Boldformat))
            sheet1.addCell(new Number(13,i,sumaPorcentajePlomo/registrosValidos,arial10Boldformat))
            sheet1.addCell(new Number(14,i,sumaPorcentajePlata/registrosValidos,arial10Boldformat))
            sheet1.addCell(new Number(15,i,sumaValor,arial10Boldformat))
        }

        workbook.write();
        workbook.close();
    }
}
