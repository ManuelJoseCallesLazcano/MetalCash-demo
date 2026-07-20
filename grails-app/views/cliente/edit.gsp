<%@ page import="org.socymet.proveedor.Cliente" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'cliente.label', default: 'Cliente')}"/>
    <title>Editar Cliente</title>
    <link rel="stylesheet" href="${assetPath(src: 'vendor/select2.min.css')}" type="text/css" >
    <style>
        .select2-container--default .select2-selection--single {
            height: calc(1.5em + .75rem + 2px);
            padding: .375rem .75rem;
            border: 1px solid #ced4da;
            border-radius: .25rem;
        }
        .select2-container--default .select2-selection--single .select2-selection__rendered {
            padding: 0;
            line-height: 1.5;
            color: #495057;
        }
        .select2-container--default .select2-selection--single .select2-selection__placeholder {
            line-height: 1.5;
            color: #6c757d;
        }
        .select2-container--default .select2-selection--single .select2-selection__arrow {
            height: 100%;
            top: 0;
            right: .375rem;
        }
        .select2-container--default .select2-selection--single .select2-selection__clear {
            line-height: 1.5;
        }
        .select2-container--default.select2-container--open .select2-selection--single,
        .select2-container--default.select2-container--focus .select2-selection--single {
            border-color: #80bdff;
            outline: 0;
            box-shadow: 0 0 0 .2rem rgba(0, 123, 255, .25);
        }
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
    <script src="${assetPath(src: 'vendor/select2.min.js')}"></script>
    <script src="${assetPath(src: 'vendor/select2-i18n-es.js')}"></script>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
    <asset:javascript src="cliente/clienteUtilidades.js"/>
</head>

<body>
<div class="card card-primary">
    <div class="card-header">
        <h3 class="card-title">Editar Cliente</h3>
    </div>
    <g:form method="post">
        <g:hiddenField name="id" value="${clienteInstance?.id}"/>
        <g:hiddenField name="version" value="${clienteInstance?.version}"/>
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
            <g:hasErrors bean="${clienteInstance}">
                <div id="swalErrorList" style="display:none">
                    <ul style="text-align:left; margin:0; padding-left:1.2em">
                        <g:eachError bean="${clienteInstance}" var="error">
                            <li><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </div>
                <script>
                    $(document).ready(function () {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error de validación',
                            html: document.getElementById('swalErrorList').innerHTML,
                            confirmButtonText: 'Corregir'
                        });
                    });
                </script>
            </g:hasErrors>
            <g:render template="form"/>
        </div>
        <div class="card-footer">
            <g:actionSubmit class="btn btn-primary" action="update" value="Actualizar"/>
            <g:actionSubmit class="btn btn-danger ml-1" action="delete" value="Eliminar" formnovalidate=""
                onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: '¿Está seguro?')}');"/>
            <g:link action="list" class="btn btn-secondary ml-1">Cancelar</g:link>
        </div>
    </g:form>
</div>
</body>
</html>
