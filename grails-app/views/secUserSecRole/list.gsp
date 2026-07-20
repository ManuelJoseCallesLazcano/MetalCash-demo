<%@ page import="org.socymet.seguridad.SecUserSecRole" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Sec User Sec Role</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Sec User Sec Role</h3>
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
					
						<th><g:message code="secUserSecRole.secRole.label" default="Sec Role" /></th>
					
						<th><g:message code="secUserSecRole.secUser.label" default="Sec User" /></th>
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${secUserSecRoleInstanceList}" var="secUserSecRoleInstance">
					<tr>
					
						<td><g:link action="show" id="${secUserSecRoleInstance.id}">${fieldValue(bean: secUserSecRoleInstance, field: "secRole")}</g:link></td>
					
						<td>${fieldValue(bean: secUserSecRoleInstance, field: "secUser")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!secUserSecRoleInstanceList}">
                    <tr><td colspan="2" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${secUserSecRoleInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
