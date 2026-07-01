package org.socymet.liquidacion

import grails.gorm.transactions.Transactional

import java.math.RoundingMode

/**
 * Motor de cálculo autoritativo de la liquidación de complejos (referencia: hoja COMPLEJO
 * de liquidacion_complejo.xlsx). El núcleo es {@link #calcular(Map)}, una función PURA
 * (sin dependencias de dominio) fácilmente testeable; {@link #recalcular} la conecta al
 * dominio leyendo inputs y escribiendo resultados.
 */
@Transactional
class LiquidacionComplejoCalculoService {

    static final BigDecimal LIBRAS_POR_TM = 2204.6223G   // 1 TM = 2204.6223 libras finas
    static final BigDecimal GRAMOS_POR_OT = 31.1035G     // 1 onza troy = 31.1035 g
    static final int ESCALA = 12

    private static BigDecimal n(v) {
        if (v == null) return 0.0G
        if (v instanceof BigDecimal) return v
        try { return new BigDecimal(v.toString()) } catch (ignored) { return 0.0G }
    }

    /** División segura (evita ArithmeticException por decimales no terminantes). */
    private static BigDecimal div(BigDecimal a, BigDecimal b) {
        (b == null || b == 0.0G) ? 0.0G : a.divide(b, ESCALA, RoundingMode.HALF_UP)
    }

