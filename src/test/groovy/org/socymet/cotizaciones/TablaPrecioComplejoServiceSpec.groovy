package org.socymet.cotizaciones

import spock.lang.Specification

/**
 * Verifica el cálculo PURO de VPT (cotización en tonelada, interpolación lineal del % pagable
 * y fórmulas por elemento). No requiere bootstrap de Grails.
 */
class TablaPrecioComplejoServiceSpec extends Specification {


    private static boolean cerca(valor, esperado, tol = 0.001) {
        Math.abs((valor as BigDecimal).subtract(esperado as BigDecimal).doubleValue()) <= tol
    }

    // puntos de prueba
    private static List p(List... pares) {
        pares.collect { [ley: it[0] as BigDecimal, porcentajePagable: it[1] as BigDecimal] }
    }

    def "cotización en tonelada Zinc/Plomo = cot · 2204.6223"() {
        expect:
        cerca(TablaPrecioComplejoService.cotToneladaZincPlomo(1.0G), 2204.6223G)
        cerca(TablaPrecioComplejoService.cotToneladaZincPlomo(0G), 0G)
    }

    def "cotización en tonelada Plata = cot · 1e6 / 31.1035 (cot=31.1035 ⇒ 1.000.000)"() {
        expect:
        cerca(TablaPrecioComplejoService.cotToneladaPlata(31.1035G), 1000000G)
    }

    def "interpolación: coincidencia exacta devuelve su % pagable"() {
        expect:
        cerca(TablaPrecioComplejoService.interpolarPorcentajePagable(p([40, 80], [50, 90]), 40G), 80G)
        cerca(TablaPrecioComplejoService.interpolarPorcentajePagable(p([40, 80], [50, 90]), 50G), 90G)
    }

    def "interpolación: punto intermedio (ley 45 entre (40,80) y (50,90)) ⇒ 85"() {
        expect:
        cerca(TablaPrecioComplejoService.interpolarPorcentajePagable(p([40, 80], [50, 90]), 45G), 85G)
    }

    def "interpolación: fuera de rango devuelve null"() {
        expect:
        TablaPrecioComplejoService.interpolarPorcentajePagable(p([40, 80], [50, 90]), 30G) == null
        TablaPrecioComplejoService.interpolarPorcentajePagable(p([40, 80], [50, 90]), 60G) == null
        TablaPrecioComplejoService.interpolarPorcentajePagable([], 45G) == null
    }

    def "VPT Zinc: cot=1, ley=100, %pag=100 ⇒ cotTon=2204.6223"() {
        expect:
        cerca(TablaPrecioComplejoService.vptElemento('ZINC', 1.0G, 100G, p([50, 100], [150, 100])), 2204.6223G)
    }

    def "VPT Plata: cot=31.1035, ley=10000, %pag=100 ⇒ 1.000.000"() {
        expect:
        cerca(TablaPrecioComplejoService.vptElemento('PLATA', 31.1035G, 10000G, p([5000, 100], [15000, 100])), 1000000G)
    }

    def "VPT con interpolación: Zinc cot=1, ley=45, puntos (40,80)/(50,90) ⇒ %pag=85 ⇒ 843.268"() {
        // 2204.6223 · 0.45 · 0.85 = 843.26802975
        expect:
        cerca(TablaPrecioComplejoService.vptElemento('ZINC', 1.0G, 45G, p([40, 80], [50, 90])), 843.268G)
    }

    def "VPT 0 cuando la ley está fuera del rango de puntos"() {
        expect:
        TablaPrecioComplejoService.vptElemento('ZINC', 1.0G, 200G, p([50, 100], [150, 100])) == 0G
        TablaPrecioComplejoService.vptElemento('PLATA', 31.1035G, 1G, p([5000, 100], [15000, 100])) == 0G
    }

    def "VPT total = suma Zinc + Plomo + Plata"() {
        when:
        def r = TablaPrecioComplejoService.vptTotal(
                p([50, 100], [150, 100]),   // Zinc
                p([50, 100], [150, 100]),   // Plomo
                p([5000, 100], [15000, 100]), // Plata
                1.0G, 1.0G, 31.1035G,       // cotizaciones
                100G, 100G, 10000G)         // leyes

        then:
        cerca(r.zinc, 2204.6223G)
        cerca(r.plomo, 2204.6223G)
        cerca(r.plata, 1000000G)
        cerca(r.total, 1004409.2446G)   // 2204.6223 + 2204.6223 + 1000000
    }
}
