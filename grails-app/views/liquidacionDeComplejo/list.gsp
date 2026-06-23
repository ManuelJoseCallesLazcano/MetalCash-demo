<%@ page import="org.socymet.liquidacion.LiquidacionDeComplejo" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Liquidación de Complejo</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'chosen.css')}" type="text/css" >
    <g:javascript src="chosen.jquery.js" />
    <g:javascript src="liquidacionDeComplejo/liquidacionComplejoUtilidades.js" />
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
        <g:form action="index" method="GET" class="mb-3">
            <div class="form-row align-items-end">
                <div class="form-group col-md-3 mb-0">
                    <label for="modoBusqueda">Buscar por:</label>
                    <g:select name="modoBusqueda" from="${['-TODOS-','CLIENTE','EMPRESA']}" value="${params.modoBusqueda}" class="form-control chosen-select"/>
                </div>
                <div id="_clienteId" class="form-group col-md-4 mb-0">
                    <label for="clienteId">Cliente:</label>
                    <g:select name="clienteId" from="${org.socymet.proveedor.Cliente.list([sort: 'nombre'])}" optionKey="id" value="${params.clienteId}" class="form-control many-to-one chosen-select"/>
                </div>
                <div id="_empresaId" class="form-group col-md-4 mb-0">
                    <label for="empresaId">Empresa:</label>
                    <g:select name="empresaId" from="${org.socymet.proveedor.Empresa.list([sort: 'nombreDeEmpresa'])}" optionKey="id" value="${params.empresaId}" class="form-control many-to-one chosen-select"/>
                </div>
                <div class="form-group col-md-1 mb-0">
                    <g:submitButton name="buscar" value="Buscar" class="btn btn-primary"/>
                </div>
            </div>
        </g:form>
        <div class="table-responsive">
        <table class="table table-hover table-striped mb-0">
            <thead class="thead-light">
            <tr>
                <th>N° Liq.</th>
                <th>Lote</th>
                <g:sortableColumn property="nombreCliente" title="Cliente"/>
                <g:sortableColumn property="nombreEmpresa" title="Empresa"/>
                <g:sortableColumn property="fechaDeLiquidacion" title="Fecha Liq."/>
                <g:sortableColumn property="kilosNetosSecos" title="K.N.S."/>
                <g:sortableColumn property="valorOficialBruto" title="Val. Bruto"/>
                <g:sortableColumn property="totalLiquidoPagable" title="Líq. Pagable"/>
                <g:sortableColumn property="nombreComposito" title="Compósito"/>
                <th style="width:60px"></th>
            </tr>
            </thead>
            <tbody>
            <g:each in="${liquidacionDeComplejoInstanceList}" var="inst">
                <tr>
                    <td><g:link action="show" id="${inst.id}">${fieldValue(bean: inst, field: "numeroLiquidacionComplejo")}</g:link></td>
                    <td><g:link action="show" id="${inst.id}">${fieldValue(bean: inst, field: "recepcionDeComplejo")}</g:link></td>
                    <td>${fieldValue(bean: inst, field: "nombreCliente")}</td>
                    <td>${fieldValue(bean: inst, field: "nombreEmpresa")}</td>
                    <td><g:formatDate date="${inst.fechaDeLiquidacion}" format="dd/MM/yyyy"/></td>
                    <td>${fieldValue(bean: inst, field: "kilosNetosSecos")}</td>
                    <td>${fieldValue(bean: inst, field: "valorOficialBruto")}</td>
                    <td>${fieldValue(bean: inst, field: "totalLiquidoPagable")}</td>
                    <td>${fieldValue(bean: inst, field: "nombreComposito")}</td>
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
        <g:paginate total="${liquidacionDeComplejoInstanceCount ?: 0}" params="${params}"/>
    </div>
</div>
</body>
</html>
