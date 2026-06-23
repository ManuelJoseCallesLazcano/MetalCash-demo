<%@ page import="org.socymet.liquidacion.LiquidacionDeAntimonio" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'liquidacionDeAntimonio.label', default: 'LiquidacionDeAntimonio')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
    <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}" type="text/css" >
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
    <g:javascript src="jquery-1.10.1.min.js" />
    <g:javascript src="i18n/grid.locale-es.js" />
    <g:javascript src="jquery.jqGrid.min.js" />
    <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
    <g:javascript src="jquery.jgrowl.min.js" />
    <g:javascript src="liquidacionDeAntimonio/recepcionAutocomplete.js" />
    <g:javascript src="liquidacionDeAntimonio/bonoCalidadAutocomplete.js" />
    <g:javascript src="liquidacionDeAntimonio/bonoIncentivoAutocomplete.js" />
    <g:javascript src="liquidacionDeAntimonio/calculosLiquidacion.js" />
    <script>
    $(document).ready(function() {
        $.jGrowl("Debe especificar el motivo por el que este registro esta siendo editado en el campo de texto al final del formulario.",
                {sticky: true, header: 'ATENCION'});
        $("#motivoDeModificacion").val("");
        $("#_modificacion").show();
    });
    </script>
</head>
<body>
<a href="#edit-liquidacionDeAntimonio" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="edit-liquidacionDeAntimonio" class="content scaffold-edit" role="main">
    <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${liquidacionDeAntimonioInstance}">
        <ul class="errors" role="alert">
            <g:eachError bean="${liquidacionDeAntimonioInstance}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form method="post" >
        <g:hiddenField name="id" value="${liquidacionDeAntimonioInstance?.id}" />
        <g:hiddenField name="version" value="${liquidacionDeAntimonioInstance?.version}" />
        <fieldset class="form">
            <g:render template="form"/>
        </fieldset>
        <fieldset class="buttons">
            <g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
            <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
        </fieldset>
    </g:form>
</div>
</body>
</html>
