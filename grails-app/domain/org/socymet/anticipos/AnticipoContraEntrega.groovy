package org.socymet.anticipos

import org.socymet.seguridad.SecUser

class AnticipoContraEntrega {
    Integer numeroAnticipo //e.g.: GENERADO: 1, PARA MOSTRAR: 0001

    String lote
    //DATOS DE LA RECEPCION (DUPLICANDO INFORMACION PARA FACILITAR LA GENERACION DE REPORTES)
    Integer recepcionId
    String nombreCliente
    String nombreEmpresa
    String fechaDeRecepcion
    BigDecimal pesoBruto
    //
    Date fechaDeAnticipo
    BigDecimal importe
    String importeLiteral
    String anticipoPagado
    String observaciones

    SecUser usuario

    transient springSecurityService

    static constraints = {
        numeroAnticipo(nullable: true)
        lote(blank: false)
        recepcionId(display: false, nullable: true)
        nombreCliente(blank: false)
        nombreEmpresa(blank: false)
        fechaDeRecepcion(blank: false)
        pesoBruto(blank: false, min: 0.0)
        fechaDeAnticipo(blank: false)
        importe(blank: false, min: 0.0)
        importeLiteral(blank: false)
        anticipoPagado blank: true, nullable: true
        observaciones(blank: true, nullable: true)
        usuario(display: false, nullable: true)
    }

    def beforeInsert = {
        def c = AnticipoContraEntrega.createCriteria()
        def results = c {
            projections {
                max('numeroAnticipo')
            }}
        def maxNumeroAnticipo = results.get(0)?: 0
        this.numeroAnticipo = maxNumeroAnticipo + 1

        this.anticipoPagado = "NO"

        this.usuario = springSecurityService.getCurrentUser()
    }

    def beforeUpdate = {
        this.usuario = springSecurityService.getCurrentUser()
    }
}
