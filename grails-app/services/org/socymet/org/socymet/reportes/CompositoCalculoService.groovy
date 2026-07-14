package org.socymet.org.socymet.reportes

import grails.gorm.transactions.Transactional
import org.socymet.calidad.ControlCalidadComplejo
import org.socymet.liquidacion.LiquidacionDeComplejo
import org.socymet.recepcion.RecepcionDeComplejo

import java.math.RoundingMode

/**
 * Motor de resumen del compósito (Conjuntos de Lotes). El núcleo es {@link #calcular(List)},
 * una función PURA (sin dependencias de dominio) que espeja el cálculo en vivo del formulario
 * y es fácilmente testeable; {@link #calcularPorRecepciones(List)} la conecta al dominio leyendo
 * recepción + control de calidad (leyes PROMEXBOL, D4) + liquidación activa del lote.
 *
 * Convenciones (§2.1 del plan, heredadas de reporteLotesLiquidados):
 *   PNS = pesoBruto·(1−H/100)·(1−merma/100);  PNH = pesoBruto·(1−merma/100)
 *   KFZn = PNS·%Zn/100;  KFPb = PNS·%Pb/100;  KFAg = PNS·(DM Ag)/10000
 *   %Zn = ΣKFZn/ΣPNS·100 (Pb ídem);  DM Ag = ΣKFAg/ΣPNS·10000;  %Hum = (ΣPNH−ΣPNS)/ΣPNH·100
 *   valorNeto y liquidoPagable suman SOLO lotes liquidados (D3).
 */
@Transactional
class CompositoCalculoService {

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
     * Cálculo PURO del resumen del conjunto. Recibe la lista de lotes (Maps) y devuelve totales,
     * ponderados y participación. Cada lote de entrada admite:
     *   pesoBruto, humedad, merma, leyZinc, leyPlomo, leyPlata,
     *   liquidado (bool), valorNeto, liquidoPagable,
     *   nombreEmpresa, departamento, municipio, lote, recepcionId, liquidacionId.
     * No muta la entrada: devuelve copias enriquecidas con pns/pnh/kf* en `lotes`.
     */
    static Map calcular(List<Map> lotesEntrada) {
        List<Map> lotes = []
        BigDecimal totPB = 0.0G, totPNS = 0.0G, totPNH = 0.0G
        BigDecimal totKFZn = 0.0G, totKFPb = 0.0G, totKFAg = 0.0G
        BigDecimal totValorNeto = 0.0G, totLiquido = 0.0G
        int cantLiquidados = 0

        (lotesEntrada ?: []).each { e ->
            BigDecimal pb    = n(e.pesoBruto)
            BigDecimal hum   = n(e.humedad)
            BigDecimal merma = n(e.merma)

            BigDecimal pnh = pb * (1.0G - div(merma, 100.0G))
            BigDecimal pns = pnh * (1.0G - div(hum, 100.0G))

            BigDecimal kfZn = pns * div(n(e.leyZinc),  100.0G)
            BigDecimal kfPb = pns * div(n(e.leyPlomo), 100.0G)
            BigDecimal kfAg = pns * div(n(e.leyPlata), 10000.0G)   // ley plata en DM

            boolean liquidado = e.liquidado as boolean
            BigDecimal valorNeto = liquidado ? n(e.valorNeto) : 0.0G
            BigDecimal liquido   = liquidado ? n(e.liquidoPagable) : 0.0G

            totPB += pb; totPNS += pns; totPNH += pnh
            totKFZn += kfZn; totKFPb += kfPb; totKFAg += kfAg
            totValorNeto += valorNeto; totLiquido += liquido
            if (liquidado) cantLiquidados++

            lotes << (e + [pns: pns, pnh: pnh, kilosFinosZinc: kfZn,
                           kilosFinosPlomo: kfPb, kilosFinosPlata: kfAg,
                           valorNeto: valorNeto, liquidoPagable: liquido])
        }

        // Ponderados sobre PNS (leyes) y sobre PNH (humedad)
        BigDecimal leyZn = div(totKFZn, totPNS) * 100.0G
        BigDecimal leyPb = div(totKFPb, totPNS) * 100.0G
        BigDecimal leyAg = div(totKFAg, totPNS) * 10000.0G
        BigDecimal humedad = totPNH == 0.0G ? 0.0G : div(totPNH - totPNS, totPNH) * 100.0G

        [
            lotes                : lotes,
            totalPesoBruto       : totPB,
            totalKilosNetosSecos : totPNS,
            totalKilosNetosHumedos: totPNH,
            totalKilosFinosZinc  : totKFZn,
            totalKilosFinosPlomo : totKFPb,
            totalKilosFinosPlata : totKFAg,
            leyPromedioZinc      : leyZn,
            leyPromedioPlomo     : leyPb,
            leyPromedioPlata     : leyAg,
            humedadPromedio      : humedad,
            totalValorNeto       : totValorNeto,
            totalLiquidoPagable  : totLiquido,
            cantidadLotes        : lotes.size(),
            cantidadLiquidados   : cantLiquidados,
            cantidadNoLiquidados : lotes.size() - cantLiquidados,
            participacion        : participacionPorEmpresa(lotes, totPNS)
        ]
    }

