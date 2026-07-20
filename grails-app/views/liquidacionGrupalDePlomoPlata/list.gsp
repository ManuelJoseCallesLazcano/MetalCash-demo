<%@ page import="org.socymet.liquidacion.LiquidacionGrupalDePlomoPlata" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Liquidacion Grupal De Plomo Plata</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Liquidacion Grupal De Plomo Plata</h3>
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
					
						<th><g:message code="liquidacionGrupalDePlomoPlata.deposito.label" default="Deposito" /></th>
					
						<g:sortableColumn property="loteInicial" title="${message(code: 'liquidacionGrupalDePlomoPlata.loteInicial.label', default: 'Lote Inicial')}" />
					
						<g:sortableColumn property="loteFinal" title="${message(code: 'liquidacionGrupalDePlomoPlata.loteFinal.label', default: 'Lote Final')}" />
					
						<g:sortableColumn property="millis" title="${message(code: 'liquidacionGrupalDePlomoPlata.millis.label', default: 'Millis')}" />
					
						<g:sortableColumn property="lotes" title="${message(code: 'liquidacionGrupalDePlomoPlata.lotes.label', default: 'Lotes')}" />
					
						<g:sortableColumn property="lotesLiquidados" title="${message(code: 'liquidacionGrupalDePlomoPlata.lotesLiquidados.label', default: 'Lotes Liquidados')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${liquidacionGrupalDePlomoPlataInstanceList}" var="liquidacionGrupalDePlomoPlataInstance">
					<tr>
					
						<td><g:link action="show" id="${liquidacionGrupalDePlomoPlataInstance.id}">${fieldValue(bean: liquidacionGrupalDePlomoPlataInstance, field: "deposito")}</g:link></td>
					
						<td>${fieldValue(bean: liquidacionGrupalDePlomoPlataInstance, field: "loteInicial")}</td>
					
						<td>${fieldValue(bean: liquidacionGrupalDePlomoPlataInstance, field: "loteFinal")}</td>
					
						<td>${fieldValue(bean: liquidacionGrupalDePlomoPlataInstance, field: "millis")}</td>
					
						<td>${fieldValue(bean: liquidacionGrupalDePlomoPlataInstance, field: "lotes")}</td>
					
						<td>${fieldValue(bean: liquidacionGrupalDePlomoPlataInstance, field: "lotesLiquidados")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!liquidacionGrupalDePlomoPlataInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${liquidacionGrupalDePlomoPlataInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
