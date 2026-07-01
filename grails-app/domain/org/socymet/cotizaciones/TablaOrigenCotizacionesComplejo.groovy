package org.socymet.cotizaciones

import org.socymet.proveedor.Empresa

import java.sql.Blob

class TablaOrigenCotizacionesComplejo {
    static auditable = true

    String nombreTabla
    Empresa empresa
    String naturalezaMineral

    String nombreArchivo
    byte[] datosArchivo
//    Blob datosArchivo
    Date fechaSubida = new Date()

    String datosZinc
    String datosPlomo
    String datosPlata

    // Rediseño (curvas ley→%pagable): cotización diaria referencial + fecha de actualización + puntos por elemento
    CotizacionDiariaDeMinerales cotizacionDiariaDeMinerales
    Date fechaActualizacion

    static hasMany = [puntos: TablaPrecioPunto]

    static constraints = {
        nombreTabla blank: false
        empresa nullable: true
        naturalezaMineral inList: ["SULFURO","OXIDO"], nullable: false, blank: false
        nombreArchivo(display: false, blank:true, nullable:false)
//        datosArchivo(blank: true, nullable:true, maxSize:1048576) //tamano maximo: 1 Megabyte
        datosArchivo(blank: true, nullable:true) //tamano maximo: 1 Megabyte
        fechaSubida display:false, nullable: true

        datosZinc display: false, blank: false, nullable: false
        datosPlomo display: false, blank: false, nullable: false
        datosPlata display: false, blank: false, nullable: false

        cotizacionDiariaDeMinerales nullable: true
        fechaActualizacion nullable: true
    }

    static mapping = {
//        datosArchivo type: 'blob'
        datosArchivo sqlType: 'blob'
        datosZinc type: 'text'
        datosPlomo type: 'text'
        datosPlata type: 'text'
    }

    String toString(){
        "$nombreTabla"
    }
}
