<%@ page import="org.socymet.org.socymet.reportes.LibroRegaliasMineras" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Libro Regalias Mineras</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Libro Regalias Mineras</h3>
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
					
						<g:sortableColumn property="elemento" title="${message(code: 'libroRegaliasMineras.elemento.label', default: 'Elemento')}" />
					
						<g:sortableColumn property="fechaInicial" title="${message(code: 'libroRegaliasMineras.fechaInicial.label', default: 'Fecha Inicial')}" />
					
						<g:sortableColumn property="fechaFinal" title="${message(code: 'libroRegaliasMineras.fechaFinal.label', default: 'Fecha Final')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${libroRegaliasMinerasInstanceList}" var="libroRegaliasMinerasInstance">
					<tr>
					
						<td><g:link action="show" id="${libroRegaliasMinerasInstance.id}">${fieldValue(bean: libroRegaliasMinerasInstance, field: "elemento")}</g:link></td>
					
						<td><g:formatDate date="${libroRegaliasMinerasInstance.fechaInicial}" /></td>
					
						<td><g:formatDate date="${libroRegaliasMinerasInstance.fechaFinal}" /></td>
					
					</tr>
				</g:each>
				
                <g:if test="${!libroRegaliasMinerasInstanceList}">
                    <tr><td colspan="3" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${libroRegaliasMinerasInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
