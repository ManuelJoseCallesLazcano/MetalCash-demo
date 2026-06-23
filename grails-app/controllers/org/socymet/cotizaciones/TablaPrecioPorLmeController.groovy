package org.socymet.cotizaciones
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class TablaPrecioPorLmeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [tablaPrecioPorLmeInstanceList: TablaPrecioPorLme.list(params), tablaPrecioPorLmeInstanceTotal: TablaPrecioPorLme.count()]
    }

    def create() {
        [tablaPrecioPorLmeInstance: new TablaPrecioPorLme(params)]
    }

    def save() {
        def tablaPrecioPorLmeInstance = new TablaPrecioPorLme(params)
        if (!tablaPrecioPorLmeInstance.save(flush: true)) {
            render(view: "create", model: [tablaPrecioPorLmeInstance: tablaPrecioPorLmeInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'tablaPrecioPorLme.label', default: 'TablaPrecioPorLme'), tablaPrecioPorLmeInstance.id])
        redirect(action: "show", id: tablaPrecioPorLmeInstance.id)
    }

    def show(Long id) {
        def tablaPrecioPorLmeInstance = TablaPrecioPorLme.get(id)
        if (!tablaPrecioPorLmeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaPrecioPorLme.label', default: 'TablaPrecioPorLme'), id])
            redirect(action: "list")
            return
        }

        [tablaPrecioPorLmeInstance: tablaPrecioPorLmeInstance]
    }

    def edit(Long id) {
        def tablaPrecioPorLmeInstance = TablaPrecioPorLme.get(id)
        if (!tablaPrecioPorLmeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaPrecioPorLme.label', default: 'TablaPrecioPorLme'), id])
            redirect(action: "list")
            return
        }

        [tablaPrecioPorLmeInstance: tablaPrecioPorLmeInstance]
    }

    def update(Long id, Long version) {
        def tablaPrecioPorLmeInstance = TablaPrecioPorLme.get(id)
        if (!tablaPrecioPorLmeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaPrecioPorLme.label', default: 'TablaPrecioPorLme'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (tablaPrecioPorLmeInstance.version > version) {
                tablaPrecioPorLmeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'tablaPrecioPorLme.label', default: 'TablaPrecioPorLme')] as Object[],
                        "Another user has updated this TablaPrecioPorLme while you were editing")
                render(view: "edit", model: [tablaPrecioPorLmeInstance: tablaPrecioPorLmeInstance])
                return
            }
        }

        tablaPrecioPorLmeInstance.properties = params

        if (!tablaPrecioPorLmeInstance.save(flush: true)) {
            render(view: "edit", model: [tablaPrecioPorLmeInstance: tablaPrecioPorLmeInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'tablaPrecioPorLme.label', default: 'TablaPrecioPorLme'), tablaPrecioPorLmeInstance.id])
        redirect(action: "show", id: tablaPrecioPorLmeInstance.id)
    }

    def delete(Long id) {
        def tablaPrecioPorLmeInstance = TablaPrecioPorLme.get(id)
        if (!tablaPrecioPorLmeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablaPrecioPorLme.label', default: 'TablaPrecioPorLme'), id])
            redirect(action: "list")
            return
        }

        try {
            tablaPrecioPorLmeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'tablaPrecioPorLme.label', default: 'TablaPrecioPorLme'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'tablaPrecioPorLme.label', default: 'TablaPrecioPorLme'), id])
            redirect(action: "show", id: id)
        }
    }

    def getValorTonelada(TablaPrecioPorLme tablaPrecioPorLme, RecepcionDeComplejo recepcionDeComplejo, BigDecimal porcentajePlata){
        def cotizacionPlataTonelada = recepcionDeComplejo.cotizacionDiariaDeMinerales.plata
        def datosPlataString = tablaPrecioPorLme.tablaDePrecios
        def datosPlataArray = JSON.parse(datosPlataString)
        def pos=0
        def porcentajePlataRecuperado=0
        def filaPlata = null
        while (params.porcentajePlataFinal.toString().toBigDecimal()>porcentajePlataRecuperado&&pos<datosPlataArray.iterator().size()){
            //System.out.println("***** porcentajePlataRecuperado=${porcentajePlataRecuperado} cuando pos=${pos}")
            filaPlata = JSON.parse(datosPlataArray[pos].toString())
            porcentajePlataRecuperado = filaPlata.getAt("leyPlata").toString().toBigDecimal()
            pos++
        }

        def porcentaje1 = (JSON.parse(datosPlataArray[pos-2].toString())).getAt("leyPlata").toString().toBigDecimal()
        def porcentaje2 = (JSON.parse(datosPlataArray[pos-1].toString())).getAt("leyPlata").toString().toBigDecimal()
        def porcentajeLME1 = (JSON.parse(datosPlataArray[pos-2].toString())).getAt("porcentajeLme").toString().toBigDecimal()
        def porcentajeLME2 = (JSON.parse(datosPlataArray[pos-1].toString())).getAt("porcentajeLme").toString().toBigDecimal()
        def vpt1 = porcentaje1*porcentajeLME1/100*cotizacionPlataTonelada*100/31.1035
        def vpt2 = porcentaje2*porcentajeLME2/100*cotizacionPlataTonelada*100/31.1035

        System.out.println("CALCULO 2: PLATA -> porcentaje1: ${porcentaje1} porcentaje2: ${porcentaje2} porcentajeLME1: ${porcentajeLME1} porcentajeLME2: ${porcentajeLME2} vpt1: ${vpt1} vpt2: ${vpt2} ")

        def precioToneladaPlata=getPuntoRecta(porcentajePlata,porcentaje1,porcentaje2,vpt1,vpt2)

        return precioToneladaPlata
    }

    def getValorPorTonelada(){
        def tablaPrecioPorLme = TablaPrecioPorLme.get(params.tablaPreciosCobre)
        def recepcionDeComplejo = RecepcionDeComplejo.get(params.recepcionId.toLong())
        def porcentajePlata = Float.parseFloat(params.porcentajePlataFinal.toString())

        def cotizacionPlataTonelada = recepcionDeComplejo.cotizacionDiariaDeMinerales.plata
        def datosPlataString = tablaPrecioPorLme.tablaDePrecios
        def datosPlataArray = JSON.parse(datosPlataString)
        def pos=0
        def porcentajePlataRecuperado=0
        def filaPlata = null
        while (params.porcentajePlataFinal.toString().toBigDecimal()>porcentajePlataRecuperado&&pos<datosPlataArray.iterator().size()){
            //System.out.println("***** porcentajePlataRecuperado=${porcentajePlataRecuperado} cuando pos=${pos}")
            filaPlata = JSON.parse(datosPlataArray[pos].toString())
            porcentajePlataRecuperado = filaPlata.getAt("leyPlata").toString().toBigDecimal()
            pos++
        }

        def porcentaje1 = (JSON.parse(datosPlataArray[pos-2].toString())).getAt("leyPlata").toString().toBigDecimal()
        def porcentaje2 = (JSON.parse(datosPlataArray[pos-1].toString())).getAt("leyPlata").toString().toBigDecimal()
        def porcentajeLME1 = (JSON.parse(datosPlataArray[pos-2].toString())).getAt("porcentajeLme").toString().toBigDecimal()
        def porcentajeLME2 = (JSON.parse(datosPlataArray[pos-1].toString())).getAt("porcentajeLme").toString().toBigDecimal()
        def vpt1 = porcentaje1*porcentajeLME1/100*cotizacionPlataTonelada*100/31.1035
        def vpt2 = porcentaje2*porcentajeLME2/100*cotizacionPlataTonelada*100/31.1035

        System.out.println("PRECIO LME:  CALCULO 2: PLATA -> porcentaje1: ${porcentaje1} porcentaje2: ${porcentaje2} porcentajeLME1: ${porcentajeLME1} porcentajeLME2: ${porcentajeLME2} vpt1: ${vpt1} vpt2: ${vpt2} ")

        def precioToneladaPlata=getPuntoRecta(porcentajePlata,porcentaje1,porcentaje2,vpt1,vpt2)

        render([vptAg: precioToneladaPlata] as JSON)
    }

    def getPuntoRecta(float ley, float ley1, float ley2, float cot1, float cot2){
        return (cot2-cot1)*(ley-ley1)/(ley2-ley1)+cot1
    }

//    def getPreciosIds = { empresaId,naturalezaMineral ->
//        def empresa = Empresa.get(empresaId.toString().toLong())
//        def precios = TablaPrecioPorLme.findAllByEmpresaAndNaturalezaMineral(empresa, naturalezaMineral,[sort: "id"])
//        def ids=""
//        precios.each {
//            ids=ids+it.id+"-"
//        }
//        return ids
//    }

    def getPreciosIds = { naturalezaMineral ->
        def precios = TablaPrecioPorLme.findAllByNaturalezaMineral(naturalezaMineral,[sort: "id"])
        def ids=""
        precios.each {
            ids=ids+it.id+"-"
        }
        return ids
    }
}
