/*
 * Cálculo en vivo de la Liquidación de Complejos (Fase 4).
 * Espeja LiquidacionComplejoCalculoService.calcular() del backend (fuente única de verdad:
 * el backend recalcula al guardar; este JS es solo para UX inmediata).
 *
 * CONTRATO DE IDs (el form de la Fase 5 debe usar estos id; si alguno falta, se ignora):
 *  Inputs:  #pesoBruto #humedad #merma
 *           #leyZinc #leyPlomo #leyPlata
 *           #cotQuincenalZinc #cotQuincenalPlomo #cotQuincenalPlata
 *           #alicuotaZinc #alicuotaPlomo #alicuotaPlata
 *           #cotDiariaZinc #cotDiariaPlomo #cotDiariaPlata
 *           #tipoCambio  #modoVPT (MANUAL|TABLA|TERMINOS DE CONTRATO)  #vpt
 *           #tablaComplejoId  #terminosDeContratoId  #recepcionDeComplejoId (hidden)
 *           #bonoCalidad #bonoTransporte #bonoLealtad #bonoIncentivo
 *           #anticipoContraEntrega #anticipoContraFuturaEntrega #saldoAnterior
 *  Retenciones: tabla con filas <tr class="retencion-row" data-asignacion="VNV|VBV" data-unidad="%|Bs">
 *               con un input.retencion-cantidad; el monto va en .retencion-monto.
 *  Outputs (se escribe value si es input, si no textContent):
 *           #out_kilosNetosSecos #out_finosZinc ... #out_totalLiquidoPagable #out_precioCalculado
 *           (ver mapOutputs abajo)
 */
