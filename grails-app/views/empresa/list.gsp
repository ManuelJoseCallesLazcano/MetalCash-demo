<%@ page import="org.socymet.proveedor.Empresa" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Empresas</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Empresas</h3>
        <div class="ml-auto">
            <g:link action="create" class="btn btn-primary btn-sm">
                <i class="fas fa-plus"></i> Nueva Empresa
            </g:link>
        </div>
    </div>
    <div class="card-body p-0">
        <g:if test="${flash.message}">
            <div id="swalFlashMsg" style="display:none">${flash.message}</div>
            <script>
                document.addEventListener('DOMContentLoaded', function () {
                    Swal.fire({
                        icon: 'info',
                        title: 'Información',
                        text: document.getElementById('swalFlashMsg').textContent.trim(),
                        confirmButtonText: 'Aceptar'
                    });
                });
            </script>
        </g:if>
        <div class="px-3 pt-3 pb-2">
            <g:form action="list" method="GET">
                <div class="input-group" style="max-width:480px">
                    <div class="input-group-prepend">
                        <span class="input-group-text border-right-0 bg-white">
                            <i class="fas fa-search text-muted fa-sm"></i>
                        </span>
                    </div>
                    <input type="text" name="q"
                           class="form-control form-control-sm border-left-0"
                           placeholder="Buscar por nombre, código, municipio o NIM…"
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
        <div class="table-responsive">
            <table class="table table-hover table-striped mb-0">
                <thead class="thead-light">
                    <tr>
                        <g:sortableColumn property="tipoDeEmpresa" title="Tipo" class="px-3" params="${[q: params.q]}"/>
                        <g:sortableColumn property="nombreDeEmpresa" title="Nombre de Empresa" class="px-3" params="${[q: params.q]}"/>
                        <g:sortableColumn property="departamento" title="Departamento" class="px-3" params="${[q: params.q]}"/>
%{--                        <g:sortableColumn property="provincia" title="Provincia" class="px-3"/>--}%
                        <g:sortableColumn property="municipio" title="Municipio" class="px-3" params="${[q: params.q]}"/>
                        <th class="px-3" style="width:90px"></th>
                    </tr>
                </thead>
                <tbody>
                <g:each in="${empresaInstanceList}" var="empresa">
                    <tr>
                        <td class="px-3">
                            <span class="badge badge-secondary">${fieldValue(bean: empresa, field: 'tipoDeEmpresa')}</span>
                        </td>
                        <td class="px-3">
                            <g:link action="show" id="${empresa.id}" class="font-weight-bold">
                                ${fieldValue(bean: empresa, field: 'nombreDeEmpresa')}
                            </g:link>
                        </td>
                        <td class="px-3">${fieldValue(bean: empresa, field: 'departamento')}</td>
%{--                        <td class="px-3">${fieldValue(bean: empresa, field: 'provincia')}</td>--}%
                        <td class="px-3">${fieldValue(bean: empresa, field: 'municipio')}</td>
                        <td class="px-3 text-right text-nowrap">
                            <g:link action="show" id="${empresa.id}" class="btn btn-xs btn-info" title="Ver">
                                <i class="fas fa-eye"></i>
                            </g:link>
                            <g:link action="edit" id="${empresa.id}" class="btn btn-xs btn-warning" title="Editar">
                                <i class="fas fa-edit"></i>
                            </g:link>
                        </td>
                    </tr>
                </g:each>
                <g:if test="${!empresaInstanceList}">
                    <tr>
                        <td colspan="5" class="text-center text-muted py-4">
                            <g:if test="${params.q}">
                                No hay empresas que coincidan con "<strong>${params.q.encodeAsHTML()}</strong>".
                            </g:if>
                            <g:else>No hay empresas registradas.</g:else>
                        </td>
                    </tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${empresaInstanceTotal ?: 0}" params="${[q: params.q]}"/>
        <g:if test="${params.q}">
            <div class="text-center small text-muted mt-1">
                ${empresaInstanceTotal ?: 0} resultado(s) para
                "<strong>${params.q.encodeAsHTML()}</strong>"
            </div>
        </g:if>
    </div>
</div>
</body>
</html>
