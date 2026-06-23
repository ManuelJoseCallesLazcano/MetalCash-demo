<%@ page import="org.socymet.cotizaciones.CotizacionQuincenalDeMinerales" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Cotización Quincenal</title>
</head>
<body>
<div class="card card-outline card-info">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title mr-auto">Cotización Quincenal</h3>
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
        <h6 class="text-muted font-weight-bold border-bottom pb-2 mb-3">Cotización Quincenal</h6>
        <dl class="row">
            <dt class="col-sm-3">Fecha</dt>
            <dd class="col-sm-9"><g:formatDate date="${cotizacionQuincenalDeMineralesInstance?.fecha}" format="dd/MM/yyyy"/></dd>
            <dt class="col-sm-3">Zinc [$us/lf]</dt>
            <dd class="col-sm-9"><g:formatNumber number="${cotizacionQuincenalDeMineralesInstance?.zinc}" format="#0.00"/></dd>
            <dt class="col-sm-3">Plomo [$us/lf]</dt>
            <dd class="col-sm-9"><g:formatNumber number="${cotizacionQuincenalDeMineralesInstance?.plomo}" format="#0.00"/></dd>
            <dt class="col-sm-3">Plata [$us/ot]</dt>
            <dd class="col-sm-9"><g:formatNumber number="${cotizacionQuincenalDeMineralesInstance?.plata}" format="#0.00"/></dd>
        </dl>
        <h6 class="text-muted font-weight-bold border-bottom pb-2 mb-3 mt-3">Alícuota Quincenal</h6>
        <dl class="row">
            <dt class="col-sm-3">Zinc %</dt>
            <dd class="col-sm-9"><g:fieldValue bean="${cotizacionQuincenalDeMineralesInstance}" field="alicuotaZinc"/></dd>
            <dt class="col-sm-3">Plomo %</dt>
            <dd class="col-sm-9"><g:fieldValue bean="${cotizacionQuincenalDeMineralesInstance}" field="alicuotaPlomo"/></dd>
            <dt class="col-sm-3">Plata %</dt>
            <dd class="col-sm-9"><g:fieldValue bean="${cotizacionQuincenalDeMineralesInstance}" field="alicuotaPlata"/></dd>
        </dl>
    </div>
    <div class="card-footer">
        <sec:ifAnyGranted roles="ROLE_ADMIN">
            <g:link action="edit" resource="${cotizacionQuincenalDeMineralesInstance}" class="btn btn-warning btn-sm">
                <i class="fas fa-edit mr-1"></i>Editar
            </g:link>
            <g:form url="[resource:cotizacionQuincenalDeMineralesInstance, action:'delete']" method="DELETE" class="d-inline">
                <g:actionSubmit action="delete" class="btn btn-danger btn-sm" value="Eliminar"
                    onclick="return confirm('¿Está seguro de eliminar este registro?');"/>
            </g:form>
        </sec:ifAnyGranted>
    </div>
</div>
</body>
</html>
