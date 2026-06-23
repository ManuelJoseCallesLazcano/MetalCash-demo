package org.socymet.anticipos

import org.socymet.proveedor.Cliente
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.seguridad.SecUser

class AnticipoContraFuturaEntrega {
    static auditable = true

    Integer numeroAnticipo //e.g.: GENERADO: 1, PARA MOSTRAR: 0001
    Date gestionMinera     // la numeración reinicia en cada gestión minera

    Cliente cliente
    Empresa empresa
    Date fechaDeAnticipo
    String compromiso
    BigDecimal importe
    String importeLiteral
    String observaciones

    Integer liquidacionId

    Boolean anulado = false   // no se edita ni elimina: se anula (reversa en el ledger)

    SecUser usuario

    transient springSecurityService

    static constraints = {
        // El correlativo se repite entre gestiones, pero es único dentro de una misma gestión
        numeroAnticipo unique: 'gestionMinera', nullable: true
        gestionMinera nullable: false
        cliente(blank: false)
        empresa(blank: false)
        compromiso(blank: false)
        fechaDeAnticipo(blank: false)
        importe(blank: false, min: 0.0)
        importeLiteral(blank: false)
        observaciones(blank: true, nullable: true)
        liquidacionId nullable: true
        anulado nullable: true
        usuario(display: false, nullable: true)
    }

    // Asignación en beforeValidate (gestionMinera y numeroAnticipo se calculan en
    // backend; gestionMinera es nullable:false y la validación corre antes del insert).
    def beforeValidate = {
        if (this.numeroAnticipo != null) return   // solo en el alta

        this.gestionMinera = RecepcionDeComplejo.gestionMineraActiva()

        // Siguiente correlativo dentro de la gestión activa (reinicia por gestión)
        def maxNumeroAnticipo = AnticipoContraFuturaEntrega.createCriteria().get {
            eq 'gestionMinera', this.gestionMinera
            projections { max 'numeroAnticipo' }
        }
        this.numeroAnticipo = (maxNumeroAnticipo ?: 0) + 1
    }

    def beforeInsert = {
        this.empresa = this.cliente.empresa
        this.usuario = springSecurityService.getCurrentUser()
    }

    def beforeUpdate = {
        this.usuario = springSecurityService.getCurrentUser()
    }

    def afterInsert = {
        def ultimoEstadoDeCuenta = EstadoDeCuenta.findAllByCliente(this.cliente, [sort: "id", order: "desc"])
        def ultimoSaldo = (ultimoEstadoDeCuenta.size()>0)?ultimoEstadoDeCuenta.get(0).saldo:0
        def saldo = this.importe + ultimoSaldo
        def estadoDeCuenta = new EstadoDeCuenta(
                cliente: this.cliente,
                empresa: this.empresa,
                ci: this.cliente.ci,
                nombre: this.cliente.nombre,
                nombreEmpresa: this.empresa.nombreDeEmpresa,
                fecha: this.fechaDeAnticipo,
                numeroComprobante: this.numeroAnticipo,
                detalle: this.compromiso,
                debe: this.importe,
                haber: 0.0,
                saldo: saldo,
                liquidacionId: 0,
                tipoMovimiento: TipoMovimiento.ANTICIPO_FUTURA_ENTREGA,
                origenId: this.id
        )
        estadoDeCuenta.save(failOnError: true)
    }

    String toString(){
        "${numeroAnticipo}/${gestionMinera ? new java.text.SimpleDateFormat('yy').format(gestionMinera) : '?'}"
    }
}
