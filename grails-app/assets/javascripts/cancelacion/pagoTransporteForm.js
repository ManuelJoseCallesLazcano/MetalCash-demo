$(document).ready(function () {

    window.disponible = 0;
    var lotesData = [];

    // Select2 en el selector de automovil (lista acotada, búsqueda del lado del cliente)
    if ($.fn.select2) {
        $('#automovil').select2({ language: 'es', width: '100%', placeholder: '-SELECCIONE-' });
    }

    // Auto-foco en el cuadro de búsqueda al abrir cualquier Select2
    $(document).on('select2:open', function () {
        var campo = document.querySelector('.select2-container--open .select2-search__field');
        if (campo) campo.focus();
    });

    // Búsqueda asíncrona de CI sobre el ledger de transporte (EstadoCuentaTransporte); al elegir
    // un CI existente se autocompleta el nombre del cobrador. tags:true permite un CI nuevo.
    if ($('#ciSelect').length && $.fn.select2) {
        $('#ciSelect').select2({
            placeholder: 'Buscar CI o escribir uno nuevo…',
            language: 'es', width: '100%',
            minimumInputLength: 1,
            tags: true,
            ajax: {
                url: '/demo-liquidaciones/estadoCuentaTransporte/cobradorBusquedaJSON',
                dataType: 'json', delay: 300,
                data: function (p) { return { q: p.term }; },
                processResults: function (d) { return { results: d.results }; },
                cache: true
            }
        });
        // El propio <select name="ci"> envía su valor (existente o tag nuevo); aquí solo
        // autocompletamos el nombre del cobrador cuando el CI viene de un registro existente.
        $('#ciSelect').on('select2:select', function (e) {
            var d = e.params.data;
            if (d.nombreCobrador) $('#nombreCobrador').val(d.nombreCobrador);
        });
    }

    function formatNumero(v) {
        var n = parseFloat(v);
        if (isNaN(n)) return '';
        return n.toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
    }

    function transFloat(v) {
        return parseFloat(('' + (v == null ? '' : v)).replace(/,/g, ''));
    }

    // ── Disponible del automovil (ledger de transporte) ─────────────────────
    function recuperarDisponible(recalcularDespues) {
        var automovilId = $('#automovil').val();
        if (!automovilId) {
            window.disponible = 0;
            $('#disponibleDisplay').val('0.00');
            if (recalcularDespues) proponerAplicadoMaximo();
            return;
        }
        $.ajax({
            url: '/demo-liquidaciones/estadoCuentaTransporte/obtenerSaldoJSON',
            dataType: 'json',
            data: { automovilId: automovilId },
            success: function (data) {
                window.disponible = transFloat(data.saldoCuenta) || 0;
                $('#disponibleDisplay').val(formatNumero(window.disponible));
                if (window.$ && $.notify) {
                    $.notify(data.notificacion, { autoHide: true, clickToHide: true, className: 'info' });
                }
                if (recalcularDespues) proponerAplicadoMaximo();
            }
        });
    }

    // ── Tabla de lotes (Bootstrap, sin jqGrid) ──────────────────────────────
    function renderLotes() {
        var $body = $('#lotesAsignadosBody').empty();
        if (!lotesData.length) {
            $body.append('<tr><td colspan="6" class="text-center text-muted py-3">No hay lotes. Elija un automóvil y presione BUSCAR LOTES.</td></tr>');
            actualizarTotalesTabla();
            return;
        }
        lotesData.forEach(function (l, i) {
            var $tr = $('<tr>').attr('data-index', i);
            $tr.append($('<td>').text(l.lote));
            $tr.append($('<td>').text(l.fechaDeRecepcion));
            $tr.append($('<td>').text(l.tipoDeMaterial));
            $tr.append($('<td class="text-right">').text(formatNumero(l.pesoBruto)));
            $tr.append($('<td class="text-right">').text(formatNumero(l.costoDeTransporte)));
            $tr.append($('<td class="text-center">').html(
                '<button type="button" class="btn btn-outline-danger btn-sm btn-quitar-lote" title="Quitar"><i class="fas fa-times"></i></button>'));
            $body.append($tr);
        });
        actualizarTotalesTabla();
    }

    // Fila de totales de la tabla de lotes: Σ peso bruto y Σ costo de transporte.
    function sumaPesoBruto() {
        return lotesData.reduce(function (acc, l) {
            var p = transFloat(l.pesoBruto);
            return acc + (isNaN(p) ? 0 : p);
        }, 0);
    }

    function actualizarTotalesTabla() {
        $('#totalPesoBruto').text(formatNumero(sumaPesoBruto()));
        $('#totalCostoTransporte').text(formatNumero(sumaCosto()));
    }

    function sincronizarLotes() {
        $('#lotes').val(lotesData.length ? JSON.stringify(lotesData) : '');
        $('#descripcion').val('POR LOTES: ' + lotesData.map(function (l) { return l.lote; }).join(', '));
    }

    function sumaCosto() {
        return lotesData.reduce(function (acc, l) {
            var c = transFloat(l.costoDeTransporte);
            return acc + (isNaN(c) ? 0 : c);
        }, 0);
    }

    function buscarLotes() {
        var automovilId = $('#automovil').val();
        if (!automovilId) { alert('Seleccione un automóvil primero.'); return; }
        $.ajax({
            url: '/demo-liquidaciones/recepcionDeComplejo/recepcionesTransporteJSON',
            dataType: 'json',
            data: { solicitante: 'Particular', automovilId: automovilId },
            success: function (data) {
                try { lotesData = data.lotes ? JSON.parse(data.lotes) : []; }
                catch (e) { lotesData = []; }
                renderLotes();
                sincronizarLotes();
                // Total por defecto = Σ costoDeTransporte (editable por el cajero)
                $('#total').val(sumaCosto().toFixed(2));
                proponerAplicadoMaximo();
            }
        });
    }

    $('#agregar').on('click', buscarLotes);

    $('#lotesAsignadosBody').on('click', '.btn-quitar-lote', function () {
        var idx = $(this).closest('tr').data('index');
        lotesData.splice(idx, 1);
        renderLotes();
        sincronizarLotes();
        $('#total').val(sumaCosto().toFixed(2));
        proponerAplicadoMaximo();
    });

    // ── Cálculo del pago ────────────────────────────────────────────────────
    // Tope del anticipo aplicable = min(total, disponible del automóvil).
    function topeAplicable() {
        var total = transFloat($('#total').val());
        if (isNaN(total)) total = 0;
        var t = Math.min(total, window.disponible || 0);
        return t < 0 ? 0 : t;
    }

    // Recalcula SOLO Total Pagable desde los valores actuales, usando el anticipo aplicado
    // clampeado a [0, tope] únicamente para el cálculo. NO reescribe #totalAnticipos, para
    // permitir borrar y escribir el campo con comodidad mientras se edita.
    function actualizarPagable() {
        var total = transFloat($('#total').val());
        if (isNaN(total)) total = 0;
        var mx = topeAplicable();
        var aplicado = transFloat($('#totalAnticipos').val());
        if (isNaN(aplicado)) aplicado = 0;
        if (aplicado < 0) aplicado = 0;
        if (aplicado > mx) aplicado = mx;
        var pagable = total - aplicado;
        $('#totalPagable').val(pagable.toFixed(2));
        actualizarLiteral(pagable);
    }

    // Normaliza el Anticipo Aplicado al salir del campo (blur) o al cambiar el Total:
    // clampea a [0, min(total, disponible)] y formatea a 2 decimales.
    function normalizarAplicado() {
        var mx = topeAplicable();
        var aplicado = transFloat($('#totalAnticipos').val());
        if (isNaN(aplicado)) aplicado = 0;
        if (aplicado < 0) aplicado = 0;
        if (aplicado > mx) aplicado = mx;
        $('#totalAnticipos').val(aplicado.toFixed(2));
        actualizarPagable();
    }

    // Propone por defecto el anticipo aplicado máximo (min(total, disponible)) y recalcula.
    function proponerAplicadoMaximo() {
        $('#totalAnticipos').val(topeAplicable().toFixed(2));
        actualizarPagable();
    }

    function actualizarLiteral(monto) {
        if (typeof CifrasEnLetras === 'undefined') return;
        var entero = '' + parseInt(monto || 0);
        var n = Math.round((monto * 100) % 100);
        var decimal = ('' + n).length < 2 ? '0' + n : '' + n;
        if (monto >= 1000 && monto < 2000)
            $('#totalPagableLiteral').val('UN ' + CifrasEnLetras.convertirNumeroEnLetras(entero).toUpperCase() + ' ' + decimal + '/100 BOLIVIANOS');
        else
            $('#totalPagableLiteral').val(CifrasEnLetras.convertirNumeroEnLetras(entero).toUpperCase() + ' ' + decimal + '/100 BOLIVIANOS');
    }

    // Total editable: al cambiar, re-clampea el anticipo aplicado a su nuevo tope y recalcula.
    $('#total').on('input keyup', normalizarAplicado);
    // Anticipo Aplicado editable: mientras se escribe solo recalcula el pagable (permite borrar
    // el campo por completo); al salir del campo (blur) se clampea y formatea a 2 decimales.
    $('#totalAnticipos').on('input', actualizarPagable);
    $('#totalAnticipos').on('blur', normalizarAplicado);
    $('#automovil').on('change', function () { recuperarDisponible(true); });

    // Estado inicial
    (function init() {
        var raw = $('#lotes').val();
        try { lotesData = raw ? JSON.parse(raw) : []; } catch (e) { lotesData = []; }
        renderLotes();
        recuperarDisponible(true);
    })();
});
