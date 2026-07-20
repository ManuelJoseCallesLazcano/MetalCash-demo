<%@ page import="org.socymet.liquidacion.LiquidacionDeWolfran" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Liquidacion De Wolfran</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Liquidacion De Wolfran</h3>
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
            <th><g:message code="liquidacionDeWolfran.numeroLiquidacionWolfran.label" default="No. Liq." /></th>

            <th><g:message code="liquidacionDeWolfran.recepcionDeWolfran.label" default="Lote" /></th>

            <g:sortableColumn property="nombreCliente" title="${message(code: 'liquidacionDeWolfran.nombreCliente.label', default: 'Nombre Cliente')}" />

            <g:sortableColumn property="nombreEmpresa" title="${message(code: 'liquidacionDeWolfran.nombreEmpresa.label', default: 'Nombre Empresa')}" />

            <g:sortableColumn property="fechaDeLiquidacion" title="${message(code: 'liquidacionDeWolfran.fechaDeLiquidacion.label', default: 'Fecha De Liquidacion')}" />

            <g:sortableColumn property="kilosNetosSecos" title="${message(code: 'liquidacionDeWolfran.kilosNetosSecos.label', default: 'K. N. S.')}" />

            <g:sortableColumn property="valorOficialBruto" title="${message(code: 'liquidacionDeWolfran.valorOficialBruto.label', default: 'Valor Bruto')}" />

            <g:sortableColumn property="totalLiquidoPagable" title="${message(code: 'liquidacionDeWolfran.totalLiquidoPagable.label', default: 'Liquido Pagable')}" />

        </tr>
                        </thead>
                <tbody>

        <g:each in="${liquidacionDeWolfranInstanceList}" var="liquidacionDeWolfranInstance">
            <tr>
                <td><g:link action="show" id="${liquidacionDeWolfranInstance.id}">${fieldValue(bean: liquidacionDeWolfranInstance, field: "numeroLiquidacionWolfran")}</g:link></td>

                <td><g:link action="show" id="${liquidacionDeWolfranInstance.id}">${fieldValue(bean: liquidacionDeWolfranInstance, field: "recepcionDeWolfran")}</g:link></td>

                <td>${fieldValue(bean: liquidacionDeWolfranInstance, field: "nombreCliente")}</td>

                <td>${fieldValue(bean: liquidacionDeWolfranInstance, field: "nombreEmpresa")}</td>

                <td><g:formatDate date="${liquidacionDeWolfranInstance.fechaDeLiquidacion}" format="dd/MM/yyyy" /></td>

                <td>${fieldValue(bean: liquidacionDeWolfranInstance, field: "kilosNetosSecos")}</td>

                <td>${fieldValue(bean: liquidacionDeWolfranInstance, field: "valorOficialBruto")}</td>

                <td>${fieldValue(bean: liquidacionDeWolfranInstance, field: "totalLiquidoPagable")}</td>

            </tr>
        </g:each>
        
                <g:if test="${!liquidacionDeWolfranInstanceList}">
                    <tr><td colspan="8" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${liquidacionDeWolfranInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
