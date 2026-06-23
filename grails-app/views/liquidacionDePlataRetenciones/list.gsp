<%@ page import="org.socymet.liquidacion.LiquidacionDePlataRetenciones" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Liquidacion De Plata Retenciones</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Liquidacion De Plata Retenciones</h3>
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
					
						<th><g:message code="liquidacionDePlataRetenciones.liquidacionDePlata.label" default="Liquidacion De Plata" /></th>
					
						<g:sortableColumn property="codigo" title="${message(code: 'liquidacionDePlataRetenciones.codigo.label', default: 'Codigo')}" />
					
						<g:sortableColumn property="cantidadDescuento" title="${message(code: 'liquidacionDePlataRetenciones.cantidadDescuento.label', default: 'Cantidad Descuento')}" />
					
						<g:sortableColumn property="unidadDeDescuento" title="${message(code: 'liquidacionDePlataRetenciones.unidadDeDescuento.label', default: 'Unidad De Descuento')}" />
					
						<g:sortableColumn property="tipoDeRetencion" title="${message(code: 'liquidacionDePlataRetenciones.tipoDeRetencion.label', default: 'Tipo De Retencion')}" />
					
						<g:sortableColumn property="descripcion" title="${message(code: 'liquidacionDePlataRetenciones.descripcion.label', default: 'Descripcion')}" />

                        <g:sortableColumn property="monto" title="${message(code: 'liquidacionDePlataRetenciones.monto.label', default: 'Monto')}" />

                        <g:sortableColumn property="asignacionDelDescuento" title="${message(code: 'liquidacionDePlataRetenciones.asignacionDelDescuento.label', default: 'Asignacion')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${liquidacionDePlataRetencionesInstanceList}" var="liquidacionDePlataRetencionesInstance">
					<tr>
					
						<td><g:link action="show" id="${liquidacionDePlataRetencionesInstance.id}">${fieldValue(bean: liquidacionDePlataRetencionesInstance, field: "liquidacionDePlata")}</g:link></td>
					
						<td>${fieldValue(bean: liquidacionDePlataRetencionesInstance, field: "codigo")}</td>
					
						<td>${fieldValue(bean: liquidacionDePlataRetencionesInstance, field: "cantidadDescuento")}</td>
					
						<td>${fieldValue(bean: liquidacionDePlataRetencionesInstance, field: "unidadDeDescuento")}</td>
					
						<td>${fieldValue(bean: liquidacionDePlataRetencionesInstance, field: "tipoDeRetencion")}</td>
					
						<td>${fieldValue(bean: liquidacionDePlataRetencionesInstance, field: "descripcion")}</td>

                        <td>${fieldValue(bean: liquidacionDePlataRetencionesInstance, field: "monto")}</td>

                        <td>${fieldValue(bean: liquidacionDePlataRetencionesInstance, field: "asignacionDelDescuento")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!liquidacionDePlataRetencionesInstanceList}">
                    <tr><td colspan="8" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${liquidacionDePlataRetencionesInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
