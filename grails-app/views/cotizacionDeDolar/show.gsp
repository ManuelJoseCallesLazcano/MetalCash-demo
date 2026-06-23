<%@ page import="org.socymet.cotizaciones.CotizacionDeDolar" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Cotización del Dólar</title>
</head>
<body>
<div class="card card-outline card-info">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title mr-auto">Cotización del Dólar Americano</h3>
        <g:link action="list" class="btn btn-secondary btn-sm mr-1"><i class="fas fa-list mr-1"></i>Lista</g:link>
        <g:link action="create" class="btn btn-primary btn-sm"><i class="fas fa-plus mr-1"></i>Nueva</g:link>
    </div>
    <div class="card-body">
        <g:if test="${flash.message}">
            <div class="alert alert-info alert-dismissible">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                ${flash.message}
            </div>
        </g:if>
        <dl class="row">
            <dt class="col-sm-3">Fecha</dt>
            <dd class="col-sm-9"><g:formatDate date="${cotizacionDeDolarInstance?.fecha}" format="dd/MM/yyyy"/></dd>
            <dt class="col-sm-3">T.C. Oficial</dt>
            <dd class="col-sm-9"><g:fieldValue bean="${cotizacionDeDolarInstance}" field="tipoDeCambioOficial"/></dd>
            <dt class="col-sm-3">T.C. Comercial</dt>
            <dd class="col-sm-9"><g:fieldValue bean="${cotizacionDeDolarInstance}" field="tipoDeCambioComercial"/></dd>
            <dt class="col-sm-3">Activo</dt>
            <dd class="col-sm-9">
                <g:if test="${cotizacionDeDolarInstance?.activo}">
                    <span class="badge badge-success">Sí</span>
                </g:if>
                <g:else>
                    <span class="badge badge-secondary">No</span>
                </g:else>
            </dd>
        </dl>
    </div>
    <div class="card-footer">
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <g:form class="d-inline">
                <g:hiddenField name="id" value="${cotizacionDeDolarInstance?.id}"/>
                <g:link action="edit" id="${cotizacionDeDolarInstance?.id}" class="btn btn-warning btn-sm">
                    <i class="fas fa-edit mr-1"></i>Editar
                </g:link>
                <g:actionSubmit action="delete" class="btn btn-danger btn-sm" value="Eliminar"
                    onclick="return confirm('¿Está seguro de eliminar este registro?');"/>
            </g:form>
        </sec:ifAnyGranted>
    </div>
</div>
</body>
</html>
