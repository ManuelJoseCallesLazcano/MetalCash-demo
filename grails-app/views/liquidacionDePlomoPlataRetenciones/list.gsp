<%@ page import="org.socymet.liquidacion.LiquidacionDePlomoPlataRetenciones" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Liquidacion De Plomo Plata Retenciones</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Liquidacion De Plomo Plata Retenciones</h3>
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
					
						<th><g:message code="liquidacionDePlomoPlataRetenciones.liquidacionDePlomoPlata.label" default="Liquidacion De Plomo Plata" /></th>
					
						<g:sortableColumn property="codigo" title="${message(code: 'liquidacionDePlomoPlataRetenciones.codigo.label', default: 'Codigo')}" />
					
						<g:sortableColumn property="cantidadDescuento" title="${message(code: 'liquidacionDePlomoPlataRetenciones.cantidadDescuento.label', default: 'Cantidad Descuento')}" />
					
						<g:sortableColumn property="unidadDeDescuento" title="${message(code: 'liquidacionDePlomoPlataRetenciones.unidadDeDescuento.label', default: 'Unidad De Descuento')}" />
					
						<g:sortableColumn property="tipoDeRetencion" title="${message(code: 'liquidacionDePlomoPlataRetenciones.tipoDeRetencion.label', default: 'Tipo De Retencion')}" />
					
						<g:sortableColumn property="descripcion" title="${message(code: 'liquidacionDePlomoPlataRetenciones.descripcion.label', default: 'Descripcion')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${liquidacionDePlomoPlataRetencionesInstanceList}" var="liquidacionDePlomoPlataRetencionesInstance">
					<tr>
					
						<td><g:link action="show" id="${liquidacionDePlomoPlataRetencionesInstance.id}">${fieldValue(bean: liquidacionDePlomoPlataRetencionesInstance, field: "liquidacionDePlomoPlata")}</g:link></td>
					
						<td>${fieldValue(bean: liquidacionDePlomoPlataRetencionesInstance, field: "codigo")}</td>
					
						<td>${fieldValue(bean: liquidacionDePlomoPlataRetencionesInstance, field: "cantidadDescuento")}</td>
					
						<td>${fieldValue(bean: liquidacionDePlomoPlataRetencionesInstance, field: "unidadDeDescuento")}</td>
					
						<td>${fieldValue(bean: liquidacionDePlomoPlataRetencionesInstance, field: "tipoDeRetencion")}</td>
					
						<td>${fieldValue(bean: liquidacionDePlomoPlataRetencionesInstance, field: "descripcion")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!liquidacionDePlomoPlataRetencionesInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${liquidacionDePlomoPlataRetencionesInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
