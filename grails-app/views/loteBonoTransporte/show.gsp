
<%@ page import="org.socymet.cancelacion.LoteBonoTransporte" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'loteBonoTransporte.label', default: 'LoteBonoTransporte')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-loteBonoTransporte" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-loteBonoTransporte" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list loteBonoTransporte">
			
				<g:if test="${loteBonoTransporteInstance?.pagoBonoTransporte}">
				<li class="fieldcontain">
					<span id="pagoBonoTransporte-label" class="property-label"><g:message code="loteBonoTransporte.pagoBonoTransporte.label" default="Pago Bono Transporte" /></span>
					
						<span class="property-value" aria-labelledby="pagoBonoTransporte-label"><g:link controller="pagoBonoTransporte" action="show" id="${loteBonoTransporteInstance?.pagoBonoTransporte?.id}">${loteBonoTransporteInstance?.pagoBonoTransporte?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${loteBonoTransporteInstance?.lote}">
				<li class="fieldcontain">
					<span id="lote-label" class="property-label"><g:message code="loteBonoTransporte.lote.label" default="Lote" /></span>
					
						<span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${loteBonoTransporteInstance}" field="lote"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${loteBonoTransporteInstance?.fechaDeRecepcion}">
				<li class="fieldcontain">
					<span id="fechaDeRecepcion-label" class="property-label"><g:message code="loteBonoTransporte.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:formatDate date="${loteBonoTransporteInstance?.fechaDeRecepcion}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${loteBonoTransporteInstance?.nombreEmpresa}">
				<li class="fieldcontain">
					<span id="nombreEmpresa-label" class="property-label"><g:message code="loteBonoTransporte.nombreEmpresa.label" default="Nombre Empresa" /></span>
					
						<span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${loteBonoTransporteInstance}" field="nombreEmpresa"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${loteBonoTransporteInstance?.nombreCliente}">
				<li class="fieldcontain">
					<span id="nombreCliente-label" class="property-label"><g:message code="loteBonoTransporte.nombreCliente.label" default="Nombre Cliente" /></span>
					
						<span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${loteBonoTransporteInstance}" field="nombreCliente"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${loteBonoTransporteInstance?.kilosBrutos}">
				<li class="fieldcontain">
					<span id="kilosBrutos-label" class="property-label"><g:message code="loteBonoTransporte.kilosBrutos.label" default="Kilos Brutos" /></span>
					
						<span class="property-value" aria-labelledby="kilosBrutos-label"><g:fieldValue bean="${loteBonoTransporteInstance}" field="kilosBrutos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${loteBonoTransporteInstance?.leyPlomo}">
				<li class="fieldcontain">
					<span id="leyPlomo-label" class="property-label"><g:message code="loteBonoTransporte.leyPlomo.label" default="Ley Plomo" /></span>
					
						<span class="property-value" aria-labelledby="leyPlomo-label"><g:fieldValue bean="${loteBonoTransporteInstance}" field="leyPlomo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${loteBonoTransporteInstance?.leyZinc}">
				<li class="fieldcontain">
					<span id="leyZinc-label" class="property-label"><g:message code="loteBonoTransporte.leyZinc.label" default="Ley Zinc" /></span>
					
						<span class="property-value" aria-labelledby="leyZinc-label"><g:fieldValue bean="${loteBonoTransporteInstance}" field="leyZinc"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${loteBonoTransporteInstance?.bono}">
				<li class="fieldcontain">
					<span id="bono-label" class="property-label"><g:message code="loteBonoTransporte.bono.label" default="Bono" /></span>
					
						<span class="property-value" aria-labelledby="bono-label"><g:fieldValue bean="${loteBonoTransporteInstance}" field="bono"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${loteBonoTransporteInstance?.id}" />
					<g:link class="edit" action="edit" id="${loteBonoTransporteInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
