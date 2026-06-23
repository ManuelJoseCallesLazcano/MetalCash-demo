
<%@ page import="org.socymet.proveedor.EmpresaRetenciones" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'empresaRetenciones.label', default: 'EmpresaRetenciones')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-empresaRetenciones" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-empresaRetenciones" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list empresaRetenciones">
			
				<g:if test="${empresaRetencionesInstance?.empresa}">
				<li class="fieldcontain">
					<span id="empresa-label" class="property-label"><g:message code="empresaRetenciones.empresa.label" default="Empresa" /></span>
					
						<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${empresaRetencionesInstance?.empresa?.id}">${empresaRetencionesInstance?.empresa?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${empresaRetencionesInstance?.descripcion}">
				<li class="fieldcontain">
					<span id="descripcion-label" class="property-label"><g:message code="empresaRetenciones.descripcion.label" default="Descripcion" /></span>
					
						<span class="property-value" aria-labelledby="descripcion-label"><g:fieldValue bean="${empresaRetencionesInstance}" field="descripcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${empresaRetencionesInstance?.tipoDeRetencion}">
				<li class="fieldcontain">
					<span id="tipoDeRetencion-label" class="property-label"><g:message code="empresaRetenciones.tipoDeRetencion.label" default="Tipo De Retencion" /></span>
					
						<span class="property-value" aria-labelledby="tipoDeRetencion-label"><g:fieldValue bean="${empresaRetencionesInstance}" field="tipoDeRetencion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${empresaRetencionesInstance?.cantidadDescuento}">
				<li class="fieldcontain">
					<span id="cantidadDescuento-label" class="property-label"><g:message code="empresaRetenciones.cantidadDescuento.label" default="Cantidad Descuento" /></span>
					
						<span class="property-value" aria-labelledby="cantidadDescuento-label"><g:fieldValue bean="${empresaRetencionesInstance}" field="cantidadDescuento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${empresaRetencionesInstance?.unidadDeDescuento}">
				<li class="fieldcontain">
					<span id="unidadDeDescuento-label" class="property-label"><g:message code="empresaRetenciones.unidadDeDescuento.label" default="Unidad De Descuento" /></span>
					
						<span class="property-value" aria-labelledby="unidadDeDescuento-label"><g:fieldValue bean="${empresaRetencionesInstance}" field="unidadDeDescuento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${empresaRetencionesInstance?.asignacionDelDescuento}">
				<li class="fieldcontain">
					<span id="asignacionDelDescuento-label" class="property-label"><g:message code="empresaRetenciones.asignacionDelDescuento.label" default="Asignacion Del Descuento" /></span>
					
						<span class="property-value" aria-labelledby="asignacionDelDescuento-label"><g:fieldValue bean="${empresaRetencionesInstance}" field="asignacionDelDescuento"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${empresaRetencionesInstance?.id}" />
					<g:link class="edit" action="edit" id="${empresaRetencionesInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
