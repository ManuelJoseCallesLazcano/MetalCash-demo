<%@ page import="org.socymet.calidad.ControlCalidadPlomoPlata" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Control Calidad Plomo Plata</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Control Calidad Plomo Plata</h3>
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

                    <th><g:message code="controlCalidadPlomoPlata.recepcionDeComplejo.label" default="Lote" /></th>

                    <g:sortableColumn property="nombreCliente" title="${message(code: 'controlCalidadPlomoPlata.nombreCliente.label', default: 'Nombre Cliente')}" />

                    <g:sortableColumn property="fechaAnalisis" title="${message(code: 'controlCalidadPlomoPlata.fechaAnalisis.label', default: 'Fecha De Analisis')}" />

                    <g:sortableColumn property="porcentajeHumedadFinal" title="${message(code: 'controlCalidadPlomoPlata.porcentajeHumedadFinal.label', default: '% Humedad')}" />

                    <g:sortableColumn property="porcentajeMermaFinal" title="${message(code: 'controlCalidadPlomoPlata.porcentajeMermaFinal.label', default: '% Merma')}" />

                    <g:sortableColumn property="porcentajePlomoFinal" title="${message(code: 'controlCalidadPlomoPlata.porcentajePlomoFinal.label', default: '% Plomo')}" />

                    <g:sortableColumn property="porcentajePlataFinal" title="${message(code: 'controlCalidadPlomoPlata.porcentajePlomoFinal.label', default: 'DM Plata')}" />

                </tr>
                                </thead>
                <tbody>

                <g:each in="${controlCalidadPlomoPlataInstanceList}" var="controlCalidadPlomoPlataInstance">
                    <tr>

                        <td><g:link action="show" id="${controlCalidadPlomoPlataInstance.id}">${fieldValue(bean: controlCalidadPlomoPlataInstance, field: "recepcionDeComplejo")}</g:link></td>

                        <td>${fieldValue(bean: controlCalidadPlomoPlataInstance, field: "nombreCliente")}</td>

                        <td><g:formatDate date="${controlCalidadPlomoPlataInstance.fechaAnalisis}" format="dd/MM/yyyy"/></td>

                        <td>${fieldValue(bean: controlCalidadPlomoPlataInstance, field: "porcentajeHumedadFinal")}</td>

                        <td>${fieldValue(bean: controlCalidadPlomoPlataInstance, field: "porcentajeMermaFinal")}</td>

                        <td>${fieldValue(bean: controlCalidadPlomoPlataInstance, field: "porcentajePlomoFinal")}</td>

                        <td>${fieldValue(bean: controlCalidadPlomoPlataInstance, field: "porcentajePlataFinal")}</td>

                    </tr>
                </g:each>
                
                <g:if test="${!controlCalidadPlomoPlataInstanceList}">
                    <tr><td colspan="7" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${controlCalidadPlomoPlataInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
