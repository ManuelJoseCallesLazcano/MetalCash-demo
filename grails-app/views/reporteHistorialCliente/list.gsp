<%@ page import="org.socymet.org.socymet.reportes.ReporteHistorialCliente" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Reporte Historial Cliente</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Reporte Historial Cliente</h3>
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
					
						<th><g:message code="reporteHistorialCliente.cliente.label" default="Cliente" /></th>
					
						<g:sortableColumn property="fechaInicial" title="${message(code: 'reporteHistorialCliente.fechaInicial.label', default: 'Fecha Inicial')}" />
					
						<g:sortableColumn property="fechaFinal" title="${message(code: 'reporteHistorialCliente.fechaFinal.label', default: 'Fecha Final')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${reporteHistorialClienteInstanceList}" var="reporteHistorialClienteInstance">
					<tr>
					
						<td><g:link action="show" id="${reporteHistorialClienteInstance.id}">${fieldValue(bean: reporteHistorialClienteInstance, field: "cliente")}</g:link></td>
					
						<td><g:formatDate date="${reporteHistorialClienteInstance.fechaInicial}" /></td>
					
						<td><g:formatDate date="${reporteHistorialClienteInstance.fechaFinal}" /></td>
					
					</tr>
				</g:each>
				
                <g:if test="${!reporteHistorialClienteInstanceList}">
                    <tr><td colspan="3" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${reporteHistorialClienteInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
