package org.socymet.cotizacionParaCliente
import grails.converters.JSON
import grails.gorm.transactions.Transactional

import org.socymet.cotizaciones.CotizacionDiariaDeMinerales
import org.socymet.cotizaciones.TablaOrigenCotizacionesComplejoController
import org.socymet.cotizaciones.TerminosDeContrato
import org.socymet.cotizaciones.TerminosDeContratoController
import org.socymet.seguridad.SecUser
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

import java.text.DecimalFormat

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class CotizacionDeComplejoController {
    def springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        params.max = Math.min(max ?: 10, 100)

//        [cotizacionDeComplejoInstanceList: CotizacionDeComplejo.list(params), cotizacionDeComplejoInstanceTotal: CotizacionDeComplejo.count()]
        [cotizacionDeComplejoInstanceList: CotizacionDeComplejo.findAllByUsuario(usuarioActual, params), cotizacionDeComplejoInstanceTotal: CotizacionDeComplejo.findAllByUsuario(usuarioActual).size()]
    }

    def create() {
        [cotizacionDeComplejoInstance: new CotizacionDeComplejo(params)]
    }

    def save() {
        def cotizacionDeComplejoInstance = new CotizacionDeComplejo(params)
        if (!cotizacionDeComplejoInstance.save(flush: true)) {
            render(view: "create", model: [cotizacionDeComplejoInstance: cotizacionDeComplejoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'cotizacionDeComplejo.label', default: 'CotizacionDeComplejo'), cotizacionDeComplejoInstance.id])
        redirect(action: "show", id: cotizacionDeComplejoInstance.id)
    }

    def show(Long id) {
        def cotizacionDeComplejoInstance = CotizacionDeComplejo.get(id)
        if (!cotizacionDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cotizacionDeComplejo.label', default: 'CotizacionDeComplejo'), id])
            redirect(action: "list")
            return
        }

        [cotizacionDeComplejoInstance: cotizacionDeComplejoInstance]
    }

    def edit(Long id) {
        def cotizacionDeComplejoInstance = CotizacionDeComplejo.get(id)
        if (!cotizacionDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cotizacionDeComplejo.label', default: 'CotizacionDeComplejo'), id])
            redirect(action: "list")
            return
        }

        [cotizacionDeComplejoInstance: cotizacionDeComplejoInstance]
    }

    def update(Long id, Long version) {
        def cotizacionDeComplejoInstance = CotizacionDeComplejo.get(id)
        if (!cotizacionDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cotizacionDeComplejo.label', default: 'CotizacionDeComplejo'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (cotizacionDeComplejoInstance.version > version) {
                cotizacionDeComplejoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'cotizacionDeComplejo.label', default: 'CotizacionDeComplejo')] as Object[],
                          "Another user has updated this CotizacionDeComplejo while you were editing")
                render(view: "edit", model: [cotizacionDeComplejoInstance: cotizacionDeComplejoInstance])
                return
            }
        }

        cotizacionDeComplejoInstance.properties = params

        if (!cotizacionDeComplejoInstance.save(flush: true)) {
            render(view: "edit", model: [cotizacionDeComplejoInstance: cotizacionDeComplejoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'cotizacionDeComplejo.label', default: 'CotizacionDeComplejo'), cotizacionDeComplejoInstance.id])
        redirect(action: "show", id: cotizacionDeComplejoInstance.id)
    }

    def delete(Long id) {
        def cotizacionDeComplejoInstance = CotizacionDeComplejo.get(id)
        if (!cotizacionDeComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cotizacionDeComplejo.label', default: 'CotizacionDeComplejo'), id])
            redirect(action: "list")
            return
        }

        try {
            cotizacionDeComplejoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'cotizacionDeComplejo.label', default: 'CotizacionDeComplejo'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'cotizacionDeComplejo.label', default: 'CotizacionDeComplejo'), id])
            redirect(action: "show", id: id)
        }
    }

    def cotizar() {
        def tablaPrecios = new TablaOrigenCotizacionesComplejoController()
        def terminosContrato = new TerminosDeContratoController()
        def porcentajeZinc=1
        def porcentajePlomo=1
        def porcentajePlata=1

        def valorToneladaZinc=0
        def valorToneladaPlomo=0
        def valorToneladaPlata=0
        def valorTonelada=0

        def valorToneladaTabla=0
        def valorToneladaTerminos=0
        def formatter = new DecimalFormat("###.00")

        def cotizacionDiaria = CotizacionDiariaDeMinerales.get(params.cotizacionDiariaId.toLong())

        if (cotizacionDiaria){
            porcentajeZinc = params.porcentajeZinc.toString().toBigDecimal()
            porcentajePlomo = params.porcentajePlomo.toString().toBigDecimal()
            porcentajePlata = params.porcentajePlata.toString().toBigDecimal()

            //determinacion del valor por tonelada
            //valor por tabla
            valorToneladaZinc = tablaPrecios.getValorPorToneladaZincCotizacion(cotizacionDiaria,params.tablaComplejoId.toString().toLong(),porcentajeZinc)
            valorToneladaPlomo = tablaPrecios.getValorPorToneladaPlomoCotizacion(cotizacionDiaria,params.tablaComplejoId.toString().toLong(),porcentajePlomo)
            valorToneladaPlata = tablaPrecios.getValorPorToneladaPlataCotizacion(cotizacionDiaria,params.tablaComplejoId.toString().toLong(),porcentajePlata)
            valorToneladaTabla = valorToneladaZinc+valorToneladaPlomo+valorToneladaPlata
            //valor por terminos
            valorToneladaTerminos = terminosContrato.getValorToneladaParaCotizacion(cotizacionDiaria,TerminosDeContrato.get(params.terminosDeContratoId.toString().toLong()),porcentajeZinc,porcentajePlomo,porcentajePlata,0)

            if (params.modoValoracion.toString().equals("TABLA")){
                valorTonelada = valorToneladaTabla-10
            }else{
                valorTonelada = valorToneladaTerminos-25
            }
            render([valorPorTonelada: valorTonelada] as JSON)
        }else{
            render([valorPorTonelada: -1] as JSON)
        }
    }
}
