<%@ page import="org.socymet.proveedor.Automovil" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Nuevo Automóvil</title>
</head>
<body>
<div class="card card-primary">
    <div class="card-header">
        <h3 class="card-title">Nuevo Automóvil</h3>
    </div>
    <g:form action="save">
        <div class="card-body">
            <g:hasErrors bean="${automovilInstance}">
                <div class="alert alert-danger alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <ul class="mb-0">
                        <g:eachError bean="${automovilInstance}" var="error">
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
