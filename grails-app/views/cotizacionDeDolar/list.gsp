<%@ page import="org.socymet.cotizaciones.CotizacionDeDolar" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Cotización del Dólar</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Cotización del Dólar Americano</h3>
        <div class="ml-auto">
            <g:link action="create" class="btn btn-primary btn-sm">
                <i class="fas fa-plus"></i> Nueva Cotización
            </g:link>
        </div>
    </div>
    <div class="card-body p-0">
        <g:if test="${flash.message}">
            <div id="swalFlashMsg" style="display:none">${flash.message}</div>
            <script>
                document.addEventListener('DOMContentLoaded', function () {
                    Swal.fire({
                        icon: 'info',
                        title: 'Información',
                        text: document.getElementById('swalFlashMsg').textContent.trim(),
                        confirmButtonText: 'Aceptar'
                    });
                });
            </script>
        </g:if>
        <div class="table-responsive">
            <table class="table table-hover table-striped mb-0">
                <thead class="thead-light">
                    <tr>
                        <g:sortableColumn property="fecha"                 title="Fecha"          class="px-3"/>
                        <g:sortableColumn property="tipoDeCambioOficial"   title="T.C. Oficial"   class="px-3"/>
                        <g:sortableColumn property="tipoDeCambioComercial" title="T.C. Comercial" class="px-3"/>
                        <g:sortableColumn property="activo"                title="Activo"         class="px-3"/>
                        <th class="px-3" style="width:90px"></th>
                    </tr>
                </thead>
                <tbody>
                <g:each in="${cotizacionDeDolarInstanceList}" var="inst">
                    <tr>
                        <td class="px-3 font-weight-bold">
                            <g:link action="show" id="${inst.id}">
                                <g:formatDate date="${inst.fecha}" format="dd/MM/yyyy"/>
                            </g:link>
                        </td>
                        <td class="px-3">${fieldValue(bean: inst, field: 'tipoDeCambioOficial')}</td>
                        <td class="px-3">${fieldValue(bean: inst, field: 'tipoDeCambioComercial')}</td>
                        <td class="px-3">
                            <g:if test="${inst.activo}">
                                <span class="badge badge-success">Sí</span>
                            </g:if>
                            <g:else>
                                <span class="badge badge-secondary">No</span>
                            </g:else>
                        </td>
                        <td class="px-3 text-right text-nowrap">
                            <g:link action="show" id="${inst.id}" class="btn btn-xs btn-info" title="Ver">
                                <i class="fas fa-eye"></i>
                            </g:link>
                            <g:link action="edit" id="${inst.id}" class="btn btn-xs btn-warning" title="Editar">
                                <i class="fas fa-edit"></i>
                            </g:link>
                        </td>
                    </tr>
                </g:each>
                <g:if test="${!cotizacionDeDolarInstanceList}">
                    <tr>
                        <td colspan="5" class="text-center text-muted py-4">No hay cotizaciones registradas.</td>
                    </tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${cotizacionDeDolarInstanceTotal ?: 0}"/>
    </div>
</div>
</body>
</html>
