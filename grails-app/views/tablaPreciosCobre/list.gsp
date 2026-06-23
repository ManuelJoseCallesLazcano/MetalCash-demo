<%@ page import="org.socymet.cotizaciones.TablaPreciosCobre" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Tabla Precios Cobre</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Tabla Precios Cobre</h3>
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
					
						<g:sortableColumn property="nombreTabla" title="${message(code: 'tablaPreciosCobre.nombreTabla.label', default: 'Nombre Tabla')}" />
					
						<th><g:message code="tablaPreciosCobre.empresa.label" default="Empresa" /></th>
					
						<g:sortableColumn property="leyInicial" title="${message(code: 'tablaPreciosCobre.leyInicial.label', default: 'Ley Inicial')}" />
					
						<g:sortableColumn property="leyFinal" title="${message(code: 'tablaPreciosCobre.leyFinal.label', default: 'Ley Final')}" />
					
						<g:sortableColumn property="valorPorPunto" title="${message(code: 'tablaPreciosCobre.valorPorPunto.label', default: 'Valor Por Punto')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${tablaPreciosCobreInstanceList}" var="tablaPreciosCobreInstance">
					<tr>
					
						<td><g:link action="show" id="${tablaPreciosCobreInstance.id}">${fieldValue(bean: tablaPreciosCobreInstance, field: "nombreTabla")}</g:link></td>
					
						<td>${fieldValue(bean: tablaPreciosCobreInstance, field: "empresa")}</td>
					
						<td>${fieldValue(bean: tablaPreciosCobreInstance, field: "leyInicial")}</td>
					
						<td>${fieldValue(bean: tablaPreciosCobreInstance, field: "leyFinal")}</td>
					
						<td>${fieldValue(bean: tablaPreciosCobreInstance, field: "valorPorPunto")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!tablaPreciosCobreInstanceList}">
                    <tr><td colspan="5" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${tablaPreciosCobreInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
