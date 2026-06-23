<%@ page import="org.socymet.recepcion.RecepcionDeEstano" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Recepcion De Estano</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Recepcion De Estano</h3>
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

                        <g:sortableColumn property="loteEstano" title="${message(code: 'recepcionDeEstano.loteEstano.label', default: 'Lote Estano')}" />

                        <g:sortableColumn property="fechaDeRecepcion" title="${message(code: 'recepcionDeEstano.fechaDeRecepcion.label', default: 'Fecha De Recepcion')}" />

                        <th><g:message code="recepcionDeEstano.cliente.label" default="Cliente" /></th>

                        <th><g:message code="recepcionDeEstano.empresa.label" default="Empresa" /></th>

                        <th><g:message code="recepcionDeEstano.cantidadDeSacos.label" default="Cantidad de Sacos" /></th>

                        <th><g:message code="recepcionDeEstano.pesoBruto.label" default="Peso Bruto" /></th>

                        <th><g:message code="recepcionDeEstano.estadoDelLote.label" default="Estado del Lote" /></th>
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${recepcionDeEstanoInstanceList}" var="recepcionDeEstanoInstance">
					<tr>

                        <td><g:link action="show" id="${recepcionDeEstanoInstance.id}">${recepcionDeEstanoInstance.toString()}</g:link></td>

                        <td><g:formatDate date="${recepcionDeEstanoInstance.fechaDeRecepcion}" format="dd/MM/yyyy"/></td>

                        <td>${recepcionDeEstanoInstance.cliente.nombre}</td>

                        <td>${recepcionDeEstanoInstance.empresa.nombreDeEmpresa}</td>

                        <td>${recepcionDeEstanoInstance.cantidadDeSacos}</td>

                        <td>${recepcionDeEstanoInstance.pesoBruto}</td>

                        <td>${recepcionDeEstanoInstance.estadoDelLote}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!recepcionDeEstanoInstanceList}">
                    <tr><td colspan="7" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${recepcionDeEstanoInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
