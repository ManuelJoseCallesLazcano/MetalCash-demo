package org.socymet.proveedor

import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
class MunicipioController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def q = params.q?.trim()
        if (q) {
            def pattern = "%${q}%"
            def criteria = {
                or {
                    ilike('departamento', pattern)
                    ilike('municipio', pattern)
                }
            }
            [municipioInstanceList : Municipio.createCriteria().list(params, criteria),
             municipioInstanceCount: Municipio.createCriteria().count(criteria)]
        } else {
            [municipioInstanceList: Municipio.list(params), municipioInstanceCount: Municipio.count()]
        }
    }

    def show(Municipio municipioInstance) {
        [municipioInstance: municipioInstance]
    }

    def create() {
        [municipioInstance: new Municipio(params)]
    }

    @Transactional
    def save(Municipio municipioInstance) {
        if (municipioInstance == null) {
            notFound()
            return
        }

        if (municipioInstance.hasErrors()) {
            render view: 'create', model: [municipioInstance: municipioInstance]
            return
        }

        municipioInstance.save flush: true

        flash.message = message(code: 'default.created.message', args: [message(code: 'municipio.label', default: 'Municipio'), municipioInstance.toString()])
        redirect action: 'show', id: municipioInstance.id
    }

    def edit(Municipio municipioInstance) {
        [municipioInstance: municipioInstance]
    }

    @Transactional
    def update(Municipio municipioInstance) {
        if (municipioInstance == null) {
            notFound()
            return
        }

        if (municipioInstance.hasErrors()) {
            render view: 'edit', model: [municipioInstance: municipioInstance]
            return
        }

        municipioInstance.save flush: true

        flash.message = message(code: 'default.updated.message', args: [message(code: 'municipio.label', default: 'Municipio'), municipioInstance.toString()])
        redirect action: 'show', id: municipioInstance.id
    }

    @Transactional
    def delete(Municipio municipioInstance) {
        if (municipioInstance == null) {
            notFound()
            return
        }

        municipioInstance.delete flush: true

        flash.message = message(code: 'default.deleted.message', args: [message(code: 'municipio.label', default: 'Municipio'), municipioInstance.toString()])
        redirect action: 'index'
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'municipio.label', default: 'Municipio'), params.toString()])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def municipiosDepartamento = {
        def empresa = params.empresaId==0?null:Empresa.get(params.empresaId)
        def departamento = params.departamento.toString()
        def municipios = Municipio.findAllByDepartamento(departamento,[sort: "municipio"])
        if (empresa)
            render g.select(name:'municipio', from: municipios, optionKey: "municipio", required: "", value: empresa.municipio, class: "chosen-select")
//            render g.select(id:'categoria', name:'categoria.id', from: municipios, optionKey: "id", required: "", value: empresa.categoria.id, class: "many-to-one")
        else
            render g.select(name:'municipio', from: municipios, optionKey: "municipio", required: "", class: "chosen-select")
//            render g.select(id:'categoria', name:'categoria.id', from: municipios, optionKey: "id", required: "", class: "many-to-one")
    }
}
