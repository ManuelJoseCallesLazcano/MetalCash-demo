<%@ page import="org.socymet.recepcion.BajaDeRecepcionDePlomoPlata" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Baja De Recepcion De Plomo Plata</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Baja De Recepcion De Plomo Plata</h3>
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

                        <g:sortableColumn property="lote" title="${message(code: 'bajaDeRecepcionDePlomoPlata.lote.label', default: 'Lote')}" />

                        <g:sortableColumn property="nombreCliente" title="${message(code: 'bajaDeRecepcionDePlomoPlata.nombreCliente.label', default: 'Nombre Cliente')}" />

                        <g:sortableColumn property="nombreEmpresa" title="${message(code: 'bajaDeRecepcionDePlomoPlata.nombreEmpresa.label', default: 'Nombre Empresa')}" />

                        <g:sortableColumn property="fechaDeBaja" title="${message(code: 'bajaDeRecepcionDePlomoPlata.fechaDeBaja.label', default: 'Fecha De Baja')}" />

                        <g:sortableColumn property="tipoDeBaja" title="${message(code: 'bajaDeRecepcionDePlomoPlata.tipoDeBaja.label', default: 'Tipo De Baja')}" />

                        <g:sortableColumn property="totalDeGastos" title="${message(code: 'bajaDeRecepcionDePlomoPlata.totalDeGastos.label', default: 'Total De Gastos')}" />
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${bajaDeRecepcionDePlomoPlataInstanceList}" var="bajaDeRecepcionDePlomoPlataInstance">
					<tr>

                        <td><g:link action="show" id="${bajaDeRecepcionDePlomoPlataInstance.id}">${fieldValue(bean: bajaDeRecepcionDePlomoPlataInstance, field: "lote")}</g:link></td>

                        <td>${fieldValue(bean: bajaDeRecepcionDePlomoPlataInstance, field: "nombreCliente")}</td>

                        <td>${fieldValue(bean: bajaDeRecepcionDePlomoPlataInstance, field: "nombreEmpresa")}</td>

                        <td><g:formatDate date="${bajaDeRecepcionDePlomoPlataInstance.fechaDeBaja}" format="dd/MM/yyyy"/></td>

                        <td>${fieldValue(bean: bajaDeRecepcionDePlomoPlataInstance, field: "tipoDeBaja")}</td>

                        <td>${fieldValue(bean: bajaDeRecepcionDePlomoPlataInstance, field: "totalDeGastos")}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!bajaDeRecepcionDePlomoPlataInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${bajaDeRecepcionDePlomoPlataInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
