package org.socymet.cotizaciones

import grails.converters.JSON
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_RECEPCION','ROLE_CAJA'])
class TablaPreciosOroController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond TablaPreciosOro.list(params), model:[tablaPreciosOroInstanceCount: TablaPreciosOro.count()]
    }

    def show(TablaPreciosOro tablaPreciosOroInstance) {
        respond tablaPreciosOroInstance
    }

    def create() {
        respond new TablaPreciosOro(params)
    }

    @Transactional
    def save(TablaPreciosOro tablaPreciosOroInstance) {
        if (tablaPreciosOroInstance == null) {
            notFound()
            return
        }

        if (tablaPreciosOroInstance.hasErrors()) {
            respond tablaPreciosOroInstance.errors, view:'create'
            return
        }

        tablaPreciosOroInstance.save flush:true

        request.withFormat {
            form multipartForm {
//                flash.message = message(code: 'default.created.message', args: [message(code: 'tablaPreciosOro.label', default: 'TablaPreciosOro'), tablaPreciosOroInstance.id])
                flash.message = message(code: 'default.created.message', args: [message(code: 'tablaPreciosOro.label', default: 'TablaPreciosOro'), tablaPreciosOroInstance.toString()])
                redirect tablaPreciosOroInstance
            }
            '*' { respond tablaPreciosOroInstance, [status: CREATED] }
        }
    }

    def edit(TablaPreciosOro tablaPreciosOroInstance) {
        respond tablaPreciosOroInstance
    }

    @Transactional
    def update(TablaPreciosOro tablaPreciosOroInstance) {
        if (tablaPreciosOroInstance == null) {
            notFound()
            return
        }

        if (tablaPreciosOroInstance.hasErrors()) {
            respond tablaPreciosOroInstance.errors, view:'edit'
            return
        }

        tablaPreciosOroInstance.save flush:true

        request.withFormat {
            form multipartForm {
//                flash.message = message(code: 'default.updated.message', args: [message(code: 'TablaPreciosOro.label', default: 'TablaPreciosOro'), tablaPreciosOroInstance.id])
                flash.message = message(code: 'default.updated.message', args: [message(code: 'TablaPreciosOro.label', default: 'TablaPreciosOro'), tablaPreciosOroInstance.toString()])
                redirect tablaPreciosOroInstance
            }
            '*'{ respond tablaPreciosOroInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(TablaPreciosOro tablaPreciosOroInstance) {

        if (tablaPreciosOroInstance == null) {
            notFound()
            return
        }

        tablaPreciosOroInstance.delete flush:true

        request.withFormat {
            form multipartForm {
//                flash.message = message(code: 'default.deleted.message', args: [message(code: 'TablaPreciosOro.label', default: 'TablaPreciosOro'), tablaPreciosOroInstance.id])
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'TablaPreciosOro.label', default: 'TablaPreciosOro'), tablaPreciosOroInstance.toString()])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaPreciosOro.label', default: 'TablaPreciosOro'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def getValorPorTonelada(Long tablaPreciosOroId,BigDecimal porcentajeOroFinal){
//        def tablaPreciosOro = TablaPreciosOro.get(params.tablaPreciosOroId)
//        def porcentajeOro = Float.parseFloat(params.porcentajeOroFinal.toString())
        def tablaPreciosOro = TablaPreciosOro.get(tablaPreciosOroId)
        def porcentajeOro = porcentajeOroFinal

        def datosOroString = tablaPreciosOro.tablaPrecios
        def datosOroArray = JSON.parse(datosOroString)
        def pos=0
        def porcentajeOroRecuperado=0
        def filaOro = null
        while (params.porcentajeOroFinal.toString().toBigDecimal()>porcentajeOroRecuperado&&pos<datosOroArray.iterator().size()){
            filaOro = JSON.parse(datosOroArray[pos].toString())
            porcentajeOroRecuperado = filaOro.getAt("leyOro").toString().toBigDecimal()
            pos++
        }

        def porcentaje1 = (JSON.parse(datosOroArray[pos-2].toString())).getAt("leyOro").toString().toBigDecimal()
        def porcentaje2 = (JSON.parse(datosOroArray[pos-1].toString())).getAt("leyOro").toString().toBigDecimal()
        def valorTonelada1 = (JSON.parse(datosOroArray[pos-2].toString())).getAt("valorTonelada").toString().toBigDecimal()
        def valorTonelada2 = (JSON.parse(datosOroArray[pos-1].toString())).getAt("valorTonelada").toString().toBigDecimal()

        System.out.println("PRECIO TONELADA LAMAS -> porcentaje1: ${porcentaje1} porcentaje2: ${porcentaje2} vpt1: ${valorTonelada1} vpt2: ${valorTonelada2} porcentajeOro: ${porcentajeOro} tablaPreciosOro: ${tablaPreciosOro.toString()}")

        def precioToneladaOro=getPuntoRecta(porcentajeOro,porcentaje1,porcentaje2,valorTonelada1,valorTonelada2)

        return precioToneladaOro
    }

    def getValorPorToneladaJSON(){
        def tablaPreciosOro = TablaPreciosOro.get(params.tablaPreciosOroId)
        def porcentajeOro = Float.parseFloat(params.porcentajeOroFinal.toString())

        def datosOroString = tablaPreciosOro.tablaPrecios
        def datosOroArray = JSON.parse(datosOroString)
        def pos=0
        def porcentajeOroRecuperado=0
        def filaOro = null
        while (params.porcentajeOroFinal.toString().toBigDecimal()>porcentajeOroRecuperado&&pos<datosOroArray.iterator().size()){
            filaOro = JSON.parse(datosOroArray[pos].toString())
            porcentajeOroRecuperado = filaOro.getAt("leyOro").toString().toBigDecimal()
            pos++
        }

        def porcentaje1 = (JSON.parse(datosOroArray[pos-2].toString())).getAt("leyOro").toString().toBigDecimal()
        def porcentaje2 = (JSON.parse(datosOroArray[pos-1].toString())).getAt("leyOro").toString().toBigDecimal()
        def valorTonelada1 = (JSON.parse(datosOroArray[pos-2].toString())).getAt("valorTonelada").toString().toBigDecimal()
        def valorTonelada2 = (JSON.parse(datosOroArray[pos-1].toString())).getAt("valorTonelada").toString().toBigDecimal()

        System.out.println("PRECIO TONELADA LAMAS -> porcentaje1: ${porcentaje1} porcentaje2: ${porcentaje2} vpt1: ${valorTonelada1} vpt2: ${valorTonelada2} ")

        def precioToneladaOro=getPuntoRecta(porcentajeOro,porcentaje1,porcentaje2,valorTonelada1,valorTonelada2)

        render([vptAu: precioToneladaOro] as JSON)
    }

    def getPuntoRecta(float ley, float ley1, float ley2, float cot1, float cot2){
        return (cot2-cot1)*(ley-ley1)/(ley2-ley1)+cot1
    }
}
