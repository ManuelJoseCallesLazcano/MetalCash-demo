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
    }

    static mapping = {
//        datosArchivo type: 'blob'
        datosArchivo sqlType: 'blob'
        datosZinc type: 'text'
        datosPlomo type: 'text'
        datosPlata type: 'text'
    }

    def beforeInsert = {
//        new TerminosDeContrato(
//                nombreContrato: "terminos auxiliares",
//                empresa: empresa,
//                tipoDeMineral: "PB-AG",
//                porcentajeArsenico: 0,
//                porcentajeAntimonio: 0,
//                porcentajeBismuto: 0,
//                porcentajeEstano: 0,
//                porcentajeHierro: 0,
//                porcentajeSilice: 0,
//                porcentajeZinc: 0,
//                deduccionUnitariaZinc: 0,
//                deduccionUnitariaPlomo: 0,
//                deduccionUnitariaPlata: 0,
//                deduccionUnitariaCobre: 0,
//                porcentajePagableLMEZinc: 0,
//                porcentajePagableLMEPlomo: 0,
//                porcentajePagableLMEPlata: 0,
//                porcentajePagableLMECobre: 0,
//                maquilaZincPlata: 0,
//                baseZincPlata: 0,
//                escaladorZincPlata: 0,
//                maquilaPlomoPlata: 0,
//                basePlomoPlata: 0,
//                escaladorPlomoPlata: 0,
//                maquilaCobre: 0,
//                baseCobre: 0,
//                escaladorCobre: 0,
//                deduccionRefinacionOnzaZincPlata: 0,
//                deduccionRefinacionOnzaPlomoPlata: 0,
//                deduccionRefinacionOnzaCobrePlata: 0,
//                deduccionRefinacionLibraZinc: 0,
//                deduccionRefinacionLibraPlomo: 0,
//                deduccionRefinacionLibraCobre: 0,
//                arsenicoLibre: 0,
//                costoUnitarioArsenico: 0,
//                porcentajeUnitarioArsenico: 0,
//                antimonioLibre: 0,
//                costoUnitarioAntimonio: 0,
//                porcentajeUnitarioAntimonio: 0,
//                bismutoLibre: 0,
//                costoUnitarioBismuto: 0,
//                porcentajeUnitarioBismuto: 0,
//                estanoLibre: 0,
//                costoUnitarioEstano: 0,
//                porcentajeUnitarioEstano: 0,
//                hierroLibre: 0,
//                costoUnitarioHierro: 0,
//                porcentajeUnitarioHierro: 0,
//                siliceLibre: 0,
//                costoUnitarioSilice: 0,
//                porcentajeUnitarioSilice: 0,
//                zincLibre: 0,
//                costoUnitarioZinc: 0,
//                porcentajeUnitarioZinc: 0,
//                transporteInterno: 0,
//                laboratorio: 0,
//                molienda: 0,
//                manipuleo: 0,
//                margenAdministrativo: 0,
//                transporteAPuerto: 0,
//                rollBack: 0
//        ).save(failOnError: true)

        new TablaPrecioPorLme(
                nombreTabla: "tabla lme auxiliar",
                empresa: empresa,
                naturalezaMineral: "SULFURO",
                cotizacionDiariaDeMinerales: CotizacionDiariaDeMinerales.findByActivo(1),
                leyPlata: 0,
                porcentajeLme: 0,
                valorPorTonelada: 0,
                tablaDePrecios: "[]"
        ).save(failOnError: true)
    }

    String toString(){
        "$nombreTabla"
    }
}
