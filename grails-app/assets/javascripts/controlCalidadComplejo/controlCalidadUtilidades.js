$(document).ready(function () {

    var $lote = $('#recepcionDeComplejo');
    var PENALIZABLES = ['Arsenico', 'Antimonio', 'Silice', 'Bismuto', 'Estano', 'Zinc'];

    // ── Select2 en el lote (solo en creación; en edición es un enlace) ──────
    if ($lote.is('select')) {
        $lote.select2({
            width: '100%',
            language: 'es',
            placeholder: 'Seleccione un lote…'
        });
    }

    // ── Recuperar datos de la recepción y poblar campos relacionados ────────
    // reset = true  → limpia penalizables para re-ingreso (cambio de lote)
    // reset = false → conserva los valores existentes (carga inicial / edición)
    function recuperarDatosRecepcion(reset) {
        var recepcionId = $lote.val();
        if (!recepcionId) return;

        $.ajax({
            url: '/demo-liquidaciones/controlCalidadComplejo/recepcionCalidadComplejoJSON',
            dataType: 'json',
            data: { recepcionId: recepcionId },
            success: function (data) {
                $('#nombreCliente').val(data.nombreCliente);
                $('#empresa\\.id').val(data.empresaId);
                $('#nombreEmpresa').val(data.nombreEmpresa);
                $('#fechaDeRecepcion').val(data.fechaDeRecepcion);
                $('#cantidadDeSacos').val(data.cantidadDeSacos);
                $('#pesoBruto').val(data.pesoBruto);
                $('#estadoDelLote').val(data.estadoDelLote);
                $('#condicionDeEntrega').val(data.condicionDeEntrega);
                desplegarMineralesPenalizables(reset);
            }
        });
    }

    // ── Penalizables: visibles solo cuando la condición NO es SPOT ──────────
    function desplegarMineralesPenalizables(reset) {
        var esSpot = $('#condicionDeEntrega').val() === 'SPOT';
        $('#_tituloPenalizables').toggle(!esSpot);

        PENALIZABLES.forEach(function (el) {
            $('#_porcentaje' + el).toggle(!esSpot);
            if (esSpot) {
                $('#porcentaje' + el).val(0);      // SPOT: sin penalizables
            } else if (reset) {
                $('#porcentaje' + el).val('');     // cambio de lote: re-ingresar
            }
        });
    }

    $lote.on('change', function () { recuperarDatosRecepcion(true); });
    recuperarDatosRecepcion(false);   // carga inicial: conservar valores

    // La Fecha de Análisis usa el tag <g:datepickerUI> (jQuery UI), igual que
    // fechaDeRecepcion de RecepcionDeComplejo; inicializa su propio datepicker.
});
