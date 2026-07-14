/*
 * Conformación de Conjuntos de Lotes (Compósitos) — Fase 4.
 * Cálculo en vivo del resumen (espeja CompositoCalculoService del backend, que recalcula de forma
 * AUTORITATIVA al guardar). No engancha document.querySelector('form') (el navbar tiene logoutForm):
 * usa el form por id (#compositoForm) y delegación en document.
 *
 * CONTRATO DE IDs:
 *  URLs (inyectadas por la vista):  window.COMPOSITO_URLS = { disponibles, empresa }
 *  Preselección (edit):             window.COMPOSITO_PRESELECCION = [ {fila de lote}, ... ]
 *  Filtros:  #empresa (select2, name=empresa.id)  #leyMinimaZinc #leyMaximaZinc #leyMinimaPlomo
 *            #leyMaximaPlomo #leyMinimaPlata #leyMaximaPlata  #f_lote
 *            #f_fechaInicial_picker #f_fechaFinal_picker (g:datepickerUI, dd/mm/yyyy)
 *            #ordenarElemento (ZINC|PLOMO|PLATA)  #f_orden (asc|desc)  #btnBuscar
 *  Tablas:   #tablaDisponibles tbody, #tablaConjunto tbody
 *  Cabecera: #destino  #comprador (select2)  #ingenio (select2)  #_comprador #_ingenio (contenedores)
 *  Outputs:  #out_totalPesoBruto #out_totalPNS #out_finosZinc #out_finosPlomo #out_finosPlata
 *            #out_leyZinc #out_leyPlomo #out_leyPlata #out_humedad #out_valorNeto #out_liquido
 *            #out_cantLotes #out_cantNoLiq   (y en el pie de #tablaConjunto: #pie_*)
 *  Submit:   #recepcionIds (hidden)  #btnRegistrar  (form #compositoForm)
 */
