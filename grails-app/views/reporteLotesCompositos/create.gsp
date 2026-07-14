<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Reporte de Lotes y Compósitos</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" type="text/css">
    <style>
        .select2-container--default .select2-selection--single { height: calc(1.5em + .75rem + 2px); padding: .375rem .75rem; border: 1px solid #ced4da; border-radius: .25rem; }
        .select2-container--default .select2-selection--single .select2-selection__rendered { padding: 0; line-height: 1.5; }
        .tabla-lotes { font-size: .8rem; }
        .tabla-lotes th { white-space: nowrap; }
    </style>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
</head>
<body>
<div class="card card-primary">
    <div class="card-header"><h3 class="card-title">Reporte de Lotes y Compósitos</h3></div>
    <div class="card-body">
        <g:if test="${flash.message}">
            <div class="alert alert-warning py-2">${flash.message}</div>
        </g:if>
        <div class="alert alert-info py-2">
            <i class="fas fa-info-circle mr-1"></i>Solo se consideran lotes que tengan <b>análisis de laboratorio registrado</b>.
        </div>
        <g:form action="create" method="get" name="filtroForm">
            <div class="form-row align-items-end">
                <div class="form-group col-md-3">
                    <label for="estado">Estado</label>
                    <g:select name="estado" id="estado" class="form-control" from="${['EN COMPOSITO','SIN COMPOSITO']}" value="${estado}" noSelection="['': '-- Seleccione --']"/>
                </div>
                <div class="form-group col-md-3" id="grupoComposito">
                    <label for="compositoId">Compósito</label>
                    <select name="compositoId" id="compositoId" class="form-control" style="width:100%">
                        <g:if test="${composito}"><option value="${composito.id}" selected>${composito.sigla}</option></g:if>
                    </select>
                </div>
                <div class="form-group col-md-3">
                    <label for="fechaInicial_picker">Fecha desde</label>
                    <g:datepickerUI name="fechaInicial" value="${fechaInicial}" class="form-control" placeholder="dd/mm/aaaa"/>
                </div>
                <div class="form-group col-md-3">
                    <label for="fechaFinal_picker">Fecha hasta</label>
                    <g:datepickerUI name="fechaFinal" value="${fechaFinal}" class="form-control" placeholder="dd/mm/aaaa"/>
                </div>
                <div class="form-group col-md-3">
                    <label for="empresaId">Empresa (opcional)</label>
                    <select name="empresaId" id="empresaId" class="form-control" style="width:100%">
                        <g:if test="${empresa}"><option value="${empresa.id}" selected>${empresa}</option></g:if>
                    </select>
                </div>
            </div>
            <button type="submit" class="btn btn-info"><i class="fas fa-search mr-1"></i>Buscar</button>
        </g:form>
    </div>
</div>

<g:if test="${resumen != null}">
    <div class="card card-outline card-secondary">
        <div class="card-header"><h3 class="card-title">Resultados (${resumen.cantidadLotes})</h3></div>
        <div class="card-body table-responsive p-0">
            <table class="table table-sm table-striped table-hover tabla-lotes mb-0">
                <thead class="thead-light"><tr>
                    <th>Lote</th><th>Empresa</th><th>Fecha</th><th class="text-right">P. Bruto [Kg]</th><th class="text-right">Humedad [%]</th>
                    <th class="text-right">PNS [Kg]</th><th class="text-right">Ley Zn [%]</th><th class="text-right">Ley Pb [%]</th><th class="text-right">Ley Ag [DM]</th>
                    <th class="text-center">Estado</th><th class="text-right">Valor Neto [Bs]</th><th class="text-right">Líquido [Bs]</th><th>Compósito</th>
                </tr></thead>
                <tbody>
                <g:each in="${resumen.lotes}" var="l">
                    <tr>
                        <td>${l.lote}</td>
                        <td>${l.nombreEmpresa}</td>
                        <td><g:formatDate date="${l.fechaDeRecepcion}" format="dd/MM/yyyy"/></td>
                        <td class="text-right"><g:formatNumber number="${l.pesoBruto}" minFractionDigits="2" maxFractionDigits="2"/></td>
                        <td class="text-right"><g:formatNumber number="${l.humedad}" minFractionDigits="2" maxFractionDigits="2"/></td>
                        <td class="text-right"><g:formatNumber number="${l.pns}" minFractionDigits="2" maxFractionDigits="2"/></td>
                        <td class="text-right"><g:formatNumber number="${l.leyZinc}" minFractionDigits="2" maxFractionDigits="2"/></td>
                        <td class="text-right"><g:formatNumber number="${l.leyPlomo}" minFractionDigits="2" maxFractionDigits="2"/></td>
                        <td class="text-right"><g:formatNumber number="${l.leyPlata}" minFractionDigits="2" maxFractionDigits="2"/></td>
                        <td class="text-center">
                            <g:if test="${l.liquidado}"><span class="badge badge-success">LIQUIDADO</span></g:if>
                            <g:else><span class="badge badge-secondary">NO LIQ.</span></g:else>
                        </td>
                        <td class="text-right">${l.liquidado ? formatNumber(number: l.valorNeto, minFractionDigits: 2, maxFractionDigits: 2) : '—'}</td>
                        <td class="text-right">${l.liquidado ? formatNumber(number: l.liquidoPagable, minFractionDigits: 2, maxFractionDigits: 2) : '—'}</td>
                        <td>${l.nombreComposito}</td>
                    </tr>
                </g:each>
                <g:if test="${!resumen.lotes}"><tr><td colspan="13" class="text-center text-muted py-2">Sin resultados para el filtro.</td></tr></g:if>
                </tbody>
                <tfoot class="font-weight-bold bg-light">
                    <tr>
                        <td colspan="3" class="text-right">TOTALES</td>
                        <td class="text-right"><g:formatNumber number="${resumen.totalPesoBruto}" minFractionDigits="2" maxFractionDigits="2"/></td>
                        <td></td>
                        <td class="text-right"><g:formatNumber number="${resumen.totalKilosNetosSecos}" minFractionDigits="2" maxFractionDigits="2"/></td>
                        <td colspan="4"></td>
                        <td class="text-right"><g:formatNumber number="${resumen.totalValorNeto}" minFractionDigits="2" maxFractionDigits="2"/></td>
                        <td class="text-right"><g:formatNumber number="${resumen.totalLiquidoPagable}" minFractionDigits="2" maxFractionDigits="2"/></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td colspan="3" class="text-right">PROMEDIOS PONDERADOS</td>
                        <td></td>
                        <td class="text-right"><g:formatNumber number="${resumen.humedadPromedio}" minFractionDigits="2" maxFractionDigits="2"/></td>
                        <td></td>
                        <td class="text-right"><g:formatNumber number="${resumen.leyPromedioZinc}" minFractionDigits="2" maxFractionDigits="2"/></td>
                        <td class="text-right"><g:formatNumber number="${resumen.leyPromedioPlomo}" minFractionDigits="2" maxFractionDigits="2"/></td>
                        <td class="text-right"><g:formatNumber number="${resumen.leyPromedioPlata}" minFractionDigits="2" maxFractionDigits="2"/></td>
                        <td colspan="4"></td>
                    </tr>
                </tfoot>
            </table>
        </div>
        <div class="card-footer">
            <g:link action="exportarExcel"
                    params="${[estado: estado, empresaId: empresa?.id, compositoId: composito?.id, fi: fechaInicial?.format('yyyy-MM-dd'), ff: fechaFinal?.format('yyyy-MM-dd')]}"
                    class="btn btn-success"><i class="fas fa-file-excel mr-1"></i>Exportar a Excel</g:link>
        </div>
    </div>
</g:if>

<script>
    $(function () {
        var urlEmp = '${createLink(controller: "empresa", action: "empresaBusquedaJSON")}';
        $('#empresaId').select2({
            language: 'es', width: '100%', allowClear: true, placeholder: '-TODAS-',
            ajax: { url: urlEmp, dataType: 'json', delay: 250, minimumInputLength: 1,
                data: function (params) { return { q: params.term }; },
                processResults: function (data) { return { results: data.results }; } }
        });

        var urlComp = '${createLink(controller: "reporteCompositoDeLotes", action: "compositosBusquedaJSON")}';
        $('#compositoId').select2({
            language: 'es', width: '100%', allowClear: true, placeholder: 'TODOS',
            ajax: { url: urlComp, dataType: 'json', delay: 250, minimumInputLength: 1,
                data: function (params) { return { q: params.term }; },
                processResults: function (data) { return { results: data.results }; } }
        });

        // El filtro de compósito solo aplica cuando el estado es EN COMPOSITO.
        function toggleComposito() {
            var en = document.getElementById('estado').value === 'EN COMPOSITO';
            document.getElementById('grupoComposito').style.display = en ? '' : 'none';
            if (!en) $('#compositoId').val(null).trigger('change');
        }
        document.getElementById('estado').addEventListener('change', toggleComposito);
        toggleComposito();
    });
</script>
</body>
</html>
