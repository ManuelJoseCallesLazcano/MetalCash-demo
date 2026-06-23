package org.socymet.proveedor
import grails.converters.JSON
import grails.gorm.transactions.Transactional

import org.grails.web.json.JSONArray
import org.socymet.cotizaciones.CotizacionDeDolar
import org.socymet.proveedor.bonos.BonoCalidad
import org.socymet.proveedor.bonos.BonoCantidad
import org.socymet.proveedor.bonos.BonoIncentivo
import org.socymet.seguridad.SecUser
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_RECEPCION'])
@Transactional
class EmpresaController {

    transient springSecurityService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def q = params.q?.trim()
        if (q) {
            def pattern = "%${q}%"
            def results = Empresa.createCriteria().list(params) {
                or {
                    ilike('nombreDeEmpresa', pattern)
                    ilike('codigoEmpresa', pattern)
                    ilike('municipio', pattern)
                    ilike('nim', pattern)
                }
            }
            [empresaInstanceList: results, empresaInstanceTotal: results.totalCount]
        } else {
            [empresaInstanceList: Empresa.list(params), empresaInstanceTotal: Empresa.count()]
        }
    }

    def empresaBusquedaJSON() {
        def term = params.q ?: ''
        def pattern = "%${term}%"
        def empresas = Empresa.findAllByNombreDeEmpresaIlikeOrCodigoEmpresaIlike(pattern, pattern, [sort: 'nombreDeEmpresa'])
        def results = empresas.collect { e ->
            [
                id  : e.id,
                text: e.toString()
            ]
        }
        render([results: results] as JSON)
    }

    def create() {
        [empresaInstance: new Empresa(params)]
    }

    def save() {
        def empresaInstance = new Empresa(params)
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        empresaInstance.deposito = usuarioActual.deposito
        if (!empresaInstance.save(flush: true)) {
            render(view: "create", model: [empresaInstance: empresaInstance])
            return
        }

        registrarRelaciones(empresaInstance)

//        flash.message = message(code: 'default.created.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresaInstance.id])
        flash.message = message(code: 'default.created.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresaInstance.toString()])
        redirect(action: "show", id: empresaInstance.id)
    }

    def show(Long id) {
        def empresaInstance = Empresa.get(id)
        if (!empresaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresa.label', default: 'Empresa'), id])
            redirect(action: "list")
            return
        }

        [empresaInstance: empresaInstance]
    }

    def edit(Long id) {
        def empresaInstance = Empresa.get(id)
        if (!empresaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresa.label', default: 'Empresa'), id])
            redirect(action: "list")
            return
        }

        [empresaInstance: empresaInstance]
    }

    def update(Long id, Long version) {
        def empresaInstance = Empresa.get(id)
        if (!empresaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresa.label', default: 'Empresa'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (empresaInstance.version > version) {
                empresaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'empresa.label', default: 'Empresa')] as Object[],
                          "Another user has updated this Empresa while you were editing")
                render(view: "edit", model: [empresaInstance: empresaInstance])
                return
            }
        }

        empresaInstance.properties = params

        if (!empresaInstance.save(flush: true)) {
            render(view: "edit", model: [empresaInstance: empresaInstance])
            return
        }

        eliminarRelaciones(empresaInstance)
        registrarRelaciones(empresaInstance)

        flash.message = message(code: 'default.updated.message', args: [message(code: 'empresa.label', default: 'Empresa'), empresaInstance.toString()])
        redirect(action: "show", id: empresaInstance.id)
    }

    def delete(Long id) {
        def empresaInstance = Empresa.get(id)
        if (!empresaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'empresa.label', default: 'Empresa'), id])
            redirect(action: "list")
            return
        }

        try {
            empresaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'empresa.label', default: 'Empresa'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'empresa.label', default: 'Empresa'), id])
            redirect(action: "show", id: id)
        }
    }

    // Crea las entidades relacionadas (retenciones, bonos de cantidad y cuadrillas)
    // a partir del JSON de la empresa ya persistida. Se ejecuta en la misma transacción
    // que el save del controller, donde la empresa ya tiene id y es visible (a diferencia
    // del antiguo afterInsert/afterUpdate con withNewTransaction, que no la veía).
    private void registrarRelaciones(Empresa empresa) {
        if (empresa.retenciones) {
            new JSONArray(empresa.retenciones).each {
                new EmpresaRetenciones(
                    empresa: empresa,
                    descripcion: it.getAt("DESCRIPCION"),
                    tipoDeRetencion: it.getAt("TIPO"),
                    cantidadDescuento: it.getAt("CANTIDAD"),
                    unidadDeDescuento: it.getAt("UNIDAD"),
                    asignacionDelDescuento: it.getAt("ASIGNACION")
                ).save(failOnError: true)
            }
        }

        if (empresa.bonoCantidadComplejo)    empresa.registrarBonoCantidad(empresa, "Complejos",   "ZnPbAg", empresa.bonoCantidadComplejo)
        if (empresa.bonoCantidadConcentrado) empresa.registrarBonoCantidad(empresa, "Concentrado", "ZnPbAg", empresa.bonoCantidadComplejo)
        if (empresa.bonoCantidadCobre)       empresa.registrarBonoCantidad(empresa, "Cobre",       "ZnPbAg", empresa.bonoCantidadComplejo)

        if (empresa.cuadrillas) {
            new JSONArray(empresa.cuadrillas).each {
                new Cuadrilla(empresa: empresa, nombreCuadrilla: it.getAt("nombreCuadrilla")).save(failOnError: true)
            }
        }
    }

    // Elimina las entidades relacionadas antes de regenerarlas en una actualización.
    private void eliminarRelaciones(Empresa empresa) {
        EmpresaRetenciones.findAllByEmpresa(empresa).each { it.delete(flush: true) }
        BonoCalidad.findAllByEmpresa(empresa).each { it.delete(flush: true) }
        BonoCantidad.findAllByEmpresa(empresa).each { it.delete(flush: true) }
        BonoIncentivo.findAllByEmpresa(empresa).each { it.delete(flush: true) }
        Cuadrilla.findAllByEmpresa(empresa).each { it.delete(flush: true) }
    }

    def datosTransporteComplejosJSON() {
        def empresa = Empresa.get(params.empresaId)
        def cotizacionDeDolar = CotizacionDeDolar.findByActivo(1)
        log.info("*********** RETENCION ID: ${params.retencionId} ***************")
        render([
            costoTransporteComplejos: (empresa)?empresa.costoTransporteComplejos:0,
            unidadMonetariaComplejos: (empresa)?empresa.unidadMonetariaComplejos:0,
            unidadDeCobroComplejos: (empresa)?empresa.unidadDeCobroComplejos:0,
            costoTransporteConcentrados: (empresa)?empresa.costoTransporteConcentrados:0,
            unidadMonetariaConcentrados: (empresa)?empresa.unidadMonetariaConcentrados:0,
            unidadDeCobroConcentrados: (empresa)?empresa.unidadDeCobroConcentrados:0,
            //tipo de cambio comercial para realizar el calculo de costo de transporte
            //cuando los parametros estan especificados en dolares
            tipoDeCambioComercial: (cotizacionDeDolar)?cotizacionDeDolar.tipoDeCambioComercial:0
        ] as JSON)
    }

    def datosTransporteEstanoJSON() {
        def empresa = Empresa.get(params.empresaId)
        def cotizacionDeDolar = CotizacionDeDolar.findByActivo(1)
        log.info("*********** RETENCION ID: ${params.retencionId} ***************")
        render([
            /*$('#costoTransporteComplejo').val(data.costoTransporteComplejo);
            $('#unidadMonetariaComplejo').val(data.unidadMonetariaComplejo);
            $('#unidadDeCobroComplejo').val(data.unidadDeCobroComplejo);*/
            costoTransporteComplejos: (empresa)?empresa.costoTransporteComplejos:0,
            unidadMonetariaComplejos: (empresa)?empresa.unidadMonetariaComplejos:0,
            unidadDeCobroComplejos: (empresa)?empresa.unidadDeCobroComplejos:0,
            costoTransporteConcentrados: (empresa)?empresa.costoTransporteConcentrados:0,
            unidadMonetariaConcentrados: (empresa)?empresa.unidadMonetariaConcentrados:0,
            unidadDeCobroConcentrados: (empresa)?empresa.unidadDeCobroConcentrados:0,
            //tipo de cambio comercial para realizar el calculo de costo de transporte
            //cuando los parametros estan especificados en dolares
            tipoDeCambioComercial: (cotizacionDeDolar)?cotizacionDeDolar.tipoDeCambioComercial:0
        ] as JSON)
    }

    def cuadrillasDeEmpresa = {
        def cuadrillas = Cuadrilla.withCriteria{
            projections {
                property("nombreCuadrilla")
            }
            eq("empresa",Empresa.get(params.id))
        }.sort()
        cuadrillas.add(0,"NO PERTENCE")
        render g.select(from: cuadrillas, id: 'cuadrilla', name: "cuadrilla")
    }

    def cuadrillasDeEmpresaParaBono = {
//        def cuadrillas = Cuadrilla.withCriteria{
//            projections {
//                property("nombreCuadrilla")
//            }
//            eq("empresa",Empresa.get(params.id))
//        }.sort()
        def cuadrillas=Cuadrilla.findAllByEmpresa(Empresa.get(params.id))
        render g.select(from: cuadrillas, id: 'cuadrilla', name: "cuadrilla", optionKey: "id")
    }
}
