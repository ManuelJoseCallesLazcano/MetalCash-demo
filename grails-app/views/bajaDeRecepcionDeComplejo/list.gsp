<%@ page import="org.socymet.recepcion.BajaDeRecepcionDeComplejo" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Baja De Recepcion De Complejo</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Baja De Recepcion De Complejo</h3>
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
					
						<g:sortableColumn property="lote" title="${message(code: 'bajaDeRecepcionDeComplejo.lote.label', default: 'Lote')}" />
					
						<g:sortableColumn property="recepcionId" title="${message(code: 'bajaDeRecepcionDeComplejo.liquidacionId.label', default: 'Recepcion Id')}" />
					
						<g:sortableColumn property="nombreCliente" title="${message(code: 'bajaDeRecepcionDeComplejo.nombreCliente.label', default: 'Nombre Cliente')}" />
					
						<g:sortableColumn property="nombreEmpresa" title="${message(code: 'bajaDeRecepcionDeComplejo.nombreEmpresa.label', default: 'Nombre Empresa')}" />
					
						<g:sortableColumn property="fechaDeRecepcion" title="${message(code: 'bajaDeRecepcionDeComplejo.fechaDeRecepcion.label', default: 'Fecha De Recepcion')}" />
					
						<g:sortableColumn property="pesoBruto" title="${message(code: 'bajaDeRecepcionDeComplejo.pesoBruto.label', default: 'Peso Bruto')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${bajaDeRecepcionDeComplejoInstanceList}" var="bajaDeRecepcionDeComplejoInstance">
					<tr>
					
						<td><g:link action="show" id="${bajaDeRecepcionDeComplejoInstance.id}">${fieldValue(bean: bajaDeRecepcionDeComplejoInstance, field: "lote")}</g:link></td>
					
						<td>${fieldValue(bean: bajaDeRecepcionDeComplejoInstance, field: "recepcionId")}</td>
					
						<td>${fieldValue(bean: bajaDeRecepcionDeComplejoInstance, field: "nombreCliente")}</td>
					
						<td>${fieldValue(bean: bajaDeRecepcionDeComplejoInstance, field: "nombreEmpresa")}</td>
					
						<td>${fieldValue(bean: bajaDeRecepcionDeComplejoInstance, field: "fechaDeRecepcion")}</td>
					
						<td>${fieldValue(bean: bajaDeRecepcionDeComplejoInstance, field: "pesoBruto")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!bajaDeRecepcionDeComplejoInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${bajaDeRecepcionDeComplejoInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
