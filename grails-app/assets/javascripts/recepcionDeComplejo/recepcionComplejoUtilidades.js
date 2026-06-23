$(document).ready(function () {

    // ── Auto-foco en el campo de búsqueda al abrir cualquier Select2 ─────────
    $(document).on('select2:open', function () {
        var campo = document.querySelector('.select2-container--open .select2-search__field');
        if (campo) campo.focus();
    });

    // ── Select2: búsqueda de proveedor por nombre o CI ──────────────────────
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
            data: function (params) {
                return { q: params.term };
            },
            processResults: function (data) {
                return { results: data.results };
            },
            cache: true
        }
    });

    $('#clienteSelect').on('select2:select', function (e) {
        var d = e.params.data;
        $('#cliente\\.id').val(d.id);
        $('#empresa\\.id').val(d.empresaId);
        $('#nombreEmpresa').val(d.nombreEmpresa);
        cargarDatosTransporte(d.empresaId);
        filtrarCuadrillas();
        siguienteLote();
    });

    $('#clienteSelect').on('select2:clear', function () {
        $('#cliente\\.id').val('');
        $('#empresa\\.id').val('');
        $('#nombreEmpresa').val('');
    });

    // ── Recalcular el lote de turno al cambiar el depósito ──────────────────
    $('#deposito').on('change', function () {
        siguienteLote();
    });

    // ── Carga inicial en modo edición (cliente ya seleccionado) ─────────────
    var empresaIdInicial = $('#empresa\\.id').val();
    if (empresaIdInicial) {
        cargarDatosTransporte(empresaIdInicial);
        filtrarCuadrillas();
        siguienteLote();
    }

    // ── Select2: búsqueda de chofer por nombre o CI ──────────────────────────
    $('#choferSelect').select2({
        placeholder: 'Buscar por nombre o CI…',
        allowClear: true,
        minimumInputLength: 1,
        language: 'es',
        width: '100%',
        ajax: {
            url: '/demo-liquidaciones/chofer/choferBusquedaJSON',
            dataType: 'json',
            delay: 250,
            data: function (params) { return { q: params.term }; },
            processResults: function (data) { return { results: data.results }; },
            cache: true
        }
    });

    $('#choferSelect').on('select2:select', function (e) {
        var d = e.params.data;
        $('#chofer\\.id').val(d.id);
        $('#nombreChofer').val(d.nombreChofer);
        $('#ciChofer').val(d.ciChofer);
    });

    $('#choferSelect').on('select2:clear', function () {
        $('#chofer\\.id').val('');
        $('#nombreChofer').val('');
        $('#ciChofer').val('');
    });

    // ── Select2: búsqueda de vehículo por placa ──────────────────────────────
    $('#automovilSelect').select2({
        placeholder: 'Buscar por placa…',
        allowClear: true,
        minimumInputLength: 1,
        language: 'es',
        width: '100%',
        ajax: {
            url: '/demo-liquidaciones/automovil/automovilBusquedaJSON',
            dataType: 'json',
            delay: 250,
            data: function (params) { return { q: params.term }; },
            processResults: function (data) { return { results: data.results }; },
            cache: true
        }
    });

    $('#automovilSelect').on('select2:select', function (e) {
        var d = e.params.data;
        $('#automovil\\.id').val(d.id);
        $('#placa').val(d.placa);
    });

    $('#automovilSelect').on('select2:clear', function () {
        $('#automovil\\.id').val('');
        $('#placa').val('');
    });

    // Lee un campo numérico tolerando separadores de miles (p. ej. "13,450" → 13450)
    function num(selector) {
        return parseFloat(($(selector).val() || '').toString().replace(/[\s,]/g, '')) || 0;
    }

    // ── Peso Bruto = Peso Neto − Peso Tara ──────────────────────────────────
    function recalcularPesoBruto() {
        var neto = num('#pesoNeto');
        var tara = num('#pesoTara');
        $('#pesoBruto').val(Math.max(0, neto - tara));
        calcularCostoTransporte();
    }

    $('#pesoNeto, #pesoTara').on('input', recalcularPesoBruto);
    $('#tipoDeMaterial').on('change', calcularCostoTransporte);
    $('#cantidadDeSacos').on('input', calcularCostoTransporte);
    recalcularPesoBruto();

    // ── Costo de Transporte = f(tipoDeMaterial, pesoBruto, datos de empresa) ──
    function calcularCostoTransporte() {
        // BROZA usa los datos de "Complejos"; CONCENTRADO los de "Concentrados"
        calcularCosto($('#tipoDeMaterial').val() === 'CONCENTRADO' ? 'Concentrados' : 'Complejos');
    }

    function calcularCosto(sufijo) {
        var costo   = num('#costoTransporte' + sufijo);
        var unidadM = $('#unidadMonetaria' + sufijo).val();
        var unidadC = $('#unidadDeCobro' + sufijo).val();
        var tc      = num('#tipoDeCambioComercial');

        var costoDeTransporte;
        if (unidadC === 'TONELADA') {
            costoDeTransporte = num('#pesoBruto') / 1000 * costo;
        } else if (unidadC === 'SACO') {
            costoDeTransporte = num('#cantidadDeSacos') * costo;
        } else {
            costoDeTransporte = NaN;
        }

        // Si el costo está en dólares, se convierte a Bs con el tipo de cambio comercial
        if (unidadM === '$us') costoDeTransporte = costoDeTransporte * tc;

        $('#costoDeTransporte').val(isNaN(costoDeTransporte) ? '?' : toFixedNum(costoDeTransporte, 2));
    }

    function toFixedNum(number, precision) {
        var multiplier = Math.pow(10, precision);
        return Math.round(number * multiplier) / multiplier;
    }

    // ── Verificación de periodo de prueba ───────────────────────────────────
    $.ajax({
        url: '/demo-liquidaciones/recepcionDeComplejo/ready',
        dataType: 'json',
        success: function (data) {
            if (data.working == 0) {
                Swal.fire({
                    icon: 'error',
                    title: 'Período de prueba expirado',
                    text: 'El período de prueba del sistema ha expirado. Contacte al administrador.',
                    allowOutsideClick: false,
                    confirmButtonText: 'Aceptar'
                });
            }
        }
    });

    // ── Validar fecha y poblar selects de cotizaciones ──────────────────────
    function validarFechaConCotizaciones() {
        var day   = $('#fechaDeRecepcion_day').val();
        var month = $('#fechaDeRecepcion_month').val();
        var year  = $('#fechaDeRecepcion_year').val();

        if (!day || !month || !year) return;

        $.ajax({
            url: '/demo-liquidaciones/cotizacionDiariaDeMinerales/obtenerCotizaciones',
            dataType: 'json',
            data: { day: day, month: month, year: year },
            success: function (data) {
                $('#cotizacionDiariaDeMinerales').val(data.cotizacionDiariaId);
                $('#cotizacionQuincenalDeMinerales').val(data.cotizacionQuincenalId);
                $('#alicuota').val(data.alicuotaId);

                $('#hayCotizacionDiaria').text(data.hayCotizacionDiaria == 0 ? '⚠ Usando cotización activa' : '');
                $('#hayCotizacionQuincenal').text(data.hayCotizacionQuincenal == 0 ? '⚠ Usando cotización activa' : '');
                $('#hayAlicuota').text(data.hayAlicuota == 0 ? '⚠ Usando alícuota activa' : '');

                var advertencias = [];
                if (data.hayCotizacionDiaria == 0)
                    advertencias.push('Cotización Diaria: no registrada para esta fecha, se usa la activa.');
                if (data.hayCotizacionQuincenal == 0)
                    advertencias.push('Cotización Quincenal: no registrada para esta fecha, se usa la activa.');
                if (data.hayAlicuota == 0)
                    advertencias.push('Alícuota: no registrada para esta fecha, se usa la activa.');

                if (advertencias.length > 0) {
                    Swal.fire({
                        icon: 'warning',
                        title: 'Cotizaciones incompletas',
                        html: '<ul style="text-align:left; margin:0; padding-left:1.2em">' +
                              advertencias.map(function (a) { return '<li>' + a + '</li>'; }).join('') +
                              '</ul>',
                        confirmButtonText: 'Aceptar'
                    });
                }
            }
        });
    }

    $('#fechaDeRecepcion_picker').on('change', function () {
        setTimeout(validarFechaConCotizaciones, 0);
    });

    // En creación (sin hidden "id"), poblar cotizaciones para la fecha de hoy al cargar.
    // Si la página viene con errores de validación, esos mensajes tienen prioridad: no
    // disparamos la notificación de cotizaciones para que no se anteponga al SweetAlert de errores.
    if ($('input[name="id"]').length === 0 && $('#swalErrorList').length === 0) {
        validarFechaConCotizaciones();
    }

    // ── Funciones internas ──────────────────────────────────────────────────
    function cargarDatosTransporte(empresaId) {
        $.ajax({
            url: '/demo-liquidaciones/empresa/datosTransporteComplejosJSON',
            dataType: 'json',
            data: { empresaId: empresaId },
            success: function (data) {
                $('#costoTransporteComplejos').val(data.costoTransporteComplejos);
                $('#unidadMonetariaComplejos').val(data.unidadMonetariaComplejos);
                $('#unidadDeCobroComplejos').val(data.unidadDeCobroComplejos);
                $('#costoTransporteConcentrados').val(data.costoTransporteConcentrados);
                $('#unidadMonetariaConcentrados').val(data.unidadMonetariaConcentrados);
                $('#unidadDeCobroConcentrados').val(data.unidadDeCobroConcentrados);
                $('#tipoDeCambioComercial').val(data.tipoDeCambioComercial);
                calcularCostoTransporte();
            }
        });
    }

    function filtrarCuadrillas() {
        // var recepcionId = parseInt($('#recepcionId').val());
        // $.ajax({
        //     url: '/demo-liquidaciones/recepcionDeComplejo/seccionesDeEmpresa',
        //     data: {
        //         recepcionId: recepcionId === 0 ? 1000000 : recepcionId,
        //         empresaId: $('#empresa\\.id').val()
        //     },
        //     success: function (html) {
        //         $('#empresaSeccion').html(html).trigger('chosen:updated');
        //         if ($('#empresaSeccion option').length > 0) $('#_empresaSeccion').show();
        //         else $('#_empresaSeccion').hide();
        //     }
        // });
    }

    function siguienteLote() {
        var empresaId = $('#empresa\\.id').val();
        var depositoId = $('#deposito').val();

        // Sin empresa o depósito no hay lote que calcular todavía
        if (!empresaId || !depositoId) {
            $('#loteTurno').html('?');
            return;
        }

        $.ajax({
            url: '/demo-liquidaciones/recepcionDeComplejo/numeroDeLote',
            dataType: 'json',
            data: {
                empresaId: empresaId,
                depositoId: depositoId
            },
            success: function (data) {
                $('#loteTurno').html(data.lote);
            }
        });
    }

});
