<%@ page import="org.socymet.cancelacion.DetallePagoTransporte" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Detalle Pago Transporte</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Detalle Pago Transporte</h3>
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
					
						<th><g:message code="detallePagoTransporte.pagoTransporte.label" default="Pago Transporte" /></th>
					
						<g:sortableColumn property="lote" title="${message(code: 'detallePagoTransporte.lote.label', default: 'Lote')}" />
					
						<g:sortableColumn property="recepcionId" title="${message(code: 'detallePagoTransporte.recepcionId.label', default: 'Recepcion Id')}" />
					
						<g:sortableColumn property="nombreChofer" title="${message(code: 'detallePagoTransporte.nombreChofer.label', default: 'Nombre Chofer')}" />
					
						<g:sortableColumn property="placaAutomovil" title="${message(code: 'detallePagoTransporte.placaAutomovil.label', default: 'Placa Automovil')}" />
					
						<g:sortableColumn property="fechaDeRecepcion" title="${message(code: 'detallePagoTransporte.fechaDeRecepcion.label', default: 'Fecha De Recepcion')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${detallePagoTransporteInstanceList}" var="detallePagoTransporteInstance">
					<tr>
					
						<td><g:link action="show" id="${detallePagoTransporteInstance.id}">${fieldValue(bean: detallePagoTransporteInstance, field: "pagoTransporte")}</g:link></td>
					
						<td>${fieldValue(bean: detallePagoTransporteInstance, field: "lote")}</td>
					
						<td>${fieldValue(bean: detallePagoTransporteInstance, field: "recepcionId")}</td>
					
						<td>${fieldValue(bean: detallePagoTransporteInstance, field: "nombreChofer")}</td>
					
						<td>${fieldValue(bean: detallePagoTransporteInstance, field: "placaAutomovil")}</td>
					
						<td>${fieldValue(bean: detallePagoTransporteInstance, field: "fechaDeRecepcion")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!detallePagoTransporteInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${detallePagoTransporteInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
