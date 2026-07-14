<%@ page import="org.smart.compositos.Ingenio" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Editar Ingenio</title>
</head>
<body>
<div class="card card-warning">
    <div class="card-header">
        <h3 class="card-title">Editar Ingenio</h3>
    </div>
    <g:form method="post">
        <div class="card-body">
            <g:hasErrors bean="${ingenioInstance}">
                <div class="alert alert-danger alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <ul class="mb-0">
                        <g:eachError bean="${ingenioInstance}" var="error">
                            <li><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </div>
            </g:hasErrors>
            <g:hiddenField name="id"      value="${ingenioInstance?.id}"/>
            <g:hiddenField name="version" value="${ingenioInstance?.version}"/>
            <g:render template="form"/>
        </div>
        <div class="card-footer">
            <g:actionSubmit action="update" class="btn btn-warning" value="Actualizar"/>
            <g:link action="index" class="btn btn-secondary ml-1">Cancelar</g:link>
        </div>
    </g:form>
</div>
</body>
</html>
