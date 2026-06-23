<%@ page import="org.socymet.proveedor.EmpresaRetenciones" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Empresa Retenciones</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Empresa Retenciones</h3>
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
					
						<th><g:message code="empresaRetenciones.empresa.label" default="Empresa" /></th>
					
						<g:sortableColumn property="descripcion" title="${message(code: 'empresaRetenciones.descripcion.label', default: 'Descripcion')}" />
					
						<g:sortableColumn property="tipoDeRetencion" title="${message(code: 'empresaRetenciones.tipoDeRetencion.label', default: 'Tipo De Retencion')}" />
					
						<g:sortableColumn property="cantidadDescuento" title="${message(code: 'empresaRetenciones.cantidadDescuento.label', default: 'Cantidad Descuento')}" />
					
						<g:sortableColumn property="unidadDeDescuento" title="${message(code: 'empresaRetenciones.unidadDeDescuento.label', default: 'Unidad De Descuento')}" />
					
						<g:sortableColumn property="asignacionDelDescuento" title="${message(code: 'empresaRetenciones.asignacionDelDescuento.label', default: 'Asignacion Del Descuento')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${empresaRetencionesInstanceList}" var="empresaRetencionesInstance">
					<tr>
					
						<td><g:link action="show" id="${empresaRetencionesInstance.id}">${fieldValue(bean: empresaRetencionesInstance, field: "empresa")}</g:link></td>
					
						<td>${fieldValue(bean: empresaRetencionesInstance, field: "descripcion")}</td>
					
						<td>${fieldValue(bean: empresaRetencionesInstance, field: "tipoDeRetencion")}</td>
					
						<td>${fieldValue(bean: empresaRetencionesInstance, field: "cantidadDescuento")}</td>
					
						<td>${fieldValue(bean: empresaRetencionesInstance, field: "unidadDeDescuento")}</td>
					
						<td>${fieldValue(bean: empresaRetencionesInstance, field: "asignacionDelDescuento")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!empresaRetencionesInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${empresaRetencionesInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
