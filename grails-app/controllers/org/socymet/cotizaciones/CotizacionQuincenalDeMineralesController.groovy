package org.socymet.cotizaciones

import grails.converters.JSON
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN'])
class CotizacionQuincenalDeMineralesController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max  = Math.min(max ?: 10, 100)
        def allowedSort = ['fecha','zinc','plomo','plata']
        params.sort  = params.sort  in allowedSort ? params.sort  : 'fecha'
        params.order = params.order in ['asc','desc']  ? params.order : 'desc'

        def q = params.q?.trim()
        def list, total

        if (q) {
            def parts = q.split('/')
            String where = null
            Map hp = [:]
            try {
                if (parts.size() == 3) {
                    where = 'where day(fecha)=:d and month(fecha)=:m and year(fecha)=:y'
                    hp = [d: parts[0].toInteger(), m: parts[1].toInteger(), y: parts[2].toInteger()]
                } else if (parts.size() == 2) {
                    where = 'where month(fecha)=:m and year(fecha)=:y'
                    hp = [m: parts[0].toInteger(), y: parts[1].toInteger()]
                } else if (q.isInteger()) {
                    where = 'where year(fecha)=:y'
                    hp = [y: q.toInteger()]
                }
            } catch (ignored) {}

            if (where) {
                def order = "order by ${params.sort} ${params.order}"
                list  = CotizacionQuincenalDeMinerales.findAll(
                    "from CotizacionQuincenalDeMinerales ${where} ${order}",
                    hp, [max: params.max, offset: params.offset ?: 0])
                total = CotizacionQuincenalDeMinerales.executeQuery(
                    "select count(*) from CotizacionQuincenalDeMinerales ${where}", hp)[0]
            } else {
                list = []; total = 0
            }
        } else {
            list  = CotizacionQuincenalDeMinerales.list(params)
            total = CotizacionQuincenalDeMinerales.count()
        }

        [cotizacionQuincenalDeMineralesInstanceList : list,
         cotizacionQuincenalDeMineralesInstanceTotal: total]
    }

    def show(CotizacionQuincenalDeMinerales cotizacionQuincenalDeMineralesInstance) {
        [cotizacionQuincenalDeMineralesInstance: cotizacionQuincenalDeMineralesInstance]
    }

    def create() {
        [cotizacionQuincenalDeMineralesInstance: new CotizacionQuincenalDeMinerales(params)]
    }

    @Transactional
    def save(CotizacionQuincenalDeMinerales cotizacionQuincenalDeMineralesInstance) {
        if (cotizacionQuincenalDeMineralesInstance == null) {
            notFound()
            return
        }

        if (cotizacionQuincenalDeMineralesInstance.hasErrors()) {
            render view: 'create', model: [cotizacionQuincenalDeMineralesInstance: cotizacionQuincenalDeMineralesInstance]
            return
        }

        cotizacionQuincenalDeMineralesInstance.save flush: true

        flash.message = message(code: 'default.created.message', args: [message(code: 'cotizacionQuincenalDeMinerales.label', default: 'CotizacionQuincenalDeMinerales'), cotizacionQuincenalDeMineralesInstance.toString()])
        redirect action: 'show', id: cotizacionQuincenalDeMineralesInstance.id
    }

    def edit(CotizacionQuincenalDeMinerales cotizacionQuincenalDeMineralesInstance) {
        [cotizacionQuincenalDeMineralesInstance: cotizacionQuincenalDeMineralesInstance]
    }

    @Transactional
    def update(CotizacionQuincenalDeMinerales cotizacionQuincenalDeMineralesInstance) {
        if (cotizacionQuincenalDeMineralesInstance == null) {
            notFound()
            return
        }

        if (cotizacionQuincenalDeMineralesInstance.hasErrors()) {
            render view: 'edit', model: [cotizacionQuincenalDeMineralesInstance: cotizacionQuincenalDeMineralesInstance]
            return
        }

        cotizacionQuincenalDeMineralesInstance.save flush: true

        flash.message = message(code: 'default.updated.message', args: [message(code: 'cotizacionQuincenalDeMinerales.label', default: 'CotizacionQuincenalDeMinerales'), cotizacionQuincenalDeMineralesInstance.toString()])
        redirect action: 'show', id: cotizacionQuincenalDeMineralesInstance.id
    }

    @Transactional
    def delete(CotizacionQuincenalDeMinerales cotizacionQuincenalDeMineralesInstance) {
        if (cotizacionQuincenalDeMineralesInstance == null) {
            notFound()
            return
        }

        cotizacionQuincenalDeMineralesInstance.delete flush: true

        flash.message = message(code: 'default.deleted.message', args: [message(code: 'cotizacionQuincenalDeMinerales.label', default: 'CotizacionQuincenalDeMinerales'), cotizacionQuincenalDeMineralesInstance.toString()])
        redirect action: 'index'
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'cotizacionQuincenalDeMinerales.label', default: 'CotizacionQuincenalDeMinerales'), params.toString()])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    def cotizacionQuincenalEstano() {
        def fecha = new Date().parse("yyyy-MM-dd",""+params.fechaCotizacion_year+"-"+params.fechaCotizacion_month+"-"+params.fechaCotizacion_day)
        def cotizacionQuincenalEstano = CotizacionQuincenalDeMinerales.findByFecha(fecha)
        render([cotDiaEstano: (cotizacionQuincenalEstano)?cotizacionQuincenalEstano.estano:0] as JSON)
    }

    def cotizacionQuincenalPlata() {
        def fecha = new Date().parse("yyyy-MM-dd",""+params.fechaCotizacion_year+"-"+params.fechaCotizacion_month+"-"+params.fechaCotizacion_day)
        def cotizacionQuincenalPlata = CotizacionQuincenalDeMinerales.findByFecha(fecha)
        render([cotDiaPlata: (cotizacionQuincenalPlata)?cotizacionQuincenalPlata.plata:0] as JSON)
    }

    def cotizacionQuincenal() {
        def fecha = new Date().parse("yyyy-MM-dd",""+params.fechaDeRecepcion_year+"-"+params.fechaDeRecepcion_month+"-"+params.fechaDeRecepcion_day)
        def cotizacionQuincenal = CotizacionQuincenalDeMinerales.findByActivo(1)
        if (fecha.equals(cotizacionQuincenal.fecha))
            render([estadoCotizacion: "actual"] as JSON)
        else
            render([estadoCotizacion: "expirada"] as JSON)
    }

    def cotizacionQuincenalPorFecha() {
        def fecha = new Date().parse("yyyy-MM-dd",""+params.fechaDeCotizacion_year+"-"+params.fechaDeCotizacion_month+"-"+params.fechaDeCotizacion_day)
        def cotizacionQuincenal = CotizacionQuincenalDeMinerales.findByFecha(fecha)
        if (cotizacionQuincenal)
            render([cotizacionId: cotizacionQuincenal.id] as JSON)
        else
            render([cotizacionId: 0] as JSON)
    }
}
