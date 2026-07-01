// Cálculo en vivo de la cotización en tonelada y del VPT por fila en las tablas de precios (Zn/Pb/Ag).
(function () {
    var LIBRAS = 2204.6223, OT = 31.1035;

    function $(s, c) { return (c || document).querySelector(s); }
    function $all(s, c) { return Array.prototype.slice.call((c || document).querySelectorAll(s)); }
    function num(v) { var n = parseFloat(v); return isNaN(n) ? 0 : n; }
    function fmt(n) { return (isFinite(n) ? n : 0).toLocaleString('es-BO', { minimumFractionDigits: 2, maximumFractionDigits: 2 }); }

    function cotSel() {
        var sel = $('#cotId');
        if (!sel) return { zinc: 0, plomo: 0, plata: 0 };
        var o = sel.options[sel.selectedIndex];
        if (!o || !o.value) return { zinc: 0, plomo: 0, plata: 0 };
        return {
            zinc: num(o.getAttribute('data-zinc')),
            plomo: num(o.getAttribute('data-plomo')),
            plata: num(o.getAttribute('data-plata'))
        };
    }

    function cotTon(elem, cot) {
        if (elem === 'PLATA') return cot.plata * 1000 * 1000 / OT;
        return (elem === 'ZINC' ? cot.zinc : cot.plomo) * LIBRAS;
    }

    function suf(elem) { return elem.charAt(0) + elem.slice(1).toLowerCase(); }   // ZINC -> Zinc

    function calcRow(row, elem, ct) {
        var ley = num($('.punto-ley', row).value);
        var pag = num($('.punto-pag', row).value);
        var factor = (elem === 'PLATA') ? ley / 10000 : ley / 100;
        var cell = $('.punto-vpt', row);
        if (cell) cell.textContent = fmt(ct * factor * pag / 100);
    }

    function recalc() {
        var cot = cotSel();
        if ($('#cotTonZinc')) $('#cotTonZinc').value = fmt(cotTon('ZINC', cot));
        if ($('#cotTonPlomo')) $('#cotTonPlomo').value = fmt(cotTon('PLOMO', cot));
        if ($('#cotTonPlata')) $('#cotTonPlata').value = fmt(cotTon('PLATA', cot));
        $all('.tabla-puntos').forEach(function (tb) {
            var elem = tb.getAttribute('data-elemento');
            var ct = cotTon(elem, cot);
            $all('.punto-row', tb).forEach(function (r) { calcRow(r, elem, ct); });
        });
    }

    function nuevaFila(elem) {
        var s = suf(elem);
        var tr = document.createElement('tr');
        tr.className = 'punto-row';
        tr.innerHTML =
            '<td><input type="number" step="any" name="ley' + s + '" class="form-control form-control-sm text-right punto-ley"/></td>' +
            '<td><input type="number" step="any" name="pag' + s + '" class="form-control form-control-sm text-right punto-pag"/></td>' +
            '<td class="text-right punto-vpt"></td>' +
            '<td class="text-center"><button type="button" class="btn btn-sm btn-outline-danger btn-quitar-punto"><i class="fas fa-times"></i></button></td>';
        return tr;
    }

    document.addEventListener('change', function (e) {
        if (e.target && e.target.id === 'cotId') recalc();
    });
    document.addEventListener('input', function (e) {
        if (e.target && (e.target.classList.contains('punto-ley') || e.target.classList.contains('punto-pag'))) recalc();
    });
    document.addEventListener('click', function (e) {
        var add = e.target.closest && e.target.closest('.btn-add-punto');
        if (add) {
            var tb = document.getElementById(add.getAttribute('data-tabla'));
            tb.querySelector('tbody').appendChild(nuevaFila(add.getAttribute('data-elemento')));
            recalc();
            return;
        }
        var quit = e.target.closest && e.target.closest('.btn-quitar-punto');
        if (quit) {
            var row = quit.closest('.punto-row');
            if (row) row.parentNode.removeChild(row);
            recalc();
        }
    });

    if (document.readyState !== 'loading') recalc();
    else document.addEventListener('DOMContentLoaded', recalc);
})();
