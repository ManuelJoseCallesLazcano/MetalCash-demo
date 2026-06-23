<%@ page import="org.socymet.liquidacion.LiquidacionDeWolfranRetenciones" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Liquidacion De Wolfran Retenciones</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Liquidacion De Wolfran Retenciones</h3>
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
					
						<th><g:message code="liquidacionDeWolfranRetenciones.liquidacionDeWolfran.label" default="Liquidacion De Wolfran" /></th>
					
						<g:sortableColumn property="codigo" title="${message(code: 'liquidacionDeWolfranRetenciones.codigo.label', default: 'Codigo')}" />
					
						<g:sortableColumn property="cantidadDescuento" title="${message(code: 'liquidacionDeWolfranRetenciones.cantidadDescuento.label', default: 'Cantidad Descuento')}" />
					
						<g:sortableColumn property="unidadDeDescuento" title="${message(code: 'liquidacionDeWolfranRetenciones.unidadDeDescuento.label', default: 'Unidad De Descuento')}" />
					
						<g:sortableColumn property="tipoDeRetencion" title="${message(code: 'liquidacionDeWolfranRetenciones.tipoDeRetencion.label', default: 'Tipo De Retencion')}" />
					
						<g:sortableColumn property="descripcion" title="${message(code: 'liquidacionDeWolfranRetenciones.descripcion.label', default: 'Descripcion')}" />

                        <g:sortableColumn property="monto" title="${message(code: 'liquidacionDeWolfranRetenciones.monto.label', default: 'Monto')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${liquidacionDeWolfranRetencionesInstanceList}" var="liquidacionDeWolfranRetencionesInstance">
					<tr>
					
						<td><g:link action="show" id="${liquidacionDeWolfranRetencionesInstance.id}">${fieldValue(bean: liquidacionDeWolfranRetencionesInstance, field: "liquidacionDeWolfran")}</g:link></td>
					
						<td>${fieldValue(bean: liquidacionDeWolfranRetencionesInstance, field: "codigo")}</td>
					
						<td>${fieldValue(bean: liquidacionDeWolfranRetencionesInstance, field: "cantidadDescuento")}</td>
					
						<td>${fieldValue(bean: liquidacionDeWolfranRetencionesInstance, field: "unidadDeDescuento")}</td>
					
						<td>${fieldValue(bean: liquidacionDeWolfranRetencionesInstance, field: "tipoDeRetencion")}</td>
					
						<td>${fieldValue(bean: liquidacionDeWolfranRetencionesInstance, field: "descripcion")}</td>

                        <td>${fieldValue(bean: liquidacionDeWolfranRetencionesInstance, field: "monto")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!liquidacionDeWolfranRetencionesInstanceList}">
                    <tr><td colspan="7" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${liquidacionDeWolfranRetencionesInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
