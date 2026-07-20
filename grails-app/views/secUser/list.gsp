<%@ page import="org.socymet.seguridad.SecUser" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Sec User</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Sec User</h3>
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
					
						<g:sortableColumn property="ci" title="${message(code: 'secUser.ci.label', default: 'Username')}" />
					
						<g:sortableColumn property="nombre" title="${message(code: 'secUser.nombre.label', default: 'Nombre')}" />
					
						%{--<g:sortableColumn property="username" title="${message(code: 'secUser.username.label', default: 'Username')}" />--}%
					
						<g:sortableColumn property="password" title="${message(code: 'secUser.password.label', default: 'Password')}" />
					
						<g:sortableColumn property="accountExpired" title="${message(code: 'secUser.accountExpired.label', default: 'Account Expired')}" />
					
						<g:sortableColumn property="accountLocked" title="${message(code: 'secUser.accountLocked.label', default: 'Account Locked')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${secUserInstanceList}" var="secUserInstance">
					<tr>
					
						<td><g:link action="show" id="${secUserInstance.id}">${fieldValue(bean: secUserInstance, field: "username")}</g:link></td>
					
						<td>${fieldValue(bean: secUserInstance, field: "nombre")}</td>
					
						%{--<td>${fieldValue(bean: secUserInstance, field: "username")}</td>--}%
					
						<td>${fieldValue(bean: secUserInstance, field: "password")}</td>
					
						<td><g:formatBoolean boolean="${secUserInstance.accountExpired}" /></td>
					
						<td><g:formatBoolean boolean="${secUserInstance.accountLocked}" /></td>
					
					</tr>
				</g:each>
				
                <g:if test="${!secUserInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${secUserInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
