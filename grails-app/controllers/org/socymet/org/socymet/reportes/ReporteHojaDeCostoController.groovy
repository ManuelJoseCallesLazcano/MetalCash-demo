package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import jxl.Workbook
import jxl.format.VerticalAlignment
import jxl.write.*
import org.socymet.liquidacion.*
import org.socymet.proveedor.Empresa
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class ReporteHojaDeCostoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reporteHojaDeCostoInstanceList: ReporteHojaDeCosto.list(params), reporteHojaDeCostoInstanceTotal: ReporteHojaDeCosto.count()]
    }

    def create() {
        [reporteHojaDeCostoInstance: new ReporteHojaDeCosto(params)]
    }

    def save() {
        def reporteHojaDeCostoInstance = new ReporteHojaDeCosto(params)
        if (!reporteHojaDeCostoInstance.save(flush: true)) {
            render(view: "create", model: [reporteHojaDeCostoInstance: reporteHojaDeCostoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reporteHojaDeCosto.label', default: 'ReporteHojaDeCosto'), reporteHojaDeCostoInstance.id])
        redirect(action: "show", id: reporteHojaDeCostoInstance.id)
    }

    def show(Long id) {
        def reporteHojaDeCostoInstance = ReporteHojaDeCosto.get(id)
        if (!reporteHojaDeCostoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteHojaDeCosto.label', default: 'ReporteHojaDeCosto'), id])
            redirect(action: "list")
            return
        }

        [reporteHojaDeCostoInstance: reporteHojaDeCostoInstance]
    }

    def edit(Long id) {
        def reporteHojaDeCostoInstance = ReporteHojaDeCosto.get(id)
        if (!reporteHojaDeCostoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteHojaDeCosto.label', default: 'ReporteHojaDeCosto'), id])
            redirect(action: "list")
            return
        }

        [reporteHojaDeCostoInstance: reporteHojaDeCostoInstance]
    }

    def update(Long id, Long version) {
        def reporteHojaDeCostoInstance = ReporteHojaDeCosto.get(id)
        if (!reporteHojaDeCostoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteHojaDeCosto.label', default: 'ReporteHojaDeCosto'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reporteHojaDeCostoInstance.version > version) {
                reporteHojaDeCostoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reporteHojaDeCosto.label', default: 'ReporteHojaDeCosto')] as Object[],
                        "Another user has updated this ReporteHojaDeCosto while you were editing")
                render(view: "edit", model: [reporteHojaDeCostoInstance: reporteHojaDeCostoInstance])
                return
            }
        }

        reporteHojaDeCostoInstance.properties = params

        if (!reporteHojaDeCostoInstance.save(flush: true)) {
            render(view: "edit", model: [reporteHojaDeCostoInstance: reporteHojaDeCostoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reporteHojaDeCosto.label', default: 'ReporteHojaDeCosto'), reporteHojaDeCostoInstance.id])
        redirect(action: "show", id: reporteHojaDeCostoInstance.id)
    }

    def delete(Long id) {
        def reporteHojaDeCostoInstance = ReporteHojaDeCosto.get(id)
        if (!reporteHojaDeCostoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteHojaDeCosto.label', default: 'ReporteHojaDeCosto'), id])
            redirect(action: "list")
            return
        }

        try {
            //reestablecer el nombre de conjunto a '-' para retornar todos estos lotes a existencia
            def liquidaciones = null
            if (reporteHojaDeCostoInstance.elemento.equals("Estano")){
                liquidaciones = LiquidacionDeEstano.findAllByConjuntoEstano(reporteHojaDeCostoInstance.nombreDelConjunto)
                System.out.println("*** SE VAN A ACTUALIZAR ${liquidaciones.size()} REGISTROS.")
                if (liquidaciones)
                    liquidaciones.each {
                        System.out.println("ACTUALIZANDO REGISTRO: ${it.lote} DEL CONJUNTO: ${it.conjuntoEstano}")
                        it.conjuntoEstano="-"
                        it.save(failOnError: true)
                    }
            }

            if (reporteHojaDeCostoInstance.elemento.equals("Plata")){
                liquidaciones = LiquidacionDePlata.findAllByConjuntoPlata(reporteHojaDeCostoInstance.nombreDelConjunto)
                System.out.println("*** SE VAN A ACTUALIZAR ${liquidaciones.size()} REGISTROS.")
                if (liquidaciones)
                    liquidaciones.each {
                        System.out.println("ACTUALIZANDO REGISTRO: ${it.lote} DEL CONJUNTO: ${it.conjuntoPlata}")
                        it.conjuntoPlata="-"
                        it.save(failOnError: true)
                    }
            }

            if (reporteHojaDeCostoInstance.elemento.equals("Wolfran")){
                liquidaciones = LiquidacionDeWolfran.findAllByConjuntoWolfran(reporteHojaDeCostoInstance.nombreDelConjunto)
                System.out.println("*** SE VAN A ACTUALIZAR ${liquidaciones.size()} REGISTROS.")
                if (liquidaciones)
                    liquidaciones.each {
                        System.out.println("ACTUALIZANDO REGISTRO: ${it.lote} DEL CONJUNTO: ${it.conjuntoWolfran}")
                        it.conjuntoWolfran="-"
                        it.save(failOnError: true)
                    }
            }

            if (reporteHojaDeCostoInstance.elemento.equals("Antimonio")){
                liquidaciones = LiquidacionDeAntimonio.findAllByConjuntoAntimonio(reporteHojaDeCostoInstance.nombreDelConjunto)
                System.out.println("*** SE VAN A ACTUALIZAR ${liquidaciones.size()} REGISTROS.")
                if (liquidaciones)
                    liquidaciones.each {
                        System.out.println("ACTUALIZANDO REGISTRO: ${it.lote} DEL CONJUNTO: ${it.conjuntoAntimonio}")
                        it.conjuntoAntimonio="-"
                        it.save(failOnError: true)
                    }
            }

            if (reporteHojaDeCostoInstance.elemento.equals("Complejo")){
                liquidaciones = LiquidacionDeComplejo.findAllByConjuntoComplejo(reporteHojaDeCostoInstance.nombreDelConjunto)
                System.out.println("*** SE VAN A ACTUALIZAR ${liquidaciones.size()} REGISTROS.")
                if (liquidaciones)
                    liquidaciones.each {
                        System.out.println("ACTUALIZANDO REGISTRO: ${it.lote} DEL CONJUNTO: ${it.conjuntoComplejo}")
                        it.conjuntoComplejo="-"
                        it.save(failOnError: true)
                    }
            }

            reporteHojaDeCostoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reporteHojaDeCosto.label', default: 'ReporteHojaDeCosto'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reporteHojaDeCosto.label', default: 'ReporteHojaDeCosto'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporteEstano = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Hoja de Costo de Estano", 0)
        sheet1.setColumnView(0,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(1,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(2,11)
        sheet1.setColumnView(3,40)
        sheet1.setColumnView(4,40)
        sheet1.setRowView(6,500)
        for(i in 5..100)
            sheet1.setColumnView(i,15)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD);
        WritableCellFormat formatoEncabezado = new WritableCellFormat (arial10BoldFont);
        formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)
        formatoEncabezado.setWrap(true)
        formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
        //formatoEncabezado.setAlignment(Alignment.CENTRE)
        WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoDatos.setFont(courier8PlainFont)
        formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)
        WritableCellFormat formatoInfoReporte = new WritableCellFormat (arial14BoldFont);
        WritableCellFormat formatoTitulo = new WritableCellFormat (arial16BoldFont);
        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="hoja_costo_estano.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(3,0, "HOJA DE COSTO DE ESTAÑO",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def tipoReporte = ""+params.tipoReporte
        def asignarConjuntoALotes = ""+params.asignarConjuntoALotes
        def nombreDelConjunto = ""+params.nombreDelConjunto
        def destinoDelConjunto = ""+params.destinoDelConjunto

        def ignorarLotes = ""+params.ignorarLotes
        def lotesIgnorados = ignorarLotes.tokenize(',')

        def empresa=null
        def fechaInicial=null
        def fechaFinal=null
        def leyMinimaEstano=null
        def leyMaximaEstano=null
        def loteInicial=""
        def loteFinal=""

        sheet1.addCell(new Label(3,1, "NOMBRE DEL CONJUNTO:",formatoInfoReporte))
        sheet1.addCell(new Label(4,1, "${nombreDelConjunto}",formatoInfoReporte))
        sheet1.addCell(new Label(3,2, "DESTINO:",formatoInfoReporte))
        sheet1.addCell(new Label(4,2, "${destinoDelConjunto}",formatoInfoReporte))

        def liquidacionesEstano = null

        if (tipoReporte.equals("fechas")){
            //empresa = Empresa.get(Integer.parseInt(""+params.empresa.id))

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)
            leyMinimaEstano = ""+params.leyMinimaEstano
            leyMinimaEstano = (leyMinimaEstano.equals(""))?0:Float.parseFloat(params.leyMinimaEstano)
            leyMaximaEstano = ""+params.leyMaximaEstano
            leyMaximaEstano = (leyMaximaEstano.equals(""))?0:Float.parseFloat(params.leyMaximaEstano)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(5,1, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))
            sheet1.addCell(new Label(5,2, "ENTRE LEYES: ${leyMinimaEstano}% AL ${leyMaximaEstano}%",formatoInfoReporte))

            //liquidacionesEstano = LiquidacionDeEstano.findAllByFechaDeLiquidacionBetweenAndPorcentajeEstanoBetweenAndNombreEmpresaLikeAndConjuntoEstano(fechaInicial,fechaFinal,leyMinimaEstano,leyMaximaEstano,"%${empresa.nombreDeEmpresa}%","-")

            def liquidaciones = LiquidacionDeEstano.findAllByFechaDeLiquidacionBetweenAndPorcentajeEstanoBetweenAndNombreEmpresaLikeAndConjuntoEstano(fechaInicial,fechaFinal,leyMinimaEstano,leyMaximaEstano,"%%","-")
            //def liquidaciones = LiquidacionDeEstano.findAllByConjuntoEstano("SOMET 20/14 (COMERCIALIZACION)")

            liquidacionesEstano=new ArrayList<LiquidacionDeEstano>()
            liquidaciones.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesEstano.add(it)
            }

            System.out.println("*** ENTRE FECHAS: ${fechaInicial} - ${fechaFinal} ENTRE LEYES: ${leyMinimaEstano} - ${leyMaximaEstano}")
            System.out.println("*** RESULTADOS ENCONTRADOS: ${liquidaciones.size()} RESULTADOS DEPURADOS: ${liquidacionesEstano.size()}")
        }
        if (tipoReporte.equals("fechasEmpresa")){
            empresa = Empresa.get(Integer.parseInt(""+params.empresa.id))

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)
            leyMinimaEstano = ""+params.leyMinimaEstano
            leyMinimaEstano = (leyMinimaEstano.equals(""))?0:Float.parseFloat(params.leyMinimaEstano)
            leyMaximaEstano = ""+params.leyMaximaEstano
            leyMaximaEstano = (leyMaximaEstano.equals(""))?0:Float.parseFloat(params.leyMaximaEstano)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(3,3, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(4,3, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(5,1, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))
            sheet1.addCell(new Label(5,2, "ENTRE LEYES: ${leyMinimaEstano}% AL ${leyMaximaEstano}%",formatoInfoReporte))

            //liquidacionesEstano = LiquidacionDeEstano.findAllByFechaDeLiquidacionBetweenAndPorcentajeEstanoBetweenAndNombreEmpresaLikeAndConjuntoEstano(fechaInicial,fechaFinal,leyMinimaEstano,leyMaximaEstano,"%${empresa.nombreDeEmpresa}%","-")

            def liquidaciones = LiquidacionDeEstano.findAllByFechaDeLiquidacionBetweenAndPorcentajeEstanoBetweenAndConjuntoEstano(fechaInicial,fechaFinal,leyMinimaEstano,leyMaximaEstano,"-")

            def liquidaciones1 = new ArrayList<LiquidacionDeEstano>()
            liquidaciones.each {
                if(it.recepcionDeEstano.empresa.id==empresa.id){
                    liquidaciones1.add(it)
                }
            }

            liquidacionesEstano=new ArrayList<LiquidacionDeEstano>()
            liquidaciones1.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesEstano.add(it)
            }
        }
        if (tipoReporte.equals("lotes")){
            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            leyMinimaEstano = ""+params.leyMinimaEstano
            leyMinimaEstano = (leyMinimaEstano.equals(""))?0:Float.parseFloat(params.leyMinimaEstano)
            leyMaximaEstano = ""+params.leyMaximaEstano
            leyMaximaEstano = (leyMaximaEstano.equals(""))?0:Float.parseFloat(params.leyMaximaEstano)

            def liquidaciones1 = LiquidacionDeEstano.findAllByPorcentajeEstanoBetweenAndConjuntoEstano(leyMinimaEstano,leyMaximaEstano,"-")

            def liquidaciones2=new ArrayList<LiquidacionDeEstano>()
            liquidaciones1.each {
                def lote = Integer.parseInt(it.lote)
                if (lote>=loteInicial&&lote<=loteFinal)
                    liquidaciones2.add(it)
            }

            liquidacionesEstano=new ArrayList<LiquidacionDeEstano>()
            liquidaciones2.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesEstano.add(it)
            }

            sheet1.addCell(new Label(5,1, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))
            sheet1.addCell(new Label(5,2, "ENTRE LEYES: ${leyMinimaEstano}% AL ${leyMaximaEstano}%",formatoInfoReporte))
        }
        if (tipoReporte.equals("lotesEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            leyMinimaEstano = ""+params.leyMinimaEstano
            leyMinimaEstano = (leyMinimaEstano.equals(""))?0:Float.parseFloat(params.leyMinimaEstano)
            leyMaximaEstano = ""+params.leyMaximaEstano
            leyMaximaEstano = (leyMaximaEstano.equals(""))?0:Float.parseFloat(params.leyMaximaEstano)

            def liquidaciones1 = LiquidacionDeEstano.findAllByPorcentajeEstanoBetweenAndConjuntoEstano(leyMinimaEstano,leyMaximaEstano,"-")

            def liquidaciones2=new ArrayList<LiquidacionDeEstano>()
            liquidaciones1.each {
                def lote = Integer.parseInt(it.lote)
                if (lote>=loteInicial&&lote<=loteFinal && it.recepcionDeEstano.empresa.id==empresa.id)
                    liquidaciones2.add(it)
            }

            liquidacionesEstano=new ArrayList<LiquidacionDeEstano>()
            liquidaciones2.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesEstano.add(it)
            }

            sheet1.addCell(new Label(3,3, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(4,3, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(5,1, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))
            sheet1.addCell(new Label(5,2, "ENTRE LEYES: ${leyMinimaEstano}% AL ${leyMaximaEstano}%",formatoInfoReporte))
        }

        //liquidacionesEstano = LiquidacionDeEstano.list()

        sheet1.addCell(new Label(0,6, "RECEPCION",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "COT. DIA",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "LIQUIDACION",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "NOMBRE",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "TOTAL TARA",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "K. N. H.",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "% H2O",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "K. N. S.",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "LEY %Sn",formatoEncabezado))
        sheet1.addCell(new Label(13,6, "K. F. %Sn",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "COT. OFICIAL",formatoEncabezado))
        sheet1.addCell(new Label(15,6, "VALOR OF. BRUTO",formatoEncabezado))
        sheet1.addCell(new Label(16,6, "ALICUOTA %",formatoEncabezado))
        sheet1.addCell(new Label(17,6, "VALOR NETO \$us",formatoEncabezado))
        sheet1.addCell(new Label(18,6, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(19,6, "BONO CALIDAD",formatoEncabezado))
        sheet1.addCell(new Label(20,6, "BONO INCENTIVO",formatoEncabezado))
        sheet1.addCell(new Label(21,6, "VALOR DE COMPRA",formatoEncabezado))
        sheet1.addCell(new Label(22,6, "RM",formatoEncabezado))

        /*AGREGAR ESTE CONTROL PARA TODOS LOS ELEMENTOS, ES PARA CUANDO NO SE GENEREN RESULTADOS, AL PARECER CUANDO EL list
        * NO ENCUENTRA RESULTADOS DEVUELVE UN LIST null. ADICIONAR EL CODIGO EL EL GSP PARA QUE APAREZCA LA NOTIFICACION.*/
        if (!liquidacionesEstano) {
            flash.error = "NO SE PUDO OBTENER RESULTADOS!"
            System.out.println("*** SE ESTA PRODUCIENDO RESULTADOS NULL!!!")
            redirect(action: "create")
            return
        }


        if (liquidacionesEstano.size()==0 || nombreDelConjunto.equals("")){
            if (liquidacionesEstano.size()==0)
                sheet1.addCell(new Label(0,7, "SIN RESULTADOS",formatoInfoReporte))
            if (nombreDelConjunto.equals(""))
                sheet1.addCell(new Label(0,7, "ESPECIFIQUE NOMBRE DE CONJUNTO",formatoInfoReporte))
        }else{

            /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA RETENCIONES DE LEY*/
            def listaRetencionesDeLey = retencionesEstanoJSON liquidacionesEstano,"DE LEY"
            def columna = 23
            listaRetencionesDeLey.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL RET. DE LEY",formatoEncabezado))
            columna++

            /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA OTRAS RETENCIONES*/
            def listaRetencionesOtras = retencionesEstanoJSON liquidacionesEstano,"OTRA"
            listaRetencionesOtras.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL OTRAS RET.",formatoEncabezado))
            columna++

            sheet1.addCell(new Label(columna,6, "TOTAL RET.",formatoEncabezado))
            sheet1.addCell(new Label(columna+1,6, "TOTAL PAGADO",formatoEncabezado))
            sheet1.addCell(new Label(columna+2,6, "ANTICIPO/ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+3,6, "ANTICIPO/F. ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+4,6, "LIQUIDO PAGABLE",formatoEncabezado))
            sheet1.addCell(new Label(columna+5,6, "CANC. TRANSPORTE",formatoEncabezado))
            sheet1.addCell(new Label(columna+6,6, "CANC. LABORAT.",formatoEncabezado))

            //DESPLIEGUE DE DATOS DE LIQUIDACIONES
            //formatoEncabezado.setAlignment(Alignment.RIGHT)
            def fila = 7
            //variables acumuladoras
            def numeroRegistros=0
            def totalCantidadSacos=0
            def totalTotalTara=0
            def totalPesoBruto=0
            def totalKilosNetosHumedos=0
            def totalHumedad=0
            def totalKilosNetosSecos=0
            def totalKilosNetosSecosCotizacionDiaria=0
            def totalPorcentajeEstano=0
            def totalKilosFinosEstano=0
            def totalCotizacionQuincenalEstano=0
            def totalValorOficialBruto=0
            def totalCotizacionDiariaEstano=0
            def totalAlicuota=0
            def totalValorNeto=0
            def totalValorNetoBolivianos=0
            def totalBonoCalidad=0
            def totalBonoIncentivo=0
            def totalValorDeCompra=0
            def totalRegaliaMinera=0
            def totalTotalRetenciones=0
            def totalTotalPagado=0
            def totalTotalAnticiposContraEntrega=0
            def totalTotalAnticiposContraFuturaEntrega=0
            def totalTotalLiquidoPagable=0
            def totalCostoDeTransporte=0
            def totalTotalCostoLaboratorio=0

            def cuotaParticipacionEmpresa = new ArrayList<String>()
            def cuotaParticipacionCuota = new ArrayList<Integer>()

            liquidacionesEstano.each {
                numeroRegistros++
                totalCantidadSacos+=it.cantidadDeSacos
                totalTotalTara+=(it.tara*it.cantidadDeSacos)
                totalPesoBruto+=it.pesoBruto
                totalKilosNetosHumedos+=it.kilosNetosHumedos
                totalHumedad+=it.humedad
                totalKilosNetosSecos+=it.kilosNetosSecos
                totalKilosNetosSecosCotizacionDiaria+=(it.kilosNetosSecos*it.recepcionDeEstano.cotizacionDiariaDeMinerales.estano)
                totalPorcentajeEstano+=it.porcentajeEstano
                totalKilosFinosEstano+=it.kilosFinosEstano
                totalCotizacionQuincenalEstano+=it.recepcionDeEstano.cotizacionQuincenalDeMinerales.estano
                totalValorOficialBruto+=it.valorOficialBruto
                totalCotizacionDiariaEstano+=it.recepcionDeEstano.cotizacionDiariaDeMinerales.estano
                totalAlicuota+=it.recepcionDeEstano.alicuota.estano
                totalValorNeto+=it.valorNetoMineral
                totalValorNetoBolivianos+=it.valorNetoMineralEnBolivianos
                totalBonoCalidad+=it.bonoCalidad
                totalBonoIncentivo+=it.bonoIncentivo
                totalValorDeCompra+=it.valorDeCompra
                totalRegaliaMinera+=it.regaliaMinera
                totalTotalRetenciones+=it.totalRetenciones
                totalTotalPagado+=it.totalPagado
                totalTotalAnticiposContraEntrega+=it.totalAnticiposContraEntrega
                totalTotalAnticiposContraFuturaEntrega+=it.totalAnticiposContraFuturaEntrega
                totalTotalLiquidoPagable=totalTotalLiquidoPagable+((it.totalLiquidoPagable.doubleValue()<0)?0:it.totalLiquidoPagable.doubleValue())
                totalCostoDeTransporte+=it.recepcionDeEstano.costoDeTransporte
                totalTotalCostoLaboratorio+=it.totalCostoLaboratorio

                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new Number(1,fila, it.recepcionDeEstano.cotizacionDiariaDeMinerales.estano,formatoDatos))
                sheet1.addCell(new DateTime(2,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(3,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(4,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(5,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(6,fila, it.cantidadDeSacos,formatoDatos))
                sheet1.addCell(new Number(7,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(8,fila, it.tara*it.cantidadDeSacos,formatoDatos))
                sheet1.addCell(new Number(9,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(10,fila, it.humedad,formatoDatos))
                sheet1.addCell(new Number(11,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(12,fila, it.porcentajeEstano,formatoDatos))
                sheet1.addCell(new Number(13,fila, it.kilosFinosEstano,formatoDatos))
                sheet1.addCell(new Number(14,fila, it.recepcionDeEstano.cotizacionQuincenalDeMinerales.estano,formatoDatos))
                sheet1.addCell(new Number(15,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(16,fila, it.recepcionDeEstano.alicuota.estano,formatoDatos))
                sheet1.addCell(new Number(17,fila, it.valorNetoMineral,formatoDatos))
                sheet1.addCell(new Number(18,fila, it.valorNetoMineralEnBolivianos,formatoDatos))
                sheet1.addCell(new Number(19,fila, it.bonoCalidad,formatoDatos))
                sheet1.addCell(new Number(20,fila, it.bonoIncentivo,formatoDatos))
                sheet1.addCell(new Number(21,fila, it.valorDeCompra,formatoDatos))
                sheet1.addCell(new Number(22,fila, it.regaliaMinera,formatoDatos))

                columna = 23

                /*DESPLIEGUE DE RETENCIONES DE LEY*/
                def retencionesDeLeyLiquidacion = LiquidacionDeEstanoRetenciones.findAllByLiquidacionDeEstanoAndTipoDeRetencion(it,"DE LEY")
                def numretDeLey = retencionesDeLeyLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretDeLey} RETENCIONES DE LEY!")
                def subtotalRetencionesDeLey=it.regaliaMinera.doubleValue()
                for(int i=0;i<listaRetencionesDeLey.size();i++){
                    def vr = valorRetencion(listaRetencionesDeLey.get(i), retencionesDeLeyLiquidacion,numretDeLey)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesDeLey+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesDeLey,formatoDatos))
                columna++

                /*DESPLIEGUE DE RETENCIONES DE LEY*/
                def retencionesOtrasLiquidacion = LiquidacionDeEstanoRetenciones.findAllByLiquidacionDeEstanoAndTipoDeRetencion(it,"OTRA")
                def numretOtras = retencionesOtrasLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretOtras} RETENCIONES DE LEY!")
                def subtotalRetencionesOtras=0
                for(int i=0;i<listaRetencionesOtras.size();i++){
                    def vr = valorRetencion(listaRetencionesOtras.get(i), retencionesOtrasLiquidacion,numretOtras)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesOtras+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesOtras,formatoDatos))
                columna++

                sheet1.addCell(new Number(columna,fila, it.totalRetenciones,formatoDatos))
                sheet1.addCell(new Number(columna+1,fila, it.totalPagado,formatoDatos))
                sheet1.addCell(new Number(columna+2,fila, it.totalAnticiposContraEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+3,fila, it.totalAnticiposContraFuturaEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+4,fila, it.totalLiquidoPagable,formatoDatos))
                sheet1.addCell(new Number(columna+5,fila, it.recepcionDeEstano.costoDeTransporte,formatoDatos))
                sheet1.addCell(new Number(columna+6,fila, it.totalCostoLaboratorio,formatoDatos))

                fila++

                if (cuotaParticipacionEmpresa.contains(it.nombreEmpresa)){
                    def obj=cuotaParticipacionCuota.get(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa))
                    obj++
                    cuotaParticipacionCuota.set(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa),obj)
                }else{
                    cuotaParticipacionEmpresa.add(it.nombreEmpresa)
                    cuotaParticipacionCuota.add(1)
                }

            }

            //IMPRESION DE TOTALES
            sheet1.addCell(new Number(1,fila, totalKilosNetosSecosCotizacionDiaria/totalKilosNetosSecos,formatoDatos))
            sheet1.addCell(new Number(6,fila, totalCantidadSacos,formatoDatos))
            sheet1.addCell(new Number(7,fila, totalPesoBruto,formatoDatos))
            sheet1.addCell(new Number(8,fila, totalTotalTara,formatoDatos))
            sheet1.addCell(new Number(9,fila, totalKilosNetosHumedos,formatoDatos))
            //sheet1.addCell(new Number(10,fila, totalHumedad/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(10,fila, (totalKilosNetosSecos/totalKilosNetosHumedos*100-100)*-1,formatoDatos))
            sheet1.addCell(new Number(11,fila, totalKilosNetosSecos,formatoDatos))
            //sheet1.addCell(new Number(12,fila, totalPorcentajeEstano/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(12,fila, totalKilosFinosEstano/totalKilosNetosSecos*100,formatoDatos))
            sheet1.addCell(new Number(13,fila, totalKilosFinosEstano,formatoDatos))
            sheet1.addCell(new Number(14,fila, totalCotizacionQuincenalEstano/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(15,fila, totalValorOficialBruto,formatoDatos))
            sheet1.addCell(new Number(16,fila, totalAlicuota/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(17,fila, totalValorNeto,formatoDatos))
            sheet1.addCell(new Number(18,fila, totalValorNetoBolivianos,formatoDatos))
            sheet1.addCell(new Number(19,fila, totalBonoCalidad,formatoDatos))
            sheet1.addCell(new Number(20,fila, totalBonoIncentivo,formatoDatos))
            sheet1.addCell(new Number(21,fila, totalValorDeCompra,formatoDatos))
            sheet1.addCell(new Number(22,fila, totalRegaliaMinera,formatoDatos))

            def columnaFinalRetenciones = 25+listaRetencionesDeLey.size()+listaRetencionesOtras.size()
            def totalLiquidaciones = liquidacionesEstano.size()
            for (int col=23;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =7;fil<totalLiquidaciones+7;fil++){
                    def valor = Double.parseDouble(sheet1.getWritableCell(col,fil).contents)
                    tret+=valor
                }
                sheet1.addCell(new Number(col,totalLiquidaciones+7, tret,formatoDatos))
            }

            sheet1.addCell(new Number(columnaFinalRetenciones,fila, totalTotalRetenciones,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+1,fila, totalTotalPagado,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+2,fila, totalTotalAnticiposContraEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+3,fila, totalTotalAnticiposContraFuturaEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+4,fila, totalTotalLiquidoPagable,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+5,fila, totalCostoDeTransporte,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+6,fila, totalTotalCostoLaboratorio,formatoDatos))

            sheet1.mergeCells(22, 5, 23+listaRetencionesDeLey.size(), 5);
            sheet1.mergeCells(23+listaRetencionesDeLey.size(), 5, 25+listaRetencionesDeLey.size()+listaRetencionesOtras.size(), 5);
            sheet1.addCell(new Label(22,5, "RETENCIONES DE LEY",formatoEncabezado))
            sheet1.addCell(new Label(23+listaRetencionesDeLey.size(),5, "OTRAS RETENCIONES",formatoEncabezado))

            //IMPRESION DE DISTRIBUCION PORCENTUAL
            sheet1.mergeCells(4, fila+3, 5, fila+3);
            sheet1.addCell(new Label(4,fila+3,"VOLUMEN DE PARTICIPACION POR EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(4,fila+4, "EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(5,fila+4, "PORCENTAJE",formatoEncabezado))
            for (int i=0;i<cuotaParticipacionEmpresa.size();i++){
                sheet1.addCell(new Label(4,fila+5+i, cuotaParticipacionEmpresa.get(i),formatoDatos))
                sheet1.addCell(new Number(5,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))
            }
            sheet1.addCell(new Label(4,fila+5+cuotaParticipacionEmpresa.size(),"TOTAL",formatoDatos))
            sheet1.addCell(new Number(5,fila+5+cuotaParticipacionEmpresa.size(),100.0,formatoDatos))
            //sheet1.addCell(new Number(5,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))

            //ACTUALIZACION DEL CAMPO "conjuntoEstano" ASIGNANDO EL NOMBRE DE CONJUNTO ESPECIFICADO
            if(asignarConjuntoALotes.equals("SI")){
                def reporteHojaDeCosto = new ReporteHojaDeCosto(
                        elemento: "Estano",
                        nombreDelConjunto: nombreDelConjunto,
                        destinoDelConjunto: destinoDelConjunto,
                        asignarConjuntoALotes: "SI",
                        ignorarLotes: ignorarLotes,
                        empresa: empresa,
                        fechaInicial: fechaInicial,
                        fechaFinal: fechaFinal,
                        loteInicial: loteInicial,
                        loteFinal: loteFinal,
                        leyMinimaEstano: leyMinimaEstano,
                        leyMaximaEstano: leyMaximaEstano,
                        leyMinimaPlata: null,
                        leyMaximaPlata: null,
                        leyMinimaWolfran: null,
                        leyMaximaWolfran: null,
                        leyMinimaAntimonio: null,
                        leyMaximaAntimonio: null,
                        leyMinimaZincComplejo: null,
                        leyMaximaZincComplejo: null,
                        leyMinimaPlomoComplejo: null,
                        leyMaximaPlomoComplejo: null,
                        leyMinimaPlataComplejo: null,
                        leyMaximaPlataComplejo: null,
                        leyMinimaPlomoPlomoPlata: null,
                        leyMaximaPlomoPlomoPlata: null,
                        leyMinimaPlataPlomoPlata: null,
                        leyMaximaPlataPlomoPlata: null,
                        leyMinimaZincZincPlata: null,
                        leyMaximaZincZincPlata: null,
                        leyMinimaPlataZincPlata: null,
                        leyMaximaPlataZincPlata: null
                )
                reporteHojaDeCosto.save(failOnError: true)

                liquidacionesEstano.each {
                    it.conjuntoEstano=nombreDelConjunto
                    it.save(failOnError: true)
                }
            }

            workbook.write();
            workbook.close();
        }
    }

    def crearReportePlata = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Hoja de Costo de Plata", 0)
        sheet1.setColumnView(0,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(1,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(2,11)
        sheet1.setColumnView(3,40)
        sheet1.setColumnView(4,40)
        sheet1.setRowView(6,500)
        for(i in 5..100)
            sheet1.setColumnView(i,15)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD);
        WritableCellFormat formatoEncabezado = new WritableCellFormat (arial10BoldFont);
        formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)
        formatoEncabezado.setWrap(true)
        formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
        //formatoEncabezado.setAlignment(Alignment.CENTRE)
        WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoDatos.setFont(courier8PlainFont)
        formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)
        WritableCellFormat formatoInfoReporte = new WritableCellFormat (arial14BoldFont);
        WritableCellFormat formatoTitulo = new WritableCellFormat (arial16BoldFont);
        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="hoja_costo_plata.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(3,0, "HOJA DE COSTO DE PLATA",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def tipoReporte = ""+params.tipoReporte
        def asignarConjuntoALotes = ""+params.asignarConjuntoALotes
        def nombreDelConjunto = ""+params.nombreDelConjunto
        def destinoDelConjunto = ""+params.destinoDelConjunto

        def ignorarLotes = ""+params.ignorarLotes
        def lotesIgnorados = ignorarLotes.tokenize(',')

        def empresa=null
        def fechaInicial=null
        def fechaFinal=null
        def leyMinimaPlata=null
        def leyMaximaPlata=null
        def loteInicial=""
        def loteFinal=""

        sheet1.addCell(new Label(3,1, "NOMBRE DEL CONJUNTO:",formatoInfoReporte))
        sheet1.addCell(new Label(4,1, "${nombreDelConjunto}",formatoInfoReporte))
        sheet1.addCell(new Label(3,2, "DESTINO:",formatoInfoReporte))
        sheet1.addCell(new Label(4,2, "${destinoDelConjunto}",formatoInfoReporte))

        def liquidacionesPlata = null

        if (tipoReporte.equals("fechas")){
            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)
            leyMinimaPlata = ""+params.leyMinimaPlata
            leyMinimaPlata = (leyMinimaPlata.equals(""))?0:Float.parseFloat(params.leyMinimaPlata)
            leyMaximaPlata = ""+params.leyMaximaPlata
            leyMaximaPlata = (leyMaximaPlata.equals(""))?0:Float.parseFloat(params.leyMaximaPlata)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(5,1, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))
            sheet1.addCell(new Label(5,2, "ENTRE LEYES: ${leyMinimaPlata}% AL ${leyMaximaPlata}%",formatoInfoReporte))

            def liquidaciones = LiquidacionDePlata.findAllByFechaDeLiquidacionBetweenAndPorcentajePlataBetweenAndConjuntoPlata(fechaInicial,fechaFinal,leyMinimaPlata,leyMaximaPlata,"-")

            liquidacionesPlata=new ArrayList<LiquidacionDePlata>()
            liquidaciones.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesPlata.add(it)
            }
        }
        if (tipoReporte.equals("fechasEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)
            leyMinimaPlata = ""+params.leyMinimaPlata
            leyMinimaPlata = (leyMinimaPlata.equals(""))?0:Float.parseFloat(params.leyMinimaPlata)
            leyMaximaPlata = ""+params.leyMaximaPlata
            leyMaximaPlata = (leyMaximaPlata.equals(""))?0:Float.parseFloat(params.leyMaximaPlata)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(3,3, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(4,3, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(5,1, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))
            sheet1.addCell(new Label(5,2, "ENTRE LEYES: ${leyMinimaPlata}% AL ${leyMaximaPlata}%",formatoInfoReporte))

            //liquidacionesPlata = LiquidacionDePlata.findAllByFechaDeLiquidacionBetweenAndPorcentajePlataBetweenAndNombreEmpresaLikeAndConjuntoPlata(fechaInicial,fechaFinal,leyMinimaPlata,leyMaximaPlata,"%${empresa.nombreDeEmpresa}%","-")

            def liquidaciones = LiquidacionDePlata.findAllByFechaDeLiquidacionBetweenAndPorcentajePlataBetweenAndConjuntoPlata(fechaInicial,fechaFinal,leyMinimaPlata,leyMaximaPlata,"-")

            def liquidaciones1 = new ArrayList<LiquidacionDePlata>()
            liquidaciones.each {
                if(it.recepcionDePlata.empresa.id==empresa.id){
                    liquidaciones1.add(it)
                }
            }

            liquidacionesPlata=new ArrayList<LiquidacionDePlata>()
            liquidaciones1.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesPlata.add(it)
            }
        }
        if (tipoReporte.equals("lotes")){
            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            leyMinimaPlata = ""+params.leyMinimaPlata
            leyMinimaPlata = (leyMinimaPlata.equals(""))?0:Float.parseFloat(params.leyMinimaPlata)
            leyMaximaPlata = ""+params.leyMaximaPlata
            leyMaximaPlata = (leyMaximaPlata.equals(""))?0:Float.parseFloat(params.leyMaximaPlata)

            def liquidaciones1 = LiquidacionDePlata.findAllByPorcentajePlataBetweenAndConjuntoPlata(leyMinimaPlata,leyMaximaPlata,"-")

            def liquidaciones2=new ArrayList<LiquidacionDePlata>()
            liquidaciones1.each {
                def lote = Integer.parseInt(it.lote)
                if (lote>=loteInicial&&lote<=loteFinal)
                    liquidaciones2.add(it)
            }

            liquidacionesPlata=new ArrayList<LiquidacionDePlata>()
            liquidaciones2.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesPlata.add(it)
            }

            sheet1.addCell(new Label(5,1, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))
            sheet1.addCell(new Label(5,2, "ENTRE LEYES: ${leyMinimaPlata}% AL ${leyMaximaPlata}%",formatoInfoReporte))
        }
        if (tipoReporte.equals("lotesEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            leyMinimaPlata = ""+params.leyMinimaPlata
            leyMinimaPlata = (leyMinimaPlata.equals(""))?0:Float.parseFloat(params.leyMinimaPlata)
            leyMaximaPlata = ""+params.leyMaximaPlata
            leyMaximaPlata = (leyMaximaPlata.equals(""))?0:Float.parseFloat(params.leyMaximaPlata)

            def liquidaciones1 = LiquidacionDePlata.findAllByPorcentajePlataBetweenAndConjuntoPlata(leyMinimaPlata,leyMaximaPlata,"-")

            def liquidaciones2=new ArrayList<LiquidacionDePlata>()
            liquidaciones1.each {
                def lote = Integer.parseInt(it.lote)
                if (lote>=loteInicial&&lote<=loteFinal&&it.recepcionDePlata.empresa.id==empresa.id)
                    liquidaciones2.add(it)
            }

            liquidacionesPlata=new ArrayList<LiquidacionDePlata>()
            liquidaciones2.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesPlata.add(it)
            }

            sheet1.addCell(new Label(3,3, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(4,3, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(5,1, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))
            sheet1.addCell(new Label(5,2, "ENTRE LEYES: ${leyMinimaPlata}% AL ${leyMaximaPlata}%",formatoInfoReporte))
        }

        sheet1.addCell(new Label(0,6, "RECEPCION",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "COT. DIA",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "LIQUIDACION",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "NOMBRE",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "TOTAL TARA",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "K. N. H.",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "% H2O",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "K. N. S.",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "LEY DM Ag",formatoEncabezado))
        sheet1.addCell(new Label(13,6, "K. F. %Ag",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "COT. OFICIAL",formatoEncabezado))
        sheet1.addCell(new Label(15,6, "VALOR OF. BRUTO",formatoEncabezado))
        sheet1.addCell(new Label(16,6, "ALICUOTA %",formatoEncabezado))
        sheet1.addCell(new Label(17,6, "VALOR NETO \$us",formatoEncabezado))
        sheet1.addCell(new Label(18,6, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(19,6, "BONO CALIDAD",formatoEncabezado))
        sheet1.addCell(new Label(20,6, "BONO INCENTIVO",formatoEncabezado))
        sheet1.addCell(new Label(21,6, "VALOR DE COMPRA",formatoEncabezado))
        sheet1.addCell(new Label(22,6, "RM",formatoEncabezado))

        if (!liquidacionesPlata) {
            flash.error = "NO SE PUDO OBTENER RESULTADOS!"
            System.out.println("*** SE ESTA PRODUCIENDO RESULTADOS NULL!!!")
            redirect(action: "create")
            return
        }

        if (liquidacionesPlata.size()==0 || nombreDelConjunto.equals("")){
            if (liquidacionesPlata.size()==0)
                sheet1.addCell(new Label(0,7, "SIN RESULTADOS",formatoInfoReporte))
            if (nombreDelConjunto.equals(""))
                sheet1.addCell(new Label(0,7, "ESPECIFIQUE NOMBRE DE CONJUNTO",formatoInfoReporte))
        }else{

            /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA RETENCIONES DE LEY*/
            def listaRetencionesDeLey = retencionesPlataJSON liquidacionesPlata,"DE LEY"
            def columna = 23
            listaRetencionesDeLey.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL RET. DE LEY",formatoEncabezado))
            columna++

            /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA OTRAS RETENCIONES*/
            def listaRetencionesOtras = retencionesPlataJSON liquidacionesPlata,"OTRA"
            listaRetencionesOtras.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL OTRAS RET.",formatoEncabezado))
            columna++

            sheet1.addCell(new Label(columna,6, "TOTAL RET.",formatoEncabezado))
            sheet1.addCell(new Label(columna+1,6, "TOTAL PAGADO",formatoEncabezado))
            sheet1.addCell(new Label(columna+2,6, "ANTICIPO/ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+3,6, "ANTICIPO/F. ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+4,6, "LIQUIDO PAGABLE",formatoEncabezado))
            sheet1.addCell(new Label(columna+5,6, "CANC. TRANSPORTE",formatoEncabezado))
            sheet1.addCell(new Label(columna+6,6, "CANC. LABORAT.",formatoEncabezado))

            //DESPLIEGUE DE DATOS DE LIQUIDACIONES
            //formatoEncabezado.setAlignment(Alignment.RIGHT)
            def fila = 7
            //variables acumuladoras
            def numeroRegistros=0
            def totalCantidadSacos=0
            def totalTotalTara=0
            def totalPesoBruto=0
            def totalKilosNetosHumedos=0
            def totalHumedad=0
            def totalKilosNetosSecos=0
            def totalKilosNetosSecosCotizacionDiaria=0
            def totalPorcentajePlata=0
            def totalKilosFinosPlata=0
            def totalCotizacionQuincenalPlata=0
            def totalValorOficialBruto=0
            def totalCotizacionDiariaPlata=0
            def totalAlicuota=0
            def totalValorNeto=0
            def totalValorNetoBolivianos=0
            def totalBonoCalidad=0
            def totalBonoIncentivo=0
            def totalValorDeCompra=0
            def totalRegaliaMinera=0
            def totalTotalRetenciones=0
            def totalTotalPagado=0
            def totalTotalAnticiposContraEntrega=0
            def totalTotalAnticiposContraFuturaEntrega=0
            def totalTotalLiquidoPagable=0
            def totalCostoDeTransporte=0
            def totalTotalCostoLaboratorio=0

            def cuotaParticipacionEmpresa = new ArrayList<String>()
            def cuotaParticipacionCuota = new ArrayList<Integer>()

            liquidacionesPlata.each {
                numeroRegistros++
                totalCantidadSacos+=it.cantidadDeSacos
                totalTotalTara+=it.tara*it.cantidadDeSacos
                totalPesoBruto+=it.pesoBruto
                totalKilosNetosHumedos+=it.kilosNetosHumedos
                totalHumedad+=it.humedad
                totalKilosNetosSecos+=it.kilosNetosSecos
                totalKilosNetosSecosCotizacionDiaria+=(it.kilosNetosSecos*it.recepcionDePlata.cotizacionDiariaDeMinerales.plata)
                totalPorcentajePlata+=it.porcentajePlata
                totalKilosFinosPlata+=it.kilosFinosPlata
                totalCotizacionQuincenalPlata+=it.recepcionDePlata.cotizacionQuincenalDeMinerales.plata
                totalValorOficialBruto+=it.valorOficialBruto
                totalCotizacionDiariaPlata+=it.recepcionDePlata.cotizacionDiariaDeMinerales.plata
                totalAlicuota+=it.recepcionDePlata.alicuota.plata
                totalValorNeto+=it.valorNetoMineral
                totalValorNetoBolivianos+=it.valorNetoMineralEnBolivianos
                totalBonoCalidad+=it.bonoCalidad
                totalBonoIncentivo+=it.bonoIncentivo
                totalValorDeCompra+=it.valorDeCompra
                totalRegaliaMinera+=it.regaliaMinera
                totalTotalRetenciones+=it.totalRetenciones
                totalTotalPagado+=it.totalPagado
                totalTotalAnticiposContraEntrega+=it.totalAnticiposContraEntrega
                totalTotalAnticiposContraFuturaEntrega+=it.totalAnticiposContraFuturaEntrega
                totalTotalLiquidoPagable=totalTotalLiquidoPagable+((it.totalLiquidoPagable.doubleValue()<0)?0:it.totalLiquidoPagable.doubleValue())
                totalCostoDeTransporte+=it.recepcionDePlata.costoDeTransporte
                totalTotalCostoLaboratorio+=it.totalCostoLaboratorio

                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new Number(1,fila, it.recepcionDePlata.cotizacionDiariaDeMinerales.plata,formatoDatos))
                sheet1.addCell(new DateTime(2,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(3,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(4,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(5,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(6,fila, it.cantidadDeSacos,formatoDatos))
                sheet1.addCell(new Number(7,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(8,fila, it.tara*it.cantidadDeSacos,formatoDatos))
                sheet1.addCell(new Number(9,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(10,fila, it.humedad,formatoDatos))
                sheet1.addCell(new Number(11,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(12,fila, it.porcentajePlata,formatoDatos))
                sheet1.addCell(new Number(13,fila, it.kilosFinosPlata,formatoDatos))
                sheet1.addCell(new Number(14,fila, it.recepcionDePlata.cotizacionQuincenalDeMinerales.plata,formatoDatos))
                sheet1.addCell(new Number(15,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(16,fila, it.recepcionDePlata.alicuota.plata,formatoDatos))
                sheet1.addCell(new Number(17,fila, it.valorNetoMineral,formatoDatos))
                sheet1.addCell(new Number(18,fila, it.valorNetoMineralEnBolivianos,formatoDatos))
                sheet1.addCell(new Number(19,fila, it.bonoCalidad,formatoDatos))
                sheet1.addCell(new Number(20,fila, it.bonoIncentivo,formatoDatos))
                sheet1.addCell(new Number(21,fila, it.valorDeCompra,formatoDatos))
                sheet1.addCell(new Number(22,fila, it.regaliaMinera,formatoDatos))

                columna = 23

                /*DESPLIEGUE DE RETENCIONES DE LEY*/
                def retencionesDeLeyLiquidacion = LiquidacionDePlataRetenciones.findAllByLiquidacionDePlataAndTipoDeRetencion(it,"DE LEY")
                def numretDeLey = retencionesDeLeyLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretDeLey} RETENCIONES DE LEY!")
                def subtotalRetencionesDeLey=it.regaliaMinera.doubleValue()
                for(int i=0;i<listaRetencionesDeLey.size();i++){
                    def vr = valorRetencion(listaRetencionesDeLey.get(i), retencionesDeLeyLiquidacion,numretDeLey)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesDeLey+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesDeLey,formatoDatos))
                columna++

                /*DESPLIEGUE DE RETENCIONES DE LEY*/
                def retencionesOtrasLiquidacion = LiquidacionDePlataRetenciones.findAllByLiquidacionDePlataAndTipoDeRetencion(it,"OTRA")
                def numretOtras = retencionesOtrasLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretOtras} RETENCIONES DE LEY!")
                def subtotalRetencionesOtras=0
                for(int i=0;i<listaRetencionesOtras.size();i++){
                    def vr = valorRetencion(listaRetencionesOtras.get(i), retencionesOtrasLiquidacion,numretOtras)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesOtras+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesOtras,formatoDatos))
                columna++

                sheet1.addCell(new Number(columna,fila, it.totalRetenciones,formatoDatos))
                sheet1.addCell(new Number(columna+1,fila, it.totalPagado,formatoDatos))
                sheet1.addCell(new Number(columna+2,fila, it.totalAnticiposContraEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+3,fila, it.totalAnticiposContraFuturaEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+4,fila, it.totalLiquidoPagable,formatoDatos))
                sheet1.addCell(new Number(columna+5,fila, it.recepcionDePlata.costoDeTransporte,formatoDatos))
                sheet1.addCell(new Number(columna+6,fila, it.totalCostoLaboratorio,formatoDatos))

                fila++

                if (cuotaParticipacionEmpresa.contains(it.nombreEmpresa)){
                    def obj=cuotaParticipacionCuota.get(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa))
                    obj++
                    cuotaParticipacionCuota.set(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa),obj)
                }else{
                    cuotaParticipacionEmpresa.add(it.nombreEmpresa)
                    cuotaParticipacionCuota.add(1)
                }

            }

            //IMPRESION DE TOTALES
            sheet1.addCell(new Number(1,fila, totalKilosNetosSecosCotizacionDiaria/totalKilosNetosSecos,formatoDatos))
            sheet1.addCell(new Number(6,fila, totalCantidadSacos,formatoDatos))
            sheet1.addCell(new Number(8,fila, totalTotalTara,formatoDatos))
            sheet1.addCell(new Number(7,fila, totalPesoBruto,formatoDatos))
            sheet1.addCell(new Number(9,fila, totalKilosNetosHumedos,formatoDatos))
            //sheet1.addCell(new Number(10,fila, totalHumedad/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(10,fila, (totalKilosNetosSecos/totalKilosNetosHumedos*100-100)*-1,formatoDatos))
            sheet1.addCell(new Number(11,fila, totalKilosNetosSecos,formatoDatos))
            //sheet1.addCell(new Number(12,fila, totalPorcentajePlata/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(12,fila, totalKilosFinosPlata/totalKilosNetosSecos*10000,formatoDatos))
            sheet1.addCell(new Number(13,fila, totalKilosFinosPlata,formatoDatos))
            sheet1.addCell(new Number(14,fila, totalCotizacionQuincenalPlata/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(15,fila, totalValorOficialBruto,formatoDatos))
            sheet1.addCell(new Number(16,fila, totalAlicuota/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(17,fila, totalValorNeto,formatoDatos))
            sheet1.addCell(new Number(18,fila, totalValorNetoBolivianos,formatoDatos))
            sheet1.addCell(new Number(19,fila, totalBonoCalidad,formatoDatos))
            sheet1.addCell(new Number(20,fila, totalBonoIncentivo,formatoDatos))
            sheet1.addCell(new Number(21,fila, totalValorDeCompra,formatoDatos))
            sheet1.addCell(new Number(22,fila, totalRegaliaMinera,formatoDatos))

            def columnaFinalRetenciones = 25+listaRetencionesDeLey.size()+listaRetencionesOtras.size()
            def totalLiquidaciones = liquidacionesPlata.size()
            for (int col=23;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =7;fil<totalLiquidaciones+7;fil++){
                    def valor = Double.parseDouble(sheet1.getWritableCell(col,fil).contents)
                    tret+=valor
                }
                sheet1.addCell(new Number(col,totalLiquidaciones+7, tret,formatoDatos))
            }

            sheet1.addCell(new Number(columnaFinalRetenciones,fila, totalTotalRetenciones,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+1,fila, totalTotalPagado,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+2,fila, totalTotalAnticiposContraEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+3,fila, totalTotalAnticiposContraFuturaEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+4,fila, totalTotalLiquidoPagable,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+5,fila, totalCostoDeTransporte,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+6,fila, totalTotalCostoLaboratorio,formatoDatos))

            sheet1.mergeCells(22, 5, 23+listaRetencionesDeLey.size(), 5);
            sheet1.mergeCells(24+listaRetencionesDeLey.size(), 5, 24+listaRetencionesDeLey.size()+listaRetencionesOtras.size(), 5);
            sheet1.addCell(new Label(22,5, "RETENCIONES DE LEY",formatoEncabezado))
            sheet1.addCell(new Label(24+listaRetencionesDeLey.size(),5, "OTRAS RETENCIONES",formatoEncabezado))

            //IMPRESION DE DISTRIBUCION PORCENTUAL
            sheet1.mergeCells(4, fila+3, 5, fila+3);
            sheet1.addCell(new Label(4,fila+3,"VOLUMEN DE PARTICIPACION POR EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(4,fila+4, "EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(5,fila+4, "PORCENTAJE",formatoEncabezado))
            for (int i=0;i<cuotaParticipacionEmpresa.size();i++){
                sheet1.addCell(new Label(4,fila+5+i, cuotaParticipacionEmpresa.get(i),formatoDatos))
                sheet1.addCell(new Number(5,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))
            }
            sheet1.addCell(new Label(4,fila+5+cuotaParticipacionEmpresa.size(),"TOTAL",formatoDatos))
            sheet1.addCell(new Number(5,fila+5+cuotaParticipacionEmpresa.size(),100.0,formatoDatos))
            //sheet1.addCell(new Number(5,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))

            //ACTUALIZACION DEL CAMPO "conjuntoPlata" ASIGNANDO EL NOMBRE DE CONJUNTO ESPECIFICADO
            if(asignarConjuntoALotes.equals("SI")){
                def reporteHojaDeCosto = new ReporteHojaDeCosto(
                        elemento: "Plata",
                        nombreDelConjunto: nombreDelConjunto,
                        destinoDelConjunto: destinoDelConjunto,
                        asignarConjuntoALotes: "SI",
                        ignorarLotes: ignorarLotes,
                        empresa: empresa,
                        fechaInicial: fechaInicial,
                        fechaFinal: fechaFinal,
                        loteInicial: loteInicial,
                        loteFinal: loteFinal,
                        leyMinimaEstano: null,
                        leyMaximaEstano: null,
                        leyMinimaPlata: leyMinimaPlata,
                        leyMaximaPlata: leyMaximaPlata,
                        leyMinimaWolfran: null,
                        leyMaximaWolfran: null,
                        leyMinimaAntimonio: null,
                        leyMaximaAntimonio: null,
                        leyMinimaZincComplejo: null,
                        leyMaximaZincComplejo: null,
                        leyMinimaPlomoComplejo: null,
                        leyMaximaPlomoComplejo: null,
                        leyMinimaPlataComplejo: null,
                        leyMaximaPlataComplejo: null,
                        leyMinimaPlomoPlomoPlata: null,
                        leyMaximaPlomoPlomoPlata: null,
                        leyMinimaPlataPlomoPlata: null,
                        leyMaximaPlataPlomoPlata: null,
                        leyMinimaZincZincPlata: null,
                        leyMaximaZincZincPlata: null,
                        leyMinimaPlataZincPlata: null,
                        leyMaximaPlataZincPlata: null
                )
                reporteHojaDeCosto.save(failOnError: true)

                liquidacionesPlata.each {
                    it.conjuntoPlata=nombreDelConjunto
                    it.save(failOnError: true)
                }
            }

            workbook.write();
            workbook.close();
        }
    }

    def crearReporteWolfran = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Hoja de Costo de Wolfran", 0)
        sheet1.setColumnView(0,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(1,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(2,11)
        sheet1.setColumnView(3,40)
        sheet1.setColumnView(4,40)
        sheet1.setRowView(6,500)
        for(i in 5..100)
            sheet1.setColumnView(i,15)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD);
        WritableCellFormat formatoEncabezado = new WritableCellFormat (arial10BoldFont);
        formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)
        formatoEncabezado.setWrap(true)
        formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
        //formatoEncabezado.setAlignment(Alignment.CENTRE)
        WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoDatos.setFont(courier8PlainFont)
        formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)
        WritableCellFormat formatoInfoReporte = new WritableCellFormat (arial14BoldFont);
        WritableCellFormat formatoTitulo = new WritableCellFormat (arial16BoldFont);
        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="hoja_costo_wolfran.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(3,0, "HOJA DE COSTO DE WOLFRAN",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def tipoReporte = ""+params.tipoReporte
        def asignarConjuntoALotes = ""+params.asignarConjuntoALotes
        def nombreDelConjunto = ""+params.nombreDelConjunto
        def destinoDelConjunto = ""+params.destinoDelConjunto

        def ignorarLotes = ""+params.ignorarLotes
        def lotesIgnorados = ignorarLotes.tokenize(',')

        def empresa=null
        def fechaInicial=null
        def fechaFinal=null
        def leyMinimaWolfran=null
        def leyMaximaWolfran=null
        def loteInicial=null
        def loteFinal=null

        sheet1.addCell(new Label(3,1, "NOMBRE DEL CONJUNTO:",formatoInfoReporte))
        sheet1.addCell(new Label(4,1, "${nombreDelConjunto}",formatoInfoReporte))
        sheet1.addCell(new Label(3,2, "DESTINO:",formatoInfoReporte))
        sheet1.addCell(new Label(4,2, "${destinoDelConjunto}",formatoInfoReporte))

        def liquidacionesWolfran = null

        if (tipoReporte.equals("fechas")){
            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)
            leyMinimaWolfran = ""+params.leyMinimaWolfran
            leyMinimaWolfran = (leyMinimaWolfran.equals(""))?0:Float.parseFloat(params.leyMinimaWolfran)
            leyMaximaWolfran = ""+params.leyMaximaWolfran
            leyMaximaWolfran = (leyMaximaWolfran.equals(""))?0:Float.parseFloat(params.leyMaximaWolfran)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(5,1, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))
            sheet1.addCell(new Label(5,2, "ENTRE LEYES: ${leyMinimaWolfran}% AL ${leyMaximaWolfran}%",formatoInfoReporte))

            def liquidaciones = LiquidacionDeWolfran.findAllByFechaDeLiquidacionBetweenAndPorcentajeWolfranBetweenAndConjuntoWolfran(fechaInicial,fechaFinal,leyMinimaWolfran,leyMaximaWolfran,"-")

            liquidacionesWolfran=new ArrayList<LiquidacionDeWolfran>()
            liquidaciones.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesWolfran.add(it)
            }
        }
        if (tipoReporte.equals("fechasEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)
            leyMinimaWolfran = ""+params.leyMinimaWolfran
            leyMinimaWolfran = (leyMinimaWolfran.equals(""))?0:Float.parseFloat(params.leyMinimaWolfran)
            leyMaximaWolfran = ""+params.leyMaximaWolfran
            leyMaximaWolfran = (leyMaximaWolfran.equals(""))?0:Float.parseFloat(params.leyMaximaWolfran)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(3,3, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(4,3, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(5,1, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))
            sheet1.addCell(new Label(5,2, "ENTRE LEYES: ${leyMinimaWolfran}% AL ${leyMaximaWolfran}%",formatoInfoReporte))

            //liquidacionesWolfran = LiquidacionDeWolfran.findAllByFechaDeLiquidacionBetweenAndPorcentajeWolfranBetweenAndNombreEmpresaLikeAndConjuntoWolfran(fechaInicial,fechaFinal,leyMinimaWolfran,leyMaximaWolfran,"%${empresa.nombreDeEmpresa}%","-")

            def liquidaciones = LiquidacionDeWolfran.findAllByFechaDeLiquidacionBetweenAndPorcentajeWolfranBetweenAndConjuntoWolfran(fechaInicial,fechaFinal,leyMinimaWolfran,leyMaximaWolfran,"-")

            def liquidaciones1 = new ArrayList<LiquidacionDeWolfran>()
            liquidaciones.each {
                if(it.recepcionDeWolfran.empresa.id==empresa.id){
                    liquidaciones1.add(it)
                }
            }

            liquidacionesWolfran=new ArrayList<LiquidacionDeWolfran>()
            liquidaciones1.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesWolfran.add(it)
            }
        }
        if (tipoReporte.equals("lotes")){
            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            leyMinimaWolfran = ""+params.leyMinimaWolfran
            leyMinimaWolfran = (leyMinimaWolfran.equals(""))?0:Float.parseFloat(params.leyMinimaWolfran)
            leyMaximaWolfran = ""+params.leyMaximaWolfran
            leyMaximaWolfran = (leyMaximaWolfran.equals(""))?0:Float.parseFloat(params.leyMaximaWolfran)

            def liquidaciones1 = LiquidacionDeWolfran.findAllByPorcentajeWolfranBetweenAndConjuntoWolfran(leyMinimaWolfran,leyMaximaWolfran,"-")

            def liquidaciones2=new ArrayList<LiquidacionDeWolfran>()
            liquidaciones1.each {
                def lote = Integer.parseInt(it.lote)
                if (lote>=loteInicial&&lote<=loteFinal)
                    liquidaciones2.add(it)
            }

            liquidacionesWolfran=new ArrayList<LiquidacionDeWolfran>()
            liquidaciones2.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesWolfran.add(it)
            }

            sheet1.addCell(new Label(5,1, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))
            sheet1.addCell(new Label(5,2, "ENTRE LEYES: ${leyMinimaWolfran}% AL ${leyMaximaWolfran}%",formatoInfoReporte))
        }
        if (tipoReporte.equals("lotesEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            leyMinimaWolfran = ""+params.leyMinimaWolfran
            leyMinimaWolfran = (leyMinimaWolfran.equals(""))?0:Float.parseFloat(params.leyMinimaWolfran)
            leyMaximaWolfran = ""+params.leyMaximaWolfran
            leyMaximaWolfran = (leyMaximaWolfran.equals(""))?0:Float.parseFloat(params.leyMaximaWolfran)

            def liquidaciones1 = LiquidacionDeWolfran.findAllByPorcentajeWolfranBetweenAndConjuntoWolfran(leyMinimaWolfran,leyMaximaWolfran,"-")

            def liquidaciones2=new ArrayList<LiquidacionDeWolfran>()
            liquidaciones1.each {
                def lote = Integer.parseInt(it.lote)
                if (lote>=loteInicial&&lote<=loteFinal&&it.recepcionDeWolfran.empresa.id==empresa.id)
                    liquidaciones2.add(it)
            }

            liquidacionesWolfran=new ArrayList<LiquidacionDeWolfran>()
            liquidaciones2.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesWolfran.add(it)
            }

            sheet1.addCell(new Label(3,3, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(4,3, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(5,1, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))
            sheet1.addCell(new Label(5,2, "ENTRE LEYES: ${leyMinimaWolfran}% AL ${leyMaximaWolfran}%",formatoInfoReporte))
        }

        sheet1.addCell(new Label(0,6, "RECEPCION",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "COT. DIA",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "LIQUIDACION",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "NOMBRE",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "TOTAL TARA",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "K. N. H.",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "% H2O",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "K. N. S.",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "LEY %WO3",formatoEncabezado))
        sheet1.addCell(new Label(13,6, "K. F. WO3",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "COT. OFICIAL",formatoEncabezado))
        sheet1.addCell(new Label(15,6, "VALOR OF. BRUTO",formatoEncabezado))
        sheet1.addCell(new Label(16,6, "ALICUOTA %",formatoEncabezado))
        sheet1.addCell(new Label(17,6, "VALOR NETO \$us",formatoEncabezado))
        sheet1.addCell(new Label(18,6, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(19,6, "BONO CALIDAD",formatoEncabezado))
        sheet1.addCell(new Label(20,6, "BONO INCENTIVO",formatoEncabezado))
        sheet1.addCell(new Label(21,6, "VALOR DE COMPRA",formatoEncabezado))
        sheet1.addCell(new Label(22,6, "RM",formatoEncabezado))

        if (!liquidacionesWolfran) {
            flash.error = "NO SE PUDO OBTENER RESULTADOS!"
            System.out.println("*** SE ESTA PRODUCIENDO RESULTADOS NULL!!!")
            redirect(action: "create")
            return
        }

        if (liquidacionesWolfran.size()==0 || nombreDelConjunto.equals("")){
            if (liquidacionesWolfran.size()==0)
                sheet1.addCell(new Label(0,7, "SIN RESULTADOS",formatoInfoReporte))
            if (nombreDelConjunto.equals(""))
                sheet1.addCell(new Label(0,7, "ESPECIFIQUE NOMBRE DE CONJUNTO",formatoInfoReporte))
        }else{
            /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA RETENCIONES DE LEY*/
            def listaRetencionesDeLey = retencionesWolfranJSON liquidacionesWolfran,"DE LEY"
            def columna = 23
            listaRetencionesDeLey.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL RET. DE LEY",formatoEncabezado))
            columna++

            /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA OTRAS RETENCIONES*/
            def listaRetencionesOtras = retencionesWolfranJSON liquidacionesWolfran,"OTRA"
            listaRetencionesOtras.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL OTRAS RET.",formatoEncabezado))
            columna++

            sheet1.addCell(new Label(columna,6, "TOTAL RET.",formatoEncabezado))
            sheet1.addCell(new Label(columna+1,6, "TOTAL PAGADO",formatoEncabezado))
            sheet1.addCell(new Label(columna+2,6, "ANTICIPO/ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+3,6, "ANTICIPO/F. ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+4,6, "LIQUIDO PAGABLE",formatoEncabezado))
            sheet1.addCell(new Label(columna+5,6, "CANC. TRANSPORTE",formatoEncabezado))
            sheet1.addCell(new Label(columna+6,6, "CANC. LABORAT.",formatoEncabezado))

            //DESPLIEGUE DE DATOS DE LIQUIDACIONES
            //formatoEncabezado.setAlignment(Alignment.RIGHT)
            def fila = 7
            //variables acumuladoras
            def numeroRegistros=0
            def totalCantidadSacos=0
            def totalTotalTara=0
            def totalPesoBruto=0
            def totalKilosNetosHumedos=0
            def totalHumedad=0
            def totalKilosNetosSecos=0
            def totalKilosNetosSecosCotizacionDiaria=0
            def totalPorcentajeWolfran=0
            def totalKilosFinosWolfran=0
            def totalCotizacionQuincenalWolfran=0
            def totalValorOficialBruto=0
            def totalCotizacionDiariaWolfran=0
            def totalAlicuota=0
            def totalValorNeto=0
            def totalValorNetoBolivianos=0
            def totalBonoCalidad=0
            def totalBonoIncentivo=0
            def totalValorDeCompra=0
            def totalRegaliaMinera=0
            def totalTotalRetenciones=0
            def totalTotalPagado=0
            def totalTotalAnticiposContraEntrega=0
            def totalTotalAnticiposContraFuturaEntrega=0
            def totalTotalLiquidoPagable=0
            def totalCostoDeTransporte=0
            def totalTotalCostoLaboratorio=0

            def cuotaParticipacionEmpresa = new ArrayList<String>()
            def cuotaParticipacionCuota = new ArrayList<Integer>()

            liquidacionesWolfran.each {
                numeroRegistros++
                totalCantidadSacos+=it.cantidadDeSacos
                totalTotalTara+=it.tara*it.cantidadDeSacos
                totalPesoBruto+=it.pesoBruto
                totalKilosNetosHumedos+=it.kilosNetosHumedos
                totalHumedad+=it.humedad
                totalKilosNetosSecos+=it.kilosNetosSecos
                totalKilosNetosSecosCotizacionDiaria+=(it.kilosNetosSecos*it.recepcionDeWolfran.cotizacionDiariaDeMinerales.wolfran)
                totalPorcentajeWolfran+=it.porcentajeWolfran
                totalKilosFinosWolfran+=it.kilosFinosWolfran
                totalCotizacionQuincenalWolfran+=it.recepcionDeWolfran.cotizacionQuincenalDeMinerales.wolfran
                totalValorOficialBruto+=it.valorOficialBruto
                totalCotizacionDiariaWolfran+=it.recepcionDeWolfran.cotizacionDiariaDeMinerales.wolfran
                totalAlicuota+=it.recepcionDeWolfran.alicuota.wolfran
                totalValorNeto+=it.valorNetoMineral
                totalValorNetoBolivianos+=it.valorNetoMineralEnBolivianos
                totalBonoCalidad+=it.bonoCalidad
                totalBonoIncentivo+=it.bonoIncentivo
                totalValorDeCompra+=it.valorDeCompra
                totalRegaliaMinera+=it.regaliaMinera
                totalTotalRetenciones+=it.totalRetenciones
                totalTotalPagado+=it.totalPagado
                totalTotalAnticiposContraEntrega+=it.totalAnticiposContraEntrega
                totalTotalAnticiposContraFuturaEntrega+=it.totalAnticiposContraFuturaEntrega
                totalTotalLiquidoPagable=totalTotalLiquidoPagable+((it.totalLiquidoPagable.doubleValue()<0)?0:it.totalLiquidoPagable.doubleValue())
                totalCostoDeTransporte+=it.recepcionDeWolfran.costoDeTransporte
                totalTotalCostoLaboratorio+=it.totalCostoLaboratorio

                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new Number(1,fila, it.recepcionDeWolfran.cotizacionDiariaDeMinerales.wolfran,formatoDatos))
                sheet1.addCell(new DateTime(2,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(3,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(4,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(5,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(6,fila, it.cantidadDeSacos,formatoDatos))
                sheet1.addCell(new Number(7,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(8,fila, it.tara*it.cantidadDeSacos,formatoDatos))
                sheet1.addCell(new Number(9,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(10,fila, it.humedad,formatoDatos))
                sheet1.addCell(new Number(11,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(12,fila, it.porcentajeWolfran,formatoDatos))
                sheet1.addCell(new Number(13,fila, it.kilosFinosWolfran,formatoDatos))
                sheet1.addCell(new Number(14,fila, it.recepcionDeWolfran.cotizacionQuincenalDeMinerales.wolfran,formatoDatos))
                sheet1.addCell(new Number(15,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(16,fila, it.recepcionDeWolfran.alicuota.wolfran,formatoDatos))
                sheet1.addCell(new Number(17,fila, it.valorNetoMineral,formatoDatos))
                sheet1.addCell(new Number(18,fila, it.valorNetoMineralEnBolivianos,formatoDatos))
                sheet1.addCell(new Number(19,fila, it.bonoCalidad,formatoDatos))
                sheet1.addCell(new Number(20,fila, it.bonoIncentivo,formatoDatos))
                sheet1.addCell(new Number(21,fila, it.valorDeCompra,formatoDatos))
                sheet1.addCell(new Number(22,fila, it.regaliaMinera,formatoDatos))

                columna = 23

                /*DESPLIEGUE DE RETENCIONES DE LEY*/
                def retencionesDeLeyLiquidacion = LiquidacionDeWolfranRetenciones.findAllByLiquidacionDeWolfranAndTipoDeRetencion(it,"DE LEY")
                def numretDeLey = retencionesDeLeyLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretDeLey} RETENCIONES DE LEY!")
                def subtotalRetencionesDeLey=it.regaliaMinera.doubleValue()
                for(int i=0;i<listaRetencionesDeLey.size();i++){
                    def vr = valorRetencion(listaRetencionesDeLey.get(i), retencionesDeLeyLiquidacion,numretDeLey)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesDeLey+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesDeLey,formatoDatos))
                columna++

                /*DESPLIEGUE DE RETENCIONES DE LEY*/
                def retencionesOtrasLiquidacion = LiquidacionDeWolfranRetenciones.findAllByLiquidacionDeWolfranAndTipoDeRetencion(it,"OTRA")
                def numretOtras = retencionesOtrasLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretOtras} RETENCIONES DE LEY!")
                def subtotalRetencionesOtras=0
                for(int i=0;i<listaRetencionesOtras.size();i++){
                    def vr = valorRetencion(listaRetencionesOtras.get(i), retencionesOtrasLiquidacion,numretOtras)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesOtras+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesOtras,formatoDatos))
                columna++

                sheet1.addCell(new Number(columna,fila, it.totalRetenciones,formatoDatos))
                sheet1.addCell(new Number(columna+1,fila, it.totalPagado,formatoDatos))
                sheet1.addCell(new Number(columna+2,fila, it.totalAnticiposContraEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+3,fila, it.totalAnticiposContraFuturaEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+4,fila, it.totalLiquidoPagable,formatoDatos))
                sheet1.addCell(new Number(columna+5,fila, it.recepcionDeWolfran.costoDeTransporte,formatoDatos))
                sheet1.addCell(new Number(columna+6,fila, it.totalCostoLaboratorio,formatoDatos))

                fila++

                if (cuotaParticipacionEmpresa.contains(it.nombreEmpresa)){
                    def obj=cuotaParticipacionCuota.get(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa))
                    obj++
                    cuotaParticipacionCuota.set(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa),obj)
                }else{
                    cuotaParticipacionEmpresa.add(it.nombreEmpresa)
                    cuotaParticipacionCuota.add(1)
                }

            }

            //IMPRESION DE TOTALES
            //sheet1.addCell(new Number(1,fila, totalCotizacionDiariaWolfran/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(1,fila, totalKilosNetosSecosCotizacionDiaria/totalKilosNetosSecos,formatoDatos))
            sheet1.addCell(new Number(6,fila, totalCantidadSacos,formatoDatos))
            sheet1.addCell(new Number(8,fila, totalTotalTara,formatoDatos))
            sheet1.addCell(new Number(7,fila, totalPesoBruto,formatoDatos))
            sheet1.addCell(new Number(9,fila, totalKilosNetosHumedos,formatoDatos))
            //sheet1.addCell(new Number(10,fila, totalHumedad/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(10,fila, (totalKilosNetosSecos/totalKilosNetosHumedos*100-100)*-1,formatoDatos))
            sheet1.addCell(new Number(11,fila, totalKilosNetosSecos,formatoDatos))
            //sheet1.addCell(new Number(12,fila, totalPorcentajeWolfran/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(12,fila, totalKilosFinosWolfran/totalKilosNetosSecos*100,formatoDatos))
            sheet1.addCell(new Number(13,fila, totalKilosFinosWolfran,formatoDatos))
            sheet1.addCell(new Number(14,fila, totalCotizacionQuincenalWolfran/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(15,fila, totalValorOficialBruto,formatoDatos))
            sheet1.addCell(new Number(16,fila, totalAlicuota/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(17,fila, totalValorNeto,formatoDatos))
            sheet1.addCell(new Number(18,fila, totalValorNetoBolivianos,formatoDatos))
            sheet1.addCell(new Number(19,fila, totalBonoCalidad,formatoDatos))
            sheet1.addCell(new Number(20,fila, totalBonoIncentivo,formatoDatos))
            sheet1.addCell(new Number(21,fila, totalValorDeCompra,formatoDatos))
            sheet1.addCell(new Number(22,fila, totalRegaliaMinera,formatoDatos))

            def columnaFinalRetenciones = 25+listaRetencionesDeLey.size()+listaRetencionesOtras.size()
            def totalLiquidaciones = liquidacionesWolfran.size()
            for (int col=23;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =7;fil<totalLiquidaciones+7;fil++){
                    def valor = Double.parseDouble(sheet1.getWritableCell(col,fil).contents)
                    tret+=valor
                }
                sheet1.addCell(new Number(col,totalLiquidaciones+7, tret,formatoDatos))
            }

            sheet1.addCell(new Number(columnaFinalRetenciones,fila, totalTotalRetenciones,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+1,fila, totalTotalPagado,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+2,fila, totalTotalAnticiposContraEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+3,fila, totalTotalAnticiposContraFuturaEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+4,fila, totalTotalLiquidoPagable,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+5,fila, totalCostoDeTransporte,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+6,fila, totalTotalCostoLaboratorio,formatoDatos))

            sheet1.mergeCells(22, 5, 23+listaRetencionesDeLey.size(), 5);
            sheet1.mergeCells(24+listaRetencionesDeLey.size(), 5, 24+listaRetencionesDeLey.size()+listaRetencionesOtras.size(), 5);
            sheet1.addCell(new Label(22,5, "RETENCIONES DE LEY",formatoEncabezado))
            sheet1.addCell(new Label(24+listaRetencionesDeLey.size(),5, "OTRAS RETENCIONES",formatoEncabezado))

            //IMPRESION DE DISTRIBUCION PORCENTUAL
            sheet1.mergeCells(4, fila+3, 5, fila+3);
            sheet1.addCell(new Label(4,fila+3,"VOLUMEN DE PARTICIPACION POR EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(4,fila+4, "EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(5,fila+4, "PORCENTAJE",formatoEncabezado))
            for (int i=0;i<cuotaParticipacionEmpresa.size();i++){
                sheet1.addCell(new Label(4,fila+5+i, cuotaParticipacionEmpresa.get(i),formatoDatos))
                sheet1.addCell(new Number(5,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))
            }
            sheet1.addCell(new Label(4,fila+5+cuotaParticipacionEmpresa.size(),"TOTAL",formatoDatos))
            sheet1.addCell(new Number(5,fila+5+cuotaParticipacionEmpresa.size(),100.0,formatoDatos))
            //sheet1.addCell(new Number(5,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))

            //ACTUALIZACION DEL CAMPO "conjuntoWolfran" ASIGNANDO EL NOMBRE DE CONJUNTO ESPECIFICADO
            if(asignarConjuntoALotes.equals("SI")){
                def reporteHojaDeCosto = new ReporteHojaDeCosto(
                        elemento: "Wolfran",
                        nombreDelConjunto: nombreDelConjunto,
                        destinoDelConjunto: destinoDelConjunto,
                        asignarConjuntoALotes: "SI",
                        ignorarLotes: ignorarLotes,
                        empresa: empresa,
                        fechaInicial: fechaInicial,
                        fechaFinal: fechaFinal,
                        loteInicial: loteInicial,
                        loteFinal: loteFinal,
                        leyMinimaEstano: null,
                        leyMaximaEstano: null,
                        leyMinimaPlata: null,
                        leyMaximaPlata: null,
                        leyMinimaWolfran: leyMinimaWolfran,
                        leyMaximaWolfran: leyMaximaWolfran,
                        leyMinimaAntimonio: null,
                        leyMaximaAntimonio: null,
                        leyMinimaZincComplejo: null,
                        leyMaximaZincComplejo: null,
                        leyMinimaPlomoComplejo: null,
                        leyMaximaPlomoComplejo: null,
                        leyMinimaPlataComplejo: null,
                        leyMaximaPlataComplejo: null,
                        leyMinimaPlomoPlomoPlata: null,
                        leyMaximaPlomoPlomoPlata: null,
                        leyMinimaPlataPlomoPlata: null,
                        leyMaximaPlataPlomoPlata: null,
                        leyMinimaZincZincPlata: null,
                        leyMaximaZincZincPlata: null,
                        leyMinimaPlataZincPlata: null,
                        leyMaximaPlataZincPlata: null
                )
                reporteHojaDeCosto.save(failOnError: true)

                liquidacionesWolfran.each {
                    it.conjuntoWolfran=nombreDelConjunto
                    it.save(failOnError: true)
                }
            }

            workbook.write();
            workbook.close();
        }
    }

    def crearReporteAntimonio = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Hoja de Costo de Antimonio", 0)
        sheet1.setColumnView(0,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(1,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(2,11)
        sheet1.setColumnView(3,40)
        sheet1.setColumnView(4,40)
        sheet1.setRowView(6,500)
        for(i in 5..100)
            sheet1.setColumnView(i,15)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD);
        WritableCellFormat formatoEncabezado = new WritableCellFormat (arial10BoldFont);
        formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)
        formatoEncabezado.setWrap(true)
        formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
        //formatoEncabezado.setAlignment(Alignment.CENTRE)
        WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoDatos.setFont(courier8PlainFont)
        formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)
        WritableCellFormat formatoInfoReporte = new WritableCellFormat (arial14BoldFont);
        WritableCellFormat formatoTitulo = new WritableCellFormat (arial16BoldFont);
        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="hoja_costo_antimonio.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(3,0, "HOJA DE COSTO DE ANTIMONIO",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def tipoReporte = ""+params.tipoReporte
        def asignarConjuntoALotes = ""+params.asignarConjuntoALotes
        def nombreDelConjunto = ""+params.nombreDelConjunto
        def destinoDelConjunto = ""+params.destinoDelConjunto

        def ignorarLotes = ""+params.ignorarLotes
        def lotesIgnorados = ignorarLotes.tokenize(',')

        def empresa=null
        def fechaInicial=null
        def fechaFinal=null
        def leyMinimaAntimonio=null
        def leyMaximaAntimonio=null
        def loteInicial=null
        def loteFinal=null

        sheet1.addCell(new Label(3,1, "NOMBRE DEL CONJUNTO:",formatoInfoReporte))
        sheet1.addCell(new Label(4,1, "${nombreDelConjunto}",formatoInfoReporte))
        sheet1.addCell(new Label(3,2, "DESTINO:",formatoInfoReporte))
        sheet1.addCell(new Label(4,2, "${destinoDelConjunto}",formatoInfoReporte))

        def liquidacionesAntimonio = null

        if (tipoReporte.equals("fechas")){
            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)
            leyMinimaAntimonio = ""+params.leyMinimaAntimonio
            leyMinimaAntimonio = (leyMinimaAntimonio.equals(""))?0:Float.parseFloat(params.leyMinimaAntimonio)
            leyMaximaAntimonio = ""+params.leyMaximaAntimonio
            leyMaximaAntimonio = (leyMaximaAntimonio.equals(""))?0:Float.parseFloat(params.leyMaximaAntimonio)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(5,1, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))
            sheet1.addCell(new Label(5,2, "ENTRE LEYES: ${leyMinimaAntimonio}% AL ${leyMaximaAntimonio}%",formatoInfoReporte))

            def liquidaciones = LiquidacionDeAntimonio.findAllByFechaDeLiquidacionBetweenAndPorcentajeAntimonioBetweenAndConjuntoAntimonio(fechaInicial,fechaFinal,leyMinimaAntimonio,leyMaximaAntimonio,"-")

            liquidacionesAntimonio=new ArrayList<LiquidacionDeAntimonio>()
            liquidaciones.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesAntimonio.add(it)
            }
        }
        if (tipoReporte.equals("fechasEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)
            leyMinimaAntimonio = ""+params.leyMinimaAntimonio
            leyMinimaAntimonio = (leyMinimaAntimonio.equals(""))?0:Float.parseFloat(params.leyMinimaAntimonio)
            leyMaximaAntimonio = ""+params.leyMaximaAntimonio
            leyMaximaAntimonio = (leyMaximaAntimonio.equals(""))?0:Float.parseFloat(params.leyMaximaAntimonio)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(3,3, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(4,3, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(5,1, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))
            sheet1.addCell(new Label(5,2, "ENTRE LEYES: ${leyMinimaAntimonio}% AL ${leyMaximaAntimonio}%",formatoInfoReporte))

            //liquidacionesAntimonio = LiquidacionDeAntimonio.findAllByFechaDeLiquidacionBetweenAndPorcentajeAntimonioBetweenAndNombreEmpresaLikeAndConjuntoAntimonio(fechaInicial,fechaFinal,leyMinimaAntimonio,leyMaximaAntimonio,"%${empresa.nombreDeEmpresa}%","-")

            def liquidaciones = LiquidacionDeAntimonio.findAllByFechaDeLiquidacionBetweenAndPorcentajeAntimonioBetweenAndConjuntoAntimonio(fechaInicial,fechaFinal,leyMinimaAntimonio,leyMaximaAntimonio,"-")

            def liquidaciones1 = new ArrayList<LiquidacionDeAntimonio>()
            liquidaciones.each {
                if(it.recepcionDeAntimonio.empresa.id==empresa.id){
                    liquidaciones1.add(it)
                }
            }

            liquidacionesAntimonio=new ArrayList<LiquidacionDeAntimonio>()
            liquidaciones1.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesAntimonio.add(it)
            }
        }
        if (tipoReporte.equals("lotes")){
            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            leyMinimaAntimonio = ""+params.leyMinimaAntimonio
            leyMinimaAntimonio = (leyMinimaAntimonio.equals(""))?0:Float.parseFloat(params.leyMinimaAntimonio)
            leyMaximaAntimonio = ""+params.leyMaximaAntimonio
            leyMaximaAntimonio = (leyMaximaAntimonio.equals(""))?0:Float.parseFloat(params.leyMaximaAntimonio)

            def liquidaciones1 = LiquidacionDeAntimonio.findAllByPorcentajeAntimonioBetweenAndConjuntoAntimonio(leyMinimaAntimonio,leyMaximaAntimonio,"-")

            def liquidaciones2=new ArrayList<LiquidacionDeAntimonio>()
            liquidaciones1.each {
                def lote = Integer.parseInt(it.lote)
                if (lote>=loteInicial&&lote<=loteFinal)
                    liquidaciones2.add(it)
            }

            liquidacionesAntimonio=new ArrayList<LiquidacionDeAntimonio>()
            liquidaciones2.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesAntimonio.add(it)
            }

            sheet1.addCell(new Label(5,1, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))
            sheet1.addCell(new Label(5,2, "ENTRE LEYES: ${leyMinimaAntimonio}% AL ${leyMaximaAntimonio}%",formatoInfoReporte))
        }
        if (tipoReporte.equals("lotesEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            leyMinimaAntimonio = ""+params.leyMinimaAntimonio
            leyMinimaAntimonio = (leyMinimaAntimonio.equals(""))?0:Float.parseFloat(params.leyMinimaAntimonio)
            leyMaximaAntimonio = ""+params.leyMaximaAntimonio
            leyMaximaAntimonio = (leyMaximaAntimonio.equals(""))?0:Float.parseFloat(params.leyMaximaAntimonio)

            def liquidaciones1 = LiquidacionDeAntimonio.findAllByPorcentajeAntimonioBetweenAndConjuntoAntimonio(leyMinimaAntimonio,leyMaximaAntimonio,"-")

            def liquidaciones2=new ArrayList<LiquidacionDeAntimonio>()
            liquidaciones1.each {
                def lote = Integer.parseInt(it.lote)
                if (lote>=loteInicial&&lote<=loteFinal&&it.recepcionDeComplejo.empresa.id==empresa.id)
                    liquidaciones2.add(it)
            }

            liquidacionesAntimonio=new ArrayList<LiquidacionDeAntimonio>()
            liquidaciones2.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesAntimonio.add(it)
            }

            sheet1.addCell(new Label(3,3, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(4,3, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(5,1, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))
            sheet1.addCell(new Label(5,2, "ENTRE LEYES: ${leyMinimaAntimonio}% AL ${leyMaximaAntimonio}%",formatoInfoReporte))
        }

        sheet1.addCell(new Label(0,6, "RECEPCION",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "COT. DIA",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "LIQUIDACION",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "NOMBRE",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "TOTAL TARA",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "K. N. H.",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "% H2O",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "K. N. S.",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "LEY %Sb",formatoEncabezado))
        sheet1.addCell(new Label(13,6, "LEY %Pb",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "LEY %As",formatoEncabezado))
        sheet1.addCell(new Label(15,6, "K. F. Sb",formatoEncabezado))
        sheet1.addCell(new Label(16,6, "K. F. Pb",formatoEncabezado))
        sheet1.addCell(new Label(17,6, "K. F. As",formatoEncabezado))
        sheet1.addCell(new Label(18,6, "COT. OFICIAL",formatoEncabezado))
        sheet1.addCell(new Label(19,6, "VALOR OF. BRUTO",formatoEncabezado))
        sheet1.addCell(new Label(20,6, "ALICUOTA %",formatoEncabezado))
        sheet1.addCell(new Label(21,6, "VALOR NETO \$us",formatoEncabezado))
        sheet1.addCell(new Label(22,6, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(23,6, "BONO CALIDAD",formatoEncabezado))
        sheet1.addCell(new Label(24,6, "BONO INCENTIVO",formatoEncabezado))
        sheet1.addCell(new Label(25,6, "VALOR DE COMPRA",formatoEncabezado))
        sheet1.addCell(new Label(26,6, "RM",formatoEncabezado))

        if (!liquidacionesAntimonio) {
            flash.error = "NO SE PUDO OBTENER RESULTADOS!"
            System.out.println("*** SE ESTA PRODUCIENDO RESULTADOS NULL!!!")
            redirect(action: "create")
            return
        }

        if (liquidacionesAntimonio.size()==0 || nombreDelConjunto.equals("")){
            if (liquidacionesAntimonio.size()==0)
                sheet1.addCell(new Label(0,7, "SIN RESULTADOS",formatoInfoReporte))
            if (nombreDelConjunto.equals(""))
                sheet1.addCell(new Label(0,7, "ESPECIFIQUE NOMBRE DE CONJUNTO",formatoInfoReporte))
        }else{

            /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA RETENCIONES DE LEY*/
            def listaRetencionesDeLey = retencionesAntimonioJSON liquidacionesAntimonio,"DE LEY"
            def columna = 27
            listaRetencionesDeLey.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL RET. DE LEY",formatoEncabezado))
            columna++

            /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA OTRAS RETENCIONES*/
            def listaRetencionesOtras = retencionesAntimonioJSON liquidacionesAntimonio,"OTRA"
            listaRetencionesOtras.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL OTRAS RET.",formatoEncabezado))
            columna++

            sheet1.addCell(new Label(columna,6, "TOTAL RET.",formatoEncabezado))
            sheet1.addCell(new Label(columna+1,6, "TOTAL PAGADO",formatoEncabezado))
            sheet1.addCell(new Label(columna+2,6, "ANTICIPO/ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+3,6, "ANTICIPO/F. ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+4,6, "LIQUIDO PAGABLE",formatoEncabezado))
            sheet1.addCell(new Label(columna+5,6, "CANC. TRANSPORTE",formatoEncabezado))
            sheet1.addCell(new Label(columna+6,6, "CANC. LABORAT.",formatoEncabezado))

            //DESPLIEGUE DE DATOS DE LIQUIDACIONES
            //formatoEncabezado.setAlignment(Alignment.RIGHT)
            def fila = 7
            //variables acumuladoras
            def numeroRegistros=0
            def totalCantidadSacos=0
            def totalTotalTara=0
            def totalPesoBruto=0
            def totalKilosNetosHumedos=0
            def totalHumedad=0
            def totalKilosNetosSecos=0
            def totalKilosNetosSecosCotizacionDiaria=0
            def totalPorcentajeAntimonio=0
            def totalPorcentajePlomo=0
            def totalPorcentajeArsenico=0
            def totalKilosFinosAntimonio=0
            def totalKilosFinosPlomo=0
            def totalKilosFinosArsenico=0
            def totalCotizacionQuincenalAntimonio=0
            def totalValorOficialBruto=0
            def totalCotizacionDiariaAntimonio=0
            def totalAlicuota=0
            def totalValorNeto=0
            def totalValorNetoBolivianos=0
            def totalBonoCalidad=0
            def totalBonoIncentivo=0
            def totalValorDeCompra=0
            def totalRegaliaMinera=0
            def totalTotalRetenciones=0
            def totalTotalPagado=0
            def totalTotalAnticiposContraEntrega=0
            def totalTotalAnticiposContraFuturaEntrega=0
            def totalTotalLiquidoPagable=0
            def totalCostoDeTransporte=0
            def totalTotalCostoLaboratorio=0

            def cuotaParticipacionEmpresa = new ArrayList<String>()
            def cuotaParticipacionCuota = new ArrayList<Integer>()

            liquidacionesAntimonio.each {
                numeroRegistros++
                totalCantidadSacos+=it.cantidadDeSacos
                totalTotalTara+=it.tara*it.cantidadDeSacos
                totalPesoBruto+=it.pesoBruto
                totalKilosNetosHumedos+=it.kilosNetosHumedos
                totalHumedad+=it.humedad
                totalKilosNetosSecos+=it.kilosNetosSecos
                totalKilosNetosSecosCotizacionDiaria+=(it.kilosNetosSecos*it.recepcionDeAntimonio.cotizacionDiariaDeMinerales.antimonio)
                totalPorcentajeAntimonio+=it.porcentajeAntimonio
                totalPorcentajePlomo+=it.porcentajePlomo
                totalPorcentajeArsenico+=it.porcentajeArsenico
                totalKilosFinosAntimonio+=it.kilosFinosAntimonio
                totalKilosFinosPlomo+=(it.kilosNetosSecos*it.porcentajePlomo/100)
                totalKilosFinosArsenico+=(it.kilosNetosSecos*it.porcentajeArsenico/100)
                totalCotizacionQuincenalAntimonio+=it.recepcionDeAntimonio.cotizacionQuincenalDeMinerales.antimonio
                totalValorOficialBruto+=it.valorOficialBruto
                totalCotizacionDiariaAntimonio+=it.recepcionDeAntimonio.cotizacionDiariaDeMinerales.antimonio
                totalAlicuota+=it.recepcionDeAntimonio.alicuota.antimonio
                totalValorNeto+=it.valorNetoMineral
                totalValorNetoBolivianos+=it.valorNetoMineralEnBolivianos
                totalBonoCalidad+=it.bonoCalidad
                totalBonoIncentivo+=it.bonoIncentivo
                totalValorDeCompra+=it.valorDeCompra
                totalRegaliaMinera+=it.regaliaMinera
                totalTotalRetenciones+=it.totalRetenciones
                totalTotalPagado+=it.totalPagado
                totalTotalAnticiposContraEntrega+=it.totalAnticiposContraEntrega
                totalTotalAnticiposContraFuturaEntrega+=it.totalAnticiposContraFuturaEntrega
                totalTotalLiquidoPagable=totalTotalLiquidoPagable+((it.totalLiquidoPagable.doubleValue()<0)?0:it.totalLiquidoPagable.doubleValue())
                totalCostoDeTransporte+=it.recepcionDeAntimonio.costoDeTransporte
                totalTotalCostoLaboratorio+=it.totalCostoLaboratorio

                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new Number(1,fila, it.recepcionDeAntimonio.cotizacionDiariaDeMinerales.antimonio,formatoDatos))
                sheet1.addCell(new DateTime(2,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(3,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(4,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(5,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(6,fila, it.cantidadDeSacos,formatoDatos))
                sheet1.addCell(new Number(7,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(8,fila, it.tara*it.cantidadDeSacos,formatoDatos))
                sheet1.addCell(new Number(9,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(10,fila, it.humedad,formatoDatos))
                sheet1.addCell(new Number(11,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(12,fila, it.porcentajeAntimonio,formatoDatos))
                sheet1.addCell(new Number(13,fila, it.porcentajePlomo,formatoDatos))
                sheet1.addCell(new Number(14,fila, it.porcentajeArsenico,formatoDatos))
                sheet1.addCell(new Number(15,fila, it.kilosFinosAntimonio,formatoDatos))
                sheet1.addCell(new Number(16,fila, it.kilosNetosSecos*it.porcentajePlomo/100,formatoDatos))
                sheet1.addCell(new Number(17,fila, it.kilosNetosSecos*it.porcentajeArsenico/100,formatoDatos))
                sheet1.addCell(new Number(18,fila, it.recepcionDeAntimonio.cotizacionQuincenalDeMinerales.antimonio,formatoDatos))
                sheet1.addCell(new Number(19,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(20,fila, it.recepcionDeAntimonio.alicuota.antimonio,formatoDatos))
                sheet1.addCell(new Number(21,fila, it.valorNetoMineral,formatoDatos))
                sheet1.addCell(new Number(22,fila, it.valorNetoMineralEnBolivianos,formatoDatos))
                sheet1.addCell(new Number(23,fila, it.bonoCalidad,formatoDatos))
                sheet1.addCell(new Number(24,fila, it.bonoIncentivo,formatoDatos))
                sheet1.addCell(new Number(25,fila, it.valorDeCompra,formatoDatos))
                sheet1.addCell(new Number(26,fila, it.regaliaMinera,formatoDatos))

                columna = 27

                /*DESPLIEGUE DE RETENCIONES DE LEY*/
                def retencionesDeLeyLiquidacion = LiquidacionDeAntimonioRetenciones.findAllByLiquidacionDeAntimonioAndTipoDeRetencion(it,"DE LEY")
                def numretDeLey = retencionesDeLeyLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretDeLey} RETENCIONES DE LEY!")
                def subtotalRetencionesDeLey=it.regaliaMinera.doubleValue()
                for(int i=0;i<listaRetencionesDeLey.size();i++){
                    def vr = valorRetencion(listaRetencionesDeLey.get(i), retencionesDeLeyLiquidacion,numretDeLey)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesDeLey+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesDeLey,formatoDatos))
                columna++

                /*DESPLIEGUE DE RETENCIONES DE LEY*/
                def retencionesOtrasLiquidacion = LiquidacionDeAntimonioRetenciones.findAllByLiquidacionDeAntimonioAndTipoDeRetencion(it,"OTRA")
                def numretOtras = retencionesOtrasLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretOtras} RETENCIONES DE LEY!")
                def subtotalRetencionesOtras=0
                for(int i=0;i<listaRetencionesOtras.size();i++){
                    def vr = valorRetencion(listaRetencionesOtras.get(i), retencionesOtrasLiquidacion,numretOtras)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesOtras+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesOtras,formatoDatos))
                columna++

                sheet1.addCell(new Number(columna,fila, it.totalRetenciones,formatoDatos))
                sheet1.addCell(new Number(columna+1,fila, it.totalPagado,formatoDatos))
                sheet1.addCell(new Number(columna+2,fila, it.totalAnticiposContraEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+3,fila, it.totalAnticiposContraFuturaEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+4,fila, it.totalLiquidoPagable,formatoDatos))
                sheet1.addCell(new Number(columna+5,fila, it.recepcionDeAntimonio.costoDeTransporte,formatoDatos))
                sheet1.addCell(new Number(columna+6,fila, it.totalCostoLaboratorio,formatoDatos))

                fila++

                if (cuotaParticipacionEmpresa.contains(it.nombreEmpresa)){
                    def obj=cuotaParticipacionCuota.get(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa))
                    obj++
                    cuotaParticipacionCuota.set(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa),obj)
                }else{
                    cuotaParticipacionEmpresa.add(it.nombreEmpresa)
                    cuotaParticipacionCuota.add(1)
                }

            }

            //IMPRESION DE TOTALES
            //sheet1.addCell(new Number(1,fila, totalCotizacionDiariaAntimonio/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(1,fila, totalKilosNetosSecosCotizacionDiaria/totalKilosNetosSecos,formatoDatos))
            sheet1.addCell(new Number(6,fila, totalCantidadSacos,formatoDatos))
            sheet1.addCell(new Number(8,fila, totalTotalTara,formatoDatos))
            sheet1.addCell(new Number(7,fila, totalPesoBruto,formatoDatos))
            sheet1.addCell(new Number(9,fila, totalKilosNetosHumedos,formatoDatos))
            //sheet1.addCell(new Number(10,fila, totalHumedad/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(10,fila, (totalKilosNetosSecos/totalKilosNetosHumedos*100-100)*-1,formatoDatos))
            sheet1.addCell(new Number(11,fila, totalKilosNetosSecos,formatoDatos))
            //sheet1.addCell(new Number(12,fila, totalPorcentajeAntimonio/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(12,fila, totalKilosFinosAntimonio/totalKilosNetosSecos*100000,formatoDatos))
            sheet1.addCell(new Number(13,fila, totalKilosFinosPlomo/totalKilosNetosSecos*100,formatoDatos))
            sheet1.addCell(new Number(14,fila, totalKilosFinosArsenico/totalKilosNetosSecos*100,formatoDatos))
            sheet1.addCell(new Number(15,fila, totalKilosFinosAntimonio,formatoDatos))
            sheet1.addCell(new Number(16,fila, totalKilosFinosPlomo,formatoDatos))
            sheet1.addCell(new Number(17,fila, totalKilosFinosArsenico,formatoDatos))
            sheet1.addCell(new Number(18,fila, totalCotizacionQuincenalAntimonio/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(19,fila, totalValorOficialBruto,formatoDatos))
            sheet1.addCell(new Number(20,fila, totalAlicuota/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(21,fila, totalValorNeto,formatoDatos))
            sheet1.addCell(new Number(22,fila, totalValorNetoBolivianos,formatoDatos))
            sheet1.addCell(new Number(23,fila, totalBonoCalidad,formatoDatos))
            sheet1.addCell(new Number(24,fila, totalBonoIncentivo,formatoDatos))
            sheet1.addCell(new Number(25,fila, totalValorDeCompra,formatoDatos))
            sheet1.addCell(new Number(26,fila, totalRegaliaMinera,formatoDatos))

            def columnaFinalRetenciones = 29+listaRetencionesDeLey.size()+listaRetencionesOtras.size()
            def totalLiquidaciones = liquidacionesAntimonio.size()
            for (int col=27;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =7;fil<totalLiquidaciones+7;fil++){
                    def valor = Double.parseDouble(sheet1.getWritableCell(col,fil).contents)
                    tret+=valor
                }
                sheet1.addCell(new Number(col,totalLiquidaciones+7, tret,formatoDatos))
            }

            sheet1.addCell(new Number(columnaFinalRetenciones,fila, totalTotalRetenciones,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+1,fila, totalTotalPagado,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+2,fila, totalTotalAnticiposContraEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+3,fila, totalTotalAnticiposContraFuturaEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+4,fila, totalTotalLiquidoPagable,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+5,fila, totalCostoDeTransporte,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+6,fila, totalTotalCostoLaboratorio,formatoDatos))

//            sheet1.mergeCells(24, 5, 25+listaRetencionesDeLey.size(), 5);
//            sheet1.mergeCells(26+listaRetencionesDeLey.size(), 5, 26+listaRetencionesDeLey.size()+listaRetencionesOtras.size(), 5);
//            sheet1.addCell(new Label(24,5, "RETENCIONES DE LEY",formatoEncabezado))
//            sheet1.addCell(new Label(26+listaRetencionesDeLey.size(),5, "OTRAS RETENCIONES",formatoEncabezado))

            sheet1.mergeCells(26, 5, 27+listaRetencionesDeLey.size(), 5);
            sheet1.mergeCells(28+listaRetencionesDeLey.size(), 5, 28+listaRetencionesDeLey.size()+listaRetencionesOtras.size(), 5);
            sheet1.addCell(new Label(26,5, "RETENCIONES DE LEY",formatoEncabezado))
            sheet1.addCell(new Label(28+listaRetencionesDeLey.size(),5, "OTRAS RETENCIONES",formatoEncabezado))

            //IMPRESION DE DISTRIBUCION PORCENTUAL
            sheet1.mergeCells(4, fila+3, 5, fila+3);
            sheet1.addCell(new Label(4,fila+3,"VOLUMEN DE PARTICIPACION POR EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(4,fila+4, "EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(5,fila+4, "PORCENTAJE",formatoEncabezado))
            for (int i=0;i<cuotaParticipacionEmpresa.size();i++){
                sheet1.addCell(new Label(4,fila+5+i, cuotaParticipacionEmpresa.get(i),formatoDatos))
                sheet1.addCell(new Number(5,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))
            }
            sheet1.addCell(new Label(4,fila+5+cuotaParticipacionEmpresa.size(),"TOTAL",formatoDatos))
            sheet1.addCell(new Number(5,fila+5+cuotaParticipacionEmpresa.size(),100.0,formatoDatos))
            //sheet1.addCell(new Number(5,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))

            //ACTUALIZACION DEL CAMPO "conjuntoAntimonio" ASIGNANDO EL NOMBRE DE CONJUNTO ESPECIFICADO
            if(asignarConjuntoALotes.equals("SI")){
                def reporteHojaDeCosto = new ReporteHojaDeCosto(
                        elemento: "Antimonio",
                        nombreDelConjunto: nombreDelConjunto,
                        destinoDelConjunto: destinoDelConjunto,
                        asignarConjuntoALotes: "SI",
                        ignorarLotes: ignorarLotes,
                        empresa: empresa,
                        fechaInicial: fechaInicial,
                        fechaFinal: fechaFinal,
                        loteInicial: loteInicial,
                        loteFinal: loteFinal,
                        leyMinimaEstano: null,
                        leyMaximaEstano: null,
                        leyMinimaPlata: null,
                        leyMaximaPlata: null,
                        leyMinimaWolfran: null,
                        leyMaximaWolfran: null,
                        leyMinimaAntimonio: leyMinimaAntimonio,
                        leyMaximaAntimonio: leyMaximaAntimonio,
                        leyMinimaZincComplejo: null,
                        leyMaximaZincComplejo: null,
                        leyMinimaPlomoComplejo: null,
                        leyMaximaPlomoComplejo: null,
                        leyMinimaPlataComplejo: null,
                        leyMaximaPlataComplejo: null,
                        leyMinimaPlomoPlomoPlata: null,
                        leyMaximaPlomoPlomoPlata: null,
                        leyMinimaPlataPlomoPlata: null,
                        leyMaximaPlataPlomoPlata: null,
                        leyMinimaZincZincPlata: null,
                        leyMaximaZincZincPlata: null,
                        leyMinimaPlataZincPlata: null,
                        leyMaximaPlataZincPlata: null
                )
                reporteHojaDeCosto.save(failOnError: true)

                liquidacionesAntimonio.each {
                    it.conjuntoAntimonio=nombreDelConjunto
                    it.save(failOnError: true)
                }
            }

            workbook.write();
            workbook.close();
        }
    }

    def crearReporteComplejo = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Hoja de Costo de Complejo", 0)
        sheet1.setColumnView(0,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(1,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(2,11)
        sheet1.setColumnView(3,11)
        sheet1.setColumnView(4,11)
        sheet1.setColumnView(5,40)
        sheet1.setColumnView(6,40)
        sheet1.setRowView(6,500)
        for(i in 7..100)
            sheet1.setColumnView(i,11)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD);
        WritableCellFormat formatoEncabezado = new WritableCellFormat (arial10BoldFont);
        formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)
        formatoEncabezado.setWrap(true)
        formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
        //formatoEncabezado.setAlignment(Alignment.CENTRE)
        WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoDatos.setFont(courier8PlainFont)
        formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)
        WritableCellFormat formatoInfoReporte = new WritableCellFormat (arial14BoldFont);
        WritableCellFormat formatoTitulo = new WritableCellFormat (arial16BoldFont);
        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="hoja_costo_complejo.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(5,0, "HOJA DE COSTO DE COMPLEJO",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def tipoReporte = ""+params.tipoReporte
        def asignarConjuntoALotes = ""+params.asignarConjuntoALotes
        def nombreDelConjunto = ""+params.nombreDelConjunto
        def destinoDelConjunto = ""+params.destinoDelConjunto

        def ignorarLotes = ""+params.ignorarLotes
        def lotesIgnorados = ignorarLotes.tokenize(',')

        def empresa=null
        def fechaInicial=null
        def fechaFinal=null
        def leyMinimaZincComplejo=null
        def leyMaximaZincComplejo=null
        def leyMinimaPlomoComplejo=null
        def leyMaximaPlomoComplejo=null
        def leyMinimaPlataComplejo=null
        def leyMaximaPlataComplejo=null
        def loteInicial=""
        def loteFinal=""

        sheet1.addCell(new Label(5,1, "NOMBRE DEL CONJUNTO:",formatoInfoReporte))
        sheet1.addCell(new Label(6,1, "${nombreDelConjunto}",formatoInfoReporte))
        sheet1.addCell(new Label(5,2, "DESTINO:",formatoInfoReporte))
        sheet1.addCell(new Label(6,2, "${destinoDelConjunto}",formatoInfoReporte))

        def liquidacionesComplejo = null

        if (tipoReporte.equals("fechas")){
            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            leyMinimaZincComplejo = ""+params.leyMinimaZincComplejo
            leyMinimaZincComplejo = (leyMinimaZincComplejo.equals(""))?0:Float.parseFloat(params.leyMinimaZincComplejo)
            leyMaximaZincComplejo = ""+params.leyMaximaZincComplejo
            leyMaximaZincComplejo = (leyMaximaZincComplejo.equals(""))?0:Float.parseFloat(params.leyMaximaZincComplejo)

            leyMinimaPlomoComplejo = ""+params.leyMinimaPlomoComplejo
            leyMinimaPlomoComplejo = (leyMinimaPlomoComplejo.equals(""))?0:Float.parseFloat(params.leyMinimaPlomoComplejo)
            leyMaximaPlomoComplejo = ""+params.leyMaximaPlomoComplejo
            leyMaximaPlomoComplejo = (leyMaximaPlomoComplejo.equals(""))?0:Float.parseFloat(params.leyMaximaPlomoComplejo)

            leyMinimaPlataComplejo = ""+params.leyMinimaPlataComplejo
            leyMinimaPlataComplejo = (leyMinimaPlataComplejo.equals(""))?0:Float.parseFloat(params.leyMinimaPlataComplejo)
            leyMaximaPlataComplejo = ""+params.leyMaximaPlataComplejo
            leyMaximaPlataComplejo = (leyMaximaPlataComplejo.equals(""))?0:Float.parseFloat(params.leyMaximaPlataComplejo)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(7,1, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))
            sheet1.addCell(new Label(7,2, "ENTRE LEYES:",formatoInfoReporte))
            sheet1.addCell(new Label(9,2, "Zn: ${leyMinimaZincComplejo}% AL ${leyMaximaZincComplejo}%",formatoInfoReporte))
            sheet1.addCell(new Label(9,3, "Pb: ${leyMinimaPlomoComplejo}% AL ${leyMaximaPlomoComplejo}%",formatoInfoReporte))
            sheet1.addCell(new Label(9,4, "Ag: ${leyMinimaPlataComplejo}% AL ${leyMaximaPlataComplejo}%",formatoInfoReporte))

            def liquidaciones = LiquidacionDeComplejo.findAllByFechaDeLiquidacionBetweenAndPorcentajeZincFinalBetweenAndPorcentajePlomoFinalBetweenAndPorcentajePlataFinalBetweenAndConjuntoComplejo(fechaInicial,fechaFinal,leyMinimaZincComplejo,leyMaximaZincComplejo,leyMinimaPlomoComplejo,leyMaximaPlomoComplejo,leyMinimaPlataComplejo,leyMaximaPlataComplejo,"-")
            //def liquidaciones = LiquidacionDeComplejo.findAllByFechaDeLiquidacionBetweenAndPorcentajeZincFinalBetweenAndPorcentajePlomoFinalBetweenAndPorcentajePlataFinalBetweenAndConjuntoComplejo(fechaInicial,fechaFinal,0,100,0,100,0,100,"-")

            liquidacionesComplejo=new ArrayList<LiquidacionDeComplejo>()
            liquidaciones.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesComplejo.add(it)
            }
        }

        if (tipoReporte.equals("fechasEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            leyMinimaZincComplejo = ""+params.leyMinimaZincComplejo
            leyMinimaZincComplejo = (leyMinimaZincComplejo.equals(""))?0:Float.parseFloat(params.leyMinimaZincComplejo)
            leyMaximaZincComplejo = ""+params.leyMaximaZincComplejo
            leyMaximaZincComplejo = (leyMaximaZincComplejo.equals(""))?0:Float.parseFloat(params.leyMaximaZincComplejo)

            leyMinimaPlomoComplejo = ""+params.leyMinimaPlomoComplejo
            leyMinimaPlomoComplejo = (leyMinimaPlomoComplejo.equals(""))?0:Float.parseFloat(params.leyMinimaPlomoComplejo)
            leyMaximaPlomoComplejo = ""+params.leyMaximaPlomoComplejo
            leyMaximaPlomoComplejo = (leyMaximaPlomoComplejo.equals(""))?0:Float.parseFloat(params.leyMaximaPlomoComplejo)

            leyMinimaPlataComplejo = ""+params.leyMinimaPlataComplejo
            leyMinimaPlataComplejo = (leyMinimaPlataComplejo.equals(""))?0:Float.parseFloat(params.leyMinimaPlataComplejo)
            leyMaximaPlataComplejo = ""+params.leyMaximaPlataComplejo
            leyMaximaPlataComplejo = (leyMaximaPlataComplejo.equals(""))?0:Float.parseFloat(params.leyMaximaPlataComplejo)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(5,3, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(6,3, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(7,1, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))
            sheet1.addCell(new Label(7,2, "ENTRE LEYES:",formatoInfoReporte))
            sheet1.addCell(new Label(9,2, "Zn: ${leyMinimaZincComplejo}% AL ${leyMaximaZincComplejo}%",formatoInfoReporte))
            sheet1.addCell(new Label(9,3, "Pb: ${leyMinimaPlomoComplejo}% AL ${leyMaximaPlomoComplejo}%",formatoInfoReporte))
            sheet1.addCell(new Label(9,4, "Ag: ${leyMinimaPlataComplejo}% AL ${leyMaximaPlataComplejo}%",formatoInfoReporte))

            def liquidaciones = LiquidacionDeComplejo.findAllByFechaDeLiquidacionBetweenAndPorcentajeZincFinalBetweenAndPorcentajePlomoFinalBetweenAndPorcentajePlataFinalBetweenAndConjuntoComplejo(fechaInicial,fechaFinal,leyMinimaZincComplejo,leyMaximaZincComplejo,leyMinimaPlomoComplejo,leyMaximaPlomoComplejo,leyMinimaPlataComplejo,leyMaximaPlataComplejo,"-")
            // && it.recepcionDeEstano.empresa.id==empresa.id

            def liquidaciones1 = new ArrayList<LiquidacionDeComplejo>()
            liquidaciones.each {
                if(it.recepcionDeComplejo.empresa.id==empresa.id){
                    liquidaciones1.add(it)
                }
            }

            liquidacionesComplejo=new ArrayList<LiquidacionDeComplejo>()
            liquidaciones1.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesComplejo.add(it)
            }
        }
        if (tipoReporte.equals("lotes")){
            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            leyMinimaZincComplejo = ""+params.leyMinimaZincComplejo
            leyMinimaZincComplejo = (leyMinimaZincComplejo.equals(""))?0:Float.parseFloat(params.leyMinimaZincComplejo)
            leyMaximaZincComplejo = ""+params.leyMaximaZincComplejo
            leyMaximaZincComplejo = (leyMaximaZincComplejo.equals(""))?0:Float.parseFloat(params.leyMaximaZincComplejo)

            leyMinimaPlomoComplejo = ""+params.leyMinimaPlomoComplejo
            leyMinimaPlomoComplejo = (leyMinimaPlomoComplejo.equals(""))?0:Float.parseFloat(params.leyMinimaPlomoComplejo)
            leyMaximaPlomoComplejo = ""+params.leyMaximaPlomoComplejo
            leyMaximaPlomoComplejo = (leyMaximaPlomoComplejo.equals(""))?0:Float.parseFloat(params.leyMaximaPlomoComplejo)

            leyMinimaPlataComplejo = ""+params.leyMinimaPlataComplejo
            leyMinimaPlataComplejo = (leyMinimaPlataComplejo.equals(""))?0:Float.parseFloat(params.leyMinimaPlataComplejo)
            leyMaximaPlataComplejo = ""+params.leyMaximaPlataComplejo
            leyMaximaPlataComplejo = (leyMaximaPlataComplejo.equals(""))?0:Float.parseFloat(params.leyMaximaPlataComplejo)

            def liquidaciones1 = LiquidacionDeComplejo.findAllByPorcentajeZincFinalBetweenAndPorcentajePlomoFinalBetweenAndPorcentajePlataFinalBetweenAndConjuntoComplejo(leyMinimaZincComplejo,leyMaximaZincComplejo,leyMinimaPlomoComplejo,leyMaximaPlomoComplejo,leyMinimaPlataComplejo,leyMaximaPlataComplejo,"-")

            def liquidaciones2=new ArrayList<LiquidacionDeComplejo>()
            liquidaciones1.each {
                def lote = Integer.parseInt(it.lote)
                if (lote>=loteInicial&&lote<=loteFinal)
                    liquidaciones2.add(it)
            }

            liquidacionesComplejo=new ArrayList<LiquidacionDeComplejo>()
            liquidaciones2.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesComplejo.add(it)
            }

            sheet1.addCell(new Label(7,1, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))
            sheet1.addCell(new Label(7,2, "ENTRE LEYES:",formatoInfoReporte))
            sheet1.addCell(new Label(9,2, "Zn: ${leyMinimaZincComplejo}% AL ${leyMaximaZincComplejo}%",formatoInfoReporte))
            sheet1.addCell(new Label(9,3, "Pb: ${leyMinimaPlomoComplejo}% AL ${leyMaximaPlomoComplejo}%",formatoInfoReporte))
            sheet1.addCell(new Label(9,4, "Ag: ${leyMinimaPlataComplejo}% AL ${leyMaximaPlataComplejo}%",formatoInfoReporte))
        }
        if (tipoReporte.equals("lotesEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            leyMinimaZincComplejo = ""+params.leyMinimaZincComplejo
            leyMinimaZincComplejo = (leyMinimaZincComplejo.equals(""))?0:Float.parseFloat(params.leyMinimaZincComplejo)
            leyMaximaZincComplejo = ""+params.leyMaximaZincComplejo
            leyMaximaZincComplejo = (leyMaximaZincComplejo.equals(""))?0:Float.parseFloat(params.leyMaximaZincComplejo)

            leyMinimaPlomoComplejo = ""+params.leyMinimaPlomoComplejo
            leyMinimaPlomoComplejo = (leyMinimaPlomoComplejo.equals(""))?0:Float.parseFloat(params.leyMinimaPlomoComplejo)
            leyMaximaPlomoComplejo = ""+params.leyMaximaPlomoComplejo
            leyMaximaPlomoComplejo = (leyMaximaPlomoComplejo.equals(""))?0:Float.parseFloat(params.leyMaximaPlomoComplejo)

            leyMinimaPlataComplejo = ""+params.leyMinimaPlataComplejo
            leyMinimaPlataComplejo = (leyMinimaPlataComplejo.equals(""))?0:Float.parseFloat(params.leyMinimaPlataComplejo)
            leyMaximaPlataComplejo = ""+params.leyMaximaPlataComplejo
            leyMaximaPlataComplejo = (leyMaximaPlataComplejo.equals(""))?0:Float.parseFloat(params.leyMaximaPlataComplejo)

            def liquidaciones1 = LiquidacionDeComplejo.findAllByPorcentajeZincFinalBetweenAndPorcentajePlomoFinalBetweenAndPorcentajePlataFinalBetweenAndConjuntoComplejo(leyMinimaZincComplejo,leyMaximaZincComplejo,leyMinimaPlomoComplejo,leyMaximaPlomoComplejo,leyMinimaPlataComplejo,leyMaximaPlataComplejo,"-")

            def liquidaciones2=new ArrayList<LiquidacionDeComplejo>()
            liquidaciones1.each {
                def lote = Integer.parseInt(it.lote)
                if (lote>=loteInicial&&lote<=loteFinal&&it.recepcionDeComplejo.empresa.id==empresa.id)
                    liquidaciones2.add(it)
            }

            liquidacionesComplejo=new ArrayList<LiquidacionDeComplejo>()
            liquidaciones2.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesComplejo.add(it)
            }

            sheet1.addCell(new Label(5,3, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(6,3, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(7,1, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))
            sheet1.addCell(new Label(7,2, "ENTRE LEYES:",formatoInfoReporte))
            sheet1.addCell(new Label(9,2, "Zn: ${leyMinimaZincComplejo}% AL ${leyMaximaZincComplejo}%",formatoInfoReporte))
            sheet1.addCell(new Label(9,3, "Pb: ${leyMinimaPlomoComplejo}% AL ${leyMaximaPlomoComplejo}%",formatoInfoReporte))
            sheet1.addCell(new Label(9,4, "Ag: ${leyMinimaPlataComplejo}% AL ${leyMaximaPlataComplejo}%",formatoInfoReporte))
        }

        sheet1.addCell(new Label(0,6, "RECEPCION",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "COT. DIA Zn",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "COT. DIA Pb",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "COT. DIA Ag",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "LIQUIDACION",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "NOMBRE",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "MERMA",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "K. N. H.",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "% H2O",formatoEncabezado))
        sheet1.addCell(new Label(13,6, "K. N. S.",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "LEY %Zn",formatoEncabezado))
        sheet1.addCell(new Label(15,6, "LEY %Pb",formatoEncabezado))
        sheet1.addCell(new Label(16,6, "LEY DM Ag",formatoEncabezado))
        sheet1.addCell(new Label(17,6, "K. F. Zn",formatoEncabezado))
        sheet1.addCell(new Label(18,6, "K. F. Pb",formatoEncabezado))
        sheet1.addCell(new Label(19,6, "K. F. Ag",formatoEncabezado))
        sheet1.addCell(new Label(20,6, "COT. OFICIAL Zn",formatoEncabezado))
        sheet1.addCell(new Label(21,6, "COT. OFICIAL Pb",formatoEncabezado))
        sheet1.addCell(new Label(22,6, "COT. OFICIAL Ag",formatoEncabezado))
        sheet1.addCell(new Label(23,6, "VALOR OF. BRUTO",formatoEncabezado))
        sheet1.addCell(new Label(24,6, "ALICUOTA Zn %",formatoEncabezado))
        sheet1.addCell(new Label(25,6, "ALICUOTA Pb %",formatoEncabezado))
        sheet1.addCell(new Label(26,6, "ALICUOTA Ag %",formatoEncabezado))
        sheet1.addCell(new Label(27,6, "VALOR NETO \$us",formatoEncabezado))
        sheet1.addCell(new Label(28,6, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(29,6, "BONO CALIDAD",formatoEncabezado))
        sheet1.addCell(new Label(30,6, "BONO INCENTIVO",formatoEncabezado))
        sheet1.addCell(new Label(31,6, "VALOR DE COMPRA",formatoEncabezado))
        sheet1.addCell(new Label(32,6, "RM",formatoEncabezado))

        if (!liquidacionesComplejo) {
            flash.error = "NO SE PUDO OBTENER RESULTADOS!"
            System.out.println("*** SE ESTA PRODUCIENDO RESULTADOS NULL!!!")
            redirect(action: "create")
            return
        }

        if (liquidacionesComplejo.size()==0 || nombreDelConjunto.equals("")){
            if (liquidacionesComplejo.size()==0)
                sheet1.addCell(new Label(0,7, "SIN RESULTADOS",formatoInfoReporte))
            if (nombreDelConjunto.equals(""))
                sheet1.addCell(new Label(0,7, "ESPECIFIQUE NOMBRE DE CONJUNTO",formatoInfoReporte))
        }else{
            //DESPLIEGUE DE CABECERAS DE COLUMNA PARA RETENCIONES DE LEY
            def listaRetencionesDeLey = retencionesComplejoJSON liquidacionesComplejo,"DE LEY"
            def columna = 33
            listaRetencionesDeLey.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL RET. DE LEY",formatoEncabezado))
            columna++

            //DESPLIEGUE DE CABECERAS DE COLUMNA PARA OTRAS RETENCIONES
            def listaRetencionesOtras = retencionesComplejoJSON liquidacionesComplejo,"OTRA"
            listaRetencionesOtras.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL OTRAS RET.",formatoEncabezado))
            columna++

            System.out.println("NUMERO LIQUIDACIONES: ${liquidacionesComplejo.size()}")
            System.out.println("NUMERO RETENCIONES LEY: ${listaRetencionesDeLey.size()}")
            System.out.println("NUMERO OTRAS RETENCIONES: ${listaRetencionesOtras.size()}")

            sheet1.addCell(new Label(columna,6, "TOTAL RET.",formatoEncabezado))
            sheet1.addCell(new Label(columna+1,6, "TOTAL PAGADO",formatoEncabezado))
            sheet1.addCell(new Label(columna+2,6, "ANTICIPO/ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+3,6, "ANTICIPO/F. ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+4,6, "LIQUIDO PAGABLE",formatoEncabezado))
            sheet1.addCell(new Label(columna+5,6, "CANC. TRANSPORTE",formatoEncabezado))
            sheet1.addCell(new Label(columna+6,6, "CANC. LABORAT.",formatoEncabezado))

            //DESPLIEGUE DE DATOS DE LIQUIDACIONES
            //formatoEncabezado.setAlignment(Alignment.RIGHT)
            def fila = 7
            //variables acumuladoras
            def numeroRegistros=0
            def totalKilosNetosSecosCotizacionDiariaZinc=0
            def totalKilosNetosSecosCotizacionDiariaPlomo=0
            def totalKilosNetosSecosCotizacionDiariaPlata=0
            def totalCantidadSacos=0
            def totalMerma=0
            def totalPesoBruto=0
            def totalKilosNetosHumedos=0
            def totalHumedad=0
            def totalKilosNetosSecos=0
            def totalPorcentajeZincFinal=0
            def totalPorcentajePlomoFinal=0
            def totalPorcentajePlataFinal=0
            def totalKilosFinosZinc=0
            def totalKilosFinosPlomo=0
            def totalKilosFinosPlata=0
            def totalCotizacionQuincenalZinc=0
            def totalCotizacionQuincenalPlomo=0
            def totalCotizacionQuincenalPlata=0
            def totalValorOficialBruto=0
            def totalAlicuotaZinc=0
            def totalAlicuotaPlomo=0
            def totalAlicuotaPlata=0
            def totalValorNeto=0
            def totalValorNetoBolivianos=0
            def totalBonoCalidad=0
            def totalBonoIncentivo=0
            def totalValorDeCompra=0
            def totalRegaliaMinera=0
            def totalTotalRetenciones=0
            def totalTotalPagado=0
            def totalTotalAnticiposContraEntrega=0
            def totalTotalAnticiposContraFuturaEntrega=0
            def totalTotalLiquidoPagable=0
            def totalCostoDeTransporte=0
            def totalTotalCostoLaboratorio=0

            def cuotaParticipacionEmpresa = new ArrayList<String>()
            def cuotaParticipacionCuota = new ArrayList<Integer>()

            liquidacionesComplejo.each {
                numeroRegistros++
                totalKilosNetosSecosCotizacionDiariaZinc+=(it.kilosNetosSecos*it.recepcionDeComplejo.cotizacionDiariaDeMinerales.zinc)
                totalKilosNetosSecosCotizacionDiariaPlomo+=(it.kilosNetosSecos*it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plomo)
                totalKilosNetosSecosCotizacionDiariaPlata+=(it.kilosNetosSecos*it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plata)
                totalCantidadSacos+=Float.parseFloat(it.cantidadDeSacos.toString())
                totalPesoBruto+=it.pesoBruto
                totalMerma+=it.porcentajeMermaFinal
                //totalKilosNetosHumedos+=it.kilosNetosHumedos
                totalKilosNetosHumedos+=(it.pesoBruto-it.pesoBruto*it.porcentajeMermaFinal/100)
                totalHumedad+=it.porcentajeHumedadFinal
                totalKilosNetosSecos+=it.kilosNetosSecos
                totalPorcentajeZincFinal+=it.porcentajeZincFinal
                totalPorcentajePlomoFinal+=it.porcentajePlomoFinal
                totalPorcentajePlataFinal+=it.porcentajePlataFinal
                totalKilosFinosZinc+=it.kilosFinosZinc
                totalKilosFinosPlomo+=it.kilosFinosPlomo
                totalKilosFinosPlata+=it.kilosFinosPlata
                totalCotizacionQuincenalZinc+=it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.zinc
                totalCotizacionQuincenalPlomo+=it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plomo
                totalCotizacionQuincenalPlata+=it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plata
                totalValorOficialBruto+=it.valorOficialBruto
                totalAlicuotaZinc+=it.recepcionDeComplejo.alicuota.zinc
                totalAlicuotaPlomo+=it.recepcionDeComplejo.alicuota.plomo
                totalAlicuotaPlata+=it.recepcionDeComplejo.alicuota.plata
                totalValorNeto+=it.valorNetoMineral
                totalValorNetoBolivianos+=it.valorNetoMineralEnBolivianos
                totalBonoCalidad+=it.bonoCalidad
                totalBonoIncentivo+=it.bonoIncentivo
                totalValorDeCompra+=it.valorDeCompra
                totalRegaliaMinera+=it.regaliaMinera
                totalTotalRetenciones+=it.totalRetenciones
                totalTotalPagado+=it.totalPagado
                totalTotalAnticiposContraEntrega+=it.totalAnticiposContraEntrega
                totalTotalAnticiposContraFuturaEntrega+=it.totalAnticiposContraFuturaEntrega
                totalTotalLiquidoPagable=totalTotalLiquidoPagable+((it.totalLiquidoPagable.doubleValue()<0)?0:it.totalLiquidoPagable.doubleValue())
                totalCostoDeTransporte+=it.recepcionDeComplejo.costoDeTransporte
                totalTotalCostoLaboratorio+=it.totalCostoLaboratorio

                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new Number(1,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.zinc,formatoDatos))
                sheet1.addCell(new Number(2,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plomo,formatoDatos))
                sheet1.addCell(new Number(3,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plata,formatoDatos))
                sheet1.addCell(new DateTime(4,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(5,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(6,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(7,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(8,fila, Float.parseFloat(it.cantidadDeSacos),formatoDatos))
                sheet1.addCell(new Number(9,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(10,fila, it.porcentajeMermaFinal,formatoDatos))
                //sheet1.addCell(new Number(11,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(11,fila, it.pesoBruto-it.pesoBruto*it.porcentajeMermaFinal/100,formatoDatos))
                sheet1.addCell(new Number(12,fila, it.porcentajeHumedadFinal,formatoDatos))
                sheet1.addCell(new Number(13,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(14,fila, it.porcentajeZincFinal,formatoDatos))
                sheet1.addCell(new Number(15,fila, it.porcentajePlomoFinal,formatoDatos))
                sheet1.addCell(new Number(16,fila, it.porcentajePlataFinal,formatoDatos))
                sheet1.addCell(new Number(17,fila, it.kilosFinosZinc,formatoDatos))
                sheet1.addCell(new Number(18,fila, it.kilosFinosPlomo,formatoDatos))
                sheet1.addCell(new Number(19,fila, it.kilosFinosPlata,formatoDatos))
                sheet1.addCell(new Number(20,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.zinc,formatoDatos))
                sheet1.addCell(new Number(21,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plomo,formatoDatos))
                sheet1.addCell(new Number(22,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plata,formatoDatos))
                sheet1.addCell(new Number(23,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(24,fila, it.recepcionDeComplejo.alicuota.zinc,formatoDatos))
                sheet1.addCell(new Number(25,fila, it.recepcionDeComplejo.alicuota.plomo,formatoDatos))
                sheet1.addCell(new Number(26,fila, it.recepcionDeComplejo.alicuota.plata,formatoDatos))
                sheet1.addCell(new Number(27,fila, it.valorNetoMineral,formatoDatos))
                sheet1.addCell(new Number(28,fila, it.valorNetoMineralEnBolivianos,formatoDatos))
                sheet1.addCell(new Number(29,fila, it.bonoCalidad,formatoDatos))
                sheet1.addCell(new Number(30,fila, it.bonoIncentivo,formatoDatos))
                sheet1.addCell(new Number(31,fila, it.valorDeCompra,formatoDatos))
                sheet1.addCell(new Number(32,fila, it.regaliaMinera,formatoDatos))

                columna = 33

                //DESPLIEGUE DE RETENCIONES DE LEY
                def retencionesDeLeyLiquidacion = LiquidacionDeComplejoRetenciones.findAllByLiquidacionDeComplejoAndTipoDeRetencion(it,"DE LEY")
                def numretDeLey = retencionesDeLeyLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretDeLey} RETENCIONES DE LEY!")
                def subtotalRetencionesDeLey=it.regaliaMinera.doubleValue()
                for(int i=0;i<listaRetencionesDeLey.size();i++){
                    def vr = valorRetencion(listaRetencionesDeLey.get(i), retencionesDeLeyLiquidacion,numretDeLey)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesDeLey+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesDeLey,formatoDatos))
                columna++

                //DESPLIEGUE DE RETENCIONES DE LEY
                def retencionesOtrasLiquidacion = LiquidacionDeComplejoRetenciones.findAllByLiquidacionDeComplejoAndTipoDeRetencion(it,"OTRA")
                def numretOtras = retencionesOtrasLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretOtras} RETENCIONES DE LEY!")
                def subtotalRetencionesOtras=0
                for(int i=0;i<listaRetencionesOtras.size();i++){
                    def vr = valorRetencion(listaRetencionesOtras.get(i), retencionesOtrasLiquidacion,numretOtras)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesOtras+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesOtras,formatoDatos))
                columna++

                sheet1.addCell(new Number(columna,fila, it.totalRetenciones,formatoDatos))
                sheet1.addCell(new Number(columna+1,fila, it.totalPagado,formatoDatos))
                sheet1.addCell(new Number(columna+2,fila, it.totalAnticiposContraEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+3,fila, it.totalAnticiposContraFuturaEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+4,fila, it.totalLiquidoPagable,formatoDatos))
                sheet1.addCell(new Number(columna+5,fila, it.recepcionDeComplejo.costoDeTransporte,formatoDatos))
                sheet1.addCell(new Number(columna+6,fila, it.totalCostoLaboratorio,formatoDatos))

                fila++

                if (cuotaParticipacionEmpresa.contains(it.nombreEmpresa)){
                    def obj=cuotaParticipacionCuota.get(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa))
                    obj++
                    cuotaParticipacionCuota.set(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa),obj)
                }else{
                    cuotaParticipacionEmpresa.add(it.nombreEmpresa)
                    cuotaParticipacionCuota.add(1)
                }

            }

            //IMPRESION DE TOTALES
            sheet1.addCell(new Number(1,fila, totalKilosNetosSecosCotizacionDiariaZinc/totalKilosNetosSecos,formatoDatos))
            sheet1.addCell(new Number(2,fila, totalKilosNetosSecosCotizacionDiariaPlomo/totalKilosNetosSecos,formatoDatos))
            sheet1.addCell(new Number(3,fila, totalKilosNetosSecosCotizacionDiariaPlata/totalKilosNetosSecos,formatoDatos))
            sheet1.addCell(new Number(8,fila, totalCantidadSacos,formatoDatos))
            sheet1.addCell(new Number(9,fila, totalPesoBruto,formatoDatos))
            sheet1.addCell(new Number(10,fila, totalMerma/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(11,fila, totalKilosNetosHumedos,formatoDatos))
            //sheet1.addCell(new Number(12,fila, totalHumedad/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(12,fila, (totalKilosNetosSecos/totalKilosNetosHumedos*100-100)*-1,formatoDatos))
            sheet1.addCell(new Number(13,fila, totalKilosNetosSecos,formatoDatos))
            //sheet1.addCell(new Number(14,fila, totalPorcentajeZincFinal/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(14,fila, totalKilosFinosZinc/totalKilosNetosSecos*100,formatoDatos))
            //sheet1.addCell(new Number(15,fila, totalPorcentajePlomoFinal/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(15,fila, totalKilosFinosPlomo/totalKilosNetosSecos*100,formatoDatos))
            //sheet1.addCell(new Number(16,fila, totalPorcentajePlataFinal/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(16,fila, totalKilosFinosPlata/totalKilosNetosSecos*10000,formatoDatos))
            sheet1.addCell(new Number(17,fila, totalKilosFinosZinc,formatoDatos))
            sheet1.addCell(new Number(18,fila, totalKilosFinosPlomo,formatoDatos))
            sheet1.addCell(new Number(19,fila, totalKilosFinosPlata,formatoDatos))
            sheet1.addCell(new Number(20,fila, totalCotizacionQuincenalZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(21,fila, totalCotizacionQuincenalZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(22,fila, totalCotizacionQuincenalZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(23,fila, totalValorOficialBruto,formatoDatos))
            sheet1.addCell(new Number(24,fila, totalAlicuotaZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(25,fila, totalAlicuotaPlomo/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(26,fila, totalAlicuotaPlata/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(27,fila, totalValorNeto,formatoDatos))
            sheet1.addCell(new Number(28,fila, totalValorNetoBolivianos,formatoDatos))
            sheet1.addCell(new Number(29,fila, totalBonoCalidad,formatoDatos))
            sheet1.addCell(new Number(30,fila, totalBonoIncentivo,formatoDatos))
            sheet1.addCell(new Number(31,fila, totalValorDeCompra,formatoDatos))
            sheet1.addCell(new Number(32,fila, totalRegaliaMinera,formatoDatos))

            def columnaFinalRetenciones = 35+listaRetencionesDeLey.size()+listaRetencionesOtras.size()
            def totalLiquidaciones = liquidacionesComplejo.size()
            for (int col=33;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =7;fil<totalLiquidaciones+7;fil++){
                    def valor = Double.parseDouble(sheet1.getWritableCell(col,fil).contents)
                    tret+=valor
                }
                sheet1.addCell(new Number(col,totalLiquidaciones+7, tret,formatoDatos))
            }

            sheet1.addCell(new Number(columnaFinalRetenciones,fila, totalTotalRetenciones,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+1,fila, totalTotalPagado,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+2,fila, totalTotalAnticiposContraEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+3,fila, totalTotalAnticiposContraFuturaEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+4,fila, totalTotalLiquidoPagable,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+5,fila, totalCostoDeTransporte,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+6,fila, totalTotalCostoLaboratorio,formatoDatos))

            sheet1.mergeCells(33, 5, 33+listaRetencionesDeLey.size(), 5);
            sheet1.mergeCells(34+listaRetencionesDeLey.size(), 5, 34+listaRetencionesDeLey.size()+listaRetencionesOtras.size(), 5);
            sheet1.addCell(new Label(33,5, "RETENCIONES DE LEY",formatoEncabezado))
            sheet1.addCell(new Label(34+listaRetencionesDeLey.size(),5, "OTRAS RETENCIONES",formatoEncabezado))

            //IMPRESION DE DISTRIBUCION PORCENTUAL
            sheet1.mergeCells(6, fila+3, 7, fila+3);
            sheet1.addCell(new Label(6,fila+3,"VOLUMEN DE PARTICIPACION POR EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(6,fila+4, "EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(7,fila+4, "PORCENTAJE",formatoEncabezado))
            for (int i=0;i<cuotaParticipacionEmpresa.size();i++){
                sheet1.addCell(new Label(6,fila+5+i, cuotaParticipacionEmpresa.get(i),formatoDatos))
                sheet1.addCell(new Number(7,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))
            }
            sheet1.addCell(new Label(6,fila+5+cuotaParticipacionEmpresa.size(),"TOTAL",formatoDatos))
            sheet1.addCell(new Number(7,fila+5+cuotaParticipacionEmpresa.size(),100.0,formatoDatos))
            //sheet1.addCell(new Number(5,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))

            //ACTUALIZACION DEL CAMPO "conjuntoComplejo" ASIGNANDO EL NOMBRE DE CONJUNTO ESPECIFICADO
            if(asignarConjuntoALotes.equals("SI")){
                def reporteHojaDeCosto = new ReporteHojaDeCosto(
                        elemento: "Complejo",
                        nombreDelConjunto: nombreDelConjunto,
                        destinoDelConjunto: destinoDelConjunto,
                        asignarConjuntoALotes: "SI",
                        ignorarLotes: ignorarLotes,
                        empresa: empresa,
                        fechaInicial: fechaInicial,
                        fechaFinal: fechaFinal,
                        loteInicial: loteInicial,
                        loteFinal: loteFinal,
                        leyMinimaEstano: null,
                        leyMaximaEstano: null,
                        leyMinimaPlata: null,
                        leyMaximaPlata: null,
                        leyMinimaWolfran: null,
                        leyMaximaWolfran: null,
                        leyMinimaAntimonio: null,
                        leyMaximaAntimonio: null,
                        leyMinimaZincComplejo: leyMinimaZincComplejo,
                        leyMaximaZincComplejo: leyMaximaZincComplejo,
                        leyMinimaPlomoComplejo: leyMinimaPlomoComplejo,
                        leyMaximaPlomoComplejo: leyMaximaPlomoComplejo,
                        leyMinimaPlataComplejo: leyMinimaZincComplejo,
                        leyMaximaPlataComplejo: leyMaximaZincComplejo,
                        leyMinimaPlomoPlomoPlata: null,
                        leyMaximaPlomoPlomoPlata: null,
                        leyMinimaPlataPlomoPlata: null,
                        leyMaximaPlataPlomoPlata: null,
                        leyMinimaZincZincPlata: null,
                        leyMaximaZincZincPlata: null,
                        leyMinimaPlataZincPlata: null,
                        leyMaximaPlataZincPlata: null
                )
                reporteHojaDeCosto.save(failOnError: true)

                liquidacionesComplejo.each {
                    it.conjuntoComplejo=nombreDelConjunto
                    it.save(failOnError: true)
                }
            }

            workbook.write();
            workbook.close();
        }
    }

    def crearReportePlomoPlata = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Hoja de Costo de PlomoPlata", 0)
        sheet1.setColumnView(0,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(1,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(2,11)
        sheet1.setColumnView(3,11)
        sheet1.setColumnView(4,11)
        sheet1.setColumnView(5,40)
        sheet1.setColumnView(6,40)
        sheet1.setRowView(6,500)
        for(i in 7..100)
            sheet1.setColumnView(i,11)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD);
        WritableCellFormat formatoEncabezado = new WritableCellFormat (arial10BoldFont);
        formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)
        formatoEncabezado.setWrap(true)
        formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
        //formatoEncabezado.setAlignment(Alignment.CENTRE)
        WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoDatos.setFont(courier8PlainFont)
        formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)
        WritableCellFormat formatoInfoReporte = new WritableCellFormat (arial14BoldFont);
        WritableCellFormat formatoTitulo = new WritableCellFormat (arial16BoldFont);
        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="hoja_costo_plomo_plata.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(5,0, "HOJA DE COSTO DE PLOMO PLATA",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def tipoReporte = ""+params.tipoReporte
        def asignarConjuntoALotes = ""+params.asignarConjuntoALotes
        def nombreDelConjunto = ""+params.nombreDelConjunto
        def destinoDelConjunto = ""+params.destinoDelConjunto

        def ignorarLotes = ""+params.ignorarLotes
        def lotesIgnorados = ignorarLotes.tokenize(',')

        def empresa=null
        def fechaInicial=null
        def fechaFinal=null
        def leyMinimaZincPlomoPlata=null
        def leyMaximaZincPlomoPlata=null
        def leyMinimaPlomoPlomoPlata=null
        def leyMaximaPlomoPlomoPlata=null
        def leyMinimaPlataPlomoPlata=null
        def leyMaximaPlataPlomoPlata=null
        def loteInicial=""
        def loteFinal=""

        sheet1.addCell(new Label(5,1, "NOMBRE DEL CONJUNTO:",formatoInfoReporte))
        sheet1.addCell(new Label(6,1, "${nombreDelConjunto}",formatoInfoReporte))
        sheet1.addCell(new Label(5,2, "DESTINO:",formatoInfoReporte))
        sheet1.addCell(new Label(6,2, "${destinoDelConjunto}",formatoInfoReporte))

        def liquidacionesPlomoPlata = null

        if (tipoReporte.equals("fechas")){
            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            leyMinimaPlomoPlomoPlata = ""+params.leyMinimaPlomoPlomoPlata
            leyMinimaPlomoPlomoPlata = (leyMinimaPlomoPlomoPlata.equals(""))?0:Float.parseFloat(params.leyMinimaPlomoPlomoPlata)
            leyMaximaPlomoPlomoPlata = ""+params.leyMaximaPlomoPlomoPlata
            leyMaximaPlomoPlomoPlata = (leyMaximaPlomoPlomoPlata.equals(""))?0:Float.parseFloat(params.leyMaximaPlomoPlomoPlata)

            leyMinimaPlataPlomoPlata = ""+params.leyMinimaPlataPlomoPlata
            leyMinimaPlataPlomoPlata = (leyMinimaPlataPlomoPlata.equals(""))?0:Float.parseFloat(params.leyMinimaPlataPlomoPlata)
            leyMaximaPlataPlomoPlata = ""+params.leyMaximaPlataPlomoPlata
            leyMaximaPlataPlomoPlata = (leyMaximaPlataPlomoPlata.equals(""))?0:Float.parseFloat(params.leyMaximaPlataPlomoPlata)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(7,1, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))
            sheet1.addCell(new Label(7,2, "ENTRE LEYES:",formatoInfoReporte))
            sheet1.addCell(new Label(9,2, "Pb: ${leyMinimaPlomoPlomoPlata}% AL ${leyMaximaPlomoPlomoPlata}%",formatoInfoReporte))
            sheet1.addCell(new Label(9,3, "Ag: ${leyMinimaPlataPlomoPlata}% AL ${leyMaximaPlataPlomoPlata}%",formatoInfoReporte))

            def liquidaciones = LiquidacionDePlomoPlata.findAllByFechaDeLiquidacionBetweenAndPorcentajePlomoFinalBetweenAndPorcentajePlataFinalBetweenAndConjuntoPlomoPlata(fechaInicial,fechaFinal,leyMinimaPlomoPlomoPlata,leyMaximaPlomoPlomoPlata,leyMinimaPlataPlomoPlata,leyMaximaPlataPlomoPlata,"-")
            //def liquidaciones = LiquidacionDePlomoPlata.findAllByFechaDeLiquidacionBetweenAndPorcentajeZincFinalBetweenAndPorcentajePlomoFinalBetweenAndPorcentajePlataFinalBetweenAndConjuntoPlomoPlata(fechaInicial,fechaFinal,0,100,0,100,0,100,"-")

            liquidacionesPlomoPlata=new ArrayList<LiquidacionDePlomoPlata>()
            liquidaciones.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesPlomoPlata.add(it)
            }
        }

        if (tipoReporte.equals("fechasEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            leyMinimaPlomoPlomoPlata = ""+params.leyMinimaPlomoPlomoPlata
            leyMinimaPlomoPlomoPlata = (leyMinimaPlomoPlomoPlata.equals(""))?0:Float.parseFloat(params.leyMinimaPlomoPlomoPlata)
            leyMaximaPlomoPlomoPlata = ""+params.leyMaximaPlomoPlomoPlata
            leyMaximaPlomoPlomoPlata = (leyMaximaPlomoPlomoPlata.equals(""))?0:Float.parseFloat(params.leyMaximaPlomoPlomoPlata)

            leyMinimaPlataPlomoPlata = ""+params.leyMinimaPlataPlomoPlata
            leyMinimaPlataPlomoPlata = (leyMinimaPlataPlomoPlata.equals(""))?0:Float.parseFloat(params.leyMinimaPlataPlomoPlata)
            leyMaximaPlataPlomoPlata = ""+params.leyMaximaPlataPlomoPlata
            leyMaximaPlataPlomoPlata = (leyMaximaPlataPlomoPlata.equals(""))?0:Float.parseFloat(params.leyMaximaPlataPlomoPlata)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(5,3, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(6,3, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(7,1, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))
            sheet1.addCell(new Label(7,2, "ENTRE LEYES:",formatoInfoReporte))
            sheet1.addCell(new Label(9,2, "Pb: ${leyMinimaPlomoPlomoPlata}% AL ${leyMaximaPlomoPlomoPlata}%",formatoInfoReporte))
            sheet1.addCell(new Label(9,3, "Ag: ${leyMinimaPlataPlomoPlata}% AL ${leyMaximaPlataPlomoPlata}%",formatoInfoReporte))

            def liquidaciones = LiquidacionDePlomoPlata.findAllByFechaDeLiquidacionBetweenAndPorcentajePlomoFinalBetweenAndPorcentajePlataFinalBetweenAndConjuntoPlomoPlata(fechaInicial,fechaFinal,leyMinimaPlomoPlomoPlata,leyMaximaPlomoPlomoPlata,leyMinimaPlataPlomoPlata,leyMaximaPlataPlomoPlata,"-")
            // && it.recepcionDeEstano.empresa.id==empresa.id

            def liquidaciones1 = new ArrayList<LiquidacionDePlomoPlata>()
            liquidaciones.each {
                if(it.recepcionDeComplejo.empresa.id==empresa.id){
                    liquidaciones1.add(it)
                }
            }

            liquidacionesPlomoPlata=new ArrayList<LiquidacionDePlomoPlata>()
            liquidaciones1.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesPlomoPlata.add(it)
            }
        }
        if (tipoReporte.equals("lotes")){
            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            leyMinimaPlomoPlomoPlata = ""+params.leyMinimaPlomoPlomoPlata
            leyMinimaPlomoPlomoPlata = (leyMinimaPlomoPlomoPlata.equals(""))?0:Float.parseFloat(params.leyMinimaPlomoPlomoPlata)
            leyMaximaPlomoPlomoPlata = ""+params.leyMaximaPlomoPlomoPlata
            leyMaximaPlomoPlomoPlata = (leyMaximaPlomoPlomoPlata.equals(""))?0:Float.parseFloat(params.leyMaximaPlomoPlomoPlata)

            leyMinimaPlataPlomoPlata = ""+params.leyMinimaPlataPlomoPlata
            leyMinimaPlataPlomoPlata = (leyMinimaPlataPlomoPlata.equals(""))?0:Float.parseFloat(params.leyMinimaPlataPlomoPlata)
            leyMaximaPlataPlomoPlata = ""+params.leyMaximaPlataPlomoPlata
            leyMaximaPlataPlomoPlata = (leyMaximaPlataPlomoPlata.equals(""))?0:Float.parseFloat(params.leyMaximaPlataPlomoPlata)

            def liquidaciones1 = LiquidacionDePlomoPlata.findAllByPorcentajePlomoFinalBetweenAndPorcentajePlataFinalBetweenAndConjuntoPlomoPlata(leyMinimaPlomoPlomoPlata,leyMaximaPlomoPlomoPlata,leyMinimaPlataPlomoPlata,leyMaximaPlataPlomoPlata,"-")

            def liquidaciones2=new ArrayList<LiquidacionDePlomoPlata>()
            liquidaciones1.each {
                def lote = Integer.parseInt(it.lote)
                if (lote>=loteInicial&&lote<=loteFinal)
                    liquidaciones2.add(it)
            }

            liquidacionesPlomoPlata=new ArrayList<LiquidacionDePlomoPlata>()
            liquidaciones2.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesPlomoPlata.add(it)
            }

            sheet1.addCell(new Label(7,1, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))
            sheet1.addCell(new Label(7,2, "ENTRE LEYES:",formatoInfoReporte))
            sheet1.addCell(new Label(9,2, "Pb: ${leyMinimaPlomoPlomoPlata}% AL ${leyMaximaPlomoPlomoPlata}%",formatoInfoReporte))
            sheet1.addCell(new Label(9,3, "Ag: ${leyMinimaPlataPlomoPlata}% AL ${leyMaximaPlataPlomoPlata}%",formatoInfoReporte))
        }
        if (tipoReporte.equals("lotesEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            leyMinimaPlomoPlomoPlata = ""+params.leyMinimaPlomoPlomoPlata
            leyMinimaPlomoPlomoPlata = (leyMinimaPlomoPlomoPlata.equals(""))?0:Float.parseFloat(params.leyMinimaPlomoPlomoPlata)
            leyMaximaPlomoPlomoPlata = ""+params.leyMaximaPlomoPlomoPlata
            leyMaximaPlomoPlomoPlata = (leyMaximaPlomoPlomoPlata.equals(""))?0:Float.parseFloat(params.leyMaximaPlomoPlomoPlata)

            leyMinimaPlataPlomoPlata = ""+params.leyMinimaPlataPlomoPlata
            leyMinimaPlataPlomoPlata = (leyMinimaPlataPlomoPlata.equals(""))?0:Float.parseFloat(params.leyMinimaPlataPlomoPlata)
            leyMaximaPlataPlomoPlata = ""+params.leyMaximaPlataPlomoPlata
            leyMaximaPlataPlomoPlata = (leyMaximaPlataPlomoPlata.equals(""))?0:Float.parseFloat(params.leyMaximaPlataPlomoPlata)

            def liquidaciones1 = LiquidacionDePlomoPlata.findAllByPorcentajePlomoFinalBetweenAndPorcentajePlataFinalBetweenAndConjuntoPlomoPlata(leyMinimaPlomoPlomoPlata,leyMaximaPlomoPlomoPlata,leyMinimaPlataPlomoPlata,leyMaximaPlataPlomoPlata,"-")

            def liquidaciones2=new ArrayList<LiquidacionDePlomoPlata>()
            liquidaciones1.each {
                def lote = Integer.parseInt(it.lote)
                if (lote>=loteInicial&&lote<=loteFinal&&it.recepcionDeComplejo.empresa.id==empresa.id)
                    liquidaciones2.add(it)
            }

            liquidacionesPlomoPlata=new ArrayList<LiquidacionDePlomoPlata>()
            liquidaciones2.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesPlomoPlata.add(it)
            }

            sheet1.addCell(new Label(5,3, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(6,3, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(7,1, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))
            sheet1.addCell(new Label(7,2, "ENTRE LEYES:",formatoInfoReporte))
            sheet1.addCell(new Label(9,2, "Pb: ${leyMinimaPlomoPlomoPlata}% AL ${leyMaximaPlomoPlomoPlata}%",formatoInfoReporte))
            sheet1.addCell(new Label(9,3, "Ag: ${leyMinimaPlataPlomoPlata}% AL ${leyMaximaPlataPlomoPlata}%",formatoInfoReporte))
        }

        sheet1.addCell(new Label(0,6, "RECEPCION",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "COT. DIA Zn",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "COT. DIA Pb",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "COT. DIA Ag",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "LIQUIDACION",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "NOMBRE",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "MERMA",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "K. N. H.",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "% H2O",formatoEncabezado))
        sheet1.addCell(new Label(13,6, "K. N. S.",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "LEY %Zn",formatoEncabezado))
        sheet1.addCell(new Label(15,6, "LEY %Pb",formatoEncabezado))
        sheet1.addCell(new Label(16,6, "LEY DM Ag",formatoEncabezado))
        sheet1.addCell(new Label(17,6, "K. F. Zn",formatoEncabezado))
        sheet1.addCell(new Label(18,6, "K. F. Pb",formatoEncabezado))
        sheet1.addCell(new Label(19,6, "K. F. Ag",formatoEncabezado))
        sheet1.addCell(new Label(20,6, "COT. OFICIAL Zn",formatoEncabezado))
        sheet1.addCell(new Label(21,6, "COT. OFICIAL Pb",formatoEncabezado))
        sheet1.addCell(new Label(22,6, "COT. OFICIAL Ag",formatoEncabezado))
        sheet1.addCell(new Label(23,6, "VALOR OF. BRUTO",formatoEncabezado))
        sheet1.addCell(new Label(24,6, "ALICUOTA Zn %",formatoEncabezado))
        sheet1.addCell(new Label(25,6, "ALICUOTA Pb %",formatoEncabezado))
        sheet1.addCell(new Label(26,6, "ALICUOTA Ag %",formatoEncabezado))
        sheet1.addCell(new Label(27,6, "VALOR NETO \$us",formatoEncabezado))
        sheet1.addCell(new Label(28,6, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(29,6, "BONO CALIDAD",formatoEncabezado))
        sheet1.addCell(new Label(30,6, "BONO INCENTIVO",formatoEncabezado))
        sheet1.addCell(new Label(31,6, "VALOR DE COMPRA",formatoEncabezado))
        sheet1.addCell(new Label(32,6, "RM",formatoEncabezado))

        if (!liquidacionesPlomoPlata) {
            flash.error = "NO SE PUDO OBTENER RESULTADOS!"
            System.out.println("*** SE ESTA PRODUCIENDO RESULTADOS NULL!!!")
            redirect(action: "create")
            return
        }

        if (liquidacionesPlomoPlata.size()==0 || nombreDelConjunto.equals("")){
            if (liquidacionesPlomoPlata.size()==0)
                sheet1.addCell(new Label(0,7, "SIN RESULTADOS",formatoInfoReporte))
            if (nombreDelConjunto.equals(""))
                sheet1.addCell(new Label(0,7, "ESPECIFIQUE NOMBRE DE CONJUNTO",formatoInfoReporte))
        }else{
            //DESPLIEGUE DE CABECERAS DE COLUMNA PARA RETENCIONES DE LEY
            def listaRetencionesDeLey = retencionesPlomoPlataJSON liquidacionesPlomoPlata,"DE LEY"
            def columna = 33
            listaRetencionesDeLey.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL RET. DE LEY",formatoEncabezado))
            columna++

            //DESPLIEGUE DE CABECERAS DE COLUMNA PARA OTRAS RETENCIONES
            def listaRetencionesOtras = retencionesPlomoPlataJSON liquidacionesPlomoPlata,"OTRA"
            listaRetencionesOtras.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL OTRAS RET.",formatoEncabezado))
            columna++

            System.out.println("NUMERO LIQUIDACIONES: ${liquidacionesPlomoPlata.size()}")
            System.out.println("NUMERO RETENCIONES LEY: ${listaRetencionesDeLey.size()}")
            System.out.println("NUMERO OTRAS RETENCIONES: ${listaRetencionesOtras.size()}")

            sheet1.addCell(new Label(columna,6, "TOTAL RET.",formatoEncabezado))
            sheet1.addCell(new Label(columna+1,6, "TOTAL PAGADO",formatoEncabezado))
            sheet1.addCell(new Label(columna+2,6, "ANTICIPO/ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+3,6, "ANTICIPO/F. ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+4,6, "LIQUIDO PAGABLE",formatoEncabezado))
            sheet1.addCell(new Label(columna+5,6, "CANC. TRANSPORTE",formatoEncabezado))
            sheet1.addCell(new Label(columna+6,6, "CANC. LABORAT.",formatoEncabezado))

            //DESPLIEGUE DE DATOS DE LIQUIDACIONES
            //formatoEncabezado.setAlignment(Alignment.RIGHT)
            def fila = 7
            //variables acumuladoras
            def numeroRegistros=0
            def totalKilosNetosSecosCotizacionDiariaZinc=0
            def totalKilosNetosSecosCotizacionDiariaPlomo=0
            def totalKilosNetosSecosCotizacionDiariaPlata=0
            def totalCantidadSacos=0
            def totalMerma=0
            def totalPesoBruto=0
            def totalKilosNetosHumedos=0
            def totalHumedad=0
            def totalKilosNetosSecos=0
            def totalPorcentajeZincFinal=0
            def totalPorcentajePlomoFinal=0
            def totalPorcentajePlataFinal=0
            def totalKilosFinosZinc=0
            def totalKilosFinosPlomo=0
            def totalKilosFinosPlata=0
            def totalCotizacionQuincenalZinc=0
            def totalCotizacionQuincenalPlomo=0
            def totalCotizacionQuincenalPlata=0
            def totalValorOficialBruto=0
            def totalAlicuotaZinc=0
            def totalAlicuotaPlomo=0
            def totalAlicuotaPlata=0
            def totalValorNeto=0
            def totalValorNetoBolivianos=0
            def totalBonoCalidad=0
            def totalBonoIncentivo=0
            def totalValorDeCompra=0
            def totalRegaliaMinera=0
            def totalTotalRetenciones=0
            def totalTotalPagado=0
            def totalTotalAnticiposContraEntrega=0
            def totalTotalAnticiposContraFuturaEntrega=0
            def totalTotalLiquidoPagable=0
            def totalCostoDeTransporte=0
            def totalTotalCostoLaboratorio=0

            def cuotaParticipacionEmpresa = new ArrayList<String>()
            def cuotaParticipacionCuota = new ArrayList<Integer>()

            liquidacionesPlomoPlata.each {
                numeroRegistros++
                totalKilosNetosSecosCotizacionDiariaZinc+=(it.kilosNetosSecos*it.recepcionDeComplejo.cotizacionDiariaDeMinerales.zinc)
                totalKilosNetosSecosCotizacionDiariaPlomo+=(it.kilosNetosSecos*it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plomo)
                totalKilosNetosSecosCotizacionDiariaPlata+=(it.kilosNetosSecos*it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plata)
                totalCantidadSacos+=Float.parseFloat(it.cantidadDeSacos.toString())
                totalPesoBruto+=it.pesoBruto
                totalMerma+=it.porcentajeMermaFinal
                totalKilosNetosHumedos+=it.kilosNetosHumedos
                totalHumedad+=it.porcentajeHumedadFinal
                totalKilosNetosSecos+=it.kilosNetosSecos
                //totalPorcentajeZincFinal+=it.porcentajeZincFinal
                totalPorcentajePlomoFinal+=it.porcentajePlomoFinal
                totalPorcentajePlataFinal+=it.porcentajePlataFinal
                //totalKilosFinosZinc+=it.kilosFinosZinc
                totalKilosFinosPlomo+=it.kilosFinosPlomo
                totalKilosFinosPlata+=it.kilosFinosPlata
                totalCotizacionQuincenalZinc+=it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.zinc
                totalCotizacionQuincenalPlomo+=it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plomo
                totalCotizacionQuincenalPlata+=it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plata
                totalValorOficialBruto+=it.valorOficialBruto
                totalAlicuotaZinc+=it.recepcionDeComplejo.alicuota.zinc
                totalAlicuotaPlomo+=it.recepcionDeComplejo.alicuota.plomo
                totalAlicuotaPlata+=it.recepcionDeComplejo.alicuota.plata
                totalValorNeto+=it.valorNetoMineral
                totalValorNetoBolivianos+=it.valorNetoMineralEnBolivianos
                totalBonoCalidad+=it.bonoCalidad
                totalBonoIncentivo+=it.bonoIncentivo
                totalValorDeCompra+=it.valorDeCompra
                totalRegaliaMinera+=it.regaliaMinera
                totalTotalRetenciones+=it.totalRetenciones
                totalTotalPagado+=it.totalPagado
                totalTotalAnticiposContraEntrega+=it.totalAnticiposContraEntrega
                totalTotalAnticiposContraFuturaEntrega+=it.totalAnticiposContraFuturaEntrega
                totalTotalLiquidoPagable=totalTotalLiquidoPagable+((it.totalLiquidoPagable.doubleValue()<0)?0:it.totalLiquidoPagable.doubleValue())
                totalCostoDeTransporte+=it.recepcionDeComplejo.costoDeTransporte
                totalTotalCostoLaboratorio+=it.totalCostoLaboratorio

                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new Number(1,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.zinc,formatoDatos))
                sheet1.addCell(new Number(2,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plomo,formatoDatos))
                sheet1.addCell(new Number(3,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plata,formatoDatos))
                sheet1.addCell(new DateTime(4,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(5,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(6,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(7,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(8,fila, Float.parseFloat(it.cantidadDeSacos),formatoDatos))
                sheet1.addCell(new Number(9,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(10,fila, it.porcentajeMermaFinal,formatoDatos))
                //sheet1.addCell(new Number(11,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(11,fila, it.pesoBruto-it.pesoBruto*it.porcentajeMermaFinal/100,formatoDatos))
                sheet1.addCell(new Number(12,fila, it.porcentajeHumedadFinal,formatoDatos))
                sheet1.addCell(new Number(13,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(14,fila, 0,formatoDatos))
                sheet1.addCell(new Number(15,fila, it.porcentajePlomoFinal,formatoDatos))
                sheet1.addCell(new Number(16,fila, it.porcentajePlataFinal,formatoDatos))
                sheet1.addCell(new Number(17,fila, 0,formatoDatos))
                sheet1.addCell(new Number(18,fila, it.kilosFinosPlomo,formatoDatos))
                sheet1.addCell(new Number(19,fila, it.kilosFinosPlata,formatoDatos))
                sheet1.addCell(new Number(20,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.zinc,formatoDatos))
                sheet1.addCell(new Number(21,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plomo,formatoDatos))
                sheet1.addCell(new Number(22,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plata,formatoDatos))
                sheet1.addCell(new Number(23,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(24,fila, it.recepcionDeComplejo.alicuota.zinc,formatoDatos))
                sheet1.addCell(new Number(25,fila, it.recepcionDeComplejo.alicuota.plomo,formatoDatos))
                sheet1.addCell(new Number(26,fila, it.recepcionDeComplejo.alicuota.plata,formatoDatos))
                sheet1.addCell(new Number(27,fila, it.valorNetoMineral,formatoDatos))
                sheet1.addCell(new Number(28,fila, it.valorNetoMineralEnBolivianos,formatoDatos))
                sheet1.addCell(new Number(29,fila, it.bonoCalidad,formatoDatos))
                sheet1.addCell(new Number(30,fila, it.bonoIncentivo,formatoDatos))
                sheet1.addCell(new Number(31,fila, it.valorDeCompra,formatoDatos))
                sheet1.addCell(new Number(32,fila, it.regaliaMinera,formatoDatos))

                columna = 33

                //DESPLIEGUE DE RETENCIONES DE LEY
                def retencionesDeLeyLiquidacion = LiquidacionDePlomoPlataRetenciones.findAllByLiquidacionDePlomoPlataAndTipoDeRetencion(it,"DE LEY")
                def numretDeLey = retencionesDeLeyLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretDeLey} RETENCIONES DE LEY!")
                def subtotalRetencionesDeLey=it.regaliaMinera.doubleValue()
                for(int i=0;i<listaRetencionesDeLey.size();i++){
                    def vr = valorRetencion(listaRetencionesDeLey.get(i), retencionesDeLeyLiquidacion,numretDeLey)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesDeLey+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesDeLey,formatoDatos))
                columna++

                //DESPLIEGUE DE RETENCIONES DE LEY
                def retencionesOtrasLiquidacion = LiquidacionDePlomoPlataRetenciones.findAllByLiquidacionDePlomoPlataAndTipoDeRetencion(it,"OTRA")
                def numretOtras = retencionesOtrasLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretOtras} RETENCIONES DE LEY!")
                def subtotalRetencionesOtras=0
                for(int i=0;i<listaRetencionesOtras.size();i++){
                    def vr = valorRetencion(listaRetencionesOtras.get(i), retencionesOtrasLiquidacion,numretOtras)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesOtras+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesOtras,formatoDatos))
                columna++

                sheet1.addCell(new Number(columna,fila, it.totalRetenciones,formatoDatos))
                sheet1.addCell(new Number(columna+1,fila, it.totalPagado,formatoDatos))
                sheet1.addCell(new Number(columna+2,fila, it.totalAnticiposContraEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+3,fila, it.totalAnticiposContraFuturaEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+4,fila, it.totalLiquidoPagable,formatoDatos))
                sheet1.addCell(new Number(columna+5,fila, it.recepcionDeComplejo.costoDeTransporte,formatoDatos))
                sheet1.addCell(new Number(columna+6,fila, it.totalCostoLaboratorio,formatoDatos))

                fila++

                if (cuotaParticipacionEmpresa.contains(it.nombreEmpresa)){
                    def obj=cuotaParticipacionCuota.get(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa))
                    obj++
                    cuotaParticipacionCuota.set(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa),obj)
                }else{
                    cuotaParticipacionEmpresa.add(it.nombreEmpresa)
                    cuotaParticipacionCuota.add(1)
                }

            }

            //IMPRESION DE TOTALES
            sheet1.addCell(new Number(1,fila, totalKilosNetosSecosCotizacionDiariaZinc/totalKilosNetosSecos,formatoDatos))
            sheet1.addCell(new Number(2,fila, totalKilosNetosSecosCotizacionDiariaPlomo/totalKilosNetosSecos,formatoDatos))
            sheet1.addCell(new Number(3,fila, totalKilosNetosSecosCotizacionDiariaPlata/totalKilosNetosSecos,formatoDatos))
            sheet1.addCell(new Number(8,fila, totalCantidadSacos,formatoDatos))
            sheet1.addCell(new Number(9,fila, totalPesoBruto,formatoDatos))
            sheet1.addCell(new Number(10,fila, totalMerma/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(11,fila, totalKilosNetosHumedos,formatoDatos))
            //sheet1.addCell(new Number(12,fila, totalHumedad/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(12,fila, (totalKilosNetosSecos/totalKilosNetosHumedos*100-100)*-1,formatoDatos))
            sheet1.addCell(new Number(13,fila, totalKilosNetosSecos,formatoDatos))
            //sheet1.addCell(new Number(14,fila, totalPorcentajeZincFinal/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(14,fila, totalKilosFinosZinc/totalKilosNetosSecos*100,formatoDatos))
            //sheet1.addCell(new Number(15,fila, totalPorcentajePlomoFinal/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(15,fila, totalKilosFinosPlomo/totalKilosNetosSecos*100,formatoDatos))
            //sheet1.addCell(new Number(16,fila, totalPorcentajePlataFinal/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(16,fila, totalKilosFinosPlata/totalKilosNetosSecos*10000,formatoDatos))
            sheet1.addCell(new Number(17,fila, totalKilosFinosZinc,formatoDatos))
            sheet1.addCell(new Number(18,fila, totalKilosFinosPlomo,formatoDatos))
            sheet1.addCell(new Number(19,fila, totalKilosFinosPlata,formatoDatos))
            sheet1.addCell(new Number(20,fila, totalCotizacionQuincenalZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(21,fila, totalCotizacionQuincenalZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(22,fila, totalCotizacionQuincenalZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(23,fila, totalValorOficialBruto,formatoDatos))
            sheet1.addCell(new Number(24,fila, totalAlicuotaZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(25,fila, totalAlicuotaPlomo/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(26,fila, totalAlicuotaPlata/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(27,fila, totalValorNeto,formatoDatos))
            sheet1.addCell(new Number(28,fila, totalValorNetoBolivianos,formatoDatos))
            sheet1.addCell(new Number(29,fila, totalBonoCalidad,formatoDatos))
            sheet1.addCell(new Number(30,fila, totalBonoIncentivo,formatoDatos))
            sheet1.addCell(new Number(31,fila, totalValorDeCompra,formatoDatos))
            sheet1.addCell(new Number(32,fila, totalRegaliaMinera,formatoDatos))

            def columnaFinalRetenciones = 35+listaRetencionesDeLey.size()+listaRetencionesOtras.size()
            def totalLiquidaciones = liquidacionesPlomoPlata.size()
            for (int col=33;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =7;fil<totalLiquidaciones+7;fil++){
                    def valor = Double.parseDouble(sheet1.getWritableCell(col,fil).contents)
                    tret+=valor
                }
                sheet1.addCell(new Number(col,totalLiquidaciones+7, tret,formatoDatos))
            }

            sheet1.addCell(new Number(columnaFinalRetenciones,fila, totalTotalRetenciones,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+1,fila, totalTotalPagado,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+2,fila, totalTotalAnticiposContraEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+3,fila, totalTotalAnticiposContraFuturaEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+4,fila, totalTotalLiquidoPagable,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+5,fila, totalCostoDeTransporte,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+6,fila, totalTotalCostoLaboratorio,formatoDatos))

            sheet1.mergeCells(33, 5, 33+listaRetencionesDeLey.size(), 5);
            sheet1.mergeCells(34+listaRetencionesDeLey.size(), 5, 34+listaRetencionesDeLey.size()+listaRetencionesOtras.size(), 5);
            sheet1.addCell(new Label(33,5, "RETENCIONES DE LEY",formatoEncabezado))
            sheet1.addCell(new Label(34+listaRetencionesDeLey.size(),5, "OTRAS RETENCIONES",formatoEncabezado))

            //IMPRESION DE DISTRIBUCION PORCENTUAL
            sheet1.mergeCells(6, fila+3, 7, fila+3);
            sheet1.addCell(new Label(6,fila+3,"VOLUMEN DE PARTICIPACION POR EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(6,fila+4, "EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(7,fila+4, "PORCENTAJE",formatoEncabezado))
            for (int i=0;i<cuotaParticipacionEmpresa.size();i++){
                sheet1.addCell(new Label(6,fila+5+i, cuotaParticipacionEmpresa.get(i),formatoDatos))
                sheet1.addCell(new Number(7,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))
            }
            sheet1.addCell(new Label(6,fila+5+cuotaParticipacionEmpresa.size(),"TOTAL",formatoDatos))
            sheet1.addCell(new Number(7,fila+5+cuotaParticipacionEmpresa.size(),100.0,formatoDatos))
            //sheet1.addCell(new Number(5,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))

            sheet1.removeColumn(17)
            sheet1.removeColumn(14)

            //ACTUALIZACION DEL CAMPO "conjuntoPlomoPlata" ASIGNANDO EL NOMBRE DE CONJUNTO ESPECIFICADO
            if(asignarConjuntoALotes.equals("SI")){
                def reporteHojaDeCosto = new ReporteHojaDeCosto(
                        elemento: "Plomo Plata",
                        nombreDelConjunto: nombreDelConjunto,
                        destinoDelConjunto: destinoDelConjunto,
                        asignarConjuntoALotes: "SI",
                        ignorarLotes: ignorarLotes,
                        empresa: empresa,
                        fechaInicial: fechaInicial,
                        fechaFinal: fechaFinal,
                        loteInicial: loteInicial,
                        loteFinal: loteFinal,
                        leyMinimaEstano: null,
                        leyMaximaEstano: null,
                        leyMinimaPlata: null,
                        leyMaximaPlata: null,
                        leyMinimaWolfran: null,
                        leyMaximaWolfran: null,
                        leyMinimaAntimonio: null,
                        leyMaximaAntimonio: null,
                        leyMinimaZincComplejo: null,
                        leyMaximaZincComplejo: null,
                        leyMinimaPlomoComplejo: null,
                        leyMaximaPlomoComplejo: null,
                        leyMinimaPlataComplejo: null,
                        leyMaximaPlataComplejo: null,
                        leyMinimaPlomoPlomoPlata: leyMinimaPlomoPlomoPlata,
                        leyMaximaPlomoPlomoPlata: leyMaximaPlomoPlomoPlata,
                        leyMinimaPlataPlomoPlata: leyMinimaPlataPlomoPlata,
                        leyMaximaPlataPlomoPlata: leyMaximaPlataPlomoPlata,
                        leyMinimaZincZincPlata: null,
                        leyMaximaZincZincPlata: null,
                        leyMinimaPlataZincPlata: null,
                        leyMaximaPlataZincPlata: null
                )
                reporteHojaDeCosto.save(failOnError: true)

                liquidacionesPlomoPlata.each {
                    it.conjuntoPlomoPlata=nombreDelConjunto
                    it.save(failOnError: true)
                }
            }

            workbook.write();
            workbook.close();
        }
    }

    def crearReporteZincPlata = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Hoja de Costo de Zinc Plata", 0)
        sheet1.setColumnView(0,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(1,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(2,11)
        sheet1.setColumnView(3,11)
        sheet1.setColumnView(4,11)
        sheet1.setColumnView(5,40)
        sheet1.setColumnView(6,40)
        sheet1.setRowView(6,500)
        for(i in 7..100)
            sheet1.setColumnView(i,11)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD);
        WritableCellFormat formatoEncabezado = new WritableCellFormat (arial10BoldFont);
        formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)
        formatoEncabezado.setWrap(true)
        formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
        //formatoEncabezado.setAlignment(Alignment.CENTRE)
        WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoDatos.setFont(courier8PlainFont)
        formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)
        WritableCellFormat formatoInfoReporte = new WritableCellFormat (arial14BoldFont);
        WritableCellFormat formatoTitulo = new WritableCellFormat (arial16BoldFont);
        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="hoja_costo_zinc_plata.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(5,0, "HOJA DE COSTO DE ZINC PLATA",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE
        def tipoReporte = ""+params.tipoReporte
        def asignarConjuntoALotes = ""+params.asignarConjuntoALotes
        def nombreDelConjunto = ""+params.nombreDelConjunto
        def destinoDelConjunto = ""+params.destinoDelConjunto

        def ignorarLotes = ""+params.ignorarLotes
        def lotesIgnorados = ignorarLotes.tokenize(',')

        def empresa=null
        def fechaInicial=null
        def fechaFinal=null
        def leyMinimaZincZincPlata=null
        def leyMaximaZincZincPlata=null
        def leyMinimaPlataZincPlata=null
        def leyMaximaPlataZincPlata=null
        def loteInicial=""
        def loteFinal=""

        sheet1.addCell(new Label(5,1, "NOMBRE DEL CONJUNTO:",formatoInfoReporte))
        sheet1.addCell(new Label(6,1, "${nombreDelConjunto}",formatoInfoReporte))
        sheet1.addCell(new Label(5,2, "DESTINO:",formatoInfoReporte))
        sheet1.addCell(new Label(6,2, "${destinoDelConjunto}",formatoInfoReporte))

        def liquidacionesZincPlata = null

        if (tipoReporte.equals("fechas")){
            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            leyMinimaZincZincPlata = ""+params.leyMinimaZincZincPlata
            leyMinimaZincZincPlata = (leyMinimaZincZincPlata.equals(""))?0:Float.parseFloat(params.leyMinimaZincZincPlata)
            leyMaximaZincZincPlata = ""+params.leyMaximaZincZincPlata
            leyMaximaZincZincPlata = (leyMaximaZincZincPlata.equals(""))?0:Float.parseFloat(params.leyMaximaZincZincPlata)

            leyMinimaPlataZincPlata = ""+params.leyMinimaPlataZincPlata
            leyMinimaPlataZincPlata = (leyMinimaPlataZincPlata.equals(""))?0:Float.parseFloat(params.leyMinimaPlataZincPlata)
            leyMaximaPlataZincPlata = ""+params.leyMaximaPlataZincPlata
            leyMaximaPlataZincPlata = (leyMaximaPlataZincPlata.equals(""))?0:Float.parseFloat(params.leyMaximaPlataZincPlata)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(7,1, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))
            sheet1.addCell(new Label(7,2, "ENTRE LEYES:",formatoInfoReporte))
            sheet1.addCell(new Label(9,2, "Zn: ${leyMinimaZincZincPlata}% AL ${leyMaximaZincZincPlata}%",formatoInfoReporte))
            sheet1.addCell(new Label(9,3, "Ag: ${leyMinimaPlataZincPlata}% AL ${leyMaximaPlataZincPlata}%",formatoInfoReporte))

            def liquidaciones = LiquidacionDeZincPlata.findAllByFechaDeLiquidacionBetweenAndPorcentajeZincFinalBetweenAndPorcentajePlataFinalBetweenAndConjuntoZincPlata(fechaInicial,fechaFinal,leyMinimaZincZincPlata,leyMaximaZincZincPlata,leyMinimaPlataZincPlata,leyMaximaPlataZincPlata,"-")
            //def liquidaciones = LiquidacionDeZincPlata.findAllByFechaDeLiquidacionBetweenAndPorcentajeZincFinalBetweenAndPorcentajePlomoFinalBetweenAndPorcentajePlataFinalBetweenAndConjuntoZincPlata(fechaInicial,fechaFinal,0,100,0,100,0,100,"-")

            liquidacionesZincPlata=new ArrayList<LiquidacionDeZincPlata>()
            liquidaciones.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesZincPlata.add(it)
            }
        }

        if (tipoReporte.equals("fechasEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-"+params.fechaInicial_day)
            fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-"+params.fechaFinal_day)

            leyMinimaZincZincPlata = ""+params.leyMinimaZincZincPlata
            leyMinimaZincZincPlata = (leyMinimaZincZincPlata.equals(""))?0:Float.parseFloat(params.leyMinimaZincZincPlata)
            leyMaximaZincZincPlata = ""+params.leyMaximaZincZincPlata
            leyMaximaZincZincPlata = (leyMaximaZincZincPlata.equals(""))?0:Float.parseFloat(params.leyMaximaZincZincPlata)

            leyMinimaPlataZincPlata = ""+params.leyMinimaPlataZincPlata
            leyMinimaPlataZincPlata = (leyMinimaPlataZincPlata.equals(""))?0:Float.parseFloat(params.leyMinimaPlataZincPlata)
            leyMaximaPlataZincPlata = ""+params.leyMaximaPlataZincPlata
            leyMaximaPlataZincPlata = (leyMaximaPlataZincPlata.equals(""))?0:Float.parseFloat(params.leyMaximaPlataZincPlata)

            def fechaInicialFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaInicial)
            def fechaFinalFormateada=new java.text.SimpleDateFormat("dd/MM/yyyy").format(fechaFinal)
            sheet1.addCell(new Label(5,3, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(6,3, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(7,1, "ENTRE FECHAS: ${fechaInicialFormateada} AL ${fechaFinalFormateada}",formatoInfoReporte))
            sheet1.addCell(new Label(7,2, "ENTRE LEYES:",formatoInfoReporte))
            sheet1.addCell(new Label(9,2, "Zn: ${leyMinimaZincZincPlata}% AL ${leyMaximaZincZincPlata}%",formatoInfoReporte))
            sheet1.addCell(new Label(9,3, "Ag: ${leyMinimaPlataZincPlata}% AL ${leyMaximaPlataZincPlata}%",formatoInfoReporte))

            def liquidaciones = LiquidacionDeZincPlata.findAllByFechaDeLiquidacionBetweenAndPorcentajeZincFinalBetweenAndPorcentajePlataFinalBetweenAndConjuntoZincPlata(fechaInicial,fechaFinal,leyMinimaZincZincPlata,leyMaximaZincZincPlata,leyMinimaPlataZincPlata,leyMaximaPlataZincPlata,"-")
            // && it.recepcionDeEstano.empresa.id==empresa.id

            def liquidaciones1 = new ArrayList<LiquidacionDeZincPlata>()
            liquidaciones.each {
                if(it.recepcionDeComplejo.empresa.id==empresa.id){
                    liquidaciones1.add(it)
                }
            }

            liquidacionesZincPlata=new ArrayList<LiquidacionDeZincPlata>()
            liquidaciones1.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesZincPlata.add(it)
            }
        }
        if (tipoReporte.equals("lotes")){
            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            leyMinimaZincZincPlata = ""+params.leyMinimaZincZincPlata
            leyMinimaZincZincPlata = (leyMinimaZincZincPlata.equals(""))?0:Float.parseFloat(params.leyMinimaZincZincPlata)
            leyMaximaZincZincPlata = ""+params.leyMaximaZincZincPlata
            leyMaximaZincZincPlata = (leyMaximaZincZincPlata.equals(""))?0:Float.parseFloat(params.leyMaximaZincZincPlata)

            leyMinimaPlataZincPlata = ""+params.leyMinimaPlataZincPlata
            leyMinimaPlataZincPlata = (leyMinimaPlataZincPlata.equals(""))?0:Float.parseFloat(params.leyMinimaPlataZincPlata)
            leyMaximaPlataZincPlata = ""+params.leyMaximaPlataZincPlata
            leyMaximaPlataZincPlata = (leyMaximaPlataZincPlata.equals(""))?0:Float.parseFloat(params.leyMaximaPlataZincPlata)

            def liquidaciones1 = LiquidacionDeZincPlata.findAllByPorcentajeZincFinalBetweenAndPorcentajePlataFinalBetweenAndConjuntoZincPlata(leyMinimaZincZincPlata,leyMaximaZincZincPlata,leyMinimaPlataZincPlata,leyMaximaPlataZincPlata,"-")

            def liquidaciones2=new ArrayList<LiquidacionDeZincPlata>()
            liquidaciones1.each {
                def lote = Integer.parseInt(it.lote)
                if (lote>=loteInicial&&lote<=loteFinal)
                    liquidaciones2.add(it)
            }

            liquidacionesZincPlata=new ArrayList<LiquidacionDeZincPlata>()
            liquidaciones2.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesZincPlata.add(it)
            }

            sheet1.addCell(new Label(7,1, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))
            sheet1.addCell(new Label(7,2, "ENTRE LEYES:",formatoInfoReporte))
            sheet1.addCell(new Label(9,2, "Zn: ${leyMinimaZincZincPlata}% AL ${leyMaximaZincZincPlata}%",formatoInfoReporte))
            sheet1.addCell(new Label(9,3, "Ag: ${leyMinimaPlataZincPlata}% AL ${leyMaximaPlataZincPlata}%",formatoInfoReporte))
        }
        if (tipoReporte.equals("lotesEmpresa")){
            empresa = Empresa.get(params.empresa.id)

            loteInicial = ""+params.loteInicial
            loteInicial = (loteInicial.equals(""))?0:Integer.parseInt(params.loteInicial)
            loteFinal = ""+params.loteFinal
            loteFinal = (loteFinal.equals(""))?0:Integer.parseInt(params.loteFinal)

            leyMinimaZincZincPlata = ""+params.leyMinimaZincZincPlata
            leyMinimaZincZincPlata = (leyMinimaZincZincPlata.equals(""))?0:Float.parseFloat(params.leyMinimaZincZincPlata)
            leyMaximaZincZincPlata = ""+params.leyMaximaZincZincPlata
            leyMaximaZincZincPlata = (leyMaximaZincZincPlata.equals(""))?0:Float.parseFloat(params.leyMaximaZincZincPlata)

            leyMinimaPlataZincPlata = ""+params.leyMinimaPlataZincPlata
            leyMinimaPlataZincPlata = (leyMinimaPlataZincPlata.equals(""))?0:Float.parseFloat(params.leyMinimaPlataZincPlata)
            leyMaximaPlataZincPlata = ""+params.leyMaximaPlataZincPlata
            leyMaximaPlataZincPlata = (leyMaximaPlataZincPlata.equals(""))?0:Float.parseFloat(params.leyMaximaPlataZincPlata)

            def liquidaciones1 = LiquidacionDeZincPlata.findAllByPorcentajeZincFinalBetweenAndPorcentajePlataFinalBetweenAndConjuntoZincPlata(leyMinimaZincZincPlata,leyMaximaZincZincPlata,leyMinimaPlataZincPlata,leyMaximaPlataZincPlata,"-")

            def liquidaciones2=new ArrayList<LiquidacionDeZincPlata>()
            liquidaciones1.each {
                def lote = Integer.parseInt(it.lote)
                if (lote>=loteInicial&&lote<=loteFinal&&it.recepcionDeComplejo.empresa.id==empresa.id)
                    liquidaciones2.add(it)
            }

            liquidacionesZincPlata=new ArrayList<LiquidacionDeZincPlata>()
            liquidaciones2.each {
                if(!existeLote(it,lotesIgnorados))
                    liquidacionesZincPlata.add(it)
            }

            sheet1.addCell(new Label(5,3, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(6,3, "${empresa.toString()}",formatoInfoReporte))
            sheet1.addCell(new Label(7,1, "ENTRE LOTES: ${loteInicial} AL ${loteFinal}",formatoInfoReporte))
            sheet1.addCell(new Label(7,2, "ENTRE LEYES:",formatoInfoReporte))
            sheet1.addCell(new Label(9,2, "Zn: ${leyMinimaZincZincPlata}% AL ${leyMaximaZincZincPlata}%",formatoInfoReporte))
            sheet1.addCell(new Label(9,3, "Ag: ${leyMinimaPlataZincPlata}% AL ${leyMaximaPlataZincPlata}%",formatoInfoReporte))
        }

        sheet1.addCell(new Label(0,6, "RECEPCION",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "COT. DIA Zn",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "COT. DIA Pb",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "COT. DIA Ag",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "LIQUIDACION",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "NOMBRE",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "MERMA",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "K. N. H.",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "% H2O",formatoEncabezado))
        sheet1.addCell(new Label(13,6, "K. N. S.",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "LEY %Zn",formatoEncabezado))
        sheet1.addCell(new Label(15,6, "LEY %Pb",formatoEncabezado))
        sheet1.addCell(new Label(16,6, "LEY DM Ag",formatoEncabezado))
        sheet1.addCell(new Label(17,6, "K. F. Zn",formatoEncabezado))
        sheet1.addCell(new Label(18,6, "K. F. Pb",formatoEncabezado))
        sheet1.addCell(new Label(19,6, "K. F. Ag",formatoEncabezado))
        sheet1.addCell(new Label(20,6, "COT. OFICIAL Zn",formatoEncabezado))
        sheet1.addCell(new Label(21,6, "COT. OFICIAL Pb",formatoEncabezado))
        sheet1.addCell(new Label(22,6, "COT. OFICIAL Ag",formatoEncabezado))
        sheet1.addCell(new Label(23,6, "VALOR OF. BRUTO",formatoEncabezado))
        sheet1.addCell(new Label(24,6, "ALICUOTA Zn %",formatoEncabezado))
        sheet1.addCell(new Label(25,6, "ALICUOTA Pb %",formatoEncabezado))
        sheet1.addCell(new Label(26,6, "ALICUOTA Ag %",formatoEncabezado))
        sheet1.addCell(new Label(27,6, "VALOR NETO \$us",formatoEncabezado))
        sheet1.addCell(new Label(28,6, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(29,6, "BONO CALIDAD",formatoEncabezado))
        sheet1.addCell(new Label(30,6, "BONO INCENTIVO",formatoEncabezado))
        sheet1.addCell(new Label(31,6, "VALOR DE COMPRA",formatoEncabezado))
        sheet1.addCell(new Label(32,6, "RM",formatoEncabezado))

        if (!liquidacionesZincPlata) {
            flash.error = "NO SE PUDO OBTENER RESULTADOS!"
            System.out.println("*** SE ESTA PRODUCIENDO RESULTADOS NULL!!!")
            redirect(action: "create")
            return
        }

        if (liquidacionesZincPlata.size()==0 || nombreDelConjunto.equals("")){
            if (liquidacionesZincPlata.size()==0)
                sheet1.addCell(new Label(0,7, "SIN RESULTADOS",formatoInfoReporte))
            if (nombreDelConjunto.equals(""))
                sheet1.addCell(new Label(0,7, "ESPECIFIQUE NOMBRE DE CONJUNTO",formatoInfoReporte))
        }else{
            //DESPLIEGUE DE CABECERAS DE COLUMNA PARA RETENCIONES DE LEY
            def listaRetencionesDeLey = retencionesZincPlataJSON liquidacionesZincPlata,"DE LEY"
            def columna = 33
            listaRetencionesDeLey.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL RET. DE LEY",formatoEncabezado))
            columna++

            //DESPLIEGUE DE CABECERAS DE COLUMNA PARA OTRAS RETENCIONES
            def listaRetencionesOtras = retencionesZincPlataJSON liquidacionesZincPlata,"OTRA"
            listaRetencionesOtras.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL OTRAS RET.",formatoEncabezado))
            columna++

            System.out.println("NUMERO LIQUIDACIONES: ${liquidacionesZincPlata.size()}")
            System.out.println("NUMERO RETENCIONES LEY: ${listaRetencionesDeLey.size()}")
            System.out.println("NUMERO OTRAS RETENCIONES: ${listaRetencionesOtras.size()}")

            sheet1.addCell(new Label(columna,6, "TOTAL RET.",formatoEncabezado))
            sheet1.addCell(new Label(columna+1,6, "TOTAL PAGADO",formatoEncabezado))
            sheet1.addCell(new Label(columna+2,6, "ANTICIPO/ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+3,6, "ANTICIPO/F. ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+4,6, "LIQUIDO PAGABLE",formatoEncabezado))
            sheet1.addCell(new Label(columna+5,6, "CANC. TRANSPORTE",formatoEncabezado))
            sheet1.addCell(new Label(columna+6,6, "CANC. LABORAT.",formatoEncabezado))

            //DESPLIEGUE DE DATOS DE LIQUIDACIONES
            //formatoEncabezado.setAlignment(Alignment.RIGHT)
            def fila = 7
            //variables acumuladoras
            def numeroRegistros=0
            def totalKilosNetosSecosCotizacionDiariaZinc=0
            def totalKilosNetosSecosCotizacionDiariaPlomo=0
            def totalKilosNetosSecosCotizacionDiariaPlata=0
            def totalCantidadSacos=0
            def totalMerma=0
            def totalPesoBruto=0
            def totalKilosNetosHumedos=0
            def totalHumedad=0
            def totalKilosNetosSecos=0
            def totalPorcentajeZincFinal=0
            def totalPorcentajePlomoFinal=0
            def totalPorcentajePlataFinal=0
            def totalKilosFinosZinc=0
            def totalKilosFinosPlomo=0
            def totalKilosFinosPlata=0
            def totalCotizacionQuincenalZinc=0
            def totalCotizacionQuincenalPlomo=0
            def totalCotizacionQuincenalPlata=0
            def totalValorOficialBruto=0
            def totalAlicuotaZinc=0
            def totalAlicuotaPlomo=0
            def totalAlicuotaPlata=0
            def totalValorNeto=0
            def totalValorNetoBolivianos=0
            def totalBonoCalidad=0
            def totalBonoIncentivo=0
            def totalValorDeCompra=0
            def totalRegaliaMinera=0
            def totalTotalRetenciones=0
            def totalTotalPagado=0
            def totalTotalAnticiposContraEntrega=0
            def totalTotalAnticiposContraFuturaEntrega=0
            def totalTotalLiquidoPagable=0
            def totalCostoDeTransporte=0
            def totalTotalCostoLaboratorio=0

            def cuotaParticipacionEmpresa = new ArrayList<String>()
            def cuotaParticipacionCuota = new ArrayList<Integer>()

            liquidacionesZincPlata.each {
                numeroRegistros++
                totalKilosNetosSecosCotizacionDiariaZinc+=(it.kilosNetosSecos*it.recepcionDeComplejo.cotizacionDiariaDeMinerales.zinc)
                totalKilosNetosSecosCotizacionDiariaPlomo+=(it.kilosNetosSecos*it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plomo)
                totalKilosNetosSecosCotizacionDiariaPlata+=(it.kilosNetosSecos*it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plata)
                totalCantidadSacos+=Float.parseFloat(it.cantidadDeSacos.toString())
                totalPesoBruto+=it.pesoBruto
                totalMerma+=it.porcentajeMermaFinal
                totalKilosNetosHumedos+=it.kilosNetosHumedos
                totalHumedad+=it.porcentajeHumedadFinal
                totalKilosNetosSecos+=it.kilosNetosSecos
                totalPorcentajeZincFinal+=it.porcentajeZincFinal
                //totalPorcentajePlomoFinal+=it.porcentajePlomoFinal
                totalPorcentajePlataFinal+=it.porcentajePlataFinal
                totalKilosFinosZinc+=it.kilosFinosZinc
                //totalKilosFinosPlomo+=it.kilosFinosPlomo
                totalKilosFinosPlata+=it.kilosFinosPlata
                totalCotizacionQuincenalZinc+=it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.zinc
                totalCotizacionQuincenalPlomo+=it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plomo
                totalCotizacionQuincenalPlata+=it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plata
                totalValorOficialBruto+=it.valorOficialBruto
                totalAlicuotaZinc+=it.recepcionDeComplejo.alicuota.zinc
                totalAlicuotaPlomo+=it.recepcionDeComplejo.alicuota.plomo
                totalAlicuotaPlata+=it.recepcionDeComplejo.alicuota.plata
                totalValorNeto+=it.valorNetoMineral
                totalValorNetoBolivianos+=it.valorNetoMineralEnBolivianos
                totalBonoCalidad+=it.bonoCalidad
                totalBonoIncentivo+=it.bonoIncentivo
                totalValorDeCompra+=it.valorDeCompra
                totalRegaliaMinera+=it.regaliaMinera
                totalTotalRetenciones+=it.totalRetenciones
                totalTotalPagado+=it.totalPagado
                totalTotalAnticiposContraEntrega+=it.totalAnticiposContraEntrega
                totalTotalAnticiposContraFuturaEntrega+=it.totalAnticiposContraFuturaEntrega
                totalTotalLiquidoPagable=totalTotalLiquidoPagable+((it.totalLiquidoPagable.doubleValue()<0)?0:it.totalLiquidoPagable.doubleValue())
                totalCostoDeTransporte+=it.recepcionDeComplejo.costoDeTransporte
                totalTotalCostoLaboratorio+=it.totalCostoLaboratorio

                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new Number(1,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.zinc,formatoDatos))
                sheet1.addCell(new Number(2,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plomo,formatoDatos))
                sheet1.addCell(new Number(3,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plata,formatoDatos))
                sheet1.addCell(new DateTime(4,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(5,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(6,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(7,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(8,fila, Float.parseFloat(it.cantidadDeSacos),formatoDatos))
                sheet1.addCell(new Number(9,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(10,fila, it.porcentajeMermaFinal,formatoDatos))
                //sheet1.addCell(new Number(11,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(11,fila, it.pesoBruto-it.pesoBruto*it.porcentajeMermaFinal/100,formatoDatos))
                sheet1.addCell(new Number(12,fila, it.porcentajeHumedadFinal,formatoDatos))
                sheet1.addCell(new Number(13,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(14,fila, it.porcentajeZincFinal,formatoDatos))
                sheet1.addCell(new Number(15,fila, 0,formatoDatos))
                sheet1.addCell(new Number(16,fila, it.porcentajePlataFinal,formatoDatos))
                sheet1.addCell(new Number(17,fila, it.kilosFinosZinc,formatoDatos))
                sheet1.addCell(new Number(18,fila, 0,formatoDatos))
                sheet1.addCell(new Number(19,fila, it.kilosFinosPlata,formatoDatos))
                sheet1.addCell(new Number(20,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.zinc,formatoDatos))
                sheet1.addCell(new Number(21,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plomo,formatoDatos))
                sheet1.addCell(new Number(22,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plata,formatoDatos))
                sheet1.addCell(new Number(23,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(24,fila, it.recepcionDeComplejo.alicuota.zinc,formatoDatos))
                sheet1.addCell(new Number(25,fila, it.recepcionDeComplejo.alicuota.plomo,formatoDatos))
                sheet1.addCell(new Number(26,fila, it.recepcionDeComplejo.alicuota.plata,formatoDatos))
                sheet1.addCell(new Number(27,fila, it.valorNetoMineral,formatoDatos))
                sheet1.addCell(new Number(28,fila, it.valorNetoMineralEnBolivianos,formatoDatos))
                sheet1.addCell(new Number(29,fila, it.bonoCalidad,formatoDatos))
                sheet1.addCell(new Number(30,fila, it.bonoIncentivo,formatoDatos))
                sheet1.addCell(new Number(31,fila, it.valorDeCompra,formatoDatos))
                sheet1.addCell(new Number(32,fila, it.regaliaMinera,formatoDatos))

                columna = 33

                //DESPLIEGUE DE RETENCIONES DE LEY
                def retencionesDeLeyLiquidacion = LiquidacionDeZincPlataRetenciones.findAllByLiquidacionDeZincPlataAndTipoDeRetencion(it,"DE LEY")
                def numretDeLey = retencionesDeLeyLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretDeLey} RETENCIONES DE LEY!")
                def subtotalRetencionesDeLey=it.regaliaMinera.doubleValue()
                for(int i=0;i<listaRetencionesDeLey.size();i++){
                    def vr = valorRetencion(listaRetencionesDeLey.get(i), retencionesDeLeyLiquidacion,numretDeLey)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesDeLey+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesDeLey,formatoDatos))
                columna++

                //DESPLIEGUE DE RETENCIONES DE LEY
                def retencionesOtrasLiquidacion = LiquidacionDeZincPlataRetenciones.findAllByLiquidacionDeZincPlataAndTipoDeRetencion(it,"OTRA")
                def numretOtras = retencionesOtrasLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretOtras} RETENCIONES DE LEY!")
                def subtotalRetencionesOtras=0
                for(int i=0;i<listaRetencionesOtras.size();i++){
                    def vr = valorRetencion(listaRetencionesOtras.get(i), retencionesOtrasLiquidacion,numretOtras)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesOtras+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesOtras,formatoDatos))
                columna++

                sheet1.addCell(new Number(columna,fila, it.totalRetenciones,formatoDatos))
                sheet1.addCell(new Number(columna+1,fila, it.totalPagado,formatoDatos))
                sheet1.addCell(new Number(columna+2,fila, it.totalAnticiposContraEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+3,fila, it.totalAnticiposContraFuturaEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+4,fila, it.totalLiquidoPagable,formatoDatos))
                sheet1.addCell(new Number(columna+5,fila, it.recepcionDeComplejo.costoDeTransporte,formatoDatos))
                sheet1.addCell(new Number(columna+6,fila, it.totalCostoLaboratorio,formatoDatos))

                fila++

                if (cuotaParticipacionEmpresa.contains(it.nombreEmpresa)){
                    def obj=cuotaParticipacionCuota.get(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa))
                    obj++
                    cuotaParticipacionCuota.set(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa),obj)
                }else{
                    cuotaParticipacionEmpresa.add(it.nombreEmpresa)
                    cuotaParticipacionCuota.add(1)
                }

            }

            //IMPRESION DE TOTALES
            sheet1.addCell(new Number(1,fila, totalKilosNetosSecosCotizacionDiariaZinc/totalKilosNetosSecos,formatoDatos))
            sheet1.addCell(new Number(2,fila, totalKilosNetosSecosCotizacionDiariaPlomo/totalKilosNetosSecos,formatoDatos))
            sheet1.addCell(new Number(3,fila, totalKilosNetosSecosCotizacionDiariaPlata/totalKilosNetosSecos,formatoDatos))
            sheet1.addCell(new Number(8,fila, totalCantidadSacos,formatoDatos))
            sheet1.addCell(new Number(9,fila, totalPesoBruto,formatoDatos))
            sheet1.addCell(new Number(10,fila, totalMerma/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(11,fila, totalKilosNetosHumedos,formatoDatos))
            //sheet1.addCell(new Number(12,fila, totalHumedad/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(12,fila, (totalKilosNetosSecos/totalKilosNetosHumedos*100-100)*-1,formatoDatos))
            sheet1.addCell(new Number(13,fila, totalKilosNetosSecos,formatoDatos))
            //sheet1.addCell(new Number(14,fila, totalPorcentajeZincFinal/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(14,fila, totalKilosFinosZinc/totalKilosNetosSecos*100,formatoDatos))
            //sheet1.addCell(new Number(15,fila, totalPorcentajePlomoFinal/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(15,fila, totalKilosFinosPlomo/totalKilosNetosSecos*100,formatoDatos))
            //sheet1.addCell(new Number(16,fila, totalPorcentajePlataFinal/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(16,fila, totalKilosFinosPlata/totalKilosNetosSecos*10000,formatoDatos))
            sheet1.addCell(new Number(17,fila, totalKilosFinosZinc,formatoDatos))
            sheet1.addCell(new Number(18,fila, totalKilosFinosPlomo,formatoDatos))
            sheet1.addCell(new Number(19,fila, totalKilosFinosPlata,formatoDatos))
            sheet1.addCell(new Number(20,fila, totalCotizacionQuincenalZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(21,fila, totalCotizacionQuincenalZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(22,fila, totalCotizacionQuincenalZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(23,fila, totalValorOficialBruto,formatoDatos))
            sheet1.addCell(new Number(24,fila, totalAlicuotaZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(25,fila, totalAlicuotaPlomo/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(26,fila, totalAlicuotaPlata/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(27,fila, totalValorNeto,formatoDatos))
            sheet1.addCell(new Number(28,fila, totalValorNetoBolivianos,formatoDatos))
            sheet1.addCell(new Number(29,fila, totalBonoCalidad,formatoDatos))
            sheet1.addCell(new Number(30,fila, totalBonoIncentivo,formatoDatos))
            sheet1.addCell(new Number(31,fila, totalValorDeCompra,formatoDatos))
            sheet1.addCell(new Number(32,fila, totalRegaliaMinera,formatoDatos))

            def columnaFinalRetenciones = 35+listaRetencionesDeLey.size()+listaRetencionesOtras.size()
            def totalLiquidaciones = liquidacionesZincPlata.size()
            for (int col=33;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =7;fil<totalLiquidaciones+7;fil++){
                    def valor = Double.parseDouble(sheet1.getWritableCell(col,fil).contents)
                    tret+=valor
                }
                sheet1.addCell(new Number(col,totalLiquidaciones+7, tret,formatoDatos))
            }

            sheet1.addCell(new Number(columnaFinalRetenciones,fila, totalTotalRetenciones,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+1,fila, totalTotalPagado,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+2,fila, totalTotalAnticiposContraEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+3,fila, totalTotalAnticiposContraFuturaEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+4,fila, totalTotalLiquidoPagable,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+5,fila, totalCostoDeTransporte,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+6,fila, totalTotalCostoLaboratorio,formatoDatos))

            sheet1.mergeCells(33, 5, 33+listaRetencionesDeLey.size(), 5);
            sheet1.mergeCells(34+listaRetencionesDeLey.size(), 5, 34+listaRetencionesDeLey.size()+listaRetencionesOtras.size(), 5);
            sheet1.addCell(new Label(33,5, "RETENCIONES DE LEY",formatoEncabezado))
            sheet1.addCell(new Label(34+listaRetencionesDeLey.size(),5, "OTRAS RETENCIONES",formatoEncabezado))

            //IMPRESION DE DISTRIBUCION PORCENTUAL
            sheet1.mergeCells(6, fila+3, 7, fila+3);
            sheet1.addCell(new Label(6,fila+3,"VOLUMEN DE PARTICIPACION POR EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(6,fila+4, "EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(7,fila+4, "PORCENTAJE",formatoEncabezado))
            for (int i=0;i<cuotaParticipacionEmpresa.size();i++){
                sheet1.addCell(new Label(6,fila+5+i, cuotaParticipacionEmpresa.get(i),formatoDatos))
                sheet1.addCell(new Number(7,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))
            }
            sheet1.addCell(new Label(6,fila+5+cuotaParticipacionEmpresa.size(),"TOTAL",formatoDatos))
            sheet1.addCell(new Number(7,fila+5+cuotaParticipacionEmpresa.size(),100.0,formatoDatos))

            //eliminar columnas innecesarias
            sheet1.removeColumn(18)
            sheet1.removeColumn(15)
            sheet1.removeColumn(2)
            //sheet1.addCell(new Number(5,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))

            //ACTUALIZACION DEL CAMPO "conjuntoZincPlata" ASIGNANDO EL NOMBRE DE CONJUNTO ESPECIFICADO
            if(asignarConjuntoALotes.equals("SI")){
                def reporteHojaDeCosto = new ReporteHojaDeCosto(
                        elemento: "Zinc Plata",
                        nombreDelConjunto: nombreDelConjunto,
                        destinoDelConjunto: destinoDelConjunto,
                        asignarConjuntoALotes: "SI",
                        ignorarLotes: ignorarLotes,
                        empresa: empresa,
                        fechaInicial: fechaInicial,
                        fechaFinal: fechaFinal,
                        loteInicial: loteInicial,
                        loteFinal: loteFinal,
                        leyMinimaEstano: null,
                        leyMaximaEstano: null,
                        leyMinimaPlata: null,
                        leyMaximaPlata: null,
                        leyMinimaWolfran: null,
                        leyMaximaWolfran: null,
                        leyMinimaAntimonio: null,
                        leyMaximaAntimonio: null,
                        leyMinimaZincComplejo: null,
                        leyMaximaZincComplejo: null,
                        leyMinimaPlomoComplejo: null,
                        leyMaximaPlomoComplejo: null,
                        leyMinimaPlataComplejo: null,
                        leyMaximaPlataComplejo: null,
                        leyMinimaPlomoPlomoPlata: null,
                        leyMaximaPlomoPlomoPlata: null,
                        leyMinimaPlataPlomoPlata: null,
                        leyMaximaPlataPlomoPlata: null,
                        leyMinimaZincZincPlata: leyMinimaZincZincPlata,
                        leyMaximaZincZincPlata: leyMaximaZincZincPlata,
                        leyMinimaPlataZincPlata: leyMinimaPlataZincPlata,
                        leyMaximaPlataZincPlata: leyMaximaPlataZincPlata
                )
                reporteHojaDeCosto.save(failOnError: true)

                liquidacionesZincPlata.each {
                    it.conjuntoZincPlata=nombreDelConjunto
                    it.save(failOnError: true)
                }
            }

            workbook.write();
            workbook.close();
        }
    }

    def valorRetencion(descripcionRetencion, retencionesLiquidacion, numeroRetenciones) {
        for(int i=0;i<numeroRetenciones;i++){
            def retencion = retencionesLiquidacion.get(i)
            if (descripcionRetencion.equals(retencion.descripcion)){
                return retencion.monto
            }
        }
        return 0
    }

    def retencionesEstanoJSON = { liquidacionesEstano,tipo ->
        List retencionesEstano = new ArrayList()
        if (liquidacionesEstano.size()>0){
            liquidacionesEstano.each {
                def liquidacionEstanoRetenciones = LiquidacionDeEstanoRetenciones.findAllByLiquidacionDeEstanoAndTipoDeRetencion(it,tipo)
                liquidacionEstanoRetenciones.each {
                    if (!retencionesEstano.contains(it.descripcion))
                        retencionesEstano.add(it.descripcion)
                }
            }
        }
        return retencionesEstano
    }

    def retencionesPlataJSON = { liquidacionesPlata,tipo ->
        List retencionesPlata = new ArrayList()
        if (liquidacionesPlata.size()>0){
            liquidacionesPlata.each {
                def liquidacionPlataRetenciones = LiquidacionDePlataRetenciones.findAllByLiquidacionDePlataAndTipoDeRetencion(it,tipo)
                liquidacionPlataRetenciones.each {
                    if (!retencionesPlata.contains(it.descripcion))
                        retencionesPlata.add(it.descripcion)
                }
            }
        }
        return retencionesPlata
    }

    def retencionesWolfranJSON = { liquidacionesWolfran,tipo ->
        List retencionesWolfran = new ArrayList()
        if (liquidacionesWolfran.size()>0){
            liquidacionesWolfran.each {
                def liquidacionWolfranRetenciones = LiquidacionDeWolfranRetenciones.findAllByLiquidacionDeWolfranAndTipoDeRetencion(it,tipo)
                liquidacionWolfranRetenciones.each {
                    if (!retencionesWolfran.contains(it.descripcion))
                        retencionesWolfran.add(it.descripcion)
                }
            }
        }
        return retencionesWolfran
    }

    def retencionesAntimonioJSON = { liquidacionesAntimonio,tipo ->
        List retencionesAntimonio = new ArrayList()
        if (liquidacionesAntimonio.size()>0){
            liquidacionesAntimonio.each {
                def liquidacionAntimonioRetenciones = LiquidacionDeAntimonioRetenciones.findAllByLiquidacionDeAntimonioAndTipoDeRetencion(it,tipo)
                liquidacionAntimonioRetenciones.each {
                    if (!retencionesAntimonio.contains(it.descripcion))
                        retencionesAntimonio.add(it.descripcion)
                }
            }
        }
        return retencionesAntimonio
    }

    def retencionesComplejoJSON = { liquidacionesComplejo,tipo ->
        List retencionesComplejo = new ArrayList()
        if (liquidacionesComplejo.size()>0){
            liquidacionesComplejo.each {
                def liquidacionComplejoRetenciones = LiquidacionDeComplejoRetenciones.findAllByLiquidacionDeComplejoAndTipoDeRetencion(it,tipo)
                liquidacionComplejoRetenciones.each {
                    if (!retencionesComplejo.contains(it.descripcion))
                        retencionesComplejo.add(it.descripcion)
                }
            }
        }
        return retencionesComplejo
    }

    def retencionesPlomoPlataJSON = { liquidacionesPlomoPlata,tipo ->
        List retencionesPlomoPlata = new ArrayList()
        if (liquidacionesPlomoPlata.size()>0){
            liquidacionesPlomoPlata.each {
                def liquidacionPlomoPlataRetenciones = LiquidacionDePlomoPlataRetenciones.findAllByLiquidacionDePlomoPlataAndTipoDeRetencion(it,tipo)
                liquidacionPlomoPlataRetenciones.each {
                    if (!retencionesPlomoPlata.contains(it.descripcion))
                        retencionesPlomoPlata.add(it.descripcion)
                }
            }
        }
        return retencionesPlomoPlata
    }

    def retencionesZincPlataJSON = { liquidacionesZincPlata,tipo ->
        List retencionesZincPlata = new ArrayList()
        if (liquidacionesZincPlata.size()>0){
            liquidacionesZincPlata.each {
                def liquidacionZincPlataRetenciones = LiquidacionDeZincPlataRetenciones.findAllByLiquidacionDeZincPlataAndTipoDeRetencion(it,tipo)
                liquidacionZincPlataRetenciones.each {
                    if (!retencionesZincPlata.contains(it.descripcion))
                        retencionesZincPlata.add(it.descripcion)
                }
            }
        }
        return retencionesZincPlata
    }

    def existeLote(liquidacion,lotesIgnorados) {
        for(int i=0;i<lotesIgnorados.size();i++){
            def loteIgnoradoString = lotesIgnorados.get(i)
            def loteIgnorado=(loteIgnoradoString.isNumber())?Integer.parseInt(loteIgnoradoString):-1
            def lote=Integer.parseInt(liquidacion.lote)
            if (loteIgnorado==lote){
                return true
            }
        }
        return false
    }

    def recrearReporteEstano = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Hoja de Costo de Estano", 0)
        sheet1.setColumnView(0,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(1,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(2,11)
        sheet1.setColumnView(3,40)
        sheet1.setColumnView(4,40)
        sheet1.setRowView(6,500)
        for(i in 5..100)
            sheet1.setColumnView(i,15)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD);
        WritableCellFormat formatoEncabezado = new WritableCellFormat (arial10BoldFont);
        formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)
        formatoEncabezado.setWrap(true)
        formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
        //formatoEncabezado.setAlignment(Alignment.CENTRE)
        WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoDatos.setFont(courier8PlainFont)
        formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)
        WritableCellFormat formatoInfoReporte = new WritableCellFormat (arial14BoldFont);
        WritableCellFormat formatoTitulo = new WritableCellFormat (arial16BoldFont);
        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="hoja_costo_estano.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(3,0, "HOJA DE COSTO DE ESTAÑO",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE

        def reporteHojaDeCosto = ReporteHojaDeCosto.get(Integer.parseInt(params.hcid))
        def nombreDelConjunto = reporteHojaDeCosto.nombreDelConjunto

        sheet1.addCell(new Label(3,1, "NOMBRE DEL CONJUNTO:",formatoInfoReporte))
        sheet1.addCell(new Label(4,1, "${reporteHojaDeCosto.nombreDelConjunto}",formatoInfoReporte))
        sheet1.addCell(new Label(3,2, "DESTINO:",formatoInfoReporte))
        sheet1.addCell(new Label(4,2, "${reporteHojaDeCosto.destinoDelConjunto}",formatoInfoReporte))

        if (reporteHojaDeCosto.empresa){
            sheet1.addCell(new Label(3,3, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(4,3, "${reporteHojaDeCosto.empresa.toString()}",formatoInfoReporte))
        }

        //******* aplicar a todos los elementos **********

        if (!reporteHojaDeCosto.fechaInicial) //aplicar a todos los elementos
        //if (!reporteHojaDeCosto.loteInicial.equals(""))
            sheet1.addCell(new Label(5,1, "ENTRE LOTES: ${reporteHojaDeCosto.loteInicial} AL ${reporteHojaDeCosto.loteFinal}",formatoInfoReporte))
        else
            sheet1.addCell(new Label(5,1, "ENTRE FECHAS: ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(reporteHojaDeCosto.fechaInicial)} AL ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(reporteHojaDeCosto.fechaFinal)}",formatoInfoReporte))

        sheet1.addCell(new Label(5,2, "ENTRE LEYES: ${reporteHojaDeCosto.leyMinimaEstano}% AL ${reporteHojaDeCosto.leyMaximaEstano}%",formatoInfoReporte))

        def liquidacionesEstano = LiquidacionDeEstano.findAllByConjuntoEstano(reporteHojaDeCosto.nombreDelConjunto)

        sheet1.addCell(new Label(0,6, "RECEPCION",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "COT. DIA",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "LIQUIDACION",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "NOMBRE",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "TOTAL TARA",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "K. N. H.",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "% H2O",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "K. N. S.",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "LEY %Sn",formatoEncabezado))
        sheet1.addCell(new Label(13,6, "K. F. %Sn",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "COT. OFICIAL",formatoEncabezado))
        sheet1.addCell(new Label(15,6, "VALOR OF. BRUTO",formatoEncabezado))
        sheet1.addCell(new Label(16,6, "ALICUOTA %",formatoEncabezado))
        sheet1.addCell(new Label(17,6, "VALOR NETO \$us",formatoEncabezado))
        sheet1.addCell(new Label(18,6, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(19,6, "BONO CALIDAD",formatoEncabezado))
        sheet1.addCell(new Label(20,6, "BONO INCENTIVO",formatoEncabezado))
        sheet1.addCell(new Label(21,6, "VALOR DE COMPRA",formatoEncabezado))
        sheet1.addCell(new Label(22,6, "RM",formatoEncabezado))

        /*AGREGAR ESTE CONTROL PARA TODOS LOS ELEMENTOS, ES PARA CUANDO NO SE GENEREN RESULTADOS, AL PARECER CUANDO EL list
        * NO ENCUENTRA RESULTADOS DEVUELVE UN LIST null. ADICIONAR EL CODIGO EL EL GSP PARA QUE APAREZCA LA NOTIFICACION.*/
        if (!liquidacionesEstano) {
            flash.error = "NO SE PUDO OBTENER RESULTADOS!"
            System.out.println("*** SE ESTA PRODUCIENDO RESULTADOS NULL!!!")
            redirect(action: "create")
            return
        }


        if (liquidacionesEstano.size()==0 || nombreDelConjunto.equals("")){
            if (liquidacionesEstano.size()==0)
                sheet1.addCell(new Label(0,7, "SIN RESULTADOS",formatoInfoReporte))
            if (nombreDelConjunto.equals(""))
                sheet1.addCell(new Label(0,7, "ESPECIFIQUE NOMBRE DE CONJUNTO",formatoInfoReporte))
        }else{

            /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA RETENCIONES DE LEY*/
            def listaRetencionesDeLey = retencionesEstanoJSON liquidacionesEstano,"DE LEY"
            def columna = 23
            listaRetencionesDeLey.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL RET. DE LEY",formatoEncabezado))
            columna++

            /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA OTRAS RETENCIONES*/
            def listaRetencionesOtras = retencionesEstanoJSON liquidacionesEstano,"OTRA"
            listaRetencionesOtras.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL OTRAS RET.",formatoEncabezado))
            columna++

            sheet1.addCell(new Label(columna,6, "TOTAL RET.",formatoEncabezado))
            sheet1.addCell(new Label(columna+1,6, "TOTAL PAGADO",formatoEncabezado))
            sheet1.addCell(new Label(columna+2,6, "ANTICIPO/ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+3,6, "ANTICIPO/F. ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+4,6, "LIQUIDO PAGABLE",formatoEncabezado))
            sheet1.addCell(new Label(columna+5,6, "CANC. TRANSPORTE",formatoEncabezado))
            sheet1.addCell(new Label(columna+6,6, "CANC. LABORAT.",formatoEncabezado))

            //DESPLIEGUE DE DATOS DE LIQUIDACIONES
            //formatoEncabezado.setAlignment(Alignment.RIGHT)
            def fila = 7
            //variables acumuladoras
            def numeroRegistros=0
            def totalCantidadSacos=0
            def totalTotalTara=0
            def totalPesoBruto=0
            def totalKilosNetosHumedos=0
            def totalHumedad=0
            def totalKilosNetosSecos=0
            def totalPorcentajeEstano=0
            def totalKilosFinosEstano=0
            def totalCotizacionQuincenalEstano=0
            def totalValorOficialBruto=0
            def totalCotizacionDiariaEstano=0
            def totalAlicuota=0
            def totalValorNeto=0
            def totalValorNetoBolivianos=0
            def totalBonoCalidad=0
            def totalBonoIncentivo=0
            def totalValorDeCompra=0
            def totalRegaliaMinera=0
            def totalTotalRetenciones=0
            def totalTotalPagado=0
            def totalTotalAnticiposContraEntrega=0
            def totalTotalAnticiposContraFuturaEntrega=0
            def totalTotalLiquidoPagable=0
            def totalCostoDeTransporte=0
            def totalTotalCostoLaboratorio=0

            def cuotaParticipacionEmpresa = new ArrayList<String>()
            def cuotaParticipacionCuota = new ArrayList<Integer>()

            liquidacionesEstano.each {
                numeroRegistros++
                totalCantidadSacos+=it.cantidadDeSacos
                totalTotalTara+=(it.tara*it.cantidadDeSacos)
                totalPesoBruto+=it.pesoBruto
                totalKilosNetosHumedos+=it.kilosNetosHumedos
                totalHumedad+=it.porcentajeHumedadFinal
                totalKilosNetosSecos+=it.kilosNetosSecos
                totalPorcentajeEstano+=it.porcentajeEstano
                totalKilosFinosEstano+=it.kilosFinosEstano
                totalCotizacionQuincenalEstano+=it.recepcionDeEstano.cotizacionQuincenalDeMinerales.estano
                totalValorOficialBruto+=it.valorOficialBruto
                totalCotizacionDiariaEstano+=it.recepcionDeEstano.cotizacionDiariaDeMinerales.estano
                totalAlicuota+=it.recepcionDeEstano.alicuota.estano
                totalValorNeto+=it.valorNetoMineral
                totalValorNetoBolivianos+=it.valorNetoMineralEnBolivianos
                totalBonoCalidad+=it.bonoCalidad
                totalBonoIncentivo+=it.bonoIncentivo
                totalValorDeCompra+=it.valorDeCompra
                totalRegaliaMinera+=it.regaliaMinera
                totalTotalRetenciones+=it.totalRetenciones
                totalTotalPagado+=it.totalPagado
                totalTotalAnticiposContraEntrega+=it.totalAnticiposContraEntrega
                totalTotalAnticiposContraFuturaEntrega+=it.totalAnticiposContraFuturaEntrega
                totalTotalLiquidoPagable=totalTotalLiquidoPagable+((it.totalLiquidoPagable.doubleValue()<0)?0:it.totalLiquidoPagable.doubleValue())
                totalCostoDeTransporte+=it.recepcionDeEstano.costoDeTransporte
                totalTotalCostoLaboratorio+=it.totalCostoLaboratorio

                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new Number(1,fila, it.recepcionDeEstano.cotizacionDiariaDeMinerales.estano,formatoDatos))
                sheet1.addCell(new DateTime(2,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(3,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(4,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(5,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(6,fila, it.cantidadDeSacos,formatoDatos))
                sheet1.addCell(new Number(7,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(8,fila, it.tara*it.cantidadDeSacos,formatoDatos))
                sheet1.addCell(new Number(9,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(10,fila, it.porcentajeHumedadFinal,formatoDatos))
                sheet1.addCell(new Number(11,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(12,fila, it.porcentajeEstano,formatoDatos))
                sheet1.addCell(new Number(13,fila, it.kilosFinosEstano,formatoDatos))
                sheet1.addCell(new Number(14,fila, it.recepcionDeEstano.cotizacionQuincenalDeMinerales.estano,formatoDatos))
                sheet1.addCell(new Number(15,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(16,fila, it.recepcionDeEstano.alicuota.estano,formatoDatos))
                sheet1.addCell(new Number(17,fila, it.valorNetoMineral,formatoDatos))
                sheet1.addCell(new Number(18,fila, it.valorNetoMineralEnBolivianos,formatoDatos))
                sheet1.addCell(new Number(19,fila, it.bonoCalidad,formatoDatos))
                sheet1.addCell(new Number(20,fila, it.bonoIncentivo,formatoDatos))
                sheet1.addCell(new Number(21,fila, it.valorDeCompra,formatoDatos))
                sheet1.addCell(new Number(22,fila, it.regaliaMinera,formatoDatos))

                columna = 23

                /*DESPLIEGUE DE RETENCIONES DE LEY*/
                def retencionesDeLeyLiquidacion = LiquidacionDeEstanoRetenciones.findAllByLiquidacionDeEstanoAndTipoDeRetencion(it,"DE LEY")
                def numretDeLey = retencionesDeLeyLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretDeLey} RETENCIONES DE LEY!")
                def subtotalRetencionesDeLey=it.regaliaMinera.doubleValue()
                for(int i=0;i<listaRetencionesDeLey.size();i++){
                    def vr = valorRetencion(listaRetencionesDeLey.get(i), retencionesDeLeyLiquidacion,numretDeLey)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesDeLey+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesDeLey,formatoDatos))
                columna++

                /*DESPLIEGUE DE RETENCIONES DE LEY*/
                def retencionesOtrasLiquidacion = LiquidacionDeEstanoRetenciones.findAllByLiquidacionDeEstanoAndTipoDeRetencion(it,"OTRA")
                def numretOtras = retencionesOtrasLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretOtras} RETENCIONES DE LEY!")
                def subtotalRetencionesOtras=0
                for(int i=0;i<listaRetencionesOtras.size();i++){
                    def vr = valorRetencion(listaRetencionesOtras.get(i), retencionesOtrasLiquidacion,numretOtras)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesOtras+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesOtras,formatoDatos))
                columna++

                sheet1.addCell(new Number(columna,fila, it.totalRetenciones,formatoDatos))
                sheet1.addCell(new Number(columna+1,fila, it.totalPagado,formatoDatos))
                sheet1.addCell(new Number(columna+2,fila, it.totalAnticiposContraEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+3,fila, it.totalAnticiposContraFuturaEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+4,fila, it.totalLiquidoPagable,formatoDatos))
                sheet1.addCell(new Number(columna+5,fila, it.recepcionDeEstano.costoDeTransporte,formatoDatos))
                sheet1.addCell(new Number(columna+6,fila, it.totalCostoLaboratorio,formatoDatos))

                fila++

                if (cuotaParticipacionEmpresa.contains(it.nombreEmpresa)){
                    def obj=cuotaParticipacionCuota.get(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa))
                    obj++
                    cuotaParticipacionCuota.set(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa),obj)
                }else{
                    cuotaParticipacionEmpresa.add(it.nombreEmpresa)
                    cuotaParticipacionCuota.add(1)
                }

            }

            //IMPRESION DE TOTALES
            sheet1.addCell(new Number(1,fila, totalCotizacionDiariaEstano/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(6,fila, totalCantidadSacos,formatoDatos))
            sheet1.addCell(new Number(7,fila, totalPesoBruto,formatoDatos))
            sheet1.addCell(new Number(8,fila, totalTotalTara,formatoDatos))
            sheet1.addCell(new Number(9,fila, totalKilosNetosHumedos,formatoDatos))
            //sheet1.addCell(new Number(10,fila, totalHumedad/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(10,fila, (totalKilosNetosSecos/totalKilosNetosHumedos*100-100)*-1,formatoDatos))
            sheet1.addCell(new Number(11,fila, totalKilosNetosSecos,formatoDatos))
            //sheet1.addCell(new Number(12,fila, totalPorcentajeEstano/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(12,fila, totalKilosFinosEstano/totalKilosNetosSecos*100,formatoDatos))
            sheet1.addCell(new Number(13,fila, totalKilosFinosEstano,formatoDatos))
            sheet1.addCell(new Number(14,fila, totalCotizacionQuincenalEstano/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(15,fila, totalValorOficialBruto,formatoDatos))
            sheet1.addCell(new Number(16,fila, totalAlicuota/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(17,fila, totalValorNeto,formatoDatos))
            sheet1.addCell(new Number(18,fila, totalValorNetoBolivianos,formatoDatos))
            sheet1.addCell(new Number(19,fila, totalBonoCalidad,formatoDatos))
            sheet1.addCell(new Number(20,fila, totalBonoIncentivo,formatoDatos))
            sheet1.addCell(new Number(21,fila, totalValorDeCompra,formatoDatos))
            sheet1.addCell(new Number(22,fila, totalRegaliaMinera,formatoDatos))

            def columnaFinalRetenciones = 25+listaRetencionesDeLey.size()+listaRetencionesOtras.size()
            def totalLiquidaciones = liquidacionesEstano.size()
            for (int col=23;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =7;fil<totalLiquidaciones+7;fil++){
                    def valor = Double.parseDouble(sheet1.getWritableCell(col,fil).contents)
                    tret+=valor
                }
                sheet1.addCell(new Number(col,totalLiquidaciones+7, tret,formatoDatos))
            }

            sheet1.addCell(new Number(columnaFinalRetenciones,fila, totalTotalRetenciones,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+1,fila, totalTotalPagado,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+2,fila, totalTotalAnticiposContraEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+3,fila, totalTotalAnticiposContraFuturaEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+4,fila, totalTotalLiquidoPagable,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+5,fila, totalCostoDeTransporte,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+6,fila, totalTotalCostoLaboratorio,formatoDatos))

            sheet1.mergeCells(22, 5, 23+listaRetencionesDeLey.size(), 5);
            sheet1.mergeCells(23+listaRetencionesDeLey.size(), 5, 25+listaRetencionesDeLey.size()+listaRetencionesOtras.size(), 5);
            sheet1.addCell(new Label(22,5, "RETENCIONES DE LEY",formatoEncabezado))
            sheet1.addCell(new Label(23+listaRetencionesDeLey.size(),5, "OTRAS RETENCIONES",formatoEncabezado))

            //IMPRESION DE DISTRIBUCION PORCENTUAL
            sheet1.mergeCells(4, fila+3, 5, fila+3);
            sheet1.addCell(new Label(4,fila+3,"VOLUMEN DE PARTICIPACION POR EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(4,fila+4, "EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(5,fila+4, "PORCENTAJE",formatoEncabezado))
            for (int i=0;i<cuotaParticipacionEmpresa.size();i++){
                sheet1.addCell(new Label(4,fila+5+i, cuotaParticipacionEmpresa.get(i),formatoDatos))
                sheet1.addCell(new Number(5,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))
            }
            sheet1.addCell(new Label(4,fila+5+cuotaParticipacionEmpresa.size(),"TOTAL",formatoDatos))
            sheet1.addCell(new Number(5,fila+5+cuotaParticipacionEmpresa.size(),100.0,formatoDatos))
            //sheet1.addCell(new Number(5,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))

            workbook.write();
            workbook.close();
        }
    }

    def recrearReportePlata = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Hoja de Costo de Plata", 0)
        sheet1.setColumnView(0,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(1,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(2,11)
        sheet1.setColumnView(3,40)
        sheet1.setColumnView(4,40)
        sheet1.setRowView(6,500)
        for(i in 5..100)
            sheet1.setColumnView(i,15)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD);
        WritableCellFormat formatoEncabezado = new WritableCellFormat (arial10BoldFont);
        formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)
        formatoEncabezado.setWrap(true)
        formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
        //formatoEncabezado.setAlignment(Alignment.CENTRE)
        WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoDatos.setFont(courier8PlainFont)
        formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)
        WritableCellFormat formatoInfoReporte = new WritableCellFormat (arial14BoldFont);
        WritableCellFormat formatoTitulo = new WritableCellFormat (arial16BoldFont);
        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="hoja_costo_plata.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(3,0, "HOJA DE COSTO DE PLATA",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE

        def reporteHojaDeCosto = ReporteHojaDeCosto.get(Integer.parseInt(params.hcid))
        def nombreDelConjunto = reporteHojaDeCosto.nombreDelConjunto

        sheet1.addCell(new Label(3,1, "NOMBRE DEL CONJUNTO:",formatoInfoReporte))
        sheet1.addCell(new Label(4,1, "${reporteHojaDeCosto.nombreDelConjunto}",formatoInfoReporte))
        sheet1.addCell(new Label(3,2, "DESTINO:",formatoInfoReporte))
        sheet1.addCell(new Label(4,2, "${reporteHojaDeCosto.destinoDelConjunto}",formatoInfoReporte))

        if (reporteHojaDeCosto.empresa){
            sheet1.addCell(new Label(3,3, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(4,3, "${reporteHojaDeCosto.empresa.toString()}",formatoInfoReporte))
        }

        if (!reporteHojaDeCosto.fechaInicial)
            sheet1.addCell(new Label(5,1, "ENTRE LOTES: ${reporteHojaDeCosto.loteInicial} AL ${reporteHojaDeCosto.loteFinal}",formatoInfoReporte))
        else
            sheet1.addCell(new Label(5,1, "ENTRE FECHAS: ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(reporteHojaDeCosto.fechaInicial)} AL ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(reporteHojaDeCosto.fechaFinal)}",formatoInfoReporte))

        sheet1.addCell(new Label(5,2, "ENTRE LEYES: ${reporteHojaDeCosto.leyMinimaPlata}% AL ${reporteHojaDeCosto.leyMaximaPlata}%",formatoInfoReporte))

        def liquidacionesPlata = LiquidacionDePlata.findAllByConjuntoPlata(reporteHojaDeCosto.nombreDelConjunto)

        sheet1.addCell(new Label(0,6, "RECEPCION",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "COT. DIA",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "LIQUIDACION",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "NOMBRE",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "TOTAL TARA",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "K. N. H.",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "% H2O",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "K. N. S.",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "LEY DM Ag",formatoEncabezado))
        sheet1.addCell(new Label(13,6, "K. F. Ag",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "COT. OFICIAL",formatoEncabezado))
        sheet1.addCell(new Label(15,6, "VALOR OF. BRUTO",formatoEncabezado))
        sheet1.addCell(new Label(16,6, "ALICUOTA %",formatoEncabezado))
        sheet1.addCell(new Label(17,6, "VALOR NETO \$us",formatoEncabezado))
        sheet1.addCell(new Label(18,6, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(19,6, "BONO CALIDAD",formatoEncabezado))
        sheet1.addCell(new Label(20,6, "BONO INCENTIVO",formatoEncabezado))
        sheet1.addCell(new Label(21,6, "VALOR DE COMPRA",formatoEncabezado))
        sheet1.addCell(new Label(22,6, "RM",formatoEncabezado))

        /*AGREGAR ESTE CONTROL PARA TODOS LOS ELEMENTOS, ES PARA CUANDO NO SE GENEREN RESULTADOS, AL PARECER CUANDO EL list
        * NO ENCUENTRA RESULTADOS DEVUELVE UN LIST null. ADICIONAR EL CODIGO EL EL GSP PARA QUE APAREZCA LA NOTIFICACION.*/
        if (!liquidacionesPlata) {
            flash.error = "NO SE PUDO OBTENER RESULTADOS!"
            System.out.println("*** SE ESTA PRODUCIENDO RESULTADOS NULL!!!")
            redirect(action: "create")
            return
        }


        if (liquidacionesPlata.size()==0 || nombreDelConjunto.equals("")){
            if (liquidacionesPlata.size()==0)
                sheet1.addCell(new Label(0,7, "SIN RESULTADOS",formatoInfoReporte))
            if (nombreDelConjunto.equals(""))
                sheet1.addCell(new Label(0,7, "ESPECIFIQUE NOMBRE DE CONJUNTO",formatoInfoReporte))
        }else{

            /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA RETENCIONES DE LEY*/
            def listaRetencionesDeLey = retencionesPlataJSON liquidacionesPlata,"DE LEY"
            def columna = 23
            listaRetencionesDeLey.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL RET. DE LEY",formatoEncabezado))
            columna++

            /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA OTRAS RETENCIONES*/
            def listaRetencionesOtras = retencionesPlataJSON liquidacionesPlata,"OTRA"
            listaRetencionesOtras.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL OTRAS RET.",formatoEncabezado))
            columna++

            sheet1.addCell(new Label(columna,6, "TOTAL RET.",formatoEncabezado))
            sheet1.addCell(new Label(columna+1,6, "TOTAL PAGADO",formatoEncabezado))
            sheet1.addCell(new Label(columna+2,6, "ANTICIPO/ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+3,6, "ANTICIPO/F. ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+4,6, "LIQUIDO PAGABLE",formatoEncabezado))
            sheet1.addCell(new Label(columna+5,6, "CANC. TRANSPORTE",formatoEncabezado))
            sheet1.addCell(new Label(columna+6,6, "CANC. LABORAT.",formatoEncabezado))

            //DESPLIEGUE DE DATOS DE LIQUIDACIONES
            //formatoEncabezado.setAlignment(Alignment.RIGHT)
            def fila = 7
            //variables acumuladoras
            def numeroRegistros=0
            def totalCantidadSacos=0
            def totalTotalTara=0
            def totalPesoBruto=0
            def totalKilosNetosHumedos=0
            def totalHumedad=0
            def totalKilosNetosSecos=0
            def totalPorcentajePlataFinal=0
            def totalKilosFinosPlata=0
            def totalCotizacionQuincenalPlata=0
            def totalValorOficialBruto=0
            def totalCotizacionDiariaPlata=0
            def totalAlicuota=0
            def totalValorNeto=0
            def totalValorNetoBolivianos=0
            def totalBonoCalidad=0
            def totalBonoIncentivo=0
            def totalValorDeCompra=0
            def totalRegaliaMinera=0
            def totalTotalRetenciones=0
            def totalTotalPagado=0
            def totalTotalAnticiposContraEntrega=0
            def totalTotalAnticiposContraFuturaEntrega=0
            def totalTotalLiquidoPagable=0
            def totalCostoDeTransporte=0
            def totalTotalCostoLaboratorio=0

            def cuotaParticipacionEmpresa = new ArrayList<String>()
            def cuotaParticipacionCuota = new ArrayList<Integer>()

            liquidacionesPlata.each {
                numeroRegistros++
                totalCantidadSacos+=it.cantidadDeSacos
                totalTotalTara+=(it.tara*it.cantidadDeSacos)
                totalPesoBruto+=it.pesoBruto
                totalKilosNetosHumedos+=it.kilosNetosHumedos
                totalHumedad+=it.porcentajeHumedadFinal
                totalKilosNetosSecos+=it.kilosNetosSecos
                totalPorcentajePlataFinal+=it.porcentajePlataFinal
                totalKilosFinosPlata+=it.kilosFinosPlata
                totalCotizacionQuincenalPlata+=it.recepcionDePlata.cotizacionQuincenalDeMinerales.plata
                totalValorOficialBruto+=it.valorOficialBruto
                totalCotizacionDiariaPlata+=it.recepcionDePlata.cotizacionDiariaDeMinerales.plata
                totalAlicuota+=it.recepcionDePlata.alicuota.plata
                totalValorNeto+=it.valorNetoMineral
                totalValorNetoBolivianos+=it.valorNetoMineralEnBolivianos
                totalBonoCalidad+=it.bonoCalidad
                totalBonoIncentivo+=it.bonoIncentivo
                totalValorDeCompra+=it.valorDeCompra
                totalRegaliaMinera+=it.regaliaMinera
                totalTotalRetenciones+=it.totalRetenciones
                totalTotalPagado+=it.totalPagado
                totalTotalAnticiposContraEntrega+=it.totalAnticiposContraEntrega
                totalTotalAnticiposContraFuturaEntrega+=it.totalAnticiposContraFuturaEntrega
                totalTotalLiquidoPagable=totalTotalLiquidoPagable+((it.totalLiquidoPagable.doubleValue()<0)?0:it.totalLiquidoPagable.doubleValue())
                totalCostoDeTransporte+=it.recepcionDePlata.costoDeTransporte
                totalTotalCostoLaboratorio+=it.totalCostoLaboratorio

                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new Number(1,fila, it.recepcionDePlata.cotizacionDiariaDeMinerales.plata,formatoDatos))
                sheet1.addCell(new DateTime(2,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(3,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(4,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(5,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(6,fila, it.cantidadDeSacos,formatoDatos))
                sheet1.addCell(new Number(7,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(8,fila, it.tara*it.cantidadDeSacos,formatoDatos))
                sheet1.addCell(new Number(9,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(10,fila, it.porcentajeHumedadFinal,formatoDatos))
                sheet1.addCell(new Number(11,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(12,fila, it.porcentajePlataFinal,formatoDatos))
                sheet1.addCell(new Number(13,fila, it.kilosFinosPlata,formatoDatos))
                sheet1.addCell(new Number(14,fila, it.recepcionDePlata.cotizacionQuincenalDeMinerales.plata,formatoDatos))
                sheet1.addCell(new Number(15,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(16,fila, it.recepcionDePlata.alicuota.plata,formatoDatos))
                sheet1.addCell(new Number(17,fila, it.valorNetoMineral,formatoDatos))
                sheet1.addCell(new Number(18,fila, it.valorNetoMineralEnBolivianos,formatoDatos))
                sheet1.addCell(new Number(19,fila, it.bonoCalidad,formatoDatos))
                sheet1.addCell(new Number(20,fila, it.bonoIncentivo,formatoDatos))
                sheet1.addCell(new Number(21,fila, it.valorDeCompra,formatoDatos))
                sheet1.addCell(new Number(22,fila, it.regaliaMinera,formatoDatos))

                columna = 23

                /*DESPLIEGUE DE RETENCIONES DE LEY*/
                def retencionesDeLeyLiquidacion = LiquidacionDePlataRetenciones.findAllByLiquidacionDePlataAndTipoDeRetencion(it,"DE LEY")
                def numretDeLey = retencionesDeLeyLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretDeLey} RETENCIONES DE LEY!")
                def subtotalRetencionesDeLey=it.regaliaMinera.doubleValue()
                for(int i=0;i<listaRetencionesDeLey.size();i++){
                    def vr = valorRetencion(listaRetencionesDeLey.get(i), retencionesDeLeyLiquidacion,numretDeLey)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesDeLey+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesDeLey,formatoDatos))
                columna++

                /*DESPLIEGUE DE RETENCIONES DE LEY*/
                def retencionesOtrasLiquidacion = LiquidacionDePlataRetenciones.findAllByLiquidacionDePlataAndTipoDeRetencion(it,"OTRA")
                def numretOtras = retencionesOtrasLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretOtras} RETENCIONES DE LEY!")
                def subtotalRetencionesOtras=0
                for(int i=0;i<listaRetencionesOtras.size();i++){
                    def vr = valorRetencion(listaRetencionesOtras.get(i), retencionesOtrasLiquidacion,numretOtras)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesOtras+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesOtras,formatoDatos))
                columna++

                sheet1.addCell(new Number(columna,fila, it.totalRetenciones,formatoDatos))
                sheet1.addCell(new Number(columna+1,fila, it.totalPagado,formatoDatos))
                sheet1.addCell(new Number(columna+2,fila, it.totalAnticiposContraEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+3,fila, it.totalAnticiposContraFuturaEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+4,fila, it.totalLiquidoPagable,formatoDatos))
                sheet1.addCell(new Number(columna+5,fila, it.recepcionDePlata.costoDeTransporte,formatoDatos))
                sheet1.addCell(new Number(columna+6,fila, it.totalCostoLaboratorio,formatoDatos))

                fila++

                if (cuotaParticipacionEmpresa.contains(it.nombreEmpresa)){
                    def obj=cuotaParticipacionCuota.get(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa))
                    obj++
                    cuotaParticipacionCuota.set(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa),obj)
                }else{
                    cuotaParticipacionEmpresa.add(it.nombreEmpresa)
                    cuotaParticipacionCuota.add(1)
                }

            }

            //IMPRESION DE TOTALES
            sheet1.addCell(new Number(1,fila, totalCotizacionDiariaPlata/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(6,fila, totalCantidadSacos,formatoDatos))
            sheet1.addCell(new Number(7,fila, totalPesoBruto,formatoDatos))
            sheet1.addCell(new Number(8,fila, totalTotalTara,formatoDatos))
            sheet1.addCell(new Number(9,fila, totalKilosNetosHumedos,formatoDatos))
            //sheet1.addCell(new Number(10,fila, totalHumedad/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(10,fila, (totalKilosNetosSecos/totalKilosNetosHumedos*100-100)*-1,formatoDatos))
            sheet1.addCell(new Number(11,fila, totalKilosNetosSecos,formatoDatos))
            //sheet1.addCell(new Number(12,fila, totalPorcentajePlataFinal/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(12,fila, totalKilosFinosPlata/totalKilosNetosSecos*10000,formatoDatos))
            sheet1.addCell(new Number(13,fila, totalKilosFinosPlata,formatoDatos))
            sheet1.addCell(new Number(14,fila, totalCotizacionQuincenalPlata/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(15,fila, totalValorOficialBruto,formatoDatos))
            sheet1.addCell(new Number(16,fila, totalAlicuota/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(17,fila, totalValorNeto,formatoDatos))
            sheet1.addCell(new Number(18,fila, totalValorNetoBolivianos,formatoDatos))
            sheet1.addCell(new Number(19,fila, totalBonoCalidad,formatoDatos))
            sheet1.addCell(new Number(20,fila, totalBonoIncentivo,formatoDatos))
            sheet1.addCell(new Number(21,fila, totalValorDeCompra,formatoDatos))
            sheet1.addCell(new Number(22,fila, totalRegaliaMinera,formatoDatos))

            def columnaFinalRetenciones = 25+listaRetencionesDeLey.size()+listaRetencionesOtras.size()
            def totalLiquidaciones = liquidacionesPlata.size()
            for (int col=23;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =7;fil<totalLiquidaciones+7;fil++){
                    def valor = Double.parseDouble(sheet1.getWritableCell(col,fil).contents)
                    tret+=valor
                }
                sheet1.addCell(new Number(col,totalLiquidaciones+7, tret,formatoDatos))
            }

            sheet1.addCell(new Number(columnaFinalRetenciones,fila, totalTotalRetenciones,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+1,fila, totalTotalPagado,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+2,fila, totalTotalAnticiposContraEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+3,fila, totalTotalAnticiposContraFuturaEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+4,fila, totalTotalLiquidoPagable,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+5,fila, totalCostoDeTransporte,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+6,fila, totalTotalCostoLaboratorio,formatoDatos))

            sheet1.mergeCells(22, 5, 23+listaRetencionesDeLey.size(), 5);
            sheet1.mergeCells(23+listaRetencionesDeLey.size(), 5, 25+listaRetencionesDeLey.size()+listaRetencionesOtras.size(), 5);
            sheet1.addCell(new Label(22,5, "RETENCIONES DE LEY",formatoEncabezado))
            sheet1.addCell(new Label(23+listaRetencionesDeLey.size(),5, "OTRAS RETENCIONES",formatoEncabezado))

            //IMPRESION DE DISTRIBUCION PORCENTUAL
            sheet1.mergeCells(4, fila+3, 5, fila+3);
            sheet1.addCell(new Label(4,fila+3,"VOLUMEN DE PARTICIPACION POR EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(4,fila+4, "EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(5,fila+4, "PORCENTAJE",formatoEncabezado))
            for (int i=0;i<cuotaParticipacionEmpresa.size();i++){
                sheet1.addCell(new Label(4,fila+5+i, cuotaParticipacionEmpresa.get(i),formatoDatos))
                sheet1.addCell(new Number(5,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))
            }
            sheet1.addCell(new Label(4,fila+5+cuotaParticipacionEmpresa.size(),"TOTAL",formatoDatos))
            sheet1.addCell(new Number(5,fila+5+cuotaParticipacionEmpresa.size(),100.0,formatoDatos))
            //sheet1.addCell(new Number(5,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))

            workbook.write();
            workbook.close();
        }
    }

    def recrearReporteWolfran = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Hoja de Costo de Wolfran", 0)
        sheet1.setColumnView(0,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(1,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(2,11)
        sheet1.setColumnView(3,40)
        sheet1.setColumnView(4,40)
        sheet1.setRowView(6,500)
        for(i in 5..100)
            sheet1.setColumnView(i,15)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD);
        WritableCellFormat formatoEncabezado = new WritableCellFormat (arial10BoldFont);
        formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)
        formatoEncabezado.setWrap(true)
        formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
        //formatoEncabezado.setAlignment(Alignment.CENTRE)
        WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoDatos.setFont(courier8PlainFont)
        formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)
        WritableCellFormat formatoInfoReporte = new WritableCellFormat (arial14BoldFont);
        WritableCellFormat formatoTitulo = new WritableCellFormat (arial16BoldFont);
        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="hoja_costo_wolfran.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(3,0, "HOJA DE COSTO DE WOLFRAN",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE

        def reporteHojaDeCosto = ReporteHojaDeCosto.get(Integer.parseInt(params.hcid))
        def nombreDelConjunto = reporteHojaDeCosto.nombreDelConjunto

        sheet1.addCell(new Label(3,1, "NOMBRE DEL CONJUNTO:",formatoInfoReporte))
        sheet1.addCell(new Label(4,1, "${reporteHojaDeCosto.nombreDelConjunto}",formatoInfoReporte))
        sheet1.addCell(new Label(3,2, "DESTINO:",formatoInfoReporte))
        sheet1.addCell(new Label(4,2, "${reporteHojaDeCosto.destinoDelConjunto}",formatoInfoReporte))

        if (reporteHojaDeCosto.empresa){
            sheet1.addCell(new Label(3,3, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(4,3, "${reporteHojaDeCosto.empresa.toString()}",formatoInfoReporte))
        }

        if (!reporteHojaDeCosto.fechaInicial)
            sheet1.addCell(new Label(5,1, "ENTRE LOTES: ${reporteHojaDeCosto.loteInicial} AL ${reporteHojaDeCosto.loteFinal}",formatoInfoReporte))
        else
            sheet1.addCell(new Label(5,1, "ENTRE FECHAS: ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(reporteHojaDeCosto.fechaInicial)} AL ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(reporteHojaDeCosto.fechaFinal)}",formatoInfoReporte))

        sheet1.addCell(new Label(5,2, "ENTRE LEYES: ${reporteHojaDeCosto.leyMinimaWolfran}% AL ${reporteHojaDeCosto.leyMaximaWolfran}%",formatoInfoReporte))

        def liquidacionesWolfran = LiquidacionDeWolfran.findAllByConjuntoWolfran(reporteHojaDeCosto.nombreDelConjunto)

        sheet1.addCell(new Label(0,6, "RECEPCION",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "COT. DIA",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "LIQUIDACION",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "NOMBRE",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "TOTAL TARA",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "K. N. H.",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "% H2O",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "K. N. S.",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "LEY %WO3",formatoEncabezado))
        sheet1.addCell(new Label(13,6, "K. F. WO3",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "COT. OFICIAL",formatoEncabezado))
        sheet1.addCell(new Label(15,6, "VALOR OF. BRUTO",formatoEncabezado))
        sheet1.addCell(new Label(16,6, "ALICUOTA %",formatoEncabezado))
        sheet1.addCell(new Label(17,6, "VALOR NETO \$us",formatoEncabezado))
        sheet1.addCell(new Label(18,6, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(19,6, "BONO CALIDAD",formatoEncabezado))
        sheet1.addCell(new Label(20,6, "BONO INCENTIVO",formatoEncabezado))
        sheet1.addCell(new Label(21,6, "VALOR DE COMPRA",formatoEncabezado))
        sheet1.addCell(new Label(22,6, "RM",formatoEncabezado))

        /*AGREGAR ESTE CONTROL PARA TODOS LOS ELEMENTOS, ES PARA CUANDO NO SE GENEREN RESULTADOS, AL PARECER CUANDO EL list
        * NO ENCUENTRA RESULTADOS DEVUELVE UN LIST null. ADICIONAR EL CODIGO EL EL GSP PARA QUE APAREZCA LA NOTIFICACION.*/
        if (!liquidacionesWolfran) {
            flash.error = "NO SE PUDO OBTENER RESULTADOS!"
            System.out.println("*** SE ESTA PRODUCIENDO RESULTADOS NULL!!!")
            redirect(action: "create")
            return
        }


        if (liquidacionesWolfran.size()==0 || nombreDelConjunto.equals("")){
            if (liquidacionesWolfran.size()==0)
                sheet1.addCell(new Label(0,7, "SIN RESULTADOS",formatoInfoReporte))
            if (nombreDelConjunto.equals(""))
                sheet1.addCell(new Label(0,7, "ESPECIFIQUE NOMBRE DE CONJUNTO",formatoInfoReporte))
        }else{

            /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA RETENCIONES DE LEY*/
            def listaRetencionesDeLey = retencionesWolfranJSON liquidacionesWolfran,"DE LEY"
            def columna = 23
            listaRetencionesDeLey.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL RET. DE LEY",formatoEncabezado))
            columna++

            /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA OTRAS RETENCIONES*/
            def listaRetencionesOtras = retencionesWolfranJSON liquidacionesWolfran,"OTRA"
            listaRetencionesOtras.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL OTRAS RET.",formatoEncabezado))
            columna++

            sheet1.addCell(new Label(columna,6, "TOTAL RET.",formatoEncabezado))
            sheet1.addCell(new Label(columna+1,6, "TOTAL PAGADO",formatoEncabezado))
            sheet1.addCell(new Label(columna+2,6, "ANTICIPO/ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+3,6, "ANTICIPO/F. ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+4,6, "LIQUIDO PAGABLE",formatoEncabezado))
            sheet1.addCell(new Label(columna+5,6, "CANC. TRANSPORTE",formatoEncabezado))
            sheet1.addCell(new Label(columna+6,6, "CANC. LABORAT.",formatoEncabezado))

            //DESPLIEGUE DE DATOS DE LIQUIDACIONES
            //formatoEncabezado.setAlignment(Alignment.RIGHT)
            def fila = 7
            //variables acumuladoras
            def numeroRegistros=0
            def totalCantidadSacos=0
            def totalTotalTara=0
            def totalPesoBruto=0
            def totalKilosNetosHumedos=0
            def totalHumedad=0
            def totalKilosNetosSecos=0
            def totalPorcentajeWolfran=0
            def totalKilosFinosWolfran=0
            def totalCotizacionQuincenalWolfran=0
            def totalValorOficialBruto=0
            def totalCotizacionDiariaWolfran=0
            def totalAlicuota=0
            def totalValorNeto=0
            def totalValorNetoBolivianos=0
            def totalBonoCalidad=0
            def totalBonoIncentivo=0
            def totalValorDeCompra=0
            def totalRegaliaMinera=0
            def totalTotalRetenciones=0
            def totalTotalPagado=0
            def totalTotalAnticiposContraEntrega=0
            def totalTotalAnticiposContraFuturaEntrega=0
            def totalTotalLiquidoPagable=0
            def totalCostoDeTransporte=0
            def totalTotalCostoLaboratorio=0

            def cuotaParticipacionEmpresa = new ArrayList<String>()
            def cuotaParticipacionCuota = new ArrayList<Integer>()

            liquidacionesWolfran.each {
                numeroRegistros++
                totalCantidadSacos+=it.cantidadDeSacos
                totalTotalTara+=(it.tara*it.cantidadDeSacos)
                totalPesoBruto+=it.pesoBruto
                totalKilosNetosHumedos+=it.kilosNetosHumedos
                totalHumedad+=it.porcentajeHumedadFinal
                totalKilosNetosSecos+=it.kilosNetosSecos
                totalPorcentajeWolfran+=it.porcentajeWolfran
                totalKilosFinosWolfran+=it.kilosFinosWolfran
                totalCotizacionQuincenalWolfran+=it.recepcionDeWolfran.cotizacionQuincenalDeMinerales.wolfran
                totalValorOficialBruto+=it.valorOficialBruto
                totalCotizacionDiariaWolfran+=it.recepcionDeWolfran.cotizacionDiariaDeMinerales.wolfran
                totalAlicuota+=it.recepcionDeWolfran.alicuota.wolfran
                totalValorNeto+=it.valorNetoMineral
                totalValorNetoBolivianos+=it.valorNetoMineralEnBolivianos
                totalBonoCalidad+=it.bonoCalidad
                totalBonoIncentivo+=it.bonoIncentivo
                totalValorDeCompra+=it.valorDeCompra
                totalRegaliaMinera+=it.regaliaMinera
                totalTotalRetenciones+=it.totalRetenciones
                totalTotalPagado+=it.totalPagado
                totalTotalAnticiposContraEntrega+=it.totalAnticiposContraEntrega
                totalTotalAnticiposContraFuturaEntrega+=it.totalAnticiposContraFuturaEntrega
                totalTotalLiquidoPagable=totalTotalLiquidoPagable+((it.totalLiquidoPagable.doubleValue()<0)?0:it.totalLiquidoPagable.doubleValue())
                totalCostoDeTransporte+=it.recepcionDeWolfran.costoDeTransporte
                totalTotalCostoLaboratorio+=it.totalCostoLaboratorio

                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new Number(1,fila, it.recepcionDeWolfran.cotizacionDiariaDeMinerales.wolfran,formatoDatos))
                sheet1.addCell(new DateTime(2,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(3,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(4,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(5,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(6,fila, it.cantidadDeSacos,formatoDatos))
                sheet1.addCell(new Number(7,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(8,fila, it.tara*it.cantidadDeSacos,formatoDatos))
                sheet1.addCell(new Number(9,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(10,fila, it.porcentajeHumedadFinal,formatoDatos))
                sheet1.addCell(new Number(11,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(12,fila, it.porcentajeWolfran,formatoDatos))
                sheet1.addCell(new Number(13,fila, it.kilosFinosWolfran,formatoDatos))
                sheet1.addCell(new Number(14,fila, it.recepcionDeWolfran.cotizacionQuincenalDeMinerales.wolfran,formatoDatos))
                sheet1.addCell(new Number(15,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(16,fila, it.recepcionDeWolfran.alicuota.wolfran,formatoDatos))
                sheet1.addCell(new Number(17,fila, it.valorNetoMineral,formatoDatos))
                sheet1.addCell(new Number(18,fila, it.valorNetoMineralEnBolivianos,formatoDatos))
                sheet1.addCell(new Number(19,fila, it.bonoCalidad,formatoDatos))
                sheet1.addCell(new Number(20,fila, it.bonoIncentivo,formatoDatos))
                sheet1.addCell(new Number(21,fila, it.valorDeCompra,formatoDatos))
                sheet1.addCell(new Number(22,fila, it.regaliaMinera,formatoDatos))

                columna = 23

                /*DESPLIEGUE DE RETENCIONES DE LEY*/
                def retencionesDeLeyLiquidacion = LiquidacionDeWolfranRetenciones.findAllByLiquidacionDeWolfranAndTipoDeRetencion(it,"DE LEY")
                def numretDeLey = retencionesDeLeyLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretDeLey} RETENCIONES DE LEY!")
                def subtotalRetencionesDeLey=it.regaliaMinera.doubleValue()
                for(int i=0;i<listaRetencionesDeLey.size();i++){
                    def vr = valorRetencion(listaRetencionesDeLey.get(i), retencionesDeLeyLiquidacion,numretDeLey)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesDeLey+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesDeLey,formatoDatos))
                columna++

                /*DESPLIEGUE DE RETENCIONES DE LEY*/
                def retencionesOtrasLiquidacion = LiquidacionDeWolfranRetenciones.findAllByLiquidacionDeWolfranAndTipoDeRetencion(it,"OTRA")
                def numretOtras = retencionesOtrasLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretOtras} RETENCIONES DE LEY!")
                def subtotalRetencionesOtras=0
                for(int i=0;i<listaRetencionesOtras.size();i++){
                    def vr = valorRetencion(listaRetencionesOtras.get(i), retencionesOtrasLiquidacion,numretOtras)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesOtras+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesOtras,formatoDatos))
                columna++

                sheet1.addCell(new Number(columna,fila, it.totalRetenciones,formatoDatos))
                sheet1.addCell(new Number(columna+1,fila, it.totalPagado,formatoDatos))
                sheet1.addCell(new Number(columna+2,fila, it.totalAnticiposContraEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+3,fila, it.totalAnticiposContraFuturaEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+4,fila, it.totalLiquidoPagable,formatoDatos))
                sheet1.addCell(new Number(columna+5,fila, it.recepcionDeWolfran.costoDeTransporte,formatoDatos))
                sheet1.addCell(new Number(columna+6,fila, it.totalCostoLaboratorio,formatoDatos))

                fila++

                if (cuotaParticipacionEmpresa.contains(it.nombreEmpresa)){
                    def obj=cuotaParticipacionCuota.get(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa))
                    obj++
                    cuotaParticipacionCuota.set(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa),obj)
                }else{
                    cuotaParticipacionEmpresa.add(it.nombreEmpresa)
                    cuotaParticipacionCuota.add(1)
                }

            }

            //IMPRESION DE TOTALES
            sheet1.addCell(new Number(1,fila, totalCotizacionDiariaWolfran/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(6,fila, totalCantidadSacos,formatoDatos))
            sheet1.addCell(new Number(7,fila, totalPesoBruto,formatoDatos))
            sheet1.addCell(new Number(8,fila, totalTotalTara,formatoDatos))
            sheet1.addCell(new Number(9,fila, totalKilosNetosHumedos,formatoDatos))
            //sheet1.addCell(new Number(10,fila, totalHumedad/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(10,fila, (totalKilosNetosSecos/totalKilosNetosHumedos*100-100)*-1,formatoDatos))
            sheet1.addCell(new Number(11,fila, totalKilosNetosSecos,formatoDatos))
            //sheet1.addCell(new Number(12,fila, totalPorcentajeWolfran/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(12,fila, totalKilosFinosWolfran/totalKilosNetosSecos*100,formatoDatos))
            sheet1.addCell(new Number(13,fila, totalKilosFinosWolfran,formatoDatos))
            sheet1.addCell(new Number(14,fila, totalCotizacionQuincenalWolfran/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(15,fila, totalValorOficialBruto,formatoDatos))
            sheet1.addCell(new Number(16,fila, totalAlicuota/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(17,fila, totalValorNeto,formatoDatos))
            sheet1.addCell(new Number(18,fila, totalValorNetoBolivianos,formatoDatos))
            sheet1.addCell(new Number(19,fila, totalBonoCalidad,formatoDatos))
            sheet1.addCell(new Number(20,fila, totalBonoIncentivo,formatoDatos))
            sheet1.addCell(new Number(21,fila, totalValorDeCompra,formatoDatos))
            sheet1.addCell(new Number(22,fila, totalRegaliaMinera,formatoDatos))

            def columnaFinalRetenciones = 25+listaRetencionesDeLey.size()+listaRetencionesOtras.size()
            def totalLiquidaciones = liquidacionesWolfran.size()
            for (int col=23;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =7;fil<totalLiquidaciones+7;fil++){
                    def valor = Double.parseDouble(sheet1.getWritableCell(col,fil).contents)
                    tret+=valor
                }
                sheet1.addCell(new Number(col,totalLiquidaciones+7, tret,formatoDatos))
            }

            sheet1.addCell(new Number(columnaFinalRetenciones,fila, totalTotalRetenciones,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+1,fila, totalTotalPagado,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+2,fila, totalTotalAnticiposContraEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+3,fila, totalTotalAnticiposContraFuturaEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+4,fila, totalTotalLiquidoPagable,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+5,fila, totalCostoDeTransporte,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+6,fila, totalTotalCostoLaboratorio,formatoDatos))

            sheet1.mergeCells(22, 5, 23+listaRetencionesDeLey.size(), 5);
            sheet1.mergeCells(23+listaRetencionesDeLey.size(), 5, 25+listaRetencionesDeLey.size()+listaRetencionesOtras.size(), 5);
            sheet1.addCell(new Label(22,5, "RETENCIONES DE LEY",formatoEncabezado))
            sheet1.addCell(new Label(23+listaRetencionesDeLey.size(),5, "OTRAS RETENCIONES",formatoEncabezado))

            //IMPRESION DE DISTRIBUCION PORCENTUAL
            sheet1.mergeCells(4, fila+3, 5, fila+3);
            sheet1.addCell(new Label(4,fila+3,"VOLUMEN DE PARTICIPACION POR EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(4,fila+4, "EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(5,fila+4, "PORCENTAJE",formatoEncabezado))
            for (int i=0;i<cuotaParticipacionEmpresa.size();i++){
                sheet1.addCell(new Label(4,fila+5+i, cuotaParticipacionEmpresa.get(i),formatoDatos))
                sheet1.addCell(new Number(5,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))
            }
            sheet1.addCell(new Label(4,fila+5+cuotaParticipacionEmpresa.size(),"TOTAL",formatoDatos))
            sheet1.addCell(new Number(5,fila+5+cuotaParticipacionEmpresa.size(),100.0,formatoDatos))
            //sheet1.addCell(new Number(5,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))

            workbook.write();
            workbook.close();
        }
    }

    def recrearReporteAntimonio = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Hoja de Costo de Antimonio", 0)
        sheet1.setColumnView(0,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(1,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(2,11)
        sheet1.setColumnView(3,40)
        sheet1.setColumnView(4,40)
        sheet1.setRowView(6,500)
        for(i in 5..100)
            sheet1.setColumnView(i,15)
        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD);
        WritableCellFormat formatoEncabezado = new WritableCellFormat (arial10BoldFont);
        formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)
        formatoEncabezado.setWrap(true)
        formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
        //formatoEncabezado.setAlignment(Alignment.CENTRE)
        WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoDatos.setFont(courier8PlainFont)
        formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)
        WritableCellFormat formatoInfoReporte = new WritableCellFormat (arial14BoldFont);
        WritableCellFormat formatoTitulo = new WritableCellFormat (arial16BoldFont);
        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="hoja_costo_antimonio.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(3,0, "HOJA DE COSTO DE ANTIMONIO",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE

        def reporteHojaDeCosto = ReporteHojaDeCosto.get(Integer.parseInt(params.hcid))
        def nombreDelConjunto = reporteHojaDeCosto.nombreDelConjunto

        sheet1.addCell(new Label(3,1, "NOMBRE DEL CONJUNTO:",formatoInfoReporte))
        sheet1.addCell(new Label(4,1, "${reporteHojaDeCosto.nombreDelConjunto}",formatoInfoReporte))
        sheet1.addCell(new Label(3,2, "DESTINO:",formatoInfoReporte))
        sheet1.addCell(new Label(4,2, "${reporteHojaDeCosto.destinoDelConjunto}",formatoInfoReporte))

        if (reporteHojaDeCosto.empresa){
            sheet1.addCell(new Label(3,3, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(4,3, "${reporteHojaDeCosto.empresa.toString()}",formatoInfoReporte))
        }

        if (!reporteHojaDeCosto.fechaInicial)
            sheet1.addCell(new Label(5,1, "ENTRE LOTES: ${reporteHojaDeCosto.loteInicial} AL ${reporteHojaDeCosto.loteFinal}",formatoInfoReporte))
        else
            sheet1.addCell(new Label(5,1, "ENTRE FECHAS: ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(reporteHojaDeCosto.fechaInicial)} AL ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(reporteHojaDeCosto.fechaFinal)}",formatoInfoReporte))

        sheet1.addCell(new Label(5,2, "ENTRE LEYES: ${reporteHojaDeCosto.leyMinimaAntimonio}% AL ${reporteHojaDeCosto.leyMaximaAntimonio}%",formatoInfoReporte))

        def liquidacionesAntimonio = LiquidacionDeAntimonio.findAllByConjuntoAntimonio(reporteHojaDeCosto.nombreDelConjunto)

        sheet1.addCell(new Label(0,6, "RECEPCION",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "COT. DIA",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "LIQUIDACION",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "NOMBRE",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "TOTAL TARA",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "K. N. H.",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "% H2O",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "K. N. S.",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "LEY %Sb",formatoEncabezado))
        sheet1.addCell(new Label(13,6, "LEY %Pb",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "LEY %As",formatoEncabezado))
        sheet1.addCell(new Label(15,6, "K. F. Sb",formatoEncabezado))
        sheet1.addCell(new Label(16,6, "K. F. Pb",formatoEncabezado))
        sheet1.addCell(new Label(17,6, "K. F. As",formatoEncabezado))
        sheet1.addCell(new Label(18,6, "COT. OFICIAL",formatoEncabezado))
        sheet1.addCell(new Label(19,6, "VALOR OF. BRUTO",formatoEncabezado))
        sheet1.addCell(new Label(20,6, "ALICUOTA %",formatoEncabezado))
        sheet1.addCell(new Label(21,6, "VALOR NETO \$us",formatoEncabezado))
        sheet1.addCell(new Label(22,6, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(23,6, "BONO CALIDAD",formatoEncabezado))
        sheet1.addCell(new Label(24,6, "BONO INCENTIVO",formatoEncabezado))
        sheet1.addCell(new Label(25,6, "VALOR DE COMPRA",formatoEncabezado))
        sheet1.addCell(new Label(26,6, "RM",formatoEncabezado))

        /*AGREGAR ESTE CONTROL PARA TODOS LOS ELEMENTOS, ES PARA CUANDO NO SE GENEREN RESULTADOS, AL PARECER CUANDO EL list
        * NO ENCUENTRA RESULTADOS DEVUELVE UN LIST null. ADICIONAR EL CODIGO EL EL GSP PARA QUE APAREZCA LA NOTIFICACION.*/
        if (!liquidacionesAntimonio) {
            flash.error = "NO SE PUDO OBTENER RESULTADOS!"
            System.out.println("*** SE ESTA PRODUCIENDO RESULTADOS NULL!!!")
            redirect(action: "create")
            return
        }


        if (liquidacionesAntimonio.size()==0 || nombreDelConjunto.equals("")){
            if (liquidacionesAntimonio.size()==0)
                sheet1.addCell(new Label(0,7, "SIN RESULTADOS",formatoInfoReporte))
            if (nombreDelConjunto.equals(""))
                sheet1.addCell(new Label(0,7, "ESPECIFIQUE NOMBRE DE CONJUNTO",formatoInfoReporte))
        }else{

            /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA RETENCIONES DE LEY*/
            def listaRetencionesDeLey = retencionesAntimonioJSON liquidacionesAntimonio,"DE LEY"
            def columna = 27
            listaRetencionesDeLey.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL RET. DE LEY",formatoEncabezado))
            columna++

            /*DESPLIEGUE DE CABECERAS DE COLUMNA PARA OTRAS RETENCIONES*/
            def listaRetencionesOtras = retencionesAntimonioJSON liquidacionesAntimonio,"OTRA"
            listaRetencionesOtras.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL OTRAS RET.",formatoEncabezado))
            columna++

            sheet1.addCell(new Label(columna,6, "TOTAL RET.",formatoEncabezado))
            sheet1.addCell(new Label(columna+1,6, "TOTAL PAGADO",formatoEncabezado))
            sheet1.addCell(new Label(columna+2,6, "ANTICIPO/ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+3,6, "ANTICIPO/F. ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+4,6, "LIQUIDO PAGABLE",formatoEncabezado))
            sheet1.addCell(new Label(columna+5,6, "CANC. TRANSPORTE",formatoEncabezado))
            sheet1.addCell(new Label(columna+6,6, "CANC. LABORAT.",formatoEncabezado))

            //DESPLIEGUE DE DATOS DE LIQUIDACIONES
            //formatoEncabezado.setAlignment(Alignment.RIGHT)
            def fila = 7
            //variables acumuladoras
            def numeroRegistros=0
            def totalCantidadSacos=0
            def totalTotalTara=0
            def totalPesoBruto=0
            def totalKilosNetosHumedos=0
            def totalHumedad=0
            def totalKilosNetosSecos=0
            def totalKilosNetosSecosCotizacionDiaria=0
            def totalPorcentajeAntimonio=0
            def totalPorcentajePlomoFinal=0
            def totalPorcentajeArsenico=0
            def totalKilosFinosAntimonio=0
            def totalKilosFinosPlomo=0
            def totalKilosFinosArsenico=0
            def totalCotizacionQuincenalAntimonio=0
            def totalValorOficialBruto=0
            def totalCotizacionDiariaAntimonio=0
            def totalAlicuota=0
            def totalValorNeto=0
            def totalValorNetoBolivianos=0
            def totalBonoCalidad=0
            def totalBonoIncentivo=0
            def totalValorDeCompra=0
            def totalRegaliaMinera=0
            def totalTotalRetenciones=0
            def totalTotalPagado=0
            def totalTotalAnticiposContraEntrega=0
            def totalTotalAnticiposContraFuturaEntrega=0
            def totalTotalLiquidoPagable=0
            def totalCostoDeTransporte=0
            def totalTotalCostoLaboratorio=0

            def cuotaParticipacionEmpresa = new ArrayList<String>()
            def cuotaParticipacionCuota = new ArrayList<Integer>()

            liquidacionesAntimonio.each {
                numeroRegistros++
                totalCantidadSacos+=it.cantidadDeSacos
                totalTotalTara+=it.tara*it.cantidadDeSacos
                totalPesoBruto+=it.pesoBruto
                totalKilosNetosHumedos+=it.kilosNetosHumedos
                totalHumedad+=it.porcentajeHumedadFinal
                totalKilosNetosSecos+=it.kilosNetosSecos
                totalKilosNetosSecosCotizacionDiaria+=(it.kilosNetosSecos*it.recepcionDeAntimonio.cotizacionDiariaDeMinerales.antimonio)
                totalPorcentajeAntimonio+=it.porcentajeAntimonio
                totalPorcentajePlomoFinal+=it.porcentajePlomoFinal
                totalPorcentajeArsenico+=it.porcentajeArsenico
                totalKilosFinosAntimonio+=it.kilosFinosAntimonio
                totalKilosFinosPlomo+=(it.kilosNetosSecos*it.porcentajePlomoFinal/100)
                totalKilosFinosArsenico+=(it.kilosNetosSecos*it.porcentajeArsenico/100)
                totalCotizacionQuincenalAntimonio+=it.recepcionDeAntimonio.cotizacionQuincenalDeMinerales.antimonio
                totalValorOficialBruto+=it.valorOficialBruto
                totalCotizacionDiariaAntimonio+=it.recepcionDeAntimonio.cotizacionDiariaDeMinerales.antimonio
                totalAlicuota+=it.recepcionDeAntimonio.alicuota.antimonio
                totalValorNeto+=it.valorNetoMineral
                totalValorNetoBolivianos+=it.valorNetoMineralEnBolivianos
                totalBonoCalidad+=it.bonoCalidad
                totalBonoIncentivo+=it.bonoIncentivo
                totalValorDeCompra+=it.valorDeCompra
                totalRegaliaMinera+=it.regaliaMinera
                totalTotalRetenciones+=it.totalRetenciones
                totalTotalPagado+=it.totalPagado
                totalTotalAnticiposContraEntrega+=it.totalAnticiposContraEntrega
                totalTotalAnticiposContraFuturaEntrega+=it.totalAnticiposContraFuturaEntrega
                totalTotalLiquidoPagable=totalTotalLiquidoPagable+((it.totalLiquidoPagable.doubleValue()<0)?0:it.totalLiquidoPagable.doubleValue())
                totalCostoDeTransporte+=it.recepcionDeAntimonio.costoDeTransporte
                totalTotalCostoLaboratorio+=it.totalCostoLaboratorio

                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new Number(1,fila, it.recepcionDeAntimonio.cotizacionDiariaDeMinerales.antimonio,formatoDatos))
                sheet1.addCell(new DateTime(2,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(3,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(4,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(5,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(6,fila, it.cantidadDeSacos,formatoDatos))
                sheet1.addCell(new Number(7,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(8,fila, it.tara*it.cantidadDeSacos,formatoDatos))
                sheet1.addCell(new Number(9,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(10,fila, it.porcentajeHumedadFinal,formatoDatos))
                sheet1.addCell(new Number(11,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(12,fila, it.porcentajeAntimonio,formatoDatos))
                sheet1.addCell(new Number(13,fila, it.porcentajePlomoFinal,formatoDatos))
                sheet1.addCell(new Number(14,fila, it.porcentajeArsenico,formatoDatos))
                sheet1.addCell(new Number(15,fila, it.kilosFinosAntimonio,formatoDatos))
                sheet1.addCell(new Number(16,fila, it.kilosNetosSecos*it.porcentajePlomoFinal/100,formatoDatos))
                sheet1.addCell(new Number(17,fila, it.kilosNetosSecos*it.porcentajeArsenico/100,formatoDatos))
                sheet1.addCell(new Number(18,fila, it.recepcionDeAntimonio.cotizacionQuincenalDeMinerales.antimonio,formatoDatos))
                sheet1.addCell(new Number(19,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(20,fila, it.recepcionDeAntimonio.alicuota.antimonio,formatoDatos))
                sheet1.addCell(new Number(21,fila, it.valorNetoMineral,formatoDatos))
                sheet1.addCell(new Number(22,fila, it.valorNetoMineralEnBolivianos,formatoDatos))
                sheet1.addCell(new Number(23,fila, it.bonoCalidad,formatoDatos))
                sheet1.addCell(new Number(24,fila, it.bonoIncentivo,formatoDatos))
                sheet1.addCell(new Number(25,fila, it.valorDeCompra,formatoDatos))
                sheet1.addCell(new Number(26,fila, it.regaliaMinera,formatoDatos))

                columna = 27

                /*DESPLIEGUE DE RETENCIONES DE LEY*/
                def retencionesDeLeyLiquidacion = LiquidacionDeAntimonioRetenciones.findAllByLiquidacionDeAntimonioAndTipoDeRetencion(it,"DE LEY")
                def numretDeLey = retencionesDeLeyLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretDeLey} RETENCIONES DE LEY!")
                def subtotalRetencionesDeLey=it.regaliaMinera.doubleValue()
                for(int i=0;i<listaRetencionesDeLey.size();i++){
                    def vr = valorRetencion(listaRetencionesDeLey.get(i), retencionesDeLeyLiquidacion,numretDeLey)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesDeLey+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesDeLey,formatoDatos))
                columna++

                /*DESPLIEGUE DE RETENCIONES DE LEY*/
                def retencionesOtrasLiquidacion = LiquidacionDeAntimonioRetenciones.findAllByLiquidacionDeAntimonioAndTipoDeRetencion(it,"OTRA")
                def numretOtras = retencionesOtrasLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretOtras} RETENCIONES DE LEY!")
                def subtotalRetencionesOtras=0
                for(int i=0;i<listaRetencionesOtras.size();i++){
                    def vr = valorRetencion(listaRetencionesOtras.get(i), retencionesOtrasLiquidacion,numretOtras)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesOtras+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesOtras,formatoDatos))
                columna++

                sheet1.addCell(new Number(columna,fila, it.totalRetenciones,formatoDatos))
                sheet1.addCell(new Number(columna+1,fila, it.totalPagado,formatoDatos))
                sheet1.addCell(new Number(columna+2,fila, it.totalAnticiposContraEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+3,fila, it.totalAnticiposContraFuturaEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+4,fila, it.totalLiquidoPagable,formatoDatos))
                sheet1.addCell(new Number(columna+5,fila, it.recepcionDeAntimonio.costoDeTransporte,formatoDatos))
                sheet1.addCell(new Number(columna+6,fila, it.totalCostoLaboratorio,formatoDatos))

                fila++

                if (cuotaParticipacionEmpresa.contains(it.nombreEmpresa)){
                    def obj=cuotaParticipacionCuota.get(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa))
                    obj++
                    cuotaParticipacionCuota.set(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa),obj)
                }else{
                    cuotaParticipacionEmpresa.add(it.nombreEmpresa)
                    cuotaParticipacionCuota.add(1)
                }

            }

            //IMPRESION DE TOTALES
            //sheet1.addCell(new Number(1,fila, totalCotizacionDiariaAntimonio/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(1,fila, totalKilosNetosSecosCotizacionDiaria/totalKilosNetosSecos,formatoDatos))
            sheet1.addCell(new Number(6,fila, totalCantidadSacos,formatoDatos))
            sheet1.addCell(new Number(8,fila, totalTotalTara,formatoDatos))
            sheet1.addCell(new Number(7,fila, totalPesoBruto,formatoDatos))
            sheet1.addCell(new Number(9,fila, totalKilosNetosHumedos,formatoDatos))
            //sheet1.addCell(new Number(10,fila, totalHumedad/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(10,fila, (totalKilosNetosSecos/totalKilosNetosHumedos*100-100)*-1,formatoDatos))
            sheet1.addCell(new Number(11,fila, totalKilosNetosSecos,formatoDatos))
            //sheet1.addCell(new Number(12,fila, totalPorcentajeAntimonio/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(12,fila, totalKilosFinosAntimonio/totalKilosNetosSecos*100000,formatoDatos))
            sheet1.addCell(new Number(13,fila, totalKilosFinosPlomo/totalKilosNetosSecos*100,formatoDatos))
            sheet1.addCell(new Number(14,fila, totalKilosFinosArsenico/totalKilosNetosSecos*100,formatoDatos))
            sheet1.addCell(new Number(15,fila, totalKilosFinosAntimonio,formatoDatos))
            sheet1.addCell(new Number(16,fila, totalKilosFinosPlomo,formatoDatos))
            sheet1.addCell(new Number(17,fila, totalKilosFinosArsenico,formatoDatos))
            sheet1.addCell(new Number(18,fila, totalCotizacionQuincenalAntimonio/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(19,fila, totalValorOficialBruto,formatoDatos))
            sheet1.addCell(new Number(20,fila, totalAlicuota/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(21,fila, totalValorNeto,formatoDatos))
            sheet1.addCell(new Number(22,fila, totalValorNetoBolivianos,formatoDatos))
            sheet1.addCell(new Number(23,fila, totalBonoCalidad,formatoDatos))
            sheet1.addCell(new Number(24,fila, totalBonoIncentivo,formatoDatos))
            sheet1.addCell(new Number(25,fila, totalValorDeCompra,formatoDatos))
            sheet1.addCell(new Number(26,fila, totalRegaliaMinera,formatoDatos))

            def columnaFinalRetenciones = 29+listaRetencionesDeLey.size()+listaRetencionesOtras.size()
            def totalLiquidaciones = liquidacionesAntimonio.size()
            for (int col=27;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =7;fil<totalLiquidaciones+7;fil++){
                    def valor = Double.parseDouble(sheet1.getWritableCell(col,fil).contents)
                    tret+=valor
                }
                sheet1.addCell(new Number(col,totalLiquidaciones+7, tret,formatoDatos))
            }

            sheet1.addCell(new Number(columnaFinalRetenciones,fila, totalTotalRetenciones,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+1,fila, totalTotalPagado,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+2,fila, totalTotalAnticiposContraEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+3,fila, totalTotalAnticiposContraFuturaEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+4,fila, totalTotalLiquidoPagable,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+5,fila, totalCostoDeTransporte,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+6,fila, totalTotalCostoLaboratorio,formatoDatos))

//            sheet1.mergeCells(24, 5, 25+listaRetencionesDeLey.size(), 5);
//            sheet1.mergeCells(26+listaRetencionesDeLey.size(), 5, 26+listaRetencionesDeLey.size()+listaRetencionesOtras.size(), 5);
//            sheet1.addCell(new Label(24,5, "RETENCIONES DE LEY",formatoEncabezado))
//            sheet1.addCell(new Label(26+listaRetencionesDeLey.size(),5, "OTRAS RETENCIONES",formatoEncabezado))

            sheet1.mergeCells(26, 5, 27+listaRetencionesDeLey.size(), 5);
            sheet1.mergeCells(28+listaRetencionesDeLey.size(), 5, 28+listaRetencionesDeLey.size()+listaRetencionesOtras.size(), 5);
            sheet1.addCell(new Label(26,5, "RETENCIONES DE LEY",formatoEncabezado))
            sheet1.addCell(new Label(28+listaRetencionesDeLey.size(),5, "OTRAS RETENCIONES",formatoEncabezado))

            //IMPRESION DE DISTRIBUCION PORCENTUAL
            sheet1.mergeCells(4, fila+3, 5, fila+3);
            sheet1.addCell(new Label(4,fila+3,"VOLUMEN DE PARTICIPACION POR EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(4,fila+4, "EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(5,fila+4, "PORCENTAJE",formatoEncabezado))
            for (int i=0;i<cuotaParticipacionEmpresa.size();i++){
                sheet1.addCell(new Label(4,fila+5+i, cuotaParticipacionEmpresa.get(i),formatoDatos))
                sheet1.addCell(new Number(5,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))
            }
            sheet1.addCell(new Label(4,fila+5+cuotaParticipacionEmpresa.size(),"TOTAL",formatoDatos))
            sheet1.addCell(new Number(5,fila+5+cuotaParticipacionEmpresa.size(),100.0,formatoDatos))
            //sheet1.addCell(new Number(5,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))

            workbook.write();
            workbook.close();
        }
    }

    def recrearReporteComplejo = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Hoja de Costo de Complejo", 0)
        sheet1.setColumnView(0,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(1,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(2,11)
        sheet1.setColumnView(3,11)
        sheet1.setColumnView(4,11)
        sheet1.setColumnView(5,40)
        sheet1.setColumnView(6,40)
        sheet1.setRowView(6,500)
        for(i in 7..100)
            sheet1.setColumnView(i,11)

        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD);
        WritableCellFormat formatoEncabezado = new WritableCellFormat (arial10BoldFont);
        formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)
        formatoEncabezado.setWrap(true)
        formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
        //formatoEncabezado.setAlignment(Alignment.CENTRE)
        WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoDatos.setFont(courier8PlainFont)
        formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)
        WritableCellFormat formatoInfoReporte = new WritableCellFormat (arial14BoldFont);
        WritableCellFormat formatoTitulo = new WritableCellFormat (arial16BoldFont);
        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="hoja_costo_complejo_reimpresion.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(3,0, "HOJA DE COSTO DE COMPLEJO",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE

        def reporteHojaDeCosto = ReporteHojaDeCosto.get(Integer.parseInt(params.hcid))
        def nombreDelConjunto = reporteHojaDeCosto.nombreDelConjunto

        sheet1.addCell(new Label(3,1, "NOMBRE DEL CONJUNTO:",formatoInfoReporte))
        sheet1.addCell(new Label(4,1, "${reporteHojaDeCosto.nombreDelConjunto}",formatoInfoReporte))
        sheet1.addCell(new Label(3,2, "DESTINO:",formatoInfoReporte))
        sheet1.addCell(new Label(4,2, "${reporteHojaDeCosto.destinoDelConjunto}",formatoInfoReporte))

        if (reporteHojaDeCosto.empresa){
            sheet1.addCell(new Label(3,3, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(4,3, "${reporteHojaDeCosto.empresa.toString()}",formatoInfoReporte))
        }

        if (!reporteHojaDeCosto.fechaInicial)
            sheet1.addCell(new Label(5,1, "ENTRE LOTES: ${reporteHojaDeCosto.loteInicial} AL ${reporteHojaDeCosto.loteFinal}",formatoInfoReporte))
        else
            sheet1.addCell(new Label(5,1, "ENTRE FECHAS: ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(reporteHojaDeCosto.fechaInicial)} AL ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(reporteHojaDeCosto.fechaFinal)}",formatoInfoReporte))

        sheet1.addCell(new Label(7,2, "ENTRE LEYES: Zn: ${reporteHojaDeCosto.leyMinimaZincComplejo}% AL ${reporteHojaDeCosto.leyMaximaZincComplejo}% Pb: ${reporteHojaDeCosto.leyMinimaPlomoComplejo}% AL ${reporteHojaDeCosto.leyMaximaPlomoComplejo}% Ag: ${reporteHojaDeCosto.leyMinimaPlataComplejo}% AL ${reporteHojaDeCosto.leyMaximaPlataComplejo}%",formatoInfoReporte))

        def liquidacionesComplejo = LiquidacionDeComplejo.findAllByConjuntoComplejo(reporteHojaDeCosto.nombreDelConjunto)

        sheet1.addCell(new Label(0,6, "RECEPCION",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "COT. DIA Zn",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "COT. DIA Pb",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "COT. DIA Ag",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "LIQUIDACION",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "NOMBRE",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "MERMA",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "K. N. H.",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "% H2O",formatoEncabezado))
        sheet1.addCell(new Label(13,6, "K. N. S.",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "LEY %Zn",formatoEncabezado))
        sheet1.addCell(new Label(15,6, "LEY %Pb",formatoEncabezado))
        sheet1.addCell(new Label(16,6, "LEY DM Ag",formatoEncabezado))
        sheet1.addCell(new Label(17,6, "K. F. Zn",formatoEncabezado))
        sheet1.addCell(new Label(18,6, "K. F. Pb",formatoEncabezado))
        sheet1.addCell(new Label(19,6, "K. F. Ag",formatoEncabezado))
        sheet1.addCell(new Label(20,6, "COT. OFICIAL Zn",formatoEncabezado))
        sheet1.addCell(new Label(21,6, "COT. OFICIAL Pb",formatoEncabezado))
        sheet1.addCell(new Label(22,6, "COT. OFICIAL Ag",formatoEncabezado))
        sheet1.addCell(new Label(23,6, "VALOR OF. BRUTO",formatoEncabezado))
        sheet1.addCell(new Label(24,6, "ALICUOTA Zn %",formatoEncabezado))
        sheet1.addCell(new Label(25,6, "ALICUOTA Pb %",formatoEncabezado))
        sheet1.addCell(new Label(26,6, "ALICUOTA Ag %",formatoEncabezado))
        sheet1.addCell(new Label(27,6, "VALOR NETO \$us",formatoEncabezado))
        sheet1.addCell(new Label(28,6, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(29,6, "BONO CALIDAD",formatoEncabezado))
        sheet1.addCell(new Label(30,6, "BONO INCENTIVO",formatoEncabezado))
        sheet1.addCell(new Label(31,6, "VALOR DE COMPRA",formatoEncabezado))
        sheet1.addCell(new Label(32,6, "RM",formatoEncabezado))

        if (!liquidacionesComplejo) {
            flash.error = "NO SE PUDO OBTENER RESULTADOS!"
            System.out.println("*** SE ESTA PRODUCIENDO RESULTADOS NULL!!!")
            redirect(action: "create")
            return
        }

        if (liquidacionesComplejo.size()==0 || nombreDelConjunto.equals("")){
            if (liquidacionesComplejo.size()==0)
                sheet1.addCell(new Label(0,7, "SIN RESULTADOS",formatoInfoReporte))
            if (nombreDelConjunto.equals(""))
                sheet1.addCell(new Label(0,7, "ESPECIFIQUE NOMBRE DE CONJUNTO",formatoInfoReporte))
        }else{
            //DESPLIEGUE DE CABECERAS DE COLUMNA PARA RETENCIONES DE LEY
            def listaRetencionesDeLey = retencionesComplejoJSON liquidacionesComplejo,"DE LEY"
            def columna = 33
            listaRetencionesDeLey.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL RET. DE LEY",formatoEncabezado))
            columna++

            //DESPLIEGUE DE CABECERAS DE COLUMNA PARA OTRAS RETENCIONES
            def listaRetencionesOtras = retencionesComplejoJSON liquidacionesComplejo,"OTRA"
            listaRetencionesOtras.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL OTRAS RET.",formatoEncabezado))
            columna++

            System.out.println("NUMERO LIQUIDACIONES: ${liquidacionesComplejo.size()}")
            System.out.println("NUMERO RETENCIONES LEY: ${listaRetencionesDeLey.size()}")
            System.out.println("NUMERO OTRAS RETENCIONES: ${listaRetencionesOtras.size()}")

            sheet1.addCell(new Label(columna,6, "TOTAL RET.",formatoEncabezado))
            sheet1.addCell(new Label(columna+1,6, "TOTAL PAGADO",formatoEncabezado))
            sheet1.addCell(new Label(columna+2,6, "ANTICIPO/ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+3,6, "ANTICIPO/F. ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+4,6, "LIQUIDO PAGABLE",formatoEncabezado))
            sheet1.addCell(new Label(columna+5,6, "CANC. TRANSPORTE",formatoEncabezado))
            sheet1.addCell(new Label(columna+6,6, "CANC. LABORAT.",formatoEncabezado))

            //DESPLIEGUE DE DATOS DE LIQUIDACIONES
            //formatoEncabezado.setAlignment(Alignment.RIGHT)
            def fila = 7
            //variables acumuladoras
            def numeroRegistros=0
            def totalCotizacionDiariaZinc=0
            def totalCotizacionDiariaPlomo=0
            def totalCotizacionDiariaPlata=0
            def totalCantidadSacos=0
            def totalMerma=0
            def totalPesoBruto=0
            def totalKilosNetosHumedos=0
            def totalHumedad=0
            def totalKilosNetosSecos=0
            def totalPorcentajeZincFinal=0
            def totalPorcentajePlomoFinal=0
            def totalPorcentajePlataFinal=0
            def totalKilosFinosZinc=0
            def totalKilosFinosPlomo=0
            def totalKilosFinosPlata=0
            def totalCotizacionQuincenalZinc=0
            def totalCotizacionQuincenalPlomo=0
            def totalCotizacionQuincenalPlata=0
            def totalValorOficialBruto=0
            def totalAlicuotaZinc=0
            def totalAlicuotaPlomo=0
            def totalAlicuotaPlata=0
            def totalValorNeto=0
            def totalValorNetoBolivianos=0
            def totalBonoCalidad=0
            def totalBonoIncentivo=0
            def totalValorDeCompra=0
            def totalRegaliaMinera=0
            def totalTotalRetenciones=0
            def totalTotalPagado=0
            def totalTotalAnticiposContraEntrega=0
            def totalTotalAnticiposContraFuturaEntrega=0
            def totalTotalLiquidoPagable=0
            def totalCostoDeTransporte=0
            def totalTotalCostoLaboratorio=0

            def cuotaParticipacionEmpresa = new ArrayList<String>()
            def cuotaParticipacionCuota = new ArrayList<Integer>()

            liquidacionesComplejo.each {
                numeroRegistros++
                totalCotizacionDiariaZinc+=it.recepcionDeComplejo.cotizacionDiariaDeMinerales.zinc
                totalCotizacionDiariaPlomo+=it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plomo
                totalCotizacionDiariaPlata+=it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plata
                totalCantidadSacos+=Float.parseFloat(it.cantidadDeSacos.toString())
                totalPesoBruto+=it.pesoBruto
                totalMerma+=it.porcentajeMermaFinal
                //totalKilosNetosHumedos+=it.kilosNetosHumedos
                totalKilosNetosHumedos+=(it.pesoBruto-it.pesoBruto*it.porcentajeMermaFinal/100)
                totalHumedad+=it.porcentajeHumedadFinal
                totalKilosNetosSecos+=it.kilosNetosSecos
                totalPorcentajeZincFinal+=it.porcentajeZincFinal
                totalPorcentajePlomoFinal+=it.porcentajePlomoFinal
                totalPorcentajePlataFinal+=it.porcentajePlataFinal
                totalKilosFinosZinc+=it.kilosFinosZinc
                totalKilosFinosPlomo+=it.kilosFinosPlomo
                totalKilosFinosPlata+=it.kilosFinosPlata
                totalCotizacionQuincenalZinc+=it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.zinc
                totalCotizacionQuincenalPlomo+=it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plomo
                totalCotizacionQuincenalPlata+=it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plata
                totalValorOficialBruto+=it.valorOficialBruto
                totalAlicuotaZinc+=it.recepcionDeComplejo.alicuota.zinc
                totalAlicuotaPlomo+=it.recepcionDeComplejo.alicuota.plomo
                totalAlicuotaPlata+=it.recepcionDeComplejo.alicuota.plata
                totalValorNeto+=it.valorNetoMineral
                totalValorNetoBolivianos+=it.valorNetoMineralEnBolivianos
                totalBonoCalidad+=it.bonoCalidad
                totalBonoIncentivo+=it.bonoIncentivo
                totalValorDeCompra+=it.valorDeCompra
                totalRegaliaMinera+=it.regaliaMinera
                totalTotalRetenciones+=it.totalRetenciones
                totalTotalPagado+=it.totalPagado
                totalTotalAnticiposContraEntrega+=it.totalAnticiposContraEntrega
                totalTotalAnticiposContraFuturaEntrega+=it.totalAnticiposContraFuturaEntrega
                totalTotalLiquidoPagable=totalTotalLiquidoPagable+((it.totalLiquidoPagable.doubleValue()<0)?0:it.totalLiquidoPagable.doubleValue())
                totalCostoDeTransporte+=it.recepcionDeComplejo.costoDeTransporte
                totalTotalCostoLaboratorio+=it.totalCostoLaboratorio

                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new Number(1,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.zinc,formatoDatos))
                sheet1.addCell(new Number(2,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plomo,formatoDatos))
                sheet1.addCell(new Number(3,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plata,formatoDatos))
                sheet1.addCell(new DateTime(4,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(5,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(6,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(7,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(8,fila, Float.parseFloat(it.cantidadDeSacos),formatoDatos))
                sheet1.addCell(new Number(9,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(10,fila, it.porcentajeMermaFinal,formatoDatos))
                //sheet1.addCell(new Number(11,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(11,fila, it.pesoBruto-it.pesoBruto*it.porcentajeMermaFinal/100,formatoDatos))
                sheet1.addCell(new Number(12,fila, it.porcentajeHumedadFinal,formatoDatos))
                sheet1.addCell(new Number(13,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(14,fila, it.porcentajeZincFinal,formatoDatos))
                sheet1.addCell(new Number(15,fila, it.porcentajePlomoFinal,formatoDatos))
                sheet1.addCell(new Number(16,fila, it.porcentajePlataFinal,formatoDatos))
                sheet1.addCell(new Number(17,fila, it.kilosFinosZinc,formatoDatos))
                sheet1.addCell(new Number(18,fila, it.kilosFinosPlomo,formatoDatos))
                sheet1.addCell(new Number(19,fila, it.kilosFinosPlata,formatoDatos))
                sheet1.addCell(new Number(20,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.zinc,formatoDatos))
                sheet1.addCell(new Number(21,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plomo,formatoDatos))
                sheet1.addCell(new Number(22,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plata,formatoDatos))
                sheet1.addCell(new Number(23,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(24,fila, it.recepcionDeComplejo.alicuota.zinc,formatoDatos))
                sheet1.addCell(new Number(25,fila, it.recepcionDeComplejo.alicuota.plomo,formatoDatos))
                sheet1.addCell(new Number(26,fila, it.recepcionDeComplejo.alicuota.plata,formatoDatos))
                sheet1.addCell(new Number(27,fila, it.valorNetoMineral,formatoDatos))
                sheet1.addCell(new Number(28,fila, it.valorNetoMineralEnBolivianos,formatoDatos))
                sheet1.addCell(new Number(29,fila, it.bonoCalidad,formatoDatos))
                sheet1.addCell(new Number(30,fila, it.bonoIncentivo,formatoDatos))
                sheet1.addCell(new Number(31,fila, it.valorDeCompra,formatoDatos))
                sheet1.addCell(new Number(32,fila, it.regaliaMinera,formatoDatos))

                columna = 33

                //DESPLIEGUE DE RETENCIONES DE LEY
                def retencionesDeLeyLiquidacion = LiquidacionDeComplejoRetenciones.findAllByLiquidacionDeComplejoAndTipoDeRetencion(it,"DE LEY")
                def numretDeLey = retencionesDeLeyLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretDeLey} RETENCIONES DE LEY!")
                def subtotalRetencionesDeLey=it.regaliaMinera.doubleValue()
                for(int i=0;i<listaRetencionesDeLey.size();i++){
                    def vr = valorRetencion(listaRetencionesDeLey.get(i), retencionesDeLeyLiquidacion,numretDeLey)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesDeLey+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesDeLey,formatoDatos))
                columna++

                //DESPLIEGUE DE RETENCIONES DE LEY
                def retencionesOtrasLiquidacion = LiquidacionDeComplejoRetenciones.findAllByLiquidacionDeComplejoAndTipoDeRetencion(it,"OTRA")
                def numretOtras = retencionesOtrasLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretOtras} RETENCIONES DE LEY!")
                def subtotalRetencionesOtras=0
                for(int i=0;i<listaRetencionesOtras.size();i++){
                    def vr = valorRetencion(listaRetencionesOtras.get(i), retencionesOtrasLiquidacion,numretOtras)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesOtras+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesOtras,formatoDatos))
                columna++

                sheet1.addCell(new Number(columna,fila, it.totalRetenciones,formatoDatos))
                sheet1.addCell(new Number(columna+1,fila, it.totalPagado,formatoDatos))
                sheet1.addCell(new Number(columna+2,fila, it.totalAnticiposContraEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+3,fila, it.totalAnticiposContraFuturaEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+4,fila, it.totalLiquidoPagable,formatoDatos))
                sheet1.addCell(new Number(columna+5,fila, it.recepcionDeComplejo.costoDeTransporte,formatoDatos))
                sheet1.addCell(new Number(columna+6,fila, it.totalCostoLaboratorio,formatoDatos))

                fila++

                if (cuotaParticipacionEmpresa.contains(it.nombreEmpresa)){
                    def obj=cuotaParticipacionCuota.get(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa))
                    obj++
                    cuotaParticipacionCuota.set(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa),obj)
                }else{
                    cuotaParticipacionEmpresa.add(it.nombreEmpresa)
                    cuotaParticipacionCuota.add(1)
                }

            }

            //IMPRESION DE TOTALES
            sheet1.addCell(new Number(1,fila, totalCotizacionDiariaZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(2,fila, totalCotizacionDiariaPlomo/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(3,fila, totalCotizacionDiariaPlata/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(8,fila, totalCantidadSacos,formatoDatos))
            sheet1.addCell(new Number(9,fila, totalPesoBruto,formatoDatos))
            sheet1.addCell(new Number(10,fila, totalMerma/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(11,fila, totalKilosNetosHumedos,formatoDatos))
            //sheet1.addCell(new Number(12,fila, totalHumedad/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(12,fila, (totalKilosNetosSecos/totalKilosNetosHumedos*100-100)*-1,formatoDatos))
            sheet1.addCell(new Number(13,fila, totalKilosNetosSecos,formatoDatos))
            //sheet1.addCell(new Number(14,fila, totalPorcentajeZincFinal/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(14,fila, totalKilosFinosZinc/totalKilosNetosSecos*100,formatoDatos))
            //sheet1.addCell(new Number(15,fila, totalPorcentajePlomoFinal/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(15,fila, totalKilosFinosPlomo/totalKilosNetosSecos*100,formatoDatos))
            //sheet1.addCell(new Number(16,fila, totalPorcentajePlataFinal/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(16,fila, totalKilosFinosPlata/totalKilosNetosSecos*10000,formatoDatos))
            sheet1.addCell(new Number(17,fila, totalKilosFinosZinc,formatoDatos))
            sheet1.addCell(new Number(18,fila, totalKilosFinosPlomo,formatoDatos))
            sheet1.addCell(new Number(19,fila, totalKilosFinosPlata,formatoDatos))
            sheet1.addCell(new Number(20,fila, totalCotizacionQuincenalZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(21,fila, totalCotizacionQuincenalZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(22,fila, totalCotizacionQuincenalZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(23,fila, totalValorOficialBruto,formatoDatos))
            sheet1.addCell(new Number(24,fila, totalAlicuotaZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(25,fila, totalAlicuotaPlomo/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(26,fila, totalAlicuotaPlata/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(27,fila, totalValorNeto,formatoDatos))
            sheet1.addCell(new Number(28,fila, totalValorNetoBolivianos,formatoDatos))
            sheet1.addCell(new Number(29,fila, totalBonoCalidad,formatoDatos))
            sheet1.addCell(new Number(30,fila, totalBonoIncentivo,formatoDatos))
            sheet1.addCell(new Number(31,fila, totalValorDeCompra,formatoDatos))
            sheet1.addCell(new Number(32,fila, totalRegaliaMinera,formatoDatos))

            def columnaFinalRetenciones = 35+listaRetencionesDeLey.size()+listaRetencionesOtras.size()
            def totalLiquidaciones = liquidacionesComplejo.size()
            for (int col=33;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =7;fil<totalLiquidaciones+7;fil++){
                    def valor = Double.parseDouble(sheet1.getWritableCell(col,fil).contents)
                    tret+=valor
                }
                sheet1.addCell(new Number(col,totalLiquidaciones+7, tret,formatoDatos))
            }

            sheet1.addCell(new Number(columnaFinalRetenciones,fila, totalTotalRetenciones,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+1,fila, totalTotalPagado,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+2,fila, totalTotalAnticiposContraEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+3,fila, totalTotalAnticiposContraFuturaEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+4,fila, totalTotalLiquidoPagable,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+5,fila, totalCostoDeTransporte,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+6,fila, totalTotalCostoLaboratorio,formatoDatos))

            sheet1.mergeCells(33, 5, 33+listaRetencionesDeLey.size(), 5);
            sheet1.mergeCells(34+listaRetencionesDeLey.size(), 5, 34+listaRetencionesDeLey.size()+listaRetencionesOtras.size(), 5);
            sheet1.addCell(new Label(33,5, "RETENCIONES DE LEY",formatoEncabezado))
            sheet1.addCell(new Label(34+listaRetencionesDeLey.size(),5, "OTRAS RETENCIONES",formatoEncabezado))

            //IMPRESION DE DISTRIBUCION PORCENTUAL
            sheet1.mergeCells(6, fila+3, 7, fila+3);
            sheet1.addCell(new Label(6,fila+3,"VOLUMEN DE PARTICIPACION POR EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(6,fila+4, "EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(7,fila+4, "PORCENTAJE",formatoEncabezado))
            for (int i=0;i<cuotaParticipacionEmpresa.size();i++){
                sheet1.addCell(new Label(6,fila+5+i, cuotaParticipacionEmpresa.get(i),formatoDatos))
                sheet1.addCell(new Number(7,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))
            }
            sheet1.addCell(new Label(6,fila+5+cuotaParticipacionEmpresa.size(),"TOTAL",formatoDatos))
            sheet1.addCell(new Number(7,fila+5+cuotaParticipacionEmpresa.size(),100.0,formatoDatos))
            //sheet1.addCell(new Number(5,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))

            workbook.write();
            workbook.close();
        }
    }

    def recrearReportePlomoPlata = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Hoja de Costo de Plomo Plata", 0)
        sheet1.setColumnView(0,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(1,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(2,11)
        sheet1.setColumnView(3,11)
        sheet1.setColumnView(4,11)
        sheet1.setColumnView(5,40)
        sheet1.setColumnView(6,40)
        sheet1.setRowView(6,500)
        for(i in 7..100)
            sheet1.setColumnView(i,11)

        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD);
        WritableCellFormat formatoEncabezado = new WritableCellFormat (arial10BoldFont);
        formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)
        formatoEncabezado.setWrap(true)
        formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
        //formatoEncabezado.setAlignment(Alignment.CENTRE)
        WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoDatos.setFont(courier8PlainFont)
        formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)
        WritableCellFormat formatoInfoReporte = new WritableCellFormat (arial14BoldFont);
        WritableCellFormat formatoTitulo = new WritableCellFormat (arial16BoldFont);
        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="hoja_costo_plomo_plata_reimpresion.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(3,0, "HOJA DE COSTO DE PLOMO PLATA",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE

        def reporteHojaDeCosto = ReporteHojaDeCosto.get(Integer.parseInt(params.hcid))
        def nombreDelConjunto = reporteHojaDeCosto.nombreDelConjunto

        sheet1.addCell(new Label(3,1, "NOMBRE DEL CONJUNTO:",formatoInfoReporte))
        sheet1.addCell(new Label(4,1, "${reporteHojaDeCosto.nombreDelConjunto}",formatoInfoReporte))
        sheet1.addCell(new Label(3,2, "DESTINO:",formatoInfoReporte))
        sheet1.addCell(new Label(4,2, "${reporteHojaDeCosto.destinoDelConjunto}",formatoInfoReporte))

        if (reporteHojaDeCosto.empresa){
            sheet1.addCell(new Label(3,3, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(4,3, "${reporteHojaDeCosto.empresa.toString()}",formatoInfoReporte))
        }

        if (!reporteHojaDeCosto.fechaInicial)
            sheet1.addCell(new Label(5,1, "ENTRE LOTES: ${reporteHojaDeCosto.loteInicial} AL ${reporteHojaDeCosto.loteFinal}",formatoInfoReporte))
        else
            sheet1.addCell(new Label(5,1, "ENTRE FECHAS: ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(reporteHojaDeCosto.fechaInicial)} AL ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(reporteHojaDeCosto.fechaFinal)}",formatoInfoReporte))

        sheet1.addCell(new Label(7,2, "ENTRE LEYES: Pb: ${reporteHojaDeCosto.leyMinimaPlomoPlomoPlata}% AL ${reporteHojaDeCosto.leyMaximaPlomoPlomoPlata}% Ag: ${reporteHojaDeCosto.leyMinimaPlataPlomoPlata} DM AL ${reporteHojaDeCosto.leyMaximaPlataPlomoPlata} DM",formatoInfoReporte))

        def liquidacionesPlomoPlata = LiquidacionDePlomoPlata.findAllByConjuntoPlomoPlata(reporteHojaDeCosto.nombreDelConjunto)

        sheet1.addCell(new Label(0,6, "RECEPCION",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "COT. DIA Zn",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "COT. DIA Pb",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "COT. DIA Ag",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "LIQUIDACION",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "NOMBRE",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "MERMA",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "K. N. H.",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "% H2O",formatoEncabezado))
        sheet1.addCell(new Label(13,6, "K. N. S.",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "LEY %Zn",formatoEncabezado))
        sheet1.addCell(new Label(15,6, "LEY %Pb",formatoEncabezado))
        sheet1.addCell(new Label(16,6, "LEY DM Ag",formatoEncabezado))
        sheet1.addCell(new Label(17,6, "K. F. Zn",formatoEncabezado))
        sheet1.addCell(new Label(18,6, "K. F. Pb",formatoEncabezado))
        sheet1.addCell(new Label(19,6, "K. F. Ag",formatoEncabezado))
        sheet1.addCell(new Label(20,6, "COT. OFICIAL Zn",formatoEncabezado))
        sheet1.addCell(new Label(21,6, "COT. OFICIAL Pb",formatoEncabezado))
        sheet1.addCell(new Label(22,6, "COT. OFICIAL Ag",formatoEncabezado))
        sheet1.addCell(new Label(23,6, "VALOR OF. BRUTO",formatoEncabezado))
        sheet1.addCell(new Label(24,6, "ALICUOTA Zn %",formatoEncabezado))
        sheet1.addCell(new Label(25,6, "ALICUOTA Pb %",formatoEncabezado))
        sheet1.addCell(new Label(26,6, "ALICUOTA Ag %",formatoEncabezado))
        sheet1.addCell(new Label(27,6, "VALOR NETO \$us",formatoEncabezado))
        sheet1.addCell(new Label(28,6, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(29,6, "BONO CALIDAD",formatoEncabezado))
        sheet1.addCell(new Label(30,6, "BONO INCENTIVO",formatoEncabezado))
        sheet1.addCell(new Label(31,6, "VALOR DE COMPRA",formatoEncabezado))
        sheet1.addCell(new Label(32,6, "RM",formatoEncabezado))

        if (!liquidacionesPlomoPlata) {
            flash.error = "NO SE PUDO OBTENER RESULTADOS!"
            System.out.println("*** SE ESTA PRODUCIENDO RESULTADOS NULL!!!")
            redirect(action: "create")
            return
        }

        if (liquidacionesPlomoPlata.size()==0 || nombreDelConjunto.equals("")){
            if (liquidacionesPlomoPlata.size()==0)
                sheet1.addCell(new Label(0,7, "SIN RESULTADOS",formatoInfoReporte))
            if (nombreDelConjunto.equals(""))
                sheet1.addCell(new Label(0,7, "ESPECIFIQUE NOMBRE DE CONJUNTO",formatoInfoReporte))
        }else{
            //DESPLIEGUE DE CABECERAS DE COLUMNA PARA RETENCIONES DE LEY
            def listaRetencionesDeLey = retencionesPlomoPlataJSON liquidacionesPlomoPlata,"DE LEY"
            def columna = 33
            listaRetencionesDeLey.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL RET. DE LEY",formatoEncabezado))
            columna++

            //DESPLIEGUE DE CABECERAS DE COLUMNA PARA OTRAS RETENCIONES
            def listaRetencionesOtras = retencionesPlomoPlataJSON liquidacionesPlomoPlata,"OTRA"
            listaRetencionesOtras.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL OTRAS RET.",formatoEncabezado))
            columna++

            System.out.println("NUMERO LIQUIDACIONES: ${liquidacionesPlomoPlata.size()}")
            System.out.println("NUMERO RETENCIONES LEY: ${listaRetencionesDeLey.size()}")
            System.out.println("NUMERO OTRAS RETENCIONES: ${listaRetencionesOtras.size()}")

            sheet1.addCell(new Label(columna,6, "TOTAL RET.",formatoEncabezado))
            sheet1.addCell(new Label(columna+1,6, "TOTAL PAGADO",formatoEncabezado))
            sheet1.addCell(new Label(columna+2,6, "ANTICIPO/ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+3,6, "ANTICIPO/F. ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+4,6, "LIQUIDO PAGABLE",formatoEncabezado))
            sheet1.addCell(new Label(columna+5,6, "CANC. TRANSPORTE",formatoEncabezado))
            sheet1.addCell(new Label(columna+6,6, "CANC. LABORAT.",formatoEncabezado))

            //DESPLIEGUE DE DATOS DE LIQUIDACIONES
            //formatoEncabezado.setAlignment(Alignment.RIGHT)
            def fila = 7
            //variables acumuladoras
            def numeroRegistros=0
            def totalCotizacionDiariaZinc=0
            def totalCotizacionDiariaPlomo=0
            def totalCotizacionDiariaPlata=0
            def totalCantidadSacos=0
            def totalMerma=0
            def totalPesoBruto=0
            def totalKilosNetosHumedos=0
            def totalHumedad=0
            def totalKilosNetosSecos=0
            def totalPorcentajeZincFinal=0
            def totalPorcentajePlomoFinal=0
            def totalPorcentajePlataFinal=0
            def totalKilosFinosZinc=0
            def totalKilosFinosPlomo=0
            def totalKilosFinosPlata=0
            def totalCotizacionQuincenalZinc=0
            def totalCotizacionQuincenalPlomo=0
            def totalCotizacionQuincenalPlata=0
            def totalValorOficialBruto=0
            def totalAlicuotaZinc=0
            def totalAlicuotaPlomo=0
            def totalAlicuotaPlata=0
            def totalValorNeto=0
            def totalValorNetoBolivianos=0
            def totalBonoCalidad=0
            def totalBonoIncentivo=0
            def totalValorDeCompra=0
            def totalRegaliaMinera=0
            def totalTotalRetenciones=0
            def totalTotalPagado=0
            def totalTotalAnticiposContraEntrega=0
            def totalTotalAnticiposContraFuturaEntrega=0
            def totalTotalLiquidoPagable=0
            def totalCostoDeTransporte=0
            def totalTotalCostoLaboratorio=0

            def cuotaParticipacionEmpresa = new ArrayList<String>()
            def cuotaParticipacionCuota = new ArrayList<Integer>()

            liquidacionesPlomoPlata.each {
                numeroRegistros++
                totalCotizacionDiariaZinc+=it.recepcionDeComplejo.cotizacionDiariaDeMinerales.zinc
                totalCotizacionDiariaPlomo+=it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plomo
                totalCotizacionDiariaPlata+=it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plata
                totalCantidadSacos+=Float.parseFloat(it.cantidadDeSacos.toString())
                totalPesoBruto+=it.pesoBruto
                totalMerma+=it.porcentajeMermaFinal
                totalKilosNetosHumedos+=it.kilosNetosHumedos
                totalHumedad+=it.porcentajeHumedadFinal
                totalKilosNetosSecos+=it.kilosNetosSecos
                //totalPorcentajeZincFinal+=it.porcentajeZincFinal
                totalPorcentajePlomoFinal+=it.porcentajePlomoFinal
                totalPorcentajePlataFinal+=it.porcentajePlataFinal
                //totalKilosFinosZinc+=it.kilosFinosZinc
                totalKilosFinosPlomo+=it.kilosFinosPlomo
                totalKilosFinosPlata+=it.kilosFinosPlata
                totalCotizacionQuincenalZinc+=it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.zinc
                totalCotizacionQuincenalPlomo+=it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plomo
                totalCotizacionQuincenalPlata+=it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plata
                totalValorOficialBruto+=it.valorOficialBruto
                totalAlicuotaZinc+=it.recepcionDeComplejo.alicuota.zinc
                totalAlicuotaPlomo+=it.recepcionDeComplejo.alicuota.plomo
                totalAlicuotaPlata+=it.recepcionDeComplejo.alicuota.plata
                totalValorNeto+=it.valorNetoMineral
                totalValorNetoBolivianos+=it.valorNetoMineralEnBolivianos
                totalBonoCalidad+=it.bonoCalidad
                totalBonoIncentivo+=it.bonoIncentivo
                totalValorDeCompra+=it.valorDeCompra
                totalRegaliaMinera+=it.regaliaMinera
                totalTotalRetenciones+=it.totalRetenciones
                totalTotalPagado+=it.totalPagado
                totalTotalAnticiposContraEntrega+=it.totalAnticiposContraEntrega
                totalTotalAnticiposContraFuturaEntrega+=it.totalAnticiposContraFuturaEntrega
                totalTotalLiquidoPagable=totalTotalLiquidoPagable+((it.totalLiquidoPagable.doubleValue()<0)?0:it.totalLiquidoPagable.doubleValue())
                totalCostoDeTransporte+=it.recepcionDeComplejo.costoDeTransporte
                totalTotalCostoLaboratorio+=it.totalCostoLaboratorio

                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new Number(1,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.zinc,formatoDatos))
                sheet1.addCell(new Number(2,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plomo,formatoDatos))
                sheet1.addCell(new Number(3,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plata,formatoDatos))
                sheet1.addCell(new DateTime(4,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(5,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(6,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(7,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(8,fila, Float.parseFloat(it.cantidadDeSacos),formatoDatos))
                sheet1.addCell(new Number(9,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(10,fila, it.porcentajeMermaFinal,formatoDatos))
                sheet1.addCell(new Number(11,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(12,fila, it.porcentajeHumedadFinal,formatoDatos))
                sheet1.addCell(new Number(13,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(14,fila, 0,formatoDatos))
                sheet1.addCell(new Number(15,fila, it.porcentajePlomoFinal,formatoDatos))
                sheet1.addCell(new Number(16,fila, it.porcentajePlataFinal,formatoDatos))
                sheet1.addCell(new Number(17,fila, 0,formatoDatos))
                sheet1.addCell(new Number(18,fila, it.kilosFinosPlomo,formatoDatos))
                sheet1.addCell(new Number(19,fila, it.kilosFinosPlata,formatoDatos))
                sheet1.addCell(new Number(20,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.zinc,formatoDatos))
                sheet1.addCell(new Number(21,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plomo,formatoDatos))
                sheet1.addCell(new Number(22,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plata,formatoDatos))
                sheet1.addCell(new Number(23,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(24,fila, it.recepcionDeComplejo.alicuota.zinc,formatoDatos))
                sheet1.addCell(new Number(25,fila, it.recepcionDeComplejo.alicuota.plomo,formatoDatos))
                sheet1.addCell(new Number(26,fila, it.recepcionDeComplejo.alicuota.plata,formatoDatos))
                sheet1.addCell(new Number(27,fila, it.valorNetoMineral,formatoDatos))
                sheet1.addCell(new Number(28,fila, it.valorNetoMineralEnBolivianos,formatoDatos))
                sheet1.addCell(new Number(29,fila, it.bonoCalidad,formatoDatos))
                sheet1.addCell(new Number(30,fila, it.bonoIncentivo,formatoDatos))
                sheet1.addCell(new Number(31,fila, it.valorDeCompra,formatoDatos))
                sheet1.addCell(new Number(32,fila, it.regaliaMinera,formatoDatos))

                columna = 33

                //DESPLIEGUE DE RETENCIONES DE LEY
                def retencionesDeLeyLiquidacion = LiquidacionDePlomoPlataRetenciones.findAllByLiquidacionDePlomoPlataAndTipoDeRetencion(it,"DE LEY")
                def numretDeLey = retencionesDeLeyLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretDeLey} RETENCIONES DE LEY!")
                def subtotalRetencionesDeLey=it.regaliaMinera.doubleValue()
                for(int i=0;i<listaRetencionesDeLey.size();i++){
                    def vr = valorRetencion(listaRetencionesDeLey.get(i), retencionesDeLeyLiquidacion,numretDeLey)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesDeLey+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesDeLey,formatoDatos))
                columna++

                //DESPLIEGUE DE RETENCIONES DE LEY
                def retencionesOtrasLiquidacion = LiquidacionDePlomoPlataRetenciones.findAllByLiquidacionDePlomoPlataAndTipoDeRetencion(it,"OTRA")
                def numretOtras = retencionesOtrasLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretOtras} RETENCIONES DE LEY!")
                def subtotalRetencionesOtras=0
                for(int i=0;i<listaRetencionesOtras.size();i++){
                    def vr = valorRetencion(listaRetencionesOtras.get(i), retencionesOtrasLiquidacion,numretOtras)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesOtras+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesOtras,formatoDatos))
                columna++

                sheet1.addCell(new Number(columna,fila, it.totalRetenciones,formatoDatos))
                sheet1.addCell(new Number(columna+1,fila, it.totalPagado,formatoDatos))
                sheet1.addCell(new Number(columna+2,fila, it.totalAnticiposContraEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+3,fila, it.totalAnticiposContraFuturaEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+4,fila, it.totalLiquidoPagable,formatoDatos))
                sheet1.addCell(new Number(columna+5,fila, it.recepcionDeComplejo.costoDeTransporte,formatoDatos))
                sheet1.addCell(new Number(columna+6,fila, it.totalCostoLaboratorio,formatoDatos))

                fila++

                if (cuotaParticipacionEmpresa.contains(it.nombreEmpresa)){
                    def obj=cuotaParticipacionCuota.get(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa))
                    obj++
                    cuotaParticipacionCuota.set(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa),obj)
                }else{
                    cuotaParticipacionEmpresa.add(it.nombreEmpresa)
                    cuotaParticipacionCuota.add(1)
                }

            }

            //IMPRESION DE TOTALES
            sheet1.addCell(new Number(1,fila, totalCotizacionDiariaZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(2,fila, totalCotizacionDiariaPlomo/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(3,fila, totalCotizacionDiariaPlata/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(8,fila, totalCantidadSacos,formatoDatos))
            sheet1.addCell(new Number(9,fila, totalPesoBruto,formatoDatos))
            sheet1.addCell(new Number(10,fila, totalMerma/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(11,fila, totalKilosNetosHumedos,formatoDatos))
            //sheet1.addCell(new Number(12,fila, totalHumedad/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(12,fila, (totalKilosNetosSecos/totalKilosNetosHumedos*100-100)*-1,formatoDatos))
            sheet1.addCell(new Number(13,fila, totalKilosNetosSecos,formatoDatos))
            //sheet1.addCell(new Number(14,fila, totalPorcentajeZincFinal/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(14,fila, totalKilosFinosZinc/totalKilosNetosSecos*100,formatoDatos))
            //sheet1.addCell(new Number(15,fila, totalPorcentajePlomoFinal/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(15,fila, totalKilosFinosPlomo/totalKilosNetosSecos*100,formatoDatos))
            //sheet1.addCell(new Number(16,fila, totalPorcentajePlataFinal/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(16,fila, totalKilosFinosPlata/totalKilosNetosSecos*10000,formatoDatos))
            sheet1.addCell(new Number(17,fila, totalKilosFinosZinc,formatoDatos))
            sheet1.addCell(new Number(18,fila, totalKilosFinosPlomo,formatoDatos))
            sheet1.addCell(new Number(19,fila, totalKilosFinosPlata,formatoDatos))
            sheet1.addCell(new Number(20,fila, totalCotizacionQuincenalZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(21,fila, totalCotizacionQuincenalZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(22,fila, totalCotizacionQuincenalZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(23,fila, totalValorOficialBruto,formatoDatos))
            sheet1.addCell(new Number(24,fila, totalAlicuotaZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(25,fila, totalAlicuotaPlomo/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(26,fila, totalAlicuotaPlata/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(27,fila, totalValorNeto,formatoDatos))
            sheet1.addCell(new Number(28,fila, totalValorNetoBolivianos,formatoDatos))
            sheet1.addCell(new Number(29,fila, totalBonoCalidad,formatoDatos))
            sheet1.addCell(new Number(30,fila, totalBonoIncentivo,formatoDatos))
            sheet1.addCell(new Number(31,fila, totalValorDeCompra,formatoDatos))
            sheet1.addCell(new Number(32,fila, totalRegaliaMinera,formatoDatos))

            def columnaFinalRetenciones = 35+listaRetencionesDeLey.size()+listaRetencionesOtras.size()
            def totalLiquidaciones = liquidacionesPlomoPlata.size()
            for (int col=33;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =7;fil<totalLiquidaciones+7;fil++){
                    def valor = Double.parseDouble(sheet1.getWritableCell(col,fil).contents)
                    tret+=valor
                }
                sheet1.addCell(new Number(col,totalLiquidaciones+7, tret,formatoDatos))
            }

            sheet1.addCell(new Number(columnaFinalRetenciones,fila, totalTotalRetenciones,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+1,fila, totalTotalPagado,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+2,fila, totalTotalAnticiposContraEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+3,fila, totalTotalAnticiposContraFuturaEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+4,fila, totalTotalLiquidoPagable,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+5,fila, totalCostoDeTransporte,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+6,fila, totalTotalCostoLaboratorio,formatoDatos))

            sheet1.mergeCells(33, 5, 33+listaRetencionesDeLey.size(), 5);
            sheet1.mergeCells(34+listaRetencionesDeLey.size(), 5, 34+listaRetencionesDeLey.size()+listaRetencionesOtras.size(), 5);
            sheet1.addCell(new Label(33,5, "RETENCIONES DE LEY",formatoEncabezado))
            sheet1.addCell(new Label(34+listaRetencionesDeLey.size(),5, "OTRAS RETENCIONES",formatoEncabezado))

            //IMPRESION DE DISTRIBUCION PORCENTUAL
            sheet1.mergeCells(6, fila+3, 7, fila+3);
            sheet1.addCell(new Label(6,fila+3,"VOLUMEN DE PARTICIPACION POR EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(6,fila+4, "EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(7,fila+4, "PORCENTAJE",formatoEncabezado))
            for (int i=0;i<cuotaParticipacionEmpresa.size();i++){
                sheet1.addCell(new Label(6,fila+5+i, cuotaParticipacionEmpresa.get(i),formatoDatos))
                sheet1.addCell(new Number(7,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))
            }
            sheet1.addCell(new Label(6,fila+5+cuotaParticipacionEmpresa.size(),"TOTAL",formatoDatos))
            sheet1.addCell(new Number(7,fila+5+cuotaParticipacionEmpresa.size(),100.0,formatoDatos))
            //sheet1.addCell(new Number(5,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))

            sheet1.removeColumn(24)
            sheet1.removeColumn(20)
            sheet1.removeColumn(17)
            sheet1.removeColumn(14)
            sheet1.removeColumn(1)

            workbook.write();
            workbook.close();
        }
    }

    def recrearReporteZincPlata = {
        //INSTANCIACION DE HOJAS Y FORMATOS
        WritableWorkbook workbook = Workbook.createWorkbook(response.outputStream)
        WritableSheet sheet1 = workbook.createSheet("Hoja de Costo de Zinc Plata", 0)
        sheet1.setColumnView(0,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(1,11)//ajustar ancho de columnas (columna,ancho)
        sheet1.setColumnView(2,11)
        sheet1.setColumnView(3,11)
        sheet1.setColumnView(4,11)
        sheet1.setColumnView(5,40)
        sheet1.setColumnView(6,40)
        sheet1.setRowView(6,500)
        for(i in 7..100)
            sheet1.setColumnView(i,11)

        WritableFont arial10BoldFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.BOLD);
        WritableFont courier8PlainFont = new WritableFont(WritableFont.COURIER, 8, WritableFont.NO_BOLD);
        WritableFont arial14BoldFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
        WritableFont arial16BoldFont = new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD);
        WritableCellFormat formatoEncabezado = new WritableCellFormat (arial10BoldFont);
        formatoEncabezado.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.MEDIUM)
        formatoEncabezado.setWrap(true)
        formatoEncabezado.setVerticalAlignment(VerticalAlignment.CENTRE)
        //formatoEncabezado.setAlignment(Alignment.CENTRE)
        WritableCellFormat formatoDatos = new WritableCellFormat (new NumberFormat("###,##0.00"));
        formatoDatos.setFont(courier8PlainFont)
        formatoDatos.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)
        WritableCellFormat formatoInfoReporte = new WritableCellFormat (arial14BoldFont);
        WritableCellFormat formatoTitulo = new WritableCellFormat (arial16BoldFont);
        DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
        WritableCellFormat formatoFecha = new WritableCellFormat (customDateFormat);
        formatoFecha.setFont(courier8PlainFont)
        formatoFecha.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN)

        response.setContentType('application/vnd.ms-excel')
        response.setHeader('Content-Disposition', 'Attachment;Filename="hoja_costo_zinc_plata_reimpresion.xls"')
        //FIN-INSTANCIACION DE HOJAS Y FORMATOS

        sheet1.addCell(new Label(3,0, "HOJA DE COSTO DE ZINC PLATA",formatoTitulo))
        //VERIFICACION DEL TIPO DE REPORTE

        def reporteHojaDeCosto = ReporteHojaDeCosto.get(Integer.parseInt(params.hcid))
        def nombreDelConjunto = reporteHojaDeCosto.nombreDelConjunto

        sheet1.addCell(new Label(3,1, "NOMBRE DEL CONJUNTO:",formatoInfoReporte))
        sheet1.addCell(new Label(4,1, "${reporteHojaDeCosto.nombreDelConjunto}",formatoInfoReporte))
        sheet1.addCell(new Label(3,2, "DESTINO:",formatoInfoReporte))
        sheet1.addCell(new Label(4,2, "${reporteHojaDeCosto.destinoDelConjunto}",formatoInfoReporte))

        if (reporteHojaDeCosto.empresa){
            sheet1.addCell(new Label(3,3, "EMPRESA:",formatoInfoReporte))
            sheet1.addCell(new Label(4,3, "${reporteHojaDeCosto.empresa.toString()}",formatoInfoReporte))
        }

        if (!reporteHojaDeCosto.fechaInicial)
            sheet1.addCell(new Label(5,1, "ENTRE LOTES: ${reporteHojaDeCosto.loteInicial} AL ${reporteHojaDeCosto.loteFinal}",formatoInfoReporte))
        else
            sheet1.addCell(new Label(5,1, "ENTRE FECHAS: ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(reporteHojaDeCosto.fechaInicial)} AL ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(reporteHojaDeCosto.fechaFinal)}",formatoInfoReporte))

        sheet1.addCell(new Label(7,2, "ENTRE LEYES: Zn: ${reporteHojaDeCosto.leyMinimaZincZincPlata}% AL ${reporteHojaDeCosto.leyMaximaZincZincPlata}% Ag: ${reporteHojaDeCosto.leyMinimaPlataZincPlata}DM AL ${reporteHojaDeCosto.leyMaximaPlataZincPlata}DM",formatoInfoReporte))

        def liquidacionesZincPlata = LiquidacionDeZincPlata.findAllByConjuntoZincPlata(reporteHojaDeCosto.nombreDelConjunto)

        sheet1.addCell(new Label(0,6, "RECEPCION",formatoEncabezado))
        sheet1.addCell(new Label(1,6, "COT. DIA Zn",formatoEncabezado))
        sheet1.addCell(new Label(2,6, "COT. DIA Pb",formatoEncabezado))
        sheet1.addCell(new Label(3,6, "COT. DIA Ag",formatoEncabezado))
        sheet1.addCell(new Label(4,6, "LIQUIDACION",formatoEncabezado))
        sheet1.addCell(new Label(5,6, "RAZON SOCIAL PROVEEDOR",formatoEncabezado))
        sheet1.addCell(new Label(6,6, "NOMBRE",formatoEncabezado))
        sheet1.addCell(new Label(7,6, "LOTE",formatoEncabezado))
        sheet1.addCell(new Label(8,6, "SACOS",formatoEncabezado))
        sheet1.addCell(new Label(9,6, "P. BRUTO Kg",formatoEncabezado))
        sheet1.addCell(new Label(10,6, "MERMA",formatoEncabezado))
        sheet1.addCell(new Label(11,6, "K. N. H.",formatoEncabezado))
        sheet1.addCell(new Label(12,6, "% H2O",formatoEncabezado))
        sheet1.addCell(new Label(13,6, "K. N. S.",formatoEncabezado))
        sheet1.addCell(new Label(14,6, "LEY %Zn",formatoEncabezado))
        sheet1.addCell(new Label(15,6, "LEY %Pb",formatoEncabezado))
        sheet1.addCell(new Label(16,6, "LEY DM Ag",formatoEncabezado))
        sheet1.addCell(new Label(17,6, "K. F. Zn",formatoEncabezado))
        sheet1.addCell(new Label(18,6, "K. F. Pb",formatoEncabezado))
        sheet1.addCell(new Label(19,6, "K. F. Ag",formatoEncabezado))
        sheet1.addCell(new Label(20,6, "COT. OFICIAL Zn",formatoEncabezado))
        sheet1.addCell(new Label(21,6, "COT. OFICIAL Pb",formatoEncabezado))
        sheet1.addCell(new Label(22,6, "COT. OFICIAL Ag",formatoEncabezado))
        sheet1.addCell(new Label(23,6, "VALOR OF. BRUTO",formatoEncabezado))
        sheet1.addCell(new Label(24,6, "ALICUOTA Zn %",formatoEncabezado))
        sheet1.addCell(new Label(25,6, "ALICUOTA Pb %",formatoEncabezado))
        sheet1.addCell(new Label(26,6, "ALICUOTA Ag %",formatoEncabezado))
        sheet1.addCell(new Label(27,6, "VALOR NETO \$us",formatoEncabezado))
        sheet1.addCell(new Label(28,6, "VALOR NETO Bs",formatoEncabezado))
        sheet1.addCell(new Label(29,6, "BONO CALIDAD",formatoEncabezado))
        sheet1.addCell(new Label(30,6, "BONO INCENTIVO",formatoEncabezado))
        sheet1.addCell(new Label(31,6, "VALOR DE COMPRA",formatoEncabezado))
        sheet1.addCell(new Label(32,6, "RM",formatoEncabezado))

        if (!liquidacionesZincPlata) {
            flash.error = "NO SE PUDO OBTENER RESULTADOS!"
            System.out.println("*** SE ESTA PRODUCIENDO RESULTADOS NULL!!!")
            redirect(action: "create")
            return
        }

        if (liquidacionesZincPlata.size()==0 || nombreDelConjunto.equals("")){
            if (liquidacionesZincPlata.size()==0)
                sheet1.addCell(new Label(0,7, "SIN RESULTADOS",formatoInfoReporte))
            if (nombreDelConjunto.equals(""))
                sheet1.addCell(new Label(0,7, "ESPECIFIQUE NOMBRE DE CONJUNTO",formatoInfoReporte))
        }else{
            //DESPLIEGUE DE CABECERAS DE COLUMNA PARA RETENCIONES DE LEY
            def listaRetencionesDeLey = retencionesZincPlataJSON liquidacionesZincPlata,"DE LEY"
            def columna = 33
            listaRetencionesDeLey.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL RET. DE LEY",formatoEncabezado))
            columna++

            //DESPLIEGUE DE CABECERAS DE COLUMNA PARA OTRAS RETENCIONES
            def listaRetencionesOtras = retencionesZincPlataJSON liquidacionesZincPlata,"OTRA"
            listaRetencionesOtras.each {
                sheet1.addCell(new Label(columna,6,it.toString(),formatoEncabezado))
                columna++
            }
            sheet1.addCell(new Label(columna,6, "TOTAL OTRAS RET.",formatoEncabezado))
            columna++

            System.out.println("NUMERO LIQUIDACIONES: ${liquidacionesZincPlata.size()}")
            System.out.println("NUMERO RETENCIONES LEY: ${listaRetencionesDeLey.size()}")
            System.out.println("NUMERO OTRAS RETENCIONES: ${listaRetencionesOtras.size()}")

            sheet1.addCell(new Label(columna,6, "TOTAL RET.",formatoEncabezado))
            sheet1.addCell(new Label(columna+1,6, "TOTAL PAGADO",formatoEncabezado))
            sheet1.addCell(new Label(columna+2,6, "ANTICIPO/ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+3,6, "ANTICIPO/F. ENTREGA",formatoEncabezado))
            sheet1.addCell(new Label(columna+4,6, "LIQUIDO PAGABLE",formatoEncabezado))
            sheet1.addCell(new Label(columna+5,6, "CANC. TRANSPORTE",formatoEncabezado))
            sheet1.addCell(new Label(columna+6,6, "CANC. LABORAT.",formatoEncabezado))

            //DESPLIEGUE DE DATOS DE LIQUIDACIONES
            //formatoEncabezado.setAlignment(Alignment.RIGHT)
            def fila = 7
            //variables acumuladoras
            def numeroRegistros=0
            def totalCotizacionDiariaZinc=0
            def totalCotizacionDiariaPlomo=0
            def totalCotizacionDiariaPlata=0
            def totalCantidadSacos=0
            def totalMerma=0
            def totalPesoBruto=0
            def totalKilosNetosHumedos=0
            def totalHumedad=0
            def totalKilosNetosSecos=0
            def totalPorcentajeZincFinal=0
            def totalPorcentajePlomoFinal=0
            def totalPorcentajePlataFinal=0
            def totalKilosFinosZinc=0
            def totalKilosFinosPlomo=0
            def totalKilosFinosPlata=0
            def totalCotizacionQuincenalZinc=0
            def totalCotizacionQuincenalPlomo=0
            def totalCotizacionQuincenalPlata=0
            def totalValorOficialBruto=0
            def totalAlicuotaZinc=0
            def totalAlicuotaPlomo=0
            def totalAlicuotaPlata=0
            def totalValorNeto=0
            def totalValorNetoBolivianos=0
            def totalBonoCalidad=0
            def totalBonoIncentivo=0
            def totalValorDeCompra=0
            def totalRegaliaMinera=0
            def totalTotalRetenciones=0
            def totalTotalPagado=0
            def totalTotalAnticiposContraEntrega=0
            def totalTotalAnticiposContraFuturaEntrega=0
            def totalTotalLiquidoPagable=0
            def totalCostoDeTransporte=0
            def totalTotalCostoLaboratorio=0

            def cuotaParticipacionEmpresa = new ArrayList<String>()
            def cuotaParticipacionCuota = new ArrayList<Integer>()

            liquidacionesZincPlata.each {
                numeroRegistros++
                totalCotizacionDiariaZinc+=it.recepcionDeComplejo.cotizacionDiariaDeMinerales.zinc
                totalCotizacionDiariaPlomo+=it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plomo
                totalCotizacionDiariaPlata+=it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plata
                totalCantidadSacos+=Float.parseFloat(it.cantidadDeSacos.toString())
                totalPesoBruto+=it.pesoBruto
                totalMerma+=it.porcentajeMermaFinal
                totalKilosNetosHumedos+=it.kilosNetosHumedos
                totalHumedad+=it.porcentajeHumedadFinal
                totalKilosNetosSecos+=it.kilosNetosSecos
                totalPorcentajeZincFinal+=it.porcentajeZincFinal
                //totalPorcentajePlomoFinal+=it.porcentajePlomoFinal
                totalPorcentajePlataFinal+=it.porcentajePlataFinal
                totalKilosFinosZinc+=it.kilosFinosZinc
                //totalKilosFinosPlomo+=it.kilosFinosPlomo
                totalKilosFinosPlata+=it.kilosFinosPlata
                totalCotizacionQuincenalZinc+=it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.zinc
                totalCotizacionQuincenalPlomo+=it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plomo
                totalCotizacionQuincenalPlata+=it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plata
                totalValorOficialBruto+=it.valorOficialBruto
                totalAlicuotaZinc+=it.recepcionDeComplejo.alicuota.zinc
                totalAlicuotaPlomo+=it.recepcionDeComplejo.alicuota.plomo
                totalAlicuotaPlata+=it.recepcionDeComplejo.alicuota.plata
                totalValorNeto+=it.valorNetoMineral
                totalValorNetoBolivianos+=it.valorNetoMineralEnBolivianos
                totalBonoCalidad+=it.bonoCalidad
                totalBonoIncentivo+=it.bonoIncentivo
                totalValorDeCompra+=it.valorDeCompra
                totalRegaliaMinera+=it.regaliaMinera
                totalTotalRetenciones+=it.totalRetenciones
                totalTotalPagado+=it.totalPagado
                totalTotalAnticiposContraEntrega+=it.totalAnticiposContraEntrega
                totalTotalAnticiposContraFuturaEntrega+=it.totalAnticiposContraFuturaEntrega
                totalTotalLiquidoPagable=totalTotalLiquidoPagable+((it.totalLiquidoPagable.doubleValue()<0)?0:it.totalLiquidoPagable.doubleValue())
                totalCostoDeTransporte+=it.recepcionDeComplejo.costoDeTransporte
                totalTotalCostoLaboratorio+=it.totalCostoLaboratorio

                sheet1.addCell(new Label(0,fila, it.fechaDeRecepcion,formatoDatos))
                sheet1.addCell(new Number(1,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.zinc,formatoDatos))
                sheet1.addCell(new Number(2,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plomo,formatoDatos))
                sheet1.addCell(new Number(3,fila, it.recepcionDeComplejo.cotizacionDiariaDeMinerales.plata,formatoDatos))
                sheet1.addCell(new DateTime(4,fila, it.fechaDeLiquidacion,formatoFecha))
                sheet1.addCell(new Label(5,fila, it.nombreEmpresa,formatoDatos))
                sheet1.addCell(new Label(6,fila, it.nombreCliente,formatoDatos))
                sheet1.addCell(new Label(7,fila, it.lote,formatoDatos))
                sheet1.addCell(new Number(8,fila, Float.parseFloat(it.cantidadDeSacos),formatoDatos))
                sheet1.addCell(new Number(9,fila, it.pesoBruto,formatoDatos))
                sheet1.addCell(new Number(10,fila, it.porcentajeMermaFinal,formatoDatos))
                sheet1.addCell(new Number(11,fila, it.kilosNetosHumedos,formatoDatos))
                sheet1.addCell(new Number(12,fila, it.porcentajeHumedadFinal,formatoDatos))
                sheet1.addCell(new Number(13,fila, it.kilosNetosSecos,formatoDatos))
                sheet1.addCell(new Number(14,fila, it.porcentajeZincFinal,formatoDatos))
                sheet1.addCell(new Number(15,fila, 0,formatoDatos))
                sheet1.addCell(new Number(16,fila, it.porcentajePlataFinal,formatoDatos))
                sheet1.addCell(new Number(17,fila, it.kilosFinosZinc,formatoDatos))
                sheet1.addCell(new Number(18,fila, 0,formatoDatos))
                sheet1.addCell(new Number(19,fila, it.kilosFinosPlata,formatoDatos))
                sheet1.addCell(new Number(20,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.zinc,formatoDatos))
                sheet1.addCell(new Number(21,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plomo,formatoDatos))
                sheet1.addCell(new Number(22,fila, it.recepcionDeComplejo.cotizacionQuincenalDeMinerales.plata,formatoDatos))
                sheet1.addCell(new Number(23,fila, it.valorOficialBruto,formatoDatos))
                sheet1.addCell(new Number(24,fila, it.recepcionDeComplejo.alicuota.zinc,formatoDatos))
                sheet1.addCell(new Number(25,fila, it.recepcionDeComplejo.alicuota.plomo,formatoDatos))
                sheet1.addCell(new Number(26,fila, it.recepcionDeComplejo.alicuota.plata,formatoDatos))
                sheet1.addCell(new Number(27,fila, it.valorNetoMineral,formatoDatos))
                sheet1.addCell(new Number(28,fila, it.valorNetoMineralEnBolivianos,formatoDatos))
                sheet1.addCell(new Number(29,fila, it.bonoCalidad,formatoDatos))
                sheet1.addCell(new Number(30,fila, it.bonoIncentivo,formatoDatos))
                sheet1.addCell(new Number(31,fila, it.valorDeCompra,formatoDatos))
                sheet1.addCell(new Number(32,fila, it.regaliaMinera,formatoDatos))

                columna = 33

                //DESPLIEGUE DE RETENCIONES DE LEY
                def retencionesDeLeyLiquidacion = LiquidacionDeZincPlataRetenciones.findAllByLiquidacionDeZincPlataAndTipoDeRetencion(it,"DE LEY")
                def numretDeLey = retencionesDeLeyLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretDeLey} RETENCIONES DE LEY!")
                def subtotalRetencionesDeLey=it.regaliaMinera.doubleValue()
                for(int i=0;i<listaRetencionesDeLey.size();i++){
                    def vr = valorRetencion(listaRetencionesDeLey.get(i), retencionesDeLeyLiquidacion,numretDeLey)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesDeLey+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesDeLey,formatoDatos))
                columna++

                //DESPLIEGUE DE RETENCIONES DE LEY
                def retencionesOtrasLiquidacion = LiquidacionDeZincPlataRetenciones.findAllByLiquidacionDeZincPlataAndTipoDeRetencion(it,"OTRA")
                def numretOtras = retencionesOtrasLiquidacion.size()
                //System.out.println("*** ITERANDO SOBRE ${numretOtras} RETENCIONES DE LEY!")
                def subtotalRetencionesOtras=0
                for(int i=0;i<listaRetencionesOtras.size();i++){
                    def vr = valorRetencion(listaRetencionesOtras.get(i), retencionesOtrasLiquidacion,numretOtras)
                    sheet1.addCell(new Number(columna,fila, vr,formatoDatos))
                    subtotalRetencionesOtras+=vr
                    columna++
                }
                sheet1.addCell(new Number(columna,fila, subtotalRetencionesOtras,formatoDatos))
                columna++

                sheet1.addCell(new Number(columna,fila, it.totalRetenciones,formatoDatos))
                sheet1.addCell(new Number(columna+1,fila, it.totalPagado,formatoDatos))
                sheet1.addCell(new Number(columna+2,fila, it.totalAnticiposContraEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+3,fila, it.totalAnticiposContraFuturaEntrega,formatoDatos))
                sheet1.addCell(new Number(columna+4,fila, it.totalLiquidoPagable,formatoDatos))
                sheet1.addCell(new Number(columna+5,fila, it.recepcionDeComplejo.costoDeTransporte,formatoDatos))
                sheet1.addCell(new Number(columna+6,fila, it.totalCostoLaboratorio,formatoDatos))

                fila++

                if (cuotaParticipacionEmpresa.contains(it.nombreEmpresa)){
                    def obj=cuotaParticipacionCuota.get(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa))
                    obj++
                    cuotaParticipacionCuota.set(cuotaParticipacionEmpresa.indexOf(it.nombreEmpresa),obj)
                }else{
                    cuotaParticipacionEmpresa.add(it.nombreEmpresa)
                    cuotaParticipacionCuota.add(1)
                }

            }

            //IMPRESION DE TOTALES
            sheet1.addCell(new Number(1,fila, totalCotizacionDiariaZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(2,fila, totalCotizacionDiariaPlomo/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(3,fila, totalCotizacionDiariaPlata/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(8,fila, totalCantidadSacos,formatoDatos))
            sheet1.addCell(new Number(9,fila, totalPesoBruto,formatoDatos))
            sheet1.addCell(new Number(10,fila, totalMerma/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(11,fila, totalKilosNetosHumedos,formatoDatos))
            //sheet1.addCell(new Number(12,fila, totalHumedad/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(12,fila, (totalKilosNetosSecos/totalKilosNetosHumedos*100-100)*-1,formatoDatos))
            sheet1.addCell(new Number(13,fila, totalKilosNetosSecos,formatoDatos))
            //sheet1.addCell(new Number(14,fila, totalPorcentajeZincFinal/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(14,fila, totalKilosFinosZinc/totalKilosNetosSecos*100,formatoDatos))
            //sheet1.addCell(new Number(15,fila, totalPorcentajePlomoFinal/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(15,fila, totalKilosFinosPlomo/totalKilosNetosSecos*100,formatoDatos))
            //sheet1.addCell(new Number(16,fila, totalPorcentajePlataFinal/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(16,fila, totalKilosFinosPlata/totalKilosNetosSecos*10000,formatoDatos))
            sheet1.addCell(new Number(17,fila, totalKilosFinosZinc,formatoDatos))
            sheet1.addCell(new Number(18,fila, totalKilosFinosPlomo,formatoDatos))
            sheet1.addCell(new Number(19,fila, totalKilosFinosPlata,formatoDatos))
            sheet1.addCell(new Number(20,fila, totalCotizacionQuincenalZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(21,fila, totalCotizacionQuincenalZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(22,fila, totalCotizacionQuincenalZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(23,fila, totalValorOficialBruto,formatoDatos))
            sheet1.addCell(new Number(24,fila, totalAlicuotaZinc/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(25,fila, totalAlicuotaPlomo/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(26,fila, totalAlicuotaPlata/numeroRegistros,formatoDatos))
            sheet1.addCell(new Number(27,fila, totalValorNeto,formatoDatos))
            sheet1.addCell(new Number(28,fila, totalValorNetoBolivianos,formatoDatos))
            sheet1.addCell(new Number(29,fila, totalBonoCalidad,formatoDatos))
            sheet1.addCell(new Number(30,fila, totalBonoIncentivo,formatoDatos))
            sheet1.addCell(new Number(31,fila, totalValorDeCompra,formatoDatos))
            sheet1.addCell(new Number(32,fila, totalRegaliaMinera,formatoDatos))

            def columnaFinalRetenciones = 35+listaRetencionesDeLey.size()+listaRetencionesOtras.size()
            def totalLiquidaciones = liquidacionesZincPlata.size()
            for (int col=33;col<columnaFinalRetenciones;col++){
                def tret=0
                for (int fil =7;fil<totalLiquidaciones+7;fil++){
                    def valor = Double.parseDouble(sheet1.getWritableCell(col,fil).contents)
                    tret+=valor
                }
                sheet1.addCell(new Number(col,totalLiquidaciones+7, tret,formatoDatos))
            }

            sheet1.addCell(new Number(columnaFinalRetenciones,fila, totalTotalRetenciones,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+1,fila, totalTotalPagado,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+2,fila, totalTotalAnticiposContraEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+3,fila, totalTotalAnticiposContraFuturaEntrega,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+4,fila, totalTotalLiquidoPagable,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+5,fila, totalCostoDeTransporte,formatoDatos))
            sheet1.addCell(new Number(columnaFinalRetenciones+6,fila, totalTotalCostoLaboratorio,formatoDatos))

            sheet1.mergeCells(33, 5, 33+listaRetencionesDeLey.size(), 5);
            sheet1.mergeCells(34+listaRetencionesDeLey.size(), 5, 34+listaRetencionesDeLey.size()+listaRetencionesOtras.size(), 5);
            sheet1.addCell(new Label(33,5, "RETENCIONES DE LEY",formatoEncabezado))
            sheet1.addCell(new Label(34+listaRetencionesDeLey.size(),5, "OTRAS RETENCIONES",formatoEncabezado))

            //IMPRESION DE DISTRIBUCION PORCENTUAL
            sheet1.mergeCells(6, fila+3, 7, fila+3);
            sheet1.addCell(new Label(6,fila+3,"VOLUMEN DE PARTICIPACION POR EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(6,fila+4, "EMPRESA",formatoEncabezado))
            sheet1.addCell(new Label(7,fila+4, "PORCENTAJE",formatoEncabezado))
            for (int i=0;i<cuotaParticipacionEmpresa.size();i++){
                sheet1.addCell(new Label(6,fila+5+i, cuotaParticipacionEmpresa.get(i),formatoDatos))
                sheet1.addCell(new Number(7,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))
            }
            sheet1.addCell(new Label(6,fila+5+cuotaParticipacionEmpresa.size(),"TOTAL",formatoDatos))
            sheet1.addCell(new Number(7,fila+5+cuotaParticipacionEmpresa.size(),100.0,formatoDatos))
            //sheet1.addCell(new Number(5,fila+5+i, 100*cuotaParticipacionCuota.get(i)/numeroRegistros,formatoDatos))
            //25,21,18,15,2
            sheet1.removeColumn(25)
            sheet1.removeColumn(21)
            sheet1.removeColumn(18)
            sheet1.removeColumn(15)
            sheet1.removeColumn(2)

            workbook.write();
            workbook.close();
        }
    }
}
