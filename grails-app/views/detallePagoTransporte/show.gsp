
<%@ page import="org.socymet.cancelacion.DetallePagoTransporte" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'detallePagoTransporte.label', default: 'DetallePagoTransporte')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-detallePagoTransporte" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-detallePagoTransporte" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list detallePagoTransporte">
			
				<g:if test="${detallePagoTransporteInstance?.pagoTransporte}">
				<li class="fieldcontain">
					<span id="pagoTransporte-label" class="property-label"><g:message code="detallePagoTransporte.pagoTransporte.label" default="Pago Transporte" /></span>
					
						<span class="property-value" aria-labelledby="pagoTransporte-label"><g:link controller="pagoTransporte" action="show" id="${detallePagoTransporteInstance?.pagoTransporte?.id}">${detallePagoTransporteInstance?.pagoTransporte?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${detallePagoTransporteInstance?.lote}">
				<li class="fieldcontain">
					<span id="lote-label" class="property-label"><g:message code="detallePagoTransporte.lote.label" default="Lote" /></span>
					
						<span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${detallePagoTransporteInstance}" field="lote"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${detallePagoTransporteInstance?.recepcionId}">
				<li class="fieldcontain">
					<span id="recepcionId-label" class="property-label"><g:message code="detallePagoTransporte.recepcionId.label" default="Recepcion Id" /></span>
					
						<span class="property-value" aria-labelledby="recepcionId-label"><g:fieldValue bean="${detallePagoTransporteInstance}" field="recepcionId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${detallePagoTransporteInstance?.nombreChofer}">
				<li class="fieldcontain">
					<span id="nombreChofer-label" class="property-label"><g:message code="detallePagoTransporte.nombreChofer.label" default="Nombre Chofer" /></span>
					
						<span class="property-value" aria-labelledby="nombreChofer-label"><g:fieldValue bean="${detallePagoTransporteInstance}" field="nombreChofer"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${detallePagoTransporteInstance?.placaAutomovil}">
				<li class="fieldcontain">
					<span id="placaAutomovil-label" class="property-label"><g:message code="detallePagoTransporte.placaAutomovil.label" default="Placa Automovil" /></span>
					
						<span class="property-value" aria-labelledby="placaAutomovil-label"><g:fieldValue bean="${detallePagoTransporteInstance}" field="placaAutomovil"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${detallePagoTransporteInstance?.fechaDeRecepcion}">
				<li class="fieldcontain">
					<span id="fechaDeRecepcion-label" class="property-label"><g:message code="detallePagoTransporte.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${detallePagoTransporteInstance}" field="fechaDeRecepcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${detallePagoTransporteInstance?.pesoBruto}">
				<li class="fieldcontain">
					<span id="pesoBruto-label" class="property-label"><g:message code="detallePagoTransporte.pesoBruto.label" default="Peso Bruto" /></span>
					
						<span class="property-value" aria-labelledby="pesoBruto-label"><g:fieldValue bean="${detallePagoTransporteInstance}" field="pesoBruto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${detallePagoTransporteInstance?.tipoDeMaterial}">
				<li class="fieldcontain">
					<span id="tipoDeMaterial-label" class="property-label"><g:message code="detallePagoTransporte.tipoDeMaterial.label" default="Tipo De Material" /></span>
					
						<span class="property-value" aria-labelledby="tipoDeMaterial-label"><g:fieldValue bean="${detallePagoTransporteInstance}" field="tipoDeMaterial"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${detallePagoTransporteInstance?.costoDeTransporte}">
				<li class="fieldcontain">
					<span id="costoDeTransporte-label" class="property-label"><g:message code="detallePagoTransporte.costoDeTransporte.label" default="Costo De Transporte" /></span>
					
						<span class="property-value" aria-labelledby="costoDeTransporte-label"><g:fieldValue bean="${detallePagoTransporteInstance}" field="costoDeTransporte"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${detallePagoTransporteInstance?.id}" />
					<g:link class="edit" action="edit" id="${detallePagoTransporteInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
