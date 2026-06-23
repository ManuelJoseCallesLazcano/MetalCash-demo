package org.socymet.calidad

import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeEstano
import org.socymet.seguridad.SecUser

class ControlCalidadEstano {
    static searchable = true

    RecepcionDeEstano recepcionDeEstano
    Empresa empresa
    String lote
    String nombreCliente
    String nombreEmpresa
    String fechaDeRecepcion
    BigDecimal cantidadDeSacos
    BigDecimal pesoBruto
    String estadoDelLote

    String nombreLaboratorio
    String numeroAnalisis
    Date fechaAnalisis

    BigDecimal porcentajeEstanoPromexbol
    BigDecimal porcentajeHumedadPromexbol
    BigDecimal porcentajeMermaPromexbol=0

    String observaciones
    SecUser usuario

    transient springSecurityService

    static constraints = {
        recepcionDeEstano(unique: true)
        empresa nullable: false
        lote(blank: false)
        nombreCliente(blank: false)
        nombreEmpresa(blank: false)
        fechaDeRecepcion(blank: false)
        cantidadDeSacos nullable: false
        pesoBruto nullable: false
        estadoDelLote blank: false

        nombreLaboratorio blank: false
        numeroAnalisis blank: false
        fechaAnalisis nullable: false

        porcentajeEstanoPromexbol nullable: false, min: 0.0, max: 100.0
        porcentajeHumedadPromexbol nullable: false, min: 0.0, max: 100.0
        porcentajeMermaPromexbol nullable: false, min: 0.0, max: 100.0

        observaciones blank: true, nullable: true
        usuario display: false, nullable: true
    }

    def beforeInsert = {
        usuario = springSecurityService.getCurrentUser()
    }

    String toString(){
        "${recepcionDeEstano.toString()}: Sn: ${porcentajeEstanoPromexbol}}"
    }
}
