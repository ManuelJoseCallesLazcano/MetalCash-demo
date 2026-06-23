<%@ page import="org.socymet.org.socymet.reportes.ReporteCompraGeneral" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reporteCompraGeneral.label', default: 'ReporteCompraGeneral')}" />
		<title><g:message code="default.report.label" args="[entityName]" /></title>
        <g:javascript src="jquery-1.10.1.min.js" />
        <g:javascript src="reportes/compraGeneral.js" />
	</head>
	<body>
		<a href="#create-reporteCompraGeneral" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="create-reporteCompraGeneral" class="content scaffold-create" role="main">
			<h1><g:message code="default.report.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${reporteCompraGeneralInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${reporteCompraGeneralInstance}" var="error">
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
