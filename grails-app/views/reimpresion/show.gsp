
<%@ page import="org.socymet.org.socymet.reportes.Reimpresion" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'reimpresion.label', default: 'Reimpresion')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-reimpresion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-reimpresion" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list reimpresion">
			
				<g:if test="${reimpresionInstance?.fecha}">
				<li class="fieldcontain">
					<span id="fecha-label" class="property-label"><g:message code="reimpresion.fecha.label" default="Fecha" /></span>
					
						<span class="property-value" aria-labelledby="fecha-label"><g:formatDate date="${reimpresionInstance?.fecha}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${reimpresionInstance?.nombreReporte}">
				<li class="fieldcontain">
					<span id="nombreReporte-label" class="property-label"><g:message code="reimpresion.nombreReporte.label" default="Nombre Reporte" /></span>
					
						<span class="property-value" aria-labelledby="nombreReporte-label"><g:fieldValue bean="${reimpresionInstance}" field="nombreReporte"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reimpresionInstance?.identificadorDocumento}">
				<li class="fieldcontain">
					<span id="identificadorDocumento-label" class="property-label"><g:message code="reimpresion.identificadorDocumento.label" default="Identificador Documento" /></span>
					
						<span class="property-value" aria-labelledby="identificadorDocumento-label"><g:fieldValue bean="${reimpresionInstance}" field="identificadorDocumento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reimpresionInstance?.lote}">
				<li class="fieldcontain">
					<span id="lote-label" class="property-label"><g:message code="reimpresion.lote.label" default="Lote" /></span>
					
						<span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${reimpresionInstance}" field="lote"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reimpresionInstance?.motivo}">
				<li class="fieldcontain">
					<span id="motivo-label" class="property-label"><g:message code="reimpresion.motivo.label" default="Motivo" /></span>
					
						<span class="property-value" aria-labelledby="motivo-label"><g:fieldValue bean="${reimpresionInstance}" field="motivo"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${reimpresionInstance?.id}" />
					<g:link class="edit" action="edit" id="${reimpresionInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
