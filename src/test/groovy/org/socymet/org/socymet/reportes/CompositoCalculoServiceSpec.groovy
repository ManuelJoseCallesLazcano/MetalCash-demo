package org.socymet.org.socymet.reportes

import spock.lang.Specification

/**
 * Verifica el motor de resumen PURO del compósito (totales, ponderados, participación).
 * No requiere bootstrap de Grails (función estática pura). Valores esperados calculados a mano.
 */
class CompositoCalculoServiceSpec extends Specification {

    private static boolean cerca(valor, esperado, tol = 0.01) {
        Math.abs((valor as BigDecimal).subtract(esperado as BigDecimal).doubleValue()) <= tol
    }

    /** Dos lotes: A liquidado (EMP1), B no liquidado (EMP2). */
    private static List<Map> dosLotes() {
        [
            [pesoBruto: 10000.0G, humedad: 5.0G, merma: 1.0G,
             leyZinc: 8.0G, leyPlomo: 0.2G, leyPlata: 100.0G,
             liquidado: true, valorNeto: 50000.0G, liquidoPagable: 40000.0G,
             nombreEmpresa: 'EMP1', departamento: 'POTOSI', municipio: 'POTOSI'],
            [pesoBruto: 20000.0G, humedad: 10.0G, merma: 1.0G,
             leyZinc: 6.0G, leyPlomo: 0.5G, leyPlata: 200.0G,
             liquidado: false, valorNeto: 99999.0G, liquidoPagable: 99999.0G,
             nombreEmpresa: 'EMP2', departamento: 'ORURO', municipio: 'ORURO']
        ]
    }

    void "totales y ponderados del conjunto (1 liquidado, 1 no liquidado)"() {
        when:
        def r = CompositoCalculoService.calcular(dosLotes())

        then:
        // PNS: A=9405, B=17820 ; PNH: A=9900, B=19800
        cerca(r.totalPesoBruto, 30000.0G)
        cerca(r.totalKilosNetosSecos, 27225.0G)
        cerca(r.totalKilosNetosHumedos, 29700.0G)
        cerca(r.totalKilosFinosZinc, 1821.6G)
        cerca(r.totalKilosFinosPlomo, 107.91G)
        cerca(r.totalKilosFinosPlata, 450.45G)

        and: "ponderados sobre PNS (leyes) y PNH (humedad)"
        cerca(r.leyPromedioZinc, 6.6905G)
        cerca(r.leyPromedioPlomo, 0.3964G)
        cerca(r.leyPromedioPlata, 165.4455G, 0.01)
        cerca(r.humedadPromedio, 8.3333G)

        and: "valor neto y líquido SOLO del lote liquidado (D3)"
        cerca(r.totalValorNeto, 50000.0G)
        cerca(r.totalLiquidoPagable, 40000.0G)
        r.cantidadLotes == 2
        r.cantidadLiquidados == 1
        r.cantidadNoLiquidados == 1
    }

    void "participación por empresa = PNS del grupo / PNS total"() {
        when:
        def r = CompositoCalculoService.calcular(dosLotes())
        def emp1 = r.participacion.find { it.nombreEmpresa == 'EMP1' }
        def emp2 = r.participacion.find { it.nombreEmpresa == 'EMP2' }

        then:
        r.participacion.size() == 2
        cerca(emp1.kilosNetosSecos, 9405.0G)
        cerca(emp1.porcentajeParticipacion, 34.5455G)
        cerca(emp2.kilosNetosSecos, 17820.0G)
        cerca(emp2.porcentajeParticipacion, 65.4545G)
    }

    void "lista vacía no divide por cero y devuelve ceros"() {
        when:
        def r = CompositoCalculoService.calcular([])

        then:
        cerca(r.totalKilosNetosSecos, 0.0G)
        cerca(r.leyPromedioZinc, 0.0G)
        cerca(r.humedadPromedio, 0.0G)
        cerca(r.totalLiquidoPagable, 0.0G)
        r.cantidadLotes == 0
        r.participacion == []
    }

    void "líquido y valor de lotes no liquidados se ignoran aunque traigan datos"() {
        given: "un solo lote NO liquidado con valores basura"
        def lotes = [[pesoBruto: 10000.0G, humedad: 0.0G, merma: 0.0G,
                      leyZinc: 5.0G, leyPlomo: 0.0G, leyPlata: 0.0G,
                      liquidado: false, valorNeto: 123.0G, liquidoPagable: 456.0G,
                      nombreEmpresa: 'X']]
        when:
        def r = CompositoCalculoService.calcular(lotes)

        then:
        cerca(r.totalValorNeto, 0.0G)
        cerca(r.totalLiquidoPagable, 0.0G)
        cerca(r.totalKilosNetosSecos, 10000.0G)   // PNS sí se calcula
        r.cantidadNoLiquidados == 1
    }
}
