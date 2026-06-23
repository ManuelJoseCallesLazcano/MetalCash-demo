<%@ page import="org.socymet.org.socymet.reportes.ReporteProduccionPorCliente" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reporteProduccionPorCliente.label', default: 'ReporteProduccionPorCliente')}" />
		<title><g:message code="default.report.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#create-reporteProduccionPorCliente" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="create-reporteProduccionPorCliente" class="content scaffold-create" role="main">
			<h1><g:message code="default.report.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${reporteProduccionPorClienteInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${reporteProduccionPorClienteInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form>
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
