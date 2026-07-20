<%@ page import="org.socymet.org.socymet.reportes.ReportePagoDeTransporte" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Reporte Pago De Transporte</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Reporte Pago De Transporte</h3>
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
					
						<th><g:message code="reportePagoDeTransporte.deposito.label" default="Deposito" /></th>
					
						<g:sortableColumn property="elemento" title="${message(code: 'reportePagoDeTransporte.elemento.label', default: 'Elemento')}" />
					
						<th><g:message code="reportePagoDeTransporte.empresa.label" default="Empresa" /></th>
					
						<th><g:message code="reportePagoDeTransporte.automovil.label" default="Automovil" /></th>
					
						<g:sortableColumn property="fechaInicial" title="${message(code: 'reportePagoDeTransporte.fechaInicial.label', default: 'Fecha Inicial')}" />
					
						<g:sortableColumn property="fechaFinal" title="${message(code: 'reportePagoDeTransporte.fechaFinal.label', default: 'Fecha Final')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${reportePagoDeTransporteInstanceList}" var="reportePagoDeTransporteInstance">
					<tr>
					
						<td><g:link action="show" id="${reportePagoDeTransporteInstance.id}">${fieldValue(bean: reportePagoDeTransporteInstance, field: "deposito")}</g:link></td>
					
						<td>${fieldValue(bean: reportePagoDeTransporteInstance, field: "elemento")}</td>
					
						<td>${fieldValue(bean: reportePagoDeTransporteInstance, field: "empresa")}</td>
					
						<td>${fieldValue(bean: reportePagoDeTransporteInstance, field: "automovil")}</td>
					
						<td><g:formatDate date="${reportePagoDeTransporteInstance.fechaInicial}" /></td>
					
						<td><g:formatDate date="${reportePagoDeTransporteInstance.fechaFinal}" /></td>
					
					</tr>
				</g:each>
				
                <g:if test="${!reportePagoDeTransporteInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${reportePagoDeTransporteInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
