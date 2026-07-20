<%@ page import="org.socymet.proveedor.Automovil" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Reporte de Pago de Transporte</title>
    <link rel="stylesheet" href="${assetPath(src: 'vendor/select2.min.css')}" type="text/css">
    <style>
        .select2-container--default .select2-selection--single { height: calc(1.5em + .75rem + 2px); padding: .375rem .75rem; border: 1px solid #ced4da; border-radius: .25rem; }
        .select2-container--default .select2-selection--single .select2-selection__rendered { padding: 0; line-height: 1.5; color: #495057; }
        .select2-container--default .select2-selection--single .select2-selection__arrow { height: 100%; top: 0; right: .375rem; }
        .select2-container--default.select2-container--open .select2-selection--single,
        .select2-container--default.select2-container--focus .select2-selection--single {
            border-color: #80bdff; outline: 0; box-shadow: 0 0 0 .2rem rgba(0, 123, 255, .25);
        }
    </style>
    <script src="${assetPath(src: 'vendor/select2.min.js')}"></script>
    <script src="${assetPath(src: 'vendor/select2-i18n-es.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Reporte de Pago de Transporte</h3>
    </div>
    <div class="card-body">

        <g:if test="${flash.message}">
            <div class="alert alert-warning"><i class="fas fa-info-circle mr-1"></i>${flash.message}</div>
        </g:if>

        <%-- ── Filtros ──────────────────────────────────────────────────── --%>
        <g:form action="create" method="GET">
            <div class="form-row align-items-end">
                <div class="form-group col-md-3">
                    <label>Filtrar por</label>
                    <select id="modoSelect" name="modo" class="form-control">
                        <option value="placa" ${modo == 'placa' ? 'selected' : ''}>Placa</option>
                        <option value="cobrador" ${modo == 'cobrador' ? 'selected' : ''}>Cobrador</option>
                    </select>
                </div>

                <div class="form-group col-md-5" id="grupoPlaca">
                    <label>Placa (automóvil)</label>
                    <g:select id="automovilSelect" name="automovilId"
                              from="${Automovil.list([sort: 'placa'])}"
                              optionKey="id" optionValue="placa" value="${automovil?.id}"
                              noSelection="['': '-SELECCIONE-']" class="form-control" style="width:100%"/>
                </div>

                <div class="form-group col-md-5" id="grupoCobrador">
                    <label>Cobrador</label>
                    <select id="cobradorSelect" name="cobrador" class="form-control" style="width:100%">
                        <g:if test="${cobrador}"><option value="${cobrador}" selected="selected">${cobrador}</option></g:if>
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

        <%-- ── Resultados ───────────────────────────────────────────────── --%>
        <g:if test="${filas != null}">
            <hr/>
            <div class="d-flex align-items-center mb-2">
                <h5 class="mb-0 mr-auto">
                    <strong>${modo == 'cobrador' ? cobrador : automovil?.placa}</strong>
                    <small class="text-muted ml-2">Pagos del <g:formatDate date="${fechaInicial}" format="dd/MM/yyyy"/> al <g:formatDate date="${fechaFinal}" format="dd/MM/yyyy"/></small>
                </h5>
            </div>
            <g:if test="${filas}">
                <g:set var="n2" value="${{ v -> g.formatNumber(number: v ?: 0, type: 'number', maxFractionDigits: 2, minFractionDigits: 2) }}"/>
                <div class="table-responsive">
                    <table class="table table-hover table-striped table-sm mb-0" style="font-size:.85rem;">
                        <thead class="thead-light">
                        <tr>
                            <th>Fecha de Pago</th>
                            <th>N° Comprob.</th>
                            <g:if test="${modo == 'cobrador'}"><th>Placa</th></g:if>
                            <th>Cobrador</th>
                            <th>Lotes</th>
                            <th class="text-right">Total [Bs]</th>
                            <th class="text-right">Anticipos [Bs]</th>
                            <th class="text-right">Total Pagable [Bs]</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${filas}" var="f">
                            <tr>
                                <td><g:formatDate date="${f.fechaPago}" format="dd/MM/yyyy"/></td>
                                <td>${f.comprobante}</td>
                                <g:if test="${modo == 'cobrador'}"><td>${f.placa}</td></g:if>
                                <td>${f.cobrador}</td>
                                <td>${f.lotes}</td>
                                <td class="text-right">${n2(f.total)}</td>
                                <td class="text-right">${n2(f.anticipos)}</td>
                                <td class="text-right">${n2(f.pagable)}</td>
                            </tr>
                        </g:each>
                        </tbody>
                        <tfoot class="font-weight-bold table-light">
                        <tr>
                            <td colspan="${modo == 'cobrador' ? 5 : 4}" class="text-right">Totales (${filas.size()})</td>
                            <td class="text-right">${n2(tot.total)}</td>
                            <td class="text-right">${n2(tot.anticipos)}</td>
                            <td class="text-right">${n2(tot.pagable)}</td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
                <div class="mt-3">
                    <g:link action="exportarExcel"
                            params="${[modo: modo, automovilId: automovil?.id, cobrador: cobrador, fi: fechaInicial?.format('yyyy-MM-dd'), ff: fechaFinal?.format('yyyy-MM-dd')]}"
                            class="btn btn-success"><i class="fas fa-file-excel mr-1"></i>Exportar a Excel</g:link>
                    <g:if test="${modo == 'placa'}">
                        <small class="text-muted ml-2">El Excel incluye una 2ª hoja con el estado de cuenta de la placa.</small>
                    </g:if>
                </div>
            </g:if>
            <g:else>
                <div class="alert alert-warning mb-0"><i class="fas fa-info-circle mr-1"></i>No se encontraron pagos de transporte para los filtros seleccionados.</div>
            </g:else>
        </g:if>
        <g:else>
            <p class="text-muted mb-0"><i class="fas fa-info-circle mr-1"></i>Seleccione el modo de filtro, el valor y un rango de fechas, luego presione Buscar.</p>
        </g:else>
    </div>
</div>

<script>
    $(function () {
        $(document).on('select2:open', function () { var c = document.querySelector('.select2-container--open .select2-search__field'); if (c) c.focus(); });

        // Muestra el campo (placa/cobrador) correspondiente al modo elegido.
        function aplicarModo() {
            var modo = $('#modoSelect').val();
            $('#grupoPlaca').toggle(modo === 'placa');
            $('#grupoCobrador').toggle(modo === 'cobrador');
        }
        $('#modoSelect').on('change', aplicarModo);
        aplicarModo();

        if ($.fn.select2) {
            // Placa: catálogo cargado en la página (client-side).
            $('#automovilSelect').select2({ placeholder: 'Buscar por placa…', language: 'es', width: '100%' });

            // Cobrador: búsqueda asíncrona con sugerencias mientras se escribe.
            $('#cobradorSelect').select2({
                language: 'es', width: '100%', minimumInputLength: 1, allowClear: true,
                placeholder: 'Escriba el nombre del cobrador…',
                ajax: {
                    url: '${createLink(controller: "reportePagoDeTransporte", action: "cobradoresBusquedaJSON")}',
                    dataType: 'json', delay: 250,
                    data: function (p) { return { q: p.term }; },
                    processResults: function (d) { return { results: d.results || [] }; },
                    cache: false
                }
            });
        }
    });
</script>
</body>
</html>
