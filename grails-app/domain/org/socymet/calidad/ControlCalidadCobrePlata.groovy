package org.socymet.calidad

import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.seguridad.SecUser

class ControlCalidadCobrePlata {
    static searchable = true

    RecepcionDeComplejo recepcionDeComplejo
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

    BigDecimal porcentajeCobrePromexbol
    BigDecimal porcentajePlataPromexbol
    BigDecimal porcentajeHumedadPromexbol
    BigDecimal porcentajeMermaPromexbol=1

    BigDecimal porcentajeCobreCliente
    BigDecimal porcentajePlataCliente
    BigDecimal porcentajeHumedadCliente
    BigDecimal porcentajeMermaCliente=1

    BigDecimal porcentajeCobreFinal
    BigDecimal porcentajePlataFinal
    BigDecimal porcentajeHumedadFinal
    BigDecimal porcentajeMermaFinal=1

    String observaciones
    SecUser usuario

    transient springSecurityService

    /*
    * ADICIONAR HUMEDAD Y MERMA A LAS VISTAS. TALVEZ HAYA QUE ACTUALIZAR LA TABLA EN LA BASE DE DATOS
    * O ELIMINAR LA TABLA.
    * TAMBIEN ACTUALIZAR EL ARCHIVO JS PARA IMPLEMENTAR EL CALCULO DEL PROMEDIO PARA HUMEDAD Y MERMA
    * */

    static constraints = {
        recepcionDeComplejo(unique: true)
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

        porcentajeCobrePromexbol nullable: false, min: 0.0, max: 100.0
        porcentajePlataPromexbol nullable: false, min: 0.0, max: 10000.0
        porcentajeHumedadPromexbol nullable: false, min: 0.0, max: 100.0
        porcentajeMermaPromexbol nullable: false, min: 0.0, max: 100.0

        porcentajeCobreCliente nullable: false, min: 0.0, max: 100.0
        porcentajePlataCliente nullable: false, min: 0.0, max: 10000.0
        porcentajeHumedadCliente nullable: false, min: 0.0, max: 100.0
        porcentajeMermaCliente nullable: false, min: 0.0, max: 100.0

        porcentajeCobreFinal nullable: false, min: 0.0, max: 100.0
        porcentajePlataFinal nullable: false, min: 0.0, max: 10000.0
        porcentajeHumedadFinal nullable: false, min: 0.0, max: 100.0
        porcentajeMermaFinal nullable: false, min: 0.0, max: 100.0

        observaciones blank: true
        usuario display: false, nullable: true
    }

    def beforeInsert = {
        usuario = springSecurityService.getCurrentUser()
    }

    String toString(){
        "${recepcionDeComplejo.toString()}: Cu: ${porcentajeCobrePromexbol}; Ag: ${porcentajePlataPromexbol}"
    }
}
