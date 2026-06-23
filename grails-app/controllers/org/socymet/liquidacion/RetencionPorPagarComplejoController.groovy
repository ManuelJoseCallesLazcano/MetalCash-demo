package org.socymet.liquidacion
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.proveedor.Empresa
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class RetencionPorPagarComplejoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [retencionPorPagarComplejoInstanceList: RetencionPorPagarComplejo.list(params), retencionPorPagarComplejoInstanceTotal: RetencionPorPagarComplejo.count()]
    }

    def create() {
        [retencionPorPagarComplejoInstance: new RetencionPorPagarComplejo(params)]
    }

    def save() {
        def retencionPorPagarComplejoInstance = new RetencionPorPagarComplejo(params)
        if (!retencionPorPagarComplejoInstance.save(flush: true)) {
            render(view: "create", model: [retencionPorPagarComplejoInstance: retencionPorPagarComplejoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'retencionPorPagarComplejo.label', default: 'RetencionPorPagarComplejo'), retencionPorPagarComplejoInstance.id])
        redirect(action: "show", id: retencionPorPagarComplejoInstance.id)
    }

    def show(Long id) {
        def retencionPorPagarComplejoInstance = RetencionPorPagarComplejo.get(id)
        if (!retencionPorPagarComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'retencionPorPagarComplejo.label', default: 'RetencionPorPagarComplejo'), id])
            redirect(action: "list")
            return
        }

        [retencionPorPagarComplejoInstance: retencionPorPagarComplejoInstance]
    }

    def edit(Long id) {
        def retencionPorPagarComplejoInstance = RetencionPorPagarComplejo.get(id)
        if (!retencionPorPagarComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'retencionPorPagarComplejo.label', default: 'RetencionPorPagarComplejo'), id])
            redirect(action: "list")
            return
        }

        [retencionPorPagarComplejoInstance: retencionPorPagarComplejoInstance]
    }

    def update(Long id, Long version) {
        def retencionPorPagarComplejoInstance = RetencionPorPagarComplejo.get(id)
        if (!retencionPorPagarComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'retencionPorPagarComplejo.label', default: 'RetencionPorPagarComplejo'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (retencionPorPagarComplejoInstance.version > version) {
                retencionPorPagarComplejoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'retencionPorPagarComplejo.label', default: 'RetencionPorPagarComplejo')] as Object[],
                        "Another user has updated this RetencionPorPagarComplejo while you were editing")
                render(view: "edit", model: [retencionPorPagarComplejoInstance: retencionPorPagarComplejoInstance])
                return
            }
        }

        retencionPorPagarComplejoInstance.properties = params

        if (!retencionPorPagarComplejoInstance.save(flush: true)) {
            render(view: "edit", model: [retencionPorPagarComplejoInstance: retencionPorPagarComplejoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'retencionPorPagarComplejo.label', default: 'RetencionPorPagarComplejo'), retencionPorPagarComplejoInstance.id])
        redirect(action: "show", id: retencionPorPagarComplejoInstance.id)
    }

    def delete(Long id) {
        def retencionPorPagarComplejoInstance = RetencionPorPagarComplejo.get(id)
        if (!retencionPorPagarComplejoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'retencionPorPagarComplejo.label', default: 'RetencionPorPagarComplejo'), id])
            redirect(action: "list")
            return
        }

        try {
            retencionPorPagarComplejoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'retencionPorPagarComplejo.label', default: 'RetencionPorPagarComplejo'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'retencionPorPagarComplejo.label', default: 'RetencionPorPagarComplejo'), id])
            redirect(action: "show", id: id)
        }
    }

    def buscarRetencionesJSON() {
        def periodo = new Date().parse("yyyy-MM",""+params.periodo_year+"-"+params.periodo_month)
        def quincena = params.quincena.toString()
        def empresa=null
        def retencionesPorPagar = null
        def fechaInicial=null
        def fechaFinal=null
        def cal=Calendar.getInstance()
        cal.setTime(periodo)
        if (quincena.equals("1ra. QUINCENA")){
            fechaInicial=new Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),1)
            fechaFinal=new Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),15)
        }else{
            fechaInicial=new Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),16)
            fechaFinal=new Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),cal.getActualMaximum(Calendar.DAY_OF_MONTH))
        }
        log.error("\nfechaInicial: $fechaInicial\nfechaFinal: $fechaFinal")
        if (!params.empresaId.toString().equals("null"))
            empresa = Empresa.get(params.empresaId.toString().toLong())
        if (empresa){
            log.error("buscando con empresa $empresa")
            retencionesPorPagar=RetencionPorPagarComplejo.withCriteria {
                projections {
                    distinct "descripcion"
                }
                and {
                    eq("empresa",empresa)
                    eq("pagado","NO")
                }
                between("fechaDeRegistro",fechaInicial,fechaFinal)
            }.sort()
        }else{
            log.error("buscando sin empresa")
            retencionesPorPagar=RetencionPorPagarComplejo.withCriteria {
                projections {
                    distinct "descripcion"
                }
                eq("pagado","NO")
                between("fechaDeRegistro",fechaInicial,fechaFinal)
            }.sort()
        }

        def retencionesPorPagarList = []
        if (retencionesPorPagar){
            retencionesPorPagar.each { retencion ->
                def mapaRetencion = [:]
                mapaRetencion.put("retencion", retencion)
                retencionesPorPagarList.add(mapaRetencion)
//                log.error("\n$retencion")
            }
        }
        render([retenciones: (retencionesPorPagarList as JSON).toString()] as JSON)
    }

    def adicionarRetencionSeleccionadaJSON() {
        def periodo = new Date().parse("yyyy-MM",""+params.periodo_year+"-"+params.periodo_month)
        def quincena = params.quincena.toString()
        def retencionSeleccionada = params.retencionSeleccionada.toString()
        def empresa=null
        def retencionesPorPagar = null
        def lotesRetencionesPorPagar = null
        def fechaInicial=null
        def fechaFinal=null
        def cal=Calendar.getInstance()
        cal.setTime(periodo)
        if (quincena.equals("1ra. QUINCENA")){
            fechaInicial=new Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),1)
            fechaFinal=new Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),15)
        }else{
            fechaInicial=new Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),16)
            fechaFinal=new Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),cal.getActualMaximum(Calendar.DAY_OF_MONTH))
        }
        log.error("\nfechaInicial: $fechaInicial\nfechaFinal: $fechaFinal")
        if (!params.empresaId.toString().equals("null"))
            empresa = Empresa.get(params.empresaId.toString().toLong())
        if (empresa){
            log.error("buscando con empresa $empresa")
            retencionesPorPagar=RetencionPorPagarComplejo.withCriteria {
                projections {
                    sum("monto")
                }
                and {
                    eq("empresa",empresa)
                    eq("descripcion",retencionSeleccionada)
                    eq("pagado","NO")
                }
                between("fechaDeRegistro",fechaInicial,fechaFinal)
            }
            lotesRetencionesPorPagar=RetencionPorPagarComplejo.findAllByEmpresaAndDescripcionAndPagadoAndFechaDeRegistroBetween(empresa,retencionSeleccionada,"NO",fechaInicial,fechaFinal)
        }else{
            log.error("buscando sin empresa")
            retencionesPorPagar=RetencionPorPagarComplejo.withCriteria {
                projections {
                    sum("monto")
                }
                and {
                    eq("descripcion",retencionSeleccionada)
                    eq("pagado","NO")
                }
                between("fechaDeRegistro",fechaInicial,fechaFinal)
            }
            lotesRetencionesPorPagar=RetencionPorPagarComplejo.findAllByDescripcionAndPagadoAndFechaDeRegistroBetween(retencionSeleccionada,"NO",fechaInicial,fechaFinal)
        }
        def totalRetencionSeleccionada = [:]
        def lotesRetencionSeleccionada = [:]
        if (retencionesPorPagar){
            log.error("\nsuma de $retencionSeleccionada=$retencionesPorPagar")
            totalRetencionSeleccionada.put("retencion",retencionSeleccionada)
            totalRetencionSeleccionada.put("total",retencionesPorPagar[0])

            lotesRetencionesPorPagar.each {
                log.error("${it.descripcion} = ${it.monto}")
            }

            lotesRetencionSeleccionada.put("lotes",lotesRetencionesPorPagar)
        }
        render([
            totalRetencion: totalRetencionSeleccionada,
            lotesRetenciones: lotesRetencionesPorPagar
//            lotesRetenciones = lotesRetencionSeleccionada
        ] as JSON)
    }
}
