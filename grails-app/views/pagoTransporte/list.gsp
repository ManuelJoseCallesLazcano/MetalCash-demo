<%@ page import="org.socymet.cancelacion.PagoTransporte" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Pago Transporte</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Pago Transporte</h3>
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

                        <g:sortableColumn property="numeroComprobante" title="${message(code: 'pagoTransporte.numeroComprobante.label', default: 'Comprobante')}" />

                        <g:sortableColumn property="solicitante" title="${message(code: 'pagoTransporte.solicitante.label', default: 'Solicitante')}" />
					
						<th><g:message code="pagoTransporte.empresa.label" default="Empresa" /></th>
					
						<th><g:message code="pagoTransporte.automovil.label" default="Automovil" /></th>
					
						<g:sortableColumn property="ci" title="${message(code: 'pagoTransporte.ci.label', default: 'Ci')}" />
					
						<g:sortableColumn property="nombreCobrador" title="${message(code: 'pagoTransporte.nombreCobrador.label', default: 'Nombre Cobrador')}" />
					
						<g:sortableColumn property="fechaDePago" title="${message(code: 'pagoTransporte.fechaDePago.label', default: 'Fecha De Pago')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${pagoTransporteInstanceList}" var="pagoTransporteInstance">
					<tr>

                        <td><g:link action="show" id="${pagoTransporteInstance.id}"><g:formatNumber number="${pagoTransporteInstance.numeroComprobante}" format="000000"/></g:link></td>

                        <td><g:link action="show" id="${pagoTransporteInstance.id}">${fieldValue(bean: pagoTransporteInstance, field: "solicitante")}</g:link></td>
					
						<td>${fieldValue(bean: pagoTransporteInstance, field: "empresa")}</td>
					
						<td>${fieldValue(bean: pagoTransporteInstance, field: "automovil")}</td>
					
						<td>${fieldValue(bean: pagoTransporteInstance, field: "ci")}</td>
					
						<td>${fieldValue(bean: pagoTransporteInstance, field: "nombreCobrador")}</td>
					
						<td><g:formatDate date="${pagoTransporteInstance.fechaDePago}" /></td>
					
					</tr>
				</g:each>
				
                <g:if test="${!pagoTransporteInstanceList}">
                    <tr><td colspan="7" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${pagoTransporteInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
