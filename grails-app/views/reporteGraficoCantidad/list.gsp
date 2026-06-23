<%@ page import="org.socymet.org.socymet.reportes.ReporteGraficoCantidad" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Reporte Grafico Cantidad</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Reporte Grafico Cantidad</h3>
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
					
						<th><g:message code="reporteGraficoCantidad.empresa.label" default="Empresa" /></th>
					
						<g:sortableColumn property="elemento" title="${message(code: 'reporteGraficoCantidad.elemento.label', default: 'Elemento')}" />
					
						<g:sortableColumn property="fechaInicial" title="${message(code: 'reporteGraficoCantidad.fechaInicial.label', default: 'Fecha Inicial')}" />
					
						<g:sortableColumn property="fechaFinal" title="${message(code: 'reporteGraficoCantidad.fechaFinal.label', default: 'Fecha Final')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${reporteGraficoCantidadInstanceList}" var="reporteGraficoCantidadInstance">
					<tr>
					
						<td><g:link action="show" id="${reporteGraficoCantidadInstance.id}">${fieldValue(bean: reporteGraficoCantidadInstance, field: "empresa")}</g:link></td>
					
						<td>${fieldValue(bean: reporteGraficoCantidadInstance, field: "elemento")}</td>
					
						<td><g:formatDate date="${reporteGraficoCantidadInstance.fechaInicial}" /></td>
					
						<td><g:formatDate date="${reporteGraficoCantidadInstance.fechaFinal}" /></td>
					
					</tr>
				</g:each>
				
                <g:if test="${!reporteGraficoCantidadInstanceList}">
                    <tr><td colspan="4" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${reporteGraficoCantidadInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
