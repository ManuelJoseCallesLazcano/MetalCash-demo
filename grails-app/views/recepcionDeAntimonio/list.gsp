<%@ page import="org.socymet.recepcion.RecepcionDeAntimonio" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Recepcion De Antimonio</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Recepcion De Antimonio</h3>
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
					
						<g:sortableColumn property="loteAntimonio" title="${message(code: 'recepcionDeAntimonio.loteAntimonio.label', default: 'Lote Antimonio')}" />
					
						<g:sortableColumn property="fechaDeRecepcion" title="${message(code: 'recepcionDeAntimonio.fechaDeRecepcion.label', default: 'Fecha De Recepcion')}" />
					
						<th><g:message code="recepcionDeAntimonio.cliente.label" default="Cliente" /></th>
					
						<th><g:message code="recepcionDeAntimonio.empresa.label" default="Empresa" /></th>

                        <th><g:message code="recepcionDeAntimonio.cantidadDeSacos.label" default="Cantidad de Sacos" /></th>

                        <th><g:message code="recepcionDeAntimonio.pesoBruto.label" default="Peso Bruto" /></th>

                        <th><g:message code="recepcionDeAntimonio.estadoDelLote.label" default="Estado del Lote" /></th>
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${recepcionDeAntimonioInstanceList}" var="recepcionDeAntimonioInstance">
					<tr>
					
						<td><g:link action="show" id="${recepcionDeAntimonioInstance.id}">${recepcionDeAntimonioInstance.toString()}</g:link></td>
					
						<td><g:formatDate date="${recepcionDeAntimonioInstance.fechaDeRecepcion}" format="dd/MM/yyyy"/></td>
					
						<td>${recepcionDeAntimonioInstance.cliente.nombre}</td>
					
						<td>${recepcionDeAntimonioInstance.empresa.nombreDeEmpresa}</td>

                        <td>${recepcionDeAntimonioInstance.cantidadDeSacos}</td>

                        <td>${recepcionDeAntimonioInstance.pesoBruto}</td>

                        <td>${recepcionDeAntimonioInstance.estadoDelLote}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!recepcionDeAntimonioInstanceList}">
                    <tr><td colspan="7" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${recepcionDeAntimonioInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
