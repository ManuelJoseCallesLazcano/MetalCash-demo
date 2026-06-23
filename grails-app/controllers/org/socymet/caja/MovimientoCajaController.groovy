package org.socymet.caja

import grails.converters.JSON
import org.joda.time.DateTime
import org.socymet.seguridad.SecUser
import org.springframework.security.access.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
class MovimientoCajaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def springSecurityService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond MovimientoCaja.list(params), model: [movimientoCajaInstanceCount: MovimientoCaja.count()]
    }

    def show(MovimientoCaja movimientoCajaInstance) {
        respond movimientoCajaInstance
    }

    def create() {
        respond new MovimientoCaja(params)
    }

    @Transactional
    def save(MovimientoCaja movimientoCajaInstance) {
        if (movimientoCajaInstance == null) {
            notFound()
            return
        }

        if (movimientoCajaInstance.hasErrors()) {
            respond movimientoCajaInstance.errors, view: 'create'
            return
        }

        movimientoCajaInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'movimientoCaja.label', default: 'MovimientoCaja'), movimientoCajaInstance.id])
                redirect movimientoCajaInstance
            }
            '*' { respond movimientoCajaInstance, [status: CREATED] }
        }
    }

    def edit(MovimientoCaja movimientoCajaInstance) {
        respond movimientoCajaInstance
    }

    @Transactional
    def update(MovimientoCaja movimientoCajaInstance) {
        if (movimientoCajaInstance == null) {
            notFound()
            return
        }

        if (movimientoCajaInstance.hasErrors()) {
            respond movimientoCajaInstance.errors, view: 'edit'
            return
        }

        movimientoCajaInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'MovimientoCaja.label', default: 'MovimientoCaja'), movimientoCajaInstance.id])
                redirect movimientoCajaInstance
            }
            '*' { respond movimientoCajaInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(MovimientoCaja movimientoCajaInstance) {

        if (movimientoCajaInstance == null) {
            notFound()
            return
        }

        movimientoCajaInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'MovimientoCaja.label', default: 'MovimientoCaja'), movimientoCajaInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'movimientoCaja.label', default: 'MovimientoCaja'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    def nombreJSON() {
        def pagoTransportes=MovimientoCaja.withCriteria {
            projections {
                distinct "ci"
                property("nombre")
            }
            like("ci","${params.ci}%")
        }.sort()
        def pagoTransportesList = []
        pagoTransportes.each {
            def mapaClientes = [:]
            //parametros en JSON para JQuery UI Autocomplete
            mapaClientes.put("id",it[0])
            mapaClientes.put("label","${it[0]}") //son las cadenas que se muestran en la lista
            mapaClientes.put("value",it[0]) //es la cadena que se establece en el input despues de ser seleccionado
            //otros parametros
            mapaClientes.put("nombre",it[1])
            pagoTransportesList.add(mapaClientes)
        }
        render pagoTransportesList as JSON
    }

    def movimientoCajaJSON() {
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        //23h 59m 59s = 86399
        def dia=params.fechaCierreCaja_day.toInteger()
        def mes=params.fechaCierreCaja_month.toInteger()
        def anio=params.fechaCierreCaja_year.toInteger()
        def fechaInicial = new DateTime(anio,mes,dia,0,0).withTimeAtStartOfDay()
        def fechaFinal = new DateTime(anio,mes,dia,0,0).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59)

        log.error("fechaInicial: ${fechaInicial.toDate().toString()}")
        log.error("fechaFinal: ${fechaFinal.toDate().toString()}")

        def movimientoCajas=MovimientoCaja.findAllByFechaMovimientoBetweenAndConsolidado(fechaInicial.toDate(),fechaFinal.toDate(),"NO")
        def movimientosList = []
        def mapaMovimientos = [:]

        def it
        def itAnterior

        for(def i=0; i<movimientoCajas.size(); i++){
            mapaMovimientos = [:]
            it=movimientoCajas.get(i)
            mapaMovimientos.put("movimientoCajaId",it.id)
            mapaMovimientos.put("numeroMovimiento",it.numeroMovimiento)
            mapaMovimientos.put("fechaMovimiento",new java.text.SimpleDateFormat("dd/MM/yyyy hh:mm a").format(it.fechaMovimiento))
            mapaMovimientos.put("ingresoId",it.ingreso?it.ingreso.id:0)
            mapaMovimientos.put("egresoId",it.egreso?it.egreso.id:0)
            mapaMovimientos.put("ci",it.ci)
            mapaMovimientos.put("nombre",it.nombre)
            mapaMovimientos.put("concepto",it.concepto)
            mapaMovimientos.put("debe",it.debe)
            mapaMovimientos.put("haber",it.haber)
            if(i==0){
                it.saldo = it.debe-it.haber
            }else{
                itAnterior=movimientoCajas.get(i-1)
                it.saldo = itAnterior.saldo+it.debe-it.haber
            }
            mapaMovimientos.put("saldo",it.saldo)
            mapaMovimientos.put("usuarioId",it.usuario.id)
            movimientosList.add(mapaMovimientos)
        }
        render movimientosList as JSON
    }
}
