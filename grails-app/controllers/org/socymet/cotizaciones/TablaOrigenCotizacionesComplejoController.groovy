package org.socymet.cotizaciones
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import jxl.Sheet
import jxl.Workbook
import jxl.format.Alignment
import jxl.format.VerticalAlignment
import jxl.write.*
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest

import java.sql.Blob

@Secured(['ROLE_ADMIN'])
@Transactional
class TablaOrigenCotizacionesComplejoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [tablaOrigenCotizacionesComplejoInstanceList: TablaOrigenCotizacionesComplejo.list(params), tablaOrigenCotizacionesComplejoInstanceTotal: TablaOrigenCotizacionesComplejo.count()]
    }

    def create() {
        [tablaOrigenCotizacionesComplejoInstance: new TablaOrigenCotizacionesComplejo(params)]
    }

    def save() {
        def tablaOrigenCotizacionesComplejoInstance = new TablaOrigenCotizacionesComplejo(params)
        if (!tablaOrigenCotizacionesComplejoInstance.save(flush: true)) {
            render(view: "create", model: [tablaOrigenCotizacionesComplejoInstance: tablaOrigenCotizacionesComplejoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'tablaOrigenCotizacionesComplejo.label', default: 'TablaOrigenCotizacionesComplejo'), tablaOrigenCotizacionesComplejoInstance.id])
        redirect(action: "show", id: tablaOrigenCotizacionesComplejoInstance.id)
    }

    def show(Long id) {
        def tablaOrigenCotizacionesComplejoInstance = TablaOrigenCotizacionesComplejo.get(id)
        if (!tablaOrigenCotizacionesComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaOrigenCotizacionesComplejo.label', default: 'TablaOrigenCotizacionesComplejo'), id])
            redirect(action: "list")
            return
        }

        [tablaOrigenCotizacionesComplejoInstance: tablaOrigenCotizacionesComplejoInstance]
    }

    def edit(Long id) {
        def tablaOrigenCotizacionesComplejoInstance = TablaOrigenCotizacionesComplejo.get(id)
        if (!tablaOrigenCotizacionesComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaOrigenCotizacionesComplejo.label', default: 'TablaOrigenCotizacionesComplejo'), id])
            redirect(action: "list")
            return
        }

        [tablaOrigenCotizacionesComplejoInstance: tablaOrigenCotizacionesComplejoInstance]
    }

    def update(Long id, Long version) {
        def tablaOrigenCotizacionesComplejoInstance = TablaOrigenCotizacionesComplejo.get(id)
        if (!tablaOrigenCotizacionesComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaOrigenCotizacionesComplejo.label', default: 'TablaOrigenCotizacionesComplejo'), id])
            redirect(action: "list")
            return
        }

//        if (version != null) {
//            if (tablaOrigenCotizacionesComplejoInstance.version > version) {
//                tablaOrigenCotizacionesComplejoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
//                        [message(code: 'tablaOrigenCotizacionesComplejo.label', default: 'TablaOrigenCotizacionesComplejo')] as Object[],
//                        "Another user has updated this TablaOrigenCotizacionesComplejo while you were editing")
//                render(view: "edit", model: [tablaOrigenCotizacionesComplejoInstance: tablaOrigenCotizacionesComplejoInstance])
//                return
//            }
//        }

        MultipartHttpServletRequest mpr = (MultipartHttpServletRequest)request;
        MultipartFile file = mpr.getFile('datosArchivo')

        if(file.empty) {
//            def empresa = Empresa.get(Integer.parseInt("${params.empresa.id}"))
            def empresa = null
//            flash.message = "El archivo no puede estar vacio!"
            tablaOrigenCotizacionesComplejoInstance.nombreTabla = params.nombreTabla
            tablaOrigenCotizacionesComplejoInstance.empresa = empresa
            tablaOrigenCotizacionesComplejoInstance.naturalezaMineral = params.naturalezaMineral
        } else {
            //def documentInstance = new TablaOrigenCotizacionesComplejo()
            def datosZinc = new ArrayList()
            def datosPlomo = new ArrayList()
            def datosPlata = new ArrayList()
//            def empresa = Empresa.get(Integer.parseInt("${params.empresa.id}"))
            def empresa = null

            tablaOrigenCotizacionesComplejoInstance.nombreTabla = params.nombreTabla
            tablaOrigenCotizacionesComplejoInstance.empresa = empresa
            tablaOrigenCotizacionesComplejoInstance.nombreArchivo = file.originalFilename
            log.error("naturalezaMineral: ${params.naturalezaMineral}")
            tablaOrigenCotizacionesComplejoInstance.naturalezaMineral = params.naturalezaMineral
            tablaOrigenCotizacionesComplejoInstance.datosArchivo = construirTabla(file,empresa,datosZinc,datosPlomo,datosPlata)
            tablaOrigenCotizacionesComplejoInstance.datosZinc = (datosZinc as JSON).toString()
            tablaOrigenCotizacionesComplejoInstance.datosPlomo = (datosPlomo as JSON).toString()
            tablaOrigenCotizacionesComplejoInstance.datosPlata = (datosPlata as JSON).toString()
            tablaOrigenCotizacionesComplejoInstance.save(flush: true)
        }

        //tablaOrigenCotizacionesComplejoInstance.properties = params

        if (!tablaOrigenCotizacionesComplejoInstance.save(flush: true)) {
            render(view: "edit", model: [tablaOrigenCotizacionesComplejoInstance: tablaOrigenCotizacionesComplejoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'tablaOrigenCotizacionesComplejo.label', default: 'TablaOrigenCotizacionesComplejo'), tablaOrigenCotizacionesComplejoInstance.id])
        redirect(action: "show", id: tablaOrigenCotizacionesComplejoInstance.id)
    }

    def delete(Long id) {
        def tablaOrigenCotizacionesComplejoInstance = TablaOrigenCotizacionesComplejo.get(id)
        if (!tablaOrigenCotizacionesComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaOrigenCotizacionesComplejo.label', default: 'TablaOrigenCotizacionesComplejo'), id])
            redirect(action: "list")
            return
        }

        try {
            tablaOrigenCotizacionesComplejoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'tablaOrigenCotizacionesComplejo.label', default: 'TablaOrigenCotizacionesComplejo'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'tablaOrigenCotizacionesComplejo.label', default: 'TablaOrigenCotizacionesComplejo'), id])
            redirect(action: "show", id: id)
        }
    }

    def upload() {
        MultipartHttpServletRequest mpr = (MultipartHttpServletRequest)request;
        MultipartFile file = mpr.getFile('datosArchivo')

        if(file.empty) {
            flash.message = "File cannot be empty"
        } else {
            def datosZinc = new ArrayList()
            def datosPlomo = new ArrayList()
            def datosPlata = new ArrayList()
            def documentInstance = new TablaOrigenCotizacionesComplejo()
//            def empresa = Empresa.get(Integer.parseInt("${params.empresa.id}"))
            def empresa = null
            documentInstance.nombreTabla = params.nombreTabla
            documentInstance.naturalezaMineral = params.naturalezaMineral
            documentInstance.empresa = empresa
            documentInstance.nombreArchivo = file.originalFilename
            documentInstance.datosArchivo = construirTabla(file,empresa,datosZinc,datosPlomo,datosPlata)
            documentInstance.datosZinc = (datosZinc as JSON).toString()
            documentInstance.datosPlomo = (datosPlomo as JSON).toString()
            documentInstance.datosPlata = (datosPlata as JSON).toString()
            documentInstance.save()
        }
        redirect (action:'list')
    }

    def download(long id) {
        TablaOrigenCotizacionesComplejo documentInstance = TablaOrigenCotizacionesComplejo.get(id)
        if ( documentInstance == null) {
            flash.message = "Document not found."
            redirect (action:'list')
        } else {
            response.setContentType("APPLICATION/OCTET-STREAM")
            response.setHeader("Content-Disposition", "Attachment;Filename=\"${documentInstance.nombreArchivo}\"")

            FileOutputStream fileOuputStream = new FileOutputStream("testing.xls");
            fileOuputStream.write(documentInstance.datosArchivo);
            fileOuputStream.close();

            File temporal = new File("temp.xls")
            FileInputStream orig = new FileInputStream("testing.xls")
            Workbook original = Workbook.getWorkbook(orig)
            WritableWorkbook workbook = Workbook.createWorkbook(temporal, original);
//            File temporal = new File("temp.xls")
//            Workbook original = Workbook.getWorkbook(multipartFile.inputStream)
//            WritableWorkbook workbook = Workbook.createWorkbook(temporal, original);

            WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);

            WritableCellFormat formatoEncabezado = new WritableCellFormat (courier8PlainFont);
            //formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)
            formatoEncabezado.setWrap(true)
            formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
            formatoEncabezado.setAlignment(Alignment.CENTRE)

            WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"));
            formatoDatos.setFont(courier8PlainFont)
            //formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

            WritableSheet sheet = workbook.getSheet(0)

            def cotizacionDiaria = CotizacionDiariaDeMinerales.findByActivo(1)
            def cotizacionZincTonelada = cotizacionDiaria.zinc*2.2046223*1000
            def cotizacionPlomoTonelada = cotizacionDiaria.plomo*2.2046223*1000
            def cotizacionPlataTonelada = cotizacionDiaria.plata

            log.error("Zn: ${cotizacionDiaria.zinc} Pb: ${cotizacionDiaria.plomo} Ag: ${cotizacionDiaria.plata}")

            sheet.addCell(new Label(1,2, documentInstance.empresa.toString(),formatoEncabezado))
            sheet.addCell(new Label(1,10, "VAMOS!",formatoEncabezado))


            sheet.addCell(new Number(2,3, cotizacionDiaria.zinc,formatoEncabezado))
            sheet.addCell(new Number(2,4, cotizacionDiaria.plomo,formatoEncabezado))
            sheet.addCell(new Number(2,5, cotizacionDiaria.plata,formatoEncabezado))

            sheet.addCell(new Label(1,6, new java.text.SimpleDateFormat("dd/MM/yyyy").format(new Date()),formatoEncabezado))

            WritableSheet hojaZinc = workbook.getSheet(1)
            WritableSheet hojaPlomo = workbook.getSheet(2)
            WritableSheet hojaPlata = workbook.getSheet(3)
            hojaZinc.addCell(new Number(1,1, cotizacionZincTonelada,formatoDatos))
            hojaPlomo.addCell(new Number(1,1, cotizacionPlomoTonelada,formatoDatos))
            hojaPlata.addCell(new Number(1,1, cotizacionPlataTonelada,formatoDatos))

            def rangoFilasZinc = 3..52
            def rangoFilasPlomo = 3..52
            def rangoFilasPlata = 3..52
            def porcentajeLey = 0
            def porcentajeLME = 0
            def precioTonelada = 0
            def precioToneladaX = 0
            def precioToneladaY = 0
            def precioPunto = 0

            rangoFilasZinc.each {
                porcentajeLey =(!hojaZinc.getWritableCell(0,it).contents.replace('%','').isNumber())?0:hojaZinc.getWritableCell(0,it).contents.replace('%','').toDouble()
                porcentajeLME =(!hojaZinc.getWritableCell(1,it).contents.replace('%','').isNumber())?0:hojaZinc.getWritableCell(1,it).contents.replace('%','').toDouble()
                precioTonelada = cotizacionZincTonelada*porcentajeLey*porcentajeLME/10000
                hojaZinc.addCell(new Number(3,it, precioTonelada,formatoDatos))
            }

            rangoFilasZinc.each {
                precioToneladaX = (!hojaZinc.getWritableCell(3,it).contents.isNumber())?0:hojaZinc.getWritableCell(3,it).contents.toDouble()
                precioToneladaY = (!hojaZinc.getWritableCell(3,it+1).contents.isNumber())?0:hojaZinc.getWritableCell(3,it+1).contents.toDouble()
                precioPunto = Math.abs(precioToneladaY - precioToneladaX)
                hojaZinc.addCell(new Number(2,it, precioPunto,formatoDatos))
            }

            rangoFilasPlomo.each {
                porcentajeLey =(!hojaPlomo.getWritableCell(0,it).contents.replace('%','').isNumber())?0:hojaPlomo.getWritableCell(0,it).contents.replace('%','').toDouble()
                porcentajeLME =(!hojaPlomo.getWritableCell(1,it).contents.replace('%','').isNumber())?0:hojaPlomo.getWritableCell(1,it).contents.replace('%','').toDouble()
                precioTonelada = cotizacionPlomoTonelada*porcentajeLey*porcentajeLME/10000
                hojaPlomo.addCell(new Number(3,it, precioTonelada,formatoDatos))
            }

            rangoFilasPlomo.each {
                precioToneladaX = (!hojaPlomo.getWritableCell(3,it).contents.isNumber())?0:hojaPlomo.getWritableCell(3,it).contents.toDouble()
                precioToneladaY = (!hojaPlomo.getWritableCell(3,it+1).contents.isNumber())?0:hojaPlomo.getWritableCell(3,it+1).contents.toDouble()
                precioPunto = Math.abs(precioToneladaY - precioToneladaX)
                hojaPlomo.addCell(new Number(2,it, precioPunto,formatoDatos))
            }

            rangoFilasPlata.each {
                porcentajeLey =(!hojaPlata.getWritableCell(0,it).contents.replace('%','').isNumber())?0:hojaPlata.getWritableCell(0,it).contents.replace('%','').toDouble()
                porcentajeLME =(!hojaPlata.getWritableCell(1,it).contents.replace('%','').isNumber())?0:hojaPlata.getWritableCell(1,it).contents.replace('%','').toDouble()
                precioTonelada = cotizacionPlataTonelada*porcentajeLey*porcentajeLME*100/(31.1035*100)
                hojaPlata.addCell(new Number(3,it, precioTonelada,formatoDatos))
            }

            rangoFilasPlata.each {
                precioToneladaX = (!hojaPlata.getWritableCell(3,it).contents.isNumber())?0:hojaPlata.getWritableCell(3,it).contents.toDouble()
                precioToneladaY = (!hojaPlata.getWritableCell(3,it+1).contents.isNumber())?0:hojaPlata.getWritableCell(3,it+1).contents.toDouble()
                precioPunto = Math.abs(precioToneladaY - precioToneladaX)
                hojaPlata.addCell(new Number(2,it, precioPunto,formatoDatos))
            }

            //cargar arraylist de zinc
//            def filaZinc=null
//            rangoFilasZinc.each {
//                filaZinc=[]
//                porcentajeLey =(!hojaZinc.getWritableCell(0,it).contents.replace('%','').isNumber())?0:hojaZinc.getWritableCell(0,it).contents.replace('%','').toDouble()
//                porcentajeLME =(!hojaZinc.getWritableCell(1,it).contents.replace('%','').isNumber())?0:hojaZinc.getWritableCell(1,it).contents.replace('%','').toDouble()
//                filaZinc.add(hojaZinc.getWritableCell(0,it).contents.replace('%','').toDouble())
//                filaZinc.add(hojaZinc.getWritableCell(1,it).contents.replace('%','').toDouble())
//                filaZinc.add(hojaZinc.getWritableCell(2,it).contents.toDouble())
//                filaZinc.add(hojaZinc.getWritableCell(3,it).contents.toDouble())
//                datosZinc.add(filaZinc)
//            }
//
//            //cargar arraylist de plomo
//            def filaPlomo=null
//            rangoFilasPlomo.each {
//                filaPlomo=[]
//                porcentajeLey =(!hojaPlomo.getWritableCell(0,it).contents.replace('%','').isNumber())?0:hojaPlomo.getWritableCell(0,it).contents.replace('%','').toDouble()
//                porcentajeLME =(!hojaPlomo.getWritableCell(1,it).contents.replace('%','').isNumber())?0:hojaPlomo.getWritableCell(1,it).contents.replace('%','').toDouble()
//                filaPlomo.add(hojaPlomo.getWritableCell(0,it).contents.replace('%','').toDouble())
//                filaPlomo.add(hojaPlomo.getWritableCell(1,it).contents.replace('%','').toDouble())
//                filaPlomo.add(hojaPlomo.getWritableCell(2,it).contents.toDouble())
//                filaPlomo.add(hojaPlomo.getWritableCell(3,it).contents.toDouble())
//                datosPlomo.add(filaPlomo)
//            }
//
//            //cargar arraylist de plata
//            def filaPlata=null
//            rangoFilasPlata.each {
//                filaPlata=[]
//                porcentajeLey =(!hojaPlata.getWritableCell(0,it).contents.replace('%','').isNumber())?0:hojaPlata.getWritableCell(0,it).contents.replace('%','').toDouble()
//                porcentajeLME =(!hojaPlata.getWritableCell(1,it).contents.replace('%','').isNumber())?0:hojaPlata.getWritableCell(1,it).contents.replace('%','').toDouble()
//                filaPlata.add(hojaPlata.getWritableCell(0,it).contents.replace('%','').toDouble())
//                filaPlata.add(hojaPlata.getWritableCell(1,it).contents.replace('%','').toDouble())
//                filaPlata.add(hojaPlata.getWritableCell(2,it).contents.toDouble())
//                filaPlata.add(hojaPlata.getWritableCell(3,it).contents.toDouble())
//                datosPlata.add(filaPlata)
//            }

            orig.close()
            workbook.write()
//            WritableWorkbook workbook2 = Workbook.createWorkbook(response.outputStream)

            workbook.close()
//            workbook2.close()

            def outputStream = response.getOutputStream()
//            outputStream << documentInstance.datosArchivo
            outputStream << temporal.getBytes()
            outputStream.flush()
            outputStream.close()
        }
    }

    def construirTabla(MultipartFile multipartFile, empresa, datosZinc, datosPlomo, datosPlata){
        File temporal = new File("temp.xls")
        Workbook original = Workbook.getWorkbook(multipartFile.inputStream)
        WritableWorkbook workbook = Workbook.createWorkbook(temporal, original);

        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);

        WritableCellFormat formatoEncabezado = new WritableCellFormat (courier8PlainFont);
        //formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)
        formatoEncabezado.setWrap(true)
        formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
        formatoEncabezado.setAlignment(Alignment.CENTRE)

        WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoDatos.setFont(courier8PlainFont)
        //formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        WritableSheet sheet = workbook.getSheet(0)

        def cotizacionDiaria = CotizacionDiariaDeMinerales.findByActivo(1)
        def cotizacionZincTonelada = cotizacionDiaria.zinc*2.2046223*1000
        def cotizacionPlomoTonelada = cotizacionDiaria.plomo*2.2046223*1000
        def cotizacionPlataTonelada = cotizacionDiaria.plata

        log.error("Zn: ${cotizacionDiaria.zinc} Pb: ${cotizacionDiaria.plomo} Ag: ${cotizacionDiaria.plata}")

