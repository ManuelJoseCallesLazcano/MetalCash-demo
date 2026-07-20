<%@ page import="org.socymet.proveedor.Cliente" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Clientes</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Clientes</h3>
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
                           placeholder="Buscar por C.I. o nombre…"
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

            %{--<th><g:message code="cliente.id.label" default="ID"/></th>--}%

            <g:sortableColumn property="empresa" title="${message(code: 'cliente.empresa.label', default: 'Empresa')}" params="${[q: params.q]}"/>

            <g:sortableColumn property="ci" title="${message(code: 'cliente.ci.label', default: 'Ci')}" params="${[q: params.q]}"/>

            <g:sortableColumn property="nombre" title="${message(code: 'cliente.nombre.label', default: 'Nombre')}" params="${[q: params.q]}"/>

            <g:sortableColumn property="telefono"
                              title="${message(code: 'cliente.telefono.label', default: 'Telefono')}" params="${[q: params.q]}"/>

%{--            <g:sortableColumn property="celular" title="${message(code: 'cliente.celular.label', default: 'Dirección')}" params="${[q: params.q]}"/>--}%

            <th style="width:90px"></th>

        </tr>
                        </thead>
                <tbody>

        <g:each in="${clienteInstanceList}" var="clienteInstance">
            <tr>

                %{--<td><g:link action="show"--}%
                            %{--id="${clienteInstance.id}">${fieldValue(bean: clienteInstance, field: "id")}</g:link></td>--}%

                <td><g:link action="show" id="${clienteInstance.id}">${fieldValue(bean: clienteInstance, field: "empresa")}</g:link></td>

                <td>${fieldValue(bean: clienteInstance, field: "ci")}</td>

                <td><g:link action="show" id="${clienteInstance.id}">${fieldValue(bean: clienteInstance, field: "nombre")}</g:link></td>
                %{--<td>${fieldValue(bean: clienteInstance, field: "nombre")}</td>--}%

                <td>${fieldValue(bean: clienteInstance, field: "telefono")}</td>

%{--                <td>${fieldValue(bean: clienteInstance, field: "celular")}</td>--}%

                <td class="text-nowrap">
                    <g:link action="show" id="${clienteInstance.id}" class="btn btn-info btn-xs" title="Ver"><i class="fas fa-eye"></i></g:link>
                    <g:link action="edit" id="${clienteInstance.id}" class="btn btn-warning btn-xs" title="Editar"><i class="fas fa-edit"></i></g:link>
                </td>

            </tr>
        </g:each>
        
                <g:if test="${!clienteInstanceList}">
                    <tr>
                        <td colspan="6" class="text-center text-muted py-4">
                            <g:if test="${params.q}">
                                No hay clientes que coincidan con "<strong>${params.q.encodeAsHTML()}</strong>".
                            </g:if>
                            <g:else>No hay clientes registrados.</g:else>
                        </td>
                    </tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${clienteInstanceTotal ?: 0}" params="${[q: params.q]}"/>
        <g:if test="${params.q}">
            <div class="text-center small text-muted mt-1">
                ${clienteInstanceTotal ?: 0} resultado(s) para
                "<strong>${params.q.encodeAsHTML()}</strong>"
            </div>
        </g:if>
    </div>
</div>
</body>
</html>
