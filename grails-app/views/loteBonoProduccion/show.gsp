
<%@ page import="org.socymet.cancelacion.LoteBonoProduccion" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'loteBonoProduccion.label', default: 'LoteBonoProduccion')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-loteBonoProduccion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-loteBonoProduccion" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list loteBonoProduccion">
			
				<g:if test="${loteBonoProduccionInstance?.pagoBonoProduccion}">
				<li class="fieldcontain">
					<span id="pagoBonoProduccion-label" class="property-label"><g:message code="loteBonoProduccion.pagoBonoProduccion.label" default="Pago Bono Produccion" /></span>
					
						<span class="property-value" aria-labelledby="pagoBonoProduccion-label"><g:link controller="pagoBonoProduccion" action="show" id="${loteBonoProduccionInstance?.pagoBonoProduccion?.id}">${loteBonoProduccionInstance?.pagoBonoProduccion?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${loteBonoProduccionInstance?.lote}">
				<li class="fieldcontain">
					<span id="lote-label" class="property-label"><g:message code="loteBonoProduccion.lote.label" default="Lote" /></span>
					
						<span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${loteBonoProduccionInstance}" field="lote"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${loteBonoProduccionInstance?.fechaDeLiquidacion}">
				<li class="fieldcontain">
					<span id="fechaDeLiquidacion-label" class="property-label"><g:message code="loteBonoProduccion.fechaDeLiquidacion.label" default="Fecha De Liquidacion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeLiquidacion-label"><g:formatDate date="${loteBonoProduccionInstance?.fechaDeLiquidacion}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${loteBonoProduccionInstance?.nombreEmpresa}">
				<li class="fieldcontain">
					<span id="nombreEmpresa-label" class="property-label"><g:message code="loteBonoProduccion.nombreEmpresa.label" default="Nombre Empresa" /></span>
					
						<span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${loteBonoProduccionInstance}" field="nombreEmpresa"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${loteBonoProduccionInstance?.nombreCliente}">
				<li class="fieldcontain">
					<span id="nombreCliente-label" class="property-label"><g:message code="loteBonoProduccion.nombreCliente.label" default="Nombre Cliente" /></span>
					
						<span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${loteBonoProduccionInstance}" field="nombreCliente"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${loteBonoProduccionInstance?.kilosNetosSecos}">
				<li class="fieldcontain">
					<span id="kilosNetosSecos-label" class="property-label"><g:message code="loteBonoProduccion.kilosNetosSecos.label" default="Kilos Netos Secos" /></span>
					
						<span class="property-value" aria-labelledby="kilosNetosSecos-label"><g:fieldValue bean="${loteBonoProduccionInstance}" field="kilosNetosSecos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${loteBonoProduccionInstance?.totalLiquidoPagable}">
				<li class="fieldcontain">
					<span id="totalLiquidoPagable-label" class="property-label"><g:message code="loteBonoProduccion.totalLiquidoPagable.label" default="Total Liquido Pagable" /></span>
					
						<span class="property-value" aria-labelledby="totalLiquidoPagable-label"><g:fieldValue bean="${loteBonoProduccionInstance}" field="totalLiquidoPagable"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${loteBonoProduccionInstance?.id}" />
					<g:link class="edit" action="edit" id="${loteBonoProduccionInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
