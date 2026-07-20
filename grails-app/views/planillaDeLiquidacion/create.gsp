<html>
<head>
    <meta name="layout" content="main">
    <title>Planilla de Liquidación</title>
    <link rel="stylesheet" href="${assetPath(src: 'vendor/select2.min.css')}" type="text/css">
    <style>
        .select2-container--default .select2-selection--single { height: calc(1.5em + .75rem + 2px); padding: .375rem .75rem; border: 1px solid #ced4da; border-radius: .25rem; }
        .select2-container--default .select2-selection--single .select2-selection__rendered { padding: 0; line-height: 1.5; color: #495057; }
        .select2-container--default .select2-selection--single .select2-selection__arrow { height: 100%; top: 0; right: .375rem; }
    </style>
    <script src="${assetPath(src: 'vendor/select2.min.js')}"></script>
    <script src="${assetPath(src: 'vendor/select2-i18n-es.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Planilla de Liquidación</h3>
    </div>
    <div class="card-body">

        <g:form action="create" method="GET">
            <div class="form-row align-items-end">
                <div class="form-group col-md-6">
                    <label>Empresa</label>
                    <select id="empresaSelect" name="empresaId" class="form-control" style="width:100%">
                        <g:if test="${empresa}"><option value="${empresa.id}" selected="selected">${empresa}</option></g:if>
                    </select>
                </div>
                <div class="form-group col-md-3">
                    <label>Fecha inicial</label>
                    <g:datepickerUI name="fechaInicial" value="${fechaInicial}" class="form-control"/>
                </div>
                <div class="form-group col-md-3">
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
                    <small class="text-muted ml-2"><g:formatDate date="${fechaInicial}" format="dd/MM/yyyy"/> al <g:formatDate date="${fechaFinal}" format="dd/MM/yyyy"/></small></h5>
            </div>
            <g:if test="${filas}">
                <g:set var="n2" value="${{ v -> g.formatNumber(number: v ?: 0, type: 'number', maxFractionDigits: 2, minFractionDigits: 2) }}"/>
                <div class="table-responsive">
                    <table class="table table-hover table-striped table-sm mb-0" style="font-size:.78rem;">
                        <thead class="thead-light">
                        <tr>
                            <g:each in="${columnas}" var="c"><th class="${c.tipo == 'numero' ? 'text-right' : ''}">${c.titulo}</th></g:each>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${filas}" var="f">
                            <tr>
                                <g:each in="${columnas}" var="c">
                                    <td class="${c.tipo == 'numero' ? 'text-right' : ''}">
                                        <g:if test="${c.tipo == 'fecha'}"><g:formatDate date="${f[c.clave]}" format="dd/MM/yyyy"/></g:if>
                                        <g:elseif test="${c.tipo == 'numero'}">${n2(f[c.clave])}</g:elseif>
                                        <g:else>${f[c.clave]}</g:else>
                                    </td>
                                </g:each>
                            </tr>
                        </g:each>
                        </tbody>
                        <tfoot class="font-weight-bold table-light">
                        <tr>
                            <g:each in="${columnas}" var="c" status="i">
                                <td class="${c.tipo == 'numero' ? 'text-right' : ''}">
                                    <g:if test="${i == 0}">Totales (${filas.size()})</g:if>
                                    <g:elseif test="${c.total == 'suma'}">${n2(tot[c.clave])}</g:elseif>
                                </td>
                            </g:each>
                        </tr>
                        <tr class="table-light">
                            <g:each in="${columnas}" var="c" status="i">
                                <td class="${c.tipo == 'numero' ? 'text-right' : ''}">
                                    <g:if test="${i == 0}">Promedios ponderados</g:if>
                                    <g:elseif test="${c.clave == 'h2o'}">${n2(prom.hum)}</g:elseif>
                                    <g:elseif test="${c.clave == 'leyZn'}">${n2(prom.zn)}</g:elseif>
                                    <g:elseif test="${c.clave == 'leyPb'}">${n2(prom.pb)}</g:elseif>
                                    <g:elseif test="${c.clave == 'leyAg'}">${n2(prom.ag)}</g:elseif>
                                </td>
                            </g:each>
                        </tr>
                        </tfoot>
                    </table>
                </div>
                <div class="alert alert-info py-2 mt-3 mb-0">
                    <i class="fas fa-info-circle mr-1"></i>El <strong>total de Líquido Pagable</strong> no incluye las liquidaciones con líquido pagable <strong>menor a cero</strong> (saldo en contra).
                </div>
                <div class="mt-3">
                    <g:link action="exportarExcel"
                            params="${[empresaId: empresa?.id, fi: fechaInicial?.format('yyyy-MM-dd'), ff: fechaFinal?.format('yyyy-MM-dd')]}"
                            class="btn btn-success"><i class="fas fa-file-excel mr-1"></i>Exportar a Excel</g:link>
                </div>
            </g:if>
            <g:else>
                <div class="alert alert-warning mb-0"><i class="fas fa-info-circle mr-1"></i>No se encontraron liquidaciones para los filtros seleccionados.</div>
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
        // Empresa OBLIGATORIA (la planilla es por empresa específica): sin opción "(Todas)".
        if ($.fn.select2) {
            $('#empresaSelect').select2({
                language: 'es', width: '100%', minimumInputLength: 1, placeholder: 'Seleccione empresa…',
                ajax: {
                    url: '${createLink(controller: "empresa", action: "empresaBusquedaJSON")}',
                    dataType: 'json', delay: 250,
                    data: function (p) { return { q: p.term }; },
                    processResults: function (d) { return { results: d.results || [] }; },
                    cache: false
                }
            });
        }
        // Buscar sólo con empresa seleccionada
        function ctrlBuscar() { $('#btnBuscar').prop('disabled', !$('#empresaSelect').val()); }
        $('#empresaSelect').on('change', ctrlBuscar);
        ctrlBuscar();
    });
</script>
</body>
</html>
