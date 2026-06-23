<%@ page import="org.socymet.recepcion.RecepcionGrupalDeComplejo" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Recepcion Grupal De Complejo</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Recepcion Grupal De Complejo</h3>
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
					
						<th><g:message code="recepcionGrupalDeComplejo.cliente.label" default="Cliente" /></th>
					
						<th><g:message code="recepcionGrupalDeComplejo.empresa.label" default="Empresa" /></th>
					
						<th><g:message code="recepcionGrupalDeComplejo.chofer.label" default="Chofer" /></th>
					
						<th><g:message code="recepcionGrupalDeComplejo.automovil.label" default="Automovil" /></th>
					
						<g:sortableColumn property="fechaDeRecepcion" title="${message(code: 'recepcionGrupalDeComplejo.fechaDeRecepcion.label', default: 'Fecha De Recepcion')}" />
					
						<th><g:message code="recepcionGrupalDeComplejo.deposito.label" default="Deposito" /></th>
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${recepcionGrupalDeComplejoInstanceList}" var="recepcionGrupalDeComplejoInstance">
					<tr>
					
						<td><g:link action="show" id="${recepcionGrupalDeComplejoInstance.id}">${fieldValue(bean: recepcionGrupalDeComplejoInstance, field: "cliente")}</g:link></td>
					
						<td>${fieldValue(bean: recepcionGrupalDeComplejoInstance, field: "empresa")}</td>
					
						<td>${fieldValue(bean: recepcionGrupalDeComplejoInstance, field: "chofer")}</td>
					
						<td>${fieldValue(bean: recepcionGrupalDeComplejoInstance, field: "automovil")}</td>
					
						<td><g:formatDate date="${recepcionGrupalDeComplejoInstance.fechaDeRecepcion}" /></td>
					
						<td>${fieldValue(bean: recepcionGrupalDeComplejoInstance, field: "deposito")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!recepcionGrupalDeComplejoInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${recepcionGrupalDeComplejoInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
