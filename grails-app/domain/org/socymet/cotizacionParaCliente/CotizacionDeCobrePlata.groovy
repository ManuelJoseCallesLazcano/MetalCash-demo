package org.socymet.cotizacionParaCliente

import org.socymet.cotizaciones.CotizacionDiariaDeMinerales
import org.socymet.cotizaciones.TablaPreciosCobre
import org.socymet.cotizaciones.TerminosDeContrato
import org.socymet.seguridad.SecUser

class CotizacionDeCobrePlata {
    Integer numeroCotizacionCobrePlata = 0 //e.g.: GENERADO: 1, PARA MOSTRAR: 0001

    String nombreSolicitante
    String empresaSolicitante
    Date fechaDeCotizacion

    CotizacionDiariaDeMinerales cotizacionDiaria

    BigDecimal leyCobre
    BigDecimal leyPlata

    String modoValoracion
    TablaPreciosCobre tablaCobrePlata
    TerminosDeContrato terminosDeContrato

    BigDecimal valorTonelada
    BigDecimal pesoBruto = 1000
    BigDecimal valorEstimado

    SecUser usuario

    transient springSecurityService

    static constraints = {
        numeroCotizacionCobrePlata nullable: false
        nombreSolicitante blank: false
        empresaSolicitante blank: false
        fechaDeCotizacion nullable: false

        cotizacionDiaria nullable: false

        leyCobre min: 0.0, nullable: false
        leyPlata min: 0.0, nullable: false
        modoValoracion inList: ["TABLA","TERMINOS DE CONTRATO"]
        tablaCobrePlata nullable: false
        terminosDeContrato nullable: false

        valorTonelada min: 0.0, nullable: false
        pesoBruto min: 0.0, nullable: false
        valorEstimado min: 0.0, nullable: false

        usuario nullable: false
    }

    def beforeInsert = {
        def c = CotizacionDeCobrePlata.createCriteria()
        def results = c {
            projections {
                max('numeroCotizacionCobrePlata')
            }}
        def maxNumeroCotizacion = results.get(0)?: 0
        this.numeroCotizacionCobrePlata = maxNumeroCotizacion + 1

        this.usuario = springSecurityService.getCurrentUser()
    }
}
