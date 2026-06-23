<%@ page import="org.socymet.cancelacion.AcumulacionBonoTransporte" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Acumulacion Bono Transporte</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Acumulacion Bono Transporte</h3>
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
					
						<th><g:message code="acumulacionBonoTransporte.pagoBonoTransporte.label" default="Pago Bono Transporte" /></th>
					
						<g:sortableColumn property="fecha" title="${message(code: 'acumulacionBonoTransporte.fecha.label', default: 'Fecha')}" />
					
						<th><g:message code="acumulacionBonoTransporte.automovil.label" default="Automovil" /></th>
					
						<g:sortableColumn property="cantidadAcumulada" title="${message(code: 'acumulacionBonoTransporte.cantidadAcumulada.label', default: 'Cantidad Acumulada')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${acumulacionBonoTransporteInstanceList}" var="acumulacionBonoTransporteInstance">
					<tr>
					
						<td><g:link action="show" id="${acumulacionBonoTransporteInstance.id}">${fieldValue(bean: acumulacionBonoTransporteInstance, field: "pagoBonoTransporte")}</g:link></td>
					
						<td><g:formatDate date="${acumulacionBonoTransporteInstance.fecha}" /></td>
					
						<td>${fieldValue(bean: acumulacionBonoTransporteInstance, field: "automovil")}</td>
					
						<td>${fieldValue(bean: acumulacionBonoTransporteInstance, field: "cantidadAcumulada")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!acumulacionBonoTransporteInstanceList}">
                    <tr><td colspan="4" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${acumulacionBonoTransporteInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
