<%@ page import="org.socymet.cancelacion.DetallePagoManipuleo" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Detalle Pago Manipuleo</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Detalle Pago Manipuleo</h3>
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
					
						<th><g:message code="detallePagoManipuleo.pagoManipuleo.label" default="Pago Manipuleo" /></th>
					
						<g:sortableColumn property="lote" title="${message(code: 'detallePagoManipuleo.lote.label', default: 'Lote')}" />
					
						<g:sortableColumn property="recepcionId" title="${message(code: 'detallePagoManipuleo.recepcionId.label', default: 'Recepcion Id')}" />
					
						<g:sortableColumn property="fechaDeRecepcion" title="${message(code: 'detallePagoManipuleo.fechaDeRecepcion.label', default: 'Fecha De Recepcion')}" />
					
						<g:sortableColumn property="pesoBruto" title="${message(code: 'detallePagoManipuleo.pesoBruto.label', default: 'Peso Bruto')}" />
					
						<g:sortableColumn property="tipoDeMaterial" title="${message(code: 'detallePagoManipuleo.tipoDeMaterial.label', default: 'Tipo De Material')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${detallePagoManipuleoInstanceList}" var="detallePagoManipuleoInstance">
					<tr>
					
						<td><g:link action="show" id="${detallePagoManipuleoInstance.id}">${fieldValue(bean: detallePagoManipuleoInstance, field: "pagoManipuleo")}</g:link></td>
					
						<td>${fieldValue(bean: detallePagoManipuleoInstance, field: "lote")}</td>
					
						<td>${fieldValue(bean: detallePagoManipuleoInstance, field: "recepcionId")}</td>
					
						<td><g:formatDate date="${detallePagoManipuleoInstance.fechaDeRecepcion}" /></td>
					
						<td>${fieldValue(bean: detallePagoManipuleoInstance, field: "pesoBruto")}</td>
					
						<td>${fieldValue(bean: detallePagoManipuleoInstance, field: "tipoDeMaterial")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!detallePagoManipuleoInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${detallePagoManipuleoInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
