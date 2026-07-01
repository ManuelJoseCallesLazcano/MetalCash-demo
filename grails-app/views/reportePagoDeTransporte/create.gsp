<html>
<head>
    <meta name="layout" content="main">
    <title>Reporte de Pago de Transporte</title>
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
        <h3 class="card-title">Reporte de Pago de Transporte</h3>
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
                <div class="form-group col-md-4">
                    <label>Cliente</label>
                    <select id="clienteSelect" name="clienteId" class="form-control" style="width:100%">
                        <g:if test="${cliente}"><option value="${cliente.id}" selected="selected">${cliente.nombre}</option></g:if>
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
                    <button type="submit" class="btn btn-primary"><i class="fas fa-search mr-1"></i>Buscar</button>
                </div>
            </div>
        </g:form>

        <g:if test="${filas != null}">
            <hr/>
            <div class="d-flex align-items-center mb-2">
                <h5 class="mb-0 mr-auto">${empresa ? empresa.toString() : 'Todas las empresas'}
                    <small class="text-muted ml-2">Pagos del <g:formatDate date="${fechaInicial}" format="dd/MM/yyyy"/> al <g:formatDate date="${fechaFinal}" format="dd/MM/yyyy"/></small></h5>
            </div>
            <g:if test="${filas}">
                <g:set var="n2" value="${{ v -> g.formatNumber(number: v ?: 0, type: 'number', maxFractionDigits: 2, minFractionDigits: 2) }}"/>
                <div class="table-responsive">
                    <table class="table table-hover table-striped table-sm mb-0" style="font-size:.85rem;">
                        <thead class="thead-light">
                        <tr>
                            <th>Lote</th><th>Fec. Recep.</th><th>Empresa</th><th>Cliente</th><th>Chofer</th>
                            <th>Automóvil</th><th>Material</th><th class="text-right">Sacos</th>
                            <th class="text-right">P. Bruto [Kg]</th><th class="text-right">Costo Transp.</th>
                            <th class="text-right">Comprob.</th><th>Fecha de Pago</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${filas}" var="f">
                            <tr>
                                <td>${f.lote}</td>
                                <td><g:formatDate date="${f.fechaRec}" format="dd/MM/yyyy"/></td>
                                <td>${f.empresa}</td><td>${f.cliente}</td><td>${f.chofer}</td>
                                <td>${f.automovil}</td><td>${f.material}</td>
                                <td class="text-right">${f.sacos}</td>
                                <td class="text-right">${n2(f.pesoBruto)}</td>
                                <td class="text-right">${n2(f.costo)}</td>
                                <td class="text-right">${f.comprobante}</td>
                                <td><g:formatDate date="${f.fechaPago}" format="dd/MM/yyyy"/></td>
                            </tr>
                        </g:each>
                        </tbody>
                        <tfoot class="font-weight-bold table-light">
                        <tr>
                            <td colspan="7" class="text-right">Totales (${filas.size()})</td>
                            <td class="text-right">${tot.sacos}</td>
                            <td class="text-right">${n2(tot.pesoBruto)}</td>
                            <td class="text-right">${n2(tot.costo)}</td>
                            <td colspan="2"></td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
                <div class="mt-3">
                    <g:link action="exportarExcel"
                            params="${[empresaId: empresa?.id, clienteId: cliente?.id, fi: fechaInicial?.format('yyyy-MM-dd'), ff: fechaFinal?.format('yyyy-MM-dd')]}"
                            class="btn btn-success"><i class="fas fa-file-excel mr-1"></i>Exportar a Excel</g:link>
                </div>
            </g:if>
            <g:else>
                <div class="alert alert-warning mb-0"><i class="fas fa-info-circle mr-1"></i>No se encontraron pagos de transporte para los filtros seleccionados.</div>
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
            select2Ajax('#clienteSelect', '${createLink(controller: "cliente", action: "clientesBusquedaJSON")}', '(Todos)',
                function () { return { empresaId: $('#empresaSelect').val() }; });
            $('#empresaSelect').on('change', function () { $('#clienteSelect').val(null).trigger('change'); });
        }
    });
</script>
</body>
</html>
