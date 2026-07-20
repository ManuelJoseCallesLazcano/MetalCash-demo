package org.socymet.cancelacion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.smart.parametros.ParametrosGenerales
import org.socymet.cotizaciones.CotizacionDeDolar
import org.socymet.liquidacion.LiquidacionDeCobrePlata
import org.socymet.liquidacion.LiquidacionDeComplejo
import org.socymet.liquidacion.LiquidacionDePlomoPlata
import org.socymet.liquidacion.LiquidacionDeZincPlata
import org.socymet.proveedor.Automovil
import org.socymet.proveedor.Cliente
import org.socymet.recepcion.RecepcionDeComplejo
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class PagoBonoTransporteController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [pagoBonoTransporteInstanceList: PagoBonoTransporte.list(params), pagoBonoTransporteInstanceTotal: PagoBonoTransporte.count()]
    }

    def create() {
        [pagoBonoTransporteInstance: new PagoBonoTransporte(params)]
    }

    def save() {
        def pagoBonoTransporteInstance = new PagoBonoTransporte(params)
        if (!pagoBonoTransporteInstance.save(flush: true)) {
            render(view: "create", model: [pagoBonoTransporteInstance: pagoBonoTransporteInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'pagoBonoTransporte.label', default: 'PagoBonoTransporte'), pagoBonoTransporteInstance.id])
        redirect(action: "show", id: pagoBonoTransporteInstance.id)
    }

    def show(Long id) {
        def pagoBonoTransporteInstance = PagoBonoTransporte.get(id)
        if (!pagoBonoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoBonoTransporte.label', default: 'PagoBonoTransporte'), id])
            redirect(action: "list")
            return
        }

        [pagoBonoTransporteInstance: pagoBonoTransporteInstance]
    }

    def edit(Long id) {
        def pagoBonoTransporteInstance = PagoBonoTransporte.get(id)
        if (!pagoBonoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoBonoTransporte.label', default: 'PagoBonoTransporte'), id])
            redirect(action: "list")
            return
        }

        [pagoBonoTransporteInstance: pagoBonoTransporteInstance]
    }

    def update(Long id, Long version) {
        def pagoBonoTransporteInstance = PagoBonoTransporte.get(id)
        if (!pagoBonoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoBonoTransporte.label', default: 'PagoBonoTransporte'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (pagoBonoTransporteInstance.version > version) {
                pagoBonoTransporteInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'pagoBonoTransporte.label', default: 'PagoBonoTransporte')] as Object[],
                        "Another user has updated this PagoBonoTransporte while you were editing")
                render(view: "edit", model: [pagoBonoTransporteInstance: pagoBonoTransporteInstance])
                return
            }
        }

        pagoBonoTransporteInstance.properties = params

        if (!pagoBonoTransporteInstance.save(flush: true)) {
            render(view: "edit", model: [pagoBonoTransporteInstance: pagoBonoTransporteInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'pagoBonoTransporte.label', default: 'PagoBonoTransporte'), pagoBonoTransporteInstance.id])
        redirect(action: "show", id: pagoBonoTransporteInstance.id)
    }

    def delete(Long id) {
        def pagoBonoTransporteInstance = PagoBonoTransporte.get(id)
        if (!pagoBonoTransporteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoBonoTransporte.label', default: 'PagoBonoTransporte'), id])
            redirect(action: "list")
            return
        }

        try {
            pagoBonoTransporteInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'pagoBonoTransporte.label', default: 'PagoBonoTransporte'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'pagoBonoTransporte.label', default: 'PagoBonoTransporte'), id])
            redirect(action: "show", id: id)
        }
    }

    def acumulacionPorMesJSON() {
        def automovil=null
        def empresa
        def numeroMesesPagables=params.numeroMesesPagables.toInteger()
        def fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-01")
        def fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-01")
        def cal = Calendar.getInstance()
        def fechaInicialCalendar = Calendar.getInstance()
        def fechaFinalCalendar = Calendar.getInstance()

        fechaInicialCalendar.setTime(fechaInicial)
        fechaFinalCalendar.setTime(fechaFinal)

        def m1 = fechaInicialCalendar.get(Calendar.YEAR) * 12 + fechaInicialCalendar.get(Calendar.MONTH);
        def m2 = fechaFinalCalendar.get(Calendar.YEAR) * 12 + fechaFinalCalendar.get(Calendar.MONTH);
        def cantidadMeses = m2-m1+1

        def recepciones
        def liquidacionComplejo = null
        def liquidacionPlomoPlata = null
        def liquidacionZincPlata = null
        def liquidacionCobrePlata = null
        def fechaClave = null
        def histograma = [:]
        def histogramaPagado = [:]
        def histogramaList = []
        def lotesParaBono = []
        def lote = [:]
        def mesesAcumulados = 0
        def parametrosGenerales = ParametrosGenerales.get(1)
        def cotizacionDeDolar = CotizacionDeDolar.findByActivo(1)
        def notificacion = ""
        def leyPlomo
        def leyZinc
        def bono
        def bonoTotal = 0
        def acumulacionBonoTransporte
        def pesoBrutoTotal = 0

        automovil = Automovil.get(params.automovilId.toString().toLong())
        
        recepciones = RecepcionDeComplejo.findAllByAutomovilAndFechaDeRecepcionBetweenAndEstadoDelLote(automovil,fechaInicial,fechaFinal,"LIQUIDADO")

        (0..cantidadMeses-1).each {
            cal.setTime(fechaInicial)
            cal.add(Calendar.MONTH,it)
            fechaClave = "${cal.get(Calendar.MONTH)+1}/${cal.get(Calendar.YEAR)}"
            histograma[fechaClave]=0
        }

        recepciones.each {recepcion ->
            cal.setTime(recepcion.fechaDeRecepcion)
            cal.set(Calendar.DAY_OF_MONTH,1)
            fechaClave = "${cal.get(Calendar.MONTH)+1}/${cal.get(Calendar.YEAR)}"

            acumulacionBonoTransporte = AcumulacionBonoTransporte.findByFecha(cal.getTime())

//            log.error("lote: ${recepcion.toString()} - empresa: ${recepcion.empresa} - fecha recepcion: ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion)} - fecha recepcion modificada: ${cal.getTime().format("dd/MM/yyyy")}")

            if(!acumulacionBonoTransporte){
                liquidacionComplejo = LiquidacionDeComplejo.findByRecepcionDeComplejo(recepcion)
                liquidacionPlomoPlata = LiquidacionDePlomoPlata.findByRecepcionDeComplejo(recepcion)
                liquidacionZincPlata = LiquidacionDeZincPlata.findByRecepcionDeComplejo(recepcion)
                liquidacionCobrePlata = LiquidacionDeCobrePlata.findByRecepcionDeComplejo(recepcion)               
                empresa = recepcion.empresa
                
                bono = 0
                if (liquidacionComplejo){
                    log.error("lote: ${liquidacionComplejo.lote} - peso bruto: ${liquidacionComplejo.recepcionDeComplejo.pesoBruto} - porcentajePlomoFinal: ${liquidacionComplejo.porcentajePlomoFinal} - porcentajeZincFinal: ${liquidacionComplejo.porcentajeZincFinal}")

                    leyPlomo = liquidacionComplejo.porcentajePlomoFinal
                    leyZinc = liquidacionComplejo.porcentajeZincFinal

                    if(leyPlomo>parametrosGenerales.leyAltaPlomoBonoTransporte||leyZinc>parametrosGenerales.leyAltaZincBonoTransporte){
                        bono = recepcion.pesoBruto*empresa.bonoMaximoTransportePorTonelada*cotizacionDeDolar.tipoDeCambioComercial/1000
                    }else{
                        if(leyPlomo>parametrosGenerales.leyBajaPlomoBonoTransporte||leyZinc>parametrosGenerales.leyBajaZincBonoTransporte){
                            bono = recepcion.pesoBruto*empresa.bonoMinimoTransportePorTonelada*cotizacionDeDolar.tipoDeCambioComercial/1000
                        }
                    }

                    if(bono>0){
                        bonoTotal += bono
                        //acumulando periodo-peso bruto acumulado
                        log.error("**** FECHA REC: ${recepcion.fechaDeRecepcion} LOTE: ${recepcion.toString()} KB: ${recepcion.pesoBruto} -> FECHA CLAVE: ${fechaClave}")
                        histograma[fechaClave]=histograma[fechaClave]+recepcion.pesoBruto
                        pesoBrutoTotal+=recepcion.pesoBruto
                        //datos del lote mas bono
                        lote = [:]
                        lote.put("lote",recepcion.toString())
                        lote.put("fechaDeRecepcion",new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
                        lote.put("nombreEmpresa",recepcion.empresa.toString())
                        lote.put("nombreCliente",recepcion.cliente.nombre)
                        lote.put("kilosBrutos",recepcion.pesoBruto)
                        lote.put("leyPlomo",leyPlomo)
                        lote.put("leyZinc",leyZinc)
                        lote.put("bono",bono)
                        lotesParaBono.add(lote)
                    }
                }

                bono = 0
                if (liquidacionPlomoPlata){
                    log.error("lote: ${liquidacionPlomoPlata.lote} - peso bruto: ${liquidacionPlomoPlata.recepcionDeComplejo.pesoBruto} - porcentajePlomoFinal: ${liquidacionPlomoPlata.porcentajePlomoFinal}")

                    leyPlomo = liquidacionPlomoPlata.porcentajePlomoFinal
                    leyZinc = 0

                    if(leyPlomo>parametrosGenerales.leyAltaPlomoBonoTransporte){
                        bono = recepcion.pesoBruto*empresa.bonoMaximoTransportePorTonelada*cotizacionDeDolar.tipoDeCambioComercial/1000
                    }else{
                        if(leyPlomo>parametrosGenerales.leyBajaPlomoBonoTransporte){
                            bono = recepcion.pesoBruto*empresa.bonoMinimoTransportePorTonelada*cotizacionDeDolar.tipoDeCambioComercial/1000
                        }
                    }

                    if(bono>0){
                        bonoTotal += bono
                        //acumulando periodo-peso bruto acumulado
                        log.error("**** FECHA REC: ${recepcion.fechaDeRecepcion} LOTE: ${recepcion.lotePlomoPlata} KB: ${recepcion.pesoBruto} -> FECHA CLAVE: ${fechaClave}")
                        histograma[fechaClave]=histograma[fechaClave]+recepcion.pesoBruto
                        pesoBrutoTotal+=recepcion.pesoBruto
                        //datos del lote mas bono
                        lote = [:]
                        lote.put("lote",recepcion.toString())
                        lote.put("fechaDeRecepcion",new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
                        lote.put("nombreEmpresa",recepcion.empresa.toString())
                        lote.put("nombreCliente",recepcion.cliente.nombre)
                        lote.put("kilosBrutos",recepcion.pesoBruto)
                        lote.put("leyPlomo",leyPlomo)
                        lote.put("leyZinc",leyZinc)
                        lote.put("bono",bono)
                        lotesParaBono.add(lote)
                    }
                }

                bono = 0
                if (liquidacionZincPlata){
                    log.error("lote: ${liquidacionZincPlata.lote} - peso bruto: ${liquidacionZincPlata.recepcionDeComplejo.pesoBruto} - porcentajeZincFinal: ${liquidacionZincPlata.porcentajeZincFinal}")

                    leyPlomo = 0
                    leyZinc = liquidacionZincPlata.porcentajeZincFinal

                    if(leyZinc>parametrosGenerales.leyAltaZincBonoTransporte){
                        bono = recepcion.pesoBruto*empresa.bonoMaximoTransportePorTonelada*cotizacionDeDolar.tipoDeCambioComercial/1000
                    }else{
                        if(leyZinc>parametrosGenerales.leyBajaZincBonoTransporte){
                            bono = recepcion.pesoBruto*empresa.bonoMinimoTransportePorTonelada*cotizacionDeDolar.tipoDeCambioComercial/1000
                        }
                    }

                    if(bono>0){
                        bonoTotal += bono
                        //acumulando periodo-peso bruto acumulado
                        log.error("**** FECHA REC: ${recepcion.fechaDeRecepcion} LOTE: ${recepcion.loteZincPlata} KB: ${recepcion.pesoBruto} -> FECHA CLAVE: ${fechaClave}")
                        histograma[fechaClave]=histograma[fechaClave]+recepcion.pesoBruto
                        pesoBrutoTotal+=recepcion.pesoBruto
                        //datos del lote mas bono
                        lote = [:]
                        lote.put("lote",recepcion.toString())
                        lote.put("fechaDeRecepcion",new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
                        lote.put("nombreEmpresa",recepcion.empresa.toString())
                        lote.put("nombreCliente",recepcion.cliente.nombre)
                        lote.put("kilosBrutos",recepcion.pesoBruto)
                        lote.put("leyPlomo",leyPlomo)
                        lote.put("leyZinc",leyZinc)
                        lote.put("bono",bono)
                        lotesParaBono.add(lote)
                    }
                }

                bono = 0
                if (liquidacionCobrePlata){
                    log.error("lote: ${liquidacionCobrePlata.lote} - peso bruto: ${liquidacionCobrePlata.recepcionDeComplejo.pesoBruto}")

                    leyPlomo = 0
                    leyZinc = 0

                    if(leyPlomo>parametrosGenerales.leyAltaPlomoBonoTransporte||leyZinc>parametrosGenerales.leyAltaZincBonoTransporte){
                        bono = recepcion.pesoBruto*empresa.bonoMaximoTransportePorTonelada*cotizacionDeDolar.tipoDeCambioComercial/1000
                    }else{
                        if(leyPlomo>parametrosGenerales.leyBajaPlomoBonoTransporte||leyZinc>parametrosGenerales.leyBajaZincBonoTransporte){
                            bono = recepcion.pesoBruto*empresa.bonoMinimoTransportePorTonelada*cotizacionDeDolar.tipoDeCambioComercial/1000
                        }
                    }

                    if(bono>0){
                        bonoTotal += bono
                        //acumulando periodo-peso bruto acumulado
                        log.error("**** FECHA REC: ${recepcion.fechaDeRecepcion} LOTE: ${recepcion.loteCobrePlata} KB: ${recepcion.pesoBruto} -> FECHA CLAVE: ${fechaClave}")
                        histograma[fechaClave]=histograma[fechaClave]+recepcion.pesoBruto
                        pesoBrutoTotal+=recepcion.pesoBruto
                        //datos del lote mas bono
                        lote = [:]
                        lote.put("lote",recepcion.toString())
                        lote.put("fechaDeRecepcion",new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
                        lote.put("nombreEmpresa",recepcion.empresa.toString())
                        lote.put("nombreCliente",recepcion.cliente.nombre)
                        lote.put("kilosBrutos",recepcion.pesoBruto)
                        lote.put("leyPlomo",leyPlomo)
                        lote.put("leyZinc",leyZinc)
                        lote.put("bono",bono)
                        lotesParaBono.add(lote)
                    }
                }
            }else{
                histogramaPagado[fechaClave]=acumulacionBonoTransporte.cantidadAcumulada
                log.error("acumulacion para ${fechaClave} encontrada!")
            }
        }

        def par = [:]
        def fecha
        mesesAcumulados = 0

        histograma.each{ k, v ->
            par = [:]
            par.put("fecha",k)
            par.put("cantidadAcumulada",v)
            histogramaList.add(par)
        }

        histogramaPagado.each{ k, v ->
            par = [:]

            fecha = new Date().parse("MM/yyyy",k.toString())
            notificacion=notificacion+"PAGADO: Fecha: ${k} - Cantidad Kgs: ${v}\n"
        }
        /*
        * se esta acumulando sobre histograma pero no se esta enviando de retorno el contenido.
        * deberia traspasarse a una lista.
        * para Complejo probar con la placa 2751-frf*/
        histogramaList.each {
            mesesAcumulados=(it.cantidadAcumulada==0)?mesesAcumulados:mesesAcumulados+1
        }

        render([
            acumulados: (histogramaList as JSON).toString(),
            lotes: (lotesParaBono as JSON).toString(),
            tipoDeCambio: cotizacionDeDolar.tipoDeCambioComercial,
            numeroMesesAcumulados: mesesAcumulados,
            pesoBruto: pesoBrutoTotal,
            totalPagable: bonoTotal,
            notificacionCancelados: notificacion
        ] as JSON)
    }

    def nombreCobradorJSON() {
        def pagos=PagoBonoTransporte.withCriteria {
            projections {
                distinct "ci"
                property("nombreCobrador")
            }
            like("ci","${params.term}%")
        }.sort()

        def pagosList = []
        pagos.each {
            def mapaClientes = [:]
            //parametros en JSON para JQuery UI Autocomplete
            mapaClientes.put("id",it[0])
            mapaClientes.put("label","${it[0]} - ${it[1]}") //son las cadenas que se muestran en la lista
            mapaClientes.put("value",it[0]) //es la cadena que se establece en el input despues de ser seleccionado
            mapaClientes.put("nombreCobrador",it[1])
            pagosList.add(mapaClientes)
        }
        render pagosList as JSON
    }

    def clientesPorNombreJSON() {
        //def clientes = Cliente.findAllByCiLike("%${params.term}%")
        def clientes = Cliente.findAllByNombreLike("${params.term}%")
        def tipoDeCambio = CotizacionDeDolar.findByActivo(1)
        def clientesList = []
        clientes.each {
            def mapaClientes = [:]
            //parametros en JSON para JQuery UI Autocomplete
            mapaClientes.put("id",it.id)
            //mapaClientes.put("label",it.ci) //son las cadenas que se muestran en la lista
            mapaClientes.put("label","${it.nombre}") //son las cadenas que se muestran en la lista
            mapaClientes.put("value",it.nombre) //es la cadena que se establece en el input despues de ser seleccionado
            //otros parametros
            mapaClientes.put("clienteId",it.id)
            mapaClientes.put("ciCliente",it.ci)
            mapaClientes.put("empresaId",it.empresa.id)
            mapaClientes.put("nombreEmpresa",it.empresa.toString())
            mapaClientes.put("bonoPorTonelada",it.empresa.bonoProduccionPorTonelada)
            mapaClientes.put("tipoDeCambio",tipoDeCambio.tipoDeCambioComercial)
            clientesList.add(mapaClientes)
        }
        render clientesList as JSON
    }

    def createReport = {
        def factura = PagoBonoProduccion.get(params.id)
        def realPath = org.socymet.util.ReportesRuntime.realPath("/reports/images/")
        params.realPath=realPath+"/"
        chain(controller:'jasper',action:'index',model:[data:factura],params:params)
    }
}
