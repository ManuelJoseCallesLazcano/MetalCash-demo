//= require NumerosALetras

$(document).ready(function () {

    // Auto-foco en el cuadro de búsqueda al abrir cualquier Select2
    $(document).on('select2:open', function () {
        var campo = document.querySelector('.select2-container--open .select2-search__field');
        if (campo) campo.focus();
    });

    // ══════════════════════════════════════════════════════════════════════
    // CLIENTE (Select2, búsqueda por nombre/CI — igual que RecepcionDeComplejo)
    // ══════════════════════════════════════════════════════════════════════
    var $cliente = $('#clienteSelect');
    if ($cliente.length && $.fn.select2) {
        $cliente.select2({
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

        $cliente.on('select2:select', function (e) {
            var d = e.params.data;
            $('#cliente\\.id').val(d.id);
            $('#empresa\\.id').val(d.empresaId);
            recuperarEstadoCuenta();
        });

        $cliente.on('select2:clear', function () {
            $('#cliente\\.id').val('');
            $('#empresa\\.id').val('');
            $('#estadoCuentaMsg').text('');
        });
    }

    // ══════════════════════════════════════════════════════════════════════
    // ÚLTIMO ESTADO DE CUENTA DEL CLIENTE
    // ══════════════════════════════════════════════════════════════════════
    function recuperarEstadoCuenta() {
        var clienteId = $('#cliente\\.id').val();
        if (!clienteId) { $('#estadoCuentaMsg').text(''); return; }
        $.ajax({
            url: '/demo-liquidaciones/estadoDeCuenta/ultimoEstadoCuenta',
            data: { clienteId: clienteId },
            success: function (data) {
                $('#estadoCuentaMsg').text(data.mensaje === '-' ? '' : data.mensaje);
            }
        });
    }
    recuperarEstadoCuenta();   // al cargar (edición o regreso por error de validación)

    // ══════════════════════════════════════════════════════════════════════
    // IMPORTE → IMPORTE EN LITERAL (Bolivianos)
    // ══════════════════════════════════════════════════════════════════════
    function actualizarLiteral() {
        var numero = parseInt($('#importe').val(), 10);
        if (isNaN(numero)) { $('#importeLiteral').val(''); return; }
        var letras = CifrasEnLetras.convertirNumeroEnLetras(numero).toUpperCase();
        if (numero >= 1000 && numero < 2000) letras = 'UN ' + letras;   // "UN MIL …"
        $('#importeLiteral').val(letras + ' 00/100 BOLIVIANOS');
    }
    $('#importe').on('input', actualizarLiteral);
    if ($('#importe').val()) actualizarLiteral();

    // La fecha usa g:datepickerUI (mismo patrón que CotizacionDiariaDeMinerales):
    // el taglib inicializa el datepicker de jQuery UI y maneja el binding por struct.
});
