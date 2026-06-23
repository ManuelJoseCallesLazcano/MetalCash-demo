<%@ page import="org.socymet.seguridad.SecRole" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Sec Role</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Sec Role</h3>
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
					
						<g:sortableColumn property="authority" title="${message(code: 'secRole.authority.label', default: 'Authority')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${secRoleInstanceList}" var="secRoleInstance">
					<tr>
					
						<td><g:link action="show" id="${secRoleInstance.id}">${fieldValue(bean: secRoleInstance, field: "authority")}</g:link></td>
					
					</tr>
				</g:each>
				
                <g:if test="${!secRoleInstanceList}">
                    <tr><td colspan="1" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${secRoleInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
