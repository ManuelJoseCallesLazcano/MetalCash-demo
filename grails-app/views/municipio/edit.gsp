<%@ page import="org.socymet.proveedor.Municipio" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Editar Municipio</title>
</head>
<body>
<div class="card card-warning">
    <div class="card-header">
        <h3 class="card-title">Editar Municipio</h3>
    </div>
    <g:form url="[resource:municipioInstance, action:'update']" method="PUT">
        <div class="card-body">
            <g:hasErrors bean="${municipioInstance}">
                <div class="alert alert-danger alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <ul class="mb-0">
                        <g:eachError bean="${municipioInstance}" var="error">
                            <li><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </div>
            </g:hasErrors>
            <g:hiddenField name="version" value="${municipioInstance?.version}"/>
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
