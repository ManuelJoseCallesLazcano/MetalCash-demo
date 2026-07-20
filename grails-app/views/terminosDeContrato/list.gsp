<%@ page import="org.socymet.cotizaciones.TerminosDeContrato" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Términos de Contrato</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Términos de Contrato</h3>
        <div class="ml-auto">
            <g:link action="create" class="btn btn-primary btn-sm"><i class="fas fa-plus"></i> Nuevo</g:link>
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
        <div class="px-3 pt-3 pb-2">
            <g:form action="list" method="GET">
                <div class="input-group" style="max-width:460px">
                    <div class="input-group-prepend">
                        <span class="input-group-text border-right-0 bg-white"><i class="fas fa-search text-muted fa-sm"></i></span>
                    </div>
                    <input type="text" name="q"
                           class="form-control form-control-sm border-left-0"
                           placeholder="Buscar por nombre de contrato…"
                           value="${q ?: ''}"
                           autocomplete="off"/>
                    <div class="input-group-append">
                        <button type="submit" class="btn btn-secondary btn-sm">Buscar</button>
                        <g:if test="${q}">
                            <g:link action="list" class="btn btn-outline-secondary btn-sm" title="Limpiar búsqueda"><i class="fas fa-times"></i></g:link>
                        </g:if>
                    </div>
                </div>
            </g:form>
        </div>
        <div class="table-responsive">
            <table class="table table-hover table-striped mb-0">
                <thead class="thead-light">
                <tr>
                    <g:sortableColumn property="nombreContrato" title="Nombre del Contrato"/>
                    <g:sortableColumn property="tipoDeMineral" title="Mineral"/>
                    <th class="text-right">Acciones</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${terminosDeContratoInstanceList}" var="t">
                    <tr>
                        <td><g:link action="show" id="${t.id}">${fieldValue(bean: t, field: "nombreContrato")}</g:link></td>
                        <td>${t.tipoDeMineral}</td>
                        <td class="text-right">
                            <g:link action="show" id="${t.id}" class="btn btn-info btn-xs"><i class="fas fa-eye"></i></g:link>
                            <g:link action="edit" id="${t.id}" class="btn btn-warning btn-xs"><i class="fas fa-edit"></i></g:link>
                        </td>
                    </tr>
                </g:each>
                <g:if test="${!terminosDeContratoInstanceList}">
                    <tr><td colspan="3" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${terminosDeContratoInstanceTotal ?: 0}"/>
    </div>
</div>
</body>
</html>
