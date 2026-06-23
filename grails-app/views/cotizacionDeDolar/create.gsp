<%@ page import="org.socymet.cotizaciones.CotizacionDeDolar" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Nueva Cotización del Dólar</title>
</head>
<body>
<div class="card card-primary">
    <div class="card-header">
        <h3 class="card-title">Nueva Cotización del Dólar Americano</h3>
    </div>
    <g:form action="save">
        <div class="card-body">
            <g:hasErrors bean="${cotizacionDeDolarInstance}">
                <div class="alert alert-danger alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <ul class="mb-0">
                        <g:eachError bean="${cotizacionDeDolarInstance}" var="error">
                            <li><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </div>
            </g:hasErrors>
            <g:render template="form"/>
        </div>
        <div class="card-footer">
            <g:submitButton name="create" class="btn btn-primary" value="Guardar"/>
            <g:link action="list" class="btn btn-secondary ml-1">Cancelar</g:link>
        </div>
    </g:form>
</div>
</body>
</html>
