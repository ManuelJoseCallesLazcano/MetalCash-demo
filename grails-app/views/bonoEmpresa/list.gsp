<%@ page import="org.socymet.proveedor.BonoEmpresa" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Bono Empresa</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Bono Empresa</h3>
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
					
						<th><g:message code="bono.empresa.label" default="Empresa" /></th>
					
						<g:sortableColumn property="bonoCalidadEstano" title="${message(code: 'bono.bonoCalidadEstano.label', default: 'BonoEmpresa Calidad Estano')}" />
					
						<g:sortableColumn property="bonoCantidadEstano" title="${message(code: 'bono.bonoCantidadEstano.label', default: 'BonoEmpresa Cantidad Estano')}" />
					
						<g:sortableColumn property="bonoIncentivoEstano" title="${message(code: 'bono.bonoIncentivoEstano.label', default: 'BonoEmpresa Incentivo Estano')}" />
					
						<g:sortableColumn property="bonoCalidadPlata" title="${message(code: 'bono.bonoCalidadPlata.label', default: 'BonoEmpresa Calidad Plata')}" />
					
						<g:sortableColumn property="bonoCantidadPlata" title="${message(code: 'bono.bonoCantidadPlata.label', default: 'BonoEmpresa Cantidad Plata')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${bonoInstanceList}" var="bonoInstance">
					<tr>
					
						<td><g:link action="show" id="${bonoInstance.id}">${fieldValue(bean: bonoInstance, field: "empresa")}</g:link></td>
					
						<td>${fieldValue(bean: bonoInstance, field: "bonoCalidadEstano")}</td>
					
						<td>${fieldValue(bean: bonoInstance, field: "bonoCantidadEstano")}</td>
					
						<td>${fieldValue(bean: bonoInstance, field: "bonoIncentivoEstano")}</td>
					
						<td>${fieldValue(bean: bonoInstance, field: "bonoCalidadPlata")}</td>
					
						<td>${fieldValue(bean: bonoInstance, field: "bonoCantidadPlata")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!bonoInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${bonoInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
