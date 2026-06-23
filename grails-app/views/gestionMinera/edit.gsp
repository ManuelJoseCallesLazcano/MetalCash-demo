<%@ page import="org.smart.parametros.GestionMinera" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Editar Gestión Minera</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" type="text/css">
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
        .select2-container--default .select2-selection--single .select2-selection__arrow {
            height: 100%;
            top: 0;
            right: .375rem;
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
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/i18n/es.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
    <script>
        $(document).ready(function () {
            $('#gestion_year').select2({ width: '100%', language: 'es' });
        });
    </script>
</head>
<body>
<div class="card card-warning">
    <div class="card-header">
        <h3 class="card-title">Editar Gestión Minera</h3>
    </div>
    <g:form url="[resource: gestionMineraInstance, action: 'update']" method="PUT">
        <g:hiddenField name="version" value="${gestionMineraInstance?.version}"/>
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
            <g:hasErrors bean="${gestionMineraInstance}">
                <div id="swalErrorList" style="display:none">
                    <ul style="text-align:left; margin:0; padding-left:1.2em">
                        <g:eachError bean="${gestionMineraInstance}" var="error">
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
            <g:submitButton name="update" class="btn btn-warning" value="Actualizar"/>
            <g:link action="index" class="btn btn-secondary ml-1">Cancelar</g:link>
        </div>
    </g:form>
%{--    <sec:ifAnyGranted roles="ROLE_ADMIN">--}%
%{--        <div class="card-footer pt-0 border-top-0">--}%
%{--            <g:form url="[resource: gestionMineraInstance, action: 'delete']" method="DELETE" style="display:inline">--}%
%{--                <g:submitButton name="delete" class="btn btn-danger" value="Eliminar"--}%
%{--                    onclick="return confirm('¿Eliminar esta gestión? Esta acción no se puede deshacer.');"/>--}%
%{--            </g:form>--}%
%{--        </div>--}%
%{--    </sec:ifAnyGranted>--}%
</div>
</body>
</html>
