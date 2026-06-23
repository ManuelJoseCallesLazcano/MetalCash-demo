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
class RecepcionDePlataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [recepcionDePlataInstanceList: RecepcionDePlata.list(params), recepcionDePlataInstanceTotal: RecepcionDePlata.count()]
    }

    def create() {
        [recepcionDePlataInstance: new RecepcionDePlata(params)]
    }

    def save() {
        def recepcionDePlataInstance = new RecepcionDePlata(params)
        if (!recepcionDePlataInstance.save(flush: true)) {
            render(view: "create", model: [recepcionDePlataInstance: recepcionDePlataInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'recepcionDePlata.label', default: 'RecepcionDePlata'), recepcionDePlataInstance.id])
        redirect(action: "show", id: recepcionDePlataInstance.id)
    }

    def show(Long id) {
        def recepcionDePlataInstance = RecepcionDePlata.get(id)
        if (!recepcionDePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDePlata.label', default: 'RecepcionDePlata'), id])
            redirect(action: "list")
            return
        }

        [recepcionDePlataInstance: recepcionDePlataInstance]
    }

    def edit(Long id) {
        def recepcionDePlataInstance = RecepcionDePlata.get(id)
        if (!recepcionDePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDePlata.label', default: 'RecepcionDePlata'), id])
            redirect(action: "list")
            return
        }

        [recepcionDePlataInstance: recepcionDePlataInstance]
    }

    def update(Long id, Long version) {
        def recepcionDePlataInstance = RecepcionDePlata.get(id)
        if (!recepcionDePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDePlata.label', default: 'RecepcionDePlata'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (recepcionDePlataInstance.version > version) {
                recepcionDePlataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'recepcionDePlata.label', default: 'RecepcionDePlata')] as Object[],
                        "Another user has updated this RecepcionDePlata while you were editing")
                render(view: "edit", model: [recepcionDePlataInstance: recepcionDePlataInstance])
                return
            }
        }

        recepcionDePlataInstance.properties = params

        if (!recepcionDePlataInstance.save(flush: true)) {
            render(view: "edit", model: [recepcionDePlataInstance: recepcionDePlataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'recepcionDePlata.label', default: 'RecepcionDePlata'), recepcionDePlataInstance.id])
        redirect(action: "show", id: recepcionDePlataInstance.id)
    }

    def delete(Long id) {
        def recepcionDePlataInstance = RecepcionDePlata.get(id)
        if (!recepcionDePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'recepcionDePlata.label', default: 'RecepcionDePlata'), id])
            redirect(action: "list")
            return
        }

        try {
            recepcionDePlataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'recepcionDePlata.label', default: 'RecepcionDePlata'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'recepcionDePlata.label', default: 'RecepcionDePlata'), id])
            redirect(action: "show", id: id)
        }
    }

    def recepcionesJSON() {
        def lote = Integer.parseInt(params.term.toString())
        def recepcionesPlata = RecepcionDePlata.findAllByLotePlataAndEstadoDelLote(lote,"NO LIQUIDADO")
        def recepcionesList = []
        recepcionesPlata.each { recepcion ->
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
            mapaRecepcion.put("cotizacionDiariaDePlata", recepcion.cotizacionDiariaDeMinerales.plata)
            mapaRecepcion.put("cotizacionQuincenalDePlata", recepcion.cotizacionQuincenalDeMinerales.plata)
            mapaRecepcion.put("alicuotaDePlata", recepcion.alicuota.plata)
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
        def c = RecepcionDePlata.createCriteria()
        def results = c {
            projections {
                max('id')
            }}
        def maxId = results.get(0)?: 0

        if (maxId!=0){
            def ultimaRecepcion = RecepcionDePlata.get(maxId)
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
        def recepcionesPlata = RecepcionDePlata.findAllByFechaDeRecepcionBetweenAndEstadoDelLote(fechaInicial,fechaFinal,"NO LIQUIDADO")
        def recepcionesList = []
        recepcionesPlata.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("lote", recepcion.lotePlata)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            //mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            def kilosNetosHumedos = recepcion.pesoBruto-Float.parseFloat(recepcion.cantidadDeSacos)*recepcion.pesoTara
            mapaRecepcion.put("kilosNetosHumedos", kilosNetosHumedos)
            mapaRecepcion.put("cotizacionPlata", recepcion.cotizacionDiariaDeMinerales.plata)
            mapaRecepcion.put("humedad", 0)
            mapaRecepcion.put("porcentajePlata", 0)
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def recepcionesPresupuestoFechasEmpresaJSON() {
        def fechaInicial = new Date().parse("yyyy-MM-dd",params.fechaInicial)
        def fechaFinal = new Date().parse("yyyy-MM-dd",params.fechaFinal)
        def empresa = Empresa.get(params.empresaId)
        def recepcionesPlata = RecepcionDePlata.findAllByFechaDeRecepcionBetweenAndEstadoDelLoteAndEmpresa(fechaInicial,fechaFinal,"NO LIQUIDADO",empresa)
        def recepcionesList = []
        recepcionesPlata.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("lote", recepcion.lotePlata)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            mapaRecepcion.put("cotizacionPlata", recepcion.cotizacionDiariaDeMinerales.plata)
            mapaRecepcion.put("humedad", 0)
            mapaRecepcion.put("porcentajePlata", 0)
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def recepcionesPresupuestoLotesJSON() {
        def loteInicial = params.loteInicial
        def loteFinal = params.loteFinal
        def recepcionesPlata = RecepcionDePlata.findAllByLotePlataBetweenAndEstadoDelLote(loteInicial,loteFinal,"NO LIQUIDADO")
        def recepcionesList = []
        recepcionesPlata.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("lote", recepcion.lotePlata)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            mapaRecepcion.put("cotizacionPlata", recepcion.cotizacionDiariaDeMinerales.plata)
            mapaRecepcion.put("humedad", 0)
            mapaRecepcion.put("porcentajePlata", 0)
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def recepcionesPresupuestoLotesEmpresaJSON() {
        def loteInicial = params.loteInicial
        def loteFinal = params.loteFinal
        def empresa = Empresa.get(params.empresaId)
        def recepcionesPlata = RecepcionDePlata.findAllByLotePlataBetweenAndEstadoDelLoteAndEmpresa(loteInicial,loteFinal,"NO LIQUIDADO",empresa)
        def recepcionesList = []
        recepcionesPlata.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("fechaDeRecepcion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(recepcion.fechaDeRecepcion))
            mapaRecepcion.put("lote", recepcion.lotePlata)
            mapaRecepcion.put("nombreEmpresa", recepcion.empresa.toString())
            mapaRecepcion.put("nombreCliente", recepcion.cliente.nombre)
            mapaRecepcion.put("cantidadDeSacos", recepcion.cantidadDeSacos)
            mapaRecepcion.put("pesoBruto", recepcion.pesoBruto)
            mapaRecepcion.put("cotizacionPlata", recepcion.cotizacionDiariaDeMinerales.plata)
            mapaRecepcion.put("humedad", 0)
            mapaRecepcion.put("porcentajePlata", 0)
            recepcionesList.add(mapaRecepcion)
        }
        render recepcionesList as JSON
    }

    def crearReporte = {
        def recepcionDePlata = RecepcionDePlata.get(params.id)
        chain(controller:'jasper',action:'index',model:[data:recepcionDePlata],params:params)
    }
}
