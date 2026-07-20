<%@ page import="org.socymet.org.socymet.reportes.ReporteLotesLiquidadosPorRecepcion" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Reporte Lotes Liquidados Por Recepcion</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Reporte Lotes Liquidados Por Recepcion</h3>
        <div class="ml-auto">
            <g:link action="create" class="btn btn-primary btn-sm">
                <i class="fas fa-plus"></i> Nuevo
            </g:link>
        </div>
    </div>
    <div class="card-body p-0">
        <g:if test="${flash.message}">
            <div id="swalFlashMsg" style="display:none">${flash.message}</div>
            <script>
                document.addEventListener('DOMContentLoaded', function () {
                    Swal.fire({ icon: 'info', title: 'Información',
                        text: document.getElementById('swalFlashMsg').textContent.trim(),
                        confirmButtonText: 'Aceptar' });
                });
            </script>
        </g:if>
        <div class="table-responsive">
            <table class="table table-hover table-striped mb-0">
                <thead class="thead-light">

					<tr>
					
						<g:sortableColumn property="elemento" title="${message(code: 'reporteLotesLiquidadosPorRecepcion.elemento.label', default: 'Elemento')}" />
					
						<th><g:message code="reporteLotesLiquidadosPorRecepcion.empresa.label" default="Empresa" /></th>
					
						<g:sortableColumn property="fechaInicial" title="${message(code: 'reporteLotesLiquidadosPorRecepcion.fechaInicial.label', default: 'Fecha Inicial')}" />
					
						<g:sortableColumn property="fechaFinal" title="${message(code: 'reporteLotesLiquidadosPorRecepcion.fechaFinal.label', default: 'Fecha Final')}" />
					
						<g:sortableColumn property="loteInicial" title="${message(code: 'reporteLotesLiquidadosPorRecepcion.loteInicial.label', default: 'Lote Inicial')}" />
					
						<g:sortableColumn property="loteFinal" title="${message(code: 'reporteLotesLiquidadosPorRecepcion.loteFinal.label', default: 'Lote Final')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${reporteLotesLiquidadosPorRecepcionInstanceList}" var="reporteLotesLiquidadosPorRecepcionInstance">
					<tr>
					
						<td><g:link action="show" id="${reporteLotesLiquidadosPorRecepcionInstance.id}">${fieldValue(bean: reporteLotesLiquidadosPorRecepcionInstance, field: "elemento")}</g:link></td>
					
						<td>${fieldValue(bean: reporteLotesLiquidadosPorRecepcionInstance, field: "empresa")}</td>
					
						<td><g:formatDate date="${reporteLotesLiquidadosPorRecepcionInstance.fechaInicial}" /></td>
					
						<td><g:formatDate date="${reporteLotesLiquidadosPorRecepcionInstance.fechaFinal}" /></td>
					
						<td>${fieldValue(bean: reporteLotesLiquidadosPorRecepcionInstance, field: "loteInicial")}</td>
					
						<td>${fieldValue(bean: reporteLotesLiquidadosPorRecepcionInstance, field: "loteFinal")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!reporteLotesLiquidadosPorRecepcionInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${reporteLotesLiquidadosPorRecepcionInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
