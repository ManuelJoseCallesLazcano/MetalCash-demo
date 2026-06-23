
<%@ page import="org.socymet.caja.Subcuenta" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'subcuenta.label', default: 'Subcuenta')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-subcuenta" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-subcuenta" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list subcuenta">
			
				<g:if test="${subcuentaInstance?.cuenta}">
				<li class="fieldcontain">
					<span id="cuenta-label" class="property-label"><g:message code="subcuenta.cuenta.label" default="Cuenta" /></span>
					
						<span class="property-value" aria-labelledby="cuenta-label"><g:link controller="cuenta" action="show" id="${subcuentaInstance?.cuenta?.id}">${subcuentaInstance?.cuenta?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${subcuentaInstance?.codigoSubcuenta}">
				<li class="fieldcontain">
					<span id="codigoSubcuenta-label" class="property-label"><g:message code="subcuenta.codigoSubcuenta.label" default="Codigo Subcuenta" /></span>
					
						<span class="property-value" aria-labelledby="codigoSubcuenta-label"><g:fieldValue bean="${subcuentaInstance}" field="codigoSubcuenta"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${subcuentaInstance?.descripcion}">
				<li class="fieldcontain">
					<span id="descripcion-label" class="property-label"><g:message code="subcuenta.descripcion.label" default="Descripcion" /></span>
					
						<span class="property-value" aria-labelledby="descripcion-label"><g:fieldValue bean="${subcuentaInstance}" field="descripcion"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:subcuentaInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<sec:ifAnyGranted roles="ROLE_ADMIN">
						<g:link class="edit" action="edit" resource="${subcuentaInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
						<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					</sec:ifAnyGranted>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
