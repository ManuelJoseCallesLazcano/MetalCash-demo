package org.socymet.caja

import grails.converters.JSON
import org.socymet.anticipos.Anticipo
import org.socymet.anticipos.AnticipoPorTransporte
import org.socymet.cancelacion.PagoTransporte
import org.socymet.liquidacion.LiquidacionDeComplejo
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.seguridad.SecUser
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
class EgresoController {
    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Egreso.list(params), model: [egresoInstanceCount: Egreso.count()]
    }

    def show(Egreso egresoInstance) {
        respond egresoInstance
    }

    def create() {
        respond new Egreso(params)
    }

    @Transactional
    def save(Egreso egresoInstance) {
        if (egresoInstance == null) {
            notFound()
            return
        }

        if (egresoInstance.hasErrors()) {
            respond egresoInstance.errors, view: 'create'
            return
        }

        egresoInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'egreso.label', default: 'Egreso'), egresoInstance.id])
                redirect egresoInstance
            }
            '*' { respond egresoInstance, [status: CREATED] }
        }
    }

    def edit(Egreso egresoInstance) {
        respond egresoInstance
    }

    @Transactional
    def update(Egreso egresoInstance) {
        if (egresoInstance == null) {
            notFound()
            return
        }

        if (egresoInstance.hasErrors()) {
            respond egresoInstance.errors, view: 'edit'
            return
        }

        egresoInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Egreso.label', default: 'Egreso'), egresoInstance.id])
                redirect egresoInstance
            }
            '*' { respond egresoInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Egreso egresoInstance) {

        if (egresoInstance == null) {
            notFound()
            return
        }

        egresoInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Egreso.label', default: 'Egreso'), egresoInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'egreso.label', default: 'Egreso'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    def importeJSON = {
        //operacion(inList: ["LIQUIDACION","ANTICIPO","PAGO DE TRANSPORTE","ANTICIPO POR TRANSPORTE","OTROS PAGOS"])
        def operacion = params.operacion.toString()
        def identificador = params.identificador.toString()

        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        def deposito = usuarioActual.deposito
        
        def resultadosList = []
        
        if(operacion.equals("LIQUIDACION")){
            def liquidacion = LiquidacionDeComplejo.findAllByLoteLikeAndDeposito("%${identificador}%",deposito)
            liquidacion.each { objeto ->
                def mapaObjeto = [:]

                mapaObjeto.put("objetoId", objeto.id)
                mapaObjeto.put("label", objeto.toString())
                mapaObjeto.put("value", objeto.toString())
                mapaObjeto.put("importe", objeto.totalLiquidoPagable)

                resultadosList.add(mapaObjeto)
            }
        }

        if(operacion.equals("ANTICIPO")){
            def liquidacion = Anticipo.findAllByNumeroComprobanteAndDeposito(identificador.toInteger(),deposito)
            liquidacion.each { objeto ->
                def mapaObjeto = [:]

                mapaObjeto.put("objetoId", objeto.id)
                mapaObjeto.put("label", objeto.toString())
                mapaObjeto.put("value", objeto.toString())
                mapaObjeto.put("importe", objeto.primerAnticipo)

                resultadosList.add(mapaObjeto)
            }
        }

        if(operacion.equals("PAGO DE TRANSPORTE")){
            def liquidacion = PagoTransporte.findAllByNumeroComprobanteAndDeposito(identificador.toInteger(),deposito)
            liquidacion.each { objeto ->
                def mapaObjeto = [:]

                mapaObjeto.put("objetoId", objeto.id)
                mapaObjeto.put("label", objeto.toString())
                mapaObjeto.put("value", objeto.toString())
                mapaObjeto.put("importe", objeto.totalPagable)

                resultadosList.add(mapaObjeto)
            }
        }

        if(operacion.equals("ANTICIPO POR TRANSPORTE")){
            def liquidacion = AnticipoPorTransporte.findAllByNumeroComprobante(identificador.toInteger())
            liquidacion.each { objeto ->
                def mapaObjeto = [:]

                mapaObjeto.put("objetoId", objeto.id)
                mapaObjeto.put("label", objeto.toString())
                mapaObjeto.put("value", objeto.toString())
                mapaObjeto.put("importe", objeto.importe)

                resultadosList.add(mapaObjeto)
            }
        }

        render resultadosList as JSON
    }

    def createReport = {
        def pesajeNormal = Egreso.get(params.id)
        def realPath = org.socymet.util.ReportesRuntime.realPath("/reports/images/")
        params.realPath=realPath+"/"
        params.SUBREPORT_DIR = "${org.socymet.util.ReportesRuntime.realPath('/reports')}/"
        chain(controller:'jasper',action:'index',model:[data:pesajeNormal],params:params)
    }
}
