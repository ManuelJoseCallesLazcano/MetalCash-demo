package org.socymet.cotizaciones
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.grails.web.json.JSONArray
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class TablaCotizacionPlataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [tablaCotizacionPlataInstanceList: TablaCotizacionPlata.list(params), tablaCotizacionPlataInstanceTotal: TablaCotizacionPlata.count()]
    }

    def create() {
        [tablaCotizacionPlataInstance: new TablaCotizacionPlata(params)]
    }

    def save() {
        def tablaCotizacionPlataInstance = new TablaCotizacionPlata(params)
        if (!tablaCotizacionPlataInstance.save(flush: true)) {
            render(view: "create", model: [tablaCotizacionPlataInstance: tablaCotizacionPlataInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'tablaCotizacionPlata.label', default: 'TablaCotizacionPlata'), tablaCotizacionPlataInstance.id])
        redirect(action: "show", id: tablaCotizacionPlataInstance.id)
    }

    def show(Long id) {
        def tablaCotizacionPlataInstance = TablaCotizacionPlata.get(id)
        if (!tablaCotizacionPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaCotizacionPlata.label', default: 'TablaCotizacionPlata'), id])
            redirect(action: "list")
            return
        }

        [tablaCotizacionPlataInstance: tablaCotizacionPlataInstance]
    }

    def edit(Long id) {
        def tablaCotizacionPlataInstance = TablaCotizacionPlata.get(id)
        if (!tablaCotizacionPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaCotizacionPlata.label', default: 'TablaCotizacionPlata'), id])
            redirect(action: "list")
            return
        }

        [tablaCotizacionPlataInstance: tablaCotizacionPlataInstance]
    }

    def update(Long id, Long version) {
        def tablaCotizacionPlataInstance = TablaCotizacionPlata.get(id)
        if (!tablaCotizacionPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaCotizacionPlata.label', default: 'TablaCotizacionPlata'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (tablaCotizacionPlataInstance.version > version) {
                tablaCotizacionPlataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'tablaCotizacionPlata.label', default: 'TablaCotizacionPlata')] as Object[],
                        "Another user has updated this TablaCotizacionPlata while you were editing")
                render(view: "edit", model: [tablaCotizacionPlataInstance: tablaCotizacionPlataInstance])
                return
            }
        }

        tablaCotizacionPlataInstance.properties = params

        if (!tablaCotizacionPlataInstance.save(flush: true)) {
            render(view: "edit", model: [tablaCotizacionPlataInstance: tablaCotizacionPlataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'tablaCotizacionPlata.label', default: 'TablaCotizacionPlata'), tablaCotizacionPlataInstance.id])
        redirect(action: "show", id: tablaCotizacionPlataInstance.id)
    }

    def delete(Long id) {
        def tablaCotizacionPlataInstance = TablaCotizacionPlata.get(id)
        if (!tablaCotizacionPlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaCotizacionPlata.label', default: 'TablaCotizacionPlata'), id])
            redirect(action: "list")
            return
        }

        try {
            tablaCotizacionPlataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'tablaCotizacionPlata.label', default: 'TablaCotizacionPlata'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'tablaCotizacionPlata.label', default: 'TablaCotizacionPlata'), id])
            redirect(action: "show", id: id)
        }
    }

    def getValorPorTonelada(){
        def tablaCotizacionPlata = TablaCotizacionPlata.get(params.tablaCotizacionPlata)
        def cotizacionDiariaDePlata = Float.parseFloat(params.cotizacionDiariaDePlata.toString())
        def porcentajePlata = Float.parseFloat(params.porcentajePlata.toString())

        def tablaJSON = new JSONArray(tablaCotizacionPlata.tablaDeCotizaciones)
        def flag = true
        def index = 0
        def fila = "";
        def valorPorTonelada = 0

        while(index < tablaJSON.length() && flag){
            fila = tablaJSON.get(index)
            def cotizacion = Float.parseFloat(fila.getAt("COT").toString())
            if (cotizacion == cotizacionDiariaDePlata)
                flag = false
            index++
        }

        if (porcentajePlata>=0&&porcentajePlata<=10){
            valorPorTonelada = Float.parseFloat(fila.getAt("L10").toString())
        }

        if (porcentajePlata>10&&porcentajePlata<=20){
            def cot1 = Float.parseFloat(fila.getAt("L10").toString())
            def cot2 = Float.parseFloat(fila.getAt("L20").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,10,20,cot1,cot2)
        }

        if (porcentajePlata>20&&porcentajePlata<=30){
            def cot1 = Float.parseFloat(fila.getAt("L20").toString())
            def cot2 = Float.parseFloat(fila.getAt("L30").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,20,30,cot1,cot2)
        }

        if (porcentajePlata>30&&porcentajePlata<=40){
            def cot1 = Float.parseFloat(fila.getAt("L30").toString())
            def cot2 = Float.parseFloat(fila.getAt("L40").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,30,40,cot1,cot2)
        }

        if (porcentajePlata>40&&porcentajePlata<=50){
            def cot1 = Float.parseFloat(fila.getAt("L40").toString())
            def cot2 = Float.parseFloat(fila.getAt("L50").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,40,50,cot1,cot2)
        }

        if (porcentajePlata>50&&porcentajePlata<=60){
            def cot1 = Float.parseFloat(fila.getAt("L50").toString())
            def cot2 = Float.parseFloat(fila.getAt("L60").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,50,60,cot1,cot2)
        }

        if (porcentajePlata>60&&porcentajePlata<=70){
            def cot1 = Float.parseFloat(fila.getAt("L60").toString())
            def cot2 = Float.parseFloat(fila.getAt("L70").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,60,70,cot1,cot2)
        }

        if (porcentajePlata>70&&porcentajePlata<=80){
            def cot1 = Float.parseFloat(fila.getAt("L70").toString())
            def cot2 = Float.parseFloat(fila.getAt("L80").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,70,80,cot1,cot2)
        }

        if (porcentajePlata>80&&porcentajePlata<=90){
            def cot1 = Float.parseFloat(fila.getAt("L80").toString())
            def cot2 = Float.parseFloat(fila.getAt("L90").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,80,90,cot1,cot2)
        }

        if (porcentajePlata>90&&porcentajePlata<=100){
            def cot1 = Float.parseFloat(fila.getAt("L90").toString())
            def cot2 = Float.parseFloat(fila.getAt("L100").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,90,100,cot1,cot2)
        }

        if (porcentajePlata>100&&porcentajePlata<=150){
            def cot1 = Float.parseFloat(fila.getAt("L100").toString())
            def cot2 = Float.parseFloat(fila.getAt("L150").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,100,150,cot1,cot2)
        }

        if (porcentajePlata>150&&porcentajePlata<=200){
            def cot1 = Float.parseFloat(fila.getAt("L150").toString())
            def cot2 = Float.parseFloat(fila.getAt("L200").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,150,200,cot1,cot2)
        }

        if (porcentajePlata>250&&porcentajePlata<=300){
            def cot1 = Float.parseFloat(fila.getAt("L250").toString())
            def cot2 = Float.parseFloat(fila.getAt("L300").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,250,300,cot1,cot2)
        }

        if (porcentajePlata>300&&porcentajePlata<=400){
            def cot1 = Float.parseFloat(fila.getAt("L300").toString())
            def cot2 = Float.parseFloat(fila.getAt("L400").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,300,400,cot1,cot2)
        }

        if (porcentajePlata>400&&porcentajePlata<=500){
            def cot1 = Float.parseFloat(fila.getAt("L400").toString())
            def cot2 = Float.parseFloat(fila.getAt("L500").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,400,500,cot1,cot2)
        }

        if (porcentajePlata>500&&porcentajePlata<=600){
            def cot1 = Float.parseFloat(fila.getAt("L500").toString())
            def cot2 = Float.parseFloat(fila.getAt("L600").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,500,600,cot1,cot2)
        }

        if (porcentajePlata>600&&porcentajePlata<=700){
            def cot1 = Float.parseFloat(fila.getAt("L600").toString())
            def cot2 = Float.parseFloat(fila.getAt("L700").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,600,700,cot1,cot2)
        }

        if (porcentajePlata>700&&porcentajePlata<=800){
            def cot1 = Float.parseFloat(fila.getAt("L700").toString())
            def cot2 = Float.parseFloat(fila.getAt("L800").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,700,800,cot1,cot2)
        }

        if (porcentajePlata>800&&porcentajePlata<=900){
            def cot1 = Float.parseFloat(fila.getAt("L800").toString())
            def cot2 = Float.parseFloat(fila.getAt("L900").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,800,900,cot1,cot2)
        }

        if (porcentajePlata>900&&porcentajePlata<=1000){
            def cot1 = Float.parseFloat(fila.getAt("L900").toString())
            def cot2 = Float.parseFloat(fila.getAt("L1000").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,900,1000,cot1,cot2)
        }

        if (porcentajePlata>1000){
            valorPorTonelada = Float.parseFloat(fila.getAt("L1000").toString())
        }

        render([vpt: valorPorTonelada.toString()] as JSON)
    }

    def getValorPorToneladaPresupuesto(Integer tablaCotizacionPlataId, Float cotizacionDiariaDePlata, Float porcentajePlata){
        def tablaCotizacionPlata = TablaCotizacionPlata.get(tablaCotizacionPlataId)

        def tablaJSON = new JSONArray(tablaCotizacionPlata.tablaDeCotizaciones)
        def flag = true
        def index = 0
        def fila = "";
        def valorPorTonelada = 0

        while(index < tablaJSON.length() && flag){
            fila = tablaJSON.get(index)
            def cotizacion = Float.parseFloat(fila.getAt("COT").toString())
            if (cotizacion == cotizacionDiariaDePlata)
                flag = false
            index++
        }

        if (porcentajePlata>=0&&porcentajePlata<=10){
            valorPorTonelada = Float.parseFloat(fila.getAt("L10").toString())
        }

        if (porcentajePlata>10&&porcentajePlata<=20){
            def cot1 = Float.parseFloat(fila.getAt("L10").toString())
            def cot2 = Float.parseFloat(fila.getAt("L20").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,10,20,cot1,cot2)
        }

        if (porcentajePlata>20&&porcentajePlata<=30){
            def cot1 = Float.parseFloat(fila.getAt("L20").toString())
            def cot2 = Float.parseFloat(fila.getAt("L30").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,20,30,cot1,cot2)
        }

        if (porcentajePlata>30&&porcentajePlata<=40){
            def cot1 = Float.parseFloat(fila.getAt("L30").toString())
            def cot2 = Float.parseFloat(fila.getAt("L40").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,30,40,cot1,cot2)
        }

        if (porcentajePlata>40&&porcentajePlata<=50){
            def cot1 = Float.parseFloat(fila.getAt("L40").toString())
            def cot2 = Float.parseFloat(fila.getAt("L50").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,40,50,cot1,cot2)
        }

        if (porcentajePlata>50&&porcentajePlata<=60){
            def cot1 = Float.parseFloat(fila.getAt("L50").toString())
            def cot2 = Float.parseFloat(fila.getAt("L60").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,50,60,cot1,cot2)
        }

        if (porcentajePlata>60&&porcentajePlata<=70){
            def cot1 = Float.parseFloat(fila.getAt("L60").toString())
            def cot2 = Float.parseFloat(fila.getAt("L70").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,60,70,cot1,cot2)
        }

        if (porcentajePlata>70&&porcentajePlata<=80){
            def cot1 = Float.parseFloat(fila.getAt("L70").toString())
            def cot2 = Float.parseFloat(fila.getAt("L80").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,70,80,cot1,cot2)
        }

        if (porcentajePlata>80&&porcentajePlata<=90){
            def cot1 = Float.parseFloat(fila.getAt("L80").toString())
            def cot2 = Float.parseFloat(fila.getAt("L90").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,80,90,cot1,cot2)
        }

        if (porcentajePlata>90&&porcentajePlata<=100){
            def cot1 = Float.parseFloat(fila.getAt("L90").toString())
            def cot2 = Float.parseFloat(fila.getAt("L100").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,90,100,cot1,cot2)
        }

        if (porcentajePlata>100&&porcentajePlata<=150){
            def cot1 = Float.parseFloat(fila.getAt("L100").toString())
            def cot2 = Float.parseFloat(fila.getAt("L150").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,100,150,cot1,cot2)
        }

        if (porcentajePlata>150&&porcentajePlata<=200){
            def cot1 = Float.parseFloat(fila.getAt("L150").toString())
            def cot2 = Float.parseFloat(fila.getAt("L200").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,150,200,cot1,cot2)
        }

        if (porcentajePlata>250&&porcentajePlata<=300){
            def cot1 = Float.parseFloat(fila.getAt("L250").toString())
            def cot2 = Float.parseFloat(fila.getAt("L300").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,250,300,cot1,cot2)
        }

        if (porcentajePlata>300&&porcentajePlata<=400){
            def cot1 = Float.parseFloat(fila.getAt("L300").toString())
            def cot2 = Float.parseFloat(fila.getAt("L400").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,300,400,cot1,cot2)
        }

        if (porcentajePlata>400&&porcentajePlata<=500){
            def cot1 = Float.parseFloat(fila.getAt("L400").toString())
            def cot2 = Float.parseFloat(fila.getAt("L500").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,400,500,cot1,cot2)
        }

        if (porcentajePlata>500&&porcentajePlata<=600){
            def cot1 = Float.parseFloat(fila.getAt("L500").toString())
            def cot2 = Float.parseFloat(fila.getAt("L600").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,500,600,cot1,cot2)
        }

        if (porcentajePlata>600&&porcentajePlata<=700){
            def cot1 = Float.parseFloat(fila.getAt("L600").toString())
            def cot2 = Float.parseFloat(fila.getAt("L700").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,600,700,cot1,cot2)
        }

        if (porcentajePlata>700&&porcentajePlata<=800){
            def cot1 = Float.parseFloat(fila.getAt("L700").toString())
            def cot2 = Float.parseFloat(fila.getAt("L800").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,700,800,cot1,cot2)
        }

        if (porcentajePlata>800&&porcentajePlata<=900){
            def cot1 = Float.parseFloat(fila.getAt("L800").toString())
            def cot2 = Float.parseFloat(fila.getAt("L900").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,800,900,cot1,cot2)
        }

        if (porcentajePlata>900&&porcentajePlata<=1000){
            def cot1 = Float.parseFloat(fila.getAt("L900").toString())
            def cot2 = Float.parseFloat(fila.getAt("L1000").toString())
            valorPorTonelada = getPuntoRecta(porcentajePlata,900,1000,cot1,cot2)
        }

        if (porcentajePlata>1000){
            valorPorTonelada = Float.parseFloat(fila.getAt("L1000").toString())
        }

        return valorPorTonelada
    }

    def getPuntoRecta(float ley, float ley1, float ley2, float cot1, float cot2){
        return (cot2-cot1)*(ley-ley1)/(ley2-ley1)+cot1
    }
}
