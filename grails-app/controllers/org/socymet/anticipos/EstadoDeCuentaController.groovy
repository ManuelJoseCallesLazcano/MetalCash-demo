package org.socymet.anticipos
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.proveedor.Cliente
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_RECEPCION','ROLE_CAJA'])
@Transactional
class EstadoDeCuentaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [estadoDeCuentaInstanceList: EstadoDeCuenta.list(params), estadoDeCuentaInstanceTotal: EstadoDeCuenta.count()]
    }

    def create() {
        [estadoDeCuentaInstance: new EstadoDeCuenta(params)]
    }

    def save() {
        def estadoDeCuentaInstance = new EstadoDeCuenta(params)
        if (!estadoDeCuentaInstance.save(flush: true)) {
            render(view: "create", model: [estadoDeCuentaInstance: estadoDeCuentaInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'estadoDeCuenta.label', default: 'EstadoDeCuenta'), estadoDeCuentaInstance.id])
        redirect(action: "show", id: estadoDeCuentaInstance.id)
    }

    def show(Long id) {
        def estadoDeCuentaInstance = EstadoDeCuenta.get(id)
        if (!estadoDeCuentaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'estadoDeCuenta.label', default: 'EstadoDeCuenta'), id])
            redirect(action: "list")
            return
        }

        [estadoDeCuentaInstance: estadoDeCuentaInstance]
    }

    def edit(Long id) {
        def estadoDeCuentaInstance = EstadoDeCuenta.get(id)
        if (!estadoDeCuentaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'estadoDeCuenta.label', default: 'EstadoDeCuenta'), id])
            redirect(action: "list")
            return
        }

        [estadoDeCuentaInstance: estadoDeCuentaInstance]
    }

    def update(Long id, Long version) {
        def estadoDeCuentaInstance = EstadoDeCuenta.get(id)
        if (!estadoDeCuentaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'estadoDeCuenta.label', default: 'EstadoDeCuenta'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (estadoDeCuentaInstance.version > version) {
                estadoDeCuentaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'estadoDeCuenta.label', default: 'EstadoDeCuenta')] as Object[],
                        "Another user has updated this EstadoDeCuenta while you were editing")
                render(view: "edit", model: [estadoDeCuentaInstance: estadoDeCuentaInstance])
                return
            }
        }

        estadoDeCuentaInstance.properties = params

        if (!estadoDeCuentaInstance.save(flush: true)) {
            render(view: "edit", model: [estadoDeCuentaInstance: estadoDeCuentaInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'estadoDeCuenta.label', default: 'EstadoDeCuenta'), estadoDeCuentaInstance.id])
        redirect(action: "show", id: estadoDeCuentaInstance.id)
    }

    def delete(Long id) {
        def estadoDeCuentaInstance = EstadoDeCuenta.get(id)
        if (!estadoDeCuentaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'estadoDeCuenta.label', default: 'EstadoDeCuenta'), id])
            redirect(action: "list")
            return
        }

        try {
            estadoDeCuentaInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'estadoDeCuenta.label', default: 'EstadoDeCuenta'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'estadoDeCuenta.label', default: 'EstadoDeCuenta'), id])
            redirect(action: "show", id: id)
        }
    }

    def clientesJSON() {
        def clientes = Cliente.findAllByCiLike("${params.term}%")
        def clientesList = []
        clientes.each {
            def mapaClientes = [:]
            //parametros en JSON para JQuery UI Autocomplete
            mapaClientes.put("id",it.id)
            mapaClientes.put("label","${it.ci} : ${it.nombre}") //son las cadenas que se muestran en la lista
            mapaClientes.put("value",it.ci) //es la cadena que se establece en el input despues de ser seleccionado
            //otros parametros
            mapaClientes.put("clienteId",it.id)
            mapaClientes.put("nombreCliente",it.nombre)
            mapaClientes.put("empresaId",it.empresa.id)
            mapaClientes.put("nombreEmpresa",it.empresa.toString())

            def ultimosEstados = EstadoDeCuenta.findAllByCliente(it)
            def ultimoEstado = null
            if (ultimosEstados){
                ultimoEstado = ultimosEstados.get(ultimosEstados.size()-1)
                mapaClientes.put("fechaTransaccion",new java.text.SimpleDateFormat("dd/MM/yyyy").format(ultimoEstado.fecha))
                mapaClientes.put("debe",ultimoEstado.debe)
                mapaClientes.put("haber",ultimoEstado.haber)
                mapaClientes.put("saldo",ultimoEstado.saldo)
            }else{
                mapaClientes.put("fechaTransaccion","-")
                mapaClientes.put("debe",0)
                mapaClientes.put("haber",0)
                mapaClientes.put("saldo",0)
            }

            clientesList.add(mapaClientes)
        }
        render clientesList as JSON
    }

    def ultimoEstadoCuenta = {
        def cliente = Cliente.get(params.clienteId)
        def ultimoEstado = EstadoDeCuenta.findByCliente(cliente,[sort: 'id', order: 'desc'])
        render([
            mensaje: ultimoEstado?"ULTIMO ESTADO DE CUENTA: Fecha: ${new java.text.SimpleDateFormat("dd/MM/yyyy").format(ultimoEstado.fecha)} | Debe: Bs ${ultimoEstado.debe} | Haber: Bs ${ultimoEstado.haber} | Saldo: Bs ${ultimoEstado.saldo}":"-",
            saldoActual: ultimoEstado?ultimoEstado.saldo:0
        ] as JSON)
    }
}
