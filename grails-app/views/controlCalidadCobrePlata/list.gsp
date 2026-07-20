<%@ page import="org.socymet.calidad.ControlCalidadCobrePlata" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Control Calidad Cobre Plata</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Control Calidad Cobre Plata</h3>
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

            <th><g:message code="controlCalidadCobrePlata.recepcionDeComplejo.label" default="Lote" /></th>

            <g:sortableColumn property="nombreCliente" title="${message(code: 'controlCalidadCobrePlata.nombreCliente.label', default: 'Nombre Cliente')}" />

            <g:sortableColumn property="fechaAnalisis" title="${message(code: 'controlCalidadCobrePlata.fechaAnalisis.label', default: 'Fecha De Analisis')}" />

            <g:sortableColumn property="porcentajeHumedadFinal" title="${message(code: 'controlCalidadCobrePlata.porcentajeHumedadFinal.label', default: '% Humedad')}" />

            <g:sortableColumn property="porcentajeMermaFinal" title="${message(code: 'controlCalidadCobrePlata.porcentajeMermaFinal.label', default: '% Merma')}" />

            <g:sortableColumn property="porcentajeCobreFinal" title="${message(code: 'controlCalidadCobrePlata.porcentajeCobreFinal.label', default: '% Cobre')}" />

            <g:sortableColumn property="porcentajePlataFinal" title="${message(code: 'controlCalidadCobrePlata.porcentajeCobreFinal.label', default: 'DM Plata')}" />

        </tr>
                        </thead>
                <tbody>

        <g:each in="${controlCalidadCobrePlataInstanceList}" var="controlCalidadCobrePlataInstance">
            <tr>

                <td><g:link action="show" id="${controlCalidadCobrePlataInstance.id}">${fieldValue(bean: controlCalidadCobrePlataInstance, field: "recepcionDeComplejo")}</g:link></td>

                <td>${fieldValue(bean: controlCalidadCobrePlataInstance, field: "nombreCliente")}</td>

                <td><g:formatDate date="${controlCalidadCobrePlataInstance.fechaAnalisis}" format="dd/MM/yyyy"/></td>

                <td>${fieldValue(bean: controlCalidadCobrePlataInstance, field: "porcentajeHumedadFinal")}</td>

                <td>${fieldValue(bean: controlCalidadCobrePlataInstance, field: "porcentajeMermaFinal")}</td>

                <td>${fieldValue(bean: controlCalidadCobrePlataInstance, field: "porcentajeCobreFinal")}</td>

                <td>${fieldValue(bean: controlCalidadCobrePlataInstance, field: "porcentajePlataFinal")}</td>

            </tr>
        </g:each>
        
                <g:if test="${!controlCalidadCobrePlataInstanceList}">
                    <tr><td colspan="7" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${controlCalidadCobrePlataInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
