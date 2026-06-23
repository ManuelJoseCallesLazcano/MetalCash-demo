package org.socymet.proveedor.bonos
import grails.converters.JSON
import grails.gorm.transactions.Transactional

import org.socymet.proveedor.Empresa
import org.springframework.dao.DataIntegrityViolationException

@Transactional
class BonoIncentivoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [bonoIncentivoInstanceList: BonoIncentivo.list(params), bonoIncentivoInstanceTotal: BonoIncentivo.count()]
    }

    def create() {
        [bonoIncentivoInstance: new BonoIncentivo(params)]
    }

    def save() {
        def bonoIncentivoInstance = new BonoIncentivo(params)
        if (!bonoIncentivoInstance.save(flush: true)) {
            render(view: "create", model: [bonoIncentivoInstance: bonoIncentivoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'bonoIncentivo.label', default: 'BonoIncentivo'), bonoIncentivoInstance.id])
        redirect(action: "show", id: bonoIncentivoInstance.id)
    }

    def show(Long id) {
        def bonoIncentivoInstance = BonoIncentivo.get(id)
        if (!bonoIncentivoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bonoIncentivo.label', default: 'BonoIncentivo'), id])
            redirect(action: "list")
            return
        }

        [bonoIncentivoInstance: bonoIncentivoInstance]
    }

    def edit(Long id) {
        def bonoIncentivoInstance = BonoIncentivo.get(id)
        if (!bonoIncentivoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bonoIncentivo.label', default: 'BonoIncentivo'), id])
            redirect(action: "list")
            return
        }

        [bonoIncentivoInstance: bonoIncentivoInstance]
    }

    def update(Long id, Long version) {
        def bonoIncentivoInstance = BonoIncentivo.get(id)
        if (!bonoIncentivoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bonoIncentivo.label', default: 'BonoIncentivo'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (bonoIncentivoInstance.version > version) {
                bonoIncentivoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'bonoIncentivo.label', default: 'BonoIncentivo')] as Object[],
                        "Another user has updated this BonoIncentivo while you were editing")
                render(view: "edit", model: [bonoIncentivoInstance: bonoIncentivoInstance])
                return
            }
        }

        bonoIncentivoInstance.properties = params

        if (!bonoIncentivoInstance.save(flush: true)) {
            render(view: "edit", model: [bonoIncentivoInstance: bonoIncentivoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'bonoIncentivo.label', default: 'BonoIncentivo'), bonoIncentivoInstance.id])
        redirect(action: "show", id: bonoIncentivoInstance.id)
    }

    def delete(Long id) {
        def bonoIncentivoInstance = BonoIncentivo.get(id)
        if (!bonoIncentivoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'bonoIncentivo.label', default: 'BonoIncentivo'), id])
            redirect(action: "list")
            return
        }

        try {
            bonoIncentivoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'bonoIncentivo.label', default: 'BonoIncentivo'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'bonoIncentivo.label', default: 'BonoIncentivo'), id])
            redirect(action: "show", id: id)
        }
    }

    def bonoIncentivoJSON() {
        def empresa = Empresa.get(params.empresaId)
        def kilosNetosSecos = Float.parseFloat(params.kilosNetosSecos.toString())
        def porcentajePlata = Float.parseFloat(params.porcentajePlata.toString())
        def bonoIncentivo = BonoIncentivo.findByEmpresaAndLeyMinimaLessThanEqualsAndLeyMaximaGreaterThanEqualsAndCantidadMinimaLessThanEqualsAndCantidadMaximaGreaterThanEquals(empresa,porcentajePlata,porcentajePlata,kilosNetosSecos,kilosNetosSecos)
        render([bono: bonoIncentivo?bonoIncentivo.bono:0] as JSON)
    }
    /***
     * ADICIONAR PREGUNTA POR EL TIPO DE MINERAL
     * EN ESTE CASO PONER elemento EN LA FUNCION y Sn EN LOS PARAMETROS
     *
     */

    def bonoIncentivoEstanoJSON() {
        def empresa = Empresa.get(params.empresaId)
        def kilosNetosSecos = Float.parseFloat(params.kilosNetosSecos.toString())
        def porcentajeEstano = Float.parseFloat(params.porcentajeEstano.toString())
        def bonoIncentivo = BonoIncentivo.findByEmpresaAndSimboloElementoAndLeyMinimaLessThanEqualsAndLeyMaximaGreaterThanEqualsAndCantidadMinimaLessThanEqualsAndCantidadMaximaGreaterThanEquals(empresa,"Sn",porcentajeEstano,porcentajeEstano,kilosNetosSecos,kilosNetosSecos)
        render([bono: bonoIncentivo?bonoIncentivo.bono:0] as JSON)
    }

    def bonoIncentivoPlataJSON() {
        def empresa = Empresa.get(params.empresaId)
        def kilosNetosSecos = Float.parseFloat(params.kilosNetosSecos.toString())
        def porcentajePlata = Float.parseFloat(params.porcentajePlata.toString())
        def bonoIncentivo = BonoIncentivo.findByEmpresaAndSimboloElementoAndLeyMinimaLessThanEqualsAndLeyMaximaGreaterThanEqualsAndCantidadMinimaLessThanEqualsAndCantidadMaximaGreaterThanEquals(empresa,"Ag",porcentajePlata,porcentajePlata,kilosNetosSecos,kilosNetosSecos)
        render([bono: bonoIncentivo?bonoIncentivo.bono:0] as JSON)
    }

    def bonoIncentivoWolfranJSON() {
        def empresa = Empresa.get(params.empresaId)
        def kilosNetosSecos = Float.parseFloat(params.kilosNetosSecos.toString())
        def porcentajeWolfran = Float.parseFloat(params.porcentajeWolfran.toString())
        def bonoIncentivo = BonoIncentivo.findByEmpresaAndSimboloElementoAndLeyMinimaLessThanEqualsAndLeyMaximaGreaterThanEqualsAndCantidadMinimaLessThanEqualsAndCantidadMaximaGreaterThanEquals(empresa,"WO3",porcentajeWolfran,porcentajeWolfran,kilosNetosSecos,kilosNetosSecos)
        render([bono: bonoIncentivo?bonoIncentivo.bono:0] as JSON)
    }

    def bonoIncentivoAntimonioJSON() {
        def empresa = Empresa.get(params.empresaId)
        def kilosNetosSecos = Float.parseFloat(params.kilosNetosSecos.toString())
        def porcentajeAntimonio = Float.parseFloat(params.porcentajeAntimonio.toString())
        def bonoIncentivo = BonoIncentivo.findByEmpresaAndSimboloElementoAndLeyMinimaLessThanEqualsAndLeyMaximaGreaterThanEqualsAndCantidadMinimaLessThanEqualsAndCantidadMaximaGreaterThanEquals(empresa,"Sb",porcentajeAntimonio,porcentajeAntimonio,kilosNetosSecos,kilosNetosSecos)
        render([bono: bonoIncentivo?bonoIncentivo.bono:0] as JSON)
    }

    def bonoIncentivoComplejo(Long empresaId, BigDecimal kilosNetosSecos, BigDecimal porcentajePlata) {
        def empresa = Empresa.get(empresaId)
        def bonoIncentivo = BonoIncentivo.findByEmpresaAndSimboloElementoAndLeyMinimaLessThanEqualsAndLeyMaximaGreaterThanEqualsAndCantidadMinimaLessThanEqualsAndCantidadMaximaGreaterThanEquals(empresa,"ZnPbAg",porcentajePlata,porcentajePlata,kilosNetosSecos,kilosNetosSecos)
        return (bonoIncentivo)?bonoIncentivo.bono:0
    }
}
