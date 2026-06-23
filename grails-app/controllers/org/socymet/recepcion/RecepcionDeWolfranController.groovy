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
class RecepcionDeWolfranController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [recepcionDeWolfranInstanceList: RecepcionDeWolfran.list(params), recepcionDeWolfranInstanceTotal: RecepcionDeWolfran.count()]
    }

    def create() {
        [recepcionDeWolfranInstance: new RecepcionDeWolfran(params)]
    }

    def save() {
        def recepcionDeWolfranInstance = new RecepcionDeWolfran(params)
        if (!recepcionDeWolfranInstance.save(flush: true)) {
            render(view: "create", model: [recepcionDeWolfranInstance: recepcionDeWolfranInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'recepcionDeWolfran.label', default: 'RecepcionDeWolfran'), recepcionDeWolfranInstance.id])
        redirect(action: "show", id: recepcionDeWolfranInstance.id)
    }

    def show(Long id) {
        def recepcionDeWolfranInstance = RecepcionDeWolfran.get(id)
        if (!recepcionDeWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDeWolfran.label', default: 'RecepcionDeWolfran'), id])
            redirect(action: "list")
            return
        }

        [recepcionDeWolfranInstance: recepcionDeWolfranInstance]
    }

    def edit(Long id) {
        def recepcionDeWolfranInstance = RecepcionDeWolfran.get(id)
        if (!recepcionDeWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDeWolfran.label', default: 'RecepcionDeWolfran'), id])
            redirect(action: "list")
            return
        }

        [recepcionDeWolfranInstance: recepcionDeWolfranInstance]
    }

    def update(Long id, Long version) {
        def recepcionDeWolfranInstance = RecepcionDeWolfran.get(id)
        if (!recepcionDeWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDeWolfran.label', default: 'RecepcionDeWolfran'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (recepcionDeWolfranInstance.version > version) {
                recepcionDeWolfranInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'recepcionDeWolfran.label', default: 'RecepcionDeWolfran')] as Object[],
                        "Another user has updated this RecepcionDeWolfran while you were editing")
                render(view: "edit", model: [recepcionDeWolfranInstance: recepcionDeWolfranInstance])
                return
            }
        }

        recepcionDeWolfranInstance.properties = params

        if (!recepcionDeWolfranInstance.save(flush: true)) {
            render(view: "edit", model: [recepcionDeWolfranInstance: recepcionDeWolfranInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'recepcionDeWolfran.label', default: 'RecepcionDeWolfran'), recepcionDeWolfranInstance.id])
        redirect(action: "show", id: recepcionDeWolfranInstance.id)
    }

    def delete(Long id) {
        def recepcionDeWolfranInstance = RecepcionDeWolfran.get(id)
        if (!recepcionDeWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDeWolfran.label', default: 'RecepcionDeWolfran'), id])
            redirect(action: "list")
            return
        }

        try {
            recepcionDeWolfranInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'recepcionDeWolfran.label', default: 'RecepcionDeWolfran'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'recepcionDeWolfran.label', default: 'RecepcionDeWolfran'), id])
            redirect(action: "show", id: id)
        }
    }

    def recepcionesJSON() {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesWolfran = RecepcionDeWolfran.findAllByLoteWolfranAndEstadoDelLote(lote,"NO LIQUIDADO")
        def recepcionesList = []
        recepcionesWolfran.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("recepcionId", recepcion.id)
            mapaRecepcion.put("label", recepcion.toString())
            mapaRecepcion.put("value", recepcion.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("empresaId", recepcion.empresa.id)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("retenciones", retencionesParaLiquidacion(recepcion.empresa.retenciones))
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            mapaRecepcion.put("tara", recepcion.pesoTara)
            mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            mapaRecepcion.put("estadoDelLote", recepcion.estadoDelLote)
            mapaRecepcion.put("cotizacionDiariaDeWolfran", recepcion.cotizacionDiariaDeMinerales.wolfran)
            mapaRecepcion.put("cotizacionQuincenalDeWolfran", recepcion.cotizacionQuincenalDeMinerales.wolfran)
            mapaRecepcion.put("alicuotaDeWolfran", recepcion.alicuota.wolfran)
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
        def c = RecepcionDeWolfran.createCriteria()
        def results = c {
            projections {
                max('id')
            }}
        def maxId = results.get(0)?: 0

        if (maxId!=0){
            def ultimaRecepcion = RecepcionDeWolfran.get(maxId)
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
        def recepcionesWolfran = RecepcionDeWolfran.findAllByFechaDeRecepcionBetweenAndEstadoDelLote(fechaInicial,fechaFinal,"NO LIQUIDADO")
        def recepcionesList = []
        recepcionesWolfran.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("lote", recepcion.loteWolfran)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            //mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            def kilosNetosHumedos = recepcion.pesoBruto-Float.parseFloat(recepcion.cantidadDeSacos)*recepcion.pesoTara
            mapaRecepcion.put("kilosNetosHumedos", kilosNetosHumedos)
            mapaRecepcion.put("cotizacionWolfran", recepcion.cotizacionDiariaDeMinerales.wolfran)
            mapaRecepcion.put("humedad", 0)
            mapaRecepcion.put("porcentajeWolfran", 0)
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def recepcionesPresupuestoFechasEmpresaJSON() {
        def fechaInicial = new Date().parse("yyyy-MM-dd",params.fechaInicial)
        def fechaFinal = new Date().parse("yyyy-MM-dd",params.fechaFinal)
        def empresa = Empresa.get(params.empresaId)
        def recepcionesWolfran = RecepcionDeWolfran.findAllByFechaDeRecepcionBetweenAndEstadoDelLoteAndEmpresa(fechaInicial,fechaFinal,"NO LIQUIDADO",empresa)
        def recepcionesList = []
        recepcionesWolfran.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("lote", recepcion.loteWolfran)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            mapaRecepcion.put("cotizacionWolfran", recepcion.cotizacionDiariaDeMinerales.wolfran)
            mapaRecepcion.put("humedad", 0)
            mapaRecepcion.put("porcentajeWolfran", 0)
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def recepcionesPresupuestoLotesJSON() {
        def loteInicial = params.loteInicial
        def loteFinal = params.loteFinal
        def recepcionesWolfran = RecepcionDeWolfran.findAllByLoteWolfranBetweenAndEstadoDelLote(loteInicial,loteFinal,"NO LIQUIDADO")
        def recepcionesList = []
        recepcionesWolfran.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("lote", recepcion.loteWolfran)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            mapaRecepcion.put("cotizacionWolfran", recepcion.cotizacionDiariaDeMinerales.wolfran)
            mapaRecepcion.put("humedad", 0)
            mapaRecepcion.put("porcentajeWolfran", 0)
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def recepcionesPresupuestoLotesEmpresaJSON() {
        def loteInicial = params.loteInicial
        def loteFinal = params.loteFinal
        def empresa = Empresa.get(params.empresaId)
        def recepcionesWolfran = RecepcionDeWolfran.findAllByLoteWolfranBetweenAndEstadoDelLoteAndEmpresa(loteInicial,loteFinal,"NO LIQUIDADO",empresa)
        def recepcionesList = []
        recepcionesWolfran.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("lote", recepcion.loteWolfran)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            mapaRecepcion.put("cotizacionWolfran", recepcion.cotizacionDiariaDeMinerales.wolfran)
            mapaRecepcion.put("humedad", 0)
            mapaRecepcion.put("porcentajeWolfran", 0)
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def crearReporte = {
        def recepcionDeWolfran = RecepcionDeWolfran.get(params.id)
        chain(controller:'jasper',action:'index',model:[data:recepcionDeWolfran],params:params)
    }
}
