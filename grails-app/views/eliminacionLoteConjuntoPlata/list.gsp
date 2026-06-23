<%@ page import="org.socymet.liquidacion.EliminacionLoteConjuntoPlata" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Eliminacion Lote Conjunto Plata</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Eliminacion Lote Conjunto Plata</h3>
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

            <g:sortableColumn property="lote" title="${message(code: 'eliminacionLoteConjuntoPlata.lote.label', default: 'Lote')}" />

            <g:sortableColumn property="nombreCliente" title="${message(code: 'eliminacionLoteConjuntoPlata.nombreCliente.label', default: 'Nombre Cliente')}" />

            <g:sortableColumn property="nombreEmpresa" title="${message(code: 'eliminacionLoteConjuntoPlata.nombreEmpresa.label', default: 'Nombre Empresa')}" />

            <g:sortableColumn property="fechaDeAsignacion" title="${message(code: 'eliminacionLoteConjuntoPlata.fechaDeAsignacion.label', default: 'Fecha De Asignacion')}" />

            <g:sortableColumn property="conjuntoOriginal" title="${message(code: 'eliminacionLoteConjuntoPlata.conjuntoOriginal.label', default: 'Conjunto Original')}" />

        </tr>
                        </thead>
                <tbody>

        <g:each in="${eliminacionLoteConjuntoPlataInstanceList}" var="eliminacionLoteConjuntoPlataInstance">
            <tr>

                <td><g:link action="show" id="${eliminacionLoteConjuntoPlataInstance.id}">${fieldValue(bean: eliminacionLoteConjuntoPlataInstance, field: "lote")}</g:link></td>

                <td>${fieldValue(bean: eliminacionLoteConjuntoPlataInstance, field: "nombreCliente")}</td>

                <td>${fieldValue(bean: eliminacionLoteConjuntoPlataInstance, field: "nombreEmpresa")}</td>

                <td><g:formatDate date="${eliminacionLoteConjuntoPlataInstance.fechaDeAsignacion}" format="dd/MM/yyyy HH:mm:ss"/></td>

                <td>${fieldValue(bean: eliminacionLoteConjuntoPlataInstance, field: "conjuntoOriginal")}</td>

            </tr>
        </g:each>
        
                <g:if test="${!eliminacionLoteConjuntoPlataInstanceList}">
                    <tr><td colspan="5" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${eliminacionLoteConjuntoPlataInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