    /**
     * Cálculo PURO de la liquidación. Recibe un Map de inputs y devuelve un Map de resultados.
     * Inputs: pesoBruto, humedad, merma, ley{Zinc,Plomo,Plata},
     *   cotQuincenal{Zinc,Plomo,Plata}, alicuota{Zinc,Plomo,Plata}, cotDiaria{Zinc,Plomo,Plata},
     *   tipoCambio, vpt, retenciones (lista de [asignacion:'VNV'|'VBV', unidad:'%'|'Bs', cantidad]),
     *   bono{Calidad,Transporte,Lealtad,Incentivo}, anticipoContraEntrega,
     *   anticipoContraFuturaEntrega, saldoAnterior.
     */
    static Map calcular(Map e) {
        BigDecimal pb   = n(e.pesoBruto)
        BigDecimal hum  = n(e.humedad)
        BigDecimal merma= n(e.merma)
        BigDecimal tc   = n(e.tipoCambio)
        BigDecimal vpt  = n(e.vpt)

        // ── 1.1 Peso Neto Seco ────────────────────────────────────────────
        BigDecimal pns = pb * (1.0G - div(hum, 100.0G)) * (1.0G - div(merma, 100.0G))

        // ── 1.2 Pesos finos ───────────────────────────────────────────────
        BigDecimal finosZn = pns * div(n(e.leyZinc),  100.0G)
        BigDecimal finosPb = pns * div(n(e.leyPlomo), 100.0G)
        BigDecimal finosAg = pns * div(n(e.leyPlata), 10000.0G)   // ley plata en DM
        BigDecimal lbZn = div(finosZn, 1000.0G) * LIBRAS_POR_TM
        BigDecimal lbPb = div(finosPb, 1000.0G) * LIBRAS_POR_TM
        BigDecimal otAg = div(finosAg * 1000.0G, GRAMOS_POR_OT)

        // ── 1.3 VBV (cotización quincenal) ────────────────────────────────
        BigDecimal vbvZnD = lbZn * n(e.cotQuincenalZinc)
        BigDecimal vbvPbD = lbPb * n(e.cotQuincenalPlomo)
        BigDecimal vbvAgD = otAg * n(e.cotQuincenalPlata)
        BigDecimal vbvZnBs = vbvZnD * tc, vbvPbBs = vbvPbD * tc, vbvAgBs = vbvAgD * tc
        BigDecimal totalVbvD  = vbvZnD + vbvPbD + vbvAgD
        BigDecimal totalVbvBs = vbvZnBs + vbvPbBs + vbvAgBs

        // ── 1.4 Regalía Minera (VBV · alícuota) ───────────────────────────
        BigDecimal rmZnD = vbvZnD * div(n(e.alicuotaZinc),  100.0G)
        BigDecimal rmPbD = vbvPbD * div(n(e.alicuotaPlomo), 100.0G)
        BigDecimal rmAgD = vbvAgD * div(n(e.alicuotaPlata), 100.0G)
        BigDecimal rmZnBs = rmZnD * tc, rmPbBs = rmPbD * tc, rmAgBs = rmAgD * tc
        BigDecimal totalRmD  = rmZnD + rmPbD + rmAgD
        BigDecimal totalRmBs = rmZnBs + rmPbBs + rmAgBs

        // ── 1.5 Valoración para venta (cotización diaria, informativo) ─────
        BigDecimal vvDiariaD = (lbZn * n(e.cotDiariaZinc)) + (lbPb * n(e.cotDiariaPlomo)) + (otAg * n(e.cotDiariaPlata))
        BigDecimal vvDiariaBs = vvDiariaD * tc

        // ── 1.6 Valor Neto de Venta (VPT) ─────────────────────────────────
        BigDecimal vnvD  = vpt * div(pns, 1000.0G)
        BigDecimal vnvBs = vnvD * tc

        // ── 1.7 Deducciones: Regalía + retenciones (% de VNV o VBV / Bs fijo) ─
        BigDecimal sacos = n(e.cantidadSacos)
        BigDecimal totalOtrasRetenciones = 0.0G
        e.retenciones?.each { r ->
            BigDecimal cant = n(r.cantidad)
            BigDecimal monto
            switch (r.asignacion) {
                case 'VBV':        monto = (r.unidad == 'Bs') ? cant : totalVbvBs * div(cant, 100.0G); break
                case 'SACO':       monto = cant * sacos; break                 // Bs por saco
                case 'TON. BRUTA': monto = cant * div(pb, 1000.0G); break       // Bs por tonelada bruta húmeda
                case 'FIJO':       monto = cant; break                          // monto fijo en Bs
                default:           monto = (r.unidad == 'Bs') ? cant : vnvBs * div(cant, 100.0G); break  // VNV
            }
            r.monto = monto
            totalOtrasRetenciones += monto
        }
        BigDecimal totalDeducciones = totalRmBs + totalOtrasRetenciones

        // ── 1.8 Valor Pagable del Mineral ─────────────────────────────────
        BigDecimal valorPagable = vnvBs - totalDeducciones

        // ── 1.9 / 1.10 Bonos y Anticipos ──────────────────────────────────
        BigDecimal totalBonos = n(e.bonoCalidad) + n(e.bonoTransporte) + n(e.bonoLealtad) + n(e.bonoIncentivo)
        // saldoAnterior es INFORMATIVO (no se descuenta); el cobro del saldo se canaliza por anticipoContraFuturaEntrega
        BigDecimal totalAnticipos = n(e.anticipoContraEntrega) + n(e.anticipoContraFuturaEntrega)

        // ── 1.11 Líquido Pagable ──────────────────────────────────────────
        BigDecimal liquido = valorPagable + totalBonos - totalAnticipos

        // ── 1.12 Precio calculado ($us/TM) ────────────────────────────────
        BigDecimal precio = div(div(liquido, tc), pns) * 1000.0G

        [
            kilosNetosSecos: pns,
            finosZinc: finosZn, finosPlomo: finosPb, finosPlata: finosAg,
            librasFinasZinc: lbZn, librasFinasPlomo: lbPb, onzasTroyPlata: otAg,
            vbvZincDolares: vbvZnD, vbvPlomoDolares: vbvPbD, vbvPlataDolares: vbvAgD,
            vbvZincBolivianos: vbvZnBs, vbvPlomoBolivianos: vbvPbBs, vbvPlataBolivianos: vbvAgBs,
            totalVbvDolares: totalVbvD, totalVbvBolivianos: totalVbvBs,
            rmZincDolares: rmZnD, rmPlomoDolares: rmPbD, rmPlataDolares: rmAgD,
            rmZincBolivianos: rmZnBs, rmPlomoBolivianos: rmPbBs, rmPlataBolivianos: rmAgBs,
            totalRmDolares: totalRmD, totalRmBolivianos: totalRmBs,
            valorVentaDiariaDolares: vvDiariaD, valorVentaDiariaBolivianos: vvDiariaBs,
            vnvDolares: vnvD, vnvBolivianos: vnvBs,
            totalDeducciones: totalDeducciones,
            valorPagable: valorPagable,
            totalBonos: totalBonos, totalAnticipos: totalAnticipos,
            liquidoPagable: liquido, precioCalculado: precio
        ]
    }

