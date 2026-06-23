<%@ page import="org.socymet.org.socymet.reportes.ReporteAnticipos" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Reporte Anticipos</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Reporte Anticipos</h3>
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
					
						<th><g:message code="reporteAnticipos.empresa.label" default="Empresa" /></th>
					
						<th><g:message code="reporteAnticipos.cliente.label" default="Cliente" /></th>
					
						<g:sortableColumn property="fechaInicial" title="${message(code: 'reporteAnticipos.fechaInicial.label', default: 'Fecha Inicial')}" />
					
						<g:sortableColumn property="fechaFinal" title="${message(code: 'reporteAnticipos.fechaFinal.label', default: 'Fecha Final')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${reporteAnticiposInstanceList}" var="reporteAnticiposInstance">
					<tr>
					
						<td><g:link action="show" id="${reporteAnticiposInstance.id}">${fieldValue(bean: reporteAnticiposInstance, field: "empresa")}</g:link></td>
					
						<td>${fieldValue(bean: reporteAnticiposInstance, field: "cliente")}</td>
					
						<td><g:formatDate date="${reporteAnticiposInstance.fechaInicial}" /></td>
					
						<td><g:formatDate date="${reporteAnticiposInstance.fechaFinal}" /></td>
					
					</tr>
				</g:each>
				
                <g:if test="${!reporteAnticiposInstanceList}">
                    <tr><td colspan="4" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${reporteAnticiposInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
