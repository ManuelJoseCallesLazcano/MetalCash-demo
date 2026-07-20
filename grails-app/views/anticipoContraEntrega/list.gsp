<%@ page import="org.socymet.anticipos.AnticipoContraEntrega" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Anticipo Contra Entrega</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Anticipo Contra Entrega</h3>
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
                        <g:sortableColumn property="id" title="${message(code: 'anticipoContraEntrega.id.label', default: 'Id')}" />
					
						<g:sortableColumn property="lote" title="${message(code: 'anticipoContraEntrega.lote.label', default: 'Lote')}" />
					
						<g:sortableColumn property="nombreCliente" title="${message(code: 'anticipoContraEntrega.nombreCliente.label', default: 'Nombre Cliente')}" />
					
						<g:sortableColumn property="nombreEmpresa" title="${message(code: 'anticipoContraEntrega.nombreEmpresa.label', default: 'Nombre Empresa')}" />
					
						<g:sortableColumn property="fechaDeRecepcion" title="${message(code: 'anticipoContraEntrega.fechaDeAnticipo.label', default: 'Fecha De Anticipo')}" />
					
						<g:sortableColumn property="pesoBruto" title="${message(code: 'anticipoContraEntrega.importe.label', default: 'Importe')}" />

                        <g:sortableColumn property="anticipoPagado" title="${message(code: 'anticipoContraEntrega.importe.label', default: 'Pagado?')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${anticipoContraEntregaInstanceList}" var="anticipoContraEntregaInstance">
					<tr>

                        <td><g:link action="show" id="${anticipoContraEntregaInstance.id}">${fieldValue(bean: anticipoContraEntregaInstance, field: "id")}</g:link></td>

                        <td><g:link action="show" id="${anticipoContraEntregaInstance.id}">${fieldValue(bean: anticipoContraEntregaInstance, field: "lote")}</g:link></td>
					
						<td>${fieldValue(bean: anticipoContraEntregaInstance, field: "nombreCliente")}</td>
					
						<td>${fieldValue(bean: anticipoContraEntregaInstance, field: "nombreEmpresa")}</td>
					
						<td><g:formatDate date="${anticipoContraEntregaInstance.fechaDeAnticipo}" format="dd/MM/yyyy"/> </td>

						<td>${fieldValue(bean: anticipoContraEntregaInstance, field: "importe")}</td>

                        <td>${fieldValue(bean: anticipoContraEntregaInstance, field: "anticipoPagado")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!anticipoContraEntregaInstanceList}">
                    <tr><td colspan="7" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${anticipoContraEntregaInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
