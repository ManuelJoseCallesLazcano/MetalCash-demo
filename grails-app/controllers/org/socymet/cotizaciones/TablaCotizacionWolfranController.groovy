package org.socymet.cotizaciones
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.grails.web.json.JSONArray
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class TablaCotizacionWolfranController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [tablaCotizacionWolfranInstanceList: TablaCotizacionWolfran.list(params), tablaCotizacionWolfranInstanceTotal: TablaCotizacionWolfran.count()]
    }

    def create() {
        [tablaCotizacionWolfranInstance: new TablaCotizacionWolfran(params)]
    }

    def save() {
        def tablaCotizacionWolfranInstance = new TablaCotizacionWolfran(params)
        if (!tablaCotizacionWolfranInstance.save(flush: true)) {
            render(view: "create", model: [tablaCotizacionWolfranInstance: tablaCotizacionWolfranInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'tablaCotizacionWolfran.label', default: 'TablaCotizacionWolfran'), tablaCotizacionWolfranInstance.id])
        redirect(action: "show", id: tablaCotizacionWolfranInstance.id)
    }

    def show(Long id) {
        def tablaCotizacionWolfranInstance = TablaCotizacionWolfran.get(id)
        if (!tablaCotizacionWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaCotizacionWolfran.label', default: 'TablaCotizacionWolfran'), id])
            redirect(action: "list")
            return
        }

        [tablaCotizacionWolfranInstance: tablaCotizacionWolfranInstance]
    }

    def edit(Long id) {
        def tablaCotizacionWolfranInstance = TablaCotizacionWolfran.get(id)
        if (!tablaCotizacionWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaCotizacionWolfran.label', default: 'TablaCotizacionWolfran'), id])
            redirect(action: "list")
            return
        }

        [tablaCotizacionWolfranInstance: tablaCotizacionWolfranInstance]
    }

    def update(Long id, Long version) {
        def tablaCotizacionWolfranInstance = TablaCotizacionWolfran.get(id)
        if (!tablaCotizacionWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaCotizacionWolfran.label', default: 'TablaCotizacionWolfran'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (tablaCotizacionWolfranInstance.version > version) {
                tablaCotizacionWolfranInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'tablaCotizacionWolfran.label', default: 'TablaCotizacionWolfran')] as Object[],
                        "Another user has updated this TablaCotizacionWolfran while you were editing")
                render(view: "edit", model: [tablaCotizacionWolfranInstance: tablaCotizacionWolfranInstance])
                return
            }
        }

        tablaCotizacionWolfranInstance.properties = params

        if (!tablaCotizacionWolfranInstance.save(flush: true)) {
            render(view: "edit", model: [tablaCotizacionWolfranInstance: tablaCotizacionWolfranInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'tablaCotizacionWolfran.label', default: 'TablaCotizacionWolfran'), tablaCotizacionWolfranInstance.id])
        redirect(action: "show", id: tablaCotizacionWolfranInstance.id)
    }

    def delete(Long id) {
        def tablaCotizacionWolfranInstance = TablaCotizacionWolfran.get(id)
        if (!tablaCotizacionWolfranInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaCotizacionWolfran.label', default: 'TablaCotizacionWolfran'), id])
            redirect(action: "list")
            return
        }

        try {
            tablaCotizacionWolfranInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'tablaCotizacionWolfran.label', default: 'TablaCotizacionWolfran'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'tablaCotizacionWolfran.label', default: 'TablaCotizacionWolfran'), id])
            redirect(action: "show", id: id)
        }
    }

    def getValorPorTonelada(){
        def tablaCotizacionWolfran = TablaCotizacionWolfran.get(params.tablaCotizacionWolfran)
        def porcentajeWolfran = Float.parseFloat(params.porcentajeWolfran.toString())

        def tablaJSON = new JSONArray(tablaCotizacionWolfran.tablaDeCotizaciones)
        def valorPorTonelada = 0
        def limiteInferior = porcentajeWolfran.intValue()
        def limiteSuperior = limiteInferior + 1

        def precioMinimo = tablaJSON.get(0).getAt("PRECIO")
        def precioMaximo = tablaJSON.get(65).getAt("PRECIO")

        if (porcentajeWolfran<=5)
            valorPorTonelada = precioMinimo
        if (porcentajeWolfran>=70)
            valorPorTonelada = precioMaximo
        if (porcentajeWolfran>5&&porcentajeWolfran<70){
            def posicion = limiteInferior.intValue() - 5
            def precioInferior = Float.parseFloat(tablaJSON.get(posicion).getAt("PRECIO").toString())
            def precioSuperior = Float.parseFloat(tablaJSON.get(posicion+1).getAt("PRECIO").toString())
            log.error("LIM INF: ${limiteInferior} LIM SUP: ${limiteSuperior} PRECIO INF: ${precioInferior} PRECIO SUP: ${precioSuperior} ")
            valorPorTonelada = getPuntoRecta(porcentajeWolfran,limiteInferior,limiteSuperior,precioInferior,precioSuperior)
        }

        render([vpt: valorPorTonelada.toString()] as JSON)
    }

    def getValorPorToneladaPresupuesto(Integer tablaCotizacionWolfranId, Float porcentajeWolfran){
        def tablaCotizacionWolfran = TablaCotizacionWolfran.get(tablaCotizacionWolfranId)

        def tablaJSON = new JSONArray(tablaCotizacionWolfran.tablaDeCotizaciones)
        def valorPorTonelada = 0
        def limiteInferior = porcentajeWolfran.intValue()
        def limiteSuperior = limiteInferior + 1

        def precioMinimo = tablaJSON.get(0).getAt("PRECIO")
        def precioMaximo = tablaJSON.get(65).getAt("PRECIO")

        if (porcentajeWolfran<=5)
            valorPorTonelada = precioMinimo
        if (porcentajeWolfran>=70)
            valorPorTonelada = precioMaximo
        if (porcentajeWolfran>5&&porcentajeWolfran<70){
            def posicion = limiteInferior.intValue() - 5
            def precioInferior = Float.parseFloat(tablaJSON.get(posicion).getAt("PRECIO").toString())
            def precioSuperior = Float.parseFloat(tablaJSON.get(posicion+1).getAt("PRECIO").toString())
            log.error("LIM INF: ${limiteInferior} LIM SUP: ${limiteSuperior} PRECIO INF: ${precioInferior} PRECIO SUP: ${precioSuperior} ")
            valorPorTonelada = getPuntoRecta(porcentajeWolfran,limiteInferior,limiteSuperior,precioInferior,precioSuperior)
        }
        
        return valorPorTonelada
    }

    def getPuntoRecta(double ley, double ley1, double ley2, double cot1, double cot2){
        return (cot2-cot1)*(ley-ley1)/(ley2-ley1)+cot1
    }
}
