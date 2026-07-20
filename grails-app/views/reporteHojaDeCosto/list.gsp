<%@ page import="org.socymet.org.socymet.reportes.ReporteHojaDeCosto" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Reporte Hoja De Costo</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Reporte Hoja De Costo</h3>
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
					
						<g:sortableColumn property="elemento" title="${message(code: 'reporteHojaDeCosto.elemento.label', default: 'Elemento')}" />
					
						<g:sortableColumn property="nombreDelConjunto" title="${message(code: 'reporteHojaDeCosto.nombreDelConjunto.label', default: 'Nombre Del Conjunto')}" />
					
						<g:sortableColumn property="fechaInicial" title="${message(code: 'reporteHojaDeCosto.fechaInicial.label', default: 'Fecha Inicial')}" />

                        <g:sortableColumn property="fechaFinal" title="${message(code: 'reporteHojaDeCosto.fechaFinal.label', default: 'Fecha Final')}" />

                        <g:sortableColumn property="loteInicial" title="${message(code: 'reporteHojaDeCosto.loteInicial.label', default: 'Lote Inicial')}" />

                        <g:sortableColumn property="loteFinal" title="${message(code: 'reporteHojaDeCosto.loteFinal.label', default: 'Lote Final')}" />
					
						<th><g:message code="reporteHojaDeCosto.empresa.label" default="Empresa" /></th>
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${reporteHojaDeCostoInstanceList}" var="reporteHojaDeCostoInstance">
					<tr>
					
						<td><g:link action="show" id="${reporteHojaDeCostoInstance.id}">${fieldValue(bean: reporteHojaDeCostoInstance, field: "elemento")}</g:link></td>
					
						<td>${fieldValue(bean: reporteHojaDeCostoInstance, field: "nombreDelConjunto")}</td>

                        <td><g:formatDate date="${reporteHojaDeCostoInstance.fechaInicial}" format="dd/MM/yyyy"/></td>

                        <td><g:formatDate date="${reporteHojaDeCostoInstance.fechaFinal}" format="dd/MM/yyyy"/></td>

                        <td>${fieldValue(bean: reporteHojaDeCostoInstance, field: "loteInicial")}</td>

                        <td>${fieldValue(bean: reporteHojaDeCostoInstance, field: "loteFinal")}</td>
					
						<td>${fieldValue(bean: reporteHojaDeCostoInstance, field: "empresa")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!reporteHojaDeCostoInstanceList}">
                    <tr><td colspan="7" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${reporteHojaDeCostoInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
