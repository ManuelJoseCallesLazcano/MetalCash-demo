
<%@ page import="org.socymet.liquidacion.LiquidacionDeWolfranRetenciones" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'liquidacionDeWolfranRetenciones.label', default: 'LiquidacionDeWolfranRetenciones')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-liquidacionDeWolfranRetenciones" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-liquidacionDeWolfranRetenciones" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list liquidacionDeWolfranRetenciones">
			
				<g:if test="${liquidacionDeWolfranRetencionesInstance?.liquidacionDeWolfran}">
				<li class="fieldcontain">
					<span id="liquidacionDeWolfran-label" class="property-label"><g:message code="liquidacionDeWolfranRetenciones.liquidacionDeWolfran.label" default="Liquidacion De Wolfran" /></span>
					
						<span class="property-value" aria-labelledby="liquidacionDeWolfran-label"><g:link controller="liquidacionDeWolfran" action="show" id="${liquidacionDeWolfranRetencionesInstance?.liquidacionDeWolfran?.id}">${liquidacionDeWolfranRetencionesInstance?.liquidacionDeWolfran?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeWolfranRetencionesInstance?.codigo}">
				<li class="fieldcontain">
					<span id="codigo-label" class="property-label"><g:message code="liquidacionDeWolfranRetenciones.codigo.label" default="Codigo" /></span>
					
						<span class="property-value" aria-labelledby="codigo-label"><g:fieldValue bean="${liquidacionDeWolfranRetencionesInstance}" field="codigo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeWolfranRetencionesInstance?.cantidadDescuento}">
				<li class="fieldcontain">
					<span id="cantidadDescuento-label" class="property-label"><g:message code="liquidacionDeWolfranRetenciones.cantidadDescuento.label" default="Cantidad Descuento" /></span>
					
						<span class="property-value" aria-labelledby="cantidadDescuento-label"><g:fieldValue bean="${liquidacionDeWolfranRetencionesInstance}" field="cantidadDescuento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeWolfranRetencionesInstance?.unidadDeDescuento}">
				<li class="fieldcontain">
					<span id="unidadDeDescuento-label" class="property-label"><g:message code="liquidacionDeWolfranRetenciones.unidadDeDescuento.label" default="Unidad De Descuento" /></span>
					
						<span class="property-value" aria-labelledby="unidadDeDescuento-label"><g:fieldValue bean="${liquidacionDeWolfranRetencionesInstance}" field="unidadDeDescuento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeWolfranRetencionesInstance?.tipoDeRetencion}">
				<li class="fieldcontain">
					<span id="tipoDeRetencion-label" class="property-label"><g:message code="liquidacionDeWolfranRetenciones.tipoDeRetencion.label" default="Tipo De Retencion" /></span>
					
						<span class="property-value" aria-labelledby="tipoDeRetencion-label"><g:fieldValue bean="${liquidacionDeWolfranRetencionesInstance}" field="tipoDeRetencion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeWolfranRetencionesInstance?.descripcion}">
				<li class="fieldcontain">
					<span id="descripcion-label" class="property-label"><g:message code="liquidacionDeWolfranRetenciones.descripcion.label" default="Descripcion" /></span>
					
						<span class="property-value" aria-labelledby="descripcion-label"><g:fieldValue bean="${liquidacionDeWolfranRetencionesInstance}" field="descripcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeWolfranRetencionesInstance?.asignacionDelDescuento}">
				<li class="fieldcontain">
					<span id="asignacionDelDescuento-label" class="property-label"><g:message code="liquidacionDeWolfranRetenciones.asignacionDelDescuento.label" default="Asignacion Del Descuento" /></span>
					
						<span class="property-value" aria-labelledby="asignacionDelDescuento-label"><g:fieldValue bean="${liquidacionDeWolfranRetencionesInstance}" field="asignacionDelDescuento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${liquidacionDeWolfranRetencionesInstance?.monto}">
				<li class="fieldcontain">
					<span id="monto-label" class="property-label"><g:message code="liquidacionDeWolfranRetenciones.monto.label" default="Monto" /></span>
					
						<span class="property-value" aria-labelledby="monto-label"><g:fieldValue bean="${liquidacionDeWolfranRetencionesInstance}" field="monto"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${liquidacionDeWolfranRetencionesInstance?.id}" />
					<g:link class="edit" action="edit" id="${liquidacionDeWolfranRetencionesInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
