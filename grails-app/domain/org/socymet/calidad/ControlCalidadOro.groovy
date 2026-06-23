package org.socymet.calidad

import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeOro
import org.socymet.seguridad.SecUser

class ControlCalidadOro {
    Deposito deposito
    RecepcionDeOro recepcionDeOro
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

    BigDecimal porcentajeOroPromexbol
    BigDecimal porcentajeHumedadPromexbol
    BigDecimal porcentajeMermaPromexbol=0

    String observaciones="-"
    SecUser usuario

    transient springSecurityService

    static constraints = {
        deposito nullable: false
        recepcionDeOro(unique: true)
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

        porcentajeOroPromexbol nullable: false, min: 0.0, max: 100.0
        porcentajeHumedadPromexbol nullable: false, min: 0.0, max: 100.0
        porcentajeMermaPromexbol nullable: false, min: 0.0, max: 100.0

        observaciones blank: true, nullable: true
        usuario display: false, nullable: true
    }

    def beforeInsert = {
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        System.out.println("*********** Empresa: DEPOSITO antes de deteccion:${deposito.toString()}")
        this.deposito = usuarioActual.deposito
        System.out.println("*********** Empresa: DEPOSITO despues de deteccion:${deposito.toString()}")
        usuario = springSecurityService.getCurrentUser()
    }

    String toString(){
        "${recepcionDeOro.toString()}:Lamas=${porcentajeOroPromexbol} gr/TM"
    }
}
