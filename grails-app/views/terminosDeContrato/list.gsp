<%@ page import="org.socymet.cotizaciones.TerminosDeContrato" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Terminos De Contrato</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Terminos De Contrato</h3>
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
					
						<g:sortableColumn property="nombreContrato" title="${message(code: 'terminosDeContrato.nombreContrato.label', default: 'Nombre Contrato')}" />
					
						%{--<g:sortableColumn property="empresa" title="${message(code: 'terminosDeContrato.empresa.label', default: 'Empresa')}" />--}%
					
						%{--<g:sortableColumn property="porcentajeArsenico" title="${message(code: 'terminosDeContrato.porcentajeArsenico.label', default: 'Porcentaje Arsenico')}" />--}%
					%{----}%
						%{--<g:sortableColumn property="porcentajeAntimonio" title="${message(code: 'terminosDeContrato.porcentajeAntimonio.label', default: 'Porcentaje Antimonio')}" />--}%
					%{----}%
						%{--<g:sortableColumn property="porcentajeBismuto" title="${message(code: 'terminosDeContrato.porcentajeBismuto.label', default: 'Porcentaje Bismuto')}" />--}%
					%{----}%
						%{--<g:sortableColumn property="porcentajeEstano" title="${message(code: 'terminosDeContrato.porcentajeEstano.label', default: 'Porcentaje Estano')}" />--}%
					
					</tr>
				                </thead>
                <tbody>

                <g:each in="${terminosDeContratoInstanceList}" var="terminosDeContratoInstance">
					<tr>
					
						<td><g:link action="show" id="${terminosDeContratoInstance.id}">${fieldValue(bean: terminosDeContratoInstance, field: "nombreContrato")}</g:link></td>
					
						%{--<td>${fieldValue(bean: terminosDeContratoInstance, field: "empresa")}</td>--}%
					
						%{--<td>${fieldValue(bean: terminosDeContratoInstance, field: "porcentajeArsenico")}</td>--}%
					%{----}%
						%{--<td>${fieldValue(bean: terminosDeContratoInstance, field: "porcentajeAntimonio")}</td>--}%
					%{----}%
						%{--<td>${fieldValue(bean: terminosDeContratoInstance, field: "porcentajeBismuto")}</td>--}%
					%{----}%
						%{--<td>${fieldValue(bean: terminosDeContratoInstance, field: "porcentajeEstano")}</td>--}%
					
					</tr>
				</g:each>
				
                <g:if test="${!terminosDeContratoInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${terminosDeContratoInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
