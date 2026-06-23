<%@ page import="org.socymet.proveedor.Chofer" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Chofer</title>
</head>
<body>
<div class="card card-outline card-info">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title mr-auto">Chofer</h3>
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
            <dt class="col-sm-3">C.I.</dt>
            <dd class="col-sm-9"><g:fieldValue bean="${choferInstance}" field="ci"/></dd>
            <dt class="col-sm-3">Nombre</dt>
            <dd class="col-sm-9"><g:fieldValue bean="${choferInstance}" field="nombre"/></dd>
            <g:if test="${choferInstance?.telefono}">
                <dt class="col-sm-3">Teléfono</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${choferInstance}" field="telefono"/></dd>
            </g:if>
            <g:if test="${choferInstance?.celular}">
                <dt class="col-sm-3">Celular</dt>
                <dd class="col-sm-9"><g:fieldValue bean="${choferInstance}" field="celular"/></dd>
            </g:if>
        </dl>
    </div>
    <div class="card-footer">
        <g:form class="d-inline">
            <g:hiddenField name="id" value="${choferInstance?.id}"/>
            <g:link action="edit" id="${choferInstance?.id}" class="btn btn-warning btn-sm">
                <i class="fas fa-edit mr-1"></i>Editar
            </g:link>
            <g:actionSubmit action="delete" class="btn btn-danger btn-sm" value="Eliminar"
                onclick="return confirm('¿Está seguro de eliminar este registro?');"/>
        </g:form>
    </div>
</div>
</body>
</html>
