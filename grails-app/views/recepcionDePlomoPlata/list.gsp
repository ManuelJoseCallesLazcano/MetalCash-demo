<%@ page import="org.socymet.recepcion.RecepcionDePlomoPlata" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Recepcion De Plomo Plata</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Recepcion De Plomo Plata</h3>
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

                        <g:sortableColumn property="lotePlomoPlata" title="${message(code: 'recepcionDePlomoPlata.lotePlomoPlata.label', default: 'Lote PlomoPlata')}" />

                        <g:sortableColumn property="fechaDeRecepcion" title="${message(code: 'recepcionDePlomoPlata.fechaDeRecepcion.label', default: 'Fecha De Recepcion')}" />

                        <th><g:message code="recepcionDePlomoPlata.cliente.label" default="Cliente" /></th>

                        <th><g:message code="recepcionDePlomoPlata.empresa.label" default="Empresa" /></th>

                        <th><g:message code="recepcionDePlomoPlata.cantidadDeSacos.label" default="Cantidad de Sacos" /></th>

                        <th><g:message code="recepcionDePlomoPlata.pesoBruto.label" default="Peso Bruto" /></th>

                        <th><g:message code="recepcionDePlomoPlata.estadoDelLote.label" default="Estado del Lote" /></th>
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${recepcionDePlomoPlataInstanceList}" var="recepcionDePlomoPlataInstance">
					<tr>

                        <td><g:link action="show" id="${recepcionDePlomoPlataInstance.id}">${recepcionDePlomoPlataInstance.toString()}</g:link></td>

                        <td><g:formatDate date="${recepcionDePlomoPlataInstance.fechaDeRecepcion}" format="dd/MM/yyyy"/></td>

                        <td>${recepcionDePlomoPlataInstance.cliente.nombre}</td>

                        <td>${recepcionDePlomoPlataInstance.empresa.nombreDeEmpresa}</td>

                        <td>${recepcionDePlomoPlataInstance.cantidadDeSacos}</td>

                        <td>${recepcionDePlomoPlataInstance.pesoBruto}</td>

                        <td>${recepcionDePlomoPlataInstance.estadoDelLote}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!recepcionDePlomoPlataInstanceList}">
                    <tr><td colspan="7" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${recepcionDePlomoPlataInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
