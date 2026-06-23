package org.socymet.cotizaciones
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.grails.web.json.JSONArray
import org.socymet.liquidacion.LiquidacionDeEstanoRetenciones
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN'])
@Transactional
class TablaCotizacionEstanoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [tablaCotizacionEstanoInstanceList: TablaCotizacionEstano.list(params), tablaCotizacionEstanoInstanceTotal: TablaCotizacionEstano.count()]
    }

    def create() {
        [tablaCotizacionEstanoInstance: new TablaCotizacionEstano(params)]
    }

    def save() {
        def tablaCotizacionEstanoInstance = new TablaCotizacionEstano(params)
        if (!tablaCotizacionEstanoInstance.save(flush: true)) {
            render(view: "create", model: [tablaCotizacionEstanoInstance: tablaCotizacionEstanoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'tablaCotizacionEstano.label', default: 'TablaCotizacionEstano'), tablaCotizacionEstanoInstance.id])
        redirect(action: "show", id: tablaCotizacionEstanoInstance.id)
    }

    def show(Long id) {
        def tablaCotizacionEstanoInstance = TablaCotizacionEstano.get(id)
        if (!tablaCotizacionEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaCotizacionEstano.label', default: 'TablaCotizacionEstano'), id])
            redirect(action: "list")
            return
        }

        [tablaCotizacionEstanoInstance: tablaCotizacionEstanoInstance]
    }

    def edit(Long id) {
        def tablaCotizacionEstanoInstance = TablaCotizacionEstano.get(id)
        if (!tablaCotizacionEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaCotizacionEstano.label', default: 'TablaCotizacionEstano'), id])
            redirect(action: "list")
            return
        }

        [tablaCotizacionEstanoInstance: tablaCotizacionEstanoInstance]
    }

    def update(Long id, Long version) {
        def tablaCotizacionEstanoInstance = TablaCotizacionEstano.get(id)
        if (!tablaCotizacionEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaCotizacionEstano.label', default: 'TablaCotizacionEstano'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (tablaCotizacionEstanoInstance.version > version) {
                tablaCotizacionEstanoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'tablaCotizacionEstano.label', default: 'TablaCotizacionEstano')] as Object[],
                        "Another user has updated this TablaCotizacionEstano while you were editing")
                render(view: "edit", model: [tablaCotizacionEstanoInstance: tablaCotizacionEstanoInstance])
                return
            }
        }

        tablaCotizacionEstanoInstance.properties = params

        if (!tablaCotizacionEstanoInstance.save(flush: true)) {
            render(view: "edit", model: [tablaCotizacionEstanoInstance: tablaCotizacionEstanoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'tablaCotizacionEstano.label', default: 'TablaCotizacionEstano'), tablaCotizacionEstanoInstance.id])
        redirect(action: "show", id: tablaCotizacionEstanoInstance.id)
    }

    def delete(Long id) {
        def tablaCotizacionEstanoInstance = TablaCotizacionEstano.get(id)
        if (!tablaCotizacionEstanoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaCotizacionEstano.label', default: 'TablaCotizacionEstano'), id])
            redirect(action: "list")
            return
        }

        try {
            tablaCotizacionEstanoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'tablaCotizacionEstano.label', default: 'TablaCotizacionEstano'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'tablaCotizacionEstano.label', default: 'TablaCotizacionEstano'), id])
            redirect(action: "show", id: id)
        }
    }

    def getValorPorTonelada(TablaCotizacionEstano tablaCotizacionEstano, float cotizacionDiariaDeEstano, float porcentajeEstano){
        log.error("getValorPorTonelada: $tablaCotizacionEstano - $cotizacionDiariaDeEstano - $porcentajeEstano")
        if (porcentajeEstano==0)
            return 0
//        def tablaCotizacionEstano = TablaCotizacionEstano.get(tablaCotizacionEstanoId)
//        def cotizacionDiariaDeEstano = Float.parseFloat(params.cotizacionDiariaDeEstano.toString())
//        def porcentajeEstano = Float.parseFloat(params.porcentajeEstano.toString())

        def tablaJSON = new JSONArray(tablaCotizacionEstano.tablaDeCotizaciones)
        def flag = true
        def index = 0
        def fila = "";
        def valorPorTonelada = 0

        while(index < tablaJSON.length() && flag){
            fila = tablaJSON.get(index)
            def cotizacion = Float.parseFloat(fila.getAt("COT").toString())
            if (cotizacion == cotizacionDiariaDeEstano)
                flag = false
            index++
        }

        if (porcentajeEstano>=0&&porcentajeEstano<=5){
            valorPorTonelada = Float.parseFloat(fila.getAt("L5").toString())
        }

        if (porcentajeEstano>5&&porcentajeEstano<=10){
            def cot1 = Float.parseFloat(fila.getAt("L5").toString())
            def cot2 = Float.parseFloat(fila.getAt("L10").toString())
            valorPorTonelada = getPuntoRecta(porcentajeEstano,5,10,cot1,cot2)
        }

        if (porcentajeEstano>10&&porcentajeEstano<=15){
            def cot1 = Float.parseFloat(fila.getAt("L10").toString())
            def cot2 = Float.parseFloat(fila.getAt("L15").toString())
            valorPorTonelada = getPuntoRecta(porcentajeEstano,10,15,cot1,cot2)
        }

        if (porcentajeEstano>15&&porcentajeEstano<=20){
            def cot1 = Float.parseFloat(fila.getAt("L15").toString())
            def cot2 = Float.parseFloat(fila.getAt("L20").toString())
            valorPorTonelada = getPuntoRecta(porcentajeEstano,15,20,cot1,cot2)
        }

        if (porcentajeEstano>20&&porcentajeEstano<=25){
            def cot1 = Float.parseFloat(fila.getAt("L20").toString())
            def cot2 = Float.parseFloat(fila.getAt("L25").toString())
            valorPorTonelada = getPuntoRecta(porcentajeEstano,20,25,cot1,cot2)
        }

        if (porcentajeEstano>25&&porcentajeEstano<=30){
            def cot1 = Float.parseFloat(fila.getAt("L25").toString())
            def cot2 = Float.parseFloat(fila.getAt("L30").toString())
            valorPorTonelada = getPuntoRecta(porcentajeEstano,25,30,cot1,cot2)
        }

        if (porcentajeEstano>30&&porcentajeEstano<=35){
            def cot1 = Float.parseFloat(fila.getAt("L30").toString())
            def cot2 = Float.parseFloat(fila.getAt("L35").toString())
            valorPorTonelada = getPuntoRecta(porcentajeEstano,30,35,cot1,cot2)
        }

        if (porcentajeEstano>35&&porcentajeEstano<=40){
            def cot1 = Float.parseFloat(fila.getAt("L35").toString())
            def cot2 = Float.parseFloat(fila.getAt("L40").toString())
            valorPorTonelada = getPuntoRecta(porcentajeEstano,35,40,cot1,cot2)
        }

        if (porcentajeEstano>40&&porcentajeEstano<=50){
            def cot1 = Float.parseFloat(fila.getAt("L40").toString())
            def cot2 = Float.parseFloat(fila.getAt("L50").toString())
            valorPorTonelada = getPuntoRecta(porcentajeEstano,40,50,cot1,cot2)
        }

        if (porcentajeEstano>50&&porcentajeEstano<=60){
            def cot1 = Float.parseFloat(fila.getAt("L50").toString())
            def cot2 = Float.parseFloat(fila.getAt("L60").toString())
            valorPorTonelada = getPuntoRecta(porcentajeEstano,50,60,cot1,cot2)
        }

        if (porcentajeEstano>60&&porcentajeEstano<=70){
            def cot1 = Float.parseFloat(fila.getAt("L60").toString())
            def cot2 = Float.parseFloat(fila.getAt("L70").toString())
            valorPorTonelada = getPuntoRecta(porcentajeEstano,60,70,cot1,cot2)
        }

        if (porcentajeEstano>70&&porcentajeEstano<=75){
            def cot1 = Float.parseFloat(fila.getAt("L70").toString())
            def cot2 = Float.parseFloat(fila.getAt("L75").toString())
            valorPorTonelada = getPuntoRecta(porcentajeEstano,70,75,cot1,cot2)
        }

        if (porcentajeEstano>75){
            valorPorTonelada = Float.parseFloat(fila.getAt("L75").toString())
        }

//        render(contentType: "text/json") {
//            vpt = valorPorTonelada.toString()
//        }
        return valorPorTonelada
    }

    def getValorPorToneladaPresupuesto(Integer tablaCotizacionEstanoId, Float cotizacionDiariaDeEstano, Float porcentajeEstano){
        def tablaCotizacionEstano = TablaCotizacionEstano.get(tablaCotizacionEstanoId)

        def tablaJSON = new JSONArray(tablaCotizacionEstano.tablaDeCotizaciones)
        def flag = true
        def index = 0
        def fila = "";
        def valorPorTonelada = 0

        while(index < tablaJSON.length() && flag){
            fila = tablaJSON.get(index)
            def cotizacion = Float.parseFloat(fila.getAt("COT").toString())
            if (cotizacion == cotizacionDiariaDeEstano)
                flag = false
            index++
        }

        if (porcentajeEstano>=0&&porcentajeEstano<=5){
            valorPorTonelada = Float.parseFloat(fila.getAt("L5").toString())
        }

        if (porcentajeEstano>5&&porcentajeEstano<=10){
            def cot1 = Float.parseFloat(fila.getAt("L5").toString())
            def cot2 = Float.parseFloat(fila.getAt("L10").toString())
            valorPorTonelada = getPuntoRecta(porcentajeEstano,5,10,cot1,cot2)
        }

        if (porcentajeEstano>10&&porcentajeEstano<=15){
            def cot1 = Float.parseFloat(fila.getAt("L10").toString())
            def cot2 = Float.parseFloat(fila.getAt("L15").toString())
            valorPorTonelada = getPuntoRecta(porcentajeEstano,10,15,cot1,cot2)
        }

        if (porcentajeEstano>15&&porcentajeEstano<=20){
            def cot1 = Float.parseFloat(fila.getAt("L15").toString())
            def cot2 = Float.parseFloat(fila.getAt("L20").toString())
            valorPorTonelada = getPuntoRecta(porcentajeEstano,15,20,cot1,cot2)
        }

        if (porcentajeEstano>20&&porcentajeEstano<=25){
            def cot1 = Float.parseFloat(fila.getAt("L20").toString())
            def cot2 = Float.parseFloat(fila.getAt("L25").toString())
            valorPorTonelada = getPuntoRecta(porcentajeEstano,20,25,cot1,cot2)
        }

        if (porcentajeEstano>25&&porcentajeEstano<=30){
            def cot1 = Float.parseFloat(fila.getAt("L25").toString())
            def cot2 = Float.parseFloat(fila.getAt("L30").toString())
            valorPorTonelada = getPuntoRecta(porcentajeEstano,25,30,cot1,cot2)
        }

        if (porcentajeEstano>30&&porcentajeEstano<=35){
            def cot1 = Float.parseFloat(fila.getAt("L30").toString())
            def cot2 = Float.parseFloat(fila.getAt("L35").toString())
            valorPorTonelada = getPuntoRecta(porcentajeEstano,30,35,cot1,cot2)
        }

        if (porcentajeEstano>35&&porcentajeEstano<=40){
            def cot1 = Float.parseFloat(fila.getAt("L35").toString())
            def cot2 = Float.parseFloat(fila.getAt("L40").toString())
            valorPorTonelada = getPuntoRecta(porcentajeEstano,35,40,cot1,cot2)
        }

        if (porcentajeEstano>40&&porcentajeEstano<=50){
            def cot1 = Float.parseFloat(fila.getAt("L40").toString())
            def cot2 = Float.parseFloat(fila.getAt("L50").toString())
            valorPorTonelada = getPuntoRecta(porcentajeEstano,40,50,cot1,cot2)
        }

        if (porcentajeEstano>50&&porcentajeEstano<=60){
            def cot1 = Float.parseFloat(fila.getAt("L50").toString())
            def cot2 = Float.parseFloat(fila.getAt("L60").toString())
            valorPorTonelada = getPuntoRecta(porcentajeEstano,50,60,cot1,cot2)
        }

        if (porcentajeEstano>60&&porcentajeEstano<=70){
            def cot1 = Float.parseFloat(fila.getAt("L60").toString())
            def cot2 = Float.parseFloat(fila.getAt("L70").toString())
            valorPorTonelada = getPuntoRecta(porcentajeEstano,60,70,cot1,cot2)
        }

        if (porcentajeEstano>70&&porcentajeEstano<=75){
            def cot1 = Float.parseFloat(fila.getAt("L70").toString())
            def cot2 = Float.parseFloat(fila.getAt("L75").toString())
            valorPorTonelada = getPuntoRecta(porcentajeEstano,70,75,cot1,cot2)
        }

        if (porcentajeEstano>75){
            valorPorTonelada = Float.parseFloat(fila.getAt("L75").toString())
        }

        return valorPorTonelada
    }

    def getPuntoRecta(float ley, float ley1, float ley2, float cot1, float cot2){
        return (cot2-cot1)*(ley-ley1)/(ley2-ley1)+cot1
    }

    def getFilas(){

//        log.error("getValorPorTonelada: $tablaCotizacionEstanoId - $cotizacionDiariaDeEstano - $porcentajeEstano")
        def tablaCotizacionEstano = TablaCotizacionEstano.get(params.tablaCotizacionEstanoId)
        def cotizacionDiariaDeMinerales = CotizacionDiariaDeMinerales.get(params.cotizacionDiariaDeMineralesId)
        def margen = params.margen.toBigDecimal()

        def tablaJSON = new JSONArray(tablaCotizacionEstano.tablaDeCotizaciones)
        def flag = true
        def index = 0
        def filaOrig = "";
        def filaAjus = "";
        def filaOriginalLista = []

        while(index < tablaJSON.length() && flag){
            filaOrig = tablaJSON.get(index)
            def cotizacion = Float.parseFloat(filaOrig.getAt("COT").toString())
//            log.error("comparando ${cotizacion} con ${cotizacionDiariaDeMinerales.estano}")
            if (cotizacion == cotizacionDiariaDeMinerales.estano.floatValue())
                flag = false
            index++
        }

        index=0
        flag=true
        def diferencia = cotizacionDiariaDeMinerales.estano-margen

        while(index < tablaJSON.length() && flag){
            filaAjus = tablaJSON.get(index)
//            def cotizacion = Float.parseFloat(filaAjus.getAt("COT").toString())
            def cotizacion = filaAjus.getAt("COT").toString().toBigDecimal()
            log.error("comparando ${cotizacion} con ${cotizacionDiariaDeMinerales.estano} diferencia: ${diferencia}")
            if (cotizacion == diferencia)
                flag = false
            index++
        }

        render([
            filaOriginal: filaOrig.toString(),
            filaAjustada: filaAjus.toString()
        ] as JSON)
    }
}
