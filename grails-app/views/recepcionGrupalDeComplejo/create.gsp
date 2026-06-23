<%@ page import="org.socymet.recepcion.RecepcionGrupalDeComplejo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'recepcionGrupalDeComplejo.label', default: 'RecepcionGrupalDeComplejo')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
        <link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}" type="text/css" >
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'ui.jqgrid.css')}" type="text/css" >
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="i18n/grid.locale-es.js" />
        <g:javascript src="jquery.jqGrid.min.js" />
        <g:javascript src="jquery-ui-1.10.3.custom.min.js" />
        <g:javascript src="jquery.jgrowl.min.js" />
        <g:javascript src="notify.min.js" />
        <g:javascript src="recepcionDeComplejo/clienteAutocomplete.js" />
        <g:javascript src="recepcionDeComplejo/choferAutocomplete.js" />
        <g:javascript src="recepcionDeComplejo/automovilAutocomplete.js" />
        <g:javascript src="recepcionDeComplejo/generadorTablaLotes.js" />
	</head>
	<body>
		<a href="#create-recepcionGrupalDeComplejo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="create-recepcionGrupalDeComplejo" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${recepcionGrupalDeComplejoInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${recepcionGrupalDeComplejoInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="save" >
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