    /** Participación por empresa: ΣPNS del grupo / ΣPNS total · 100. */
    private static List<Map> participacionPorEmpresa(List<Map> lotes, BigDecimal totPNS) {
        Map<String, Map> grupos = [:]
        lotes.each { l ->
            String clave = (l.nombreEmpresa ?: '-').toString()
            Map g = grupos[clave]
            if (g == null) {
                grupos[clave] = [nombreEmpresa: clave,
                                 departamento: (l.departamento ?: '-').toString(),
                                 municipio   : (l.municipio ?: '-').toString(),
                                 kilosNetosSecos: n(l.pns)]
            } else {
                g.kilosNetosSecos = n(g.kilosNetosSecos) + n(l.pns)
            }
        }
        grupos.values().collect { g ->
            g + [porcentajeParticipacion: div(n(g.kilosNetosSecos), totPNS) * 100.0G]
        }
    }

    /**
     * Reúne los inputs desde el dominio para las recepciones dadas y llama a {@link #calcular}.
     * Leyes/humedad/merma = PROMEXBOL (D4). Valor neto/líquido = de la liquidación ACTIVA del lote
     * (no anulada); si el lote no está liquidado, 0 (D3).
     */
    Map calcularPorRecepciones(List<Long> recepcionIds) {
        List<Map> lotes = (recepcionIds ?: []).collect { id -> loteMap(id) }.findAll { it != null }
        calcular(lotes)
    }

    /** Construye el Map de un lote (recepción + control de calidad + liquidación activa). */
    Map loteMap(Long recepcionId) {
        def rec = RecepcionDeComplejo.get(recepcionId)
        if (rec == null) return null
        def cc = ControlCalidadComplejo.findByRecepcionDeComplejo(rec)
        if (cc == null) return null   // requisito: el lote debe tener análisis de laboratorio
        def liq = liquidacionActiva(rec)

        [
            recepcionId     : recepcionId,
            liquidacionId   : liq?.id,
            lote            : rec.toString(),
            nombreEmpresa   : rec.empresa?.nombreDeEmpresa,
            departamento    : rec.empresa?.departamento,
            municipio       : rec.empresa?.municipio,
            proveedor       : rec.cliente?.nombre,
            fechaDeRecepcion: rec.fechaDeRecepcion,
            costoDeTransporte: rec.costoDeTransporte,
            costoManipuleo  : rec.costoManipuleo,
            pesoBruto       : rec.pesoBruto,
            humedad         : cc.porcentajeHumedadPromexbol,
            merma           : cc.porcentajeMermaPromexbol,
            leyZinc         : cc.porcentajeZincPromexbol,
            leyPlomo        : cc.porcentajePlomoPromexbol,
            leyPlata        : cc.porcentajePlataPromexbol,
            liquidado       : liq != null,
            valorNeto       : liq?.valorNetoMineralEnBolivianos,
            liquidoPagable  : liq?.totalLiquidoPagable,
            nombreComposito : rec.nombreComposito
        ]
    }

    // ── Persistencia (F4/F5): armar hija + participación + reserva ──────────

