<%@ page import="org.socymet.liquidacion.LiquidacionGrupalDeCobrePlata" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Liquidacion Grupal De Cobre Plata</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Liquidacion Grupal De Cobre Plata</h3>
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
					
						<th><g:message code="liquidacionGrupalDeCobrePlata.deposito.label" default="Deposito" /></th>
					
						<g:sortableColumn property="loteInicial" title="${message(code: 'liquidacionGrupalDeCobrePlata.loteInicial.label', default: 'Lote Inicial')}" />
					
						<g:sortableColumn property="loteFinal" title="${message(code: 'liquidacionGrupalDeCobrePlata.loteFinal.label', default: 'Lote Final')}" />
					
						<g:sortableColumn property="millis" title="${message(code: 'liquidacionGrupalDeCobrePlata.millis.label', default: 'Millis')}" />
					
						<g:sortableColumn property="lotes" title="${message(code: 'liquidacionGrupalDeCobrePlata.lotes.label', default: 'Lotes')}" />
					
						<g:sortableColumn property="lotesLiquidados" title="${message(code: 'liquidacionGrupalDeCobrePlata.lotesLiquidados.label', default: 'Lotes Liquidados')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${liquidacionGrupalDeCobrePlataInstanceList}" var="liquidacionGrupalDeCobrePlataInstance">
					<tr>
					
						<td><g:link action="show" id="${liquidacionGrupalDeCobrePlataInstance.id}">${fieldValue(bean: liquidacionGrupalDeCobrePlataInstance, field: "deposito")}</g:link></td>
					
						<td>${fieldValue(bean: liquidacionGrupalDeCobrePlataInstance, field: "loteInicial")}</td>
					
						<td>${fieldValue(bean: liquidacionGrupalDeCobrePlataInstance, field: "loteFinal")}</td>
					
						<td>${fieldValue(bean: liquidacionGrupalDeCobrePlataInstance, field: "millis")}</td>
					
						<td>${fieldValue(bean: liquidacionGrupalDeCobrePlataInstance, field: "lotes")}</td>
					
						<td>${fieldValue(bean: liquidacionGrupalDeCobrePlataInstance, field: "lotesLiquidados")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!liquidacionGrupalDeCobrePlataInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${liquidacionGrupalDeCobrePlataInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
