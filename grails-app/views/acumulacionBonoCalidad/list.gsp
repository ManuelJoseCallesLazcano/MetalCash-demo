<%@ page import="org.socymet.cancelacion.AcumulacionBonoCalidad" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Acumulacion Bono Calidad</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Acumulacion Bono Calidad</h3>
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
					
						<th><g:message code="acumulacionBonoCalidad.pagoBonoCalidad.label" default="Pago Bono Calidad" /></th>
					
						<g:sortableColumn property="fecha" title="${message(code: 'acumulacionBonoCalidad.fecha.label', default: 'Fecha')}" />
					
						<g:sortableColumn property="tipoSeleccion" title="${message(code: 'acumulacionBonoCalidad.tipoSeleccion.label', default: 'Tipo Seleccion')}" />
					
						<th><g:message code="acumulacionBonoCalidad.cliente.label" default="Cliente" /></th>
					
						<th><g:message code="acumulacionBonoCalidad.empresa.label" default="Empresa" /></th>
					
						<th><g:message code="acumulacionBonoCalidad.cuadrilla.label" default="Cuadrilla" /></th>
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${acumulacionBonoCalidadInstanceList}" var="acumulacionBonoCalidadInstance">
					<tr>
					
						<td><g:link action="show" id="${acumulacionBonoCalidadInstance.id}">${fieldValue(bean: acumulacionBonoCalidadInstance, field: "pagoBonoCalidad")}</g:link></td>
					
						<td><g:formatDate date="${acumulacionBonoCalidadInstance.fecha}" /></td>
					
						<td>${fieldValue(bean: acumulacionBonoCalidadInstance, field: "tipoSeleccion")}</td>
					
						<td>${fieldValue(bean: acumulacionBonoCalidadInstance, field: "cliente")}</td>
					
						<td>${fieldValue(bean: acumulacionBonoCalidadInstance, field: "empresa")}</td>
					
						<td>${fieldValue(bean: acumulacionBonoCalidadInstance, field: "cuadrilla")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!acumulacionBonoCalidadInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${acumulacionBonoCalidadInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
