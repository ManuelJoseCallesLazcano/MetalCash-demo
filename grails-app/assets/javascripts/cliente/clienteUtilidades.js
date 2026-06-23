$(document).ready(function () {

    // ── Auto-foco en el campo de búsqueda al abrir el Select2 ────────────────
    $(document).on('select2:open', function () {
        var campo = document.querySelector('.select2-container--open .select2-search__field');
        if (campo) campo.focus();
    });

    // ── Select2: búsqueda asíncrona de empresa por nombre o código ───────────
    $('#empresaSelect').select2({
        placeholder: 'Buscar empresa por nombre o código…',
        allowClear: true,
        minimumInputLength: 1,
        language: 'es',
        width: '100%',
        ajax: {
            url: '/demo-liquidaciones/empresa/empresaBusquedaJSON',
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

});
