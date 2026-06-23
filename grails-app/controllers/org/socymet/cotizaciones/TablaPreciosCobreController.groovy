package org.socymet.cotizaciones
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.grails.web.json.JSONArray
import org.socymet.proveedor.Empresa
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class TablaPreciosCobreController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [tablaPreciosCobreInstanceList: TablaPreciosCobre.list(params), tablaPreciosCobreInstanceTotal: TablaPreciosCobre.count()]
    }

    def create() {
        [tablaPreciosCobreInstance: new TablaPreciosCobre(params)]
    }

    def save() {
        def tablaPreciosCobreInstance = new TablaPreciosCobre(params)
        if (!tablaPreciosCobreInstance.save(flush: true)) {
            render(view: "create", model: [tablaPreciosCobreInstance: tablaPreciosCobreInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'tablaPreciosCobre.label', default: 'TablaPreciosCobre'), tablaPreciosCobreInstance.id])
        redirect(action: "show", id: tablaPreciosCobreInstance.id)
    }

    def show(Long id) {
        def tablaPreciosCobreInstance = TablaPreciosCobre.get(id)
        if (!tablaPreciosCobreInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaPreciosCobre.label', default: 'TablaPreciosCobre'), id])
            redirect(action: "list")
            return
        }

        [tablaPreciosCobreInstance: tablaPreciosCobreInstance]
    }

    def edit(Long id) {
        def tablaPreciosCobreInstance = TablaPreciosCobre.get(id)
        if (!tablaPreciosCobreInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaPreciosCobre.label', default: 'TablaPreciosCobre'), id])
            redirect(action: "list")
            return
        }

        [tablaPreciosCobreInstance: tablaPreciosCobreInstance]
    }

    def update(Long id, Long version) {
        def tablaPreciosCobreInstance = TablaPreciosCobre.get(id)
        if (!tablaPreciosCobreInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaPreciosCobre.label', default: 'TablaPreciosCobre'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (tablaPreciosCobreInstance.version > version) {
                tablaPreciosCobreInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'tablaPreciosCobre.label', default: 'TablaPreciosCobre')] as Object[],
                        "Another user has updated this TablaPreciosCobre while you were editing")
                render(view: "edit", model: [tablaPreciosCobreInstance: tablaPreciosCobreInstance])
                return
            }
        }

        tablaPreciosCobreInstance.properties = params

        if (!tablaPreciosCobreInstance.save(flush: true)) {
            render(view: "edit", model: [tablaPreciosCobreInstance: tablaPreciosCobreInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'tablaPreciosCobre.label', default: 'TablaPreciosCobre'), tablaPreciosCobreInstance.id])
        redirect(action: "show", id: tablaPreciosCobreInstance.id)
    }

    def delete(Long id) {
        def tablaPreciosCobreInstance = TablaPreciosCobre.get(id)
        if (!tablaPreciosCobreInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaPreciosCobre.label', default: 'TablaPreciosCobre'), id])
            redirect(action: "list")
            return
        }

        try {
            tablaPreciosCobreInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'tablaPreciosCobre.label', default: 'TablaPreciosCobre'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'tablaPreciosCobre.label', default: 'TablaPreciosCobre'), id])
            redirect(action: "show", id: id)
        }
    }

    def getValorTonelada(TablaPreciosCobre tablaPreciosCobre, BigDecimal porcentajeCobre){
        def tablaJSON = new JSONArray(tablaPreciosCobre.tablaDePrecios)
        def valorPorTonelada = 0
        def limiteInferior = porcentajeCobre.intValue()
        def limiteSuperior = limiteInferior + 1

        def precioMinimo = tablaJSON.get(0).getAt("vpt")
        def precioMaximo = tablaJSON.get(tablaJSON.length()-1).getAt("vpt")

        if (porcentajeCobre<=tablaJSON.get(0).getAt("ley"))
            valorPorTonelada = precioMinimo
        if (porcentajeCobre>=tablaJSON.get(tablaJSON.length()-1).getAt("ley"))
            valorPorTonelada = precioMaximo
        if (porcentajeCobre>tablaJSON.get(0).getAt("ley")&&porcentajeCobre<tablaJSON.get(tablaJSON.length()-1).getAt("ley")){
            def posicion = limiteInferior.intValue() - 5
            def precioInferior = Float.parseFloat(tablaJSON.get(posicion).getAt("vpt").toString())
            def precioSuperior = Float.parseFloat(tablaJSON.get(posicion+1).getAt("vpt").toString())
            log.error("LIM INF: ${limiteInferior} LIM SUP: ${limiteSuperior} PRECIO INF: ${precioInferior} PRECIO SUP: ${precioSuperior} PORCENTAJE COBRE: ${porcentajeCobre} ")
            valorPorTonelada = getPuntoRecta(porcentajeCobre.toFloat(),limiteInferior.toFloat(),limiteSuperior.toFloat(),precioInferior,precioSuperior)
            log.error("VALOR POR TONELADA COBRE: ${valorPorTonelada}")
        }

        return valorPorTonelada
    }

    def getValorPorTonelada(){
        def tablaPreciosCobre = TablaPreciosCobre.get(params.tablaPreciosCobre)
        def porcentajeCobre = Float.parseFloat(params.porcentajeCobre.toString())

        def tablaJSON = new JSONArray(tablaPreciosCobre.tablaDePrecios)
        def valorPorTonelada = 0
        def limiteInferior = porcentajeCobre.intValue()
        def limiteSuperior = limiteInferior + 1

        def precioMinimo = tablaJSON.get(0).getAt("vpt")
        def precioMaximo = tablaJSON.get(tablaJSON.length()-1).getAt("vpt")

        if (porcentajeCobre<=tablaJSON.get(0).getAt("ley"))
            valorPorTonelada = precioMinimo
        if (porcentajeCobre>=tablaJSON.get(tablaJSON.length()-1).getAt("ley"))
            valorPorTonelada = precioMaximo
        if (porcentajeCobre>tablaJSON.get(0).getAt("ley")&&porcentajeCobre<tablaJSON.get(tablaJSON.length()-1).getAt("ley")){
            def posicion = limiteInferior.intValue() - 5
            def precioInferior = Float.parseFloat(tablaJSON.get(posicion).getAt("vpt").toString())
            def precioSuperior = Float.parseFloat(tablaJSON.get(posicion+1).getAt("vpt").toString())
            log.error("LIM INF: ${limiteInferior} LIM SUP: ${limiteSuperior} PRECIO INF: ${precioInferior} PRECIO SUP: ${precioSuperior} PORCENTAJE COBRE: ${porcentajeCobre} ")
            valorPorTonelada = getPuntoRecta(porcentajeCobre,limiteInferior,limiteSuperior,precioInferior,precioSuperior)
        }

        render([vpt: valorPorTonelada.toString()] as JSON)
    }

    def getPuntoRecta(float ley, float ley1, float ley2, float cot1, float cot2){
        return (cot2-cot1)*(ley-ley1)/(ley2-ley1)+cot1
    }

    def getTablasIds = { empresaId ->
        def empresa = Empresa.get(empresaId.toString().toLong())
        def tablas = TablaPreciosCobre.findAllByEmpresa(empresa,[sort: "id"])
        //<g:select id="tablaComplejo" name="tablaComplejo.id" from="${org.socymet.cotizaciones.TablaOrigenCotizacionesComplejo.list()}" optionKey="id" value="${controlCalidadComplejoInstance?.tablaComplejo?.id}" class="many-to-one"/>
        //render g.select(name: "tablaComplejo.id",id: "tablaComplejo",from: tablas,optionKey: "id",value: "${controlCalidadComplejoInstance?.tablaComplejo?.id}",class: "many-to-one")
        //render g.select(name: "tablaComplejo.id",id: "tablaComplejo",from: tablas,optionKey: "id",class: "many-to-one")
        def ids=""
        tablas.each {
            ids=ids+it.id+"-"
        }
        return ids
    }

    def getValorPorToneladaCobreCotizacion(CotizacionDiariaDeMinerales cotizacionDiariaDeMinerales, Long tablaId, BigDecimal porcentajeCobre){
        if (porcentajeCobre==0)
            return 0

        def tablaPreciosCobre = TablaPreciosCobre.get(tablaId)

        def tablaJSON = new JSONArray(tablaPreciosCobre.tablaDePrecios)
        def valorPorTonelada = 0
        def limiteInferior = porcentajeCobre.intValue()
        def limiteSuperior = limiteInferior + 1

        def precioMinimo = tablaJSON.get(0).getAt("vpt")
        def precioMaximo = tablaJSON.get(tablaJSON.length()-1).getAt("vpt")

        if (porcentajeCobre<=tablaJSON.get(0).getAt("ley"))
            valorPorTonelada = precioMinimo
        if (porcentajeCobre>=tablaJSON.get(tablaJSON.length()-1).getAt("ley"))
            valorPorTonelada = precioMaximo
        if (porcentajeCobre>tablaJSON.get(0).getAt("ley")&&porcentajeCobre<tablaJSON.get(tablaJSON.length()-1).getAt("ley")){
            def posicion = limiteInferior.intValue() - 5
            def precioInferior = Float.parseFloat(tablaJSON.get(posicion).getAt("vpt").toString())
            def precioSuperior = Float.parseFloat(tablaJSON.get(posicion+1).getAt("vpt").toString())
            log.error("LIM INF: ${limiteInferior} LIM SUP: ${limiteSuperior} PRECIO INF: ${precioInferior} PRECIO SUP: ${precioSuperior} ")
            valorPorTonelada = getPuntoRecta(porcentajeCobre,limiteInferior,limiteSuperior,precioInferior,precioSuperior)
        }

        return valorPorTonelada
    }
}
