<%@ page import="org.socymet.proveedor.bonos.BonoCantidad" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Bono Cantidad</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Bono Cantidad</h3>
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
					
						<th><g:message code="bonoCantidad.empresa.label" default="Empresa" /></th>
					
						<g:sortableColumn property="elemento" title="${message(code: 'bonoCantidad.elemento.label', default: 'Elemento')}" />
					
						<g:sortableColumn property="simboloElemento" title="${message(code: 'bonoCantidad.simboloElemento.label', default: 'Simbolo Elemento')}" />
					
						<g:sortableColumn property="cantidadMinima" title="${message(code: 'bonoCantidad.cantidadMinima.label', default: 'Cantidad Minima')}" />
					
						<g:sortableColumn property="cantidadMaxima" title="${message(code: 'bonoCantidad.cantidadMaxima.label', default: 'Cantidad Maxima')}" />
					
						<g:sortableColumn property="bono" title="${message(code: 'bonoCantidad.bono.label', default: 'Bono')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${bonoCantidadInstanceList}" var="bonoCantidadInstance">
					<tr>
					
						<td><g:link action="show" id="${bonoCantidadInstance.id}">${fieldValue(bean: bonoCantidadInstance, field: "empresa")}</g:link></td>
					
						<td>${fieldValue(bean: bonoCantidadInstance, field: "elemento")}</td>
					
						<td>${fieldValue(bean: bonoCantidadInstance, field: "simboloElemento")}</td>
					
						<td>${fieldValue(bean: bonoCantidadInstance, field: "cantidadMinima")}</td>
					
						<td>${fieldValue(bean: bonoCantidadInstance, field: "cantidadMaxima")}</td>
					
						<td>${fieldValue(bean: bonoCantidadInstance, field: "bono")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!bonoCantidadInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${bonoCantidadInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
