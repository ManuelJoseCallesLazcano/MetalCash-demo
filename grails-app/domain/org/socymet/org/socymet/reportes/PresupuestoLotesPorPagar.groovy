package org.socymet.org.socymet.reportes

import org.socymet.cotizaciones.TablaCotizacionAntimonio
import org.socymet.cotizaciones.TablaCotizacionEstano
import org.socymet.cotizaciones.TablaCotizacionPlata
import org.socymet.cotizaciones.TablaCotizacionWolfran
import org.socymet.proveedor.Empresa

class PresupuestoLotesPorPagar {
    String elemento
    TablaCotizacionEstano tablaCotizacionEstano
    TablaCotizacionPlata tablaCotizacionPlata
    TablaCotizacionAntimonio tablaCotizacionAntimonio
    TablaCotizacionWolfran tablaCotizacionWolfran
    Empresa empresa
    Date fechaInicial
    Date fechaFinal
    String loteInicial
    String loteFinal

    static constraints = {
        elemento inList: ["Complejo"]
        tablaCotizacionEstano nullable: true
        tablaCotizacionPlata nullable: true
        tablaCotizacionAntimonio nullable: true
        tablaCotizacionWolfran nullable: true
        empresa nullable: true
        fechaInicial(nullable: true)
        fechaFinal(nullable: true)
        loteInicial blank: true
        loteFinal blank: true
    }
}
