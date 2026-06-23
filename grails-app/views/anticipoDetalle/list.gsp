<%@ page import="org.socymet.anticipos.AnticipoDetalle" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Anticipo Detalle</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Anticipo Detalle</h3>
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
					
						<th><g:message code="anticipoDetalle.anticipo.label" default="Anticipo" /></th>
					
						<g:sortableColumn property="lote" title="${message(code: 'anticipoDetalle.lote.label', default: 'Lote')}" />
					
						<g:sortableColumn property="recepcionId" title="${message(code: 'anticipoDetalle.recepcionId.label', default: 'Recepcion Id')}" />
					
						<g:sortableColumn property="nombreCliente" title="${message(code: 'anticipoDetalle.nombreCliente.label', default: 'Nombre Cliente')}" />
					
						<g:sortableColumn property="nombreEmpresa" title="${message(code: 'anticipoDetalle.nombreEmpresa.label', default: 'Nombre Empresa')}" />
					
						<g:sortableColumn property="fechaDeRecepcion" title="${message(code: 'anticipoDetalle.fechaDeRecepcion.label', default: 'Fecha De Recepcion')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${anticipoDetalleInstanceList}" var="anticipoDetalleInstance">
					<tr>
					
						<td><g:link action="show" id="${anticipoDetalleInstance.id}">${fieldValue(bean: anticipoDetalleInstance, field: "anticipo")}</g:link></td>
					
						<td>${fieldValue(bean: anticipoDetalleInstance, field: "lote")}</td>
					
						<td>${fieldValue(bean: anticipoDetalleInstance, field: "recepcionId")}</td>
					
						<td>${fieldValue(bean: anticipoDetalleInstance, field: "nombreCliente")}</td>
					
						<td>${fieldValue(bean: anticipoDetalleInstance, field: "nombreEmpresa")}</td>
					
						<td>${fieldValue(bean: anticipoDetalleInstance, field: "fechaDeRecepcion")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!anticipoDetalleInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${anticipoDetalleInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
