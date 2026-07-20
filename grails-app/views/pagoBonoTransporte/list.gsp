<%@ page import="org.socymet.cancelacion.PagoBonoTransporte" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Pago Bono Transporte</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Pago Bono Transporte</h3>
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
					
						<g:sortableColumn property="numeroComprobante" title="${message(code: 'pagoBonoTransporte.numeroComprobante.label', default: 'Numero Comprobante')}" />
					
						<g:sortableColumn property="ci" title="${message(code: 'pagoBonoTransporte.ci.label', default: 'Ci')}" />
					
						<g:sortableColumn property="nombreCobrador" title="${message(code: 'pagoBonoTransporte.nombreCobrador.label', default: 'Nombre Cobrador')}" />
					
						<th><g:message code="pagoBonoTransporte.automovil.label" default="Automovil" /></th>
					
						<g:sortableColumn property="fechaDePago" title="${message(code: 'pagoBonoTransporte.fechaDePago.label', default: 'Fecha De Pago')}" />

                        <g:sortableColumn property="totalKilosBrutos" title="${message(code: 'pagoBonoTransporte.totalKilosBrutos.label', default: 'Total Kilos Brutos')}" />

                        <g:sortableColumn property="totalPagable" title="${message(code: 'pagoBonoTransporte.totalPagable.label', default: 'Total Pagable')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${pagoBonoTransporteInstanceList}" var="pagoBonoTransporteInstance">
					<tr>
					
						<td><g:link action="show" id="${pagoBonoTransporteInstance.id}"><g:formatNumber number="${pagoBonoTransporteInstance.numeroComprobante}" format="000000"/></g:link></td>
					
						<td>${fieldValue(bean: pagoBonoTransporteInstance, field: "ci")}</td>
					
						<td>${fieldValue(bean: pagoBonoTransporteInstance, field: "nombreCobrador")}</td>
					
						<td>${fieldValue(bean: pagoBonoTransporteInstance, field: "automovil")}</td>
					
						<td><g:formatDate date="${pagoBonoTransporteInstance.fechaDePago}" /></td>

                        <td>${fieldValue(bean: pagoBonoTransporteInstance, field: "totalKilosBrutos")}</td>

                        <td>${fieldValue(bean: pagoBonoTransporteInstance, field: "totalPagable")}</td>
					</tr>
				</g:each>
				
                <g:if test="${!pagoBonoTransporteInstanceList}">
                    <tr><td colspan="7" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${pagoBonoTransporteInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
