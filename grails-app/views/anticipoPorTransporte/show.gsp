<%@ page import="org.socymet.anticipos.AnticipoPorTransporte" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Anticipo por Transporte</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
    <style>
        .form-section-title {
            font-size: 0.78rem; font-weight: 700; text-transform: uppercase; letter-spacing: 0.07em;
            color: #2c3e50; border-left: 4px solid #17a2b8; background: linear-gradient(to right, #e5f6f8, transparent);
            padding: 0.45rem 0.85rem; margin: 1.5rem 0 1rem; border-radius: 0 3px 3px 0;
        }
    </style>
</head>
<body>
<div class="card card-outline card-info">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title mr-auto">Anticipo por Transporte
            <span class="badge badge-info ml-1">N° ${anticipoPorTransporteInstance}</span>
            <g:if test="${anticipoPorTransporteInstance?.anulado}">
                <span class="badge badge-danger ml-1">ANULADO</span>
            </g:if>
        </h3>
        <%-- Impresión oficial: genera orden_anticipo_contra_transporte.jasper (PDF) directo vía
             jasperService. El id de la URL lleva el N° del anticipo (título de la pestaña); el id
             real va en el query param 'lid'. --%>
        <g:set var="nombreArchivo" value="${('AnticipoTransporte-' + anticipoPorTransporteInstance).replaceAll(/[^0-9A-Za-z._-]/, '-')}"/>
        <g:link controller="anticipoPorTransporte" action="imprimirPdf" id="${nombreArchivo}" params="[lid: anticipoPorTransporteInstance?.id]" target="_blank"
                class="btn btn-success btn-sm mr-1 font-weight-bold"><i class="fas fa-print mr-1"></i>Imprimir Anticipo</g:link>
        <g:link action="list" class="btn btn-secondary btn-sm mr-1"><i class="fas fa-list mr-1"></i>Lista</g:link>
        <g:link action="create" class="btn btn-primary btn-sm"><i class="fas fa-plus mr-1"></i>Nuevo</g:link>
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

        <h5 class="form-section-title">Cobrador</h5>
        <dl class="row mb-0">
            <dt class="col-sm-3">CI</dt>
            <dd class="col-sm-9"><g:fieldValue bean="${anticipoPorTransporteInstance}" field="ci"/></dd>
            <dt class="col-sm-3">Cobrador</dt>
            <dd class="col-sm-9"><g:fieldValue bean="${anticipoPorTransporteInstance}" field="nombreCobrador"/></dd>
        </dl>

        <h5 class="form-section-title">Automóvil</h5>
        <dl class="row mb-0">
            <g:if test="${anticipoPorTransporteInstance?.automovil}">
                <dt class="col-sm-3">Automóvil</dt>
                <dd class="col-sm-9">
                    <g:link controller="automovil" action="show" id="${anticipoPorTransporteInstance?.automovil?.id}">${anticipoPorTransporteInstance?.automovil?.encodeAsHTML()}</g:link>
                </dd>
            </g:if>
            <g:if test="${anticipoPorTransporteInstance?.empresa}">
                <dt class="col-sm-3">Empresa</dt>
                <dd class="col-sm-9">
                    <g:link controller="empresa" action="show" id="${anticipoPorTransporteInstance?.empresa?.id}">${anticipoPorTransporteInstance?.empresa?.encodeAsHTML()}</g:link>
                </dd>
            </g:if>
        </dl>

        <h5 class="form-section-title">Datos del Anticipo</h5>
        <dl class="row mb-0">
            <dt class="col-sm-3">Fecha</dt>
            <dd class="col-sm-9"><g:formatDate date="${anticipoPorTransporteInstance?.fecha}" format="dd/MM/yyyy"/></dd>

            <dt class="col-sm-3">Concepto</dt>
            <dd class="col-sm-9"><g:fieldValue bean="${anticipoPorTransporteInstance}" field="descripcion"/></dd>

            <dt class="col-sm-3">Importe [Bs]</dt>
            <dd class="col-sm-9"><g:formatNumber number="${anticipoPorTransporteInstance?.importe ?: 0}" type="number" maxFractionDigits="2"/></dd>

            <g:if test="${anticipoPorTransporteInstance?.importeLiteral}">
                <dt class="col-sm-3">Literal</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${anticipoPorTransporteInstance}" field="importeLiteral"/></dd>
            </g:if>

%{--            <g:if test="${anticipoPorTransporteInstance?.observaciones}">--}%
%{--                <dt class="col-sm-3">Observaciones</dt>--}%
%{--                <dd class="col-sm-9"><g:fieldValue bean="${anticipoPorTransporteInstance}" field="observaciones"/></dd>--}%
%{--            </g:if>--}%

            <g:if test="${anticipoPorTransporteInstance?.usuario}">
                <dt class="col-sm-3">Usuario</dt>
                <dd class="col-sm-9">${anticipoPorTransporteInstance?.usuario?.username}</dd>
            </g:if>
        </dl>
    </div>

    <div class="card-footer d-flex align-items-center">
        <g:if test="${!anticipoPorTransporteInstance?.anulado}">
            <g:form action="anular" class="d-inline">
                <g:hiddenField name="id" value="${anticipoPorTransporteInstance?.id}"/>
                <button type="button" class="btn btn-danger btn-sm btn-anular">
                    <i class="fas fa-ban mr-1"></i>Anular
                </button>
            </g:form>
        </g:if>
        <g:else>
            <span class="text-muted"><i class="fas fa-ban mr-1"></i>Anticipo anulado</span>
        </g:else>
        <div class="ml-auto">
            <g:jasperReport controller="anticipoPorTransporte" action="createReport" jasper="orden_anticipo_contra_transporte"
                            format="PDF" _format="PDF"
                            name="OrdenAnticipoTransporte_${anticipoPorTransporteInstance.numeroComprobante}">
                <input type="hidden" name="anticipoId" value="${anticipoPorTransporteInstance.id}"/>
            </g:jasperReport>
        </div>
    </div>
</div>

<script>
    $(function () {
        $('.btn-anular').on('click', function () {
            var form = this.form;
            Swal.fire({
                title: '¿Anular este anticipo?',
                html: 'Se revertirá su efecto en el disponible del automóvil.<br>El registro no se elimina, queda marcado como ANULADO.',
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
