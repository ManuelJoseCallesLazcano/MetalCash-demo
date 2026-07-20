<%@ page import="org.socymet.org.socymet.reportes.Reimpresion" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Reimpresion</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Reimpresion</h3>
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
					
						<g:sortableColumn property="fecha" title="${message(code: 'reimpresion.fecha.label', default: 'Fecha')}" />
					
						<g:sortableColumn property="nombreReporte" title="${message(code: 'reimpresion.nombreReporte.label', default: 'Nombre Reporte')}" />
					
						<g:sortableColumn property="identificadorDocumento" title="${message(code: 'reimpresion.identificadorDocumento.label', default: 'Identificador Documento')}" />
					
						<g:sortableColumn property="lote" title="${message(code: 'reimpresion.lote.label', default: 'Lote')}" />
					
						<g:sortableColumn property="motivo" title="${message(code: 'reimpresion.motivo.label', default: 'Motivo')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${reimpresionInstanceList}" var="reimpresionInstance">
					<tr>
					
						<td><g:link action="show" id="${reimpresionInstance.id}">${fieldValue(bean: reimpresionInstance, field: "fecha")}</g:link></td>
					
						<td>${fieldValue(bean: reimpresionInstance, field: "nombreReporte")}</td>
					
						<td>${fieldValue(bean: reimpresionInstance, field: "identificadorDocumento")}</td>
					
						<td>${fieldValue(bean: reimpresionInstance, field: "lote")}</td>
					
						<td>${fieldValue(bean: reimpresionInstance, field: "motivo")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!reimpresionInstanceList}">
                    <tr><td colspan="5" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${reimpresionInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
