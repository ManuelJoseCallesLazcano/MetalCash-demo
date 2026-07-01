<html>
<head>
    <meta name="layout" content="main">
    <title>Reporte de Retenciones</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" type="text/css">
    <style>
        .select2-container--default .select2-selection--single { height: calc(1.5em + .75rem + 2px); padding: .375rem .75rem; border: 1px solid #ced4da; border-radius: .25rem; }
        .select2-container--default .select2-selection--single .select2-selection__rendered { padding: 0; line-height: 1.5; color: #495057; }
        .select2-container--default .select2-selection--single .select2-selection__arrow { height: 100%; top: 0; right: .375rem; }
    </style>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/i18n/es.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Reporte de Retenciones</h3>
    </div>
    <div class="card-body">

        <g:form action="create" method="GET">
            <div class="form-row align-items-end">
                <div class="form-group col-md-4">
                    <label>Empresa</label>
                    <select id="empresaSelect" name="empresaId" class="form-control" style="width:100%">
                        <g:if test="${empresa}"><option value="${empresa.id}" selected="selected">${empresa}</option></g:if>
                    </select>
                </div>
                <div class="form-group col-md-2">
                    <label>Tipo</label>
                    <g:select name="tipoRetencion" from="${['Todas','DE LEY','OTRA']}" value="${tipoRetencion}" class="form-control"/>
                </div>
                <div class="form-group col-md-3">
                    <label>Retención</label>
                    <select id="retencionSelect" name="retencion" class="form-control">
                        <option value="Todas">Todas</option>
                        <g:if test="${retencion && retencion != 'Todas'}"><option value="${retencion}" selected="selected">${retencion}</option></g:if>
                    </select>
                </div>
                <div class="form-group col-md-2">
                    <label>Fecha inicial</label>
                    <g:datepickerUI name="fechaInicial" value="${fechaInicial}" class="form-control"/>
                </div>
                <div class="form-group col-md-2">
                    <label>Fecha final</label>
                    <g:datepickerUI name="fechaFinal" value="${fechaFinal}" class="form-control"/>
                </div>
                <div class="form-group col-md-12 text-right mb-0">
                    <button type="submit" id="btnBuscar" class="btn btn-primary"><i class="fas fa-search mr-1"></i>Buscar</button>
                </div>
            </div>
        </g:form>

        <g:if test="${filas != null}">
            <hr/>
            <div class="d-flex align-items-center mb-2">
                <h5 class="mb-0 mr-auto">${empresa ? empresa.toString() : 'Todas las empresas'}
                    <small class="text-muted ml-2">${tipoRetencion} · <g:formatDate date="${fechaInicial}" format="dd/MM/yyyy"/> al <g:formatDate date="${fechaFinal}" format="dd/MM/yyyy"/></small></h5>
            </div>
            <g:if test="${filas}">
                <g:set var="n2" value="${{ v -> g.formatNumber(number: v ?: 0, type: 'number', maxFractionDigits: 2, minFractionDigits: 2) }}"/>
                <div class="table-responsive">
                    <table class="table table-hover table-striped table-sm mb-0" style="font-size:.85rem;">
                        <thead class="thead-light">
                        <tr>
                            <th>Fec. Liq.</th><th>N° Liq.</th><th>Empresa</th><th>Cliente</th><th>Lote</th>
                            <th>Tipo</th><th>Descripción</th><th class="text-right">Cantidad</th><th>Unidad</th>
                            <th class="text-right">Monto [Bs]</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${filas}" var="f">
                            <tr>
                                <td><g:formatDate date="${f.fecha}" format="dd/MM/yyyy"/></td>
                                <td>${f.numero}</td><td>${f.empresa}</td><td>${f.cliente}</td><td>${f.lote}</td>
                                <td>${f.tipo}</td><td>${f.descripcion}</td>
                                <td class="text-right">${n2(f.cantidad)}</td><td>${f.unidad}</td>
                                <td class="text-right">${n2(f.monto)}</td>
                            </tr>
                        </g:each>
                        </tbody>
                        <tfoot class="font-weight-bold table-light">
                        <tr>
                            <td colspan="9" class="text-right">Total Retenciones (${filas.size()})</td>
                            <td class="text-right">${n2(tot.monto)}</td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
                <div class="mt-3">
                    <g:link action="exportarExcel"
                            params="${[empresaId: empresa?.id, tipoRetencion: tipoRetencion, retencion: retencion, fi: fechaInicial?.format('yyyy-MM-dd'), ff: fechaFinal?.format('yyyy-MM-dd')]}"
                            class="btn btn-success"><i class="fas fa-file-excel mr-1"></i>Exportar a Excel</g:link>
                </div>
            </g:if>
            <g:else>
                <div class="alert alert-warning mb-0"><i class="fas fa-info-circle mr-1"></i>No se encontraron retenciones para los filtros seleccionados.</div>
            </g:else>
        </g:if>
        <g:else>
            <p class="text-muted mb-0"><i class="fas fa-info-circle mr-1"></i>Seleccione los filtros y presione Buscar.</p>
        </g:else>
    </div>
</div>

<script>
    $(function () {
        $(document).on('select2:open', function () { var c = document.querySelector('.select2-container--open .select2-search__field'); if (c) c.focus(); });
        function select2Ajax(sel, url, etiquetaTodos, extra) {
            $(sel).select2({
                language: 'es', width: '100%', minimumInputLength: 1, allowClear: true, placeholder: etiquetaTodos,
                ajax: { url: url, dataType: 'json', delay: 250,
                    data: function (p) { var d = { q: p.term }; if (extra) Object.assign(d, extra()); return d; },
                    processResults: function (d) { return { results: [{ id: '', text: etiquetaTodos }].concat(d.results || []) }; },
                    cache: false }
            });
        }
        if ($.fn.select2) {
            select2Ajax('#empresaSelect', '${createLink(controller: "empresa", action: "empresaBusquedaJSON")}', '(Todas)');
            $('#empresaSelect').on('change', function () { cargarRetenciones('Todas'); });
        }

        var URL_RET = '${createLink(action: "retencionesDisponiblesJSON")}';
        var $btnBuscar = $('#btnBuscar');

        function fechasListas() {
            return $('#fechaInicial_year').val() && $('#fechaFinal_year').val();
        }

        // Puebla el select Retención (dinámico) según Tipo + rango (+ empresa/cliente).
        // "Buscar" sólo se habilita cuando hay rango de fechas y la lista de retenciones está cargada.
        function cargarRetenciones(seleccion) {
            var $r = $('#retencionSelect');
            if (!fechasListas()) {
                $r.html('<option value="Todas">Todas</option>').prop('disabled', true);
                $btnBuscar.prop('disabled', true);
                return;
            }
            var data = {
                tipoRetencion: $('#tipoRetencion').val(),
                empresaId: $('#empresaSelect').val(),
                fechaInicial_day: $('#fechaInicial_day').val(), fechaInicial_month: $('#fechaInicial_month').val(), fechaInicial_year: $('#fechaInicial_year').val(),
                fechaFinal_day: $('#fechaFinal_day').val(), fechaFinal_month: $('#fechaFinal_month').val(), fechaFinal_year: $('#fechaFinal_year').val()
            };
            $r.prop('disabled', true);
            $.getJSON(URL_RET, data, function (d) {
                var opts = '<option value="Todas">Todas</option>';
                (d.results || []).forEach(function (x) {
                    var esc = $('<div>').text(x).html();
                    opts += '<option value="' + esc + '">' + esc + '</option>';
                });
                $r.html(opts).prop('disabled', false);
                if (seleccion) $r.val(seleccion);
                $btnBuscar.prop('disabled', false);
            }).fail(function () { $r.prop('disabled', false); $btnBuscar.prop('disabled', false); });
        }

        // Recargar al cambiar Tipo o las fechas (con pequeño retraso para que el datepicker actualice los hidden)
        $('#tipoRetencion').on('change', function () { cargarRetenciones('Todas'); });
        $('#fechaInicial_picker, #fechaFinal_picker').on('change', function () { setTimeout(function () { cargarRetenciones('Todas'); }, 80); });

        // Al cargar la página: preservar la retención actual si ya hay filtros
        cargarRetenciones('${retencion ?: 'Todas'}');
    });
</script>
</body>
</html>
