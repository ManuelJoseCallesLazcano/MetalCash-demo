
<%@ page import="org.socymet.liquidacion.RetencionPorPagarComplejo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'retencionPorPagarComplejo.label', default: 'RetencionPorPagarComplejo')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-retencionPorPagarComplejo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-retencionPorPagarComplejo" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list retencionPorPagarComplejo">
			
				<g:if test="${retencionPorPagarComplejoInstance?.codigo}">
				<li class="fieldcontain">
					<span id="codigo-label" class="property-label"><g:message code="retencionPorPagarComplejo.codigo.label" default="Codigo" /></span>
					
						<span class="property-value" aria-labelledby="codigo-label"><g:fieldValue bean="${retencionPorPagarComplejoInstance}" field="codigo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${retencionPorPagarComplejoInstance?.cantidadDescuento}">
				<li class="fieldcontain">
					<span id="cantidadDescuento-label" class="property-label"><g:message code="retencionPorPagarComplejo.cantidadDescuento.label" default="Cantidad Descuento" /></span>
					
						<span class="property-value" aria-labelledby="cantidadDescuento-label"><g:fieldValue bean="${retencionPorPagarComplejoInstance}" field="cantidadDescuento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${retencionPorPagarComplejoInstance?.unidadDeDescuento}">
				<li class="fieldcontain">
					<span id="unidadDeDescuento-label" class="property-label"><g:message code="retencionPorPagarComplejo.unidadDeDescuento.label" default="Unidad De Descuento" /></span>
					
						<span class="property-value" aria-labelledby="unidadDeDescuento-label"><g:fieldValue bean="${retencionPorPagarComplejoInstance}" field="unidadDeDescuento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${retencionPorPagarComplejoInstance?.tipoDeRetencion}">
				<li class="fieldcontain">
					<span id="tipoDeRetencion-label" class="property-label"><g:message code="retencionPorPagarComplejo.tipoDeRetencion.label" default="Tipo De Retencion" /></span>
					
						<span class="property-value" aria-labelledby="tipoDeRetencion-label"><g:fieldValue bean="${retencionPorPagarComplejoInstance}" field="tipoDeRetencion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${retencionPorPagarComplejoInstance?.descripcion}">
				<li class="fieldcontain">
					<span id="descripcion-label" class="property-label"><g:message code="retencionPorPagarComplejo.descripcion.label" default="Descripcion" /></span>
					
						<span class="property-value" aria-labelledby="descripcion-label"><g:fieldValue bean="${retencionPorPagarComplejoInstance}" field="descripcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${retencionPorPagarComplejoInstance?.asignacionDelDescuento}">
				<li class="fieldcontain">
					<span id="asignacionDelDescuento-label" class="property-label"><g:message code="retencionPorPagarComplejo.asignacionDelDescuento.label" default="Asignacion Del Descuento" /></span>
					
						<span class="property-value" aria-labelledby="asignacionDelDescuento-label"><g:fieldValue bean="${retencionPorPagarComplejoInstance}" field="asignacionDelDescuento"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${retencionPorPagarComplejoInstance?.monto}">
				<li class="fieldcontain">
					<span id="monto-label" class="property-label"><g:message code="retencionPorPagarComplejo.monto.label" default="Monto" /></span>
					
						<span class="property-value" aria-labelledby="monto-label"><g:fieldValue bean="${retencionPorPagarComplejoInstance}" field="monto"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${retencionPorPagarComplejoInstance?.lote}">
				<li class="fieldcontain">
					<span id="lote-label" class="property-label"><g:message code="retencionPorPagarComplejo.lote.label" default="Lote" /></span>
					
						<span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${retencionPorPagarComplejoInstance}" field="lote"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${retencionPorPagarComplejoInstance?.recepcionDeComplejo}">
				<li class="fieldcontain">
					<span id="recepcionDeComplejo-label" class="property-label"><g:message code="retencionPorPagarComplejo.recepcionDeComplejo.label" default="Recepcion De Complejo" /></span>
					
						<span class="property-value" aria-labelledby="recepcionDeComplejo-label"><g:link controller="recepcionDeComplejo" action="show" id="${retencionPorPagarComplejoInstance?.recepcionDeComplejo?.id}">${retencionPorPagarComplejoInstance?.recepcionDeComplejo?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${retencionPorPagarComplejoInstance?.tipoDeMineral}">
				<li class="fieldcontain">
					<span id="tipoDeMineral-label" class="property-label"><g:message code="retencionPorPagarComplejo.tipoDeMineral.label" default="Tipo De Mineral" /></span>
					
						<span class="property-value" aria-labelledby="tipoDeMineral-label"><g:fieldValue bean="${retencionPorPagarComplejoInstance}" field="tipoDeMineral"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${retencionPorPagarComplejoInstance?.empresa}">
				<li class="fieldcontain">
					<span id="empresa-label" class="property-label"><g:message code="retencionPorPagarComplejo.empresa.label" default="Empresa" /></span>
					
						<span class="property-value" aria-labelledby="empresa-label"><g:link controller="empresa" action="show" id="${retencionPorPagarComplejoInstance?.empresa?.id}">${retencionPorPagarComplejoInstance?.empresa?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${retencionPorPagarComplejoInstance?.fechaDeRegistro}">
				<li class="fieldcontain">
					<span id="fechaDeRegistro-label" class="property-label"><g:message code="retencionPorPagarComplejo.fechaDeRegistro.label" default="Fecha De Registro" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeRegistro-label"><g:formatDate date="${retencionPorPagarComplejoInstance?.fechaDeRegistro}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${retencionPorPagarComplejoInstance?.pagado}">
				<li class="fieldcontain">
					<span id="pagado-label" class="property-label"><g:message code="retencionPorPagarComplejo.pagado.label" default="Pagado" /></span>
					
						<span class="property-value" aria-labelledby="pagado-label"><g:fieldValue bean="${retencionPorPagarComplejoInstance}" field="pagado"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${retencionPorPagarComplejoInstance?.kilosNetosSecos}">
				<li class="fieldcontain">
					<span id="kilosNetosSecos-label" class="property-label"><g:message code="retencionPorPagarComplejo.kilosNetosSecos.label" default="Kilos Netos Secos" /></span>
					
						<span class="property-value" aria-labelledby="kilosNetosSecos-label"><g:fieldValue bean="${retencionPorPagarComplejoInstance}" field="kilosNetosSecos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${retencionPorPagarComplejoInstance?.valorOficialNeto}">
				<li class="fieldcontain">
					<span id="valorOficialNeto-label" class="property-label"><g:message code="retencionPorPagarComplejo.valorOficialNeto.label" default="Valor Oficial Neto" /></span>
					
						<span class="property-value" aria-labelledby="valorOficialNeto-label"><g:fieldValue bean="${retencionPorPagarComplejoInstance}" field="valorOficialNeto"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${retencionPorPagarComplejoInstance?.id}" />
					<g:link class="edit" action="edit" id="${retencionPorPagarComplejoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
