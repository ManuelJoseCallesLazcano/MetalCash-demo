$(document).ready(function () {

    // Formato numérico (mismo estilo que <g:formatNumber type="number" maxFractionDigits="2"/>;
    // locale de la app = Locale.US → separador de miles "," y decimal ".")
    function formatNumero(v) {
        var n = parseFloat(v);
        if (isNaN(n)) return (v != null ? v : '');
        return n.toLocaleString('en-US', { maximumFractionDigits: 2 });
    }

    // Auto-foco en el cuadro de búsqueda al abrir cualquier Select2
    $(document).on('select2:open', function () {
        var campo = document.querySelector('.select2-container--open .select2-search__field');
        if (campo) campo.focus();
    });

    // ══════════════════════════════════════════════════════════════════════
    // BÚSQUEDA DE CLIENTE POR NOMBRE/CI (Select2, igual que RecepcionDeComplejo)
    // ══════════════════════════════════════════════════════════════════════
    if ($('#clienteSelect').length && $.fn.select2) {
        $('#clienteSelect').select2({
            placeholder: 'Buscar por nombre o CI…',
            allowClear: true,
            minimumInputLength: 1,
            language: 'es',
            width: '100%',
            ajax: {
                url: '/demo-liquidaciones/cliente/clientesBusquedaJSON',
                dataType: 'json',
                delay: 300,
                data: function (params) { return { q: params.term }; },
                processResults: function (data) { return { results: data.results }; },
                cache: true
            }
        });

        $('#clienteSelect').on('select2:select', function (e) {
            var d = e.params.data;
            $('#cliente\\.id').val(d.id);
            $('#empresa\\.id').val(d.empresaId);
            $('#nombreCliente').val(d.nombreCliente);
            $('#nombreEmpresa').val(d.nombreEmpresa);
        });

        $('#clienteSelect').on('select2:clear', function () {
            $('#cliente\\.id').val('');
            $('#empresa\\.id').val('');
            $('#nombreCliente').val('');
            $('#nombreEmpresa').val('');
        });
    }

    // ══════════════════════════════════════════════════════════════════════
    // LOTES ASIGNADOS (tabla Bootstrap, sin jqGrid — patrón Empresa)
    // ══════════════════════════════════════════════════════════════════════
    var lotesData = [];
    var lotesEditable = $('#lotesAsignados').data('editable') !== false &&
                        $('#lotesAsignados').data('editable') !== 'false';

    function cargarLotesIniciales() {
        var raw = $('#lotes').val();
        try { lotesData = raw ? JSON.parse(raw) : []; }
        catch (e) { lotesData = []; }
        renderLotes();
    }

    function sincronizarLotes() {
        $('#lotes').val(lotesData.length ? JSON.stringify(lotesData) : '');
        $('#descripcion').val('POR LOTES: ' + lotesData.map(function (l) { return l.lote; }).join(', '));
    }

    function renderLotes() {
        var $body = $('#lotesAsignadosBody').empty();
        if (!lotesData.length) {
            $body.append('<tr><td colspan="5" class="text-center text-muted py-3">No hay lotes asignados.</td></tr>');
            return;
        }
        lotesData.forEach(function (l, i) {
            var $tr = $('<tr>').attr('data-index', i);
            $tr.append($('<td>').text(l.lote));
            $tr.append($('<td>').text(l.fechaDeRecepcion));
            $tr.append($('<td>').text(l.tipoDeMaterial));
            $tr.append($('<td class="text-right">').text(formatNumero(l.pesoBruto)));
            $tr.append($('<td class="text-center">').html(lotesEditable ?
                '<button type="button" class="btn btn-outline-danger btn-sm btn-quitar-lote" title="Quitar"><i class="fas fa-times"></i></button>' : ''));
            $body.append($tr);
        });
    }

    function buscarLotes() {
        $.ajax({
            url: '/demo-liquidaciones/recepcionDeComplejo/recepcionesAnticipoJSON',
            dataType: 'json',
            data: {
                tipoDeMineral: $('#tipoDeMineral').val(),
                loteInicial: $('#loteInicial').val(),
                loteFinal: $('#loteFinal').val(),
                depositoId: $('#depositoId').val(),
                empresaId: $('#empresa\\.id').val(),
                clienteId: $('#cliente\\.id').val()
            },
            success: function (data) {
                try { lotesData = data.lotes ? JSON.parse(data.lotes) : []; }
                catch (e) { lotesData = []; }
                renderLotes();
                sincronizarLotes();
            }
        });
    }

    $('#agregar').on('click', buscarLotes);

    $('#lotesAsignadosBody').on('click', '.btn-quitar-lote', function () {
        var idx = $(this).closest('tr').data('index');
        lotesData.splice(idx, 1);
        renderLotes();
        sincronizarLotes();
    });

    cargarLotesIniciales();

    // ══════════════════════════════════════════════════════════════════════
    // CUOTAS DE ANTICIPO (solo en alta; cada fila = un anticipo emitido)
    // ══════════════════════════════════════════════════════════════════════
    if ($('#cuotasBody').length) {

        // Mismo datepicker que CotizacionDiariaDeMinerales (g:datepickerUI): dd/mm/yy,
        // locale es si está disponible, y beforeShow que reposiciona el calendario bajo
        // el input + sube el z-index (necesario por los contenedores de AdminLTE).
        function initDatepicker($el) {
            if (!$.fn.datepicker) return;
            var opciones = {
                dateFormat: 'dd/mm/yy',
                changeMonth: true,
                changeYear: true,
                beforeShow: function (input, inst) {
                    inst.dpDiv.css('z-index', 9999);
                    setTimeout(function () {
                        var offset = $(input).offset();
                        inst.dpDiv.css({ top: (offset.top + $(input).outerHeight()) + 'px', left: offset.left + 'px' });
                    }, 1);
                }
            };
            if ($.datepicker && $.datepicker.regional && $.datepicker.regional['es']) {
                opciones = $.extend({}, $.datepicker.regional['es'], opciones);
            }
            $el.datepicker(opciones);
        }
        initDatepicker($('.cuota-fecha'));

        function recalcularTotal() {
            var total = 0;
            $('.cuota-monto').each(function () {
                var v = parseFloat($(this).val());
                if (!isNaN(v)) total += v;
            });
            $('#totalAnticipos').val(total);                          // hidden: valor crudo enviado
            $('#totalAnticiposDisplay').val(formatNumero(total));     // visible: formateado
            var pagado = parseFloat($('input[name=totalPagado]').val()) || 0;
            $('input[name=totalPorPagar]').val(total - pagado);
            // El literal lo calcula el backend (recalcularTotal con NumeroALiteral),
            // fuente única para alta y para "emitir anticipo" desde el detalle.
        }

        $('#cuotasBody').on('input', '.cuota-monto', recalcularTotal);

        $('#agregarCuotaRow').on('click', function () {
            var hoy = $('.cuota-fecha').first().val();
            var $row = $(
                '<tr>' +
                '<td><input type="number" step="any" min="0" name="cuotaMonto" class="form-control form-control-sm cuota-monto" required></td>' +
                '<td><input type="text" name="cuotaFecha" class="form-control form-control-sm cuota-fecha" autocomplete="off" value="' + hoy + '"></td>' +
                '<td class="text-center"><button type="button" class="btn btn-outline-danger btn-sm cuota-remove" title="Quitar"><i class="fas fa-times"></i></button></td>' +
                '</tr>');
            $('#cuotasBody').append($row);
            initDatepicker($row.find('.cuota-fecha'));
        });

        $('#cuotasBody').on('click', '.cuota-remove', function () {
            if ($('#cuotasBody tr').length > 1) {
                $(this).closest('tr').remove();
                recalcularTotal();
            }
        });
    }
});
