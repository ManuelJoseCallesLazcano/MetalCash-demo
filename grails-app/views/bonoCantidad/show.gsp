
<%@ page import="org.socymet.proveedor.bonos.BonoCantidad" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'bonoCantidad.label', default: 'BonoCantidad')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-bonoCantidad" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-bonoCantidad" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list bonoCantidad">
			
				<g:if test="${bonoCantidadInstance?.empresa}">
				<li class="fieldcontain">
					<span id="empresa-label" class="property-label"><g:message code="bonoCantidad.empresa.label" default="Empresa" /></span>
					
						<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${bonoCantidadInstance?.empresa?.id}">${bonoCantidadInstance?.empresa?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoCantidadInstance?.elemento}">
				<li class="fieldcontain">
					<span id="elemento-label" class="property-label"><g:message code="bonoCantidad.elemento.label" default="Elemento" /></span>
					
						<span class="property-value" aria-labelledby="elemento-label"><g:fieldValue bean="${bonoCantidadInstance}" field="elemento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoCantidadInstance?.simboloElemento}">
				<li class="fieldcontain">
					<span id="simboloElemento-label" class="property-label"><g:message code="bonoCantidad.simboloElemento.label" default="Simbolo Elemento" /></span>
					
						<span class="property-value" aria-labelledby="simboloElemento-label"><g:fieldValue bean="${bonoCantidadInstance}" field="simboloElemento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoCantidadInstance?.cantidadMinima}">
				<li class="fieldcontain">
					<span id="cantidadMinima-label" class="property-label"><g:message code="bonoCantidad.cantidadMinima.label" default="Cantidad Minima" /></span>
					
						<span class="property-value" aria-labelledby="cantidadMinima-label"><g:fieldValue bean="${bonoCantidadInstance}" field="cantidadMinima"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoCantidadInstance?.cantidadMaxima}">
				<li class="fieldcontain">
					<span id="cantidadMaxima-label" class="property-label"><g:message code="bonoCantidad.cantidadMaxima.label" default="Cantidad Maxima" /></span>
					
						<span class="property-value" aria-labelledby="cantidadMaxima-label"><g:fieldValue bean="${bonoCantidadInstance}" field="cantidadMaxima"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoCantidadInstance?.bono}">
				<li class="fieldcontain">
					<span id="bono-label" class="property-label"><g:message code="bonoCantidad.bono.label" default="Bono" /></span>
					
						<span class="property-value" aria-labelledby="bono-label"><g:fieldValue bean="${bonoCantidadInstance}" field="bono"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${bonoCantidadInstance?.id}" />
					<g:link class="edit" action="edit" id="${bonoCantidadInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
