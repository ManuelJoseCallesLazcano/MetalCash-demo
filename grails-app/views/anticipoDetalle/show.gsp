
<%@ page import="org.socymet.anticipos.AnticipoDetalle" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'anticipoDetalle.label', default: 'AnticipoDetalle')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-anticipoDetalle" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-anticipoDetalle" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list anticipoDetalle">
			
				<g:if test="${anticipoDetalleInstance?.anticipo}">
				<li class="fieldcontain">
					<span id="anticipo-label" class="property-label"><g:message code="anticipoDetalle.anticipo.label" default="Anticipo" /></span>
					
						<span class="property-value" aria-labelledby="anticipo-label"><g:link controller="anticipo" action="show" id="${anticipoDetalleInstance?.anticipo?.id}">${anticipoDetalleInstance?.anticipo?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${anticipoDetalleInstance?.lote}">
				<li class="fieldcontain">
					<span id="lote-label" class="property-label"><g:message code="anticipoDetalle.lote.label" default="Lote" /></span>
					
						<span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${anticipoDetalleInstance}" field="lote"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${anticipoDetalleInstance?.recepcionId}">
				<li class="fieldcontain">
					<span id="recepcionId-label" class="property-label"><g:message code="anticipoDetalle.recepcionId.label" default="Recepcion Id" /></span>
					
						<span class="property-value" aria-labelledby="recepcionId-label"><g:fieldValue bean="${anticipoDetalleInstance}" field="recepcionId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${anticipoDetalleInstance?.nombreCliente}">
				<li class="fieldcontain">
					<span id="nombreCliente-label" class="property-label"><g:message code="anticipoDetalle.nombreCliente.label" default="Nombre Cliente" /></span>
					
						<span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${anticipoDetalleInstance}" field="nombreCliente"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${anticipoDetalleInstance?.nombreEmpresa}">
				<li class="fieldcontain">
					<span id="nombreEmpresa-label" class="property-label"><g:message code="anticipoDetalle.nombreEmpresa.label" default="Nombre Empresa" /></span>
					
						<span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${anticipoDetalleInstance}" field="nombreEmpresa"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${anticipoDetalleInstance?.fechaDeRecepcion}">
				<li class="fieldcontain">
					<span id="fechaDeRecepcion-label" class="property-label"><g:message code="anticipoDetalle.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${anticipoDetalleInstance}" field="fechaDeRecepcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${anticipoDetalleInstance?.pesoBruto}">
				<li class="fieldcontain">
					<span id="pesoBruto-label" class="property-label"><g:message code="anticipoDetalle.pesoBruto.label" default="Peso Bruto" /></span>
					
						<span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${anticipoDetalleInstance}" field="pesoBruto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${anticipoDetalleInstance?.estadoAnticipo}">
				<li class="fieldcontain">
					<span id="estadoAnticipo-label" class="property-label"><g:message code="anticipoDetalle.estadoAnticipo.label" default="Estado Anticipo" /></span>
					
						<span class="property-value" aria-labelledby="estadoAnticipo-label"><g:fieldValue bean="${anticipoDetalleInstance}" field="estadoAnticipo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${anticipoDetalleInstance?.anticipoPagable}">
				<li class="fieldcontain">
					<span id="anticipoPagable-label" class="property-label"><g:message code="anticipoDetalle.anticipoPagable.label" default="Anticipo Pagable" /></span>
					
						<span class="property-value" aria-labelledby="anticipoPagable-label"><g:fieldValue bean="${anticipoDetalleInstance}" field="anticipoPagable"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${anticipoDetalleInstance?.id}" />
					<g:link class="edit" action="edit" id="${anticipoDetalleInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
