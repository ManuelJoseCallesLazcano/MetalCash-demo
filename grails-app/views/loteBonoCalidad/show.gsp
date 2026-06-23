
<%@ page import="org.socymet.cancelacion.LoteBonoCalidad" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'loteBonoCalidad.label', default: 'LoteBonoCalidad')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-loteBonoCalidad" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-loteBonoCalidad" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list loteBonoCalidad">
			
				<g:if test="${loteBonoCalidadInstance?.pagoBonoProduccion}">
				<li class="fieldcontain">
					<span id="pagoBonoProduccion-label" class="property-label"><g:message code="loteBonoCalidad.pagoBonoProduccion.label" default="Pago Bono Produccion" /></span>
					
						<span class="property-value" aria-labelledby="pagoBonoProduccion-label"><g:link controller="pagoBonoProduccion" action="show" id="${loteBonoCalidadInstance?.pagoBonoProduccion?.id}">${loteBonoCalidadInstance?.pagoBonoProduccion?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${loteBonoCalidadInstance?.lote}">
				<li class="fieldcontain">
					<span id="lote-label" class="property-label"><g:message code="loteBonoCalidad.lote.label" default="Lote" /></span>
					
						<span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${loteBonoCalidadInstance}" field="lote"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${loteBonoCalidadInstance?.fechaDeLiquidacion}">
				<li class="fieldcontain">
					<span id="fechaDeLiquidacion-label" class="property-label"><g:message code="loteBonoCalidad.fechaDeLiquidacion.label" default="Fecha De Liquidacion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeLiquidacion-label"><g:formatDate date="${loteBonoCalidadInstance?.fechaDeLiquidacion}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${loteBonoCalidadInstance?.nombreEmpresa}">
				<li class="fieldcontain">
					<span id="nombreEmpresa-label" class="property-label"><g:message code="loteBonoCalidad.nombreEmpresa.label" default="Nombre Empresa" /></span>
					
						<span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${loteBonoCalidadInstance}" field="nombreEmpresa"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${loteBonoCalidadInstance?.nombreCliente}">
				<li class="fieldcontain">
					<span id="nombreCliente-label" class="property-label"><g:message code="loteBonoCalidad.nombreCliente.label" default="Nombre Cliente" /></span>
					
						<span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${loteBonoCalidadInstance}" field="nombreCliente"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${loteBonoCalidadInstance?.kilosNetosSecos}">
				<li class="fieldcontain">
					<span id="kilosNetosSecos-label" class="property-label"><g:message code="loteBonoCalidad.kilosNetosSecos.label" default="Kilos Netos Secos" /></span>
					
						<span class="property-value" aria-labelledby="kilosNetosSecos-label"><g:fieldValue bean="${loteBonoCalidadInstance}" field="kilosNetosSecos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${loteBonoCalidadInstance?.porcentajePlataFinal}">
				<li class="fieldcontain">
					<span id="porcentajePlataFinal-label" class="property-label"><g:message code="loteBonoCalidad.porcentajePlataFinal.label" default="Porcentaje Plata Final" /></span>
					
						<span class="property-value" aria-labelledby="porcentajePlataFinal-label"><g:fieldValue bean="${loteBonoCalidadInstance}" field="porcentajePlataFinal"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${loteBonoCalidadInstance?.totalLiquidoPagable}">
				<li class="fieldcontain">
					<span id="totalLiquidoPagable-label" class="property-label"><g:message code="loteBonoCalidad.totalLiquidoPagable.label" default="Total Liquido Pagable" /></span>
					
						<span class="property-value" aria-labelledby="totalLiquidoPagable-label"><g:fieldValue bean="${loteBonoCalidadInstance}" field="totalLiquidoPagable"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${loteBonoCalidadInstance?.id}" />
					<g:link class="edit" action="edit" id="${loteBonoCalidadInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
