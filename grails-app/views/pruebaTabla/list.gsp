<%@ page import="org.socymet.proveedor.PruebaTabla" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Prueba Tabla</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Prueba Tabla</h3>
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
					
						<g:sortableColumn property="nombreDeTabla" title="${message(code: 'pruebaTabla.nombreDeTabla.label', default: 'Nombre De Tabla')}" />
					
						<g:sortableColumn property="contenido" title="${message(code: 'pruebaTabla.contenido.label', default: 'Contenido')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${pruebaTablaInstanceList}" var="pruebaTablaInstance">
					<tr>
					
						<td><g:link action="show" id="${pruebaTablaInstance.id}">${fieldValue(bean: pruebaTablaInstance, field: "nombreDeTabla")}</g:link></td>
					
						<td>${fieldValue(bean: pruebaTablaInstance, field: "contenido")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!pruebaTablaInstanceList}">
                    <tr><td colspan="2" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${pruebaTablaInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
