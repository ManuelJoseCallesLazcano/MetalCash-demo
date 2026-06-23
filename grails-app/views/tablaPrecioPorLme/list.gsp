<%@ page import="org.socymet.cotizaciones.TablaPrecioPorLme" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Tabla Precio Por Lme</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Tabla Precio Por Lme</h3>
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
					
						<g:sortableColumn property="nombreTabla" title="${message(code: 'tablaPrecioPorLme.nombreTabla.label', default: 'Nombre Tabla')}" />
					
						<th><g:message code="tablaPrecioPorLme.empresa.label" default="Empresa" /></th>
					
						<g:sortableColumn property="naturalezaMineral" title="${message(code: 'tablaPrecioPorLme.naturalezaMineral.label', default: 'Naturaleza Mineral')}" />
					
						<th><g:message code="tablaPrecioPorLme.cotizacionDiariaDeMinerales.label" default="Cotizacion Diaria De Minerales" /></th>
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${tablaPrecioPorLmeInstanceList}" var="tablaPrecioPorLmeInstance">
					<tr>
					
						<td><g:link action="show" id="${tablaPrecioPorLmeInstance.id}">${fieldValue(bean: tablaPrecioPorLmeInstance, field: "nombreTabla")}</g:link></td>
					
						<td>${fieldValue(bean: tablaPrecioPorLmeInstance, field: "empresa")}</td>
					
						<td>${fieldValue(bean: tablaPrecioPorLmeInstance, field: "naturalezaMineral")}</td>
					
						<td>${fieldValue(bean: tablaPrecioPorLmeInstance, field: "cotizacionDiariaDeMinerales")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!tablaPrecioPorLmeInstanceList}">
                    <tr><td colspan="4" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${tablaPrecioPorLmeInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
