<%@ page import="org.socymet.anticipos.AnticipoPorTransporte" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Editar Anticipo por Transporte</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" type="text/css">
    <style>
        .select2-container--default .select2-selection--single {
            height: calc(1.5em + .75rem + 2px);
            padding: .375rem .75rem;
            border: 1px solid #ced4da;
            border-radius: .25rem;
        }
        .select2-container--default .select2-selection--single .select2-selection__rendered { padding: 0; line-height: 1.5; color: #495057; }
        .select2-container--default .select2-selection--single .select2-selection__arrow { height: 100%; top: 0; right: .375rem; }
        .form-section-title {
            font-size: 0.78rem; font-weight: 700; text-transform: uppercase; letter-spacing: 0.07em;
            color: #2c3e50; border-left: 4px solid #17a2b8; background: linear-gradient(to right, #e5f6f8, transparent);
            padding: 0.45rem 0.85rem; margin: 1.5rem 0 1rem; border-radius: 0 3px 3px 0;
        }
    </style>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/i18n/es.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
    <asset:javascript src="NumerosALetras.js"/>
    <asset:javascript src="anticipo/anticipoPorTransporteForm.js"/>
</head>
<body>
<div class="card card-primary">
    <div class="card-header">
        <h3 class="card-title">Editar Anticipo por Transporte
            <span class="badge badge-light ml-1">${anticipoPorTransporteInstance}</span>
        </h3>
    </div>
    <g:form method="post" name="anticipoForm">
        <g:hiddenField name="id" value="${anticipoPorTransporteInstance?.id}"/>
        <g:hiddenField name="version" value="${anticipoPorTransporteInstance?.version}"/>
        <div class="card-body">
            <g:hasErrors bean="${anticipoPorTransporteInstance}">
                <div id="swalErrorList" style="display:none">
                    <ul style="text-align:left; margin:0; padding-left:1.2em">
                        <g:eachError bean="${anticipoPorTransporteInstance}" var="error">
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
            <g:actionSubmit class="btn btn-primary" action="update" value="Actualizar"/>
            <g:link action="show" id="${anticipoPorTransporteInstance?.id}" class="btn btn-secondary ml-1">Cancelar</g:link>
        </div>
    </g:form>
</div>
</body>
</html>
