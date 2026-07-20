<%@ page import="org.socymet.cotizaciones.TerminosContratoPlomoPlata" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Terminos Contrato Plomo Plata</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Terminos Contrato Plomo Plata</h3>
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
					
						<g:sortableColumn property="nombreTerminosContrato" title="${message(code: 'terminosContratoPlomoPlata.nombreTerminosContrato.label', default: 'Nombre Terminos Contrato')}" />
					
						<g:sortableColumn property="deduccionUnitariaPlomo" title="${message(code: 'terminosContratoPlomoPlata.deduccionUnitariaPlomo.label', default: 'Deduccion Unitaria Plomo')}" />
					
						<g:sortableColumn property="deduccionUnitariaPlata" title="${message(code: 'terminosContratoPlomoPlata.deduccionUnitariaPlata.label', default: 'Deduccion Unitaria Plata')}" />
					
						<g:sortableColumn property="porcentajePagablePlomo" title="${message(code: 'terminosContratoPlomoPlata.porcentajePagablePlomo.label', default: 'Porcentaje Pagable Plomo')}" />
					
						<g:sortableColumn property="porcentajePagablePlata" title="${message(code: 'terminosContratoPlomoPlata.porcentajePagablePlata.label', default: 'Porcentaje Pagable Plata')}" />
					
						<g:sortableColumn property="maquila" title="${message(code: 'terminosContratoPlomoPlata.maquila.label', default: 'Maquila')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${terminosContratoPlomoPlataInstanceList}" var="terminosContratoPlomoPlataInstance">
					<tr>
					
						<td><g:link action="show" id="${terminosContratoPlomoPlataInstance.id}">${fieldValue(bean: terminosContratoPlomoPlataInstance, field: "nombreTerminosContrato")}</g:link></td>
					
						<td>${fieldValue(bean: terminosContratoPlomoPlataInstance, field: "deduccionUnitariaPlomo")}</td>
					
						<td>${fieldValue(bean: terminosContratoPlomoPlataInstance, field: "deduccionUnitariaPlata")}</td>
					
						<td>${fieldValue(bean: terminosContratoPlomoPlataInstance, field: "porcentajePagablePlomo")}</td>
					
						<td>${fieldValue(bean: terminosContratoPlomoPlataInstance, field: "porcentajePagablePlata")}</td>
					
						<td>${fieldValue(bean: terminosContratoPlomoPlataInstance, field: "maquila")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!terminosContratoPlomoPlataInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${terminosContratoPlomoPlataInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
