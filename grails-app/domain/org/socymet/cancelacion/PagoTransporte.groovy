package org.socymet.cancelacion

import org.grails.web.json.JSONArray
import org.socymet.proveedor.Automovil
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.seguridad.SecUser

class PagoTransporte {
    static auditable = true

    static searchable = true

    Deposito deposito

    Integer numeroComprobante
    String ci
    String nombreCobrador

    Date fechaDePago

    String solicitante
    Empresa empresa
    Automovil automovil

    String lotes

    Integer recepcionId
    BigDecimal pesoBruto=0
    BigDecimal precioTonelada=0
    String descripcion="POR EL LOTE: "
    BigDecimal total=0
    BigDecimal totalAnticipos=0
    BigDecimal totalPagable=0
    String totalPagableLiteral

    String observaciones

    SecUser usuario

    transient springSecurityService

    static constraints = {
        deposito nullable: false
        numeroComprobante nullable: true
        ci blank: false
        nombreCobrador blank: false

        fechaDePago nullable: false

        solicitante inList: ["Empresa","Particular"], nullable: false
        empresa nullable: true
        automovil nullable: true

        lotes blank: false, nullable: false

        descripcion blank: false, nullable: false

        total nullable: false, min: 0.0
        totalAnticipos nullable: false, min: 0.0
        totalPagable nullable: false, min: 0.0
        totalPagableLiteral blank: false
        observaciones blank: true, nullable: true

        usuario display: false, nullable: true
    }

    static mapping = {
        lotes type: 'text'
        descripcion type: 'text'
    }

    def beforeInsert = {
        def c = PagoTransporte.createCriteria()
        def results = c {
            projections {
                max('numeroComprobante')
            }}
        def maxNumeroComprobante = results.get(0)?: 0
        this.numeroComprobante = maxNumeroComprobante + 1
        this.fechaDePago = new java.util.Date()
        this.usuario = springSecurityService.getCurrentUser() as SecUser
        this.deposito = usuario.deposito
    }

    def afterInsert = {
        def lotesJSON = new JSONArray(lotes)

        PagoTransporte.withNewTransaction {
            lotesJSON.each {
                def lote = it.getAt("lote")
                def recepcionId = it.getAt("recepcionId").toString().toLong()
                def nombreChofer = it.getAt("nombreChofer")
                def placaAutomovil = it.getAt("placaAutomovil")
                def fechaDeRecepcion = it.getAt("fechaDeRecepcion")
                def pesoBruto = it.getAt("pesoBruto").toString().toBigDecimal()
                def tipoDeMaterial = it.getAt("tipoDeMaterial")
                def costoDeTransporte = it.getAt("costoDeTransporte").toString().toBigDecimal()

                log.error("**** LOTE: ${lote} - ${recepcionId} - ${nombreChofer} - ${placaAutomovil} - ${fechaDeRecepcion} - ${pesoBruto} - ${costoDeTransporte}")
                def pagoTransporteDetalle = new DetallePagoTransporte(
                        pagoTransporte: this,
                        lote: lote,
                        recepcionId: recepcionId,
                        nombreChofer: nombreChofer,
                        placaAutomovil: placaAutomovil,
                        fechaDeRecepcion: fechaDeRecepcion,
                        pesoBruto: pesoBruto,
                        tipoDeMaterial: tipoDeMaterial,
                        costoDeTransporte: costoDeTransporte
                )
                pagoTransporteDetalle.save(failOnError: true)
            }
        }

        PagoTransporte.withNewTransaction {
            def recepcion = RecepcionDeComplejo.get(this.recepcionId)
            recepcion.transportePagado="SI"
            recepcion.save(failOnError: true, flush: true)
        }

        PagoTransporte.withNewTransaction {
            def recepcion = RecepcionDeComplejo.get(this.recepcionId)
            def estadoCuentaTransporte = new EstadoCuentaTransporte(
                    recepcionDeComplejo: recepcion,
                    solicitante: solicitante,
                    empresa: empresa,
                    automovil: automovil,
                    ci: ci,
                    nombreResponsable: nombreCobrador,
                    fecha: fechaDePago,
                    descripcion: "REGISTRO AUTOMATICO: PAGO DE ANTICIPO CONTRA TRANSPORTE",
                    ingreso: totalAnticipos,
                    egreso: 0,
                    saldo: 0 //ultimo saldo + ingreso - egreso
            )
            estadoCuentaTransporte.save(failOnError: true)
        }
    }

    def afterUpdate = {
//        def anticipoDetalleAnteriores = AnticipoDetalle.findAllByAnticipo(this)
//        anticipoDetalleAnteriores.each {
//            log.error("**** ELIMINANDO: ${it.lote} PESO BRUTO: ${it.pesoBruto}")
//            it.delete()
//        }
//
//        Anticipo.withNewTransaction {
//            //actualizando el estado del lote
//            //this.anticipoPagado = "SI" //este campo no ha sido declarado
//            def retencionesJSON = new JSONArray(lotes)
//            retencionesJSON.each {
//                def recepcion = RecepcionDeComplejo.get(it.getAt("recepcionId").toString().toInteger())
//                recepcion.estadoAnticipo="CON ANTICIPO"
//                recepcion.save(failOnError: true)
//
//                def lote = it.getAt("lote")
//                def recepcionId = it.getAt("recepcionId")
//                def nombreChofer = it.getAt("nombreChofer")
//                def nombreEmpresa = it.getAt("nombreEmpresa")
//                def fechaDeRecepcion = it.getAt("fechaDeRecepcion")
//                def pesoBruto = it.getAt("pesoBruto")
//
//                log.error("**** LOTE: ${lote} - ${recepcionId} - ${nombreChofer} - ${nombreEmpresa} - ${fechaDeRecepcion} - ${pesoBruto}")
//                def anticipoDetalle = new AnticipoDetalle(
//                        anticipo: this,
//                        lote: lote,
//                        recepcionId: recepcionId,
//                        nombreChofer: nombreChofer,
//                        nombreEmpresa: nombreEmpresa,
//                        fechaDeRecepcion: fechaDeRecepcion,
//                        pesoBruto: pesoBruto
//                )
//                anticipoDetalle.save(failOnError: true)
//            }
//        }
    }

//    String toString(){
//        "Cobrador: ${nombreCobrador} [${ci}] Fecha de Pago: ${fechaDePago.format('dd/MM/yyyy')} Solicitante: ${(solicitante.equals("Empresa"))?empresa.toString():automovil.toString()} Lotes: ${descripcion}"
//    }

    String toString(){
        numeroComprobante.toString()
    }
}
