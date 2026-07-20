<%@ page import="org.socymet.org.socymet.reportes.CompositoDeLotesDetalle" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Composito De Lotes Detalle</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Composito De Lotes Detalle</h3>
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
					
						<th><g:message code="compositoDeLotesDetalle.reporteCompositoDeLotes.label" default="Reporte Composito De Lotes" /></th>
					
						<g:sortableColumn property="lote" title="${message(code: 'compositoDeLotesDetalle.lote.label', default: 'Lote')}" />
					
						<g:sortableColumn property="recepcionId" title="${message(code: 'compositoDeLotesDetalle.recepcionId.label', default: 'Recepcion Id')}" />
					
						<g:sortableColumn property="liquidacionId" title="${message(code: 'compositoDeLotesDetalle.liquidacionId.label', default: 'Liquidacion Id')}" />
					
						<g:sortableColumn property="nombreEmpresa" title="${message(code: 'compositoDeLotesDetalle.nombreEmpresa.label', default: 'Nombre Empresa')}" />
					
						<g:sortableColumn property="fechaDeRecepcion" title="${message(code: 'compositoDeLotesDetalle.fechaDeRecepcion.label', default: 'Fecha De Recepcion')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${compositoDeLotesDetalleInstanceList}" var="compositoDeLotesDetalleInstance">
					<tr>
					
						<td><g:link action="show" id="${compositoDeLotesDetalleInstance.id}">${fieldValue(bean: compositoDeLotesDetalleInstance, field: "reporteCompositoDeLotes")}</g:link></td>
					
						<td>${fieldValue(bean: compositoDeLotesDetalleInstance, field: "lote")}</td>
					
						<td>${fieldValue(bean: compositoDeLotesDetalleInstance, field: "recepcionId")}</td>
					
						<td>${fieldValue(bean: compositoDeLotesDetalleInstance, field: "liquidacionId")}</td>
					
						<td>${fieldValue(bean: compositoDeLotesDetalleInstance, field: "nombreEmpresa")}</td>
					
						<td><g:formatDate date="${compositoDeLotesDetalleInstance.fechaDeRecepcion}" /></td>
					
					</tr>
				</g:each>
				
                <g:if test="${!compositoDeLotesDetalleInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${compositoDeLotesDetalleInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
