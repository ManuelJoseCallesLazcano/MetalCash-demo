
<%@ page import="org.socymet.liquidacion.LiquidacionGrupalDePlomoPlataDetalle" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'liquidacionGrupalDePlomoPlataDetalle.label', default: 'LiquidacionGrupalDePlomoPlataDetalle')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-liquidacionGrupalDePlomoPlataDetalle" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-liquidacionGrupalDePlomoPlataDetalle" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list liquidacionGrupalDePlomoPlataDetalle">
			
				<g:if test="${liquidacionGrupalDePlomoPlataDetalleInstance?.millis}">
				<li class="fieldcontain">
					<span id="millis-label" class="property-label"><g:message code="liquidacionGrupalDePlomoPlataDetalle.millis.label" default="Millis" /></span>
					
						<span class="property-value" aria-labelledby="millis-label"><g:fieldValue bean="${liquidacionGrupalDePlomoPlataDetalleInstance}" field="millis"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionGrupalDePlomoPlataDetalleInstance?.liquidacionDePlomoPlata}">
				<li class="fieldcontain">
					<span id="liquidacionDePlomoPlata-label" class="property-label"><g:message code="liquidacionGrupalDePlomoPlataDetalle.liquidacionDePlomoPlata.label" default="Liquidacion De Plomo Plata" /></span>
					
						<span class="property-value" aria-labelledby="liquidacionDePlomoPlata-label"><g:link controller="liquidacionDePlomoPlata" action="show" id="${liquidacionGrupalDePlomoPlataDetalleInstance?.liquidacionDePlomoPlata?.id}">${liquidacionGrupalDePlomoPlataDetalleInstance?.liquidacionDePlomoPlata?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${liquidacionGrupalDePlomoPlataDetalleInstance?.id}" />
					<g:link class="edit" action="edit" id="${liquidacionGrupalDePlomoPlataDetalleInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
