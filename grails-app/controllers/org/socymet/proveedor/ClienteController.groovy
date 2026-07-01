package org.socymet.proveedor
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.seguridad.SecUser
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_RECEPCION','ROLE_CONTROL_CALIDAD','ROLE_LIQUIDACION'])
@Transactional
class ClienteController {
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
            [clienteInstanceList : Cliente.findAllByCiIlikeOrNombreIlike(pattern, pattern, params),
             clienteInstanceTotal: Cliente.countByCiIlikeOrNombreIlike(pattern, pattern)]
        } else {
            [clienteInstanceList: Cliente.list(params), clienteInstanceTotal: Cliente.count()]
        }
    }

    def create() {
        [clienteInstance: new Cliente(params)]
    }

    def save() {
        def clienteInstance = new Cliente(params)
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        clienteInstance.deposito = usuarioActual.deposito
        if (!clienteInstance.save(flush: true)) {
            render(view: "create", model: [clienteInstance: clienteInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'cliente.label', default: 'Cliente'), clienteInstance.toString()])
        redirect(action: "show", id: clienteInstance.id)
    }

    def show(Long id) {
        def clienteInstance = Cliente.get(id)
        if (!clienteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cliente.label', default: 'Cliente'), id])
            redirect(action: "list")
            return
        }

        [clienteInstance: clienteInstance]
    }

    def edit(Long id) {
        def clienteInstance = Cliente.get(id)
        if (!clienteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cliente.label', default: 'Cliente'), id])
            redirect(action: "list")
            return
        }

        [clienteInstance: clienteInstance]
    }

    def update(Long id, Long version) {
        def clienteInstance = Cliente.get(id)
        if (!clienteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cliente.label', default: 'Cliente'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (clienteInstance.version > version) {
                clienteInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'cliente.label', default: 'Cliente')] as Object[],
                        "Another user has updated this Cliente while you were editing")
                render(view: "edit", model: [clienteInstance: clienteInstance])
                return
            }
        }

        clienteInstance.properties = params

        if (!clienteInstance.save(flush: true)) {
            render(view: "edit", model: [clienteInstance: clienteInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'cliente.label', default: 'Cliente'), clienteInstance.toString()])
        redirect(action: "show", id: clienteInstance.id)
    }

    def delete(Long id) {
        def clienteInstance = Cliente.get(id)
        if (!clienteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cliente.label', default: 'Cliente'), id])
            redirect(action: "list")
            return
        }

        try {
            clienteInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'cliente.label', default: 'Cliente'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'cliente.label', default: 'Cliente'), id])
            redirect(action: "show", id: id)
        }
    }

    def getUsuarioActual(){
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        [usuarioActual: usuarioActual.deposito]
    }

    def getEmpresasSegunUsuario() {
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
//        render text: g.select(id: 'accountNumberSelect', name: 'accountNumber', from: Account.findAllByNumber(number), optionKey: "id", optionValue: "someProperty")
        render text: g.select(id: 'empresa', name: 'empresa.id', from: Empresa.findAllByDeposito(usuarioActual.deposito), optionKey: "id")
    }

    def clientesJSON() {
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        def clientes = Cliente.findAllByCiLikeAndDeposito("${params.term}%",usuarioActual.deposito)
        def clientesList = []
        clientes.each {
            def mapaClientes = [:]
            //parametros en JSON para JQuery UI Autocomplete
            mapaClientes.put("id",it.id)
            mapaClientes.put("label","${it.ci} : ${it.nombre} [${it.empresa.toString()}]") //son las cadenas que se muestran en la lista
            mapaClientes.put("value",it.ci) //es la cadena que se establece en el input despues de ser seleccionado
            //otros parametros
            mapaClientes.put("clienteId",it.id)
            mapaClientes.put("nombreCliente",it.nombre)
            mapaClientes.put("empresaId",it.empresa.id)
            mapaClientes.put("nombreEmpresa",it.empresa.nombreDeEmpresa)
            clientesList.add(mapaClientes)
        }
        render clientesList as JSON
    }

    def clientesPorNombreJSON() {
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        def clientes = Cliente.findAllByNombreLike("${params.term}%")
        def clientesList = []
        clientes.each {
            def mapaClientes = [:]
            //parametros en JSON para JQuery UI Autocomplete
            mapaClientes.put("id",it.id)
            //mapaClientes.put("label",it.ci) //son las cadenas que se muestran en la lista
            mapaClientes.put("label","${it.nombre} [${it.empresa.toString()}]") //son las cadenas que se muestran en la lista
            mapaClientes.put("value",it.nombre) //es la cadena que se establece en el input despues de ser seleccionado
            //otros parametros
            mapaClientes.put("clienteId",it.id)
            mapaClientes.put("ciCliente",it.ci)
            mapaClientes.put("empresaId",it.empresa.id)
            mapaClientes.put("nombreEmpresa",it.empresa.nombreDeEmpresa)
            clientesList.add(mapaClientes)
        }
        render clientesList as JSON
    }

    @Secured(['ROLE_ADMIN','ROLE_RECEPCION','ROLE_CONTROL_CALIDAD','ROLE_LIQUIDACION','ROLE_CAJA','ROLE_REPORTES'])
    def clientesBusquedaJSON() {
        def term = params.q ?: ''
        def pattern = "%${term}%"
        // Filtro opcional por empresa (cascada empresa→cliente). Si no llega empresaId, busca en todos.
        def empresa = params.empresaId ? Empresa.get(params.long('empresaId')) : null
        def clientes = Cliente.createCriteria().list(max: 50) {
            or { ilike('nombre', pattern); ilike('ci', pattern) }
            if (empresa) eq('empresa', empresa)
            order('nombre', 'asc')
        }
        def results = clientes.collect { c ->
            [
                id         : c.id,
                text       : "${c.ci} — ${c.nombre} [${c.empresa.toString()}]",
                nombreCliente: c.nombre,
                ciCliente  : c.ci,
                empresaId  : c.empresa.id,
                nombreEmpresa: c.empresa.nombreDeEmpresa
            ]
        }
        render([results: results] as JSON)
    }

    def crearReporte = {
        def cliente = Cliente.get(params.id)
        chain(controller:'jasper',action:'index',model:[data:cliente],params:params)
    }
}
