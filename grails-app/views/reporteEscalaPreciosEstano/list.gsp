<%@ page import="org.socymet.org.socymet.reportes.ReporteEscalaPreciosEstano" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Reporte Escala Precios Estano</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Reporte Escala Precios Estano</h3>
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
					
						<g:sortableColumn property="fechaCotizacion" title="${message(code: 'reporteEscalaPreciosEstano.fechaCotizacion.label', default: 'Fecha Cotizacion')}" />
					
						<g:sortableColumn property="cotizacionEstano" title="${message(code: 'reporteEscalaPreciosEstano.cotizacionEstano.label', default: 'Cotizacion Estano')}" />
					
						<th><g:message code="reporteEscalaPreciosEstano.tablaCotizacionEstano.label" default="Tabla Cotizacion Estano" /></th>
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${reporteEscalaPreciosEstanoInstanceList}" var="reporteEscalaPreciosEstanoInstance">
					<tr>
					
						<td><g:link action="show" id="${reporteEscalaPreciosEstanoInstance.id}">${fieldValue(bean: reporteEscalaPreciosEstanoInstance, field: "fechaCotizacion")}</g:link></td>
					
						<td>${fieldValue(bean: reporteEscalaPreciosEstanoInstance, field: "cotizacionEstano")}</td>
					
						<td>${fieldValue(bean: reporteEscalaPreciosEstanoInstance, field: "tablaCotizacionEstano")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!reporteEscalaPreciosEstanoInstanceList}">
                    <tr><td colspan="3" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${reporteEscalaPreciosEstanoInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
