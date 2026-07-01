<%@ page import="org.socymet.proveedor.Empresa" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Reporte de Lotes Liquidados</title>
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
        <h3 class="card-title">Reporte de Lotes Liquidados</h3>
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
                    <small class="text-muted ml-2"><g:formatDate date="${fechaInicial}" format="dd/MM/yyyy"/> al <g:formatDate date="${fechaFinal}" format="dd/MM/yyyy"/></small></h5>
            </div>
            <g:if test="${filas}">
                <g:set var="n2" value="${{ v -> g.formatNumber(number: v ?: 0, type: 'number', maxFractionDigits: 2, minFractionDigits: 2) }}"/>
                <div class="table-responsive">
                    <table class="table table-hover table-striped table-sm mb-0" style="font-size:.82rem;">
                        <thead class="thead-light">
                        <tr>
                            <th>Fec. Liq.</th><th>N° Liq.</th><th>Empresa</th><th>Cliente</th><th>Lote</th>
                            <th class="text-right">Sacos</th><th class="text-right">P. Bruto</th><th class="text-right">K.N.S.</th>
                            <th class="text-right">%Zn</th><th class="text-right">%Pb</th><th class="text-right">%Ag</th>
                            <th class="text-right">K.F. Zn</th><th class="text-right">K.F. Pb</th><th class="text-right">K.F. Ag</th>
                            <th class="text-right">V.Neto $us</th><th class="text-right">V.Neto Bs</th>
                            <th class="text-right">Ret. Ley</th><th class="text-right">Otras Ret.</th>
                            <th class="text-right">Ant./Ent.</th><th class="text-right">Líq. Pagable</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${filas}" var="f">
                            <tr>
                                <td><g:formatDate date="${f.fecha}" format="dd/MM/yyyy"/></td>
                                <td>${f.numero}</td><td>${f.empresa}</td><td>${f.cliente}</td><td>${f.lote}</td>
                                <td class="text-right">${f.sacos}</td>
                                <td class="text-right">${n2(f.pesoBruto)}</td>
                                <td class="text-right">${n2(f.kns)}</td>
                                <td class="text-right">${n2(f.leyZn)}</td>
                                <td class="text-right">${n2(f.leyPb)}</td>
                                <td class="text-right">${n2(f.leyAg)}</td>
                                <td class="text-right">${n2(f.kfZn)}</td>
                                <td class="text-right">${n2(f.kfPb)}</td>
                                <td class="text-right">${n2(f.kfAg)}</td>
                                <td class="text-right">${n2(f.vNetoUsd)}</td>
                                <td class="text-right">${n2(f.vNetoBs)}</td>
                                <td class="text-right">${n2(f.retLey)}</td>
                                <td class="text-right">${n2(f.otrasRet)}</td>
                                <td class="text-right">${n2(f.antEnt)}</td>
                                <td class="text-right">${n2(f.liquido)}</td>
                            </tr>
                        </g:each>
                        </tbody>
                        <tfoot class="font-weight-bold table-light">
                        <tr>
                            <td colspan="5" class="text-right">Totales (${filas.size()})</td>
                            <td class="text-right">${tot.sacos}</td>
                            <td class="text-right">${n2(tot.pesoBruto)}</td>
                            <td class="text-right">${n2(tot.kns)}</td>
                            <td colspan="3"></td>
                            <td class="text-right">${n2(tot.kfZn)}</td>
                            <td class="text-right">${n2(tot.kfPb)}</td>
                            <td class="text-right">${n2(tot.kfAg)}</td>
                            <td class="text-right">${n2(tot.vNetoUsd)}</td>
                            <td class="text-right">${n2(tot.vNetoBs)}</td>
                            <td class="text-right">${n2(tot.retLey)}</td>
                            <td class="text-right">${n2(tot.otrasRet)}</td>
                            <td class="text-right">${n2(tot.antEnt)}</td>
                            <td class="text-right">${n2(tot.liquido)}</td>
                        </tr>
                        <tr class="table-light">
                            <td colspan="5" class="text-right">Promedios ponderados — Humedad: ${n2(prom.hum)} %</td>
                            <td colspan="3"></td>
                            <td class="text-right">${n2(prom.zn)}</td>
                            <td class="text-right">${n2(prom.pb)}</td>
                            <td class="text-right">${n2(prom.ag)}</td>
                            <td colspan="9"></td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
                <div class="alert alert-info py-2 mt-3 mb-0">
                    <i class="fas fa-info-circle mr-1"></i>El <strong>total de Líquido Pagable</strong> no incluye las liquidaciones con líquido pagable <strong>menor a cero</strong> (saldo en contra).
                </div>
                <div class="mt-3">
                    <g:link action="exportarExcel"
                            params="${[empresaId: empresa?.id, clienteId: cliente?.id, fi: fechaInicial?.format('yyyy-MM-dd'), ff: fechaFinal?.format('yyyy-MM-dd')]}"
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
        // Empresa y Cliente: Select2 con búsqueda ASÍNCRONA (las listas crecen; no se renderizan completas).
        // extra: función opcional que aporta parámetros adicionales a la consulta (p. ej. empresaId)
        function select2Ajax(sel, url, etiquetaTodos, extra) {
            $(sel).select2({
                language: 'es', width: '100%', minimumInputLength: 1, allowClear: true,
                placeholder: etiquetaTodos,
                ajax: {
                    url: url, dataType: 'json', delay: 250,
                    data: function (p) { var d = { q: p.term }; if (extra) Object.assign(d, extra()); return d; },
                    processResults: function (d) { return { results: [{ id: '', text: etiquetaTodos }].concat(d.results || []) }; },
                    cache: false
                }
            });
        }
        if ($.fn.select2) {
            select2Ajax('#empresaSelect', '${createLink(controller: "empresa", action: "empresaBusquedaJSON")}', '(Todas)');
            // Cliente en cascada: filtra por la Empresa elegida (si hay)
            select2Ajax('#clienteSelect', '${createLink(controller: "cliente", action: "clientesBusquedaJSON")}', '(Todos)',
                function () { return { empresaId: $('#empresaSelect').val() }; });
            $('#empresaSelect').on('change', function () { $('#clienteSelect').val(null).trigger('change'); });
        }
    });
</script>
</body>
</html>
