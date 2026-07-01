<%@ page import="org.socymet.liquidacion.LiquidacionDeComplejo" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Liquidación de Complejo</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
                <h3 class="card-title">Liquidación de Complejo</h3>
        <div class="ml-auto">
            <g:link action="create" class="btn btn-primary btn-sm">
            <i class="fas fa-plus mr-1"></i>Nueva
        </g:link>
        </div>
    </div>
    <div class="card-body">
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
        <div class="mb-3">
            <g:form action="list" method="GET">
                <div class="input-group" style="max-width:520px">
                    <div class="input-group-prepend">
                        <span class="input-group-text border-right-0 bg-white"><i class="fas fa-search text-muted fa-sm"></i></span>
                    </div>
                    <input type="text" name="q"
                           class="form-control form-control-sm border-left-0"
                           placeholder="Buscar por N° liquidación, lote, cliente o empresa…"
                           value="${q ?: ''}"
                           autocomplete="off"/>
                    <div class="input-group-append">
                        <button type="submit" class="btn btn-secondary btn-sm">Buscar</button>
                        <g:if test="${q}">
                            <g:link action="list" class="btn btn-outline-secondary btn-sm" title="Limpiar búsqueda"><i class="fas fa-times"></i></g:link>
                        </g:if>
                    </div>
                </div>
            </g:form>
        </div>
        <div class="table-responsive">
        <table class="table table-hover table-striped mb-0">
            <thead class="thead-light">
            <tr>
                <th>N° Liq.</th>
                <th>Lote</th>
                <g:sortableColumn property="nombreCliente" title="Cliente"/>
                <g:sortableColumn property="nombreEmpresa" title="Empresa"/>
                <g:sortableColumn property="fechaDeLiquidacion" title="Fecha Liq."/>
%{--                <g:sortableColumn property="kilosNetosSecos" title="K.N.S."/>--}%
%{--                <g:sortableColumn property="valorOficialBruto" title="Val. Bruto"/>--}%
                <g:sortableColumn property="totalLiquidoPagable" title="Líq. Pagable"/>
%{--                <g:sortableColumn property="nombreComposito" title="Compósito"/>--}%
                <th style="width:60px"></th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${liquidacionDeComplejoInstanceList}" var="inst">
                <tr>
                    <td>
                        <g:link action="show" id="${inst.id}">${inst.numeroLiquidacionComplejo}/<g:formatDate date="${inst.gestionMinera}" format="yy"/></g:link>
                        <g:if test="${inst.anulado}"><span class="badge badge-danger ml-1">ANULADA</span></g:if>
                    </td>
                    <td><g:link action="show" id="${inst.id}">${fieldValue(bean: inst, field: "recepcionDeComplejo")}</g:link></td>
                    <td>${fieldValue(bean: inst, field: "nombreCliente")}</td>
                    <td>${fieldValue(bean: inst, field: "nombreEmpresa")}</td>
                    <td><g:formatDate date="${inst.fechaDeLiquidacion}" format="dd/MM/yyyy"/></td>
%{--                    <td>${fieldValue(bean: inst, field: "kilosNetosSecos")}</td>--}%
%{--                    <td>${fieldValue(bean: inst, field: "valorOficialBruto")}</td>--}%
                    <td>${fieldValue(bean: inst, field: "totalLiquidoPagable")}</td>
%{--                    <td>${fieldValue(bean: inst, field: "nombreComposito")}</td>--}%
                    <td class="text-nowrap">
                        <g:link action="show" id="${inst.id}" class="btn btn-info btn-xs"><i class="fas fa-eye"></i></g:link>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${liquidacionDeComplejoInstanceCount ?: 0}" params="${[q: q]}"/>
    </div>
</div>
</body>
</html>
