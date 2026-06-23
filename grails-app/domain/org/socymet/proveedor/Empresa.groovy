package org.socymet.proveedor

import org.grails.web.json.JSONArray
import org.socymet.proveedor.bonos.BonoCalidad
import org.socymet.proveedor.bonos.BonoCantidad
import org.socymet.proveedor.bonos.BonoIncentivo
import org.socymet.seguridad.SecUser

class Empresa {
    static auditable = true

    static searchable = true

    Deposito deposito

    String tipoDeEmpresa
    String nombreDeEmpresa
    String codigoEmpresa
    String departamento
    String provincia
    String municipio
    String codigoMunicipio
    String canton = 0
    String concesion = 0
    String nit = 0
    String nim = 0
    String numeroCuentaCNS = 0
    String numeroCuentaComibol = 0
    //costos por flete
    BigDecimal costoTransporteComplejos
    String unidadMonetariaComplejos
    String unidadDeCobroComplejos

    BigDecimal costoTransporteConcentrados
    String unidadMonetariaConcentrados
    String unidadDeCobroConcentrados

    BigDecimal costoTransporteCobre
    String unidadMonetariaCobre
    String unidadDeCobroCobre

    BigDecimal costoTratamiento = 0 // toll
    //descuentos de ley
    String retenciones
    //bono transporte
    String aplicarBonoTransporte
    BigDecimal bonoTransporteKilosNetosSecos = 0
    //cuadrillas de trabajadores
    String cuadrillas
    //bonos
    //Complejo
    String bonoCantidadComplejo
    String bonoCantidadConcentrado
    String bonoCantidadCobre

    BigDecimal bonoProduccionPorTonelada=0
    BigDecimal bonoCalidadPorTonelada=0

    BigDecimal bonoMinimoTransportePorTonelada=0
    BigDecimal bonoMaximoTransportePorTonelada=0

    BigDecimal costoTransporteOroTonelada=0
    BigDecimal bonoLiquidacionOro=0

    transient springSecurityService

    // ── Opciones de selección (fuente única para constraints y formularios) ──
    static final List TIPOS_DE_EMPRESA   = ["COOP. MIN.","COOP.","EMPRESA","COMERCIALIZADORA","PARTICULAR"]
    static final List DEPARTAMENTOS      = ["ORURO","LA PAZ","POTOSI","COCHABAMBA","CHUQUISACA","TARIJA","PANDO","BENI","SANTA CRUZ"]
    static final List UNIDADES_MONETARIAS = ["Bs",'$us']
    static final List UNIDADES_DE_COBRO   = ["TONELADA","SACO"]

    static constraints = {
        deposito nullable: false
        tipoDeEmpresa(inList: TIPOS_DE_EMPRESA, blank: false)
        nombreDeEmpresa(blank: false)
        codigoEmpresa()
        departamento(inList: DEPARTAMENTOS, blank: false)
        provincia(blank: false)
        municipio(blank: false)
        codigoMunicipio(blank: false)
        canton(blank: false)
        concesion(blank: false)
        nit()
        nim()
        numeroCuentaCNS blank: true
        numeroCuentaComibol blank: true
        //costos por flete
        costoTransporteComplejos(min: 0.0, nullable: true)
        unidadMonetariaComplejos(inList: UNIDADES_MONETARIAS, nullable: true)
        unidadDeCobroComplejos(inList: UNIDADES_DE_COBRO, nullable: true)
//        unidadDeCobroComplejos(inList: ["TONELADA"], nullable: true)

        costoTransporteConcentrados(min: 0.0, nullable: true)
        unidadMonetariaConcentrados(inList: UNIDADES_MONETARIAS, nullable: true)
        unidadDeCobroConcentrados(inList: UNIDADES_DE_COBRO, nullable: true)
//        unidadDeCobroConcentrados(inList: ["TONELADA"], nullable: true)

        costoTransporteCobre(min: 0.0, nullable: true)
        unidadMonetariaCobre(inList: UNIDADES_MONETARIAS, nullable: true)
        unidadDeCobroCobre(inList: ["TONELADA","SACO","Carrera"], nullable: true)
        //descuentos de ley
        retenciones blank: false, nullable: false

        aplicarBonoTransporte(inList: ["SI","NO"])
        bonoTransporteKilosNetosSecos()
        //descuentos de ley
        cuadrillas blank: true, nullable: true
        //bonos
        //Complejo
        bonoCantidadComplejo blank: true, nullable: true
        bonoCantidadConcentrado blank: true, nullable: true
        bonoCantidadCobre blank: true, nullable: true

        bonoProduccionPorTonelada(min: 0.0, nullable: false)
        bonoCalidadPorTonelada(min: 0.0, nullable: false)

        bonoMinimoTransportePorTonelada(min: 0.0, nullable: false)

        costoTransporteOroTonelada(min: 0.0, nullable: false)
        bonoLiquidacionOro(min: 0.0, nullable: false)
    }

    static mapping = {
        retenciones type: 'text'
        cuadrillas type: 'text'
        bonoCantidadComplejo type: 'text'
        bonoCantidadConcentrado type: 'text'
        bonoCantidadCobre type: 'text'
    }

    def beforeInsert = {
        costoTransporteComplejos = (!costoTransporteComplejos)? 0: costoTransporteComplejos
        costoTransporteConcentrados = (!costoTransporteConcentrados)? 0: costoTransporteConcentrados
        costoTransporteCobre = (!costoTransporteCobre)? 0: costoTransporteCobre

        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
//        System.out.println("*********** Empresa: DEPOSITO antes de deteccion:${deposito.toString()}")
//        this.deposito = usuarioActual.deposito
//        System.out.println("*********** Empresa: DEPOSITO despues de deteccion:${deposito.toString()}")

//        this.municipio = Municipio.get(this.municipio).municipio
    }

    def beforeUpdate = {
//        this.municipio = Municipio.get(this.municipio).municipio
    }

    // El registro de entidades relacionadas (retenciones, bonos de cantidad y cuadrillas)
    // se realiza ahora en EmpresaController.save()/update() mediante registrarRelaciones()
    // y eliminarRelaciones(). Se movió fuera de afterInsert/afterUpdate porque, en GORM 7,
    // el withNewTransaction no veía la Empresa aún no commiteada y Empresa.get(id) devolvía null.

    String toString(){
        "${tipoDeEmpresa} ${nombreDeEmpresa}"
    }

    def registrarBonoCantidad = { empresa, elemento, simboloElemento, jsonData ->
        def cantidadData = new JSONArray(jsonData)
        cantidadData.each {
            def cantidadMinima = it.getAt("CANTIDAD MINIMA")
            def cantidadMaxima = it.getAt("CANTIDAD MAXIMA")
            def bono = it.getAt("BONO")
            log.error("**** BONO CANTIDAD ${elemento}: ${cantidadMinima} - ${cantidadMaxima} - ${bono}")
            def bonoCantidad = new BonoCantidad( //[{"CANTIDAD MINIMA":"10","CANTIDAD MAXIMA":"20","BONO":"30"},{"CANTIDAD MINIMA":"40","CANTIDAD MAXIMA":"50","BONO":"60"}]
                    empresa: empresa,
                    elemento: elemento,
                    simboloElemento: simboloElemento,
                    cantidadMinima: cantidadMinima,
                    cantidadMaxima: cantidadMaxima,
                    bono: bono)
            bonoCantidad.save(failOnError: true)
        }
    }
}
