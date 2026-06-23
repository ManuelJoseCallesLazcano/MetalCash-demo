<%@ page import="org.socymet.proveedor.Chofer" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Editar Chofer</title>
</head>
<body>
<div class="card card-warning">
    <div class="card-header">
        <h3 class="card-title">Editar Chofer</h3>
    </div>
    <g:form method="post">
        <div class="card-body">
            <g:hasErrors bean="${choferInstance}">
                <div class="alert alert-danger alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <ul class="mb-0">
                        <g:eachError bean="${choferInstance}" var="error">
                            <li><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </div>
            </g:hasErrors>
            <g:hiddenField name="id"      value="${choferInstance?.id}"/>
            <g:hiddenField name="version" value="${choferInstance?.version}"/>
            <g:render template="form"/>
        </div>
        <div class="card-footer">
            <g:actionSubmit action="update" class="btn btn-warning" value="Actualizar"/>
            <g:link action="list" class="btn btn-secondary ml-1">Cancelar</g:link>
        </div>
    </g:form>
</div>
</body>
</html>
