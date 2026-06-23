<%@ page import="org.socymet.anticipos.Amortizacion" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Amortizacion</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Amortizacion</h3>
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
					
						<th><g:message code="amortizacion.numeroAmortizacion.label" default="numero Amortizacion" /></th>

						<th><g:message code="amortizacion.cliente.label" default="Cliente" /></th>

%{--						<th><g:message code="amortizacion.empresa.label" default="Empresa" /></th>--}%
					
%{--						<g:sortableColumn property="ci" title="${message(code: 'amortizacion.ci.label', default: 'Ci')}" />--}%
					
%{--						<g:sortableColumn property="nombre" title="${message(code: 'amortizacion.nombre.label', default: 'Nombre')}" />--}%
					
%{--						<g:sortableColumn property="nombreEmpresa" title="${message(code: 'amortizacion.nombreEmpresa.label', default: 'Nombre Empresa')}" />--}%
					
						<g:sortableColumn property="fecha" title="${message(code: 'amortizacion.fecha.label', default: 'Fecha')}" />

						<g:sortableColumn property="importe" title="${message(code: 'amortizacion.importe.label', default: 'Importe')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${amortizacionInstanceList}" var="amortizacionInstance">
					<tr>
					
						<td>
							<g:link action="show" id="${amortizacionInstance.id}">${amortizacionInstance}</g:link>
							<g:if test="${amortizacionInstance.anulado}"><span class="badge badge-danger ml-1">ANULADA</span></g:if>
						</td>

						<td>${amortizacionInstance.cliente?.nombre}</td>

%{--						<td>${fieldValue(bean: amortizacionInstance, field: "empresa")}</td>--}%
					
%{--						<td>${fieldValue(bean: amortizacionInstance, field: "ci")}</td>--}%
					
%{--						<td>${fieldValue(bean: amortizacionInstance, field: "nombre")}</td>--}%
					
%{--						<td>${fieldValue(bean: amortizacionInstance, field: "nombreEmpresa")}</td>--}%
					
						<td><g:formatDate date="${amortizacionInstance.fecha}" format="dd/MM/yyyy"/></td>

						<td><g:formatNumber number="${amortizacionInstance.importe ?: 0}" type="number" maxFractionDigits="2"/></td>
					
					</tr>
				</g:each>
				
                <g:if test="${!amortizacionInstanceList}">
                    <tr><td colspan="4" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${amortizacionInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
