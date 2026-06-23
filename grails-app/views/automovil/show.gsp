<%@ page import="org.socymet.proveedor.Automovil" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Automóvil</title>
</head>
<body>
<div class="card card-outline card-info">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title mr-auto">Automóvil</h3>
        <g:link action="list" class="btn btn-secondary btn-sm mr-1"><i class="fas fa-list mr-1"></i>Lista</g:link>
        <g:link action="create" class="btn btn-primary btn-sm"><i class="fas fa-plus mr-1"></i>Nuevo</g:link>
    </div>
    <div class="card-body">
        <g:if test="${flash.message}">
            <div class="alert alert-info alert-dismissible">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                ${flash.message}
            </div>
        </g:if>
        <dl class="row">
            <dt class="col-sm-3">Placa</dt>
            <dd class="col-sm-9"><g:fieldValue bean="${automovilInstance}" field="placa"/></dd>
            <g:if test="${automovilInstance?.modelo}">
                <dt class="col-sm-3">Modelo</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${automovilInstance}" field="modelo"/></dd>
            </g:if>
            <g:if test="${automovilInstance?.color}">
                <dt class="col-sm-3">Color</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${automovilInstance}" field="color"/></dd>
            </g:if>
        </dl>
    </div>
    <div class="card-footer">
        <g:form class="d-inline">
            <g:hiddenField name="id" value="${automovilInstance?.id}"/>
            <g:link action="edit" id="${automovilInstance?.id}" class="btn btn-warning btn-sm">
                <i class="fas fa-edit mr-1"></i>Editar
            </g:link>
            <g:actionSubmit action="delete" class="btn btn-danger btn-sm" value="Eliminar"
                onclick="return confirm('¿Está seguro de eliminar este registro?');"/>
        </g:form>
    </div>
</div>
</body>
</html>
