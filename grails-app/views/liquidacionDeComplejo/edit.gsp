<%@ page import="org.socymet.liquidacion.LiquidacionDeComplejo" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Editar Liquidación de Complejo</title>
    <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'styleScrolling.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'chosen.css')}" type="text/css" >
    <g:javascript src="jquery-1.10.1.min.js" />
    <g:javascript src="i18n/grid.locale-es.js" />
    <g:javascript src="jquery.jqGrid.min.js" />
    <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
    <g:javascript src="jquery.jgrowl.min.js" />
    <g:javascript src="notify.min.js" />
    <g:javascript src="scrolling.js" />
    <g:javascript src="chosen.jquery.js" />
    <g:javascript src="jquery.alphanum.js" />
    <script>
        $(document).ready(function() {
            $("#vista").val("edit");
            $.jGrowl("Debe especificar el motivo por el que este registro esta siendo editado en el campo de texto al final del formulario.",
                {sticky: true, header: 'ATENCION'});
            $("#motivoDeModificacion").val("");
            $("#porcentajeRegalia").val("0");
            $("#_modificacion").show();
        });
    </script>
    <g:javascript src="liquidacionDeComplejo/recepcionAutocomplete.js" />
</head>
<body>
<div class="card card-warning">
    <div class="card-header">
        <h3 class="card-title">Editar Liquidación de Complejo</h3>
    </div>
    <g:form method="post">
        <g:hiddenField name="id" value="${liquidacionDeComplejoInstance?.id}" />
        <g:hiddenField name="version" value="${liquidacionDeComplejoInstance?.version}" />
        <div class="card-body">
            <g:if test="${flash.message}">
                <div class="alert alert-info alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    ${flash.message}
                </div>
            </g:if>
            <g:hasErrors bean="${liquidacionDeComplejoInstance}">
                <div class="alert alert-danger alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <ul class="mb-0">
                        <g:eachError bean="${liquidacionDeComplejoInstance}" var="error">
                            <li><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </div>
            </g:hasErrors>
            <g:render template="form"/>
        </div>
        <div class="card-footer">
            <g:actionSubmit class="btn btn-warning" action="update" value="Actualizar"/>
            <g:actionSubmit class="btn btn-danger ml-1" action="delete" value="Eliminar"
                formnovalidate=""
                onclick="return confirm('¿Está seguro de eliminar este registro?');"/>
            <g:link action="list" class="btn btn-secondary ml-1">Cancelar</g:link>
        </div>
    </g:form>
</div>
</body>
</html>
