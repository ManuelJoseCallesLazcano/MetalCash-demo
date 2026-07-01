<%@ page import="org.socymet.cotizaciones.TablaOrigenCotizacionesComplejo" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Nueva Tabla de Precios</title>
    <asset:javascript src="tablaPrecioComplejo/tablaPrecioComplejo.js"/>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title mr-auto">Nueva Tabla de Precios</h3>
        <g:link action="list" class="btn btn-secondary btn-sm"><i class="fas fa-list mr-1"></i>Lista</g:link>
    </div>
    <g:form action="save">
        <div class="card-body">
            <g:if test="${flash.message}">
                <div class="alert alert-info alert-dismissible"><button type="button" class="close" data-dismiss="alert">&times;</button>${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${tablaOrigenCotizacionesComplejoInstance}">
                <div class="alert alert-danger">
                    <g:eachError bean="${tablaOrigenCotizacionesComplejoInstance}" var="error"><div><g:message error="${error}"/></div></g:eachError>
                </div>
            </g:hasErrors>
            <g:render template="form"/>
        </div>
        <div class="card-footer">
            <button type="submit" class="btn btn-primary"><i class="fas fa-save mr-1"></i>Guardar</button>
            <g:link action="list" class="btn btn-secondary">Cancelar</g:link>
        </div>
    </g:form>
</div>
</body>
</html>
