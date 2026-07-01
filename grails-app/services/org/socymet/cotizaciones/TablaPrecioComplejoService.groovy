package org.socymet.cotizaciones

/**
 * Cálculo del Valor por Tonelada (VPT) a partir de las curvas (ley → % pagable) de una
 * TablaOrigenCotizacionesComplejo, por interpolación lineal. Métodos puros estáticos (testeables).
 *
 *  - cotTon Zinc/Plomo = cotización · 2204.6223
 *  - cotTon Plata      = cotización · 1000 · 1000 / 31.1035
 *  - VPT Zinc/Plomo    = cotTon · ley/100   · %pagable/100
 *  - VPT Plata         = cotTon · ley/10000 · %pagable/100
 *  - %pagable se interpola linealmente por la ley entre los puntos que la rodean;
 *    si la ley queda fuera del rango de puntos → VPT 0 para ese elemento.
 */
class TablaPrecioComplejoService {

    static transactional = false

    static final BigDecimal LIBRAS_POR_TM = 2204.6223G
    static final BigDecimal GRAMOS_POR_OT = 31.1035G

    /** Cotización en tonelada para Zinc/Plomo. */
    static BigDecimal cotToneladaZincPlomo(BigDecimal cot) {
        (cot ?: 0G) * LIBRAS_POR_TM
    }

    /** Cotización en tonelada para Plata. */
    static BigDecimal cotToneladaPlata(BigDecimal cot) {
        (cot ?: 0G) * 1000G * 1000G / GRAMOS_POR_OT
    }

    /**
     * Interpola linealmente el % pagable según la ley, usando los puntos (cada uno con .ley y
     * .porcentajePagable — TablaPrecioPunto o Map). Devuelve null si la ley queda fuera del rango.
     */
    static BigDecimal interpolarPorcentajePagable(List puntos, BigDecimal ley) {
        if (!puntos || ley == null) return null
        def ps = puntos
                .collect { [ley: (it.ley as BigDecimal), pag: (it.porcentajePagable as BigDecimal)] }
                .findAll { it.ley != null && it.pag != null }
                .sort { it.ley }
        if (!ps) return null
        if (ley < ps.first().ley || ley > ps.last().ley) return null     // fuera de rango
        for (int i = 0; i < ps.size(); i++) {
            if (ley == ps[i].ley) return ps[i].pag                        // coincidencia exacta
            if (i > 0 && ley > ps[i - 1].ley && ley < ps[i].ley) {        // interpolación lineal
                BigDecimal x0 = ps[i - 1].ley, x1 = ps[i].ley
                BigDecimal y0 = ps[i - 1].pag, y1 = ps[i].pag
                return y0 + (y1 - y0) * (ley - x0) / (x1 - x0)
            }
        }
        return null
    }

    /**
     * VPT de un elemento. cotElemento = cotización diaria del elemento (no en tonelada).
     * elemento ∈ {ZINC, PLOMO, PLATA}. Si la ley está fuera del rango de puntos → 0.
     */
    static BigDecimal vptElemento(String elemento, BigDecimal cotElemento, BigDecimal ley, List puntos) {
        BigDecimal pag = interpolarPorcentajePagable(puntos, ley)
        if (pag == null || ley == null) return 0.0G   // 0.0G = BigDecimal (0G sería BigInteger y rompería setScale)
        BigDecimal cotTon = (elemento == 'PLATA') ? cotToneladaPlata(cotElemento) : cotToneladaZincPlomo(cotElemento)
        BigDecimal factorLey = (elemento == 'PLATA') ? (ley / 10000G) : (ley / 100G)
        cotTon * factorLey * pag / 100G
    }

    /** VPT total = Zinc + Plomo + Plata (overload puro, testeable). */
    static Map vptTotal(List puntosZinc, List puntosPlomo, List puntosPlata,
                        BigDecimal cotZinc, BigDecimal cotPlomo, BigDecimal cotPlata,
                        BigDecimal leyZinc, BigDecimal leyPlomo, BigDecimal leyPlata) {
        BigDecimal vZn = vptElemento('ZINC', cotZinc, leyZinc, puntosZinc)
        BigDecimal vPb = vptElemento('PLOMO', cotPlomo, leyPlomo, puntosPlomo)
        BigDecimal vAg = vptElemento('PLATA', cotPlata, leyPlata, puntosPlata)
        [zinc: vZn, plomo: vPb, plata: vAg, total: vZn + vPb + vAg]
    }

    /**
     * VPT total tomando la tabla (sus puntos por elemento) y la cotización diaria del lote
     * más las leyes finales de la liquidación.
     */
    static Map vptTotal(TablaOrigenCotizacionesComplejo tabla, CotizacionDiariaDeMinerales cot,
                        BigDecimal leyZinc, BigDecimal leyPlomo, BigDecimal leyPlata) {
        def puntos = tabla?.puntos ?: []
        vptTotal(
                puntos.findAll { it.elemento == 'ZINC' }.toList(),
                puntos.findAll { it.elemento == 'PLOMO' }.toList(),
                puntos.findAll { it.elemento == 'PLATA' }.toList(),
                cot?.zinc, cot?.plomo, cot?.plata,
                leyZinc, leyPlomo, leyPlata
        )
    }
}
