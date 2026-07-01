<%@ page import="org.socymet.proveedor.Empresa; org.socymet.proveedor.Deposito" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Reporte de Lotes Recepcionados</title>
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
        <h3 class="card-title">Reporte de Lotes Recepcionados</h3>
    </div>
    <div class="card-body">

        <%-- ── Filtros ──────────────────────────────────────────────────── --%>
        <g:form action="create" method="GET">
            <div class="form-row align-items-end">
                <div class="form-group col-md-3">
                    <label>Depósito</label>
                    <select id="depositoSelect" name="depositoId" class="form-control" style="width:100%">
                        <option value="">(Todos)</option>
                        <g:each in="${Deposito.list([sort: 'nombreDeposito'])}" var="d">
                            <option value="${d.id}" ${deposito?.id == d.id ? 'selected' : ''}>${d.toString()}</option>
                        </g:each>
                    </select>
                </div>
                <div class="form-group col-md-3">
                    <label>Empresa</label>
                    <select id="empresaSelect" name="empresaId" class="form-control" style="width:100%">
                        <g:if test="${empresa}"><option value="${empresa.id}" selected="selected">${empresa}</option></g:if>
                    </select>
                </div>
                <div class="form-group col-md-3">
                    <label>Cliente</label>
                    <select id="clienteSelect" name="clienteId" class="form-control" style="width:100%">
                        <g:if test="${cliente}"><option value="${cliente.id}" selected="selected">${cliente.nombre}</option></g:if>
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
                <div class="form-group col-md-3">
                    <label>Estado</label>
                    <g:select name="estado" from="${['Todos','NO LIQUIDADO','LIQUIDADO']}" value="${estado}" class="form-control"/>
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
                <h5 class="mb-0 mr-auto">${empresa ? empresa.toString() : 'Todas las empresas'}
                    <small class="text-muted ml-2">${estado} · <g:formatDate date="${fechaInicial}" format="dd/MM/yyyy"/> al <g:formatDate date="${fechaFinal}" format="dd/MM/yyyy"/></small></h5>
            </div>
            <g:if test="${filas}">
                <div class="table-responsive">
                    <table class="table table-hover table-striped table-sm mb-0">
                        <thead class="thead-light">
                        <tr>
                            <th>Fec. Rec.</th><th>Lote</th><th>Procedencia</th><th>Proveedor</th>
                            <th class="text-right">Sacos</th><th class="text-right">P. Bruto [Kg]</th><th>Estado</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${filas}" var="f">
                            <tr>
                                <td><g:formatDate date="${f.fecha}" format="dd/MM/yyyy"/></td>
                                <td>${f.lote}</td>
                                <td>${f.procedencia}</td>
                                <td>${f.proveedor}</td>
                                <td class="text-right">${f.sacos}</td>
                                <td class="text-right"><g:formatNumber number="${f.pesoBruto}" type="number" maxFractionDigits="2" minFractionDigits="2"/></td>
                                <td>${f.estado}</td>
                            </tr>
                        </g:each>
                        </tbody>
                        <tfoot class="font-weight-bold table-light">
                        <tr>
                            <td colspan="4" class="text-right">Totales (${filas.size()} lote${filas.size() == 1 ? '' : 's'})</td>
                            <td class="text-right">${totSacos}</td>
                            <td class="text-right"><g:formatNumber number="${totPeso}" type="number" maxFractionDigits="2" minFractionDigits="2"/></td>
                            <td></td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
                <div class="mt-3">
                    <g:link action="exportarExcel"
                            params="${[empresaId: empresa?.id, depositoId: deposito?.id, clienteId: cliente?.id, estado: estado, fi: fechaInicial?.format('yyyy-MM-dd'), ff: fechaFinal?.format('yyyy-MM-dd')]}"
                            class="btn btn-success"><i class="fas fa-file-excel mr-1"></i>Exportar a Excel</g:link>
                </div>
            </g:if>
            <g:else>
                <div class="alert alert-warning mb-0"><i class="fas fa-info-circle mr-1"></i>No se encontraron lotes para los filtros seleccionados.</div>
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
        // Empresa y Cliente: Select2 con búsqueda ASÍNCRONA (las listas crecen; no se renderizan completas).
        // Se antepone "(Todos)" a los resultados para poder volver a "sin filtro" (value="").
        // extra: función opcional que aporta parámetros adicionales a la consulta (p. ej. empresaId)
        function select2Ajax(sel, url, etiquetaTodos, extra) {
            $(sel).select2({
                language: 'es', width: '100%', minimumInputLength: 1, allowClear: true,
                placeholder: etiquetaTodos,
                ajax: {
                    url: url, dataType: 'json', delay: 250,
                    data: function (p) { var d = { q: p.term }; if (extra) Object.assign(d, extra()); return d; },
                    processResults: function (d) { return { results: [{ id: '', text: etiquetaTodos }].concat(d.results || []) }; },
                    cache: false   // depende de empresaId; no cachear para evitar resultados obsoletos
                }
            });
        }
        if ($.fn.select2) {
            select2Ajax('#empresaSelect', '${createLink(controller: "empresa", action: "empresaBusquedaJSON")}', '(Todas)');
            // Cliente en cascada: filtra por la Empresa elegida (si hay)
            select2Ajax('#clienteSelect', '${createLink(controller: "cliente", action: "clientesBusquedaJSON")}', '(Todos)',
                function () { return { empresaId: $('#empresaSelect').val() }; });
            // Al cambiar la empresa, limpiar el cliente (evita combinaciones empresa≠cliente)
            $('#empresaSelect').on('change', function () { $('#clienteSelect').val(null).trigger('change'); });
            // Depósito: lista corta y estable → select estático con búsqueda local
            if ($('#depositoSelect').length) $('#depositoSelect').select2({ language: 'es', width: '100%', minimumResultsForSearch: 0 });
        }
    });
</script>
</body>
</html>
