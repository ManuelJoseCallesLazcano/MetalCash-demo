package org.socymet.cancelacion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.cotizaciones.CotizacionDeDolar
import org.socymet.liquidacion.LiquidacionDeCobrePlata
import org.socymet.liquidacion.LiquidacionDeComplejo
import org.socymet.liquidacion.LiquidacionDePlomoPlata
import org.socymet.liquidacion.LiquidacionDeZincPlata
import org.socymet.proveedor.Cliente
import org.socymet.proveedor.Cuadrilla
import org.socymet.proveedor.Empresa
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class PagoBonoCalidadController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [pagoBonoCalidadInstanceList: PagoBonoCalidad.list(params), pagoBonoCalidadInstanceTotal: PagoBonoCalidad.count()]
    }

    def create() {
        [pagoBonoCalidadInstance: new PagoBonoCalidad(params)]
    }

    def save() {
        def pagoBonoCalidadInstance = new PagoBonoCalidad(params)
        if (!pagoBonoCalidadInstance.save(flush: true)) {
            render(view: "create", model: [pagoBonoCalidadInstance: pagoBonoCalidadInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'pagoBonoCalidad.label', default: 'PagoBonoCalidad'), pagoBonoCalidadInstance.id])
        redirect(action: "show", id: pagoBonoCalidadInstance.id)
    }

    def show(Long id) {
        def pagoBonoCalidadInstance = PagoBonoCalidad.get(id)
        if (!pagoBonoCalidadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoBonoCalidad.label', default: 'PagoBonoCalidad'), id])
            redirect(action: "list")
            return
        }

        [pagoBonoCalidadInstance: pagoBonoCalidadInstance]
    }

    def edit(Long id) {
        def pagoBonoCalidadInstance = PagoBonoCalidad.get(id)
        if (!pagoBonoCalidadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoBonoCalidad.label', default: 'PagoBonoCalidad'), id])
            redirect(action: "list")
            return
        }

        [pagoBonoCalidadInstance: pagoBonoCalidadInstance]
    }

    def update(Long id, Long version) {
        def pagoBonoCalidadInstance = PagoBonoCalidad.get(id)
        if (!pagoBonoCalidadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoBonoCalidad.label', default: 'PagoBonoCalidad'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (pagoBonoCalidadInstance.version > version) {
                pagoBonoCalidadInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'pagoBonoCalidad.label', default: 'PagoBonoCalidad')] as Object[],
                        "Another user has updated this PagoBonoCalidad while you were editing")
                render(view: "edit", model: [pagoBonoCalidadInstance: pagoBonoCalidadInstance])
                return
            }
        }

        pagoBonoCalidadInstance.properties = params

        if (!pagoBonoCalidadInstance.save(flush: true)) {
            render(view: "edit", model: [pagoBonoCalidadInstance: pagoBonoCalidadInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'pagoBonoCalidad.label', default: 'PagoBonoCalidad'), pagoBonoCalidadInstance.id])
        redirect(action: "show", id: pagoBonoCalidadInstance.id)
    }

    def delete(Long id) {
        def pagoBonoCalidadInstance = PagoBonoCalidad.get(id)
        if (!pagoBonoCalidadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoBonoCalidad.label', default: 'PagoBonoCalidad'), id])
            redirect(action: "list")
            return
        }

        try {
            pagoBonoCalidadInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'pagoBonoCalidad.label', default: 'PagoBonoCalidad'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'pagoBonoCalidad.label', default: 'PagoBonoCalidad'), id])
            redirect(action: "show", id: id)
        }
    }

    def acumulacionPorMesJSON() {
        def tipoSeleccion=params.tipoSeleccion.toString()
        def cliente=null
        def empresa=null
        def cuadrilla=null
        def numeroMesesPagables=params.numeroMesesPagables.toInteger()
        def fechaInicial = new Date().parse("yyyy-MM-dd",""+params.fechaInicial_year+"-"+params.fechaInicial_month+"-01")
        def fechaFinal = new Date().parse("yyyy-MM-dd",""+params.fechaFinal_year+"-"+params.fechaFinal_month+"-01")
        def cal = Calendar.getInstance()
        def fechaInicialCalendar = Calendar.getInstance()
        def fechaFinalCalendar = Calendar.getInstance()
        def leyMinimaPlata = params.leyMinimaPlata.toBigDecimal()

        fechaInicialCalendar.setTime(fechaInicial)
        fechaFinalCalendar.setTime(fechaFinal)

        def m1 = fechaInicialCalendar.get(Calendar.YEAR) * 12 + fechaInicialCalendar.get(Calendar.MONTH);
        def m2 = fechaFinalCalendar.get(Calendar.YEAR) * 12 + fechaFinalCalendar.get(Calendar.MONTH);
        def cantidadMeses = m2-m1+1

        def liquidacionesComplejo = null
        def liquidacionesPlomoPlata = null
        def liquidacionesZincPlata = null
        def liquidacionesCobrePlata = null
        def fechaClave = null
        def histograma = [:]
        def histogramaList = []
        def lotesParaBono = []
        def lote = [:]
        def mesesAcumulados = 0
        def cotizacionDeDolar = CotizacionDeDolar.findByActivo(1)
        def notificacion = ""
        if (tipoSeleccion.equals("INDIVIDUAL")&&!params.clienteId.toString().equals("null")){
            cliente = Cliente.get(params.clienteId.toString().toLong())
            empresa = cliente.empresa

            (0..cantidadMeses-1).each {
                cal.setTime(fechaInicial)
                cal.add(Calendar.MONTH,it)
                fechaClave = "${cal.get(Calendar.MONTH)+1}/${cal.get(Calendar.YEAR)}"
                histograma[fechaClave]=0
            }

            liquidacionesComplejo = LiquidacionDeComplejo.findAllByFechaDeLiquidacionBetweenAndEmpresaAndPorcentajePlataFinalGreaterThanEquals(fechaInicial,fechaFinal,empresa,leyMinimaPlata)
            liquidacionesPlomoPlata = LiquidacionDePlomoPlata.findAllByFechaDeLiquidacionBetweenAndEmpresaAndPorcentajePlataFinalGreaterThanEquals(fechaInicial,fechaFinal,empresa,leyMinimaPlata)
            liquidacionesZincPlata = LiquidacionDeZincPlata.findAllByFechaDeLiquidacionBetweenAndEmpresaAndPorcentajePlataFinalGreaterThanEquals(fechaInicial,fechaFinal,empresa,leyMinimaPlata)
            liquidacionesCobrePlata = LiquidacionDeCobrePlata.findAllByFechaDeLiquidacionBetweenAndEmpresaAndPorcentajePlataFinalGreaterThanEquals(fechaInicial,fechaFinal,empresa,leyMinimaPlata)

            if(liquidacionesComplejo){
                liquidacionesComplejo.each { liq ->
                    if(liq.recepcionDeComplejo.cliente.equals(cliente)){
                        cal.setTime(liq.fechaDeLiquidacion)
                        fechaClave = "${cal.get(Calendar.MONTH)+1}/${cal.get(Calendar.YEAR)}"
                        log.error("**** FECHA LIQ: ${liq.fechaDeLiquidacion} LOTE: ${liq.lote} KNS: ${liq.kilosNetosSecos} -> FECHA CLAVE: ${fechaClave}")
                        histograma[fechaClave]=histograma[fechaClave]+liq.kilosNetosSecos

                        lote = [:]
                        lote.put("lote",liq.lote)
                        lote.put("fechaDeLiquidacion",new java.text.SimpleDateFormat("dd/MM/yyyy").format(liq.fechaDeLiquidacion))
                        lote.put("nombreEmpresa",liq.nombreEmpresa)
                        lote.put("nombreCliente",liq.nombreCliente)
                        lote.put("kilosNetosSecos",liq.kilosNetosSecos)
                        lote.put("porcentajePlataFinal",liq.porcentajePlataFinal)
                        lote.put("totalLiquidoPagable",liq.totalLiquidoPagable)
                        lotesParaBono.add(lote)
                    }
                }
            }

            if(liquidacionesPlomoPlata){
                liquidacionesPlomoPlata.each { liq ->
                    if(liq.recepcionDeComplejo.cliente.equals(cliente)){
                        log.error("**** FECHA LIQ: ${liq.fechaDeLiquidacion} LOTE: ${liq.lote} KNS: ${liq.kilosNetosSecos}")
                        cal.setTime(liq.fechaDeLiquidacion)
                        fechaClave = "${cal.get(Calendar.MONTH)+1}/${cal.get(Calendar.YEAR)}"
                        histograma[fechaClave]=histograma[fechaClave]+liq.kilosNetosSecos

                        lote = [:]
                        lote.put("lote",liq.lote)
                        lote.put("fechaDeLiquidacion",new java.text.SimpleDateFormat("dd/MM/yyyy").format(liq.fechaDeLiquidacion))
                        lote.put("nombreEmpresa",liq.nombreEmpresa)
                        lote.put("nombreCliente",liq.nombreCliente)
                        lote.put("kilosNetosSecos",liq.kilosNetosSecos)
                        lote.put("porcentajePlataFinal",liq.porcentajePlataFinal)
                        lote.put("totalLiquidoPagable",liq.totalLiquidoPagable)
                        lotesParaBono.add(lote)
                    }
                }
            }

            if(liquidacionesZincPlata){
                liquidacionesZincPlata.each { liq ->
                    if(liq.recepcionDeComplejo.cliente.equals(cliente)){
                        log.error("**** FECHA LIQ: ${liq.fechaDeLiquidacion} LOTE: ${liq.lote} KNS: ${liq.kilosNetosSecos}")
                        cal.setTime(liq.fechaDeLiquidacion)
                        fechaClave = "${cal.get(Calendar.MONTH)+1}/${cal.get(Calendar.YEAR)}"
                        histograma[fechaClave]+=liq.kilosNetosSecos

                        lote = [:]
                        lote.put("lote",liq.lote)
                        lote.put("fechaDeLiquidacion",new java.text.SimpleDateFormat("dd/MM/yyyy").format(liq.fechaDeLiquidacion))
                        lote.put("nombreEmpresa",liq.nombreEmpresa)
                        lote.put("nombreCliente",liq.nombreCliente)
                        lote.put("kilosNetosSecos",liq.kilosNetosSecos)
                        lote.put("porcentajePlataFinal",liq.porcentajePlataFinal)
                        lote.put("totalLiquidoPagable",liq.totalLiquidoPagable)
                        lotesParaBono.add(lote)
                    }
                }
            }

            if(liquidacionesCobrePlata){
                liquidacionesCobrePlata.each { liq ->
                    if(liq.recepcionDeComplejo.cliente.equals(cliente)){
                        log.error("**** FECHA LIQ: ${liq.fechaDeLiquidacion} LOTE: ${liq.lote} KNS: ${liq.kilosNetosSecos}")
                        cal.setTime(liq.fechaDeLiquidacion)
                        fechaClave = "${cal.get(Calendar.MONTH)+1}/${cal.get(Calendar.YEAR)}"
                        histograma[fechaClave]+=liq.kilosNetosSecos

                        lote = [:]
                        lote.put("lote",liq.lote)
                        lote.put("fechaDeLiquidacion",new java.text.SimpleDateFormat("dd/MM/yyyy").format(liq.fechaDeLiquidacion))
                        lote.put("nombreEmpresa",liq.nombreEmpresa)
                        lote.put("nombreCliente",liq.nombreCliente)
                        lote.put("kilosNetosSecos",liq.kilosNetosSecos)
                        lote.put("porcentajePlataFinal",liq.porcentajePlataFinal)
                        lote.put("totalLiquidoPagable",liq.totalLiquidoPagable)
                        lotesParaBono.add(lote)
                    }
                }
            }

            cuadrilla = Cuadrilla.findByEmpresaAndNombreCuadrilla(empresa,cliente.cuadrilla)
            log.error("***** CUADRILLA EN INDIVIDUAL: $cuadrilla")
            def par = [:]
            def acumulacionBonoProduccion1 = null
            def acumulacionBonoProduccion2 = null
            def fecha = null
            mesesAcumulados = 0
            histograma.each{ k, v ->
                par = [:]

                fecha = new Date().parse("MM/yyyy",k.toString())
                acumulacionBonoProduccion1 = AcumulacionBonoProduccion.findByClienteAndFecha(cliente,fecha)
                acumulacionBonoProduccion2 = (cuadrilla)?AcumulacionBonoProduccion.findByCuadrillaAndFecha(cuadrilla,fecha):null
                log.error("***** AcumulacionBonoProduccion.findByClienteAndFecha(cliente,fecha) en $k: $acumulacionBonoProduccion1")
                log.error("***** AcumulacionBonoProduccion.findByCuadrillaAndFecha(cuadrilla,fecha) en $k: $acumulacionBonoProduccion2")

                if (!acumulacionBonoProduccion1&&!acumulacionBonoProduccion2){
                    par.put("fecha",k)
                    par.put("cantidadAcumulada",v)
                    histogramaList.add(par)
                }else{
                    log.error("****** ELIMINADO FECHA: ${k} - CANTIDAD: ${v}")
                    notificacion=notificacion+"PAGO ${((acumulacionBonoProduccion1)?"INDIVIDUAL":"POR CUADRILLA")}: Fecha: ${k} - Cantidad Kgs: ${v}\n"
                }
            }

            histogramaList.each {
                mesesAcumulados=(it.cantidadAcumulada==0)?mesesAcumulados:mesesAcumulados+1
            }
        }

        if (tipoSeleccion.equals("CUADRILLA")&&!params.empresaId.toString().equals("null")&&!params.cuadrillaId.toString().equals("null")){
            empresa = Empresa.get(params.empresaId.toString().toLong())
            cuadrilla = Cuadrilla.get(params.cuadrillaId.toString().toLong())
            def clientes = Cliente.findAllByCuadrillaAndEmpresa(cuadrilla.nombreCuadrilla,empresa)

            (0..cantidadMeses-1).each {
                cal.setTime(fechaInicial)
                cal.add(Calendar.MONTH,it)
                fechaClave = "${cal.get(Calendar.MONTH)+1}/${cal.get(Calendar.YEAR)}"
                histograma[fechaClave]=0
            }

            clientes.each { clienteDeCuadrilla ->
                log.error("******* cliente: ${clienteDeCuadrilla}")

                liquidacionesComplejo = LiquidacionDeComplejo.findAllByFechaDeLiquidacionBetweenAndEmpresaAndPorcentajePlataFinalGreaterThanEquals(fechaInicial,fechaFinal,empresa,leyMinimaPlata)
                liquidacionesPlomoPlata = LiquidacionDePlomoPlata.findAllByFechaDeLiquidacionBetweenAndEmpresaAndPorcentajePlataFinalGreaterThanEquals(fechaInicial,fechaFinal,empresa,leyMinimaPlata)
                liquidacionesZincPlata = LiquidacionDeZincPlata.findAllByFechaDeLiquidacionBetweenAndEmpresaAndPorcentajePlataFinalGreaterThanEquals(fechaInicial,fechaFinal,empresa,leyMinimaPlata)
                liquidacionesCobrePlata = LiquidacionDeCobrePlata.findAllByFechaDeLiquidacionBetweenAndEmpresaAndPorcentajePlataFinalGreaterThanEquals(fechaInicial,fechaFinal,empresa,leyMinimaPlata)

                if(liquidacionesComplejo){
                    liquidacionesComplejo.each { liq ->
                        if(liq.recepcionDeComplejo.cliente.equals(clienteDeCuadrilla)){
                            cal.setTime(liq.fechaDeLiquidacion)
                            fechaClave = "${cal.get(Calendar.MONTH)+1}/${cal.get(Calendar.YEAR)}"
                            log.error("**** FECHA LIQ: ${liq.fechaDeLiquidacion} LOTE: ${liq.lote} KNS: ${liq.kilosNetosSecos} -> FECHA CLAVE: ${fechaClave}")
                            histograma[fechaClave]=histograma[fechaClave]+liq.kilosNetosSecos

                            lote = [:]
                            lote.put("lote",liq.lote)
                            lote.put("fechaDeLiquidacion",new java.text.SimpleDateFormat("dd/MM/yyyy").format(liq.fechaDeLiquidacion))
                            lote.put("nombreEmpresa",liq.nombreEmpresa)
                            lote.put("nombreCliente",liq.nombreCliente)
                            lote.put("kilosNetosSecos",liq.kilosNetosSecos)
                            lote.put("porcentajePlataFinal",liq.porcentajePlataFinal)
                            lote.put("totalLiquidoPagable",liq.totalLiquidoPagable)
                            lotesParaBono.add(lote)
                        }
                    }
                }

                if(liquidacionesPlomoPlata){
                    liquidacionesPlomoPlata.each { liq ->
                        if(liq.recepcionDeComplejo.cliente.equals(clienteDeCuadrilla)){
                            log.error("**** FECHA LIQ: ${liq.fechaDeLiquidacion} LOTE: ${liq.lote} KNS: ${liq.kilosNetosSecos}")
                            cal.setTime(liq.fechaDeLiquidacion)
                            fechaClave = "${cal.get(Calendar.MONTH)+1}/${cal.get(Calendar.YEAR)}"
                            histograma[fechaClave]=histograma[fechaClave]+liq.kilosNetosSecos

                            lote = [:]
                            lote.put("lote",liq.lote)
                            lote.put("fechaDeLiquidacion",new java.text.SimpleDateFormat("dd/MM/yyyy").format(liq.fechaDeLiquidacion))
                            lote.put("nombreEmpresa",liq.nombreEmpresa)
                            lote.put("nombreCliente",liq.nombreCliente)
                            lote.put("kilosNetosSecos",liq.kilosNetosSecos)
                            lote.put("porcentajePlataFinal",liq.porcentajePlataFinal)
                            lote.put("totalLiquidoPagable",liq.totalLiquidoPagable)
                            lotesParaBono.add(lote)
                        }
                    }
                }

                if(liquidacionesZincPlata){
                    liquidacionesZincPlata.each { liq ->
                        if(liq.recepcionDeComplejo.cliente.equals(clienteDeCuadrilla)){
                            log.error("**** FECHA LIQ: ${liq.fechaDeLiquidacion} LOTE: ${liq.lote} KNS: ${liq.kilosNetosSecos}")
                            cal.setTime(liq.fechaDeLiquidacion)
                            fechaClave = "${cal.get(Calendar.MONTH)+1}/${cal.get(Calendar.YEAR)}"
                            histograma[fechaClave]+=liq.kilosNetosSecos

                            lote = [:]
                            lote.put("lote",liq.lote)
                            lote.put("fechaDeLiquidacion",new java.text.SimpleDateFormat("dd/MM/yyyy").format(liq.fechaDeLiquidacion))
                            lote.put("nombreEmpresa",liq.nombreEmpresa)
                            lote.put("nombreCliente",liq.nombreCliente)
                            lote.put("kilosNetosSecos",liq.kilosNetosSecos)
                            lote.put("porcentajePlataFinal",liq.porcentajePlataFinal)
                            lote.put("totalLiquidoPagable",liq.totalLiquidoPagable)
                            lotesParaBono.add(lote)
                        }
                    }
                }

                if(liquidacionesCobrePlata){
                    liquidacionesCobrePlata.each { liq ->
                        if(liq.recepcionDeComplejo.cliente.equals(clienteDeCuadrilla)){
                            log.error("**** FECHA LIQ: ${liq.fechaDeLiquidacion} LOTE: ${liq.lote} KNS: ${liq.kilosNetosSecos}")
                            cal.setTime(liq.fechaDeLiquidacion)
                            fechaClave = "${cal.get(Calendar.MONTH)+1}/${cal.get(Calendar.YEAR)}"
                            histograma[fechaClave]+=liq.kilosNetosSecos

                            lote = [:]
                            lote.put("lote",liq.lote)
                            lote.put("fechaDeLiquidacion",new java.text.SimpleDateFormat("dd/MM/yyyy").format(liq.fechaDeLiquidacion))
                            lote.put("nombreEmpresa",liq.nombreEmpresa)
                            lote.put("nombreCliente",liq.nombreCliente)
                            lote.put("kilosNetosSecos",liq.kilosNetosSecos)
                            lote.put("porcentajePlataFinal",liq.porcentajePlataFinal)
                            lote.put("totalLiquidoPagable",liq.totalLiquidoPagable)
                            lotesParaBono.add(lote)
                        }
                    }
                }
            }

            def par = [:]
            def fecha
            def acumulacionBonoProduccion1 = null
            def acumulacionBonoProduccion2 = null
            def acumulacionPorClientes = null
            mesesAcumulados = 0
            /*
            * verificar para el caso que se haya quiera hacer un pago con CUADRILLA teniendo ya pagos previos
            * validar en:
            * if (tipoSeleccion.equals("CUADRILLA")&&...)...
            * 1. verificar que como CUADRILLA  no exista en la tabla de acumulacion, si existe debe eliminarse del mapa
            * 2. verificar para cada cliente miembro de la cuadrilla que haya cobrado su bono, si ya ha cobrado debe restarse
            *    la cantidad acumulada ya pagada del acumulado en proceso en la fecha dada */

            histograma.each{ k, v ->
                par = [:]

                fecha = new Date().parse("MM/yyyy",k.toString())

                acumulacionPorClientes = new ArrayList<AcumulacionBonoProduccion>()

                acumulacionBonoProduccion2 = (cuadrilla)?AcumulacionBonoProduccion.findByCuadrillaAndFecha(cuadrilla,fecha):null
                log.error("***** AcumulacionBonoProduccion.findByClienteAndFecha(cliente,fecha) en $k: $acumulacionBonoProduccion1")
                log.error("***** AcumulacionBonoProduccion.findByCuadrillaAndFecha(cuadrilla,fecha) en $k: $acumulacionBonoProduccion2")
                clientes.each { client ->
                    acumulacionBonoProduccion1 = AcumulacionBonoProduccion.findByClienteAndFecha(client,fecha)
                    if (acumulacionBonoProduccion1)
                        acumulacionPorClientes.add(acumulacionBonoProduccion1)
                }
                if (acumulacionPorClientes.size()==0&&!acumulacionBonoProduccion2){
                    par.put("fecha",k)
                    par.put("cantidadAcumulada",v)
                    histogramaList.add(par)
                }else{
                    if(acumulacionPorClientes.size()>0){
                        par.put("fecha",k)
                        par.put("cantidadAcumulada",v-acumulacionPorClientes.cantidadAcumulada.sum())
                        histogramaList.add(par)
                        acumulacionPorClientes.each { apc ->
                            notificacion=notificacion+"PAGO INDIVIDUAL A ${apc.cliente}: Fecha: ${k} - Cantidad Kgs: ${v}\n"
                        }
                    }
                    if (acumulacionBonoProduccion2){
                        log.error("****** ELIMINADO FECHA: ${k} - CANTIDAD: ${v}")
                        notificacion=notificacion+"PAGO POR CUADRILLA: Fecha: ${k} - Cantidad Kgs: ${v}\n"
                    }
                }
            }

            histogramaList.each {
                mesesAcumulados=(it.cantidadAcumulada==0)?mesesAcumulados:mesesAcumulados+1
            }
        }

        render([
            acumulados: (histogramaList as JSON).toString(),
            lotes: (lotesParaBono as JSON).toString(),
            bonoPorTonelada: empresa.bonoProduccionPorTonelada,
            tipoDeCambio: cotizacionDeDolar.tipoDeCambioComercial,
            numeroMesesAcumulados: mesesAcumulados,
            notificacionCancelados: notificacion
        ] as JSON)
    }

    def nombreCobradorJSON() {
        def pagos=PagoBonoProduccion.withCriteria {
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
        def realPath = servletContext.getRealPath("/reports/images/")
        params.realPath=realPath+"/"
        chain(controller:'jasper',action:'index',model:[data:factura],params:params)
    }
}
