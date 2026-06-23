
<%@ page import="org.socymet.proveedor.bonos.Bono" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'bono.label', default: 'Bono')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-bono" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-bono" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list bono">
			
				<g:if test="${bonoInstance?.bono}">
				<li class="fieldcontain">
					<span id="bono-label" class="property-label"><g:message code="bono.bono.label" default="Bono" /></span>
					
						<span class="property-value" aria-labelledby="bono-label"><g:fieldValue bean="${bonoInstance}" field="bono"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoInstance?.elemento}">
				<li class="fieldcontain">
					<span id="elemento-label" class="property-label"><g:message code="bono.elemento.label" default="Elemento" /></span>
					
						<span class="property-value" aria-labelledby="elemento-label"><g:fieldValue bean="${bonoInstance}" field="elemento"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${bonoInstance?.id}" />
					<g:link class="edit" action="edit" id="${bonoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
