<html>
<head>
    <meta name="layout" content="main">
    <title>Nueva Cotización Diaria</title>
</head>
<body>
<div class="card card-primary">
    <div class="card-header">
        <h3 class="card-title">Nueva Cotización Diaria</h3>
    </div>
    <g:form url="[resource:cotizacionDiariaDeMineralesInstance, action:'save']">
        <div class="card-body">
            <g:hasErrors bean="${cotizacionDiariaDeMineralesInstance}">
                <div class="alert alert-danger alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <ul class="mb-0">
                        <g:eachError bean="${cotizacionDiariaDeMineralesInstance}" var="error">
                            <li><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </div>
            </g:hasErrors>
            <g:render template="form"/>
        </div>
        <div class="card-footer">
            <g:submitButton name="create" class="btn btn-primary" value="Guardar"/>
            <g:link action="index" class="btn btn-secondary ml-1">Cancelar</g:link>
        </div>
    </g:form>
</div>
</body>
</html>
