
<%@ page import="org.socymet.cancelacion.AcumulacionBonoProduccion" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'acumulacionBonoProduccion.label', default: 'AcumulacionBonoProduccion')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-acumulacionBonoProduccion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-acumulacionBonoProduccion" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list acumulacionBonoProduccion">
			
				<g:if test="${acumulacionBonoProduccionInstance?.pagoBonoProduccion}">
				<li class="fieldcontain">
					<span id="pagoBonoProduccion-label" class="property-label"><g:message code="acumulacionBonoProduccion.pagoBonoProduccion.label" default="Pago Bono Produccion" /></span>
					
						<span class="property-value" aria-labelledby="pagoBonoProduccion-label"><g:link controller="pagoBonoProduccion" action="show" id="${acumulacionBonoProduccionInstance?.pagoBonoProduccion?.id}">${acumulacionBonoProduccionInstance?.pagoBonoProduccion?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${acumulacionBonoProduccionInstance?.fecha}">
				<li class="fieldcontain">
					<span id="fecha-label" class="property-label"><g:message code="acumulacionBonoProduccion.fecha.label" default="Fecha" /></span>
					
						<span class="property-value" aria-labelledby="fecha-label"><g:formatDate date="${acumulacionBonoProduccionInstance?.fecha}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${acumulacionBonoProduccionInstance?.tipoSeleccion}">
				<li class="fieldcontain">
					<span id="tipoSeleccion-label" class="property-label"><g:message code="acumulacionBonoProduccion.tipoSeleccion.label" default="Tipo Seleccion" /></span>
					
						<span class="property-value" aria-labelledby="tipoSeleccion-label"><g:fieldValue bean="${acumulacionBonoProduccionInstance}" field="tipoSeleccion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${acumulacionBonoProduccionInstance?.cliente}">
				<li class="fieldcontain">
					<span id="cliente-label" class="property-label"><g:message code="acumulacionBonoProduccion.cliente.label" default="Cliente" /></span>
					
						<span class="property-value" aria-labelledby="cliente-label"><g:link controller="cliente" action="show" id="${acumulacionBonoProduccionInstance?.cliente?.id}">${acumulacionBonoProduccionInstance?.cliente?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${acumulacionBonoProduccionInstance?.empresa}">
				<li class="fieldcontain">
					<span id="empresa-label" class="property-label"><g:message code="acumulacionBonoProduccion.empresa.label" default="Empresa" /></span>
					
						<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${acumulacionBonoProduccionInstance?.empresa?.id}">${acumulacionBonoProduccionInstance?.empresa?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${acumulacionBonoProduccionInstance?.cuadrilla}">
				<li class="fieldcontain">
					<span id="cuadrilla-label" class="property-label"><g:message code="acumulacionBonoProduccion.cuadrilla.label" default="Cuadrilla" /></span>
					
						<span class="property-value" aria-labelledby="cuadrilla-label"><g:link controller="cuadrilla" action="show" id="${acumulacionBonoProduccionInstance?.cuadrilla?.id}">${acumulacionBonoProduccionInstance?.cuadrilla?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${acumulacionBonoProduccionInstance?.cantidadAcumulada}">
				<li class="fieldcontain">
					<span id="cantidadAcumulada-label" class="property-label"><g:message code="acumulacionBonoProduccion.cantidadAcumulada.label" default="Cantidad Acumulada" /></span>
					
						<span class="property-value" aria-labelledby="cantidadAcumulada-label"><g:fieldValue bean="${acumulacionBonoProduccionInstance}" field="cantidadAcumulada"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${acumulacionBonoProduccionInstance?.id}" />
					<g:link class="edit" action="edit" id="${acumulacionBonoProduccionInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
