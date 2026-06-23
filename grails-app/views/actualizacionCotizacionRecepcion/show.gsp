
<%@ page import="org.socymet.recepcion.ActualizacionCotizacionRecepcion" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'actualizacionCotizacionRecepcion.label', default: 'ActualizacionCotizacionRecepcion')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-actualizacionCotizacionRecepcion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-actualizacionCotizacionRecepcion" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list actualizacionCotizacionRecepcion">
			
				<g:if test="${actualizacionCotizacionRecepcionInstance?.tipoCotizacion}">
				<li class="fieldcontain">
					<span id="tipoCotizacion-label" class="property-label"><g:message code="actualizacionCotizacionRecepcion.tipoCotizacion.label" default="Tipo Cotizacion" /></span>
					
						<span class="property-value" aria-labelledby="tipoCotizacion-label"><g:fieldValue bean="${actualizacionCotizacionRecepcionInstance}" field="tipoCotizacion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${actualizacionCotizacionRecepcionInstance?.cotizacionDiariaDeMinerales}">
				<li class="fieldcontain">
					<span id="cotizacionDiariaDeMinerales-label" class="property-label"><g:message code="actualizacionCotizacionRecepcion.cotizacionDiariaDeMinerales.label" default="Cotizacion Diaria De Minerales" /></span>
					
						<span class="property-value" aria-labelledby="cotizacionDiariaDeMinerales-label"><g:link controller="cotizacionDiariaDeMinerales" action="show" id="${actualizacionCotizacionRecepcionInstance?.cotizacionDiariaDeMinerales?.id}">${actualizacionCotizacionRecepcionInstance?.cotizacionDiariaDeMinerales?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${actualizacionCotizacionRecepcionInstance?.cotizacionQuincenalDeMinerales}">
				<li class="fieldcontain">
					<span id="cotizacionQuincenalDeMinerales-label" class="property-label"><g:message code="actualizacionCotizacionRecepcion.cotizacionQuincenalDeMinerales.label" default="Cotizacion Quincenal De Minerales" /></span>
					
						<span class="property-value" aria-labelledby="cotizacionQuincenalDeMinerales-label"><g:link controller="cotizacionQuincenalDeMinerales" action="show" id="${actualizacionCotizacionRecepcionInstance?.cotizacionQuincenalDeMinerales?.id}">${actualizacionCotizacionRecepcionInstance?.cotizacionQuincenalDeMinerales?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${actualizacionCotizacionRecepcionInstance?.fechaInicial}">
				<li class="fieldcontain">
					<span id="fechaInicial-label" class="property-label"><g:message code="actualizacionCotizacionRecepcion.fechaInicial.label" default="Fecha Inicial" /></span>
					
						<span class="property-value" aria-labelledby="fechaInicial-label"><g:formatDate date="${actualizacionCotizacionRecepcionInstance?.fechaInicial}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${actualizacionCotizacionRecepcionInstance?.fechaFinal}">
				<li class="fieldcontain">
					<span id="fechaFinal-label" class="property-label"><g:message code="actualizacionCotizacionRecepcion.fechaFinal.label" default="Fecha Final" /></span>
					
						<span class="property-value" aria-labelledby="fechaFinal-label"><g:formatDate date="${actualizacionCotizacionRecepcionInstance?.fechaFinal}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${actualizacionCotizacionRecepcionInstance?.detalleLotes}">
				<li class="fieldcontain">
					<span id="detalleLotes-label" class="property-label"><g:message code="actualizacionCotizacionRecepcion.detalleLotes.label" default="Detalle Lotes" /></span>
					
						<span class="property-value" aria-labelledby="detalleLotes-label"><g:fieldValue bean="${actualizacionCotizacionRecepcionInstance}" field="detalleLotes"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:actualizacionCotizacionRecepcionInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${actualizacionCotizacionRecepcionInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
