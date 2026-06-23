package org.smart.parametros

import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION'])
class GestionMineraController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def q = params.q?.trim()
        if (q) {
            def results = GestionMinera.createCriteria().list(params) {
                or {
                    ilike('estado', "%${q}%")
                    if (q.isInteger()) {
                        int anio = q.toInteger()
                        def cal = Calendar.instance
                        cal.clear(); cal.set(anio, Calendar.JANUARY, 1, 0, 0, 0)
                        Date inicio = cal.time
                        cal.set(anio, Calendar.DECEMBER, 31, 23, 59, 59)
                        Date fin = cal.time
                        between('gestion', inicio, fin)
                    }
                }
            }
            [gestionMineraInstanceList: results, gestionMineraInstanceTotal: results.totalCount]
        } else {
            [gestionMineraInstanceList: GestionMinera.list(params), gestionMineraInstanceTotal: GestionMinera.count()]
        }
    }

    def show(GestionMinera gestionMineraInstance) {
        if (gestionMineraInstance == null) {
            notFound()
            return
        }
        [gestionMineraInstance: gestionMineraInstance]
    }

    def create() {
        [gestionMineraInstance: new GestionMinera(params)]
    }

    @Transactional
    def save(GestionMinera gestionMineraInstance) {
        if (gestionMineraInstance == null) {
            notFound()
            return
        }

        if (gestionMineraInstance.hasErrors()) {
            render view: 'create', model: [gestionMineraInstance: gestionMineraInstance]
            return
        }

        gestionMineraInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'gestionMinera.label', default: 'GestionMinera'), gestionMineraInstance.toString()])
                redirect gestionMineraInstance
            }
            '*' { respond gestionMineraInstance, [status: CREATED] }
        }
    }

    def edit(GestionMinera gestionMineraInstance) {
        if (gestionMineraInstance == null) {
            notFound()
            return
        }
        [gestionMineraInstance: gestionMineraInstance]
    }

    @Transactional
    def update(GestionMinera gestionMineraInstance) {
        if (gestionMineraInstance == null) {
            notFound()
            return
        }

        if (gestionMineraInstance.hasErrors()) {
            render view: 'edit', model: [gestionMineraInstance: gestionMineraInstance]
            return
        }

        gestionMineraInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'GestionMinera.label', default: 'GestionMinera'), gestionMineraInstance.toString()])
                redirect gestionMineraInstance
            }
            '*'{ respond gestionMineraInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(GestionMinera gestionMineraInstance) {

        if (gestionMineraInstance == null) {
            notFound()
            return
        }

        gestionMineraInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'GestionMinera.label', default: 'GestionMinera'), gestionMineraInstance.toString()])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'gestionMinera.label', default: 'GestionMinera'), params.toString()])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
