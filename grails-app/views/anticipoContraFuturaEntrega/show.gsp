<%@ page import="org.socymet.anticipos.AnticipoContraFuturaEntrega" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Anticipo Contra Futura Entrega</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
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
        <h3 class="card-title mr-auto">Anticipo Contra Futura Entrega
            <g:if test="${anticipoContraFuturaEntregaInstance?.numeroAnticipo}">
                <span class="badge badge-info ml-1">N° ${anticipoContraFuturaEntregaInstance}</span>
            </g:if>
            <g:if test="${anticipoContraFuturaEntregaInstance?.anulado}">
                <span class="badge badge-danger ml-1">ANULADO</span>
            </g:if>
        </h3>
        <g:link action="list" class="btn btn-secondary btn-sm mr-1">
            <i class="fas fa-list mr-1"></i>Lista
        </g:link>
        <g:link action="create" class="btn btn-primary btn-sm">
            <i class="fas fa-plus mr-1"></i>Nuevo
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
            <g:if test="${anticipoContraFuturaEntregaInstance?.cliente}">
                <dt class="col-sm-3">Cliente</dt>
                <dd class="col-sm-9">
                    <g:link controller="cliente" action="show" id="${anticipoContraFuturaEntregaInstance?.cliente?.id}">${anticipoContraFuturaEntregaInstance?.cliente?.encodeAsHTML()}</g:link>
                </dd>
            </g:if>
            <g:if test="${anticipoContraFuturaEntregaInstance?.empresa}">
                <dt class="col-sm-3">Empresa</dt>
                <dd class="col-sm-9">
                    <g:link controller="empresa" action="show" id="${anticipoContraFuturaEntregaInstance?.empresa?.id}">${anticipoContraFuturaEntregaInstance?.empresa?.encodeAsHTML()}</g:link>
                </dd>
            </g:if>
        </dl>

        <h5 class="form-section-title">Datos del Anticipo</h5>
        <dl class="row mb-0">
            <dt class="col-sm-3">Fecha de Anticipo</dt>
            <dd class="col-sm-9"><g:formatDate date="${anticipoContraFuturaEntregaInstance?.fechaDeAnticipo}" format="dd/MM/yyyy"/></dd>

            <dt class="col-sm-3">Concepto</dt>
            <dd class="col-sm-9"><g:fieldValue bean="${anticipoContraFuturaEntregaInstance}" field="compromiso"/></dd>

            <dt class="col-sm-3">Monto [Bs]</dt>
            <dd class="col-sm-9"><g:formatNumber number="${anticipoContraFuturaEntregaInstance?.importe ?: 0}" type="number" maxFractionDigits="2"/></dd>

            <g:if test="${anticipoContraFuturaEntregaInstance?.importeLiteral}">
                <dt class="col-sm-3">Literal</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${anticipoContraFuturaEntregaInstance}" field="importeLiteral"/></dd>
            </g:if>

            <g:if test="${anticipoContraFuturaEntregaInstance?.observaciones}">
                <dt class="col-sm-3">Observaciones</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${anticipoContraFuturaEntregaInstance}" field="observaciones"/></dd>
            </g:if>

            <g:if test="${anticipoContraFuturaEntregaInstance?.usuario}">
                <dt class="col-sm-3">Usuario</dt>
                <dd class="col-sm-9">${anticipoContraFuturaEntregaInstance?.usuario?.username}</dd>
            </g:if>
        </dl>
    </div>

    <div class="card-footer d-flex align-items-center">
        <g:if test="${!anticipoContraFuturaEntregaInstance?.anulado}">
            <g:form action="anular" class="d-inline">
                <g:hiddenField name="id" value="${anticipoContraFuturaEntregaInstance?.id}"/>
                <button type="button" class="btn btn-danger btn-sm btn-anular">
                    <i class="fas fa-ban mr-1"></i>Anular
                </button>
            </g:form>
        </g:if>
        <g:else>
            <span class="text-muted"><i class="fas fa-ban mr-1"></i>Anticipo anulado</span>
        </g:else>
        <div class="ml-auto">
            <g:jasperReport controller="anticipoContraFuturaEntrega" action="crearReporte" jasper="anticipo_contra_futura_entrega"
                            format="PDF" _format="PDF"
                            name="ComprobanteAnticipoContraFuturaEntrega_${anticipoContraFuturaEntregaInstance.numeroAnticipo}">
                <input type="hidden" name="ACE_ID" value="${anticipoContraFuturaEntregaInstance.id}"/>
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
                html: 'Se revertirá su efecto en el estado de cuenta del cliente.<br>El registro no se elimina, queda marcado como ANULADO.',
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
