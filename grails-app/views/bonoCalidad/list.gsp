<%@ page import="org.socymet.proveedor.bonos.BonoCalidad" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Bono Calidad</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Bono Calidad</h3>
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
					
						<th><g:message code="bonoCalidad.empresa.label" default="Empresa" /></th>
					
						<g:sortableColumn property="elemento" title="${message(code: 'bonoCalidad.elemento.label', default: 'Elemento')}" />
					
						<g:sortableColumn property="simboloElemento" title="${message(code: 'bonoCalidad.simboloElemento.label', default: 'Simbolo Elemento')}" />
					
						<g:sortableColumn property="leyMinima" title="${message(code: 'bonoCalidad.leyMinima.label', default: 'Ley Minima')}" />
					
						<g:sortableColumn property="leyMaxima" title="${message(code: 'bonoCalidad.leyMaxima.label', default: 'Ley Maxima')}" />
					
						<g:sortableColumn property="bono" title="${message(code: 'bonoCalidad.bono.label', default: 'Bono')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${bonoCalidadInstanceList}" var="bonoCalidadInstance">
					<tr>
					
						<td><g:link action="show" id="${bonoCalidadInstance.id}">${fieldValue(bean: bonoCalidadInstance, field: "empresa")}</g:link></td>
					
						<td>${fieldValue(bean: bonoCalidadInstance, field: "elemento")}</td>
					
						<td>${fieldValue(bean: bonoCalidadInstance, field: "simboloElemento")}</td>
					
						<td>${fieldValue(bean: bonoCalidadInstance, field: "leyMinima")}</td>
					
						<td>${fieldValue(bean: bonoCalidadInstance, field: "leyMaxima")}</td>
					
						<td>${fieldValue(bean: bonoCalidadInstance, field: "bono")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!bonoCalidadInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${bonoCalidadInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
