
<%@ page import="org.socymet.liquidacion.LiquidacionGrupalDeZincPlata" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'liquidacionGrupalDeZincPlata.label', default: 'LiquidacionGrupalDeZincPlata')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-liquidacionGrupalDeZincPlata" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-liquidacionGrupalDeZincPlata" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list liquidacionGrupalDeZincPlata">
			
				<g:if test="${liquidacionGrupalDeZincPlataInstance?.deposito}">
				<li class="fieldcontain">
					<span id="deposito-label" class="property-label"><g:message code="liquidacionGrupalDeZincPlata.deposito.label" default="Deposito" /></span>
					
						<span class="property-value" aria-labelledby="deposito-label"><g:link controller="deposito" action="show" id="${liquidacionGrupalDeZincPlataInstance?.deposito?.id}">${liquidacionGrupalDeZincPlataInstance?.deposito?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionGrupalDeZincPlataInstance?.loteInicial}">
				<li class="fieldcontain">
					<span id="loteInicial-label" class="property-label"><g:message code="liquidacionGrupalDeZincPlata.loteInicial.label" default="Lote Inicial" /></span>
					
						<span class="property-value" aria-labelledby="loteInicial-label"><g:fieldValue bean="${liquidacionGrupalDeZincPlataInstance}" field="loteInicial"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionGrupalDeZincPlataInstance?.loteFinal}">
				<li class="fieldcontain">
					<span id="loteFinal-label" class="property-label"><g:message code="liquidacionGrupalDeZincPlata.loteFinal.label" default="Lote Final" /></span>
					
						<span class="property-value" aria-labelledby="loteFinal-label"><g:fieldValue bean="${liquidacionGrupalDeZincPlataInstance}" field="loteFinal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionGrupalDeZincPlataInstance?.millis}">
				<li class="fieldcontain">
					<span id="millis-label" class="property-label"><g:message code="liquidacionGrupalDeZincPlata.millis.label" default="Millis" /></span>
					
						<span class="property-value" aria-labelledby="millis-label"><g:fieldValue bean="${liquidacionGrupalDeZincPlataInstance}" field="millis"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionGrupalDeZincPlataInstance?.lotes}">
				<li class="fieldcontain">
					<span id="lotes-label" class="property-label"><g:message code="liquidacionGrupalDeZincPlata.lotes.label" default="Lotes" /></span>
					
						<span class="property-value" aria-labelledby="lotes-label"><g:fieldValue bean="${liquidacionGrupalDeZincPlataInstance}" field="lotes"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionGrupalDeZincPlataInstance?.lotesLiquidados}">
				<li class="fieldcontain">
					<span id="lotesLiquidados-label" class="property-label"><g:message code="liquidacionGrupalDeZincPlata.lotesLiquidados.label" default="Lotes Liquidados" /></span>
					
						<span class="property-value" aria-labelledby="lotesLiquidados-label"><g:fieldValue bean="${liquidacionGrupalDeZincPlataInstance}" field="lotesLiquidados"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionGrupalDeZincPlataInstance?.total}">
				<li class="fieldcontain">
					<span id="total-label" class="property-label"><g:message code="liquidacionGrupalDeZincPlata.total.label" default="Total" /></span>
					
						<span class="property-value" aria-labelledby="total-label"><g:fieldValue bean="${liquidacionGrupalDeZincPlataInstance}" field="total"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${liquidacionGrupalDeZincPlataInstance?.id}" />
					<g:link class="edit" action="edit" id="${liquidacionGrupalDeZincPlataInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
