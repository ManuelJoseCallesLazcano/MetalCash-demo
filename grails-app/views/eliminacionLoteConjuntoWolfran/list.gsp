<%@ page import="org.socymet.liquidacion.EliminacionLoteConjuntoWolfran" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Eliminacion Lote Conjunto Wolfran</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Eliminacion Lote Conjunto Wolfran</h3>
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

            <g:sortableColumn property="lote" title="${message(code: 'eliminacionLoteConjuntoWolfran.lote.label', default: 'Lote')}" />

            <g:sortableColumn property="nombreCliente" title="${message(code: 'eliminacionLoteConjuntoWolfran.nombreCliente.label', default: 'Nombre Cliente')}" />

            <g:sortableColumn property="nombreEmpresa" title="${message(code: 'eliminacionLoteConjuntoWolfran.nombreEmpresa.label', default: 'Nombre Empresa')}" />

            <g:sortableColumn property="fechaDeAsignacion" title="${message(code: 'eliminacionLoteConjuntoWolfran.fechaDeAsignacion.label', default: 'Fecha De Asignacion')}" />

            <g:sortableColumn property="conjuntoOriginal" title="${message(code: 'eliminacionLoteConjuntoWolfran.conjuntoOriginal.label', default: 'Conjunto Original')}" />

        </tr>
                        </thead>
                <tbody>

        <g:each in="${eliminacionLoteConjuntoWolfranInstanceList}" var="eliminacionLoteConjuntoWolfranInstance">
            <tr>

                <td><g:link action="show" id="${eliminacionLoteConjuntoWolfranInstance.id}">${fieldValue(bean: eliminacionLoteConjuntoWolfranInstance, field: "lote")}</g:link></td>

                <td>${fieldValue(bean: eliminacionLoteConjuntoWolfranInstance, field: "nombreCliente")}</td>

                <td>${fieldValue(bean: eliminacionLoteConjuntoWolfranInstance, field: "nombreEmpresa")}</td>

                <td><g:formatDate date="${eliminacionLoteConjuntoWolfranInstance.fechaDeAsignacion}" format="dd/MM/yyyy HH:mm:ss"/></td>

                <td>${fieldValue(bean: eliminacionLoteConjuntoWolfranInstance, field: "conjuntoOriginal")}</td>

            </tr>
        </g:each>
        
                <g:if test="${!eliminacionLoteConjuntoWolfranInstanceList}">
                    <tr><td colspan="5" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${eliminacionLoteConjuntoWolfranInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
