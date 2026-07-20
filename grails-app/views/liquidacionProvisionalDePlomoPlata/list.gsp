<%@ page import="org.socymet.liquidacion.LiquidacionProvisionalDePlomoPlata" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Liquidacion Provisional De Plomo Plata</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Liquidacion Provisional De Plomo Plata</h3>
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
					
						<g:sortableColumn property="fechaDeLiquidacionProvisional" title="${message(code: 'liquidacionProvisionalDePlomoPlata.fechaDeLiquidacionProvisional.label', default: 'Fecha De Liquidacion Provisional')}" />
					
						<g:sortableColumn property="numeroLiquidacionProvisionalPlomoPlata" title="${message(code: 'liquidacionProvisionalDePlomoPlata.numeroLiquidacionProvisionalPlomoPlata.label', default: 'Numero Liquidacion Provisional Plomo Plata')}" />
					
						<g:sortableColumn property="lote" title="${message(code: 'liquidacionProvisionalDePlomoPlata.lote.label', default: 'Lote')}" />
					
						<th><g:message code="liquidacionProvisionalDePlomoPlata.recepcionDeComplejo.label" default="Recepcion De Complejo" /></th>
					
						<th><g:message code="liquidacionProvisionalDePlomoPlata.deposito.label" default="Deposito" /></th>
					
						<g:sortableColumn property="cotizacionDiariaDeZinc" title="${message(code: 'liquidacionProvisionalDePlomoPlata.cotizacionDiariaDeZinc.label', default: 'Cotizacion Diaria De Zinc')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${liquidacionProvisionalDePlomoPlataInstanceList}" var="liquidacionProvisionalDePlomoPlataInstance">
					<tr>
					
						<td><g:link action="show" id="${liquidacionProvisionalDePlomoPlataInstance.id}">${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: "fechaDeLiquidacionProvisional")}</g:link></td>
					
						<td>${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: "numeroLiquidacionProvisionalPlomoPlata")}</td>
					
						<td>${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: "lote")}</td>
					
						<td>${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: "recepcionDeComplejo")}</td>
					
						<td>${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: "deposito")}</td>
					
						<td>${fieldValue(bean: liquidacionProvisionalDePlomoPlataInstance, field: "cotizacionDiariaDeZinc")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!liquidacionProvisionalDePlomoPlataInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${liquidacionProvisionalDePlomoPlataInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
