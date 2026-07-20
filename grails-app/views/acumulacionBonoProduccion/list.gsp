<%@ page import="org.socymet.cancelacion.AcumulacionBonoProduccion" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Acumulacion Bono Produccion</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Acumulacion Bono Produccion</h3>
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
					
						<th><g:message code="acumulacionBonoProduccion.pagoBonoProduccion.label" default="Pago Bono Produccion" /></th>
					
						<g:sortableColumn property="fecha" title="${message(code: 'acumulacionBonoProduccion.fecha.label', default: 'Fecha')}" />
					
						<g:sortableColumn property="tipoSeleccion" title="${message(code: 'acumulacionBonoProduccion.tipoSeleccion.label', default: 'Tipo Seleccion')}" />
					
						<th><g:message code="acumulacionBonoProduccion.cliente.label" default="Cliente" /></th>
					
						<th><g:message code="acumulacionBonoProduccion.empresa.label" default="Empresa" /></th>
					
						<th><g:message code="acumulacionBonoProduccion.cuadrilla.label" default="Cuadrilla" /></th>
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${acumulacionBonoProduccionInstanceList}" var="acumulacionBonoProduccionInstance">
					<tr>
					
						<td><g:link action="show" id="${acumulacionBonoProduccionInstance.id}">${fieldValue(bean: acumulacionBonoProduccionInstance, field: "pagoBonoProduccion")}</g:link></td>
					
						<td><g:formatDate date="${acumulacionBonoProduccionInstance.fecha}" /></td>
					
						<td>${fieldValue(bean: acumulacionBonoProduccionInstance, field: "tipoSeleccion")}</td>
					
						<td>${fieldValue(bean: acumulacionBonoProduccionInstance, field: "cliente")}</td>
					
						<td>${fieldValue(bean: acumulacionBonoProduccionInstance, field: "empresa")}</td>
					
						<td>${fieldValue(bean: acumulacionBonoProduccionInstance, field: "cuadrilla")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!acumulacionBonoProduccionInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${acumulacionBonoProduccionInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
