package org.socymet.cancelacion

import org.grails.web.json.JSONArray
import org.socymet.liquidacion.RetencionPagada
import org.socymet.liquidacion.RetencionPorPagarComplejo
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.seguridad.SecUser

class PagoDeRetenciones {
    static searchable = true

    Deposito deposito

    Integer numeroComprobante
    String ci
    String nombreCobrador
    String beneficiario

    Date fechaDePago
    //parametros de busqueda
    Date periodo
    String quincena
    Empresa empresa

    String retenciones
    String retencionesSeleccionadas
    String descripcion="POR RETENCIONES: "
    String lotes

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
        beneficiario blank: false
        fechaDePago nullable: false
        //parametros de busqueda
        periodo nullable: false
        quincena inList: ["1ra. QUINCENA","2da. QUINCENA"],blank: false
        empresa nullable: true

        retenciones blank: false, nullable: false
        retencionesSeleccionadas blank: false, nullable: false
        descripcion blank: false, nullable: false
        lotes blank: false, nullable: false

        totalPagable nullable: false, min: 0.0
        totalPagableLiteral blank: false

        observaciones blank: true

        usuario display: false, nullable: true
    }

    static mapping = {
        retenciones type: 'text'
        retencionesSeleccionadas type: 'text'
        descripcion type: 'text'
        lotes type: 'text'
    }

    def beforeInsert = {
        def c = PagoDeRetenciones.createCriteria()
        def results = c {
            projections {
                max('numeroComprobante')
            }}
        def maxNumeroComprobante = results.get(0)?: 0
        this.numeroComprobante = maxNumeroComprobante + 1
        this.usuario = springSecurityService.getCurrentUser() as SecUser
        this.deposito = usuario.deposito
    }

    def afterInsert = {
        def retencionesSeleccionadasJSON = new JSONArray(retencionesSeleccionadas)
        def lotesJSON = new JSONArray(lotes)
        def retencion
        def total
        
        def codigo
        def cantidadDescuento
        def unidadDeDescuento
        def tipoDeRetencion
        def descripcion
        def asignacionDelDescuento
        def monto
        def lote
        def kilosNetosSecos
        def valorOficialNeto
        def recepcionDeComplejo
        def tipoDeMineral
        def empresa
        def fechaDeRegistro

        PagoDeRetenciones.withNewTransaction {
            retencionesSeleccionadasJSON.each {
                retencion = it.getAt("retencion")
                total = it.getAt("total").toString().toBigDecimal()

                log.error("**** RETENCION PAGADA: ${retencion} - ${total}")
                def totalRetencionPagada = new TotalRetencionPagada(
                    pagoDeRetenciones: this,
                    retencion: retencion,
                    total: total
                )
                totalRetencionPagada.save(failOnError: true)
            }

            lotesJSON.each {
                def lotesDeRetencion=new JSONArray(it)
                lotesDeRetencion.each { lot ->
                    codigo = lot.getAt("codigo").toString().toInteger()
                    cantidadDescuento = lot.getAt("cantidadDescuento").toString().toBigDecimal()
                    unidadDeDescuento = lot.getAt("unidadDeDescuento")
                    tipoDeRetencion = lot.getAt("tipoDeRetencion")
                    descripcion = lot.getAt("descripcion")
                    asignacionDelDescuento = lot.getAt("asignacionDelDescuento")
                    monto = lot.getAt("monto").toString().toBigDecimal()
                    lote = lot.getAt("lote")
                    kilosNetosSecos = lot.getAt("kilosNetosSecos").toString().toBigDecimal()
                    valorOficialNeto = lot.getAt("valorOficialNeto").toString().toBigDecimal()
                    recepcionDeComplejo = RecepcionDeComplejo.get(lot.getAt("recepcionDeComplejo").getAt("id").toString().toLong())
                    tipoDeMineral = lot.getAt("tipoDeMineral")
                    empresa = Empresa.get(lot.getAt("empresa").getAt("id").toString().toLong())
                    fechaDeRegistro = new Date()

                    log.error("**** RETENCION PAGADA: ${lote} - ${monto}")

                    def retencionPagada = new RetencionPagada(
                            pagoDeRetenciones: this,
                            codigo: codigo,
                            cantidadDescuento: cantidadDescuento,
                            unidadDeDescuento: unidadDeDescuento,
                            tipoDeRetencion: tipoDeRetencion,
                            descripcion: descripcion,
                            asignacionDelDescuento: asignacionDelDescuento,
                            monto: monto,
                            lote: lote,
                            kilosNetosSecos: kilosNetosSecos,
                            valorOficialNeto: valorOficialNeto,
                            recepcionDeComplejo: recepcionDeComplejo,
                            tipoDeMineral: tipoDeMineral,
                            empresa: empresa,
                            fechaDeRegistro: fechaDeRegistro
                    )
                    retencionPagada.save(failOnError: true)
                }
            }
        }
        //cambiando el estado de las retenciones por pagar
        lotesJSON.each {
            def lotesDeRetencion=new JSONArray(it)
            lotesDeRetencion.each { lot ->
                recepcionDeComplejo = RecepcionDeComplejo.get(lot.getAt("recepcionDeComplejo").getAt("id").toString().toLong())
                def retencionPorPagar = RetencionPorPagarComplejo.get(lot.getAt("id").toString().toLong())
                retencionPorPagar.pagado = "SI"
                retencionPorPagar.save(failOnError: true)
            }
        }
    }

    def afterUpdate = {
    }

    String toString(){
        "Cobrador: ${nombreCobrador} [${ci}] Beneficiario: $beneficiario Fecha de Pago: ${new java.text.SimpleDateFormat('dd/MM/yyyy').format(fechaDePago)} Descripcion: ${descripcion}"
    }
}
