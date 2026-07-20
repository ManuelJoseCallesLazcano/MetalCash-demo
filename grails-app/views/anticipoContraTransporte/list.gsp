<%@ page import="org.socymet.anticipos.AnticipoContraTransporte" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Anticipo Contra Transporte</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Anticipo Contra Transporte</h3>
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
            <g:sortableColumn property="id" title="${message(code: 'anticipoContraTransporte.id.label', default: 'Id')}" />

            <g:sortableColumn property="lote" title="${message(code: 'anticipoContraTransporte.lote.label', default: 'Lote')}" />

            <g:sortableColumn property="nombreCliente" title="${message(code: 'anticipoContraTransporte.nombreCliente.label', default: 'Nombre Cliente')}" />

            <g:sortableColumn property="nombreEmpresa" title="${message(code: 'anticipoContraTransporte.nombreEmpresa.label', default: 'Nombre Empresa')}" />

            <g:sortableColumn property="fechaDeRecepcion" title="${message(code: 'anticipoContraTransporte.fechaDeAnticipo.label', default: 'Fecha De Anticipo')}" />

            <g:sortableColumn property="pesoBruto" title="${message(code: 'anticipoContraTransporte.importe.label', default: 'Importe')}" />

        </tr>
                        </thead>
                <tbody>

        <g:each in="${anticipoContraTransporteInstanceList}" var="anticipoContraTransporteInstance">
            <tr>

                <td><g:link action="show" id="${anticipoContraTransporteInstance.id}">${fieldValue(bean: anticipoContraTransporteInstance, field: "id")}</g:link></td>

                <td><g:link action="show" id="${anticipoContraTransporteInstance.id}">${fieldValue(bean: anticipoContraTransporteInstance, field: "lote")}</g:link></td>

                <td>${fieldValue(bean: anticipoContraTransporteInstance, field: "nombreCliente")}</td>

                <td>${fieldValue(bean: anticipoContraTransporteInstance, field: "nombreEmpresa")}</td>

                <td><g:formatDate date="${anticipoContraTransporteInstance.fechaDeAnticipo}" format="dd/MM/yyyy"/> </td>

                <td>${fieldValue(bean: anticipoContraTransporteInstance, field: "importe")}</td>

            </tr>
        </g:each>
        
                <g:if test="${!anticipoContraTransporteInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${anticipoContraTransporteInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
