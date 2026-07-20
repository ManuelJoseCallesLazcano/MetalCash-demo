<%@ page import="org.socymet.anticipos.Amortizacion" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Amortización</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
    <style>
        .form-section-title {
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
    </style>
</head>
<body>
<div class="card card-outline card-info">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title mr-auto">Amortización
            <g:if test="${amortizacionInstance?.numeroAmortizacion}">
                <span class="badge badge-info ml-1">N° ${amortizacionInstance}</span>
            </g:if>
            <g:if test="${amortizacionInstance?.anulado}">
                <span class="badge badge-danger ml-1">ANULADA</span>
            </g:if>
        </h3>
        <%-- Impresión oficial: genera amortizacion.jasper (PDF) directo vía jasperService. El id de
             la URL lleva el N° de amortización (título de la pestaña); el id real va en 'lid'. --%>
        <g:set var="nombreArchivo" value="${('Amortizacion-' + amortizacionInstance).replaceAll(/[^0-9A-Za-z._-]/, '-')}"/>
        <g:link controller="amortizacion" action="imprimirPdf" id="${nombreArchivo}" params="[lid: amortizacionInstance?.id]" target="_blank"
                class="btn btn-success btn-sm mr-1 font-weight-bold">
            <i class="fas fa-print mr-1"></i>Imprimir Amortización
        </g:link>
        <g:link action="list" class="btn btn-secondary btn-sm mr-1">
            <i class="fas fa-list mr-1"></i>Lista
        </g:link>
        <g:link action="create" class="btn btn-primary btn-sm">
            <i class="fas fa-plus mr-1"></i>Nueva
        </g:link>
    </div>
    <div class="card-body">
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

        <h5 class="form-section-title">Cliente</h5>
        <dl class="row mb-0">
            <g:if test="${amortizacionInstance?.cliente}">
                <dt class="col-sm-3">Cliente</dt>
                <dd class="col-sm-9">
                    <g:link controller="cliente" action="show" id="${amortizacionInstance?.cliente?.id}">${amortizacionInstance?.cliente?.encodeAsHTML()}</g:link>
                </dd>
            </g:if>
            <g:if test="${amortizacionInstance?.empresa}">
                <dt class="col-sm-3">Empresa</dt>
                <dd class="col-sm-9">
                    <g:link controller="empresa" action="show" id="${amortizacionInstance?.empresa?.id}">${amortizacionInstance?.empresa?.encodeAsHTML()}</g:link>
                </dd>
            </g:if>
        </dl>

        <h5 class="form-section-title">Datos de la Amortización</h5>
        <dl class="row mb-0">
            <dt class="col-sm-3">Fecha</dt>
            <dd class="col-sm-9"><g:formatDate date="${amortizacionInstance?.fecha}" format="dd/MM/yyyy"/></dd>

            <dt class="col-sm-3">Concepto</dt>
            <dd class="col-sm-9"><g:fieldValue bean="${amortizacionInstance}" field="concepto"/></dd>

            <dt class="col-sm-3">Importe [Bs]</dt>
            <dd class="col-sm-9"><g:formatNumber number="${amortizacionInstance?.importe ?: 0}" type="number" maxFractionDigits="2"/></dd>

            <g:if test="${amortizacionInstance?.importeLiteral}">
                <dt class="col-sm-3">Literal</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${amortizacionInstance}" field="importeLiteral"/></dd>
            </g:if>

            <dt class="col-sm-3">Saldo por pagar [Bs]</dt>
            <dd class="col-sm-9"><g:formatNumber number="${amortizacionInstance?.saldoPorPagar ?: 0}" type="number" maxFractionDigits="2"/></dd>

            <g:if test="${amortizacionInstance?.usuario}">
                <dt class="col-sm-3">Usuario</dt>
                <dd class="col-sm-9">${amortizacionInstance?.usuario?.username}</dd>
            </g:if>
        </dl>
    </div>

    <div class="card-footer d-flex align-items-center">
        <g:if test="${!amortizacionInstance?.anulado}">
            <g:form action="anular" class="d-inline">
                <g:hiddenField name="id" value="${amortizacionInstance?.id}"/>
                <button type="button" class="btn btn-danger btn-sm btn-anular">
                    <i class="fas fa-ban mr-1"></i>Anular
                </button>
            </g:form>
        </g:if>
        <g:else>
            <span class="text-muted"><i class="fas fa-ban mr-1"></i>Amortización anulada</span>
        </g:else>
        <div class="ml-auto">
%{--            <g:jasperReport controller="amortizacion" action="crearReporte" jasper="amortizacion"--}%
%{--                            format="PDF" _format="PDF" name="AMORTIZACION_${amortizacionInstance.numeroAmortizacion}">--}%
%{--                <input type="hidden" name="ACE_ID" value="${amortizacionInstance.id}"/>--}%
%{--            </g:jasperReport>--}%
        </div>
    </div>
</div>

<script>
    $(function () {
        $('.btn-anular').on('click', function () {
            var form = this.form;
            Swal.fire({
                title: '¿Anular esta amortización?',
                html: 'Se revertirá su efecto en el estado de cuenta del cliente.<br>El registro no se elimina, queda marcado como ANULADA.',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                cancelButtonColor: '#6c757d',
                confirmButtonText: 'Sí, anular',
                cancelButtonText: 'Cancelar',
                reverseButtons: true
            }).then(function (result) {
                if (result.isConfirmed) form.submit();
            });
        });
    });
</script>
</body>
</html>
