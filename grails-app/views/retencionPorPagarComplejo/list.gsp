<%@ page import="org.socymet.liquidacion.RetencionPorPagarComplejo" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Retencion Por Pagar Complejo</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Retencion Por Pagar Complejo</h3>
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
					
						<g:sortableColumn property="codigo" title="${message(code: 'retencionPorPagarComplejo.codigo.label', default: 'Codigo')}" />
					
						<g:sortableColumn property="cantidadDescuento" title="${message(code: 'retencionPorPagarComplejo.cantidadDescuento.label', default: 'Cantidad Descuento')}" />
					
						<g:sortableColumn property="unidadDeDescuento" title="${message(code: 'retencionPorPagarComplejo.unidadDeDescuento.label', default: 'Unidad De Descuento')}" />
					
						<g:sortableColumn property="tipoDeRetencion" title="${message(code: 'retencionPorPagarComplejo.tipoDeRetencion.label', default: 'Tipo De Retencion')}" />
					
						<g:sortableColumn property="descripcion" title="${message(code: 'retencionPorPagarComplejo.descripcion.label', default: 'Descripcion')}" />
					
						<g:sortableColumn property="asignacionDelDescuento" title="${message(code: 'retencionPorPagarComplejo.asignacionDelDescuento.label', default: 'Asignacion Del Descuento')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${retencionPorPagarComplejoInstanceList}" var="retencionPorPagarComplejoInstance">
					<tr>
					
						<td><g:link action="show" id="${retencionPorPagarComplejoInstance.id}">${fieldValue(bean: retencionPorPagarComplejoInstance, field: "codigo")}</g:link></td>
					
						<td>${fieldValue(bean: retencionPorPagarComplejoInstance, field: "cantidadDescuento")}</td>
					
						<td>${fieldValue(bean: retencionPorPagarComplejoInstance, field: "unidadDeDescuento")}</td>
					
						<td>${fieldValue(bean: retencionPorPagarComplejoInstance, field: "tipoDeRetencion")}</td>
					
						<td>${fieldValue(bean: retencionPorPagarComplejoInstance, field: "descripcion")}</td>
					
						<td>${fieldValue(bean: retencionPorPagarComplejoInstance, field: "asignacionDelDescuento")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!retencionPorPagarComplejoInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${retencionPorPagarComplejoInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
