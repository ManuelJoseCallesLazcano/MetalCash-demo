<%@ page import="org.socymet.liquidacion.LiquidacionDeComplejoRetenciones" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Liquidacion De Complejo Retenciones</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Liquidacion De Complejo Retenciones</h3>
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
					
						<th><g:message code="liquidacionDeComplejoRetenciones.liquidacionDeComplejo.label" default="Liquidacion De Complejo" /></th>
					
						<g:sortableColumn property="codigo" title="${message(code: 'liquidacionDeComplejoRetenciones.codigo.label', default: 'Codigo')}" />
					
						<g:sortableColumn property="cantidadDescuento" title="${message(code: 'liquidacionDeComplejoRetenciones.cantidadDescuento.label', default: 'Cantidad Descuento')}" />
					
						<g:sortableColumn property="unidadDeDescuento" title="${message(code: 'liquidacionDeComplejoRetenciones.unidadDeDescuento.label', default: 'Unidad De Descuento')}" />
					
						<g:sortableColumn property="tipoDeRetencion" title="${message(code: 'liquidacionDeComplejoRetenciones.tipoDeRetencion.label', default: 'Tipo De Retencion')}" />
					
						<g:sortableColumn property="descripcion" title="${message(code: 'liquidacionDeComplejoRetenciones.descripcion.label', default: 'Descripcion')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${liquidacionDeComplejoRetencionesInstanceList}" var="liquidacionDeComplejoRetencionesInstance">
					<tr>
					
						<td><g:link action="show" id="${liquidacionDeComplejoRetencionesInstance.id}">${fieldValue(bean: liquidacionDeComplejoRetencionesInstance, field: "liquidacionDeComplejo")}</g:link></td>
					
						<td>${fieldValue(bean: liquidacionDeComplejoRetencionesInstance, field: "codigo")}</td>
					
						<td>${fieldValue(bean: liquidacionDeComplejoRetencionesInstance, field: "cantidadDescuento")}</td>
					
						<td>${fieldValue(bean: liquidacionDeComplejoRetencionesInstance, field: "unidadDeDescuento")}</td>
					
						<td>${fieldValue(bean: liquidacionDeComplejoRetencionesInstance, field: "tipoDeRetencion")}</td>
					
						<td>${fieldValue(bean: liquidacionDeComplejoRetencionesInstance, field: "descripcion")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!liquidacionDeComplejoRetencionesInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${liquidacionDeComplejoRetencionesInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
