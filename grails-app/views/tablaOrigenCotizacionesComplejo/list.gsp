<%@ page import="org.socymet.cotizaciones.TablaOrigenCotizacionesComplejo" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Tablas de Precios</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Tablas de Precios</h3>
        <div class="ml-auto">
            <g:link action="create" class="btn btn-primary btn-sm"><i class="fas fa-plus"></i> Nueva</g:link>
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
                        <span class="input-group-text border-right-0 bg-white">
                            <i class="fas fa-search text-muted fa-sm"></i>
                        </span>
                    </div>
                    <input type="text" name="q"
                           class="form-control form-control-sm border-left-0"
                           placeholder="Buscar por nombre de tabla…"
                           value="${q ?: ''}"
                           autocomplete="off"/>
                    <div class="input-group-append">
                        <button type="submit" class="btn btn-secondary btn-sm">Buscar</button>
                        <g:if test="${q}">
                            <g:link action="list" class="btn btn-outline-secondary btn-sm" title="Limpiar búsqueda">
                                <i class="fas fa-times"></i>
                            </g:link>
                        </g:if>
                    </div>
                </div>
            </g:form>
        </div>
        <div class="table-responsive">
            <table class="table table-hover table-striped mb-0">
                <thead class="thead-light">
                <tr>
                    <g:sortableColumn property="nombreTabla" title="Nombre"/>
%{--                    <th>Empresa</th>--}%
%{--                    <g:sortableColumn property="naturalezaMineral" title="Naturaleza"/>--}%
                    <th>Cotización Referencial</th>
                    <th class="text-center">Puntos</th>
                    <g:sortableColumn property="fechaActualizacion" title="Actualización"/>
                </tr>
                </thead>
                <tbody>
                <g:each in="${tablaOrigenCotizacionesComplejoInstanceList}" var="t">
                    <tr>
                        <td><g:link action="show" id="${t.id}">${t.nombreTabla}</g:link></td>
%{--                        <td>${t.empresa?.encodeAsHTML() ?: '—'}</td>--}%
%{--                        <td>${t.naturalezaMineral}</td>--}%
                        <td>${t.cotizacionDiariaDeMinerales?.encodeAsHTML() ?: '—'}</td>
                        <td class="text-center">${t.puntos?.size() ?: 0}</td>
                        <td><g:formatDate date="${t.fechaActualizacion}" format="dd/MM/yyyy HH:mm"/></td>
                    </tr>
                </g:each>
                <g:if test="${!tablaOrigenCotizacionesComplejoInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${tablaOrigenCotizacionesComplejoInstanceTotal ?: 0}"/>
    </div>
</div>
</body>
</html>
