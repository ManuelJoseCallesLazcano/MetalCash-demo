
<%@ page import="org.socymet.seguridad.MiCuenta" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'miCuenta.label', default: 'MiCuenta')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-miCuenta" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-miCuenta" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list miCuenta">
			
				<g:if test="${miCuentaInstance?.nombre}">
				<li class="fieldcontain">
					<span id="nombre-label" class="property-label"><g:message code="miCuenta.nombre.label" default="Nombre" /></span>
					
						<span class="property-value" aria-labelledby="nombre-label"><g:fieldValue bean="${miCuentaInstance}" field="nombre"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${miCuentaInstance?.cuenta}">
				<li class="fieldcontain">
					<span id="cuenta-label" class="property-label"><g:message code="miCuenta.cuenta.label" default="Cuenta" /></span>
					
						<span class="property-value" aria-labelledby="cuenta-label"><g:fieldValue bean="${miCuentaInstance}" field="cuenta"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${miCuentaInstance?.contrasena}">
				<li class="fieldcontain">
					<span id="contrasena-label" class="property-label"><g:message code="miCuenta.contrasena.label" default="Contrasena" /></span>
					
						<span class="property-value" aria-labelledby="contrasena-label"><g:fieldValue bean="${miCuentaInstance}" field="contrasena"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${miCuentaInstance?.confirmarContrasena}">
				<li class="fieldcontain">
					<span id="confirmarContrasena-label" class="property-label"><g:message code="miCuenta.confirmarContrasena.label" default="Confirmar Contrasena" /></span>
					
						<span class="property-value" aria-labelledby="confirmarContrasena-label"><g:fieldValue bean="${miCuentaInstance}" field="confirmarContrasena"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${miCuentaInstance?.id}" />
					<g:link class="edit" action="edit" id="${miCuentaInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
