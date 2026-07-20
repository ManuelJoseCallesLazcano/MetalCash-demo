<%@ page import="org.socymet.cotizacionParaCliente.CotizacionDeComplejo" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Cotizacion De Complejo</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Cotizacion De Complejo</h3>
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
					
						%{--<g:sortableColumn property="numeroCotizacionComplejo" title="${message(code: 'cotizacionDeComplejo.numeroCotizacionComplejo.label', default: 'Numero Cotizacion Complejo')}" />--}%
					
						<g:sortableColumn property="nombreSolicitante" title="${message(code: 'cotizacionDeComplejo.nombreSolicitante.label', default: 'Nombre Solicitante')}" />
					
						%{--<g:sortableColumn property="empresaSolicitante" title="${message(code: 'cotizacionDeComplejo.empresaSolicitante.label', default: 'Empresa Solicitante')}" />--}%
					
						<g:sortableColumn property="fechaDeCotizacion" title="${message(code: 'cotizacionDeComplejo.fechaDeCotizacion.label', default: 'Fecha De Cotizacion')}" />
					
						<th><g:message code="cotizacionDeComplejo.cotizacionDiaria.label" default="Cotizacion Diaria" /></th>
					
						<g:sortableColumn property="leyZinc" title="${message(code: 'cotizacionDeComplejo.leyZinc.label', default: 'Ley Zinc')}" />

						<g:sortableColumn property="leyPlomo" title="${message(code: 'cotizacionDeComplejo.leyPlomo.label', default: 'Ley Plomo')}" />

						<g:sortableColumn property="leyPlata" title="${message(code: 'cotizacionDeComplejo.leyPlata.label', default: 'Ley Plata')}" />

						<g:sortableColumn property="valorTonelada" title="${message(code: 'cotizacionDeComplejo.valorTonelada.label', default: 'Valor Tonelada')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${cotizacionDeComplejoInstanceList}" var="cotizacionDeComplejoInstance">
					<tr>
					
						%{--<td><g:link action="show" id="${cotizacionDeComplejoInstance.id}">${fieldValue(bean: cotizacionDeComplejoInstance, field: "numeroCotizacionComplejo")}</g:link></td>--}%

						<td><g:link action="show" id="${cotizacionDeComplejoInstance.id}">${fieldValue(bean: cotizacionDeComplejoInstance, field: "nombreSolicitante")}</g:link></td>

						%{--<td>${fieldValue(bean: cotizacionDeComplejoInstance, field: "empresaSolicitante")}</td>--}%
					
						<td><g:formatDate date="${cotizacionDeComplejoInstance.fechaDeCotizacion}" /></td>
					
						<td>${fieldValue(bean: cotizacionDeComplejoInstance, field: "cotizacionDiaria")}</td>
					
						<td>${fieldValue(bean: cotizacionDeComplejoInstance, field: "leyZinc")}</td>

						<td>${fieldValue(bean: cotizacionDeComplejoInstance, field: "leyPlomo")}</td>

						<td>${fieldValue(bean: cotizacionDeComplejoInstance, field: "leyPlata")}</td>

						<td>${fieldValue(bean: cotizacionDeComplejoInstance, field: "valorTonelada")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!cotizacionDeComplejoInstanceList}">
                    <tr><td colspan="9" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${cotizacionDeComplejoInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
