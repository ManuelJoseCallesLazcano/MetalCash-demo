<%@ page import="org.socymet.org.socymet.reportes.ReporteEscalaPreciosPlata" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Reporte Escala Precios Plata</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Reporte Escala Precios Plata</h3>
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
					
						<g:sortableColumn property="fechaCotizacion" title="${message(code: 'reporteEscalaPreciosPlata.fechaCotizacion.label', default: 'Fecha Cotizacion')}" />
					
						<g:sortableColumn property="cotizacionPlata" title="${message(code: 'reporteEscalaPreciosPlata.cotizacionPlata.label', default: 'Cotizacion Plata')}" />
					
						<th><g:message code="reporteEscalaPreciosPlata.tablaCotizacionPlata.label" default="Tabla Cotizacion Plata" /></th>
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${reporteEscalaPreciosPlataInstanceList}" var="reporteEscalaPreciosPlataInstance">
					<tr>
					
						<td><g:link action="show" id="${reporteEscalaPreciosPlataInstance.id}">${fieldValue(bean: reporteEscalaPreciosPlataInstance, field: "fechaCotizacion")}</g:link></td>
					
						<td>${fieldValue(bean: reporteEscalaPreciosPlataInstance, field: "cotizacionPlata")}</td>
					
						<td>${fieldValue(bean: reporteEscalaPreciosPlataInstance, field: "tablaCotizacionPlata")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!reporteEscalaPreciosPlataInstanceList}">
                    <tr><td colspan="3" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${reporteEscalaPreciosPlataInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
