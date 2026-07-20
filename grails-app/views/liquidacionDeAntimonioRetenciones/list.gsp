<%@ page import="org.socymet.liquidacion.LiquidacionDeAntimonioRetenciones" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Liquidacion De Antimonio Retenciones</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Liquidacion De Antimonio Retenciones</h3>
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
					
						<th><g:message code="liquidacionDeAntimonioRetenciones.liquidacionDeAntimonio.label" default="Liquidacion De Antimonio" /></th>
					
						<g:sortableColumn property="codigo" title="${message(code: 'liquidacionDeAntimonioRetenciones.codigo.label', default: 'Codigo')}" />
					
						<g:sortableColumn property="cantidadDescuento" title="${message(code: 'liquidacionDeAntimonioRetenciones.cantidadDescuento.label', default: 'Cantidad Descuento')}" />
					
						<g:sortableColumn property="unidadDeDescuento" title="${message(code: 'liquidacionDeAntimonioRetenciones.unidadDeDescuento.label', default: 'Unidad De Descuento')}" />
					
						<g:sortableColumn property="tipoDeRetencion" title="${message(code: 'liquidacionDeAntimonioRetenciones.tipoDeRetencion.label', default: 'Tipo De Retencion')}" />
					
						<g:sortableColumn property="descripcion" title="${message(code: 'liquidacionDeAntimonioRetenciones.descripcion.label', default: 'Descripcion')}" />

                        <g:sortableColumn property="monto" title="${message(code: 'liquidacionDeAntimonioRetenciones.monto.label', default: 'Monto')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${liquidacionDeAntimonioRetencionesInstanceList}" var="liquidacionDeAntimonioRetencionesInstance">
					<tr>
					
						<td><g:link action="show" id="${liquidacionDeAntimonioRetencionesInstance.id}">${fieldValue(bean: liquidacionDeAntimonioRetencionesInstance, field: "liquidacionDeAntimonio")}</g:link></td>
					
						<td>${fieldValue(bean: liquidacionDeAntimonioRetencionesInstance, field: "codigo")}</td>
					
						<td>${fieldValue(bean: liquidacionDeAntimonioRetencionesInstance, field: "cantidadDescuento")}</td>
					
						<td>${fieldValue(bean: liquidacionDeAntimonioRetencionesInstance, field: "unidadDeDescuento")}</td>
					
						<td>${fieldValue(bean: liquidacionDeAntimonioRetencionesInstance, field: "tipoDeRetencion")}</td>
					
						<td>${fieldValue(bean: liquidacionDeAntimonioRetencionesInstance, field: "descripcion")}</td>

                        <td>${fieldValue(bean: liquidacionDeAntimonioRetencionesInstance, field: "monto")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!liquidacionDeAntimonioRetencionesInstanceList}">
                    <tr><td colspan="7" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${liquidacionDeAntimonioRetencionesInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