    /** Conecta el motor al dominio: lee inputs de la liquidación, calcula y escribe resultados. */
    void recalcular(LiquidacionDeComplejo liq) {
        // Todas las retenciones del detalle son deducciones; la Regalía Minera la agrega
        // el motor por separado (calculada de la RM), no es una fila de EmpresaRetenciones.
        // _ref permite reescribir el monto calculado en cada retención hija.
        def retenciones = liq.detalleRetenciones?.collect {
            [asignacion: it.asignacionDelDescuento, unidad: it.unidadDeDescuento, cantidad: it.cantidadDescuento, _ref: it]
        } ?: []

        def r = calcular([
            pesoBruto: liq.pesoBruto, humedad: liq.porcentajeHumedadFinal, merma: liq.porcentajeMermaFinal,
            leyZinc: liq.porcentajeZincFinal, leyPlomo: liq.porcentajePlomoFinal, leyPlata: liq.porcentajePlataFinal,
            cotQuincenalZinc: liq.cotizacionQuincenalDeZinc, cotQuincenalPlomo: liq.cotizacionQuincenalDePlomo, cotQuincenalPlata: liq.cotizacionQuincenalDePlata,
            alicuotaZinc: liq.alicuotaDeZinc, alicuotaPlomo: liq.alicuotaDePlomo, alicuotaPlata: liq.alicuotaDePlata,
            cotDiariaZinc: liq.cotizacionDiariaDeZinc, cotDiariaPlomo: liq.cotizacionDiariaDePlomo, cotDiariaPlata: liq.cotizacionDiariaDePlata,
            tipoCambio: liq.tipoDeCambioOficial, vpt: liq.valorPorTonelada,
            cantidadSacos: liq.cantidadSacos,
            retenciones: retenciones,
            bonoCalidad: liq.bonoCalidad, bonoTransporte: liq.bonoTransporte, bonoLealtad: liq.bonoLealtad, bonoIncentivo: liq.bonoIncentivo,
            anticipoContraEntrega: liq.totalAnticiposContraEntrega, anticipoContraFuturaEntrega: liq.totalAnticiposContraFuturaEntrega, saldoAnterior: liq.saldoAnterior
        ])

        liq.kilosNetosSecos = r.kilosNetosSecos
        liq.kilosFinosZinc = r.finosZinc; liq.kilosFinosPlomo = r.finosPlomo; liq.kilosFinosPlata = r.finosPlata
        liq.librasFinasDeZinc = r.librasFinasZinc; liq.librasFinasDePlomo = r.librasFinasPlomo; liq.onzasTroyDePlata = r.onzasTroyPlata
        liq.valorOficialBrutoDeZinc = r.vbvZincDolares; liq.valorOficialBrutoDePlomo = r.vbvPlomoDolares; liq.valorOficialBrutoDePlata = r.vbvPlataDolares
        liq.valorOficialBrutoDeZincEnBolivianos = r.vbvZincBolivianos; liq.valorOficialBrutoDePlomoEnBolivianos = r.vbvPlomoBolivianos; liq.valorOficialBrutoDePlataEnBolivianos = r.vbvPlataBolivianos
        liq.valorOficialBruto = r.totalVbvDolares; liq.valorOficialBrutoEnBolivianos = r.totalVbvBolivianos
        liq.regaliaMineraDeZinc = r.rmZincDolares; liq.regaliaMineraDePlomo = r.rmPlomoDolares; liq.regaliaMineraDePlata = r.rmPlataDolares
        liq.regaliaMineraDeZincEnBolivianos = r.rmZincBolivianos; liq.regaliaMineraDePlomoEnBolivianos = r.rmPlomoBolivianos; liq.regaliaMineraDePlataEnBolivianos = r.rmPlataBolivianos
        liq.totalRegaliaMineraDolares = r.totalRmDolares; liq.regaliaMinera = r.totalRmBolivianos
        liq.valorNetoMineral = r.vnvDolares; liq.valorNetoMineralEnBolivianos = r.vnvBolivianos
        liq.totalRetenciones = r.totalDeducciones
        liq.valorPagableMineral = r.valorPagable
        liq.totalBonos = r.totalBonos; liq.totalAnticipos = r.totalAnticipos
        liq.totalLiquidoPagable = r.liquidoPagable
        liq.precioCalculado = r.precioCalculado

        // Escribir el monto calculado en cada retención hija
        retenciones.each { if (it._ref != null) it._ref.monto = it.monto }
    }
}
