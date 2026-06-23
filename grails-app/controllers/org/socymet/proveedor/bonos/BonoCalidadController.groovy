package org.socymet.proveedor.bonos
import grails.gorm.transactions.Transactional

import org.socymet.proveedor.Empresa
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class BonoCalidadController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [bonoCalidadInstanceList: BonoCalidad.list(params), bonoCalidadInstanceTotal: BonoCalidad.count()]
    }

    def create() {
        [bonoCalidadInstance: new BonoCalidad(params)]
    }

    def save() {
        def bonoCalidadInstance = new BonoCalidad(params)
        if (!bonoCalidadInstance.save(flush: true)) {
            render(view: "create", model: [bonoCalidadInstance: bonoCalidadInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'bonoCalidad.label', default: 'BonoCalidad'), bonoCalidadInstance.id])
        redirect(action: "show", id: bonoCalidadInstance.id)
    }

    def show(Long id) {
        def bonoCalidadInstance = BonoCalidad.get(id)
        if (!bonoCalidadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bonoCalidad.label', default: 'BonoCalidad'), id])
            redirect(action: "list")
            return
        }

        [bonoCalidadInstance: bonoCalidadInstance]
    }

    def edit(Long id) {
        def bonoCalidadInstance = BonoCalidad.get(id)
        if (!bonoCalidadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bonoCalidad.label', default: 'BonoCalidad'), id])
            redirect(action: "list")
            return
        }

        [bonoCalidadInstance: bonoCalidadInstance]
    }

    def update(Long id, Long version) {
        def bonoCalidadInstance = BonoCalidad.get(id)
        if (!bonoCalidadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bonoCalidad.label', default: 'BonoCalidad'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (bonoCalidadInstance.version > version) {
                bonoCalidadInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'bonoCalidad.label', default: 'BonoCalidad')] as Object[],
                        "Another user has updated this BonoCalidad while you were editing")
                render(view: "edit", model: [bonoCalidadInstance: bonoCalidadInstance])
                return
            }
        }

        bonoCalidadInstance.properties = params

        if (!bonoCalidadInstance.save(flush: true)) {
            render(view: "edit", model: [bonoCalidadInstance: bonoCalidadInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'bonoCalidad.label', default: 'BonoCalidad'), bonoCalidadInstance.id])
        redirect(action: "show", id: bonoCalidadInstance.id)
    }

    def delete(Long id) {
        def bonoCalidadInstance = BonoCalidad.get(id)
        if (!bonoCalidadInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bonoCalidad.label', default: 'BonoCalidad'), id])
            redirect(action: "list")
            return
        }

        try {
            bonoCalidadInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'bonoCalidad.label', default: 'BonoCalidad'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'bonoCalidad.label', default: 'BonoCalidad'), id])
            redirect(action: "show", id: id)
        }
    }

    def bonoCalidadComplejo(Long empresaId, BigDecimal porcentajePlata) {
        def empresa = Empresa.get(empresaId)
        def bonoCalidad = BonoCalidad.findByEmpresaAndLeyMinimaLessThanEqualsAndLeyMaximaGreaterThanEquals(empresa,porcentajePlata,porcentajePlata)
        return (bonoCalidad)?bonoCalidad.bono:0
    }

//    def bonoCalidadJSON() {
//        def empresa = Empresa.get(params.empresaId)
//        //def porcentajePlata = Float.parseFloat(params.porcentajePlata?params.porcentajePlata.toString():"0")
//        def porcentajePlata = Float.parseFloat(params.porcentajePlata.toString())
//        def bonoCalidad = BonoCalidad.findByEmpresaAndLeyMinimaLessThanEqualsAndLeyMaximaGreaterThanEquals(empresa,porcentajePlata,porcentajePlata)
//        render(contentType: "text/json") {
//            //bono = (bonoCalidad)?bonoCalidad.bono:0
//            bono = bonoCalidad?bonoCalidad.bono:0
//        }
//    }
//
//    def bonoCalidadEstanoJSON() {
//        def empresa = Empresa.get(params.empresaId)
//        //def porcentajeEstano = Float.parseFloat(params.porcentajeEstano?params.porcentajeEstano.toString():"0")
//        def porcentajeEstano = Float.parseFloat(params.porcentajeEstano.toString())
//        def bonoCalidad = BonoCalidad.findByEmpresaAndSimboloElementoAndLeyMinimaLessThanEqualsAndLeyMaximaGreaterThanEquals(empresa,"Sn",porcentajeEstano,porcentajeEstano)
//        render(contentType: "text/json") {
//            //bono = (bonoCalidad)?bonoCalidad.bono:0
//            bono = bonoCalidad?bonoCalidad.bono:0
//        }
//    }
//
//    def bonoCalidadPlataJSON() {
//        def empresa = Empresa.get(params.empresaId)
//        //def porcentajePlata = Float.parseFloat(params.porcentajePlata?params.porcentajePlata.toString():"0")
//        def porcentajePlata = Float.parseFloat(params.porcentajePlata.toString())
//        def bonoCalidad = BonoCalidad.findByEmpresaAndSimboloElementoAndLeyMinimaLessThanEqualsAndLeyMaximaGreaterThanEquals(empresa,"Ag",porcentajePlata,porcentajePlata)
//        render(contentType: "text/json") {
//            //bono = (bonoCalidad)?bonoCalidad.bono:0
//            bono = bonoCalidad?bonoCalidad.bono:0
//        }
//    }
//
//    def bonoCalidadWolfranJSON() {
//        def empresa = Empresa.get(params.empresaId)
//        //def porcentajeWolfran = Float.parseFloat(params.porcentajeWolfran?params.porcentajeWolfran.toString():"0")
//        def porcentajeWolfran = Float.parseFloat(params.porcentajeWolfran.toString())
//        def bonoCalidad = BonoCalidad.findByEmpresaAndSimboloElementoAndLeyMinimaLessThanEqualsAndLeyMaximaGreaterThanEquals(empresa,"WO3",porcentajeWolfran,porcentajeWolfran)
//        render(contentType: "text/json") {
//            //bono = (bonoCalidad)?bonoCalidad.bono:0
//            bono = bonoCalidad?bonoCalidad.bono:0
//        }
//    }
//
//    def bonoCalidadAntimonioJSON() {
//        def empresa = Empresa.get(params.empresaId)
//        //def porcentajeAntimonio = Float.parseFloat(params.porcentajeAntimonio?params.porcentajeAntimonio.toString():"0")
//        def porcentajeAntimonio = Float.parseFloat(params.porcentajeAntimonio.toString())
//        def bonoCalidad = BonoCalidad.findByEmpresaAndSimboloElementoAndLeyMinimaLessThanEqualsAndLeyMaximaGreaterThanEquals(empresa,"Sb",porcentajeAntimonio,porcentajeAntimonio)
//        render(contentType: "text/json") {
//            //bono = (bonoCalidad)?bonoCalidad.bono:0
//            bono = bonoCalidad?bonoCalidad.bono:0
//        }
//    }
}