(function () {
    'use strict';

    var conjunto = {};   // recepcionId -> fila

    function n(v) { var x = parseFloat(v); return isNaN(x) ? 0 : x; }
    function div(a, b) { return (!b) ? 0 : a / b; }
    function fmt(v) { return (isFinite(v) ? v : 0).toLocaleString('es-BO', { minimumFractionDigits: 2, maximumFractionDigits: 2 }); }
    function val(sel) { var el = document.querySelector(sel); return el ? el.value : ''; }

    // ── Resumen en vivo (suma de valores por lote ya calculados por el backend) ──
    function recompute() {
        var arr = Object.keys(conjunto).map(function (k) { return conjunto[k]; });
        var totPB = 0, totPNS = 0, totPNH = 0, kfZn = 0, kfPb = 0, kfAg = 0, valor = 0, liquido = 0, liq = 0;
        arr.forEach(function (e) {
            var pns = n(e.kilosNetosSecos), hum = n(e.porcentajeHumedad);
            var pnh = (hum >= 100) ? pns : div(pns, (1 - hum / 100));
            totPB += n(e.pesoBruto); totPNS += pns; totPNH += pnh;
            kfZn += n(e.kilosFinosZinc); kfPb += n(e.kilosFinosPlomo); kfAg += n(e.kilosFinosPlata);
            if (e.liquidado) { valor += n(e.valorNeto); liquido += n(e.liquidoPagable); liq++; }
        });
        var leyZn = div(kfZn, totPNS) * 100, leyPb = div(kfPb, totPNS) * 100, leyAg = div(kfAg, totPNS) * 10000;
        var humedad = (totPNH === 0) ? 0 : div(totPNH - totPNS, totPNH) * 100;

        put('#out_totalPesoBruto', fmt(totPB)); put('#pie_pesoBruto', fmt(totPB));
        put('#out_totalPNS', fmt(totPNS));      put('#pie_pns', fmt(totPNS));
        put('#out_finosZinc', fmt(kfZn));       put('#pie_finosZinc', fmt(kfZn));
        put('#out_finosPlomo', fmt(kfPb));      put('#pie_finosPlomo', fmt(kfPb));
        put('#out_finosPlata', fmt(kfAg));      put('#pie_finosPlata', fmt(kfAg));
        put('#out_leyZinc', fmt(leyZn));        put('#pie_leyZinc', fmt(leyZn));
        put('#out_leyPlomo', fmt(leyPb));       put('#pie_leyPlomo', fmt(leyPb));
        put('#out_leyPlata', fmt(leyAg));       put('#pie_leyPlata', fmt(leyAg));
        put('#out_humedad', fmt(humedad));      put('#pie_humedad', fmt(humedad));
        put('#out_valorNeto', fmt(valor));      put('#pie_valorNeto', fmt(valor));
        put('#out_liquido', fmt(liquido));      put('#pie_liquido', fmt(liquido));
        put('#out_cantLotes', arr.length);
        put('#out_cantNoLiq', arr.length - liq);

        var hidden = document.getElementById('recepcionIds');
        if (hidden) hidden.value = Object.keys(conjunto).join(',');
    }

    function put(sel, txt) {
        var el = document.querySelector(sel);
        if (!el) return;
        if (el.tagName === 'INPUT') el.value = txt; else el.textContent = txt;
    }

    // ── Render de tablas ──
    function celda(v, num) { return '<td class="' + (num ? 'text-right' : '') + '">' + (v == null ? '' : v) + '</td>'; }

    function filaHTML(l, enConjunto) {
        var estado = l.liquidado
            ? '<span class="badge badge-success">LIQUIDADO</span>'
            : '<span class="badge badge-secondary">NO LIQ.</span>';
        var btn = enConjunto
            ? '<button type="button" class="btn btn-xs btn-danger btn-quitar" data-id="' + l.recepcionId + '"><i class="fas fa-minus"></i></button>'
            : '<button type="button" class="btn btn-xs btn-primary btn-agregar" data-id="' + l.recepcionId + '"><i class="fas fa-plus"></i></button>';
        return '<tr data-id="' + l.recepcionId + '">' +
            celda(l.lote) + celda(l.nombreEmpresa) + celda(l.fechaDeRecepcion) +
            celda(fmt(l.pesoBruto), true) + celda(fmt(l.porcentajeHumedad), true) +
            celda(fmt(l.kilosNetosSecos), true) +
            celda(fmt(l.leyZinc), true) + celda(fmt(l.leyPlomo), true) + celda(fmt(l.leyPlata), true) +
            '<td class="text-center">' + estado + '</td>' +
            celda(l.liquidado ? fmt(l.valorNeto) : '—', true) +
            celda(l.liquidado ? fmt(l.liquidoPagable) : '—', true) +
            '<td class="text-center">' + btn + '</td>' +
            '</tr>';
    }

    function renderDisponibles(lotes) {
        var tb = document.querySelector('#tablaDisponibles tbody');
        if (!tb) return;
        var visibles = lotes.filter(function (l) { return !conjunto[l.recepcionId]; });
        tb.innerHTML = visibles.length
            ? visibles.map(function (l) { return filaHTML(l, false); }).join('')
            : '<tr><td colspan="13" class="text-center text-muted py-2">Sin lotes disponibles para el filtro.</td></tr>';
    }

    function renderConjunto() {
        var tb = document.querySelector('#tablaConjunto tbody');
        if (!tb) return;
        var arr = Object.keys(conjunto).map(function (k) { return conjunto[k]; });
        tb.innerHTML = arr.length
            ? arr.map(function (l) { return filaHTML(l, true); }).join('')
            : '<tr><td colspan="13" class="text-center text-muted py-2">Aún no hay lotes en el compósito.</td></tr>';
    }

    // ── Datos de una fila desde el DOM de disponibles (para no re-consultar) ──
    var cacheDisponibles = {};   // recepcionId -> fila

    function buscar() {
        var u = window.COMPOSITO_URLS && window.COMPOSITO_URLS.disponibles;
        if (!u) return;
        var p = {
            empresaId: val('#empresa') || 'null',
            leyMinZinc: val('#leyMinimaZinc'), leyMaxZinc: val('#leyMaximaZinc'),
            leyMinPlomo: val('#leyMinimaPlomo'), leyMaxPlomo: val('#leyMaximaPlomo'),
            leyMinPlata: val('#leyMinimaPlata'), leyMaxPlata: val('#leyMaximaPlata'),
            lote: val('#f_lote'),
            fechaInicial: val('#f_fechaInicial_picker'),   // jQuery UI datepicker: ya en dd/mm/yyyy
            fechaFinal: val('#f_fechaFinal_picker'),
            ordenarPor: val('#ordenarElemento'),
            orden: val('#f_orden')
        };
        $.getJSON(u, p, function (r) {
            cacheDisponibles = {};
            (r.lotes || []).forEach(function (l) { cacheDisponibles[l.recepcionId] = l; });
            renderDisponibles(r.lotes || []);
        }).fail(function () {
            if (window.Swal) Swal.fire({ icon: 'error', title: 'Error', text: 'No se pudieron cargar los lotes.' });
        });
    }

    function agregar(id) {
        var l = cacheDisponibles[id];
        if (!l) return;
        conjunto[id] = l;
        var fila = document.querySelector('#tablaDisponibles tbody tr[data-id="' + id + '"]');
        if (fila) fila.parentNode.removeChild(fila);
        renderConjunto(); recompute();
    }

    function quitar(id) {
        delete conjunto[id];
        renderConjunto(); recompute();
        // reinsertar en disponibles si sigue en caché del último filtro
        if (cacheDisponibles[id]) {
            var tb = document.querySelector('#tablaDisponibles tbody');
            if (tb) tb.insertAdjacentHTML('afterbegin', filaHTML(cacheDisponibles[id], false));
        }
    }

    // ── Cabecera: toggle comprador/ingenio según destino ──
    function toggleDestino() {
        var d = val('#destino');
        var comp = document.getElementById('_comprador');
        var ing = document.getElementById('_ingenio');
        if (comp) comp.style.display = (d === 'INGENIO') ? 'none' : '';
        if (ing) ing.style.display = (d === 'INGENIO') ? '' : 'none';
    }

    // Advertencia al marcar el compósito como DEFINITIVO (queda inmutable). Si el usuario
    // cancela, se revierte a PROVISIONAL.
    function avisarDefinitivo() {
        var sel = document.getElementById('estadoDelComposito');
        if (!sel || sel.value !== 'DEFINITIVO') return;
        if (!window.Swal) return;
        Swal.fire({
            icon: 'warning',
            title: '¿Marcar como DEFINITIVO?',
            html: 'Un compósito <b>DEFINITIVO</b> no se puede modificar: no podrá agregar ni quitar lotes ' +
                  'ni editar sus datos. Para volver a editarlo habría que reabrirlo (solo administradores).',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            confirmButtonText: 'Sí, dejarlo definitivo',
            cancelButtonText: 'Cancelar',
            reverseButtons: true
        }).then(function (r) {
            if (!r.isConfirmed) sel.value = 'PROVISIONAL';
        });
    }

    function registrar() {
        if (Object.keys(conjunto).length === 0) {
            if (window.Swal) Swal.fire({ icon: 'warning', title: 'Sin lotes', text: 'Agregue al menos un lote al compósito.' });
            return;
        }
        recompute();
        var form = document.getElementById('compositoForm');
        if (form) form.submit();
    }

    // ── Init ──
    document.addEventListener('DOMContentLoaded', function () {
        // Select2
        if (window.jQuery && $.fn.select2) {
            $('#comprador, #ingenio').select2({ language: 'es', width: '100%' });
            var urlEmp = window.COMPOSITO_URLS && window.COMPOSITO_URLS.empresa;
            if (urlEmp) {
                $('#empresa').select2({
                    language: 'es', width: '100%', allowClear: true, placeholder: '-TODAS-',
                    ajax: { url: urlEmp, dataType: 'json', delay: 250, minimumInputLength: 1,
                        data: function (params) { return { q: params.term }; },
                        processResults: function (data) { return { results: data.results }; } }
                });
            } else {
                $('#empresa').select2({ language: 'es', width: '100%', allowClear: true });
            }
            $(document).on('select2:open', function () {
                var c = document.querySelector('.select2-container--open .select2-search__field');
                if (c) c.focus();
            });
        }

        var d = document.getElementById('destino');
        if (d) d.addEventListener('change', toggleDestino);
        toggleDestino();

        var est = document.getElementById('estadoDelComposito');
        if (est) est.addEventListener('change', avisarDefinitivo);

        var b = document.getElementById('btnBuscar');
        if (b) b.addEventListener('click', buscar);
        var reg = document.getElementById('btnRegistrar');
        if (reg) reg.addEventListener('click', registrar);

        // Delegación agregar/quitar
        document.addEventListener('click', function (ev) {
            var a = ev.target.closest ? ev.target.closest('.btn-agregar') : null;
            var q = ev.target.closest ? ev.target.closest('.btn-quitar') : null;
            if (a) { agregar(a.getAttribute('data-id')); }
            else if (q) { quitar(q.getAttribute('data-id')); }
        });

        // Preselección (edit)
        if (Array.isArray(window.COMPOSITO_PRESELECCION)) {
            window.COMPOSITO_PRESELECCION.forEach(function (l) { conjunto[l.recepcionId] = l; cacheDisponibles[l.recepcionId] = l; });
        }
        renderConjunto();
        recompute();
        buscar();
    });
})();
