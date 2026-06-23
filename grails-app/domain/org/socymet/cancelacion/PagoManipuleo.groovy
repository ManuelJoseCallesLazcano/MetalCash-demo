package org.socymet.cancelacion

import org.grails.web.json.JSONArray
import org.socymet.proveedor.Deposito
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.seguridad.SecUser

class PagoManipuleo {
    Integer numeroComprobante
    String ci
    String nombreCobrador

    Date fechaDePago

    Deposito deposito

    String lotes

    String descripcion="POR LOTES: "

    BigDecimal totalPagable=0
    String totalPagableLiteral

    String observaciones

    SecUser usuario

    transient springSecurityService

    static constraints = {
        numeroComprobante nullable: true
        ci blank: false
        nombreCobrador blank: false

        fechaDePago nullable: false

        deposito nullable: false

        lotes blank: false, nullable: false

        descripcion blank: false, nullable: false

        totalPagable nullable: false, min: 0.0
        totalPagableLiteral blank: false
        observaciones blank: true

        usuario display: false, nullable: true
    }

    static mapping = {
        lotes type: 'text'
        descripcion type: 'text'
    }

    def beforeInsert = {
        def c = PagoManipuleo.createCriteria()
        def results = c {
            projections {
                max('numeroComprobante')
            }}
        def maxNumeroComprobante = results.get(0)?: 0
        this.numeroComprobante = maxNumeroComprobante + 1
        this.usuario = springSecurityService.getCurrentUser()
    }

    def afterInsert = {
        def lotesJSON = new JSONArray(lotes)

        PagoManipuleo.withNewTransaction {
            lotesJSON.each {
                def lote = it.getAt("lote")
                def recepcionId = it.getAt("recepcionId").toString().toLong()
                def fechaDeRecepcion = new Date().parse("dd/MM/yyyy",it.getAt("fechaDeRecepcion").toString())
                def pesoBruto = it.getAt("pesoBruto").toString().toBigDecimal()
                def tipoDeMaterial = it.getAt("tipoDeMaterial")
                def pesadaVaciada = it.getAt("pesadaVaciada")
                def carguioMaquina = it.getAt("carguioMaquina")
                def embolsadaArrumada = it.getAt("embolsadaArrumada")
                def soloComuneada = it.getAt("soloComuneada")
                def soloVaciada = it.getAt("soloVaciada")
                def soloPesada = it.getAt("soloPesada")
                def soloEmbolsada = it.getAt("soloEmbolsada")
                def costoDeManipuleo = it.getAt("costoManipuleo").toString().toBigDecimal()

                def pagoTransporteDetalle = new DetallePagoManipuleo(
                        pagoManipuleo: this,
                        lote: lote,
                        recepcionId: recepcionId,
                        fechaDeRecepcion: fechaDeRecepcion,
                        pesoBruto: pesoBruto,
                        pesadaVaciada: pesadaVaciada,
                        carguioMaquina: carguioMaquina,
                        embolsadaArrumada: embolsadaArrumada,
                        soloComuneada: soloComuneada,
                        soloVaciada: soloVaciada,
                        soloPesada: soloPesada,
                        soloEmbolsada: soloEmbolsada,
                        tipoDeMaterial: tipoDeMaterial,
                        costoDeManipuleo: costoDeManipuleo
                )
                pagoTransporteDetalle.save(failOnError: true)
            }
        }

        lotesJSON.each {
            def recepcionId = it.getAt("recepcionId").toString().toLong()
            def costoDeManipuleo = it.getAt("costoManipuleo").toString().toBigDecimal()
            def recepcion = RecepcionDeComplejo.get(recepcionId)
            recepcion.manipuleoPagado="SI"
            recepcion.costoManipuleo=costoDeManipuleo
            recepcion.save()
        }
    }

    def afterUpdate = {

        def lotesJSON = new JSONArray(lotes)

        PagoManipuleo.withNewTransaction {
            def pagoManipuleo = PagoManipuleo.get(id)
            log.error("*********** executing afterUpdate ************")
            log.error("*********** eliminando detalles anteriores ************")
            def detallePagoManipuleos = DetallePagoManipuleo.findAllByPagoManipuleo(pagoManipuleo)
            detallePagoManipuleos.each { r ->
                r.delete(flush: true)
            }

            lotesJSON.each {
                def lote = it.getAt("lote")
                def recepcionId = it.getAt("recepcionId").toString().toLong()
                def fechaDeRecepcion = new Date().parse("dd/MM/yyyy",it.getAt("fechaDeRecepcion").toString())
                def pesoBruto = it.getAt("pesoBruto").toString().toBigDecimal()
                def tipoDeMaterial = it.getAt("tipoDeMaterial")
                def pesadaVaciada = it.getAt("pesadaVaciada")
                def carguioMaquina = it.getAt("carguioMaquina")
                def embolsadaArrumada = it.getAt("embolsadaArrumada")
                def soloComuneada = it.getAt("soloComuneada")
                def soloVaciada = it.getAt("soloVaciada")
                def soloPesada = it.getAt("soloPesada")
                def soloEmbolsada = it.getAt("soloEmbolsada")
                def costoDeManipuleo = it.getAt("costoManipuleo").toString().toBigDecimal()

                def pagoTransporteDetalle = new DetallePagoManipuleo(
                        pagoManipuleo: this,
                        lote: lote,
                        recepcionId: recepcionId,
                        fechaDeRecepcion: fechaDeRecepcion,
                        pesoBruto: pesoBruto,
                        pesadaVaciada: pesadaVaciada,
                        carguioMaquina: carguioMaquina,
                        embolsadaArrumada: embolsadaArrumada,
                        soloComuneada: soloComuneada,
                        soloVaciada: soloVaciada,
                        soloPesada: soloPesada,
                        soloEmbolsada: soloEmbolsada,
                        tipoDeMaterial: tipoDeMaterial,
                        costoDeManipuleo: costoDeManipuleo
                )
                pagoTransporteDetalle.save(failOnError: true)
            }
        }

        lotesJSON.each {
            def recepcionId = it.getAt("recepcionId").toString().toLong()
            def costoDeManipuleo = it.getAt("costoManipuleo").toString().toBigDecimal()
            def recepcion = RecepcionDeComplejo.get(recepcionId)
            recepcion.manipuleoPagado="SI"
            recepcion.costoManipuleo=costoDeManipuleo
            recepcion.save()
        }
    }

    def beforeDelete = {
        def lotesJSON = new JSONArray(lotes)

        PagoManipuleo.withNewTransaction {
            def pagoManipuleo = PagoManipuleo.get(id)
            def detallePagoManipuleos = DetallePagoManipuleo.findAllByPagoManipuleo(pagoManipuleo)
            detallePagoManipuleos.each { r ->
                r.delete(flush: true)
            }
        }

        lotesJSON.each {
            def recepcionId = it.getAt("recepcionId").toString().toLong()
            def recepcion = RecepcionDeComplejo.get(recepcionId)
            recepcion.manipuleoPagado="NO"
            recepcion.costoManipuleo=0
            recepcion.save()
        }
    }

    String toString(){
        "Cobrador: ${nombreCobrador} [${ci}] Fecha de Pago: ${new java.text.SimpleDateFormat('dd/MM/yyyy').format(fechaDePago)} Lotes: ${descripcion}"
    }
}
