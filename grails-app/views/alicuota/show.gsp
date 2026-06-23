<%@ page import="org.socymet.cotizaciones.Alicuota" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Alícuota</title>
</head>
<body>
<div class="card card-outline card-info">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title mr-auto">Alícuota</h3>
        <g:link action="index" class="btn btn-secondary btn-sm mr-1"><i class="fas fa-list mr-1"></i>Lista</g:link>
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
            <dd class="col-sm-9"><g:formatDate date="${alicuotaInstance?.fecha}" format="dd/MM/yyyy"/></dd>
            <dt class="col-sm-3">Zinc</dt>
            <dd class="col-sm-9"><g:formatNumber number="${alicuotaInstance?.zinc}" format="#0.00"/></dd>
            <dt class="col-sm-3">Plomo</dt>
            <dd class="col-sm-9"><g:formatNumber number="${alicuotaInstance?.plomo}" format="#0.00"/></dd>
            <dt class="col-sm-3">Plata</dt>
            <dd class="col-sm-9"><g:formatNumber number="${alicuotaInstance?.plata}" format="#0.00"/></dd>
        </dl>
    </div>
    <div class="card-footer">
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <g:link action="edit" resource="${alicuotaInstance}" class="btn btn-warning btn-sm">
                <i class="fas fa-edit mr-1"></i>Editar
            </g:link>
            <g:form url="[resource:alicuotaInstance, action:'delete']" method="DELETE" class="d-inline">
                <g:actionSubmit action="delete" class="btn btn-danger btn-sm" value="Eliminar"
                    onclick="return confirm('¿Está seguro de eliminar este registro?');"/>
            </g:form>
        </sec:ifAnyGranted>
    </div>
</div>
</body>
</html>
