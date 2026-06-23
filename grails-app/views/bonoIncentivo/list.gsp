<%@ page import="org.socymet.proveedor.bonos.BonoIncentivo" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Bono Incentivo</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Bono Incentivo</h3>
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
					
						<th><g:message code="bonoIncentivo.empresa.label" default="Empresa" /></th>
					
						<g:sortableColumn property="elemento" title="${message(code: 'bonoIncentivo.elemento.label', default: 'Elemento')}" />
					
						<g:sortableColumn property="simboloElemento" title="${message(code: 'bonoIncentivo.simboloElemento.label', default: 'Simbolo Elemento')}" />
					
						<g:sortableColumn property="leyMinima" title="${message(code: 'bonoIncentivo.leyMinima.label', default: 'Ley Minima')}" />
					
						<g:sortableColumn property="leyMaxima" title="${message(code: 'bonoIncentivo.leyMaxima.label', default: 'Ley Maxima')}" />
					
						<g:sortableColumn property="cantidadMinima" title="${message(code: 'bonoIncentivo.cantidadMinima.label', default: 'Cantidad Minima')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${bonoIncentivoInstanceList}" var="bonoIncentivoInstance">
					<tr>
					
						<td><g:link action="show" id="${bonoIncentivoInstance.id}">${fieldValue(bean: bonoIncentivoInstance, field: "empresa")}</g:link></td>
					
						<td>${fieldValue(bean: bonoIncentivoInstance, field: "elemento")}</td>
					
						<td>${fieldValue(bean: bonoIncentivoInstance, field: "simboloElemento")}</td>
					
						<td>${fieldValue(bean: bonoIncentivoInstance, field: "leyMinima")}</td>
					
						<td>${fieldValue(bean: bonoIncentivoInstance, field: "leyMaxima")}</td>
					
						<td>${fieldValue(bean: bonoIncentivoInstance, field: "cantidadMinima")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!bonoIncentivoInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${bonoIncentivoInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
