<%@ page import="org.socymet.org.socymet.reportes.ReporteCompositoDeLotes" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Reporte Composito De Lotes</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Reporte Composito De Lotes</h3>
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
					
						<g:sortableColumn property="numeroComposito" title="${message(code: 'reporteCompositoDeLotes.numeroComposito.label', default: 'No. Composito')}" />
					
						<g:sortableColumn property="sigla" title="${message(code: 'reporteCompositoDeLotes.sigla.label', default: 'Sigla')}" />
					
						<g:sortableColumn property="destino" title="${message(code: 'reporteCompositoDeLotes.destino.label', default: 'Destino')}" />
					
						<g:sortableColumn property="comprador" title="${message(code: 'reporteCompositoDeLotes.comprador.label', default: 'Comprador')}" />

						<g:sortableColumn property="ingenio" title="${message(code: 'reporteCompositoDeLotes.ingenio.label', default: 'Ingenio')}" />
					
						<g:sortableColumn property="fechaDeElaboracion" title="${message(code: 'reporteCompositoDeLotes.fechaDeElaboracion.label', default: 'Fecha De Elaboracion')}" />
					
						<th><g:message code="reporteCompositoDeLotes.empresa.label" default="Empresa" /></th>

                        <g:sortableColumn property="estadoDelComposito" title="${message(code: 'reporteCompositoDeLotes.estadoDelComposito.label', default: 'Estado Del Composito')}" />

                        %{--<g:sortableColumn property="aprobadoPor" title="${message(code: 'reporteCompositoDeLotes.aprobadoPor.label', default: 'Aprobado Por')}" />--}%
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${reporteCompositoDeLotesInstanceList}" var="reporteCompositoDeLotesInstance">
					<tr>
					
						<td><g:link action="show" id="${reporteCompositoDeLotesInstance.id}"><g:formatNumber number="${reporteCompositoDeLotesInstance.numeroComposito}" format="000000"/></g:link></td>

						<td><g:link action="show" id="${reporteCompositoDeLotesInstance.id}">${fieldValue(bean: reporteCompositoDeLotesInstance, field: "sigla")}</g:link></td>
%{--						<td>${fieldValue(bean: reporteCompositoDeLotesInstance, field: "sigla")}</td>--}%
					
						<td>${fieldValue(bean: reporteCompositoDeLotesInstance, field: "destino")}</td>
					
%{--						<td>${fieldValue(bean: reporteCompositoDeLotesInstance, field: "comprador")}</td>--}%
						<td>${reporteCompositoDeLotesInstance?.destino.equals("VENTA") || reporteCompositoDeLotesInstance?.destino.equals("EXPORTACION")?reporteCompositoDeLotesInstance?.destino:""}</td>

%{--						<td>${fieldValue(bean: reporteCompositoDeLotesInstance, field: "ingenio")}</td>--}%
						<td>${reporteCompositoDeLotesInstance?.destino.equals("INGENIO")?reporteCompositoDeLotesInstance?.ingenio:""}</td>

						<td><g:formatDate date="${reporteCompositoDeLotesInstance.fechaDeElaboracion}" format="dd/MM/yyyy"/></td>
					
						<td>${fieldValue(bean: reporteCompositoDeLotesInstance, field: "empresa")}</td>

%{--                        <td>${fieldValue(bean: reporteCompositoDeLotesInstance, field: "estadoDelComposito")}</td>--}%
						<g:if test="${reporteCompositoDeLotesInstance.estadoDelComposito.equals("DEFINITIVO")}">
							<td style="color: green; font-weight: bold">${reporteCompositoDeLotesInstance.estadoDelComposito.toUpperCase()}</td>
						</g:if>
						<g:else>
							<td style="color: red; font-weight: bold">${reporteCompositoDeLotesInstance.estadoDelComposito.toUpperCase()}</td>
						</g:else>

                        %{--<td>${fieldValue(bean: reporteCompositoDeLotesInstance, field: "aprobadoPor")}</td>--}%
					
					</tr>
				</g:each>
				
                <g:if test="${!reporteCompositoDeLotesInstanceList}">
                    <tr><td colspan="9" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${reporteCompositoDeLotesInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
