<%@ page import="org.socymet.proveedor.Municipio" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Municipio</title>
</head>
<body>
<div class="card card-outline card-info">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title mr-auto">Municipio</h3>
        <g:link action="index" class="btn btn-secondary btn-sm mr-1"><i class="fas fa-list mr-1"></i>Lista</g:link>
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
            <dt class="col-sm-3">Departamento</dt>
            <dd class="col-sm-9"><g:fieldValue bean="${municipioInstance}" field="departamento"/></dd>
            <dt class="col-sm-3">Municipio</dt>
            <dd class="col-sm-9"><g:fieldValue bean="${municipioInstance}" field="municipio"/></dd>
        </dl>
    </div>
    <div class="card-footer">
        <g:link action="edit" resource="${municipioInstance}" class="btn btn-warning btn-sm">
            <i class="fas fa-edit mr-1"></i>Editar
        </g:link>
        <g:form url="[resource:municipioInstance, action:'delete']" method="DELETE" class="d-inline">
            <g:actionSubmit action="delete" class="btn btn-danger btn-sm" value="Eliminar"
                onclick="return confirm('¿Está seguro de eliminar este registro?');"/>
        </g:form>
    </div>
</div>
</body>
</html>
