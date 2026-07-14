<%@ page import="org.smart.compositos.Comprador" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Compradores</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Compradores</h3>
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
            <g:form action="index" method="GET">
                <div class="input-group" style="max-width:460px">
                    <div class="input-group-prepend">
                        <span class="input-group-text border-right-0 bg-white">
                            <i class="fas fa-search text-muted fa-sm"></i>
                        </span>
                    </div>
                    <input type="text" name="q"
                           class="form-control form-control-sm border-left-0"
                           placeholder="Buscar por comprador o contacto…"
                           value="${params.q ?: ''}"
                           autocomplete="off"/>
                    <div class="input-group-append">
                        <button type="submit" class="btn btn-secondary btn-sm">Buscar</button>
                        <g:if test="${params.q}">
                            <g:link action="index" class="btn btn-outline-secondary btn-sm" title="Limpiar búsqueda">
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
                    <g:sortableColumn property="nombreComprador" title="Comprador" params="${[q: params.q]}"/>
                    <g:sortableColumn property="nombreContacto"  title="Contacto"  params="${[q: params.q]}"/>
                    <g:sortableColumn property="telefono"        title="Teléfono"  params="${[q: params.q]}"/>
                    <g:sortableColumn property="email"           title="Email"     params="${[q: params.q]}"/>
                    <th style="width:90px"></th>
                </tr>
            </thead>
            <tbody>
            <g:each in="${compradorInstanceList}" var="inst">
                <tr>
                    <td>${fieldValue(bean: inst, field: 'nombreComprador')}</td>
                    <td>${fieldValue(bean: inst, field: 'nombreContacto')}</td>
                    <td>${fieldValue(bean: inst, field: 'telefono')}</td>
                    <td>${fieldValue(bean: inst, field: 'email')}</td>
                    <td class="text-nowrap">
                        <g:link action="show" id="${inst.id}" class="btn btn-info btn-xs"><i class="fas fa-eye"></i></g:link>
                        <g:link action="edit" id="${inst.id}" class="btn btn-warning btn-xs"><i class="fas fa-edit"></i></g:link>
                    </td>
                </tr>
            </g:each>
            <g:if test="${!compradorInstanceList}">
                <tr>
                    <td colspan="5" class="text-center text-muted py-4">
                        <g:if test="${params.q}">
                            No hay compradores que coincidan con "<strong>${params.q.encodeAsHTML()}</strong>".
                        </g:if>
                        <g:else>No hay compradores registrados.</g:else>
                    </td>
                </tr>
            </g:if>
            </tbody>
        </table>
   </div>
 </div>
    <div class="card-footer">
        <g:paginate total="${compradorInstanceTotal ?: 0}" params="${[q: params.q]}"/>
        <g:if test="${params.q}">
            <div class="text-center small text-muted mt-1">
                ${compradorInstanceTotal ?: 0} resultado(s) para
                "<strong>${params.q.encodeAsHTML()}</strong>"
            </div>
        </g:if>
    </div>
</div>
</body>
</html>
