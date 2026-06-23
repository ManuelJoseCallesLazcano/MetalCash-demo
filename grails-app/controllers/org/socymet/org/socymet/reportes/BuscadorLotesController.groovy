package org.socymet.org.socymet.reportes

import grails.converters.JSON
import org.socymet.anticipos.AnticipoDetalle
import org.socymet.calidad.ControlCalidadCobrePlata
import org.socymet.calidad.ControlCalidadComplejo
import org.socymet.calidad.ControlCalidadPlomoPlata
import org.socymet.calidad.ControlCalidadZincPlata
import org.socymet.cancelacion.DetallePagoTransporte
import org.socymet.liquidacion.LiquidacionDeCobrePlata
import org.socymet.liquidacion.LiquidacionDeComplejo
import org.socymet.liquidacion.LiquidacionDePlomoPlata
import org.socymet.liquidacion.LiquidacionDeZincPlata
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.seguridad.SecUser
import org.springframework.security.access.annotation.Secured

import java.text.DecimalFormat

import static org.springframework.http.HttpStatus.*
import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)

@Secured(['ROLE_ADMIN','ROLE_RECEPCION','ROLE_CONTROL_CALIDAD','ROLE_CAJA','ROLE_LIQUIDACION'])
class BuscadorLotesController {
    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond BuscadorLotes.list(params), model: [buscadorLotesInstanceCount: BuscadorLotes.count()]
    }

    def show(BuscadorLotes buscadorLotesInstance) {
        respond buscadorLotesInstance
    }

    def create() {
        respond new BuscadorLotes(params)
    }

    @Transactional
    def save(BuscadorLotes buscadorLotesInstance) {
        if (buscadorLotesInstance == null) {
            notFound()
            return
        }

        if (buscadorLotesInstance.hasErrors()) {
            respond buscadorLotesInstance.errors, view: 'create'
            return
        }

        buscadorLotesInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'buscadorLotes.label', default: 'BuscadorLotes'), buscadorLotesInstance.id])
                redirect buscadorLotesInstance
            }
            '*' { respond buscadorLotesInstance, [status: CREATED] }
        }
    }

    def edit(BuscadorLotes buscadorLotesInstance) {
        respond buscadorLotesInstance
    }

    @Transactional
    def update(BuscadorLotes buscadorLotesInstance) {
        if (buscadorLotesInstance == null) {
            notFound()
            return
        }

        if (buscadorLotesInstance.hasErrors()) {
            respond buscadorLotesInstance.errors, view: 'edit'
            return
        }

        buscadorLotesInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'BuscadorLotes.label', default: 'BuscadorLotes'), buscadorLotesInstance.id])
                redirect buscadorLotesInstance
            }
            '*' { respond buscadorLotesInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(BuscadorLotes buscadorLotesInstance) {

        if (buscadorLotesInstance == null) {
            notFound()
            return
        }

        buscadorLotesInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'BuscadorLotes.label', default: 'BuscadorLotes'), buscadorLotesInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'buscadorLotes.label', default: 'BuscadorLotes'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    def localizadorLoteJSON() {
        def lote = params.term.toString()
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser

        def recepcionesComplejoCriteria = RecepcionDeComplejo.createCriteria()
        def recepcionesComplejo = recepcionesComplejoCriteria{
            projections {
                property('id')
                property('empresa')
                property('loteComplejo')
                property('gestionMinera')
            }
            sqlRestriction "cast( lote_complejo AS char( 256 ) ) like '$lote'"
            eq('deposito',usuarioActual.deposito)
        }

        def recepcionesPlomoPlataCriteria = RecepcionDeComplejo.createCriteria()
        def recepcionesPlomoPlata = recepcionesPlomoPlataCriteria{
            projections {
                property('id')
                property('empresa')
                property('lotePlomoPlata')
            }
            sqlRestriction "cast( lote_plomo_plata AS char( 256 ) ) like '$lote'"
        }

        def recepcionesZincPlataCriteria = RecepcionDeComplejo.createCriteria()
        def recepcionesZincPlata = recepcionesZincPlataCriteria{
            projections {
                property('id')
                property('empresa')
                property('loteZincPlata')
            }
            sqlRestriction "cast( lote_zinc_plata AS char( 256 ) ) like '$lote'"
        }

        def recepcionesCobrePlataCriteria = RecepcionDeComplejo.createCriteria()
        def recepcionesCobrePlata = recepcionesCobrePlataCriteria{
            projections {
                property('id')
                property('empresa')
                property('loteCobrePlata')
            }
            sqlRestriction "cast( lote_cobre_plata AS char( 256 ) ) like '$lote'"
        }

        def enlacesList = []
        def decimalFormat = new DecimalFormat("000")
        def recepcionBase
        def controlCalidad
        def liquidacion
        def anticipo
        def anticipoDetalle
        def pagoTransporte
        def pagoTransporteDetalle

        recepcionesComplejo.each { recepcion ->
            def mapaRecepcion = [:]
//            return "${empresa?.codigoEmpresa}-${decimalFormat.format(loteComplejo)}/${gestionMinera.format("YY")}"
//            mapaRecepcion.put("label", "${recepcion[1].codigoEmpresa}-${decimalFormat.format(recepcion[2])}/") //son las cadenas que se muestran en la lista
            mapaRecepcion.put("label", "${recepcion[1].codigoEmpresa}-${decimalFormat.format(recepcion[2])}/${recepcion[3].format("YY")}") //son las cadenas que se muestran en la lista
//            mapaRecepcion.put("value", "${recepcion[1].codigoEmpresa}-${decimalFormat.format(recepcion[2])}/") //es la cadena que se establece en el input despues de ser seleccionado
            mapaRecepcion.put("value", "${recepcion[1].codigoEmpresa}-${decimalFormat.format(recepcion[2])}/${recepcion[3].format("YY")}") //son las cadenas que se muestran en la lista
            mapaRecepcion.put("depositoId", recepcion[1].id)

            recepcionBase = RecepcionDeComplejo.get(recepcion[0])

            mapaRecepcion.put("recepcionId", "recepcionDeComplejo/show/${recepcion[0]}")

            controlCalidad=ControlCalidadComplejo.findByRecepcionDeComplejo(recepcionBase)
            mapaRecepcion.put("controlCalidadId", controlCalidad?"controlCalidadComplejo/show/${controlCalidad.id}":0)

            liquidacion=LiquidacionDeComplejo.findByRecepcionDeComplejo(recepcionBase)
            mapaRecepcion.put("liquidacionId", liquidacion?"liquidacionDeComplejo/show/${liquidacion.id}":0)

            anticipoDetalle=AnticipoDetalle.findByRecepcionId(recepcionBase.id)
            mapaRecepcion.put("anticipoId", anticipoDetalle?"anticipo/show/${anticipoDetalle.anticipo.id}":0)

            pagoTransporteDetalle=DetallePagoTransporte.findByRecepcionId(recepcionBase.id)
            mapaRecepcion.put("pagoTransporteId", pagoTransporteDetalle?"pagoTransporte/show/${pagoTransporteDetalle.pagoTransporte.id}":0)

            enlacesList.add(mapaRecepcion)
        }

        recepcionesPlomoPlata.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("label", "${recepcion[1].codigoDeposito}-PGAG${decimalFormat.format(recepcion[2])}") //son las cadenas que se muestran en la lista
            mapaRecepcion.put("value", "${recepcion[1].codigoDeposito}-PGAG${decimalFormat.format(recepcion[2])}") //es la cadena que se establece en el input despues de ser seleccionado
            mapaRecepcion.put("depositoId", recepcion[1].id)

            recepcionBase = RecepcionDeComplejo.get(recepcion[0])

            mapaRecepcion.put("recepcionId", "recepcionDeComplejo/show/${recepcion[0]}")

            controlCalidad=ControlCalidadPlomoPlata.findByRecepcionDeComplejo(recepcionBase)
            mapaRecepcion.put("controlCalidadId", controlCalidad?"controlCalidadPlomoPlata/show/${controlCalidad.id}":0)

            liquidacion=LiquidacionDePlomoPlata.findByRecepcionDeComplejo(recepcionBase)
            mapaRecepcion.put("liquidacionId", liquidacion?"liquidacionDePlomoPlata/show/${liquidacion.id}":0)

            anticipoDetalle=AnticipoDetalle.findByRecepcionId(recepcionBase.id)
            mapaRecepcion.put("anticipoId", anticipoDetalle?"anticipo/show/${anticipoDetalle.anticipo.id}":0)

            pagoTransporteDetalle=DetallePagoTransporte.findByRecepcionId(recepcionBase.id)
            mapaRecepcion.put("pagoTransporteId", pagoTransporteDetalle?"pagoTransporte/show/${pagoTransporteDetalle.pagoTransporte.id}":0)

            enlacesList.add(mapaRecepcion)
        }

        recepcionesZincPlata.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("label", "${recepcion[1].codigoDeposito}-ZNAG${decimalFormat.format(recepcion[2])}") //son las cadenas que se muestran en la lista
            mapaRecepcion.put("value", "${recepcion[1].codigoDeposito}-ZNAG${decimalFormat.format(recepcion[2])}") //es la cadena que se establece en el input despues de ser seleccionado
            mapaRecepcion.put("depositoId", recepcion[1].id)

            recepcionBase = RecepcionDeComplejo.get(recepcion[0])

            mapaRecepcion.put("recepcionId", "recepcionDeComplejo/show/${recepcion[0]}")

            controlCalidad=ControlCalidadZincPlata.findByRecepcionDeComplejo(recepcionBase)
            mapaRecepcion.put("controlCalidadId", controlCalidad?"controlCalidadZincPlata/show/${controlCalidad.id}":0)

            liquidacion=LiquidacionDeZincPlata.findByRecepcionDeComplejo(recepcionBase)
            mapaRecepcion.put("liquidacionId", liquidacion?"liquidacionDeZincPlata/show/${liquidacion.id}":0)

            anticipoDetalle=AnticipoDetalle.findByRecepcionId(recepcionBase.id)
            mapaRecepcion.put("anticipoId", anticipoDetalle?"anticipo/show/${anticipoDetalle.anticipo.id}":0)

            pagoTransporteDetalle=DetallePagoTransporte.findByRecepcionId(recepcionBase.id)
            mapaRecepcion.put("pagoTransporteId", pagoTransporteDetalle?"pagoTransporte/show/${pagoTransporteDetalle.pagoTransporte.id}":0)

            enlacesList.add(mapaRecepcion)
        }

        recepcionesCobrePlata.each { recepcion ->
            def mapaRecepcion = [:]
            mapaRecepcion.put("label", "${recepcion[1].codigoDeposito}-CUAG${decimalFormat.format(recepcion[2])}") //son las cadenas que se muestran en la lista
            mapaRecepcion.put("value", "${recepcion[1].codigoDeposito}-CUAG${decimalFormat.format(recepcion[2])}") //es la cadena que se establece en el input despues de ser seleccionado
            mapaRecepcion.put("depositoId", recepcion[1].id)

            recepcionBase = RecepcionDeComplejo.get(recepcion[0])

            mapaRecepcion.put("recepcionId", "recepcionDeComplejo/show/${recepcion[0]}")

            controlCalidad=ControlCalidadCobrePlata.findByRecepcionDeComplejo(recepcionBase)
            mapaRecepcion.put("controlCalidadId", controlCalidad?"controlCalidadCobrePlata/show/${controlCalidad.id}":0)

            liquidacion=LiquidacionDeCobrePlata.findByRecepcionDeComplejo(recepcionBase)
            mapaRecepcion.put("liquidacionId", liquidacion?"liquidacionDeCobrePlata/show/${liquidacion.id}":0)

            anticipoDetalle=AnticipoDetalle.findByRecepcionId(recepcionBase.id)
            mapaRecepcion.put("anticipoId", anticipoDetalle?"anticipo/show/${anticipoDetalle.anticipo.id}":0)

            pagoTransporteDetalle=DetallePagoTransporte.findByRecepcionId(recepcionBase.id)
            mapaRecepcion.put("pagoTransporteId", pagoTransporteDetalle?"pagoTransporte/show/${pagoTransporteDetalle.pagoTransporte.id}":0)

            enlacesList.add(mapaRecepcion)
        }

        render enlacesList as JSON
    }
}
