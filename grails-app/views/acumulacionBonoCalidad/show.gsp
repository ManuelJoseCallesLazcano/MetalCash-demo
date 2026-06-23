
<%@ page import="org.socymet.cancelacion.AcumulacionBonoCalidad" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'acumulacionBonoCalidad.label', default: 'AcumulacionBonoCalidad')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-acumulacionBonoCalidad" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-acumulacionBonoCalidad" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list acumulacionBonoCalidad">
			
				<g:if test="${acumulacionBonoCalidadInstance?.pagoBonoCalidad}">
				<li class="fieldcontain">
					<span id="pagoBonoCalidad-label" class="property-label"><g:message code="acumulacionBonoCalidad.pagoBonoCalidad.label" default="Pago Bono Calidad" /></span>
					
						<span class="property-value" aria-labelledby="pagoBonoCalidad-label"><g:link controller="pagoBonoCalidad" action="show" id="${acumulacionBonoCalidadInstance?.pagoBonoCalidad?.id}">${acumulacionBonoCalidadInstance?.pagoBonoCalidad?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${acumulacionBonoCalidadInstance?.fecha}">
				<li class="fieldcontain">
					<span id="fecha-label" class="property-label"><g:message code="acumulacionBonoCalidad.fecha.label" default="Fecha" /></span>
					
						<span class="property-value" aria-labelledby="fecha-label"><g:formatDate date="${acumulacionBonoCalidadInstance?.fecha}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${acumulacionBonoCalidadInstance?.tipoSeleccion}">
				<li class="fieldcontain">
					<span id="tipoSeleccion-label" class="property-label"><g:message code="acumulacionBonoCalidad.tipoSeleccion.label" default="Tipo Seleccion" /></span>
					
						<span class="property-value" aria-labelledby="tipoSeleccion-label"><g:fieldValue bean="${acumulacionBonoCalidadInstance}" field="tipoSeleccion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${acumulacionBonoCalidadInstance?.cliente}">
				<li class="fieldcontain">
					<span id="cliente-label" class="property-label"><g:message code="acumulacionBonoCalidad.cliente.label" default="Cliente" /></span>
					
						<span class="property-value" aria-labelledby="cliente-label"><g:link controller="cliente" action="show" id="${acumulacionBonoCalidadInstance?.cliente?.id}">${acumulacionBonoCalidadInstance?.cliente?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${acumulacionBonoCalidadInstance?.empresa}">
				<li class="fieldcontain">
					<span id="empresa-label" class="property-label"><g:message code="acumulacionBonoCalidad.empresa.label" default="Empresa" /></span>
					
						<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${acumulacionBonoCalidadInstance?.empresa?.id}">${acumulacionBonoCalidadInstance?.empresa?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${acumulacionBonoCalidadInstance?.cuadrilla}">
				<li class="fieldcontain">
					<span id="cuadrilla-label" class="property-label"><g:message code="acumulacionBonoCalidad.cuadrilla.label" default="Cuadrilla" /></span>
					
						<span class="property-value" aria-labelledby="cuadrilla-label"><g:link controller="cuadrilla" action="show" id="${acumulacionBonoCalidadInstance?.cuadrilla?.id}">${acumulacionBonoCalidadInstance?.cuadrilla?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${acumulacionBonoCalidadInstance?.cantidadAcumulada}">
				<li class="fieldcontain">
					<span id="cantidadAcumulada-label" class="property-label"><g:message code="acumulacionBonoCalidad.cantidadAcumulada.label" default="Cantidad Acumulada" /></span>
					
						<span class="property-value" aria-labelledby="cantidadAcumulada-label"><g:fieldValue bean="${acumulacionBonoCalidadInstance}" field="cantidadAcumulada"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${acumulacionBonoCalidadInstance?.id}" />
					<g:link class="edit" action="edit" id="${acumulacionBonoCalidadInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
