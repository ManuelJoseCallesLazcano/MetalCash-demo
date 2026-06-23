package org.socymet.calidad

import org.socymet.cotizaciones.TablaOrigenCotizacionesComplejo
import org.socymet.cotizaciones.TerminosDeContrato
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.seguridad.SecUser

class ControlCalidadComplejo {
    static auditable = true

    static searchable = true

    Deposito deposito
    RecepcionDeComplejo recepcionDeComplejo
    Empresa empresa
    String lote="-"
    String nombreCliente
    String nombreEmpresa
    String fechaDeRecepcion
    String condicionDeEntrega
    BigDecimal cantidadDeSacos
    BigDecimal pesoBruto
    String estadoDelLote

    String nombreLaboratorio
    String numeroAnalisis
    Date fechaAnalisis

    BigDecimal porcentajeZincPromexbol
    BigDecimal porcentajePlomoPromexbol
    BigDecimal porcentajePlataPromexbol
    BigDecimal porcentajeHumedadPromexbol
    BigDecimal porcentajeMermaPromexbol=1

    BigDecimal porcentajeZincCliente=0
    BigDecimal porcentajePlomoCliente=0
    BigDecimal porcentajePlataCliente=0
    BigDecimal porcentajeHumedadCliente=0
    BigDecimal porcentajeMermaCliente=0

    BigDecimal porcentajeZincFinal=0
    BigDecimal porcentajePlomoFinal=0
    BigDecimal porcentajePlataFinal=0
    BigDecimal porcentajeHumedadFinal=0
    BigDecimal porcentajeMermaFinal=0

    String modoValoracion
    TablaOrigenCotizacionesComplejo tablaComplejo
    TerminosDeContrato terminosDeContrato

    String tablasIds
    String terminosIds
    //penalidades
    BigDecimal porcentajeArsenico=0
    BigDecimal porcentajeAntimonio=0
    BigDecimal porcentajeSilice=0
    BigDecimal porcentajeBismuto=0
    BigDecimal porcentajeEstano=0
    BigDecimal porcentajeZinc=0

    String observaciones="-"
    SecUser usuario

    transient springSecurityService

    /*
    * ADICIONAR HUMEDAD Y MERMA A LAS VISTAS. TALVEZ HAYA QUE ACTUALIZAR LA TABLA EN LA BASE DE DATOS
    * O ELIMINAR LA TABLA.
    * TAMBIEN ACTUALIZAR EL ARCHIVO JS PARA IMPLEMENTAR EL CALCULO DEL PROMEDIO PARA HUMEDAD Y MERMA
    * */

    static constraints = {
        deposito nullable: false
        recepcionDeComplejo(unique: true)
        empresa nullable: false
        lote(blank: false)
        nombreCliente(blank: false)
        nombreEmpresa(blank: false)
        fechaDeRecepcion(blank: false)
        condicionDeEntrega blank: false
        cantidadDeSacos nullable: false
        pesoBruto nullable: false
        estadoDelLote blank: false

        nombreLaboratorio blank: false
        numeroAnalisis blank: false
        fechaAnalisis nullable: false

        porcentajeZincPromexbol nullable: false, min: 0.0, max: 100.0
        porcentajePlomoPromexbol nullable: false, min: 0.0, max: 100.0
        porcentajePlataPromexbol nullable: false, min: 0.0, max: 10000.0
        porcentajeHumedadPromexbol nullable: false, min: 0.0, max: 100.0
        porcentajeMermaPromexbol nullable: false, min: 0.0, max: 100.0

        porcentajeZincCliente nullable: false, min: 0.0, max: 100.0
        porcentajePlomoCliente nullable: false, min: 0.0, max: 100.0
        porcentajePlataCliente nullable: false, min: 0.0, max: 10000.0
        porcentajeHumedadCliente nullable: false, min: 0.0, max: 100.0
        porcentajeMermaCliente nullable: false, min: 0.0, max: 100.0

        porcentajeZincFinal nullable: false, min: 0.0, max: 100.0
        porcentajePlomoFinal nullable: false, min: 0.0, max: 100.0
        porcentajePlataFinal nullable: false, min: 0.0, max: 10000.0
        porcentajeHumedadFinal nullable: false, min: 0.0, max: 100.0
        porcentajeMermaFinal nullable: false, min: 0.0, max: 100.0

        /**********SE TIENE QUE DIFERENCIAR OBLIGATORIAMENTE ENTRE PBAG Y ZNAG PARA PODER
         * REALIZAR EL SEGUIMIENTO EN LA LIQUIDACION Y EN LOS REPORTES**********/
        modoValoracion inList: ["TABLA","TERMINOS DE CONTRATO"]
        tablaComplejo nullable: true
        terminosDeContrato nullable: true

        tablasIds display: false, blank: true, nullable: true
        terminosIds display: false, blank: true, nullable: true
        //penalidades
        porcentajeArsenico nullable: false, min: 0.0, max: 100.0
        porcentajeAntimonio nullable: false, min: 0.0, max: 100.0
        porcentajeSilice nullable: false, min: 0.0, max: 100.0
        porcentajeBismuto nullable: false, min: 0.0, max: 100.0
        porcentajeEstano nullable: false, min: 0.0, max: 100.0
        porcentajeZinc nullable: false, min: 0.0, max: 100.0

        observaciones blank: true, nullable: true
        usuario display: false, nullable: true
    }

    def beforeInsert = {
        def usuarioActual = springSecurityService.getCurrentUser() as SecUser
        System.out.println("*********** Empresa: DEPOSITO antes de deteccion:${deposito.toString()}")
        this.deposito = usuarioActual.deposito
        System.out.println("*********** Empresa: DEPOSITO despues de deteccion:${deposito.toString()}")
        usuario = springSecurityService.getCurrentUser()

        this.lote = this.recepcionDeComplejo.toString()
    }

    String toString(){
        "${recepcionDeComplejo.toString()}: Zn=${porcentajeZincPromexbol}; Pb: ${porcentajePlomoPromexbol}; Ag: ${porcentajePlataPromexbol}; H2O: ${porcentajeHumedadPromexbol}"
    }
}
