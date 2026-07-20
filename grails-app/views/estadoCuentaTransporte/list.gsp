<%@ page import="org.socymet.cancelacion.EstadoCuentaTransporte" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Estado Cuenta Transporte</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Estado Cuenta Transporte</h3>
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
					
						<g:sortableColumn property="solicitante" title="${message(code: 'estadoCuentaTransporte.solicitante.label', default: 'Solicitante')}" />
					
						<th><g:message code="estadoCuentaTransporte.empresa.label" default="Empresa" /></th>
					
						<th><g:message code="estadoCuentaTransporte.automovil.label" default="Automovil" /></th>
					
						<g:sortableColumn property="ci" title="${message(code: 'estadoCuentaTransporte.ci.label', default: 'Ci')}" />
					
						<g:sortableColumn property="nombreResponsable" title="${message(code: 'estadoCuentaTransporte.nombreResponsable.label', default: 'Nombre Responsable')}" />
					
						<g:sortableColumn property="fecha" title="${message(code: 'estadoCuentaTransporte.fecha.label', default: 'Fecha')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${estadoCuentaTransporteInstanceList}" var="estadoCuentaTransporteInstance">
					<tr>
					
						<td><g:link action="show" id="${estadoCuentaTransporteInstance.id}">${fieldValue(bean: estadoCuentaTransporteInstance, field: "solicitante")}</g:link></td>
					
						<td>${fieldValue(bean: estadoCuentaTransporteInstance, field: "empresa")}</td>
					
						<td>${fieldValue(bean: estadoCuentaTransporteInstance, field: "automovil")}</td>
					
						<td>${fieldValue(bean: estadoCuentaTransporteInstance, field: "ci")}</td>
					
						<td>${fieldValue(bean: estadoCuentaTransporteInstance, field: "nombreResponsable")}</td>
					
						<td><g:formatDate date="${estadoCuentaTransporteInstance.fecha}" /></td>
					
					</tr>
				</g:each>
				
                <g:if test="${!estadoCuentaTransporteInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${estadoCuentaTransporteInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
