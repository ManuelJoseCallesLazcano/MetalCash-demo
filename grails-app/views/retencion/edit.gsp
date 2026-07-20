<%@ page import="org.socymet.proveedor.Retencion" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Editar Retención</title>
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
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-warning">
    <div class="card-header">
        <h3 class="card-title">Editar Retención</h3>
    </div>
    <g:form method="post">
        <g:hiddenField name="id"      value="${retencionInstance?.id}"/>
        <g:hiddenField name="version" value="${retencionInstance?.version}"/>
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
            <g:hasErrors bean="${retencionInstance}">
                <div id="swalErrorList" style="display:none">
                    <ul style="text-align:left; margin:0; padding-left:1.2em">
                        <g:eachError bean="${retencionInstance}" var="error">
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
            <g:actionSubmit action="update" class="btn btn-warning" value="Actualizar"/>
            <g:link action="list" class="btn btn-secondary ml-1">Cancelar</g:link>
            <sec:ifAnyGranted roles="ROLE_ADMIN">
                <g:actionSubmit action="delete" class="btn btn-danger ml-3" value="Eliminar"
                    onclick="return confirm('¿Eliminar esta retención? Esta acción no se puede deshacer.');"/>
            </sec:ifAnyGranted>
        </div>
    </g:form>
</div>
<script>
$(document).ready(function () {
    var opcionesPorUnidad = {
        '%':   ['VNV', 'VBV'],
        'Bs': ['SACO', 'FIJO']
    };
    $('#unidadDeDescuento').on('change', function () {
        var opciones = opcionesPorUnidad[$(this).val()] || [];
        var sel = $('#asignacionDelDescuento').empty();
        if (!opciones.length) sel.append($('<option>').val('').text('— Seleccione —'));
        $.each(opciones, function (i, v) { sel.append($('<option>').val(v).text(v)); });
    });
});
</script>
</body>
</html>