var LiquidacionComplejoCalc = (function () {
    'use strict';

    var LIBRAS_POR_TM = 2204.6223, GRAMOS_POR_OT = 31.1035;
    var CTX = '/demo-liquidaciones';

    function n(v) { var x = parseFloat(v); return isNaN(x) ? 0 : x; }
    function div(a, b) { return (!b) ? 0 : a / b; }

    // ── Núcleo PURO (idéntico al backend) ──────────────────────────────────
    function calcular(e) {
        var pb = n(e.pesoBruto), hum = n(e.humedad), merma = n(e.merma), tc = n(e.tipoCambio), vpt = n(e.vpt);

        var pns = pb * (1 - hum / 100) * (1 - merma / 100);

        var finosZn = pns * n(e.leyZinc) / 100,
            finosPb = pns * n(e.leyPlomo) / 100,
            finosAg = pns * n(e.leyPlata) / 10000;
        var lbZn = finosZn / 1000 * LIBRAS_POR_TM,
            lbPb = finosPb / 1000 * LIBRAS_POR_TM,
            otAg = finosAg * 1000 / GRAMOS_POR_OT;

        var vbvZnD = lbZn * n(e.cotQuincenalZinc), vbvPbD = lbPb * n(e.cotQuincenalPlomo), vbvAgD = otAg * n(e.cotQuincenalPlata);
        var vbvZnBs = vbvZnD * tc, vbvPbBs = vbvPbD * tc, vbvAgBs = vbvAgD * tc;
        var totalVbvD = vbvZnD + vbvPbD + vbvAgD, totalVbvBs = vbvZnBs + vbvPbBs + vbvAgBs;

        var rmZnD = vbvZnD * n(e.alicuotaZinc) / 100, rmPbD = vbvPbD * n(e.alicuotaPlomo) / 100, rmAgD = vbvAgD * n(e.alicuotaPlata) / 100;
        var rmZnBs = rmZnD * tc, rmPbBs = rmPbD * tc, rmAgBs = rmAgD * tc;
        var totalRmD = rmZnD + rmPbD + rmAgD, totalRmBs = rmZnBs + rmPbBs + rmAgBs;

        var vvDiariaD = lbZn * n(e.cotDiariaZinc) + lbPb * n(e.cotDiariaPlomo) + otAg * n(e.cotDiariaPlata);
        var vvDiariaBs = vvDiariaD * tc;

        var vnvD = vpt * pns / 1000, vnvBs = vnvD * tc;

        var sacos = n(e.cantidadSacos);
        var totalOtras = 0;
        (e.retenciones || []).forEach(function (r) {
            var cant = n(r.cantidad), monto;
            switch (r.asignacion) {
                case 'VBV':        monto = (r.unidad === 'Bs') ? cant : totalVbvBs * cant / 100; break;
                case 'SACO':       monto = cant * sacos; break;
                case 'TON. BRUTA': monto = cant * pb / 1000; break;
                case 'FIJO':       monto = cant; break;
                default:           monto = (r.unidad === 'Bs') ? cant : vnvBs * cant / 100; break;
            }
            r.monto = monto;
            totalOtras += monto;
        });
        var totalDeducciones = totalRmBs + totalOtras;

        var valorPagable = vnvBs - totalDeducciones;
        var totalBonos = n(e.bonoCalidad) + n(e.bonoTransporte) + n(e.bonoLealtad) + n(e.bonoIncentivo);
        // saldoAnterior es informativo (no se descuenta); el cobro va por anticipoContraFuturaEntrega
        var totalAnticipos = n(e.anticipoContraEntrega) + n(e.anticipoContraFuturaEntrega);
        var liquido = valorPagable + totalBonos - totalAnticipos;
        var precio = div(div(liquido, tc), pns) * 1000;

        return {
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
            totalDeducciones: totalDeducciones, valorPagable: valorPagable,
            totalBonos: totalBonos, totalAnticipos: totalAnticipos,
            liquidoPagable: liquido, precioCalculado: precio
        };
    }

    // ── Capa DOM ───────────────────────────────────────────────────────────
    function $id(id) { return document.getElementById(id); }
    function valOf(id) { var el = $id(id); return el ? el.value : null; }
    function fmt(x) { return (isFinite(x) ? x : 0).toLocaleString('en-US', { maximumFractionDigits: 2 }); }

    function setOut(id, val) {
        var el = $id('out_' + id) || $id(id);
        if (!el) return;
        if (el.tagName === 'INPUT' || el.tagName === 'TEXTAREA') el.value = fmt(val);
        else el.textContent = fmt(val);
    }

    function leerRetenciones() {
        var filas = document.querySelectorAll('.retencion-row');
        return Array.prototype.map.call(filas, function (tr) {
            var inp = tr.querySelector('.retencion-cantidad');
            return { asignacion: tr.getAttribute('data-asignacion'), unidad: tr.getAttribute('data-unidad'),
                     cantidad: inp ? inp.value : 0, _tr: tr };
        });
    }

    function leerInputs() {
        var rets = leerRetenciones();
        return {
            pesoBruto: valOf('pesoBruto'), humedad: valOf('humedad'), merma: valOf('merma'),
            leyZinc: valOf('leyZinc'), leyPlomo: valOf('leyPlomo'), leyPlata: valOf('leyPlata'),
            cotQuincenalZinc: valOf('cotQuincenalZinc'), cotQuincenalPlomo: valOf('cotQuincenalPlomo'), cotQuincenalPlata: valOf('cotQuincenalPlata'),
            alicuotaZinc: valOf('alicuotaZinc'), alicuotaPlomo: valOf('alicuotaPlomo'), alicuotaPlata: valOf('alicuotaPlata'),
            cotDiariaZinc: valOf('cotDiariaZinc'), cotDiariaPlomo: valOf('cotDiariaPlomo'), cotDiariaPlata: valOf('cotDiariaPlata'),
            tipoCambio: valOf('tipoCambio'), vpt: valOf('vpt'), cantidadSacos: valOf('cantidadSacos'),
            bonoCalidad: valOf('bonoCalidad'), bonoTransporte: valOf('bonoTransporte'), bonoLealtad: valOf('bonoLealtad'), bonoIncentivo: valOf('bonoIncentivo'),
            anticipoContraEntrega: valOf('anticipoContraEntrega'), anticipoContraFuturaEntrega: valOf('anticipoContraFuturaEntrega'), saldoAnterior: valOf('saldoAnterior'),
            retenciones: rets
        };
    }

    var OUTPUTS = ['kilosNetosSecos','finosZinc','finosPlomo','finosPlata','librasFinasZinc','librasFinasPlomo','onzasTroyPlata',
        'vbvZincDolares','vbvPlomoDolares','vbvPlataDolares','vbvZincBolivianos','vbvPlomoBolivianos','vbvPlataBolivianos',
        'totalVbvDolares','totalVbvBolivianos','rmZincDolares','rmPlomoDolares','rmPlataDolares',
        'rmZincBolivianos','rmPlomoBolivianos','rmPlataBolivianos','totalRmDolares','totalRmBolivianos',
        'valorVentaDiariaDolares','valorVentaDiariaBolivianos','vnvDolares','vnvBolivianos',
        'totalDeducciones','valorPagable','totalBonos','totalAnticipos','liquidoPagable','precioCalculado'];

    function recalcular() {
        var inputs = leerInputs();
        var r = calcular(inputs);
        OUTPUTS.forEach(function (k) { setOut(k, r[k]); });
        // regalía minera total en la tabla de deducciones (id propio para no chocar con el de la tabla VBV/RM)
        var rgDed = $id('out_regaliaDeducciones');
        if (rgDed) rgDed.textContent = fmt(r.totalRmBolivianos);
        // montos por fila de retención
        inputs.retenciones.forEach(function (ret) {
            var cell = ret._tr.querySelector('.retencion-monto');
            if (cell) cell.textContent = fmt(ret.monto);
        });
        // resaltar líquido pagable según signo
        var box = $id('liquidoBox');
        if (box) {
            var neg = r.liquidoPagable < 0;
            box.classList.toggle('liquido-negativo', neg);
            box.classList.toggle('liquido-positivo', !neg);
        }
        return r;
    }

    // ── VPT (manual / automático por tablas o contrato) ─────────────────────
    function aplicarModoVPT() {
        var modo = valOf('modoVPT');
        var vptInput = $id('vpt');
        var manual = (modo === 'MANUAL' || !modo);
        if (vptInput) vptInput.readOnly = !manual;
        // Mostrar solo el select correspondiente al modo (con su botón Calcular VPT)
        var rowTabla = $id('rowTabla'), rowTermino = $id('rowTermino');
        if (rowTabla) rowTabla.style.display = (modo === 'TABLA') ? '' : 'none';
        if (rowTermino) rowTermino.style.display = (modo === 'TERMINOS DE CONTRATO') ? '' : 'none';
        if (!manual) calcularVPTAutomatico();
        else recalcular();
    }

    function spinnerVPT(on) {
        var s = $id('vptSpinner');
        if (s) s.style.display = on ? '' : 'none';
    }

    function setVptCero() {
        if ($id('vpt')) $id('vpt').value = 0;
        recalcular();
    }

    function calcularVPTAutomatico() {
        var modo = valOf('modoVPT');
        if (!window.jQuery) return;
        var url, data = {
            recepcionDeComplejoId: valOf('recepcionDeComplejoId'),
            leyZinc: valOf('leyZinc'), leyPlomo: valOf('leyPlomo'), leyPlata: valOf('leyPlata')
        };
        if (modo === 'TABLA') {
            data.tablaId = valOf('tablaComplejoId');
            if (!data.tablaId) { setVptCero(); return; }     // (ninguna) seleccionada → VPT 0
            url = CTX + '/tablaOrigenCotizacionesComplejo/calcularVPT';
        } else if (modo === 'TERMINOS DE CONTRATO') {
            data.terminoId = valOf('terminosDeContratoId');
            if (!data.terminoId) { setVptCero(); return; }   // (ninguno) seleccionado → VPT 0
            url = CTX + '/terminosDeContrato/calcularVPT';
        } else { recalcular(); return; }

        spinnerVPT(true);
        jQuery.getJSON(url, data).done(function (resp) {
            if ($id('vpt') && resp && resp.vpt != null) $id('vpt').value = resp.vpt;
            recalcular();
        }).fail(function () { recalcular(); }).always(function () { spinnerVPT(false); });
    }

    // ── Init: enlaza eventos ────────────────────────────────────────────────
    function init() {
        // Recalcular ante cualquier cambio de input/select (delegado en document, porque el
        // layout tiene un <form> de logout en el navbar que sería el "primer form").
        document.addEventListener('input', function (e) {
            recalcular();
            // Si cambian las leyes finales (o las del cliente que las promedian) y el VPT viene
            // de una fuente automática, recalcular también el VPT.
            if (e.target && (e.target.classList.contains('ley-final') || e.target.classList.contains('ley-cliente'))) {
                var m = valOf('modoVPT');
                if (m === 'TABLA' || m === 'TERMINOS DE CONTRATO') calcularVPTAutomatico();
            }
        });
        document.addEventListener('change', function () { recalcular(); });
        var modo = $id('modoVPT');
        if (modo) modo.addEventListener('change', aplicarModoVPT);
        // Calcular VPT automáticamente al elegir tabla/término (sin depender del botón)
        var selTabla = $id('tablaComplejoId');
        if (selTabla) selTabla.addEventListener('change', calcularVPTAutomatico);
        var selTermino = $id('terminosDeContratoId');
        if (selTermino) selTermino.addEventListener('change', calcularVPTAutomatico);

        aplicarModoVPT();   // estado inicial (también recalcula)
    }

    if (document.readyState !== 'loading') init();
    else document.addEventListener('DOMContentLoaded', init);

    return { calcular: calcular, recalcular: recalcular, calcularVPTAutomatico: calcularVPTAutomatico };
})();
