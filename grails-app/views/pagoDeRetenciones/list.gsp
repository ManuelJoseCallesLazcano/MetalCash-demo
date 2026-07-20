<%@ page import="org.socymet.cancelacion.PagoDeRetenciones" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Pago De Retenciones</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Pago De Retenciones</h3>
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
					
						<g:sortableColumn property="numeroComprobante" title="${message(code: 'pagoDeRetenciones.numeroComprobante.label', default: 'No. Comprobante')}" />
					
						<g:sortableColumn property="beneficiario" title="${message(code: 'pagoDeRetenciones.beneficiario.label', default: 'Beneficiario')}" />
					
						<g:sortableColumn property="fechaDePago" title="${message(code: 'pagoDeRetenciones.fechaDePago.label', default: 'Fecha De Pago')}" />
					
						<g:sortableColumn property="periodo" title="${message(code: 'pagoDeRetenciones.periodo.label', default: 'Periodo')}" />

                        <g:sortableColumn property="quincena" title="${message(code: 'pagoDeRetenciones.quincena.label', default: 'Quincena')}" />

                        <g:sortableColumn property="descripcion" title="${message(code: 'pagoDeRetenciones.descripcion.label', default: 'Descripcion')}" />

                        <g:sortableColumn property="totalPagable" title="${message(code: 'pagoDeRetenciones.totalPagable.label', default: 'Total Pagable')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${pagoDeRetencionesInstanceList}" var="pagoDeRetencionesInstance">
					<tr>
					
						<td><g:link action="show" id="${pagoDeRetencionesInstance.id}"><g:formatNumber number="${pagoDeRetencionesInstance.numeroComprobante}" format="000000"/></g:link></td>
					
						<td>${fieldValue(bean: pagoDeRetencionesInstance, field: "beneficiario")}</td>
					
						<td><g:formatDate date="${pagoDeRetencionesInstance.fechaDePago}" /></td>

                        <td><g:formatDate date="${pagoDeRetencionesInstance.periodo}" format="MM/yyyy"/></td>

                        <td>${fieldValue(bean: pagoDeRetencionesInstance, field: "quincena")}</td>

                        <td>${fieldValue(bean: pagoDeRetencionesInstance, field: "descripcion")}</td>

                        <td>${fieldValue(bean: pagoDeRetencionesInstance, field: "totalPagable")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!pagoDeRetencionesInstanceList}">
                    <tr><td colspan="7" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${pagoDeRetencionesInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
