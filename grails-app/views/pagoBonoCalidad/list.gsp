<%@ page import="org.socymet.cancelacion.PagoBonoCalidad" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Pago Bono Calidad</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Pago Bono Calidad</h3>
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

            <g:sortableColumn property="numeroComprobante" title="${message(code: 'pagoBonoCalidad.numeroComprobante.label', default: 'No.')}" />

            <g:sortableColumn property="ci" title="${message(code: 'pagoBonoCalidad.ci.label', default: 'Ci')}" />

            <g:sortableColumn property="nombreCobrador" title="${message(code: 'pagoBonoCalidad.nombreCobrador.label', default: 'Nombre Cobrador')}" />

            <g:sortableColumn property="empresa" title="${message(code: 'pagoBonoCalidad.empresa.label', default: 'Empresa')}" />

            <th><g:message code="pagoBonoCalidad.tipoSeleccion.label" default="Pagado A" /></th>

            <g:sortableColumn property="fechaDePago" title="${message(code: 'pagoBonoCalidad.fechaDePago.label', default: 'Fecha De Pago')}" />

            <g:sortableColumn property="totalKilosNetosSecos" title="${message(code: 'pagoBonoCalidad.totalKilosNetosSecos.label', default: 'Total KNS')}" />

            <g:sortableColumn property="totalPagable" title="${message(code: 'pagoBonoCalidad.totalPagable.label', default: 'Total Pagable')}" />

        </tr>
                        </thead>
                <tbody>

        <g:each in="${pagoBonoCalidadInstanceList}" var="pagoBonoCalidadInstance">
            <tr>

                <td><g:link action="show" id="${pagoBonoCalidadInstance.id}"><g:formatNumber number="${pagoBonoCalidadInstance.numeroComprobante}" format="000000"/></g:link></td>

                <td>${fieldValue(bean: pagoBonoCalidadInstance, field: "ci")}</td>

                <td>${fieldValue(bean: pagoBonoCalidadInstance, field: "nombreCobrador")}</td>

                <td>${fieldValue(bean: pagoBonoCalidadInstance, field: "empresa")}</td>

                <td>${(pagoBonoCalidadInstance.tipoSeleccion.equals("INDIVIDUAL"))?pagoBonoCalidadInstance.cliente:pagoBonoCalidadInstance.cuadrilla}</td>

                <td><g:formatDate date="${pagoBonoCalidadInstance.fechaDePago}" /></td>

                <td>${fieldValue(bean: pagoBonoCalidadInstance, field: "totalKilosNetosSecos")}</td>

                <td>${fieldValue(bean: pagoBonoCalidadInstance, field: "totalPagable")}</td>

            </tr>
        </g:each>
        
                <g:if test="${!pagoBonoCalidadInstanceList}">
                    <tr><td colspan="8" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${pagoBonoCalidadInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
