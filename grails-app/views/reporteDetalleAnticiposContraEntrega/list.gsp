<%@ page import="org.socymet.org.socymet.reportes.ReporteDetalleAnticiposContraEntrega" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Reporte Detalle Anticipos Contra Entrega</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Reporte Detalle Anticipos Contra Entrega</h3>
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
					
						<th><g:message code="reporteDetalleAnticiposContraEntrega.empresa.label" default="Empresa" /></th>
					
						<g:sortableColumn property="fechaInicial" title="${message(code: 'reporteDetalleAnticiposContraEntrega.fechaInicial.label', default: 'Fecha Inicial')}" />
					
						<g:sortableColumn property="fechaFinal" title="${message(code: 'reporteDetalleAnticiposContraEntrega.fechaFinal.label', default: 'Fecha Final')}" />
					
						<g:sortableColumn property="numeroAnticipoInicial" title="${message(code: 'reporteDetalleAnticiposContraEntrega.numeroAnticipoInicial.label', default: 'Numero Anticipo Inicial')}" />
					
						<g:sortableColumn property="numeroAnticipoFinal" title="${message(code: 'reporteDetalleAnticiposContraEntrega.numeroAnticipoFinal.label', default: 'Numero Anticipo Final')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${reporteDetalleAnticiposContraEntregaInstanceList}" var="reporteDetalleAnticiposContraEntregaInstance">
					<tr>
					
						<td><g:link action="show" id="${reporteDetalleAnticiposContraEntregaInstance.id}">${fieldValue(bean: reporteDetalleAnticiposContraEntregaInstance, field: "empresa")}</g:link></td>
					
						<td><g:formatDate date="${reporteDetalleAnticiposContraEntregaInstance.fechaInicial}" /></td>
					
						<td><g:formatDate date="${reporteDetalleAnticiposContraEntregaInstance.fechaFinal}" /></td>
					
						<td>${fieldValue(bean: reporteDetalleAnticiposContraEntregaInstance, field: "numeroAnticipoInicial")}</td>
					
						<td>${fieldValue(bean: reporteDetalleAnticiposContraEntregaInstance, field: "numeroAnticipoFinal")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!reporteDetalleAnticiposContraEntregaInstanceList}">
                    <tr><td colspan="5" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${reporteDetalleAnticiposContraEntregaInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
