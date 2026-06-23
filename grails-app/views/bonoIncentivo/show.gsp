
<%@ page import="org.socymet.proveedor.bonos.BonoIncentivo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'bonoIncentivo.label', default: 'BonoIncentivo')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-bonoIncentivo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-bonoIncentivo" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list bonoIncentivo">
			
				<g:if test="${bonoIncentivoInstance?.empresa}">
				<li class="fieldcontain">
					<span id="empresa-label" class="property-label"><g:message code="bonoIncentivo.empresa.label" default="Empresa" /></span>
					
						<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${bonoIncentivoInstance?.empresa?.id}">${bonoIncentivoInstance?.empresa?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoIncentivoInstance?.elemento}">
				<li class="fieldcontain">
					<span id="elemento-label" class="property-label"><g:message code="bonoIncentivo.elemento.label" default="Elemento" /></span>
					
						<span class="property-value" aria-labelledby="elemento-label"><g:fieldValue bean="${bonoIncentivoInstance}" field="elemento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoIncentivoInstance?.simboloElemento}">
				<li class="fieldcontain">
					<span id="simboloElemento-label" class="property-label"><g:message code="bonoIncentivo.simboloElemento.label" default="Simbolo Elemento" /></span>
					
						<span class="property-value" aria-labelledby="simboloElemento-label"><g:fieldValue bean="${bonoIncentivoInstance}" field="simboloElemento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoIncentivoInstance?.leyMinima}">
				<li class="fieldcontain">
					<span id="leyMinima-label" class="property-label"><g:message code="bonoIncentivo.leyMinima.label" default="Ley Minima" /></span>
					
						<span class="property-value" aria-labelledby="leyMinima-label"><g:fieldValue bean="${bonoIncentivoInstance}" field="leyMinima"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoIncentivoInstance?.leyMaxima}">
				<li class="fieldcontain">
					<span id="leyMaxima-label" class="property-label"><g:message code="bonoIncentivo.leyMaxima.label" default="Ley Maxima" /></span>
					
						<span class="property-value" aria-labelledby="leyMaxima-label"><g:fieldValue bean="${bonoIncentivoInstance}" field="leyMaxima"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoIncentivoInstance?.cantidadMinima}">
				<li class="fieldcontain">
					<span id="cantidadMinima-label" class="property-label"><g:message code="bonoIncentivo.cantidadMinima.label" default="Cantidad Minima" /></span>
					
						<span class="property-value" aria-labelledby="cantidadMinima-label"><g:fieldValue bean="${bonoIncentivoInstance}" field="cantidadMinima"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoIncentivoInstance?.cantidadMaxima}">
				<li class="fieldcontain">
					<span id="cantidadMaxima-label" class="property-label"><g:message code="bonoIncentivo.cantidadMaxima.label" default="Cantidad Maxima" /></span>
					
						<span class="property-value" aria-labelledby="cantidadMaxima-label"><g:fieldValue bean="${bonoIncentivoInstance}" field="cantidadMaxima"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bonoIncentivoInstance?.bono}">
				<li class="fieldcontain">
					<span id="bono-label" class="property-label"><g:message code="bonoIncentivo.bono.label" default="Bono" /></span>
					
						<span class="property-value" aria-labelledby="bono-label"><g:fieldValue bean="${bonoIncentivoInstance}" field="bono"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${bonoIncentivoInstance?.id}" />
					<g:link class="edit" action="edit" id="${bonoIncentivoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
