<%@ page import="org.socymet.cancelacion.LoteBonoCalidad" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Lote Bono Calidad</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Lote Bono Calidad</h3>
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
					
						<th><g:message code="loteBonoCalidad.pagoBonoProduccion.label" default="Pago Bono Produccion" /></th>
					
						<g:sortableColumn property="lote" title="${message(code: 'loteBonoCalidad.lote.label', default: 'Lote')}" />
					
						<g:sortableColumn property="fechaDeLiquidacion" title="${message(code: 'loteBonoCalidad.fechaDeLiquidacion.label', default: 'Fecha De Liquidacion')}" />
					
						<g:sortableColumn property="nombreEmpresa" title="${message(code: 'loteBonoCalidad.nombreEmpresa.label', default: 'Nombre Empresa')}" />
					
						<g:sortableColumn property="nombreCliente" title="${message(code: 'loteBonoCalidad.nombreCliente.label', default: 'Nombre Cliente')}" />
					
						<g:sortableColumn property="kilosNetosSecos" title="${message(code: 'loteBonoCalidad.kilosNetosSecos.label', default: 'Kilos Netos Secos')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${loteBonoCalidadInstanceList}" var="loteBonoCalidadInstance">
					<tr>
					
						<td><g:link action="show" id="${loteBonoCalidadInstance.id}">${fieldValue(bean: loteBonoCalidadInstance, field: "pagoBonoProduccion")}</g:link></td>
					
						<td>${fieldValue(bean: loteBonoCalidadInstance, field: "lote")}</td>
					
						<td><g:formatDate date="${loteBonoCalidadInstance.fechaDeLiquidacion}" /></td>
					
						<td>${fieldValue(bean: loteBonoCalidadInstance, field: "nombreEmpresa")}</td>
					
						<td>${fieldValue(bean: loteBonoCalidadInstance, field: "nombreCliente")}</td>
					
						<td>${fieldValue(bean: loteBonoCalidadInstance, field: "kilosNetosSecos")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!loteBonoCalidadInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${loteBonoCalidadInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
