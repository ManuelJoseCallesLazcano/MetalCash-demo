<%@ page import="org.socymet.cancelacion.LoteBonoTransporte" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Lote Bono Transporte</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Lote Bono Transporte</h3>
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
					
						<th><g:message code="loteBonoTransporte.pagoBonoTransporte.label" default="Pago Bono Transporte" /></th>
					
						<g:sortableColumn property="lote" title="${message(code: 'loteBonoTransporte.lote.label', default: 'Lote')}" />
					
						<g:sortableColumn property="fechaDeRecepcion" title="${message(code: 'loteBonoTransporte.fechaDeRecepcion.label', default: 'Fecha De Recepcion')}" />
					
						<g:sortableColumn property="nombreEmpresa" title="${message(code: 'loteBonoTransporte.nombreEmpresa.label', default: 'Nombre Empresa')}" />
					
						<g:sortableColumn property="nombreCliente" title="${message(code: 'loteBonoTransporte.nombreCliente.label', default: 'Nombre Cliente')}" />
					
						<g:sortableColumn property="kilosBrutos" title="${message(code: 'loteBonoTransporte.kilosBrutos.label', default: 'Kilos Brutos')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${loteBonoTransporteInstanceList}" var="loteBonoTransporteInstance">
					<tr>
					
						<td><g:link action="show" id="${loteBonoTransporteInstance.id}">${fieldValue(bean: loteBonoTransporteInstance, field: "pagoBonoTransporte")}</g:link></td>
					
						<td>${fieldValue(bean: loteBonoTransporteInstance, field: "lote")}</td>
					
						<td><g:formatDate date="${loteBonoTransporteInstance.fechaDeRecepcion}" /></td>
					
						<td>${fieldValue(bean: loteBonoTransporteInstance, field: "nombreEmpresa")}</td>
					
						<td>${fieldValue(bean: loteBonoTransporteInstance, field: "nombreCliente")}</td>
					
						<td>${fieldValue(bean: loteBonoTransporteInstance, field: "kilosBrutos")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!loteBonoTransporteInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${loteBonoTransporteInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
