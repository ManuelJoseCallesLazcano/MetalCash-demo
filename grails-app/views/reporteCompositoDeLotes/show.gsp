<%@ page import="org.socymet.org.socymet.reportes.ReporteCompositoDeLotes" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Compósito ${reporteCompositoDeLotesInstance?.sigla}</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
    <style>
        .tabla-lotes { font-size: .8rem; }
        .tabla-lotes th { white-space: nowrap; }
        .resumen-box { border:1px solid #dee2e6; border-radius:6px; padding:10px 14px; text-align:center; background:#f8f9fa; }
        .resumen-box .valor { font-size:1.15rem; font-weight:700; color:#2c3e50; }
        .resumen-box .rotulo { font-size:.7rem; text-transform:uppercase; letter-spacing:.05em; color:#6c757d; }
    </style>
</head>
<body>
<g:set var="i" value="${reporteCompositoDeLotesInstance}"/>

<g:if test="${flash.message}">
    <div id="swalFlashMsg" style="display:none">${flash.message}</div>
    <script>document.addEventListener('DOMContentLoaded', function () { Swal.fire({ icon: '${flash.swalIcon ?: 'success'}', title: '${flash.swalTitle ?: 'Información'}', text: document.getElementById('swalFlashMsg').textContent.trim(), confirmButtonText: 'Aceptar' }); });</script>
</g:if>

<div class="card card-outline card-info">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title mr-auto">Compósito ${i?.sigla}
            <g:if test="${i?.numeroComposito}"><span class="badge badge-info ml-1">N° ${i.numeroComposito}<g:if test="${i?.gestionMinera}">/<g:formatDate date="${i.gestionMinera}" format="yy"/></g:if></span></g:if>
            <g:if test="${i?.anulado}"><span class="badge badge-dark ml-1">ANULADO</span></g:if>
            <g:elseif test="${i?.estadoDelComposito == 'DEFINITIVO'}"><span class="badge badge-success ml-1">DEFINITIVO</span></g:elseif>
            <g:else><span class="badge badge-warning ml-1">PROVISIONAL</span></g:else>
        </h3>
        <g:link action="list" class="btn btn-secondary btn-sm mr-1"><i class="fas fa-list mr-1"></i>Lista</g:link>
        <g:link controller="reporteCompositoDeLotes" action="exportarExcel" id="${i.id}" class="btn btn-outline-success btn-sm"><i class="fas fa-file-excel mr-1"></i>Exportar</g:link>
    </div>

    <div class="card-body">
        <dl class="row mb-0">
            <dt class="col-sm-3">N° / Gestión</dt><dd class="col-sm-3">${i?.numeroComposito ?: '-'} <g:if test="${i?.gestionMinera}">/ ${i.gestionMinera.format('yyyy')}</g:if></dd>
            <dt class="col-sm-3">Destino</dt><dd class="col-sm-3">${i?.destino} — ${i?.nombreDestino}</dd>

            <dt class="col-sm-3">${i?.destino == 'INGENIO' ? 'Ingenio' : 'Comprador'}</dt>
            <dd class="col-sm-3">${i?.destino == 'INGENIO' ? (i?.ingenio?.toString() ?: '-') : (i?.comprador?.toString() ?: '-')}</dd>
            <dt class="col-sm-3">Elaborado por</dt><dd class="col-sm-3">${i?.elaboradoPor}</dd>

            <dt class="col-sm-3">Fecha elaboración</dt><dd class="col-sm-3"><g:formatDate date="${i?.fechaDeElaboracion}" format="dd/MM/yyyy"/></dd>
            <dt class="col-sm-3">Estado</dt><dd class="col-sm-3">${i?.estadoDelComposito}${i?.anulado ? ' (ANULADO)' : ''}</dd>
        </dl>
        <g:if test="${i?.observaciones && i.observaciones != '-'}">
            <dl class="row mb-0 mt-1"><dt class="col-sm-3">Observaciones</dt><dd class="col-sm-9">${i?.observaciones}</dd></dl>
        </g:if>
    </div>

    <div class="card-footer d-flex align-items-center">
        <g:if test="${i?.estadoDelComposito == 'PROVISIONAL' && !i?.anulado}">
            <g:link action="edit" id="${i?.id}" class="btn btn-primary btn-sm"><i class="fas fa-edit mr-1"></i>Editar</g:link>
        </g:if>
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <g:if test="${!i?.anulado}">
                <g:if test="${i?.estadoDelComposito == 'DEFINITIVO'}">
                    <g:form action="reabrir" class="d-inline ml-1">
                        <g:hiddenField name="id" value="${i?.id}"/>
                        <button type="button" class="btn btn-outline-primary btn-sm btn-reabrir"><i class="fas fa-lock-open mr-1"></i>Reabrir</button>
                    </g:form>
                </g:if>
                <g:form action="anular" class="d-inline ml-1">
                    <g:hiddenField name="id" value="${i?.id}"/>
                    <button type="button" class="btn btn-danger btn-sm btn-anular"><i class="fas fa-ban mr-1"></i>Anular</button>
                </g:form>
            </g:if>
            <g:else><span class="text-muted ml-1"><i class="fas fa-ban mr-1"></i>Compósito anulado</span></g:else>
        </sec:ifAnyGranted>
    </div>
</div>

<%-- ── Lotes del compósito ── --%>
<div class="card card-outline card-secondary">
    <div class="card-header"><h3 class="card-title">Lotes del compósito (${detalle?.size() ?: 0})</h3></div>
    <div class="card-body table-responsive p-0">
        <table class="table table-sm table-striped table-hover tabla-lotes mb-0">
            <thead class="thead-light"><tr>
                <th>Lote</th><th>Empresa</th><th>Fecha</th><th class="text-right">P. Bruto [Kg]</th><th class="text-right">Humedad [%]</th>
                <th class="text-right">Ley Zn [%]</th><th class="text-right">Ley Pb [%]</th><th class="text-right">Ley Ag [DM]</th><th class="text-right">PNS [Kg]</th>
                <th class="text-right">K.F. Zn [Kg]</th><th class="text-right">K.F. Pb [Kg]</th><th class="text-right">K.F. Ag [Kg]</th>
                <th class="text-right">Valor Neto [Bs]</th><th class="text-right">Líquido [Bs]</th>
            </tr></thead>
            <tbody>
            <g:each in="${detalle}" var="d">
                <tr>
                    <td>${d.lote}</td><td>${d.nombreEmpresa}</td><td><g:formatDate date="${d.fechaDeRecepcion}" format="dd/MM/yyyy"/></td>
                    <td class="text-right"><g:formatNumber number="${d.pesoBruto}" minFractionDigits="2" maxFractionDigits="2"/></td>
                    <td class="text-right"><g:formatNumber number="${d.porcentajeHumedad}" minFractionDigits="2" maxFractionDigits="2"/></td>
                    <td class="text-right"><g:formatNumber number="${d.porcentajeZincFinal}" minFractionDigits="2" maxFractionDigits="2"/></td>
                    <td class="text-right"><g:formatNumber number="${d.porcentajePlomoFinal}" minFractionDigits="2" maxFractionDigits="2"/></td>
                    <td class="text-right"><g:formatNumber number="${d.porcentajePlataFinal}" minFractionDigits="2" maxFractionDigits="2"/></td>
                    <td class="text-right"><g:formatNumber number="${d.kilosNetosSecos}" minFractionDigits="2" maxFractionDigits="2"/></td>
                    <td class="text-right"><g:formatNumber number="${d.kilosFinosZinc}" minFractionDigits="2" maxFractionDigits="2"/></td>
                    <td class="text-right"><g:formatNumber number="${d.kilosFinosPlomo}" minFractionDigits="2" maxFractionDigits="2"/></td>
                    <td class="text-right"><g:formatNumber number="${d.kilosFinosPlata}" minFractionDigits="2" maxFractionDigits="2"/></td>
                    <td class="text-right">${(d.liquidacionId && d.liquidacionId != 0) ? formatNumber(number: d.valorNetoMineralEnBolivianos, minFractionDigits: 2, maxFractionDigits: 2) : '—'}</td>
                    <td class="text-right">${(d.liquidacionId && d.liquidacionId != 0) ? formatNumber(number: d.liquidoPagable, minFractionDigits: 2, maxFractionDigits: 2) : '—'}</td>
                </tr>
            </g:each>
            <g:if test="${!detalle}"><tr><td colspan="14" class="text-center text-muted py-2">Sin lotes.</td></tr></g:if>
            </tbody>
            <tfoot class="font-weight-bold bg-light"><tr>
                <td colspan="3" class="text-right">TOTALES / PONDERADOS</td>
                <td class="text-right"><g:formatNumber number="${i?.totalKilosBrutos}" minFractionDigits="2" maxFractionDigits="2"/></td>
                <td></td>
                <td class="text-right"><g:formatNumber number="${i?.leyPromedioZinc}" minFractionDigits="2" maxFractionDigits="2"/></td>
                <td class="text-right"><g:formatNumber number="${i?.leyPromedioPlomo}" minFractionDigits="2" maxFractionDigits="2"/></td>
                <td class="text-right"><g:formatNumber number="${i?.leyPromedioPlata}" minFractionDigits="2" maxFractionDigits="2"/></td>
                <td class="text-right"><g:formatNumber number="${i?.totalKilosNetosSecos}" minFractionDigits="2" maxFractionDigits="2"/></td>
                <td class="text-right"><g:formatNumber number="${i?.totalKilosFinosZinc}" minFractionDigits="2" maxFractionDigits="2"/></td>
                <td class="text-right"><g:formatNumber number="${i?.totalKilosFinosPlomo}" minFractionDigits="2" maxFractionDigits="2"/></td>
                <td class="text-right"><g:formatNumber number="${i?.totalKilosFinosPlata}" minFractionDigits="2" maxFractionDigits="2"/></td>
                <td class="text-right"><g:formatNumber number="${i?.totalValorNeto}" minFractionDigits="2" maxFractionDigits="2"/></td>
                <td class="text-right"><g:formatNumber number="${i?.totalLiquidoPagable}" minFractionDigits="2" maxFractionDigits="2"/></td>
            </tr></tfoot>
        </table>
    </div>
</div>

<%-- ── Resumen del compósito ── --%>
<g:set var="cantNoLiq" value="${detalle?.count { !(it.liquidacionId && it.liquidacionId != 0) } ?: 0}"/>
<div class="card card-success">
    <div class="card-header"><h3 class="card-title">Resumen del compósito</h3></div>
    <div class="card-body">
        <div class="form-row">
            <div class="col-md-3 mb-2"><div class="resumen-box"><div class="valor"><g:formatNumber number="${i?.totalKilosBrutos}" minFractionDigits="2" maxFractionDigits="2"/></div><div class="rotulo">Peso bruto [Kg]</div></div></div>
            <div class="col-md-3 mb-2"><div class="resumen-box"><div class="valor"><g:formatNumber number="${i?.totalKilosNetosSecos}" minFractionDigits="2" maxFractionDigits="2"/></div><div class="rotulo">Peso neto seco [Kg]</div></div></div>
            <div class="col-md-3 mb-2"><div class="resumen-box"><div class="valor"><g:formatNumber number="${i?.totalValorNeto}" minFractionDigits="2" maxFractionDigits="2"/></div><div class="rotulo">Valor neto [Bs]</div></div></div>
            <div class="col-md-3 mb-2"><div class="resumen-box"><div class="valor"><g:formatNumber number="${i?.totalLiquidoPagable}" minFractionDigits="2" maxFractionDigits="2"/></div><div class="rotulo">Líquido pagable [Bs]</div></div></div>
        </div>
        <div class="form-row">
            <div class="col-md-3 mb-2"><div class="resumen-box"><div class="valor"><g:formatNumber number="${i?.totalKilosFinosZinc}" minFractionDigits="2" maxFractionDigits="2"/></div><div class="rotulo">K. Finos Zn [Kg]</div></div></div>
            <div class="col-md-3 mb-2"><div class="resumen-box"><div class="valor"><g:formatNumber number="${i?.totalKilosFinosPlomo}" minFractionDigits="2" maxFractionDigits="2"/></div><div class="rotulo">K. Finos Pb [Kg]</div></div></div>
            <div class="col-md-3 mb-2"><div class="resumen-box"><div class="valor"><g:formatNumber number="${i?.totalKilosFinosPlata}" minFractionDigits="2" maxFractionDigits="2"/></div><div class="rotulo">K. Finos Ag [Kg]</div></div></div>
            <div class="col-md-3 mb-2"><div class="resumen-box"><div class="valor"><g:formatNumber number="${humedadPromedio}" minFractionDigits="2" maxFractionDigits="2"/></div><div class="rotulo">Humedad pond. [%]</div></div></div>
        </div>
        <div class="form-row">
            <div class="col-md-3 mb-2"><div class="resumen-box"><div class="valor"><g:formatNumber number="${i?.leyPromedioZinc}" minFractionDigits="2" maxFractionDigits="2"/></div><div class="rotulo">Ley Zn pond. [%]</div></div></div>
            <div class="col-md-3 mb-2"><div class="resumen-box"><div class="valor"><g:formatNumber number="${i?.leyPromedioPlomo}" minFractionDigits="2" maxFractionDigits="2"/></div><div class="rotulo">Ley Pb pond. [%]</div></div></div>
            <div class="col-md-3 mb-2"><div class="resumen-box"><div class="valor"><g:formatNumber number="${i?.leyPromedioPlata}" minFractionDigits="2" maxFractionDigits="2"/></div><div class="rotulo">Ley Ag pond. [DM]</div></div></div>
            <div class="col-md-3 mb-2"><div class="resumen-box"><div class="valor">${detalle?.size() ?: 0} / <span class="text-warning">${cantNoLiq}</span></div><div class="rotulo">Lotes / sin liquidar</div></div></div>
        </div>
        <div class="alert alert-light border small mb-0"><i class="fas fa-info-circle mr-1"></i>Valor neto y líquido pagable suman <b>solo lotes liquidados</b>; el contador indica cuántos están sin liquidar.</div>
    </div>
</div>

<script>
    $(function () {
        $('.btn-anular').on('click', function () {
            var form = this.form;
            Swal.fire({ title: '¿Anular este compósito?',
                html: 'Se liberarán los lotes reservados (volverán a estar disponibles) y el registro quedará marcado como ANULADO.',
                icon: 'warning', showCancelButton: true, confirmButtonColor: '#d33',
                confirmButtonText: 'Sí, anular', cancelButtonText: 'Cancelar', reverseButtons: true
            }).then(function (r) { if (r.isConfirmed) form.submit(); });
        });
        $('.btn-reabrir').on('click', function () {
            var form = this.form;
            Swal.fire({ title: '¿Reabrir este compósito?',
                html: 'Volverá a estado PROVISIONAL para poder editarlo. Los lotes siguen reservados.',
                icon: 'question', showCancelButton: true,
                confirmButtonText: 'Sí, reabrir', cancelButtonText: 'Cancelar', reverseButtons: true
            }).then(function (r) { if (r.isConfirmed) form.submit(); });
        });
    });
</script>
</body>
</html>
