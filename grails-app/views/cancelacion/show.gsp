
<%@ page import="org.socymet.cancelacion.Cancelacion" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'cancelacion.label', default: 'Cancelacion')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-cancelacion" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-cancelacion" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list cancelacion">
			
				<g:if test="${cancelacionInstance?.lote}">
				<li class="fieldcontain">
					<span id="lote-label" class="property-label"><g:message code="cancelacion.lote.label" default="Lote" /></span>
					
						<span class="property-value" aria-labelledby="lote-label"><g:fieldValue bean="${cancelacionInstance}" field="lote"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cancelacionInstance?.nombreCliente}">
				<li class="fieldcontain">
					<span id="nombreCliente-label" class="property-label"><g:message code="cancelacion.nombreCliente.label" default="Nombre Cliente" /></span>
					
						<span class="property-value" aria-labelledby="nombreCliente-label"><g:fieldValue bean="${cancelacionInstance}" field="nombreCliente"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cancelacionInstance?.nombreEmpresa}">
				<li class="fieldcontain">
					<span id="nombreEmpresa-label" class="property-label"><g:message code="cancelacion.nombreEmpresa.label" default="Nombre Empresa" /></span>
					
						<span class="property-value" aria-labelledby="nombreEmpresa-label"><g:fieldValue bean="${cancelacionInstance}" field="nombreEmpresa"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cancelacionInstance?.fechaDeRecepcion}">
				<li class="fieldcontain">
					<span id="fechaDeRecepcion-label" class="property-label"><g:message code="cancelacion.fechaDeRecepcion.label" default="Fecha De Recepcion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeRecepcion-label"><g:fieldValue bean="${cancelacionInstance}" field="fechaDeRecepcion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cancelacionInstance?.fechaDeLiquidacion}">
				<li class="fieldcontain">
					<span id="fechaDeLiquidacion-label" class="property-label"><g:message code="cancelacion.fechaDeLiquidacion.label" default="Fecha De Liquidacion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeLiquidacion-label"><g:fieldValue bean="${cancelacionInstance}" field="fechaDeLiquidacion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cancelacionInstance?.totalLiquidoPagable}">
				<li class="fieldcontain">
					<span id="totalLiquidoPagable-label" class="property-label"><g:message code="cancelacion.totalLiquidoPagable.label" default="Total Liquido Pagable" /></span>
					
						<span class="property-value" aria-labelledby="totalLiquidoPagable-label"><g:fieldValue bean="${cancelacionInstance}" field="totalLiquidoPagable"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cancelacionInstance?.fechaDeCancelacion}">
				<li class="fieldcontain">
					<span id="fechaDeCancelacion-label" class="property-label"><g:message code="cancelacion.fechaDeCancelacion.label" default="Fecha De Cancelacion" /></span>
					
						<span class="property-value" aria-labelledby="fechaDeCancelacion-label"><g:formatDate date="${cancelacionInstance?.fechaDeCancelacion}" format="dd/MM/yyyy"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cancelacionInstance?.observaciones}">
				<li class="fieldcontain">
					<span id="observaciones-label" class="property-label"><g:message code="cancelacion.observaciones.label" default="Observaciones" /></span>
					
						<span class="property-value" aria-labelledby="observaciones-label"><g:fieldValue bean="${cancelacionInstance}" field="observaciones"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${cancelacionInstance?.usuario}">
				<li class="fieldcontain">
					<span id="usuario-label" class="property-label"><g:message code="cancelacion.usuario.label" default="Usuario" /></span>
					
						<span class="property-value" aria-labelledby="usuario-label"><g:link controller="secUser" action="show" id="${cancelacionInstance?.usuario?.id}">${cancelacionInstance?.usuario?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${cancelacionInstance?.id}" />
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
