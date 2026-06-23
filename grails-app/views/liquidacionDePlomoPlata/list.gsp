<%@ page import="org.socymet.liquidacion.LiquidacionDePlomoPlata" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Liquidacion De Plomo Plata</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Liquidacion De Plomo Plata</h3>
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
            <th><g:message code="liquidacionDePlomoPlata.numeroLiquidacionComplejo.label" default="No. Liq." /></th>

            <th><g:message code="liquidacionDePlomoPlata.recepcionDeComplejo.label" default="Lote" /></th>

            <g:sortableColumn property="nombreCliente" title="${message(code: 'liquidacionDePlomoPlata.nombreCliente.label', default: 'Nombre Cliente')}" />

            <g:sortableColumn property="nombreEmpresa" title="${message(code: 'liquidacionDePlomoPlata.nombreEmpresa.label', default: 'Nombre Empresa')}" />

            <g:sortableColumn property="fechaDeLiquidacion" title="${message(code: 'liquidacionDePlomoPlata.fechaDeLiquidacion.label', default: 'Fecha De Liquidacion')}" />

            <g:sortableColumn property="kilosNetosSecos" title="${message(code: 'liquidacionDePlomoPlata.kilosNetosSecos.label', default: 'K. N. S.')}" />

            <g:sortableColumn property="valorOficialBruto" title="${message(code: 'liquidacionDePlomoPlata.valorOficialBruto.label', default: 'Valor Bruto')}" />

            <g:sortableColumn property="totalLiquidoPagable" title="${message(code: 'liquidacionDePlomoPlata.totalLiquidoPagable.label', default: 'Liquido Pagable')}" />

        </tr>
                        </thead>
                <tbody>

        <g:each in="${liquidacionDePlomoPlataInstanceList}" var="liquidacionDePlomoPlataInstance">
            <tr>
                <td><g:link action="show" id="${liquidacionDePlomoPlataInstance.id}">${fieldValue(bean: liquidacionDePlomoPlataInstance, field: "numeroLiquidacionPlomoPlata")}</g:link></td>

                <td><g:link action="show" id="${liquidacionDePlomoPlataInstance.id}">${fieldValue(bean: liquidacionDePlomoPlataInstance, field: "recepcionDeComplejo")}</g:link></td>

                <td>${fieldValue(bean: liquidacionDePlomoPlataInstance, field: "nombreCliente")}</td>

                <td>${fieldValue(bean: liquidacionDePlomoPlataInstance, field: "nombreEmpresa")}</td>

                <td><g:formatDate date="${liquidacionDePlomoPlataInstance.fechaDeLiquidacion}" format="dd/MM/yyyy" /></td>

                <td>${fieldValue(bean: liquidacionDePlomoPlataInstance, field: "kilosNetosSecos")}</td>

                <td>${fieldValue(bean: liquidacionDePlomoPlataInstance, field: "valorOficialBruto")}</td>

                <td>${fieldValue(bean: liquidacionDePlomoPlataInstance, field: "totalLiquidoPagable")}</td>

            </tr>
        </g:each>
        
                <g:if test="${!liquidacionDePlomoPlataInstanceList}">
                    <tr><td colspan="8" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${liquidacionDePlomoPlataInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
