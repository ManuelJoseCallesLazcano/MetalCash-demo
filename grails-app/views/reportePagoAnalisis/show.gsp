
<%@ page import="org.socymet.org.socymet.reportes.ReportePagoAnalisis" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reportePagoAnalisis.label', default: 'ReportePagoAnalisis')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-reportePagoAnalisis" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-reportePagoAnalisis" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list reportePagoAnalisis">
			
				<g:if test="${reportePagoAnalisisInstance?.nombreDeLaboratorio}">
				<li class="fieldcontain">
					<span id="nombreDeLaboratorio-label" class="property-label"><g:message code="reportePagoAnalisis.nombreDeLaboratorio.label" default="Nombre De Laboratorio" /></span>
					
						<span class="property-value" aria-labelledby="nombreDeLaboratorio-label"><g:fieldValue bean="${reportePagoAnalisisInstance}" field="nombreDeLaboratorio"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reportePagoAnalisisInstance?.fechaInicial}">
				<li class="fieldcontain">
					<span id="fechaInicial-label" class="property-label"><g:message code="reportePagoAnalisis.fechaInicial.label" default="Fecha Inicial" /></span>
					
						<span class="property-value" aria-labelledby="fechaInicial-label"><g:formatDate date="${reportePagoAnalisisInstance?.fechaInicial}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${reportePagoAnalisisInstance?.fechaFinal}">
				<li class="fieldcontain">
					<span id="fechaFinal-label" class="property-label"><g:message code="reportePagoAnalisis.fechaFinal.label" default="Fecha Final" /></span>
					
						<span class="property-value" aria-labelledby="fechaFinal-label"><g:formatDate date="${reportePagoAnalisisInstance?.fechaFinal}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${reportePagoAnalisisInstance?.id}" />
					<g:link class="edit" action="edit" id="${reportePagoAnalisisInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
