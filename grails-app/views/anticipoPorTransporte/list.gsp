<%@ page import="org.socymet.anticipos.AnticipoPorTransporte" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Anticipo Por Transporte</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Anticipo Por Transporte</h3>
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
					
						<g:sortableColumn property="numeroComprobante" title="${message(code: 'anticipoPorTransporte.numeroComprobante.label', default: 'numeroComprobante')}" />

						<g:sortableColumn property="solicitante" title="${message(code: 'anticipoPorTransporte.solicitante.label', default: 'Solicitante')}" />

						<th><g:message code="anticipoPorTransporte.empresa.label" default="Empresa" /></th>
					
						<th><g:message code="anticipoPorTransporte.automovil.label" default="Automovil" /></th>
					
						<g:sortableColumn property="fecha" title="${message(code: 'anticipoPorTransporte.fecha.label', default: 'Fecha')}" />
					
						<g:sortableColumn property="ultimoSaldo" title="${message(code: 'anticipoPorTransporte.ultimoSaldo.label', default: 'Ultimo Saldo')}" />
					
						<g:sortableColumn property="importe" title="${message(code: 'anticipoPorTransporte.importe.label', default: 'Importe')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${anticipoPorTransporteInstanceList}" var="anticipoPorTransporteInstance">
					<tr>
					
						<td><g:link action="show" id="${anticipoPorTransporteInstance.id}"><g:formatNumber number="${anticipoPorTransporteInstance.numeroComprobante}" format="000000" /></g:link></td>

						<td><g:link action="show" id="${anticipoPorTransporteInstance.id}">${fieldValue(bean: anticipoPorTransporteInstance, field: "solicitante")}</g:link></td>

						<td>${fieldValue(bean: anticipoPorTransporteInstance, field: "empresa")}</td>
					
						<td>${fieldValue(bean: anticipoPorTransporteInstance, field: "automovil")}</td>
					
						<td><g:formatDate date="${anticipoPorTransporteInstance.fecha}" format="dd/MM/yyyy" /></td>
					
						<td>${fieldValue(bean: anticipoPorTransporteInstance, field: "ultimoSaldo")}</td>
					
						<td>${fieldValue(bean: anticipoPorTransporteInstance, field: "importe")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!anticipoPorTransporteInstanceList}">
                    <tr><td colspan="7" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${anticipoPorTransporteInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
