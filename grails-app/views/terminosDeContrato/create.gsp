<%@ page import="org.socymet.cotizaciones.TerminosDeContrato" %>
<html>
<head>
    <meta name="layout" content="main">
    <title>Nuevo Término de Contrato</title>
</head>
<body>
<div class="card card-secondary">
    <div class="card-header d-flex align-items-center">
        <h3 class="card-title mr-auto">Nuevo Término de Contrato</h3>
        <g:link action="list" class="btn btn-secondary btn-sm"><i class="fas fa-list mr-1"></i>Lista</g:link>
    </div>
    <g:form action="save">
        <div class="card-body">
            <g:hasErrors bean="${terminosDeContratoInstance}">
                <div class="alert alert-danger">
                    <g:eachError bean="${terminosDeContratoInstance}" var="error"><div><g:message error="${error}"/></div></g:eachError>
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
