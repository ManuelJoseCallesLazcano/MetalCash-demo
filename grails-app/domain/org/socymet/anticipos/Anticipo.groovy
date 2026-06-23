package org.socymet.anticipos

import org.socymet.proveedor.Cliente
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.seguridad.SecUser

class Anticipo {
    static auditable = true

    static searchable = true

    Deposito deposito

    Cliente cliente
    String nombreCliente
    Empresa empresa
    String nombreEmpresa

    BigDecimal totalAnticipos = 0
    String literalTotalAnticipos

    BigDecimal totalPagado = 0
    BigDecimal totalPorPagar = 0

    String tipoDeMineral

    String lotes

    String descripcion = "POR LOTES: "

    String observaciones = "-"

    SecUser usuario

    transient springSecurityService

    static hasMany = [cuotas: AnticipoCuota]

    static constraints = {
        deposito nullable: false
        cliente()
        nombreCliente()
        empresa()
        nombreEmpresa()

        totalAnticipos min: 0.0
        literalTotalAnticipos nullable: true
        totalPagado min: 0.0
        totalPorPagar min: 0.0

        tipoDeMineral inList: ["COMPLEJO","ORO"], nullable: false

        lotes blank: false, nullable: false

        descripcion blank: false

        observaciones blank: true, nullable: true

        usuario display: false, nullable: true
    }

    static mapping = {
        lotes type: 'text'
        cuotas sort: 'fecha', order: 'asc'
    }

    def beforeInsert = {
        this.usuario = springSecurityService.getCurrentUser() as SecUser
        this.deposito = usuario.deposito
    }

    String toString() {
        def n = cuotas?.size() ?: 0
        // Último anticipo otorgado (cuota más reciente por orden de emisión), en el
        // formato del detalle: numeroComprobante/yy (gestión minera). Si no hay, 's/c'.
        def ultima = cuotas ? cuotas.max { it.id ?: 0 } : null
        def comprobante = ultima ?
            "N° ${ultima.numeroComprobante}/${ultima.gestionMinera ? new java.text.SimpleDateFormat('yy').format(ultima.gestionMinera) : '?'}" :
            'N° s/c'
        "Anticipo ${comprobante} · ${nombreCliente ?: 'Sin cliente'} · ${n} ${n == 1 ? 'anticipo' : 'anticipos'} · Bs " +
            new java.text.DecimalFormat('#,##0.00').format(totalAnticipos ?: 0)
    }
}
