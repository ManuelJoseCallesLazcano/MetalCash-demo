<%@ page import="org.socymet.calidad.ControlCalidadZincPlata" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Control Calidad Zinc Plata</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Control Calidad Zinc Plata</h3>
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

            <th><g:message code="controlCalidadZincPlata.recepcionDeComplejo.label" default="Lote" /></th>

            <g:sortableColumn property="nombreCliente" title="${message(code: 'controlCalidadZincPlata.nombreCliente.label', default: 'Nombre Cliente')}" />

            <g:sortableColumn property="fechaAnalisis" title="${message(code: 'controlCalidadZincPlata.fechaAnalisis.label', default: 'Fecha De Analisis')}" />

            <g:sortableColumn property="porcentajeHumedadFinal" title="${message(code: 'controlCalidadZincPlata.porcentajeHumedadFinal.label', default: '% Humedad')}" />

            <g:sortableColumn property="porcentajeMermaFinal" title="${message(code: 'controlCalidadZincPlata.porcentajeMermaFinal.label', default: '% Merma')}" />

            <g:sortableColumn property="porcentajeZincFinal" title="${message(code: 'controlCalidadZincPlata.porcentajeZincFinal.label', default: '% Plomo')}" />

            <g:sortableColumn property="porcentajePlataFinal" title="${message(code: 'controlCalidadZincPlata.porcentajeZincFinal.label', default: 'DM Plata')}" />

        </tr>
                        </thead>
                <tbody>

        <g:each in="${controlCalidadZincPlataInstanceList}" var="controlCalidadZincPlataInstance">
            <tr>

                <td><g:link action="show" id="${controlCalidadZincPlataInstance.id}">${fieldValue(bean: controlCalidadZincPlataInstance, field: "recepcionDeComplejo")}</g:link></td>

                <td>${fieldValue(bean: controlCalidadZincPlataInstance, field: "nombreCliente")}</td>

                <td><g:formatDate date="${controlCalidadZincPlataInstance.fechaAnalisis}" format="dd/MM/yyyy"/></td>

                <td>${fieldValue(bean: controlCalidadZincPlataInstance, field: "porcentajeHumedadFinal")}</td>

                <td>${fieldValue(bean: controlCalidadZincPlataInstance, field: "porcentajeMermaFinal")}</td>

                <td>${fieldValue(bean: controlCalidadZincPlataInstance, field: "porcentajeZincFinal")}</td>

                <td>${fieldValue(bean: controlCalidadZincPlataInstance, field: "porcentajePlataFinal")}</td>

            </tr>
        </g:each>
        
                <g:if test="${!controlCalidadZincPlataInstanceList}">
                    <tr><td colspan="7" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${controlCalidadZincPlataInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