//        sheet.addCell(new Label(1,2, empresa.toString(),formatoEncabezado))
        sheet.addCell(new Label(1,2, "-",formatoEncabezado))

        sheet.addCell(new Number(2,3, cotizacionDiaria.zinc,formatoEncabezado))
        sheet.addCell(new Number(2,4, cotizacionDiaria.plomo,formatoEncabezado))
        sheet.addCell(new Number(2,5, cotizacionDiaria.plata,formatoEncabezado))

        sheet.addCell(new Label(1,6, new java.text.SimpleDateFormat("dd/MM/yyyy").format(new Date()),formatoEncabezado))

        WritableSheet hojaZinc = workbook.getSheet(1)
        WritableSheet hojaPlomo = workbook.getSheet(2)
        WritableSheet hojaPlata = workbook.getSheet(3)
        hojaZinc.addCell(new Number(1,1, cotizacionZincTonelada,formatoDatos))
        hojaPlomo.addCell(new Number(1,1, cotizacionPlomoTonelada,formatoDatos))
        hojaPlata.addCell(new Number(1,1, cotizacionPlataTonelada,formatoDatos))

        def rangoFilasZinc = 3..52
        def rangoFilasPlomo = 3..52
        def rangoFilasPlata = 3..52
        def porcentajeLey = 0
        def porcentajeLME = 0
        def precioTonelada = 0
        def precioToneladaX = 0
        def precioToneladaY = 0
        def precioPunto = 0

        rangoFilasZinc.each {
            porcentajeLey =(!hojaZinc.getWritableCell(0,it).contents.replace('%','').isNumber())?0:hojaZinc.getWritableCell(0,it).contents.replace('%','').toDouble()
            porcentajeLME =(!hojaZinc.getWritableCell(1,it).contents.replace('%','').isNumber())?0:hojaZinc.getWritableCell(1,it).contents.replace('%','').toDouble()
            precioTonelada = cotizacionZincTonelada*porcentajeLey*porcentajeLME/10000
            hojaZinc.addCell(new Number(3,it, precioTonelada,formatoDatos))
        }

        rangoFilasZinc.each {
            precioToneladaX = (!hojaZinc.getWritableCell(3,it).contents.isNumber())?0:hojaZinc.getWritableCell(3,it).contents.toDouble()
            precioToneladaY = (!hojaZinc.getWritableCell(3,it+1).contents.isNumber())?0:hojaZinc.getWritableCell(3,it+1).contents.toDouble()
            precioPunto = Math.abs(precioToneladaY - precioToneladaX)
            hojaZinc.addCell(new Number(2,it, precioPunto,formatoDatos))
        }

        rangoFilasPlomo.each {
            porcentajeLey =(!hojaPlomo.getWritableCell(0,it).contents.replace('%','').isNumber())?0:hojaPlomo.getWritableCell(0,it).contents.replace('%','').toDouble()
            porcentajeLME =(!hojaPlomo.getWritableCell(1,it).contents.replace('%','').isNumber())?0:hojaPlomo.getWritableCell(1,it).contents.replace('%','').toDouble()
            precioTonelada = cotizacionPlomoTonelada*porcentajeLey*porcentajeLME/10000
            hojaPlomo.addCell(new Number(3,it, precioTonelada,formatoDatos))
        }

        rangoFilasPlomo.each {
            precioToneladaX = (!hojaPlomo.getWritableCell(3,it).contents.isNumber())?0:hojaPlomo.getWritableCell(3,it).contents.toDouble()
            precioToneladaY = (!hojaPlomo.getWritableCell(3,it+1).contents.isNumber())?0:hojaPlomo.getWritableCell(3,it+1).contents.toDouble()
            precioPunto = Math.abs(precioToneladaY - precioToneladaX)
            hojaPlomo.addCell(new Number(2,it, precioPunto,formatoDatos))
        }

        rangoFilasPlata.each {
            porcentajeLey =(!hojaPlata.getWritableCell(0,it).contents.replace('%','').isNumber())?0:hojaPlata.getWritableCell(0,it).contents.replace('%','').toDouble()
            porcentajeLME =(!hojaPlata.getWritableCell(1,it).contents.replace('%','').isNumber())?0:hojaPlata.getWritableCell(1,it).contents.replace('%','').toDouble()
            precioTonelada = cotizacionPlataTonelada*porcentajeLey*porcentajeLME*100/(31.1035*100)
            hojaPlata.addCell(new Number(3,it, precioTonelada,formatoDatos))
        }

        rangoFilasPlata.each {
            precioToneladaX = (!hojaPlata.getWritableCell(3,it).contents.isNumber())?0:hojaPlata.getWritableCell(3,it).contents.toDouble()
            precioToneladaY = (!hojaPlata.getWritableCell(3,it+1).contents.isNumber())?0:hojaPlata.getWritableCell(3,it+1).contents.toDouble()
            precioPunto = Math.abs(precioToneladaY - precioToneladaX)
            hojaPlata.addCell(new Number(2,it, precioPunto,formatoDatos))
        }

        //cargar arraylist de zinc
        def filaZinc=null
        rangoFilasZinc.each {
            filaZinc=[]
            porcentajeLey =(!hojaZinc.getWritableCell(0,it).contents.replace('%','').isNumber())?0:hojaZinc.getWritableCell(0,it).contents.replace('%','').toDouble()
            porcentajeLME =(!hojaZinc.getWritableCell(1,it).contents.replace('%','').isNumber())?0:hojaZinc.getWritableCell(1,it).contents.replace('%','').toDouble()
            filaZinc.add(hojaZinc.getWritableCell(0,it).contents.replace('%','').replace(',','.').toDouble())
            filaZinc.add(hojaZinc.getWritableCell(1,it).contents.replace('%','').replace(',','.').toDouble())
            filaZinc.add(hojaZinc.getWritableCell(2,it).contents.replace(',','.').toDouble())
            filaZinc.add(hojaZinc.getWritableCell(3,it).contents.replace(',','.').toDouble())
            datosZinc.add(filaZinc)
        }

        //cargar arraylist de plomo
        def filaPlomo=null
        rangoFilasPlomo.each {
            filaPlomo=[]
            porcentajeLey =(!hojaPlomo.getWritableCell(0,it).contents.replace('%','').isNumber())?0:hojaPlomo.getWritableCell(0,it).contents.replace('%','').toDouble()
            porcentajeLME =(!hojaPlomo.getWritableCell(1,it).contents.replace('%','').isNumber())?0:hojaPlomo.getWritableCell(1,it).contents.replace('%','').toDouble()
            filaPlomo.add(hojaPlomo.getWritableCell(0,it).contents.replace('%','').replace(',','.').toDouble())
            filaPlomo.add(hojaPlomo.getWritableCell(1,it).contents.replace('%','').replace(',','.').toDouble())
            filaPlomo.add(hojaPlomo.getWritableCell(2,it).contents.replace(',','.').toDouble())
            filaPlomo.add(hojaPlomo.getWritableCell(3,it).contents.replace(',','.').toDouble())
            datosPlomo.add(filaPlomo)
        }

        //cargar arraylist de plata
        def filaPlata=null
        rangoFilasPlata.each {
            filaPlata=[]
            porcentajeLey =(!hojaPlata.getWritableCell(0,it).contents.replace('%','').isNumber())?0:hojaPlata.getWritableCell(0,it).contents.replace('%','').toDouble()
            porcentajeLME =(!hojaPlata.getWritableCell(1,it).contents.replace('%','').isNumber())?0:hojaPlata.getWritableCell(1,it).contents.replace('%','').toDouble()
            filaPlata.add(hojaPlata.getWritableCell(0,it).contents.replace('%','').replace(',','.').toDouble())
            filaPlata.add(hojaPlata.getWritableCell(1,it).contents.replace('%','').replace(',','.').toDouble())
            filaPlata.add(hojaPlata.getWritableCell(2,it).contents.replace(',','.').toDouble())
            filaPlata.add(hojaPlata.getWritableCell(3,it).contents.replace(',','.').toDouble())
            datosPlata.add(filaPlata)
        }

        workbook.write()
        workbook.close()

        return temporal.getBytes()
    }

    def getValorPorToneladaZinc2(RecepcionDeComplejo recepcionDeComplejo, Long tablaId, BigDecimal porcentajeZinc){
        if (porcentajeZinc==0)
            return 0

        def tablaPrecios = TablaOrigenCotizacionesComplejo.get(tablaId)

        def datosZincString = tablaPrecios.datosZinc
        def datosZincArray = JSON.parse(datosZincString)
        def pos=0
        def porcentajeZincRecuperado=0
        def filaZinc = null
        def cotizacionZincTonelada = recepcionDeComplejo.cotizacionDiariaDeMinerales.zinc*2.2046223*1000
        while (porcentajeZinc>porcentajeZincRecuperado&&pos<50){
            //log.error("***** porcentajeZincRecuperado=${porcentajeZincRecuperado} cuando pos=${pos}")
            filaZinc = JSON.parse(datosZincArray[pos].toString())
            porcentajeZincRecuperado = filaZinc[0].toString().toBigDecimal()
            pos++
        }
        def porcentaje1 = (JSON.parse(datosZincArray[pos-2].toString()))[0].toString().toBigDecimal()
        def porcentaje2 = (JSON.parse(datosZincArray[pos-1].toString()))[0].toString().toBigDecimal()
        def punto1 = (JSON.parse(datosZincArray[pos-2].toString()))[1].toString().toBigDecimal()
        def punto2 = (JSON.parse(datosZincArray[pos-1].toString()))[1].toString().toBigDecimal()
//        def vpt1 = (JSON.parse(datosZincArray[pos-2].toString()))[3].toString().toBigDecimal()
//        def vpt2 = (JSON.parse(datosZincArray[pos-1].toString()))[3].toString().toBigDecimal()
        def vpt1 = porcentaje1*punto1*cotizacionZincTonelada/10000
        def vpt2 = porcentaje2*punto2*cotizacionZincTonelada/10000
        log.error("CALCULO 2: ZINC -> porcentaje1: ${porcentaje1} porcentaje2: ${porcentaje2} vpt1: ${vpt1} vpt2: ${vpt2} ")
        def precioToneladaZinc=getPuntoRecta(porcentajeZinc,porcentaje1,porcentaje2,vpt1,vpt2)

        return precioToneladaZinc
    }

    def getValorPorToneladaZincCotizacion(CotizacionDiariaDeMinerales cotizacionDiariaDeMinerales, Long tablaId, BigDecimal porcentajeZinc){
        if (porcentajeZinc==0)
            return 0

        def tablaPrecios = TablaOrigenCotizacionesComplejo.get(tablaId)

        def datosZincString = tablaPrecios.datosZinc
        def datosZincArray = JSON.parse(datosZincString)
        def pos=0
        def porcentajeZincRecuperado=0
        def filaZinc = null
        def cotizacionZincTonelada = cotizacionDiariaDeMinerales.zinc*2.2046223*1000
        while (porcentajeZinc>porcentajeZincRecuperado&&pos<50){
            //log.error("***** porcentajeZincRecuperado=${porcentajeZincRecuperado} cuando pos=${pos}")
            filaZinc = JSON.parse(datosZincArray[pos].toString())
            porcentajeZincRecuperado = filaZinc[0].toString().toBigDecimal()
            pos++
        }
        def porcentaje1 = (JSON.parse(datosZincArray[pos-2].toString()))[0].toString().toBigDecimal()
        def porcentaje2 = (JSON.parse(datosZincArray[pos-1].toString()))[0].toString().toBigDecimal()
        def punto1 = (JSON.parse(datosZincArray[pos-2].toString()))[1].toString().toBigDecimal()
        def punto2 = (JSON.parse(datosZincArray[pos-1].toString()))[1].toString().toBigDecimal()
//        def vpt1 = (JSON.parse(datosZincArray[pos-2].toString()))[3].toString().toBigDecimal()
//        def vpt2 = (JSON.parse(datosZincArray[pos-1].toString()))[3].toString().toBigDecimal()
        def vpt1 = porcentaje1*punto1*cotizacionZincTonelada/10000
        def vpt2 = porcentaje2*punto2*cotizacionZincTonelada/10000
        log.error("CALCULO 2: ZINC -> porcentaje1: ${porcentaje1} porcentaje2: ${porcentaje2} vpt1: ${vpt1} vpt2: ${vpt2} ")
        def precioToneladaZinc=getPuntoRecta(porcentajeZinc,porcentaje1,porcentaje2,vpt1,vpt2)

        return precioToneladaZinc
    }

    def getVPTZinc2 = {
        //recepcionId
        if (params.porcentajeZinc.toString().toBigDecimal()==0)
            render([vptZn: 0] as JSON)

        def recepcionDeComplejo = RecepcionDeComplejo.get(params.recepcionId.toLong())
        def cotizacionZincTonelada = recepcionDeComplejo.cotizacionDiariaDeMinerales.zinc*2.2046223*1000
        def tablaPrecios = TablaOrigenCotizacionesComplejo.get(params.tablaId)

        def datosZincString = tablaPrecios.datosZinc
        def datosZincArray = JSON.parse(datosZincString)
        def pos=0
        def porcentajeZincRecuperado=0
        def filaZinc = null
        while (params.porcentajeZinc.toString().toBigDecimal()>porcentajeZincRecuperado&&pos<50){
            //log.error("***** porcentajeZincRecuperado=${porcentajeZincRecuperado} cuando pos=${pos}")
            filaZinc = JSON.parse(datosZincArray[pos].toString())
            porcentajeZincRecuperado = filaZinc[0].toString().toBigDecimal()
            pos++
        }

        //log.error("***** final ciclo pos=${pos}")
        def porcentaje1 = (JSON.parse(datosZincArray[pos-2].toString()))[0].toString().toBigDecimal()
        def porcentaje2 = (JSON.parse(datosZincArray[pos-1].toString()))[0].toString().toBigDecimal()
        def punto1 = (JSON.parse(datosZincArray[pos-2].toString()))[1].toString().toBigDecimal()
        def punto2 = (JSON.parse(datosZincArray[pos-1].toString()))[1].toString().toBigDecimal()
//        def vpt1 = (JSON.parse(datosZincArray[pos-2].toString()))[3].toString().toBigDecimal()
//        def vpt2 = (JSON.parse(datosZincArray[pos-1].toString()))[3].toString().toBigDecimal()
        def vpt1 = porcentaje1*punto1*cotizacionZincTonelada/10000
        def vpt2 = porcentaje2*punto2*cotizacionZincTonelada/10000
        log.error("CALCULO 2: ZINC -> porcentaje1: ${porcentaje1} porcentaje2: ${porcentaje2} vpt1: ${vpt1} vpt2: ${vpt2} ")
        def precioToneladaZinc=getPuntoRecta(params.porcentajeZinc.toString().toBigDecimal(),porcentaje1,porcentaje2,vpt1,vpt2)

        render([vptZn: precioToneladaZinc] as JSON)
    }

    def getValorPorToneladaPlomo2(RecepcionDeComplejo recepcionDeComplejo, Long tablaId, BigDecimal porcentajePlomo){
        if (porcentajePlomo==0)
            return 0

        def tablaPrecios = TablaOrigenCotizacionesComplejo.get(tablaId)

        def datosPlomoString = tablaPrecios.datosPlomo
        def datosPlomoArray = JSON.parse(datosPlomoString)
        def pos=0
        def porcentajePlomoRecuperado=0
        def filaPlomo = null
        def cotizacionPlomoTonelada = recepcionDeComplejo.cotizacionDiariaDeMinerales.plomo*2.2046223*1000
        while (porcentajePlomo>porcentajePlomoRecuperado&&pos<50){
            filaPlomo = JSON.parse(datosPlomoArray[pos].toString())
            porcentajePlomoRecuperado = filaPlomo[0].toString().toBigDecimal()
            pos++
        }

        def porcentaje1 = (JSON.parse(datosPlomoArray[pos-2].toString()))[0].toString().toBigDecimal()
        def porcentaje2 = (JSON.parse(datosPlomoArray[pos-1].toString()))[0].toString().toBigDecimal()
        def punto1 = (JSON.parse(datosPlomoArray[pos-2].toString()))[1].toString().toBigDecimal()
        def punto2 = (JSON.parse(datosPlomoArray[pos-1].toString()))[1].toString().toBigDecimal()
//        def vpt1 = (JSON.parse(datosPlomoArray[pos-2].toString()))[3].toString().toBigDecimal()
//        def vpt2 = (JSON.parse(datosPlomoArray[pos-1].toString()))[3].toString().toBigDecimal()
        def vpt1 = porcentaje1*punto1*cotizacionPlomoTonelada/10000
        def vpt2 = porcentaje2*punto2*cotizacionPlomoTonelada/10000

        log.error("CALCULO 2: PLOMO -> porcentaje1: ${porcentaje1} porcentaje2: ${porcentaje2} punto1: ${punto1} punto2: ${punto2} vpt1: ${vpt1} vpt2: ${vpt2} ")

        def precioToneladaPlomo=getPuntoRecta(porcentajePlomo,porcentaje1,porcentaje2,vpt1,vpt2)

        return precioToneladaPlomo
    }

    def getValorPorToneladaPlomoCotizacion(CotizacionDiariaDeMinerales cotizacionDiariaDeMinerales, Long tablaId, BigDecimal porcentajePlomo){
        if (porcentajePlomo==0)
            return 0

        def tablaPrecios = TablaOrigenCotizacionesComplejo.get(tablaId)

        def datosPlomoString = tablaPrecios.datosPlomo
        def datosPlomoArray = JSON.parse(datosPlomoString)
        def pos=0
        def porcentajePlomoRecuperado=0
        def filaPlomo = null
        def cotizacionPlomoTonelada = cotizacionDiariaDeMinerales.plomo*2.2046223*1000
        while (porcentajePlomo>porcentajePlomoRecuperado&&pos<50){
            filaPlomo = JSON.parse(datosPlomoArray[pos].toString())
            porcentajePlomoRecuperado = filaPlomo[0].toString().toBigDecimal()
            pos++
        }

        def porcentaje1 = (JSON.parse(datosPlomoArray[pos-2].toString()))[0].toString().toBigDecimal()
        def porcentaje2 = (JSON.parse(datosPlomoArray[pos-1].toString()))[0].toString().toBigDecimal()
        def punto1 = (JSON.parse(datosPlomoArray[pos-2].toString()))[1].toString().toBigDecimal()
        def punto2 = (JSON.parse(datosPlomoArray[pos-1].toString()))[1].toString().toBigDecimal()
//        def vpt1 = (JSON.parse(datosPlomoArray[pos-2].toString()))[3].toString().toBigDecimal()
//        def vpt2 = (JSON.parse(datosPlomoArray[pos-1].toString()))[3].toString().toBigDecimal()
        def vpt1 = porcentaje1*punto1*cotizacionPlomoTonelada/10000
        def vpt2 = porcentaje2*punto2*cotizacionPlomoTonelada/10000

        log.error("CALCULO 2: PLOMO -> porcentaje1: ${porcentaje1} porcentaje2: ${porcentaje2} vpt1: ${vpt1} vpt2: ${vpt2} ")

        def precioToneladaPlomo=getPuntoRecta(porcentajePlomo,porcentaje1,porcentaje2,vpt1,vpt2)

        return precioToneladaPlomo
    }

    def getVPTPlomo2 = {
        if (params.porcentajePlomo.toString().toBigDecimal()==0)
            render([vptPb: 0] as JSON)

        def tablaPrecios = TablaOrigenCotizacionesComplejo.get(params.tablaId)
        def recepcionDeComplejo = RecepcionDeComplejo.get(params.recepcionId.toLong())
        def cotizacionPlomoTonelada = recepcionDeComplejo.cotizacionDiariaDeMinerales.plomo*2.2046223*1000

        def datosPlomoString = tablaPrecios.datosPlomo
        def datosPlomoArray = JSON.parse(datosPlomoString)
        def pos=0
        def porcentajePlomoRecuperado=0
        def filaPlomo = null
        while (params.porcentajePlomo.toString().toBigDecimal()>porcentajePlomoRecuperado&&pos<50){
            //log.error("***** porcentajePlomoRecuperado=${porcentajePlomoRecuperado} cuando pos=${pos}")
            filaPlomo = JSON.parse(datosPlomoArray[pos].toString())
            porcentajePlomoRecuperado = filaPlomo[0].toString().toBigDecimal()
            pos++
        }

        //log.error("***** final ciclo pos=${pos}")
        def porcentaje1 = (JSON.parse(datosPlomoArray[pos-2].toString()))[0].toString().toBigDecimal()
        def porcentaje2 = (JSON.parse(datosPlomoArray[pos-1].toString()))[0].toString().toBigDecimal()
        def punto1 = (JSON.parse(datosPlomoArray[pos-2].toString()))[1].toString().toBigDecimal()
        def punto2 = (JSON.parse(datosPlomoArray[pos-1].toString()))[1].toString().toBigDecimal()
//        def vpt1 = (JSON.parse(datosPlomoArray[pos-2].toString()))[3].toString().toBigDecimal()
//        def vpt2 = (JSON.parse(datosPlomoArray[pos-1].toString()))[3].toString().toBigDecimal()
        def vpt1 = porcentaje1*punto1*cotizacionPlomoTonelada/10000
        def vpt2 = porcentaje2*punto2*cotizacionPlomoTonelada/10000
        log.error("CALCULO 2: PLOMO -> porcentaje1: ${porcentaje1} porcentaje2: ${porcentaje2} vpt1: ${vpt1} vpt2: ${vpt2} ")
        def precioToneladaPlomo=getPuntoRecta(params.porcentajePlomo.toString().toBigDecimal(),porcentaje1,porcentaje2,vpt1,vpt2)

        render([vptPb: precioToneladaPlomo] as JSON)
    }

    def getValorPorToneladaPlata2(RecepcionDeComplejo recepcionDeComplejo, Long tablaId, BigDecimal porcentajePlata){
        if (porcentajePlata==0)
            return 0

        def tablaPrecios = TablaOrigenCotizacionesComplejo.get(tablaId)

        def datosPlataString = tablaPrecios.datosPlata
        def datosPlataArray = JSON.parse(datosPlataString)
        def pos=0
        def porcentajePlataRecuperado=0
        def filaPlata = null
        //def cotizacionPlataTonelada = recepcionDeComplejo.cotizacionDiariaDeMinerales.plata*2.2046223*1000
        def cotizacionPlataTonelada = recepcionDeComplejo.cotizacionDiariaDeMinerales.plata
        while (porcentajePlata>porcentajePlataRecuperado&&pos<50){
            filaPlata = JSON.parse(datosPlataArray[pos].toString())

            porcentajePlataRecuperado = filaPlata[0].toString().toBigDecimal()
            pos++
        }

        def porcentaje1 = (JSON.parse(datosPlataArray[pos-2].toString()))[0].toString().toBigDecimal()
        def porcentaje2 = (JSON.parse(datosPlataArray[pos-1].toString()))[0].toString().toBigDecimal()
        def porcentajeLME1 = (JSON.parse(datosPlataArray[pos-2].toString()))[1].toString().toBigDecimal()
        def porcentajeLME2 = (JSON.parse(datosPlataArray[pos-1].toString()))[1].toString().toBigDecimal()
//        def vpt1 = (JSON.parse(datosPlataArray[pos-2].toString()))[3].toString().toBigDecimal()
//        def vpt2 = (JSON.parse(datosPlataArray[pos-1].toString()))[3].toString().toBigDecimal()

//        def vpt1 = porcentaje1*porcentajeLME1*cotizacionPlataTonelada/10000
        def vpt1 = porcentaje1*porcentajeLME1/100*cotizacionPlataTonelada*100/31.1035
//        def vpt2 = porcentaje2*porcentajeLME2*cotizacionPlataTonelada/10000
        def vpt2 = porcentaje2*porcentajeLME2/100*cotizacionPlataTonelada*100/31.1035

        log.error("CALCULO 2: PLATA -> porcentaje1: ${porcentaje1} porcentaje2: ${porcentaje2} porcentajeLME1: ${porcentajeLME1} porcentajeLME2: ${porcentajeLME2} vpt1: ${vpt1} vpt2: ${vpt2} ")

        def precioToneladaPlata=0
        if(porcentaje2==porcentajePlata)
            precioToneladaPlata=vpt2
        else
            precioToneladaPlata=getPuntoRecta(porcentajePlata,porcentaje1,porcentaje2,vpt1,vpt2)

        return precioToneladaPlata
    }

    def getValorPorToneladaPlataCotizacion(CotizacionDiariaDeMinerales cotizacionDiariaDeMinerales, Long tablaId, BigDecimal porcentajePlata){
        if (porcentajePlata==0)
            return 0

        def tablaPrecios = TablaOrigenCotizacionesComplejo.get(tablaId)

        def datosPlataString = tablaPrecios.datosPlata
        def datosPlataArray = JSON.parse(datosPlataString)
        def pos=0
        def porcentajePlataRecuperado=0
        def filaPlata = null
//        def cotizacionPlataTonelada = cotizacionDiariaDeMinerales.plata*2.2046223*1000
        def cotizacionPlataTonelada = cotizacionDiariaDeMinerales.plata
        while (porcentajePlata>porcentajePlataRecuperado&&pos<50){
            filaPlata = JSON.parse(datosPlataArray[pos].toString())

            porcentajePlataRecuperado = filaPlata[0].toString().toBigDecimal()
            pos++
        }

        def porcentaje1 = (JSON.parse(datosPlataArray[pos-2].toString()))[0].toString().toBigDecimal()
        def porcentaje2 = (JSON.parse(datosPlataArray[pos-1].toString()))[0].toString().toBigDecimal()
        def porcentajeLME1 = (JSON.parse(datosPlataArray[pos-2].toString()))[1].toString().toBigDecimal()
        def porcentajeLME2 = (JSON.parse(datosPlataArray[pos-1].toString()))[1].toString().toBigDecimal()
//        def vpt1 = (JSON.parse(datosPlataArray[pos-2].toString()))[3].toString().toBigDecimal()
//        def vpt2 = (JSON.parse(datosPlataArray[pos-1].toString()))[3].toString().toBigDecimal()

//        def vpt1 = porcentaje1*porcentajeLME1*cotizacionPlataTonelada/10000
        def vpt1 = porcentaje1*porcentajeLME1/100*cotizacionPlataTonelada*100/31.1035
//        def vpt2 = porcentaje2*porcentajeLME2*cotizacionPlataTonelada/10000
        def vpt2 = porcentaje2*porcentajeLME2/100*cotizacionPlataTonelada*100/31.1035

        log.error("CALCULO 2: PLATA -> porcentaje1: ${porcentaje1} porcentaje2: ${porcentaje2} porcentajeLME1: ${porcentajeLME1} porcentajeLME2: ${porcentajeLME2} vpt1: ${vpt1} vpt2: ${vpt2} ")

        def precioToneladaPlata=getPuntoRecta(porcentajePlata,porcentaje1,porcentaje2,vpt1,vpt2)

        return precioToneladaPlata
    }

    def getVPTPlata2 = {
        if (params.porcentajePlata.toString().toBigDecimal()==0)
            render([vptAg: 0] as JSON)

        def tablaPrecios = TablaOrigenCotizacionesComplejo.get(params.tablaId)
        def recepcionDeComplejo = RecepcionDeComplejo.get(params.recepcionId.toLong())
//        def cotizacionPlataTonelada = recepcionDeComplejo.cotizacionDiariaDeMinerales.plata*2.2046223*1000
        def cotizacionPlataTonelada = recepcionDeComplejo.cotizacionDiariaDeMinerales.plata

        def datosPlataString = tablaPrecios.datosPlata
        def datosPlataArray = JSON.parse(datosPlataString)
        def pos=0
        def porcentajePlataRecuperado=0
        def filaPlata = null
        while (params.porcentajePlata.toString().toBigDecimal()>porcentajePlataRecuperado&&pos<50){
            //log.error("***** porcentajePlataRecuperado=${porcentajePlataRecuperado} cuando pos=${pos}")
            filaPlata = JSON.parse(datosPlataArray[pos].toString())
            porcentajePlataRecuperado = filaPlata[0].toString().toBigDecimal()
            pos++
        }

        //log.error("***** final ciclo pos=${pos}")
        def porcentaje1 = (JSON.parse(datosPlataArray[pos-2].toString()))[0].toString().toBigDecimal()
        def porcentaje2 = (JSON.parse(datosPlataArray[pos-1].toString()))[0].toString().toBigDecimal()
        def porcentajeLME1 = (JSON.parse(datosPlataArray[pos-2].toString()))[1].toString().toBigDecimal()
        def porcentajeLME2 = (JSON.parse(datosPlataArray[pos-1].toString()))[1].toString().toBigDecimal()
//        def vpt1 = (JSON.parse(datosPlataArray[pos-2].toString()))[3].toString().toBigDecimal()
//        def vpt2 = (JSON.parse(datosPlataArray[pos-1].toString()))[3].toString().toBigDecimal()

//        def vpt1 = porcentaje1*porcentajeLME1*cotizacionPlataTonelada/10000
        def vpt1 = porcentaje1*porcentajeLME1/100*cotizacionPlataTonelada*100/31.1035
//        def vpt2 = porcentaje2*porcentajeLME2*cotizacionPlataTonelada/10000
        def vpt2 = porcentaje2*porcentajeLME2/100*cotizacionPlataTonelada*100/31.1035

        log.error("CALCULO 2: PLATA -> porcentaje1: ${porcentaje1} porcentaje2: ${porcentaje2} porcentajeLME1: ${porcentajeLME1} porcentajeLME2: ${porcentajeLME2} vpt1: ${vpt1} vpt2: ${vpt2} ")

        def precioToneladaPlata=getPuntoRecta(porcentajePlata,porcentaje1,porcentaje2,vpt1,vpt2)

        render([vptAg: precioToneladaPlata] as JSON)
    }

    def getVPTZinc(Long empresaId, BigDecimal porcentajeZinc){
        def tablaPrecios = TablaOrigenCotizacionesComplejo.findByEmpresa(Empresa.get(empresaId))

        File temporal = new File("temp.xls")
        temporal.setBytes(tablaPrecios.datosArchivo)

        Workbook workbook = Workbook.getWorkbook(temporal);
        Sheet hojaZinc = workbook.getSheet(1)

        def pos=3
        def porcentajeZincRecuperado=0
        def precioToneladaZinc=0
        def porcentaje1=0
        def porcentaje2=0
        def vpt1=0
        def vpt2=0

        while (porcentajeZinc>porcentajeZincRecuperado){
            porcentajeZincRecuperado =(!hojaZinc.getCell(0,pos).contents.replace('%','').isNumber())?0:hojaZinc.getCell(0,pos).contents.replace('%','').toDouble()
            pos++
        }

        porcentaje1 =(!hojaZinc.getCell(0,pos-2).contents.replace('%','').isNumber())?0:hojaZinc.getCell(0,pos-2).contents.replace('%','').toFloat()
        porcentaje2 =(!hojaZinc.getCell(0,pos-1).contents.replace('%','').isNumber())?0:hojaZinc.getCell(0,pos-1).contents.replace('%','').toFloat()
        vpt1 =(!hojaZinc.getCell(3,pos-2).contents.replace('%','').isNumber())?0:hojaZinc.getCell(3,pos-2).contents.replace('%','').toFloat()
        vpt2 =(!hojaZinc.getCell(3,pos-1).contents.replace('%','').isNumber())?0:hojaZinc.getCell(3,pos-1).contents.replace('%','').toFloat()

        log.error("CALCULO 1: ZINC -> porcentaje1: ${porcentaje1} porcentaje2: ${porcentaje2} vpt1: ${vpt1} vpt2: ${vpt2} ")

        precioToneladaZinc=getPuntoRecta(porcentajeZinc,porcentaje1,porcentaje2,vpt1,vpt2)

        return precioToneladaZinc
    }

    def getVPTPlomo(Long empresaId, BigDecimal porcentajePlomo){
        def tablaPrecios = TablaOrigenCotizacionesComplejo.findByEmpresa(Empresa.get(empresaId))

        File temporal = new File("temp.xls")
        temporal.setBytes(tablaPrecios.datosArchivo)

        Workbook workbook = Workbook.getWorkbook(temporal);
        Sheet hojaPlomo = workbook.getSheet(2)

        def pos=3
        def porcentajePlomoRecuperado=0
        def precioToneladaPlomo=0
        def porcentaje1=0
        def porcentaje2=0
        def vpt1=0
        def vpt2=0

        while (porcentajePlomo>porcentajePlomoRecuperado){
            porcentajePlomoRecuperado =(!hojaPlomo.getCell(0,pos).contents.replace('%','').isNumber())?0:hojaPlomo.getCell(0,pos).contents.replace('%','').toDouble()
            pos++
        }

        porcentaje1 =(!hojaPlomo.getCell(0,pos-2).contents.replace('%','').isNumber())?0:hojaPlomo.getCell(0,pos-2).contents.replace('%','').toFloat()
        porcentaje2 =(!hojaPlomo.getCell(0,pos-1).contents.replace('%','').isNumber())?0:hojaPlomo.getCell(0,pos-1).contents.replace('%','').toFloat()
        vpt1 =(!hojaPlomo.getCell(3,pos-2).contents.replace('%','').isNumber())?0:hojaPlomo.getCell(3,pos-2).contents.replace('%','').toFloat()
        vpt2 =(!hojaPlomo.getCell(3,pos-1).contents.replace('%','').isNumber())?0:hojaPlomo.getCell(3,pos-1).contents.replace('%','').toFloat()

        log.error("CALCULO 1: PLOMO -> porcentaje1: ${porcentaje1} porcentaje2: ${porcentaje2} vpt1: ${vpt1} vpt2: ${vpt2} ")

        precioToneladaPlomo=getPuntoRecta(porcentajePlomo,porcentaje1,porcentaje2,vpt1,vpt2)

        return precioToneladaPlomo
    }

    def getVPTPlata(Long empresaId, BigDecimal porcentajePlata){
        def tablaPrecios = TablaOrigenCotizacionesComplejo.findByEmpresa(Empresa.get(empresaId))

        File temporal = new File("temp.xls")
        temporal.setBytes(tablaPrecios.datosArchivo)

        Workbook workbook = Workbook.getWorkbook(temporal);
        Sheet hojaPlata = workbook.getSheet(3)

        def pos=3
        def porcentajePlataRecuperado=0
        def precioToneladaPlata=0
        def porcentaje1=0
        def porcentaje2=0
        def vpt1=0
        def vpt2=0

        while (porcentajePlata>porcentajePlataRecuperado){
            porcentajePlataRecuperado =(!hojaPlata.getCell(0,pos).contents.replace('%','').isNumber())?0:hojaPlata.getCell(0,pos).contents.replace('%','').toDouble()
            pos++
        }

        porcentaje1 =(!hojaPlata.getCell(0,pos-2).contents.replace('%','').isNumber())?0:hojaPlata.getCell(0,pos-2).contents.replace('%','').toFloat()
        porcentaje2 =(!hojaPlata.getCell(0,pos-1).contents.replace('%','').isNumber())?0:hojaPlata.getCell(0,pos-1).contents.replace('%','').toFloat()
        vpt1 =(!hojaPlata.getCell(3,pos-2).contents.replace('%','').isNumber())?0:hojaPlata.getCell(3,pos-2).contents.replace('%','').toFloat()
        vpt2 =(!hojaPlata.getCell(3,pos-1).contents.replace('%','').isNumber())?0:hojaPlata.getCell(3,pos-1).contents.replace('%','').toFloat()

        log.error("CALCULO 1: PLATA -> porcentaje1: ${porcentaje1} porcentaje2: ${porcentaje2} vpt1: ${vpt1} vpt2: ${vpt2} ")

        precioToneladaPlata=getPuntoRecta(porcentajePlata,porcentaje1,porcentaje2,vpt1,vpt2)

        return precioToneladaPlata
    }

    def getPuntoRecta(BigDecimal ley, BigDecimal ley1, BigDecimal ley2, BigDecimal cot1, BigDecimal cot2){
        return (cot2-cot1)*(ley-ley1)/(ley2-ley1)+cot1
    }

    def filtrarTablas = {
        def empresa = Empresa.get(params.empresaId.toString().toLong())
        def tablas = TablaOrigenCotizacionesComplejo.findAllByEmpresa(empresa)
        //<g:select id="tablaComplejo" name="tablaComplejo.id" from="${org.socymet.cotizaciones.TablaOrigenCotizacionesComplejo.list()}" optionKey="id" value="${controlCalidadComplejoInstance?.tablaComplejo?.id}" class="many-to-one"/>
        //render g.select(name: "tablaComplejo.id",id: "tablaComplejo",from: tablas,optionKey: "id",value: "${controlCalidadComplejoInstance?.tablaComplejo?.id}",class: "many-to-one")
        //render g.select(name: "tablaComplejo.id",id: "tablaComplejo",from: tablas,optionKey: "id",class: "many-to-one")
        def tablasIds = []
        tablas.each {
            tablasIds.add(it.id)
        }
        render tablasIds as JSON
    }

//    def getTablasIds = { empresaId,naturalezaMineral ->
//        def empresa = Empresa.get(empresaId.toString().toLong())
//        def tablas = TablaOrigenCotizacionesComplejo.findAllByEmpresaAndNaturalezaMineral(empresa,naturalezaMineral,[sort: "id"])
//        def ids=""
//        tablas.each {
//            ids=ids+it.id+"-"
//        }
//        return ids
//    }

    def getTablasIds = { naturalezaMineral ->
        def tablas = TablaOrigenCotizacionesComplejo.findAllByNaturalezaMineral(naturalezaMineral,[sort: "id"])
        def ids=""
        tablas.each {
            ids=ids+it.id+"-"
        }
        return ids
    }
}
