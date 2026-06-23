package org.socymet.reportesAcopiadoras

import jxl.Workbook
import jxl.format.Alignment
import jxl.write.DateFormat
import jxl.write.DateTime
import jxl.write.Label
import jxl.write.Number
import jxl.write.NumberFormat
import jxl.write.WritableCellFormat
import jxl.write.WritableFont
import jxl.write.WritableSheet
import jxl.write.WritableWorkbook
import org.socymet.anticipos.AnticipoContraEntrega
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.seguridad.SecUser
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_RECEPCION','ROLE_LIQUIDACION'])
class LotesRecepcionadosController {
    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond LotesRecepcionados.list(params), model:[lotesRecepcionadosInstanceCount: LotesRecepcionados.count()]
    }

    def show(LotesRecepcionados lotesRecepcionadosInstance) {
        respond lotesRecepcionadosInstance
    }

    def create() {
        respond new LotesRecepcionados(params)
    }

    @Transactional
    def save(LotesRecepcionados lotesRecepcionadosInstance) {
        if (lotesRecepcionadosInstance == null) {
            notFound()
            return
        }

        if (lotesRecepcionadosInstance.hasErrors()) {
            respond lotesRecepcionadosInstance.errors, view:'create'
            return
        }

        lotesRecepcionadosInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'lotesRecepcionados.label', default: 'LotesRecepcionados'), lotesRecepcionadosInstance.id])
                redirect lotesRecepcionadosInstance
            }
            '*' { respond lotesRecepcionadosInstance, [status: CREATED] }
        }
    }

    def edit(LotesRecepcionados lotesRecepcionadosInstance) {
        respond lotesRecepcionadosInstance
    }

    @Transactional
    def update(LotesRecepcionados lotesRecepcionadosInstance) {
        if (lotesRecepcionadosInstance == null) {
            notFound()
            return
        }

        if (lotesRecepcionadosInstance.hasErrors()) {
            respond lotesRecepcionadosInstance.errors, view:'edit'
            return
        }

        lotesRecepcionadosInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'LotesRecepcionados.label', default: 'LotesRecepcionados'), lotesRecepcionadosInstance.id])
                redirect lotesRecepcionadosInstance
            }
            '*'{ respond lotesRecepcionadosInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(LotesRecepcionados lotesRecepcionadosInstance) {

        if (lotesRecepcionadosInstance == null) {
            notFound()
            return
        }

        lotesRecepcionadosInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'LotesRecepcionados.label', default: 'LotesRecepcionados'), lotesRecepcionadosInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'lotesRecepcionados.label', default: 'LotesRecepcionados'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def getEmpresasSegunUsuario() {
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        render text: g.select(id: 'empresa', name: 'empresa.id', from: Empresa.findAllByDeposito(usuarioActual.deposito), optionKey: "id")
    }

    def crearReporteComplejo = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.TAHOMA, 7, WritableFont.NO_BOLD);
        WritableFont courier6PlainFont = new WritableFont(WritableFont.TAHOMA, 7, WritableFont.NO_BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.TAHOMA, 7, WritableFont.NO_BOLD);
        WritableFont courier8BoldFont = new WritableFont(WritableFont.TAHOMA, 7, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.NO_BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.TAHOMA, 16, WritableFont.NO_BOLD);

        WritableCellFormat formatoEncabezado = new WritableCellFormat (arial10BoldFont);
        formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)
        formatoEncabezado.setWrap(true)
        //formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
        formatoEncabezado.setAlignment(Alignment.CENTRE)

        WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoDatos.setFont(courier8PlainFont)
        formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        WritableCellFormat formatoTotales = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoTotales.setFont(courier8BoldFont)
        formatoTotales.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)

        WritableCellFormat formatoInfoReporte = new WritableCellFormat (arial14BoldFont);
        WritableCellFormat formatoTitulo = new WritableCellFormat (arial16BoldFont);

        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        WritableSheet sheet1 = workbook.createSheet("Reporte de Lotes Recepcionados", 0)

        for(i in 0..100)
            sheet1.setColumnView(i,12)
        sheet1.setColumnView(2,30)
        sheet1.setColumnView(3,30)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="reporte_lotes_recepcionados.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(2,0, "REPORTE DE RECEPCION",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        def deposito = usuarioActual.deposito
        def estado = params.estado.toString()
        def tipoReporte = ""+params.tipoReporte
        def empresa=null
        def fechaInicial=null
        def fechaFinal=null
        def loteInicial=0
        def loteFinal=0

        def recepcionesComplejo=null

        sheet1.addCell(new Label(0,2, "ELEMENTO: COMPLEJO",formatoInfoReporte))
        sheet1.addCell(new Label(0,3, "DEPOSITO: ${deposito}",formatoInfoReporte))

        if (tipoReporte.equals("fechas")){
            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(0,4, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            if (estado.equals("Todos"))
                recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetweenAndTipoDeMineralAndDeposito(fechaInicial,fechaFinal,"COMPLEJO",deposito)
            else
                recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetweenAndTipoDeMineralAndEstadoDelLoteAndDeposito(fechaInicial,fechaFinal,"COMPLEJO",estado,deposito)
        }
        if (tipoReporte.equals("fechasEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(0,5, "EMPRESA: ${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(0,4, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))

            if (estado.equals("Todos"))
                recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetweenAndEmpresaAndTipoDeMineralAndDeposito(fechaInicial,fechaFinal,empresa,"COMPLEJO",deposito)
            else
                recepcionesComplejo = RecepcionDeComplejo.findAllByFechaDeRecepcionBetweenAndEmpresaAndTipoDeMineralAndEstadoDelLoteAndDeposito(fechaInicial,fechaFinal,empresa,"COMPLEJO",estado,deposito)
        }

        sheet1.addCell(new Label(0,7, "FEC. REC.",formatoEncabezado))
        sheet1.addCell(new Label(1,7, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(2,7, "PROCEDENCIA",formatoEncabezado))
        sheet1.addCell(new Label(3,7, "PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(4,7, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(5,7, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(6,7, "ANTICIPO",formatoEncabezado))
        sheet1.addCell(new Label(7,7, "ESTADO",formatoEncabezado))

        def fila = 8

        def totalCantidadDeSacos=0
        def totalPesoBruto=0
        def totalAnticipo=0

        if(recepcionesComplejo){
            recepcionesComplejo.each {
                sheet1.addCell(new DateTime(0,fila, it.fechaDeRecepcion,formatoFecha))
                sheet1.addCell(new Label(1,fila, it.toString(),formatoDatos))
                sheet1.addCell(new Label(2,fila, it.empresa.toString(),formatoDatos))
                sheet1.addCell(new Label(3,fila, it.cliente.nombre,formatoDatos))
                sheet1.addCell(new Number(4,fila, Double.parseDouble(it.cantidadDeSacos),formatoDatos))
                sheet1.addCell(new Number(5,fila, it.pesoBruto,formatoDatos))
                def anticipo = AnticipoContraEntrega.findByRecepcionId(it.id)
                def montoAnticipo = (anticipo)?anticipo.importe:0
                sheet1.addCell(new Number(6,fila, montoAnticipo,formatoDatos))
                sheet1.addCell(new Label(7,fila, it.estadoDelLote,formatoDatos))

                totalCantidadDeSacos+=Double.parseDouble(it.cantidadDeSacos.toString())
                totalPesoBruto+=it.pesoBruto
                totalAnticipo+=montoAnticipo

                fila++
            }
            //llenado de totales
            sheet1.addCell(new Number(4,fila, totalCantidadDeSacos,formatoTotales))
            sheet1.addCell(new Number(5,fila, totalPesoBruto,formatoTotales))
            sheet1.addCell(new Number(6,fila, totalAnticipo,formatoTotales))
        }

        workbook.write();
        workbook.close();
    }
}
