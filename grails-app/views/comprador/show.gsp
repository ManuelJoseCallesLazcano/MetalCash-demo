
<%@ page import="org.smart.compositos.Comprador" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'comprador.label', default: 'Comprador')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-comprador" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-comprador" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list comprador">
			
				<g:if test="${compradorInstance?.nombreComprador}">
				<li class="fieldcontain">
					<span id="nombreComprador-label" class="property-label"><g:message code="comprador.nombreComprador.label" default="Nombre Comprador" /></span>
					
						<span class="property-value" aria-labelledby="nombreComprador-label"><g:fieldValue bean="${compradorInstance}" field="nombreComprador"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${compradorInstance?.nombreContacto}">
				<li class="fieldcontain">
					<span id="nombreContacto-label" class="property-label"><g:message code="comprador.nombreContacto.label" default="Nombre Contacto" /></span>
					
						<span class="property-value" aria-labelledby="nombreContacto-label"><g:fieldValue bean="${compradorInstance}" field="nombreContacto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${compradorInstance?.telefono}">
				<li class="fieldcontain">
					<span id="telefono-label" class="property-label"><g:message code="comprador.telefono.label" default="Telefono" /></span>
					
						<span class="property-value" aria-labelledby="telefono-label"><g:fieldValue bean="${compradorInstance}" field="telefono"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${compradorInstance?.email}">
				<li class="fieldcontain">
					<span id="email-label" class="property-label"><g:message code="comprador.email.label" default="Email" /></span>
					
						<span class="property-value" aria-labelledby="email-label"><g:fieldValue bean="${compradorInstance}" field="email"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:compradorInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${compradorInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
