package org.socymet.cotizaciones

import grails.converters.JSON
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
class CotizacionDiariaDeMineralesController {

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
                list  = CotizacionDiariaDeMinerales.findAll(
                    "from CotizacionDiariaDeMinerales ${where} ${order}",
                    hp, [max: params.max, offset: params.offset ?: 0])
                total = CotizacionDiariaDeMinerales.executeQuery(
                    "select count(*) from CotizacionDiariaDeMinerales ${where}", hp)[0]
            } else {
                list = []; total = 0
            }
        } else {
            list  = CotizacionDiariaDeMinerales.list(params)
            total = CotizacionDiariaDeMinerales.count()
        }

        [cotizacionDiariaDeMineralesInstanceList : list,
         cotizacionDiariaDeMineralesInstanceTotal: total]
    }

    def show(CotizacionDiariaDeMinerales cotizacionDiariaDeMineralesInstance) {
        [cotizacionDiariaDeMineralesInstance: cotizacionDiariaDeMineralesInstance]
    }

    def create() {
        [cotizacionDiariaDeMineralesInstance: new CotizacionDiariaDeMinerales(params)]
    }

    @Transactional
    def save(CotizacionDiariaDeMinerales cotizacionDiariaDeMineralesInstance) {
        if (cotizacionDiariaDeMineralesInstance == null) {
            notFound()
            return
        }

        if (cotizacionDiariaDeMineralesInstance.hasErrors()) {
            render view: 'create', model: [cotizacionDiariaDeMineralesInstance: cotizacionDiariaDeMineralesInstance]
            return
        }

        cotizacionDiariaDeMineralesInstance.save flush: true

        flash.message = message(code: 'default.created.message', args: [message(code: 'cotizacionDiariaDeMinerales.label', default: 'CotizacionDiariaDeMinerales'), cotizacionDiariaDeMineralesInstance.toString()])
        redirect action: 'show', id: cotizacionDiariaDeMineralesInstance.id
    }

    def edit(CotizacionDiariaDeMinerales cotizacionDiariaDeMineralesInstance) {
        [cotizacionDiariaDeMineralesInstance: cotizacionDiariaDeMineralesInstance]
    }

    @Transactional
    def update(CotizacionDiariaDeMinerales cotizacionDiariaDeMineralesInstance) {
        if (cotizacionDiariaDeMineralesInstance == null) {
            notFound()
            return
        }

        if (cotizacionDiariaDeMineralesInstance.hasErrors()) {
            render view: 'edit', model: [cotizacionDiariaDeMineralesInstance: cotizacionDiariaDeMineralesInstance]
            return
        }

        cotizacionDiariaDeMineralesInstance.save flush: true

        flash.message = message(code: 'default.updated.message', args: [message(code: 'cotizacionDiariaDeMinerales.label', default: 'CotizacionDiariaDeMinerales'), cotizacionDiariaDeMineralesInstance.toString()])
        redirect action: 'show', id: cotizacionDiariaDeMineralesInstance.id
    }

    @Transactional
    def delete(CotizacionDiariaDeMinerales cotizacionDiariaDeMineralesInstance) {
        if (cotizacionDiariaDeMineralesInstance == null) {
            notFound()
            return
        }

        cotizacionDiariaDeMineralesInstance.delete flush: true

        flash.message = message(code: 'default.deleted.message', args: [message(code: 'cotizacionDiariaDeMinerales.label', default: 'CotizacionDiariaDeMinerales'), cotizacionDiariaDeMineralesInstance.toString()])
        redirect action: 'index'
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'cotizacionDiariaDeMinerales.label', default: 'CotizacionDiariaDeMinerales'), params.toString()])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    def cotizacionDiariaEstano() {
        def fecha = new Date().parse("yyyy-MM-dd",""+params.fechaCotizacion_year+"-"+params.fechaCotizacion_month+"-"+params.fechaCotizacion_day)
        def cotizacionDiariaEstano = CotizacionDiariaDeMinerales.findByFecha(fecha)
        render([cotDiaEstano: cotizacionDiariaEstano ? cotizacionDiariaEstano.estano : 0] as JSON)
    }

    def cotizacionDiariaPlata() {
        def fecha = new Date().parse("yyyy-MM-dd",""+params.fechaCotizacion_year+"-"+params.fechaCotizacion_month+"-"+params.fechaCotizacion_day)
        def cotizacionDiariaPlata = CotizacionDiariaDeMinerales.findByFecha(fecha)
        render([cotDiaPlata: cotizacionDiariaPlata ? cotizacionDiariaPlata.plata : 0] as JSON)
    }

    def cotizacionDiaria() {
        def fecha = new Date().parse("yyyy-MM-dd",""+params.fechaDeRecepcion_year+"-"+params.fechaDeRecepcion_month+"-"+params.fechaDeRecepcion_day)
        def cotizacionDiaria = CotizacionDiariaDeMinerales.findByActivo(1)
        if (fecha.equals(cotizacionDiaria.fecha))
            render([estadoCotizacion: "actual"] as JSON)
        else
            render([estadoCotizacion: "expirada"] as JSON)
    }

    def cotizacionDiariaPorFecha() {
        def fecha = new Date().parse("yyyy-MM-dd",""+params.fechaDeCotizacion_year+"-"+params.fechaDeCotizacion_month+"-"+params.fechaDeCotizacion_day)
        def cotizacionDiaria = CotizacionDiariaDeMinerales.findByFecha(fecha)
        if (cotizacionDiaria)
            render([cotizacionId: cotizacionDiaria.id] as JSON)
        else
            render([cotizacionId: 0] as JSON)
    }

    def obtenerCotizaciones() {
        def year      = params.year.toInteger()
        def month     = params.month.toInteger()
        def day       = params.day.toInteger()
        def dayQuince = day > 15 ? 16 : 1

        log.error("fechaDiaria: ${year}-${month}-${day} | inicioQuincenal: ${year}-${month}-${dayQuince}")

        def cotizacionDiaria = CotizacionDiariaDeMinerales.find(
            "from CotizacionDiariaDeMinerales where year(fecha)=:y and month(fecha)=:m and day(fecha)=:d",
            [y: year, m: month, d: day])

        def cotizacionQuincenal = CotizacionQuincenalDeMinerales.find(
            "from CotizacionQuincenalDeMinerales where year(fecha)=:y and month(fecha)=:m and day(fecha)=:d",
            [y: year, m: month, d: dayQuince])

        def alicuota = Alicuota.find(
            "from Alicuota where year(fecha)=:y and month(fecha)=:m and day(fecha)=:d",
            [y: year, m: month, d: dayQuince])

        def diariaId
        def hayDiaria
        def quincenalId
        def hayQuincenal
        def alicuotaQuincenalId
        def hayAlicuotaQuincenal

        if (cotizacionDiaria){
            diariaId = cotizacionDiaria.id
            hayDiaria = 1
        }else{
            diariaId = CotizacionDiariaDeMinerales.findByActivo(1).id
            hayDiaria = 0
        }

        if (cotizacionQuincenal){
            quincenalId = cotizacionQuincenal.id
            hayQuincenal = 1
        }else{
            quincenalId = CotizacionQuincenalDeMinerales.findByActivo(1).id
            hayQuincenal = 0
        }

        log.error("quincenalId: ${quincenalId}")
        log.error("hayQuincenal: ${hayQuincenal}")

        if (alicuota){
            alicuotaQuincenalId = alicuota.id
            hayAlicuotaQuincenal = 1
        }else{
            alicuotaQuincenalId = Alicuota.findByActivo(1).id
            hayAlicuotaQuincenal = 0
        }
        
        render([
            cotizacionDiariaId    : diariaId,
            hayCotizacionDiaria   : hayDiaria,
            cotizacionQuincenalId : quincenalId,
            hayCotizacionQuincenal: hayQuincenal,
            alicuotaId            : alicuotaQuincenalId,
            hayAlicuota           : hayAlicuotaQuincenal
        ] as JSON)
    }
}
