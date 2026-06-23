<%@ page import="org.socymet.liquidacion.LiquidacionGrupalDeComplejo" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Liquidacion Grupal De Complejo</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Liquidacion Grupal De Complejo</h3>
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
					
						<th><g:message code="liquidacionGrupalDeComplejo.deposito.label" default="Deposito" /></th>
					
						<g:sortableColumn property="loteInicial" title="${message(code: 'liquidacionGrupalDeComplejo.loteInicial.label', default: 'Lote Inicial')}" />
					
						<g:sortableColumn property="loteFinal" title="${message(code: 'liquidacionGrupalDeComplejo.loteFinal.label', default: 'Lote Final')}" />
					
						<g:sortableColumn property="millis" title="${message(code: 'liquidacionGrupalDeComplejo.millis.label', default: 'Millis')}" />
					
						<g:sortableColumn property="lotes" title="${message(code: 'liquidacionGrupalDeComplejo.lotes.label', default: 'Lotes')}" />
					
						<g:sortableColumn property="lotesLiquidados" title="${message(code: 'liquidacionGrupalDeComplejo.lotesLiquidados.label', default: 'Lotes Liquidados')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${liquidacionGrupalDeComplejoInstanceList}" var="liquidacionGrupalDeComplejoInstance">
					<tr>
					
						<td><g:link action="show" id="${liquidacionGrupalDeComplejoInstance.id}">${fieldValue(bean: liquidacionGrupalDeComplejoInstance, field: "deposito")}</g:link></td>
					
						<td>${fieldValue(bean: liquidacionGrupalDeComplejoInstance, field: "loteInicial")}</td>
					
						<td>${fieldValue(bean: liquidacionGrupalDeComplejoInstance, field: "loteFinal")}</td>
					
						<td>${fieldValue(bean: liquidacionGrupalDeComplejoInstance, field: "millis")}</td>
					
						<td>${fieldValue(bean: liquidacionGrupalDeComplejoInstance, field: "lotes")}</td>
					
						<td>${fieldValue(bean: liquidacionGrupalDeComplejoInstance, field: "lotesLiquidados")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!liquidacionGrupalDeComplejoInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${liquidacionGrupalDeComplejoInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
