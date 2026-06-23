<%@ page import="org.socymet.cancelacion.Cancelacion" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Cancelacion</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Cancelacion</h3>
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
					
						<g:sortableColumn property="lote" title="${message(code: 'cancelacion.lote.label', default: 'Lote')}" />

                        <g:sortableColumn property="nombreEmpresa" title="${message(code: 'cancelacion.nombreEmpresa.label', default: 'Nombre Empresa')}" />

                        <g:sortableColumn property="nombreCliente" title="${message(code: 'cancelacion.nombreCliente.label', default: 'Nombre Cliente')}" />
					
						<g:sortableColumn property="fechaDeLiquidacion" title="${message(code: 'cancelacion.fechaDeLiquidacion.label', default: 'Fecha De Liquidacion')}" />

                        <g:sortableColumn property="fechaDeCancelacion" title="${message(code: 'cancelacion.fechaDeCancelacion.label', default: 'Fecha De Cancelacion')}" />

                        <g:sortableColumn property="totalLiquidoPagable" title="${message(code: 'cancelacion.totalLiquidoPagable.label', default: 'Liquido Pagable')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${cancelacionInstanceList}" var="cancelacionInstance">
					<tr>
					
						<td><g:link action="show" id="${cancelacionInstance.id}">${fieldValue(bean: cancelacionInstance, field: "lote")}</g:link></td>

                        <td>${fieldValue(bean: cancelacionInstance, field: "nombreEmpresa")}</td>

						<td>${fieldValue(bean: cancelacionInstance, field: "nombreCliente")}</td>
					
						<td>${fieldValue(bean: cancelacionInstance, field: "fechaDeLiquidacion")}</td>
					
						<td><g:formatDate date="${cancelacionInstance.fechaDeCancelacion}" format="dd/MM/yyyy"/></td>

                        <td>${fieldValue(bean: cancelacionInstance, field: "totalLiquidoPagable")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!cancelacionInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${cancelacionInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
