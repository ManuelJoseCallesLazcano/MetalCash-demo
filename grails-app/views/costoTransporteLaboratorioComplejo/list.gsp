<%@ page import="org.socymet.liquidacion.CostoTransporteLaboratorioComplejo" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Costo Transporte Laboratorio Complejo</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Costo Transporte Laboratorio Complejo</h3>
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

            <g:sortableColumn property="lote" title="${message(code: 'costoTransporteLaboratorioComplejo.lote.label', default: 'Lote')}" />

            <g:sortableColumn property="nombreCliente" title="${message(code: 'costoTransporteLaboratorioComplejo.nombreCliente.label', default: 'Nombre Cliente')}" />

            <g:sortableColumn property="nombreEmpresa" title="${message(code: 'costoTransporteLaboratorioComplejo.nombreEmpresa.label', default: 'Nombre Empresa')}" />

            <g:sortableColumn property="pesoBruto" title="${message(code: 'costoTransporteLaboratorioComplejo.pesoBruto.label', default: 'Peso Bruto')}" />

            <g:sortableColumn property="costoDeTransporteNuevo" title="${message(code: 'costoTransporteLaboratorioComplejo.costoDeTransporteNuevo.label', default: 'Costo De Transporte Nuevo')}" />

            <g:sortableColumn property="totalCostoLaboratorioNuevo" title="${message(code: 'costoTransporteLaboratorioComplejo.totalCostoLaboratorioNuevo.label', default: 'Total Costo Laboratorio Nuevo')}" />

        </tr>
                        </thead>
                <tbody>

        <g:each in="${costoTransporteLaboratorioComplejoInstanceList}" var="costoTransporteLaboratorioComplejoInstance">
            <tr>

                <td><g:link action="show" id="${costoTransporteLaboratorioComplejoInstance.id}">${fieldValue(bean: costoTransporteLaboratorioComplejoInstance, field: "lote")}</g:link></td>

                <td>${fieldValue(bean: costoTransporteLaboratorioComplejoInstance, field: "nombreCliente")}</td>

                <td>${fieldValue(bean: costoTransporteLaboratorioComplejoInstance, field: "nombreEmpresa")}</td>

                <td>${fieldValue(bean: costoTransporteLaboratorioComplejoInstance, field: "pesoBruto")}</td>

                <td>${fieldValue(bean: costoTransporteLaboratorioComplejoInstance, field: "costoDeTransporteNuevo")}</td>

                <td>${fieldValue(bean: costoTransporteLaboratorioComplejoInstance, field: "totalCostoLaboratorioNuevo")}</td>

            </tr>
        </g:each>
        
                <g:if test="${!costoTransporteLaboratorioComplejoInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${costoTransporteLaboratorioComplejoInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
