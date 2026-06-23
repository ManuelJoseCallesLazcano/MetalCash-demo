<%@ page import="org.socymet.recepcion.RecepcionDeOro" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'recepcionDeOro.label', default: 'RecepcionDeOro')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
		<link rel="stylesheet" href="${resource(dir: 'css/ui-lightness', file: 'jquery-ui-1.10.3.custom.css')}" type="text/css" >
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jgrowl.css')}" type="text/css" >
		<g:javascript src="jquery-1.10.1.min.js" />
		<g:javascript src="jquery-ui-1.10.3.custom.min.js" />
		<g:javascript src="jquery.jgrowl.min.js" />
		<g:javascript src="recepcionDeOro/clienteAutocomplete.js" />
		<g:javascript src="recepcionDeOro/choferAutocomplete.js" />
		<g:javascript src="recepcionDeOro/automovilAutocomplete.js" />
		<g:javascript src="recepcionDeOro/calculosRecepcion.js" />
		<g:javascript src="calculos/calculoCostoTransporte.js" />
		<script>
            $(document).ready(function() {
                $("#_cotizaciones").show();
                $("#_deposito").hide();
                $("#radio").hide();
            });
		</script>
	</head>
	<body>
		<a href="#edit-recepcionDeOro" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="edit-recepcionDeOro" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${recepcionDeOroInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${recepcionDeOroInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form url="[resource:recepcionDeOroInstance, action:'update']" method="PUT" >
				<g:hiddenField name="version" value="${recepcionDeOroInstance?.version}" />
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
					<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
