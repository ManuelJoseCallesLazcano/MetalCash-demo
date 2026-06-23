<%@ page import="org.socymet.org.socymet.reportes.PresupuestoLotesPorPagar" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Presupuesto Lotes Por Pagar</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Presupuesto Lotes Por Pagar</h3>
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
					
						<g:sortableColumn property="elemento" title="${message(code: 'presupuestoLotesPorPagar.elemento.label', default: 'Elemento')}" />
					
						<th><g:message code="presupuestoLotesPorPagar.empresa.label" default="Empresa" /></th>
					
						<g:sortableColumn property="fechaInicial" title="${message(code: 'presupuestoLotesPorPagar.fechaInicial.label', default: 'Fecha Inicial')}" />
					
						<g:sortableColumn property="fechaFinal" title="${message(code: 'presupuestoLotesPorPagar.fechaFinal.label', default: 'Fecha Final')}" />
					
						<g:sortableColumn property="loteInicial" title="${message(code: 'presupuestoLotesPorPagar.loteInicial.label', default: 'Lote Inicial')}" />
					
						<g:sortableColumn property="loteFinal" title="${message(code: 'presupuestoLotesPorPagar.loteFinal.label', default: 'Lote Final')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${presupuestoLotesPorPagarInstanceList}" var="presupuestoLotesPorPagarInstance">
					<tr>
					
						<td><g:link action="show" id="${presupuestoLotesPorPagarInstance.id}">${fieldValue(bean: presupuestoLotesPorPagarInstance, field: "elemento")}</g:link></td>
					
						<td>${fieldValue(bean: presupuestoLotesPorPagarInstance, field: "empresa")}</td>
					
						<td><g:formatDate date="${presupuestoLotesPorPagarInstance.fechaInicial}" /></td>
					
						<td><g:formatDate date="${presupuestoLotesPorPagarInstance.fechaFinal}" /></td>
					
						<td>${fieldValue(bean: presupuestoLotesPorPagarInstance, field: "loteInicial")}</td>
					
						<td>${fieldValue(bean: presupuestoLotesPorPagarInstance, field: "loteFinal")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!presupuestoLotesPorPagarInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${presupuestoLotesPorPagarInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
