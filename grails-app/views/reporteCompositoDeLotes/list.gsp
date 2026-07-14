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
        <div class="mb-3 px-3 pt-3">
            <g:form action="list" method="GET">
                <div class="input-group" style="max-width:560px">
                    <div class="input-group-prepend">
                        <span class="input-group-text border-right-0 bg-white"><i class="fas fa-search text-muted fa-sm"></i></span>
                    </div>
                    <input type="text" name="q"
                           class="form-control form-control-sm border-left-0"
                           placeholder="Buscar por N° de compósito, nombre, comprador o ingenio…"
                           value="${q ?: ''}"
                           autocomplete="off"/>
                    <div class="input-group-append">
                        <button type="submit" class="btn btn-secondary btn-sm">Buscar</button>
                        <g:if test="${q}">
                            <g:link action="list" class="btn btn-outline-secondary btn-sm" title="Limpiar búsqueda"><i class="fas fa-times"></i></g:link>
                        </g:if>
                    </div>
                </div>
            </g:form>
        </div>
        <div class="table-responsive">
            <table class="table table-hover table-striped mb-0">
                <thead class="thead-light">

					<tr>
					
						<g:sortableColumn property="numeroComposito" title="${message(code: 'reporteCompositoDeLotes.numeroComposito.label', default: 'No.')}" />
					
						<g:sortableColumn property="sigla" title="${message(code: 'reporteCompositoDeLotes.sigla.label', default: 'Sigla')}" />
					
						<g:sortableColumn property="destino" title="${message(code: 'reporteCompositoDeLotes.destino.label', default: 'Destino')}" />
					
						<g:sortableColumn property="comprador" title="${message(code: 'reporteCompositoDeLotes.comprador.label', default: 'Comprador')}" />

						<g:sortableColumn property="ingenio" title="${message(code: 'reporteCompositoDeLotes.ingenio.label', default: 'Ingenio')}" />
					
						<g:sortableColumn property="fechaDeElaboracion" title="${message(code: 'reporteCompositoDeLotes.fechaDeElaboracion.label', default: 'Fecha De Elaboracion')}" />
					
						<g:sortableColumn property="estadoDelComposito" title="${message(code: 'reporteCompositoDeLotes.estadoDelComposito.label', default: 'Estado Del Composito')}" />

                        %{--<g:sortableColumn property="aprobadoPor" title="${message(code: 'reporteCompositoDeLotes.aprobadoPor.label', default: 'Aprobado Por')}" />--}%
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${reporteCompositoDeLotesInstanceList}" var="reporteCompositoDeLotesInstance">
					<tr>
					
						<td><g:link action="show" id="${reporteCompositoDeLotesInstance.id}">${reporteCompositoDeLotesInstance.numeroComposito}<g:if test="${reporteCompositoDeLotesInstance.gestionMinera}">/<g:formatDate date="${reporteCompositoDeLotesInstance.gestionMinera}" format="yy"/></g:if></g:link></td>

						<td><g:link action="show" id="${reporteCompositoDeLotesInstance.id}">${fieldValue(bean: reporteCompositoDeLotesInstance, field: "sigla")}</g:link></td>
%{--						<td>${fieldValue(bean: reporteCompositoDeLotesInstance, field: "sigla")}</td>--}%
					
						<td>${fieldValue(bean: reporteCompositoDeLotesInstance, field: "destino")}</td>
					
%{--						<td>${fieldValue(bean: reporteCompositoDeLotesInstance, field: "comprador")}</td>--}%
						<td>${reporteCompositoDeLotesInstance?.destino.equals("VENTA") || reporteCompositoDeLotesInstance?.destino.equals("EXPORTACION")?reporteCompositoDeLotesInstance?.comprador:""}</td>

%{--						<td>${fieldValue(bean: reporteCompositoDeLotesInstance, field: "ingenio")}</td>--}%
						<td>${reporteCompositoDeLotesInstance?.destino.equals("INGENIO")?reporteCompositoDeLotesInstance?.ingenio:""}</td>

						<td><g:formatDate date="${reporteCompositoDeLotesInstance.fechaDeElaboracion}" format="dd/MM/yyyy"/></td>

						<td>
							<g:if test="${reporteCompositoDeLotesInstance.anulado}"><span class="badge badge-dark">ANULADO</span></g:if>
							<g:elseif test="${reporteCompositoDeLotesInstance.estadoDelComposito.equals('DEFINITIVO')}"><span class="badge badge-success">DEFINITIVO</span></g:elseif>
							<g:else><span class="badge badge-warning">PROVISIONAL</span></g:else>
						</td>

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
        <g:paginate total="${reporteCompositoDeLotesInstanceTotal ?: 0}" params="${[q: q]}" />
    </div>
</div>
</body>
</html>
