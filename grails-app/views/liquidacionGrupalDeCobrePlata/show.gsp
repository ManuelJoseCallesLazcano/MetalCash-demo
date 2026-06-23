
<%@ page import="org.socymet.liquidacion.LiquidacionGrupalDeCobrePlata" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'liquidacionGrupalDeCobrePlata.label', default: 'LiquidacionGrupalDeCobrePlata')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-liquidacionGrupalDeCobrePlata" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-liquidacionGrupalDeCobrePlata" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list liquidacionGrupalDeCobrePlata">
			
				<g:if test="${liquidacionGrupalDeCobrePlataInstance?.deposito}">
				<li class="fieldcontain">
					<span id="deposito-label" class="property-label"><g:message code="liquidacionGrupalDeCobrePlata.deposito.label" default="Deposito" /></span>
					
						<span class="property-value" aria-labelledby="deposito-label"><g:link controller="deposito" action="show" id="${liquidacionGrupalDeCobrePlataInstance?.deposito?.id}">${liquidacionGrupalDeCobrePlataInstance?.deposito?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionGrupalDeCobrePlataInstance?.loteInicial}">
				<li class="fieldcontain">
					<span id="loteInicial-label" class="property-label"><g:message code="liquidacionGrupalDeCobrePlata.loteInicial.label" default="Lote Inicial" /></span>
					
						<span class="property-value" aria-labelledby="loteInicial-label"><g:fieldValue bean="${liquidacionGrupalDeCobrePlataInstance}" field="loteInicial"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionGrupalDeCobrePlataInstance?.loteFinal}">
				<li class="fieldcontain">
					<span id="loteFinal-label" class="property-label"><g:message code="liquidacionGrupalDeCobrePlata.loteFinal.label" default="Lote Final" /></span>
					
						<span class="property-value" aria-labelledby="loteFinal-label"><g:fieldValue bean="${liquidacionGrupalDeCobrePlataInstance}" field="loteFinal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionGrupalDeCobrePlataInstance?.millis}">
				<li class="fieldcontain">
					<span id="millis-label" class="property-label"><g:message code="liquidacionGrupalDeCobrePlata.millis.label" default="Millis" /></span>
					
						<span class="property-value" aria-labelledby="millis-label"><g:fieldValue bean="${liquidacionGrupalDeCobrePlataInstance}" field="millis"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionGrupalDeCobrePlataInstance?.lotes}">
				<li class="fieldcontain">
					<span id="lotes-label" class="property-label"><g:message code="liquidacionGrupalDeCobrePlata.lotes.label" default="Lotes" /></span>
					
						<span class="property-value" aria-labelledby="lotes-label"><g:fieldValue bean="${liquidacionGrupalDeCobrePlataInstance}" field="lotes"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionGrupalDeCobrePlataInstance?.lotesLiquidados}">
				<li class="fieldcontain">
					<span id="lotesLiquidados-label" class="property-label"><g:message code="liquidacionGrupalDeCobrePlata.lotesLiquidados.label" default="Lotes Liquidados" /></span>
					
						<span class="property-value" aria-labelledby="lotesLiquidados-label"><g:fieldValue bean="${liquidacionGrupalDeCobrePlataInstance}" field="lotesLiquidados"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionGrupalDeCobrePlataInstance?.total}">
				<li class="fieldcontain">
					<span id="total-label" class="property-label"><g:message code="liquidacionGrupalDeCobrePlata.total.label" default="Total" /></span>
					
						<span class="property-value" aria-labelledby="total-label"><g:fieldValue bean="${liquidacionGrupalDeCobrePlataInstance}" field="total"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${liquidacionGrupalDeCobrePlataInstance?.id}" />
					<g:link class="edit" action="edit" id="${liquidacionGrupalDeCobrePlataInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
