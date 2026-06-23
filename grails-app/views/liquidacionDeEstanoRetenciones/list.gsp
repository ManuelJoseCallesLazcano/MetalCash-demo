<%@ page import="org.socymet.liquidacion.LiquidacionDeEstanoRetenciones" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Liquidacion De Estano Retenciones</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Liquidacion De Estano Retenciones</h3>
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
					
						<th><g:message code="liquidacionDeEstanoRetenciones.liquidacionDeEstano.label" default="Liquidacion De Estano" /></th>
					
						<g:sortableColumn property="codigo" title="${message(code: 'liquidacionDeEstanoRetenciones.codigo.label', default: 'Codigo')}" />
					
						<g:sortableColumn property="cantidadDescuento" title="${message(code: 'liquidacionDeEstanoRetenciones.cantidadDescuento.label', default: 'Cantidad Descuento')}" />
					
						<g:sortableColumn property="unidadDeDescuento" title="${message(code: 'liquidacionDeEstanoRetenciones.unidadDeDescuento.label', default: 'Unidad De Descuento')}" />
					
						<g:sortableColumn property="tipoDeRetencion" title="${message(code: 'liquidacionDeEstanoRetenciones.tipoDeRetencion.label', default: 'Tipo De Retencion')}" />
					
						<g:sortableColumn property="descripcion" title="${message(code: 'liquidacionDeEstanoRetenciones.descripcion.label', default: 'Descripcion')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${liquidacionDeEstanoRetencionesInstanceList}" var="liquidacionDeEstanoRetencionesInstance">
					<tr>
					
						<td><g:link action="show" id="${liquidacionDeEstanoRetencionesInstance.id}">${fieldValue(bean: liquidacionDeEstanoRetencionesInstance, field: "liquidacionDeEstano")}</g:link></td>
					
						<td>${fieldValue(bean: liquidacionDeEstanoRetencionesInstance, field: "codigo")}</td>
					
						<td>${fieldValue(bean: liquidacionDeEstanoRetencionesInstance, field: "cantidadDescuento")}</td>
					
						<td>${fieldValue(bean: liquidacionDeEstanoRetencionesInstance, field: "unidadDeDescuento")}</td>
					
						<td>${fieldValue(bean: liquidacionDeEstanoRetencionesInstance, field: "tipoDeRetencion")}</td>
					
						<td>${fieldValue(bean: liquidacionDeEstanoRetencionesInstance, field: "descripcion")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!liquidacionDeEstanoRetencionesInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${liquidacionDeEstanoRetencionesInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