    /**
     * Recalcula totales AUTORITATIVOS (no confía en el cliente) y los escribe en la cabecera.
     * Devuelve el resumen (con lotes enriquecidos) para poblar los hijos tras guardar la cabecera.
     */
    Map armarComposito(composito, List<Long> recepcionIds) {
        def r = calcularPorRecepciones(recepcionIds)
        composito.totalKilosBrutos     = r.totalPesoBruto
        composito.totalKilosNetosSecos = r.totalKilosNetosSecos
        composito.leyPromedioZinc      = r.leyPromedioZinc
        composito.leyPromedioPlomo     = r.leyPromedioPlomo
        composito.leyPromedioPlata     = r.leyPromedioPlata
        composito.totalKilosFinosZinc  = r.totalKilosFinosZinc
        composito.totalKilosFinosPlomo = r.totalKilosFinosPlomo
        composito.totalKilosFinosPlata = r.totalKilosFinosPlata
        composito.totalValorNeto       = r.totalValorNeto
        composito.totalLiquidoPagable  = r.totalLiquidoPagable
        composito.totalValorDeCompra   = 0.0G
        r
    }

    /** Crea los CompositoDeLotesDetalle + CompositoLotesParticipacion (la cabecera ya debe estar guardada). */
    void poblarHijos(composito, Map resumen) {
        resumen.lotes.each { l ->
            new CompositoDeLotesDetalle(
                    reporteCompositoDeLotes: composito,
                    recepcionId: l.recepcionId,
                    liquidacionId: l.liquidacionId ?: 0,
                    fechaDeRecepcion: l.fechaDeRecepcion,
                    lote: l.lote,
                    nombreEmpresa: l.nombreEmpresa ?: '-',
                    departamento: l.departamento,
                    municipio: l.municipio,
                    proveedor: l.proveedor,
                    pesoBruto: l.pesoBruto,
                    porcentajeHumedad: l.humedad,
                    kilosNetosSecos: l.pns,
                    porcentajeZincFinal: l.leyZinc,      // Promexbol (D4)
                    porcentajePlomoFinal: l.leyPlomo,
                    porcentajePlataFinal: l.leyPlata,
                    kilosFinosZinc: l.kilosFinosZinc,
                    kilosFinosPlomo: l.kilosFinosPlomo,
                    kilosFinosPlata: l.kilosFinosPlata,
                    precioTonelada: 0,
                    valorOficialBruto: 0,
                    valorNetoMineralEnBolivianos: l.valorNeto ?: 0,
                    liquidoPagable: l.liquidoPagable ?: 0,
                    costoUnitarioTransporte: 0,
                    costoDeTransporte: l.costoDeTransporte ?: 0,
                    costoManipuleo: l.costoManipuleo ?: 0,
                    bonos: 0,
                    valorDeCompra: 0
            ).save(failOnError: true)
        }
        resumen.participacion.each { p ->
            new CompositoLotesParticipacion(
                    reporteCompositoDeLotes: composito,
                    nombreEmpresa: p.nombreEmpresa,
                    departamento: p.departamento,
                    municipio: p.municipio,
                    kilosNetosSecos: p.kilosNetosSecos,
                    porcentajeParticipacion: p.porcentajeParticipacion
            ).save(failOnError: true)
        }
    }

    /** Reserva los lotes marcando nombreComposito con la sigla (HQL, no .save(); ver gotcha). */
    void reservar(composito, List<Long> recepcionIds) {
        if (recepcionIds) RecepcionDeComplejo.executeUpdate(
                "update RecepcionDeComplejo set nombreComposito = :sigla where id in :ids",
                [sigla: composito.sigla, ids: recepcionIds])
    }

    /** Libera los lotes (nombreComposito = '-') que estaban reservados por esta sigla. */
    void liberar(List<Long> recepcionIds, String sigla) {
        if (recepcionIds) RecepcionDeComplejo.executeUpdate(
                "update RecepcionDeComplejo set nombreComposito = '-' where id in :ids and nombreComposito = :sigla",
                [ids: recepcionIds, sigla: sigla])
    }

    /** Liquidación de complejo ACTIVA (no anulada) del lote, o null. */
    private static LiquidacionDeComplejo liquidacionActiva(RecepcionDeComplejo rec) {
        LiquidacionDeComplejo.createCriteria().get {
            eq 'recepcionDeComplejo', rec
            or { eq 'anulado', false; isNull 'anulado' }
            maxResults 1
        } as LiquidacionDeComplejo
    }
}
