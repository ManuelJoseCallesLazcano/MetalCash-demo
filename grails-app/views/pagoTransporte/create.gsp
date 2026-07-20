<%@ page import="org.socymet.cancelacion.PagoTransporte" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Nuevo Pago de Transporte</title>
    <link rel="stylesheet" href="${assetPath(src: 'vendor/select2.min.css')}" type="text/css">
    <style>
        .select2-container--default .select2-selection--single {
            height: calc(1.5em + .75rem + 2px);
            padding: .375rem .75rem;
            border: 1px solid #ced4da;
            border-radius: .25rem;
        }
        .select2-container--default .select2-selection--single .select2-selection__rendered { padding: 0; line-height: 1.5; color: #495057; }
        .select2-container--default .select2-selection--single .select2-selection__arrow { height: 100%; top: 0; right: .375rem; }
        .select2-container--default.select2-container--open .select2-selection--single,
        .select2-container--default.select2-container--focus .select2-selection--single {
            border-color: #80bdff; outline: 0; box-shadow: 0 0 0 .2rem rgba(0, 123, 255, .25);
        }
        .form-section-title {
            font-size: 0.78rem; font-weight: 700; text-transform: uppercase; letter-spacing: 0.07em;
            color: #2c3e50; border-left: 4px solid #17a2b8; background: linear-gradient(to right, #e5f6f8, transparent);
            padding: 0.45rem 0.85rem; margin: 1.5rem 0 1rem; border-radius: 0 3px 3px 0;
        }
    </style>
    <script src="${assetPath(src: 'vendor/select2.min.js')}"></script>
    <script src="${assetPath(src: 'vendor/select2-i18n-es.js')}"></script>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
    <asset:javascript src="NumerosALetras.js"/>
    <asset:javascript src="cancelacion/pagoTransporteForm.js"/>
</head>
<body>
<div class="card card-primary">
    <div class="card-header">
        <h3 class="card-title">Nuevo Pago de Transporte</h3>
    </div>
    <g:form action="save" name="pagoForm">
        <g:hiddenField name="vista" value="create"/>
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
            <g:hasErrors bean="${pagoTransporteInstance}">
                <div id="swalErrorList" style="display:none">
                    <ul style="text-align:left; margin:0; padding-left:1.2em">
                        <g:eachError bean="${pagoTransporteInstance}" var="error">
                            <li><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </div>
                <script>
                    document.addEventListener('DOMContentLoaded', function () {
                        Swal.fire({ icon: 'error', title: 'Error de validación',
                            html: document.getElementById('swalErrorList').innerHTML, confirmButtonText: 'Corregir' });
                    });
                </script>
            </g:hasErrors>
            <g:render template="form"/>
        </div>
        <div class="card-footer">
            <button type="button" class="btn btn-primary" id="btnGuardar"><i class="fas fa-save mr-1"></i>Guardar</button>
            <g:link action="list" class="btn btn-secondary ml-1">Cancelar</g:link>
        </div>
    </g:form>
</div>
<script>
    $(function () {
        $('#btnGuardar').on('click', function () {
            if (!$('#lotes').val()) {
                Swal.fire({ icon: 'warning', title: 'Sin lotes', text: 'Elija un automóvil y presione BUSCAR LOTES antes de guardar.' });
                return;
            }
            Swal.fire({
                title: '¿Registrar este pago?',
                html: 'Se registrará el pago, se consumirá el anticipo disponible del automóvil y se marcarán los lotes como pagados.',
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Sí, registrar',
                cancelButtonText: 'Cancelar',
                reverseButtons: true
            }).then(function (result) {
                if (result.isConfirmed) document.forms['pagoForm'].submit();
            });
        });
    });
</script>
</body>
</html>
