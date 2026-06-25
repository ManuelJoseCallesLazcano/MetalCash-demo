<%@ page import="org.socymet.anticipos.AnticipoContraFuturaEntrega" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Anticipo Contra Futura Entrega</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Anticipo Contra Futura Entrega</h3>
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
                    Swal.fire({ icon: '${flash.swalIcon ?: 'info'}', title: '${flash.swalTitle ?: 'Información'}',
                        text: document.getElementById('swalFlashMsg').textContent.trim(),
                        confirmButtonText: 'Aceptar' });
                });
            </script>
        </g:if>
        <div class="px-3 pt-3 pb-2">
            <g:form action="list" method="GET">
                <div class="input-group" style="max-width:460px">
                    <div class="input-group-prepend">
                        <span class="input-group-text border-right-0 bg-white">
                            <i class="fas fa-search text-muted fa-sm"></i>
                        </span>
                    </div>
                    <input type="text" name="q"
                           class="form-control form-control-sm border-left-0"
                           placeholder="Buscar comprobante, cliente o empresa…"
                           value="${q ?: ''}"
                           autocomplete="off"/>
                    <div class="input-group-append">
                        <button type="submit" class="btn btn-secondary btn-sm">Buscar</button>
                        <g:if test="${q}">
                            <g:link action="list" class="btn btn-outline-secondary btn-sm" title="Limpiar búsqueda">
                                <i class="fas fa-times"></i>
                            </g:link>
                        </g:if>
                    </div>
                </div>
            </g:form>
        </div>
        <div class="table-responsive">
            <table class="table table-hover table-striped mb-0">
                <thead class="thead-light">
                    <tr>
                        <g:sortableColumn property="numeroAnticipo" title="N°" params="${[q: q]}"/>
                        <th>Cliente</th>
                        <th>Empresa</th>
                        <g:sortableColumn property="fechaDeAnticipo" title="Fecha" params="${[q: q]}"/>
                        <g:sortableColumn property="importe" title="Monto [Bs]" params="${[q: q]}"/>
%{--                        <th class="text-right">Último Saldo Cliente [Bs]</th>--}%
                        <th style="width:60px"></th>
                    </tr>
                </thead>
                <tbody>
                <g:if test="${!anticipoContraFuturaEntregaInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">
                        <g:if test="${q}">No se encontraron anticipos para "${q}".</g:if>
                        <g:else>No hay registros.</g:else>
                    </td></tr>
                </g:if>
                <g:each in="${anticipoContraFuturaEntregaInstanceList}" var="inst">
                    <tr>
                        <td>
                            <g:link action="show" id="${inst.id}">${inst}</g:link>
                            <g:if test="${inst.anulado}"><span class="badge badge-danger ml-1">ANULADO</span></g:if>
                        </td>
                        <td>${inst.cliente?.nombre}</td>
                        <td>${inst.empresa?.nombreDeEmpresa}</td>
                        <td><g:formatDate date="${inst.fechaDeAnticipo}" format="dd/MM/yyyy"/></td>
                        <td class="text-right"><g:formatNumber number="${inst.importe ?: 0}" type="number" maxFractionDigits="2"/></td>
%{--                        <g:set var="saldo" value="${org.socymet.anticipos.EstadoDeCuenta.findByCliente(inst.cliente, [sort: 'id', order: 'desc'])?.saldo ?: 0}"/>--}%
%{--                        <td class="text-right font-weight-bold ${saldo > 0 ? 'text-danger' : 'text-success'}">--}%
%{--                            <g:formatNumber number="${saldo}" type="number" maxFractionDigits="2"/>--}%
%{--                        </td>--}%
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
        <g:paginate total="${anticipoContraFuturaEntregaInstanceTotal ?: 0}" params="${[q: q]}"/>
    </div>
</div>
</body>
</html>
