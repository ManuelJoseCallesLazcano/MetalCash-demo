<%@ page import="org.socymet.proveedor.Empresa" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>${empresaInstance?.toString()}</title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css">
    <style>
        .show-section-title {
            font-size: 0.78rem;
            font-weight: 700;
            text-transform: uppercase;
            letter-spacing: 0.07em;
            color: #2c3e50;
            border-left: 4px solid #17a2b8;
            background: linear-gradient(to right, #e5f6f8, transparent);
            padding: 0.45rem 0.85rem;
            margin: 1.5rem 0 1rem;
            border-radius: 0 3px 3px 0;
        }
        .show-label {
            font-weight: 600;
            color: #6c757d;
            font-size: 0.82rem;
            text-transform: uppercase;
            letter-spacing: 0.04em;
        }
        .show-value {
            font-size: 0.95rem;
            color: #343a40;
        }
    </style>
%{--    <g:javascript src="jquery.jqGrid.min.js"/>--}%
%{--    <g:javascript src="i18n/grid.locale-es.js"/>--}%
%{--    <g:javascript src="conversor_json_tabla.js"/>--}%
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">${empresaInstance?.toString()}</h3>
        <div class="ml-auto">
            <g:link action="list" class="btn btn-secondary btn-sm">
                <i class="fas fa-list"></i> Lista
            </g:link>
            <sec:ifAnyGranted roles="ROLE_ADMIN">
                <g:link action="edit" id="${empresaInstance?.id}" class="btn btn-warning btn-sm ml-1">
                    <i class="fas fa-edit"></i> Editar
                </g:link>
            </sec:ifAnyGranted>
        </div>
    </div>
    <div class="card-body">
        <g:if test="${flash.message}">
            <div id="swalFlashMsg" style="display:none">${flash.message}</div>
            <script>
                $(document).ready(function () {
                    Swal.fire({
                        icon: 'info',
                        title: 'Información',
                        text: document.getElementById('swalFlashMsg').textContent.trim(),
                        confirmButtonText: 'Aceptar'
                    });
                });
            </script>
        </g:if>

        <%-- Identificación --%>
        <h5 class="show-section-title">Identificación</h5>
        <div class="row mb-2">
            <div class="col-sm-3">
                <div class="show-label">Tipo de Empresa</div>
                <div class="show-value">
                    <span class="badge badge-secondary">${fieldValue(bean: empresaInstance, field: 'tipoDeEmpresa')}</span>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="show-label">Nombre</div>
                <div class="show-value font-weight-bold">${fieldValue(bean: empresaInstance, field: 'nombreDeEmpresa')}</div>
            </div>
            <div class="col-sm-2">
                <div class="show-label">Código</div>
                <div class="show-value">${fieldValue(bean: empresaInstance, field: 'codigoEmpresa')}</div>
            </div>
            <div class="col-sm-2">
                <div class="show-label">NIM</div>
                <div class="show-value">${fieldValue(bean: empresaInstance, field: 'nim') ?: '—'}</div>
            </div>
            <div class="col-sm-2">
                <div class="show-label">NIT</div>
                <div class="show-value">${fieldValue(bean: empresaInstance, field: 'nit') ?: '—'}</div>
            </div>
        </div>

        <%-- Ubicación --%>
        <h5 class="show-section-title">Ubicación</h5>
        <div class="row mb-2">
            <div class="col-sm-3">
                <div class="show-label">Departamento</div>
                <div class="show-value">${fieldValue(bean: empresaInstance, field: 'departamento')}</div>
            </div>
            <div class="col-sm-3">
                <div class="show-label">Provincia</div>
                <div class="show-value">${fieldValue(bean: empresaInstance, field: 'provincia')}</div>
            </div>
            <div class="col-sm-4">
                <div class="show-label">Municipio</div>
                <div class="show-value">${fieldValue(bean: empresaInstance, field: 'municipio')}</div>
            </div>
        </div>

        <%-- Costos de Transporte --%>
        <h5 class="show-section-title">Costos de Transporte</h5>
        <div class="table-responsive mb-3" style="max-width: 700px;">
            <table class="table table-sm table-bordered">
                <thead class="thead-light">
                    <tr>
                        <th>Descripción</th>
                        <th>Costo</th>
                        <th>Unidad Monetaria</th>
                        <th>Unidad de Cobro</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Broza</td>
                        <td>${fieldValue(bean: empresaInstance, field: 'costoTransporteComplejos') ?: '—'}</td>
                        <td>${fieldValue(bean: empresaInstance, field: 'unidadMonetariaComplejos') ?: '—'}</td>
                        <td>${fieldValue(bean: empresaInstance, field: 'unidadDeCobroComplejos') ?: '—'}</td>
                    </tr>
                    <tr>
                        <td>Concentrados</td>
                        <td>${fieldValue(bean: empresaInstance, field: 'costoTransporteConcentrados') ?: '—'}</td>
                        <td>${fieldValue(bean: empresaInstance, field: 'unidadMonetariaConcentrados') ?: '—'}</td>
                        <td>${fieldValue(bean: empresaInstance, field: 'unidadDeCobroConcentrados') ?: '—'}</td>
                    </tr>
                </tbody>
            </table>
        </div>

%{--        <g:if test="${empresaInstance?.costoTratamiento}">--}%
%{--            <div class="row mb-3">--}%
%{--                <div class="col-sm-3">--}%
%{--                    <div class="show-label">Costo de Tratamiento</div>--}%
%{--                    <div class="show-value">${fieldValue(bean: empresaInstance, field: 'costoTratamiento')}</div>--}%
%{--                </div>--}%
%{--            </div>--}%
%{--        </g:if>--}%

        <%-- Retenciones --%>
        <g:set var="listaRetenciones" value="${org.socymet.proveedor.EmpresaRetenciones.findAllByEmpresa(empresaInstance)}"/>
        <g:if test="${listaRetenciones}">
            <h5 class="show-section-title">Retenciones</h5>
            <div class="table-responsive" style="max-width: 750px;">
                <table class="table table-sm table-bordered table-striped mb-0">
                    <thead class="thead-light">
                        <tr>
                            <th>Descripción</th>
                            <th style="width:90px">Tipo</th>
                            <th class="text-right" style="width:90px">Cantidad</th>
                            <th style="width:80px">Unidad</th>
                            <th style="width:110px">Asignación</th>
                        </tr>
                    </thead>
                    <tbody>
                        <g:each in="${listaRetenciones}" var="ret">
                            <tr>
                                <td>${ret.descripcion}</td>
                                <td>
                                    <span class="badge badge-${ret.tipoDeRetencion == 'DE LEY' ? 'danger' : 'secondary'}">${ret.tipoDeRetencion}</span>
                                </td>
                                <td class="text-right">${ret.cantidadDescuento}</td>
                                <td>${ret.unidadDeDescuento}</td>
                                <td>${ret.asignacionDelDescuento}</td>
                            </tr>
                        </g:each>
                    </tbody>
                </table>
            </div>
        </g:if>
    </div>

    <div class="card-footer">
        <g:link action="list" class="btn btn-secondary btn-sm">
            <i class="fas fa-arrow-left"></i> Volver
        </g:link>
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <g:link action="edit" id="${empresaInstance?.id}" class="btn btn-warning btn-sm ml-1">
                <i class="fas fa-edit"></i> Editar
            </g:link>
            <g:form action="delete" method="post" style="display:inline">
                <g:hiddenField name="id" value="${empresaInstance?.id}"/>
                <button type="submit" class="btn btn-danger btn-sm ml-1"
                    onclick="return confirm('¿Eliminar esta empresa?')">
                    <i class="fas fa-trash"></i> Eliminar
                </button>
            </g:form>
        </sec:ifAnyGranted>
    </div>
</div>
</body>
</html>
