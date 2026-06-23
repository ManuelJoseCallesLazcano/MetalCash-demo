<%@ page import="org.socymet.anticipos.EstadoDeCuenta" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Estado De Cuenta</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Estado De Cuenta</h3>
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
					
						<th><g:message code="estadoDeCuenta.cliente.label" default="Cliente" /></th>
					
						<th><g:message code="estadoDeCuenta.empresa.label" default="Empresa" /></th>
					
						<g:sortableColumn property="ci" title="${message(code: 'estadoDeCuenta.ci.label', default: 'Ci')}" />
					
						<g:sortableColumn property="nombre" title="${message(code: 'estadoDeCuenta.nombre.label', default: 'Nombre')}" />
					
						<g:sortableColumn property="nombreEmpresa" title="${message(code: 'estadoDeCuenta.nombreEmpresa.label', default: 'Nombre Empresa')}" />
					
						<g:sortableColumn property="fecha" title="${message(code: 'estadoDeCuenta.fecha.label', default: 'Fecha')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${estadoDeCuentaInstanceList}" var="estadoDeCuentaInstance">
					<tr>
					
						<td><g:link action="show" id="${estadoDeCuentaInstance.id}">${fieldValue(bean: estadoDeCuentaInstance, field: "cliente")}</g:link></td>
					
						<td>${fieldValue(bean: estadoDeCuentaInstance, field: "empresa")}</td>
					
						<td>${fieldValue(bean: estadoDeCuentaInstance, field: "ci")}</td>
					
						<td>${fieldValue(bean: estadoDeCuentaInstance, field: "nombre")}</td>
					
						<td>${fieldValue(bean: estadoDeCuentaInstance, field: "nombreEmpresa")}</td>
					
						<td><g:formatDate date="${estadoDeCuentaInstance.fecha}" /></td>
					
					</tr>
				</g:each>
				
                <g:if test="${!estadoDeCuentaInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${estadoDeCuentaInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
