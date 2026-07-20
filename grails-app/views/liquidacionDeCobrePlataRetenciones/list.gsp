<%@ page import="org.socymet.liquidacion.LiquidacionDeCobrePlataRetenciones" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Liquidacion De Cobre Plata Retenciones</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Liquidacion De Cobre Plata Retenciones</h3>
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
					
						<th><g:message code="liquidacionDeCobrePlataRetenciones.liquidacionDeCobrePlata.label" default="Liquidacion De Cobre Plata" /></th>
					
						<g:sortableColumn property="codigo" title="${message(code: 'liquidacionDeCobrePlataRetenciones.codigo.label', default: 'Codigo')}" />
					
						<g:sortableColumn property="cantidadDescuento" title="${message(code: 'liquidacionDeCobrePlataRetenciones.cantidadDescuento.label', default: 'Cantidad Descuento')}" />
					
						<g:sortableColumn property="unidadDeDescuento" title="${message(code: 'liquidacionDeCobrePlataRetenciones.unidadDeDescuento.label', default: 'Unidad De Descuento')}" />
					
						<g:sortableColumn property="tipoDeRetencion" title="${message(code: 'liquidacionDeCobrePlataRetenciones.tipoDeRetencion.label', default: 'Tipo De Retencion')}" />
					
						<g:sortableColumn property="descripcion" title="${message(code: 'liquidacionDeCobrePlataRetenciones.descripcion.label', default: 'Descripcion')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${liquidacionDeCobrePlataRetencionesInstanceList}" var="liquidacionDeCobrePlataRetencionesInstance">
					<tr>
					
						<td><g:link action="show" id="${liquidacionDeCobrePlataRetencionesInstance.id}">${fieldValue(bean: liquidacionDeCobrePlataRetencionesInstance, field: "liquidacionDeCobrePlata")}</g:link></td>
					
						<td>${fieldValue(bean: liquidacionDeCobrePlataRetencionesInstance, field: "codigo")}</td>
					
						<td>${fieldValue(bean: liquidacionDeCobrePlataRetencionesInstance, field: "cantidadDescuento")}</td>
					
						<td>${fieldValue(bean: liquidacionDeCobrePlataRetencionesInstance, field: "unidadDeDescuento")}</td>
					
						<td>${fieldValue(bean: liquidacionDeCobrePlataRetencionesInstance, field: "tipoDeRetencion")}</td>
					
						<td>${fieldValue(bean: liquidacionDeCobrePlataRetencionesInstance, field: "descripcion")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!liquidacionDeCobrePlataRetencionesInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${liquidacionDeCobrePlataRetencionesInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
