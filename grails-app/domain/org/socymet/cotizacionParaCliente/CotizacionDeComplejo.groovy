package org.socymet.cotizacionParaCliente

import org.socymet.cotizaciones.CotizacionDiariaDeMinerales
import org.socymet.cotizaciones.TablaOrigenCotizacionesComplejo
import org.socymet.cotizaciones.TerminosDeContrato
import org.socymet.seguridad.SecUser

class CotizacionDeComplejo {
    Integer numeroCotizacionComplejo = 0 //e.g.: GENERADO: 1, PARA MOSTRAR: 0001
    
    String nombreSolicitante
    String empresaSolicitante
    Date fechaDeCotizacion

    CotizacionDiariaDeMinerales cotizacionDiaria

    BigDecimal leyZinc
    BigDecimal leyPlomo
    BigDecimal leyPlata

    String modoValoracion
    TablaOrigenCotizacionesComplejo tablaComplejo
    TerminosDeContrato terminosDeContrato
    
    BigDecimal valorTonelada
    BigDecimal pesoBruto = 1000
    BigDecimal valorEstimado

    SecUser usuario

    transient springSecurityService

    static constraints = {
        numeroCotizacionComplejo nullable: false
        nombreSolicitante blank: false
        empresaSolicitante blank: false
        fechaDeCotizacion nullable: false

        cotizacionDiaria nullable: false
        
        leyZinc min: 0.0, nullable: false
        leyPlomo min: 0.0, nullable: false
        leyPlata min: 0.0, nullable: false
        modoValoracion inList: ["TABLA","TERMINOS DE CONTRATO"]
        tablaComplejo nullable: false
        terminosDeContrato nullable: false
        
        valorTonelada min: 0.0, nullable: false
        pesoBruto min: 0.0, nullable: false
        valorEstimado min: 0.0, nullable: false

        usuario nullable: false
    }

    def beforeInsert = {
        def c = CotizacionDeComplejo.createCriteria()
        def results = c {
            projections {
                max('numeroCotizacionComplejo')
            }}
        def maxNumeroCotizacion = results.get(0)?: 0
        this.numeroCotizacionComplejo = maxNumeroCotizacion + 1
        
        this.usuario = springSecurityService.getCurrentUser()
    }
}
