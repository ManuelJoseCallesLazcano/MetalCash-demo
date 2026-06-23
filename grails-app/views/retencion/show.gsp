<%@ page import="org.socymet.proveedor.Retencion" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>${retencionInstance?.descripcion}</title>
    <style>
        .show-section-title {
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
        .show-label {
            font-weight: 600;
            color: #6c757d;
            font-size: 0.82rem;
            text-transform: uppercase;
            letter-spacing: 0.04em;
            margin-bottom: 0.15rem;
        }
        .show-value {
            font-size: 0.95rem;
            color: #343a40;
        }
    </style>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">${retencionInstance?.descripcion}</h3>
        <div class="ml-auto">
            <g:link action="list" class="btn btn-secondary btn-sm">
                <i class="fas fa-list"></i> Lista
            </g:link>
            <g:link action="create" class="btn btn-primary btn-sm ml-1">
                <i class="fas fa-plus"></i> Nueva
            </g:link>
        </div>
    </div>
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

        <h5 class="show-section-title">Datos de la Retención</h5>

        <div class="row mb-3">
            <div class="col-sm-6">
                <div class="show-label">Descripción</div>
                <div class="show-value font-weight-bold">
                    <g:fieldValue bean="${retencionInstance}" field="descripcion"/>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="show-label">Tipo de Retención</div>
                <div class="show-value">
                    <span class="badge badge-${retencionInstance?.tipoDeRetencion == 'DE LEY' ? 'danger' : 'secondary'} badge-pill">
                        <g:fieldValue bean="${retencionInstance}" field="tipoDeRetencion"/>
                    </span>
                </div>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-sm-3">
                <div class="show-label">Cantidad de Descuento</div>
                <div class="show-value">
                    <g:fieldValue bean="${retencionInstance}" field="cantidadDescuento"/>
                    <span class="text-muted ml-1"><g:fieldValue bean="${retencionInstance}" field="unidadDeDescuento"/></span>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="show-label">Unidad de Descuento</div>
                <div class="show-value">
                    <g:fieldValue bean="${retencionInstance}" field="unidadDeDescuento"/>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="show-label">Asignación del Descuento</div>
                <div class="show-value">
                    <g:fieldValue bean="${retencionInstance}" field="asignacionDelDescuento"/>
                </div>
            </div>
        </div>
    </div>

    <div class="card-footer">
        <g:link action="list" class="btn btn-secondary btn-sm">
            <i class="fas fa-arrow-left"></i> Volver
        </g:link>
        <g:link action="edit" id="${retencionInstance?.id}" class="btn btn-warning btn-sm ml-1">
            <i class="fas fa-edit"></i> Editar
        </g:link>
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <g:form action="delete" method="post" style="display:inline">
                <g:hiddenField name="id" value="${retencionInstance?.id}"/>
                <button type="submit" class="btn btn-danger btn-sm ml-1"
                    onclick="return confirm('¿Eliminar esta retención?')">
                    <i class="fas fa-trash"></i> Eliminar
                </button>
            </g:form>
        </sec:ifAnyGranted>
    </div>
</div>
</body>
</html>
