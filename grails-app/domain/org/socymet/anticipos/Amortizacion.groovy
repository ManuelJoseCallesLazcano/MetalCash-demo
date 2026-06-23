package org.socymet.anticipos

import org.socymet.proveedor.Cliente
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.seguridad.SecUser

class Amortizacion {
    static auditable = true

    Integer numeroAmortizacion //e.g.: GENERADO: 1, PARA MOSTRAR: 0001
    Date gestionMinera         // la numeración reinicia en cada gestión minera

    Cliente cliente
    Empresa empresa
    String ci="-"
    String nombre="-"
    String nombreEmpresa="-"
    Date fecha
    BigDecimal importe
    String importeLiteral
    BigDecimal saldoActual
    BigDecimal saldoPorPagar
    String concepto
    String observaciones

    Boolean anulado = false   // no se edita ni elimina: se anula (reversa en el ledger)

    SecUser usuario

    transient springSecurityService

    static constraints = {
        // El correlativo se repite entre gestiones, pero es único dentro de una misma gestión
        numeroAmortizacion unique: 'gestionMinera', nullable: true
        gestionMinera nullable: false
        cliente()
        empresa()
        ci(blank: false)
        nombre(blank: false)
        nombreEmpresa(blank: false)
        fecha(blank: false)
        importe(blank: false)
        importeLiteral(blank: false)
        saldoActual()
        saldoPorPagar()
        concepto(blank: false)
        observaciones(blank: true, nullable: true)
        anulado nullable: true

        usuario(display: false, nullable: true)
    }

    // Asignación en beforeValidate (gestionMinera y numeroAmortizacion se calculan en
    // backend; gestionMinera es nullable:false y la validación corre antes del insert).
    def beforeValidate = {
        if (this.numeroAmortizacion != null) return   // solo en el alta

        this.gestionMinera = RecepcionDeComplejo.gestionMineraActiva()

        // Siguiente correlativo dentro de la gestión activa (reinicia por gestión)
        def maxNumeroAmortizacion = Amortizacion.createCriteria().get {
            eq 'gestionMinera', this.gestionMinera
            projections { max 'numeroAmortizacion' }
        }
        this.numeroAmortizacion = (maxNumeroAmortizacion ?: 0) + 1
    }

    def beforeInsert = {
        this.empresa = this.cliente.empresa
        this.nombreEmpresa = this.cliente.empresa.toString()
        this.ci = this.cliente.ci
        this.nombre = this.cliente.nombre
        this.usuario = springSecurityService.getCurrentUser()
    }

    def afterInsert = {
        Amortizacion.withNewTransaction {
            // Último saldo del cliente (mismo criterio que los demás orígenes: id desc).
            // Siempre se registra el movimiento; si no hay estados previos, último saldo = 0.
            def ultimosEstados = EstadoDeCuenta.findAllByCliente(cliente, [sort: "id", order: "desc"])
            def ultimoSaldo = (ultimosEstados.size() > 0) ? ultimosEstados.get(0).saldo : 0
            def estadoDeCuenta = new EstadoDeCuenta(
                    cliente: cliente,
                    empresa: empresa,
                    ci: ci,
                    nombre: nombre,
                    nombreEmpresa: nombreEmpresa,
                    fecha: fecha,
                    numeroComprobante: this.numeroAmortizacion,
                    detalle: concepto,
                    debe: 0,
                    haber: importe,
                    saldo: ultimoSaldo - importe,
                    liquidacionId: 0,
                    tipoMovimiento: TipoMovimiento.AMORTIZACION,
                    origenId: this.id
            )
            estadoDeCuenta.save(failOnError: true, flush: true)
        }
    }

    String toString(){
        "${numeroAmortizacion}/${gestionMinera ? new java.text.SimpleDateFormat('yy').format(gestionMinera) : '?'}"
    }
}
