<%@ page import="org.socymet.calidad.ControlCalidadComplejo" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Análisis de Laboratorio</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Análisis de Laboratorio</h3>
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
                    Swal.fire({ icon: '${flash.swalIcon ?: 'info'}', title: '${flash.swalTitle ?: 'Información'}',
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
                           placeholder="Buscar lote, cliente, empresa o N° análisis…"
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
                    <th>Lote</th>
                    <g:sortableColumn property="nombreCliente" title="Cliente" params="${[q: q]}"/>
                    <g:sortableColumn property="fechaAnalisis" title="Fecha Análisis" params="${[q: q]}"/>
                    <g:sortableColumn property="porcentajeZincPromexbol" title="Zinc [%]" params="${[q: q]}"/>
                    <g:sortableColumn property="porcentajePlomoPromexbol" title="Plomo [%]" params="${[q: q]}"/>
                    <g:sortableColumn property="porcentajePlataPromexbol" title="Plata [DM]" params="${[q: q]}"/>
                    <g:sortableColumn property="porcentajeHumedadPromexbol" title="Humedad [%]" params="${[q: q]}"/>
                    <th style="width:90px"></th>
                </tr>
            </thead>
            <tbody>
            <g:if test="${!controlCalidadComplejoInstanceList}">
                <tr>
                    <td colspan="8" class="text-center text-muted py-3">
                        <g:if test="${q}">No se encontraron análisis para "${q}".</g:if>
                        <g:else>No hay análisis registrados.</g:else>
                    </td>
                </tr>
            </g:if>
            <g:each in="${controlCalidadComplejoInstanceList}" var="inst">
                <tr>
                    <td><g:link action="show" id="${inst.id}">${fieldValue(bean: inst, field: "recepcionDeComplejo")}</g:link></td>
                    <td>${fieldValue(bean: inst, field: "nombreCliente")}</td>
                    <td><g:formatDate date="${inst.fechaAnalisis}" format="dd/MM/yyyy"/></td>
                    <td>${fieldValue(bean: inst, field: "porcentajeZincPromexbol")}</td>
                    <td>${fieldValue(bean: inst, field: "porcentajePlomoPromexbol")}</td>
                    <td>${fieldValue(bean: inst, field: "porcentajePlataPromexbol")}</td>
                    <td>${fieldValue(bean: inst, field: "porcentajeHumedadPromexbol")}</td>
                    <td class="text-nowrap">
                        <g:link action="show" id="${inst.id}" class="btn btn-info btn-xs"><i class="fas fa-eye"></i></g:link>
                        <g:set var="motivos" value="${inst.motivosBloqueo()}"/>
                        <g:if test="${!motivos}">
                            <g:link action="edit" id="${inst.id}" class="btn btn-warning btn-xs"><i class="fas fa-edit"></i></g:link>
                        </g:if>
                        <g:else>
                            <span class="btn btn-outline-secondary btn-xs disabled" title="No editable: ${motivos.join('; ')}"><i class="fas fa-lock"></i></span>
                        </g:else>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${controlCalidadComplejoInstanceTotal ?: 0}" params="${[q: q]}"/>
    </div>
</div>
</body>
</html>
