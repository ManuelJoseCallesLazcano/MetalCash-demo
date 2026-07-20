<%@ page import="org.socymet.recepcion.RecepcionDeZincPlata" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Recepcion De Zinc Plata</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Recepcion De Zinc Plata</h3>
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

                        <g:sortableColumn property="loteZincPlata" title="${message(code: 'recepcionDeZincPlata.loteZincPlata.label', default: 'Lote ZincPlata')}" />

                        <g:sortableColumn property="fechaDeRecepcion" title="${message(code: 'recepcionDeZincPlata.fechaDeRecepcion.label', default: 'Fecha De Recepcion')}" />

                        <th><g:message code="recepcionDeZincPlata.cliente.label" default="Cliente" /></th>

                        <th><g:message code="recepcionDeZincPlata.empresa.label" default="Empresa" /></th>

                        <th><g:message code="recepcionDeZincPlata.cantidadDeSacos.label" default="Cantidad de Sacos" /></th>

                        <th><g:message code="recepcionDeZincPlata.pesoBruto.label" default="Peso Bruto" /></th>

                        <th><g:message code="recepcionDeZincPlata.estadoDelLote.label" default="Estado del Lote" /></th>
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${recepcionDeZincPlataInstanceList}" var="recepcionDeZincPlataInstance">
					<tr>

                        <td><g:link action="show" id="${recepcionDeZincPlataInstance.id}">${recepcionDeZincPlataInstance.toString()}</g:link></td>

                        <td><g:formatDate date="${recepcionDeZincPlataInstance.fechaDeRecepcion}" format="dd/MM/yyyy"/></td>

                        <td>${recepcionDeZincPlataInstance.cliente.nombre}</td>

                        <td>${recepcionDeZincPlataInstance.empresa.nombreDeEmpresa}</td>

                        <td>${recepcionDeZincPlataInstance.cantidadDeSacos}</td>

                        <td>${recepcionDeZincPlataInstance.pesoBruto}</td>

                        <td>${recepcionDeZincPlataInstance.estadoDelLote}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!recepcionDeZincPlataInstanceList}">
                    <tr><td colspan="7" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${recepcionDeZincPlataInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
