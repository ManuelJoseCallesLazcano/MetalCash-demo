<%@ page import="org.socymet.cancelacion.PagoBonoProduccion" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Pago Bono Produccion</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Pago Bono Produccion</h3>
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
					
						<g:sortableColumn property="numeroComprobante" title="${message(code: 'pagoBonoProduccion.numeroComprobante.label', default: 'No.')}" />

                        <g:sortableColumn property="ci" title="${message(code: 'pagoBonoProduccion.ci.label', default: 'Ci')}" />

                        <g:sortableColumn property="nombreCobrador" title="${message(code: 'pagoBonoProduccion.nombreCobrador.label', default: 'Nombre Cobrador')}" />

                        <g:sortableColumn property="empresa" title="${message(code: 'pagoBonoProduccion.empresa.label', default: 'Empresa')}" />

                        <th><g:message code="pagoBonoProduccion.tipoSeleccion.label" default="Pagado A" /></th>

                        <g:sortableColumn property="fechaDePago" title="${message(code: 'pagoBonoProduccion.fechaDePago.label', default: 'Fecha De Pago')}" />

                        <g:sortableColumn property="totalKilosNetosSecos" title="${message(code: 'pagoBonoProduccion.totalKilosNetosSecos.label', default: 'Total KNS')}" />

                        <g:sortableColumn property="totalPagable" title="${message(code: 'pagoBonoProduccion.totalPagable.label', default: 'Total Pagable')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${pagoBonoProduccionInstanceList}" var="pagoBonoProduccionInstance">
					<tr>
					
						<td><g:link action="show" id="${pagoBonoProduccionInstance.id}"><g:formatNumber number="${pagoBonoProduccionInstance.numeroComprobante}" format="000000"/></g:link></td>
					
						<td>${fieldValue(bean: pagoBonoProduccionInstance, field: "ci")}</td>
					
						<td>${fieldValue(bean: pagoBonoProduccionInstance, field: "nombreCobrador")}</td>

                        <td>${fieldValue(bean: pagoBonoProduccionInstance, field: "empresa")}</td>
					
						<td>${(pagoBonoProduccionInstance.tipoSeleccion.equals("INDIVIDUAL"))?pagoBonoProduccionInstance.cliente:pagoBonoProduccionInstance.cuadrilla}</td>
					
						<td><g:formatDate date="${pagoBonoProduccionInstance.fechaDePago}" /></td>

                        <td>${fieldValue(bean: pagoBonoProduccionInstance, field: "totalKilosNetosSecos")}</td>

                        <td>${fieldValue(bean: pagoBonoProduccionInstance, field: "totalPagable")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!pagoBonoProduccionInstanceList}">
                    <tr><td colspan="8" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${pagoBonoProduccionInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
