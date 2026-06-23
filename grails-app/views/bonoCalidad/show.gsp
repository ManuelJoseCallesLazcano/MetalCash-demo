
<%@ page import="org.socymet.proveedor.bonos.BonoCalidad" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'bonoCalidad.label', default: 'BonoCalidad')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-bonoCalidad" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-bonoCalidad" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list bonoCalidad">
			
				<g:if test="${bonoCalidadInstance?.empresa}">
				<li class="fieldcontain">
					<span id="empresa-label" class="property-label"><g:message code="bonoCalidad.empresa.label" default="Empresa" /></span>
					
						<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${bonoCalidadInstance?.empresa?.id}">${bonoCalidadInstance?.empresa?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoCalidadInstance?.elemento}">
				<li class="fieldcontain">
					<span id="elemento-label" class="property-label"><g:message code="bonoCalidad.elemento.label" default="Elemento" /></span>
					
						<span class="property-value" aria-labelledby="elemento-label"><g:fieldValue bean="${bonoCalidadInstance}" field="elemento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoCalidadInstance?.simboloElemento}">
				<li class="fieldcontain">
					<span id="simboloElemento-label" class="property-label"><g:message code="bonoCalidad.simboloElemento.label" default="Simbolo Elemento" /></span>
					
						<span class="property-value" aria-labelledby="simboloElemento-label"><g:fieldValue bean="${bonoCalidadInstance}" field="simboloElemento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoCalidadInstance?.leyMinima}">
				<li class="fieldcontain">
					<span id="leyMinima-label" class="property-label"><g:message code="bonoCalidad.leyMinima.label" default="Ley Minima" /></span>
					
						<span class="property-value" aria-labelledby="leyMinima-label"><g:fieldValue bean="${bonoCalidadInstance}" field="leyMinima"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoCalidadInstance?.leyMaxima}">
				<li class="fieldcontain">
					<span id="leyMaxima-label" class="property-label"><g:message code="bonoCalidad.leyMaxima.label" default="Ley Maxima" /></span>
					
						<span class="property-value" aria-labelledby="leyMaxima-label"><g:fieldValue bean="${bonoCalidadInstance}" field="leyMaxima"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoCalidadInstance?.bono}">
				<li class="fieldcontain">
					<span id="bono-label" class="property-label"><g:message code="bonoCalidad.bono.label" default="Bono" /></span>
					
						<span class="property-value" aria-labelledby="bono-label"><g:fieldValue bean="${bonoCalidadInstance}" field="bono"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${bonoCalidadInstance?.id}" />
					<g:link class="edit" action="edit" id="${bonoCalidadInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
