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
        <div class="table-responsive">
            <table class="table table-hover table-striped mb-0">
                <thead class="thead-light">
                    <tr>
                        <g:sortableColumn property="numeroAnticipo" title="N°"/>
                        <th>Cliente</th>
                        <g:sortableColumn property="fechaDeAnticipo" title="Fecha"/>
                        <g:sortableColumn property="importe" title="Monto [Bs]"/>
%{--                        <th class="text-right">Último Saldo Cliente [Bs]</th>--}%
                        <th style="width:60px"></th>
                    </tr>
                </thead>
                <tbody>
                <g:if test="${!anticipoContraFuturaEntregaInstanceList}">
                    <tr><td colspan="6" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                <g:each in="${anticipoContraFuturaEntregaInstanceList}" var="inst">
                    <tr>
                        <td>
                            <g:link action="show" id="${inst.id}">${inst}</g:link>
                            <g:if test="${inst.anulado}"><span class="badge badge-danger ml-1">ANULADO</span></g:if>
                        </td>
                        <td>${inst.cliente?.nombre}</td>
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
        <g:paginate total="${anticipoContraFuturaEntregaInstanceTotal ?: 0}"/>
    </div>
</div>
</body>
</html>
