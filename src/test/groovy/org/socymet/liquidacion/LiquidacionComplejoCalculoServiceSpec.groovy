package org.socymet.liquidacion

import spock.lang.Specification

/**
 * Verifica el motor de cálculo PURO contra los valores de referencia de la hoja COMPLEJO
 * de liquidacion_complejo.xlsx. No requiere bootstrap de Grails (función estática pura).
 */
class LiquidacionComplejoCalculoServiceSpec extends Specification {

    private static boolean cerca(valor, esperado, tol = 0.05) {
        Math.abs((valor as BigDecimal).subtract(esperado as BigDecimal).doubleValue()) <= tol
    }

    /** Inputs idénticos al ejemplo del xlsx (hoja COMPLEJO). */
    private static Map inputsXlsx() {
        [
            // pesoBruto = 29360 (bruto húmedo) − 11000 (tara) = 18360 (neto de tara, húmedo)
            pesoBruto: 18360.0G, humedad: 1.0G, merma: 0.0G,
            leyZinc: 7.88G, leyPlomo: 0.18G, leyPlata: 0.98G,
            cotQuincenalZinc: 1.27G, cotQuincenalPlomo: 0.9G, cotQuincenalPlata: 38.22G,
            alicuotaZinc: 3.0G, alicuotaPlomo: 3.0G, alicuotaPlata: 3.6G,
            cotDiariaZinc: 1.2492G, cotDiariaPlomo: 0.8702G, cotDiariaPlata: 37.35G,
            tipoCambio: 6.96G, vpt: 200.0G,
            retenciones: [
                [asignacion: 'VNV', unidad: '%', cantidad: 1.8G],   // CNS
                [asignacion: 'VNV', unidad: '%', cantidad: 1.0G],   // COMIBOL
                [asignacion: 'VNV', unidad: '%', cantidad: 0.4G],   // FENCOMIN
                [asignacion: 'VNV', unidad: '%', cantidad: 1.5G]    // FEDECOMIN
            ],
            bonoCalidad: 100.0G, bonoTransporte: 200.0G, bonoLealtad: 300.0G,
            anticipoContraEntrega: 2500.0G, anticipoContraFuturaEntrega: 500.0G, saldoAnterior: 0.0G
        ]
    }

    void "el cálculo reproduce los valores de referencia del xlsx"() {
        when:
        def r = LiquidacionComplejoCalculoService.calcular(inputsXlsx())

        then:
        cerca(r.kilosNetosSecos, 18176.4G)          // F15
        cerca(r.finosZinc, 1432.30032G)             // J15
        cerca(r.finosPlomo, 32.71752G)              // J16
        cerca(r.totalVbvBolivianos, 43597.5706G, 0.1)   // G26 (VBV Bs total)
        cerca(r.totalRmBolivianos, 1399.3334G, 0.1)     // J26 (RM Bs total = Regalía)
        cerca(r.vnvBolivianos, 25301.5488G, 0.1)        // D39
        cerca(r.totalDeducciones, 2588.5062G, 0.1)      // G46
        cerca(r.valorPagable, 22713.0426G, 0.1)         // I48
        cerca(r.totalBonos, 600.0G)                      // D54
        cerca(r.totalAnticipos, 3000.0G)                // D60
        cerca(r.liquidoPagable, 20313.0426G, 0.1)       // I62
        cerca(r.precioCalculado, 160.5676G, 0.1)        // H65
    }

    void "sin tipo de cambio ni peso no lanza excepción y da cero"() {
        when:
        def r = LiquidacionComplejoCalculoService.calcular([:])

        then:
        notThrown(Exception)
        r.kilosNetosSecos == 0.0G
        r.liquidoPagable == 0.0G
        r.precioCalculado == 0.0G
    }
}
