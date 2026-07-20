<%@ page import="org.socymet.proveedor.Cliente" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Estado de Cuenta de Cliente</title>
    <link rel="stylesheet" href="${assetPath(src: 'vendor/select2.min.css')}" type="text/css">
    <style>
        .select2-container--default .select2-selection--single {
            height: calc(1.5em + .75rem + 2px);
            padding: .375rem .75rem;
            border: 1px solid #ced4da;
            border-radius: .25rem;
        }
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
        <h3 class="card-title">Estado de Cuenta de Cliente</h3>
    </div>
    <div class="card-body">

        <%-- ── Filtros ──────────────────────────────────────────────────── --%>
        <g:form action="create" method="GET">
            <g:hiddenField name="clienteId" value="${cliente?.id}"/>
            <div class="form-row align-items-end">
                <div class="form-group col-md-5">
                    <label>Cliente</label>
                    <select id="clienteSelect" class="form-control" style="width:100%">
                        <g:if test="${cliente}">
                            <option value="${cliente.id}" selected="selected">${cliente.ci} — ${cliente.nombre}</option>
                        </g:if>
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
                <div class="form-group col-md-1">
                    <button type="submit" class="btn btn-primary btn-block"><i class="fas fa-search"></i></button>
                </div>
            </div>
        </g:form>

        <%-- ── Resultados ───────────────────────────────────────────────── --%>
        <g:if test="${estadoCuentaList != null}">
            <hr/>
            <div class="d-flex align-items-center mb-2">
                <h5 class="mb-0 mr-auto"><strong>${cliente?.nombre}</strong>
                    <small class="text-muted ml-2"><g:formatDate date="${fechaInicial}" format="dd/MM/yyyy"/> al <g:formatDate date="${fechaFinal}" format="dd/MM/yyyy"/></small></h5>
            </div>

            <g:if test="${estadoCuentaList}">
                <g:set var="totDebe" value="${estadoCuentaList.sum { it.debe ?: 0 } ?: 0}"/>
                <g:set var="totHaber" value="${estadoCuentaList.sum { it.haber ?: 0 } ?: 0}"/>
                <g:set var="saldoFinal" value="${estadoCuentaList[-1].saldo ?: 0}"/>
                <div class="table-responsive">
                    <table class="table table-hover table-striped table-sm mb-0">
                        <thead class="thead-light">
                        <tr>
                            <th>Fecha</th>
                            <th>N° Comprobante</th>
                            <th>Detalle</th>
                            <th class="text-right">Debe [Bs]</th>
                            <th class="text-right">Haber [Bs]</th>
                            <th class="text-right">Saldo [Bs]</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${estadoCuentaList}" var="ec">
                            <tr>
                                <td><g:formatDate date="${ec.fecha}" format="dd/MM/yyyy"/></td>
                                <td>${comprobanteEc[ec.id] ?: (ec.numeroComprobante ?: '—')}</td>
                                <td>${ec.detalle}</td>
                                <td class="text-right"><g:formatNumber number="${ec.debe ?: 0}" type="number" maxFractionDigits="2" minFractionDigits="2"/></td>
                                <td class="text-right"><g:formatNumber number="${ec.haber ?: 0}" type="number" maxFractionDigits="2" minFractionDigits="2"/></td>
                                <td class="text-right"><g:formatNumber number="${ec.saldo ?: 0}" type="number" maxFractionDigits="2" minFractionDigits="2"/></td>
                            </tr>
                        </g:each>
                        </tbody>
                        <tfoot class="font-weight-bold">
                        <tr class="table-light">
                            <td colspan="3" class="text-right">Totales</td>
                            <td class="text-right"><g:formatNumber number="${totDebe}" type="number" maxFractionDigits="2" minFractionDigits="2"/></td>
                            <td class="text-right"><g:formatNumber number="${totHaber}" type="number" maxFractionDigits="2" minFractionDigits="2"/></td>
                            <td class="text-right">Saldo: <g:formatNumber number="${saldoFinal}" type="number" maxFractionDigits="2" minFractionDigits="2"/></td>
                        </tr>
                        </tfoot>
                    </table>
                </div>

                <div class="mt-3">
                    <g:link action="exportarExcel"
                            params="${[clienteId: cliente?.id, fi: fechaInicial?.format('yyyy-MM-dd'), ff: fechaFinal?.format('yyyy-MM-dd')]}"
                            class="btn btn-success"><i class="fas fa-file-excel mr-1"></i>Exportar a Excel</g:link>
                    <g:link action="exportarPdf" target="_blank"
                            params="${[clienteId: cliente?.id, fi: fechaInicial?.format('yyyy-MM-dd'), ff: fechaFinal?.format('yyyy-MM-dd')]}"
                            class="btn btn-danger"><i class="fas fa-file-pdf mr-1"></i>Exportar PDF</g:link>
                </div>
            </g:if>
            <g:else>
                <div class="alert alert-warning mb-0"><i class="fas fa-info-circle mr-1"></i>No se encontraron movimientos para el cliente y rango seleccionados.</div>
            </g:else>
        </g:if>
        <g:else>
            <p class="text-muted mb-0"><i class="fas fa-info-circle mr-1"></i>Seleccione un cliente y un rango de fechas, luego presione buscar.</p>
        </g:else>
    </div>
</div>

<script>
    $(function () {
        $(document).on('select2:open', function () {
            var c = document.querySelector('.select2-container--open .select2-search__field');
            if (c) c.focus();
        });
        var $cli = $('#clienteSelect');
        if ($cli.length && $.fn.select2) {
            $cli.select2({
                placeholder: 'Buscar por nombre o CI…',
                allowClear: true, minimumInputLength: 1, language: 'es', width: '100%',
                ajax: {
                    url: '${createLink(controller: "cliente", action: "clientesBusquedaJSON")}',
                    dataType: 'json', delay: 300,
                    data: function (p) { return { q: p.term }; },
                    processResults: function (d) { return { results: d.results }; },
                    cache: true
                }
            });
            $cli.on('select2:select', function (e) { $('#clienteId').val(e.params.data.id); });
            $cli.on('select2:clear', function () { $('#clienteId').val(''); });
        }
    });
</script>
</body>
</html>
