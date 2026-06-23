package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.recepcion.RecepcionDeEstano
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class LiquidacionDeEstanoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [liquidacionDeEstanoInstanceList: LiquidacionDeEstano.list(params), liquidacionDeEstanoInstanceTotal: LiquidacionDeEstano.count()]
    }

    def create() {
        [liquidacionDeEstanoInstance: new LiquidacionDeEstano(params)]
    }

    def save() {
        def liquidacionDeEstanoInstance = new LiquidacionDeEstano(params)
        if (!liquidacionDeEstanoInstance.save(flush: true)) {
            render(view: "create", model: [liquidacionDeEstanoInstance: liquidacionDeEstanoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'liquidacionDeEstano.label', default: 'LiquidacionDeEstano'), liquidacionDeEstanoInstance.id])
        redirect(action: "show", id: liquidacionDeEstanoInstance.id)
    }

    def show(Long id) {
        def liquidacionDeEstanoInstance = LiquidacionDeEstano.get(id)
        if (!liquidacionDeEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeEstano.label', default: 'LiquidacionDeEstano'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDeEstanoInstance: liquidacionDeEstanoInstance]
    }

    def edit(Long id) {
        def liquidacionDeEstanoInstance = LiquidacionDeEstano.get(id)
        if (!liquidacionDeEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeEstano.label', default: 'LiquidacionDeEstano'), id])
            redirect(action: "list")
            return
        }

        [liquidacionDeEstanoInstance: liquidacionDeEstanoInstance]
    }

    def update(Long id, Long version) {
        def liquidacionDeEstanoInstance = LiquidacionDeEstano.get(id)
        if (!liquidacionDeEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeEstano.label', default: 'LiquidacionDeEstano'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (liquidacionDeEstanoInstance.version > version) {
                liquidacionDeEstanoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'liquidacionDeEstano.label', default: 'LiquidacionDeEstano')] as Object[],
                        "Another user has updated this LiquidacionDeEstano while you were editing")
                render(view: "edit", model: [liquidacionDeEstanoInstance: liquidacionDeEstanoInstance])
                return
            }
        }

        liquidacionDeEstanoInstance.properties = params

        if (!liquidacionDeEstanoInstance.save(flush: true)) {
            render(view: "edit", model: [liquidacionDeEstanoInstance: liquidacionDeEstanoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'liquidacionDeEstano.label', default: 'LiquidacionDeEstano'), liquidacionDeEstanoInstance.id])
        redirect(action: "show", id: liquidacionDeEstanoInstance.id)
    }

    def delete(Long id) {
        def liquidacionDeEstanoInstance = LiquidacionDeEstano.get(id)
        if (!liquidacionDeEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeEstano.label', default: 'LiquidacionDeEstano'), id])
            redirect(action: "list")
            return
        }

        try {
            /* CAMBIAR EL ESTADO DEL LOTE */
            def recepcion = RecepcionDeEstano.get(liquidacionDeEstanoInstance.recepcionDeEstano.id)
            recepcion.estadoDelLote = "NO LIQUIDADO"
            recepcion.save()
            /* FIN - CAMBIAR EL ESTADO DEL LOTE*/

            /* ELIMINAR LAS RETENCIONES*/
            def listaRetenciones = LiquidacionDeEstanoRetenciones.findAllByLiquidacionDeEstano(liquidacionDeEstanoInstance)
            listaRetenciones.each {
                it.delete(flush: true)
            }
            /* FIN - ELIMINAR RETENCIONES*/

            liquidacionDeEstanoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'liquidacionDeEstano.label', default: 'LiquidacionDeEstano'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDeEstano.label', default: 'LiquidacionDeEstano'), id])
            redirect(action: "show", id: id)
        }
    }

    def eliminarDeConjunto(Long id) {
        def liquidacionDeEstanoInstance = LiquidacionDeEstano.get(id)
        if (!liquidacionDeEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeEstano.label', default: 'LiquidacionDeEstano'), id])
            redirect(action: "list")
            return
        }

        try {
            System.out.println("*** LOCALIZADO LOTE: ${liquidacionDeEstanoInstance.lote} DEL CONJUNTO: ${liquidacionDeEstanoInstance.conjuntoEstano}")
            liquidacionDeEstanoInstance.conjuntoEstano="-"
            liquidacionDeEstanoInstance.save(failOnError: true)
            flash.message = "El Lote ha sido eliminado del Conjunto anteriormente asignado."
            redirect(action: "show", id: id)
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDeEstano.label', default: 'LiquidacionDeEstano'), id])
            redirect(action: "show", id: id)
        }
    }

    def cancelacionDeLote(Long id) {
        def liquidacionDeEstanoInstance = LiquidacionDeEstano.get(id)
        if (!liquidacionDeEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'liquidacionDeEstano.label', default: 'LiquidacionDeEstano'), id])
            redirect(action: "list")
            return
        }

        try {
            liquidacionDeEstanoInstance.fechaDeCancelacion=new java.util.Date()
            liquidacionDeEstanoInstance.save(failOnError: true)
            flash.message = "Se ha establecido la Fecha de Cancelacion del Lote"
            redirect(action: "show", id: id)
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'liquidacionDeEstano.label', default: 'LiquidacionDeEstano'), id])
            redirect(action: "show", id: id)
        }
    }

    def crearReporte = {
        System.out.println("***** GENERANDO REPORTE LIQUIDACION ESTANO LIQ_SN_ID:${params.LIQ_SN_ID}")
        def liquidacionDeEstano = LiquidacionDeEstano.get(params.id)
        params.SUBREPORT_DIR = "${servletContext.getRealPath('/reports')}/"
        chain(controller:'jasper',action:'index',model:[data:liquidacionDeEstano],params:params)
    }

    def liquidacionesJSON() {
        def lote = params.term.toString()
        def liquidacionDeEstanos = LiquidacionDeEstano.findAllByLoteLikeAndConjuntoEstano("%${lote}%","-")
        def liquidacionesList = []
        liquidacionDeEstanos.each { liquidacion ->
            def liquidacionMap = [:]
            liquidacionMap.put("liquidacionId", liquidacion.id)
            liquidacionMap.put("label", liquidacion.lote)
            liquidacionMap.put("value", liquidacion.lote)
            liquidacionMap.put("nombreCliente", liquidacion.nombreCliente)
            liquidacionMap.put("nombreEmpresa", liquidacion.nombreEmpresa)
            liquidacionMap.put("fechaDeRecepcion", liquidacion.fechaDeRecepcion)
            liquidacionMap.put("fechaDeLiquidacion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(liquidacion.fechaDeLiquidacion))
            liquidacionMap.put("kilosNetosSecos", liquidacion.kilosNetosSecos)
            liquidacionMap.put("porcentajeEstano", liquidacion.porcentajeEstano)
            liquidacionesList.add(liquidacionMap)
        }
        render liquidacionesList as JSON
    }

    def lotesEnConjuntoJSON() {
        def lote = params.term.toString()
        def liquidacionDeEstanos = LiquidacionDeEstano.findAllByLoteLikeAndConjuntoEstanoNotEqual("%${lote}%","-")
        def liquidacionesList = []
        liquidacionDeEstanos.each { liquidacion ->
            def liquidacionMap = [:]
            liquidacionMap.put("liquidacionId", liquidacion.id)
            liquidacionMap.put("label", liquidacion.lote)
            liquidacionMap.put("value", liquidacion.lote)
            liquidacionMap.put("nombreCliente", liquidacion.nombreCliente)
            liquidacionMap.put("nombreEmpresa", liquidacion.nombreEmpresa)
            liquidacionMap.put("fechaDeRecepcion", liquidacion.fechaDeRecepcion)
            liquidacionMap.put("fechaDeLiquidacion", new java.text.SimpleDateFormat("dd/MM/yyyy").format(liquidacion.fechaDeLiquidacion))
            liquidacionMap.put("kilosNetosSecos", liquidacion.kilosNetosSecos)
            liquidacionMap.put("porcentajeEstano", liquidacion.porcentajeEstano)
            liquidacionMap.put("conjuntoEstano", liquidacion.conjuntoEstano)
            liquidacionesList.add(liquidacionMap)
        }
        render liquidacionesList as JSON
    }
}

/*DEBE IMPLEMENTARSE BOTON EN EL SHOW DE LA HOJA DE COSTO PARA QUE SE PUEDA "REIMPRIMIR" EL ARCHIVO DE EXCEL SEGUN LOS PARAMETROS
* CON LOS QUE SE GENERO LA PRIMERA VEZ EL REPORTE, SE PODRIA UTILIZAR SIMPLEMENTE EL NOMBRE DEL MINERAL Y EL NOMBRE DEL CONJUNTO
* PARA FILTRAR
* - IMPLEMENTAR LA CAPACIDAD DE PODER AGREGAR UN LOTE A UN CONJUNTO*/
