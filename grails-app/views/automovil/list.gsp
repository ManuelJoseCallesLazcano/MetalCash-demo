<%@ page import="org.socymet.proveedor.Automovil" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Automóviles</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
                <h3 class="card-title">Automóviles</h3>
        <div class="ml-auto">
            <g:link action="create" class="btn btn-primary btn-sm">
            <i class="fas fa-plus mr-1"></i>Nuevo
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
        <div class="px-3 pt-3 pb-2">
            <g:form action="list" method="GET">
                <div class="input-group" style="max-width:420px">
                    <div class="input-group-prepend">
                        <span class="input-group-text border-right-0 bg-white">
                            <i class="fas fa-search text-muted fa-sm"></i>
                        </span>
                    </div>
                    <input type="text" name="q"
                           class="form-control form-control-sm border-left-0"
                           placeholder="Buscar por placa…"
                           value="${params.q ?: ''}"
                           autocomplete="off"/>
                    <div class="input-group-append">
                        <button type="submit" class="btn btn-secondary btn-sm">Buscar</button>
                        <g:if test="${params.q}">
                            <g:link action="list" class="btn btn-outline-secondary btn-sm" title="Limpiar búsqueda">
                                <i class="fas fa-times"></i>
                            </g:link>
                        </g:if>
                    </div>
                </div>
            </g:form>
        </div>
        <div class="table-responsive"><table class="table table-hover table-striped mb-0">
            <thead class="thead-light">
                <tr>
                    <g:sortableColumn property="placa"  title="Placa"  params="${[q: params.q]}"/>
                    <g:sortableColumn property="modelo" title="Modelo" params="${[q: params.q]}"/>
                    <g:sortableColumn property="color"  title="Color"  params="${[q: params.q]}"/>
                    <th style="width:90px"></th>
                </tr>
            </thead>
            <tbody>
            <g:each in="${automovilInstanceList}" var="inst">
                <tr>
                    <td>${fieldValue(bean: inst, field: 'placa')}</td>
                    <td>${fieldValue(bean: inst, field: 'modelo')}</td>
                    <td>${fieldValue(bean: inst, field: 'color')}</td>
                    <td class="text-nowrap">
                        <g:link action="show" id="${inst.id}" class="btn btn-info btn-xs"><i class="fas fa-eye"></i></g:link>
                        <g:link action="edit" id="${inst.id}" class="btn btn-warning btn-xs"><i class="fas fa-edit"></i></g:link>
                    </td>
                </tr>
            </g:each>
            <g:if test="${!automovilInstanceList}">
                <tr>
                    <td colspan="4" class="text-center text-muted py-4">
                        <g:if test="${params.q}">
                            No hay automóviles que coincidan con "<strong>${params.q.encodeAsHTML()}</strong>".
                        </g:if>
                        <g:else>No hay automóviles registrados.</g:else>
                    </td>
                </tr>
            </g:if>
            </tbody>
        </table>
   </div>
 </div>
    <div class="card-footer">
        <g:paginate total="${automovilInstanceTotal ?: 0}" params="${[q: params.q]}"/>
        <g:if test="${params.q}">
            <div class="text-center small text-muted mt-1">
                ${automovilInstanceTotal ?: 0} resultado(s) para
                "<strong>${params.q.encodeAsHTML()}</strong>"
            </div>
        </g:if>
    </div>
</div>
</body>
</html>
