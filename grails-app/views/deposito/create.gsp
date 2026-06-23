<%@ page import="org.socymet.proveedor.Deposito" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Nuevo Depósito</title>
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
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-primary">
    <div class="card-header">
        <h3 class="card-title">Nuevo Depósito</h3>
    </div>
    <g:form action="save">
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
            <g:hasErrors bean="${depositoInstance}">
                <div id="swalErrorList" style="display:none">
                    <ul style="text-align:left; margin:0; padding-left:1.2em">
                        <g:eachError bean="${depositoInstance}" var="error">
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
            <g:submitButton name="create" class="btn btn-primary" value="Guardar"/>
            <g:link action="list" class="btn btn-secondary ml-1">Cancelar</g:link>
        </div>
    </g:form>
</div>
</body>
</html>
