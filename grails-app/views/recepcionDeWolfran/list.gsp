<%@ page import="org.socymet.recepcion.RecepcionDeWolfran" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Recepcion De Wolfran</title>
    <script src="${assetPath(src: 'vendor/sweetalert2.all.min.js')}"></script>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title">Recepcion De Wolfran</h3>
        <div class="ml-auto">
            <g:link action="create" class="btn btn-primary btn-sm">
                <i class="fas fa-plus"></i> Nuevo
            </g:link>
        </div>
    </div>
    <div class="card-body p-0">
        <g:if test="${flash.message}">
            <div id="swalFlashMsg" style="display:none">${flash.message}</div>
            <script>
                document.addEventListener('DOMContentLoaded', function () {
                    Swal.fire({ icon: 'info', title: 'Información',
                        text: document.getElementById('swalFlashMsg').textContent.trim(),
                        confirmButtonText: 'Aceptar' });
                });
            </script>
        </g:if>
        <div class="table-responsive">
            <table class="table table-hover table-striped mb-0">
                <thead class="thead-light">

					<tr>

                        <g:sortableColumn property="loteWolfran" title="${message(code: 'recepcionDeWolfran.loteWolfran.label', default: 'Lote Wolfran')}" />

                        <g:sortableColumn property="fechaDeRecepcion" title="${message(code: 'recepcionDeWolfran.fechaDeRecepcion.label', default: 'Fecha De Recepcion')}" />

                        <th><g:message code="recepcionDeWolfran.cliente.label" default="Cliente" /></th>

                        <th><g:message code="recepcionDeWolfran.empresa.label" default="Empresa" /></th>

                        <th><g:message code="recepcionDeWolfran.cantidadDeSacos.label" default="Cantidad de Sacos" /></th>

                        <th><g:message code="recepcionDeWolfran.pesoBruto.label" default="Peso Bruto" /></th>

                        <th><g:message code="recepcionDeWolfran.estadoDelLote.label" default="Estado del Lote" /></th>
					
					</tr>
				                </thead>
                <tbody>

				<g:each in="${recepcionDeWolfranInstanceList}" var="recepcionDeWolfranInstance">
					<tr>

                        <td><g:link action="show" id="${recepcionDeWolfranInstance.id}">${recepcionDeWolfranInstance.toString()}</g:link></td>

                        <td><g:formatDate date="${recepcionDeWolfranInstance.fechaDeRecepcion}" format="dd/MM/yyyy"/></td>

                        <td>${recepcionDeWolfranInstance.cliente.nombre}</td>

                        <td>${recepcionDeWolfranInstance.empresa.nombreDeEmpresa}</td>

                        <td>${recepcionDeWolfranInstance.cantidadDeSacos}</td>

                        <td>${recepcionDeWolfranInstance.pesoBruto}</td>

                        <td>${recepcionDeWolfranInstance.estadoDelLote}</td>
					
					</tr>
				</g:each>
				
                <g:if test="${!recepcionDeWolfranInstanceList}">
                    <tr><td colspan="7" class="text-center text-muted py-4">No hay registros.</td></tr>
                </g:if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer">
        <g:paginate total="${recepcionDeWolfranInstanceTotal ?: 0}" />
    </div>
</div>
</body>
</html>
