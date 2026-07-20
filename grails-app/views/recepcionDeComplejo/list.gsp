<%@ page import="org.socymet.recepcion.RecepcionDeComplejo" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Recepción</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Recepción</h3>
        <div class="ml-auto">
            <g:link action="create" class="btn btn-primary btn-sm">
                <i class="fas fa-plus"></i> Nueva
            </g:link>
        </div>
    </div>
    <div class="card-body p-0">
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
        <div class="px-3 pt-3 pb-2">
            <g:form action="list" method="GET">
                <div class="input-group" style="max-width:420px">
                    <div class="input-group-prepend">
                        <span class="input-group-text border-right-0 bg-white">
                            <i class="fas fa-search text-muted fa-sm"></i>
                        </span>
                    </div>
                    <input type="text" name="q"
                           class="form-control form-control-sm border-left-0"
                           placeholder="Buscar lote, cliente, empresa o composito…"
                           value="${q ?: ''}"
                           autocomplete="off"/>
                    <div class="input-group-append">
                        <button type="submit" class="btn btn-secondary btn-sm">Buscar</button>
                        <g:if test="${q}">
                            <g:link action="list" class="btn btn-outline-secondary btn-sm" title="Limpiar búsqueda">
                                <i class="fas fa-times"></i>
                            </g:link>
                        </g:if>
                    </div>
                </div>
            </g:form>
        </div>
        <div class="table-responsive">
        <table class="table table-hover table-striped mb-0">
            <thead class="thead-light">
                <tr>
                    <g:sortableColumn property="loteComplejo" title="Lote" params="${[q: q]}"/>
                    <g:sortableColumn property="fechaDeRecepcion" title="Fecha Rec." params="${[q: q]}"/>
                    <th>Cliente</th>
                    <th>Empresa</th>
                    <th>Peso Bruto Humedo [Kg]</th>
                    <th>Análisis</th>
                    <th>Anticipo</th>
                    <th>Estado</th>
                    <th>Composito</th>
                    <th style="width:60px"></th>
                </tr>
            </thead>
            <tbody>
            <g:if test="${!recepcionDeComplejoInstanceList}">
                <tr>
                    <td colspan="10" class="text-center text-muted py-3">
                        <g:if test="${q}">No se encontraron recepciones para "${q}".</g:if>
                        <g:else>No hay recepciones registradas.</g:else>
                    </td>
                </tr>
            </g:if>
            <g:each in="${recepcionDeComplejoInstanceList}" var="inst">
                <tr>
                    <td><g:link action="show" id="${inst.id}">${inst.toString()}</g:link></td>
                    <td><g:formatDate date="${inst.fechaDeRecepcion}" format="dd/MM/yyyy"/></td>
                    <td>${inst.cliente.nombre}</td>
                    <td>${inst.empresa.nombreDeEmpresa}</td>
                    <td><g:formatNumber number="${inst.pesoBruto}" type="number" maxFractionDigits="2"/></td>
                    <td>
                        <g:if test="${inst.estadoAnalisis == 'CON ANALISIS'}">
                            <span class="badge badge-success">${inst.estadoAnalisis}</span>
                        </g:if>
                        <g:else>
                            <span class="badge badge-danger">${inst.estadoAnalisis}</span>
                        </g:else>
                    </td>
                    <td>
                        <g:if test="${inst.estadoAnticipo == 'PAGADO'}">
                            <span class="badge badge-success">${inst.estadoAnticipo}</span>
                        </g:if>
                        <g:elseif test="${inst.estadoAnticipo == 'CON ANTICIPO'}">
                            <span class="badge badge-danger">${inst.estadoAnticipo}</span>
                        </g:elseif>
                        <g:else>
                            <span class="badge badge-info">${inst.estadoAnticipo}</span>
                        </g:else>
                    </td>
                    <td>
                        <g:if test="${inst.estadoDelLote == 'LIQUIDADO'}">
                            <span class="badge badge-success">${inst.estadoDelLote}</span>
                        </g:if>
                        <g:else>
                            <span class="badge badge-danger">${inst.estadoDelLote}</span>
                        </g:else>
                    </td>
                    <td>
                        <g:if test="${!inst.nombreComposito.equals('-')}">
                            <span class="badge badge-primary">${inst.nombreComposito}</span>
                        </g:if>
                        <g:else>
                            <span class="badge badge-secondary">${inst.nombreComposito}</span>
                        </g:else>
                    </td>
                    <td class="text-nowrap">
                        <g:link action="show" id="${inst.id}" class="btn btn-info btn-xs"><i class="fas fa-eye"></i></g:link>
                        <g:set var="motivos" value="${inst.motivosBloqueo()}"/>
                        <g:if test="${!motivos}">
                            <g:link action="edit" id="${inst.id}" class="btn btn-warning btn-xs"><i class="fas fa-edit"></i></g:link>
                        </g:if>
                        <g:else>
                            <span class="btn btn-outline-secondary btn-xs disabled" title="No editable: ${motivos.join('; ')}"><i class="fas fa-lock"></i></span>
                        </g:else>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${recepcionDeComplejoInstanceTotal ?: 0}" params="${[q: q]}"/>
    </div>
</div>
</body>
</html>
