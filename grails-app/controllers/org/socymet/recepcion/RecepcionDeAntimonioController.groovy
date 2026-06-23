package org.socymet.recepcion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject
import org.socymet.anticipos.AnticipoContraEntrega
import org.socymet.anticipos.EstadoDeCuenta
import org.socymet.proveedor.Cliente
import org.socymet.proveedor.Empresa
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class RecepcionDeAntimonioController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [recepcionDeAntimonioInstanceList: RecepcionDeAntimonio.list(params), recepcionDeAntimonioInstanceTotal: RecepcionDeAntimonio.count()]
    }

    def create() {
        [recepcionDeAntimonioInstance: new RecepcionDeAntimonio(params)]
    }

    def save() {
        def recepcionDeAntimonioInstance = new RecepcionDeAntimonio(params)
        if (!recepcionDeAntimonioInstance.save(flush: true)) {
            render(view: "create", model: [recepcionDeAntimonioInstance: recepcionDeAntimonioInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'recepcionDeAntimonio.label', default: 'RecepcionDeAntimonio'), recepcionDeAntimonioInstance.id])
        redirect(action: "show", id: recepcionDeAntimonioInstance.id)
    }

    def show(Long id) {
        def recepcionDeAntimonioInstance = RecepcionDeAntimonio.get(id)
        if (!recepcionDeAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDeAntimonio.label', default: 'RecepcionDeAntimonio'), id])
            redirect(action: "list")
            return
        }

        [recepcionDeAntimonioInstance: recepcionDeAntimonioInstance]
    }

    def edit(Long id) {
        def recepcionDeAntimonioInstance = RecepcionDeAntimonio.get(id)
        if (!recepcionDeAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDeAntimonio.label', default: 'RecepcionDeAntimonio'), id])
            redirect(action: "list")
            return
        }

        [recepcionDeAntimonioInstance: recepcionDeAntimonioInstance]
    }

    def update(Long id, Long version) {
        def recepcionDeAntimonioInstance = RecepcionDeAntimonio.get(id)
        if (!recepcionDeAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDeAntimonio.label', default: 'RecepcionDeAntimonio'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (recepcionDeAntimonioInstance.version > version) {
                recepcionDeAntimonioInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'recepcionDeAntimonio.label', default: 'RecepcionDeAntimonio')] as Object[],
                          "Another user has updated this RecepcionDeAntimonio while you were editing")
                render(view: "edit", model: [recepcionDeAntimonioInstance: recepcionDeAntimonioInstance])
                return
            }
        }

        recepcionDeAntimonioInstance.properties = params

        if (!recepcionDeAntimonioInstance.save(flush: true)) {
            render(view: "edit", model: [recepcionDeAntimonioInstance: recepcionDeAntimonioInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'recepcionDeAntimonio.label', default: 'RecepcionDeAntimonio'), recepcionDeAntimonioInstance.id])
        redirect(action: "show", id: recepcionDeAntimonioInstance.id)
    }

    def delete(Long id) {
        def recepcionDeAntimonioInstance = RecepcionDeAntimonio.get(id)
        if (!recepcionDeAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDeAntimonio.label', default: 'RecepcionDeAntimonio'), id])
            redirect(action: "list")
            return
        }

        try {
            recepcionDeAntimonioInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'recepcionDeAntimonio.label', default: 'RecepcionDeAntimonio'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'recepcionDeAntimonio.label', default: 'RecepcionDeAntimonio'), id])
            redirect(action: "show", id: id)
        }
    }

    def recepcionesJSON() {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesAntimonio = RecepcionDeAntimonio.findAllByLoteAntimonioAndEstadoDelLote(lote,"NO LIQUIDADO")
        def recepcionesList = []
        recepcionesAntimonio.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("recepcionId", recepcion.id)
            mapaRecepcion.put("label", recepcion.toString())
            mapaRecepcion.put("value", recepcion.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("empresaId", recepcion.empresa.id)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.nombreDeEmpresa)
            mapaRecepcion.put("retenciones", retencionesParaLiquidacion(recepcion.empresa.retenciones))
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            mapaRecepcion.put("tara", recepcion.pesoTara)
            mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            mapaRecepcion.put("estadoDelLote", recepcion.estadoDelLote)
            mapaRecepcion.put("cotizacionDiariaDeAntimonio", recepcion.cotizacionDiariaDeMinerales.antimonio)
            mapaRecepcion.put("cotizacionQuincenalDeAntimonio", recepcion.cotizacionQuincenalDeMinerales.antimonio)
            mapaRecepcion.put("alicuotaDeAntimonio", recepcion.alicuota.antimonio)
            mapaRecepcion.put("tipoDeCambioOficial", recepcion.cotizacionDeDolar.tipoDeCambioOficial)
            mapaRecepcion.put("tipoDeCambioComercial", recepcion.cotizacionDeDolar.tipoDeCambioComercial)
            mapaRecepcion.put("detalleLaboratorio1", recepcion.detalleLaboratorio1)
            mapaRecepcion.put("costoLaboratorio1", recepcion.costoLaboratorio1)
            mapaRecepcion.put("detalleLaboratorio2", recepcion.detalleLaboratorio2)
            mapaRecepcion.put("costoLaboratorio2", recepcion.costoLaboratorio2)
            mapaRecepcion.put("detalleLaboratorio3", recepcion.detalleLaboratorio3)
            mapaRecepcion.put("costoLaboratorio3", recepcion.costoLaboratorio3)
            mapaRecepcion.put("detalleLaboratorio4", recepcion.detalleLaboratorio4)
            mapaRecepcion.put("costoLaboratorio4", recepcion.costoLaboratorio4)
            mapaRecepcion.put("totalCostoLaboratorio", recepcion.totalCostoLaboratorio)
            mapaRecepcion.put("totalAnticiposContraEntrega", getTotalAnticiposContraEntrega(recepcion.id))
            mapaRecepcion.put("totalAnticiposContraFuturaEntrega", getTotalAnticiposContraFuturaEntrega(recepcion.cliente.id))
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def retencionesParaLiquidacion = { retenciones ->
        def retencionesJSON = new JSONArray(retenciones)
        //def retencionesEmpresaListJSON = new JSONObject()
        def retencionesEmpresaListJSON = new JSONArray()
        //retencion estatica - regalia minera
        def regaliaMineraJSON = new JSONObject()
        regaliaMineraJSON.put("CODIGO","-")
        regaliaMineraJSON.put("CANTIDAD"," ")
        regaliaMineraJSON.put("TIPO","DE LEY")
        regaliaMineraJSON.put("UNIDAD"," ")
        regaliaMineraJSON.put("DESCRIPCION","REGALIA MINERA")
        regaliaMineraJSON.put("ASIGNACION","VBV")
        regaliaMineraJSON.put("MONTO","0")
        retencionesEmpresaListJSON.put(regaliaMineraJSON)

        retencionesJSON.each {
            def retencionesEmpresaJSON = new JSONObject()
            //def retencionesEmpresaJSON = new JSONObject()
            def codigo = it.getAt("CODIGO")
            def cantidad = it.getAt("CANTIDAD")
            def unidad = it.getAt("UNIDAD")
            def tipo = it.getAt("TIPO")
            def descripcion = it.getAt("DESCRIPCION")
            def asignacion = it.getAt("ASIGNACION")
            retencionesEmpresaJSON.put("CODIGO",codigo)
            retencionesEmpresaJSON.put("CANTIDAD",cantidad)
            retencionesEmpresaJSON.put("TIPO",tipo)
            retencionesEmpresaJSON.put("UNIDAD",unidad)
            retencionesEmpresaJSON.put("DESCRIPCION",descripcion)
            retencionesEmpresaJSON.put("ASIGNACION",asignacion)
            retencionesEmpresaJSON.put("MONTO","0")
            retencionesEmpresaListJSON.put(retencionesEmpresaJSON)
            //it.putAt("MONTO",0)
        }
        return retencionesEmpresaListJSON.toString()
        //return retencionesJSON.toString()
    }

    def getTotalAnticiposContraEntrega = { recepcionId ->
        def totalAnticiposContraEntrega = 0
        def anticipoContraEntregas = AnticipoContraEntrega.findAllByRecepcionId(recepcionId)
        totalAnticiposContraEntrega = anticipoContraEntregas*.importe.sum()
        return (totalAnticiposContraEntrega)?totalAnticiposContraEntrega:0
    }

    def getTotalAnticiposContraFuturaEntrega = { clienteId ->
        def cliente = Cliente.get(clienteId)
        def ultimoEstadoDeCuenta = EstadoDeCuenta.findAllByCliente(cliente, [sort: "id", order: "desc"])
        def ultimoSaldo = (ultimoEstadoDeCuenta.size()>0)?ultimoEstadoDeCuenta.get(0).saldo:0
        return ultimoSaldo
        /*def anticipoContraFuturaEntregas = AnticipoContraFuturaEntrega.findAllByCliente(Cliente.get(clienteId))
        def totalAnticipoContraFuturaEntregas = anticipoContraFuturaEntregas*.importe.sum()
        return (totalAnticipoContraFuturaEntregas)?totalAnticipoContraFuturaEntregas:0*/
    }

    def datosTransporteUltimaRecepcionJSON() {
        def c = RecepcionDeAntimonio.createCriteria()
        def results = c {
            projections {
                max('id')
            }}
        def maxId = results.get(0)?: 0

        if (maxId!=0){
            def ultimaRecepcion = RecepcionDeAntimonio.get(maxId)
            render([
                empresaId: ultimaRecepcion.empresa.id.toString(),
                nombreEmpresa: ultimaRecepcion.empresa.nombreDeEmpresa,
                choferId: ultimaRecepcion.chofer.id.toString(),
                ciChofer: ultimaRecepcion.chofer.ci,
                nombreChofer: ultimaRecepcion.chofer.nombre,
                automovilId: ultimaRecepcion.automovil.id.toString(),
                placaAutomovil: ultimaRecepcion.automovil.placa,
                modeloAutomovil: ultimaRecepcion.automovil.modelo,
                colorAutomovil: ultimaRecepcion.automovil.color
            ] as JSON)
        }
    }

    def recepcionesPresupuestoFechasJSON() {
        def fechaInicial = new Date().parse("yyyy-MM-dd",params.fechaInicial)
        def fechaFinal = new Date().parse("yyyy-MM-dd",params.fechaFinal)
        System.err.println("********* FECHA INICIAL: $fechaInicial")
        System.err.println("********* FECHA FINAL: $fechaFinal")
        def recepcionesAntimonio = RecepcionDeAntimonio.findAllByFechaDeRecepcionBetweenAndEstadoDelLote(fechaInicial,fechaFinal,"NO LIQUIDADO")
        def recepcionesList = []
        recepcionesAntimonio.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("lote", recepcion.loteAntimonio)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            //mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            def kilosNetosHumedos = recepcion.pesoBruto-Float.parseFloat(recepcion.cantidadDeSacos)*recepcion.pesoTara
            mapaRecepcion.put("kilosNetosHumedos", kilosNetosHumedos)
            mapaRecepcion.put("cotizacionAntimonio", recepcion.cotizacionDiariaDeMinerales.antimonio)
            mapaRecepcion.put("humedad", 0)
            mapaRecepcion.put("porcentajeAntimonio", 0)
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def recepcionesPresupuestoFechasEmpresaJSON() {
        def fechaInicial = new Date().parse("yyyy-MM-dd",params.fechaInicial)
        def fechaFinal = new Date().parse("yyyy-MM-dd",params.fechaFinal)
        def empresa = Empresa.get(params.empresaId)
        def recepcionesAntimonio = RecepcionDeAntimonio.findAllByFechaDeRecepcionBetweenAndEstadoDelLoteAndEmpresa(fechaInicial,fechaFinal,"NO LIQUIDADO",empresa)
        def recepcionesList = []
        recepcionesAntimonio.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("lote", recepcion.loteAntimonio)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            mapaRecepcion.put("cotizacionAntimonio", recepcion.cotizacionDiariaDeMinerales.antimonio)
            mapaRecepcion.put("humedad", 0)
            mapaRecepcion.put("porcentajeAntimonio", 0)
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def recepcionesPresupuestoLotesJSON() {
        def loteInicial = params.loteInicial
        def loteFinal = params.loteFinal
        def recepcionesAntimonio = RecepcionDeAntimonio.findAllByLoteAntimonioBetweenAndEstadoDelLote(loteInicial,loteFinal,"NO LIQUIDADO")
        def recepcionesList = []
        recepcionesAntimonio.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("lote", recepcion.loteAntimonio)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            mapaRecepcion.put("cotizacionAntimonio", recepcion.cotizacionDiariaDeMinerales.antimonio)
            mapaRecepcion.put("humedad", 0)
            mapaRecepcion.put("porcentajeAntimonio", 0)
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def recepcionesPresupuestoLotesEmpresaJSON() {
        def loteInicial = params.loteInicial
        def loteFinal = params.loteFinal
        def empresa = Empresa.get(params.empresaId)
        def recepcionesAntimonio = RecepcionDeAntimonio.findAllByLoteAntimonioBetweenAndEstadoDelLoteAndEmpresa(loteInicial,loteFinal,"NO LIQUIDADO",empresa)
        def recepcionesList = []
        recepcionesAntimonio.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("lote", recepcion.loteAntimonio)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            mapaRecepcion.put("cotizacionAntimonio", recepcion.cotizacionDiariaDeMinerales.antimonio)
            mapaRecepcion.put("humedad", 0)
            mapaRecepcion.put("porcentajeAntimonio", 0)
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def crearReporte = {
        def recepcionDeAntimonio = RecepcionDeAntimonio.get(params.id)
        chain(controller:'jasper',action:'index',model:[data:recepcionDeAntimonio],params:params)
    }
}
