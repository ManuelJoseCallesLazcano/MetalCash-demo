<%@ page import="org.socymet.cotizacionParaCliente.CotizacionDeCobrePlata" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Cotizacion De Cobre Plata</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Cotizacion De Cobre Plata</h3>
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
					
						<g:sortableColumn property="numeroCotizacionCobrePlata" title="${message(code: 'cotizacionDeCobrePlata.numeroCotizacionCobrePlata.label', default: 'Numero Cotizacion Cobre Plata')}" />
					
						<g:sortableColumn property="nombreSolicitante" title="${message(code: 'cotizacionDeCobrePlata.nombreSolicitante.label', default: 'Nombre Solicitante')}" />
					
						<g:sortableColumn property="empresaSolicitante" title="${message(code: 'cotizacionDeCobrePlata.empresaSolicitante.label', default: 'Empresa Solicitante')}" />
					
						<g:sortableColumn property="fechaDeCotizacion" title="${message(code: 'cotizacionDeCobrePlata.fechaDeCotizacion.label', default: 'Fecha De Cotizacion')}" />
					
						<th><g:message code="cotizacionDeCobrePlata.cotizacionDiaria.label" default="Cotizacion Diaria" /></th>
					
						<g:sortableColumn property="leyCobre" title="${message(code: 'cotizacionDeCobrePlata.leyCobre.label', default: 'Ley Cobre')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${cotizacionDeCobrePlataInstanceList}" var="cotizacionDeCobrePlataInstance">
					<tr>
					
						<td><g:link action="show" id="${cotizacionDeCobrePlataInstance.id}">${fieldValue(bean: cotizacionDeCobrePlataInstance, field: "numeroCotizacionCobrePlata")}</g:link></td>
					
						<td>${fieldValue(bean: cotizacionDeCobrePlataInstance, field: "nombreSolicitante")}</td>
					
						<td>${fieldValue(bean: cotizacionDeCobrePlataInstance, field: "empresaSolicitante")}</td>
					
						<td><g:formatDate date="${cotizacionDeCobrePlataInstance.fechaDeCotizacion}" /></td>
					
						<td>${fieldValue(bean: cotizacionDeCobrePlataInstance, field: "cotizacionDiaria")}</td>
					
						<td>${fieldValue(bean: cotizacionDeCobrePlataInstance, field: "leyCobre")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!cotizacionDeCobrePlataInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${cotizacionDeCobrePlataInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
