<%@ page import="org.socymet.seguridad.MiCuenta" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Mi Cuenta</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Mi Cuenta</h3>
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
					
						<g:sortableColumn property="nombre" title="${message(code: 'miCuenta.nombre.label', default: 'Nombre')}" />
					
						<g:sortableColumn property="cuenta" title="${message(code: 'miCuenta.cuenta.label', default: 'Cuenta')}" />
					
						<g:sortableColumn property="contrasena" title="${message(code: 'miCuenta.contrasena.label', default: 'Contrasena')}" />
					
						<g:sortableColumn property="confirmarContrasena" title="${message(code: 'miCuenta.confirmarContrasena.label', default: 'Confirmar Contrasena')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${miCuentaInstanceList}" var="miCuentaInstance">
					<tr>
					
						<td><g:link action="show" id="${miCuentaInstance.id}">${fieldValue(bean: miCuentaInstance, field: "nombre")}</g:link></td>
					
						<td>${fieldValue(bean: miCuentaInstance, field: "cuenta")}</td>
					
						<td>${fieldValue(bean: miCuentaInstance, field: "contrasena")}</td>
					
						<td>${fieldValue(bean: miCuentaInstance, field: "confirmarContrasena")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!miCuentaInstanceList}">
                    <tr><td colspan="4" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${miCuentaInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
