
<%@ page import="org.socymet.liquidacion.LiquidacionDePlomoPlataRetenciones" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'liquidacionDePlomoPlataRetenciones.label', default: 'LiquidacionDePlomoPlataRetenciones')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-liquidacionDePlomoPlataRetenciones" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-liquidacionDePlomoPlataRetenciones" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list liquidacionDePlomoPlataRetenciones">
			
				<g:if test="${liquidacionDePlomoPlataRetencionesInstance?.liquidacionDePlomoPlata}">
				<li class="fieldcontain">
					<span id="liquidacionDePlomoPlata-label" class="property-label"><g:message code="liquidacionDePlomoPlataRetenciones.liquidacionDePlomoPlata.label" default="Liquidacion De Plomo Plata" /></span>
					
						<span class="property-value" aria-labelledby="liquidacionDePlomoPlata-label"><g:link controller="liquidacionDePlomoPlata" action="show" id="${liquidacionDePlomoPlataRetencionesInstance?.liquidacionDePlomoPlata?.id}">${liquidacionDePlomoPlataRetencionesInstance?.liquidacionDePlomoPlata?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDePlomoPlataRetencionesInstance?.codigo}">
				<li class="fieldcontain">
					<span id="codigo-label" class="property-label"><g:message code="liquidacionDePlomoPlataRetenciones.codigo.label" default="Codigo" /></span>
					
						<span class="property-value" aria-labelledby="codigo-label"><g:fieldValue bean="${liquidacionDePlomoPlataRetencionesInstance}" field="codigo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDePlomoPlataRetencionesInstance?.cantidadDescuento}">
				<li class="fieldcontain">
					<span id="cantidadDescuento-label" class="property-label"><g:message code="liquidacionDePlomoPlataRetenciones.cantidadDescuento.label" default="Cantidad Descuento" /></span>
					
						<span class="property-value" aria-labelledby="cantidadDescuento-label"><g:fieldValue bean="${liquidacionDePlomoPlataRetencionesInstance}" field="cantidadDescuento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDePlomoPlataRetencionesInstance?.unidadDeDescuento}">
				<li class="fieldcontain">
					<span id="unidadDeDescuento-label" class="property-label"><g:message code="liquidacionDePlomoPlataRetenciones.unidadDeDescuento.label" default="Unidad De Descuento" /></span>
					
						<span class="property-value" aria-labelledby="unidadDeDescuento-label"><g:fieldValue bean="${liquidacionDePlomoPlataRetencionesInstance}" field="unidadDeDescuento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDePlomoPlataRetencionesInstance?.tipoDeRetencion}">
				<li class="fieldcontain">
					<span id="tipoDeRetencion-label" class="property-label"><g:message code="liquidacionDePlomoPlataRetenciones.tipoDeRetencion.label" default="Tipo De Retencion" /></span>
					
						<span class="property-value" aria-labelledby="tipoDeRetencion-label"><g:fieldValue bean="${liquidacionDePlomoPlataRetencionesInstance}" field="tipoDeRetencion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDePlomoPlataRetencionesInstance?.descripcion}">
				<li class="fieldcontain">
					<span id="descripcion-label" class="property-label"><g:message code="liquidacionDePlomoPlataRetenciones.descripcion.label" default="Descripcion" /></span>
					
						<span class="property-value" aria-labelledby="descripcion-label"><g:fieldValue bean="${liquidacionDePlomoPlataRetencionesInstance}" field="descripcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDePlomoPlataRetencionesInstance?.asignacionDelDescuento}">
				<li class="fieldcontain">
					<span id="asignacionDelDescuento-label" class="property-label"><g:message code="liquidacionDePlomoPlataRetenciones.asignacionDelDescuento.label" default="Asignacion Del Descuento" /></span>
					
						<span class="property-value" aria-labelledby="asignacionDelDescuento-label"><g:fieldValue bean="${liquidacionDePlomoPlataRetencionesInstance}" field="asignacionDelDescuento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDePlomoPlataRetencionesInstance?.monto}">
				<li class="fieldcontain">
					<span id="monto-label" class="property-label"><g:message code="liquidacionDePlomoPlataRetenciones.monto.label" default="Monto" /></span>
					
						<span class="property-value" aria-labelledby="monto-label"><g:fieldValue bean="${liquidacionDePlomoPlataRetencionesInstance}" field="monto"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${liquidacionDePlomoPlataRetencionesInstance?.id}" />
					<g:link class="edit" action="edit" id="${liquidacionDePlomoPlataRetencionesInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
