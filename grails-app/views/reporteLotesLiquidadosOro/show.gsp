
<%@ page import="org.socymet.org.socymet.reportes.ReporteLotesLiquidadosOro" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reporteLotesLiquidadosOro.label', default: 'ReporteLotesLiquidadosOro')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-reporteLotesLiquidadosOro" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-reporteLotesLiquidadosOro" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list reporteLotesLiquidadosOro">
			
				<g:if test="${reporteLotesLiquidadosOroInstance?.empresa}">
				<li class="fieldcontain">
					<span id="empresa-label" class="property-label"><g:message code="reporteLotesLiquidadosOro.empresa.label" default="Empresa" /></span>
					
						<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${reporteLotesLiquidadosOroInstance?.empresa?.id}">${reporteLotesLiquidadosOroInstance?.empresa?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteLotesLiquidadosOroInstance?.deposito}">
				<li class="fieldcontain">
					<span id="deposito-label" class="property-label"><g:message code="reporteLotesLiquidadosOro.deposito.label" default="Deposito" /></span>
					
						<span class="property-value" aria-labelledby="deposito-label"><g:link controller="deposito" action="show" id="${reporteLotesLiquidadosOroInstance?.deposito?.id}">${reporteLotesLiquidadosOroInstance?.deposito?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteLotesLiquidadosOroInstance?.elemento}">
				<li class="fieldcontain">
					<span id="elemento-label" class="property-label"><g:message code="reporteLotesLiquidadosOro.elemento.label" default="Elemento" /></span>
					
						<span class="property-value" aria-labelledby="elemento-label"><g:fieldValue bean="${reporteLotesLiquidadosOroInstance}" field="elemento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteLotesLiquidadosOroInstance?.fechaInicial}">
				<li class="fieldcontain">
					<span id="fechaInicial-label" class="property-label"><g:message code="reporteLotesLiquidadosOro.fechaInicial.label" default="Fecha Inicial" /></span>
					
						<span class="property-value" aria-labelledby="fechaInicial-label"><g:formatDate date="${reporteLotesLiquidadosOroInstance?.fechaInicial}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${reporteLotesLiquidadosOroInstance?.fechaFinal}">
				<li class="fieldcontain">
					<span id="fechaFinal-label" class="property-label"><g:message code="reporteLotesLiquidadosOro.fechaFinal.label" default="Fecha Final" /></span>
					
						<span class="property-value" aria-labelledby="fechaFinal-label"><g:formatDate date="${reporteLotesLiquidadosOroInstance?.fechaFinal}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:reporteLotesLiquidadosOroInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${reporteLotesLiquidadosOroInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
