<%@ page import="org.socymet.liquidacion.LiquidacionDePlata" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Liquidacion De Plata</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Liquidacion De Plata</h3>
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
            <th><g:message code="liquidacionDePlata.numeroLiquidacionPlata.label" default="No. Liq." /></th>

            <th><g:message code="liquidacionDePlata.recepcionDePlata.label" default="Lote" /></th>

            <g:sortableColumn property="nombreCliente" title="${message(code: 'liquidacionDePlata.nombreCliente.label', default: 'Nombre Cliente')}" />

            <g:sortableColumn property="nombreEmpresa" title="${message(code: 'liquidacionDePlata.nombreEmpresa.label', default: 'Nombre Empresa')}" />

            <g:sortableColumn property="fechaDeLiquidacion" title="${message(code: 'liquidacionDePlata.fechaDeLiquidacion.label', default: 'Fecha De Liquidacion')}" />

            <g:sortableColumn property="kilosNetosSecos" title="${message(code: 'liquidacionDePlata.kilosNetosSecos.label', default: 'K. N. S.')}" />

            <g:sortableColumn property="valorOficialBruto" title="${message(code: 'liquidacionDePlata.valorOficialBruto.label', default: 'Valor Bruto')}" />

            <g:sortableColumn property="totalLiquidoPagable" title="${message(code: 'liquidacionDePlata.totalLiquidoPagable.label', default: 'Liquido Pagable')}" />

        </tr>
                        </thead>
                <tbody>

        <g:each in="${liquidacionDePlataInstanceList}" var="liquidacionDePlataInstance">
            <tr>
                <td><g:link action="show" id="${liquidacionDePlataInstance.id}">${fieldValue(bean: liquidacionDePlataInstance, field: "numeroLiquidacionPlata")}</g:link></td>

                <td><g:link action="show" id="${liquidacionDePlataInstance.id}">${fieldValue(bean: liquidacionDePlataInstance, field: "recepcionDePlata")}</g:link></td>

                <td>${fieldValue(bean: liquidacionDePlataInstance, field: "nombreCliente")}</td>

                <td>${fieldValue(bean: liquidacionDePlataInstance, field: "nombreEmpresa")}</td>

                <td><g:formatDate date="${liquidacionDePlataInstance.fechaDeLiquidacion}" format="dd/MM/yyyy" /></td>

                <td>${fieldValue(bean: liquidacionDePlataInstance, field: "kilosNetosSecos")}</td>

                <td>${fieldValue(bean: liquidacionDePlataInstance, field: "valorOficialBruto")}</td>

                <td>${fieldValue(bean: liquidacionDePlataInstance, field: "totalLiquidoPagable")}</td>

            </tr>
        </g:each>
        
                <g:if test="${!liquidacionDePlataInstanceList}">
                    <tr><td colspan="8" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${liquidacionDePlataInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
