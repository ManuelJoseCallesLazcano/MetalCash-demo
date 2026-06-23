package org.socymet.cotizaciones
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.grails.web.json.JSONArray
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class TablaCotizacionAntimonioController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [tablaCotizacionAntimonioInstanceList: TablaCotizacionAntimonio.list(params), tablaCotizacionAntimonioInstanceTotal: TablaCotizacionAntimonio.count()]
    }

    def create() {
        [tablaCotizacionAntimonioInstance: new TablaCotizacionAntimonio(params)]
    }

    def save() {
        def tablaCotizacionAntimonioInstance = new TablaCotizacionAntimonio(params)
        if (!tablaCotizacionAntimonioInstance.save(flush: true)) {
            render(view: "create", model: [tablaCotizacionAntimonioInstance: tablaCotizacionAntimonioInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'tablaCotizacionAntimonio.label', default: 'TablaCotizacionAntimonio'), tablaCotizacionAntimonioInstance.id])
        redirect(action: "show", id: tablaCotizacionAntimonioInstance.id)
    }

    def show(Long id) {
        def tablaCotizacionAntimonioInstance = TablaCotizacionAntimonio.get(id)
        if (!tablaCotizacionAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaCotizacionAntimonio.label', default: 'TablaCotizacionAntimonio'), id])
            redirect(action: "list")
            return
        }

        [tablaCotizacionAntimonioInstance: tablaCotizacionAntimonioInstance]
    }

    def edit(Long id) {
        def tablaCotizacionAntimonioInstance = TablaCotizacionAntimonio.get(id)
        if (!tablaCotizacionAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaCotizacionAntimonio.label', default: 'TablaCotizacionAntimonio'), id])
            redirect(action: "list")
            return
        }

        [tablaCotizacionAntimonioInstance: tablaCotizacionAntimonioInstance]
    }

    def update(Long id, Long version) {
        def tablaCotizacionAntimonioInstance = TablaCotizacionAntimonio.get(id)
        if (!tablaCotizacionAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaCotizacionAntimonio.label', default: 'TablaCotizacionAntimonio'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (tablaCotizacionAntimonioInstance.version > version) {
                tablaCotizacionAntimonioInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'tablaCotizacionAntimonio.label', default: 'TablaCotizacionAntimonio')] as Object[],
                        "Another user has updated this TablaCotizacionAntimonio while you were editing")
                render(view: "edit", model: [tablaCotizacionAntimonioInstance: tablaCotizacionAntimonioInstance])
                return
            }
        }

        tablaCotizacionAntimonioInstance.properties = params

        if (!tablaCotizacionAntimonioInstance.save(flush: true)) {
            render(view: "edit", model: [tablaCotizacionAntimonioInstance: tablaCotizacionAntimonioInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'tablaCotizacionAntimonio.label', default: 'TablaCotizacionAntimonio'), tablaCotizacionAntimonioInstance.id])
        redirect(action: "show", id: tablaCotizacionAntimonioInstance.id)
    }

    def delete(Long id) {
        def tablaCotizacionAntimonioInstance = TablaCotizacionAntimonio.get(id)
        if (!tablaCotizacionAntimonioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaCotizacionAntimonio.label', default: 'TablaCotizacionAntimonio'), id])
            redirect(action: "list")
            return
        }

        try {
            tablaCotizacionAntimonioInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'tablaCotizacionAntimonio.label', default: 'TablaCotizacionAntimonio'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'tablaCotizacionAntimonio.label', default: 'TablaCotizacionAntimonio'), id])
            redirect(action: "show", id: id)
        }
    }

    def getValorPorTonelada(){
        def tablaCotizacionAntimonio = TablaCotizacionAntimonio.get(params.tablaCotizacionAntimonio)
        def porcentajeAntimonio = Float.parseFloat(params.porcentajeAntimonio.toString())

        def tablaJSON = new JSONArray(tablaCotizacionAntimonio.tablaDeCotizaciones)
        def valorPorTonelada = 0
        def limiteInferior = porcentajeAntimonio.intValue()
        def limiteSuperior = limiteInferior + 1

        def precioMinimo = tablaJSON.get(0).getAt("PRECIO")
        def precioMaximo = tablaJSON.get(65).getAt("PRECIO")

        if (porcentajeAntimonio<=5)
            valorPorTonelada = precioMinimo
        if (porcentajeAntimonio>=70)
            valorPorTonelada = precioMaximo
        if (porcentajeAntimonio>5&&porcentajeAntimonio<70){
            def posicion = limiteInferior.intValue() - 5
            def precioInferior = Float.parseFloat(tablaJSON.get(posicion).getAt("PRECIO").toString())
            def precioSuperior = Float.parseFloat(tablaJSON.get(posicion+1).getAt("PRECIO").toString())
            log.error("LIM INF: ${limiteInferior} LIM SUP: ${limiteSuperior} PRECIO INF: ${precioInferior} PRECIO SUP: ${precioSuperior} ")
            valorPorTonelada = getPuntoRecta(porcentajeAntimonio,limiteInferior,limiteSuperior,precioInferior,precioSuperior)
        }

        render([vpt: valorPorTonelada.toString()] as JSON)
    }
    def getValorPorToneladaPresupuesto(Integer tablaCotizacionAntimonioId, Float porcentajeAntimonio){
        def tablaCotizacionAntimonio = TablaCotizacionAntimonio.get(tablaCotizacionAntimonioId)

        def tablaJSON = new JSONArray(tablaCotizacionAntimonio.tablaDeCotizaciones)
        def valorPorTonelada = 0
        def limiteInferior = porcentajeAntimonio.intValue()
        def limiteSuperior = limiteInferior + 1

        def precioMinimo = tablaJSON.get(0).getAt("PRECIO")
        def precioMaximo = tablaJSON.get(65).getAt("PRECIO")

        if (porcentajeAntimonio<=5)
            valorPorTonelada = precioMinimo
        if (porcentajeAntimonio>=70)
            valorPorTonelada = precioMaximo
        if (porcentajeAntimonio>5&&porcentajeAntimonio<70){
            def posicion = limiteInferior.intValue() - 5
            def precioInferior = Float.parseFloat(tablaJSON.get(posicion).getAt("PRECIO").toString())
            def precioSuperior = Float.parseFloat(tablaJSON.get(posicion+1).getAt("PRECIO").toString())
            log.error("LIM INF: ${limiteInferior} LIM SUP: ${limiteSuperior} PRECIO INF: ${precioInferior} PRECIO SUP: ${precioSuperior} ")
            valorPorTonelada = getPuntoRecta(porcentajeAntimonio,limiteInferior,limiteSuperior,precioInferior,precioSuperior)
        }

        return valorPorTonelada
    }

    def getPuntoRecta(double ley, double ley1, double ley2, double cot1, double cot2){
        return (cot2-cot1)*(ley-ley1)/(ley2-ley1)+cot1
    }
}
