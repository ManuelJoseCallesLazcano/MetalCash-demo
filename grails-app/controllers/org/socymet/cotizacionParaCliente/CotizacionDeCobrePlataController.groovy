package org.socymet.cotizacionParaCliente
import grails.converters.JSON
import grails.gorm.transactions.Transactional

import org.socymet.cotizaciones.CotizacionDiariaDeMinerales
import org.socymet.cotizaciones.TablaPreciosCobreController
import org.socymet.cotizaciones.TerminosDeContrato
import org.socymet.cotizaciones.TerminosDeContratoController
import org.springframework.dao.DataIntegrityViolationException

import java.text.DecimalFormat

@Transactional
class CotizacionDeCobrePlataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [cotizacionDeCobrePlataInstanceList: CotizacionDeCobrePlata.list(params), cotizacionDeCobrePlataInstanceTotal: CotizacionDeCobrePlata.count()]
    }

    def create() {
        [cotizacionDeCobrePlataInstance: new CotizacionDeCobrePlata(params)]
    }

    def save() {
        def cotizacionDeCobrePlataInstance = new CotizacionDeCobrePlata(params)
        if (!cotizacionDeCobrePlataInstance.save(flush: true)) {
            render(view: "create", model: [cotizacionDeCobrePlataInstance: cotizacionDeCobrePlataInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'cotizacionDeCobrePlata.label', default: 'CotizacionDeCobrePlata'), cotizacionDeCobrePlataInstance.id])
        redirect(action: "show", id: cotizacionDeCobrePlataInstance.id)
    }

    def show(Long id) {
        def cotizacionDeCobrePlataInstance = CotizacionDeCobrePlata.get(id)
        if (!cotizacionDeCobrePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cotizacionDeCobrePlata.label', default: 'CotizacionDeCobrePlata'), id])
            redirect(action: "list")
            return
        }

        [cotizacionDeCobrePlataInstance: cotizacionDeCobrePlataInstance]
    }

    def edit(Long id) {
        def cotizacionDeCobrePlataInstance = CotizacionDeCobrePlata.get(id)
        if (!cotizacionDeCobrePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cotizacionDeCobrePlata.label', default: 'CotizacionDeCobrePlata'), id])
            redirect(action: "list")
            return
        }

        [cotizacionDeCobrePlataInstance: cotizacionDeCobrePlataInstance]
    }

    def update(Long id, Long version) {
        def cotizacionDeCobrePlataInstance = CotizacionDeCobrePlata.get(id)
        if (!cotizacionDeCobrePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cotizacionDeCobrePlata.label', default: 'CotizacionDeCobrePlata'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (cotizacionDeCobrePlataInstance.version > version) {
                cotizacionDeCobrePlataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'cotizacionDeCobrePlata.label', default: 'CotizacionDeCobrePlata')] as Object[],
                        "Another user has updated this CotizacionDeCobrePlata while you were editing")
                render(view: "edit", model: [cotizacionDeCobrePlataInstance: cotizacionDeCobrePlataInstance])
                return
            }
        }

        cotizacionDeCobrePlataInstance.properties = params

        if (!cotizacionDeCobrePlataInstance.save(flush: true)) {
            render(view: "edit", model: [cotizacionDeCobrePlataInstance: cotizacionDeCobrePlataInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'cotizacionDeCobrePlata.label', default: 'CotizacionDeCobrePlata'), cotizacionDeCobrePlataInstance.id])
        redirect(action: "show", id: cotizacionDeCobrePlataInstance.id)
    }

    def delete(Long id) {
        def cotizacionDeCobrePlataInstance = CotizacionDeCobrePlata.get(id)
        if (!cotizacionDeCobrePlataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cotizacionDeCobrePlata.label', default: 'CotizacionDeCobrePlata'), id])
            redirect(action: "list")
            return
        }

        try {
            cotizacionDeCobrePlataInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'cotizacionDeCobrePlata.label', default: 'CotizacionDeCobrePlata'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'cotizacionDeCobrePlata.label', default: 'CotizacionDeCobrePlata'), id])
            redirect(action: "show", id: id)
        }
    }

    def cotizar() {
        def tablaPrecios = new TablaPreciosCobreController()
        def terminosContrato = new TerminosDeContratoController()
        def porcentajeCobre=1
        def porcentajePlata=1

        def valorToneladaCobre=0
        def valorToneladaPlata=0
        def valorTonelada=0

        def valorToneladaTabla=0
        def valorToneladaTerminos=0
        def formatter = new DecimalFormat("###.00")

        def cotizacionDiaria = CotizacionDiariaDeMinerales.get(params.cotizacionDiariaId.toLong())

        if (cotizacionDiaria){
            porcentajeCobre = params.porcentajeCobre.toString().toBigDecimal()
            porcentajePlata = params.porcentajePlata.toString().toBigDecimal()

            //determinacion del valor por tonelada
            //valor por tabla
            valorToneladaCobre = tablaPrecios.getValorPorToneladaCobreCotizacion(cotizacionDiaria,params.tablaCobrePlataId.toString().toLong(),porcentajeCobre)
//            valorToneladaPlata = tablaPrecios.getValorPorToneladaPlataCotizacion(cotizacionDiaria,params.tablaComplejoId.toString().toLong(),porcentajePlata)
            valorToneladaPlata = 0
            valorToneladaTabla = valorToneladaCobre+valorToneladaPlata
            //valor por terminos
            valorToneladaTerminos = terminosContrato.getValorToneladaParaCotizacion(cotizacionDiaria,TerminosDeContrato.get(params.terminosDeContratoId.toString().toLong()),porcentajeCobre,0,porcentajePlata,0)

            if (params.modoValoracion.toString().equals("TABLA")){
                valorTonelada = valorToneladaTabla
            }else{
                valorTonelada = valorToneladaTerminos
            }
            render([valorPorTonelada: valorTonelada] as JSON)
        }else{
            render([valorPorTonelada: -1] as JSON)
        }
    }
}
