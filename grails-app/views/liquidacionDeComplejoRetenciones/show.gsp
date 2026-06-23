
<%@ page import="org.socymet.liquidacion.LiquidacionDeComplejoRetenciones" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'liquidacionDeComplejoRetenciones.label', default: 'LiquidacionDeComplejoRetenciones')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-liquidacionDeComplejoRetenciones" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-liquidacionDeComplejoRetenciones" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list liquidacionDeComplejoRetenciones">
			
				<g:if test="${liquidacionDeComplejoRetencionesInstance?.liquidacionDeComplejo}">
				<li class="fieldcontain">
					<span id="liquidacionDeComplejo-label" class="property-label"><g:message code="liquidacionDeComplejoRetenciones.liquidacionDeComplejo.label" default="Liquidacion De Complejo" /></span>
					
						<span class="property-value" aria-labelledby="liquidacionDeComplejo-label"><g:link controller="liquidacionDeComplejo" action="show" id="${liquidacionDeComplejoRetencionesInstance?.liquidacionDeComplejo?.id}">${liquidacionDeComplejoRetencionesInstance?.liquidacionDeComplejo?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeComplejoRetencionesInstance?.codigo}">
				<li class="fieldcontain">
					<span id="codigo-label" class="property-label"><g:message code="liquidacionDeComplejoRetenciones.codigo.label" default="Codigo" /></span>
					
						<span class="property-value" aria-labelledby="codigo-label"><g:fieldValue bean="${liquidacionDeComplejoRetencionesInstance}" field="codigo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeComplejoRetencionesInstance?.cantidadDescuento}">
				<li class="fieldcontain">
					<span id="cantidadDescuento-label" class="property-label"><g:message code="liquidacionDeComplejoRetenciones.cantidadDescuento.label" default="Cantidad Descuento" /></span>
					
						<span class="property-value" aria-labelledby="cantidadDescuento-label"><g:fieldValue bean="${liquidacionDeComplejoRetencionesInstance}" field="cantidadDescuento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeComplejoRetencionesInstance?.unidadDeDescuento}">
				<li class="fieldcontain">
					<span id="unidadDeDescuento-label" class="property-label"><g:message code="liquidacionDeComplejoRetenciones.unidadDeDescuento.label" default="Unidad De Descuento" /></span>
					
						<span class="property-value" aria-labelledby="unidadDeDescuento-label"><g:fieldValue bean="${liquidacionDeComplejoRetencionesInstance}" field="unidadDeDescuento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeComplejoRetencionesInstance?.tipoDeRetencion}">
				<li class="fieldcontain">
					<span id="tipoDeRetencion-label" class="property-label"><g:message code="liquidacionDeComplejoRetenciones.tipoDeRetencion.label" default="Tipo De Retencion" /></span>
					
						<span class="property-value" aria-labelledby="tipoDeRetencion-label"><g:fieldValue bean="${liquidacionDeComplejoRetencionesInstance}" field="tipoDeRetencion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeComplejoRetencionesInstance?.descripcion}">
				<li class="fieldcontain">
					<span id="descripcion-label" class="property-label"><g:message code="liquidacionDeComplejoRetenciones.descripcion.label" default="Descripcion" /></span>
					
						<span class="property-value" aria-labelledby="descripcion-label"><g:fieldValue bean="${liquidacionDeComplejoRetencionesInstance}" field="descripcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeComplejoRetencionesInstance?.asignacionDelDescuento}">
				<li class="fieldcontain">
					<span id="asignacionDelDescuento-label" class="property-label"><g:message code="liquidacionDeComplejoRetenciones.asignacionDelDescuento.label" default="Asignacion Del Descuento" /></span>
					
						<span class="property-value" aria-labelledby="asignacionDelDescuento-label"><g:fieldValue bean="${liquidacionDeComplejoRetencionesInstance}" field="asignacionDelDescuento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeComplejoRetencionesInstance?.monto}">
				<li class="fieldcontain">
					<span id="monto-label" class="property-label"><g:message code="liquidacionDeComplejoRetenciones.monto.label" default="Monto" /></span>
					
						<span class="property-value" aria-labelledby="monto-label"><g:fieldValue bean="${liquidacionDeComplejoRetencionesInstance}" field="monto"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${liquidacionDeComplejoRetencionesInstance?.id}" />
					<g:link class="edit" action="edit" id="${liquidacionDeComplejoRetencionesInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
