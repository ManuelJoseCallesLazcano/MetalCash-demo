<%@ page import="org.socymet.liquidacion.LiquidacionDeComplejo" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Nueva Liquidación de Complejo</title>
    <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'styleScrolling.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'chosen.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'print.min.css')}" type="text/css" >
    <g:javascript src="jquery-1.10.1.min.js" />
    <g:javascript src="i18n/grid.locale-es.js" />
    <g:javascript src="jquery.jqGrid.min.js" />
    <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
    <g:javascript src="jquery.jgrowl.min.js" />
    <g:javascript src="notify.min.js" />
    <g:javascript src="scrolling.js" />
    <g:javascript src="chosen.jquery.js" />
    <g:javascript src="jquery.alphanum.js" />
    <g:javascript src="print.min.js" />
    <g:javascript src="liquidacionDeComplejo/recepcionAutocomplete.js" />
    <g:javascript src="liquidacionDeComplejo/bonoCalidadAutocomplete.js" />
    <g:javascript src="liquidacionDeComplejo/bonoIncentivoAutocomplete.js" />
    <script>
        $(document).ready(function() {
            $("#vista").val("create");
            $("#motivoDeModificacion").val("-");
        });
    </script>
</head>
<body>
<div class="card card-primary">
    <div class="card-header">
        <h3 class="card-title">Nueva Liquidación de Complejo</h3>
    </div>
    <g:form action="save">
        <fieldset id="formulario">
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
                <g:submitButton name="create" class="btn btn-primary" value="Guardar"/>
                <g:link action="list" class="btn btn-secondary ml-1">Cancelar</g:link>
            </div>
        </fieldset>
    </g:form>
</div>
</body>
</html>
