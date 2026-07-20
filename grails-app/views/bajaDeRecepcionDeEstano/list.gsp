<%@ page import="org.socymet.recepcion.BajaDeRecepcionDeEstano" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Baja De Recepcion De Estano</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Baja De Recepcion De Estano</h3>
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

                        <g:sortableColumn property="lote" title="${message(code: 'bajaDeRecepcionDeEstano.lote.label', default: 'Lote')}" />

                        <g:sortableColumn property="nombreCliente" title="${message(code: 'bajaDeRecepcionDeEstano.nombreCliente.label', default: 'Nombre Cliente')}" />

                        <g:sortableColumn property="nombreEmpresa" title="${message(code: 'bajaDeRecepcionDeEstano.nombreEmpresa.label', default: 'Nombre Empresa')}" />

                        <g:sortableColumn property="fechaDeBaja" title="${message(code: 'bajaDeRecepcionDeEstano.fechaDeBaja.label', default: 'Fecha De Baja')}" />

                        <g:sortableColumn property="tipoDeBaja" title="${message(code: 'bajaDeRecepcionDeEstano.tipoDeBaja.label', default: 'Tipo De Baja')}" />

                        <g:sortableColumn property="totalDeGastos" title="${message(code: 'bajaDeRecepcionDeEstano.totalDeGastos.label', default: 'Total De Gastos')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${bajaDeRecepcionDeEstanoInstanceList}" var="bajaDeRecepcionDeEstanoInstance">
					<tr>

                        <td><g:link action="show" id="${bajaDeRecepcionDeEstanoInstance.id}">${fieldValue(bean: bajaDeRecepcionDeEstanoInstance, field: "lote")}</g:link></td>

                        <td>${fieldValue(bean: bajaDeRecepcionDeEstanoInstance, field: "nombreCliente")}</td>

                        <td>${fieldValue(bean: bajaDeRecepcionDeEstanoInstance, field: "nombreEmpresa")}</td>

                        <td><g:formatDate date="${bajaDeRecepcionDeEstanoInstance.fechaDeBaja}" format="dd/MM/yyyy"/></td>

                        <td>${fieldValue(bean: bajaDeRecepcionDeEstanoInstance, field: "tipoDeBaja")}</td>

                        <td>${fieldValue(bean: bajaDeRecepcionDeEstanoInstance, field: "totalDeGastos")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!bajaDeRecepcionDeEstanoInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${bajaDeRecepcionDeEstanoInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
