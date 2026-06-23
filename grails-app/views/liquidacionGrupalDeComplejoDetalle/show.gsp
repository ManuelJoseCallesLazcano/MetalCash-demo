
<%@ page import="org.socymet.liquidacion.LiquidacionGrupalDeComplejoDetalle" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'liquidacionGrupalDeComplejoDetalle.label', default: 'LiquidacionGrupalDeComplejoDetalle')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-liquidacionGrupalDeComplejoDetalle" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-liquidacionGrupalDeComplejoDetalle" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list liquidacionGrupalDeComplejoDetalle">
			
				<g:if test="${liquidacionGrupalDeComplejoDetalleInstance?.millis}">
				<li class="fieldcontain">
					<span id="millis-label" class="property-label"><g:message code="liquidacionGrupalDeComplejoDetalle.millis.label" default="Millis" /></span>
					
						<span class="property-value" aria-labelledby="millis-label"><g:fieldValue bean="${liquidacionGrupalDeComplejoDetalleInstance}" field="millis"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionGrupalDeComplejoDetalleInstance?.liquidacionDeComplejo}">
				<li class="fieldcontain">
					<span id="liquidacionDeComplejo-label" class="property-label"><g:message code="liquidacionGrupalDeComplejoDetalle.liquidacionDeComplejo.label" default="Liquidacion De Complejo" /></span>
					
						<span class="property-value" aria-labelledby="liquidacionDeComplejo-label"><g:link controller="liquidacionDeComplejo" action="show" id="${liquidacionGrupalDeComplejoDetalleInstance?.liquidacionDeComplejo?.id}">${liquidacionGrupalDeComplejoDetalleInstance?.liquidacionDeComplejo?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${liquidacionGrupalDeComplejoDetalleInstance?.id}" />
					<g:link class="edit" action="edit" id="${liquidacionGrupalDeComplejoDetalleInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
