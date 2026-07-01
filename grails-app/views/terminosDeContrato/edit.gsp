<%@ page import="org.socymet.cotizaciones.TerminosDeContrato" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Editar Término de Contrato</title>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title mr-auto">Editar Término de Contrato</h3>
        <g:link action="show" id="${terminosDeContratoInstance?.id}" class="btn btn-secondary btn-sm"><i class="fas fa-eye mr-1"></i>Ver</g:link>
    </div>
    <g:form action="update">
        <g:hiddenField name="id" value="${terminosDeContratoInstance?.id}"/>
        <g:hiddenField name="version" value="${terminosDeContratoInstance?.version}"/>
        <div class="card-body">
            <g:hasErrors bean="${terminosDeContratoInstance}">
                <div class="alert alert-danger">
                    <g:eachError bean="${terminosDeContratoInstance}" var="error"><div><g:message error="${error}"/></div></g:eachError>
                </div>
            </g:hasErrors>
            <g:render template="form"/>
        </div>
        <div class="card-footer">
            <button type="submit" class="btn btn-primary"><i class="fas fa-save mr-1"></i>Actualizar</button>
            <g:link action="show" id="${terminosDeContratoInstance?.id}" class="btn btn-secondary">Cancelar</g:link>
        </div>
    </g:form>
</div>
</body>
</html>
