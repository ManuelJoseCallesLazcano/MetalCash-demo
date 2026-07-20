<%@ page import="org.socymet.org.socymet.reportes.PlanillaDeLiquidacion" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Planilla De Liquidacion</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Planilla De Liquidacion</h3>
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
					
						<th><g:message code="planillaDeLiquidacion.empresa.label" default="Empresa" /></th>
					
						<g:sortableColumn property="fechaInicial" title="${message(code: 'planillaDeLiquidacion.fechaInicial.label', default: 'Fecha Inicial')}" />
					
						<g:sortableColumn property="fechaFinal" title="${message(code: 'planillaDeLiquidacion.fechaFinal.label', default: 'Fecha Final')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${planillaDeLiquidacionInstanceList}" var="planillaDeLiquidacionInstance">
					<tr>
					
						<td><g:link action="show" id="${planillaDeLiquidacionInstance.id}">${fieldValue(bean: planillaDeLiquidacionInstance, field: "empresa")}</g:link></td>
					
						<td><g:formatDate date="${planillaDeLiquidacionInstance.fechaInicial}" /></td>
					
						<td><g:formatDate date="${planillaDeLiquidacionInstance.fechaFinal}" /></td>
					
					</tr>
				</g:each>
				
                <g:if test="${!planillaDeLiquidacionInstanceList}">
                    <tr><td colspan="3" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${planillaDeLiquidacionInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
