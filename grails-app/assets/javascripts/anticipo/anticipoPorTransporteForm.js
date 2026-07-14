$(document).ready(function () {

    // Select2 en el selector de automovil (lista acotada, búsqueda del lado del cliente)
    if ($.fn.select2) {
        $('#automovil').select2({ language: 'es', width: '100%', placeholder: '-SELECCIONE-' });
    }

    // Auto-foco en el cuadro de búsqueda al abrir cualquier Select2
    $(document).on('select2:open', function () {
        var campo = document.querySelector('.select2-container--open .select2-search__field');
        if (campo) campo.focus();
    });

    // Búsqueda asíncrona de CI sobre anticipos por transporte previos; al elegir un CI
    // existente se autocompleta el nombre del cobrador. tags:true permite ingresar un CI nuevo.
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
        if (isNaN(n)) return '0.00';
        return n.toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
    }

    // Al cambiar de automovil, recuperar el disponible corriente del ledger de transporte.
    function recuperarDisponible() {
        var automovilId = $('#automovil').val();
        if (!automovilId) {
            $('#ultimoSaldo').val('');
            $('#disponibleMsg').text('');
            return;
        }
        $.ajax({
            url: '/demo-liquidaciones/estadoCuentaTransporte/obtenerSaldoJSON',
            dataType: 'json',
            data: { automovilId: automovilId },
            success: function (data) {
                $('#ultimoSaldo').val(data.saldoCuenta);
                $('#disponibleMsg').text('Anticipo disponible actual: Bs ' + formatNumero(data.saldoCuenta));
            }
        });
    }

    $('#automovil').on('change', recuperarDisponible);
    recuperarDisponible();

    // Importe -> literal en bolivianos
    function convertir() {
        var total = parseFloat(('' + $('#importe').val()).replace(/,/g, ''));
        if (isNaN(total)) { $('#importeLiteral').val('?'); return; }
        var entero = '' + parseInt(total);
        var n = Math.round((total * 100) % 100);
        var decimal = ('' + n).length < 2 ? '0' + n : '' + n;
        if (total >= 1000 && total < 2000)
            $('#importeLiteral').val('UN ' + CifrasEnLetras.convertirNumeroEnLetras(entero).toUpperCase() + ' ' + decimal + '/100 BOLIVIANOS');
        else
            $('#importeLiteral').val(CifrasEnLetras.convertirNumeroEnLetras(entero).toUpperCase() + ' ' + decimal + '/100 BOLIVIANOS');
    }

    $('#importe').on('keyup input', convertir);
});
