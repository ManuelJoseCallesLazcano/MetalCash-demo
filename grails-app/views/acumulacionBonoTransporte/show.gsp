
<%@ page import="org.socymet.cancelacion.AcumulacionBonoTransporte" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'acumulacionBonoTransporte.label', default: 'AcumulacionBonoTransporte')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-acumulacionBonoTransporte" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-acumulacionBonoTransporte" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list acumulacionBonoTransporte">
			
				<g:if test="${acumulacionBonoTransporteInstance?.pagoBonoTransporte}">
				<li class="fieldcontain">
					<span id="pagoBonoTransporte-label" class="property-label"><g:message code="acumulacionBonoTransporte.pagoBonoTransporte.label" default="Pago Bono Transporte" /></span>
					
						<span class="property-value" aria-labelledby="pagoBonoTransporte-label"><g:link controller="pagoBonoTransporte" action="show" id="${acumulacionBonoTransporteInstance?.pagoBonoTransporte?.id}">${acumulacionBonoTransporteInstance?.pagoBonoTransporte?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${acumulacionBonoTransporteInstance?.fecha}">
				<li class="fieldcontain">
					<span id="fecha-label" class="property-label"><g:message code="acumulacionBonoTransporte.fecha.label" default="Fecha" /></span>
					
						<span class="property-value" aria-labelledby="fecha-label"><g:formatDate date="${acumulacionBonoTransporteInstance?.fecha}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${acumulacionBonoTransporteInstance?.automovil}">
				<li class="fieldcontain">
					<span id="automovil-label" class="property-label"><g:message code="acumulacionBonoTransporte.automovil.label" default="Automovil" /></span>
					
						<span class="property-value" aria-labelledby="automovil-label"><g:link controller="automovil" action="show" id="${acumulacionBonoTransporteInstance?.automovil?.id}">${acumulacionBonoTransporteInstance?.automovil?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${acumulacionBonoTransporteInstance?.cantidadAcumulada}">
				<li class="fieldcontain">
					<span id="cantidadAcumulada-label" class="property-label"><g:message code="acumulacionBonoTransporte.cantidadAcumulada.label" default="Cantidad Acumulada" /></span>
					
						<span class="property-value" aria-labelledby="cantidadAcumulada-label"><g:fieldValue bean="${acumulacionBonoTransporteInstance}" field="cantidadAcumulada"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${acumulacionBonoTransporteInstance?.id}" />
					<g:link class="edit" action="edit" id="${acumulacionBonoTransporteInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
