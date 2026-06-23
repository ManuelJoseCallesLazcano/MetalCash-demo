<%@ page import="org.socymet.liquidacion.AsignacionLoteConjuntoComplejo" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Asignacion Lote Conjunto Complejo</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Asignacion Lote Conjunto Complejo</h3>
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

            <g:sortableColumn property="lote" title="${message(code: 'asignacionLoteConjuntoComplejo.lote.label', default: 'Lote')}" />

            <g:sortableColumn property="nombreCliente" title="${message(code: 'asignacionLoteConjuntoComplejo.nombreCliente.label', default: 'Nombre Cliente')}" />

            <g:sortableColumn property="nombreEmpresa" title="${message(code: 'asignacionLoteConjuntoComplejo.nombreEmpresa.label', default: 'Nombre Empresa')}" />

            <g:sortableColumn property="fechaDeAsignacion" title="${message(code: 'asignacionLoteConjuntoComplejo.fechaDeAsignacion.label', default: 'Fecha De Asignacion')}" />

            <g:sortableColumn property="conjuntoDestino" title="${message(code: 'asignacionLoteConjuntoComplejo.conjuntoDestino.label', default: 'Conjunto Destino')}" />

        </tr>
                        </thead>
                <tbody>

        <g:each in="${asignacionLoteConjuntoComplejoInstanceList}" var="asignacionLoteConjuntoComplejoInstance">
            <tr>

                <td><g:link action="show" id="${asignacionLoteConjuntoComplejoInstance.id}">${fieldValue(bean: asignacionLoteConjuntoComplejoInstance, field: "lote")}</g:link></td>

                <td>${fieldValue(bean: asignacionLoteConjuntoComplejoInstance, field: "nombreCliente")}</td>

                <td>${fieldValue(bean: asignacionLoteConjuntoComplejoInstance, field: "nombreEmpresa")}</td>

                <td><g:formatDate date="${asignacionLoteConjuntoComplejoInstance.fechaDeAsignacion}" format="dd/MM/yyyy HH:mm:ss"/></td>

                <td>${fieldValue(bean: asignacionLoteConjuntoComplejoInstance, field: "conjuntoDestino")}</td>

            </tr>
        </g:each>
        
                <g:if test="${!asignacionLoteConjuntoComplejoInstanceList}">
                    <tr><td colspan="5" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${asignacionLoteConjuntoComplejoInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
