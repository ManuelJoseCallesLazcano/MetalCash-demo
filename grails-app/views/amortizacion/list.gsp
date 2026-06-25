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
        <div class="px-3 pt-3 pb-2">
            <g:form action="list" method="GET">
                <div class="input-group" style="max-width:460px">
                    <div class="input-group-prepend">
                        <span class="input-group-text border-right-0 bg-white">
                            <i class="fas fa-search text-muted fa-sm"></i>
                        </span>
                    </div>
                    <input type="text" name="q"
                           class="form-control form-control-sm border-left-0"
                           placeholder="Buscar comprobante, cliente o empresa…"
                           value="${q ?: ''}"
                           autocomplete="off"/>
                    <div class="input-group-append">
                        <button type="submit" class="btn btn-secondary btn-sm">Buscar</button>
                        <g:if test="${q}">
                            <g:link action="list" class="btn btn-outline-secondary btn-sm" title="Limpiar búsqueda">
                                <i class="fas fa-times"></i>
                            </g:link>
                        </g:if>
                    </div>
                </div>
            </g:form>
        </div>
        <div class="table-responsive">
            <table class="table table-hover table-striped mb-0">
                <thead class="thead-light">

					<tr>

						<g:sortableColumn property="numeroAmortizacion" title="N°" params="${[q: q]}"/>

						<th><g:message code="amortizacion.cliente.label" default="Cliente" /></th>

						<th>Empresa</th>

						<g:sortableColumn property="fecha" title="${message(code: 'amortizacion.fecha.label', default: 'Fecha')}" params="${[q: q]}"/>

						<g:sortableColumn property="importe" title="${message(code: 'amortizacion.importe.label', default: 'Importe')}" params="${[q: q]}"/>

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

						<td>${amortizacionInstance.empresa?.nombreDeEmpresa}</td>
					
%{--						<td>${fieldValue(bean: amortizacionInstance, field: "ci")}</td>--}%
					
%{--						<td>${fieldValue(bean: amortizacionInstance, field: "nombre")}</td>--}%
					
%{--						<td>${fieldValue(bean: amortizacionInstance, field: "nombreEmpresa")}</td>--}%
					
						<td><g:formatDate date="${amortizacionInstance.fecha}" format="dd/MM/yyyy"/></td>

						<td><g:formatNumber number="${amortizacionInstance.importe ?: 0}" type="number" maxFractionDigits="2"/></td>
					
					</tr>
				</g:each>
				
                <g:if test="${!amortizacionInstanceList}">
                    <tr><td colspan="5" class="text-center text-muted py-4">
                        <g:if test="${q}">No se encontraron amortizaciones para "${q}".</g:if>
                        <g:else>No hay registros.</g:else>
                    </td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${amortizacionInstanceTotal ?: 0}" params="${[q: q]}"/>
    </div>
</div>
</body>
</html>
