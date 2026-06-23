<%@ page import="org.socymet.cancelacion.PagoManipuleo" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Pago Manipuleo</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Pago Manipuleo</h3>
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
					
						<g:sortableColumn property="numeroComprobante" title="${message(code: 'pagoManipuleo.numeroComprobante.label', default: 'No. Comprobante')}" />
					
						<g:sortableColumn property="ci" title="${message(code: 'pagoManipuleo.ci.label', default: 'Ci')}" />
					
						<g:sortableColumn property="nombreCobrador" title="${message(code: 'pagoManipuleo.nombreCobrador.label', default: 'Nombre Cobrador')}" />
					
						<g:sortableColumn property="fechaDePago" title="${message(code: 'pagoManipuleo.fechaDePago.label', default: 'Fecha De Pago')}" />
					
						<th><g:message code="pagoManipuleo.deposito.label" default="Deposito" /></th>
					
						<g:sortableColumn property="totalPagable" title="${message(code: 'pagoManipuleo.totalPagable.label', default: 'Total Pagable')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${pagoManipuleoInstanceList}" var="pagoManipuleoInstance">
					<tr>
					
						<td><g:link action="show" id="${pagoManipuleoInstance.id}"><g:formatNumber number="${pagoManipuleoInstance.numeroComprobante}" format="000000"/></g:link></td>
					
						<td>${fieldValue(bean: pagoManipuleoInstance, field: "ci")}</td>
					
						<td>${fieldValue(bean: pagoManipuleoInstance, field: "nombreCobrador")}</td>
					
						<td><g:formatDate date="${pagoManipuleoInstance.fechaDePago}" /></td>
					
						<td>${fieldValue(bean: pagoManipuleoInstance, field: "deposito")}</td>
					
						<td>${fieldValue(bean: pagoManipuleoInstance, field: "totalPagable")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!pagoManipuleoInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${pagoManipuleoInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
