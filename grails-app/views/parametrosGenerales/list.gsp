<%@ page import="org.smart.parametros.ParametrosGenerales" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Parametros Generales</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Parametros Generales</h3>
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
					
						<g:sortableColumn property="mesesPagablesBonoCantidad" title="${message(code: 'parametrosGenerales.mesesPagablesBonoCantidad.label', default: 'Meses Pagables Bono Cantidad')}" />
					
						<g:sortableColumn property="mesesPagablesBonoCalidad" title="${message(code: 'parametrosGenerales.mesesPagablesBonoCalidad.label', default: 'Meses Pagables Bono Calidad')}" />
					
						<g:sortableColumn property="leyMinimaPlataBonoCalidad" title="${message(code: 'parametrosGenerales.leyMinimaPlataBonoCalidad.label', default: 'Ley Minima Plata Bono Calidad')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${parametrosGeneralesInstanceList}" var="parametrosGeneralesInstance">
					<tr>
					
						<td><g:link action="show" id="${parametrosGeneralesInstance.id}">${fieldValue(bean: parametrosGeneralesInstance, field: "mesesPagablesBonoCantidad")}</g:link></td>
					
						<td>${fieldValue(bean: parametrosGeneralesInstance, field: "mesesPagablesBonoCalidad")}</td>
					
						<td>${fieldValue(bean: parametrosGeneralesInstance, field: "leyMinimaPlataBonoCalidad")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!parametrosGeneralesInstanceList}">
                    <tr><td colspan="3" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${parametrosGeneralesInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
